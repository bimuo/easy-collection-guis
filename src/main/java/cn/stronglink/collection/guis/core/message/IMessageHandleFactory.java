package cn.stronglink.collection.guis.core.message;

import io.netty.channel.ChannelHandler;
/**
 * 消息工厂
 * @author yuzhantao
 *
 */
public interface IMessageHandleFactory {
	/**
	 * 创建消息解码
	 * @return
	 */
	ChannelHandler createMessageDecoder();
	
	/**
	 * 创建消息处理
	 * @return
	 */
	ChannelHandler createMessageHandle();
}
