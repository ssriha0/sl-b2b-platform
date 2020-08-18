/**
 * 
 */
package com.newco.marketplace.dto.vo.financemanger;

import java.math.BigDecimal;

import com.newco.marketplace.vo.provider.BaseVO;

/**
 * @author Infosys
 *
 */
public class AdminPaymentVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vendorID;
	private	BigDecimal goodwillCredits;
	private	BigDecimal goodwillDebits;
	/* NetGoodwillAmount = Debits - Credits*/
	private	BigDecimal netGoodwillAmount;
	public String getVendorID() {
		return vendorID;
	}
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
	public BigDecimal getGoodwillCredits() {
		return goodwillCredits;
	}
	public void setGoodwillCredits(BigDecimal goodwillCredits) {
		this.goodwillCredits = goodwillCredits;
	}
	public BigDecimal getGoodwillDebits() {
		return goodwillDebits;
	}
	public void setGoodwillDebits(BigDecimal goodwillDebits) {
		this.goodwillDebits = goodwillDebits;
	}
	public BigDecimal getNetGoodwillAmount() {
		return netGoodwillAmount;
	}
	public void setNetGoodwillAmount(BigDecimal netGoodwillAmount) {
		this.netGoodwillAmount = netGoodwillAmount;
	} 

}
