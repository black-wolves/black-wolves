/**
 * 
 */
package com.blackwolves.mail.yahoo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author gastondapice
 *
 */
public class YahooProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(YahooProcessor.class);

	public static void main(String[] args) {
		
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
		
		String[] offerFroms = {"ADT Authorized Company","Secure Your Home","SecureYourHome","ADT Authorized Co","SYH - ADT Auth Co"};
		
		String to = "yaninadefays02@yahoo.com";
		
		String [] subjects  =  {"Get ADT Protection and Video for $0 Offer","Limited Time get ADT and 1 Video Camera with $0 offer","ADT is the home security choice get the $0 offer","Get ADT and 1 Video Camera with $0 offer"};
		
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
		
		for (int i = 0; i < users.length; i++) {
			for(int j = 0; j < subjects.length; j ++){
				for(int k = 0; k < subjects.length; k ++){
					SendFromYahoo.getInstance().generateAndSendMail(users[i], pass, offerFroms[j], to, subjects[k], body);
				}
			}
		} 
	}

	/**
	 * @return
	 */
	private static List<String[]> generateList(String route, String file) {
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
}
