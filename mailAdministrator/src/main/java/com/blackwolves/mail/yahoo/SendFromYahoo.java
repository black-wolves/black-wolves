package com.blackwolves.mail.yahoo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendFromYahoo {
	
	public static void main(String[] args) {
		// Sender's email ID needs to be mentioned
		String from = "The Zebra <gaston@thewolvesareback.info>";
		String user = "gaston@thewolvesareback.info";
		String pass = "wolf2015.2";
		// Recipient's email ID needs to be mentioned.
		String to = "yaninadefays02@yahoo.com";
		String host = "smtp.bizmail.yahoo.com";

		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", user);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("You're eligible to save on car insurance");
			
			String htmlContent = "<html><body bgcolor=\"#FFFFFF\"><br />" +
					"<h1><center><a target=\"_blank\" href=\"http://eeyouagainwhenseee.info/b6ddd052210dec1b620159f8caad32d149415de829f2f4f86d9b0cdb6fa7efe6e891647f46b15a11dacd7ec9219a38a76f014d22a60e2eda56941b2469eeb1d0\"><font color=\"#FF0000\"></font></a></center></h1><br />" +
					"<map name=\"fbygd\"><area shape=\"rect\" coords=\"4,3,2736,2138\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/b6ddd052210dec1b620159f8caad32d149415de829f2f4f86d9b0cdb6fa7efe6e891647f46b15a11dacd7ec9219a38a76f014d22a60e2eda56941b2469eeb1d0\"></map>" +
					"<map name=\"xdeyo\"><area shape=\"rect\" coords=\"0,4,2914,2227\" target=\"_blank\" href=\"http://eeyouagainwhenseee.info/8ad078851cae9e44166bae0dabe48a3ac6012210111b048f5f27c337872405d560f881a443734feef365c7049f49827f16dc208dfe10b7e3c9938be358980913\"></map>" +
					"<table height=\"313\" width=\"434\" align=\"center\" border=\"0\" bgcolor=\"#2E9AFE\" cellspacing=\"0\" cellpadding=\"0\">" +
					"<tr><td bgcolor=\"#FFFFFF\" align=\"center\" ><p>Please click the \"Show Images\" button above if you cannot see the content.</p></td></tr>" +
					"<tr><td align=\"center\"><img style=\"display:block\" alt=\"content\" title=\"content\" src=\"http://eeyouagainwhenseee.info/234355816be268528d67de557a282bf94c120bba9eedbe90cd871b57058bfe3e71b023451dd0707ca12fdbf41d6225d349bdd77d1382b6b878c7d5b7f2359a7c\" usemap=\"#fbygd\"></td></tr>" +
					"<tr><td align=\"center\"><img style=\"display:block\" alt=\"optout\" title=\"optout\" src=\"http://eeyouagainwhenseee.info/a581cb3c49f6f01824e9c240449c2996a0dfc8bb24cfff5ef6b5d28dc140b6db4ab25f289009efbce9f122c76f4d67273148d40df6a8e202cb7cd0486bfe8bab\" usemap=\"#xdeyo\"></td></tr></table><br />" +
					"</body></html>";

			// Now set the actual message
			message.setContent(htmlContent, "text/html; charset=utf-8");

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}