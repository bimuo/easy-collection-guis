package cn.stronglink.collection.guis.iot.moudle.temp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 温度历史记录
 * @author tgb
 *
 */
@Entity
public class TempHistory {
	@Id
	private String deviceCode;
	@Column(columnDefinition = "varchar(1024)")
	private String json;
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
	
}
