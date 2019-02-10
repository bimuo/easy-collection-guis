package cn.stronglink.collection.guis.iot.devices.guis.message;
/**
 *   GUIS 消息实体
 * @author 25969
 *
 */
public class GUISMessage {
	/**
	 * 主机编号  	3字节
	 */
	private byte[] hostNumber;
	/**
	 * 命令       1字节         用于区分协议的类型
	 */
	private byte command;  
	/**
	 * 数据     N字节     数据段
	 */
	private byte[] data;
	
	public GUISMessage() {}
	
	public GUISMessage(byte command, byte[] hostNumber, byte[] data) {
		this.hostNumber = hostNumber;
		this.command = command;
		this.data = data;
	}
	
	
	public byte[] getHostNumber() {
		return hostNumber;
	}
	public void setHostNumber(byte[] hostNumber) {
		this.hostNumber = hostNumber;
	}
	public byte getCommand() {
		return command;
	}
	public void setCommand(byte command) {
		this.command = command;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
