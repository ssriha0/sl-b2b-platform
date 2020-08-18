package com.servicelive.domain.d2cproviderportal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vendor_service_offered_on")
public class VendorServiceOfferedOn {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	@ManyToOne
	@JoinColumn (name = "offers_on_id")
	private LuOffersOn luOffersOn;
	@ManyToOne
	@JoinColumn (name = "vendor_category_map_id")
	private VendorCategoryMap vendorCategoryMap;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LuOffersOn getLuOffersOn() {
		return luOffersOn;
	}
	public void setLuOffersOn(LuOffersOn luOffersOn) {
		this.luOffersOn = luOffersOn;
	}
	public VendorCategoryMap getVendorCategoryMap() {
		return vendorCategoryMap;
	}
	public void setVendorCategoryMap(VendorCategoryMap vendorCategoryMap) {
		this.vendorCategoryMap = vendorCategoryMap;
	}
	
}
