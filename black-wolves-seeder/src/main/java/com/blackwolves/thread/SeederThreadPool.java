/**
 * 
 */
package com.blackwolves.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blackwolves.seeder.Seeder;

/**
 * @author gastondapice
 *
 */
public class SeederThreadPool {
	
	static Logger logger = LoggerFactory.getLogger(SeederThreadPool.class);
	
	private static final int SEEDS_TO_PROCESS = 4;
	private static ApplicationContext context;

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(SeederThreadPool.class);
		logger.info("Starting SeederThreadPool");
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		ExecutorService executor = Executors.newFixedThreadPool(SEEDS_TO_PROCESS);
		
		for (int i = 1; i <= SEEDS_TO_PROCESS; i++) {
			
        	String[] seed = args[i].split(",");
        	MDC.put("userid", seed[0]);
        	MDC.put("logFileName", seed[0]);
            Seeder seeder = new Seeder(seed, logger);
            Runnable worker = seeder;
            logger.info("Executing thread: " + i + " with seed: " + seed[0] + " " +seed[1]);
            executor.execute(worker);
          }
		
        executor.shutdown();
        
        while (!executor.isTerminated()) {
        	
        }
        System.out.println("Finished all threads");
	}
}
