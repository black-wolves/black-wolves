/**
 * 
 */
package com.blackwolves.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.blackwolves.seeder.Seed;
import com.blackwolves.seeder.Seeder;
import com.blackwolves.seeder.YahooRunnable;
import com.blackwolves.seeder.util.Constant;
import com.blackwolves.seeder.util.JDBC;

/**
 * @author gastondapice
 *
 */
public class SeederThreadPool {

	private static Logger logger = LoggerFactory.getLogger(SeederThreadPool.class);

	public static void main(String[] args) {
		ExecutorService executor = null;
		
		boolean goal = false;
		do{
			List<Seed> seeds = JDBC.getLastUpdatedSeeds();
			
			double loginPercentage = YahooRunnable.generateDoubleRandom(1, 1);
			
			int sampleSeeds = (int) (seeds.size() * loginPercentage);
	
			sampleSeeds = sampleSeeds<50?sampleSeeds:50;
			
			List<Seed> finalSeeds = seeds.subList(0, sampleSeeds);
			
			logger.info("Processing " + finalSeeds.size() + " seeds");
			executor = Executors.newFixedThreadPool(finalSeeds.size());
			
			processSeeds(executor, finalSeeds.size(), finalSeeds);
			if (executor != null) {
				executor.shutdown();
	
				while (!executor.isTerminated()) {
	
				}
				logger.info("Finished all threads");
			}
			
			goal = checkGoal();
		}while(!goal);
		
	}

	private static boolean checkGoal() {
		Map<String, Object> stats = JDBC.getStats();
		int mailCount = (int) stats.get(Constant.FEEDER.MAIL_COUNT);
		int opened = (int) stats.get(Constant.FEEDER.OPENED);
		int clicked = (int) stats.get(Constant.FEEDER.CLICKED);
		int spammed = (int) stats.get(Constant.FEEDER.SPAMMED);
		
		double openRate = opened/mailCount;
		double clickRate = clicked/mailCount;
		double spamRate = spammed/mailCount;
		
		double randomGoal = YahooRunnable.generateDoubleRandom(0.35, 0.08);
		logger.info("Random goal is: " + randomGoal);
		
		if(openRate > randomGoal){
			return true;
		}
		return false;
	}

	private static void processSeeds(ExecutorService executor, int size, List<Seed> finalSeeds) {
		for (int i = 0; i < finalSeeds.size(); i++) {
			Seed seed = finalSeeds.get(i);
			MDC.put("logFileName", seed.getUser());
			Seeder seeder = new Seeder(seed, logger);
			Runnable worker = seeder;
			logger.info("Executing thread: " + i + " with seed: " + seed.getUser() + " and password " + seed.getPassword());
			executor.execute(worker);
		}
	}
}
