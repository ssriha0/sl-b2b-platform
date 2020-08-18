package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("languages")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderLanguages {	
	@OptionalParam
	@XStreamImplicit(itemFieldName="language")
	private List<String> languages;
	
	public ProviderLanguages() {
		
	}
	
	public ProviderLanguages(List<String> languages) {
		this.languages = languages;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
}
