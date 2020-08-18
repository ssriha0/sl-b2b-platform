package com.servicelive.domain.spn.campaign;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.BaseDomain;
import com.servicelive.domain.provider.ProviderFirm;

@Entity
@Table ( name = "spnet_campaign_provider_firm")
public class CampaignProviderFirm extends BaseDomain {

	private static final long serialVersionUID = 20090330L;

	public CampaignProviderFirm() {
		super();
	}

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column ( name = "spnet_campaign_provider_firm_id" , unique= true,nullable = false, insertable = true, updatable = true )
	private Integer id;

	@ManyToOne
    @JoinColumn(name="campaign_id", insertable=false, updatable=false)
	private CampaignHeader campaign;

	@ManyToOne
	@JoinColumn( referencedColumnName="vendor_id", name = "provider_firm_id", nullable=false,insertable = true, updatable = true)
	private ProviderFirm  providerFirm;

	public CampaignHeader getCampaign() {
		return campaign;
	}

	public void setCampaign(CampaignHeader campaign) {
		this.campaign = campaign;
	}

	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}

	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
