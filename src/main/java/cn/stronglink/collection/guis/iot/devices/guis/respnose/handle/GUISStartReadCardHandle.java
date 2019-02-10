package cn.stronglink.collection.guis.iot.devices.guis.respnose.handle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.stronglink.collection.guis.core.message.IMessageHandle;
import cn.stronglink.collection.guis.core.util.ByteUtil;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessage;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.common.RedirectOutputStream;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.entity.UInfoEntity;
import io.netty.channel.ChannelHandlerContext;

/**
 * 解析"上位机启动读卡" 回复报文
 * 
 * @author 25969
 *
 */
public class GUISStartReadCardHandle implements IMessageHandle<GUISMessage, Object> {
	private static Logger logger = LogManager.getLogger(GUISStartReadCardHandle.class.getName());
	private static SimpleDateFormat df = null;

	@Override
	public boolean isHandle(GUISMessage t) {
		if (Integer.valueOf("24", 16).byteValue() == t.getCommand()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object handle(ChannelHandlerContext ctx, GUISMessage t) {

		if ((t.getData().length) % 5 == 0) { // 因“N个标签数据，每个标签=1byte（U位）+4 byte（UID）” ，此处判断 数据的长度是不是5的倍数，如果不是暂时认为时客户端传输有误
			String hostNumber = ByteUtil.byteArrToHexString(t.getHostNumber()); // 主机编号
			int iCurIdx = 0; // 指针
			StringBuilder myStringBuilder = new StringBuilder("收到11号指令（上位机启动读卡）：主机编号--" + hostNumber + " 标签U位集合--");

			UInfoEntity devs = new UInfoEntity();
			List<UInfoEntity.UdevInfo> list = new ArrayList<>();
			UInfoEntity.UdevInfo u = null;

			while (iCurIdx < t.getData().length) {
				u = devs.new UdevInfo();
				String uPosition = ByteUtil.byteArrToHexString(t.getData(), iCurIdx, 1); // U位
				String labCode = ByteUtil.byteArrToHexString(t.getData(), (iCurIdx + 1), 4); // 标签编号
				iCurIdx += 5;
				myStringBuilder.append("U位:" + uPosition + "U、");
				myStringBuilder.append("标签编号:" + labCode + ";");
				u.setRfid(labCode);
				u.setU(ByteUtil.hexStringToInt(uPosition));
				list.add(u);
			}
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			logger.info(myStringBuilder.toString()); // 打印收到的数据
			RedirectOutputStream.put(df.format(new Date()) + "收到启动读卡返回报文  " + ByteUtil.byteArrToHexString(t.getData()));
		} else {
			logger.info("GUIS 12号指令返回的正文不是5的倍数！"); // 根据协议，此处应该给下位机返回接受失败的应答
		}

		return null;

	}

}
