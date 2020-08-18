package com.servicelive.orderfulfillment.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.orderfulfillment.domain.type.CarrierType;
import com.servicelive.orderfulfillment.domain.type.MeasurementStandard;

/**
 * 
 * @author Yunus Burhani
 * 
 */

@Entity
@Table(name = "so_parts")
@XmlRootElement()
public class SOPart extends SOChild implements Comparable<SOPart> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1373806363093793981L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "part_id")
	private Integer partId;

	@Column(name = "part_descr")
	private String partDs;

	@Column(name = "ship_carrier_id")
	private Integer shipCarrierId;
	
	@Column(name = "ship_carrier_other")
	private String shipCarrierOther;
	
	@Column(name = "ship_track_no")
	private String shipTrackNo;

	@Column(name = "return_carrier_id")
	private Integer returnCarrierId;
	
	@Column(name = "return_carrier_other")
	private String returnCarrierOther;
	
	@Column(name = "return_track_no")
	private String returnTrackNo;

	@Column(name = "core_return_carrier_id")
	private Integer coreReturnCarrierId;
	
	@Column(name = "core_return_carrier_other")
	private String coreReturnCarrierOther;
	
	@Column(name = "core_return_track_no")
	private String coreReturnTrackNo;
	
	@Column(name = "provider_bring_part_ind")
	private Boolean providerBringPartInd;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "model_no")
	private String modelNumber;
	
	@Column(name = "measurement_standard")
	private Integer measurementStandard = MeasurementStandard.ENGLISH.getId();
	
	@Column(name = "length")
	private String length;
	
	@Column(name = "width")
	private String width;
	
	@Column(name = "height")
	private String height;
	
	@Column(name = "weight")
	private String weight;
	
	@Column(name = "quantity")
	private String quantity = "1";

	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "so_locn_id")
	private SOLocation pickupLocation;
	
	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "so_contact_id")
	private SOContact pickupContact;
	
	@Column(name = "ship_date")
	private Date shipDate;
	
	@Column(name = "reference_part_id")
	private String referencePartId;
	
	@Column(name = "parts_file_generated_date")
	private Date partFileGeneratedDate;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "manufacturer_part_number")
	private String manufacturerPartNumber;
	
	@Column(name = "vendor_part_number")
	private String vendorPartNumber;
	
	@Column(name = "product_line")
	private String productName;
	
	@Column(name = "additional_part_info")
	private String additionalPartInfo;
	
	@Column(name = "purchase_order_number")
	private String purchaseOrderNumber;
	
	@Column(name = "part_status_id")
	private Integer partStatusInt;
	
	@Column(name = "return_track_date")
	private String returnTrackDate;
	
	@Column(name = "order_number")
	private String orderNumber;
	
	@Column(name = "alt_part_ref1")
	private String alternatePartReference1;
	
	@Column(name = "alt_part_ref2")
	private String alternatePartReference2;
    
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (parent instanceof ServiceOrder) {
			this.setServiceOrder((ServiceOrder)parent);
			if(this.pickupLocation != null)
				this.pickupLocation.setServiceOrder((ServiceOrder)parent);
			if(this.pickupContact != null)
				this.pickupContact.setServiceOrder((ServiceOrder)parent);
			if(this.pickupLocation != null && this.pickupContact != null){
				this.pickupContact.setLocation(this.pickupLocation);
			}
		}
	}

	public int compareTo(SOPart o) {
		return new CompareToBuilder()
					.append(alternatePartReference1, o.alternatePartReference1)
					.append(alternatePartReference2, o.alternatePartReference2)
				 	.append(height, o.height).append(length, o.length)
				 	.append(manufacturer, o.manufacturer).append(measurementStandard, o.measurementStandard)
				 	.append(modelNumber, o.modelNumber).append(partDs, o.partDs)
				 	.append(partFileGeneratedDate, o.partFileGeneratedDate).append(productName, o.productName)
				 	.append(providerBringPartInd, o.providerBringPartInd)
				 	.append(quantity, o.quantity).append(referencePartId, o.referencePartId)
				 	.append(returnCarrierId, o.returnCarrierId)
				 	.append(returnCarrierOther, o.returnCarrierOther).append(returnTrackDate, o.returnTrackDate)
				 	.append(returnTrackNo, o.returnTrackNo).append(shipCarrierId, o.shipCarrierId)
				 	.append(shipCarrierOther, o.shipCarrierOther).append(shipDate, o.shipDate)
				 	.append(shipTrackNo, o.shipTrackNo).append(weight, o.weight)
				 	.append(width, o.width).append(pickupContact, o.pickupContact)
				 	.append(pickupLocation, o.pickupLocation).toComparison();
	}

	/**
	 * Define equality of SOAddon.
	 */
	@Override 
	public boolean equals( Object aThat ) {
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOPart) ) return false;
	
	    SOPart o = (SOPart)aThat;
	    boolean result = new EqualsBuilder()
					.append(alternatePartReference1, o.alternatePartReference1)
					.append(alternatePartReference2, o.alternatePartReference2)
				 	.append(height, o.height).append(length, o.length)
				 	.append(manufacturer, o.manufacturer).append(measurementStandard, o.measurementStandard)
				 	.append(modelNumber, o.modelNumber).append(partDs, o.partDs)
				 	.append(pickupContact, o.pickupContact)
				 	.append(pickupLocation, o.pickupLocation).append(productName, o.productName)
				 	.append(providerBringPartInd, o.providerBringPartInd)
				 	.append(quantity, o.quantity).append(referencePartId, o.referencePartId)
				 	.append(returnCarrierId, o.returnCarrierId)
				 	.append(returnCarrierOther, o.returnCarrierOther).append(returnTrackDate, o.returnTrackDate)
				 	.append(returnTrackNo, o.returnTrackNo).append(shipCarrierId, o.shipCarrierId)
				 	.append(shipCarrierOther, o.shipCarrierOther).append(shipDate, o.shipDate)
				 	.append(shipTrackNo, o.shipTrackNo).append(weight, o.weight)
				 	.append(width, o.width).isEquals();
	    if(!result) return result;
	    result = new EqualsBuilder().append(pickupContact, o.pickupContact).isEquals();
	    if(!result) return result;
	    result = new EqualsBuilder().append(pickupLocation, o.pickupLocation).isEquals();
	    return result;
	}

	public String getAdditionalPartInfo() {
		return additionalPartInfo;
	}

	public String getAlternatePartReference1() {
		return alternatePartReference1;
	}

	public String getAlternatePartReference2() {
		return alternatePartReference2;
	}

	public Integer getCoreReturnCarrierId() {
		return coreReturnCarrierId;
	}

	public String getCoreReturnCarrierOther() {
		return coreReturnCarrierOther;
	}

	public String getCoreReturnTrackNo() {
		return coreReturnTrackNo;
	}

	public String getHeight() {
		return height;
	}

	public String getLength() {
		return length;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getManufacturerPartNumber() {
		return manufacturerPartNumber;
	}

	public Integer getMeasurementStandard() {
		return measurementStandard;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getPartDs() {
		return partDs;
	}

	public Date getPartFileGeneratedDate() {
		return partFileGeneratedDate;
	}

	public Integer getPartId() {
		return partId;
	}

	public Integer getPartStatusInt() {
		return partStatusInt;
	}

	public SOContact getPickupContact() {
		return pickupContact;
	}

	public SOLocation getPickupLocation() {
		return pickupLocation;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public Boolean getProviderBringPartInd() {
		return providerBringPartInd;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getReferencePartId() {
		return referencePartId;
	}

	public CarrierType getReturnCarrierId() {
		if(returnCarrierId == null) return null;
		return CarrierType.fromId(returnCarrierId);
	}

	public String getReturnCarrierOther() {
		return returnCarrierOther;
	}

	public String getReturnTrackDate() {
		return returnTrackDate;
	}

	public String getReturnTrackNo() {
		return returnTrackNo;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public CarrierType getShipCarrierId() {
		if(shipCarrierId == null) return null;
		return CarrierType.fromId(shipCarrierId);
	}

	public String getShipCarrierOther() {
		return shipCarrierOther;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public String getShipTrackNo() {
		return shipTrackNo;
	}

	public String getVendorPartNumber() {
		return vendorPartNumber;
	}

	public String getWeight() {
		return weight;
	}

	public String getWidth() {
		return width;
	}

	/**
   	* A class that overrides equals must also override hashCode.
  	*/
	@Override 
  	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(alternatePartReference1).append(alternatePartReference2)
		 	.append(height).append(length)
		 	.append(manufacturer).append(measurementStandard)
		 	.append(modelNumber).append(partDs)
		 	.append(partFileGeneratedDate).append(productName).append(providerBringPartInd)
		 	.append(quantity).append(referencePartId)
		 	.append(returnCarrierId).append(returnCarrierOther).append(returnTrackDate)
		 	.append(returnTrackNo).append(shipCarrierId)
		 	.append(shipCarrierOther).append(shipDate)
		 	.append(shipTrackNo).append(weight)
		 	.append(width).append(pickupContact)
		 	.append(pickupLocation);
		 return hcb.toHashCode();
  	}

	@XmlElement()
	public void setAdditionalPartInfo(String additionalPartInfo) {
		this.additionalPartInfo = additionalPartInfo;
	}

	@XmlElement()
	public void setAlternatePartReference1(String alternatePartReference1) {
		this.alternatePartReference1 = alternatePartReference1;
	}

	@XmlElement()
	public void setAlternatePartReference2(String alternatePartReference2) {
		this.alternatePartReference2 = alternatePartReference2;
	}

	@XmlElement()
	public void setCoreReturnCarrierId(Integer coreReturnCarrierId) {
		this.coreReturnCarrierId = coreReturnCarrierId;
	}

	@XmlElement()
	public void setCoreReturnCarrierOther(String coreReturnCarrierOther) {
		this.coreReturnCarrierOther = coreReturnCarrierOther;
	}

	@XmlElement()
	public void setCoreReturnTrackNo(String coreReturnTrackNo) {
		this.coreReturnTrackNo = coreReturnTrackNo;
	}
	
	@XmlElement()
	public void setHeight(String height) {
		this.height = height;
	}
	
	@XmlElement()
	public void setLength(String length) {
		this.length = length;
	}
	
	@XmlElement()
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@XmlElement()
	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		this.manufacturerPartNumber = manufacturerPartNumber;
	}

	@XmlElement()
	public void setMeasurementStandard(Integer measurementStandard) {
		this.measurementStandard = measurementStandard;
	}

	@XmlElement()
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	@XmlElement()
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@XmlElement()
	public void setPartDs(String partDs) {
		this.partDs = partDs;
	}

	@XmlElement()
	public void setPartFileGeneratedDate(Date partFileGeneratedDate) {
		this.partFileGeneratedDate = partFileGeneratedDate;
	}

	@XmlElement()
	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	@XmlElement()
	public void setPartStatusInt(Integer partStatusInt) {
		this.partStatusInt = partStatusInt;
	}

	@XmlElement()
	public void setPickupContact(SOContact pickupContact) {
		this.pickupContact = pickupContact;
		if(this.getServiceOrder() != null){
			this.pickupContact.setServiceOrder(this.getServiceOrder());
			if(this.pickupLocation != null){
				this.pickupContact.setLocation(this.pickupLocation);
			}
		}
	}

	@XmlElement()
	public void setPickupLocation(SOLocation pickupLocation) {
		this.pickupLocation = pickupLocation;
		if(this.getServiceOrder() != null){
			this.pickupLocation.setServiceOrder(this.getServiceOrder());
			if(this.pickupContact != null){
				this.pickupContact.setLocation(this.pickupLocation);
			}
		}
	}

	@XmlElement()
	public void setProductName(String productName) {
		this.productName = productName;
	}

	@XmlElement()
	public void setProviderBringPartInd(Boolean providerBringPartInd) {
		this.providerBringPartInd = providerBringPartInd;
	}

	@XmlElement()
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	@XmlElement()
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@XmlElement()
	public void setReferencePartId(String referencePartId) {
		this.referencePartId = referencePartId;
	}

	@XmlElement()
	public void setReturnCarrierId(CarrierType returnCarrier) {
		this.returnCarrierId = returnCarrier.getId();
	}

	@XmlElement()
	public void setReturnCarrierOther(String returnCarrierOther) {
		this.returnCarrierOther = returnCarrierOther;
	}

	@XmlElement()
	public void setReturnTrackDate(String returnTrackDate) {
		this.returnTrackDate = returnTrackDate;
	}

	@XmlElement()
	public void setReturnTrackNo(String returnTrackNo) {
		this.returnTrackNo = returnTrackNo;
	}

	@XmlElement()
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@XmlElement()
	public void setShipCarrierId(CarrierType shipCarrier) {
		this.shipCarrierId = shipCarrier.getId();
	}

	@XmlElement()
	public void setShipCarrierOther(String shipCarrierOther) {
		this.shipCarrierOther = shipCarrierOther;
	}

	@XmlElement()
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	@XmlElement()
	public void setShipTrackNo(String shipTrackNo) {
		this.shipTrackNo = shipTrackNo;
	}
	
	@XmlElement()
	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}
	
	@XmlElement()
	public void setWeight(String weight) {
		this.weight = weight;
	}

	@XmlElement()
	public void setWidth(String width) {
		this.width = width;
	}
  
	@Override
	@XmlTransient()
	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
		if(this.pickupLocation != null)
			this.pickupLocation.setServiceOrder(this.serviceOrder);
		if(this.pickupContact != null)
			this.pickupContact.setServiceOrder(this.serviceOrder);
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
