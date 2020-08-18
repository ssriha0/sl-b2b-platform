package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import com.sears.os.vo.SerializableBaseVO;

public class TierRoutedProvider extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5658069740402316295L;
	private String soId;
	private Integer resourceId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	private Integer vendorId;
	private Integer spnId;
	private Double perfScore;
	private Double firmPerfScore;
	private Integer performanceLevelId;
	private String performanceLevelDesc;
	private String spnName;
	private String firstName;
	private String lastName;
	private String firmName;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Double getPerfScore() {
		return perfScore;
	}
	public void setPerfScore(Double perfScore) {
		this.perfScore = perfScore;
	}
	public Integer getPerformanceLevelId() {
		return performanceLevelId;
	}
	public void setPerformanceLevelId(Integer performanceLevelId) {
		this.performanceLevelId = performanceLevelId;
	}
	public String getPerformanceLevelDesc() {
		return performanceLevelDesc;
	}
	public void setPerformanceLevelDesc(String performanceLevelDesc) {
		this.performanceLevelDesc = performanceLevelDesc;
	}
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public Double getFirmPerfScore() {
		return firmPerfScore;
	}
	public void setFirmPerfScore(Double firmPerfScore) {
		this.firmPerfScore = firmPerfScore;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	
}
