package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("parts")
public class Parts {

	@XStreamImplicit(itemFieldName = "part")
	private List<Part> part;

	public List<Part> getPart() {
		return part;
	}

	public void setPart(List<Part> part) {
		this.part = part;
	}

	

}
