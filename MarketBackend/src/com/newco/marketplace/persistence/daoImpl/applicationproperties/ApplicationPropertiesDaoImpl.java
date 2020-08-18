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

/*
 * Maintenance History: See bottom of file
 */
public class ApplicationPropertiesDaoImpl extends ABaseImplDao implements IApplicationPropertiesDao{
	
	
	private static Map<String,ApplicationPropertiesVO> dataMap = new HashMap<String,ApplicationPropertiesVO>();
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao#query(java.lang.String)
	 */
	public synchronized ApplicationPropertiesVO query(String key)throws DataAccessException, DataNotFoundException {
		ApplicationPropertiesVO toReturn = null;
		if (dataMap.containsKey(key)) {
			toReturn = (ApplicationPropertiesVO)dataMap.get(key);
		} else {
			loadDataTable();
			if (dataMap.containsKey(key)) {
				toReturn = (ApplicationPropertiesVO)dataMap.get(key);
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
			toReturn.add((ApplicationPropertiesVO)dataMap.get(key));
		}
		
		return toReturn;
    }
	
	/**
	 * loadDataTable first clears the internal Map of all data and then reloads it with data from the database
	 * @throws DataAccessException
	 */
	private void loadDataTable() throws DataAccessException {
		List<ApplicationPropertiesVO> allData = queryForList("application_properties.query", new ApplicationPropertiesVO());
		
		dataMap.clear();
		for (ApplicationPropertiesVO data : allData) {
			dataMap.put(data.getAppKey(), data);
		}
    }
	public synchronized String queryByKey(String key)throws DataAccessException, DataNotFoundException {
		return (String)queryForObject("application_propertiesbykey.query", key);
    }
}
/*
 * Maintenance History
 * $Log: ApplicationPropertiesDaoImpl.java,v $
 * Revision 1.8  2008/04/26 00:40:38  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.6.6.1  2008/04/23 11:42:21  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.7  2008/04/23 05:01:56  hravi
 * Reverting to build 247.
 *
 * Revision 1.6  2008/02/26 18:20:59  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.5.18.1  2008/02/22 19:50:05  slazar2
 * added queryByKey() method
 *
 * Revision 1.5  2007/12/11 03:55:48  mhaye05
 * fixed empty Map
 *
 * Revision 1.4  2007/12/11 01:28:35  mhaye05
 * updated because ApplicationPropertiesDAOImpl.query now takes a String
 *
 * Revision 1.3  2007/12/10 19:19:49  mhaye05
 * updated to have internal cache
 *
 */