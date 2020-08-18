/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.ILicensesAndCertDao;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.StackTraceHelper;


/**
 * @author LENOVO USER
 *
 */
public class LicensesAndCertDaoImpl extends SqlMapClientDaoSupport implements ILicensesAndCertDao {

	private final Log logger = LogFactory.getLog(getClass());
	private IActivityRegistryDao activityRegistryDao;

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ILicensesAndCertDao#getData(com.newco.marketplace.vo.LicensesAndCertVO)
	 */
	public LicensesAndCertVO getData(LicensesAndCertVO objLicensesAndCertVO) {
		
		try {
			
			//objLicensesAndCertVO.setDataList(getDataList());
			LicensesAndCertVO certVO = new LicensesAndCertVO();
			
			certVO = objLicensesAndCertVO;
			
			if(objLicensesAndCertVO.getVendorCredId() > 0 ){
				objLicensesAndCertVO = (LicensesAndCertVO) getSqlMapClient().queryForObject("selectLicensesAndCert.query",
						objLicensesAndCertVO);
				
				if (objLicensesAndCertVO != null && certVO.getCredentialTypeId() > 0)
					objLicensesAndCertVO.setCredentialTypeId(certVO.getCredentialTypeId());
				
				objLicensesAndCertVO.setMapCredentialType(getMapCredentialType());
				if(objLicensesAndCertVO.getCredentialTypeId()>0){
					objLicensesAndCertVO.setMapCategory(getMapCategory(objLicensesAndCertVO));
				}
				objLicensesAndCertVO.setMapState(getMapState());
			}else{
				objLicensesAndCertVO.setMapCredentialType(getMapCredentialType());
				objLicensesAndCertVO.setMapState(getMapState());
				if(objLicensesAndCertVO.getCredentialTypeId()>0){
					objLicensesAndCertVO.setMapCategory(getMapCategory(objLicensesAndCertVO));
				}
			}
		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return objLicensesAndCertVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ILicensesAndCertDao#getData(com.newco.marketplace.vo.LicensesAndCertVO)
	 */
	public LicensesAndCertVO getDataList(LicensesAndCertVO objLicensesAndCertVO) {
		
		try {
			
			objLicensesAndCertVO.setCredentials(getCredentialsList(objLicensesAndCertVO));	
	
		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return objLicensesAndCertVO;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ILicensesAndCertDao#save(com.newco.marketplace.vo.LicensesAndCertVO)
	 */
	public LicensesAndCertVO save(LicensesAndCertVO objLicensesAndCertVO) {
		
		if(objLicensesAndCertVO.getCategoryId() == -1)
			objLicensesAndCertVO.setCategoryId(0);
		if(objLicensesAndCertVO.getCredentialTypeId()== -1)
			objLicensesAndCertVO.setCategoryId(0);	
		Integer vendorCredID = null;
			
	try {
		if(objLicensesAndCertVO.getVendorCredId()>0)
		{
			getSqlMapClient().update("updateLicensesAndCert.update",
					objLicensesAndCertVO);

		} else {
			
		 vendorCredID = (Integer)	getSqlMapClient().insert("insertLicensesAndCert.insert",
					objLicensesAndCertVO);
		 objLicensesAndCertVO.setVendorCredId(vendorCredID.intValue());
		}
		
		getActivityRegistryDao().updateActivityStatus(String.valueOf(objLicensesAndCertVO.getVendorId()), ActivityRegistryConstants.LICENSE);
	} catch (Exception ex) {
		logger.info("[LicensesAndCertDaoImpl.insert or update - Exception] "
				+ ex.getStackTrace());
		ex.printStackTrace();
	}
		return objLicensesAndCertVO;
	}
	
	public HashMap getMapCredentialType() {
		
		HashMap mapCredentialType = new HashMap();

		try {
			mapCredentialType = (HashMap)getSqlMapClient().queryForMap("getCredentialType.query", null, "id","description");
			mapCredentialType=sortHashMapByValuesD(mapCredentialType);
		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return mapCredentialType;
	}
	
	public HashMap getMapCategory(LicensesAndCertVO objLicensesAndCertVO ) {
		
		HashMap mapCategory = new HashMap();

		try {
			mapCategory = (HashMap)getSqlMapClient().queryForMap("getMapCategory.query", objLicensesAndCertVO, "id","description");
			mapCategory=sortHashMapByValuesD(mapCategory);
		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return mapCategory;
	}
	
	public List getMapState() {
		
		List mapState = null;

		try {
			mapState = getSqlMapClient().queryForList("state_code_lookup.query", null);
			
		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}
		
	
		
		return mapState;
	}
	
/*	public LicensesAndCertVO getDataList(LicensesAndCertVO objLicensesAndCertVO) {
			
			try {	
					objLicensesAndCertVO.setMapCredentialType(getMapCredentialType());
				
				
			} catch (Exception ex) {
				logger.info("[LicensesAndCertBOImpl.select - Exception] "
						+ ex.getStackTrace());
				ex.printStackTrace();
			}
			return objLicensesAndCertVO;
	}*/
	/*public LicensesAndCertVO getDataList(LicensesAndCertVO objLicensesAndCertVO) {

		try {
			
				objLicensesAndCertVO = (LicensesAndCertVO) getSqlMapClient().queryForObject("getCredentialList",
						objLicensesAndCertVO);
				
				
			
		} catch (Exception ex) {
			logger.info("[LicensesAndCertDaoImpl.select list - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return objLicensesAndCertVO;
	}*/
	/*
public Map getDataList() {
		
	Map<String, LicensesAndCertVO> studentsMap=null;
		try {
			
			SqlMapClient sql = getSqlMapClient();
			studentsMap = sql.queryForMap("getCredentialList", null, "vendorCredId");

						
		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return studentsMap;
	}
	*/
public List getCredentialsList(LicensesAndCertVO objLicensesAndCertVO){
	
	List credentialList=null;
		try {

			SqlMapClient sql = getSqlMapClient();
			credentialList =  sql.queryForList("getCredentialList.query", objLicensesAndCertVO);   

						
		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return credentialList;
	}

/**
 * @return the activityRegistryDao
 */
public IActivityRegistryDao getActivityRegistryDao() {
	return activityRegistryDao;
}

/**
 * @param activityRegistryDao the activityRegistryDao to set
 */
public void setActivityRegistryDao(IActivityRegistryDao activityRegistryDao) {
	this.activityRegistryDao = activityRegistryDao;
}
	
/* (non-Javadoc)
 * @see com.newco.marketplace.persistence.iDao.ILicensesDao#update(com.newco.marketplace.vo.LicensesVO)
 */
public void update(LicensesAndCertVO objLicensesAndCertVO) {
	
	try {
		/*Reader configReader = Resources
				.getResourceAsReader("resources/ibatis/SqlMapConfig.xml");
		SqlMapClient sqlMap = SqlMapClientBuilder
				.buildSqlMapClient(configReader);
		
		sqlMap.startTransaction();
		if (sqlMap != null) {
			objLoginVO = (LoginVO) sqlMap.queryForObject("selectUser",
					objLoginVO);
		}
		sqlMap.commitTransaction();
		sqlMap.endTransaction();
		*/			
		getSqlMapClient().update("licenses.updateAddLicenseToFileData", objLicensesAndCertVO);
		
		/**
		 * Added to Update Incomplete status.
		 * Fix for Sears00045965.
		 */
		if (objLicensesAndCertVO.getAddCredentialToFile() == 0 && objLicensesAndCertVO.getCredSize() ==0)
			getActivityRegistryDao().updateActivityStatus(String.valueOf(objLicensesAndCertVO.getVendorId()), ActivityRegistryConstants.LICENSE, ActivityRegistryConstants.ACTIVITY_STATUS_INCOMPLETE);
		else
			getActivityRegistryDao().updateActivityStatus(String.valueOf(objLicensesAndCertVO.getVendorId()), ActivityRegistryConstants.LICENSE);
	} catch (Exception ex) {			
		ex.printStackTrace();
	}				
	
}

/* (non-Javadoc)
 * @see com.newco.marketplace.persistence.iDao.ILicensesDao#get(com.newco.marketplace.vo.LicensesVO)
 */
public LicensesAndCertVO get(LicensesAndCertVO objLicensesAndCertVO) {
	// ADD IBATIS CODE HERE from Vendor Policy table		
	try {
		objLicensesAndCertVO = (LicensesAndCertVO)getSqlMapClient().queryForObject("licenses.loadAddLicenseToFileData", objLicensesAndCertVO);
	} catch (Exception ex) {			
		ex.printStackTrace();
	}		
	return objLicensesAndCertVO;
}
/**
 * 
 */
public List getVendorCredentialsList(int vendorId) throws DBException {
	
	List credentialList=null;
		try {

			
			credentialList =  getSqlMapClient().queryForList("viewVendorCredential.query", vendorId);   

						
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @LicensesAndCertDaoImpl.getVendorCredentialsList() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LicensesAndCertDaoImpl.getVendorCredentialsList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @LicensesAndCertDaoImpl.getVendorCredentialsList() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @LicensesAndCertDaoImpl.getVendorCredentialsList() due to "
							+ ex.getMessage());
		}

		return credentialList;
	}
/**
 * 
 */
public List getVendorCredentialsInsurance(int vendorId) throws DBException {
	
	List credentialList=null;
		try {

			
			credentialList =  getSqlMapClient().queryForList("getVendorCredentialInsurance.query", vendorId);   

						
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @LicensesAndCertDaoImpl.getVendorCredentialsInsurance() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LicensesAndCertDaoImpl.getVendorCredentialsInsurance() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @LicensesAndCertDaoImpl.getVendorCredentialsInsurance() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @LicensesAndCertDaoImpl.getVendorCredentialsInsurance() due to "
							+ ex.getMessage());
		}

		return credentialList;
	}
//Method to sort hashMap
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
    return sortedMap;
}

public Boolean isVendorCredentialIdExist(LicensesAndCertVO objLicensesAndCertVO) throws DBException  {

	try {
		Object obj = getSqlMapClient().queryForObject("vendorCred.isAvailable", objLicensesAndCertVO);
		return (Boolean) obj;
		
	}  catch (SQLException ex) {
		logger
				.info("SQL Exception @CredentialDaoImpl.isVendorCredentialIdExist() due to"
						+ StackTraceHelper.getStackTrace(ex));
		throw new DBException(
				"SQL Exception @CredentialDaoImpl.isVendorCredentialIdExist() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		logger
				.info("General Exception @CredentialDaoImpl.isVendorCredentialIdExist() due to"
						+ StackTraceHelper.getStackTrace(ex));
		throw new DBException(
				"General Exception @CredentialDaoImpl.isVendorCredentialIdExist() due to",
				ex);
	}
}


//To update vendor credential
public LicensesAndCertVO updateLicense(LicensesAndCertVO objLicensesAndCertVO) throws DBException {
	
	if(objLicensesAndCertVO.getCategoryId() == -1)
		objLicensesAndCertVO.setCategoryId(0);
	if(objLicensesAndCertVO.getCredentialTypeId()== -1)
		objLicensesAndCertVO.setCategoryId(0);	
		
try {
	if(objLicensesAndCertVO.getVendorCredId()>0)
	{
		getSqlMapClient().update("updateLicenses.update",
				objLicensesAndCertVO);

	}
	getActivityRegistryDao().updateActivityStatus(String.valueOf(objLicensesAndCertVO.getVendorId()), ActivityRegistryConstants.LICENSE);
} catch (Exception ex) {
	logger.info("[LicensesAndCertDaoImpl.updateLicense - Exception] "
			+ ex.getStackTrace());
}
	return objLicensesAndCertVO;
}

}
