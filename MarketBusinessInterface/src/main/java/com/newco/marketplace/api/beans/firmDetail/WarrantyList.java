package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("warrantyList")
@XmlAccessorType(XmlAccessType.FIELD)
public class WarrantyList {
		
	@XStreamImplicit(itemFieldName="warranty")
	private List<Warranty> warranty;

	public List<Warranty> getWarranty() {
		return warranty;
	}

	public void setWarranty(List<Warranty> warranty) {
		this.warranty = warranty;
	}


}
