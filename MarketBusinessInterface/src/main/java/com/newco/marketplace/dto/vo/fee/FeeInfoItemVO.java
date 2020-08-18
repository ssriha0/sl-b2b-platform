package com.newco.marketplace.dto.vo.fee;

import com.sears.os.vo.SerializableBaseVO;

public class FeeInfoItemVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7470807262202626281L;
	private Integer ledgerFeeDetailsId;
	private Integer calculatedBy;
	private Double feeValue;
	private Integer timePeriod;
	
	public Integer getCalculatedBy() {
		return calculatedBy;
	}
	public void setCalculatedBy(Integer calculatedBy) {
		this.calculatedBy = calculatedBy;
	}
	public Double getFeeValue() {
		return feeValue;
	}
	public void setFeeValue(Double feeValue) {
		this.feeValue = feeValue;
	}
	public Integer getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(Integer timePeriod) {
		this.timePeriod = timePeriod;
	}
	public Integer getLedgerFeeDetailsId() {
		return ledgerFeeDetailsId;
	}
	public void setLedgerFeeDetailsId(Integer ledgerFeeDetailsId) {
		this.ledgerFeeDetailsId = ledgerFeeDetailsId;
	}
}
