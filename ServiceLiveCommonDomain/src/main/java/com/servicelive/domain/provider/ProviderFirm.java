package com.servicelive.domain.provider;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupWfStates;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table ( name = "vendor_hdr")
public class ProviderFirm  extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090114L;

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="vendor_id", unique=true, nullable= false, insertable=false, updatable=false, length=11)
	private Integer id;

	@Column(name="business_name", unique=true, nullable= false, insertable=true, updatable=true, length=100)
	private String businessName;
	
	@Column(name="dba_name", unique=false, nullable=true, insertable=true, updatable=true, length=30)
	private String dba;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn (name = "wf_state_id")
	private LookupWfStates serviceLiveWorkFlowState;
	
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="primary_resource_id")
	private ServiceProvider primaryServiceProvider;
	
	@Transient 
	private Integer serviceProCount;

	@Transient 
	private boolean autoCloseExcluded;
	
	/**
	 * 
	 */
	public ProviderFirm() {
		super();
	}

	/**
	 * @param id
	 */
	public ProviderFirm(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	/**
	 * @return the dba
	 */
	public String getDba() {
		return dba;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setDba(String dba) {
		this.dba = dba;
	}

	/**
	 * @return the serviceLiveWorkFlowState
	 */
	public LookupWfStates getServiceLiveWorkFlowState()
	{
		return serviceLiveWorkFlowState;
	}

	/**
	 * @param serviceLiveWorkFlowState the serviceLiveWorkFlowState to set
	 */
	public void setServiceLiveWorkFlowState(LookupWfStates serviceLiveWorkFlowState)
	{
		this.serviceLiveWorkFlowState = serviceLiveWorkFlowState;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ProviderFirm [businessName=" + businessName 
		+ ", dba=" + dba 
		+ ", id=" + id 
		+ ", serviceLiveWorkFlowState="
		+ serviceLiveWorkFlowState + "]";
	}

	/**
	 * @return the serviceProCount
	 */
	public Integer getServiceProCount() {
		return serviceProCount;
	}

	/**
	 * @param serviceProCount the serviceProCount to set
	 */
	public void setServiceProCount(Integer serviceProCount) {
		this.serviceProCount = serviceProCount;
	}

	public void setPrimaryServiceProvider(ServiceProvider primaryServiceProvider) {
		this.primaryServiceProvider = primaryServiceProvider;
	}

	public ServiceProvider getPrimaryServiceProvider() {
		return primaryServiceProvider;
	}

	public boolean isAutoCloseExcluded() {
		return autoCloseExcluded;
	}

	public void setAutoCloseExcluded(boolean autoCloseExcluded) {
		this.autoCloseExcluded = autoCloseExcluded;
	}
	
	
}
