package com.newco.marketplace.web.action.details.zerobid.providerapplication.viewobjects;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.servicelive.activitylog.domain.ActivityAssociationType;
import com.servicelive.activitylog.domain.ActivityType;

public class ActivityLogViewObject {
	
	ActivityType type;
	Long providerId;
	Long activityId;
	Long parentActivityId;
	String createdBy;
	Date createdOn;
	String post;
	String comment;
	
	Date expiration;
	Double laborEffort;
	Double laborRate;
	Double maximumLaborCost;
	Double materialCost;
	
	ActivityAssociationType sourceType;
	ActivityAssociationType targetType;
	
	SimpleDateFormat dateOutput = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
	SimpleDateFormat shortDateOutput = new SimpleDateFormat("MM/dd/yyyy");
	
	DecimalFormat currency = new DecimalFormat("$0.00");
	
	public int getType() {
		return type.getId();
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return dateOutput.format(createdOn);
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getExpiration() {
		return shortDateOutput.format(expiration);
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public Double getLaborEffort() {
		return laborEffort;
	}
	public void setLaborEffort(Double laborEffort) {
		this.laborEffort = laborEffort;
	}
	public String getLaborRate() {
		return currency.format(laborRate);
	}
	public void setLaborRate(Double laborRate) {
		this.laborRate = laborRate;
	}
	public String getMaterialCost() {
		return currency.format(materialCost);
	}
	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}
	public String getSourceType() {
		return sourceType.toString();
	}
	public void setSourceType(ActivityAssociationType sourceType) {
		this.sourceType = sourceType;
	}
	public String getTargetType() {
		return targetType.toString();
	}
	public void setTargetType(ActivityAssociationType targetType) {
		this.targetType = targetType;
	}
	public Long getParentActivityId() {
		return parentActivityId;
	}
	public void setParentActivityId(Long parentActivityId) {
		this.parentActivityId = parentActivityId;
	}
	public Long getProviderId() {
		return providerId;
	}
	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}
	public String getMaximumLaborCost() {
		return currency.format(maximumLaborCost);
	}
	public void setMaximumLaborCost(Double maximumLaborCost) {
		this.maximumLaborCost = maximumLaborCost;
	}
	
	public String getEstimatedLaborCost() {
		return currency.format(laborEffort * laborRate);
	}
}
