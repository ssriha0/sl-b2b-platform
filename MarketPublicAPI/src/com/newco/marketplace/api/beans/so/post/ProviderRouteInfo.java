package com.newco.marketplace.api.beans.so.post;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing provider route information for 
 * the SOPostService
 * @author Infosys
 *
 */
@XStreamAlias("providerRouteInfo")
public class ProviderRouteInfo {
	
	@XStreamAlias("maxDistance")
	private String maxDistance;
	
	@XStreamAlias("minRating")
	private String minRating;
	
	@XStreamAlias("languages")
	private Languages language;
	
	@XStreamAlias("template")
	@XStreamAsAttribute()   
	private String template;
	
	@XStreamAlias("version")
	@XStreamAsAttribute()   
	private String version;
	
	@XStreamAlias("specificProviders")
	private SpecificProviders specificProviders;

	public Languages getLanguage() {
		return language;
	}

	public void setLanguage(Languages language) {
		this.language = language;
	}

	public SpecificProviders getSpecificProviders() {
		return specificProviders;
	}

	public void setSpecificProviders(SpecificProviders specificProviders) {
		this.specificProviders = specificProviders;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(String maxDistance) {
		this.maxDistance = maxDistance;
	}

	public String getMinRating() {
		return minRating;
	}

	public void setMinRating(String minRating) {
		this.minRating = minRating;
	}

	
}
