package com.newco.marketplace.dto.vo.fee;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.SerializableBaseVO;

public class FeeInfoVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8484175721522757116L;
	private Integer ledgerFeeId;
	private Integer ledgerFeeTypeId;
	private String feeName;
	private Integer ledgerEntryRuleId;
	private Double feeAmount;
	private ArrayList<FeeInfoItemVO> feeInfoItem;
		
	public Integer getLedgerEntryRuleId() {
		return ledgerEntryRuleId;
	}
	public void setLedgerEntryRuleId(Integer ledgerEntryRuleId) {
		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}
	public ArrayList<FeeInfoItemVO> getFeeInfoItem() {
		return feeInfoItem;
	}
	public void setFeeInfoItem(ArrayList<FeeInfoItemVO> feeInfoItem) {
		this.feeInfoItem = feeInfoItem;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public Integer getLedgerFeeId() {
		return ledgerFeeId;
	}
	public void setLedgerFeeId(Integer ledgerFeeId) {
		this.ledgerFeeId = ledgerFeeId;
	}
	public Integer getLedgerFeeTypeId() {
		return ledgerFeeTypeId;
	}
	public void setLedgerFeeTypeId(Integer ledgerFeeTypeId) {
		this.ledgerFeeTypeId = ledgerFeeTypeId;
	}
	public Double getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("ledgerFeeId", getLedgerFeeId())
			.append("ledgerFeeTypeId", getLedgerFeeTypeId())
			.append("feeName", getFeeName())
			.append("ledgerEntryRuleId", getLedgerEntryRuleId())	
			.append("feeAmount", getFeeAmount())
			.toString();
	}
}
