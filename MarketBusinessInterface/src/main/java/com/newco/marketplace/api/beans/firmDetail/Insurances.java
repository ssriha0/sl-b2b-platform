package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("insurances")
@XmlAccessorType(XmlAccessType.FIELD)
public class Insurances {
	
	@XStreamImplicit(itemFieldName="insurance")
	private List<Insurance> insurance;

	public List<Insurance> getInsurance() {
		return insurance;
	}

	public void setInsurance(List<Insurance> insurance) {
		this.insurance = insurance;
	}

	
}
