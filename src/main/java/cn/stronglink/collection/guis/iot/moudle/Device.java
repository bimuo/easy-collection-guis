package cn.stronglink.collection.guis.iot.moudle;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Device {
	private String productKey;
	@Id
	private String deviceName;
	private String deviceSecret;
	public String getProductKey() {
		return productKey;
	}
	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceSecret() {
		return deviceSecret;
	}
	public void setDeviceSecret(String deviceSecret) {
		this.deviceSecret = deviceSecret;
	}
	
	
}
