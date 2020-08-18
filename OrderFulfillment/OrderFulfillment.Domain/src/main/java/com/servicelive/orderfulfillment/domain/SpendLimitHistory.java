
/*
 * this is the entity class for Spend Limit History table
 * 
 */
package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_spend_limit_history")
@XmlRootElement()
public class SpendLimitHistory extends SOElement{

	private static final long serialVersionUID = 5242278822016834315L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spend_limit_history_id")
	private Integer spendLimitHistoryId;


	@Column(name = "so_id")
	private String soId;

	@Column(name = "created_by")
	private String createdBy;  
	
	@Column(name = "created_by_name")
	private String createdByName;  
	
	@Column(name = "reason_code_id")
	private Integer reasonCodeId;

	@Column(name = "maximum_price")
	private BigDecimal maximumPrice;
	
	@Column(name = "old_price")
	private BigDecimal oldPrice;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name="modify_reason")
	private String modifyReason;


	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(BigDecimal maximumPrice) {
		this.maximumPrice = maximumPrice;
	}
	
	public BigDecimal getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Integer getSpendLimitHistoryId() {
		return spendLimitHistoryId;
	}


	public void setSpendLimitHistoryId(Integer spendLimitHistoryId) {
		this.spendLimitHistoryId = spendLimitHistoryId;
	}
	
	public Integer getReasonCodeId() {
		return reasonCodeId;
	}


	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

}
