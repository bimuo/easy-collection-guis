package cn.stronglink.collection.guis.iot.devices.guis.respnose.entity;

import cn.stronglink.collection.guis.core.annotation.NotProguard;

@NotProguard
public class DoorInfoEntity {
	
	private String devAddrCode;
	private String cdoorstate;
	
	public String getDevAddrCode() {
		return devAddrCode;
	}
	public void setDevAddrCode(String devAddrCode) {
		this.devAddrCode = devAddrCode;
	}
	public String getCdoorstate() {
		return cdoorstate;
	}
	public void setCdoorstate(String cdoorstate) {
		this.cdoorstate = cdoorstate;
	}


}
