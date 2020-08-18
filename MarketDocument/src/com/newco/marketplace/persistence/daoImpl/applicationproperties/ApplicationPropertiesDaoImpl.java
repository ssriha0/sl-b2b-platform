package com.newco.marketplace.persistence.daoImpl.applicationproperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * ApplicationPropertiesDaoImpl keeps an internal collection of data from the application_properties table.
 * The internal collection is loaded only once when the class is first created.  In order for any changes that
 * have been made to the database table to be reflected in this class the server that it is running in will
 * have to be restarted.
 *  
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 00:40:38 $
 */

public class ApplicationPropertiesDaoImpl extends ABaseImplDao implements IApplicationPropertiesDao{
	
	
	private static Map<String,ApplicationPropertiesVO> dataMap = new HashMap<String,ApplicationPropertiesVO>();
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao#query(java.lang.String)
	 */
	public synchronized ApplicationPropertiesVO query(String key)throws DataAccessException, DataNotFoundException {
		ApplicationPropertiesVO toReturn = null;
		if (dataMap.containsKey(key)) {
			toReturn = dataMap.get(key);
		} else {
			loadDataTable();
			if (dataMap.containsKey(key)) {
				toReturn = dataMap.get(key);
			} else {
				throw new DataNotFoundException ("Unable to find data for key: " + key);
			}
		}
		
		return toReturn;
    }
    
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao#queryList()
	 */
	public synchronized List<ApplicationPropertiesVO> queryList() throws DataAccessException {
		List<ApplicationPropertiesVO> toReturn = new ArrayList<ApplicationPropertiesVO>();

		if (dataMap.isEmpty()) {
			loadDataTable();
		}
		
		for (String key : dataMap.keySet()) {
			toReturn.add(dataMap.get(key));
		}
		
		return toReturn;
    }
	
	/**
	 * loadDataTable first clears the internal Map of all data and then reloads it with data from the database
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	private void loadDataTable() throws DataAccessException {
		List<ApplicationPropertiesVO> allData = queryForList("doc_application_properties.query", new ApplicationPropertiesVO());
		
		dataMap.clear();
		for (ApplicationPropertiesVO data : allData) {
			dataMap.put(data.getAppKey(), data);
		}
    }
	public synchronized String queryByKey(String key)throws DataAccessException, DataNotFoundException {
		return (String)queryForObject("application_propertiesbykey.query", key);
    }
}