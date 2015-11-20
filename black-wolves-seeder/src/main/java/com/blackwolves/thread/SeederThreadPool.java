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
import com.blackwolves.seeder.YahooRunnable;

/**
 * @author gastondapice
 *
 */
public class SeederThreadPool {
	
	private static Logger logger = LoggerFactory.getLogger(SeederThreadPool.class);
	

	public static void main(String[] args) {
		ExecutorService executor = null;
		if("multiple".equals(args[0])){
			logger.info("Starting SeederThreadPool");
			int seedsToProcess = Integer.valueOf(args[1]);
			executor = Executors.newFixedThreadPool(seedsToProcess);
			processSeeds(args, executor, seedsToProcess);
		}else if("one".equals(args[0])){
			executor = Executors.newFixedThreadPool(1);
			processSeed(args, executor);
		}
		
//		UNCOMMENT THIS FOR TEST ONLY AND COMMENT THE ONE ABOVE
//		__________________________________________testIterateSeeds(args, executor);
		
		if(executor!=null){
			executor.shutdown();
	        
	        while (!executor.isTerminated()) {
	        	
	        }
	        logger.info("Finished all threads");
		}
	}
	
	/**
	 * @param args
	 * @param executor
	 * @param seedsToProcess 
	 */
	private static void processSeeds(String[] args, ExecutorService executor, int seedsToProcess) {
		List<String[]> seeds = YahooRunnable.generateSeedsList();
		for (int i = 1; i <= seedsToProcess; i++) {
			String[] seed = seeds.get(YahooRunnable.randInt(0, seeds.size()-1));
			MDC.put("logFileName", seed[0]);
			Seeder seeder = new Seeder(seed, logger, args[2]);
			Runnable worker = seeder;
			logger.info("Executing thread: " + i + " with seed: " + seed[0] + " " + seed[1]);
			executor.execute(worker);
		}
	}
	
	/**
	 * @param args
	 * @param executor
	 */
	private static void processSeed(String[] args, ExecutorService executor) {
		String[] seed = args[1].split(",");
		MDC.put("logFileName", seed[0]);
		Seeder seeder = new Seeder(seed, logger, args[2]);
		Runnable worker = seeder;
		logger.info("Executing thread: with seed: " + seed[0] + " " + seed[1]);
		executor.execute(worker);
	}

	/**
	 * 
	 * @param args
	 * @param executor
	 */
	private static void __________________________________________testIterateSeeds(String[] args, ExecutorService executor) {
    	String[] seed = args[0].split(",");
    	MDC.put("logFileName", seed[0]);
        Seeder seeder = new Seeder(seed, logger,"InboxAndSpam");
        Runnable worker = seeder;
        logger.info("Executing thread: with seed: " + seed[0] + " " +seed[1]);
        executor.execute(worker);
	}
}
