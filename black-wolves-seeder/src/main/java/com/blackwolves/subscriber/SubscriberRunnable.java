package com.blackwolves.subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.blackwolves.seeder.Seed;
import com.blackwolves.seeder.YahooRunnable;
import com.blackwolves.seeder.util.JDBC;

/**
 * @author daniel.grane
 *
 */
public class SubscriberRunnable {

	private static final Logger logger = LoggerFactory.getLogger(SubscriberRunnable.class);

	public SubscriberRunnable() {
	}

	public static void main(String[] args) {
		logger.info("Starting Subscriber...");
		int index = Integer.parseInt(args[0]);
		List<Seed> seeds = JDBC.getSeedsWithNoSubscriptions(index);
		ExecutorService executor = null;
			executor = Executors.newFixedThreadPool(seeds.size());
			subscribeToNewsletters(seeds, executor);
			if (executor != null) {
				executor.shutdown();
				try {
					executor.awaitTermination(5, TimeUnit.MINUTES);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (!executor.isTerminated()) {
						logger.info("******************* cancel non-finished tasks**********************");
					}
					executor.shutdownNow();
					logger.info("shutdown finished");
				}

		}
		logger.info("Finished all threads");

	}

	private static void subscribeToNewsletters(List<Seed> seeds,  ExecutorService executor) {
		for (int i = 0; i < seeds.size(); i++) {
			Seed seed = seeds.get(i);
			MDC.put("logFileName", seed.getUser());
			Subscriber subscriber = new Subscriber(seed);
			Runnable worker = subscriber;
			logger.info("Executing thread: " + i + " with seed: " + seed.getUser() + " " + seed.getPassword());
			executor.execute(worker);
		}
	}
}
