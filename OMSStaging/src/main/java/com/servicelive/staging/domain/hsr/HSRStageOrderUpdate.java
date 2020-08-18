/**
 * 
 */
package com.servicelive.staging.domain.hsr;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.servicelive.staging.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "hsr_order_update")
public class HSRStageOrderUpdate extends LoggableBaseDomain {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
   *  `update_modified_date` VARCHAR(10)  NULL ,
  `update_modified_time` VARCHAR(8) NULL ,
  `emp_id_number` VARCHAR(7) NULL ,
  `filler` VARCHAR(4) NULL ,
  `modifying_unit_id` VARCHAR(7) NULL ,
   */
	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_order_update_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderUpdateId;

	@Column (name = "update_modified_date", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	private String updatedModifiedDate;

	@Column (name = "update_modified_time", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private String updatedModifiedTime;
	
	@Column (name = "emp_id_number", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	private String empIdNumber;

	@Column (name = "filler", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	private String filler;
	
	@Column (name = "modifying_unit_id", unique = false, nullable = true, insertable = true, updatable = true, length =7)
	private String modifyingUnitId;
	
	
	@ManyToOne
    @JoinColumn(name="hsr_order_id", insertable=false, updatable=false)
	private HSRStageOrder hsrOrderId;
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "hsr_order_update_id",nullable = false, insertable = true, updatable = true)
	private List<HSRStageOrderUpdatePart> parts = new ArrayList<HSRStageOrderUpdatePart> (0);


	/**
	 * @return the hsrOrderUpdateId
	 */
	public Integer getHsrOrderUpdateId() {
		return hsrOrderUpdateId;
	}


	/**
	 * @param hsrOrderUpdateId the hsrOrderUpdateId to set
	 */
	public void setHsrOrderUpdateId(Integer hsrOrderUpdateId) {
		this.hsrOrderUpdateId = hsrOrderUpdateId;
	}


	/**
	 * @return the updatedModifiedDate
	 */
	public String getUpdatedModifiedDate() {
		return updatedModifiedDate;
	}


	/**
	 * @param updatedModifiedDate the updatedModifiedDate to set
	 */
	public void setUpdatedModifiedDate(String updatedModifiedDate) {
		this.updatedModifiedDate = updatedModifiedDate;
	}


	/**
	 * @return the updatedModifiedTime
	 */
	public String getUpdatedModifiedTime() {
		return updatedModifiedTime;
	}


	/**
	 * @param updatedModifiedTime the updatedModifiedTime to set
	 */
	public void setUpdatedModifiedTime(String updatedModifiedTime) {
		this.updatedModifiedTime = updatedModifiedTime;
	}


	/**
	 * @return the empIdNumber
	 */
	public String getEmpIdNumber() {
		return empIdNumber;
	}


	/**
	 * @param empIdNumber the empIdNumber to set
	 */
	public void setEmpIdNumber(String empIdNumber) {
		this.empIdNumber = empIdNumber;
	}


	/**
	 * @return the filler
	 */
	public String getFiller() {
		return filler;
	}


	/**
	 * @param filler the filler to set
	 */
	public void setFiller(String filler) {
		this.filler = filler;
	}


	/**
	 * @return the modifyingUnitId
	 */
	public String getModifyingUnitId() {
		return modifyingUnitId;
	}


	/**
	 * @param modifyingUnitId the modifyingUnitId to set
	 */
	public void setModifyingUnitId(String modifyingUnitId) {
		this.modifyingUnitId = modifyingUnitId;
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


	/**
	 * @return the parts
	 */
	public List<HSRStageOrderUpdatePart> getParts() {
		return parts;
	}


	/**
	 * @param parts the parts to set
	 */
	public void setParts(List<HSRStageOrderUpdatePart> parts) {
		this.parts = parts;
	}
}
