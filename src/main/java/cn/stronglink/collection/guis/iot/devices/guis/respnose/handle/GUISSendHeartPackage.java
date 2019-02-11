package cn.stronglink.collection.guis.iot.devices.guis.respnose.handle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.stronglink.collection.guis.core.message.IMessageHandle;
import cn.stronglink.collection.guis.core.util.ByteUtil;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessage;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.common.CommonSendToCustomer;
import cn.stronglink.collection.guis.iot.mq.producer.AliTopicSender;
import io.netty.channel.ChannelHandlerContext;

/**
 * 下位机发送心跳包
 * 
 * @author 25969
 *
 */
public class GUISSendHeartPackage implements IMessageHandle<GUISMessage, Object> {
	private static Logger logger = LogManager.getLogger(GUISSendHeartPackage.class.getName());
	private AliTopicSender topicSender;
	private String hostNumber = null;
	private static byte ACTION_CODE = Integer.valueOf("82", 16).byteValue();
	private static final String TAG_NULL = "00000000";

	public GUISSendHeartPackage(AliTopicSender topicSender) {
		this.topicSender = topicSender;
	}

	@Override
	public boolean isHandle(GUISMessage t) {
		if (ACTION_CODE == t.getCommand()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object handle(ChannelHandlerContext ctx, GUISMessage t) {
		try {
			hostNumber = ByteUtil.byteArrToHexString(t.getHostNumber()); // 主机编号
			// logger.info("收到(14号指令)主机 " + hostNumber + " 定时心跳包");
			if (!TAG_NULL.equals(hostNumber)) {
				CommonSendToCustomer.commonSendMessageToCustomer(ctx, t, (byte) 0, 1); // 上位机回复信息
			}
			// 如果发送信息的类没有初始化，就初始化一下
			if(this.topicSender.isInit()==false) {
				this.topicSender.init(hostNumber);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			hostNumber = null;
		}
		return null;
	}
}
