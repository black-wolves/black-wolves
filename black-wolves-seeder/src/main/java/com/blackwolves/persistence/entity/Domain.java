package com.blackwolves.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DOMAIN")
public class Domain implements Serializable {

	private static final long serialVersionUID = -2703143527741382550L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "domain_gen")
	@SequenceGenerator(name = "domain_gen", sequenceName = "DOMAIN_SEQ")
	@Column(name = "DOMN_ID")
	private Long id;

	@Column(name = "DOMN_NAME", nullable = false)
	private String name;
	
	@Column(name = "DOMN_INBOX_RATE", nullable = false)
	private String inboxRate;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the inboxRate
	 */
	public String getInboxRate() {
		return inboxRate;
	}

	/**
	 * @param inboxRate the inboxRate to set
	 */
	public void setInboxRate(String inboxRate) {
		this.inboxRate = inboxRate;
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
		Domain other = (Domain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
