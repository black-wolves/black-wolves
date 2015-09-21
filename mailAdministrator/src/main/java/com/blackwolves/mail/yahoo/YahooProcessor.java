/**
 * 
 */
package com.blackwolves.mail.yahoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		
//		readAndTuneBodies(test, warmup, args);
	}

	/**
	 * @param test 
	 * @param warmup 
	 * @param args
	 */
	private static void readAndTuneBodies(boolean test, boolean warmup, String[] args) {
		if(test){
			logger.info("TEST MODE");

			WolfYahoo handler = new TestWolfYahoo();
			handler.readEmailsAndGenerateBodies(null, 0, 0);
		}else if(warmup){
			logger.info("WARMUP MODE");
			String offer = args[0];
			int from = Integer.valueOf(args[1]);
			int to = Integer.valueOf(args[2]);
			WolfYahoo handler = new WarmupWolfYahoo();
			handler.readEmailsAndGenerateBodies(offer, from, to);
		}else{
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
	private static void generateDropBodies(boolean test, String list) {
		String pass = "wolf2015.2";
		
		String[] users = { "gaston@thewolvesareback.info",
				"peaches@thewolvesareback.info",
				"riquelme@thewolvesareback.info",
				"palermo@thewolvesareback.info",
				"guillermo@thewolvesareback.info",
				"alberto@thewolvesareback.info",
				"sales1@thewolvesareback.info", "sales2@thewolvesareback.info",
				"sales3@thewolvesareback.info", "sales4@thewolvesareback.info",
				"sales5@thewolvesareback.info", "sales6@thewolvesareback.info",
				"sales7@thewolvesareback.info", "sales8@thewolvesareback.info",
				"sales9@thewolvesareback.info", "sales10@thewolvesareback.info",
				"sales11@thewolvesareback.info", "sales12@thewolvesareback.info",
				"sales13@thewolvesareback.info", "sales14@thewolvesareback.info",
				"sales15@thewolvesareback.info", "sales16@thewolvesareback.info",
				"sales17@thewolvesareback.info", "sales18@thewolvesareback.info",
				"sales19@thewolvesareback.info", "sales20@thewolvesareback.info",};
		
		
		List<String[]> contacts;
		contacts = WolfYahoo.generateList("/root/blackwolves/lists/" , list);
		
		
		String[] offerFroms = {"ADT Authorized Company","Secure Your Home","SecureYourHome","ADT Authorized Co","SYH - ADT Auth Co"};
		
//		String[] offerFroms = {"The Zebra"};
		
		String[] subjects  =  {"Get ADT Protection and Video for $0 Offer","Limited Time get ADT and 1 Video Camera with $0 offer","ADT is the home security choice get the $0 offer","Get ADT and 1 Video Camera with $0 offer"};
		
//		String[] subjects = {"Pay as little as $27/mo for car insurance","Auto Insurance for as low as $27/mo","Car Insurance for as low as $27/mo","Car Insurance for as low as $7/week","Auto Insurance for as low as $7/wk","Time to save on your car insurance rates?","Compare Car Insurance in Real Time","Time to re-shop your car insurance rates","You're eligible to save on car insurance","Car insurance starting at $27/month?"};		

		String body = "<html>" +
						"<body bgcolor=\"#FFFFFF\"><br />" +
						"<h1><center><a target=\"_blank\" href=\"http://eeyouagainwhenseee.info/aa0bac4c84c19d772582b5860c882025ea01ea46d0390f2cef6278e2cee045adf251eca4113bb0331c122447e8cd0bdd61c03bd36cebd97d56a2af637424cf9c\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
						"<map name=\"eoorc\"><area shape=\"rect\" coords=\"0,2,2146,2245\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/aa0bac4c84c19d772582b5860c882025ea01ea46d0390f2cef6278e2cee045adf251eca4113bb0331c122447e8cd0bdd61c03bd36cebd97d56a2af637424cf9c\"></map>" +
						"<map name=\"ydnyc\"><area shape=\"rect\" coords=\"4,2,2047,2531\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/bcecc38f34f8a76337a3386572c2855133a038643593b440643ad22c5191c52d104fa5986ed6410562134dc9e00c7264769e3d8ec72ee0d9d58fd410addce129\"></map>" +
						"<table height=\"397\" width=\"493\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
						"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
						"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://eeyouagainwhenseee.info/b85baad39526d033245686feb9b1685a7fa0f641e140a18f4b806b14460aecd29fbff3ff88d78079ddd856a5db0478d5a3e7a3b838e0ac15100e9f500cf4c4d2\" usemap=\"#eoorc\"></td></tr>" +
						"<tr><td align=\"center\"><img style=\"display:block\" alt=\"optout\" title=\"optout\" src=\"http://eeyouagainwhenseee.info/31e9a16412d3563bce24a1ab020106baea608b5467544d107de88f69e7fa29bbe91b2a03789d1614572e20392ec5a9a6bdb3447895fdef167acd2ce63ee00052\" usemap=\"#ydnyc\"></td></tr></table><br />" +
						"</body></html>";
		
//		String body = "<html>" +
//						"<body bgcolor=\"#FFFFFF\"><br />" +
//						"<h1><center><a target=\"_blank\" href=\"http://eeyouagainwhenseee.info/c1d5d3402176da7391fe5ed4bca4eed692a297ced8ab9821206beb48106deb3cef19ff8dbb06380eee846b94858afc65ca5f2cfa0bb633293767b93eb0d22afe\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
//						"<map name=\"aulez\"><area shape=\"rect\" coords=\"0,1,2671,2768\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/c1d5d3402176da7391fe5ed4bca4eed692a297ced8ab9821206beb48106deb3cef19ff8dbb06380eee846b94858afc65ca5f2cfa0bb633293767b93eb0d22afe\"></map>" +
//						"<map name=\"ytmus\"><area shape=\"rect\" coords=\"4,4,2060,2948\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/963eab6bbd98309675957dc8dc52b8e64117f141a214e993610a53ed173f276ae894c5cf2001f2b5403f65af7b932d3b59ac130af85f20fcc70719feaed746ad\"></map>" +
//						"<table height=\"392\" width=\"420\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
//						"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
//						"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://eeyouagainwhenseee.info/272c6df07eee0830dfc9f3be146cd62d03390fab7c001ec75aee7129af34e0eb4f140897d6d0f2fa3e94f473ad61725ecc911f1fccd0385fa89ae02760ba1b0a\" usemap=\"#aulez\"></td></tr>" +
//						"<tr><td align=\"center\"><img style=\"display:block\" alt=\"optout\" title=\"optout\" src=\"http://eeyouagainwhenseee.info/1cc52a86671cfc43c9b06f266bd484a31da92b56905b6f5d60ad150f306657288530a7b6a963d3aac3f67f00cf8eb45fb94c8a79ccdf62ae3d331f74b69e4f04\" usemap=\"#ytmus\"></td></tr></table><br />" +
//						"</body>" +
//						"</html>";
		
		if(test){
			/*
			 * THIS IS ONLY FOR TEST
			 */
		//	WolfYahoo handler = new TestWolfYahoo();
		//	handler.generateAndSendMail(users[WolfYahoo.randInt(0, users.length-1)], pass, offerFroms[WolfYahoo.randInt(0, offerFroms.length-1)], to, subjects[WolfYahoo.randInt(0, subjects.length-1)], body);
		}else{
			/*
			 * THIS IS PRODUCTION
			 */
			WolfYahoo handler = new ProductionWolfYahoo();
//			for (int i = 0; i < users.length; i++) {
//				for(int j = 0; j < offerFroms.length; j ++){
//					for(int k = 0; k < subjects.length; k ++){
//						handler.generateAndSendMail(users[i], pass, offerFroms[j], to, subjects[k], body);
//					}
//				}
//			}
			int i = 0;
			List <String> contactGroup = new ArrayList<String>();
			for(String []contact : contacts){
				String[] c = contact[0].split("\\|");
				contactGroup.add(c[0]);
				logger.info("Sending to:" + c.toString());
				logger.info(Integer.toString(i++));
				if(contactGroup.size() == 3)
				{
					handler.generateAndSendMail(users[WolfYahoo.randInt(0, users.length-1)], pass, offerFroms[WolfYahoo.randInt(0, offerFroms.length-1)], contactGroup, subjects[WolfYahoo.randInt(0, subjects.length-1)], body);
					contactGroup.clear();
				}	
			}
		}
	}
}
