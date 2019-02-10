package cn.stronglink.collection.guis.iot.devices.guis.respnose.entity;

import java.util.List;

import cn.stronglink.collection.guis.core.annotation.NotProguard;

@NotProguard
public class AlarmInfoEntity {
	
	private String devAddrCode;
	private List<U> udevInfo;
	public String getDevAddrCode() {
		return devAddrCode;
	}
	public void setDevAddrCode(String devAddrCode) {
		this.devAddrCode = devAddrCode;
	}
	public List<U> getUdevInfo() {
		return udevInfo;
	}
	public void setUdevInfo(List<U> udevInfo) {
		this.udevInfo = udevInfo;
	}
	
	public class U {
		private String name;
		
		private Integer position;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getPosition() {
			return position;
		}
		public void setPosition(Integer position) {
			this.position = position;
		}
	}
}
