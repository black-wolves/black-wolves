package com.blackwolves.mail.yahoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
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

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public abstract class WolfYahoo {

	protected static Logger logger = LoggerFactory.getLogger(WolfYahoo.class);

	public void generateAndSendMail(String user, String pass, CustomFrom customFrom, String subject, String body) {

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

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			//message.setFrom(new InternetAddress(from));
			message.setFrom(customFrom);

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setContent(body, Constant.Yahoo.CONTENT_TYPE);
			message.setHeader("Content-Transfer-Encoding", Constant.Yahoo.CONTENT_TRANSFER_ENCODING);
			message.setHeader("Message-ID", Constant.EMPTY_STRING);
			message.setHeader("X-Priority", "1");
			
			Address [] ad =  new Address[1] ;
			ad[0] =  new InternetAddress(user);

			message.writeTo(new FileOutputStream(new File("/var/www/logs/"+customFrom.getCustomer())));

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(Constant.Yahoo.HOST, user, pass);
			transport.sendMessage(message, ad);
			transport.close();
			logger.info("Body generation successfully for "+ customFrom.getCustomer());
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public abstract void readEmailsAndGenerateBodies(String offer, int from, int to);

	/**
	 * @param message
	 * @param mail
	 * @throws MessagingException
	 */
	public void iterateHeaders(Message message, StringBuilder mail) throws MessagingException {
		Enumeration headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
			if (validateHeaders(h)) {
				mail.append("\n");
				mail.append(h.getName() + ": " + h.getValue());
			}
		}
	}

	/**
	 * @param h
	 * @return
	 */
	public boolean validateHeaders(Header h) {
		if (h.getName().equals("X-Apparently-To") || h.getName().equals("Return-Path")
				|| h.getName().equals("Received-SPF") || h.getName().equals("X-YMailISG")
				|| h.getName().equals("X-Originating-IP") || h.getName().equals("Authentication-Results")
				|| h.getName().equals("Received") || h.getName().equals("X-Yahoo-Newman-Property")
				|| h.getName().equals("X-YMail-OSG") || h.getName().equals("X-Yahoo-SMTP")
				|| h.getName().equals("Content-Length")) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public static List<String[]> generateList(String route, String file) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(route + file));
			list = reader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
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