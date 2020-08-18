package com.servicelive.orderfulfillment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "tier_route_eligilble_providers")
@XmlRootElement()
public class TierRouteProviders extends SOChild{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5658069740402316295L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tier_providers_id")
	private Integer tierProvidersId;
	
	@Column(name = "resource_id")
	private Integer providerResourceId;

	@Column(name = "vendor_id")
	private Integer vendorId;

	@Column(name = "spn_id")
	private Integer spnId;

	@Column(name = "routed_time_perf_score")
	private Double performanceScore;

	@Column(name = "routed_time_firm_perf_score")
	private Double firmPerformanceScore;
	
	@Column(name = "rank")
	private Integer rank;
	
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getProviderResourceId() {
		return providerResourceId;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public Double getPerformanceScore() {
		return performanceScore;
	}

	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}

	public void setProviderResourceId(Integer providerResourceId) {
		this.providerResourceId = providerResourceId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Double getFirmPerformanceScore() {
		return firmPerformanceScore;
	}

	public void setFirmPerformanceScore(Double firmPerformanceScore) {
		this.firmPerformanceScore = firmPerformanceScore;
	}

}
