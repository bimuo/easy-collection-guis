package cn.stronglink.collection.guis.iot.devices.guis.request.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;

import cn.stronglink.collection.guis.core.message.MessageHandleContext;
import cn.stronglink.collection.guis.iot.devices.guis.message.MQMessageOfGUIS;

@Component
public class MQMessageOfGUISListener  extends MessageListenerAdapter{
	private static Logger logger = LogManager.getLogger(MQMessageOfGUISListener.class.getName());
	// 通过策略模式处理不同的信息
	private MessageHandleContext<MQMessageOfGUIS,Object> messageHandleContent;
//	@Autowired
//	private TopicSender topicSender;
	
	public MQMessageOfGUISListener(){
		super();
		this.messageHandleContent=new MessageHandleContext<>();
//		this.messageHandleContent.addHandleClass(new InventoryHandler(topicSender,uHistoryRepository));
	}
	
	@JmsListener(destination="readInfo",concurrency="1")
    public void onMessage(Message message, Session session) throws JMSException {
		if(message instanceof TextMessage){
			TextMessage tm = (TextMessage)message;
			String json = tm.getText();
			MQMessageOfGUIS rm = JSON.parseObject(json,new TypeToken<MQMessageOfGUIS>(){}.getType());
			this.messageHandleContent.handle(null,rm);
		}else{
			logger.info("无法解析的mq对象消息:"+message.getClass().getName());
		}
    }
}
