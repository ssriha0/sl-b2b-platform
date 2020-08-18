package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SpecialtyAddOn entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "specialty_add_on", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"specialty_code", "stock_number" }) })
public class SpecialtyAddOn extends AbstractSpecialtyAddOn implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public SpecialtyAddOn() {
		// intentionally blank
	}

	/** minimal constructor */
	public SpecialtyAddOn(Integer specialtyAddOnId, String specialtyCode,
			String stockNumber) {
		super(specialtyAddOnId, specialtyCode, stockNumber);
	}

	/** full constructor */
	public SpecialtyAddOn(Integer specialtyAddOnId, String specialtyCode,
			String specialtyDescription, Integer majorHeadingCode,
			String majorHeadingDescription, Integer subHeadingCode,
			String subHeadingDescription, String classificationCode,
			String classificationDescription, String coverage,
			String coverageDescription, String jobCodeSuffix,
			String stockNumber, String jobCodeDescripton,
			String longDescription, String inclusionDescription,
			Integer sequence, String contractorCostType,
			String contractorCostTypeDescription, Integer dispatchDaysOut,
			Date activeDate, Date updateDate, Date deactiveDate,
			Date createdDate, Date modifiedDate, String modifiedBy, Double markUpPercentage) {
		super(specialtyAddOnId, specialtyCode, specialtyDescription,
				majorHeadingCode, majorHeadingDescription, subHeadingCode,
				subHeadingDescription, classificationCode,
				classificationDescription, coverage, coverageDescription,
				jobCodeSuffix, stockNumber, jobCodeDescripton, longDescription,
				inclusionDescription, sequence, contractorCostType,
				contractorCostTypeDescription, dispatchDaysOut, activeDate,
				updateDate, deactiveDate, createdDate, modifiedDate, modifiedBy,markUpPercentage);
	}

}
