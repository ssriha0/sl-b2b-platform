package com.newco.marketplace.dto.vo.spn;


import java.io.Serializable;
import java.util.Date;
import java.util.List;




/**
 * Class to hold exceptions types,gracePeriod values (for SL_18018)
 *
 */

public class SPNExclusionsVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	
	private Integer credentialExceptionId;
	
	private Integer spnId;
	
	private Integer credentialId;
	
	private Integer credentialTypeId;
	
	private Integer exceptionTypeId;
	
	private String exceptionValue;
	
	
	private String selectedStates;
	
	private String selectedStatesLeft;
	
	private Integer remainingStatesCount;
	
	private Integer expCount;
	
	private List<String> selectedStatesValues;
	
	private Boolean activeInd;
	
	private String credentialType;
	
	private String exceptionCredentialType;
	
	private String credentialCategory;
	
	private Date createdDate;
	
	
	private Date modifiedDate;
	
	private Integer modifiedBy;
	
	private String description;
	
	private Integer criteriaId;
	
	private Integer credentialCategoryId;
	
	private String comments;
	
	private String modifiedByUserName;
	
	private String state;
	
	private String[] excepmtedStates;
	
	public String[] getExcepmtedStates() {
		return excepmtedStates;
	}
	public void setExcepmtedStates(String[] excepmtedStates) {
		this.excepmtedStates = excepmtedStates;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}	private String matchCriteria;
	
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

	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
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
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
