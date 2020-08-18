package com.newco.marketplace.business.iBusiness.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.BuyerSOTemplateForSkuVO;
import com.newco.marketplace.dto.vo.BuyerSkuCategoryVO;
import com.newco.marketplace.dto.vo.BuyerSkuTaskForSoVO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
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
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;


public interface ILookupBO {

	public ArrayList<LuProviderRespReasonVO> getProviderRespReason(LuProviderRespReasonVO request) throws DataServiceException ;
	public List<LookupVO> getLuAutocloseRules() throws DataServiceException;
	public ArrayList<SkillNodeVO> getSkillTreeMainCategories() throws DataServiceException;
	public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(Integer selectedId) throws DataServiceException;
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId,String stateCd) throws DataServiceException;
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(Integer selectedId,String buyerId,String stateCd) throws DataServiceException;
	public List<ServiceTypesVO> getNotBlackedOutServiceTypes(Integer selectedId,String buyerId,String stateCd) throws DataServiceException;

	/**
	 * Returns List of states.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO>  getStateCodes();
	public ArrayList<LookupVO>  getPercentOwnedList() throws DataServiceException ;
	public ArrayList<LookupVO>  getAccountTypeList() throws DataServiceException ;
	public ArrayList<LookupVO>  getCreditCardTypeList() throws DataServiceException ;
	public ArrayList<LookupVO> getPhoneTypes() throws DataServiceException ;
	public ArrayList<LookupVO> getShippingCarrier() throws DataServiceException;
	public boolean getIfZipInServiceBlackout(Integer nodeId, String zip) throws DataServiceException;
	public LocationVO checkIFZipExists(String zip) throws DataServiceException;
	public ArrayList<LuBuyerRefVO> getBuyerRef(String buyerId) throws DataServiceException;
	public LuBuyerRefVO getBuyerRef(String buyerId,String refType) throws DataServiceException;
	public ArrayList<LookupVO> getLanguages() throws DataServiceException;
	public ArrayList<LookupVO> getCredentials() throws DataServiceException;
	public ArrayList<LookupVO> getCredentialCategory(Integer credentialType) throws DataServiceException;
	public TermsAndConditionsVO getTermsConditionsContent() throws DataServiceException;
	public TermsAndConditionsVO getTermsConditionsContent(String termsAndCond) throws DataServiceException;
	public List<LookupVO> getCompanyRoles(Integer roleId) throws DataServiceException;
	public String getDaylightSavingsFlg(String zip) throws DataServiceException;
	public ArrayList<LookupVO> getCredCategoryForBuyer(Map<String, Integer> hm)throws DataServiceException;
	public List<LookupVO> getBusinessTypes() throws Exception;
	public List<LookupVO>  loadProviderPrimaryIndustry();
	public List<LookupVO>  loadWorkFlowStatusForEntity(String entity);
	public List<LookupVO>  getReferral();
	public List<LookupVO>  getPerformanceLevels() throws DataServiceException;
	//E-Wallet Enhancement
	public String getStateRegulationNote(Integer reasonCodeId);
	public String getIRSlevyNote(Integer reasonCodeId);
	public String getLegalHoldNote(Integer reasonCodeId);
	// Checking User Permission : Legal Hold To Login User
	public Integer checkLegalHoldPermission(String adminUserName);


	public String getZipTimezone(String zip);
	/**
	 * Returns List of all ServiceLive Markets.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getMarkets();
	/**
	 * Returns List of all ServiceLive Districts.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getDistricts();
	/**
	 * Returns List of all ServiceLive Regions.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getRegions();
	/**
	 * Returns List of all ServiceLive Provider Firm Statuses.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getProviderFirmStatuses();
	/**
	 * Returns List of all ServiceLive Auditable Items.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getAuditableItems();
	/**
	 * Returns List of all ServiceLive Background Statuses.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getBackgroundStatuses();
	/**
	 * Returns List of all ServiceLive Provider Networks.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getProviderNetworks();
	/**
	 * Returns List of all ServiceLive Primary Skills.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getPrimarySkills();

	public List<LookupVO> getEntityStatusList(String entityType)throws BusinessServiceException;

	public List<LookupVO> loadReasonCodeList(LookupVO lookupVO)throws BusinessServiceException;

	public List<LookupVO> getReasonCodeListByTaskId(LookupVO lookup)throws BusinessServiceException;

	public ArrayList<LookupVO> getTransferSLBucksReasonCodes() throws DataServiceException;
	
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
	 * throws BusinessServiceException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws BusinessServiceException;
	/**
	 * This method retrieves skills from lu_service_type_template table.	 
	 * @return List
	 * throws DataServiceException
	 */

	public ArrayList<LookupVO> getServiceTypeTemplates() throws BusinessServiceException;
	/**
	 * This method is used to fetch all markets by startindex and endindex
	 */
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex);
	
	public List<LookupVO> getSubStatusList(Integer statusId);
	public List<LookupVO>  getNotBlackedOutStateCodes();
	public int getPostedStatus(String soId);
	/**
	 * This method retrieves ratings for insurance.
	 * @return InsuranceRatingsLookupVO
	 * throws BusinessServiceException
	 */
	public InsuranceRatingsLookupVO getInsuranceRatings() throws BusinessServiceException;
	
	public List<String> getCountryList();
	
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<BuyerDocumentTypeVO>
	 * @throws BusinessServiceException
	 */
	
	public List<LookupVO> retrieveLookUpDocumentByBuyerId(Integer buyerId, Integer source) throws BusinessServiceException;
	/**
	 * This method retrieves category id for a buyerId.
	 * @param Integer buyerId
	 * @return List
	 * throws BusinessServiceException
	 */
	public List<BuyerSkuCategoryVO> fetchBuyerSkuCategories(Integer buyerId)throws BusinessServiceException;
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
	 * throws BusinessServiceException
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
	 * @return BuyerSOTemplateForSkuVO
	 * throws DelegateException
	 */
	public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) throws BusinessServiceException;
	public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId) throws BusinessServiceException;
	//to check whether buyer has permission to view prov docs
	public boolean getViewDocPermission(String userName) throws BusinessServiceException;
	/**
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public String getLeadGenFeesLink(Integer vendorId) throws DataServiceException;
	
	public ServiceOrder fetchSODetails(String soId) throws DataServiceException;
}
