package com.blackwolves.mail.yahoo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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

import au.com.bytecode.opencsv.CSVReader;

/**
 * 
 * @author gastondapice
 *
 */
public class ReadFromYahoo {

	private static Logger logger = LoggerFactory.getLogger(ReadFromYahoo.class);

	public static void main(String[] args) {
		String offer = args[0];
		int from = Integer.valueOf(args[1]);
		int to = Integer.valueOf(args[2]);
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		try {
			Store store = session.getStore("imaps");
			// IMAP host for yahoo.
			store.connect(Constant.Yahoo.IMAP_YAHOO, "yaninadefays02@yahoo.com", "wolf2015.2");
			Folder bodiesFolder = store.getFolder(offer);
			bodiesFolder.open(Folder.READ_ONLY);
			Message msg[] = bodiesFolder.getMessages();
			StringBuilder mail = new StringBuilder();
			List<String[]> contacts = generateList(offer);
			String vmta = "awu9";
//			for (String[] contact : contacts) {
			for (; from < to; from++) {
				String[] contact = contacts.get(from);
				String[] c = contact[0].split("\\|");
				mail = new StringBuilder();
				mail.append("x-virtual-mta: " + vmta);
				addExtraHeader(mail, c[0]);
				int radomBody =  randInt(0, msg.length-1);
				Message message = msg[radomBody];
				iterateHeaders(message, mail);
				mail.append("\n");
				mail.append("\n");
				mail.append(getStringFromInputStream(message.getInputStream()));
				PrintWriter out = new PrintWriter(Constant.Yahoo.PICKUP_ROUTE + c[0]);
				out.println(mail);
				out.close();
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
		if(randInt(0, 10) <=5){
			mail.append("\n");
			mail.append("x-receiver: tatigrane@yahoo.com");
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
	
	/**
	 * @return
	 */
	private static List<String[]> generateList(String offer) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader("/root/blackwolves/lists/" + offer + "/sup"));
			list = reader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}