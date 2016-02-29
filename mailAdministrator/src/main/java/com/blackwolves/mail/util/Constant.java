package com.blackwolves.mail.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public interface Constant {

	public static final String ROUTE = "/var/www/";
	public static final String ROUTE_LOGS_ERROR = "/var/www/logs/errors/";
	public static final String EMPTY_STRING = "";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String BLANK_SPACE = " ";
	public static final String VMTA = "vps-yahoo";
	public static String randomString = (UUID.randomUUID() + new SimpleDateFormat("MMddyyyy").format(new Date()))
			.replaceAll("-", "");

	public interface Yahoo {
		public static final String IMAP_YAHOO = "imap.mail.yahoo.com";
		public static final int IMAP_PORT = 993;
		public static final String PICKUP_ROUTE = "/ramcache/pmta/pickup/";
		public static final String BLACKWOLVES_ROUTE = "/var/www/bodies/";
		public static final String INBOX = "Inbox";
		public static final String SPAM = "Bulk Mail";
		public static final String BODIES = "Bodies";

		public static final String HOST = "smtp.mail.yahoo.com";
		public static final String PORT = "587";
		public static final String CONTENT_TYPE = "text/html; charset=utf-8";
		public static final String CONTENT_TRANSFER_ENCODING = "7bit";
	}
	
	public interface domains{
		public static final String unacervezarafaga = "unacervezarafaga.com";
		public static final String austroyed = "austroyed.info";
	}

	public interface senderEmails{
		
		public static final String[] unacervezarafaga_com = {
			 "susana.aronov@unacervezarafaga.com,wolf2015.",
			 "dtmxvvktje@unacervezarafaga.com,wolf2015.",
			 "panjemthy@unacervezarafaga.com,wolf2015.",
			 "qgunprmjde@unacervezarafaga.com,wolf2015.",
			 "cpscdxrha@unacervezarafaga.com,wolf2015.",
			 "rzzdyyut@unacervezarafaga.com,wolf2015.",
			"xtfvqav@unacervezarafaga.com,wolf2015.",
			"zdhbkvhm@unacervezarafaga.com,wolf2015.",
			"vnsmuilcv@unacervezarafaga.com,wolf2015.",
			"efnaq@unacervezarafaga.com,wolf2015.",
		};
		public static final String[] austroyed_info = {
			"pedro.vicco@austroyed.info,wolf2015.2",
			"ykfzfg@austroyed.info,wolf2015.1",
			"pbjpbunb@austroyed.info,wolf2015.1",
			"dlvszwwqxsg@austroyed.info,wolf2015.2",
			"qldqlrlanpt@austroyed.info,wolf2015.2",
			"ewcppqfts@austroyed.info,wolf2015.",
			"evuhoiheske@austroyed.info,wolf2015.",
			"tnaup@austroyed.info,wolf2015.",
			"frcgevpgoeo@austroyed.info,wolf2015.",
			"smbidnklmz@austroyed.info,wolf2015.",
			"sddxjaiye@austroyed.info,wolf2015.",
			"icdzwvn@austroyed.info,wolf2015.",
			"kbbivek@austroyed.info,wolf2015.",
			"vwctiphvxo@austroyed.info,wolf2015.",
			"lxsgknxykhq@austroyed.info,wolf2015." 
		};
	}
	public interface contactEmails{
		public static final String[] unacervezarafaga_com = {
			"offers-1@unacervezarafaga.com,wolf2015.",
			"offers-2@unacervezarafaga.com,wolf2015.",
			"offers-3@unacervezarafaga.com,wolf2015.",
			"offers-4@unacervezarafaga.com,wolf2015.",
			"offers-5@unacervezarafaga.com,wolf2015." 
		};
		public static final String[] austroyed_info = {
			"offers-1@austroyed.info,wolf2015.",
			"offers-2@austroyed.info,wolf2015.",
			"offers-3@austroyed.info,wolf2015.",
			"offers-4@austroyed.info,wolf2015.",
			"offers-5@austroyed.info,wolf2015."
		};

	}
	public interface newsletter {
		public static final String[] froms = { "The Cool Info" };

		public static final String[] subjects = { "Do you know what \"SATURNALIA\" is?" };

		public static final String[] bodies = {
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">" + "<head>"
						+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
						+ "	<!--[if !mso]><!-->" + "		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />"
						+ "	<!--<![endif]-->"
						+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
						+ "	<title>The Cool Info</title>" + "	<style type=\"text/css\">"
						+ "		div, p, a, li, td { " + "			-webkit-text-size-adjust:none;" + "		}"
						+ "		p {" + "			font-size: 14px;" + "			Margin-bottom: 10px;" + "		}"
						+ "		body {" + "			Margin: 0;" + "			padding: 0;"
						+ "			min-width: 100%;" + "			background-color: #ffffff;" + "		}"
						+ "		table {" + "			border-spacing: 0;" + "			font-family: sans-serif;"
						+ "			color: #333333;" + "		}" + "		td {" + "			padding: 0;"
						+ "		}" + "		img {" + "			border: 0;" + "		}" + "		a {"
						+ "			border-radius:3px;" + "		}" + "		.wrapper {" + "			width: 100%;"
						+ "			table-layout: fixed;" + "			-webkit-text-size-adjust: 100%;"
						+ "			-ms-text-size-adjust: 100%;" + "		}" + "		.webkit {"
						+ "			max-width: 600px;" + "		}" + "		.outer {" + "			Margin: 0 auto;"
						+ "			width: 100%;" + "			max-width: 600px;"
						+ "			-webkit-box-shadow: inset 0px -1px 5px 0px rgba(0,0,0,0.75);"
						+ "			-moz-box-shadow: inset 0px -1px 5px 0px rgba(0,0,0,0.75);"
						+ "			box-shadow: inset 0px -1px 5px 0px rgba(0,0,0,0.75);" + "		}"
						+ "		.full-width-image img {" + "			width: 100%;" + "			max-width: 600px;"
						+ "			height: auto;" + "		}" + "		.inner {" + "			padding: 20px;"
						+ "		}" + "		a {" + "			color: #ee6a56;"
						+ "			text-decoration: underline;" + "		}" + "		.h1 {"
						+ "			font-size: 21px;" + "			font-weight: bold;"
						+ "			Margin-bottom: 18px;" + "		}" + "		" + "		.one-column .contents {"
						+ "			text-align: center;" + "		}" + "		.two-column {"
						+ "			text-align: center;" + "			font-size: 0;" + "		}"
						+ "		.two-column .column {" + "			width: 100%;" + "			max-width: 300px;"
						+ "			display: inline-block;" + "			vertical-align: top;" + "		}"
						+ "		.contents {" + "			width: 100%;" + "		}" + "		.two-column .contents {"
						+ "			font-size: 14px;" + "			text-align: center;" + "			color:#000;"
						+ "		}" + "		.two-column img {" + "			width: 100%;"
						+ "			max-width: 300px;" + "			height: auto;" + "		}"
						+ "		.two-column .text {" + "			padding-top: 30px;" + "		}" + "		"
						+ "		.three-column {" + "			text-align: center;" + "			font-size: 0;"
						+ "			padding-top: 10px;" + "			padding-bottom: 10px;" + "		}"
						+ "		.three-column .column {" + "			width: 100%;" + "			max-width: 200px;"
						+ "			display: inline-block;" + "			vertical-align: top;" + "		}"
						+ "		.three-column .contents {" + "			font-size: 14px;"
						+ "			text-align: center;" + "		}" + "		.three-column img {"
						+ "			width: 100%;" + "			max-width: 60px;" + "			height: auto;"
						+ "		}" + "		.three-column .text {" + "			padding-top: 10px;" + "		}"
						+ "		.text-shadow {" + "			text-shadow: 1px 1px 1px rgba(150, 150, 150, 1);"
						+ "			text-align:center;" + "		}" + "	</style>" + "	"
						+ "	<!--[if (gte mso 9)|(IE)]>" + "		<style type=\"text/css\">"
						+ "			table {border-collapse: collapse !important;}" + "		</style>"
						+ "	<![endif]-->" + "	" + "</head>"
						+ "<body style=\"Margin:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;min-width:100%;background-color:#ffffff;\">"
						+ "	<center class=\"wrapper\" style=\"width:100%;table-layout:fixed;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;\">"
						+ "		<div class=\"webkit\" style=\"max-width:600px;\">" + "				"
						+ "				<!-- Start: Preheader -->"
						+ "                <div style=\"font-family:Arial, sans-serif;font-size:1px;color:#f6f7fb;line-height:1px;mso-line-height-rule:exactly;display:none;max-width:0px;max-height:0px;opacity:0;overflow:hidden;mso-hide:all;\" >"
						+ "                	<!-- Your preheader text goes here -->" + "                </div>"
						+ "                <!-- End: Preheader -->" + "                "
						+ "			<!--[if (gte mso 9)|(IE)]>"
						+ "				<table width=\"600\" align=\"center\" style=\"border-spacing:0;font-family:sans-serif;color:#333333;\">"
						+ "				<tr>"
						+ "				<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;\">"
						+ "			<![endif]-->" + "				"
						+ "			<table class=\"outer\" align=\"center\" style=\"border-spacing:0;font-family:sans-serif;color:#333333;Margin:0 auto;width:100%;max-width:600px;-webkit-box-shadow:inset 0px -1px 5px 0px rgba(0,0,0,0.75);-moz-box-shadow:inset 0px -1px 5px 0px rgba(0,0,0,0.75);box-shadow:inset 0px -1px 5px 0px rgba(0,0,0,0.75);\">"
						+ "				<tr>"
						+ "					<td class=\"one-column\" style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;\">"
						+ "						<table width=\"100%\" style=\"border-spacing:0;font-family:sans-serif;color:#333333;\">"
						+ "							<tr>"
						+ "								<td class=\"inner contents\" width=\"600\" height=\"50\" style=\" background-color:#FF6609;padding-top:20px;padding-bottom:20px;padding-right:20px;padding-left:20px;text-align:center;\">"
						+ "									<p style=\"font-size:24px; color:#ffffff; font-family:arial, sans-serif; Margin-bottom:0px;\">"
						+ "										<a href=\"http://thecoolinfo.com\" valign=\"middle\" style=\"text-decoration:none; font-family:arial, sans-serif; color:#ffffff; font-weight:bold;\">The Cool Info"
						+ "										</a>" + "									</p>"
						+ "								</td>" + "							</tr>"
						+ "						</table>" + "					</td>" + "				</tr>"
						+ "				<tr>" + "					<td class=\"container\">"
						+ "                        <img src=\"http://thecoolinfo.com/images/658.png\" class=\"img-responsive\" width=\"600\" height=\"403\" alt=\"Fact\"/>"
						+ "						<!--[if gte mso 9]>"
						+ "						<v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"width:600px;height:448px;\">"
						+ "							<v:fill type=\"tile\" src=\"http://thecoolinfo.com/images/658.png\" color=\"#ff0000\" />"
						+ "							<v:textbox inset=\"0,0,0,0\">"
						+ "						<![endif]-->" + "						" + "						"
						+ "						<!--[if gte mso 9]>" + "						</v:textbox>"
						+ "						</v:rect>" + "						<![endif]-->"
						+ "					</td>" + "				</tr>" + "				<tr>"
						+ "					<td class=\"one-column\" style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;\">"
						+ "						<table width=\"100%\" style=\"border-spacing:0;font-family:sans-serif;color:#333333;\">"
						+ "							<tr>"
						+ "								<td class=\"one-column\" style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;\">"
						+ "									<table width=\"100%\" style=\"border-spacing:0;font-family:sans-serif;color:#333333;\">"
						+ "										<tr>"
						+ "											<td class=\"inner contents\" style=\"background-color:#FF6609;	padding-top:20px;padding-bottom:10px;padding-right:20px;padding-left:20px;width:100%;text-align:center; color:#000000; font-size:20px;\">"
						+ "												<p style=\"color:#ffffff; font-size:18px; font-weight:bold;\">Another fact that might be of interest to you</p>"
						+ "												<p style=\"font-size:16px;\">Ancient Babylonians did math in base 60 instead of base 10. That's why we have... <a href=\"http://thecoolinfo.com/factoftheday.html\" style=\"color:#ffffff;text-decoration:none;border-radius:3px;\">...read the rest of the fact here</a></p>"
						+ "											</td>"
						+ "										</tr>" + "									</table>"
						+ "								</td>" + "							</tr>"
						+ "						</table>" + "					</td>" + "				</tr>"
						+ "				<tr>"
						+ "					<td class=\"one-column\" style=\"background-color:#efefef; padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;\">"
						+ "						<table width=\"100%\" style=\"border-spacing:0;font-family:sans-serif;color:#333333;\">"
						+ "							<tr>"
						+ "								<td class=\"inner contents\" align=\"left\" style=\" color:#333333; font-family:arial;font-size:16px;padding-top:20px;padding-bottom:20px;padding-right:20px;padding-left:20px;width:100%;text-align:center; width:600px;\">"
						+ "									<p style=\"font-family:arial; text-align:center; sans-serif;font-size:12px;Margin:0;Margin-bottom:10px; margin-top:30px;\">This email was sent to '%%emailaddress%%'.</p>"
						+ "									<p style=\"font-family:arial; text-align:center; sans-serif;font-size:12px;Margin:0;Margin-bottom:10px;\">If you no longer wish to receive these emails you may unsubscribe by <a href=\"%%unsubscribelink%%\">clicking this link</a> at any time.</p>"
						+ "								</td>" + "							</tr>"
						+ "							<tr>"
						+ "								<td align=\"center\" width=\"100%\" height=\"10\" style=\"background-color:#ff6609\">"
						+ "									<p style=\"color:#ffffff; font-size:16px;\">&copy; 2016 The Cool Info, all rights reserved.</p>"
						+ "								</td>" + "							</tr>"
						+ "						</table>" + "					</td>" + "				</tr>"
						+ "			</table>" + "			" + "			<!--[if (gte mso 9)|(IE)]>"
						+ "				</td>" + "				</tr>" + "				</table>"
						+ "			<![endif]-->" + "			" + "		</div>" + "	</center>" + "</body>"
						+ "</html>" };
	}

	public interface Offer{
		
		public interface _804 {
			
			public static final String number = "804";
			
			public static final String[] froms = { "The HARP Lenders", "The Harp Lenders", "TheHarpLenders",
					"TheHarpLenders Partner", "TheHarpLenders Offer", "TheHarpLenders Website", "Harp Lenders Direct",
					"harplendersdirect", "harp lenders direct", "harp.lenders.direct", "harplenders-direct",
					"harp-lenders-direct" };
	
			public static final String[] subjects = { "Presidents Refinance Program",
					"HARP Extended into 2016. Start Saving on Your Mortgage Now.",
					"2016 HARP Program Extended. Get Dramatic Mortgage Savings",
					"Save Thousands on Your Mortgage. Learn More about HARP.",
					"Save Thousands on Your Mortgage with the Home Affordable Refinance Program.",
					"HARP 2.0 Refinance Program Extended Until 2016 - Act Before Rates Go Up",
					"President Announces HARP Extension! Respond", "Licensed Lenders for HARP 2.0 Program await you!",
					"What Is HARP? Info here", "HARP - Why refinance? we can help!",
					"Obama waived Minimum Credit Score requirements for Refinancing. Take advantage while you can!",
					"Obama Announces the HARP Program. Learn How You Can Save Thousands a Year" + "HARP 2.0 Extended!",
					"Refinance Today!", "Mortgage Underwater?", "Mortgage Underwater? Obama's HARP Program Extended!",
					"Refinance with Obama's Home Affordable Refinance Program",
					"Refinance Your Underwater Mortgage with Obama's Home Affordable Refinance Program",
					"Struggling to Pay Your Mortgage? Refinance Today!",
					"Struggling to Pay Your Mortgage? Refinance with HARP today!",
					"See if You Qualify For The Obama HARP 2.0 Plan", "How Much Will YOU save with Obama's HARP 2.0?",
					"Refinance and Save With Government HARP 2.0", "Obama's HARP 2.0 Refinance Program",
					"2016 HARP Program Extended. Get Dramatic Mortgage Savings",
					"HARP Extended into 2016. Qualify To Save On Your Mortgage",
					"HARP Savings Are still Available. Lower Your Mortgage Payment Today",
					"HARP Extended into 2016. Start Saving on Your Mortgage Now.",
					"homeowners suffering during the mortgage crisis" };
	
			public static final String[] bodies = { "<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
					+ "<h1><center><a target=\"_blank\" href=\"http://collowed.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e8211ad6f629f9714242293389c2726788f13b45bf9870d91c312a7d46579d0b70\"><font color=\"#FF0000\"></font></a></center></h1><br />"
					+ "<map name=\"wruqu\"><area shape=\"rect\" coords=\"4,2,2743,2956\" target=\"_blank\" href=\"http://collowed.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e8211ad6f629f9714242293389c2726788f13b45bf9870d91c312a7d46579d0b70\"></map>"
					+ "<map name=\"cihrl\"><area shape=\"rect\" coords=\"4,1,2004,2696\" target=\"_blank\" href=\"http://collowed.info/c61cf1f7683bfef7b07d82c668cbaa36b0bd5052fade62e4d51a44e21886d8eb472451cada12dd0d9c4b67e7492a738b7748fca6c094e5fd7f03de30133bd673\"></map>"
					+ "<table height=\"351\" width=\"407\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
					+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
					+ " title=\"content" + randomString + "\""
					+ " src=\"http://collowed.info/337a6d0c1a8c45dbd31e3a959d0d8c276db43f2e1ea077256525153a52c15b6037101e10d4045312244b9cf68d3c21a73d83c6f08d07721fc8aa91499f49f6ce\" usemap=\"#wruqu\"></td></tr>"
					+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\""
					+ " title=\"unsubscribe" + randomString + "\""
					+ " src=\"http://collowed.info/312a8b2346fbc0a25d0cae29f5a71eb8803d3548da8ad03a7fdfb84a56809e01ef6e653f336371c9cf4832cdd7c5dbab7d73e5f30fa4bdc221b2b879287c4604\" usemap=\"#cihrl\"></td></tr></table><br />"
					+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://catholocausing.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63a30a2386251631db9331eb56a89afc5fc338440bed61e93a62ebaff59d6dca9d\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"sjzkg\"><area shape=\"rect\" coords=\"0,0,2102,2708\" target=\"_blank\" href=\"http://catholocausing.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63a30a2386251631db9331eb56a89afc5fc338440bed61e93a62ebaff59d6dca9d\"></map>"
							+ "<map name=\"uvwba\"><area shape=\"rect\" coords=\"1,3,2761,2404\" target=\"_blank\" href=\"http://catholocausing.info/db0b993e7b373a0370b0cca089f680f4bcc63a5b865c9513a081bd77b86d37d2416764f76ce52f3717bca4951f14b11bb3064b296401610b44d7b46b77dff5d9\"></map>"
							+ "<table height=\"345\" width=\"499\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://catholocausing.info/5dd7f803c0270767fad0fa43490a2176e23f3b83fbd920432d7fa137a7fdcc152c85b2adaf87efbf10ce0a589aea6b0cf6c566d7bbf85b3c396bd657c9e5fcb6\" usemap=\"#sjzkg\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://catholocausing.info/108de14473fbd0f11f4b3f4a6e7eba75c9845958b35126ab3249c0b2741b52c8ab57830548b6ed6bd31ca662ac39791bb39a76b032611ea90a5a4fbb8271ea4e\" usemap=\"#uvwba\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://geneveral.info/76143020d90cca2d29091aa4c4feb6889b90c7c606151d0c2fa46ff07381e7960326a5e261cefeb2c21250d67ca715634e1d5164b19bc21bb8949eb7eeb41663\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"vvupk\"><area shape=\"rect\" coords=\"1,1,2336,2862\" target=\"_blank\" href=\"http://geneveral.info/76143020d90cca2d29091aa4c4feb6889b90c7c606151d0c2fa46ff07381e7960326a5e261cefeb2c21250d67ca715634e1d5164b19bc21bb8949eb7eeb41663\"></map>"
							+ "<map name=\"jszbv\"><area shape=\"rect\" coords=\"4,3,2482,2383\" target=\"_blank\" href=\"http://geneveral.info/c953465b5b3d5b1547a44ffec0cdc59f592db6c373933392bd00b2091b69f64e9e5d84d25ede03bd136a3772669950b728d41a7a5826b92dbaf6495ec862d710\"></map>"
							+ "<table height=\"380\" width=\"487\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://geneveral.info/c910276c3f93d1119e9c9d5a289525a93dea8e8d205004eb468aab2b0872ee5cfa6d3efa12c202bc481e42bc072a4fde850c3ce3900c74ed679978ba060effaf\" usemap=\"#vvupk\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://geneveral.info/ccd73a49d288c921140d058fb14de385b853fcead0cc25c58edc2eedae4c155455e48721b74ef81211b3c9519b24ebdb135ac099d96fff305abc1e47ccf6fb3f\" usemap=\"#jszbv\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://calculum.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e88d5b64c6dc50539578748c3625a255b8d2265bcd9a03325f785562e4e33d3d53\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"explr\"><area shape=\"rect\" coords=\"0,1,2150,2746\" target=\"_blank\" href=\"http://calculum.info/e466e62e389506ea13ee8aafd540c954c620c1f071be03ab1d29cb205c60b0e88d5b64c6dc50539578748c3625a255b8d2265bcd9a03325f785562e4e33d3d53\"></map>"
							+ "<map name=\"tcshz\"><area shape=\"rect\" coords=\"1,1,2348,2950\" target=\"_blank\" href=\"http://calculum.info/c61cf1f7683bfef7b07d82c668cbaa36b0bd5052fade62e4d51a44e21886d8eb5367b11b9bf1f0821ad0dcc020c6a1c0a2764e95d6ceacb09045af832ef8f1c2\"></map>"
							+ "<table height=\"344\" width=\"488\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://calculum.info/337a6d0c1a8c45dbd31e3a959d0d8c276db43f2e1ea077256525153a52c15b6082c44aca48bde1c325d78519032e808745e8102c5dfab7ea61f6877b4899ad5b\" usemap=\"#explr\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://calculum.info/312a8b2346fbc0a25d0cae29f5a71eb8803d3548da8ad03a7fdfb84a56809e0148e1e2e97b59e34cf78807c0e80593246437f1c37ee4dcbb62e3ebf5d0703e97\" usemap=\"#tcshz\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://tichess.info/06cff3d440bf848c815f5e84ac969c9ea92ba6e0cdc94e3603f2c800b5ce1a45d0facef77781e2309f66b14bc5b64a3ff34b5022d0f7eaddc0e59dd431ed88ba\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"pzhzg\"><area shape=\"rect\" coords=\"0,1,2890,2846\" target=\"_blank\" href=\"http://tichess.info/06cff3d440bf848c815f5e84ac969c9ea92ba6e0cdc94e3603f2c800b5ce1a45d0facef77781e2309f66b14bc5b64a3ff34b5022d0f7eaddc0e59dd431ed88ba\"></map>"
							+ "<map name=\"yuyrk\"><area shape=\"rect\" coords=\"2,4,2167,2434\" target=\"_blank\" href=\"http://tichess.info/6a07d06a4a74b6adb2d02b546756080d5ca7c134af97339d7d011f41c871b0b582b329b21d05350a8823ff9ee396d5db9e74fc63dd8d94b4eb693e3e48d24695\"></map>"
							+ "<table height=\"307\" width=\"482\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://tichess.info/93826849aaf60ca8062fb1906c48a5c593c52e3e5eaf0706ba4b090f530573e01cc382c77596db851f399fdbdb4bdcd1e7358ad2cfeddec66bef52ea92366194\" usemap=\"#pzhzg\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://tichess.info/57c8c0fe4d8fa827bdc18aa50b48b8c5119cc92bec88161149da47157229daa7ad7566ec9d5330ea3738b39cc75a152d2049b249658ee1baba765dd5b8580349\" usemap=\"#yuyrk\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://localiform.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63ec402a76a0426699f2addd10bbc820b6bbc7d42338f5f8ac5562d30c302571e3\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"imfme\"><area shape=\"rect\" coords=\"2,3,2814,2646\" target=\"_blank\" href=\"http://localiform.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a63ec402a76a0426699f2addd10bbc820b6bbc7d42338f5f8ac5562d30c302571e3\"></map>"
							+ "<map name=\"fbtpf\"><area shape=\"rect\" coords=\"0,0,2579,2161\" target=\"_blank\" href=\"http://localiform.info/db0b993e7b373a0370b0cca089f680f4bcc63a5b865c9513a081bd77b86d37d2c82474e8ea1f576d377aff49bed522356aa7ab7c5963573c32e0e64fac05b95f\"></map>"
							+ "<table height=\"374\" width=\"446\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://localiform.info/5dd7f803c0270767fad0fa43490a2176e23f3b83fbd920432d7fa137a7fdcc15c37ce5d6b74d7e90d2f45b7ece749dadc4f54568929fbdbdd31ce1b1eb52c000\" usemap=\"#imfme\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://localiform.info/108de14473fbd0f11f4b3f4a6e7eba75c9845958b35126ab3249c0b2741b52c866cebc2a62fa7d1713b2dee71c4b73d304179e7385aee978b30cc44e76956af4\" usemap=\"#fbtpf\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://natasterfahrten.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a6327977cf718cb2ba0e4ee655b94be6682cf5275a56417d3ac65549c43751c507b\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"dxppn\"><area shape=\"rect\" coords=\"4,2,2148,2858\" target=\"_blank\" href=\"http://natasterfahrten.info/6b9680d42157dc41535ced53855476011c83911542f0283409a8249e9f3b4a6327977cf718cb2ba0e4ee655b94be6682cf5275a56417d3ac65549c43751c507b\"></map>"
							+ "<map name=\"wikaq\"><area shape=\"rect\" coords=\"1,3,2904,2476\" target=\"_blank\" href=\"http://natasterfahrten.info/db0b993e7b373a0370b0cca089f680f4bcc63a5b865c9513a081bd77b86d37d2cc7a56f8df6f95346844a58339a43b4c7577699c00945b729a1029c7e20e4a7f\"></map>"
							+ "<table height=\"320\" width=\"425\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://natasterfahrten.info/5dd7f803c0270767fad0fa43490a2176e23f3b83fbd920432d7fa137a7fdcc153a66b0576f1122004a435f8594cb585dc3747a6db9aef884369fc0a51740803a\" usemap=\"#dxppn\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://natasterfahrten.info/108de14473fbd0f11f4b3f4a6e7eba75c9845958b35126ab3249c0b2741b52c85119890937b0273d94b2f5a582303598ff0c8862ac9023313e3f3993ad072b17\" usemap=\"#wikaq\"></td></tr></table><br />"
							+ "</body>" + "</html>" };
		}
	
		public interface _1148 {
			
			public static final String number = "1148";
			
			public static final String[] froms = { "Window Price Offer", "Window Price Promo", "WindowPrice",
					"Window Price", "WindowPrice.net" };
	
			public static final String[] subjects = { "Upgrade your home with affordable window replacements",
					"Replace your windows without spending a fortune", "Start the year with a home window replacement",
					"New Year! New Windows!", "Affordable window replacement made easy",
					"Quotes on local window replacement contractors", "Energy Efficient Windows for Local Residents",
					"Winter Specials on Energy-Saving Windows for Local Residents",
					"Energy Efficient Windows, Winter Closeout Prices!", "Local Window Installers Offering Winter Specials",
					"Top Brands offering Winter closeout prices on Windows",
					"Stop Wasting Energy, Replace Windows and Save!", "Huge Winter Savings on Windows in your city!",
					"Best Windows Replacement Deals of the Year- Available Now.",
					"Update Your Windows and Slash Your Energy Bills!",
					"Windows Specials from Local Professionals & Other Local Contractors",
					"Window-Replacement Specials from Local Professionals, Andersen and more",
					"Window-Replacement Deals from Local Contractors, Andersen and more",
					"Local Window-replacement specials from Local Contractors and Others",
					"Savings on Windows From Local Professionals and Other Local Contractors",
					"Energy Efficient Windows From Local Professionals & Other Local Contractors",
					"Local Professionals & Local Contractors Window Replacement Deals",
					"Local Professionals & Local Contractors Energy Efficient Window Deals",
					"Quality Window Replacement Deals - Quotes from Local Professionals & Local Contractors!",
					"Window Deals from Local Professionals & other local contractors",
					"Window Replacement Estimates from Local Professionals & more",
					"Window replacement deals from Local Professionals & other local contractors",
					"Save on Windows from Local Professionals & other local contractors",
					"Free Window Replacement Estimates from Local Professionals & more",
					"Energy Efficient Windows From Local Professionals & more",
					"Energy Efficient Window Deals from Local Professionals & more",
					"Looking for window deals in your city?", "What are the best window deals in your city?",
					"Need new windows?", "Looking for great deals on windows?", "Check out these window deals",
					"Window deals may be available in your city", "Looking for hot deals on windows?",
					"Winter Window Specials in Your City", "Winter Window Savings on Pella, Andersen, Marvin & more",
					"Save on top window brands this season", "Top Window Brands Savings this Season",
					"Winter Discounts on Pella, Andersen, Marvin & more", "Quality Window Replacement Deals",
					"Check out these low prices on home window replacements",
					"Does your home need new windows? Check out these deals",
					"Check out the latest in replacement windows" };
	
			public static final String[] bodies = { "<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
					+ "<h1><center><a target=\"_blank\" href=\"http://ential.info/17fcb6f62ca3f8d5b52bad3f85927af0954e6ea95946c17330741279ae6e574fbf989990038ad0c1e07656e97b049cd7a244e5dd991121057e6fb0c6d84bdfb2\"><font color=\"#FF0000\"></font></a></center></h1><br />"
					+ "<map name=\"vknsj\"><area shape=\"rect\" coords=\"2,0,2602,2247\" target=\"_blank\" href=\"http://ential.info/17fcb6f62ca3f8d5b52bad3f85927af0954e6ea95946c17330741279ae6e574fbf989990038ad0c1e07656e97b049cd7a244e5dd991121057e6fb0c6d84bdfb2\"></map>"
					+ "<map name=\"eecxy\"><area shape=\"rect\" coords=\"4,0,2590,2682\" target=\"_blank\" href=\"http://ential.info/1e77b4fabe33cc7034770c8249d8eece74f1ef01508abcef470a330c3f2cc940d53cf11cacb6375ab413f376918543098d120567bbe1669c0f155aa039202d25\"></map>"
					+ "<table height=\"348\" width=\"420\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
					+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
					+ " title=\"content" + randomString + "\""
					+ " src=\"http://ential.info/1d96208254fde627ffe8bcc7875ce67ceb77dd626b80f3f5f551c58f248b06891b9379d654d84ec7ab3dbcff9883e649a9199a389407979bf11f3d85e85a63ec\" usemap=\"#vknsj\"></td></tr>"
					+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\""
					+ " title=\"unsubscribe" + randomString + "\""
					+ " src=\"http://ential.info/94590abe440dcddc962d9e2903ae2ec072323a14ddcbf8206a8b11073edfe25d41cfc7ec65c725b06d65afe71dbde243b051d77e664e1b2e3e9010b702c3616b\" usemap=\"#eecxy\"></td></tr></table><br />"
					+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://collowed.info/5dc9114afa702b0aa5686278995b10690d18e9490c0167fa1e446405a712e51ce3819e7b63e730ad9f659ff440ec3750495fa61c0c9eb09a2fe9f50160687b94\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"ugbum\"><area shape=\"rect\" coords=\"1,0,2273,2483\" target=\"_blank\" href=\"http://collowed.info/5dc9114afa702b0aa5686278995b10690d18e9490c0167fa1e446405a712e51ce3819e7b63e730ad9f659ff440ec3750495fa61c0c9eb09a2fe9f50160687b94\"></map>"
							+ "<map name=\"zwdnd\"><area shape=\"rect\" coords=\"3,1,2593,2034\" target=\"_blank\" href=\"http://collowed.info/5b9d3be3437b36300580bcc6e7aa504f05bc532efe5d00eb6acada0b371a332efe80d1fcc29f35838e8aa5a22bd3620b4e30b0eea62f1ca40900b6b9645db95e\"></map>"
							+ "<table height=\"374\" width=\"442\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://collowed.info/5356664074e9d9594ecfdbe8ccb608464e6c241a57b7570f7af8ddd96c451ea301d158729f05020ca4d2ff15c04f5af307bdd98fbe7b395411ccb58d83e9ff7e\" usemap=\"#ugbum\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://collowed.info/5930ac7dff43229a83d463147f64511ff000d283bde6a28740512bba4fc44c639f4691bae22de179eb60300ad7df466284bd99f41abc5570639abcf3956fdedf\" usemap=\"#zwdnd\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://catholocausing.info/b95c786b70c2de0b649c99d90eec72c959a69040ef69a5b4fb37076a29b8338acaded04cfe58dec7b261c30c2f278e8f8d74fc87807528f748c799f03e89d69c\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"afryo\"><area shape=\"rect\" coords=\"1,1,2183,2280\" target=\"_blank\" href=\"http://catholocausing.info/b95c786b70c2de0b649c99d90eec72c959a69040ef69a5b4fb37076a29b8338acaded04cfe58dec7b261c30c2f278e8f8d74fc87807528f748c799f03e89d69c\"></map>"
							+ "<map name=\"kwvkd\"><area shape=\"rect\" coords=\"2,2,2216,2972\" target=\"_blank\" href=\"http://catholocausing.info/0a49abc4db7be073636922c3d3d4c8ce91733575aeecec3d5831ead5b44273d8f36cd646a4e19e87dd1211162f7e3fb86f700e776d9bfa77f30a6533a60b1db3\"></map>"
							+ "<table height=\"337\" width=\"458\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://catholocausing.info/149d17366d2d45ac7dcf776143f082b50a44f374b7ef3ccc56938865ebe3b75bafbacf964a5f9f2e6290eb0c28c89213dda154e160a95327deed5cc9206fcbde\" usemap=\"#afryo\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://catholocausing.info/a094187e7d6b99abda3fc6bd03f78d5d436747c82320515ec2dcf0ffaca67fd26b18ab68d14ae113823e73bcf37f080183bdb4c246eada0d329d43017eb249ec\" usemap=\"#kwvkd\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://geneveral.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da86fa1f3768d2d8ef0dee38b179cb25a0ab3ee52fd26644abf445d8ad2b21acb28\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"mektm\"><area shape=\"rect\" coords=\"3,0,2736,2012\" target=\"_blank\" href=\"http://geneveral.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da86fa1f3768d2d8ef0dee38b179cb25a0ab3ee52fd26644abf445d8ad2b21acb28\"></map>"
							+ "<map name=\"gukum\"><area shape=\"rect\" coords=\"3,2,2348,2082\" target=\"_blank\" href=\"http://geneveral.info/954f20c0a4ee969aea12bb23868dc87f6f9a373076d79ed41f9af8be198f57e439767227aa288063d93dd677133eb6f3fe222d3cf811c7b7a9b2712fa8b7a56e\"></map>"
							+ "<table height=\"305\" width=\"415\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://geneveral.info/51be3265a94bd6eb21ba25198e76c9d5b2dc1473d101e566cbac25644cc8ccbd3e93ef1a27f94a989d07b7d20975c4d75a219247ad1e0e232cfea1aedcb84dce\" usemap=\"#mektm\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://geneveral.info/4474869ce8643d042bd1c20dc650c30043d8dbe9b1a7e582f1f9584a627b3854fc5a08d3d9693a7b83018db8b13e9eb37ebb9af78c501c656ec34c84907a7e33\" usemap=\"#gukum\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://calculum.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb6105533eb06e2ab2d3753104c052556c60d9880a4e00eb863cb6b5871b32d8a674f54\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"gpaky\"><area shape=\"rect\" coords=\"0,3,2783,2632\" target=\"_blank\" href=\"http://calculum.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb6105533eb06e2ab2d3753104c052556c60d9880a4e00eb863cb6b5871b32d8a674f54\"></map>"
							+ "<map name=\"ienbr\"><area shape=\"rect\" coords=\"3,4,2158,2200\" target=\"_blank\" href=\"http://calculum.info/51538d847a09c8205acc165697d2c01169dac8eb1cc560c6fa6424365ad92ea11b7cdc2d028c05f41592666edf41a716d78e35cee351aed4a51dc1579f442347\"></map>"
							+ "<table height=\"329\" width=\"409\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://calculum.info/d049434b2d395ab8de267eb36a619c75f61482eb817906801880447391242209fbde5ac3fe4097f99ae736425a8f7221f8c45ce7e0a831d50f5bb11127e363c6\" usemap=\"#gpaky\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://calculum.info/8b127638e5fb753da75894622d649669cd0f21f6d2abf5c745b505a9c0833de013a8da47d650024659d6c98f3a11cb8dfa106d4fdd31c6dac31d59ea04cee0cd\" usemap=\"#ienbr\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://tichess.info/dc12d37d0701a79982ac111a08e1265f41ba8678faca9c61607aa13355ea4b2dcdc92e6f7749ef5e698b45548e2fd3db5df767d98264a66f3557334bdde476db\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"qlcdk\"><area shape=\"rect\" coords=\"4,3,2508,2104\" target=\"_blank\" href=\"http://tichess.info/dc12d37d0701a79982ac111a08e1265f41ba8678faca9c61607aa13355ea4b2dcdc92e6f7749ef5e698b45548e2fd3db5df767d98264a66f3557334bdde476db\"></map>"
							+ "<map name=\"himjb\"><area shape=\"rect\" coords=\"2,2,2268,2107\" target=\"_blank\" href=\"http://tichess.info/70c0bfe58a8f556880021ceedbe6f6ee420e20846da5ac0453c0590ff4d68fc3ac1ebefaa44e0999f7e4c6243865de05f01cc508a667baebd9ae1fa17a71e349\"></map>"
							+ "<table height=\"301\" width=\"476\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://tichess.info/f54c753083d750b41bbe1db6a2e7caf93f7ac0c929780001530b2184b814c9616345248e0e4b59ee39f99b8c2872b0b5cfcb709b49c2c4c324901c5a6080636c\" usemap=\"#qlcdk\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://tichess.info/3cfc6aa04626b72de5fdf0614e0f97c0b815e1c596cbbba0f127e6d8fe4c01a4ad6da004297cfa0b3de03e20a08b5fc737aab1413ed696c79a20e5a47dead8c3\" usemap=\"#himjb\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://localiform.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da825bf3efd9469eb6168d511b935ec06ddb4e28a803ead27e5d6bcbcfcc6acfb05\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"kpnac\"><area shape=\"rect\" coords=\"2,4,2426,2965\" target=\"_blank\" href=\"http://localiform.info/20c39eabace87ba24bb5f85f6949f5ba23a606419cce301f629f9ca1fc3c3da825bf3efd9469eb6168d511b935ec06ddb4e28a803ead27e5d6bcbcfcc6acfb05\"></map>"
							+ "<map name=\"kgibm\"><area shape=\"rect\" coords=\"0,2,2315,2430\" target=\"_blank\" href=\"http://localiform.info/954f20c0a4ee969aea12bb23868dc87f6f9a373076d79ed41f9af8be198f57e47b84492d52d8a9b2d3f0a5f5f2277f2b84d9eac17b9f799791465833250f3653\"></map>"
							+ "<table height=\"329\" width=\"430\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://localiform.info/51be3265a94bd6eb21ba25198e76c9d5b2dc1473d101e566cbac25644cc8ccbdd0b2d345c75a9f469f383c2c96e7515fd288eeec9da7861b8fd141836c772ee8\" usemap=\"#kpnac\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://localiform.info/4474869ce8643d042bd1c20dc650c30043d8dbe9b1a7e582f1f9584a627b3854ebd4937892a8fd45afcf8e573be1013c3ee2b45238b1d743fdc846020ce1e575\" usemap=\"#kgibm\"></td></tr></table><br />"
							+ "</body>" + "</html>",
	
					"<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://natasterfahrten.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb610550a0e22050a7600126ad3868176daddbdd721ba5378056390e91864e9ab23c049\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"iwcyd\"><area shape=\"rect\" coords=\"2,0,2979,2718\" target=\"_blank\" href=\"http://natasterfahrten.info/bd1119f9880beb3abf968de4a788a65a5776fd95d9fb101c6d6789e7ffb610550a0e22050a7600126ad3868176daddbdd721ba5378056390e91864e9ab23c049\"></map>"
							+ "<map name=\"jijhn\"><area shape=\"rect\" coords=\"2,4,2079,2617\" target=\"_blank\" href=\"http://natasterfahrten.info/51538d847a09c8205acc165697d2c01169dac8eb1cc560c6fa6424365ad92ea191e184390cf11a25959e5e05b884da33b6254ccac157c047e758d9ce05cab83e\"></map>"
							+ "<table height=\"310\" width=\"458\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
							+ " title=\"content" + randomString + "\""
							+ " src=\"http://natasterfahrten.info/d049434b2d395ab8de267eb36a619c75f61482eb817906801880447391242209a6b85b937c199b8e229a44c10a6916501b453193b829a71bd46356d85395b246\" usemap=\"#iwcyd\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString
							+ "\"" + " title=\"unsubscribe" + randomString + "\""
							+ " src=\"http://natasterfahrten.info/8b127638e5fb753da75894622d649669cd0f21f6d2abf5c745b505a9c0833de0279615b9ba59360a068be3046b455beaadfb9d869cc0c05de95215e76c2642ab\" usemap=\"#jijhn\"></td></tr></table><br />"
							+ "</body>" + "</html>" };
		}
	
		public interface _1607 {
			public static final String number = "1607";
			
			public static final String[] froms = { "Dental Implant Services", 
													"Dental Implant Service", 
													"Dental Implants",
													"Dental Services" };
	
			public static final String[] subjects = { "Access a Variety of Options for Dental Implants",
					"Are you in need of Dental Implants?", "Available Options For Dental Implants",
					"Check Dental Implant Options, and Services...", "Checkout the latest Dental Implants",
					"Compare Dental Implant Options", "Compare Dental Implants in Just 1 Click",
					"Compare Options on Dental Implants", "Compare Savings on Dental Implants",
					"Compare The Results on Dental Implants", "Comparison Options for Dental Implants",
					"Dental Implant Choices.. Look inside..", "Dental Implant Options Could be Yours",
					"Dental Implant Options Now Available", "Dental Implant Options are Ready and Available",
					"Dental Implants Comparison is Easy..", "Dental Implants Could be yours Today!",
					"Discover the options for Dental Implants", "Don't Miss out on Dental Implants",
					"Explore your options for Dental Implants", "Find Dental Implants No Login Required",
					"Find Dental Implants here", "Find Information on Dental Implants",
					"Find Resources for Dental Implants", "Find out Instantly about Dental Implants",
					"Get Ready for Dental Implant Options", "Information on Dental Implants",
					"It's easy to review Dental Implant Options",
					"Knowing How to Get Dental Implant Options Means Acting Fast", "Look at Dental Implants inside",
					"Look at These Options for Dental Implants", "Make sure you look at Dental Implant Options",
					"Mind Blowing options on Dental Implants", "Need Dental Implants?", "Need Dental Implants? Go Here",
					"Options for Dental Implants Ready for Review", "Reminder to Check Status for Dental Implants",
					"Review Dental Implant Options Now", "Review Your Dental Implant Options",
					"Save on Dental Implant Costs", "Savings are Good on Dental Implants",
					"Search Here For Dental Implants", "See Dental Implant Options in Minutes",
					"See How our Options for Dental Implants can help...", "Shop Options for Dental Implants",
					"Special Delivery on Dental Implants Resources", "Stop Searching for Dental Implants Look Here",
					"Take a chance on Dental Implants", "Take a minute to explore Dental Implant Options",
					"This Could Be Your Chance For Dental Implants", "View Dental Implant Options, and Services...",
					"Your Search for Dental Implants Ends Here", "Your search for Dental Implants may end"};
	
			public static final String[] bodies = { "<html>" + "<body bgcolor=\"#FFFFFF\"><br />"
					+ "<h1><center><a target=\"_blank\" href=\"http://quedosimple.com.ar/7b6980b4eaee864e9b55b130a04fd1f72f8f18dff19a9aec5975f2d2e1e7465707beffe1a7ea558180c969e44699284c1c8c09eb48cba3ae3dd41db3837c2d6c\"><font color=\"#FF0000\"></font></a></center></h1><br />"
					+ "<map name=\"ulhjb\"><area shape=\"rect\" coords=\"3,3,2936,2865\" target=\"_blank\" href=\"http://quedosimple.com.ar/7b6980b4eaee864e9b55b130a04fd1f72f8f18dff19a9aec5975f2d2e1e7465707beffe1a7ea558180c969e44699284c1c8c09eb48cba3ae3dd41db3837c2d6c\"></map>"
					+ "<map name=\"gxtbu\"><area shape=\"rect\" coords=\"0,3,2791,2037\" target=\"_blank\" href=\"http://quedosimple.com.ar/be6679e0e2d8b58bf6a29a5e4d31858ba6ca0032519e2e951617b316791ce3f816654f41bb983dc807f97de94f62672b3a5bea557989454355dfccc97c56086e\"></map>"
					+ "<table height=\"312\" width=\"430\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
					+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\""
					+ " title=\"content" + randomString + "\""
					+ " src=\"http://quedosimple.com.ar/22e92c41db67e6d4549e8e1ea9e7e78122d2f7a214754627c717bcbe4d5207c4f32057d3bd3d3b046b69ddcbaf5350994ce66d05a9a4add8d3796a64e0c2ef51\" usemap=\"#ulhjb\"></td></tr>"
					+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString +"\" title=\"unsubscribe" + randomString + "\" src=\"http://quedosimple.com.ar/5562648c272a6bba5b1db15691c45f27f9731a850a674adfdcb574d2fb25146e416df843db1af32d58af31ae41b4534d152964b41b59d1c1f373752ecde06e9a\" usemap=\"#gxtbu\"></td></tr></table><br />"
					+ "</body>" + "</html>",
					"<html>" 
					+ "<body bgcolor=\"#FFFFFF\"><br />"
						+"<h1><center><a target=\"_blank\" href=\"http://joinbullsstennis.com/102f38e90f89e6224202dcaa68479eee0a2f4ebfb8d893e52dfc751a6927510d338db72a9d6ff23cd40725add6bc5b3ab28b45d831d8c550cef5139c21308499\"><font color=\"#FF0000\"></font></a></center></h1><br /> "
						+"<map name=\"tjftp\"><area shape=\"rect\" coords=\"4,2,2672,2761\" target=\"_blank\" href=\"http://joinbullsstennis.com/102f38e90f89e6224202dcaa68479eee0a2f4ebfb8d893e52dfc751a6927510d338db72a9d6ff23cd40725add6bc5b3ab28b45d831d8c550cef5139c21308499\"></map>"
						+"<map name=\"yathv\"><area shape=\"rect\" coords=\"0,2,2741,2678\" target=\"_blank\" href=\"http://joinbullsstennis.com/59593c432bd96b8e5527e28abb0444652fae302edefc0b09365484f7da880b0e16283ad59679eb9b357815360365900216647255ebe3bc5ab6f8aeba7f737dbd\"></map>"
						+"<table height=\"359\" width=\"436\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
						+"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
						+"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\" title=\"content" + randomString + "\" src=\"http://joinbullsstennis.com/dec421acb5e69762bcf6fb55fc03aa8aed95d6906ba653e17a7711dde49f1d80dd715543566ce77dd3c33535930987cc575d506a75937e1d5e8fef29c646db32\" usemap=\"#tjftp\"></td></tr>"
						+"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe " + randomString + "\" src=\"http://joinbullsstennis.com/84403b0e7800af2897fa0ceb17ea210adde17568aa732d70985e931aa7e4f3e364935e3c3fc5be11a6577ed978c082e9cbecdb916387e349c2523309fbd8321b\" usemap=\"#yathv\"></td></tr></table><br />"
						+"</body>"
					+"</html>",
					"<html>"
					+" <body bgcolor=\"#FFFFFF\"><br />"
					+" <h1><center><a target=\"_blank\" href=\"http://spendbullssoccer.com/a776d66da366a198cbd1f4d80f86b48f56c189d36cc149c49d9d9136cda2a86f65d9c11aa81c0c727db7c13452042ea9ba4e9f0ea48eac12c07de5a49ffe3b44\"><font color=\"#FF0000\"></font></a></center></h1><br />"
					+" <map name=\"lscyp\"><area shape=\"rect\" coords=\"3,1,2040,2699\" target=\"_blank\" href=\"http://spendbullssoccer.com/a776d66da366a198cbd1f4d80f86b48f56c189d36cc149c49d9d9136cda2a86f65d9c11aa81c0c727db7c13452042ea9ba4e9f0ea48eac12c07de5a49ffe3b44\"></map>"
					+" <map name=\"dkpej\"><area shape=\"rect\" coords=\"3,1,2113,2067\" target=\"_blank\" href=\"http://spendbullssoccer.com/7a412692537324bb75772824429570b44e5baa32857b1824ac0c2be903030869c7c2023b002faa3bc47e04e4abc5751595c1a68e3f31960ce1837b9a2733dddd\"></map>"
					+" <table height=\"384\" width=\"428\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
					+" <tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
					+" <tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\" title=\"content" + randomString + "\" src=\"http://spendbullssoccer.com/0b376d659d7bf236a2101d9e1db28ad24bf98afb225648787e319bac0e9c058a99e80f5ab00f7fb62596779dcb8f312725b51113e5163276f405b9806423d383\" usemap=\"#lscyp\"></td></tr>"
					+" <tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://spendbullssoccer.com/632259e24bad81a23fadd790bf0a12d523d6379aee73bcdba28828c82e12ed510859e78e450bcbaabb88dc5290ed7719603f483ef72aba3882bd5832dcd3a9d4\" usemap=\"#dkpej\"></td></tr></table><br />"
					+" </body>"
					+" </html>",
					 "<html>"
							+ "<body bgcolor=\"#FFFFFF\"><br /> "
							+ "<h1><center><a target=\"_blank\" href=\"http://showbullsguitar.com/0f7b8cf12d6c7786eaa59b845c79c0d4246a9326a2d19b3a404c00fa8834dd743f5f477b39ec4346fd223889795bd4d2d2b39e0f94fe167c028e503f579e887b\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"cjoep\"><area shape=\"rect\" coords=\"2,1,2156,2999\" target=\"_blank\" href=\"http://showbullsguitar.com/0f7b8cf12d6c7786eaa59b845c79c0d4246a9326a2d19b3a404c00fa8834dd743f5f477b39ec4346fd223889795bd4d2d2b39e0f94fe167c028e503f579e887b\"></map>"
							+ "<map name=\"duldd\"><area shape=\"rect\" coords=\"0,0,2579,2100\" target=\"_blank\" href=\"http://showbullsguitar.com/7cf716252c52133610501860a103793d27e200757228141558db9186b0026cb738e597bf547c746793c6012fa43a62caf134125e37d6b8efab82b4257d834e27\"></map>"
							+ "<table height=\"335\" width=\"412\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\" title=\"content" + randomString + "\" src=\"http://showbullsguitar.com/efcaa38cb3f9b120ee1213b8f3b94eda8ff23f6e0a4bd70df91a56175191e1a5296670d873659416a5dd69c6120fa883af68d48f7486543b4497ab55b50bb81b\" usemap=\"#cjoep\"></td></tr> "
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://showbullsguitar.com/4f68d9700192abd6b5d44e8c5bc1e01ddebe362191217ed848e79abf7d69a68468df0aaa92cc146f2a44bb6fc39138cf0c321b0978284fb16153ab4bb3683110\" usemap=\"#duldd\"></td></tr></table><br />"
							+ "</body>"
					+ "</html>",
					"<html>"
							+ "<body bgcolor=\"#FFFFFF\"><br />"
							+ "<h1><center><a target=\"_blank\" href=\"http://playbullsnfl.com/0f7b8cf12d6c7786eaa59b845c79c0d4246a9326a2d19b3a404c00fa8834dd74fe35a6319865f2f20ca9590d09bd6b75b5cd65a570f90f67b46e7bd3888fc63a\"><font color=\"#FF0000\"></font></a></center></h1><br />"
							+ "<map name=\"leymj\"><area shape=\"rect\" coords=\"1,3,2866,2606\" target=\"_blank\" href=\"http://playbullsnfl.com/0f7b8cf12d6c7786eaa59b845c79c0d4246a9326a2d19b3a404c00fa8834dd74fe35a6319865f2f20ca9590d09bd6b75b5cd65a570f90f67b46e7bd3888fc63a\"></map>"
							+ "<map name=\"jpmld\"><area shape=\"rect\" coords=\"1,0,2293,2051\" target=\"_blank\" href=\"http://playbullsnfl.com/7cf716252c52133610501860a103793d27e200757228141558db9186b0026cb78c5852c695d9043f62da1a59dde387cdbbc1043814b6cfde1aedd9e2214364c2\"></map>"
							+ "<table height=\"326\" width=\"489\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\" title=\"content" + randomString + "\" src=\"http://playbullsnfl.com/efcaa38cb3f9b120ee1213b8f3b94eda8ff23f6e0a4bd70df91a56175191e1a56d0727097c3d3109c46743fec8800e5684cc920b11f069f0a1b28aff5ce2b800\" usemap=\"#leymj\"></td></tr>"
							+ "<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://playbullsnfl.com/4f68d9700192abd6b5d44e8c5bc1e01ddebe362191217ed848e79abf7d69a684eea76f60b5c575f29d05999c0f91df440ca5a8ac921ee3bd966f167df90b6c8a\" usemap=\"#jpmld\"></td></tr></table><br />"
							+ "</body>"
							+ "</html>"
						};
		}
		
		public interface _1654 {
			
			public static final String number = "1654";
			
			public static final String[] froms = { 
				"Drug Rehab Providers", 
				"Drug-Rehab-Providers", 
				"Drug-Rehab-Providers.com", 
				"Drug-Rehab-Providers.com Affiliate", 
				"Drug-Rehab-Providers.com Promo", 
				"Drug-Rehab-Providers.com Offer"
			};
	
			public static final String[] subjects = {
				"Find Drug Rehab Information Here",
				"This Could Be Your Chance For Drug Rehab",
				"Drug Rehab Options Could be Yours",
				"Compare Drug Rehab Options",
				"Information on Drug Rehab",
				"Find Information on Drug Rehab",
				"Save on Drug Rehab Costs",
				"Need Drug Rehab? Go Here",
				"Drug Rehab Could be Beneficial",
				"Need Drug Rehab?",
				"Compare The Results on Drug Rehab",
				"Drug Rehab Choices.. Look inside..",
				"Find Drug Rehab No Login Required",
				"See Drug Rehab Options in Minutes",
				"It's easy to review Drug Rehab Options",
				"Compare Options on Drug Rehab",
				"This Drug Rehab is Worth Looking Into..",
				"Comparison Options for Drug Rehab",
				"Drug Rehab Comparison is Easy..",
				"Review Drug Rehab Options Now",
				"Review Your Drug Rehab Options",
				"Stop Searching for Drug Rehab Look Here",
				"Your Search for Drug Rehab Ends Here",
				"Drug Rehab Options are Ready and Available",
				"Available Options For Drug Rehab",
				"Look at These Options for Drug Rehab",
				"Compare Drug Rehab in Just 1 Click",
				"Drug Rehab For a Friend?",
				"Compare Savings on Drug Rehab?",
				"Drug Rehab Options Now Available",
				"Options for Drug Rehab Ready for Review"
			};
	
			public static final String[] bodies = {
				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://joinbullsstennis.com/98206b643d991f944ebb3e417a2c52c700a46c2c8b845ef40e3c38c5b48de3d635cb6827619ca9edeaade0e52673cea19d9ad3f5d0f96318be67c1b719422632\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"lmsuh\"><area shape=\"rect\" coords=\"0,4,2763,2551\" target=\"_blank\" href=\"http://joinbullsstennis.com/98206b643d991f944ebb3e417a2c52c700a46c2c8b845ef40e3c38c5b48de3d635cb6827619ca9edeaade0e52673cea19d9ad3f5d0f96318be67c1b719422632\"></map>" +
				"<map name=\"jbjvg\"><area shape=\"rect\" coords=\"2,4,2226,2956\" target=\"_blank\" href=\"http://joinbullsstennis.com/3730e3dca7ad544a3f9058dc4ca17a72491dda077e50e3d4e1618d6de73d8ced26dcb2d49c45850ae435d6a829cc5274fb7bfef2932ddad384a3a24220b8c04c\"></map>" +
				"<table height=\"306\" width=\"406\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://joinbullsstennis.com/b04226bce62bbf43d9cf1326e633989d04d8905ac41eb312d52ca13c9508e0df9ebb33e185430365e8c757c3e836cc80ce95eec15e7829671adf73a264ace501\" usemap=\"#lmsuh\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://joinbullsstennis.com/c8ee6d3f690afe6f2d3af9efd8bf729729014f02f097b8f0dc1fb60767a318109d53d688da831edbe58352fb82e59dbbdf0e445b1de262214e7c3cbb4ca25fa9\" usemap=\"#jbjvg\"></td></tr></table><br />" +
				"</body>" +
				"</html>",
	
				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://spendbullssoccer.com/c798dfe3028da1058ac21ea6f766841becb3525c8d971ff2f5c1c0addd5fc06679bf180f280266a23429b1b88754b38e47bdbd8820be787e2fdac5dde4b269c6\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"rceki\"><area shape=\"rect\" coords=\"1,2,2987,2502\" target=\"_blank\" href=\"http://spendbullssoccer.com/c798dfe3028da1058ac21ea6f766841becb3525c8d971ff2f5c1c0addd5fc06679bf180f280266a23429b1b88754b38e47bdbd8820be787e2fdac5dde4b269c6\"></map>" +
				"<map name=\"jrlng\"><area shape=\"rect\" coords=\"4,1,2994,2328\" target=\"_blank\" href=\"http://spendbullssoccer.com/99ed4dad5a0d5eaa54000526c44ec60e283f14f478a1bf39741674f196c805b177747d31c421a0a737e2abd5cdd3303d53b158c2a2d03aff1efeb3a093a06879\"></map>" +
				"<table height=\"391\" width=\"456\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://spendbullssoccer.com/bba250a5998fc0de60688a4ddefb587109a8a4e30e0d61eadd98abb643a551d19e3c66fa19fee51326d3399b7b0b2183ac3786ac1f1b1ceabbda052395d4725a\" usemap=\"#rceki\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://spendbullssoccer.com/62d93d674bbe044c57e466f0af9348ae312913cf80eee396722babdf77028b1a36bd7034f2d8c533e83a89958d4fd1dbf8c0137c860969490da5d1dd0bcea747\" usemap=\"#jrlng\"></td></tr></table><br />" +
				"</body>" +
				"</html>",
	
				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://showbullsguitar.com/98206b643d991f944ebb3e417a2c52c700a46c2c8b845ef40e3c38c5b48de3d63de91a0e694b9b3163a286ab855c7fbea04d42c16ef592472e9c2aa055514a78\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"affye\"><area shape=\"rect\" coords=\"4,4,2517,2175\" target=\"_blank\" href=\"http://showbullsguitar.com/98206b643d991f944ebb3e417a2c52c700a46c2c8b845ef40e3c38c5b48de3d63de91a0e694b9b3163a286ab855c7fbea04d42c16ef592472e9c2aa055514a78\"></map>" +
				"<map name=\"zrfkl\"><area shape=\"rect\" coords=\"3,0,2876,2295\" target=\"_blank\" href=\"http://showbullsguitar.com/3730e3dca7ad544a3f9058dc4ca17a72491dda077e50e3d4e1618d6de73d8ced013fe35b6834cbcc2527927fb00a12e9ac8aa47873228483e1eccf7e59d543fe\"></map>" +
				"<table height=\"321\" width=\"427\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://showbullsguitar.com/b04226bce62bbf43d9cf1326e633989d04d8905ac41eb312d52ca13c9508e0dfef59721498dd60264806614cd5e073f173c51790df1bf0f648b943820ae03d99\" usemap=\"#affye\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://showbullsguitar.com/c8ee6d3f690afe6f2d3af9efd8bf729729014f02f097b8f0dc1fb60767a318109628b951c9821b15d6b7ebb1896898bfadea288d2819eeb0b6f0fb0622704cdb\" usemap=\"#zrfkl\"></td></tr></table><br />" +
				"</body>" +
				"</html>",
	
				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://playbullsnfl.com/42ad94b08b177e9100f558e346fcef30e3896d8121722e205d4dc5e7714e27f884dce98822b04cfae976e7a7d6343e240c5e597850c5406a001d48f41980c0fb\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"gptya\"><area shape=\"rect\" coords=\"2,0,2252,2026\" target=\"_blank\" href=\"http://playbullsnfl.com/42ad94b08b177e9100f558e346fcef30e3896d8121722e205d4dc5e7714e27f884dce98822b04cfae976e7a7d6343e240c5e597850c5406a001d48f41980c0fb\"></map>" +
				"<map name=\"goisi\"><area shape=\"rect\" coords=\"4,1,2485,2991\" target=\"_blank\" href=\"http://playbullsnfl.com/6b939745bf8ca8988eb279e4766a6125ab3d3eaa29e07ef3465ed38dd06c8e6fb934cc1e1069ce903641cf3c8bccc5d1d6412450e8bbfa2bd0db5f843fe08fe9\"></map>" +
				"<table height=\"307\" width=\"440\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://playbullsnfl.com/d9856a55a89079ea7ad431659fc1a97c11cb37a6efc992c7e23c402bf3bec1291920cff46fb289f00a335dd59ef4f67396c729df637c2f3753d9ca3c52a4ede5\" usemap=\"#gptya\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://playbullsnfl.com/bde01a8b2c3dc038725f35674a8d644b3a4c75b19ed5166915e01739fc412711b0a862947186b1e8a73b305b0d7f0b0870f2b5dcb0d09ccec30f87485a1c2988\" usemap=\"#goisi\"></td></tr></table><br />" +
				"</body>" +
				"</html>",
	
				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://quedosimple.com.ar/42ad94b08b177e9100f558e346fcef30e3896d8121722e205d4dc5e7714e27f8d9b2f0b100c4b670235e4a0b306786f4d09564d5ec1a515e014e1aad2dd55f69\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"jwwbi\"><area shape=\"rect\" coords=\"4,0,2033,2372\" target=\"_blank\" href=\"http://quedosimple.com.ar/42ad94b08b177e9100f558e346fcef30e3896d8121722e205d4dc5e7714e27f8d9b2f0b100c4b670235e4a0b306786f4d09564d5ec1a515e014e1aad2dd55f69\"></map>" +
				"<map name=\"hulot\"><area shape=\"rect\" coords=\"1,2,2730,2125\" target=\"_blank\" href=\"http://quedosimple.com.ar/6b939745bf8ca8988eb279e4766a6125ab3d3eaa29e07ef3465ed38dd06c8e6f29ade2646b243326a5351a149c2d49de14f131683069d943083a120fc8c4a5a6\"></map>" +
				"<table height=\"319\" width=\"493\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://quedosimple.com.ar/d9856a55a89079ea7ad431659fc1a97c11cb37a6efc992c7e23c402bf3bec129c0a5567744bd0d4d6ef24617cf3d9b2e9cf0995058d79d89a0b473184a5ee97a\" usemap=\"#jwwbi\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://quedosimple.com.ar/bde01a8b2c3dc038725f35674a8d644b3a4c75b19ed5166915e01739fc412711b347aa642f187caca9e92444ca02c769db082509d91fac4d0756c3295b51b75c\" usemap=\"#hulot\"></td></tr></table><br />" +
				"</body>" +
				"</html>"
			};
		}
		
		public interface _1553 {
			
			public static final String number = "1553";
			
			public static final String[] froms = { 
				"eSolar Program",
				"eSolarProgram"
			};
	
			public static final String[] subjects = {
				"2015 Program Lowers Cost of Electricity for Qualifying Homeowners",
				"Up to 65% Off Your Home Energy Bills - Act Now!",
				"Want lower electric bills?",
				"Up to 65% Off Your Home Energy Bills!",
				"Government Program Helps Homeowners Save Up to 65% Savings on Electricity",
				"Government Program Helps Lowers Energy Costs - Apply now!"
			};
	
			public static final String[] bodies = {
				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://collowed.info/ef6293e900b4f9bda05bfaf59de62145114f57e0cdaa4bff735f42c2aa38e5d5467214e4a4ed8b718c8f2b96d576a27ccbd86a3ea99a872511e9d255b278952a\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"avssu\"><area shape=\"rect\" coords=\"2,3,2889,2363\" target=\"_blank\" href=\"http://collowed.info/ef6293e900b4f9bda05bfaf59de62145114f57e0cdaa4bff735f42c2aa38e5d5467214e4a4ed8b718c8f2b96d576a27ccbd86a3ea99a872511e9d255b278952a\"></map>" +
				"<map name=\"bzycb\"><area shape=\"rect\" coords=\"2,3,2537,2804\" target=\"_blank\" href=\"http://collowed.info/3cee828f1a3684db879ef51d3267251d0b943304fea75266d9bcfc35854567eaafdcfc3d0cc337557d0af0d51911b92cc4a42a2ec504ba975f104848ad467ae9\"></map>" +
				"<table height=\"396\" width=\"448\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://collowed.info/b1bb0b42b801bd17b6b1537a691ce550372d18eb276ba57deb7837f26a31d04d76d16c39e2e4ed82324ec771d49d01d8320c203804891fd5ef42af3a6bcaa5fe\" usemap=\"#avssu\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://collowed.info/17f7356663f12d8daae6c4a808790b34b8a2e924b4532e07cc317b7b1004ba401152eb9f3e48bb67ce2c403447496e63fe817a323cbd9b3cf1605f56a1f0edbd\" usemap=\"#bzycb\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://catholocausing.info/910017b3d681e7715d84a520cd4db3b0feceed0a137fe499a9b134af7fde5c9635507b5436fcad5c218eb90ff33658cf6ccc87b058febe48a5776fc094c06bbb\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"rrkxd\"><area shape=\"rect\" coords=\"1,0,2942,2049\" target=\"_blank\" href=\"http://catholocausing.info/910017b3d681e7715d84a520cd4db3b0feceed0a137fe499a9b134af7fde5c9635507b5436fcad5c218eb90ff33658cf6ccc87b058febe48a5776fc094c06bbb\"></map>" +
				"<map name=\"dozrx\"><area shape=\"rect\" coords=\"2,3,2991,2737\" target=\"_blank\" href=\"http://catholocausing.info/d95f133949d8fb5ad09ffeb2f289b6a701d9f594727dda4f0b515544b291d1e2e44608730a4863d9b84bf32cd3c1c3c1fdb902125f67a3fdd2c6c30612e18bb5\"></map>" +
				"<table height=\"341\" width=\"409\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://catholocausing.info/3b52cc1eec45bacfa3b7ac1c49199135a13f0e54d78bedd83beb75e77ac55f8788203d9b505339eddc51f04b3390a2d6e52c01cd663ea683cffe264e82ed265d\" usemap=\"#rrkxd\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://catholocausing.info/fac20e89a404eee5ab2ad662d09ab899fa3a9772196b6c012db88079b2d6caef1d5075183dc614fb3773be3b99887613e80e2603f577e40ad1d262b2b416c704\" usemap=\"#dozrx\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://geneveral.info/58e8aec83f4996465f353e0d6ed90215f109b4bc89f6bb7d841dd611dd790fa470da6cdb11062c2495e8de92d7e699b118d6a7c2b23cce19261439b30170dbd9\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"htqgo\"><area shape=\"rect\" coords=\"2,1,2043,2498\" target=\"_blank\" href=\"http://geneveral.info/58e8aec83f4996465f353e0d6ed90215f109b4bc89f6bb7d841dd611dd790fa470da6cdb11062c2495e8de92d7e699b118d6a7c2b23cce19261439b30170dbd9\"></map>" +
				"<map name=\"zrbmh\"><area shape=\"rect\" coords=\"0,0,2251,2395\" target=\"_blank\" href=\"http://geneveral.info/08a2db0914a08deff8d844e1119975aa49925c6a37c3d76b38f415e63d62a7a3b482f53b814022a5753b8b8160c168cdfaed4bc47fd6c26289f9d1a64e77a2fe\"></map>" +
				"<table height=\"332\" width=\"407\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://geneveral.info/aa78701fcc9a75a344f4047196efff7fec972ef64317bae02cab9df5539f32befb85a0b51819097920e201f0eb683380cc7a1d1ec7569589a0fcc688582068cf\" usemap=\"#htqgo\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://geneveral.info/b555e58e40984bc10294cfe4862e06a32cf7a79b4d56d27740b43df73002e9c9275715f5cc4538cb3db6e0b04b3210cd69c2dbe8d447c95de1c01405c958dfd2\" usemap=\"#zrbmh\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://calculum.info/92e6afd80f8047a599e61202043c5cc09fed519c24de15f600ec1152e0b8c72336b637d194f580081ca4c214acd52beb302b71fa2cb8974bde4d96b009f52fa3\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"ahucf\"><area shape=\"rect\" coords=\"1,0,2499,2150\" target=\"_blank\" href=\"http://calculum.info/92e6afd80f8047a599e61202043c5cc09fed519c24de15f600ec1152e0b8c72336b637d194f580081ca4c214acd52beb302b71fa2cb8974bde4d96b009f52fa3\"></map>" +
				"<map name=\"sxdkk\"><area shape=\"rect\" coords=\"4,0,2570,2673\" target=\"_blank\" href=\"http://calculum.info/61997e437219b85ec0121281051fc20728c0c4917b3c0ff30d4bfbe1671d546b38ae9dac2ac8f19a5bacce87fa25670d42eef2df548052e34e81826193ecc7f9\"></map>" +
				"<table height=\"311\" width=\"499\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://calculum.info/de7690ed8de0b1bec7eba292ea9b01665f23878a8774a33caecb4a3bacfea6cf7282be848178921e60c01ffdb4b7fb25f23fc58418bf336c3e3f0aaa66d1897a\" usemap=\"#ahucf\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://calculum.info/8619faa8cf94b15b2d102b214c004740acd498d78f732d6b58a49094163ade1d24346722b8e53a1c148b72a0b2a73cabb1a0cf9e2b4a599eb7be5178afa25896\" usemap=\"#sxdkk\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://tichess.info/9764b9bd79b2f16dd301ae4077b78a24f24219a53b4fc712dc4397004170a05bed3c8f97b35d933ad8af04d5a46cbc739404ba029f0735280f1dc963da58cf41\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"ytdbh\"><area shape=\"rect\" coords=\"1,1,2646,2126\" target=\"_blank\" href=\"http://tichess.info/9764b9bd79b2f16dd301ae4077b78a24f24219a53b4fc712dc4397004170a05bed3c8f97b35d933ad8af04d5a46cbc739404ba029f0735280f1dc963da58cf41\"></map>" +
				"<map name=\"aywdi\"><area shape=\"rect\" coords=\"1,3,2662,2425\" target=\"_blank\" href=\"http://tichess.info/9bec678a6b529d1e0dbe44bb3ad17e30acdc3ae5e515a61950d6c70b359489918db373be54a2de3bffc90c2304a2d66fc8cd7cd56242c151adb37ee358df3856\"></map>" +
				"<table height=\"346\" width=\"465\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://tichess.info/47dd25b4d14ebc14c1213d4777e2bd5602fb6fef0bdc01fafc15b1a9a12addebef0cbeb882ba53d37ed445daf186910e40a875bd1a76155936672ed3a04564a8\" usemap=\"#ytdbh\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://tichess.info/bb1da3db1ec1a0c819f3f77e37df65a511fb412948473d4b45410162709d3f2e0342963a9cb751176a034f0350981c17c6de4d55f777a909f38d5db5e4951fca\" usemap=\"#aywdi\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://localiform.info/92e6afd80f8047a599e61202043c5cc09fed519c24de15f600ec1152e0b8c723419d3e54a594bd0bf1dbd1159b11335196d8031ef1b13c60a05dfcce28de7d98\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"hlcot\"><area shape=\"rect\" coords=\"0,2,2147,2658\" target=\"_blank\" href=\"http://localiform.info/92e6afd80f8047a599e61202043c5cc09fed519c24de15f600ec1152e0b8c723419d3e54a594bd0bf1dbd1159b11335196d8031ef1b13c60a05dfcce28de7d98\"></map>" +
				"<map name=\"spzeq\"><area shape=\"rect\" coords=\"4,3,2053,2015\" target=\"_blank\" href=\"http://localiform.info/61997e437219b85ec0121281051fc20728c0c4917b3c0ff30d4bfbe1671d546b1d9a4beb4e1cb85348158b80e9570b6615559f2bd44e3ff6256fd843d2245a79\"></map>" +
				"<table height=\"314\" width=\"419\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://localiform.info/de7690ed8de0b1bec7eba292ea9b01665f23878a8774a33caecb4a3bacfea6cfb954b0561dade725221676057411d1bee0351344eadd2b1259995f4e20888922\" usemap=\"#hlcot\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://localiform.info/8619faa8cf94b15b2d102b214c004740acd498d78f732d6b58a49094163ade1d0ca1f943dae817e5cdf5d6cdb964378dbe67d60c60b29d1467ac9da6d19d1f53\" usemap=\"#spzeq\"></td></tr></table><br />" +
				"</body>" +
				"</html>",

				"<html>" +
				"<body bgcolor=\"#FFFFFF\"><br />" +
				"<h1><center><a target=\"_blank\" href=\"http://natasterfahrten.info/92e6afd80f8047a599e61202043c5cc09fed519c24de15f600ec1152e0b8c7233716fb6041b48b87c041c6a981206f0aeca6335804eb75a4638829833bd31904\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
				"<map name=\"cisue\"><area shape=\"rect\" coords=\"2,3,2973,2841\" target=\"_blank\" href=\"http://natasterfahrten.info/92e6afd80f8047a599e61202043c5cc09fed519c24de15f600ec1152e0b8c7233716fb6041b48b87c041c6a981206f0aeca6335804eb75a4638829833bd31904\"></map>" +
				"<map name=\"kvuzl\"><area shape=\"rect\" coords=\"1,2,2929,2208\" target=\"_blank\" href=\"http://natasterfahrten.info/61997e437219b85ec0121281051fc20728c0c4917b3c0ff30d4bfbe1671d546b9ee994cf733ad62972e4073ba17da7ad801ab121d1dec3f2d473ad880bd26d8c\"></map>" +
				"<table height=\"385\" width=\"449\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
				"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content" + randomString + "\"  title=\"content" + randomString + "\"  src=\"http://natasterfahrten.info/de7690ed8de0b1bec7eba292ea9b01665f23878a8774a33caecb4a3bacfea6cf243ce7c5d7766fbaec3027dc7381ffce0064a1e9ff08479d0ef6778db61e6825\" usemap=\"#cisue\"></td></tr>" +
				"<tr><td align=\"center\"><img style=\"display:block\" alt=\"unsubscribe" + randomString + "\" title=\"unsubscribe" + randomString + "\" src=\"http://natasterfahrten.info/8619faa8cf94b15b2d102b214c004740acd498d78f732d6b58a49094163ade1dca710d3cfef93366b5574ee60c0962d361517aae8fd8bce785abf37455876651\" usemap=\"#kvuzl\"></td></tr></table><br />" +
				"</body>" +
				"</html>"
			};
		}
	}

}
