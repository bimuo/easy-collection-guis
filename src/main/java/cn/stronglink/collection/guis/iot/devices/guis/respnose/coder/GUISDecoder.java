package cn.stronglink.collection.guis.iot.devices.guis.respnose.coder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.stronglink.collection.guis.core.util.ByteUtil;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class GUISDecoder extends LengthFieldBasedFrameDecoder {
	private static Logger logger = LogManager.getLogger(GUISDecoder.class.getName());

	// 头部起始码 0x5a 0xa5
	private static final int HEADER_SIZE = 6;
	// 校验码长度
	private static final int BCC_SIZE = 1;

	/**
	 * 协议头 2字节 包头，固定值，0X55AA
	 */
	private byte header;
	/**
	 * 数据长度 2字节 数据长度，LEN=HID+CMD+DATA
	 */
	private short dataLen;
	/**
	 * DATA 长度 DATA=dataLen-4
	 */
	private short realDataLen;
	/**
	 * 主机编号 3字节
	 */
	private byte[] hostNumber = new byte[3];
	/**
	 * 命令 1字节 用于区分协议的类型
	 */
	private byte command;
	/**
	 * 数据 N字节 数据段
	 */
	private byte[] data = new byte[0];

	/**
	 * 
	 * @param maxFrameLength
	 *            解码时，处理每个帧数据的最大长度
	 * @param lengthFieldOffset
	 *            该帧数据中，存放该帧数据的长度的数据的起始位置
	 * @param lengthFieldLength
	 *            记录该帧数据长度的字段本身的长度
	 * @param lengthAdjustment
	 *            修改帧数据长度字段中定义的值，可以为负数
	 * @param initialBytesToStrip
	 *            解析的时候需要跳过的字节数
	 * @param failFast
	 *            为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
	 */
	public GUISDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip, boolean failFast) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ByteBuf bf = this.internalBuffer();
		bf.release();
	}

//	public static void main(String[] args) {
////		76  command=23  hostNumber=004526  dataLen=13  data=00 00 01 626235505E01
//		byte[] len = ByteUtil.shortToByteArr((short)13);
//		byte[] bb= {0x23,0x00,0x45,0x26,len[0],len[1],0x00,0x00,0x01,
//				Integer.valueOf("62",16).byteValue(),
//				0x62,0x35,0x50,0x5E,0x01};
//		 byte nor=0;
//			//NOR：从长度字节到数据的异或校验
//			for(int i=0;i<bb.length;i++) {
//				nor^=bb[i];
//			}
//			System.out.println(ByteUtil.byteArrToHexString(bb));
//			
//			System.out.println(ByteUtil.byteArrToHexString(new byte[] {nor}));
//	}
	int index = 0;
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

//		 byte[] bb = new byte[in.readableBytes()]; in.readBytes(bb);
//		 byte nor=0;
//			//NOR：从长度字节到数据的异或校验
//			for(int i=2;i<bb.length-1;i++) {
//				nor^=bb[i];
//			}
//		 System.out.println(ByteUtil.byteArrToHexString(bb)+"    bbc="+ByteUtil.byteArrToHexString(new byte[]{nor})); return null;
		if (in == null) {
			logger.info("threadId=" + Thread.currentThread().getId() + "return null;");
			return null;
		}
		// System.out.println("#info 可读信息长度="+in.readableBytes());

		if (this.data != null) { // body不等于空说明上一次读到完整数据了
			if (in.readableBytes() < HEADER_SIZE) {
				return null;
			}
			this.header = in.readByte();
			if (this.header != Integer.valueOf("55", 16).byteValue()) { // 判断包头
				return null;
			}
			this.header = in.readByte();
			if (this.header != Integer.valueOf("AA", 16).byteValue()) { // 判断包头
				return null;
			}
			this.dataLen = in.readShort(); // 数据长度，2个字节
			in.readBytes(this.hostNumber); // 主机编号
			this.command = in.readByte(); // 命令
			// 根据 LEN=HID+CMD+DATA，DATA长度=LEN-HID-CMD
			// 计算 DATA长度
			this.realDataLen = (short) (this.dataLen - 4);

		}
		if (in.readableBytes() < this.realDataLen + BCC_SIZE) {
			this.data = null;
			return null;
		} else {
			this.data = new byte[this.realDataLen];
			if (this.realDataLen > 0) {
				in.readBytes(this.data);
			}
			byte bcc = in.readByte();
			
			byte[] len = ByteUtil.shortToByteArr(this.dataLen);
			byte tempBcc=0;
			tempBcc^=len[0];
			tempBcc^=len[1];

			for (byte b : this.hostNumber) {
				tempBcc^=b;
			}
			tempBcc ^= this.command;
			for (byte b : this.data) {
				tempBcc ^= b;
			}
			
			if(this.data.length>300) {
				System.out.println("");
			}
//			logger.info("原始数据:55AA{}{}{}{}{}",
//					ByteUtil.byteArrToHexString(len),
//					ByteUtil.byteArrToHexString(this.hostNumber),
//					ByteUtil.byteArrToHexString(new byte[] {command}),
//					ByteUtil.byteArrToHexString(this.data),
//					ByteUtil.byteArrToHexString(new byte[] {bcc})
//					);
			// 校验BCC正确性，并过滤command=82（心跳）协议不校验BCC
			if (bcc != tempBcc && this.isCheckBcc(this.command)) {
				logger.info("BCC验证失败,源BBC={} 目标极计算BBC={}  command={}  hostNumber={}  dataLen={}  data={}", 
						ByteUtil.byteArrToHexString(new byte[] {bcc}), 
						ByteUtil.byteArrToHexString(new byte[] {tempBcc}),
						ByteUtil.byteArrToHexString(new byte[] {this.command}),
						ByteUtil.byteArrToHexString(this.hostNumber),
						this.dataLen,
						ByteUtil.byteArrToHexString(this.data)
						);
				this.data=new byte[0];
				return null;
			}
		}
		GUISMessage message = new GUISMessage(this.command, this.hostNumber, this.data);
		return message;
	}
	
	private boolean isCheckBcc(byte command) {
//		if(this.command == Integer.valueOf("82",16).byteValue() ||
//				this.command == Integer.valueOf("23",16).byteValue()) {
//			return false;
//		}else {
//			return true;
//		}
		return false;
	}
}