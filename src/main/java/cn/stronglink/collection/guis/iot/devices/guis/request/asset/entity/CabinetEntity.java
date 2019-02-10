package cn.stronglink.collection.guis.iot.devices.guis.request.asset.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="t_cabinet")
public class CabinetEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String devAddrCode;
	private String jsonData;
	private String rfid;
	private Integer u;
	
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDevAddrCode() {
		return devAddrCode;
	}
	public void setDevAddrCode(String devAddrCode) {
		this.devAddrCode = devAddrCode;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public Integer getU() {
		return u;
	}
	public void setU(Integer u) {
		this.u = u;
	}
	
}
