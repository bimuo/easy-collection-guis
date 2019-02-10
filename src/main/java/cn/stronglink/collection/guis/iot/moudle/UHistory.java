package cn.stronglink.collection.guis.iot.moudle;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 记录U位扫描的数据
 * @author yuzhantao
 *
 */
@Entity
public class UHistory {
	@Id
	private String deviceCode;
	@Column(columnDefinition = "varchar(1024)")
	private String json;
	private Date updateTime;
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
