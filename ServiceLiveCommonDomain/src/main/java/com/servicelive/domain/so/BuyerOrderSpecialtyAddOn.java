package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "specialty_add_on", uniqueConstraints = { @UniqueConstraint(columnNames = {"specialty_code", "stock_number" }) })
public class BuyerOrderSpecialtyAddOn implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "specialty_add_on_id", unique = true, nullable = false)
    private Integer specialtyAddOnId;

    @Column(name = "specialty_code", nullable = false, length = 10)
    private String specialtyCode;

    @Column(name = "specialty_description", length = 25)
    private String specialtyDescription;

    @Column(name = "major_heading_code")
    private Integer majorHeadingCode;

    @Column(name = "major_heading_description", length = 50)
    private String majorHeadingDescription;

    @Column(name = "sub_heading_code")
    private Integer subHeadingCode;

    @Column(name = "sub_heading_description", length = 50)
    private String subHeadingDescription;

    @Column(name = "classification_code", length = 10)
    private String classificationCode;

    @Column(name = "classification_description", length = 25)
    private String classificationDescription;

    @Column(name = "coverage", length = 2)
    private String coverage;

    @Column(name = "coverage_description", length = 25)
    private String coverageDescription;

    @Column(name = "job_code_suffix", length = 1)
    private String jobCodeSuffix;

    @Column(name = "stock_number", length = 5)
    private String stockNumber;

    @Column(name = "job_code_descripton", length = 25)
    private String jobCodeDescripton;

    @Column(name = "long_description", length = 100)
    private String longDescription;

    @Column(name = "inclusion_description", length = 65535)
    private String inclusionDescription;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "contractor_cost_type", length = 10)
    private String contractorCostType;

    @Column(name = "contractor_cost_type_description", length = 25)
    private String contractorCostTypeDescription;

    @Column(name = "dispatch_days_out")
    private Integer dispatchDaysOut;

    @Column(name = "mark_up_percentage", precision = 9, scale = 4)
    private Double markUpPercentage;

    @Temporal(TemporalType.DATE)
    @Column(name = "active_date", length = 10)
    private Date activeDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_date", length = 10)
    private Date updateDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "deactive_date", length = 10)
    private Date deactiveDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date", length = 19)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "modified_date", length = 19)
    private Date modifiedDate;

    @Column(name = "modified_by", length = 30)
    private String modifiedBy;

    public Integer getSpecialtyAddOnId() {
        return specialtyAddOnId;
    }

    public void setSpecialtyAddOnId(Integer specialtyAddOnId) {
        this.specialtyAddOnId = specialtyAddOnId;
    }

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public String getSpecialtyDescription() {
        return specialtyDescription;
    }

    public void setSpecialtyDescription(String specialtyDescription) {
        this.specialtyDescription = specialtyDescription;
    }

    public Integer getMajorHeadingCode() {
        return majorHeadingCode;
    }

    public void setMajorHeadingCode(Integer majorHeadingCode) {
        this.majorHeadingCode = majorHeadingCode;
    }

    public String getMajorHeadingDescription() {
        return majorHeadingDescription;
    }

    public void setMajorHeadingDescription(String majorHeadingDescription) {
        this.majorHeadingDescription = majorHeadingDescription;
    }

    public Integer getSubHeadingCode() {
        return subHeadingCode;
    }

    public void setSubHeadingCode(Integer subHeadingCode) {
        this.subHeadingCode = subHeadingCode;
    }

    public String getSubHeadingDescription() {
        return subHeadingDescription;
    }

    public void setSubHeadingDescription(String subHeadingDescription) {
        this.subHeadingDescription = subHeadingDescription;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    public void setClassificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
    }

    public String getClassificationDescription() {
        return classificationDescription;
    }

    public void setClassificationDescription(String classificationDescription) {
        this.classificationDescription = classificationDescription;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getCoverageDescription() {
        return coverageDescription;
    }

    public void setCoverageDescription(String coverageDescription) {
        this.coverageDescription = coverageDescription;
    }

    public String getJobCodeSuffix() {
        return jobCodeSuffix;
    }

    public void setJobCodeSuffix(String jobCodeSuffix) {
        this.jobCodeSuffix = jobCodeSuffix;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getJobCodeDescripton() {
        return jobCodeDescripton;
    }

    public void setJobCodeDescripton(String jobCodeDescripton) {
        this.jobCodeDescripton = jobCodeDescripton;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getInclusionDescription() {
        return inclusionDescription;
    }

    public void setInclusionDescription(String inclusionDescription) {
        this.inclusionDescription = inclusionDescription;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getContractorCostType() {
        return contractorCostType;
    }

    public void setContractorCostType(String contractorCostType) {
        this.contractorCostType = contractorCostType;
    }

    public String getContractorCostTypeDescription() {
        return contractorCostTypeDescription;
    }

    public void setContractorCostTypeDescription(String contractorCostTypeDescription) {
        this.contractorCostTypeDescription = contractorCostTypeDescription;
    }

    public Integer getDispatchDaysOut() {
        return dispatchDaysOut;
    }

    public void setDispatchDaysOut(Integer dispatchDaysOut) {
        this.dispatchDaysOut = dispatchDaysOut;
    }

    public Double getMarkUpPercentage() {
        return markUpPercentage;
    }

    public void setMarkUpPercentage(Double markUpPercentage) {
        this.markUpPercentage = markUpPercentage;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeactiveDate() {
        return deactiveDate;
    }

    public void setDeactiveDate(Date deactiveDate) {
        this.deactiveDate = deactiveDate;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
