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
@Table ( name = "hsr_order_update_part")
public class HSRStageOrderUpdatePart extends LoggableBaseDomain {
	/*
	 *  `hsr_order_update_id` INT(11) NOT NULL ,
     `div_no` VARCHAR(3) NULL ,
  `pls` VARCHAR(3) NULL ,
  `part_number` VARCHAR(24) NULL,
  `qty` VARCHAR(24) NULL,
  `unit_price` VARCHAR(7) NULL,
  `coverage_type` VARCHAR(2) NULL,
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1639924794312972519L;

	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_order_update_parts_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderUpdatePartId;

	@Column (name = "div_no", unique = false, nullable = false, insertable = true, updatable = true, length = 3)
	private String updatedDivisionNO;

	@Column (name = "pls", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	private String pls;
	
	@Column (name = "part_number", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
	private String partNumber;

	@Column (name = "qty", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	private String quantity;
	
	@Column (name = "unit_price", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	private String unitPrice;
	
	@Column (name = "coverage_type", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private String coverageType;

	
	
	@ManyToOne
    @JoinColumn(name="hsr_order_update_id", insertable=false, updatable=false)
	private HSRStageOrderUpdate hsrOrderUpdateId;



	/**
	 * @return the hsrOrderUpdatePartId
	 */
	public Integer getHsrOrderUpdatePartId() {
		return hsrOrderUpdatePartId;
	}



	/**
	 * @param hsrOrderUpdatePartId the hsrOrderUpdatePartId to set
	 */
	public void setHsrOrderUpdatePartId(Integer hsrOrderUpdatePartId) {
		this.hsrOrderUpdatePartId = hsrOrderUpdatePartId;
	}



	/**
	 * @return the updatedDivisionNO
	 */
	public String getUpdatedDivisionNO() {
		return updatedDivisionNO;
	}



	/**
	 * @param updatedDivisionNO the updatedDivisionNO to set
	 */
	public void setUpdatedDivisionNO(String updatedDivisionNO) {
		this.updatedDivisionNO = updatedDivisionNO;
	}



	/**
	 * @return the pls
	 */
	public String getPls() {
		return pls;
	}



	/**
	 * @param pls the pls to set
	 */
	public void setPls(String pls) {
		this.pls = pls;
	}



	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}



	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}



	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}



	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	/**
	 * @return the unitPrice
	 */
	public String getUnitPrice() {
		return unitPrice;
	}



	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}



	/**
	 * @return the coverageType
	 */
	public String getCoverageType() {
		return coverageType;
	}



	/**
	 * @param coverageType the coverageType to set
	 */
	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}



	/**
	 * @return the hsrOrderUpdateId
	 */
	public HSRStageOrderUpdate getHsrOrderUpdateId() {
		return hsrOrderUpdateId;
	}



	/**
	 * @param hsrOrderUpdateId the hsrOrderUpdateId to set
	 */
	public void setHsrOrderUpdateId(HSRStageOrderUpdate hsrOrderUpdateId) {
		this.hsrOrderUpdateId = hsrOrderUpdateId;
	}
}
