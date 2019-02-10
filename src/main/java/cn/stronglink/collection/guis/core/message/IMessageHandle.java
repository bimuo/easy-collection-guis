package cn.stronglink.collection.guis.core.message;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理接口
 * @author yuzhantao
 *
 * @param <T>
 */
public interface IMessageHandle<T,V> {
	/**
	 * 是否处理消息
	 * @param t
	 * @return
	 */
	boolean isHandle(T t);
	/**
	 * 处理消息
	 * @param t
	 */
	V handle(ChannelHandlerContext ctx, T t);
}
