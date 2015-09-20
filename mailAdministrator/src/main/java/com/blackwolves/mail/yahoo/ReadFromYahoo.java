package com.blackwolves.mail.yahoo;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.mail.util.Constant;

/**
 * 
 * @author gastondapice
 *
 */
public class ReadFromYahoo {

	private static Logger logger = LoggerFactory.getLogger(ReadFromYahoo.class);
	
	private static ReadFromYahoo instance = null;

	protected ReadFromYahoo() {
	}

	public static ReadFromYahoo getInstance() {
		if (instance == null) {
			instance = new ReadFromYahoo();
		}
		return instance;
	}

	public void readEmailsAndGenerateBodies(boolean test, boolean warmup, String offer, int from, int to) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		try {
			Store store = session.getStore("imaps");
			// IMAP host for yahoo.
			store.connect(Constant.Yahoo.IMAP_YAHOO, "yaninadefays02@yahoo.com", "wolf2015.2");
			Folder bodiesFolder = store.getFolder(Constant.Yahoo.INBOX);
			bodiesFolder.open(Folder.READ_ONLY);
			Message msg[] = bodiesFolder.getMessages();
			StringBuilder mail = new StringBuilder();
			String vmta = "awu9";
			if(test){
				/*
				 * THIS IS ONLY FOR TEST
				 */
				mail = new StringBuilder();
				mail.append("x-virtual-mta: " + vmta);
				mail.append("\n");
				mail.append("x-receiver: tatigrane@yahoo.com");
				mail.append("\n");
				mail.append("x-receiver: yaninadefays02@yahoo.com");
				mail.append("\n");
				mail.append("x-receiver: danielsaulino03@yahoo.com");
				int radomBody =  YahooProcessor.randInt(0, msg.length-1);
				Message message = msg[radomBody];
				iterateHeaders(message, mail);
				mail.append("\n");
				mail.append("\n");
				mail.append(message.getContent());
				PrintWriter out = new PrintWriter(Constant.Yahoo.PICKUP_ROUTE + "test"+ UUID.randomUUID());
				out.println(mail);
				out.close();
			}else{
				/*
				 * THIS IS PRODUCTION
				 */
				List<String[]> contacts;
				if(warmup){
					contacts = YahooProcessor.generateList(Constant.ROUTE , "seeds.csv");
				}else{
					contacts = YahooProcessor.generateList("/root/blackwolves/lists/" + offer + "/" , "sup");
				}
//				for (String[] contact : contacts) {
				for (; from < to; from++) {
					String[] contact = contacts.get(from);
					String[] c = contact[0].split("\\|");
					mail = new StringBuilder();
					mail.append("x-virtual-mta: " + vmta);
					addExtraHeader(mail, c[0]);
					int radomBody =  YahooProcessor.randInt(0, msg.length-1);
					Message message = msg[radomBody];
					iterateHeaders(message, mail);
					mail.append("\n");
					mail.append("\n");
					mail.append(message.getContent());
					PrintWriter out = new PrintWriter(Constant.Yahoo.PICKUP_ROUTE + c[0]);
					out.println(mail);
					out.close();
				}
			}
			bodiesFolder.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void addExtraHeader(StringBuilder mail, String contact) {
		mail.append("\n");
		mail.append("x-receiver: " + contact);
		if(YahooProcessor.randInt(0, 10) <=5){
			mail.append("\n");
			mail.append("x-receiver: tatigrane@yahoo.com");
			mail.append("\n");
			mail.append("x-receiver: yaninadefays02@yahoo.com");
		}
	}
	
	/**
	 * @param message
	 * @param mail 
	 * @throws MessagingException
	 */
	private static void iterateHeaders(Message message, StringBuilder mail)
			throws MessagingException {
		Enumeration headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
			if(validateHeaders(h)){
				mail.append("\n");
				mail.append(h.getName() + ": " + h.getValue());
			}
		}
	}

	/**
	 * @param h
	 * @return
	 */
	private static boolean validateHeaders(Header h) {
		if(h.getName().equals("X-Apparently-To") || h.getName().equals("Return-Path")
				|| h.getName().equals("Received-SPF") || h.getName().equals("X-YMailISG")
				|| h.getName().equals("X-Originating-IP") || h.getName().equals("Authentication-Results")
				|| h.getName().equals("Received") || h.getName().equals("X-Yahoo-Newman-Property")
				|| h.getName().equals("X-YMail-OSG") || h.getName().equals("X-Yahoo-SMTP")
				|| h.getName().equals("Content-Length")){
			return false;
		}
		return true;
	}
	
}