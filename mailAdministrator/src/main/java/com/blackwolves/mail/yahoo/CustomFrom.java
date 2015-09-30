package com.blackwolves.mail.yahoo;

import javax.mail.Address;

public class CustomFrom extends Address {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String from;
	private String customer ;
	private String senderMail;
	private String title;

	public CustomFrom(String customer, String senderMail, String title) {
		super();
		this.customer = customer;
		this.senderMail = senderMail;
		this.title = title;
		
	}

	@Override
	public String toString() {
		from = "\"=?ISO-8859-15?Q?"+title+"?=\" \n";
		from += " \"=?ISO-8859-15?Q?|"+customer+"|?=\"\n <"+senderMail+">";

		return from;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "rfc822";

	}

	@Override
	public boolean equals(Object address) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
