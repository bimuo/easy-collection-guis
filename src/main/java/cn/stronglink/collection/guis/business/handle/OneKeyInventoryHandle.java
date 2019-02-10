package cn.stronglink.collection.guis.business.handle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.stronglink.collection.guis.business.handle.message.RequestIMessageOfITOM;
import cn.stronglink.collection.guis.business.handle.message.RequestOneKeyInventoryMessage;
import cn.stronglink.collection.guis.core.message.IMessageHandle;
import cn.stronglink.collection.guis.iot.devices.guis.message.TagPojo;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.entity.UAlarmSubmitVo;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.entity.UInfoArrayVo;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.entity.UInfoVo;
import cn.stronglink.collection.guis.iot.moudle.UHistory;
import cn.stronglink.collection.guis.iot.moudle.UHistoryRepository;
import cn.stronglink.collection.guis.iot.mq.producer.QueueSender;
import io.netty.channel.ChannelHandlerContext;

/**
 * 接收到ITOM一键盘点的处理
 * 
 * @author yuzhantao
 *
 */
public class OneKeyInventoryHandle implements IMessageHandle<RequestIMessageOfITOM, Object> {
	protected final static Logger logger = LogManager.getLogger(OneKeyInventoryHandle.class.getName());
	protected final static String ACTION_CODE = "reader004";
	protected final static String SEND_TOPIC = "itom";
	protected UHistoryRepository uHistoryRepository;
	protected QueueSender queueSender;
	protected final static long U_TIMEOUT = 1000 * 60 * 10; // u位有效期为10分钟

	public OneKeyInventoryHandle(UHistoryRepository uh, QueueSender qs) {
		this.uHistoryRepository = uh;
		this.queueSender = qs;
	}

	@Override
	public boolean isHandle(RequestIMessageOfITOM t) {
		if (t.getActioncode().equals(ACTION_CODE)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object handle(ChannelHandlerContext ctx, RequestIMessageOfITOM t) {
		RequestOneKeyInventoryMessage msg = ((JSONObject) t.getPostdata())
				.toJavaObject(RequestOneKeyInventoryMessage.class);
		for (String deviceCode : msg.getDevAddrCodes()) {
			try {
				Optional<UHistory> opt = uHistoryRepository.findById(deviceCode);
				if (opt.isPresent() == false || opt.get().getDeviceCode().equals(deviceCode) == false)
					continue;
				List<UInfoVo> uList = JSON.parseArray(opt.get().getJson(), UInfoVo.class);
				// 如果之前的信息超时，则认为是空数据
				if (System.currentTimeMillis() - opt.get().getUpdateTime().getTime() > U_TIMEOUT) {
					uList = new ArrayList<>();
				}

				TagPojo tp = this.getPushData(deviceCode, uList);
				String json = JSON.toJSONString(tp);
				queueSender.send(SEND_TOPIC, json);
				logger.info("发送开关门盘点信息:" + json);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return null;
	}

	/**
	 * 获取推送的数据
	 * 
	 * @param deviceCode
	 * @param uListVo
	 * @return
	 */
	private TagPojo getPushData(String deviceCode, List<UInfoVo> uList) {
		UInfoArrayVo uListVo = new UInfoArrayVo();
		uListVo.setDevAddrCode(deviceCode);
		uListVo.setUdevInfo(uList);

		UAlarmSubmitVo uas = new UAlarmSubmitVo();
		uas.setDevAddrList(new ArrayList<UInfoArrayVo>());
		uas.getDevAddrList().add(uListVo);
		uas.setDevAddrCode(deviceCode);
		TagPojo tagPojo = new TagPojo();
		tagPojo.setAwsPostdata(JSON.toJSONString(uas));
		tagPojo.setActioncode("readerSingleInve");
		tagPojo.setGUID(java.util.UUID.randomUUID().toString());
		tagPojo.setRfidtype("RU1000");
		return tagPojo;
	}
}
