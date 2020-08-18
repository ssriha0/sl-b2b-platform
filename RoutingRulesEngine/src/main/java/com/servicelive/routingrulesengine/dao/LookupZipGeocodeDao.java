package com.servicelive.routingrulesengine.dao;

import java.util.List;
import java.util.Map;

import com.servicelive.domain.lookup.LookupZipGeocode;

/**
 * 
 * @author svanloon
 *
 */
public interface LookupZipGeocodeDao {

	/**
	 * 
	 * @param stateCd
	 * @return List<LookupZipGeocode> 
	 */
	public List<LookupZipGeocode> findByState(String stateCd);

	/**
	 * 
	 * @param zip
	 * @return
	 */
	public LookupZipGeocode findByZip(String zip);
	
	/**
	 * Find the valid zips from the list
	 * 
	 * @param zipList
	 * @return List<String>
	 */
	public List<String> findZips(List<String> zips);
	

	/**
	 * Find the valid cities from the list
	 * 
	 * @param zips
	 * @return Map<String,String>
	 */
	public Map<String,String> findCityByZips(List<String> zips);
	
	
}
