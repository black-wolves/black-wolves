package com.blackwolves.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author gastondapice
 *
 */
@Component
public class Main {

	
private static ApplicationContext context;
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:com/blackwolves/persistence/application-context-black-wolves-persistence.xml");
	}
}
