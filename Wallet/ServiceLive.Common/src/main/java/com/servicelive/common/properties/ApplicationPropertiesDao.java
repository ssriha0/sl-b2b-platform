package com.servicelive.common.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataNotFoundException;


/**
 * ApplicationPropertiesDaoImpl keeps an internal collection of data from the application_properties table.
 * The internal collection is loaded only once when the class is first created. In order for any changes that
 * have been made to the database table to be reflected in this class the server that it is running in will
 * have to be restarted.
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 * 
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 00:40:38 $
 */
public class ApplicationPropertiesDao extends ABaseDao implements IApplicationPropertiesDao {

	/** The data map. */
	private static Map<String, ApplicationPropertiesVO> dataMap = new HashMap<String, ApplicationPropertiesVO>();

	/**
	 * loadDataTable first clears the internal Map of all data and then reloads it with data from the database.
	 * 
	 * @throws DataAccessException 
	 */
	@SuppressWarnings("unchecked")
	private void loadDataTable() throws DataAccessException {

		List<ApplicationPropertiesVO> allData = (List<ApplicationPropertiesVO>) queryForList("application_properties.query");

		dataMap.clear();
		for (ApplicationPropertiesVO data : allData) {
			dataMap.put(data.getAppKey(), data);
		}
	}


	public synchronized ApplicationPropertiesVO query(String key) throws DataAccessException, DataNotFoundException {

		ApplicationPropertiesVO toReturn = null;
		if (dataMap.containsKey(key)) {
			toReturn = (ApplicationPropertiesVO) dataMap.get(key);
		} else {
			loadDataTable();
			if (dataMap.containsKey(key)) {
				toReturn = (ApplicationPropertiesVO) dataMap.get(key);
			} else {
				throw new DataNotFoundException("Unable to find data for key: " + key);
			}
		}

		return toReturn;
	}


	public ApplicationPropertiesVO queryToDatabase(String key) throws DataAccessException, DataNotFoundException {
	// reverted changes for SLT-2112
		return (ApplicationPropertiesVO) queryForObject("application_propertiesbykey.query", key);
	}

/*
	public synchronized List<ApplicationPropertiesVO> queryList() throws DataAccessException {

		List<ApplicationPropertiesVO> toReturn = new ArrayList<ApplicationPropertiesVO>();

		if (dataMap.isEmpty()) {
			loadDataTable();
		}

		for (String key : dataMap.keySet()) {
			toReturn.add((ApplicationPropertiesVO) dataMap.get(key));
		}

		return toReturn;
	}
*/
}
