package com.servicelive.domain.d2cproviderportal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.domain.provider.ProviderFirm;

@Entity
@Table(name = "vendor_category_map")
public class VendorCategoryMap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="vendor_id")
	private ProviderFirm providerFirm;
	
	@ManyToOne
	@JoinColumn (name = "category_id")
	private LookupPrimaryIndustry lookupPrimaryIndustry;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}

	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}

	public LookupPrimaryIndustry getLookupPrimaryIndustry() {
		return lookupPrimaryIndustry;
	}

	public void setLookupPrimaryIndustry(LookupPrimaryIndustry lookupPrimaryIndustry) {
		this.lookupPrimaryIndustry = lookupPrimaryIndustry;
	}
}
