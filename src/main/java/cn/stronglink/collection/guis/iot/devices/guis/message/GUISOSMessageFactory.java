package cn.stronglink.collection.guis.iot.devices.guis.message;

import cn.stronglink.collection.guis.core.util.ByteUtil;
/**
 * 解析业务系统发送的json,封装对应的byte给硬件   的工厂
 * @author 25969
 *
 */
public class GUISOSMessageFactory {
	private final static short HEADER1=0x55;	// 协议头
	private final static short HEADER2=0xAA;	// 协议头
	
	/**
	 * 
	 * @param hostNumber     主机编号 
	 * @param command         命令 
	 * @param data            数据段
	 * @return
	 */
	public byte[] createGUISOSMessage(
		byte[] hostNumber,
		byte command, 
		byte[] datas
    ) {
		// 获取数据长度
		int dataLen = datas==null?0:datas.length;
		// 在内存中开辟一条协议数据的空间
		byte[] result = new byte[9+dataLen];
		// 设置协议头
		result[0]=(byte)HEADER1;
		result[1]=(byte)HEADER2;
		//设置数据长度
		byte[] bLen = ByteUtil.shortToByteArr((short)(dataLen+4));
		System.arraycopy(bLen, 0, result, 2, bLen.length);
		//设置主机编号
		System.arraycopy(hostNumber, 0, result, 4, 3);
		//设置命令字
		result[7]=command;
		//填充数据
		System.arraycopy(datas, 0, result, 8, dataLen);
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
