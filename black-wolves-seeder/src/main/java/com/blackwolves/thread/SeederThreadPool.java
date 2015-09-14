/**
 * 
 */
package com.blackwolves.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blackwolves.seeder.Seeder;
import com.blackwolves.seeder.YahooRunnable;

/**
 * @author gastondapice
 *
 */
public class SeederThreadPool {
	
	private static final Logger logger = LogManager.getLogger(SeederThreadPool.class.getName());
	
	private static final int SEEDS_TO_PROCESS = 3;
	private static ApplicationContext context;

	public static void main(String[] args) {
		logger.info("Starting SeederThreadPool");
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		
		ExecutorService executor = Executors.newFixedThreadPool(SEEDS_TO_PROCESS);
		
		List<String[]> seeds = YahooRunnable.generateSeedsList();
        
		for (int i = 0; i < SEEDS_TO_PROCESS; i++) {
        	String[] seed = seeds.get(YahooRunnable.randInt(0, seeds.size()-1));
        	Seeder seeder = new Seeder(seed);
            Runnable worker = seeder;
            logger.info("Executing thread: " + i);
            executor.execute(worker);
          }
		
        executor.shutdown();
        
        while (!executor.isTerminated()) {
        	
        }
        System.out.println("Finished all threads");
	}
}
