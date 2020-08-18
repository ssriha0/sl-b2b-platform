package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.BaseDomain;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ServiceProvider;
// SL-12300 : Entity class to store the overriden information for provider resource.
@Entity
@Table(name = "spnet_provider_network_override")
public class ProviderNetworkStatusOverride extends BaseDomain{

	private static final long serialVersionUID = -6008256008796241342L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column ( name = "provider_network_status_override_id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name="spn_id", unique=false, nullable= false, insertable=true, updatable=true)
	private SPNHeader spnHeader;

	@ManyToOne
	@JoinColumn(name="service_provider_id", unique=false, nullable= false, insertable=true, updatable=true)
	private ServiceProvider serviceProvider;
	
	@Column(name="active_ind" , nullable = false, insertable = true, updatable = true)
    private boolean activeIndicator = false;
	
	@ManyToOne
	@JoinColumn(name = "previous_network_status", unique = false, nullable = false, insertable = true, updatable = true)
	private LookupSPNWorkflowState previousNetworkStatus;
	
	@ManyToOne
	@JoinColumn(name = "overrided_network_status", unique = false, nullable = false, insertable = true, updatable = true)
	private LookupSPNWorkflowState overridedNetworkStatus;
	
	@Column(name= "created_by", nullable = false, length =50)
	private String createdBy;
	
	@Column(name= "validity_date", nullable = true)
	private Date validityDate;
	
	@Column(name = "status_override_comments", unique = false, nullable = true, insertable = true, updatable = true, length = 750)
	private String statusOverrideComments;
	
	@Column(name = "status_override_reason_id", unique = false, nullable = true, insertable = true, updatable = true)
	private Integer statusOverrideReasonId;
	
	@Column(name = "no_expiration_date_ind", insertable = true, updatable = true)
	private Boolean noExpirationDateInd;

	public Boolean getNoExpirationDateInd() {
		return noExpirationDateInd;
	}

	public void setNoExpirationDateInd(Boolean noExpirationDateInd) {
		this.noExpirationDateInd = noExpirationDateInd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SPNHeader getSpnHeader() {
		return spnHeader;
	}

	public void setSpnHeader(SPNHeader spnHeader) {
		this.spnHeader = spnHeader;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public boolean isActiveIndicator() {
		return activeIndicator;
	}

	public void setActiveIndicator(boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	public LookupSPNWorkflowState getPreviousNetworkStatus() {
		return previousNetworkStatus;
	}

	public void setPreviousNetworkStatus(
			LookupSPNWorkflowState previousNetworkStatus) {
		this.previousNetworkStatus = previousNetworkStatus;
	}

	public LookupSPNWorkflowState getOverridedNetworkStatus() {
		return overridedNetworkStatus;
	}

	public void setOverridedNetworkStatus(
			LookupSPNWorkflowState overridedNetworkStatus) {
		this.overridedNetworkStatus = overridedNetworkStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatusOverrideComments() {
		return statusOverrideComments;
	}

	public void setStatusOverrideComments(String statusOverrideComments) {
		this.statusOverrideComments = statusOverrideComments;
	}

	public Integer getStatusOverrideReasonId() {
		return statusOverrideReasonId;
	}

	public void setStatusOverrideReasonId(Integer statusOverrideReasonId) {
		this.statusOverrideReasonId = statusOverrideReasonId;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
}
