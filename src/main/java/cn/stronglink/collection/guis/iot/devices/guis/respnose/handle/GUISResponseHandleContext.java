package cn.stronglink.collection.guis.iot.devices.guis.respnose.handle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.stronglink.collection.guis.core.message.MessageHandleContext;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessage;
import cn.stronglink.collection.guis.iot.mq.producer.AliTopicSender;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GUISResponseHandleContext extends SimpleChannelInboundHandler<GUISMessage> {
	private MessageHandleContext<GUISMessage, Object> messageHandleContent;
	private static Logger logger = LogManager.getLogger(GUISResponseHandleContext.class.getName());
	public static final ChannelGroup channels = new DefaultChannelGroup("GUISChannelGroup", GlobalEventExecutor.INSTANCE);
	private AliTopicSender topicSender = new AliTopicSender();

	public GUISResponseHandleContext() {
		super();
		this.messageHandleContent = new MessageHandleContext<>();
		this.messageHandleContent.addHandleClass(new GUISTimeUploadUScanLaterHandle(topicSender)); // 处理下位机定时上传U位扫描后的信息的类
//		this.messageHandleContent
//				.addHandleClass(new GUISUScanChangeUploadULocationMegHandle(topicSender, uHistoryRepository)); // 处理下位机在U位信息有变化时主动上传U位扫描信息的类
		this.messageHandleContent.addHandleClass(new GUISSendHeartPackage(topicSender)); // 下位机发送心跳包
		this.messageHandleContent.addHandleClass(new GUISStartReadCardHandle()); // 解析"上位机启动读卡"
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("GUIS设备已连接" + ctx.channel().remoteAddress().toString());
		channels.add(ctx.channel());
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("GUIS设备已断开" + ctx.channel().remoteAddress().toString());
		channels.remove(ctx.channel());
		this.messageHandleContent.clearHandleClass();
		super.channelInactive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GUISMessage msg) throws Exception {
		if (msg == null)
			return;
		this.messageHandleContent.handle(ctx, msg);
	}
}
