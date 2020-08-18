package com.servicelive.domain.tier.performance.vo;

import java.util.Date;


public class PerformanceVO  implements java.io.Serializable {

		
	private static final long serialVersionUID = -3186181427118588822L;
	private Integer vendorId;
	private Integer perfId;
	private Integer resourceId;
	private Integer spnId;
	private Integer buyerId;
	private Integer perfCriteriaId;
	private Double perfValue;
	private Double perfNinetyValue;
	private String perfCriteriaTimeframe;
	private String perfCriteriaScope;
	private Date createdDate;
	private Double perfCountAll;
	private Double perfValueAll;
	private Double ninetyPerfCount;
	private Double ninetyPerfVal;
	private String type;
	private boolean found =false;
	
	public boolean isFound() {
		return found;
	}
	public void setFound(boolean found) {
		this.found = found;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPerfId() {
		return perfId;
	}
	public void setPerfId(Integer perfId) {
		this.perfId = perfId;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Double getPerfNinetyValue() {
		return perfNinetyValue;
	}
	public void setPerfNinetyValue(Double perfNinetyValue) {
		this.perfNinetyValue = perfNinetyValue;
	}
	
	public Double getPerfCountAll() {
		return perfCountAll;
	}
	public void setPerfCountAll(Double perfCountAll) {
		this.perfCountAll = perfCountAll;
	}
	public Double getPerfValueAll() {
		return perfValueAll;
	}
	public void setPerfValueAll(Double perfValueAll) {
		this.perfValueAll = perfValueAll;
	}
	public Double getNinetyPerfCount() {
		return ninetyPerfCount;
	}
	public void setNinetyPerfCount(Double ninetyPerfCount) {
		this.ninetyPerfCount = ninetyPerfCount;
	}
	public Double getNinetyPerfVal() {
		return ninetyPerfVal;
	}
	public void setNinetyPerfVal(Double ninetyPerfVal) {
		this.ninetyPerfVal = ninetyPerfVal;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getPerfCriteriaId() {
		return perfCriteriaId;
	}
	public void setPerfCriteriaId(Integer perfCriteriaId) {
		this.perfCriteriaId = perfCriteriaId;
	}
	public Double getPerfValue() {
		return perfValue;
	}
	public void setPerfValue(Double perfValue) {
		this.perfValue = perfValue;
	}
	public String getPerfCriteriaTimeframe() {
		return perfCriteriaTimeframe;
	}
	public void setPerfCriteriaTimeframe(String perfCriteriaTimeframe) {
		this.perfCriteriaTimeframe = perfCriteriaTimeframe;
	}
	public String getPerfCriteriaScope() {
		return perfCriteriaScope;
	}
	public void setPerfCriteriaScope(String perfCriteriaScope) {
		this.perfCriteriaScope = perfCriteriaScope;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	private Date modifiedDate;
	
}
