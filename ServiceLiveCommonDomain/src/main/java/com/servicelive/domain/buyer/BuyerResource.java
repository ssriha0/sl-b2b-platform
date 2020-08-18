package com.servicelive.domain.buyer;
// default package

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.userprofile.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * Buyer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="buyer_resource" )
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyerResource  extends LoggableBaseDomain
{

	private static final long serialVersionUID = 6566140541336634661L;

	// Fields
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="resource_id", unique=true, nullable=false)
	private Integer resourceId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="buyer_id")
    private Buyer buyer;
  
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contact_id")
    private Contact contact;
  
	 @Column(name="company_role_id")
	 private Integer companyRoleId;
	 
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_name", nullable=false)
    private User user;

    /**
     * 
     * @return Buyer
     */
	public Buyer getBuyer()
	{
		return buyer;
	}

	/**
	 * 
	 * @param buyer
	 */
	public void setBuyer(Buyer buyer)
	{
		this.buyer = buyer;
	}

	/**
	 * 
	 * @return Contact
	 */
	public Contact getContact()
	{
		return contact;
	}

	/**
	 * 
	 * @param contact
	 */
	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	/**
	 * 
	 * @return Integer
	 */
	public Integer getCompanyRoleId()
	{
		return companyRoleId;
	}

	/**
	 * 
	 * @param companyRoleId
	 */
	public void setCompanyRoleId(Integer companyRoleId)
	{
		this.companyRoleId = companyRoleId;
	}

	/**
	 * 
	 * @return User
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * 
	 * @param user
	 */
	public void setUser(User user)
	{
		this.user = user;
	}
	
	/**
	 * 
	 * @return Integer
	 */
	public Integer getResourceId()
	{
		return resourceId;
	}
	/**
	 * 
	 * @param resourceId
	 */
	public void setResourceId(Integer resourceId)
	{
		this.resourceId = resourceId;
	}	 
     
     


}