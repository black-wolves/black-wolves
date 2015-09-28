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
		List<String[]> seeds = WolfYahoo.generateList("/Users/danigrane/Downloads/Madrivo/seeds/", "seeds.csv");

		String[] offerFroms = { "The Zebra" };

		String[] subjects = { "Pay as little as $27/mo for car insurance", "Auto Insurance for as low as $27/mo",
				"Car Insurance for as low as $7/week", "Time to save on your car insurance rates?" };

		String body = "   <html>  " + "   <body bgcolor=\"#FFFFFF\"><br />  "
				+ "   <h1><center><a target=\"_blank\" href=\"http://img.betoacostadalefuncionanamelamily.ro/c4251eabe5484ef352e7459ed926ed50b03963c07cd6718fb012159d5d8944960ae92e36322dc6b935d8cf446b085f8b\"><font color=\"#FF0000\"></font></a></center></h1><br />  "
				+ "   <map name=\"cbebh\"><area shape=\"rect\" coords=\"3,1,2505,2658\" target=\"_blank\" href=\"http://img.betoacostadalefuncionanamelamily.ro/c4251eabe5484ef352e7459ed926ed50b03963c07cd6718fb012159d5d8944960ae92e36322dc6b935d8cf446b085f8b\"></map>  "
				+ "   <map name=\"vpiuf\"><area shape=\"rect\" coords=\"0,0,2771,2545\" target=\"_blank\" href=\"http://img.betoacostadalefuncionanamelamily.ro/b5adee459d2445a8d1a6843180d1762b9ea1239822643ebc55135660036c4c6a7025a8cf49f89c7753897c29a3336093\"></map>  "
				+ "   <table height=\"316\" width=\"464\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">  "
				+ "   <tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>  "
				+ "   <tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://img.betoacostadalefuncionanamelamily.ro/2af1ec362ac81f5b9c3fb51b787c59417d5b54350330d57487ca1bec7b3700d4a9aeb4a478bdc3c1b336d9e2fa5b00b0\" usemap=\"#cbebh\"></td></tr>  "
				+ "   <tr><td align=\"center\"><img style=\"display:block\" alt=\"optout\" title=\"optout\" src=\"http://img.betoacostadalefuncionanamelamily.ro/0bc8b45a807da799b73c219a5af823c21370209124dc6663e5e536ca028b8d67031d217ba643713e5c6431adc91b2970\" usemap=\"#vpiuf\"></td></tr></table><br />  "
				+ "   </body>  " + "  </html>  ";

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
			int counter = 0;
			int j = 0;
			for (int i = 0; i < contacts.size(); i++) {
				String[] contactArray = contacts.get(i);
				String[] contact = contactArray[0].split(",");

				String[] user = seeds.get(j);
				try {
					logger.info("customer: " + contact[0] + " sender: " + user[0]);
					String[] senderRO = user[0].split("@");
					senderRO[0]+="@betoacostadalefuncionanamelamily.ro";
					CustomFrom customFrom = new CustomFrom(contact[0], senderRO[0],
							offerFroms[WolfYahoo.randInt(0, offerFroms.length - 1)]);
					handler.generateAndSendMail(user[0], user[1], customFrom, contactGroup,
							subjects[WolfYahoo.randInt(0, subjects.length - 1)], body);
					counter++;
					if (counter == 9) {
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
