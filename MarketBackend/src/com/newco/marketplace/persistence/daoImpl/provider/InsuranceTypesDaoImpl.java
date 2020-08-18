package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IInsuranceTypesDao;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;



public class InsuranceTypesDaoImpl extends SqlMapClientDaoSupport implements
		IInsuranceTypesDao {
	public List queryListForInsuranceTypes(InsuranceTypesVO insuranceTypesVO)
			throws DBException {
		List insuranceTypeList = new ArrayList();
		logger.info("insuranceTypesVO.getVendorId() in dao impl"
				+ insuranceTypesVO.getVendorId());
		try {
			insuranceTypeList = (java.util.ArrayList) getSqlMapClient()
					.queryForList(
							"credentialProfile.queryListForInsuranceTypes",
							insuranceTypesVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @InsuranceTypesDaoImpl.queryListForInsuranceTypes() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @InsuranceTypesDaoImpl.queryListForInsuranceTypes() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsuranceTypesDaoImpl.queryListForInsuranceTypes() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @InsuranceTypesDaoImpl.queryListForInsuranceTypes() due to "
							+ ex.getMessage());
		}

		return insuranceTypeList;
	}


/* (non-Javadoc)
 * Method to get a Map of Other Insurance Categories
 * @see com.newco.marketplace.persistence.iDao.provider.IInsuranceTypesDao#getAdditionalMapCategory(com.newco.marketplace.vo.provider.CredentialProfile)
 */
public HashMap getAdditionalMapCategory(CredentialProfile cProfile ) {
		
		HashMap<Integer,String> mapCategory = new HashMap<Integer, String>();
		Integer credentialTypeId=cProfile.getCredentialTypeId();

		try {
			mapCategory = (HashMap<Integer,String>)getSqlMapClient().queryForMap("credentialProfile.queryGetInsuranceMapCategory",credentialTypeId, "id","description");
			mapCategory=sortHashMapByValuesD(mapCategory);
			//Filter List to remove AL, GL and WL Insurances
			//mapCategory=filterMap(mapCategory);
		} catch (Exception ex) {
			logger.info("General Exception @InsuranceTypesDaoImpl.getAdditionalMapCategory() due to  "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return mapCategory;
	}

	public static HashMap sortHashMapByValuesD(HashMap passedMap) {
	    ArrayList mapKeys = new ArrayList(passedMap.keySet());
	    ArrayList<String>mapValues = new ArrayList(passedMap.values());
	    Collections.sort(mapValues,new Comparator<String>() {
	       public int compare(String o1, String o2) {
	    	   if(null== o1 && null != o2){
	    			return 1;
	    		}else if(null!= o1&& null == o2){
	    			return -1;
	    		}else if(null== o1 && null == o2){
	    			return 0;
	    		}else{
	    			return o1.trim().compareToIgnoreCase(o2.trim());
	    		}
		}
		});
	    
	    Collections.sort(mapKeys);
	        
	    HashMap sortedMap= 
	        new LinkedHashMap();
	    
	    Iterator valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        Object val = valueIt.next();
	        Iterator keyIt = mapKeys.iterator();
	        
	        while (keyIt.hasNext()) {
	            Object key = keyIt.next();
	            String comp1 = passedMap.get(key).toString();
	            String comp2 = val.toString();
	            
	            if (comp1.equals(comp2)){
	                passedMap.remove(key);
	                mapKeys.remove(key);
	                sortedMap.put((Long)key, (String)val);
	                break;
	            }

	        }

	    }
	    //To add "Other" to the bottom of the map
	    Long otherKey = 150l;
	    String otherValue = "Other";
	    HashMap sortedOtherMap= new LinkedHashMap();
	    if(sortedMap.containsKey(otherKey)){
	    	sortedMap.remove(otherKey);
	    	sortedMap.remove(otherValue);
	    }
	    sortedOtherMap.putAll(sortedMap);
	    sortedOtherMap.put((Long)otherKey, (String)otherValue);
	    return sortedOtherMap;
	}
	public List getAdditionalInsuranceList(CredentialProfile cProfile) {

		List licenceList = new ArrayList();
		try {

			licenceList = (java.util.ArrayList) getSqlMapClient().queryForList(
					"getInsuranceList.query", cProfile.getVendorId());

		} catch (Exception ex) {
			logger.info("[InsuranceTypesDaoImpl.getAdditionalInsuranceList() - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return licenceList;
	}


}