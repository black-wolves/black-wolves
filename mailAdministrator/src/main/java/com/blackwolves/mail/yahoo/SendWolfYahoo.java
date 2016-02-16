package com.blackwolves.mail.yahoo;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.blackwolves.mail.CustomFrom;
import com.blackwolves.mail.CustomMimeMessage;
import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public class SendWolfYahoo extends WolfYahoo{

	/**
	 * 
	 */
	@Override
	public void generateAndSendEmail(String user, String pass, String subject, String body, String contactEmail, String domain, String offerFrom) throws MessagingException, AuthenticationFailedException {

		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", Constant.TRUE);
		properties.put("mail.smtp.host", Constant.Yahoo.HOST);
		properties.put("mail.smtp.user", user);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", Constant.Yahoo.PORT);
		properties.put("mail.smtp.auth", Constant.TRUE);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		// Create a default MimeMessage object.
		MimeMessage message = new CustomMimeMessage(session, domain);
//		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		//message.setFrom(new InternetAddress(from));
		CustomFrom customFrom = new CustomFrom(contactEmail, user, offerFrom);
		message.setFrom(customFrom);

		// Set Subject: header field
		message.setSubject(subject);

		// Now set the actual message
//		MimeBodyPart textPart = new MimeBodyPart();
//		textPart.setContent(body, Constant.Yahoo.CONTENT_TYPE);
//		message.setHeader("Content-Transfer-Encoding", Constant.Yahoo.CONTENT_TRANSFER_ENCODING);
//		message.setHeader("Content-Transfer-Encoding", "quoted-printable");
//		Multipart mp = new MimeMultipart();
//		mp.addBodyPart(textPart);
//		message.setContent(mp);
		message.setContent(body, Constant.Yahoo.CONTENT_TYPE);

		Address [] toAd =  new Address[1] ;
		toAd[0] =  new InternetAddress(contactEmail);
//		toAd[0] =  new InternetAddress("gastondapice@yahoo.com");
		message.addRecipient(Message.RecipientType.TO, toAd[0]);

		// Send message
		Transport transport = session.getTransport("smtp");
		transport.connect(Constant.Yahoo.HOST, user, pass);
		transport.sendMessage(message,message.getAllRecipients());
		transport.close();
	}

	@Override
	public void downloadAndGenerateDropBodies(String offer, String seed,
			String pass) {
		// TODO Auto-generated method stub
		
	}
}