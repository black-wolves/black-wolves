/**
 * 
 */
package com.blackwolves.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.blackwolves.seeder.Seeder;

/**
 * @author gastondapice
 *
 */
public class SeederThreadPool {
	
	private static Logger logger = LoggerFactory.getLogger(SeederThreadPool.class);
	
	private static final int SEEDS_TO_PROCESS = 1;

	public static void main(String[] args) {
		logger.info("Starting SeederThreadPool");
		ExecutorService executor = Executors.newFixedThreadPool(SEEDS_TO_PROCESS);
	//	List seeds = Seeder.generateList("/var/www/", "seeds.csv");
		String [] seed =  new String [2];
		seed = args[1].split(",");
		iterateSeeds(seed, executor);
		
//		UNCOMMENT THIS FOR TEST ONLY AND COMMENT THE ONE ABOVE
//		__________________________________________testIterateSeeds(args, executor);
		
        executor.shutdown();
        
        while (!executor.isTerminated()) {
        	
        }
        logger.info("Finished all threads");
	}
	
	/**
	 * @param seeds
	 * @param executor
	 */
	private static void iterateSeeds(String[] seed, ExecutorService executor) {
		for (int i = 0; i < SEEDS_TO_PROCESS; i++) {
	//		String[] seed = (String [])seeds.get(i);
			
			MDC.put("logFileName", seed[0]);
     		Seeder seeder = new Seeder(seed, logger);
			Runnable worker = seeder;
			logger.info("Executing thread: " + i + " with seed: " + seed[0] + " " + seed[1]);
			executor.execute(worker);
		}
	}

	/**
	 * 
	 * @param args
	 * @param executor
	 */
	private static void __________________________________________testIterateSeeds(String[] args, ExecutorService executor) {
    	String[] seed = args[0].split(",");
    	MDC.put("logFileName", seed[0]);
        Seeder seeder = new Seeder(seed, logger);
        Runnable worker = seeder;
        logger.info("Executing thread: with seed: " + seed[0] + " " +seed[1]);
        executor.execute(worker);
	}
}
