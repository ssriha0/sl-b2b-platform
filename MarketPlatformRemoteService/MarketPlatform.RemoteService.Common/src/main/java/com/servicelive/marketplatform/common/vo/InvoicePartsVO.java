package com.servicelive.marketplatform.common.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class InvoicePartsVO implements Serializable{

    /**
	 *
	 */
	private static final long serialVersionUID = 4891774862616124095L;
    
  
    private BigDecimal reimbursementRate;  
    private BigDecimal  grossUpValue;
    private String identifier;
    
	
	public BigDecimal getReimbursementRate() {
		return reimbursementRate;
	}
	public void setReimbursementRate(BigDecimal reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}
	public BigDecimal getGrossUpValue() {
		return grossUpValue;
	}
	public void setGrossUpValue(BigDecimal grossUpValue) {
		this.grossUpValue = grossUpValue;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
    
    
    

    
    
}
