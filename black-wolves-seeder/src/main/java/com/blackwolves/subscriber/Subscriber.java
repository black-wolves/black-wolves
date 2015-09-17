/**
 * 
 */
package com.blackwolves.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gaston.dapice
 *
 */
//@Component
public class Subscriber {
	
	private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

//	private static ApplicationContext context;
	
	public static void main(String[] args) {
//		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
//		SubscriberRunnable sr = context.getBean(SubscriberRunnable.class);
		//sr.runProcess(args[0]);
		logger.info("Subscriber process finished");
		return;
	}
	
}
