package com.blackwolves.persistence.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@Column(name = "SEED_FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "SEED_LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "SEED_EMAIL", nullable = false)
	private String email;
	
	@Column(name = "SEED_PASSWORD", nullable = false)
	private String password;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SEED_SUBSCRIPTION"
				, joinColumns = { @JoinColumn(name = "SDSB_SEED_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_SUBSCRIPTION_01")) }
				, inverseJoinColumns = { @JoinColumn(name = "SDSB_SUBS_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_SUBSCRIPTION_02")) }
				, uniqueConstraints = { @UniqueConstraint(columnNames = { "SDSB_SEED_ID", "SDSB_SUBS_ID" }) })
	private Set<Subscription> subscriptions;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SEED_DOMAIN"
				, joinColumns = { @JoinColumn(name = "SDDM_SEED_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_DOMAIN_01")) }
				, inverseJoinColumns = { @JoinColumn(name = "SDDM_DOMN_ID"
						, foreignKey = @ForeignKey(name = "FK_SEED_DOMAIN_02")) }
				, uniqueConstraints = { @UniqueConstraint(columnNames = { "SDDM_SEED_ID", "SDDM_DOMN_ID" }) })
	private Set<Domain> domains;

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
	 * @return the subscriptions
	 */
	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	/**
	 * @param subscriptions the subscriptions to set
	 */
	public void setSubscriptions(SortedSet<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	/**
	 * @return the domains
	 */
	public Set<Domain> getDomains() {
		return domains;
	}

	/**
	 * @param domains the domains to set
	 */
	public void setDomains(SortedSet<Domain> domains) {
		this.domains = domains;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seed other = (Seed) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
