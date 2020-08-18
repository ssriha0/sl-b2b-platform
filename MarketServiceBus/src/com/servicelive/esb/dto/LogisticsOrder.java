package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Order")
public class LogisticsOrder  implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -7865047025163761024L;

	@XStreamAlias("Address")
    private Address address;
	
	@XStreamAlias("DeliveryDate")
	private String deliveryDate;
	
	@XStreamAlias("DeliveryDescription")
    private String deliveryDescription;
	
	@XStreamAlias("DeliveryStatus")
    private String deliveryStatus;
	
	@XStreamAlias("DeliveryTimeCode")
    private String deliveryTimeCode;
	
	@XStreamAlias("DeliveryTimeDescription")
    private String deliveryTimeDescription;
	
	@XStreamAlias("HoldCode")
    private String holdCode;
	
	@XStreamAlias("HoldDescription")
    private String holdDescription;
	
	@XStreamAlias("LastMaintenanceDate")
    private String lastMaintenanceDate;
	
	@XStreamAlias("Number")
    private String number;
	
	@XStreamAlias("PendCode")
    private String pendCode;
	
	@XStreamAlias("PendDescription")
    private String pendDescription;
	
	@XStreamAlias("PickupLocationCode")
    private String pickupLocationCode;
	
	@XStreamAlias("SpecialInstructions")
    private String specialInstructions;
	
	@XStreamAlias("ItemInstructions")
	private String itemInstructions;
	
	@XStreamAlias("ShipmentMethodCode")
    private String shipmentMethodCode;
	
	@XStreamAlias("ShipmentMethodDescription")
    private String shipmentMethodDescription;
	
	@XStreamAlias("WarehouseNumber")
    private String warehouseNumber;
    
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryDescription() {
		return deliveryDescription;
	}

	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryTimeCode() {
		return deliveryTimeCode;
	}

	public void setDeliveryTimeCode(String deliveryTimeCode) {
		this.deliveryTimeCode = deliveryTimeCode;
	}

	public String getDeliveryTimeDescription() {
		return deliveryTimeDescription;
	}

	public void setDeliveryTimeDescription(String deliveryTimeDescription) {
		this.deliveryTimeDescription = deliveryTimeDescription;
	}

	public String getHoldCode() {
		return holdCode;
	}

	public void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
	}

	public String getHoldDescription() {
		return holdDescription;
	}

	public void setHoldDescription(String holdDescription) {
		this.holdDescription = holdDescription;
	}

	public String getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	public void setLastMaintenanceDate(String lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPendCode() {
		return pendCode;
	}

	public void setPendCode(String pendCode) {
		this.pendCode = pendCode;
	}

	public String getPendDescription() {
		return pendDescription;
	}

	public void setPendDescription(String pendDescription) {
		this.pendDescription = pendDescription;
	}

	public String getPickupLocationCode() {
		return pickupLocationCode;
	}

	public void setPickupLocationCode(String pickupLocationCode) {
		this.pickupLocationCode = pickupLocationCode;
	}

	public String getShipmentMethodCode() {
		return shipmentMethodCode;
	}

	public void setShipmentMethodCode(String shipmentMethodCode) {
		this.shipmentMethodCode = shipmentMethodCode;
	}

	public String getShipmentMethodDescription() {
		return shipmentMethodDescription;
	}

	public void setShipmentMethodDescription(String shipmentMethodDescription) {
		this.shipmentMethodDescription = shipmentMethodDescription;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getWarehouseNumber() {
		return warehouseNumber;
	}

	public void setWarehouseNumber(String warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}

	public String getItemInstructions() {
		return itemInstructions;
	}

	public void setItemInstructions(String itemInstructions) {
		this.itemInstructions = itemInstructions;
	}

    
}
