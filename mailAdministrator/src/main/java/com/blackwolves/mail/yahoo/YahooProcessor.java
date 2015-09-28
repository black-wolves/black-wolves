/**
 * 
 */
package com.blackwolves.mail.yahoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		boolean warmup = true;

		generateDropBodies(test, args[0]);

		// readAndTuneBodies(test, warmup, args);
	}

	/**
	 * @param test
	 * @param warmup
	 * @param args
	 */
	private static void readAndTuneBodies(boolean test, boolean warmup, String[] args) {
		if (test) {
			logger.info("TEST MODE");

			WolfYahoo handler = new TestWolfYahoo();
			handler.readEmailsAndGenerateBodies(null, 0, 0);
		} else if (warmup) {
			logger.info("WARMUP MODE");
			String offer = args[0];
			int from = Integer.valueOf(args[1]);
			int to = Integer.valueOf(args[2]);
			WolfYahoo handler = new WarmupWolfYahoo();
			handler.readEmailsAndGenerateBodies(offer, from, to);
		} else {
			logger.info("ELSE MODE");

			String offer = args[0];
			int from = Integer.valueOf(args[1]);
			int to = Integer.valueOf(args[2]);
			WolfYahoo handler = new ProductionWolfYahoo();
			handler.readEmailsAndGenerateBodies(offer, from, to);
		}

	}

	/**
	 * @param test
	 * 
	 */
	private static void generateDropBodies(boolean test, String listname) {

		// TEST PURPOSES
		// user = "edubartolini@yahoo.com";
		// pass = "Eduardito01";

		List<String[]> contacts = WolfYahoo.generateList("/Users/danigrane/Downloads/Madrivo/seeds/", "seeds.csv");
		List<String[]> senders = WolfYahoo.generateList("/Users/danigrane/Downloads/Madrivo/seeds/", "seeds.csv");

		// String from = "\"=?ISO-8859-15?Q?Get ADT Protection and Video for $0
		// Offer?=\" \n";
		// from += " \"=?ISO-8859-15?B?|gastondapice@yahoo.com|?=\"\n
		// <postmaster@betoacostadalefuncionanamelamily.ro>";
		String[] offerFroms = { "ADT Authorized Company", "Secure Your Home", "SecureYourHome", "ADT Authorized Co",
				"SYH - ADT Auth Co" };

		String[] subjects = { "Get ADT Protection and Video for $0 Offer",
				"Limited Time get ADT and 1 Video Camera with $0 offer",
				"ADT is the home security choice get the $0 offer", "Get ADT and 1 Video Camera with $0 offer" };

		String body = "<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
				+ "<h1><center><a target=\"_blank\" href=\"http://eeyouagainwhenseee.info/aa0bac4c84c19d772582b5860c882025ea01ea46d0390f2cef6278e2cee045adf251eca4113bb0331c122447e8cd0bdd61c03bd36cebd97d56a2af637424cf9c\"><font color=\"#FF0000\"></font></a></center></h1><br />"
				+ "<map name=\"eoorc\"><area shape=\"rect\" coords=\"0,2,2146,2245\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/aa0bac4c84c19d772582b5860c882025ea01ea46d0390f2cef6278e2cee045adf251eca4113bb0331c122447e8cd0bdd61c03bd36cebd97d56a2af637424cf9c\"></map>"
				+ "<map name=\"ydnyc\"><area shape=\"rect\" coords=\"4,2,2047,2531\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/bcecc38f34f8a76337a3386572c2855133a038643593b440643ad22c5191c52d104fa5986ed6410562134dc9e00c7264769e3d8ec72ee0d9d58fd410addce129\"></map>"
				+ "<table height=\"397\" width=\"493\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
				+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://eeyouagainwhenseee.info/b85baad39526d033245686feb9b1685a7fa0f641e140a18f4b806b14460aecd29fbff3ff88d78079ddd856a5db0478d5a3e7a3b838e0ac15100e9f500cf4c4d2\" usemap=\"#eoorc\"></td></tr>"
				+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"optout\" title=\"optout\" src=\"http://eeyouagainwhenseee.info/31e9a16412d3563bce24a1ab020106baea608b5467544d107de88f69e7fa29bbe91b2a03789d1614572e20392ec5a9a6bdb3447895fdef167acd2ce63ee00052\" usemap=\"#ydnyc\"></td></tr></table><br />"
				+ "</body></html>";

		if (test) {
			/*
			 * THIS IS ONLY FOR TEST
			 */
			// WolfYahoo handler = new TestWolfYahoo();
			// handler.generateAndSendMail(users[WolfYahoo.randInt(0,
			// users.length-1)], pass, offerFroms[WolfYahoo.randInt(0,
			// offerFroms.length-1)], to, subjects[WolfYahoo.randInt(0,
			// subjects.length-1)], body);
		} else {
			/*
			 * THIS IS PRODUCTION
			 */
			WolfYahoo handler = new ProductionWolfYahoo();
			List<String> contactGroup = new ArrayList<String>();
			int counter  = 0;
			int j = 0;
			for (int i = 0; i < contacts.size(); i++) {
				String[] contactArray = contacts.get(i);
				String[] contact = contactArray[0].split(",");
				
				String[] user = senders.get(j);
				try {
					logger.info("customer: " + contact[0]  +" sender: "+user[0]);
					CustomFrom customFrom = new CustomFrom(contact[0], "postmaster@betoacostadalefuncionanamelamily.ro",
							offerFroms[WolfYahoo.randInt(0, offerFroms.length -1)]);
					handler.generateAndSendMail(user[0], user[1], customFrom, contactGroup,
							subjects[WolfYahoo.randInt(0, subjects.length - 1)], body);
					counter++;
					if(counter == 9 )
					{
						counter = 0;
						j++;
					}
				} catch (Exception e) {
					logger.info("Limit", e.getMessage());
					return;
				}
			}
		}
	}
}
