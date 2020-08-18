package com.newco.marketplace.dto;

import com.sears.os.vo.SerializableBaseVO;


public class BuyerSOTemplatePartDTO extends SerializableBaseVO{
	
	private String title;
	private String manufacturer;
	private String size;//   	 24"x32"x8"
	private String modelNumber;
	private String weight;
	private String qty;
	private String description;
	private String pickupInstructions;
	private BuyerSOTemplateContactDTO contact;
	private String referencePartId;
	private String shipDate;
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
	public BuyerSOTemplateContactDTO getContact() {
		return contact;
	}
	public void setContact(BuyerSOTemplateContactDTO contact) {
		this.contact = contact;
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
