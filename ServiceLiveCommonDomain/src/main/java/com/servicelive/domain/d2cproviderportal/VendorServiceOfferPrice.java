package com.servicelive.domain.d2cproviderportal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table ( name = "vendor_serviceoffer_price")
public class VendorServiceOfferPrice {
	public VendorServiceOfferPrice() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="vendor_service_area_id")
	private Integer vendorServiceAreaId;
	@Column(name="modified_by")
	private String modifiedBy;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="vendor_serviceoffering_id")
	private VendorServiceOffering vendorServiceOffering;
	@Column(name="price")
	private Double price;
	@Column(name="daily_limit")
	private Integer dailyLimit;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVendorServiceAreaId() {
		return vendorServiceAreaId;
	}
	public void setVendorServiceAreaId(Integer vendorServiceAreaId) {
		this.vendorServiceAreaId = vendorServiceAreaId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public VendorServiceOffering getVendorServiceOffering() {
		return vendorServiceOffering;
	}
	public void setVendorServiceOffering(
			VendorServiceOffering vendorServiceOffering) {
		this.vendorServiceOffering = vendorServiceOffering;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(Integer dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
}
