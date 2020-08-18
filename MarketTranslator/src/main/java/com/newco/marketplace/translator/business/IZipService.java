package com.newco.marketplace.translator.business;

import java.util.List;
import java.util.TimeZone;

import com.newco.marketplace.translator.exception.InvalidZipCodeException;

public interface IZipService {
	
	/**
	 * Retrieves and returns the timezone for given zip code
	 * 
	 * @param zipCode
	 * @return Timezone of given zipcode
	 * @throws InvalidZipCodeException In case given zipcode is not valid, or no timezone found for given zipcode.
	 */
	public TimeZone getTimeZoneByZip(String zipCode) throws InvalidZipCodeException;
	
	/**
	 * Retrieves and returns list of timezones for a given state
	 * 
	 * @param stateCode
	 * @return TimeZone List of timezones in given state
	 */
	public List<TimeZone> getTimeZonesByState(String stateCode);
	
}
