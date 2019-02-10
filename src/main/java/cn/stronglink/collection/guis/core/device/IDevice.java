package cn.stronglink.collection.guis.core.device;
/**
 * 设备接口
 * @author yuzhantao
 *
 */
public interface IDevice {
	/**
	 * 获取设备编号
	 * @return 设备编号
	 */
	String getDeviceCode();
	/**
	 * 设置设备编号
	 * @param deviceCode 设备编号
	 */
	void setDeviceCode(String deviceCode);
	/**
	 * 获取设备IP地址
	 * @return IP地址
	 */
	String getDeviceIp();
}
