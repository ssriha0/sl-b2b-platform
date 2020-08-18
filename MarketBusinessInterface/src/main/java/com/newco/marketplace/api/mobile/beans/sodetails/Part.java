package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */

@XStreamAlias("part")
@XmlAccessorType(XmlAccessType.FIELD)
public class Part {
	
	@XStreamAlias("partId")
	private Integer partId; 
	
	@XStreamAlias("partName")
	private String partName;
	
	@XStreamAlias("manufacturer")
	private String manufacturer;
	
	@XStreamAlias("modelNumber")
	private String modelNumber;
	
	@XStreamAlias("serialNumber")
	private String serialNumber;
	
	@XStreamAlias("oemNumber")
	private String oemNumber;
	
	@XStreamAlias("orderNumber")
	private String orderNumber;	
	
	@XStreamAlias("purchaseOrderNumber")
	private String purchaseOrderNumber;
	
	@XStreamAlias("size")
	private String size;
	
	@XStreamAlias("weight")
	private String weight;
	
	@XStreamAlias("vendorPartNumber")
	private String vendorPartNumber;
	
	@XStreamAlias("partType")
	private String partType;
	
	@XStreamAlias("qty")
	private String qty;
	
	@XStreamAlias("partStatus")
	private String partStatus;
	
	@XStreamAlias("partDescription")
	private String partDescription;
	
	@XStreamAlias("additionalPartInfo")
	private String additionalPartInfo;
	
	@XStreamAlias("shippingCarrier")
	private String shippingCarrier;
	
	@XStreamAlias("shippingTrackingNumber")
	private String shippingTrackingNumber;
	
	@XStreamAlias("shipDate")
	private String shipDate;
	
	@XStreamAlias("coreReturnCarrier")
	private String coreReturnCarrier ;
	
	@XStreamAlias("coreReturnTrackingNumber")
	private String coreReturnTrackingNumber ; 
	
	@XStreamAlias("coreReturnDate")
	private String coreReturnDate;	

	@XStreamAlias("pickupLocationAvailability")
	private String pickupLocationAvailability;
	
	@XStreamAlias("pickupLocation")
	private PickUpLocation pickupLocation;
	
	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	/**
	 * @return the partId
	 */
	public Integer getPartId() {
		return partId;
	}

	/**
	 * @param partId the partId to set
	 */
	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOemNumber() {
		return oemNumber;
	}

	public void setOemNumber(String oemNumber) {
		this.oemNumber = oemNumber;
	}

	public String getWeight() {
		return weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	/**
	 * @param purchaseOrderNumber the purchaseOrderNumber to set
	 */
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	/**
	 * @return the vendorPartNumber
	 */
	public String getVendorPartNumber() {
		return vendorPartNumber;
	}

	/**
	 * @param vendorPartNumber the vendorPartNumber to set
	 */
	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}

	public String getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getAdditionalPartInfo() {
		return additionalPartInfo;
	}

	public void setAdditionalPartInfo(String additionalPartInfo) {
		this.additionalPartInfo = additionalPartInfo;
	}

	public String getShippingCarrier() {
		return shippingCarrier;
	}

	public void setShippingCarrier(String shippingCarrier) {
		this.shippingCarrier = shippingCarrier;
	}

	public String getShippingTrackingNumber() {
		return shippingTrackingNumber;
	}

	public void setShippingTrackingNumber(String shippingTrackingNumber) {
		this.shippingTrackingNumber = shippingTrackingNumber;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public String getCoreReturnCarrier() {
		return coreReturnCarrier;
	}

	public void setCoreReturnCarrier(String coreReturnCarrier) {
		this.coreReturnCarrier = coreReturnCarrier;
	}

	public String getCoreReturnTrackingNumber() {
		return coreReturnTrackingNumber;
	}

	public void setCoreReturnTrackingNumber(String coreReturnTrackingNumber) {
		this.coreReturnTrackingNumber = coreReturnTrackingNumber;
	}

	public String getCoreReturnDate() {
		return coreReturnDate;
	}

	public void setCoreReturnDate(String coreReturnDate) {
		this.coreReturnDate = coreReturnDate;
	}

	public PickUpLocation getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(PickUpLocation pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPickupLocationAvailability() {
		return pickupLocationAvailability;
	}

	public void setPickupLocationAvailability(String pickupLocationAvailability) {
		this.pickupLocationAvailability = pickupLocationAvailability;
	}

}
