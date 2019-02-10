package cn.stronglink.collection.guis.iot.devices.guis.request.entity;
/**
 * 返回是否成功标识给京东  实体
 * @author 25969
 *
 */
public class ReturnUInfoEntity {
   private String devAddrCode;   //机柜编号
   
   private boolean success;  //点亮标签是否成功，true表示成功，如有指定的U位不存在，则返回flase

public String getDevAddrCode() {
	return devAddrCode;
}

public void setDevAddrCode(String devAddrCode) {
	this.devAddrCode = devAddrCode;
}

public boolean isSuccess() {
	return success;
}

public void setSuccess(boolean success) {
	this.success = success;
}


   
   
   
}
