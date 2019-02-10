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
import cn.stronglink.collection.guis.iot.devices.guis.respnose.entity.UInfoVo;
import cn.stronglink.collection.guis.iot.moudle.UHistory;
import cn.stronglink.collection.guis.iot.moudle.UHistoryRepository;
import cn.stronglink.collection.guis.iot.mq.producer.AliTopicSender;
import io.netty.channel.ChannelHandlerContext;

/**
 * 下位机在U位信息有变化时主动上传U位扫描信息
 * 
 * @author 25969
 *
 */
public class GUISUScanChangeUploadULocationMegHandle implements IMessageHandle<GUISMessage, Object> {
	protected static Logger logger = LogManager.getLogger(GUISUScanChangeUploadULocationMegHandle.class.getName());
	protected final static String EMPTY_TAG_ID = "00000000"; // 空标签ID
	protected final static String EXCEPTION_TAG_ID = "FFFFFFFF"; // 异常标签ID
	protected AliTopicSender topicSender;
	protected static byte ACTION_CODE = Integer.valueOf("81", 16).byteValue();
	protected  UHistoryRepository uHistoryRepository;
	// 最大标签数量
	protected final static int MAX_TAG_COUNT = 43;

	public GUISUScanChangeUploadULocationMegHandle(AliTopicSender topicSender, UHistoryRepository uHistoryRepository) {
		this.topicSender = topicSender;
		this.uHistoryRepository = uHistoryRepository;
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
//			System.out.println("盘点数据16进制完整报文=" + ByteUtil.byteArrToHexString(t.getData()));
			// TODO 因硬件原因，软件端需要屏蔽超过最大U位数量的数据
			if ((t.getData().length / 5) > MAX_TAG_COUNT) {
				logger.error("获取的标签数量超过{}个", MAX_TAG_COUNT);
				return null;
			}

			if ((t.getData().length) % 5 == 0) { // 因“N个标签数据，每个标签=1byte（U位）+4 byte（UID）” ，此处判断
													// 数据的长度是不是5的倍数，如果不是暂时认为时客户端传输有误
				int iCurIdx = 0; // 指针

				List<UInfoVo> uList = new ArrayList<>();
				long dataLen = t.getData().length;
				while (iCurIdx < dataLen) {
					String uPosition = ByteUtil.byteArrToHexString(t.getData(), iCurIdx, 1); // U位
					String labCode = ByteUtil.byteArrToHexString(t.getData(), (iCurIdx + 1), 4).toUpperCase(); // 标签编号
					iCurIdx += 5;
					if(EXCEPTION_TAG_ID.equals(labCode)) {  // 如果标签有FFFFFFFF的，认为扫描杆程序异常，将不处理，并且不返回信息。
						logger.info("有标签编号为[{}],因此本次标签数据不处理，源数据:{}",EXCEPTION_TAG_ID,JSON.toJSONString(t));
						return null;
					} else if (!EMPTY_TAG_ID.equals(labCode)) {
						UInfoVo uInfo = new UInfoVo();
						uInfo.setU(uPosition);
						uInfo.setRfid(labCode);
						uList.add(uInfo);
					}
				}
				// 应答硬件
				CommonSendToCustomer.commonSendMessageToCustomer(ctx, t, (byte) 0, 1); // 上位机回复信息

				// 保存盘点的数据到数据库
				String deviceCode = ByteUtil.byteArrToHexString(t.getHostNumber()); // 主机编号
				String json = JSON.toJSONString(uList);

				UHistory uHistory = new UHistory();
				uHistory.setDeviceCode(deviceCode);
				uHistory.setJson(json);
				uHistoryRepository.deleteById(deviceCode);
				uHistoryRepository.save(uHistory);
			} else {
				logger.info("GUIS 9号指令返回的正文不是5的倍数！"); // 根据协议，此处应该给下位机返回接受失败的应答
				CommonSendToCustomer.commonSendMessageToCustomer(ctx, t, (byte) 1, 1); // 上位机回复信息
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
		return null;
	}
}
