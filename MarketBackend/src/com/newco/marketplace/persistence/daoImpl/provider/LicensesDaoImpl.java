/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.persistence.iDao.provider.ILicensesDao;
import com.newco.marketplace.vo.provider.LicensesVO;

/**
 * @author MTedder
 *
 */
public class LicensesDaoImpl extends SqlMapClientDaoSupport implements ILicensesDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ILicensesDao#delete(com.newco.marketplace.vo.LicensesVO)
	 */
	public void delete(LicensesVO licensesVO) {
		
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
			
			getSqlMapClient().delete("licenses.deletegeAddLicenseToFileData", licensesVO);
			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ILicensesDao#get(com.newco.marketplace.vo.LicensesVO)
	 */
	public LicensesVO get(LicensesVO licensesVO) {
		// ADD IBATIS CODE HERE from Vendor Policy table		
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
			
			licensesVO = (LicensesVO)getSqlMapClient().queryForObject("licenses.loadAddLicenseToFileData", licensesVO);			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}		
		return licensesVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ILicensesDao#update(com.newco.marketplace.vo.LicensesVO)
	 */
	public void update(LicensesVO licensesVO) {
		
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
			getSqlMapClient().update("licenses.updateAddLicenseToFileData", licensesVO);
			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}				
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ILicensesDao#insert(com.newco.marketplace.vo.LicensesVO)
	 */
	public void insert(LicensesVO licensesVO) {
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
			getSqlMapClient().insert("licenses.insertAddLicenseToFileData", licensesVO);
			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}	
	}
}
