package cn.stronglink.collection.guis.business.handle.message;

public class RequestIMessageOfITOM {
	private String actioncode;
	private String GUID;
	private Object postdata;
	private String rfidtype;
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
	public Object getPostdata() {
		return postdata;
	}
	public void setPostdata(Object postdata) {
		this.postdata = postdata;
	}
	public String getRfidtype() {
		return rfidtype;
	}
	public void setRfidtype(String rfidtype) {
		this.rfidtype = rfidtype;
	}
	
}
