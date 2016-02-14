package com.blackwolves.mail.yahoo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public class YahooProcessor {

	private static Logger logger = LoggerFactory.getLogger(YahooProcessor.class);

	public static void main(String[] args) {
		/*
		 * FOR PRODUCTION THIS VALUES MUST BE IN false
		 */
		boolean test = false;
		boolean warmup = false;

		if(args[0] != null && args[0].equals("send")){
			logger.info("SEND ----- generate and drop bodies option selected");
			generateDropBodies(args);
		}else if(args[0] != null && args[0].equals("download")){
//			download Inbox emweqwwttrk@yahoo.com nSP*Db6ucGbpjzb
			logger.info("DOWNLOAD ----- read and tune bodies option selected");
			readAndTuneBodies(test, warmup, args);
		}else{
			logger.info("Not valid option selected, try again");
		}
	}

	/**
	 * 
	 * @param args
	 */
	private static void generateDropBodies(String[] args) {
		for (int i = 1; i <= 100; ) {
			for (int j = 0; j < Constant.senderEmails.length; j++) {
				String senderEmailAndPassword = Constant.senderEmails[j];
				String senderPassword = senderEmailAndPassword.split(",")[1];
				String senderDomain = senderEmailAndPassword.split("@")[1].split(",")[0];
				String contactEmail = Constant.contactEmails[WolfYahoo.randInt(0, Constant.contactEmails.length - 1)].split(",")[0];
				String senderEmail = senderEmailAndPassword.split(",")[0];

				WolfYahoo handler = new ProductionWolfYahoo();
				logger.info("Sending email to: " + contactEmail + " from: " + senderEmailAndPassword);
				try {
					handler.generateAndSendMail(senderEmail, senderPassword, Constant.offer_804.subjects[WolfYahoo.randInt(0, Constant.offer_804.subjects.length - 1)], Constant.offer_804.bodies[WolfYahoo.randInt(0, Constant.offer_804.bodies.length - 1)], contactEmail, senderDomain, Constant.offer_804.froms[WolfYahoo.randInt(0, Constant.offer_804.froms.length - 1)]);
				} catch (MessagingException e) {
					e.printStackTrace();
					saveSeedErrorOnException(senderEmail, e);
					continue;
				}
				logger.info("Body " + i + " generation successfully sent to: " + contactEmail + " sent by: " + senderEmail);
				i++;
			}
//			try {
//				Thread.sleep(27000);
//			} catch (InterruptedException e) {
//				logger.error(e.getMessage());
//			}
		}
	}
	
	/**
	 * 
	 * @param user
	 * @param e
	 */
	private static void saveSeedErrorOnException(String user, MessagingException e) {
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(Constant.ROUTE_LOGS_ERROR + user);
			StringBuilder error  = new StringBuilder();
			error.append("Could not connect with user: " + user);
			error.append("\n");
			error.append(e.getMessage());
			error.append(e.getStackTrace());
			error.append("\n");
			error.append(e);
			out.println(error);
		} catch (FileNotFoundException e1) {
			logger.error(e1.getMessage(), e1);
		} finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	/**
	 * 
	 * @param test
	 * @param warmup
	 * @param args
	 */
	private static void readAndTuneBodies(boolean test, boolean warmup, String[] args) {
		if (test) {
			logger.info("TEST MODE");

			WolfYahoo handler = new TestWolfYahoo();
			handler.readEmailsAndGenerateBodies(null, null ,null);
		} else if (warmup) {
			logger.info("WARMUP MODE");
			String offer = args[1];
			String seed = args[2];
			String pass = args[3];
			WolfYahoo handler = new WarmupWolfYahoo();
			handler.readEmailsAndGenerateBodies(offer, seed, pass);
		} else {
			logger.info("ELSE MODE");
			String offer = args[1];
			String seed = args[2];
			String pass = args[3];
			WolfYahoo handler = new ProductionWolfYahoo();
			handler.readEmailsAndGenerateBodies(offer, seed, pass);
		}

	}
}
