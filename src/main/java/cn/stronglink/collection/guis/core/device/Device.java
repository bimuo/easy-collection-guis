package cn.stronglink.collection.guis.core.device;

/**
 * 基本设备
 * @author yuzhantao
 *
 */
public class Device implements IDevice {
	private String deviceCode;
	private String deviceIp;

	public Device(String deviceCode, String deviceIp) {
		this.deviceCode = deviceCode;
		this.deviceIp = deviceIp;
	}

	/**
	 * 获取设备编码
	 */
	@Override
	public String getDeviceCode() {
		return this.deviceCode;
	}

	/**
	 * 获取设备IP
	 */
	@Override
	public String getDeviceIp() {
		return this.deviceIp;
	}

	/**
	 * 设置设备编码
	 * @param deviceCode 设备编码
	 */
	@Override
	public void setDeviceCode(String deviceCode) {
		this.deviceCode=deviceCode;
	}

}
