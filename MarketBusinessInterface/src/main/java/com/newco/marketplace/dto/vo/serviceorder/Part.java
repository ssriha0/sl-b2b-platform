package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class Part extends CommonVO implements Comparable<Part> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1373806363093793981L;
	

	private String manufacturer;
	private String modelNumber;
	private String serialNumber;
	private String manufacturerPartNumber;
	private String vendorPartNumber;
	private String quantity = "1";
	private String partDesc;
	private Integer partId;
	private String additionalPartInfo;
	private String orderNumber;
	private String purchaseOrderNumber;
	private Integer partStatusId;
	private Integer measurementStandard;
	private String length;
	private String width;
	private String height;
	private String weight;
	
	private String soId;  
	private Integer taskId;
	private String referencePartId;
	private String partDs;
	
	private Carrier shippingCarrier;
	private Carrier returnCarrier;
	private Carrier coreReturnCarrier;
	private Timestamp shipDate;
	private Timestamp returnTrackDate;
	private String shipCarrierOther;
	private String returnCarrierOther;
	private String coreReturnCarrierOther;
	private SoLocation pickupLocation;
	private Contact pickupContact;
	private Contact altPickupContact;
	private Boolean providerBringPartInd;
	private Boolean providerBringparts;
	private Boolean hasPartsAtDifferntLocation = false;
	public String getAltPartRef1() {
		return altPartRef1;
	}

	public void setAltPartRef1(String altPartRef1) {
		this.altPartRef1 = altPartRef1;
	}

	public String getAltPartRef2() {
		return altPartRef2;
	}

	public void setAltPartRef2(String altPartRef2) {
		this.altPartRef2 = altPartRef2;
	}

	private String altPartRef1;
	private String altPartRef2;
	private String productLine;
	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPartDs() {
		return partDs;
	}
	public void setPartDs(String partDs) {
		this.partDs = partDs;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	
	
	
	public String getShipCarrierOther() {
		return shipCarrierOther;
	}
	public void setShipCarrierOther(String shipCarrierOther) {
		this.shipCarrierOther = shipCarrierOther;
	}
	public String getReturnCarrierOther() {
		return returnCarrierOther;
	}
	public void setReturnCarrierOther(String returnCarrierOther) {
		this.returnCarrierOther = returnCarrierOther;
	}

	/**
	 * @return the coreReturnCarrierOther
	 */
	public String getCoreReturnCarrierOther() {
		return coreReturnCarrierOther;
	}

	/**
	 * @param coreReturnCarrierOther the coreReturnCarrierOther to set
	 */
	public void setCoreReturnCarrierOther(String coreReturnCarrierOther) {
		this.coreReturnCarrierOther = coreReturnCarrierOther;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public Integer getMeasurementStandard() {
		return measurementStandard;
	}
	public void setMeasurementStandard(Integer measurementStandard) {
		this.measurementStandard = measurementStandard;
	}
	
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getQuantity() {
		if (quantity == null) {
			quantity = "1";
		}
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Carrier getShippingCarrier() {
		return shippingCarrier;
	}
	public void setShippingCarrier(Carrier shippingCarrier) {
		this.shippingCarrier = shippingCarrier;
	}
	public Carrier getReturnCarrier() {
		return returnCarrier;
	}
	public void setReturnCarrier(Carrier returnCarrier) {
		this.returnCarrier = returnCarrier;
	}
	/**
	 * @return the coreReturnCarrier
	 */
	public Carrier getCoreReturnCarrier() {
		return coreReturnCarrier;
	}

	/**
	 * @param coreReturnCarrier the coreReturnCarrier to set
	 */
	public void setCoreReturnCarrier(Carrier coreReturnCarrier) {
		this.coreReturnCarrier = coreReturnCarrier;
	}
	public SoLocation getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(SoLocation pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public Boolean isProviderBringPartInd() {
		return providerBringPartInd;
	}
	public void setProviderBringPartInd(Boolean providerBringPartInd) {
		this.providerBringPartInd = providerBringPartInd;
	}
	public Contact getPickupContact() {
		return pickupContact;
	}
	public void setPickupContact(Contact pickupContact) {
		this.pickupContact = pickupContact;
	}
	public Contact getAltPickupContact() {
		return altPickupContact;
	}
	public void setAltPickupContact(Contact altPickupContact) {
		this.altPickupContact = altPickupContact;
	}
	public String getManufacturerPartNumber() {
		return manufacturerPartNumber;
	}
	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		this.manufacturerPartNumber = manufacturerPartNumber;
	}
	public String getVendorPartNumber() {
		return vendorPartNumber;
	}
	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public String getAdditionalPartInfo() {
		return additionalPartInfo;
	}
	public void setAdditionalPartInfo(String additionalPartInfo) {
		this.additionalPartInfo = additionalPartInfo;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public Integer getPartStatusId() {
		return partStatusId;
	}
	public void setPartStatusId(Integer partStatusId) {
		this.partStatusId = partStatusId;
	}
	public Timestamp getReturnTrackDate() {
		return returnTrackDate;
	}
	public void setReturnTrackDate(Timestamp returnTrackDate) {
		this.returnTrackDate = returnTrackDate;
	}
	public String getReferencePartId() {
		return referencePartId;
	}
	public void setReferencePartId(String referencePartId) {
		this.referencePartId = referencePartId;
	}
	public Timestamp getShipDate() {
		return shipDate;
	}
	public void setShipDate(Timestamp shipDate) {
		this.shipDate = shipDate;
	}
	public Part() {
	    super();
	}
	
    public Part(String manufacturer, String modelNumber, String length,
            String width, String height, String weight) {

        super();
        this.manufacturer = manufacturer;
        this.modelNumber = modelNumber;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }
    	
    @Override
	public String toString() {
		return ""; /*new ToStringBuilder(this)
			.append("partId", getPartId())
			.append("soId", getSoId())
			.append("taskId", getTaskId())
			.append("partDs", getPartDs())
			.append("shipCarrierOther", getShipCarrierOther())
			.toString();*/
    }
	public int compareTo(Part o) {
		if (!o.getComparableString().equals(this.getComparableString())) {
			return 1;
		}
		return 0;
	}
		
	/**
	 * Change this without knowing what you are doing and you will be placed in a small room with Lacy
	 * and forced to tell him what you did to screw up the whole application. (bring body armor)
	 * @return
	 */
	public String getComparableString() {
		StringBuilder sb = new StringBuilder();
		sb.append((getHeight() != null ? getHeight() : ""));
		sb.append((getLength() != null ? getLength() : ""));
		sb.append((getManufacturer() != null ? getManufacturer() : ""));
		sb.append((getModelNumber() != null ? getModelNumber() : ""));
		sb.append((getPartDs() != null ? getPartDs() : ""));
		sb.append((getPickupContact() != null ? getPickupContact().getComparableString() : ""));
		sb.append((getPickupLocation() != null ? getPickupLocation().getComparableString() : ""));
		sb.append((getQuantity() != null ? getQuantity() : "1"));
		sb.append((getReferencePartId() != null ? getReferencePartId() : ""));
		sb.append((getReturnCarrier() != null ? getReturnCarrier().getComparableString() : ""));
		sb.append((getReturnCarrierOther() != null ? getReturnCarrierOther() : ""));
		sb.append((getCoreReturnCarrier() != null ? getCoreReturnCarrier().getComparableString() : ""));
		sb.append((getCoreReturnCarrierOther() != null ? getCoreReturnCarrierOther() : ""));
		sb.append((getShipCarrierOther() != null? getShipCarrierOther() : ""));
		sb.append((getShipDate() != null ? getShipDate() : ""));
		sb.append((getSerialNumber() != null ? getSerialNumber() : ""));
		sb.append((getManufacturerPartNumber() != null ? getManufacturerPartNumber() : ""));
		sb.append((getVendorPartNumber() != null ? getVendorPartNumber() : ""));
		sb.append((getProductLine() != null ? getProductLine() : ""));
		sb.append((getReturnTrackDate() != null ? getReturnTrackDate() : ""));
		sb.append((getOrderNumber() != null ? getOrderNumber() : ""));
		sb.append((getPurchaseOrderNumber() != null ? getPurchaseOrderNumber() : ""));
		return sb.toString();
	}		
	
}
