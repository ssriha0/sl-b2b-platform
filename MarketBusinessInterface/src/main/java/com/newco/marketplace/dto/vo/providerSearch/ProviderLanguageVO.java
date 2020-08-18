package com.newco.marketplace.dto.vo.providerSearch;

import java.util.List;

public class ProviderLanguageVO {
	
	private List<Integer> languageIds;
	private List<String> languageNames;
	
	public List<Integer> getLanguageIds() {
		return languageIds;
	}
	public void setLanguageIds(List<Integer> languageIds) {
		this.languageIds = languageIds;
	}
	public List<String> getLanguageNames() {
		return languageNames;
	}
	public void setLanguageNames(List<String> languageNames) {
		this.languageNames = languageNames;
	}

}
