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
@Table ( name = "hsr_order_repair_location")
public class HSRStageOrderRepairLocation extends LoggableBaseDomain {
  /**
	 * 
	 */
	private static final long serialVersionUID = -4526935180366348359L;

/*
   * `service_loc_type_ind` VARCHAR(1) NOT NULL ,
  `repair_street1` VARCHAR(50) NULL ,
  `repair_street2` VARCHAR(25) NULL ,
  `city` VARCHAR(20) NULL ,
  `state_code` VARCHAR(2) NULL ,
  `postal_code` VARCHAR(9) NULL ,
  `service_location_notes` VARCHAR(30) NULL ,
   */
	
	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_repair_location_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderRepairLocId;

	@Column (name = "service_loc_type_ind", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String serviceLocnTypeInd;

	@Column (name = "repair_street1", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	private String repairStreet1;
	
	@Column (name = "repair_street2", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	private String repairStreet2;

	@Column (name = "city", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private String city;
	
	@Column (name = "state_code", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	private String stateCode;
	
	@Column (name = "postal_code", unique = false, nullable = true, insertable = true, updatable = true, length = 9)
	private String postalCode;
	
	@Column (name = "service_location_notes", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	private String serviceLocnNotes;

	@Column (name = "service_location_code", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	private String serviceLocnCode;
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.ALL} , optional = false, targetEntity = HSRStageOrder.class)
    @PrimaryKeyJoinColumn(name="hsr_order_id")
    @JoinColumn(name="hsr_order_id", insertable=true, updatable=false)
    @NotNull
 	private HSRStageOrder hsrOrderId;



	/**
	 * @return the hsrOrderRepairLocId
	 */
	public Integer getHsrOrderRepairLocId() {
		return hsrOrderRepairLocId;
	}



	/**
	 * @param hsrOrderRepairLocId the hsrOrderRepairLocId to set
	 */
	public void setHsrOrderRepairLocId(Integer hsrOrderRepairLocId) {
		this.hsrOrderRepairLocId = hsrOrderRepairLocId;
	}



	/**
	 * @return the serviceLocnTypeInd
	 */
	public String getServiceLocnTypeInd() {
		return serviceLocnTypeInd;
	}



	/**
	 * @param serviceLocnTypeInd the serviceLocnTypeInd to set
	 */
	public void setServiceLocnTypeInd(String serviceLocnTypeInd) {
		this.serviceLocnTypeInd = serviceLocnTypeInd;
	}



	/**
	 * @return the repairStreet1
	 */
	public String getRepairStreet1() {
		return repairStreet1;
	}



	/**
	 * @param repairStreet1 the repairStreet1 to set
	 */
	public void setRepairStreet1(String repairStreet1) {
		this.repairStreet1 = repairStreet1;
	}



	/**
	 * @return the repairStreet2
	 */
	public String getRepairStreet2() {
		return repairStreet2;
	}



	/**
	 * @param repairStreet2 the repairStreet2 to set
	 */
	public void setRepairStreet2(String repairStreet2) {
		this.repairStreet2 = repairStreet2;
	}



	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}



	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}



	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}



	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}



	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}



	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}



	/**
	 * @return the serviceLocnNotes
	 */
	public String getServiceLocnNotes() {
		return serviceLocnNotes;
	}



	/**
	 * @param serviceLocnNotes the serviceLocnNotes to set
	 */
	public void setServiceLocnNotes(String serviceLocnNotes) {
		this.serviceLocnNotes = serviceLocnNotes;
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
	 * @return the serviceLocnCode
	 */
	public String getServiceLocnCode() {
		return serviceLocnCode;
	}



	/**
	 * @param serviceLocnCode the serviceLocnCode to set
	 */
	public void setServiceLocnCode(String serviceLocnCode) {
		this.serviceLocnCode = serviceLocnCode;
	}

}
