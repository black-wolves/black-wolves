/**
 * 
 */
package com.blackwolves.seeder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class Suscriber {

	private static final Logger logger = LogManager.getLogger(Seeder.class.getName());
	private static final String ROUTE = "/var/www/";

	public static void main(String[] args) {
		suscribeToNewsletters(args[0]);
	}

	public static void suscribeToNewsletters(String mySeed) {
		SuscriberRunnable suscriberRunnable =  new SuscriberRunnable(mySeed);
		suscriberRunnable.runProcess();
		

	}

	/**
	 * @return
	 */
	private static List<String[]> generateIpsList() {
		List<String[]> ips = new ArrayList<String[]>();
		try {
			CSVReader ipsReader = new CSVReader(new FileReader(ROUTE + "ip_curl.txt"));
			ips = ipsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return ips;
	}

	/**
	 * @return
	 */
	private static List<String[]> generateSeedsList() {
		List<String[]> seeds = new ArrayList<String[]>();
		try {
			CSVReader seedsReader = new CSVReader(new FileReader(ROUTE + "seeds.csv"));
			seeds = seedsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return seeds;
	}
}
