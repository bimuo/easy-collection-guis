package cn.stronglink.collection.guis.iot.devices.guis.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.stronglink.collection.guis.iot.devices.guis.request.entity.OmcPostData;
import cn.stronglink.collection.guis.iot.devices.guis.request.entity.Postdata;

public class MQMessageOfGUIS {
	// 开关门上传报警信息格式
	// {"actioncode":"readerSingleInve","awsPostdata":"{"devAddrCode":"101433","devAddrList":[{"devAddrCode":"101433","udevInfo":[{"rfid":"CA0035E4","u":"3"},{"rfid":"CA0035E5","u":"6"}]}]}","gUID":"71ed3145-4256-3445-234","rfidtype":"RU1000"}
	
	
	
	/**动作编号 */
	private String actioncode;
	/**时间戳*/
	@JsonProperty
	private String GUID;
	/**发送数据*/
	private Postdata postdata;
	
	/**发送数据 OMC*/
	private OmcPostData omcPostData;
	
	
	/**采集服务发给业务系统  的  基站点名的数据 */
	private String awsPostdata;
	/**采集服务发给业务系统  的  新reader 连接成功状态的  文本    reader的唯一号*/
	private String readerState;
	/**RFID类型*/
	private String rfidtype;
	
	
	private String resultCode;  
	
	
	private String resultDesc;


	public String getActioncode() {
		return actioncode;
	}


	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}


	public String getGUID() {
		return GUID;
	}


	public void setGUID(String gUID) {
		GUID = gUID;
	}


	public Postdata getPostdata() {
		return postdata;
	}


	public void setPostdata(Postdata postdata) {
		this.postdata = postdata;
	}


	public OmcPostData getOmcPostData() {
		return omcPostData;
	}


	public void setOmcPostData(OmcPostData omcPostData) {
		this.omcPostData = omcPostData;
	}


	public String getAwsPostdata() {
		return awsPostdata;
	}


	public void setAwsPostdata(String awsPostdata) {
		this.awsPostdata = awsPostdata;
	}


	public String getReaderState() {
		return readerState;
	}


	public void setReaderState(String readerState) {
		this.readerState = readerState;
	}


	public String getRfidtype() {
		return rfidtype;
	}


	public void setRfidtype(String rfidtype) {
		this.rfidtype = rfidtype;
	}


	public String getResultCode() {
		return resultCode;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}


	public String getResultDesc() {
		return resultDesc;
	}


	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}  
	
	
}
