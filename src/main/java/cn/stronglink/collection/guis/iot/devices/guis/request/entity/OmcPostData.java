package cn.stronglink.collection.guis.iot.devices.guis.request.entity;
/***
 * 发送数据 OMC   pojo
 *                       
 * @Filename: Postdata.java
 * @Description: 
 * @Version: 1.0
 * @Author: 周军
 * @Email: zhoujun@hzdd.com
 * @History:<br>
 *
 */
public class OmcPostData {
	
	
	/**OMC向外部系统推送数据*/
	   /*  "id":67
	     "devsn":"133.0.131",
	     "rfid":"133.0.131",
	     "position":15,
	     "eventType":1,      "eventDesc":"插天线"
	     		+ "
	*/     
	   private String id;   //该预警的编号
	     
	   private String devsn;   //  转换器编号
	   
	    private String rfid;   // 标签编号
	    
	    private String  position  ; //U位
	     
	    private String eventType;  //  事件类型
	    
	    private String eventDesc;   //事件描述

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDevsn() {
			return devsn;
		}

		public void setDevsn(String devsn) {
			this.devsn = devsn;
		}

		public String getRfid() {
			return rfid;
		}

		public void setRfid(String rfid) {
			this.rfid = rfid;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getEventType() {
			return eventType;
		}

		public void setEventType(String eventType) {
			this.eventType = eventType;
		}

		public String getEventDesc() {
			return eventDesc;
		}

		public void setEventDesc(String eventDesc) {
			this.eventDesc = eventDesc;
		}
	    
	    
	    
}
