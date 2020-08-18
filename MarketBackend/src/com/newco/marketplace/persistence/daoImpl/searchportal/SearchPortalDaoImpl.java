/**
 *
 */
package com.newco.marketplace.persistence.daoImpl.searchportal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.searchportal.ISearchPortalDao;
import com.newco.marketplace.vo.apiSearch.FirmResponseData;
import com.newco.marketplace.vo.apiSearch.ProviderResponseData;
import com.newco.marketplace.vo.apiSearch.SearchProviderRequestCriteria;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.searchportal.SearchPortalBuyerVO;
import com.newco.marketplace.vo.searchportal.SearchPortalProviderFirmVO;
import com.newco.marketplace.vo.searchportal.SearchPortalServiceProviderVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author hoza
 *
 */
public class SearchPortalDaoImpl extends ABaseImplDao implements ISearchPortalDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.searchportal.ISearchPortalDao#searchBuyer(com.newco.marketplace.vo.searchportal.SearchPortalBuyerVO)
	 */
	public List<SearchPortalBuyerVO> searchBuyer(SearchPortalBuyerVO filter)
			throws Exception {


		try {
			List<SearchPortalBuyerVO>  result = new ArrayList<SearchPortalBuyerVO>();
			if(!filter.getIsChildResultNeeded().booleanValue()) {
				result = (List<SearchPortalBuyerVO> )queryForList("searchPortalbuyerOnly.query", filter);
			}
			else {  result  = (List<SearchPortalBuyerVO> )queryForList("searchPortalbuyer.query", filter); }
			return result;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @searchBuyer due to "
							+ exception.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.searchportal.ISearchPortalDao#searchProviderFirm(com.newco.marketplace.vo.searchportal.SearchPortalProviderFirmVO)
	 */
	public List<SearchPortalProviderFirmVO> searchProviderFirm(
			SearchPortalProviderFirmVO filter) throws Exception {
		try {
			List<SearchPortalProviderFirmVO>  result  = (List<SearchPortalProviderFirmVO> )queryForList("searchPortalProviderFirm.query", filter);
			return result;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @searchProviderFirm due to "
							+ exception.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.searchportal.ISearchPortalDao#searchServiceProvider(com.newco.marketplace.vo.searchportal.SearchPortalServiceProviderVO)
	 */
	public List<SearchPortalServiceProviderVO> searchServiceProvider(
			SearchPortalServiceProviderVO filter) throws Exception {

		try {
			List<SearchPortalServiceProviderVO>  result  = (List<SearchPortalServiceProviderVO> )queryForList("searchPortalServicePro.query", filter);
			return result;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @searchServiceProvider due to "
							+ exception.getMessage());
		}
	}
	
	/* Search provider firms based on the input criteria
	 * @param SearchProviderRequestCriteria
	 * @return List<FirmResponseData>
	 */
	public List<FirmResponseData> searchProviderFirmFromAPI(SearchProviderRequestCriteria searchCriteria) throws Exception{
		List<FirmResponseData> firmResponseList = new ArrayList<FirmResponseData>();
		try{
			firmResponseList = (List<FirmResponseData>)queryForList("searchProviderFirmFromAPI.query", searchCriteria);
		}catch (Exception exception) {
			throw new DataServiceException("General Exception @searchServiceProvider due to " + exception.getMessage());
		}		
		return firmResponseList;
	}
	
	public boolean updateServiceProviderName(
			String fName,String mName,String lName,String resourceId) throws Exception {
		try {
			//List<SearchPortalProviderFirmVO>  result  = (List<SearchPortalProviderFirmVO> )queryForList("searchPortalProviderFirm.query", filter);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("fName", fName);
			map.put("mName", mName);
			map.put("lName", lName);
			map.put("resourceId", resourceId);
			logger.info("Going to update provider Names");
			getSqlMapClientTemplate().update("updateServiceProviderName.query", map);
			logger.info("Provider Names Updated");
			
			return true;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @updateServiceProviderName due to "
							+ exception.getMessage());
		}
	}
	
	public boolean insertServiceProviderName_Audit(
			String oldName,String newName,String resouceId,String type,String modified_by) throws Exception {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("oldName", oldName);
			map.put("newName", newName);
			map.put("resource_Id", resouceId);
			map.put("type", type);
			map.put("modified_by", modified_by);
			logger.info("Going to insert the audit record");
			getSqlMapClientTemplate().insert("insertServiceProviderName_Audit.query", map);
			logger.info("Provider Audit Record Inserted");
			return true;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @insertServiceProviderName_Audit due to "
							+ exception.getMessage());
		}
	}
	
	/* Search service providers based on the input criteria
	 * @param SearchProviderRequestCriteria
	 * @return List<FirmResponseData>
	 */
	public List<ProviderResponseData> searchServiceProviderFromAPI(SearchProviderRequestCriteria searchCriteria) throws Exception{
		List<ProviderResponseData> providerResponseList = new ArrayList<ProviderResponseData>();
		try{
			providerResponseList = (List<ProviderResponseData>)queryForList("searchServiceProvidersFromAPI.query", searchCriteria);
		}catch (Exception exception) {
			throw new DataServiceException("General Exception @searchServiceProvider due to " + exception.getMessage());
		}		
		return providerResponseList;
	}
	
	// SLT-976
	public GeneralInfoVO getProviderDetails(GeneralInfoVO generalInfoVO) throws DBException {
		try {
			generalInfoVO = (GeneralInfoVO) getSqlMapClient().queryForObject("searchServiceProvider.vendorResource.get",
					generalInfoVO);
		} catch (SQLException ex) {
			logger.info("SQL Exception @SearchPortalDaoImpl.getProviderDetails() due to" + ex.getMessage());
			throw new DBException("SQL Exception @SearchPortalDaoImpl.getProviderDetails() due to " + ex.getMessage());
		} catch (Exception ex) {
			logger.info("General Exception @SearchPortalDaoImpl.getProviderDetails() due to" + ex.getMessage());
			throw new DBException(
					"General Exception @SearchPortalDaoImpl.getProviderDetails() due to " + ex.getMessage());
		}

		return generalInfoVO;
	}

}
