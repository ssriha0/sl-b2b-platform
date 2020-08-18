package com.newco.marketplace.api.mobile.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("codes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Codes {

	@XStreamImplicit(itemFieldName = "code")
	private List<Code> code;

	public List<Code> getCode() {
		return code;
	}

	public void setCode(List<Code> code) {
		this.code = code;
	}

}
