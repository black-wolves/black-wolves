/**
 * 
 */
package com.blackwolves.persistence.entity;

import java.io.Serializable;

/**
 * @author gastondapice
 *
 */
public class Subscription implements Serializable {

	private static final long serialVersionUID = 425219251604705012L;

	private Long id;

	private String name;
	
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
