package com.blackwolves.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CustomMimeMessage extends MimeMessage {
	Session session;
	private static int id = 0;

	public CustomMimeMessage(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	protected void updateMessageID() throws MessagingException {
		setHeader("Message-ID", "<" + getUniqueMessageIDValue(session) + ">");
	}

	public static String getUniqueMessageIDValue(Session ssn) {
		String suffix = null;

		InternetAddress addr = InternetAddress.getLocalAddress(ssn);
		if (addr != null)
			suffix = addr.getAddress();
		else {
			suffix = "javamailuser@localhost"; // worst-case default
		}

		StringBuffer s = new StringBuffer();

		// Unique string is <hashcode>.<id>.<currentTime>.JavaMail.<suffix>
		s.append(s.hashCode()).append('.').append(getUniqueId()).append('.').append(System.currentTimeMillis())
				.append('.').append("mailer.").append(suffix);
		return s.toString();
	}

	private static synchronized int getUniqueId() {
		return id++;
	}
}