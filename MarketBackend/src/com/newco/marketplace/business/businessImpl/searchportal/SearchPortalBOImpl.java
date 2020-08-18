/**
 *
 */
package com.newco.marketplace.business.businessImpl.searchportal;

import java.util.ArrayList;
import java.util.List;
import com.newco.marketplace.business.iBusiness.searchportal.ISearchPortalBO;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.searchportal.ISearchPortalDao;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.apiSearch.FirmResponseData;
import com.newco.marketplace.vo.apiSearch.ProviderResponseData;
import com.newco.marketplace.vo.apiSearch.SearchProviderRequestCriteria;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.searchportal.SearchPortalBuyerVO;
import com.newco.marketplace.vo.searchportal.SearchPortalProviderFirmVO;
import com.newco.marketplace.vo.searchportal.SearchPortalServiceProviderVO;

/**
 * @author hoza
 *
 */
public class SearchPortalBOImpl implements ISearchPortalBO {

	private  ISearchPortalDao searchPortalDao;
	private IContactDao iContactDao;
	private static int BG_STATE_ID = 7;


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.searchportal.SearchPortalBO#searchBuyer(com.newco.marketplace.vo.searchportal.SearchPortalBuyerVO)
	 */
	public List<SearchPortalBuyerVO> searchBuyer(SearchPortalBuyerVO filter)
			throws Exception {

		return searchPortalDao.searchBuyer(filter);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.searchportal.SearchPortalBO#searchProviderFirm(com.newco.marketplace.vo.searchportal.SearchPortalProviderFirmVO)
	 */
	public List<SearchPortalProviderFirmVO> searchProviderFirm(
			SearchPortalProviderFirmVO filter) throws Exception {
		return searchPortalDao.searchProviderFirm(filter);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.searchportal.SearchPortalBO#searchServiceProvider(com.newco.marketplace.vo.searchportal.SearchPortalServiceProviderVO)
	 */
	public List<SearchPortalServiceProviderVO> searchServiceProvider(
			SearchPortalServiceProviderVO filter) throws Exception {
		return searchPortalDao.searchServiceProvider(filter);
	}
	
	/* Search provider firms based on the input criteria
	 * @param SearchProviderRequestCriteria
	 * @return List<FirmResponseData>
	 */
	public List<FirmResponseData> searchProviderFirmFromAPI(SearchProviderRequestCriteria searchCriteria) throws Exception{
		//List<FirmResponseData> firmResponseList = new ArrayList<FirmResponseData>();		
		return searchPortalDao.searchProviderFirmFromAPI(searchCriteria);
	}
	
	/* Search service provider based on the input criteria
	 * @param SearchProviderRequestCriteria
	 * @return List<ProviderResponseData>
	 */
	public List<ProviderResponseData> searchServiceProviderFromAPI(SearchProviderRequestCriteria searchCriteria) throws Exception{
		return searchPortalDao.searchServiceProviderFromAPI(searchCriteria);
	}

	/**
	 * @return the searchPortalDao
	 */
	public ISearchPortalDao getSearchPortalDao() {
		return searchPortalDao;
	}

	/**
	 * @param searchPortalDao the searchPortalDao to set
	 */
	public void setSearchPortalDao(ISearchPortalDao searchPortalDao) {
		this.searchPortalDao = searchPortalDao;
	}
	//SL-18330 -- Changes done for firstname/lastname
	public boolean updateServiceProviderName(
			String fName,String mName,String lName,String resourceId) throws Exception {
		return searchPortalDao.updateServiceProviderName(fName, mName, lName, resourceId);
}
	
public boolean insertServiceProviderName_Audit(
		String oldName,String newName,String resouceId,String type,String modified_by) throws Exception {
		return searchPortalDao.insertServiceProviderName_Audit(oldName,newName,resouceId,type,modified_by);
}

	//SLT-976
	public IContactDao getiContactDao() {
		return iContactDao;
	}

	public void setiContactDao(IContactDao iContactDao) {
		this.iContactDao = iContactDao;
	}
	
	public GeneralInfoVO getProviderDetails(GeneralInfoVO provider) throws Exception {
		return searchPortalDao.getProviderDetails(provider);
	}
	
	public Integer getBackgroundCheckIdWithSameContact(GeneralInfoVO provider) throws Exception{
		return iContactDao.getBackgroundCheckDetailsWithSameContact(provider);
	}
	
	public GeneralInfoVO getProviderDetailsWithSameContact(GeneralInfoVO provider) throws Exception{
		return iContactDao.getProviderDetailsWithSameContact(provider);
	}
	
	public Integer getBackgroundCheckInfo(Integer bcCheckId)throws Exception{
		return iContactDao.getBackgroundCheckInfo(bcCheckId).getBackgroundCheckStateId();
	}
	
	public void updateBackGroundCheckDetails(String resourceId, String resourceSsn) throws Exception {
		Integer backgroundCheckId = insertPlusOneKeyForBackgroundCheck(resourceSsn, resourceId);
		if (null != backgroundCheckId) {
			GeneralInfoVO generalInfo = new GeneralInfoVO();
			generalInfo.setBcCheckId(backgroundCheckId);
			generalInfo.setResourceId(resourceId);
			generalInfo.setBcStateId(BG_STATE_ID);
			iContactDao.updateBcCheck(generalInfo);
		}
	}
	
	private Integer insertPlusOneKeyForBackgroundCheck(String ssn, String resourceID) throws Exception {
		if (ssn != null && ssn.trim().length() > 0 && resourceID != null && resourceID.trim().length() > 0) {
			String plusOne = resourceID.trim() + ssn.trim();
			GeneralInfoVO backgroundInfo = new GeneralInfoVO();
					// R12_2
					// SL-20553
			backgroundInfo.setResourceId(resourceID);
			backgroundInfo.setPlusOneKey(CryptoUtil.encryptKeyForSSNAndPlusOne(plusOne));
			Integer bcCheckId = iContactDao.insertBcCheck(backgroundInfo);
			return bcCheckId;
		}
		return null;
	}
			
	public void updateBGCheck_details(String resourceId, Integer bcCheckId, Integer bgCheckStateId) throws Exception {
		GeneralInfoVO generalInfo = new GeneralInfoVO();
		generalInfo.setBcCheckId(bcCheckId);
		generalInfo.setResourceId(resourceId);
		generalInfo.setBcStateId(bgCheckStateId);
		iContactDao.updateBcCheck(generalInfo);

	}
			
	public void updateBGStateInfo(Integer bcCheckId, Integer bgCheckStateId) throws Exception {
		GeneralInfoVO generalInfo = new GeneralInfoVO();
		generalInfo.setBcCheckId(bcCheckId);
		generalInfo.setBcStateId(bgCheckStateId);
		iContactDao.updateBGStateInfo(generalInfo);

	}

	public Integer getBGCheckId(String resourceId) throws Exception {
		return iContactDao.getBcCheckId(resourceId);
	}

}
