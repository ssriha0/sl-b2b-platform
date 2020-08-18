
package com.servicelive.wallet.creditcard;

import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.common.CommonConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XmlRootElement(name="CreditRefundRequest")
@XStreamAlias("CreditRefundRequest")
public class CreditRefundRequest {

	
	@XStreamAsAttribute
    final String xmlns = CommonConstants.HS_REFUND_NAMESPACE;

	private String acctNo;
	private String transAmt;
	private String transTs;
	private String expirationDate;
	private String track2;
	private String termId;
	private String zipCode;
	private String inetBased;
	private String maskedAcctNo;
	
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	
	public String getTransTs() {
		return transTs;
	}
	
	public void setTransTs(String transTs) {
		this.transTs = transTs;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getTrack2() {
		return track2;
	}
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getInetBased() {
		return inetBased;
	}
	public void setInetBased(String inetBased) {
		this.inetBased = inetBased;
	}
	public String getMaskedAcctNo() {
		return maskedAcctNo;
	}
	public void setMaskedAcctNo(String maskedAcctNo) {
		this.maskedAcctNo = maskedAcctNo;
	}

	

}
