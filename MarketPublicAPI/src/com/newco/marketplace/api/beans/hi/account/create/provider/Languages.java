package com.newco.marketplace.api.beans.hi.account.create.provider;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("languages")
public class Languages{

    @XStreamImplicit(itemFieldName="language")
	private List<String> language;

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(List<String> language) {
		this.language = language;
	}

 

}
