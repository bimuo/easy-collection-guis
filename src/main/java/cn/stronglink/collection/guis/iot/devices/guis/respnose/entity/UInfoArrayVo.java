package cn.stronglink.collection.guis.iot.devices.guis.respnose.entity;

import java.util.List;

/**
 * U位信息数组对象，为告警传输用。
 * @author yuzhantao
 *
 */
public class UInfoArrayVo {
	private List<UInfoVo> udevInfo;
	private String devAddrCode;
	public List<UInfoVo> getUdevInfo() {
		return udevInfo;
	}

	public void setUdevInfo(List<UInfoVo> udevInfo) {
		this.udevInfo = udevInfo;
	}

	public String getDevAddrCode() {
		return devAddrCode;
	}

	public void setDevAddrCode(String devAddrCode) {
		this.devAddrCode = devAddrCode;
	}
}
