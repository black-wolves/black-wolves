/**
 * 
 */
package com.blackwolves.mail.yahoo;

import java.io.PrintWriter;
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

	/* (non-Javadoc)
	 * @see com.blackwolves.mail.yahoo.WolfYahoo#generateAndSendMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
//	@Override
//	public void generateAndSendMail(String user, String pass, String offerFrom,
//			String to, String subject, String body) {
//		super.generateAndSendMail(user, pass, offerFrom, to, subject, body);
//
//	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.mail.yahoo.WolfYahoo#readEmailsAndGenerateBodies(java.lang.String, int, int)
	 */
	@Override
	public void readEmailsAndGenerateBodies(String offer) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		String vmta = "awu9";
		StringBuilder mail = new StringBuilder();
		try {
			List<String> contacts = generateList("/root/blackwolves/lists/" + offer + "/" , "sup");
			logger.info("Contact lists generated");
			Store store = session.getStore("imaps");
			Folder offerFolder = null;
			int bodiesCount;
			if(!store.isConnected()){
				logger.info("Store is not connected, starting the connection");
				store.connect(Constant.Yahoo.IMAP_YAHOO, "yaninadefays03@yahoo.com", "wolf2015.1");
				logger.info("Connected to yaninadefays03@yahoo.com");
				offerFolder = store.getFolder(offer);
				offerFolder.open(Folder.READ_WRITE);
			}
			Message msgs[] = offerFolder.getMessages();
			bodiesCount = offerFolder.getMessageCount();
			logger.info("Bodies to create: " + bodiesCount);
			for (int i = 0; i < msgs.length; i++) {
				try{
					Message message = msgs[i];
					String[] from = message.getFrom()[0].toString().split("\\|");
					String receiver = from[1];
					if(contacts.contains(receiver) && from[0].contains("Military")){
						logger.info("Creating body: " + (i+1));
						mail = new StringBuilder();
						mail.append("x-virtual-mta: " + vmta);
						mail.append("\n");
						mail.append("x-receiver: " + receiver);
						iterateHeaders(message, mail);
						mail.append("\n");
						mail.append("\n");
						mail.append(message.getContent());
						PrintWriter out = new PrintWriter(Constant.Yahoo.BLACKWOLVES_ROUTE + "897/" + receiver);
						out.println(mail);
						out.close();
						logger.info("Body created for: " + receiver);
						--bodiesCount;
						logger.info("Remainig bodies: " + bodiesCount);
						saveMessages(store, offer, message, offerFolder, message.getMessageNumber());
					}
				}catch (ArrayIndexOutOfBoundsException e) {
					logger.error(e.getMessage(), e);
					continue;
				}
			}
			offerFolder.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	public void saveMessages(Store store, String offer, Message message, Folder offerFolder, int messageNumber) throws Exception {
		Folder dfolder = store.getFolder(offer+"-OLD");
		if (!dfolder.exists())
			dfolder.create(Folder.HOLDS_MESSAGES);

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
		offerFolder.expunge();
	}

}
