/**
 * 
 */
package com.blackwolves.thread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blackwolves.seeder.Seeder;

/**
 * @author gastondapice
 *
 */
public class SeederThreadPool {
	
	private static final Logger logger = LogManager.getLogger(SeederThreadPool.class.getName());
	
	private static final int SEEDS_TO_PROCESS = 4;
	private static ApplicationContext context;

	public static void main(String[] args) {
		logger.info("Starting SeederThreadPool");
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		ExecutorService executor = Executors.newFixedThreadPool(SEEDS_TO_PROCESS);
		
		for (int i = 1; i <= SEEDS_TO_PROCESS; i++) {
        	String[] seed = args[i].split(",");
        	Seeder seeder = new Seeder(seed);
            Runnable worker = seeder;
            logger.removeAllAppenders();
            try {
				logger.addAppender(new FileAppender(new SimpleLayout(), seed[0] + ".log", true));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
            logger.info("Executing thread: " + i + "with seed :" +seed[0] + " "+seed[1]);
            executor.execute(worker);
          }
		
        executor.shutdown();
        
        while (!executor.isTerminated()) {
        	
        }
        System.out.println("Finished all threads");
	}
}
