package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.BaseDomain;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ProviderFirm;
//SL-12300 : Entity class to store the overridden information for provider firm.
@Entity
@Table(name = "spnet_provider_firm_network_override")
public class ProviderFirmNetworkStatusOverride extends BaseDomain{

	private static final long serialVersionUID = -8034931024639625583L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column ( name = "firm_network_status_override_id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne (fetch=FetchType.EAGER, cascade= { CascadeType.REFRESH} )
    @JoinColumn(name="spn_id", unique=false, nullable= false, insertable=true, updatable=true)
	private SPNHeader spnHeader;

	@ManyToOne
	@JoinColumn(name="provider_firm_id", unique=false, nullable= false, insertable=true, updatable=true)
	private ProviderFirm providerFirm;
	
	@Column(name="active_ind" , nullable = true, insertable = true, updatable = true)
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

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	public Integer getStatusOverrideReasonId() {
		return statusOverrideReasonId;
	}

	public void setStatusOverrideReasonId(Integer statusOverrideReasonId) {
		this.statusOverrideReasonId = statusOverrideReasonId;
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

	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}

	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}

	public boolean isActiveIndicator() {
		return activeIndicator;
	}

	public void setActiveIndicator(boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	
}
