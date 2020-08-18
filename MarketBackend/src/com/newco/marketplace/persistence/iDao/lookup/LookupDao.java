package com.newco.marketplace.persistence.iDao.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.BuyerSOTemplateForSkuDTO;
import com.newco.marketplace.dto.vo.BuyerSOTemplateForSkuVO;
import com.newco.marketplace.dto.vo.BuyerSkuCategoryVO;
import com.newco.marketplace.dto.vo.BuyerSkuTaskForSoVO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DocumentForSkuVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.LuServiceTypeTemplateVO;
import com.newco.marketplace.dto.vo.SkillTreeForSkuVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * $Revision: 1.27 $ $Author: pjoy0 $ $Date: 2008/06/08 16:15:36 $
 */

/*
 * Maintenance History: See bottom of file
 */
public interface LookupDao {
	
	public ArrayList<LuProviderRespReasonVO> getProviderRespReason(LuProviderRespReasonVO luProviderRespReasonVO) throws DataServiceException;
	public List<LookupVO> getLuAutocloseRules() throws DataServiceException;
	public ArrayList<SkillNodeVO> getSkillTreeMainCategories() throws DataServiceException;
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId,String stateCd) throws DataServiceException;
	public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(Integer selectedId)throws DataServiceException;
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(Integer selectedId,String buyerId,String stateCd)throws DataServiceException;
	public List<ServiceTypesVO> getNotBlackedOutServiceTypes(Integer selectedId,String buyerId,String stateCd)throws DataServiceException;

	public List<LookupVO> getStateCodes() throws DataServiceException;
	public ArrayList<LookupVO> getPercentOwnedList() throws DataServiceException;
	public ArrayList<LookupVO> getPhoneTypes() throws DataServiceException;
	public ArrayList<LookupVO> getShippingCarrier() throws DataServiceException;
	public HashMap getServiceBlackout(Integer nodeId, String zip) throws DataServiceException;
	public ArrayList<LuBuyerRefVO> getBuyerRef(String buyerId)throws DataServiceException;
	public LuBuyerRefVO getBuyerRef(String buyerId,String refType)throws DataServiceException;
	public ArrayList<LookupVO> getCredentials() throws DataServiceException;
	public ArrayList<LookupVO> getLanguages() throws DataServiceException;
	public ArrayList<LookupVO> getCredentialCategory(Integer credentialType) throws DataServiceException;
	public TermsAndConditionsVO getTermsConditionsContent() throws DataServiceException;
	public TermsAndConditionsVO getTermsConditionsContent(String termsAndCond) throws DataServiceException;
	/**
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public String getLeadGenFeesLink(Integer vendorId) throws DataServiceException;
	public LocationVO getZipCodeEntry(String zip) throws DataServiceException;
	
	public List<LookupVO> getMarkets() throws DataServiceException;
	public List<LookupVO> getDistricts() throws DataServiceException;
	public List<LookupVO> getRegions() throws DataServiceException;
	public List<LookupVO> getProviderFirmStatuses() throws DataServiceException;
	public List<LookupVO> getAuditableItems() throws DataServiceException;
	public List<LookupVO> getBackgroundStatuses() throws DataServiceException;
	public List<LookupVO> getProviderNetworks() throws DataServiceException;
	public List<LookupVO> getPrimarySkills() throws DataServiceException;
	public String getDaylightSavingsFlg(String zip) throws DataServiceException;
	public ArrayList<LookupVO> getCredCategoryForBuyer(Map<String, Integer> hm) throws DataServiceException;
	/**
	 * getTimeIntervals returns a sorted list (by sort_order) of time intervals from the 
	 * lu_time_interval table. All rows are returned
	 * @return
	 * @throws DataServiceException
	 */
	public List<LookupVO> getTimeIntervals() throws DataServiceException;
	
//	public Long getNextIdentifierFromFulfillmentEntryId(Long fulfillmentEntryId);
//	public Integer getNextIdentifier(String key);	
	
	public List<LookupVO> loadCompanyRole(Integer roleId)throws DBException;
	
	public List<LookupVO> getEntityStatusList(String entityType)throws DataServiceException;
	
	/**
	 * getSPNStatuses returns a sorted list select provider networks
	 * @return
	 * @throws DataServiceException
	 */
	public List<LookupVO> getSPNStatuses() throws DataServiceException;
	public List<LookupVO> getPerformanceLevels() throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public LookupVO getLanguageById(Integer languageId) throws DataServiceException;
	public String getZipTimeZone(String zip) throws DataServiceException;
	public String getStateForZip(String zipCode) throws DataServiceException;
	
	public List<LookupVO> getMinimumRatings() throws DataServiceException;
	
	public List<LookupVO> getResourceDistList() throws DataServiceException;
	
	public List<LookupVO> getProviderTopSelectList() throws DataServiceException;

	public List<LookupVO> getTaxPayerTypeIdList() throws DataServiceException;
	
	public ArrayList<LookupVO> getPartStatus() throws DataServiceException;
	
	/**
	 * This method retrieves parent node id for a subCategoryId.
	 * @param Integer subCategoryId
	 * @return Integer
	 * throws DataServiceException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws DataServiceException;
	/**
	 * This method retrieves skills from lu_service_type_template table.	 
	 * @return List
	 * throws DataServiceException
	 */
	public ArrayList<LookupVO> getServiceTypeTemplate() throws DataServiceException;
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex) throws DataServiceException;
	public List<LookupVO> getSubStatusList(Integer statusId) throws DataServiceException;
	public List<LookupVO> getNotBlackedOutStateCodes() throws DataServiceException;
	/**
	 * This method retrieves ratings for insurance.
	 * @return InsuranceRatingsLookupVO
	 * throws DataServiceException
	 */
	public List<LookupVO> getInsuranceRatings() throws DataServiceException;
	public List<String> getCountryList() throws DBException;
	/**
	 * Retrieves document types  by buyer id, to be listed in document manager
	 * @param buyerId,source
	 * @return List<LookupVO>
	 * @throws BusinessServiceException
	  */
	 public List<LookupVO> retrieveLookUpDocumentByBuyerId(Integer buyerId, Integer source) throws BusinessServiceException;
	 /**
		 * This method retrieves category id for a buyerId.
		 * @param Integer buyerId
		 * @return List
		 * throws BusinessServiceException
		 */
		public List<BuyerSkuCategoryVO> fetchBuyerSkuCategories(Integer buyerId) throws BusinessServiceException;
		/**
		 * This method retrieves sku name for a categoryid.
		 * @param Integer buyerId
		 * @return List
		 * throws BusinessServiceException
		 */
		public List<BuyerSkuVO>fetchBuyerSkuNameByCategory(Integer categoryId) throws BusinessServiceException;
		/**
		 * This method retrieves sku details for a skuId.
		 * @param Integer skuId
		 * @return BuyerSkuTaskForSoVO
		 * throws DelegateException
		 */
		public BuyerSkuTaskForSoVO fetchBuyerSkuDetailBySkuId(Integer skuId)throws BusinessServiceException;
		/**
		 * This method retrieves bid price of the selected skuId.
		 * @param Integer skuId
		 * @return BuyerSkuTaskForSoVO
		 * throws DelegateException
		 */
		public Double fetchBidPriceBySkuId(Integer skuId)throws BusinessServiceException;
		/**
		 * This method retrieves template details for a serviceTypeTemplateId for a particular sku id.
		 * @param Integer serviceTypeTemplateId
		 * @return LuServiceTypeTemplateVO
		 * throws BusinessServiceException
		 */
		public LuServiceTypeTemplateVO fetchServiceTypeTemplate(Integer serviceTypeTemplateId) throws BusinessServiceException;
		/**
		 * This method retrieves skill tree details for a  particular node id.
		 * @param Integer nodeId
		 * @return SkillTreeForSkuVO
		 * throws DelegateException
		 */
		public SkillTreeForSkuVO fetchSkillTreeDetailBySkuId(Integer nodeId) throws BusinessServiceException;
		/**
		 * This method retrieves template details for a  particular template Id.
		 * @param Integer templateId
		 * @return BuyerSOTemplateForSkuDTO
		 * throws DelegateException
		 */
		public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) throws BusinessServiceException;
		public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId) throws BusinessServiceException;
		//to check whether buyer has permission to view prov docs
		public boolean getViewDocPermission(String userName);
		
		public ServiceOrder fetchSODetails(String soId);
		
		//E-Wallet Enhancement
		public String getStateRegulationNote(Integer reasonCodeId);
		public String getIRSlevyNote(Integer reasonCodeId);
		public String getLegalHoldNote(Integer reasonCodeId);
		//Checking User Permission : Legal Hold To Login User
	    public Integer checkLegalHoldPermission(String adminUserName);
}
/*
 * Maintenance History
 * $Log: LookupDao.java,v $
 * Revision 1.27  2008/06/08 16:15:36  pjoy0
 * Fixed as per B2C issue 65: State and Zip fields on Join ServiceLive page is not pre-populated.
 *
 * Revision 1.26  2008/05/21 22:54:29  akashya
 * I21 Merged
 *
 * Revision 1.25.2.4  2008/05/21 07:31:10  pjoy0
 * Fixed as per Sears00053387:Sears Credential - Explore MP - returns no records
 *
 * Revision 1.25.2.3  2008/05/19 22:30:42  gjacks8
 * timezone changes
 *
 * Revision 1.25.2.2  2008/05/16 12:12:21  pjoy0
 * Fixed as per Sears00051266 and Sears00051228: Time zone Issues
 *
 * Revision 1.25.2.1  2008/05/15 20:52:46  glacy
 * AddedServiceTypeLookup
 *
 * Revision 1.25  2008/05/02 21:23:48  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.24  2008/04/26 00:40:38  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.19.12.3  2008/04/23 11:42:21  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.23  2008/04/23 07:04:53  glacy
 * Shyam: Re-merge of I19_FreeTab branch to HEAD.
 *
 * Revision 1.20.8.2  2008/04/10 21:49:00  mhaye05
 * updates
 *
 * Revision 1.20.8.1  2008/04/08 18:05:39  mhaye05
 * added getSPNStatuses() and corresponding sql in map
 *
 * Revision 1.20.6.1  2008/04/19 20:36:04  pvarkey
 * Checked in as part of Blackout story that is a part of SOW Free tabbing for Iteration 19.5.
 *
 * Revision 1.22  2008/04/23 05:02:12  hravi
 * Reverting to build 247.
 *
 * Revision 1.19.12.2  2008/04/01 21:52:58  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.19.12.1  2008/03/28 17:12:36  schavda
 * added getNextIdentifier() and resetIdentifierValue()
 *
 * Revision 1.19  2008/02/08 15:53:54  mhaye05
 * updated return types to be List and not ArrayList
 *
 */