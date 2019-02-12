package cn.stronglink.collection.guis.iot.devices.guis.respnose.handle;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.stronglink.collection.guis.core.message.IMessageHandle;
import cn.stronglink.collection.guis.core.util.ByteUtil;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessage;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.common.CommonSendToCustomer;
import cn.stronglink.collection.guis.iot.mq.producer.AliTopicSender;
import cn.stronglink.collection.guis.iot.vo.TagVo;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理下位机定时上传U位扫描后的信息的类
 * 
 * @author 25969
 *
 */
public class GUISTimeUploadUScanLaterHandle implements IMessageHandle<GUISMessage, Object> {
	private static Logger logger = LogManager.getLogger(GUISTimeUploadUScanLaterHandle.class.getName());
	private AliTopicSender topicSender;
	private static byte ACTION_CODE = Integer.valueOf("80", 16).byteValue();
	private static final String TAG_NULL = "00000000";
	protected final static String EXCEPTION_TAG_ID = "FFFFFFFF"; // 异常标签ID

	// 最大标签数量
	private final static int MAX_TAG_COUNT = 54;

	public GUISTimeUploadUScanLaterHandle(AliTopicSender topicSender) {
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
			// TODO 因硬件原因，软件端需要屏蔽超过最大U位数量的数据
			if ((t.getData().length / 5) > MAX_TAG_COUNT) {
				logger.error("获取的标签数量超过{}个", MAX_TAG_COUNT);
				return null;
			}

			if ((t.getData().length) % 5 == 0) { // 因“N个标签数据，每个标签=1byte（U位）+4 byte（UID）” ，此处判断
													// 数据的长度是不是5的倍数，如果不是暂时认为时客户端传输有误
				int iCurIdx = 0; // 指针
				List<TagVo> tags = new ArrayList<>();
				long dataLen = t.getData().length;
				// 遍历标签
				while (iCurIdx < dataLen) {
					String uPosition = String.valueOf(t.getData()[iCurIdx]); // U位
					String labCode = ByteUtil.byteArrToHexString(t.getData(), (iCurIdx + 1), 4).toUpperCase(); // 标签编号
					iCurIdx += 5;
					if (EXCEPTION_TAG_ID.equals(labCode)) { // 如果标签有FFFFFFFF的，认为扫描杆程序异常，将不处理，并且不返回信息。
						logger.info("有标签编号为[{}],因此本次标签数据不处理，源数据:{}", EXCEPTION_TAG_ID, JSON.toJSONString(t));
						return null;
					} else if (!TAG_NULL.equals(labCode)) {
						TagVo tag = new TagVo();
						tag.setTag(labCode);
						tag.setU(Integer.valueOf(uPosition));
						tags.add(tag);
					}
				}
				CommonSendToCustomer.commonSendMessageToCustomer(ctx, t, (byte) 0, 1); // 上位机回复信息
				// 保存盘点的数据到数据库
				String deviceCode = ByteUtil.byteArrToHexString(t.getHostNumber()); // 主机编号
				topicSender.devPropertyPush(deviceCode, tags);
				logger.info("获取到定时扫描U位信息，并发送到阿里IOT平台  deviceCode:{}   tags={}", deviceCode, JSON.toJSONString(tags));
			} else {
				logger.info("GUIS 12号指令返回的正文不是5的倍数！"); // 根据协议，此处应该给下位机返回接受失败的应答
				CommonSendToCustomer.commonSendMessageToCustomer(ctx, t, (byte) 1, 1); // 上位机回复信息
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
