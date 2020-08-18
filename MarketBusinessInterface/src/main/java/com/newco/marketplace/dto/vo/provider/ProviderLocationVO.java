package com.newco.marketplace.dto.vo.provider;

import java.sql.Timestamp;

import com.newco.marketplace.dto.vo.LocationVO;


public class ProviderLocationVO extends LocationVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2253770902312023840L;

	private String street;
	
	private String gis_latitude;
	private String gis_longitude;

	// Added new variables for using in updating location point
   
    private Integer locationId = new Integer(-1);
 
    
    private String XY; 
     
	private String locnName = "";
	
	
	private Integer locnTypeId = new Integer(-1);
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy = "";

	
	
//	 Added new variables for using in updating location point
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	
	public String getXY() {
		return XY;
	}
	public void setXY(String xy) {
		XY = xy;
	}
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getLocnName() {
		return locnName;
	}
	public void setLocnName(String locnName) {
		this.locnName = locnName;
	}
	public Integer getLocnTypeId() {
		return locnTypeId;
	}
	public void setLocnTypeId(Integer locnTypeId) {
		this.locnTypeId = locnTypeId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	public String getGis_latitude() {
		return gis_latitude;
	}
	public void setGis_latitude(String gis_latitude) {
		this.gis_latitude = gis_latitude;
	}
	public String getGis_longitude() {
		return gis_longitude;
	}
	public void setGis_longitude(String gis_longitude) {
		this.gis_longitude = gis_longitude;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	


}
