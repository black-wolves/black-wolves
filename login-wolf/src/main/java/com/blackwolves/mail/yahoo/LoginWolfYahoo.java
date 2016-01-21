package com.blackwolves.mail.yahoo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public class LoginWolfYahoo implements Callable<List<String[]>>{

	private static Logger logger = LoggerFactory.getLogger(LoginWolfYahoo.class);
	
	List<String[]> list;
	
	public LoginWolfYahoo(List<String[]> list) {
		this.list = list;
	}
	
	@Override
	public List<String[]> call() throws Exception {
		return checkAvailableSeeds(list, null);
	}


	/**
	 * 
	 * @param inputFileName
	 * @param outputFileName 
	 */
	private static List<String[]> checkAvailableSeeds(List<String[]> list, String outputFileName) {
		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		
		List<String[]> result = new ArrayList<String[]>();
		
		logger.info("Seeds to calculate: " + list.size());
		int seedsToGo = list.size();
		for (String[] seed : list) {
			logger.info("Seeds to go: " + seedsToGo);
			try{
				Store store = session.getStore("imaps");
				
				logger.info("Starting the connection");
				store.connect(Constant.Yahoo.IMAP_YAHOO, Constant.Yahoo.IMAP_PORT, seed[0], seed[1]);
				logger.info("Connected to " + seed[0] + " with pass: " + seed[1]);
				
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
				
				String[] newSeed = {seed[0], seed[1], String.valueOf(inboxCount), String.valueOf(spamCount), "true"};
				result.add(newSeed);
				store.close();
				logger.info("Store closed");
				
				--seedsToGo;
			} catch (NoSuchProviderException e) {
				String[] newSeed = {seed[0], seed[1], String.valueOf("-1"), String.valueOf("-1"), "true"};
				result.add(newSeed);
				logger.error("Error processing seed: " + seed[0] + " with pass: " + seed[1]);
				logger.error(e.getMessage());
				--seedsToGo;
				continue;
			} catch (AuthenticationFailedException e) {
				String[] newSeed = {seed[0], seed[1], String.valueOf("-1"), String.valueOf("-1"), "false"};
				result.add(newSeed);
				logger.error("Error processing seed: " + seed[0] + " with pass: " + seed[1]);
				logger.error(e.getMessage());
				--seedsToGo;
				continue;
			} catch (MessagingException e) {
				String[] newSeed = {seed[0], seed[1], String.valueOf("-1"), String.valueOf("-1"), "true"};
				result.add(newSeed);
				logger.error("Error processing seed: " + seed[0] + " with pass: " + seed[1]);
				logger.error(e.getMessage());
				--seedsToGo;
				continue;
			}
		}
		return result;
	}

}
