package cn.stronglink.collection.guis.core.message;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消息转换处理
 * @author yuzhantao
 *
 * @param <T>
 * @param <V>
 */
public abstract class MessageRelayHandle<T,V> implements IMessageHandle<T,V> {
	private IMessageFactory<T,V> messageFactory;
	private IMessageSender<V> messageSender;
	
	@Override
	public V handle(ChannelHandlerContext ctx, T t) {
		V result = messageFactory.parse(t);
		messageSender.sendMessage(result);
		return result;
	}
}
