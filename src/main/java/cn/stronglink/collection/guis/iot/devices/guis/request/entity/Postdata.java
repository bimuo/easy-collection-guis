package cn.stronglink.collection.guis.iot.devices.guis.request.entity;

import java.util.List;

/***
 * 发送数据  pojo
 *                       
 * @Filename: Postdata.java
 * @Description: 
 * @Version: 1.0
 * @Author: 周军
 * @Email: zhoujun@hzdd.com
 * @History:<br>
 *
 */
public class Postdata {
    /*基站编号**/
	//private List<String> devsn; 
	
	private String devsn;
	
	private List<String> devAddrCodes;   //RU1000SN号集合
	
	/**标签号集合*/
	private List<Cards> cards;
	/**标签信息 集合*/
	private List<Cardlist> cardlist;
	/**标签号侦听基站短地址，最多8项*/
    private int shortAddr;
    /**基站IP*/
    private String ip;
    /**网关地址*/
    private String gateway;
    /**服务IP*/
    private String serverip;
    /**服务端口*/
    private int serverport;
    /**单个 基站编号*/
    private String readerDevsn;
    /**子网掩码*/
    private String subnetmark;
   	private String cvtName;
    private int iDevAddr;
    private int assetMon;   //资产监控状态   1表示监控  0表示不监控和你
    private int assetTempe;  //温度监控状态  1表示
    private int authorized;  
    private int ucoding;
    private int uLation;  //U位编号
    private String content;  //自定义RU1000显示屏报警内容
    private int redCf;    //是否启用红外触发信息主动上传
    private int redZd;    //是否禁用 RU1000 红外触发主动读取
    
    private String tkdevsn;
    private String alarmInfo;
   
    /**DNS地址*/
    private String dnsMasTer;
    /**DNS备用地址*/
    private String dnsOther;
    /**网络使用模式       0表示IP   1表示域名*/
     private String netmode;
     /**域名*/
     private String domain;
  	private int serState; // RU1000的正反序状态， 正序是1 反序是2

  	private int loginPicState; // 登录界面的状态 1是无登录界面 2是启动登录界面

  	private int uLoStart; // RU1000的起始U位
    
	public int getAssetMon() {
		return assetMon;
	}
	public void setAssetMon(int assetMon) {
		this.assetMon = assetMon;
	}
	public int getAssetTempe() {
		return assetTempe;
	}
	public void setAssetTempe(int assetTempe) {
		this.assetTempe = assetTempe;
	}
	public int getAuthorized() {
		return authorized;
	}
	public void setAuthorized(int authorized) {
		this.authorized = authorized;
	}
	public String getCvtName() {
		return cvtName;
	}
	public void setCvtName(String cvtName) {
		this.cvtName = cvtName;
	}
	public String getSubnetmark() {
		return subnetmark;
	}
	public void setSubnetmark(String subnetmark) {
		this.subnetmark = subnetmark;
	}
	public String getReaderDevsn() {
		return readerDevsn;
	}
	public void setReaderDevsn(String readerDevsn) {
		this.readerDevsn = readerDevsn;
	}
	
	public String getDevsn() {
		return devsn;
	}
	public void setDevsn(String devsn) {
		this.devsn = devsn;
	}
	public List<Cards> getCards() {
		return cards;
	}
	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}
	public List<Cardlist> getCardlist() {
		return cardlist;
	}
	public void setCardlist(List<Cardlist> cardlist) {
		this.cardlist = cardlist;
	}
	
	public int getShortAddr() {
		return shortAddr;
	}
	public void setShortAddr(int shortAddr) {
		this.shortAddr = shortAddr;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getServerip() {
		return serverip;
	}
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	public int getServerport() {
		return serverport;
	}
	public void setServerport(int serverport) {
		this.serverport = serverport;
	}
	public int getiDevAddr() {
		return iDevAddr;
	}
	public void setiDevAddr(int iDevAddr) {
		this.iDevAddr = iDevAddr;
	}
	public int getuLation() {
		return uLation;
	}
	public void setuLation(int uLation) {
		this.uLation = uLation;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRedCf() {
		return redCf;
	}
	public void setRedCf(int redCf) {
		this.redCf = redCf;
	}
	public int getRedZd() {
		return redZd;
	}
	public void setRedZd(int redZd) {
		this.redZd = redZd;
	}
	public String getDnsMasTer() {
		return dnsMasTer;
	}
	public void setDnsMasTer(String dnsMasTer) {
		this.dnsMasTer = dnsMasTer;
	}
	public String getDnsOther() {
		return dnsOther;
	}
	public void setDnsOther(String dnsOther) {
		this.dnsOther = dnsOther;
	}
	public String getNetmode() {
		return netmode;
	}
	public void setNetmode(String netmode) {
		this.netmode = netmode;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getUcoding() {
		return ucoding;
	}
	public void setUcoding(int ucoding) {
		this.ucoding = ucoding;
	}
	public List<String> getDevAddrCodes() {
		return devAddrCodes;
	}
	public void setDevAddrCodes(List<String> devAddrCodes) {
		this.devAddrCodes = devAddrCodes;
	}
	public String getTkdevsn() {
		return tkdevsn;
	}
	public void setTkdevsn(String tkdevsn) {
		this.tkdevsn = tkdevsn;
	}
	public String getAlarmInfo() {
		return alarmInfo;
	}
	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}
	public int getSerState() {
		return serState;
	}
	public void setSerState(int serState) {
		this.serState = serState;
	}
	public int getLoginPicState() {
		return loginPicState;
	}
	public void setLoginPicState(int loginPicState) {
		this.loginPicState = loginPicState;
	}
	public int getuLoStart() {
		return uLoStart;
	}
	public void setuLoStart(int uLoStart) {
		this.uLoStart = uLoStart;
	}

}
