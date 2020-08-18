package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.common.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XSD(name="closeSORequest.xsd", path="/resources/schemas/so/")
@XStreamAlias("closeSORequest")
public class CloseSORequest extends UserIdentificationRequest {
	 	
	@XStreamAlias("finalPartsPrice")
	private Double finalPartsPrice;
 	
	@XStreamAlias("finalLaborPrice")
	private Double finalLaborPrice;

	public Double getFinalPartsPrice() {
		return finalPartsPrice;
	}

	public void setFinalPartsPrice(Double finalPartsPrice) {
		this.finalPartsPrice = finalPartsPrice;
	}

	public Double getFinalLaborPrice() {
		return finalLaborPrice;
	}

	public void setFinalLaborPrice(Double finalLaborPrice) {
		this.finalLaborPrice = finalLaborPrice;
	}

}
