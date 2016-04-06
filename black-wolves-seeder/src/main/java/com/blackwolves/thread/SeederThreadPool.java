/**
 * 
 */
package com.blackwolves.thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		boolean goal = false;
		
		do{
			ExecutorService executor = null;
			
			List<Seed> seeds = JDBC.getLastUpdatedSeeds();
			
			logger.info("Last updated seeds size: " + seeds.size() + " seeds");
			
			double loginPercentage = YahooRunnable.generateDoubleRandom(1,1);
			
			logger.info("Login percentage: " + loginPercentage);
			
			int sampleSeeds = (int) (seeds.size() * loginPercentage);
			
			sampleSeeds = sampleSeeds<50?sampleSeeds:50;

//			sampleSeeds = 1;

			logger.info("Sample seeds: " + sampleSeeds);
			
			List<Seed> finalSeeds = seeds.subList(0, sampleSeeds);
			
			logger.info("Writing seeds to file /var/www/in-use-seeds.txt");
			writeSeedsToFile(finalSeeds);
			
			logger.info("Updating seeds IN_USE to TRUE");
			JDBC.updateSeeds(finalSeeds, true);
			
			logger.info("Processing " + finalSeeds.size() + " seeds");
			executor = Executors.newFixedThreadPool(finalSeeds.size());
			
			processSeeds(executor, finalSeeds.size(), finalSeeds);
			if (executor != null) {
				executor.shutdown();
	
				while (!executor.isTerminated()) {
	
				}
				logger.info("Finished all threads");
			}
			
			logger.info("Updating seeds IN_USE to FALSE");
			JDBC.updateSeeds(finalSeeds, false);
			
			goal = checkGoal();
		}while(!goal);
		
	}

	private static boolean checkGoal() {
		Map<String, Object> stats = JDBC.getStats();
		int mailCount = (int) stats.get(Constant.FEEDER.MAIL_COUNT);
		int opened = (int) stats.get(Constant.FEEDER.OPENED);
//		int clicked = (int) stats.get(Constant.FEEDER.CLICKED);
//		int spammed = (int) stats.get(Constant.FEEDER.SPAMMED);
		
		double openRate = (double)opened/(double)mailCount;
//		double clickRate = (double)clicked/(double)mailCount;
//		double spamRate = (double)spammed/(double)mailCount;
		
		double randomGoal = YahooRunnable.generateDoubleRandom(0.35, 0.15);
		logger.info("Random goal is: " + randomGoal);
		logger.info("Open Rate is: " + openRate);
		
		if(openRate > randomGoal){
			logger.info("OpenRate is greater than goal. We are on track!");
			return true;
		}
		return false;
	}

	private static void processSeeds(ExecutorService executor, int size, List<Seed> finalSeeds) {
		logger.info("Processing seeds before the for");
		for (int i = 0; i < finalSeeds.size(); i++) {
			Seed seed = finalSeeds.get(i);
			MDC.put("logFileName", seed.getUser());
			Seeder seeder = new Seeder(seed, logger);
			Runnable worker = seeder;
			logger.info("Executing thread: " + i + " with seed: " + seed.getUser() + " and password " + seed.getPassword());
			executor.execute(worker);
		}
	}
	
	public static void writeSeedsToFile(List<Seed> seeds) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(Constant.ROUTE + "in-use-seeds.txt"));
			for (Seed seed : seeds) {
				pw.write(seed.getUser());
				pw.write("\n");
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
}
