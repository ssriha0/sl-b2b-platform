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
@Table ( name = "hsr_order_schedule")
public class HSRStageOrderSchedule extends LoggableBaseDomain {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3081810702565896926L;

	/*
	  *  `order_taken_time` VARCHAR(8) NOT NULL ,
  `order_taken_date` VARCHAR(10) NOT NULL ,
  `promised_date` VARCHAR(10) NULL ,
  `promised_time_from` VARCHAR(8) NULL ,
  `promised_time_to` VARCHAR(8) NULL ,
  `original_sch_date` VARCHAR(10) NULL ,
  `original_time_from` VARCHAR(8) NULL ,
  `original_time_to` VARCHAR(8) NULL ,
 
	  */

	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_order_sch_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderScheduleId;

	@Column (name = "order_taken_time", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private String orderTakenTime;

	@Column (name = "order_taken_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String orderTakenDate;
	
	@Column (name = "promised_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String promisedDate;

	@Column (name = "promised_time_from", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private String promisedTimeFrom;
	
	@Column (name = "promised_time_to", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private String promisedTimeTo;
	
	@Column (name = "original_sch_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String originalScheduleDate;
	
	@Column (name = "original_time_from", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private String originalScheduleTimeFrom;
	@Column (name = "original_time_to", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private String originalScheduleTimeTo;
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.ALL} , optional = false, targetEntity = HSRStageOrder.class)
    @PrimaryKeyJoinColumn(name="hsr_order_id")
    @JoinColumn(name="hsr_order_id", insertable=true, updatable=false)
    @NotNull
 	private HSRStageOrder hsrOrderId;


	/**
	 * @return the hsrOrderScheduleId
	 */
	public Integer getHsrOrderScheduleId() {
		return hsrOrderScheduleId;
	}


	/**
	 * @param hsrOrderScheduleId the hsrOrderScheduleId to set
	 */
	public void setHsrOrderScheduleId(Integer hsrOrderScheduleId) {
		this.hsrOrderScheduleId = hsrOrderScheduleId;
	}


	/**
	 * @return the orderTakenTime
	 */
	public String getOrderTakenTime() {
		return orderTakenTime;
	}


	/**
	 * @param orderTakenTime the orderTakenTime to set
	 */
	public void setOrderTakenTime(String orderTakenTime) {
		this.orderTakenTime = orderTakenTime;
	}


	/**
	 * @return the orderTakenDate
	 */
	public String getOrderTakenDate() {
		return orderTakenDate;
	}


	/**
	 * @param orderTakenDate the orderTakenDate to set
	 */
	public void setOrderTakenDate(String orderTakenDate) {
		this.orderTakenDate = orderTakenDate;
	}


	/**
	 * @return the promisedDate
	 */
	public String getPromisedDate() {
		return promisedDate;
	}


	/**
	 * @param promisedDate the promisedDate to set
	 */
	public void setPromisedDate(String promisedDate) {
		this.promisedDate = promisedDate;
	}


	/**
	 * @return the promisedTimeFrom
	 */
	public String getPromisedTimeFrom() {
		return promisedTimeFrom;
	}


	/**
	 * @param promisedTimeFrom the promisedTimeFrom to set
	 */
	public void setPromisedTimeFrom(String promisedTimeFrom) {
		this.promisedTimeFrom = promisedTimeFrom;
	}


	/**
	 * @return the promisedTimeTo
	 */
	public String getPromisedTimeTo() {
		return promisedTimeTo;
	}


	/**
	 * @param promisedTimeTo the promisedTimeTo to set
	 */
	public void setPromisedTimeTo(String promisedTimeTo) {
		this.promisedTimeTo = promisedTimeTo;
	}


	/**
	 * @return the originalScheduleDate
	 */
	public String getOriginalScheduleDate() {
		return originalScheduleDate;
	}


	/**
	 * @param originalScheduleDate the originalScheduleDate to set
	 */
	public void setOriginalScheduleDate(String originalScheduleDate) {
		this.originalScheduleDate = originalScheduleDate;
	}


	/**
	 * @return the originalScheduleTimeFrom
	 */
	public String getOriginalScheduleTimeFrom() {
		return originalScheduleTimeFrom;
	}


	/**
	 * @param originalScheduleTimeFrom the originalScheduleTimeFrom to set
	 */
	public void setOriginalScheduleTimeFrom(String originalScheduleTimeFrom) {
		this.originalScheduleTimeFrom = originalScheduleTimeFrom;
	}


	/**
	 * @return the originalScheduleTimeTo
	 */
	public String getOriginalScheduleTimeTo() {
		return originalScheduleTimeTo;
	}


	/**
	 * @param originalScheduleTimeTo the originalScheduleTimeTo to set
	 */
	public void setOriginalScheduleTimeTo(String originalScheduleTimeTo) {
		this.originalScheduleTimeTo = originalScheduleTimeTo;
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
