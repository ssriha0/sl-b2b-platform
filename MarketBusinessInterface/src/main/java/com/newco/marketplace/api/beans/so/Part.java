package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XStreamAlias("part")
public class Part {
	
	@XStreamAlias("partId")
	private int partId;
	
	@XStreamAlias("manufacturer")
	private String manufacturer;
	
	@XStreamAlias("model")
	private String model;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("quantity")
	private String quantity;
	
	@XStreamAlias("dimensions")
	private Dimensions dimensions;
	
	@XStreamAlias("shipCarrier")
	private String shipCarrier;
	
	@XStreamAlias("shipTracking")
	private String shipTracking;
	
	@XStreamAlias("returnCarrier")
	private String returnCarrier;
	
	@XStreamAlias("returnTracking")
	private String returnTracking;
	
	@XStreamAlias("requiresPickup")
	private String requiresPickup;
	
	@XStreamAlias("shippingDate")
	private String shippingDate;
	
	@XStreamAlias("pickupLocation")
	private Location pickupLocation;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Dimensions getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	public String getShipCarrier() {
		return shipCarrier;
	}

	public void setShipCarrier(String shipCarrier) {
		this.shipCarrier = shipCarrier;
	}

	public String getShipTracking() {
		return shipTracking;
	}

	public void setShipTracking(String shipTracking) {
		this.shipTracking = shipTracking;
	}

	public String getReturnCarrier() {
		return returnCarrier;
	}

	public void setReturnCarrier(String returnCarrier) {
		this.returnCarrier = returnCarrier;
	}

	public String getReturnTracking() {
		return returnTracking;
	}

	public void setReturnTracking(String returnTracking) {
		this.returnTracking = returnTracking;
	}

	public String getRequiresPickup() {
		return requiresPickup;
	}

	public void setRequiresPickup(String requiresPickup) {
		this.requiresPickup = requiresPickup;
	}

	public Location getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(Location pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
}
