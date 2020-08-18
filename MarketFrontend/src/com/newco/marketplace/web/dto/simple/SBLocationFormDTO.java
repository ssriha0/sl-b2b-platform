package com.newco.marketplace.web.dto.simple;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.utils.SLStringUtils;

public class SBLocationFormDTO extends SOWBaseTabDTO{

	private static final long serialVersionUID = -2178653439488596019L;
	
	private List<LocationVO> locations = new ArrayList<LocationVO>();
	private LocationVO newLocation = new LocationVO();
	private Integer selectedLocationId;

	public List<LocationVO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationVO> locations) {
		this.locations = locations;
	}

	public LocationVO getNewLocation() {
		return newLocation;
	}

	public void setNewLocation(LocationVO newLocation) {
		this.newLocation = newLocation;
	}


	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return OrderConstants.SSO_SELECT_LOCATION_DTO;
	}

	@Override
	public void validate()
	{
	}

	public boolean hasSelectedLocation(String locationId)
	{
		clearAllErrors();
		
		if(SLStringUtils.isNullOrEmpty(locationId))
		{
			addError("Select Location", "A location must be selected before continuing.", OrderConstants.SOW_TAB_ERROR);
			return false;
		}
		
		return true;
	}
	
	public void validateNewLocation()
	{
		clearAllErrors();
		
		if(SLStringUtils.isNullOrEmpty(newLocation.getLocName()))
		{
			addError("Location Name", "Location Name is required", OrderConstants.SOW_TAB_ERROR);
		}

		if(!SLStringUtils.isNullOrEmpty(newLocation.getLocName()))
		{
			for(LocationVO vo : getLocations())
			{
				if(vo.getLocName().trim().equalsIgnoreCase(newLocation.getLocName().trim()))
				{
					addError("Location Name", "Location Name (case insensitive) cannot be the same as an existing location", OrderConstants.SOW_TAB_ERROR);					
					break;
				}
			}
		}		
		
		if(SLStringUtils.isNullOrEmpty(newLocation.getStreet1()))
		{
			addError("Street 1", "Street 1 is required", OrderConstants.SOW_TAB_ERROR);
		}

		if(SLStringUtils.isNullOrEmpty(newLocation.getCity()))
		{
			addError("City", "City", OrderConstants.SOW_TAB_ERROR);
		}
		
		if(SLStringUtils.isNullOrEmpty(newLocation.getState()))
		{
			addError("State", "State is required", OrderConstants.SOW_TAB_ERROR);
		}
		
		if(newLocation.getState().equals("-1"))
		{
			addError("State", "State is required", OrderConstants.SOW_TAB_ERROR);
		}
		
		if(SLStringUtils.isNullOrEmpty(newLocation.getZip()))
		{
			addError("Zip", "Zip is required", OrderConstants.SOW_TAB_ERROR);
		}

		if(!SLStringUtils.isNullOrEmpty(newLocation.getZip()))
		{
			if(newLocation.getZip().length() != 5)
				addError("Zip", "Zip code is not 5 digits", OrderConstants.SOW_TAB_ERROR);
		}
		int zipCheck = LocationUtils.checkIfZipAndStateValid(newLocation.getZip(),newLocation.getState());
		switch (zipCheck) {
			case Constants.LocationConstants.ZIP_NOT_VALID:
				addError(getTheResourceBundle().getString("Zip"), 
						getTheResourceBundle().getString("Zip_Not_Valid"), OrderConstants.SOW_TAB_ERROR);
				break;
			case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
				addError(getTheResourceBundle().getString("Zip"), 
						getTheResourceBundle().getString("Zip_State_No_Match"), OrderConstants.SOW_TAB_ERROR);
				break;
		}		

	}

	public Integer getSelectedLocationId() {
		return selectedLocationId;
	}

	public void setSelectedLocationId(Integer selectedLocationId) {
		this.selectedLocationId = selectedLocationId;
	}

}
