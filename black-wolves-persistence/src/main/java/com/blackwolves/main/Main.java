package com.blackwolves.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gastondapice
 *
 */
public class Main {

	
private static ApplicationContext context;
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:application-context-black-wolves-persistence.xml");
	}
}
