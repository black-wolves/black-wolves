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
@Table(name = "PROFILE")
public class Profile implements Serializable {

	private static final long serialVersionUID = 3373517075997013757L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "profile_gen")
	@SequenceGenerator(name = "profile_gen", sequenceName = "PROFILE_SEQ")
	@Column(name = "PROF_ID")
	private Long id;
	
	@Column(name = "PROF_HOURS_NEXT_LOGIN")
	private int hoursNextLogin;
	

	/**
	 * 
	 */
	public Profile() {}
	

	/**
	 * @param hoursNextLogin
	 */
	public Profile(int hoursNextLogin) {
		this.hoursNextLogin = hoursNextLogin;
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
	 * @return the hoursNextLogin
	 */
	public int getHoursNextLogin() {
		return hoursNextLogin;
	}

	/**
	 * @param hoursNextLogin the hoursNextLogin to set
	 */
	public void setHoursNextLogin(int hoursNextLogin) {
		this.hoursNextLogin = hoursNextLogin;
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
		Profile other = (Profile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
