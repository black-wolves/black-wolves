/**
 * 
 */
package com.blackwolves.mail.yahoo;

import java.io.PrintWriter;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public class TestWolfYahoo extends WolfYahoo {

	/* (non-Javadoc)
	 * @see com.blackwolves.mail.yahoo.WolfYahoo#generateAndSendMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void generateAndSendMail(String user, String pass, String offerFrom,
			String to, String subject, String body) {
		super.generateAndSendMail(user, pass, offerFrom, to, subject, body);

	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.mail.yahoo.WolfYahoo#readEmailsAndGenerateBodies(java.lang.String, int, int)
	 */
	@Override
	public void readEmailsAndGenerateBodies(String offer, int from, int to) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		try {
			Store store = session.getStore("imaps");
			// IMAP host for yahoo.
			store.connect(Constant.Yahoo.IMAP_YAHOO, "yaninadefays02@yahoo.com", "wolf2015.2");
			Folder bodiesFolder = store.getFolder(Constant.Yahoo.INBOX);
			bodiesFolder.open(Folder.READ_ONLY);
			Message msg[] = bodiesFolder.getMessages();
			StringBuilder mail = new StringBuilder();
			String vmta = "awu9";
			mail = new StringBuilder();
			mail.append("x-virtual-mta: " + vmta);
			mail.append("\n");
			mail.append("x-receiver: tatigrane@yahoo.com");
			mail.append("\n");
			mail.append("x-receiver: yaninadefays02@yahoo.com");
			mail.append("\n");
			mail.append("x-receiver: danielsaulino03@yahoo.com");
			int radomBody = randInt(0, msg.length-1);
			Message message = msg[radomBody];
			iterateHeaders(message, mail);
			mail.append("\n");
			mail.append("\n");
			mail.append(message.getContent());
			PrintWriter out = new PrintWriter(Constant.Yahoo.PICKUP_ROUTE + "test"+ UUID.randomUUID());
			out.println(mail);
			out.close();
			bodiesFolder.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
