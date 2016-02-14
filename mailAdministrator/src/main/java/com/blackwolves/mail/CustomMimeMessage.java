package com.blackwolves.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class CustomMimeMessage extends MimeMessage {
	Session session;
	static String senderDomain;
	private static int id = 0;

	public CustomMimeMessage(Session session, String senderDomain) {
		super(session);
		this.session = session;
		CustomMimeMessage.senderDomain = senderDomain;
	}

	@Override
	protected void updateMessageID() throws MessagingException {
		setHeader("Message-ID", "<" + getUniqueMessageIDValue(session) + ">");
	}

	public static String getUniqueMessageIDValue(Session ssn) {
		StringBuffer s = new StringBuffer();

		// Unique string is <hashcode>.<id>.<currentTime>.JavaMail.<suffix>
		s.append(s.hashCode()).append('.').append(getUniqueId()).append('.').append(System.currentTimeMillis())
				.append("@" + senderDomain);
		return s.toString();
	}

	private static synchronized int getUniqueId() {
		return id++;
	}
}