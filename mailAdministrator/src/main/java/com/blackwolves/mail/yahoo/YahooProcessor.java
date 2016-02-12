package com.blackwolves.mail.yahoo;

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
		boolean warmup = false;

		if(args[0] != null && args[0].equals("send")){
			logger.info("SEND ----- generate and drop bodies option selected");
			generateDropBodies(test, args);
		}else if(args[0] != null && args[0].equals("download")){
//			download Inbox emweqwwttrk@yahoo.com nSP*Db6ucGbpjzb
			logger.info("DOWNLOAD ----- read and tune bodies option selected");
			readAndTuneBodies(test, warmup, args);
		}else{
			logger.info("Not valid option selected, try again");
		}
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

	/**
	 * @param test
	 * 
	 */
	private static void generateDropBodies(boolean test, String[] args) {

		// TEST PURPOSES
		// user = "edubartolini@yahoo.com";
		// pass = "Eduardito01";

		// List<String[]> contacts =
		// WolfYahoo.generateList("/Users/danigrane/Downloads/Madrivo/seeds/",
		// "mini_zebra.csv");
		// List<String[]> seeds =
		// WolfYahoo.generateList("/Users/danigrane/Downloads/Madrivo/seeds/",
		// "seeds_good.csv");
		
		String senderEmail = args[1].split(",")[0];
		String senderPassword = args[1].split(",")[1];
		String senderDomainRo = args[1].split(",")[2];
		String contactEmail = args[2];

		String[] offerFroms = { "The HARP Lenders" };
		
//		String[] offerFroms = { "The Zebra" };
		
//		String[] subjects = { "Pay as little as $27/mo for car insurance",
//				"Auto Insurance for as low as $27/mo",
//				"Car Insurance for as low as $27/mo",
//				"Car Insurance for as low as $7/week",
//				"Auto Insurance for as low as $7/wk",
//				"Time to save on your car insurance rates?",
//				"Compare Car Insurance in Real Time",
//				"Time to re-shop your car insurance rates",
//				"You're eligible to save on car insurance" };
		
		String[] subjects = { "ARP 2.0 Refinance Program Extended Until 2016 - Act Before Rates Go Up"};

//		String body = "<html><body>Hello World</body></html>";
		
		String body = "<html>" +
		"<body bgcolor=\"#FFFFFF\"><br />" +
		"<h1><center><a target=\"_blank\" href=\"http://geneveral.info/2eefc6e61b5c886a475f1ede161d2039134d9aec45571392f005f6f41ab29810c0dd3848215d2ef110183a725a88763c29caf10e0165c54160f4bec64479ff9b\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
		"<map name=\"ihawg\"><area shape=\"rect\" coords=\"4,1,2014,2576\" target=\"_blank\" href=\"http://geneveral.info/2eefc6e61b5c886a475f1ede161d2039134d9aec45571392f005f6f41ab29810c0dd3848215d2ef110183a725a88763c29caf10e0165c54160f4bec64479ff9b\"></map>" +
		"<map name=\"vwykz\"><area shape=\"rect\" coords=\"4,2,2462,2435\" target=\"_blank\" href=\"http://geneveral.info/e370e9e1ec6a1dd6bd475af4ebe7a9bc64e930386adf309d3b60f7d7b3935a772854c6f76a2f30e7fdf2f9ad3dad6900a75a77d351e18afdc26825394621fe26\"></map>" +
		"<table height=\"368\" width=\"419\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
		"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
		"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://geneveral.info/4a31ddc604ea45b58e0f203b61a62d7196a30d61327c8d0114e070440a4150c03f4dcbec72620d248632b681aa50ded215169cb599c0346d28b6f2308e53d6cd\" usemap=\"#ihawg\"></td></tr>" +
		"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe\" title=\"unsubscribe\" src=\"http://geneveral.info/f3a39bcfeb6585aacc44470cbb9b2177d1ab912c5625b77b5128efd049932048de85006a272a7d346917399fbd4b8ee2f08c47a87247a1f9e1ab1aef3bfb8e7d\" usemap=\"#vwykz\"></td></tr></table><br />" +
		"</body>" +
		"</html>";

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
			try {
				logger.info("customer: " + contactEmail + " sender: " + senderEmail);
				handler.generateAndSendMail(senderEmail, senderPassword, subjects[WolfYahoo.randInt(0, subjects.length - 1)], body, contactEmail, senderDomainRo, offerFroms[WolfYahoo.randInt(0, offerFroms.length - 1)]);

			} catch (Exception e) {
				logger.info("Error", e.getMessage());
				return;
			}
		}
	}
}
