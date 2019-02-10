package cn.stronglink.collection.guis.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;


public class ContextUtils{

	private static ApplicationContext applicationContext;

	protected static Logger logger = LogManager.getLogger(ContextUtils.class);

	public static void setApplicationContext(ApplicationContext applicationContext) {
		synchronized (ContextUtils.class) {
			logger.debug("setApplicationContext, notifyAll");
			ContextUtils.applicationContext = applicationContext;
			ContextUtils.class.notifyAll();
		}
	}

	public static ApplicationContext getApplicationContext() {
		synchronized (ContextUtils.class) {
			while (applicationContext == null) {
				try {
					logger.debug("getApplicationContext, wait...");
					ContextUtils.class.wait(60000);
					if (applicationContext == null) {
						logger.warn("Have been waiting for ApplicationContext to be set for 1 minute", new Exception());
					}
				} catch (InterruptedException ex) {
					logger.debug("getApplicationContext, wait interrupted");
				}
			}
			return applicationContext;
		}
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public static <T> Object getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		 ContextUtils.applicationContext = applicationContext;
//	}
//
//	 /**
//     * 获取applicationContext
//     * @return
//     */
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//
//    
//    /**
//     * 通过name获取 Bean.
//     * @param name
//     * @return
//     */
//    public static Object getBean(String name){
//        return getApplicationContext().getBean(name);
//    }
//
//    /**
//     * 通过class获取Bean.
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public <T> T getBean(Class<T> clazz){
//        return getApplicationContext().getBean(clazz);
//    }
//
//    /**
//     * 通过name,以及Clazz返回指定的Bean
//     * @param name
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public <T> T getBean(String name,Class<T> clazz){
//        return getApplicationContext().getBean(name, clazz);
//    }
}
