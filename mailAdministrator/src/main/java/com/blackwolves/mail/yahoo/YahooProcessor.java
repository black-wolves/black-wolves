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
		String domain = args[1];
		String offer = args[2];
		for (int i = 1; i <= 100; ) {  
			for (int j = 0; j < senderEmailsSelector(domain).length ; j++) {
				String senderEmailAndPassword = senderEmailsSelector(domain)[j];
				String senderPassword = senderEmailAndPassword.split(",")[1];
				String senderDomain = senderEmailAndPassword.split("@")[1].split(",")[0];
				String contactEmail = contactEmailsSelector(domain)[SendWolfYahoo.randInt(0, contactEmailsSelector(domain).length - 1)].split(",")[0];
				String senderEmail = senderEmailAndPassword.split(",")[0];

				WolfYahoo handler = new SendWolfYahoo();
				logger.info("Sending email to: " + contactEmail + " from: " + senderEmailAndPassword);
				try {
					handler.generateAndSendEmail(senderEmail, senderPassword, subjectsOfferSelector(offer)[SendWolfYahoo.randInt(0, subjectsOfferSelector(offer).length - 1)], bodiesOfferSelector(offer)[SendWolfYahoo.randInt(0, bodiesOfferSelector(offer).length - 1)], contactEmail, senderDomain, fromsOfferSelector(offer)[SendWolfYahoo.randInt(0, fromsOfferSelector(offer).length - 1)]);
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
//				handler.generateAndSendEmail(senderEmail, senderPassword, subjectsOfferSelector(offer)[SendWolfYahoo.randInt(0, subjectsOfferSelector(offer).length - 1)], bodiesOfferSelector[SendWolfYahoo.randInt(1, bodiesOfferSelector.length - 1)], contactEmail, senderDomain, fromsOfferSelector(offer)[SendWolfYahoo.randInt(0, fromsOfferSelector(offer).length - 1)]);
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
	 * @param offer 
	 * @return
	 */
	private static String[] fromsOfferSelector(String offer) {
		switch(offer) {
		    case Constant.Offer._804.number:
		    	return Constant.Offer._804.froms;
		    case Constant.Offer._1148.number:
		    	return Constant.Offer._1148.froms;
		    case Constant.Offer._1607.number:
		    	return Constant.Offer._1607.froms;
		    case Constant.Offer._1654.number:
		    	return Constant.Offer._1654.froms;
		    case Constant.Offer._1553.number:
		    	return Constant.Offer._1553.froms;
		}
		return null;
	}

	/**
	 * @param offer 
	 * @return
	 */
	private static String[] bodiesOfferSelector(String offer) {
		switch(offer) {
	    case Constant.Offer._804.number:
	    	return Constant.Offer._804.bodies;
	    case Constant.Offer._1148.number:
	    	return Constant.Offer._1148.bodies;
	    case Constant.Offer._1607.number:
	    	return Constant.Offer._1607.bodies;
	    case Constant.Offer._1654.number:
	    	return Constant.Offer._1654.bodies;
	    case Constant.Offer._1553.number:
	    	return Constant.Offer._1553.bodies;
	}
	return null;
	}

	/**
	 * @param offer 
	 * @return
	 */
	private static String[] subjectsOfferSelector(String offer) {
		switch(offer) {
	    case Constant.Offer._804.number:
	    	return Constant.Offer._804.subjects;
	    case Constant.Offer._1148.number:
	    	return Constant.Offer._1148.subjects;
	    case Constant.Offer._1607.number:
	    	return Constant.Offer._1607.subjects;
	    case Constant.Offer._1654.number:
	    	return Constant.Offer._1654.subjects;
	    case Constant.Offer._1553.number:
	    	return Constant.Offer._1553.subjects;
	}
	return null;
	}

	/**
	 * @param domain 
	 * @return
	 */
	private static String[] contactEmailsSelector(String domain) {
		switch(domain) {
	    case Constant.domains.unacervezarafaga:
	    	return Constant.contactEmails.unacervezarafaga_com;
	    case Constant.domains.austroyed:
	    	return Constant.contactEmails.austroyed_info;
		}
		return null;
	}

	/**
	 * @param domain 
	 * @return
	 */
	private static String[] senderEmailsSelector(String domain) {
		switch(domain) {
	    case Constant.domains.unacervezarafaga:
	    	return Constant.senderEmails.unacervezarafaga_com;
	    case Constant.domains.austroyed:
	    	return Constant.senderEmails.austroyed_info;
		}
		return null;
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
