package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("parts")
public class EstimateParts {

	@XStreamImplicit(itemFieldName="part")
	private List<EstimatePart> part;

	public List<EstimatePart> getPart() {
		return part;
	}

	public void setPart(List<EstimatePart> part) {
		this.part = part;
	}
	
}
