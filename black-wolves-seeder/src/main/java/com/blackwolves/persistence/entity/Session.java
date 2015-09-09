package com.blackwolves.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


public class Session implements Serializable {

	private static final long serialVersionUID = -16612492652696753L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "session_gen")
	@SequenceGenerator(name = "session_gen", sequenceName = "SESSION_SEQ")
	@Column(name = "SESS_ID")
	private Long id;
	
	@Column(name = "SESS_IP", nullable = false)
	private String ip;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SESS_LAST_DATE")
    private Date lastDate;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "SESSION_ACTION"
				, joinColumns = { @JoinColumn(name = "SSAT_SESS_ID"
						, foreignKey = @ForeignKey(name = "FK_SESSION_ACTION_01")) }
				, inverseJoinColumns = { @JoinColumn(name = "SSAT_ACTN_ID"
						, foreignKey = @ForeignKey(name = "FK_SESSION_ACTION_02")) }
				, uniqueConstraints = { @UniqueConstraint(columnNames = { "SSAT_SESS_ID", "SSAT_ACTN_ID" }) })
	private Set<Action> actions;
	
	/**
	 * Constructor
	 */
	public Session() {
		this.lastDate = new Date();
	}
	
	/**
	 * Constructor
	 */
	public Session(String ip) {
		this.ip = ip;
		this.lastDate = new Date();
	}

	/**
	 * Constructor
	 */
	public Session(Date lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * Constructor
	 */
	public Session(String ip, Date lastDate, Set<Action> actions) {
		this.ip = ip;
		this.lastDate = lastDate;
		this.actions = actions;
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the lastDate
	 */
	public Date getLastDate() {
		return lastDate;
	}

	/**
	 * @param lastDate the lastDate to set
	 */
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @return the actions
	 */
	public Set<Action> getActions() {
		if(actions==null){
			actions =  new HashSet<Action>();
		}
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(Set<Action> actions) {
		this.actions = actions;
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
		Session other = (Session) obj;
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
