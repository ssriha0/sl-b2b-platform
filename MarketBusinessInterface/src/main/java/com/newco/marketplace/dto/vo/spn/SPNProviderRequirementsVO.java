package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SPNProviderRequirementsVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4764452425943780134L;
	private Integer credId;
	public Integer getCredId() {
		return credId;
	}
	public void setCredId(Integer credId) {
		this.credId = credId;
	}
	private String groupName;
	private String criteriaDesc;
	private String groupValue;
	private String childNode;
	private String parentNode;
	private String value;
	private int matchedProvidersCount;
	private int overridedProvidersCount;
	private int outOfCompliantProvidersCount;
	private String matchCriteria;
	private Date expirationDate;
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getMatchCriteria() {
		return matchCriteria;
	}
	public void setMatchCriteria(String matchCriteria) {
		this.matchCriteria = matchCriteria;
	}
	public int getOverridedProvidersCount() {
		return overridedProvidersCount;
	}
	public void setOverridedProvidersCount(int overridedProvidersCount) {
		this.overridedProvidersCount = overridedProvidersCount;
	}
	public int getOutOfCompliantProvidersCount() {
		return outOfCompliantProvidersCount;
	}
	public void setOutOfCompliantProvidersCount(int outOfCompliantProvidersCount) {
		this.outOfCompliantProvidersCount = outOfCompliantProvidersCount;
	}
	private Integer credTypeId;
	private Integer credCatId;
	private String wfState;
	private List<SPNExclusionsVO> exclusionsVO ;
	private String exceptionTypeId;
	private String state;
	private Integer spnId;
	private String matchedCriteria;

	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public String getMatchedCriteria() {
		return matchedCriteria;
	}
	public void setMatchedCriteria(String matchedCriteria) {
		this.matchedCriteria = matchedCriteria;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	public String getExceptionTypeId() {
		return exceptionTypeId;
	}
	public void setExceptionTypeId(String exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}
	public List<SPNExclusionsVO> getExclusionsVO() {
		return exclusionsVO;
	}
	public void setExclusionsVO(List<SPNExclusionsVO> exclusionsVO) {
		this.exclusionsVO = exclusionsVO;
	}
	private String matchedValue;
	
	public String getMatchedValue() {
		return matchedValue;
	}
	public void setMatchedValue(String matchedValue) {
		this.matchedValue = matchedValue;
	}
	public Integer getCredTypeId() {
		return credTypeId;
	}
	public void setCredTypeId(Integer credTypeId) {
		this.credTypeId = credTypeId;
	}
	public Integer getCredCatId() {
		return credCatId;
	}
	public void setCredCatId(Integer credCatId) {
		this.credCatId = credCatId;
	}
	public String getWfState() {
		return wfState;
	}
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCriteriaDesc() {
		return criteriaDesc;
	}
	public void setCriteriaDesc(String criteriaDesc) {
		this.criteriaDesc = criteriaDesc;
	}
	public String getGroupValue() {
		return groupValue;
	}
	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}
	public String getChildNode() {
		return childNode;
	}
	public void setChildNode(String childNode) {
		this.childNode = childNode;
	}
	public String getParentNode() {
		return parentNode;
	}
	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}
	public int getMatchedProvidersCount() {
		return matchedProvidersCount;
	}
	public void setMatchedProvidersCount(int matchedProvidersCount) {
		this.matchedProvidersCount = matchedProvidersCount;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
