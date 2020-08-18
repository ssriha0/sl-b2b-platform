package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_provider_invoice_part_location")
@XmlRootElement()
public class SoProviderInvoicePartLocation  extends InvoicePartChild {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "provider_part_location_id")
	private Integer partsLocationId;
	
	@Column(name = "supplier_name")
	private String supplierName;
	
	@Column(name = "street_1")
	private String street1;
	
	@Column(name = "street_2")
	private String street2;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state_cd")
	private String state;
	
	@Column(name = "zip")
	private String zip;
	
	@Column(name = "phone")
	private String phoneNo;
	
	@Column(name = "fax")
	private String faxNo;
	
	@Column(name = "latitude")
	private Float  latitude;
	
	@Column(name = "longitude")
	private Float longitude;
	
	@Column(name = "distance")
	private Double distance;
	
    public Integer getPartsLocationId() {
		return partsLocationId;
	}
	
	public void setPartsLocationId(Integer partsLocationId) {
		this.partsLocationId = partsLocationId;
	}

	public String getSupplierName() {
		return supplierName;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStreet1() {
		return street1;
	}
	
	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}
	
	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNo() {
		return phoneNo;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFaxNo() {
		return faxNo;
	}
	
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
