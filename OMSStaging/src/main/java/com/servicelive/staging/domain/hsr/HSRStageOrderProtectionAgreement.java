/**
 * 
 */
package com.servicelive.staging.domain.hsr;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.servicelive.staging.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "hsr_order_protect_agreement")
public class HSRStageOrderProtectionAgreement extends LoggableBaseDomain {
  /**
	 * 
	 */
	private static final long serialVersionUID = 7183749982172978415L;

/*
   *  `hsr_order_id` INT(11) NOT NULL ,
  `pa_type` VARCHAR(3) NULL ,
  `pa_no` VARCHAR(15) NULL ,
  `pa_latest_exp_date` VARCHAR(10) NULL ,
  `pa_plan_type` VARCHAR(6) NULL ,
   */
	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_order_pa_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderProtectionAgreementId;

	@Column (name = "pa_type", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	private String paType;

	@Column (name = "pa_no", unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	private String paNumber;
	
	@Column (name = "pa_latest_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String paLtestExpDate;

	@Column (name = "pa_plan_type", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	private String paPlanType;
	
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.ALL} , optional = false, targetEntity = HSRStageOrder.class)
    @PrimaryKeyJoinColumn(name="hsr_order_id")
    @JoinColumn(name="hsr_order_id", insertable=true, updatable=false)
    @NotNull
 	private HSRStageOrder hsrOrderId;



	/**
	 * @return the hsrOrderProtectionAgreementId
	 */
	public Integer getHsrOrderProtectionAgreementId() {
		return hsrOrderProtectionAgreementId;
	}



	/**
	 * @param hsrOrderProtectionAgreementId the hsrOrderProtectionAgreementId to set
	 */
	public void setHsrOrderProtectionAgreementId(
			Integer hsrOrderProtectionAgreementId) {
		this.hsrOrderProtectionAgreementId = hsrOrderProtectionAgreementId;
	}



	/**
	 * @return the paType
	 */
	public String getPaType() {
		return paType;
	}



	/**
	 * @param paType the paType to set
	 */
	public void setPaType(String paType) {
		this.paType = paType;
	}



	/**
	 * @return the paNumber
	 */
	public String getPaNumber() {
		return paNumber;
	}



	/**
	 * @param paNumber the paNumber to set
	 */
	public void setPaNumber(String paNumber) {
		this.paNumber = paNumber;
	}



	/**
	 * @return the paLtestExpDate
	 */
	public String getPaLtestExpDate() {
		return paLtestExpDate;
	}



	/**
	 * @param paLtestExpDate the paLtestExpDate to set
	 */
	public void setPaLtestExpDate(String paLtestExpDate) {
		this.paLtestExpDate = paLtestExpDate;
	}



	/**
	 * @return the paPlanType
	 */
	public String getPaPlanType() {
		return paPlanType;
	}



	/**
	 * @param paPlanType the paPlanType to set
	 */
	public void setPaPlanType(String paPlanType) {
		this.paPlanType = paPlanType;
	}



	/**
	 * @return the hsrOrderId
	 */
	public HSRStageOrder getHsrOrderId() {
		return hsrOrderId;
	}



	/**
	 * @param hsrOrderId the hsrOrderId to set
	 */
	public void setHsrOrderId(HSRStageOrder hsrOrderId) {
		this.hsrOrderId = hsrOrderId;
	}
}
