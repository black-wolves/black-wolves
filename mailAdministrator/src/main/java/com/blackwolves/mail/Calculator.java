package com.blackwolves.mail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.mail.util.Constant;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author gaston.dapice
 *
 */
public class Calculator {

	private static Logger logger = LoggerFactory.getLogger(Calculator.class);
	
	public static void main(String args[]) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		List<String[]> seeds = generateSeedsList();
		List<String> warmupDomains = generateDomainsList();
		Map<String, Long> spamDomains = new HashMap<String, Long>();
		Map<String, Long> inboxDomains = new HashMap<String, Long>();
		int i = 1;
		int max = seeds.size()<=50?seeds.size():50;
		for (String[] seed : seeds) {
			if (i > max){
				break;
			}
			try{
				Store store = session.getStore("imaps");
				// IMAP host for yahoo.
				store.connect(Constant.Yahoo.IMAP_YAHOO, seed[0], seed[1]);
				obtainFoldersCount(store, spamDomains, inboxDomains, warmupDomains);
				store.close();
				i++;
			} catch (NoSuchProviderException e) {
				logger.error(e.getMessage(), e);
			} catch (MessagingException e) {
				logger.error(e.getMessage(), e);
			} catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
		Map<String, Double> percentageDomains = new HashMap<String, Double>();
		percentageDomains = obtainDomainPercentage(spamDomains, inboxDomains);
		Set<String> domains = percentageDomains.keySet();
		for (String domain : domains) {
			DecimalFormat df = new DecimalFormat("#.00");
			logger.info("Domain: " + domain + " ===>>> Inbox rate: " + df.format(percentageDomains.get(domain)) + "%");
		}
		
//		logger.info(store);
//		Folder spam = store.getFolder(SPAM);
//		spam.open(Folder.READ_WRITE);
//		Folder inbox = store.getFolder(INBOX);
//		inbox.open(Folder.READ_WRITE);
//			
//		showMails(inbox, spam);
//		showUnreadMails(inbox);
//		spam.close(true);
//		inbox.close(true);
//		store.close();

	}
	
	/**
	 * @return
	 */
	private static List<String[]> generateSeedsList() {
		List<String[]> seeds = new ArrayList<String[]>();
		try {
			CSVReader seedsReader = new CSVReader(new FileReader(Constant.ROUTE + "seeds.csv"));
			seeds = seedsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return seeds;
	}
	
	/**
	 * @return
	 */
	private static List<String> generateDomainsList() {
		List<String[]> domains = new ArrayList<String[]>();
		List<String> finalDomains = new ArrayList<String>();
		try {
			CSVReader domainsReader = new CSVReader(new FileReader(Constant.ROUTE + "domains.txt"));
			domains = domainsReader.readAll();
			for (String[] domain : domains) {
				finalDomains.add(domain[0]);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return finalDomains;
	}

	/**
	 * 
	 * @param store
	 * @param spamDomains
	 * @param inboxDomains
	 * @param warmupDomains
	 * @throws MessagingException
	 */
	private static void obtainFoldersCount(Store store, Map<String, Long> spamDomains, Map<String, Long> inboxDomains, List<String> warmupDomains) throws MessagingException {
		Folder spam = store.getFolder(Constant.Yahoo.SPAM);
		Folder inbox = store.getFolder(Constant.Yahoo.INBOX);
		spamDomains = obtainFolderCountByDomain(spamDomains, spam, warmupDomains);
		inboxDomains = obtainFolderCountByDomain(inboxDomains, inbox, warmupDomains);
		
//		double totalSpam = spam.getMessageCount();
//		double totalInbox = inbox.getMessageCount();
//		double totalMail = totalSpam + totalInbox;
//		Double percent = (totalInbox / (totalMail)) * 100;
//		DecimalFormat df = new DecimalFormat("#.00");
//		logger.info("Inbox rate: " + df.format(percent));
	}

	/**
	 * 
	 * @param spamDomains
	 * @param inboxDomains
	 * @return 
	 */
	private static Map<String, Double> obtainDomainPercentage(Map<String, Long> spamDomains, Map<String, Long> inboxDomains) {
		Map<String, Double> percentageDomains = new HashMap<String, Double>();
		Set<String> sDomains = spamDomains.keySet();
		Set<String> iDomains = inboxDomains.keySet();
		
		for (String domain : sDomains) {
			Long spamDomainCount = spamDomains.get(domain);
			Long inboxDomainCount = inboxDomains.get(domain);
			if(inboxDomainCount != null){
				Double totalMail = (double) (spamDomainCount + inboxDomainCount);
				Double percent = (inboxDomainCount / (totalMail)) * 100;
				percentageDomains.put(domain, percent);
				inboxDomains.remove(domain);
			}else{
				percentageDomains.put(domain, Double.valueOf(100));
			}
			
		}
		for (String domain : iDomains) {
			percentageDomains.put(domain, Double.valueOf(100));
		}
		return percentageDomains;
	}

	/**
	 * 
	 * @param domains 
	 * @param folder
	 * @param warmupDomains 
	 * @return
	 * @throws MessagingException
	 */
	private static Map<String, Long> obtainFolderCountByDomain(Map<String, Long> domains, Folder folder, List<String> warmupDomains)
			throws MessagingException {
		folder.open(Folder.READ_ONLY);
		Message msg[] = folder.getMessages();
		int i = 1;
		int max = msg.length<=50?msg.length:50;
		for (Message message : msg) {
			if(i >= max){
				if(validateMessage(message)){
					String domain = message.getFrom()[0].toString().split("@")[1].replace(">","");
					if(warmupDomains.contains(domain)){
						Long domainCount = domains.get(domain);
						domains.put(domain, calculateDomainCount(domainCount));
					}
				}
				i++;
			}
			
		}
		folder.close(true);
		return domains;
	}

	/**
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	private static boolean validateMessage(Message message)
			throws MessagingException {
		return message!=null && message.getFrom()!=null && message.getFrom()[0]!=null && message.getFrom()[0].toString().split("@")!=null && message.getFrom()[0].toString().split("@")[1]!=null;
	}

	/**
	 * @param domainCount
	 * @return
	 */
	private static Long calculateDomainCount(Long domainCount) {
		if(domainCount!=null){
			return domainCount.longValue() + 1L;
		}
		return Long.valueOf(1);
	}
	
	/**
	 * @param inbox
	 * @param spam 
	 */
	private static void showMails(Folder inbox, Folder spam) {
		try {
			Message msg[] = spam.getMessages();
			logger.info("MAILS: " + msg.length);
//			String flags[] = inbox.getPermanentFlags().getUserFlags();
//			for (String flag : flags) {
//				logger.info(flag);
//			}
			for (Message message : msg) {
				try {
//					message.setFlag(Flags.Flag.FLAGGED, true);
					message.setFlags(new Flags("$NotJunk"), true);
					logger.info("DATE: " + message.getSentDate().toString());
					logger.info("FROM: " + message.getFrom()[0].toString());
					logger.info("Clean From: " + (message.getFrom()[0].toString().split("@"))[1].replace(">",""));
					logger.info("SUBJECT: " + message.getSubject().toString());
//					logger.info("CONTENT: " + message.getContent().toString());
					logger.info("******************************************");
					message.saveChanges();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
//			spam.copyMessages(msg, inbox);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	static public void showUnreadMails(Folder inbox) {
		try {
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message msg[] = inbox.search(ft);
			logger.info("MAILS: " + msg.length);
			for (Message message : msg) {
				try {
					logger.info("DATE: " + message.getSentDate().toString());
					logger.info("FROM: " + message.getFrom()[0].toString());
					logger.info("SUBJECT: " + message.getSubject().toString());
//					logger.info("CONTENT: " + message.getContent().toString());
					logger.info("******************************************");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
