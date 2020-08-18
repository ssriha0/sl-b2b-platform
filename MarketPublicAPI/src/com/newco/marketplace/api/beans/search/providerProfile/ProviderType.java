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

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing all information of 
 * the providerType
 * @author Infosys
 * @author Shekhar Nirkhe
 *
 */
@XStreamAlias("provider")
public class ProviderType {
	
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
	@XStreamAlias("company")
	private Company company;

	@OptionalParam
	@XStreamAlias("metrics")
	private Metrics metrics;

	@OptionalParam
	@XStreamAlias("reviews")
	private Reviews reviews;

	@OptionalParam
	@XStreamAlias("credentials")
	private Credentials credentials;

	@OptionalParam
	@XStreamAlias("skills")
	private Skills skills;
	
	@OptionalParam
	@XStreamAlias("availability")
	private Availability availability;
	
	@OptionalParam
	@XStreamAlias("languages")
	private Languages languages;
	
	
	@OptionalParam
	@XStreamAlias("zip")
	private String zip;

	public ProviderType(Integer resourceId, String name, float distance, 
			Date memberSince, String jobTitle, String imageUrl) {
		this.resourceId  = resourceId;
		this.name = name;
		this.distance = distance;
		this.memberSince = memberSince;
		this.jobTitle = jobTitle;
		this.imageUrl = imageUrl;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Metrics getMetrics() {
		return metrics;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}

	public Reviews getReviews() {
		return reviews;
	}

	public void setReviews(Reviews reviews) {
		this.reviews = reviews;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Skills getSkills() {
		return skills;
	}

	public void setSkills(Skills skills) {
		this.skills = skills;
	}

	public int getResourceId() {
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

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability value) {
		this.availability = value;	
	}

	public Languages getLanguages() {
		return languages;
	}

	public void setLanguages(Languages languages) {
		this.languages = languages;
	}
	
	public void setLanguages(List<String> langList) {
		this.languages = new Languages(langList);
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
