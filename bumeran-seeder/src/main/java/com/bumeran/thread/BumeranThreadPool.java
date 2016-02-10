package com.bumeran.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.bumeran.seeder.BumeranSeeder;

/**
 * 
 * @author gastondapice
 *
 */
public class BumeranThreadPool {
	
	private static Logger logger = LoggerFactory.getLogger(BumeranThreadPool.class);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executor = null;
		logger.info("Starting one SeederThreadPool");
		executor = Executors.newFixedThreadPool(1);
		processArguments(args, executor);
		
		if(executor!=null){
			executor.shutdown();
	        
	        while (!executor.isTerminated()) {
	        	
	        }
	        logger.info("Finished all threads");
		}
	}
	
	/**
	 * 
	 * @param args
	 * @param executor
	 */
	private static void processArguments(String[] args, ExecutorService executor) {
		MDC.put("logFileName", "bumeran");
		BumeranSeeder seeder = new BumeranSeeder(logger);
		if(seeder!=null){
			Runnable worker = seeder;
			logger.info("Executing thread");
			executor.execute(worker);
		}
	}
}
