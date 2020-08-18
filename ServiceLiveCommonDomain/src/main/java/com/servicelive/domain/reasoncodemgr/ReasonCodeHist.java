package com.servicelive.domain.reasoncodemgr;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * ReasonCode entity.
 * 
 */
@Entity
@Table(name = "reason_code_history")
public class ReasonCodeHist {

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
	@Column(name = "reason_code_history_id", unique = true, nullable = false)
	private Integer reasonCodeHistoryId;
		
	@Column(name = "reason_code_id")
	private int reasonCodeId;
	
	@Column(name = "buyer_id")
	private int buyerId;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	// Constructors

	/** default constructor */
	public ReasonCodeHist() {
		super();
	}

	public Integer getReasonCodeHistoryId() {
		return reasonCodeHistoryId;
	}

	public void setReasonCodeHistoryId(Integer reasonCodeHistoryId) {
		this.reasonCodeHistoryId = reasonCodeHistoryId;
	}


	public int getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(int reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}