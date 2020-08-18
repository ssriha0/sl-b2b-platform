/**
 *
 */
package com.newco.marketplace.vo.provider;

import java.math.BigDecimal;
import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author hoza
 *
 */
public class InsurancePolicyVO extends SerializableBaseVO{

	/**
	 *
	 */
	private static final long serialVersionUID = -6766399224116583309L;
	boolean policyIndicator = false;
	BigDecimal policyamount  = new BigDecimal(0);
	String name;
	Boolean isVerified = false;
	Date insVerifiedDate;
	//SL20014-adding new variable for credential description to fetch the correct documentId for buyer to display provider firm uploaded compliance documents
	String credTypeDesc;
	//SL20014-end
	public boolean isPolicyIndicator() {
		return policyIndicator;
	}
	public void setPolicyIndicator(boolean policyIndicator) {
		this.policyIndicator = policyIndicator;
	}
	public BigDecimal getPolicyamount() {
		return policyamount;
	}
	public void setPolicyamount(BigDecimal policyamount) {
		this.policyamount = policyamount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the isVerified
	 */
	public Boolean getIsVerified() {
		return isVerified;
	}
	/**
	 * @param isVerified the isVerified to set
	 */
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	public Date getInsVerifiedDate() {
		return insVerifiedDate;
	}
	public void setInsVerifiedDate(Date insVerifiedDate) {
		this.insVerifiedDate = insVerifiedDate;
	}
	public String getCredTypeDesc() {
		return credTypeDesc;
	}
	public void setCredTypeDesc(String credTypeDesc) {
		this.credTypeDesc = credTypeDesc;
	}
}
