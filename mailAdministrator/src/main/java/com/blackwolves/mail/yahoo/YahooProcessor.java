package com.blackwolves.mail.yahoo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.mail.AuthenticationFailedException;
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

		if(args[0] != null && args[0].equals("send")){
			logger.info("SEND ----- generate and drop bodies option selected");
			generateAndSendEmail(args);
		}else if(args[0] != null && args[0].equals("download")){
			logger.info("DOWNLOAD ----- read and tune bodies option selected");
			downloadAndGenerateDropBodies(args);
		}else{
			logger.info("Not valid option selected, try again");
		}
	}

	/**
	 * 
	 * @param args
	 */
	private static void generateAndSendEmail(String[] args) {
		for (int i = 1; i <= 100; ) {  
			for (int j = 0; j < Constant.senderEmails.length ; j++) {
				String senderEmailAndPassword = Constant.senderEmails[j];
				String senderPassword = senderEmailAndPassword.split(",")[1];
				String senderDomain = senderEmailAndPassword.split("@")[1].split(",")[0];
				String contactEmail = Constant.contactEmails[SendWolfYahoo.randInt(0, Constant.contactEmails.length - 1)].split(",")[0];
				String senderEmail = senderEmailAndPassword.split(",")[0];

				WolfYahoo handler = new SendWolfYahoo();
				logger.info("Sending email to: " + contactEmail + " from: " + senderEmailAndPassword);
				try {
					handler.generateAndSendEmail(senderEmail, senderPassword, Constant.offer_1607.subjects[SendWolfYahoo.randInt(0, Constant.offer_1607.subjects.length - 1)], Constant.offer_1607.bodies[SendWolfYahoo.randInt(0, Constant.offer_1607.bodies.length - 1)], contactEmail, senderDomain, Constant.offer_1607.froms[SendWolfYahoo.randInt(0, Constant.offer_1607.froms.length - 1)]);
				}catch (AuthenticationFailedException e) {
					e.printStackTrace();
					saveSeedErrorOnException(senderEmail, e);
					continue;
				} catch (MessagingException e) {
					e.printStackTrace();
					saveSeedErrorOnException(senderEmail, e);
					continue;
				} 
				logger.info("Body " + i + " generation successfully sent to: " + contactEmail + " sent by: " + senderEmail);
				i++;
			}
//			String senderEmailAndPassword = Constant.senderEmails[0];
//			String senderPassword = senderEmailAndPassword.split(",")[1];
//			String senderDomain = senderEmailAndPassword.split("@")[1].split(",")[0];
//			String contactEmail = Constant.contactEmails[0].split(",")[0];
//			String senderEmail = senderEmailAndPassword.split(",")[0];
//
//			try {
//				WolfYahoo handler = new SendWolfYahoo();
//				logger.info("Sending email to: " + contactEmail + " from: " + senderEmailAndPassword);
//				handler.generateAndSendEmail(senderEmail, senderPassword, Constant.offer_1148.subjects[SendWolfYahoo.randInt(0, Constant.offer_1148.subjects.length - 1)], Constant.offer_1148.bodies[SendWolfYahoo.randInt(1, Constant.offer_1148.bodies.length - 1)], contactEmail, senderDomain, Constant.offer_1148.froms[SendWolfYahoo.randInt(0, Constant.offer_1148.froms.length - 1)]);
//			}catch (AuthenticationFailedException e) {
//				e.printStackTrace();
//				saveSeedErrorOnException(senderEmail, e);
//				continue;
//			} catch (MessagingException e) {
//				e.printStackTrace();
//				saveSeedErrorOnException(senderEmail, e);
//				continue;
//			} 
//			logger.info("Body " + i + " generation successfully sent to: " + contactEmail + " sent by: " + senderEmail);
//			i++;
		}
	}
	
	/**
	 * 
	 * @param args
	 */
	private static void downloadAndGenerateDropBodies(String[] args) {
		String offer = args[1];
		String seed = args[2].split(",")[0];
		String pass = args[2].split(",")[1];
		WolfYahoo handler = new SendWolfYahoo();
		handler.downloadAndGenerateDropBodies(offer, seed, pass);

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
}
