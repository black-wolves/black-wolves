package com.blackwolves.subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.blackwolves.seeder.YahooRunnable;

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
		List<String[]> seeds = YahooRunnable.generateSeedsList("subscriber.csv");
		ExecutorService executor = null;
		for (int i = index; i < seeds.size() - 1; i++) {
			executor = Executors.newFixedThreadPool(50);
			logger.info("Count is: " + i);
			subscribeToNewsletters(seeds, i, executor);
			i = i + 49;
			if (executor != null) {
				executor.shutdown();
				while (!executor.isTerminated()) {
					try {
						executor.awaitTermination(300000, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally {
					    if (!executor.isTerminated()) {
					        logger.info("******************* cancel non-finished tasks**********************");
					    }
					    executor.shutdownNow();
					    logger.info("shutdown finished");
					}
				}
			}

		}
		logger.info("Finished all threads");

		
	}

	private static void subscribeToNewsletters(List<String[]> seeds, int index, ExecutorService executor) {
		int limit = index + 50;
		for (int i = index; i <= limit; i++) {
			String[] seed = seeds.get(i);
			MDC.put("logFileName", seed[0]);
			Subscriber subscriber = new Subscriber(seed);
			Runnable worker = subscriber;
			logger.info("Executing thread: " + i + " with seed: " + seed[0] + " " + seed[1]);
			executor.execute(worker);
		}
	}
}
