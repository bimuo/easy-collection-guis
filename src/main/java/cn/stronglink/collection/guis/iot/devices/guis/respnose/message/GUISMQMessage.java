package cn.stronglink.collection.guis.iot.devices.guis.respnose.message;
/**
 * 向业务系统（MQ）发送   数据  实体类
 * @author 25969
 *
 */
public class GUISMQMessage {

	private String actioncode;
	private String rfidtype;
	private Object awsPostdata;
	private String timeStamp;
	private boolean isTiemout=false;
	public String getActioncode() {
		return actioncode;
	}
	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}
	public String getRfidtype() {
		return rfidtype;
	}
	public void setRfidtype(String rfidtype) {
		this.rfidtype = rfidtype;
	}
	public Object getAwsPostdata() {
		return awsPostdata;
	}
	public void setAwsPostdata(Object awsPostdata) {
		this.awsPostdata = awsPostdata;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public boolean isTiemout() {
		return isTiemout;
	}
	public void setTiemout(boolean isTiemout) {
		this.isTiemout = isTiemout;
	}
	
	
}
