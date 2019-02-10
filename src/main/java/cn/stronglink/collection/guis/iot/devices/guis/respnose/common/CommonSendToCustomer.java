package cn.stronglink.collection.guis.iot.devices.guis.respnose.common;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessage;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 公共发送响应报文
 * @author 25969
 *
 */
public class CommonSendToCustomer {
	private static Logger logger = LogManager.getLogger(CommonSendToCustomer.class.getName());
	private static GUISMessageFactory guisFactory=new GUISMessageFactory();
   /**
    * 公共发送响应报文
    * state 成功0,失败1
    * length  协议中 LEN：数据长度，LEN= DATA    作为参数，也是因为他协议中 长度不固定
    */
	
	static long index=0;
	public static void commonSendMessageToCustomer(ChannelHandlerContext ctx, GUISMessage t,byte state,int length) {
		byte[] datas = guisFactory.createGUISMessage(t, state,length);
//		String hex = ByteUtil.byteArrToHexString(datas);
//		System.out.println("返回到下位机的命令="+hex);
		 ByteBuf bs  = Unpooled.copiedBuffer(datas);
		//暂时注释GUISResponseHandleContext.channels.writeAndFlush(bs,new MyChannelMatchers(getIp(ctx)));
	//	ctx.channel().config().setWriteBufferHighWaterMark(10*1024*1024);
	//	if(ctx.channel().isWritable()) {
//			System.out.println("channels size="+GUISResponseHandleContext.channels.size());
		ctx.channel().writeAndFlush(bs);
	/*	}else {
			System.out.println("The write queue is busy");
		}*/
//		bs.release();
		datas=null;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void commonSendMessageToCustomer(ChannelHandlerContext ctx, GUISMessage t,byte state,int length,final String printMsg) {
		byte[] datas = guisFactory.createGUISMessage(t, state,length);
//		String hex = ByteUtil.byteArrToHexString(datas);
//		System.out.println("返回到下位机的命令="+hex);
		 ByteBuf bs  = Unpooled.copiedBuffer(datas);
		//暂时注释GUISResponseHandleContext.channels.writeAndFlush(bs,new MyChannelMatchers(getIp(ctx)));
	//	ctx.channel().config().setWriteBufferHighWaterMark(10*1024*1024);
	//	if(ctx.channel().isWritable()) {
//			System.out.println("channels size="+GUISResponseHandleContext.channels.size());
		 ChannelFuture cf = ctx.channel().writeAndFlush(bs);
		 cf.addListener(new GenericFutureListener() {
			@Override
			public void operationComplete(Future future) throws Exception {
				logger.info("采集->GUIS数据发送完成 {}",printMsg);
			}
			 
		 });
	/*	}else {
			System.out.println("The write queue is busy");
		}*/
//		bs.release();
		datas=null;
		
	}
	
	/**
	 * 获取IP
	 * @param ctx 
	 * @return
	 */
	public static String getIp(ChannelHandlerContext ctx) {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
				.remoteAddress();
	return insocket.getAddress().getHostAddress();
}

	

	
}
