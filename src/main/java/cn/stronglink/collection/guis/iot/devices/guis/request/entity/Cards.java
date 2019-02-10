package cn.stronglink.collection.guis.iot.devices.guis.request.entity;
/**
 * 
 *   标签号集合                    
 * @Filename: Cards.java
 * @Description: 
 * @Version: 1.0
 * @Author: 周军
 * @Email: zhoujun@hzdd.com
 * @History:<br>
 *
 */
public class Cards {
	/**标签号*/
	private String card;
	/**标签灯*/
	private String lightcolor;
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getLightcolor() {
		return lightcolor;
	}
	public void setLightcolor(String lightcolor) {
		this.lightcolor = lightcolor;
	}
	
}
