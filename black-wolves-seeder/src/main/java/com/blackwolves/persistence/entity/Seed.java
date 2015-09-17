package com.blackwolves.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Seed implements Serializable {

	private static final long serialVersionUID = 5426661713841147777L;

	private Long id;

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private boolean myTurn = true;
	
	private int pid;
	
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private Date wakeUp;
	
	private Date lastSession;
	
	private Set<Subscription> subscriptions;
	
	/**
	 * Constructor
	 */
	public Seed() {}
	
	/**
	 * Constructor
	 * @param email
	 * @param password
	 */
	public Seed(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Constructor
	 * @param email
	 * @param password
	 * @param ip
	 */
	public Seed(String email, String password, String ip) {
		this.email = email;
		this.password = password;
		this.ip = ip;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the myTurn
	 */
	public boolean isMyTurn() {
		return myTurn;
	}

	/**
	 * @param myTurn the myTurn to set
	 */
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}

	/**
	 * @return the wakeUp
	 */
	public Date getWakeUp() {
		return wakeUp;
	}

	/**
	 * @param wakeUp the wakeUp to set
	 */
	public void setWakeUp(Date wakeUp) {
		this.wakeUp = wakeUp;
	}

	/**
	 * @return the lastSession
	 */
	public Date getLastSession() {
		return lastSession;
	}

	/**
	 * @param lastSession the lastSession to set
	 */
	public void setLastSession(Date lastSession) {
		this.lastSession = lastSession;
	}

	/**
	 * @return the subscriptions
	 */
	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	/**
	 * @param subscriptions the subscriptions to set
	 */
	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Seed other = (Seed) obj;
		if (id == null) {
			if (other.id != null){
				return false;
			}
		} else if (!id.equals(other.id)){
			return false;
		}
		return true;
	}

}
