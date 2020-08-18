package com.servicelive.domain.spn.network;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ServiceProvider;

/**
 * 
 * @author svanloo
 * 
 */
@Entity
@Table(name = "spnet_serviceprov_st_history")
public class SPNServiceProviderStateHistory extends LoggableBaseDomain {

	private static final long serialVersionUID = 20100412L;

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, length = 250)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "spn_id", unique = false, nullable = false, insertable = false, updatable = false)
	private SPNHeader spnHeader;

	@ManyToOne
	@JoinColumn(name = "service_provider_id", unique = false, nullable = false, insertable = false, updatable = false)
	private ServiceProvider serviceProvider;

	@ManyToOne
	@JoinColumn(name = "provider_wf_state", unique = false, nullable = false, insertable = true, updatable = true)
	private LookupSPNWorkflowState wfState;

	// perf_level int(11) YES MUL 4
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "perf_level", unique = false, nullable = true, insertable = true, updatable = true)
	private LookupPerformanceLevel performanceLevel;

	// status_override_reason_id int(5) YES MUL (null)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "status_override_reason_id", unique = false, nullable = true, insertable = true, updatable = true)
	private LookupSPNStatusOverrideReason lookupSPNStatusOverrideReason;

	// status_override_comments varchar(250) YES (null)
	@Column(name = "status_override_comments", unique = false, nullable = true, insertable = true, updatable = true, length = 750)
	private String statusOverrideComments;

	// status_update_action_id int(5) YES MUL (null)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "status_update_action_id", unique = false, nullable = true, insertable = true, updatable = true)
	private LookupSPNStatusActionMapper lookupSPNStatusActionMapper;

	// pf_level_change_comments varchar(250) YES (null)
	@Column(name = "pf_level_change_comments", unique = false, nullable = true, insertable = true, updatable = true, length = 250)
	private String performanceLevelChangeComments;
	
	// SL-12300
	// The expiry date for override from front-end will be saved in this column. 
	@Column(name = "status_override_effective_date", unique = false, nullable = true, insertable = true, updatable = true)
	private String statusOverrideEffectiveDate;
	
	//JIRA SL-12300
	//Indicator to identify that the network status is overridden from the front-end.
	@Column(name = "status_override_ind", insertable = true, updatable = true)
	private Boolean statusOverrideInd;
	
	public String getStatusOverrideEffectiveDate() {
		return statusOverrideEffectiveDate;
	}

	public void setStatusOverrideEffectiveDate(String statusOverrideEffectiveDate) {
		this.statusOverrideEffectiveDate = statusOverrideEffectiveDate;
	}

	/**
	 * @return the wfState
	 */
	public LookupSPNWorkflowState getWfState() {
		return wfState;
	}

	/**
	 * @param wfState
	 *            the wfState to set
	 */
	public void setWfState(LookupSPNWorkflowState wfState) {
		this.wfState = wfState;
	}

	/**
	 * @return the lookupSPNStatusOverrideReason
	 */
	public LookupSPNStatusOverrideReason getLookupSPNStatusOverrideReason() {
		return lookupSPNStatusOverrideReason;
	}

	/**
	 * @param lookupSPNStatusOverrideReason
	 *            the lookupSPNStatusOverrideReason to set
	 */
	public void setLookupSPNStatusOverrideReason(LookupSPNStatusOverrideReason lookupSPNStatusOverrideReason) {
		this.lookupSPNStatusOverrideReason = lookupSPNStatusOverrideReason;
	}

	/**
	 * @return the statusOverrideComments
	 */
	public String getStatusOverrideComments() {
		return statusOverrideComments;
	}

	/**
	 * @param statusOverrideComments
	 *            the statusOverrideComments to set
	 */
	public void setStatusOverrideComments(String statusOverrideComments) {
		this.statusOverrideComments = statusOverrideComments;
	}

	/**
	 * @return the lookupSPNStatusActionMapper
	 */
	public LookupSPNStatusActionMapper getLookupSPNStatusActionMapper() {
		return lookupSPNStatusActionMapper;
	}

	/**
	 * @param lookupSPNStatusActionMapper
	 *            the lookupSPNStatusActionMapper to set
	 */
	public void setLookupSPNStatusActionMapper(LookupSPNStatusActionMapper lookupSPNStatusActionMapper) {
		this.lookupSPNStatusActionMapper = lookupSPNStatusActionMapper;
	}

	/**
	 * @return the performanceLevel
	 */
	public LookupPerformanceLevel getPerformanceLevel() {
		return performanceLevel;
	}

	/**
	 * @param performanceLevel
	 *            the performanceLevel to set
	 */
	public void setPerformanceLevel(LookupPerformanceLevel performanceLevel) {
		this.performanceLevel = performanceLevel;
	}

	/**
	 * @return the performanceLevelChangeComments
	 */
	public String getPerformanceLevelChangeComments() {
		return performanceLevelChangeComments;
	}

	/**
	 * @param performanceLevelChangeComments
	 *            the performanceLevelChangeComments to set
	 */
	public void setPerformanceLevelChangeComments(String performanceLevelChangeComments) {
		this.performanceLevelChangeComments = performanceLevelChangeComments;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the spnHeader
	 */
	public SPNHeader getSpnHeader() {
		return spnHeader;
	}

	/**
	 * @param spnHeader
	 *            the spnHeader to set
	 */
	public void setSpnHeader(SPNHeader spnHeader) {
		this.spnHeader = spnHeader;
	}

	/**
	 * @return the serviceProvider
	 */
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	/**
	 * @param serviceProvider
	 *            the serviceProvider to set
	 */
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Boolean getStatusOverrideInd() {
		return statusOverrideInd;
	}

	public void setStatusOverrideInd(Boolean statusOverrideInd) {
		this.statusOverrideInd = statusOverrideInd;
	}

}
