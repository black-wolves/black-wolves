/**
 * 
 */
package com.blackwolves.mail;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

/**
 * @author gaston.dapice
 *
 */
public class EmailReader {

	public static void main(String args[]) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			session.setDebug(true);
			// IMAP host for gmail.
			// Replace <username> with the valid username of your Email ID.
			// Replace <password> with a valid password of your Email ID.

			// store.connect("imap.gmail.com", "<username>", "<password>");

			// IMAP host for yahoo.
			store.connect("imap.mail.yahoo.com", "yaninadefays01", "madrivorules");

			System.out.println(store);
			Folder spam = store.getFolder("Bulk Mail");
			spam.open(Folder.READ_WRITE);
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_WRITE);
			showMails(inbox, spam);
			spam.close(true);
			inbox.close(true);
			store.close();

		} catch (NoSuchProviderException e) {
			System.out.println(e.toString());
			System.exit(1);
		} catch (MessagingException e) {
			System.out.println(e.toString());
			System.exit(2);
		}

	}
	
	/**
	 * @param inbox
	 * @param spam 
	 */
	private static void showMails(Folder inbox, Folder spam) {
		try {
			Message msg[] = spam.getMessages();
			System.out.println("MAILS: " + msg.length);
			String flags[] = inbox.getPermanentFlags().getUserFlags();
			for (String flag : flags) {
				System.out.println(flag);
			}
			for (Message message : msg) {
				try {
//					message.setFlag(Flags.Flag.FLAGGED, true);
					message.setFlags(new Flags("$NotJunk"), true);
					System.out.println("DATE: " + message.getSentDate().toString());
					System.out.println("FROM: " + message.getFrom()[0].toString());
					System.out.println("SUBJECT: " + message.getSubject().toString());
					System.out.println("CONTENT: " + message.getContent().toString());
					System.out.println("******************************************");
					message.saveChanges();
				} catch (Exception e) {
					System.out.println("No Information");
				}
			}
//			spam.copyMessages(msg, inbox);
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}

	static public void showUnreadMails(Folder inbox) {
		try {
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message msg[] = inbox.search(ft);
			System.out.println("MAILS: " + msg.length);
			for (Message message : msg) {
				try {
					System.out.println("DATE: " + message.getSentDate().toString());
					System.out.println("FROM: " + message.getFrom()[0].toString());
					System.out.println("SUBJECT: " + message.getSubject().toString());
					System.out.println("CONTENT: " + message.getContent().toString());
					System.out.println("******************************************");
				} catch (Exception e) {
					System.out.println("No Information");
				}
			}
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}

}