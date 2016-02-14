package com.blackwolves.mail.yahoo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.FolderClosedException;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.mail.CustomFrom;
import com.blackwolves.mail.CustomMimeMessage;
import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public abstract class WolfYahoo {

	protected static Logger logger = LoggerFactory.getLogger(WolfYahoo.class);

	public void generateAndSendMail(String user, String pass, String subject, String body, String contactEmail, String domain, String offerFrom) throws MessagingException {

		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", Constant.TRUE);
		properties.put("mail.smtp.host", Constant.Yahoo.HOST);
		properties.put("mail.smtp.user", user);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", Constant.Yahoo.PORT);
		properties.put("mail.smtp.auth", Constant.TRUE);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		// Create a default MimeMessage object.
		MimeMessage message = new CustomMimeMessage(session, domain);
//		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		//message.setFrom(new InternetAddress(from));
		CustomFrom customFrom = new CustomFrom(contactEmail, user, offerFrom);
		message.setFrom(customFrom);

		// Set Subject: header field
		message.setSubject(subject);

		// Now set the actual message
		
//		MimeBodyPart textPart = new MimeBodyPart();
//		textPart.setContent(body, "text/html");
//		Multipart mp = new MimeMultipart();
//		mp.addBodyPart(textPart);
//		message.setContent(mp);
		
		message.setContent(body, Constant.Yahoo.CONTENT_TYPE);
		
		Address [] toAd =  new Address[1] ;
		toAd[0] =  new InternetAddress(contactEmail);
		message.addRecipient(Message.RecipientType.TO, toAd[0]);

		// Send message
		Transport transport = session.getTransport("smtp");
		transport.connect(Constant.Yahoo.HOST, user, pass);
		transport.sendMessage(message,message.getAllRecipients());
		transport.close();
	}

	public abstract void readEmailsAndGenerateBodies(String offer, String seed, String pass);

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
//				mail.append(h.getName() + ": " + (h.getName().equals("Return-Path")?"<>":h.getValue()));
				mail.append(h.getName() + ": " + h.getValue());
				
//				if header == date change the date
//				String pattern = "EEE, dd MMM yyyy HH:mm:ss Z";
//				SimpleDateFormat format = new SimpleDateFormat(pattern);
//				message.setHeader("DATE", format.format(new Date()));
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
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it
		// inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}