package com.newco.marketplace.dto.vo.survey;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("options")
@XmlAccessorType(XmlAccessType.FIELD)
public class Options {
	
	@XStreamImplicit(itemFieldName="optionID")
	List<Integer> optionID;

	public List<Integer> getOptionID() {
		return optionID;
	}

	public void setOptionID(List<Integer> optionID) {
		this.optionID = optionID;
	}

	
}
