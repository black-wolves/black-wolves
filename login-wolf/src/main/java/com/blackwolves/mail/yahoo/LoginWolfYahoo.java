package com.blackwolves.mail.yahoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public class LoginWolfYahoo {

	private static Logger logger = LoggerFactory.getLogger(LoginWolfYahoo.class);

	public static void main(String[] args) {
		
		String inputFileName = args[0];
		String outputFileName = args[1];
		checkAvailableSeeds(inputFileName, outputFileName);
	}

	/**
	 * 
	 * @param inputFileName
	 * @param outputFileName 
	 */
	private static void checkAvailableSeeds(String inputFileName, String outputFileName) {
		logger.info("Generating contacts list");
		List<String[]> contacts = generateSeedsList(inputFileName);
		logger.info("Contact lists generated");
		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		
		clearFileContent(outputFileName);
		
		for (String[] seed : contacts) {
			try{
				Store store = session.getStore("imaps");
				
				logger.info("Starting the connection");
				store.connect(Constant.Yahoo.IMAP_YAHOO, Constant.Yahoo.IMAP_PORT, seed[0], seed[1]);
				logger.info("Connected to " + seed);
				
				
				Folder inbox = store.getFolder(Constant.Yahoo.INBOX);
				inbox.open(Folder.READ_WRITE);
				int inboxCount = inbox.getMessageCount();
				inbox.close(true);
				logger.info("Inbox folder closed");
				
				Folder spam = store.getFolder(Constant.Yahoo.SPAM);
				spam.open(Folder.READ_WRITE);
				int spamCount = spam.getMessageCount();
				spam.close(true);
				logger.info("Spam folder closed");
				
				logger.info("Seed is alive with " + inboxCount + " messages in inbox and " + spamCount + " messages in spam");
				
				store.close();
				
				writeSeedToFile(seed, outputFileName);
				logger.info("Store closed");
			} catch (NoSuchProviderException e) {
				logger.error("Error processing seed: " + seed[0] + " with pass: " + seed[1]);
				logger.error(e.getMessage(), e);
				continue;
			} catch (AuthenticationFailedException e) {
				logger.error("Error processing seed: " + seed[0] + " with pass: " + seed[1]);
				logger.error(e.getMessage());
				continue;
			} catch (MessagingException e) {
				logger.error("Error processing seed: " + seed[0] + " with pass: " + seed[1]);
				logger.error(e.getMessage(), e);
				continue;
			}
		}
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
	
	/**
	 * 
	 * @param outputFileName
	 */
	private static void clearFileContent(String outputFileName){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(Constant.ROUTE + outputFileName);
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

}
