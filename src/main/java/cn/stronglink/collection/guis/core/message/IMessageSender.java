package cn.stronglink.collection.guis.core.message;
/**
 * 消息发
 * @author yuzhantao
 *
 */
public interface IMessageSender<T> {
	void sendMessage(T datas);
}
