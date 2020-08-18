/**
 *
 */
package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "spnet_hdr")
public class SPNHeader extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090106L;

	@Id @GeneratedValue (strategy=IDENTITY)
	@Column (name = "spn_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer spnId;

	@Column (name = "spn_name", unique = false, nullable = false, insertable = true, updatable = true, length = 150)
	private String spnName;

	@Column (name = "contact_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String contactName;

	@Column (name = "contact_email", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	private String contactEmail;

	@Column (name = "contact_phone", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	private String contactPhone;

	@Column (name = "spn_description", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	private String spnDescription;

	@Column (name = "spn_instruction", unique = false, nullable = false, insertable = true, updatable = true, length = 1000)
	private String spnInstruction;

	@Column (name = "is_alias", unique = false, nullable = false, insertable = true, updatable = true)
	private Boolean isAlias = Boolean.FALSE;

	@Column (name = "alias_original_spn_id", unique = true, nullable = true, insertable = true, updatable = true, length = 11)
	private Integer aliasOriginalSpnId;

	@Column (name = "comments", unique = false, nullable = true, insertable = true, updatable = true, length = 150)
	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "effective_date", unique = false, nullable = true, insertable = true, updatable = true)
	private Date effectiveDate;

	@OneToMany( fetch=FetchType.EAGER, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "spn_id",nullable = false, insertable = true, updatable = true)
	private List<SPNApprovalCriteria> approvalCriterias = new ArrayList<SPNApprovalCriteria> (0);
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "spn_id",nullable = false, insertable = true, updatable = true)
	private List<SPNDocument> spnDocuments = new ArrayList<SPNDocument> (0);
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "spn_id",nullable = false, insertable = true, updatable = true)
	private List<SPNBuyer> buyer = new ArrayList<SPNBuyer> (0);
	
	@Column(name="mp_overflow" , nullable = false, insertable = true, updatable = true)
	private Boolean marketPlaceOverFlow = Boolean.FALSE;
	
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} , mappedBy="spnTierPK.spnId")
	@JoinColumn(name = "spn_id",nullable = false)
	private List<SPNTierMinutes> tierMinutes = new ArrayList<SPNTierMinutes>(0);
	
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} , mappedBy="spnId")
	@JoinColumn(name = "spn_id",nullable = false )
	private List<SPNTierPerformanceLevel> performanceLevels = new ArrayList<SPNTierPerformanceLevel>(0);
	
	@Column (name = "exceptions_included", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	private Boolean exceptionsInd;
	
	@Column (name = "performance_criteria_level", nullable = false, insertable = true, updatable = true)
	private String criteriaLevel;

	@Column (name = "performance_criteria_timeframe", nullable = false, insertable = true, updatable = true)
	private String criteriaTimeframe;

	@Column (name = "routing_priority_status", nullable = false, insertable = true, updatable = true)
	private String routingPriorityStatus;

	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "spn_id",nullable = false, insertable = true, updatable = true)
	private List<SPNExclusionsVO> spnExceptions = new ArrayList<SPNExclusionsVO> (0);
	
	/**
	 * 
	 */
	public SPNHeader() {
		super();
	}

	
	public Boolean getExceptionsInd() {
		return exceptionsInd;
	}


	public void setExceptionsInd(Boolean exceptionsInd) {
		this.exceptionsInd = exceptionsInd;
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
	 * @return the spnName
	 */
	public String getSpnName() {
		return spnName;
	}

	/**
	 * @param spnName the spnName to set
	 */
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the spnDescription
	 */
	public String getSpnDescription() {
		return spnDescription;
	}

	/**
	 * @param spnDescription the spnDescription to set
	 */
	public void setSpnDescription(String spnDescription) {
		this.spnDescription = spnDescription;
	}

	/**
	 * @return the spnInstruction
	 */
	public String getSpnInstruction() {
		return spnInstruction;
	}

	/**
	 * @param spnInstruction the spnInstruction to set
	 */
	public void setSpnInstruction(String spnInstruction) {
		this.spnInstruction = spnInstruction;
	}

	/**
	 * @return the approvalCriterias
	 */
	public List<SPNApprovalCriteria> getApprovalCriterias() {
		return approvalCriterias;
	}

	/**
	 * @param approvalCriterias the approvalCriterias to set
	 */
	public void setApprovalCriterias(List<SPNApprovalCriteria> approvalCriterias) {
		this.approvalCriterias = approvalCriterias;
	}

	
	/**
	 * 
	 * @return List
	 */
	public List<SPNDocument> getSpnDocuments() {
		return spnDocuments;
	}
	/**
	 * 
	 * @param spnDocuments
	 */
	public void setSpnDocuments(List<SPNDocument> spnDocuments) {
		this.spnDocuments = spnDocuments;
	}
	
	
	/**
	 * @return the buyer
	 */
	public List<SPNBuyer> getBuyer() {
		return buyer;
	}

	/**
	 * @param buyer the buyer to set
	 */
	public void setBuyer(List<SPNBuyer> buyer) {
		this.buyer = buyer;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the isAlias
	 */
	public Boolean getIsAlias() {
		return isAlias;
	}

	/**
	 * @param isAlias the isAlias to set
	 */
	public void setIsAlias(Boolean isAlias) {
		this.isAlias = isAlias;
	}

	/**
	 * @return the aliasOriginalSpnId
	 */
	public Integer getAliasOriginalSpnId() {
		return aliasOriginalSpnId;
	}

	/**
	 * @param aliasOriginalSpnId the aliasOriginalSpnId to set
	 */
	public void setAliasOriginalSpnId(Integer aliasOriginalSpnId) {
		this.aliasOriginalSpnId = aliasOriginalSpnId;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the marketPlaceOverFlow
	 */
	public Boolean getMarketPlaceOverFlow() {
		return marketPlaceOverFlow;
	}

	/**
	 * @param marketPlaceOverFlow the marketPlaceOverFlow to set
	 */
	public void setMarketPlaceOverFlow(Boolean marketPlaceOverFlow) {
		this.marketPlaceOverFlow = marketPlaceOverFlow;
	}

	/**
	 * @return the tierMinutes
	 */
	public List<SPNTierMinutes> getTierMinutes() {
		return tierMinutes;
	}

	/**
	 * @param tierMinutes the tierMinutes to set
	 */
	public void setTierMinutes(List<SPNTierMinutes> tierMinutes) {
		this.tierMinutes = tierMinutes;
	}

	/**
	 * @return the performanceLevels
	 */
	public List<SPNTierPerformanceLevel> getPerformanceLevels() {
		return performanceLevels;
	}

	/**
	 * @param performanceLevels the performanceLevels to set
	 */
	public void setPerformanceLevels(List<SPNTierPerformanceLevel> performanceLevels) {
		this.performanceLevels = performanceLevels;
	}

	public String getCriteriaLevel() {
		return criteriaLevel;
	}

	public void setCriteriaLevel(String criteriaLevel) {
		this.criteriaLevel = criteriaLevel;
	}

	public String getCriteriaTimeframe() {
		return criteriaTimeframe;
	}

	public void setCriteriaTimeframe(String criteriaTimeframe) {
		this.criteriaTimeframe = criteriaTimeframe;
	}

	public String getRoutingPriorityStatus() {
		return routingPriorityStatus;
	}

	public void setRoutingPriorityStatus(String routingPriorityStatus) {
		this.routingPriorityStatus = routingPriorityStatus;
	}


	public List<SPNExclusionsVO> getSpnExceptions() {
		return spnExceptions;
	}


	public void setSpnExceptions(List<SPNExclusionsVO> spnExceptions) {
		this.spnExceptions = spnExceptions;
	}
	
	
}
