package com.servicelive.domain.reasoncodemgr;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ReasonCode entity.
 * 
 */
@Entity
@Table(name = "reason_code_types")
public class ReasonCodeTypes {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405708666300608558L;

	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "reason_type_id", unique = true, nullable = false)
	private Integer reasonTypeId;
		
	@Column(name = "reason_code_type")
	private String reasonCodeType;
	
	// Constructors

	/** default constructor */
	public ReasonCodeTypes() {
		super();
	}

	public Integer getReasonTypeId() {
		return reasonTypeId;
	}

	public void setReasonTypeId(Integer reasonTypeId) {
		this.reasonTypeId = reasonTypeId;
	}

	public String getReasonCodeType() {
		return reasonCodeType;
	}

	public void setReasonCodeType(String reasonCodeType) {
		this.reasonCodeType = reasonCodeType;
	}


}