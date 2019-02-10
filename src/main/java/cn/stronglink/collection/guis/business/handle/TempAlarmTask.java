package cn.stronglink.collection.guis.business.handle;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.stronglink.collection.guis.iot.devices.guis.message.TagPojo;
import cn.stronglink.collection.guis.iot.moudle.temp.TempHistory;
import cn.stronglink.collection.guis.iot.moudle.temp.TempHistoryRepository;
import cn.stronglink.collection.guis.iot.mq.producer.QueueSender;

@Component
public class TempAlarmTask {
	private final static Logger logger = LogManager.getLogger(TempAlarmTask.class);
	@Autowired
	private QueueSender queueSender;
	private static final String TOPIC = "itom";
	private static final String ACTION_CODE = "reader006";
	private static final String RFID_TYPE = "RU1000";
	@Autowired
	private TempHistoryRepository tempHistoryRepository;
	@Scheduled(cron = "0 0/10 * * * ?")
	public void uploadTemp() {
		List<TempHistory> tempHistoryList = tempHistoryRepository.findAll();
		List<JSONObject> devAddrList = new ArrayList<>();
		if(tempHistoryList!=null && tempHistoryList.size()>0) {
			for(TempHistory tempHistory : tempHistoryList) {
				JSONObject jsonObject = JSONObject.parseObject(tempHistory.getJson());
				if(jsonObject!=null) {
					devAddrList.add(jsonObject);	
				}
			}
			
			JSONObject object  = new JSONObject();
			object.put("devAddrList", devAddrList);
			TagPojo tagPojo = new TagPojo();
			tagPojo.setAwsPostdata(object.toJSONString());
			tagPojo.setActioncode(ACTION_CODE);
			tagPojo.setGUID(java.util.UUID.randomUUID().toString());
			tagPojo.setRfidtype(RFID_TYPE);
			String json = JSON.toJSONString(tagPojo);
			queueSender.send(TOPIC, json);
			logger.debug("定时向itom发送温度数据："+json);
		}
	}

}
