package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing general section information.
 * @author Infosys
 *
 */

@XStreamAlias("orderDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralSection {
	
	@XStreamAlias("soId")
	private String soId ;
	
	@XStreamAlias("soStatus")
	private String soStatus;	
	
	@XStreamAlias("soSubStatus")
	private String soSubStatus;	
    
	@XStreamAlias("soTitle")
	private String soTitle;	
    
    @XStreamAlias("overView")
	private String overView;
    
	@XStreamAlias("buyerTerms")
	private String buyerTerms;
	
	@XStreamAlias("specialInstuctions")
	private String specialInstuctions;
	
	@XStreamAlias("soPrice")
	private Double soPrice;

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	public String getBuyerTerms() {
		return buyerTerms;
	}

	public void setBuyerTerms(String buyerTerms) {
		this.buyerTerms = buyerTerms;
	}

	public String getSpecialInstuctions() {
		return specialInstuctions;
	}

	public void setSpecialInstuctions(String specialInstuctions) {
		this.specialInstuctions = specialInstuctions;
	}

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}

	public String getSoSubStatus() {
		return soSubStatus;
	}

	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}

	public String getSoTitle() {
		return soTitle;
	}

	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Double getSoPrice() {
		return soPrice;
	}

	public void setSoPrice(Double soPrice) {
		this.soPrice = soPrice;
	}


	

	
}
