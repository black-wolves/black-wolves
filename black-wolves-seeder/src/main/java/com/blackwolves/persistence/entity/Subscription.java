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
@Table(name = "SUBSCRIPTION")
public class Subscription implements Serializable {

	private static final long serialVersionUID = 425219251604705012L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "subscription_gen")
	@SequenceGenerator(name = "subscription_gen", sequenceName = "SUBSCRIPTION_SEQ")
	@Column(name = "SUBS_ID")
	private Long id;

	@Column(name = "SUBS_NAME", nullable = false)
	private String name;
	
	@Column(name = "SUBS_URL", nullable = false)
	private String url;

	/**
	 * Constructor
	 * @param name
	 * @param url
	 */
	public Subscription(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public Subscription(String url) {
		this.url = url;
	}
	
	public Subscription(){
		
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
		Subscription other = (Subscription) obj;
		if (id == null) {
			if (other.id!= null){
				return false;
			}
		} else if (!id.equals(other.id)){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public boolean equalsByUrl(String url){
		return (this.url).equals(url);
	}

}
