/**
 * 
 */
package com.blackwolves.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author gastondapice
 *
 */
@Entity
@Table(name = "CONTACT")
public class Contact implements Serializable {

	private static final long serialVersionUID = -5114051586726465347L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "contact_gen")
	@SequenceGenerator(name = "contact_gen", sequenceName = "CONTACT_SEQ")
	@Column(name = "CONT_ID")
	private Long id;
	
	@Column(name = "SEED_FIRST_NAME")
	private String firstName;
	
	@Column(name = "SEED_LAST_NAME")
	private String lastName;
	
	@Column(name = "SEED_EMAIL", nullable = false)
	private String email;

	/**
	 * Constructor
	 */
	public Contact() {
	}

	/**
	 * Constructor
	 * @param email
	 */
	public Contact(String email) {
		this.email = email;
	}

	/**
	 * Constructor
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
	public Contact(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
		Contact other = (Contact) obj;
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
