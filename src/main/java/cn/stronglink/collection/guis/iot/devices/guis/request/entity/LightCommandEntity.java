package cn.stronglink.collection.guis.iot.devices.guis.request.entity;

import java.util.ArrayList;
import java.util.List;

import cn.stronglink.collection.guis.core.annotation.NotProguard;
/**
 * 解析  1.5	向机柜发送亮灭灯的指令(new)  京东 实体类
 * @author 25969
 *
 */
@NotProguard
public class LightCommandEntity {
	
	private List<DevAddr> devAddrList=new ArrayList<>();
	
	@NotProguard
	public static class DevAddr{
		
		private String devAddrCode;
		private String timeStamp;
		private List<LightCommand> lightCommandList=new ArrayList<>();
		
		public String getDevAddrCode() {
			return devAddrCode;
		}
		public void setDevAddrCode(String devAddrCode) {
			this.devAddrCode = devAddrCode;
		}
		public List<LightCommand> getLightCommandList() {
			return lightCommandList;
		}
		public void setLightCommandList(List<LightCommand> lightCommandList) {
			this.lightCommandList = lightCommandList;
		}
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
	
	
	
	}
	
	@NotProguard
	public static class LightCommand{
		
		private Integer onTime;
		private Integer offTime;
		private Integer loopCount;
		private Integer u;
		private Integer color;   //颜色 （0=灭 1=红 2=绿 3=蓝 4=黄 5=紫 6=白 7=定制）
		private Integer luminance;  //（0--7）8个级别的亮度，通常3以上才好用
		public Integer getOnTime() {
			return onTime;
		}
		public void setOnTime(Integer onTime) {
			this.onTime = onTime;
		}
		public Integer getOffTime() {
			return offTime;
		}
		public void setOffTime(Integer offTime) {
			this.offTime = offTime;
		}
		public Integer getLoopCount() {
			return loopCount;
		}
		public void setLoopCount(Integer loopCount) {
			this.loopCount = loopCount;
		}
		public Integer getU() {
			return u;
		}
		public void setU(Integer u) {
			this.u = u;
		}
		/**
		 * @return the color
		 */
		public Integer getColor() {
			return color;
		}
		/**
		 * @param color the color to set
		 */
		public void setColor(Integer color) {
			this.color = color;
		}
		/**
		 * @return the luminance
		 */
		public Integer getLuminance() {
			return luminance;
		}
		/**
		 * @param luminance the luminance to set
		 */
		public void setLuminance(Integer luminance) {
			this.luminance = luminance;
		}
		
		
		
	}

	public List<DevAddr> getDevAddrList() {
		return devAddrList;
	}

	public void setDevAddrList(List<DevAddr> devAddrList) {
		this.devAddrList = devAddrList;
	}

}
