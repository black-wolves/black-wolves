package com.blackwolves.mail.yahoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public abstract class WolfYahoo{

	protected static Logger logger = LoggerFactory.getLogger(WolfYahoo.class);
	
	public abstract void generateAndSendEmail(String user, String pass, String subject, String body, String contactEmail, String domain, String offerFrom) throws MessagingException;
	
	public abstract void downloadAndGenerateDropBodies(String offer, String seed, String pass);

	/**
	 * 
	 * @param seed
	 */
	protected void writeSeedToFile(String[] seed, String outputFileName) {
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
	protected List<String> readSeedsFromFile(String outputFileName) {
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
	/**
	 * 
	 * @param is
	 * @return
	 */
	protected static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	/**
	 * 
	 * @param route
	 * @param file
	 * @return
	 */
	public static List<String> generateList(String route, String file) {
		List<String[]> list = new ArrayList<String[]>();
		List<String> finalList = new ArrayList<String>();
		try {
			CSVReader reader = new CSVReader(new FileReader(route + file));
			list = reader.readAll();
			for (String[] uglySeed : list) {
				String[] seed = uglySeed[0].split("\\|");
				finalList.add(seed[0]);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return finalList;
	}

	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	protected static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it
		// inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}