package com.blackwolves.subscriber.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.blackwolves.subscriber.Seed;
import com.blackwolves.subscriber.Subscriber;
import com.blackwolves.subscriber.util.JDBC;

/**
 * @author daniel.grane
 *
 */
public class SubscriberThreadPool {

	private static final Logger logger = LoggerFactory.getLogger(SubscriberThreadPool.class);

	public SubscriberThreadPool() {
	}

	public static void main(String[] args) {
		logger.info("Starting Subscriber...");
		List<Seed> seeds = JDBC.getSeedsForSubscriptions();
		logger.info("Working with "+seeds.size() + " seeds");

		ExecutorService executor = Executors.newFixedThreadPool(seeds.size());
		subscribeToNewsletters(seeds, executor);
		if (executor != null) {
			executor.shutdown();
			while (!executor.isTerminated()) {

			}
		}
		logger.info("Finished all threads");

	}

	/**
	 * 
	 * @param seeds
	 * @param executor
	 */
	private static void subscribeToNewsletters(List<Seed> seeds,  ExecutorService executor) {
		for (int i = 0; i < seeds.size(); i++) {
			Seed seed = seeds.get(i);
			MDC.put("logFileName", seed.getUser());
			Subscriber subscriber = new Subscriber(seed, logger);
			Runnable worker = subscriber;
			logger.info("Executing thread: " + i + " with seed: " + seed.getUser() + " and password " + seed.getPassword());
			executor.execute(worker);
		}
	}
}
