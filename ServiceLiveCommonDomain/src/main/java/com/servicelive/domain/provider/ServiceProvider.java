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
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.common.Location;
import com.servicelive.domain.common.ProviderLocationDetails;

import com.servicelive.domain.lookup.LookupWfStates;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table ( name = "vendor_resource")
public class ServiceProvider extends LoggableBaseDomain {

	
	private static final long serialVersionUID = 20090114L;
	
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="resource_id", unique=true, nullable= false, insertable=false, updatable=false, length=11)
	private Integer id;
 
	@OneToOne( fetch=FetchType.EAGER, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "contact_id", unique=false, nullable=false, insertable=true, updatable=false)
	private Contact contact;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name="vendor_id", unique=false, nullable=false, insertable=true, updatable=false)
	private ProviderFirm providerFirm;
	
	
	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn (name = "wf_state_id")
	private LookupWfStates serviceLiveWorkFlowId;
	
	@ManyToOne
    @JoinColumn(name="locn_id", unique=false, nullable= true, insertable=false, updatable=false)
	private Location location;


	@Column(name="primary_ind", unique=false, nullable=false, insertable=true, updatable=true, length=1)
	private Boolean primaryInd;
	
	@Column(name="resource_ind", unique=false, nullable=false, insertable=true, updatable=true, length=1)
	private Boolean resourceInd;
	
	@Column(name="mkt_place_ind", unique=false, nullable= false, insertable=true, updatable=true, length=1)
	private Integer mktPlaceInd;
	
	@Transient 
	private boolean autoCloseExcluded;	
	
	//SL-19381
	//Code added to display contact information of a firm
	@Transient 
	private ProviderLocationDetails providerLocationDetails;	
	
	public ProviderLocationDetails getProviderLocationDetails() {
		return providerLocationDetails;
	}
	public void setProviderLocationDetails(ProviderLocationDetails providerLocationDetails) {
		this.providerLocationDetails = providerLocationDetails;
	}
	public Integer getMktPlaceInd() {
		return mktPlaceInd;
	}
	public void setMktPlaceInd(Integer mktPlaceInd) {
		this.mktPlaceInd = mktPlaceInd;
	}
	/**S
	 * 
	 */
	public ServiceProvider() {
		super();
	}
	/**
	 * @param id
	 */
	public ServiceProvider(Integer id) {
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
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/**
	 * @return the providerFirm
	 */
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}

	/**
	 * @param providerFirm the providerFirm to set
	 */
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}

	/**
	 * @return the primaryInd
	 */
	public Boolean getPrimaryInd() {
		return primaryInd;
	}

	/**
	 * @param primaryInd the primaryInd to set
	 */
	public void setPrimaryInd(Boolean primaryInd) {
		this.primaryInd = primaryInd;
	}
	/**
	 * @return the serviceLiveWorkFlowId
	 */
	public LookupWfStates getServiceLiveWorkFlowId() {
		return serviceLiveWorkFlowId;
	}
	/**
	 * @param serviceLiveWorkFlowId the serviceLiveWorkFlowId to set
	 */
	public void setServiceLiveWorkFlowId(LookupWfStates serviceLiveWorkFlowId) {
		this.serviceLiveWorkFlowId = serviceLiveWorkFlowId;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @return the resourceInd
	 */
	public Boolean getResourceInd() {
		return resourceInd;
	}
	/**
	 * @param resourceInd the resourceInd to set
	 */
	public void setResourceInd(Boolean resourceInd) {
		this.resourceInd = resourceInd;
	}
	
	public boolean isAutoCloseExcluded() {
		return autoCloseExcluded;
	}
	public void setAutoCloseExcluded(boolean autoCloseExcluded) {
		this.autoCloseExcluded = autoCloseExcluded;
	}

}
