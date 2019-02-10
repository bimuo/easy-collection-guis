package cn.stronglink.collection.guis.iot.devices.guis.request.entity;
/**
 * 标签信息 集合
 *                       
 * @Filename: Cardlist.java
 * @Description: 
 * @Version: 1.0
 * @Author: 周军
 * @Email: zhoujun@hzdd.com
 * @History:<br>
 *
 */
public class Cardlist {
   /**标签号*/
	private String card;
	/**低电量     1为正常   2为低电量*/
	private String batterState;
	/**标签号侦听基站短地址，最多8项*/
    private String shortAddr;
    /**白灯状态  0为灭  1为亮 */
    private String wlightstate;
    /**蓝灯状态    0为灭  1为亮*/
    private String blightstate;
    /**绿灯状态    0为灭  1为亮*/
    private String gightstate;
    /**红灯状态    0为灭  1为亮*/
    private String rlightstate;
    /**按键状态  1为按下 2为弹起*/
    private String btnstate;
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getBatterState() {
		return batterState;
	}
	public void setBatterState(String batterState) {
		this.batterState = batterState;
	}
	public String getShortAddr() {
		return shortAddr;
	}
	public void setShortAddr(String shortAddr) {
		this.shortAddr = shortAddr;
	}
	public String getWlightstate() {
		return wlightstate;
	}
	public void setWlightstate(String wlightstate) {
		this.wlightstate = wlightstate;
	}
	public String getBlightstate() {
		return blightstate;
	}
	public void setBlightstate(String blightstate) {
		this.blightstate = blightstate;
	}
	public String getGightstate() {
		return gightstate;
	}
	public void setGightstate(String gightstate) {
		this.gightstate = gightstate;
	}
	public String getRlightstate() {
		return rlightstate;
	}
	public void setRlightstate(String rlightstate) {
		this.rlightstate = rlightstate;
	}
	public String getBtnstate() {
		return btnstate;
	}
	public void setBtnstate(String btnstate) {
		this.btnstate = btnstate;
	}
    
}
