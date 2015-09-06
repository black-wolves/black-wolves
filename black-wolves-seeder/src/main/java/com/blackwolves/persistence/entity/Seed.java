package com.blackwolves.persistence.entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SEED")
public class Seed implements Serializable {


	private static final long serialVersionUID = 5426661713841147777L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seed_gen")
	@SequenceGenerator(name = "seed_gen", sequenceName = "SEED_SEQ")
	@Column(name = "SEED_ID")
	private Long id;

	@Column(name = "SEED_FIRST_NAME")
	private String firstName;
	
	@Column(name = "SEED_LAST_NAME")
	private String lastName;
	
	@Column(name = "SEED_EMAIL", nullable = false)
	private String email;
	
	@Column(name = "SEED_PASSWORD", nullable = false)
	private String password;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SEED_PROF_ID", nullable = false)
    private Profile profile;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "SEED_SUBSCRIPTION"
				, joinColumns = { @JoinColumn(name = "SDSB_SEED_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_SUBSCRIPTION_01")) }
				, inverseJoinColumns = { @JoinColumn(name = "SDSB_SUBS_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_SUBSCRIPTION_02")) }
				, uniqueConstraints = { @UniqueConstraint(columnNames = { "SDSB_SEED_ID", "SDSB_SUBS_ID" }) })
	private Set<Subscription> subscriptions;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "SEED_DOMAIN"
				, joinColumns = { @JoinColumn(name = "SDDM_SEED_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_DOMAIN_01")) }
				, inverseJoinColumns = { @JoinColumn(name = "SDDM_DOMN_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_DOMAIN_02")) }
				, uniqueConstraints = { @UniqueConstraint(columnNames = { "SDDM_SEED_ID", "SDDM_DOMN_ID" }) })
	private Set<Domain> domains;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "SEED_SESSION"
				, joinColumns = { @JoinColumn(name = "SDSS_SEED_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_SESSION_01")) }
				, inverseJoinColumns = { @JoinColumn(name = "SDDM_SESS_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_DOMAIN_02")) }
				, uniqueConstraints = { @UniqueConstraint(columnNames = { "SDSS_SEED_ID", "SDDM_SESS_ID" }) })
	private Set<Session> sessions;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "SEED_CONTACT"
				, joinColumns = { @JoinColumn(name = "SDCT_SEED_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_CONTACT_01")) }
				, inverseJoinColumns = { @JoinColumn(name = "SDCT_CONT_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_CONTACT_02")) }
				, uniqueConstraints = { @UniqueConstraint(columnNames = { "SDCT_SEED_ID", "SDCT_CONT_ID" }) })
	private Set<Contact> contacts;

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
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 */
	public Seed(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Constructor
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param profile
	 */
	public Seed(String firstName, String lastName, String email, String password, Profile profile) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.profile = profile;
	}

	/**
	 * Constructor
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param profile
	 * @param subscriptions
	 * @param domains
	 * @param sessions
	 */
	public Seed(String firstName, String lastName, String email,
			String password, Profile profile, Set<Subscription> subscriptions,
			Set<Domain> domains, Set<Session> sessions, Set<Contact> contacts) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.subscriptions = subscriptions;
		this.domains = domains;
		this.sessions = sessions;
		this.contacts = contacts;
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
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/**
	 * @return the subscriptions
	 */
	public Set<Subscription> getSubscriptions() {
		if(subscriptions==null){
			subscriptions = new HashSet<Subscription>();
		}
		return subscriptions;
	}

	/**
	 * @param subscriptions the subscriptions to set
	 */
	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	/**
	 * @return the domains
	 */
	public Set<Domain> getDomains() {
		if(domains==null){
			domains = new HashSet<Domain>();
		}
		return domains;
	}

	/**
	 * @param domains the domains to set
	 */
	public void setDomains(Set<Domain> domains) {
		this.domains = domains;
	}

	/**
	 * @return the sessions
	 */
	public Set<Session> getSessions() {
		if(sessions==null){
			sessions = new HashSet<Session>();
		}
		return sessions;
	}

	/**
	 * @param sessions the sessions to set
	 */
	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}

	/**
	 * @return the contacts
	 */
	public Set<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
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
