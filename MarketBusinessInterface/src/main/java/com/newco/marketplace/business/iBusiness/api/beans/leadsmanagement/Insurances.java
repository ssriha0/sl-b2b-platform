package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Insurances")
public class Insurances {
	@XStreamImplicit(itemFieldName = "Insurance")
	private  List<PostInsurance> postInsurance;

	public List<PostInsurance> getPostInsurance() {
		return postInsurance;
	}

	public void setPostInsurance(List<PostInsurance> postInsurance) {
		this.postInsurance = postInsurance;
	}

	

	
	

}
