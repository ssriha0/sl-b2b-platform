package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_part_labor_price_reason")
@XmlRootElement()

public class SOPartLaborPriceReason  extends SOChild{

	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -6915755286350489241L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reason_id")
	private Integer reasonId;
	
	@Column(name = "price_type")
	private String priceType;
	
	@Column(name = "reason_code_id")
	private String reasonCodeId;
	
	@Column(name = "reason_comments")
	private String reasonComments;
	
	public Integer getReasonId() {
		return reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(String reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public String getReasonComments() {
		return reasonComments;
	}

	public void setReasonComments(String reasonComments) {
		this.reasonComments = reasonComments;
	}


}
