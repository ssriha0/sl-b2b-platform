package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("languages")
public class Languages {	
	@OptionalParam
	@XStreamImplicit(itemFieldName="language")
	private List<String> languages;
	
	public Languages() {
		
	}
	
	public Languages(List<String> languages) {
		this.languages = languages;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
}
