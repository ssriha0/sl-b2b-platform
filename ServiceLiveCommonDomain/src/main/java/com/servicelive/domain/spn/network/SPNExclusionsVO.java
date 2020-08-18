package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;




/**
 * Class to hold exceptions types,gracePeriod values (for SL_18018)
 *
 */
@Entity
@Table ( name = "spnet_credential_exception")
public class SPNExclusionsVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	
	@Id @GeneratedValue (strategy=IDENTITY)
	@Column (name = "cred_exception_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer credentialExceptionId;
	
	@ManyToOne
    @JoinColumn(name="spn_id",insertable=false, updatable=false)
	@ForeignKey (name = "FK_spnet_credential_exception")
	private SPNHeader spn;
	 
	
	@Transient
	private Integer credentialId;
	
	@Transient
	private Integer spnId;
	
	@Column (name = "cred_type_id", nullable = false, insertable = true, updatable = true)
	private Integer credentialTypeId;
	
	@Column (name = "exception_type_id", nullable = false, insertable = true, updatable = true)
	private Integer exceptionTypeId;
	
	@Column (name = "exception_value", nullable = false, insertable = true, updatable = true)
	private String exceptionValue;
	
	
	@Transient
	private String selectedStates;
	

	@Transient
	private String selectedStatesLeft;
	
	@Transient
	private Integer remainingStatesCount;
	
	@Transient
	private Integer expCount;
	
	@Transient
	private List<String> selectedStatesValues;
	
	@Column (name = "active_ind", nullable = false, insertable = true, updatable = true)
	private Boolean activeInd;
	
	@Transient
	private String credentialType;
	
	@Column (name = "credential_type", nullable = false, insertable = true, updatable = true)
	private String exceptionCredentialType;
	
	@Transient
	private String credentialCategory;
	
	@Column (name = "created_date", nullable = false, insertable = true, updatable = true)
	private Date createdDate;
	
	
	@Column (name = "modified_date",nullable = false, insertable = true, updatable = true)
	private Date modifiedDate;
	
	@Column (name = "modified_by",nullable = false, insertable = true, updatable = true)
	private String modifiedBy;
	
	@Column (name = "buyer_id",nullable = false, insertable = true, updatable = true)
	private Integer buyerId;
	
	@Transient
	private String description;
	
	@Transient
	private Integer criteriaId;
	
	@Column (name = "cred_category_id",nullable = true, insertable = true, updatable = true)
	private Integer credentialCategoryId;
	
	@Transient
	private String comments;
	
	@Transient
	private String modifiedByUserName;
	
	@Transient
	private String matchCriteria;
	
	public String getMatchCriteria() {
		return matchCriteria;
	}
	public void setMatchCriteria(String matchCriteria) {
		this.matchCriteria = matchCriteria;
	}
	public Integer getExpCount() {
		return expCount;
	}
	public void setExpCount(Integer expCount) {
		this.expCount = expCount;
	}
	public Integer getRemainingStatesCount() {
		return remainingStatesCount;
	}
	public void setRemainingStatesCount(Integer remainingStatesCount) {
		this.remainingStatesCount = remainingStatesCount;
	}
	public String getSelectedStatesLeft() {
		return selectedStatesLeft;
	}
	public void setSelectedStatesLeft(String selectedStatesLeft) {
		this.selectedStatesLeft = selectedStatesLeft;
	}
	public String getSelectedStates() {
		return selectedStates;
	}
	public void setSelectedStates(String selectedStates) {
		this.selectedStates = selectedStates;
	}
	public List<String> getSelectedStatesValues() {
		return selectedStatesValues;
	}
	public void setSelectedStatesValues(List<String> selectedStatesValues) {
		this.selectedStatesValues = selectedStatesValues;
	}

	
	public String getExceptionCredentialType() {
		return exceptionCredentialType;
	}
	public void setExceptionCredentialType(String exceptionCredentialType) {
		this.exceptionCredentialType = exceptionCredentialType;
	}


	public String getModifiedByUserName() {
		return modifiedByUserName;
	}
	public void setModifiedByUserName(String modifiedByUserName) {
		this.modifiedByUserName = modifiedByUserName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getCredentialExceptionId() {
		return credentialExceptionId;
	}
	public void setCredentialExceptionId(Integer credentialExceptionId) {
		this.credentialExceptionId = credentialExceptionId;
	}
	public Integer getCredentialCategoryId() {
		return credentialCategoryId;
	}
	public void setCredentialCategoryId(Integer credentialCategoryId) {
		this.credentialCategoryId = credentialCategoryId;
	}
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	public SPNHeader getSpn() {
		return spn;
	}
	public void setSpn(SPNHeader spn) {
		this.spn = spn;
	}
	public Integer getCredentialId() {
		return credentialId;
	}
	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}
	public Integer getCredentialTypeId() {
		return credentialTypeId;
	}
	public void setCredentialTypeId(Integer credentialTypeId) {
		this.credentialTypeId = credentialTypeId;
	}
	public Integer getExceptionTypeId() {
		return exceptionTypeId;
	}
	public void setExceptionTypeId(Integer exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}
	public String getExceptionValue() {
		return exceptionValue;
	}
	public void setExceptionValue(String exceptionValue) {
		this.exceptionValue = exceptionValue;
	}

	
	public Boolean getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}
	public String getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	public String getCredentialCategory() {
		return credentialCategory;
	}
	public void setCredentialCategory(String credentialCategory) {
		this.credentialCategory = credentialCategory;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	
}
