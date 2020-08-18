package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XSD(name = "updateSOProviderPartRequest.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "updateSOProviderPartRequest")
@XStreamAlias("updateSOProviderPartRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateSOProviderPartRequest {

	@XStreamAlias("currentTripNo")
	private String currentTripNo;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;
	
	@XStreamAlias("partList")
	private InvoiceSupplierList partList;
	
	/**Result of validation*/
	@XStreamOmitField
	private ResultsCode validationCode;



	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public String getCurrentTripNo() {
		return currentTripNo;
	}

	public void setCurrentTripNo(String currentTripNo) {
		this.currentTripNo = currentTripNo;
	}

	public InvoiceSupplierList getPartList() {
		return partList;
	}

	public void setPartList(InvoiceSupplierList partList) {
		this.partList = partList;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

}
