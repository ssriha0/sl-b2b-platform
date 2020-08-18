/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * This class will be used to represent the language aspect of the provider.
 * @author priti
 *
 */
@XStreamAlias("providerLanguage")
public class ProviderLanguage {
	
	@XStreamAlias("languageName")
	private String languageName;

	@XStreamAlias("count")
	private int count;

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String value) {
		this.languageName = value;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
