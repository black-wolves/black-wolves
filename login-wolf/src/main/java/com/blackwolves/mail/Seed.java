package com.blackwolves.mail;

import java.sql.Timestamp;

import com.blackwolves.mail.util.Constant;

/**
 * @author gastondapice
 *
 */
public class Seed {

	private String user;
	private String password;
	private String fullSeed;
	private int mailCount;
	private int opened;
	private int clicked;
	private int spammed;
	private int notSpammed;
	private Timestamp feederUpdatedDate;
	private Timestamp seederUpdatedDate;
	private String subscription = new String();
	private String newUser;
	private String firstName;
	private String lastName;
	private String gender;
	private String birthDate;
	private boolean validated;
	private boolean fbRegistered;
	private boolean fbConfirmed;
	
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
			int clicked, int spammed,int notSpammed ,Timestamp feederUpdatedDate,
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
	 * 
	 * @param user
	 * @param password
	 * @param fullSeed
	 */
	public Seed(String user, String password, String fullSeed) {
		this.user = user;
		this.password = password;
		this.fullSeed = fullSeed;
	}

	/**
	 * 
	 * @param user
	 * @param password
	 * @param fullSeed
	 * @param feederUpdatedDate
	 * @param seederUpdatedDate
	 * @param subscription
	 */
	public Seed(String user, String password, String fullSeed,
			Timestamp feederUpdatedDate, Timestamp seederUpdatedDate,
			String subscription) {
		this.user = user;
		this.password = password;
		this.fullSeed = fullSeed;
		this.feederUpdatedDate = feederUpdatedDate;
		this.seederUpdatedDate = seederUpdatedDate;
		this.subscription = subscription;
	}

	/**
	 * 
	 * @param user
	 * @param password
	 * @param fullSeed
	 * @param subscription
	 */
	public Seed(String user, String password, String fullSeed, String subscription) {
		this.user = user;
		this.password = password;
		this.fullSeed = fullSeed;
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
	 * @return the fullSeed
	 */
	public String getFullSeed() {
		return fullSeed;
	}

	/**
	 * @param fullSeed the fullSeed to set
	 */
	public void setFullSeed(String fullSeed) {
		this.fullSeed = fullSeed;
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
	 * @return the notSpammed
	 */
	public int getNotSpammed() {
		return notSpammed;
	}

	/**
	 * @param notSpammed the notSpammed to set
	 */
	public void setNotSpammed(int notSpammed) {
		this.notSpammed = notSpammed;
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

	/**
	 * @return the subscription
	 */
	public String getSubscription() {
		return subscription;
	}

	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	/**
	 * @return the newUser
	 */
	public String getNewUser() {
		return newUser;
	}

	/**
	 * @param newUser the newUser to set
	 */
	public void setNewUser(String newUser) {
		this.newUser = newUser;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the validated
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * @param validated the validated to set
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	/**
	 * @return the fbRegistered
	 */
	public boolean isFbRegistered() {
		return fbRegistered;
	}

	/**
	 * @param fbRegistered the fbRegistered to set
	 */
	public void setFbRegistered(boolean fbRegistered) {
		this.fbRegistered = fbRegistered;
	}

	/**
	 * @return the fbConfirmed
	 */
	public boolean isFbConfirmed() {
		return fbConfirmed;
	}

	/**
	 * @param fbConfirmed the fbConfirmed to set
	 */
	public void setFbConfirmed(boolean fbConfirmed) {
		this.fbConfirmed = fbConfirmed;
	}
	
	public String getDayOfBirth(){
		String[] s = getBirthDate().split(Constant.DASH);
		return s[2];
	}
	
	public String getMonthOfBirth(){
		String[] s = getBirthDate().split(Constant.DASH);
		return s[1];
	}
	
	public String getYearOfBirth(){
		String[] s = getBirthDate().split(Constant.DASH);
		return s[0];
	}
	
}