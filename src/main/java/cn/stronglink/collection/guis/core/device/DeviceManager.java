package cn.stronglink.collection.guis.core.device;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备管理
 * @author yuzhantao
 *
 */
public class DeviceManager {
	protected Map<String,IDevice> deviceMap = new ConcurrentHashMap<>();
	protected static DeviceManager instance;
	
	/**
	 * 单利设备管理
	 * @return 设备管理实体
	 */
	public static DeviceManager getInstance() {
		if(instance==null) {
			instance = new DeviceManager();
		}
		return instance;
	}
	
	private DeviceManager() {}
	
	public Map<String,IDevice> getDeviceMap(){
		return this.deviceMap;
	}
	
	/**
	 * 注册设备
	 * @param dev 设备实体
	 */
	public void registerDevice(IDevice dev) {
		this.deviceMap.put(dev.getDeviceIp(), dev);
	}
	/**
	 * 注销设备
	 * @param ip 设备IP
	 */
	public void unregisterDevice(String ip) {
		if(this.deviceMap.containsKey(ip)) {
			this.deviceMap.remove(ip);
		}
	}
	
	/**
	 * 获取设备实体
	 * @param ip IP地址
	 * @return 设备实体
	 */
	public IDevice findDevice(String ip) {
		if(this.deviceMap.containsKey(ip)) {
			return this.deviceMap.get(ip);
		}else {
			return null;
		}
	}
}
