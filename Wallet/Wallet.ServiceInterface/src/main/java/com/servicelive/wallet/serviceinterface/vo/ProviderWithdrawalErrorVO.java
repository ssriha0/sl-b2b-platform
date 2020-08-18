package com.servicelive.wallet.serviceinterface.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.common.vo.ABaseVO;

/**
 * Class WalletVO.
 */
@XmlRootElement()
public class ProviderWithdrawalErrorVO extends ABaseVO {

	/** serialVersionUID. */
	private static final long serialVersionUID = -2904710734175035010L;

	/** sessionWithdrawalAmt */
	private Double sessionWithdrawalAmt;

	/** sessionWithdrawalCount */
	private Integer sessionWithdrawalCount;

	/** errorMessageId */
	private Integer errorMessageId;

	/** withdrawalAmt. */
	private Double withdrawalAmt;

	/** pastWithdrawalAmt */
	private Double pastWithdrawalAmt;

	/** pastWithdrawalCount */
	private Double pastWithdrawalCount;
	
	/** pastWithdrawalCount */
	private Double vlbcBalance;


	public Double getVlbcBalance() {
		return vlbcBalance;
	}
	
	/**
	 * setVlbcBalance.
	 * 
	 * @param vlbcBalance
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setVlbcBalance(Double vlbcBalance) {
		this.vlbcBalance = vlbcBalance;
	}

	public Double getPastWithdrawalAmt() {
		return pastWithdrawalAmt;
	}
	
	/**
	 * setPastWithdrawalAmt.
	 * 
	 * @param pastWithdrawalAmt
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setPastWithdrawalAmt(Double pastWithdrawalAmt) {
		this.pastWithdrawalAmt = pastWithdrawalAmt;
	}

	public Double getSessionWithdrawalAmt() {
		return sessionWithdrawalAmt;
	}
	/**
	 * setSessionWithdrawalAmt.
	 * 
	 * @param sessionWithdrawalAmt
	 * 
	 * @return void
	 */
	public void setSessionWithdrawalAmt(Double sessionWithdrawalAmt) {
		this.sessionWithdrawalAmt = sessionWithdrawalAmt;
	}

	public Integer getSessionWithdrawalCount() {
		return sessionWithdrawalCount;
	}
	/**
	 * setSessionWithdrawalCount.
	 * 
	 * @param sessionWithdrawalCount
	 * 
	 * @return void
	 */
	public void setSessionWithdrawalCount(Integer sessionWithdrawalCount) {
		this.sessionWithdrawalCount = sessionWithdrawalCount;
	}

	public Integer getErrorMessageId() {
		return errorMessageId;
	}
	/**
	 * setErrorMessageId.
	 * 
	 * @param errorMessageId
	 * 
	 * @return void
	 */
	public void setErrorMessageId(Integer errorMessageId) {
		this.errorMessageId = errorMessageId;
	}

	public Double getWithdrawalAmt() {
		return withdrawalAmt;
	}
	/**
	 * setWithdrawalAmt.
	 * 
	 * @param withdrawalAmt
	 * 
	 * @return void
	 */
	public void setWithdrawalAmt(Double withdrawalAmt) {
		this.withdrawalAmt = withdrawalAmt;
	}

	public Double getPastWithdrawalCount() {
		return pastWithdrawalCount;
	}
	/**
	 * setPastWithdrawalCount.
	 * 
	 * @param pastWithdrawalCount
	 * 
	 * @return void
	 */
	public void setPastWithdrawalCount(Double pastWithdrawalCount) {
		this.pastWithdrawalCount = pastWithdrawalCount;
	}
	@Override
	public String toString() {
		return null;
	}

}