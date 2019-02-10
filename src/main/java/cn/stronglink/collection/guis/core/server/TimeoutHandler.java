package cn.stronglink.collection.guis.core.server;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
/**
 * 对socket超时的处
 * @author yuzhantao
 *
 */
public class TimeoutHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LogManager.getLogger(TimeoutHandler.class.getName());
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
//			IdleStateEvent event = (IdleStateEvent) evt;
			// 当读数据超时时，关闭通道
//			if (event.state() == IdleState.READER_IDLE) {
//				
//			}
			printIpInfo(ctx);
			ctx.channel().close();
		}else {
			super.userEventTriggered(ctx, evt);
		}
	}
	
	/**
	 * 打印ip信息
	 * @param ctx
	 */
	private static void printIpInfo(ChannelHandlerContext ctx) {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
		logger.info("客户端["+insocket.getAddress().getHostAddress()+":"+insocket.getPort()+"]连接超时，主动将其关闭?");
	}
}
