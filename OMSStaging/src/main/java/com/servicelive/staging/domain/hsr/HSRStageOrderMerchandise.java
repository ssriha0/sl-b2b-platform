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
@Table ( name = "hsr_order_merchandise")
public class HSRStageOrderMerchandise extends LoggableBaseDomain {
  /**
	 * 
	 */
	private static final long serialVersionUID = 2970514208400559192L;

/*
   * `merch_code` VARCHAR(12) NOT NULL ,
  `merch_description` VARCHAR(50) NOT NULL ,
  `purchase_date` VARCHAR(10) NULL ,
  `brand` VARCHAR(12) NULL ,
  `model` VARCHAR(24) NULL ,
  `serial_no` VARCHAR(20) NULL ,
  `sears_sold_code` VARCHAR(1) NULL ,
  `system_item_suffix` VARCHAR(7) NULL ,
  `item_no` VARCHAR(5) NULL ,
  `sears_store_no` VARCHAR(45) NULL ,
  `original_delivery_date` DATETIME NULL ,
  `type_of_usage` VARCHAR(1) NULL ,
   */

	@Id @GeneratedValue (strategy = IDENTITY)
	@Column (name = "hsr_merch_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer hsrOrderMerchCodeId;

	@Column (name = "merch_code", unique = false, nullable = false, insertable = true, updatable = true, length = 12)
	private String merchandiseCode;

	@Column (name = "merch_description", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String merchandiseDescription;
	
	@Column (name = "purchase_date", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	private String purchaseDate;

	@Column (name = "brand", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	private String brand;
	
	@Column (name = "model", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
	private String model;
	
	@Column (name = "serial_no", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private String serialNumber;
	
	@Column (name = "sears_sold_code", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String searsSoldCode;
	@Column (name = "system_item_suffix", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	private String systemItemSuffix;
	@Column (name = "item_no", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	private String itemNumber;
	
	@Column (name = "sears_store_no", unique = false, nullable = true, insertable = true, updatable = true, length = 45)
	private String searsStoreNumber;
	
	@Column (name = "original_delivery_date", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String originalDeliveryDate;
	
	@Column (name = "type_of_usage", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private String typeOfUsage;
	
	@Column (name = "division", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	private String division;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.ALL} , optional = false, targetEntity = HSRStageOrder.class)
    @PrimaryKeyJoinColumn(name="hsr_order_id")
    @JoinColumn(name="hsr_order_id", insertable=true, updatable=false)
    @NotNull
 	private HSRStageOrder hsrOrderId;
	/**
	 * @return the hsrOrderMerchCodeId
	 */
	public Integer getHsrOrderMerchCodeId() {
		return hsrOrderMerchCodeId;
	}
	/**
	 * @param hsrOrderMerchCodeId the hsrOrderMerchCodeId to set
	 */
	public void setHsrOrderMerchCodeId(Integer hsrOrderMerchCodeId) {
		this.hsrOrderMerchCodeId = hsrOrderMerchCodeId;
	}
	/**
	 * @return the merchandiseCode
	 */
	public String getMerchandiseCode() {
		return merchandiseCode;
	}
	/**
	 * @param merchandiseCode the merchandiseCode to set
	 */
	public void setMerchandiseCode(String merchandiseCode) {
		this.merchandiseCode = merchandiseCode;
	}
	/**
	 * @return the merchandiseDescription
	 */
	public String getMerchandiseDescription() {
		return merchandiseDescription;
	}
	/**
	 * @param merchandiseDescription the merchandiseDescription to set
	 */
	public void setMerchandiseDescription(String merchandiseDescription) {
		this.merchandiseDescription = merchandiseDescription;
	}
	/**
	 * @return the purchaseDate
	 */
	public String getPurchaseDate() {
		return purchaseDate;
	}
	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the searsSoldCode
	 */
	public String getSearsSoldCode() {
		return searsSoldCode;
	}
	/**
	 * @param searsSoldCode the searsSoldCode to set
	 */
	public void setSearsSoldCode(String searsSoldCode) {
		this.searsSoldCode = searsSoldCode;
	}
	/**
	 * @return the systemItemSuffix
	 */
	public String getSystemItemSuffix() {
		return systemItemSuffix;
	}
	/**
	 * @param systemItemSuffix the systemItemSuffix to set
	 */
	public void setSystemItemSuffix(String systemItemSuffix) {
		this.systemItemSuffix = systemItemSuffix;
	}
	/**
	 * @return the itemNumber
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return the searsStoreNumber
	 */
	public String getSearsStoreNumber() {
		return searsStoreNumber;
	}
	/**
	 * @param searsStoreNumber the searsStoreNumber to set
	 */
	public void setSearsStoreNumber(String searsStoreNumber) {
		this.searsStoreNumber = searsStoreNumber;
	}
	/**
	 * @return the originalDeliveryDate
	 */
	public String getOriginalDeliveryDate() {
		return originalDeliveryDate;
	}
	/**
	 * @param originalDeliveryDate the originalDeliveryDate to set
	 */
	public void setOriginalDeliveryDate(String originalDeliveryDate) {
		this.originalDeliveryDate = originalDeliveryDate;
	}
	/**
	 * @return the typeOfUsage
	 */
	public String getTypeOfUsage() {
		return typeOfUsage;
	}
	/**
	 * @param typeOfUsage the typeOfUsage to set
	 */
	public void setTypeOfUsage(String typeOfUsage) {
		this.typeOfUsage = typeOfUsage;
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
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}
	/**
	 * @param division the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	
}
