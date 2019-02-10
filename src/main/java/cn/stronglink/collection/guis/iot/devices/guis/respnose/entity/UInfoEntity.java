package cn.stronglink.collection.guis.iot.devices.guis.respnose.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.stronglink.collection.guis.core.annotation.NotProguard;

@NotProguard
public class UInfoEntity {
	private String devAddrCode;
	private String nativeIPAddress;
	private String subnetMask;
    private String gateway;
    private String targetIaddressIp;
    private String dns;
    private String targetPort;
    private String macAddress;
	private List<DevAddr> devAddrList=new ArrayList<>();
	
	@NotProguard
	public class DevAddr{
		
		private String devAddrCode;
		private List<UdevInfo> udevInfo=new ArrayList<>();
		private byte doorState;
		private Date doorStateUpdateTime;
		private Date checkUpdateTime;
		
		public String getDevAddrCode() {
			return devAddrCode;
		}
		public void setDevAddrCode(String devAddrCode) {
			this.devAddrCode = devAddrCode;
		}
		public List<UdevInfo> getUdevInfo() {
			return udevInfo;
		}
		public void setUdevInfo(List<UdevInfo> udevInfo) {
			this.udevInfo = udevInfo;
		}
		public byte getDoorState() {
			return doorState;
		}

		public void setDoorState(byte doorState) {
			this.doorState = doorState;
		}

		public Date getDoorStateUpdateTime() {
			return doorStateUpdateTime;
		}

		public void setDoorStateUpdateTime(Date doorStateUpdateTime) {
			this.doorStateUpdateTime = doorStateUpdateTime;
		}
		public Date getCheckUpdateTime() {
			return checkUpdateTime;
		}
		public void setCheckUpdateTime(Date checkUpdateTime) {
			this.checkUpdateTime = checkUpdateTime;
		}
	}
	
	@NotProguard
	public class UdevInfo{
		
		private Integer U;
		private String rfid;
		
		public Integer getU() {
			return U;
		}
		public void setU(Integer u) {
			U = u;
		}
		public String getRfid() {
			return rfid;
		}
		public void setRfid(String rfid) {
			this.rfid = rfid;
		}
		
	}

	public List<DevAddr> getDevAddrList() {
		return devAddrList;
	}

	public void setDevAddrList(List<DevAddr> devAddrList) {
		this.devAddrList = devAddrList;
	}

	public String getDevAddrCode() {
		return devAddrCode;
	}

	public void setDevAddrCode(String devAddrCode) {
		this.devAddrCode = devAddrCode;
	}

	public String getNativeIPAddress() {
		return nativeIPAddress;
	}

	public void setNativeIPAddress(String nativeIPAddress) {
		this.nativeIPAddress = nativeIPAddress;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getTargetIaddressIp() {
		return targetIaddressIp;
	}

	public void setTargetIaddressIp(String targetIaddressIp) {
		this.targetIaddressIp = targetIaddressIp;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public String getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(String targetPort) {
		this.targetPort = targetPort;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

}
