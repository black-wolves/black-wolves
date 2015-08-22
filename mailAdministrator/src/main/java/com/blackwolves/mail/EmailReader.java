/**
 * 
 */
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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author gaston.dapice
 *
 */
public class EmailReader {

	private static final Logger logger = LogManager.getLogger(EmailReader.class.getName());
	
	private static final String IMAP_YAHOO = "imap.mail.yahoo.com";
//	private static final String ROUTE = "/var/www/";
	private static final String ROUTE = "/Users/gastondapice/Dropbox/Black Wolves/Seeder/test/";
	private static final String INBOX = "Inbox";
	private static final String SPAM = "Bulk Mail";

	public static void main(String args[]) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			List<String[]> seeds = generateSeedsList();
			Map<String, Long> spamDomains = new HashMap<String, Long>();
			Map<String, Long> inboxDomains = new HashMap<String, Long>();
			for (String[] seed : seeds) {
				Store store = session.getStore("imaps");
				// IMAP host for yahoo.
				store.connect(IMAP_YAHOO, seed[0], seed[1]);
				obtainFoldersCount(store, spamDomains, inboxDomains);
				store.close();
			}
			Map<String, Double> percentageDomains = new HashMap<String, Double>();
			percentageDomains = obtainDomainPercentage(spamDomains, inboxDomains);
			Set<String> domains = percentageDomains.keySet();
			for (String domain : domains) {
				DecimalFormat df = new DecimalFormat("#.00");
				logger.info("Domain: " + domain + " ===>>> Inbox rate: " + df.format(percentageDomains.get(domain)) + "%");
			}
			
//			logger.info(store);
//			Folder spam = store.getFolder(SPAM);
//			spam.open(Folder.READ_WRITE);
//			Folder inbox = store.getFolder(INBOX);
//			inbox.open(Folder.READ_WRITE);
//			
//			
//			showMails(inbox, spam);
//			showUnreadMails(inbox);
//			spam.close(true);
//			inbox.close(true);
//			store.close();

		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
			System.exit(1);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
			System.exit(2);
		}

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

	/**
	 * 
	 * @param store
	 * @param inboxDomains 
	 * @param spamDomains 
	 * @param percentageDomains2 
	 * @throws MessagingException
	 */
	private static void obtainFoldersCount(Store store, Map<String, Long> spamDomains, Map<String, Long> inboxDomains) throws MessagingException {
		Folder spam = store.getFolder(SPAM);
		Folder inbox = store.getFolder(INBOX);
		spamDomains = obtainFolderCountByDomain(spamDomains, spam);
		inboxDomains = obtainFolderCountByDomain(inboxDomains, inbox);
		
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
	 * @return
	 * @throws MessagingException
	 */
	private static Map<String, Long> obtainFolderCountByDomain(Map<String, Long> domains, Folder folder)
			throws MessagingException {
		folder.open(Folder.READ_ONLY);
		Message msg[] = folder.getMessages();
		for (Message message : msg) {
			String domain = message.getFrom()[0].toString().split("@")[1].replace(">","");
			Long domainCount = domains.get(domain);
			domains.put(domain, calculateDomainCount(domainCount));
		}
		folder.close(true);
		return domains;
	}

	/**
	 * @param domainCount
	 * @return
	 */
	private static long calculateDomainCount(Long domainCount) {
		if(domainCount!=null){
			return Long.sum(domainCount, Long.valueOf(1));
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