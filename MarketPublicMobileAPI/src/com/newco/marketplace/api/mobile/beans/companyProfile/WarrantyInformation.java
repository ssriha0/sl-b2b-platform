package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("warrantyInformation")
@XmlAccessorType(XmlAccessType.FIELD)
public class WarrantyInformation {

	
	@XStreamAlias("projectEstimatesChargeInd")
	private Boolean projectEstimatesChargeInd;
	
	@XStreamAlias("warrantyOnLaborInd")
	private Boolean warrantyOnLaborInd;
	
	@XStreamAlias("warrantyOnLabor")
	private String warrantyOnLabor;
	
	@XStreamAlias("warrantyOnPartsInd")
	private Boolean warrantyOnPartsInd;
	
	@XStreamAlias("warrantyOnParts")
	private String warrantyOnParts;
	
	public Boolean getWarrantyOnLaborInd() {
		return warrantyOnLaborInd;
	}
	public void setWarrantyOnLaborInd(Boolean warrantyOnLaborInd) {
		this.warrantyOnLaborInd = warrantyOnLaborInd;
	}
	public String getWarrantyOnLabor() {
		return warrantyOnLabor;
	}
	public void setWarrantyOnLabor(String warrantyOnLabor) {
		this.warrantyOnLabor = warrantyOnLabor;
	}
	public Boolean getProjectEstimatesChargeInd() {
		return projectEstimatesChargeInd;
	}
	public void setProjectEstimatesChargeInd(Boolean projectEstimatesChargeInd) {
		this.projectEstimatesChargeInd = projectEstimatesChargeInd;
	}
	public String getWarrantyOnParts() {
		return warrantyOnParts;
	}
	public void setWarrantyOnParts(String warrantyOnParts) {
		this.warrantyOnParts = warrantyOnParts;
	}
	public Boolean getWarrantyOnPartsInd() {
		return warrantyOnPartsInd;
	}
	public void setWarrantyOnPartsInd(Boolean warrantyOnPartsInd) {
		this.warrantyOnPartsInd = warrantyOnPartsInd;
	}
}
