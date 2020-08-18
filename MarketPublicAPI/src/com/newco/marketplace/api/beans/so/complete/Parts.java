package com.newco.marketplace.api.beans.so.complete;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("parts")
public class Parts {

	@XStreamImplicit(itemFieldName="part")
	private List<PartList> part;

	public List<PartList> getPart() {
		return part;
	}

	public void setPart(List<PartList> part) {
		this.part = part;
	}
	
	
}
