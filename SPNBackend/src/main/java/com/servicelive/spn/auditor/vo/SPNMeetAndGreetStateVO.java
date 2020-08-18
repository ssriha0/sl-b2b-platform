/**
 * 
 */
package com.servicelive.spn.auditor.vo;

import java.util.Date;

/**
 * @author Madhup_Chand
 *
 */
public class SPNMeetAndGreetStateVO {
	private Date createdDate = new Date();
	private String modifiedBy;
	private Date modifiedDate = new Date();
	private Integer spnId;
	private String comments;
	private Integer providerFirmId;
	private String meetAndGreetStateId;
	private Date meetAndGreetDate = new Date();
	private String meetAndGreetPerson;
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the comment
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the providerFirmId
	 */
	public Integer getProviderFirmId() {
		return providerFirmId;
	}
	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	/**
	 * @return the meetAndGreetStateId
	 */
	public String getMeetAndGreetStateId() {
		return meetAndGreetStateId;
	}
	/**
	 * @param meetAndGreetStateId the meetAndGreetStateId to set
	 */
	public void setMeetAndGreetStateId(String meetAndGreetStateId) {
		this.meetAndGreetStateId = meetAndGreetStateId;
	}
	/**
	 * @return the meetAndGreetDate
	 */
	public Date getMeetAndGreetDate() {
		return meetAndGreetDate;
	}
	/**
	 * @param meetAndGreetDate the meetAndGreetDate to set
	 */
	public void setMeetAndGreetDate(Date meetAndGreetDate) {
		this.meetAndGreetDate = meetAndGreetDate;
	}
	/**
	 * @return the meetAndGreetPerson
	 */
	public String getMeetAndGreetPerson() {
		return meetAndGreetPerson;
	}
	/**
	 * @param meetAndGreetPerson the meetAndGreetPerson to set
	 */
	public void setMeetAndGreetPerson(String meetAndGreetPerson) {
		this.meetAndGreetPerson = meetAndGreetPerson;
	}
   
}
