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

import com.blackwolves.persistence.util.Constant;
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
		if(Constant.MULTIPLE.equals(args[0])){
			logger.info("Starting multiple SeederThreadPool");
			List<String[]> seeds = YahooRunnable.generateSeedsList("seeds.csv");
			
			int seedsToProcess = Integer.valueOf(args[1]);
			executor = Executors.newFixedThreadPool(seedsToProcess);
			
			processSeeds(args, executor, seedsToProcess, seeds, Constant.MULTIPLE);
		}else if(Constant.SPECIFIC.equals(args[0])){
			logger.info("Starting specific SeederThreadPool");
			List<String[]> seeds = YahooRunnable.generateSeedsList("specific.csv");
			
			int seedsToProcess = seeds.size();
			executor = Executors.newFixedThreadPool(seedsToProcess);
			
			processSeeds(args, executor, seedsToProcess, seeds, Constant.SPECIFIC);
		}else if(Constant.ONE.equals(args[0])){
			logger.info("Starting one SeederThreadPool");
			executor = Executors.newFixedThreadPool(1);
			processSeed(args, executor, Constant.ONE);
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
	private static void processSeeds(String[] args, ExecutorService executor, int seedsToProcess, List<String[]> seeds, String type) {

		for (int i = 1; i <= seeds.size() -1 ; i++) {
			int seedNumber = YahooRunnable.randInt(0, seeds.size()-1);
			String[] seed = seeds.get(i);
			List<String> usedSeeds = YahooRunnable.readSeedsFromFile();
			if(!usedSeeds.contains(seed[0])){
				MDC.put("logFileName", seed[0]);
				Seeder seeder = new Seeder(seed, logger, args[2], type);
				Runnable worker = seeder;
				YahooRunnable.writeSeedToFile(seed[0]);
				logger.info("Executing thread: " + i + " with seed: " + seed[0] + " " + seed[1]);
				executor.execute(worker);
			}else{
				logger.info("Seeder is already in the used file");
			}
			seeds.remove(seedNumber);
		}
	}
	
	/**
	 * @param args
	 * @param executor
	 */
	private static void processSeed(String[] args, ExecutorService executor, String type) {
		String[] seed = args[1].split(",");
		MDC.put("logFileName", seed[0]);
		Seeder seeder = new Seeder(seed, logger, args[2], type);
		Runnable worker = seeder;
		logger.info("Executing thread: with seed: " + seed[0] + " " + seed[1]);
		executor.execute(worker);
	}

	/**
	 * 
	 * @param args
	 * @param executor
	 */
	private static void __________________________________________testIterateSeeds(String[] args, ExecutorService executor, String type) {
    	String[] seed = args[0].split(",");
    	MDC.put("logFileName", seed[0]);
        Seeder seeder = new Seeder(seed, logger,"InboxAndSpam", type);
        Runnable worker = seeder;
        logger.info("Executing thread: with seed: " + seed[0] + " " +seed[1]);
        executor.execute(worker);
	}
}
