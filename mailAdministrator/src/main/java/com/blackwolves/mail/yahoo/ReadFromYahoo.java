package com.blackwolves.mail.yahoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ReadFromYahoo {

	private static final Logger logger = LogManager
			.getLogger(ReadFromYahoo.class.getName());

	private static final String IMAP_YAHOO = "imap.mail.yahoo.com";
	private static final String ROUTE = "/var/www/";
	private static final String INBOX = "Inbox";
	private static final String SPAM = "Bulk Mail";

	public static void main(String[] args) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		try {
			Store store = session.getStore("imaps");
			// IMAP host for yahoo.
			store.connect(IMAP_YAHOO, "yaninadefays02@yahoo.com", "wolf2015.2");
			Folder inbox = store.getFolder(INBOX);
			inbox.open(Folder.READ_ONLY);
			Message msg[] = inbox.getMessages();
			for (Message message : msg) {

				Enumeration headers = message.getAllHeaders();
				while (headers.hasMoreElements()) {
					Header h = (Header) headers.nextElement();
					System.out.println(h.getName() + ": " + h.getValue());
				}

//				System.out.println(message.getContent());
				System.out.println(getStringFromInputStream(message.getInputStream()));
			}
			inbox.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	private static String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			boolean textIsHtml = p.isMimeType("text/html");
			if(p.isMimeType("text/html")){
				return s;
			}
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}
}