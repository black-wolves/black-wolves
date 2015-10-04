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

//		generateDropBodies(test, args[0], args[1]);

		 readAndTuneBodies(test, warmup, args);
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
			handler.readEmailsAndGenerateBodies(null);
		} else if (warmup) {
			logger.info("WARMUP MODE");
			String offer = args[0];
			WolfYahoo handler = new WarmupWolfYahoo();
			handler.readEmailsAndGenerateBodies(offer);
		} else {
			logger.info("ELSE MODE");
			String offer = args[0];
			WolfYahoo handler = new ProductionWolfYahoo();
			handler.readEmailsAndGenerateBodies(offer);
		}

	}

	/**
	 * @param test
	 * 
	 */
	private static void generateDropBodies(boolean test, String senderEmailAndPassword, String contactEmail) {

		// TEST PURPOSES
		// user = "edubartolini@yahoo.com";
		// pass = "Eduardito01";

		// List<String[]> contacts =
		// WolfYahoo.generateList("/Users/danigrane/Downloads/Madrivo/seeds/",
		// "mini_zebra.csv");
		// List<String[]> seeds =
		// WolfYahoo.generateList("/Users/danigrane/Downloads/Madrivo/seeds/",
		// "seeds_good.csv");
		
		String senderEmail = senderEmailAndPassword.split(",")[0];
		String senderPassword = senderEmailAndPassword.split(",")[1];

//		String[] offerFroms = { "The Zebra" };
		
		String[] offerFroms = { "Military.com" };
		
//		String[] offerFroms = { "From the from" };

//		String[] subjects = { "Pay as little as $27/mo for car insurance",
//				"Auto Insurance for as low as $27/mo",
//				"Car Insurance for as low as $27/mo",
//				"Car Insurance for as low as $7/week",
//				"Auto Insurance for as low as $7/wk",
//				"Time to save on your car insurance rates?",
//				"Compare Car Insurance in Real Time",
//				"Time to re-shop your car insurance rates",
//				"You're eligible to save on car insurance" };
		
		String[] subjects = { "Veterans Don't Need 20% Down",
				"Get up to $417k through your VA Home Loan",
				"Lock in a Low Rate with your VA Loan",
				"Buy a Home - $0 Down Secure Your VA Loan Today",
				"Use Your VA Loan Benefit   $0 Down and Get up to $417k",
				"VA Loan  Receive FREE no  obligation rate quotes",
				"VA Loan - $0 Down and secure your loan today!" };
		
//		String[] subjects = { "Get up to $417k through your VA Home Loan" };
		
//		String[] subjects = { "subject the subjec"};

//		String body = "<html><body bgcolor=\"#FFFFFF\"><br /><h1><center><a target=\"_blank\" href=\"http://img.betoacostadalefuncionanamelamily.ro/ecb85094cae9f7db3dd1bf283f2406e7dc3f1d2712face818a03b4438348ac85d4cc555e66b25add937ef7434cee36f6\"><font color=\"#FF0000\"></font></a></center></h1><br /><map name=\"tfmeb\"><area shape=\"rect\" coords=\"4,1,2213,2551\" target=\"_blank\" href=\"http://img.betoacostadalefuncionanamelamily.ro/ecb85094cae9f7db3dd1bf283f2406e7dc3f1d2712face818a03b4438348ac85d4cc555e66b25add937ef7434cee36f6\"></map><map name=\"eknww\"><area shape=\"rect\" coords=\"2,2,2327,2317\" target=\"_blank\" href=\"http://img.betoacostadalefuncionanamelamily.ro/93cc8d0d56a0e104722e4740e4b03c529efb5b0a385359af8cf3ad376ed734d70c9f8eec2852481f76998d1f0ef9dae2\"></map><table height=\"384\" width=\"446\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\"><tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr><tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://img.betoacostadalefuncionanamelamily.ro/620daa263754bd3ac41416f46c330671102826c866e77945c9bb4c20c0f23e5b1701964e2ef93e023332b0d4c698012c\" usemap=\"#tfmeb\"></td></tr><tr><td align=\"center\"><img style=\"display:block\" alt=\"optout\" title=\"optout\" src=\"http://img.betoacostadalefuncionanamelamily.ro/36f64d8b1a3f59494a4a4451613eaef706cdb838fb21885edbcd93a1781226a4993d6776d1324891dd7afbe685f612d7\" usemap=\"#eknww\"></td></tr></table><br /></body></html>";
		
		String body = "<html>		<body bgcolor=\"#FFFFFF\"><br />		<h1><center><a target=\"_blank\" href=\"http://img.dmacareanaalefuncionanamelamance.ro/fdd4b2ad8e8be35868f26a1dec1257713b32f3983a7de1d7d632bdd75e3aafc5af9d256510a4347ad355024c50a07d2f\"><font color=\"#FF0000\"></font></a></center></h1><br />		<map name=\"mtpcc\"><area shape=\"rect\" coords=\"2,3,2717,2260\" target=\"_blank\" href=\"http://img.dmacareanaalefuncionanamelamance.ro/fdd4b2ad8e8be35868f26a1dec1257713b32f3983a7de1d7d632bdd75e3aafc5af9d256510a4347ad355024c50a07d2f\"></map>		<map name=\"kufps\"><area shape=\"rect\" coords=\"4,4,2075,2916\" target=\"_blank\" href=\"http://img.dmacareanaalefuncionanamelamance.ro/d3fdddbb5cbf0ce60060a61c453b9df16f45626f816eff3d3d44e8892f984a970a3cc682a71720fcc8ddcb310a354f79\"></map>		<table height=\"374\" width=\"457\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">		<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>		<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://img.dmacareanaalefuncionanamelamance.ro/d0a45b2aad159559871942c9ba2c4f41e42946e8b275d9ec4e98f40f15209fac64925dddeae59e5643dfbdef81fcbbe9\" usemap=\"#mtpcc\"></td></tr>		<tr><td align=\"center\"><img style=\"display:block\" alt=\"optout\" title=\"optout\" src=\"http://img.dmacareanaalefuncionanamelamance.ro/07a642d9a3eebfea354581f260fbc61ff2b450c60c899302ffac018d981dcf15591107fb1d673632501aeed4d0361755\" usemap=\"#kufps\"></td></tr></table><br />		</body>		</html>";		

//		String body = "<html><body>Hello World</body></html>";
//		String body = "";

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
				String[] senderRO = senderEmail.split("@");
				senderRO[0] += "@betoacostadalefuncionanamelamily.ro";
//				senderRO[0] += "@dmacareanaalefuncionanamelamance.ro";
				CustomFrom customFrom = new CustomFrom(contactEmail, senderRO[0], offerFroms[WolfYahoo.randInt(0, offerFroms.length - 1)]);
				handler.generateAndSendMail(senderEmail, senderPassword, customFrom, subjects[WolfYahoo.randInt(0, subjects.length - 1)], body);

			} catch (Exception e) {
				logger.info("Error", e.getMessage());
				return;
			}
		}
	}
}
