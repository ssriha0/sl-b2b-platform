package com.newco.marketplace.web.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.web.utils.SLStringUtils;

public class SOPartsDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1683874068677417299L;
	private Integer partId;
	

	private String title;
	private String manufacturer;
	private String size;//   	 24"x32"x8"
	private String modelNumber;
	private String weight;
	private String qty;
	private String description;
	private String pickupInstructions;
	private Integer shippingCarrierId;
	private String shippingCarrier;
	private String otherShippingCarrier;
	private String shippingTrackingNumber;
	private Integer coreReturnCarrierId;
    private String coreReturnTrackingNumber;
    private String coreReturnCarrier;
    private String otherCoreReturnCarrier;
	private SOContactDTO contact;
	private String referencePartId;
	private String shipDate;
	private String shipDateFormatted;
	private String serialNumber;
	private String manufacturerPartNumber;
	private String vendorPartNumber;
	private String partType;
	private String additionalPartInfo;
	private String orderNumber;
	private String purchaseOrderNumber;
	private Integer partStatusId;
	private String returnTrackDate;
	private String returnTrackDateFormatted;
	private String productLine;
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
	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public SOPartsDTO()
	{		
	}

	public SOPartsDTO(SOWPartDTO dto)
	{
		title = dto.getManufacturer() + " " + dto.getModelNumber();
		manufacturer = dto.getManufacturer();
		String standardString="";
		if(dto.getStandard() != null)
		{
			if(dto.getStandard().equals(OrderConstants.ENGLISH))
				standardString = OrderConstants.STANDARD_ENGLISH;
			else if(dto.getStandard().equals(OrderConstants.METRIC))
				standardString = OrderConstants.STANDARD_METRIC;
		}
		//size = dto.getLength() + " x " + dto.getWidth() + " x " + dto.getHeight() + "\t\t" + standardString;
		size = "";
		if( dto.getLength() != null )
			size += dto.getLength();
		if( dto.getWidth() != null )
			size += " x " + dto.getWidth();
		if( dto.getHeight() != null )
			size += "x " + dto.getHeight();
		if( size.length() > 0 )
			size += "\t\t" + standardString;
		
		modelNumber = dto.getModelNumber();
		serialNumber = dto.getSerialNumber();
		manufacturerPartNumber = dto.getManufacturerPartNumber();
		vendorPartNumber = dto.getVendorPartNumber();
		additionalPartInfo = dto.getAdditionalPartInfo();
		purchaseOrderNumber = dto.getPurchaseOrderNumber();
		orderNumber = dto.getOrderNumber();
		partStatusId = dto.getPartStatusId();
		
		weight = dto.getWeight();
		if(SLStringUtils.IsParsableNumber(dto.getQuantity()))
			qty = (dto.getQuantity());
		else
			qty = "0";
		description = dto.getPartDesc();
		pickupInstructions = "Pickup Instructions TODO- fix this";
		shippingCarrierId =dto.getShippingCarrierId();
		shippingTrackingNumber = dto.getShippingTrackingNo();
		shipDate=dto.getShipDate();
		returnTrackDate=dto.getReturnTrackDate();
		if (!StringUtils.isBlank(shipDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fmtPartShipDate=null;
			try {
				fmtPartShipDate = sdf.parse(shipDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			shipDateFormatted = DateUtils.getFormatedDate(fmtPartShipDate, "MMM d, yyyy");
		}
		else{
			shipDateFormatted="";
			
		}
		if (!StringUtils.isBlank(returnTrackDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fmtReturnTrackDate=null;
			try {
				fmtReturnTrackDate = sdf.parse(returnTrackDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnTrackDateFormatted = DateUtils.getFormatedDate(fmtReturnTrackDate, "MMM d, yyyy");
		}
		else{
			returnTrackDateFormatted="";
			
		}
		otherShippingCarrier = dto.getOtherShippingCarrier();
		
		coreReturnCarrierId=dto.getReturnCarrierId();
		coreReturnTrackingNumber = dto.getReturnTrackingNo();
		otherCoreReturnCarrier = dto.getOtherReturnCarrier();
		contact = new SOContactDTO(dto.getPickupContactLocation());
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPickupInstructions() {
		return pickupInstructions;
	}
	public void setPickupInstructions(String pickupInstructions) {
		this.pickupInstructions = pickupInstructions;
	}
	
	public String getShippingTrackingNumber() {
		return shippingTrackingNumber;
	}
	public void setShippingTrackingNumber(String shippingTrackingNumber) {
		this.shippingTrackingNumber = shippingTrackingNumber;
	}
	public String getCoreReturnTrackingNumber() {
		return coreReturnTrackingNumber;
	}
	public void setCoreReturnTrackingNumber(String coreReturnTrackingNumber) {
		this.coreReturnTrackingNumber = coreReturnTrackingNumber;
	}
	public SOContactDTO getContact() {
		return contact;
	}
	public void setContact(SOContactDTO contact) {
		this.contact = contact;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("title",this.getTitle())
			.append("manufacturer", this.getManufacturer())
			.append("size", this.getSize())
			.append("modelNumber", this.getModelNumber())	
			.append("weight", this.getWeight())
			.append("qty", this.getQty())
			.append("description", this.getDescription())
			.append("pickupInstructions", this.getPickupInstructions())
			.append("shippingTrackingNumber", this.getShippingTrackingNumber())
			.append("coreReturnTrackingNumber", this.getCoreReturnTrackingNumber())
			.toString();
	}

	public Integer getShippingCarrierId() {
		return shippingCarrierId;
	}

	public void setShippingCarrierId(Integer shippingCarrierId) {
		this.shippingCarrierId = shippingCarrierId;
	}

	public Integer getCoreReturnCarrierId() {
		return coreReturnCarrierId;
	}

	public void setCoreReturnCarrierId(Integer coreReturnCarrierId) {
		this.coreReturnCarrierId = coreReturnCarrierId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getOtherShippingCarrier() {
		return otherShippingCarrier;
	}

	public void setOtherShippingCarrier(String otherShippingCarrier) {
		this.otherShippingCarrier = otherShippingCarrier;
	}

	public String getOtherCoreReturnCarrier() {
		return otherCoreReturnCarrier;
	}

	public void setOtherCoreReturnCarrier(String otherCoreReturnCarrier) {
		this.otherCoreReturnCarrier = otherCoreReturnCarrier;
	}

	public String getCoreReturnCarrier() {
		return coreReturnCarrier;
	}

	public void setCoreReturnCarrier(String coreReturnCarrier) {
		this.coreReturnCarrier = coreReturnCarrier;
	}

	public String getShippingCarrier() {
		return shippingCarrier;
	}

	public void setShippingCarrier(String shippingCarrier) {
		this.shippingCarrier = shippingCarrier;
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
	
	public String getShipDateFormatted() {
		return shipDateFormatted;
	}

	public void setShipDateFormatted(String shipDateFormatted) {
		this.shipDateFormatted = shipDateFormatted;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getReturnTrackDate() {
		return returnTrackDate;
	}

	public void setReturnTrackDate(String returnTrackDate) {
		this.returnTrackDate = returnTrackDate;
	}

	public String getReturnTrackDateFormatted() {
		return returnTrackDateFormatted;
	}

	public void setReturnTrackDateFormatted(String returnTrackDateFormatted) {
		this.returnTrackDateFormatted = returnTrackDateFormatted;
	}

	
}
