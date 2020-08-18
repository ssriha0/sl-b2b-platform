/**
 * 
 */
package com.newco.marketplace.vo.provider;

/**
 * @author sahmad7
 *
 */
public class VendorLocation extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8791729235240993624L;
	private int vendorId = -1;
	private int locationId = -1;
	private java.sql.Date createdDate;
	
	public java.sql.Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(java.sql.Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
}
