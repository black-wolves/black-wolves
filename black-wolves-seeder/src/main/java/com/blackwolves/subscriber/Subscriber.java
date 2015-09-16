/**
 * 
 */
package com.blackwolves.subscriber;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.util.CsvReader;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Subscriber {
	
	private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		SubscriberRunnable sr = context.getBean(SubscriberRunnable.class);
		CsvReader csvReader = new CsvReader();
		ArrayList<Seed> seeds = csvReader.getSeedsFromFile();
		for (Seed seed : seeds) {
			sr.runProcess(seed);
		}
		logger.info("Subscriber process finished");
		return;
	}
	
}
