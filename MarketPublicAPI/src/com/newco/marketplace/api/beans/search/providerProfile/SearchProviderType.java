/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing all information of 
 * the providerType
 * @author Infosys
 *
 *
 */
@XStreamAlias("providers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchProviderType {
	
	@XStreamAlias("resourceId")
	@XStreamAsAttribute()
	private Integer resourceId;

	@OptionalParam
	@XStreamAlias("distance")
	@XStreamAsAttribute()
	private float distance;
	
	@XStreamAlias("name")
	private String name;

	@XStreamAlias("memberSince")	
	private Date memberSince;

	@OptionalParam
	@XStreamAlias("jobTitle")
	private String jobTitle;

	@OptionalParam
	@XStreamAlias("imageUrl")
	private String imageUrl;


	@OptionalParam
	@XStreamAlias("metrics")
	private ProviderMetrics metrics;

	@OptionalParam
	@XStreamAlias("reviews")
	private ProviderReviews reviews;

	@OptionalParam
	@XStreamAlias("credentials")
	private ProviderCredentials credentials;

	@OptionalParam
	@XStreamAlias("skills")
	private ProviderSkills skills;
	
	@OptionalParam
	@XStreamAlias("availability")
	private ProviderAvailability availability;
	
	@OptionalParam
	@XStreamAlias("languages")
	private ProviderLanguages languages;
	
	
	@OptionalParam
	@XStreamAlias("zip")
	private String zip;

	public SearchProviderType(){
		
	}
	public SearchProviderType(Integer resourceId, String name, float distance, 
			Date memberSince, String jobTitle, String imageUrl) {
		this.resourceId  = resourceId;
		this.name = name;
		this.distance = distance;
		this.memberSince = memberSince;
		this.jobTitle = jobTitle;
		this.imageUrl = imageUrl;
	}
	
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ProviderMetrics getMetrics() {
		return metrics;
	}

	public void setMetrics(ProviderMetrics metrics) {
		this.metrics = metrics;
	}

	public ProviderReviews getReviews() {
		return reviews;
	}

	public void setReviews(ProviderReviews reviews) {
		this.reviews = reviews;
	}

	public ProviderCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(ProviderCredentials credentials) {
		this.credentials = credentials;
	}

	public ProviderSkills getSkills() {
		return skills;
	}

	public void setSkills(ProviderSkills skills) {
		this.skills = skills;
	}

	public ProviderAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(ProviderAvailability availability) {
		this.availability = availability;
	}

	public ProviderLanguages getLanguages() {
		return languages;
	}

	public void setLanguages(ProviderLanguages languages) {
		this.languages = languages;
	}
	
	public void setLanguages(List<String> langList) {
		this.languages = new ProviderLanguages(langList);
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	

}
