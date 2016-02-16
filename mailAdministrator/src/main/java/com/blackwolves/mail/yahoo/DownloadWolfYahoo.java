package com.blackwolves.mail.yahoo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Header;
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
public class DownloadWolfYahoo extends WolfYahoo{

	/**
	 * 
	 * @param offer
	 * @param seed
	 * @param pass
	 */
	@Override
	public void downloadAndGenerateDropBodies(String offer, String seed, String pass) {
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
						int i = randInt(0, msgs.length-1);
						Message message = msgs[i];
						String receiver = message.getAllRecipients()[0].toString();
//						if(contacts.contains(receiver) && from[0].contains("Military")){
							logger.info("Creating body: " + count);
							count++;
							StringBuilder mail = new StringBuilder();
							mail.append("x-virtual-mta: " + Constant.VMTA);
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
	 * @param message
	 * @param mail
	 * @throws MessagingException
	 * @throws FolderClosedException
	 */
	public void iterateHeaders(Message message, StringBuilder mail) throws MessagingException, FolderClosedException {
		Enumeration headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
//			if (validateHeaders(h)) {
				mail.append("\n");
				mail.append(h.getName() + ": " + h.getValue());
//				mail.append(h.getName() + ": " + (h.getName().equals("Return-Path")?"<>":h.getValue()));
//			}
		}
	}

	/**
	 * 
	 * @param h
	 * @return
	 */
	public boolean validateHeaders(Header h) {
//		if (h.getName().equals("X-Apparently-To") || h.getName().equals("Return-Path")
//				|| h.getName().equals("Received-SPF") || h.getName().equals("X-YMailISG")
//				|| h.getName().equals("X-Originating-IP") || h.getName().equals("Authentication-Results")
//				|| h.getName().equals("Received") || h.getName().equals("X-Yahoo-Newman-Property")
//				|| h.getName().equals("X-YMail-OSG") || h.getName().equals("X-Yahoo-SMTP")
//				|| h.getName().equals("X-Yahoo-Newman-Id") || h.getName().equals("Content-Length")) {
		if (h.getName().equals("X-Yahoo-SMTP")) {
			return false;
		}
		return true;
	}

	@Override
	public void generateAndSendEmail(String user, String pass, String subject,
			String body, String contactEmail, String domain, String offerFrom)
			throws MessagingException {
		// TODO Auto-generated method stub
		
	}

}