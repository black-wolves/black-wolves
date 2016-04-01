/**
 * 
 */
package com.blackwolves.seeder;

import java.sql.Timestamp;

/**
 * @author gastondapice
 *
 */
public class Seed {

	private String user;
	private String password;
	private int mailCount;
	private int opened;
	private int clicked;
	private int spammed;
	private Timestamp feederUpdatedDate;
	private Timestamp seederUpdatedDate;
	private String subscription = new String();
	
	/**
	 * @param user
	 * @param password
	 * @param mailCount
	 * @param opened
	 * @param clicked
	 * @param spammed
	 * @param feederUpdatedDate
	 * @param seederUpdatedDate
	 * @param subscriptions 
	 */
	public Seed(String user, String password, int mailCount, int opened,
			int clicked, int spammed, Timestamp feederUpdatedDate,
			Timestamp seederUpdatedDate, String subscription) {
		this.user = user;
		this.password = password;
		this.mailCount = mailCount;
		this.opened = opened;
		this.clicked = clicked;
		this.spammed = spammed;
		this.feederUpdatedDate = feederUpdatedDate;
		this.seederUpdatedDate = seederUpdatedDate;
		this.subscription = subscription;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the mailCount
	 */
	public int getMailCount() {
		return mailCount;
	}
	/**
	 * @param mailCount the mailCount to set
	 */
	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}
	/**
	 * @return the opened
	 */
	public int getOpened() {
		return opened;
	}
	/**
	 * @param opened the opened to set
	 */
	public void setOpened(int opened) {
		this.opened = opened;
	}
	/**
	 * @return the clicked
	 */
	public int getClicked() {
		return clicked;
	}
	/**
	 * @param clicked the clicked to set
	 */
	public void setClicked(int clicked) {
		this.clicked = clicked;
	}
	/**
	 * @return the spammed
	 */
	public int getSpammed() {
		return spammed;
	}
	/**
	 * @param spammed the spammed to set
	 */
	public void setSpammed(int spammed) {
		this.spammed = spammed;
	}
	/**
	 * @return the feederUpdatedDate
	 */
	public Timestamp getFeederUpdatedDate() {
		return feederUpdatedDate;
	}
	/**
	 * @param feederUpdatedDate the feederUpdatedDate to set
	 */
	public void setFeederUpdatedDate(Timestamp feederUpdatedDate) {
		this.feederUpdatedDate = feederUpdatedDate;
	}
	/**
	 * @return the seederUpdatedDate
	 */
	public Timestamp getSeederUpdatedDate() {
		return seederUpdatedDate;
	}
	/**
	 * @param seederUpdatedDate the seederUpdatedDate to set
	 */
	public void setSeederUpdatedDate(Timestamp seederUpdatedDate) {
		this.seederUpdatedDate = seederUpdatedDate;
	}
	public String getSubscription() {
		return subscription;
	}
	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	
}
