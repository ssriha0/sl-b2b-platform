/**
 * 
 */
package com.newco.marketplace.dto.vo.alert;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author sahmad7
 *
 */
public class ContactAlertPreferencesVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 544554742920566491L;
	private Integer contactId;
	private Boolean emailAlert;
	private Boolean smsAlert;
	private Boolean pagerAlert;
	private Timestamp pagerWindowBegin;
	private Timestamp pagerWindowEnd;
	private Timestamp smsWindowBegin;
	private Timestamp smsWindowEnd;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Boolean getEmailAlert() {
		return emailAlert;
	}
	public void setEmailAlert(Boolean emailAlert) {
		this.emailAlert = emailAlert;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Boolean getPagerAlert() {
		return pagerAlert;
	}
	public void setPagerAlert(Boolean pagerAlert) {
		this.pagerAlert = pagerAlert;
	}
	public Timestamp getPagerWindowBegin() {
		return pagerWindowBegin;
	}
	public void setPagerWindowBegin(Timestamp pagerWindowBegin) {
		this.pagerWindowBegin = pagerWindowBegin;
	}
	public Timestamp getPagerWindowEnd() {
		return pagerWindowEnd;
	}
	public void setPagerWindowEnd(Timestamp pagerWindowEnd) {
		this.pagerWindowEnd = pagerWindowEnd;
	}
	public Boolean getSmsAlert() {
		return smsAlert;
	}
	public void setSmsAlert(Boolean smsAlert) {
		this.smsAlert = smsAlert;
	}
	public Timestamp getSmsWindowBegin() {
		return smsWindowBegin;
	}
	public void setSmsWindowBegin(Timestamp smsWindowBegin) {
		this.smsWindowBegin = smsWindowBegin;
	}
	public Timestamp getSmsWindowEnd() {
		return smsWindowEnd;
	}
	public void setSmsWindowEnd(Timestamp smsWindowEnd) {
		this.smsWindowEnd = smsWindowEnd;
	}
	
}
