package cn.stronglink.collection.guis.business;

import javax.jms.ConnectionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;

import cn.stronglink.collection.guis.business.handle.OneKeyInventoryHandle;
import cn.stronglink.collection.guis.business.handle.message.RequestIMessageOfITOM;
import cn.stronglink.collection.guis.core.message.MessageHandleContext;
import cn.stronglink.collection.guis.iot.moudle.UHistoryRepository;
import cn.stronglink.collection.guis.iot.mq.producer.QueueSender;

@Service
public class BusinessListener {
	private static Logger logger = LogManager.getLogger(BusinessListener.class.getName());
	// 通过策略模式处理不同的信息
	private MessageHandleContext<RequestIMessageOfITOM, Object> messageHandleContent;

	@Autowired
	public BusinessListener(UHistoryRepository uHistoryRepository, QueueSender queueSender) {
		this.messageHandleContent = new MessageHandleContext<RequestIMessageOfITOM, Object>();
		this.messageHandleContent.addHandleClass(new OneKeyInventoryHandle(uHistoryRepository, queueSender));
	}

	@JmsListener(destination = "readerInfo", containerFactory = "jmsListenerServiceToITOMTopic")
	public void onMessage(String json) {
		try {
			logger.info("MQ主题[readerInfo]接收到json数据:" + json);
			RequestIMessageOfITOM rm = JSON.parseObject(json, new TypeToken<RequestIMessageOfITOM>() {
			}.getType());
			this.messageHandleContent.handle(null, rm);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerServiceToITOMTopic(ConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setPubSubDomain(true);
		factory.setConnectionFactory(activeMQConnectionFactory);
		// 设置连接数
		factory.setConcurrency("1");
		// 重连间隔时间
		factory.setRecoveryInterval(1000L);
		return factory;
	}
}
