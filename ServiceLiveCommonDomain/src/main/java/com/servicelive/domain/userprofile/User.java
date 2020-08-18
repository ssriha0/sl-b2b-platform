/**
 *
 */
package com.servicelive.domain.userprofile;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.BaseDomain;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.lookup.LookupUserRoles;

/**
 * @author Admin
 *
 */
@Entity
@Table (name = "user_profile")
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends BaseDomain {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name= "user_name")
	private String username;

	@Column(name= "password")
	private String password;
	
	
	
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_id")
	protected Contact contact;
	
	 @ManyToOne
	 @JoinColumn(name="role_id", insertable=false, updatable=false)
	private LookupUserRoles role;
	
	@Transient
	private Integer userId;
	
	@Transient
	private Integer userChildId;
	

	@Transient
	private Set<String>authorities = new TreeSet<String>();
	


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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**
	 * 
	 * @return LookupUserRoles
	 */
	public LookupUserRoles getRole() {
		return role;
	}
	/**
	 * 
	 * @param role
	 */
	public void setRole(LookupUserRoles role) {
		this.role = role;
	}
	/**
	 * @return the userChildId
	 */
	public Integer getUserChildId() {
		return userChildId;
	}
	/**
	 * @param userChildId the userChildId to set
	 */
	public void setUserChildId(Integer userChildId) {
		this.userChildId = userChildId;
	}
	/**
	 * @return the authorities
	 */
	public Set<String> getAuthorities() {
		return authorities;
	}
	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}



}
