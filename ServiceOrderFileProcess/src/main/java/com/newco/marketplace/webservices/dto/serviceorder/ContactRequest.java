package com.newco.marketplace.webservices.dto.serviceorder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;


public class ContactRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Do not remove individual phone parameters as 
	//needed for population of so related contact tables.
	private String soId;
	private Integer contactId;
	private Integer contactTypeId;
	private Integer phoneTypeId;
	private String businessName= null;
	private String lastName= null;
	private String firstName=null;
	private String mi=null;
	private String suffix=null;
	private String title=null;
	private Date phoneCreatedDate;
	
	private String email=null;
	private Integer contactGroup=Integer.valueOf(-1);
	private Integer contactMethodId=Integer.valueOf(-1);
	private Timestamp createdDate = null;
	private Timestamp modifiedDate = null;
	private String modifiedBy;
    private String honorific = null;
    
    private String emailPreference;
    private String smsPreference;
    private Integer entityTypeId;
    private Integer entityId;
    private List<PhoneRequest> phones;
	
	
	    
	
	
	public String getEmailPreference() {
		if (emailPreference == null || emailPreference.equals("0")) {
			return "N";
		}
		return "Y";

	}
	public void setEmailPreference(String emailPreference) {
		this.emailPreference = emailPreference;
	}
	
	public String getSmsPreference() {
		if (smsPreference == null || smsPreference.equals("0")) {
			return "N";
		}
		return "Y";	
	}
	public void setSmsPreference(String smsPreference) {
		this.smsPreference = smsPreference;
	}
	
	public Integer getContactGroup() {
		return contactGroup;
	}
	public void setContactGroup(Integer contactGroup) {
		this.contactGroup = contactGroup;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getContactMethodId() {
		return contactMethodId;
	}
	public void setContactMethodId(Integer contactMethodId) {
		this.contactMethodId = contactMethodId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMi() {
		return mi;
	}
	public void setMi(String mi) {
		this.mi = mi;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    /**
     * @return the honorific
     */
    public String getHonorific() {
        return honorific;
    }
    /**
     * @param honorific the honorific to set
     */
    public void setHonorific(String honorific) {
        this.honorific = honorific;
    }
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getContactTypeId() {
		return contactTypeId;
	}
	public void setContactTypeId(Integer contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("soId", getSoId())
			.append("contactId", getContactId())
			.append("businessName", getBusinessName())
			.append("lastName", getLastName())	
			.append("firstName", getFirstName())
			.append("mi", getMi())
			.append("suffix", getSuffix())
			.append("title", getTitle())
						.append("locName")
			.toString();
	}

	
	public Date getPhoneCreatedDate() {
		return phoneCreatedDate;
	}
	public void setPhoneCreatedDate(Date phoneCreatedDate) {
		this.phoneCreatedDate = phoneCreatedDate;
	}



	public Integer getPhoneTypeId() {
		return phoneTypeId;
	}



	public void setPhoneTypeId(Integer phoneTypeId) {
		this.phoneTypeId = phoneTypeId;
	}



	public List<PhoneRequest> getPhones() {
		return phones;
	}



	public void setPhones(List<PhoneRequest> phones) {
		this.phones = phones;
	}
}
