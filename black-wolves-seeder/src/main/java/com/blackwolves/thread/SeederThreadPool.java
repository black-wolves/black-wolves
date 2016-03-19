/**
 * 
 */
package com.blackwolves.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.blackwolves.seeder.Seeder;
import com.blackwolves.seeder.YahooRunnable;
import com.blackwolves.seeder.util.Constant;

/**
 * @author gastondapice
 *
 */
public class SeederThreadPool {

	private static Logger logger = LoggerFactory.getLogger(SeederThreadPool.class);

	public static void main(String[] args) {
		ExecutorService executor = null;
		if (Constant.MULTIPLE.equals(args[0])) {
			logger.info("Starting multiple SeederThreadPool");
			List<String[]> seeds = new ArrayList<String[]>();
			if(args.length == 3 && Constant.SENDER.equals(args[2])){
				seeds = YahooRunnable.generateSeedsList("seeds_sender.csv");
			}else{
				seeds = YahooRunnable.generateSeedsList("seeds.csv");
			}

			executor = Executors.newFixedThreadPool(seeds.size());

			processSeeds(args, executor, seeds.size(), seeds, Constant.MULTIPLE);
		} else if (Constant.SPECIFIC.equals(args[0])) {
			logger.info("Starting specific SeederThreadPool");
			List<String[]> seeds = YahooRunnable.generateSeedsList("specific.csv");

			int seedsToProcess = seeds.size();
			executor = Executors.newFixedThreadPool(seedsToProcess);

			processSeeds(args, executor, seedsToProcess, seeds, Constant.SPECIFIC);
		} 
		
		else if (Constant.DESTROYER.equals(args[0])) {
			logger.info("Starting Destroyer SeederThreadPool");
			List<String[]> seeds = YahooRunnable.generateSeedsList("specific.csv");

			int seedsToProcess = seeds.size();
			if(seeds == null || seeds.size() == 0){
				logger.info("There are no seeds to load!");
			}else{
				executor = Executors.newFixedThreadPool(seedsToProcess);
				processSeeds(args, executor, seedsToProcess, seeds, Constant.DESTROYER);
			}
		}
		else if (Constant.ONE.equals(args[0])) {
			logger.info("Starting one SeederThreadPool");
			executor = Executors.newFixedThreadPool(1);
			processSeed(args, executor, Constant.ONE);
		}

		// UNCOMMENT THIS FOR TEST ONLY AND COMMENT THE ONE ABOVE
		// __________________________________________testIterateSeeds(args,
		// executor);

		if (executor != null) {
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
	private static void processSeeds(String[] args, ExecutorService executor, int seedsToProcess, List<String[]> seeds,
			String type) {

		for (int i = 0; i <= seedsToProcess - 1; i++) {
			String[] seed = seeds.get(i);
			MDC.put("logFileName", seed[0]);
			Seeder seeder = new Seeder(seed, logger, args[1], type);
			Runnable worker = seeder;
			YahooRunnable.writeSeedToFile(seed[0]);
			logger.info("Executing thread: " + i + " with seed: " + seed[0] + " " + seed[1]);
			executor.execute(worker);
		}
	}

	/**
	 * @param args
	 * @param executor
	 */
	private static void processSeed(String[] args, ExecutorService executor, String type) {
		String[] seed = args[1].split(",");
		MDC.put("logFileName", seed[0]);
		Seeder seeder = new Seeder(seed, logger, args[1], type);
		Runnable worker = seeder;
		logger.info("Executing thread: with seed: " + seed[0] + " " + seed[1]);
		executor.execute(worker);
	}

	/**
	 * 
	 * @param args
	 * @param executor
	 */
	private static void __________________________________________testIterateSeeds(String[] args,
			ExecutorService executor, String type) {
		String[] seed = args[0].split(",");
		MDC.put("logFileName", seed[0]);
		Seeder seeder = new Seeder(seed, logger, "InboxAndSpam", type);
		Runnable worker = seeder;
		logger.info("Executing thread: with seed: " + seed[0] + " " + seed[1]);
		executor.execute(worker);
	}
}
