package com.blackwolves.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	String host, port, emailid, username, password;
	Properties props = System.getProperties();
	Session l_session = null;
	
	public static void main(String[] args) {
		new SendMail();
	}

	public SendMail() {
		host = "smtp.bizmail.yahoo.com";
//		host = "smtp.mail.yahoo.com";
		port = "465";
//		port = "587";
//		emailid = "yaninadefays02@yahoo.com";
		emailid = "gaston@thewolvesareback.info";
//		username = "yaninadefays02";
		username = "gaston";
		password = "wolf2015.2";

		emailSettings();
		createSession();
		sendMessage("a@yahoo.com", "rahul@gmail.com", "Test", "test Mail");
	}

	public void emailSettings() {
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", port);
//		props.put("mail.smtp.ssl.enable", "true");
//		props.put("mail.smtp.starttls.enable", "true");
		// props.put("mail.smtp.socketFactory.port", port);
		// props.put("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		// props.put("mail.smtp.socketFactory.fallback", "false");

	}

	public void createSession() {

		l_session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		l_session.setDebug(true); // Enable the debug mode

	}

	public boolean sendMessage(String emailFromUser, String toEmail,
			String subject, String msg) {
		// System.out.println("Inside sendMessage 2 :: >> ");
		try {
			// System.out.println("Sending Message *********************************** ");
			MimeMessage message = new MimeMessage(l_session);
			emailid = emailFromUser;
			// System.out.println("mail id in property ============= >>>>>>>>>>>>>> "
			// + emailid);
			// message.setFrom(new InternetAddress(emailid));
			message.setFrom(new InternetAddress(this.emailid));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toEmail));
			message.addRecipient(Message.RecipientType.BCC,
					new InternetAddress("tatigrane@yahoo.com"));
			message.setSubject(subject);
			message.setContent(msg, "text/html");

			// message.setText(msg);
			Transport.send(message);
			System.out.println("Message Sent");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}// end catch block
		return true;
	}

}
