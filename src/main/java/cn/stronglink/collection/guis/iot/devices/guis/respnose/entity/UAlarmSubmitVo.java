package cn.stronglink.collection.guis.iot.devices.guis.respnose.entity;

import java.util.List;

/**
 * U位告警传输格式
 * @author yuzhantao
 *
 */
public class UAlarmSubmitVo {
	private List<UInfoArrayVo> devAddrList;
	private String devAddrCode;
	public List<UInfoArrayVo> getDevAddrList() {
		return devAddrList;
	}

	public void setDevAddrList(List<UInfoArrayVo> devAddrList) {
		this.devAddrList = devAddrList;
	}

	public String getDevAddrCode() {
		return devAddrCode;
	}

	public void setDevAddrCode(String devAddrCode) {
		this.devAddrCode = devAddrCode;
	}
	
	
}
