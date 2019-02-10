package cn.stronglink.collection.guis.iot.mq.producer;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component("queueSender")
public class QueueSender {
	@Autowired
    private JmsMessagingTemplate jmsTemplate;
	
	public void send(String topic,String msg){
		Destination destination = new ActiveMQQueue(topic);
        jmsTemplate.convertAndSend(destination, msg);
    }
}
