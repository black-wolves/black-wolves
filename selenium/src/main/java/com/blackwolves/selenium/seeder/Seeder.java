/**
 * 
 */
package com.blackwolves.selenium.seeder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.collect.Lists;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Seeder {
	
	private static final Logger logger = LogManager.getLogger(Seeder.class.getName());

	/**
	 * @throws BrokenBarrierException 
	 * @throws InterruptedException 
	 * 
	 */
	public void checkMail(){
		
		final int THREADS = 25;
		
		List<String[]> seeds = generateSeedsList();
		List<String[]> ips = generateIpsList();
		
		List<List<String[]>> partitions = Lists.partition(seeds, 10);
		
		Random seedRandomizer = new Random();
		final Random ipRandomizer = new Random();
		
		ExecutorService executor = Executors.newFixedThreadPool(THREADS);
		for (final List<String[]> partition : partitions) {
			
			Runnable worker = new SeederRunnable(partition, ips, ipRandomizer);
			executor.execute(worker);
//			processSeeds(partition, ips, ipRandomizer);
		}
		

		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			
		}
		logger.debug("\nFinished all threads");
	}
	
	/**
	 * @return
	 */
	private List<String[]> generateIpsList() {
		List<String[]> ips = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader("/Users/gastondapice/Dropbox/Black Wolves/Seeder/" + SERVER + "/ip_curl.txt"));
			ips = reader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return ips;
	}

	private static final String SERVER = "test";
	
	/**
	 * @return
	 */
	private List<String[]> generateSeedsList() {
		List<String[]> seeds = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader("/Users/gastondapice/Dropbox/Black Wolves/Seeder/" + SERVER + "/seeds.csv"));
			seeds = reader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return seeds;
	}
}
