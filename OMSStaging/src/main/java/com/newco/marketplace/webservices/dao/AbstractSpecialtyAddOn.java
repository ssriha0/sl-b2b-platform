package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractSpecialtyAddOn entity provides the base persistence definition of the
 * SpecialtyAddOn entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractSpecialtyAddOn implements java.io.Serializable {

	// Fields

	private Integer specialtyAddOnId;
	private String specialtyCode;
	private String specialtyDescription;
	private Integer majorHeadingCode;
	private String majorHeadingDescription;
	private Integer subHeadingCode;
	private String subHeadingDescription;
	private String classificationCode;
	private String classificationDescription;
	private String coverage;
	private String coverageDescription;
	private String jobCodeSuffix;
	private String stockNumber;
	private String jobCodeDescripton;
	private String longDescription;
	private String inclusionDescription;
	private Integer sequence;
	private String contractorCostType;
	private String contractorCostTypeDescription;
	private Integer dispatchDaysOut;
	private Date activeDate;
	private Date updateDate;
	private Date deactiveDate;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private Double markUpPercentage;

	// Constructors

	/** default constructor */
	public AbstractSpecialtyAddOn() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractSpecialtyAddOn(Integer specialtyAddOnId,
			String specialtyCode, String stockNumber) {
		this.specialtyAddOnId = specialtyAddOnId;
		this.specialtyCode = specialtyCode;
		this.stockNumber = stockNumber;
	}

	/** full constructor */
	public AbstractSpecialtyAddOn(Integer specialtyAddOnId,
			String specialtyCode, String specialtyDescription,
			Integer majorHeadingCode, String majorHeadingDescription,
			Integer subHeadingCode, String subHeadingDescription,
			String classificationCode, String classificationDescription,
			String coverage, String coverageDescription, String jobCodeSuffix,
			String stockNumber, String jobCodeDescripton,
			String longDescription, String inclusionDescription,
			Integer sequence, String contractorCostType,
			String contractorCostTypeDescription, Integer dispatchDaysOut,
			Date activeDate, Date updateDate, Date deactiveDate,
			Date createdDate, Date modifiedDate, String modifiedBy, Double markUpPercentage) {
		this.specialtyAddOnId = specialtyAddOnId;
		this.specialtyCode = specialtyCode;
		this.specialtyDescription = specialtyDescription;
		this.majorHeadingCode = majorHeadingCode;
		this.majorHeadingDescription = majorHeadingDescription;
		this.subHeadingCode = subHeadingCode;
		this.subHeadingDescription = subHeadingDescription;
		this.classificationCode = classificationCode;
		this.classificationDescription = classificationDescription;
		this.coverage = coverage;
		this.coverageDescription = coverageDescription;
		this.jobCodeSuffix = jobCodeSuffix;
		this.stockNumber = stockNumber;
		this.jobCodeDescripton = jobCodeDescripton;
		this.longDescription = longDescription;
		this.inclusionDescription = inclusionDescription;
		this.sequence = sequence;
		this.contractorCostType = contractorCostType;
		this.contractorCostTypeDescription = contractorCostTypeDescription;
		this.dispatchDaysOut = dispatchDaysOut;
		this.activeDate = activeDate;
		this.updateDate = updateDate;
		this.deactiveDate = deactiveDate;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.markUpPercentage = markUpPercentage;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "specialty_add_on_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getSpecialtyAddOnId() {
		return this.specialtyAddOnId;
	}

	public void setSpecialtyAddOnId(Integer specialtyAddOnId) {
		this.specialtyAddOnId = specialtyAddOnId;
	}

	@Column(name = "specialty_code", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public String getSpecialtyCode() {
		return this.specialtyCode;
	}

	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}

	@Column(name = "specialty_description", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getSpecialtyDescription() {
		return this.specialtyDescription;
	}

	public void setSpecialtyDescription(String specialtyDescription) {
		this.specialtyDescription = specialtyDescription;
	}

	@Column(name = "major_heading_code", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getMajorHeadingCode() {
		return this.majorHeadingCode;
	}

	public void setMajorHeadingCode(Integer majorHeadingCode) {
		this.majorHeadingCode = majorHeadingCode;
	}

	@Column(name = "major_heading_description", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getMajorHeadingDescription() {
		return this.majorHeadingDescription;
	}

	public void setMajorHeadingDescription(String majorHeadingDescription) {
		this.majorHeadingDescription = majorHeadingDescription;
	}

	@Column(name = "sub_heading_code", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getSubHeadingCode() {
		return this.subHeadingCode;
	}

	public void setSubHeadingCode(Integer subHeadingCode) {
		this.subHeadingCode = subHeadingCode;
	}

	@Column(name = "sub_heading_description", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSubHeadingDescription() {
		return this.subHeadingDescription;
	}

	public void setSubHeadingDescription(String subHeadingDescription) {
		this.subHeadingDescription = subHeadingDescription;
	}

	@Column(name = "classification_code", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getClassificationCode() {
		return this.classificationCode;
	}

	public void setClassificationCode(String classificationCode) {
		this.classificationCode = classificationCode;
	}

	@Column(name = "classification_description", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getClassificationDescription() {
		return this.classificationDescription;
	}

	public void setClassificationDescription(String classificationDescription) {
		this.classificationDescription = classificationDescription;
	}

	@Column(name = "coverage", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public String getCoverage() {
		return this.coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	@Column(name = "coverage_description", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getCoverageDescription() {
		return this.coverageDescription;
	}

	public void setCoverageDescription(String coverageDescription) {
		this.coverageDescription = coverageDescription;
	}

	@Column(name = "job_code_suffix", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public String getJobCodeSuffix() {
		return this.jobCodeSuffix;
	}

	public void setJobCodeSuffix(String jobCodeSuffix) {
		this.jobCodeSuffix = jobCodeSuffix;
	}

	@Column(name = "stock_number", unique = false, nullable = false, insertable = true, updatable = true, length = 5)
	public String getStockNumber() {
		return this.stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	@Column(name = "job_code_descripton", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getJobCodeDescripton() {
		return this.jobCodeDescripton;
	}

	public void setJobCodeDescripton(String jobCodeDescripton) {
		this.jobCodeDescripton = jobCodeDescripton;
	}

	@Column(name = "long_description", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getLongDescription() {
		return this.longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	@Column(name = "inclusion_description", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public String getInclusionDescription() {
		return this.inclusionDescription;
	}

	public void setInclusionDescription(String inclusionDescription) {
		this.inclusionDescription = inclusionDescription;
	}

	@Column(name = "sequence", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Column(name = "contractor_cost_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getContractorCostType() {
		return this.contractorCostType;
	}

	public void setContractorCostType(String contractorCostType) {
		this.contractorCostType = contractorCostType;
	}

	@Column(name = "contractor_cost_type_description", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getContractorCostTypeDescription() {
		return this.contractorCostTypeDescription;
	}

	public void setContractorCostTypeDescription(
			String contractorCostTypeDescription) {
		this.contractorCostTypeDescription = contractorCostTypeDescription;
	}

	@Column(name = "dispatch_days_out", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getDispatchDaysOut() {
		return this.dispatchDaysOut;
	}

	public void setDispatchDaysOut(Integer dispatchDaysOut) {
		this.dispatchDaysOut = dispatchDaysOut;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "active_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Date getActiveDate() {
		return this.activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "update_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deactive_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Date getDeactiveDate() {
		return this.deactiveDate;
	}

	public void setDeactiveDate(Date deactiveDate) {
		this.deactiveDate = deactiveDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "mark_up_percentage", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 4)
	public Double getMarkUpPercentage() {
		return markUpPercentage;
	}

	public void setMarkUpPercentage(Double markUpPercentage) {
		this.markUpPercentage = markUpPercentage;
	}

}
