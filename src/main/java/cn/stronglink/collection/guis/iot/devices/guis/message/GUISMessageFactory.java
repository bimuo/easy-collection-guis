package cn.stronglink.collection.guis.iot.devices.guis.message;

import cn.stronglink.collection.guis.core.util.ByteUtil;
/**
 * 自动应答客户端  工厂（硬件每次发送报文给上位机后，上位机必须自动做出应答，此类和解析业务系统的工厂有区别）
 * @author 25969
 *
 */
public class GUISMessageFactory {
	private final static short HEADER1=0x55;	// 协议头
	private final static short HEADER2=0xAA;	// 协议头
	
	public byte[] createGUISMessage(
		 GUISMessage guis,
		 byte state, 
		 int length
    ) {
		byte[] result = new byte[10];
		// 设置协议头
		result[0]=(byte)HEADER1;
		result[1]=(byte)HEADER2;
		// 设置长度    先默认是5，如果到时候变了再改这个方法吧，反正长度作为参数传递进来了
		byte[] bLen = ByteUtil.shortToByteArr((short)(length+4));
		System.arraycopy(bLen, 0, result, 2, bLen.length);
		//设置主机编号
		System.arraycopy(guis.getHostNumber(), 0, result, 4, 3);
		//设置命令字
		result[7]=guis.getCommand();
		//状态，成功0,失败1
		result[8]=state;
		// 设置校验码
		byte nor=0;
		//NOR：从长度字节到数据的异或校验
		for(int i=2;i<result.length-1;i++) {
			nor^=result[i];
		}
		result[result.length-1]=nor;
		
		return result;
    }
	
}
