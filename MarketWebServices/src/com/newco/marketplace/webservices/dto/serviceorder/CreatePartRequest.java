package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.xfire.aegis.type.java5.XmlElement;

import com.newco.marketplace.interfaces.OrderConstants;


public class CreatePartRequest extends ABaseWebserviceRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6352696170192372329L;
	private Integer partId;
	private String soId;
	private String manufacturer;
	private String modelNumber;
	private String partDesc;
	private String standard = OrderConstants.ENGLISH;  // measurement
	private String length;
	private String height;
	private String weight;
	private String width;
	private String quantity;
	private Integer shippingCarrierId;
	private String shippingTrackingNo;
	private String otherShippingCarrier;
	private Integer returnCarrierId;
	private String returnTrackingNo;
	private String otherReturnCarrier;
	private String referencePartId;
	private String shipDate;
	private Boolean providerBringparts = false;
	private LocationRequest pickupLocation;
	private ContactRequest pickupContact;
	private Boolean hasPartsAtDifferntLocation = false;
	private Date createdDate;
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@XmlElement(minOccurs="1", nillable=false)
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
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
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
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
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Integer getShippingCarrierId() {
		return shippingCarrierId;
	}
	public void setShippingCarrierId(Integer shippingCarrierId) {
		this.shippingCarrierId = shippingCarrierId;
	}
	public String getShippingTrackingNo() {
		return shippingTrackingNo;
	}
	public void setShippingTrackingNo(String shippingTrackingNo) {
		this.shippingTrackingNo = shippingTrackingNo;
	}
	public String getOtherShippingCarrier() {
		return otherShippingCarrier;
	}
	public void setOtherShippingCarrier(String otherShippingCarrier) {
		this.otherShippingCarrier = otherShippingCarrier;
	}
	public Integer getReturnCarrierId() {
		return returnCarrierId;
	}
	public void setReturnCarrierId(Integer returnCarrierId) {
		this.returnCarrierId = returnCarrierId;
	}
	public String getReturnTrackingNo() {
		return returnTrackingNo;
	}
	public void setReturnTrackingNo(String returnTrackingNo) {
		this.returnTrackingNo = returnTrackingNo;
	}
	public String getOtherReturnCarrier() {
		return otherReturnCarrier;
	}
	public void setOtherReturnCarrier(String otherReturnCarrier) {
		this.otherReturnCarrier = otherReturnCarrier;
	}
	public Boolean isProviderBringparts() {
		return providerBringparts;
	}
	public void setProviderBringparts(Boolean providerBringparts) {
		this.providerBringparts = providerBringparts;
	}
	public LocationRequest getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(LocationRequest pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public Boolean isHasPartsAtDifferntLocation() {
		return hasPartsAtDifferntLocation;
	}
	public void setHasPartsAtDifferntLocation(Boolean hasPartsAtDifferntLocation) {
		this.hasPartsAtDifferntLocation = hasPartsAtDifferntLocation;
	}
	public ContactRequest getPickupContact() {
		return pickupContact;
	}
	public void setPickupContact(ContactRequest pickupContact) {
		this.pickupContact = pickupContact;
	}

	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("partId", this.getPartId())
			.append("modelNumber", this.getModelNumber())
			.toString();
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getReferencePartId() {
		return referencePartId;
	}
	public void setReferencePartId(String referencePartId) {
		this.referencePartId = referencePartId;
	}
	public String getShipDate() {
		return shipDate;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}	
	
}
