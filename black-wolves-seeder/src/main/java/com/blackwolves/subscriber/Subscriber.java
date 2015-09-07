/**
 * 
 */
package com.blackwolves.subscriber;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Subscriber {
	
	private static final Logger logger = LogManager.getLogger(Subscriber.class.getName());

	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		SubscriberRunnable suscriberRunnable =    context.getBean(SubscriberRunnable.class);
		suscriberRunnable.runProcess(args[0]);
		logger.info("Subscriber process finished");
		return;
	}
	
}
