package com.blackwolves.mail.yahoo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.mail.util.Constant;

/**
 * 
 * @author gastondapice
 *
 */
public class SendFromYahoo {
	
	private static Logger logger = LoggerFactory.getLogger(SendFromYahoo.class);

	private static SendFromYahoo instance = null;

	protected SendFromYahoo() {
	}

	public static SendFromYahoo getInstance() {
		if (instance == null) {
			instance = new SendFromYahoo();
		}
		return instance;
	}

	public void generateAndSendMail(String user, String pass, String offerFrom, String to, String subject, String body) {
		
		String from = offerFrom + " <" + user + ">";

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

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);
			
			// Now set the actual message
			message.setContent(body, Constant.Yahoo.CONTENT_TYPE);
			
			message.setHeader( "Content-Transfer-Encoding", Constant.Yahoo.CONTENT_TRANSFER_ENCODING);
//			message.setHeader( "Message-ID", Constant.EMPTY_STRING);
//			message.setHeader( "Message-Id", Constant.EMPTY_STRING);
//			message.setHeader( "message-id", Constant.EMPTY_STRING);
//			message.setHeader( "MESSAGE-ID", Constant.EMPTY_STRING);
			message.setHeader("X-Priority", "1");
//			message.setHeader("Priority", "Urgent");
//			message.setHeader("Importance", "High");
			
//			message.removeHeader( "Message-ID");
//			message.removeHeader( "Message-Id");
//			message.removeHeader( "message-id");
//			message.removeHeader( "MESSAGE-ID");

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(Constant.Yahoo.HOST, user, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully....");
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}
}