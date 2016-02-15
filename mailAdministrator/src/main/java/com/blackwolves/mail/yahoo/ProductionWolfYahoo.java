/**
 * 
 */
package com.blackwolves.mail.yahoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public class ProductionWolfYahoo extends WolfYahoo {

	private static final String VMTA = "vps-yahoo";

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.mail.yahoo.WolfYahoo#readEmailsAndGenerateBodies(java.lang.String, int, int)
	 */
	@Override
	public void readEmailsAndGenerateBodies(String offer, String seed, String pass) {
		try {
//			List<String> contacts = generateList("/root/blackwolves/lists/" + offer + "/" , "sup");
			logger.info("Contact lists generated");
			
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", "imaps");
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			
			boolean keepGoing = true;
			int count = 1;
			while(keepGoing){
				if(!store.isConnected()){
					
					logger.info("Store is not connected, starting the connection");
					store.connect(Constant.Yahoo.IMAP_YAHOO, Constant.Yahoo.IMAP_PORT, seed, pass);
					logger.info("Connected to " + seed);
				}
				Folder offerFolder = store.getFolder(Constant.Yahoo.INBOX);
				offerFolder.open(Folder.READ_WRITE);
				Message msgs[] = offerFolder.getMessages();
				int bodiesCount = offerFolder.getMessageCount();
				logger.info("Bodies to create: " + bodiesCount);
				keepGoing = msgs==null||msgs.length<=0?false:true;
				if(keepGoing){
					try{
						int i = WolfYahoo.randInt(0, msgs.length-1);
						Message message = msgs[i];
						String receiver = message.getAllRecipients()[0].toString();
//						if(contacts.contains(receiver) && from[0].contains("Military")){
							logger.info("Creating body: " + count);
							count++;
							StringBuilder mail = new StringBuilder();
							mail.append("x-virtual-mta: " + VMTA);
							mail.append("\n");
							mail.append("x-receiver: " + "gastondapice@yahoo.com");
							iterateHeaders(message, mail);
							mail.append("\n");
							mail.append("\n");
							mail.append(message.getContent());
							PrintWriter out = new PrintWriter(Constant.Yahoo.BLACKWOLVES_ROUTE + offer + "/" + receiver + i);
							out.println(mail);
							out.close();
							logger.info("Body created for: " + receiver);
							--bodiesCount;
							logger.info("Remainig bodies: " + bodiesCount);
//							saveMessages(store, offer, message, offerFolder, message.getMessageNumber());
//						}
						offerFolder.close(true);
						logger.info("Folder closed");
					} catch (ArrayIndexOutOfBoundsException e) {
						logger.error(e.getMessage(), e);
						continue;
					}
				}
			}
			store.close();
			logger.info("Store closed");
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	private void saveMessages(Store store, String offer, Message message, Folder offerFolder, int messageNumber) throws Exception {
		Folder dfolder = store.getFolder(offer+"-OLD");
//		if (!dfolder.exists()){
//			dfolder.create(Folder.HOLDS_MESSAGES);
//		}

		// Get the message objects to copy
		Message[] msgs = offerFolder.getMessages(messageNumber, messageNumber);
		logger.info("Moving " + msgs.length + " messages");

		if (msgs.length != 0) {
			offerFolder.copyMessages(msgs, dfolder);
			offerFolder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);

			// Dump out the Flags of the moved messages, to insure that
			// all got deleted
			for (int i = 0; i < msgs.length; i++) {
				if (!msgs[i].isSet(Flags.Flag.DELETED))
					logger.info("Message # " + msgs[i] + " not deleted");
			}
		}
		logger.info("Message moved");
	}
	
	/**
	 * 
	 * @param seed
	 */
	private void writeSeedToFile(String[] seed, String outputFileName) {
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
	private List<String> readSeedsFromFile(String outputFileName) {
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
	
	private static String getStringFromInputStream(InputStream is) {

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

}
