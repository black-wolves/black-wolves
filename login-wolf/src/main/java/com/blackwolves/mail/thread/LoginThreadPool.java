/**
 * 
 */
package com.blackwolves.mail.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.mail.util.Constant;
import com.blackwolves.mail.util.SeedComparator;
import com.blackwolves.mail.yahoo.LoginWolfYahoo;

/**
 * @author gastondapice
 *
 */
public class LoginThreadPool {

	private static Logger logger = LoggerFactory.getLogger(LoginThreadPool.class);

	public static void main(String[] args) {

		String inputFileName = args[0];
		String outputFileName = args[1];

		int threads = Constant.DEFAULT_THREADS;
		if(args.length > 2){
			threads = Integer.valueOf(args[2]);
		}
		
		clearFileContent(outputFileName);
		clearFileContent("specific.csv");

		logger.info("Generating contacts list");
		List<String[]> contacts = generateSeedsList(inputFileName);
		if(contacts.size()<threads){
			threads = contacts.size();
		}
		List<List<String[]>> subLists = ListUtils.partition(contacts, contacts.size()/threads);
		logger.info("Contact lists generated");
		
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		List<Future<List<String[]>>> list = new ArrayList<Future<List<String[]>>>();
		for (List<String[]> subList : subLists) {
			Callable<List<String[]>> worker = new LoginWolfYahoo(subList);
			Future<List<String[]>> submit = executor.submit(worker);
			list.add(submit);
		}

		List<String[]> activeSeeds = new ArrayList<String[]>();
		List<String[]> activeSeedsWithSpam = new ArrayList<String[]>();
		List<String[]> inactiveSeeds = new ArrayList<String[]>();
		for (Future<List<String[]>> future : list) {
			processResults(future, outputFileName, activeSeeds, activeSeedsWithSpam, inactiveSeeds);
		}
		
		printResults(activeSeeds, activeSeedsWithSpam, inactiveSeeds);
		
		if (executor != null) {
			executor.shutdown();
			while (!executor.isTerminated()) {

			}
			logger.info("Finished all threads");
		}
	}

	/**
	 * @param future 
	 * @param outputFileName 
	 * @param inactiveSeeds 
	 * @param activeSeedsWithSpam 
	 * @param activeSeeds 
	 * 
	 */
	private static void processResults(Future<List<String[]>> future, String outputFileName, List<String[]> activeSeeds, List<String[]> activeSeedsWithSpam, List<String[]> inactiveSeeds) {
		try {
			List<String[]> result = future.get();

			for (String[] seed : result) {
				if(seed[4].equals(Constant.FALSE)){
					inactiveSeeds.add(seed);
				}else{
					writeSeedToFile(seed, outputFileName);
					
					isSpecific(activeSeeds, activeSeedsWithSpam, seed);
				}
			}
		
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (ExecutionException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @param activeSeeds
	 * @param activeSeedsWithSpam
	 * @param seed
	 */
	private static void isSpecific(List<String[]> activeSeeds,
			List<String[]> activeSeedsWithSpam, String[] seed) {
		int spamCount = Integer.valueOf(seed[3]);
		if(spamCount <= Constant.MAX_SPAM){
			activeSeeds.add(seed);
		}else{
			activeSeedsWithSpam.add(seed);
			writeSeedToFile(seed, "specific.csv");
		}
	}

	/**
	 * @param activeSeeds
	 * @param activeSeedsWithSpam
	 * @param inactiveSeeds
	 */
	private static void printResults(List<String[]> activeSeeds,
			List<String[]> activeSeedsWithSpam, List<String[]> inactiveSeeds) {
		Collections.sort(activeSeeds, new SeedComparator<String[]>());
		Collections.sort(activeSeedsWithSpam, new SeedComparator<String[]>());
		
		logger.info("");
		logger.info("Active seeds:");
		for (String[] seed : activeSeeds) {
			logger.info(seed[0] + "," + seed[1] + " ACTIVE seed with " + seed[2] + " messages in inbox and " + seed[3] + " messages in spam");
		}
		for (String[] seed : activeSeedsWithSpam) {
			logger.info(seed[0] + "," + seed[1] + " ACTIVE seed with " + seed[2] + " messages in inbox and " + seed[3] + " messages in spam");
		}
		logger.info("");
		logger.info("Inactive seeds:");
		for (String[] seed : inactiveSeeds) {
			logger.info("Seed " + seed[0] + " with pass: " + seed[1] + " is INACTIVE");
		}
		logger.info("");
		logger.info("Finished validating seeds");
	}

	/**
	 * @return
	 */
	private static List<String[]> generateSeedsList(String fileName) {
		List<String[]> seeds = new ArrayList<String[]>();
		try {
			CSVReader seedsReader = new CSVReader(new FileReader(Constant.ROUTE + fileName));
			seeds = seedsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return seeds;
	}
	
	/**
	 * 
	 * @param fileName
	 */
	private static void clearFileContent(String fileName){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(Constant.ROUTE + fileName);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} finally{
			if(writer!=null){
				writer.close();
			}
		}
	}
	
	/**
	 * 
	 * @param seed
	 */
	private static void writeSeedToFile(String[] seed, String outputFileName) {
		PrintWriter pw = null;
		try {
			List<String> usedSeeds = readSeedsFromFile(outputFileName);
			pw = new PrintWriter(new FileWriter(Constant.ROUTE + outputFileName));
			for (String usedSeed : usedSeeds) {
				pw.write(usedSeed);
				pw.write("\n");
			}
			pw.write(seed[0] + "," + seed[1]);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private static List<String> readSeedsFromFile(String outputFileName) {
		List<String> list = null;
		try {
			File file = new File(Constant.ROUTE + outputFileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			list = new ArrayList<String>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
