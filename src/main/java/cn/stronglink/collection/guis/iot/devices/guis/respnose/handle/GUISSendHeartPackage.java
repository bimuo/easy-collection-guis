package cn.stronglink.collection.guis.iot.devices.guis.respnose.handle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.stronglink.collection.guis.core.message.IMessageHandle;
import cn.stronglink.collection.guis.core.util.ByteUtil;
import cn.stronglink.collection.guis.core.util.ContextUtils;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessage;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.common.CommonSendToCustomer;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.common.GuisCommonEntity;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.entity.UInfoEntity;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.message.GUISMQMessage;
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
	private AliTopicSender topicSender = (AliTopicSender) ContextUtils.getBean("topicSender");
	private String hostNumber = null;
//	private InetSocketAddress insocket = null;
	private GUISMQMessage mes = null;
	private UInfoEntity u = null;
	private static byte ACTION_CODE = Integer.valueOf("82", 16).byteValue();
	private static final String TAG_NULL = "00000000";

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
			// df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			// RedirectOutputStream.put(df.format(new Date())+" 收到主机 "+hostNumber+" 定时心跳包 报文
			// "+ByteUtil.byteArrToHexString(t.getData()));
			// 暂时注释心跳包数据，老唐的配置工具可能会受影响
			pushToMq(hostNumber); // 推送心跳包数据
			// 存储HID和机柜IP的对应关系

			GuisCommonEntity.getInstance().getIpByHid.put(hostNumber, ctx.channel().id());
			// logger.info("&&&添加channelId缓存 {}",hostNumber);
			// logger.info("&&&==========================================");
			// for(Map.Entry<String, ChannelId> item :
			// GuisCommonEntity.getInstance().getIpByHid.entrySet()) {
			// Channel channel = GUISResponseHandleContext.channels.find(item.getValue());
			// InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
			// logger.info("&&&hostname:{}
			// address:{}",item.getKey(),insocket.getAddress().getHostAddress());
			// }
			// logger.info("&&&==========================================");
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			hostNumber = null;
		}
		return null;
	}

	/**
	 * 推送心跳包数据
	 * 
	 * @param hostNumber
	 */
	private void pushToMq(String hostNumber) {
		try {
			mes = new GUISMQMessage();
			mes.setActioncode("reader030");
			u = new UInfoEntity();
			u.setDevAddrCode(hostNumber);
			mes.setAwsPostdata(u);

			// String json = JSON.toJSONString(mes);
			logger.debug(JSON.toJSONString(mes));
			topicSender.send("daioReader", JSON.toJSONString(mes));
		} finally {
			mes = null;
			u = null;
		}
	}

	// public static void main(String args[]) {
	// GUISMQMessage mes = new GUISMQMessage();
	// mes.setActioncode("reader030");
	// UInfoEntity u = new UInfoEntity();
	// u.setDevAddrCode("000000");
	// mes.setAwsPostdata(u);
	//
	// String json = JSON.toJSONString(mes);
	// }
}
