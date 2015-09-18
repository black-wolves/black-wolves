/**
 * 
 */
package com.blackwolves.mail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author gaston.dapice
 *
 */
public class BodyEmail {

	private static final Logger logger = LogManager.getLogger(BodyEmail.class.getName());
	
	private static final String IMAP_YAHOO = "imap.mail.yahoo.com";
	private static final String ROUTE = "/var/www/";
	private static final String INBOX = "Inbox";
	private static final String SPAM = "Bulk Mail";

	public static void main(String args[]) {
		
		sendEmail();
		
//		sendEmail2();
		
//		readEmail();

	}

	private static void sendEmail() {
		final String username = "gaston@thewolvesareback.info";
		final String password = "wolf2015.2";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.bizmail.yahoo.com");
		props.put("mail.smtp.port", "465");
		
		props.put("mail.smtp.ssl.enable", "true");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("The Zebra <gaston@thewolvesareback.info>"));
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("tatigrane@yahoo.com"));
			message.setSubject("You're eligible to save on car insurance");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void sendEmail2(){
		// Recipient's email ID needs to be mentioned.
	      String to = "tatigrane@yahoo.com";

	      // Sender's email ID needs to be mentioned
	      String from = "The Zebra <gaston@thewolvesareback.info>";

	      // Assuming you are sending email from localhost
	      String host = "smtp.bizmail.yahoo.com";
	      
	      String port = "465";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);
	      
	      properties.setProperty("mail.smtp.port", port);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("This is the Subject Line!");

	         // Now set the actual message
	         message.setText("This is actual message");

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

	/**
	 * 
	 */
	private static void readEmail() {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		try{
			Store store = session.getStore("imaps");
			// IMAP host for yahoo.
			store.connect(IMAP_YAHOO, "yaninadefays02@yahoo.com", "wolf2015.2");
			Folder inbox = store.getFolder(INBOX);
			inbox.open(Folder.READ_ONLY);
			Message msg[] = inbox.getMessages();
			for (Message message : msg) {
				System.out.println(msg.toString());
			}
			inbox.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
}
