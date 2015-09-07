/**
 * 
 */
package com.blackwolves.seeder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Suscriber {

	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		SuscriberRunnable suscriberRunnable =    context.getBean(SuscriberRunnable.class);
		suscriberRunnable.runProcess(args[0]);
	}
	
}
