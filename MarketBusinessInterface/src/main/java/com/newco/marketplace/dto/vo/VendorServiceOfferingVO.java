package com.newco.marketplace.dto.vo;

import java.util.List;

public class VendorServiceOfferingVO {

	private Integer id;
	private String primaryIndustryId;
	
	private List<VendorServiceOfferPriceVo> vendorServiceOfferPriceVo;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public List<VendorServiceOfferPriceVo> getVendorServiceOfferPriceVo() {
		return vendorServiceOfferPriceVo;
	}
	public void setVendorServiceOfferPriceVo(List<VendorServiceOfferPriceVo> vendorServiceOfferPriceVo) {
		this.vendorServiceOfferPriceVo = vendorServiceOfferPriceVo;
	}
	public String getPrimaryIndustryId() {
		return primaryIndustryId;
	}
	public void setPrimaryIndustryId(String primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}
	
}
