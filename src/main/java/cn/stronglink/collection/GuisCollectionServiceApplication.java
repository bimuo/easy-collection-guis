package cn.stronglink.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.stronglink.collection.guis.core.CollectionServer;
import cn.stronglink.collection.guis.iot.devices.guis.message.GUISMessageHandleFactory;

@SpringBootApplication
public class GuisCollectionServiceApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(GuisCollectionServiceApplication.class);
		springApplication.addListeners(beanGuisServer());
		springApplication.run(args);
	}
	
	/**
	 * 实例化GUIS采集 
	 * @return
	 */
	private static CollectionServer beanGuisServer() {
		CollectionServer guisServer = new CollectionServer();
		guisServer.setServerName("GUIS");
		guisServer.setServerPort(16868);
		guisServer.setReadTimeout(100);
		guisServer.setMessageHandleFactory(new GUISMessageHandleFactory());
		return guisServer;
	}

}

