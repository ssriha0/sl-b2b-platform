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
@Table(name = "vendor_category_price_card")
public class VendorCategoryPriceCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "price")
	private Double price;

	@ManyToOne
	@JoinColumn(name = "vendor_cat_map_id")
	private VendorCategoryMap vendorCategoryMap;

	@ManyToOne
	@JoinColumn(name = "service_day_id")
	private LookupServiceDay serviceDay;

	@ManyToOne
	@JoinColumn(name = "service_rate_period_id")
	private LookupServiceRatePeriod serviceRatePeriod;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public VendorCategoryMap getVendorCategoryMap() {
		return vendorCategoryMap;
	}

	public void setVendorCategoryMap(VendorCategoryMap vendorCategoryMap) {
		this.vendorCategoryMap = vendorCategoryMap;
	}

	public LookupServiceDay getServiceDay() {
		return serviceDay;
	}

	public void setServiceDay(LookupServiceDay serviceDay) {
		this.serviceDay = serviceDay;
	}

	public LookupServiceRatePeriod getServiceRatePeriod() {
		return serviceRatePeriod;
	}

	public void setServiceRatePeriod(LookupServiceRatePeriod serviceRatePeriod) {
		this.serviceRatePeriod = serviceRatePeriod;
	}
}
