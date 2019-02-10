package cn.stronglink.collection.guis.iot.devices.guis.respnose.common;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

/**
 * 公共实体类   单例   
 * 
 * 2.10 纪要
 * IP  HID  对应关系
 * .我2分钟之内没收到你的心跳包，这时我直接向上发送接口返回设备错误；
 * 上面两个暂时没必要写，等京东要的时候在说
 * 6.标签号小写    OK
 * 点亮回复     OK
 * 遗漏？例如打印的日志到时候得注释  部署前
 * 
 * 
 * @author 25969
 *
 */
public class GuisCommonEntity {
   
	private static GuisCommonEntity instance;
	
    public static synchronized GuisCommonEntity getInstance(){
        if (instance == null) {
            instance = new GuisCommonEntity();
        }
        return instance;
    }
    
    private GuisCommonEntity() {}
    
    /**
     * key 是机柜的HID（流水号），value =2时，表示发送点亮指令下位机有回复，成功；value =1时，表示发送点亮指令下位机没有回复，错误；默认是1
     * 和唐工了解过，只要点亮时，它有给我回复，硬件一般是能亮的，测了一个礼拜没发现不亮的
     * 而且他给我回复的时候，data默认 0x00(成功)，判断是否成功点亮他现在也没加
     */
    public ConcurrentHashMap <Long,Long> lightReturnMap = new ConcurrentHashMap<Long,Long>();
    /**
     * key 是机柜的HID（流水号），value =2时，表改机柜；value =1时，表示发送点亮指令下位机没有回复，错误；
     */
   // public ConcurrentHashMap<String,String> hidIsHavHeart = new ConcurrentHashMap<String,String>();  
    /**
     * key 是机柜的HID（流水号），value是 机柜IP
     * @return
     */
    public ConcurrentHashMap<String,ChannelId> getIpByHid = new ConcurrentHashMap<String,ChannelId>();
    /**
     * key 是时间戳   value 是机柜编号
     */
    public ConcurrentHashMap <Long,String> timeStampForDev = new ConcurrentHashMap<Long,String>();


	public ConcurrentHashMap<Long, Long> getLightReturnMap() {
		return lightReturnMap;
	}

	public void setLightReturnMap(ConcurrentHashMap<Long, Long> lightReturnMap) {
		this.lightReturnMap = lightReturnMap;
	}
  
	public ConcurrentHashMap<String, ChannelId> getGetIpByHid() {
		return getIpByHid;
	}

	public void setGetIpByHid(ConcurrentHashMap<String, ChannelId> getIpByHid) {
		this.getIpByHid = getIpByHid;
	}

	public ConcurrentHashMap<Long, String> getTimeStampForDev() {
		return timeStampForDev;
	}

	public void setTimeStampForDev(ConcurrentHashMap<Long, String> timeStampForDev) {
		this.timeStampForDev = timeStampForDev;
	}

	/**
	 * 向 lightReturnMap 添加 元素   如果不存在新增，存在赋值
	 */
    public void put(long timeStamp,Long value,boolean update) {
    	if(lightReturnMap.containsKey(timeStamp)==false) {
    		lightReturnMap.put(timeStamp, value);
    	}else {
    		if(update) {
    		lightReturnMap.put(timeStamp, value);
    		}
    	}
    }
    /**
     * 根据key从lightReturnMap中取value
     * 如果key不存在，默认返回hid本身
     * @return
     */
    public Long get(long hid) {
    	if(lightReturnMap.containsKey(hid)) {
    		return lightReturnMap.get(hid);
    	}
		return hid;
    }

    
    /**
     * 根据时间戳删除数据
     * @param hid
     */
	public void del(long hid) {
		lightReturnMap.remove(hid);
	}
/*
 *  根据时间戳获取机柜编号
 */
	public String getDevByTimeStamp(long hid) {
		// TODO Auto-generated method stub
		return timeStampForDev.get(hid);
	}
    
    public void putDevByTimeStamp(long timeStamp,String dev) {
    	timeStampForDev.put(timeStamp, dev);
    }
    
	public void selDev(long hid) {
		timeStampForDev.remove(hid);
		
	}
    
}
