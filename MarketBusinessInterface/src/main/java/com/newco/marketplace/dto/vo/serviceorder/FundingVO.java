package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

public class FundingVO  extends SerializableBaseVO{

	private static final long serialVersionUID = -1032856395055147123L;
	private double amtToFund;
	private double amtToRefund;
	private double availableBalance;
	private double soProjectBalance;
	private boolean enoughFunds;
	private boolean decreaseFunds;
	private String soId;
	private double increaseAmt;

	public double getIncreaseAmt() {
		return increaseAmt;
	}
	public void setIncreaseAmt(double increaseAmt) {
		this.increaseAmt = increaseAmt;
	}
	public double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public boolean isEnoughFunds() {
		return enoughFunds;
	}
	public void setEnoughFunds(boolean enoughFunds) {
		this.enoughFunds = enoughFunds;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public double getSoProjectBalance() {
		return soProjectBalance;
	}
	public void setSoProjectBalance(double soProjectBalance) {
		this.soProjectBalance = soProjectBalance;
	}
	public boolean isDecreaseFunds() {
		return decreaseFunds;
	}
	public void setDecreaseFunds(boolean decreaseFunds) {
		this.decreaseFunds = decreaseFunds;
	}
	public double getAmtToFund() {
		return amtToFund;
	}
	public void setAmtToFund(double amtToFund) {
		this.amtToFund = amtToFund;
	}
	public double getAmtToRefund() {
		return amtToRefund;
	}
	public void setAmtToRefund(double amtToRefund) {
		this.amtToRefund = amtToRefund;
	}
}
