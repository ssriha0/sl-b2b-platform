package com.newco.marketplace.web.delegates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.BuyerSOTemplateForSkuDTO;
import com.newco.marketplace.dto.vo.BuyerSOTemplateForSkuVO;
import com.newco.marketplace.dto.vo.BuyerSkuCategoryVO;
import com.newco.marketplace.dto.vo.BuyerSkuTaskForSoVO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DocumentForSkuVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.LuServiceTypeTemplateVO;
import com.newco.marketplace.dto.vo.SkillTreeForSkuVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNetTierReleaseVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;
import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;

public interface ILookupDelegate {

	public ArrayList<LuProviderRespReasonVO> getLuProviderRespReason(LuProviderRespReasonVO luReasonVO)
			throws BusinessServiceException;
	public List<LookupVO> getLuAutocloseRules() throws BusinessServiceException;
	public ArrayList<SkillNodeVO> getSkillTreeMainCategories() throws BusinessServiceException;
	public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(Integer selectedId) 
																throws BusinessServiceException;
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId,String stateCd) throws BusinessServiceException;
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(Integer selectedId,String buyerId,String stateCd) 
																throws BusinessServiceException;

	/**
	 * Returns List of states.  If the list cannot be found an empty list will
	 * be returned
	 * @return
	 */
	public List<LookupVO> getStateCodes();
	public ArrayList<LookupVO> getPercentOwnedList()throws BusinessServiceException;
	public ArrayList<LuBuyerRefVO> getBuyerRef(String entityId)throws BusinessServiceException;
	public ArrayList<LookupVO> getPhoneTypes()throws BusinessServiceException;
	public ArrayList<LookupVO> getAccountTypeList()throws BusinessServiceException;
	public ArrayList<LookupVO> getCreditCardTypeList()throws BusinessServiceException;
	public ArrayList<LookupVO> getShippingCarrier() throws BusinessServiceException;
	
	public boolean getIfZipInServiceBlackout(Integer nodeId, String zip) throws BusinessServiceException;
	public LocationVO checkIfZipISValid(String zip) throws BusinessServiceException;
	
	public ArrayList<LookupVO> getLanguages() throws BusinessServiceException;
	public ArrayList<LookupVO> getCredentials() throws BusinessServiceException;
	public ArrayList<LookupVO> getCredentialCategory(Integer credentialType) throws BusinessServiceException;
	public TermsAndConditionsVO getTermsConditionsContent() throws DataServiceException;
	public TermsAndConditionsVO getTermsConditionsContent(String termsAndCond)throws DataServiceException;
	public String getDaylightSavingsFlg(String zip) throws BusinessServiceException;
	public ArrayList<LookupVO> getCredCategoryForBuyer(Map<String,Integer> hm) throws BusinessServiceException;
	
	//E-Wallet Enhancement
	public String getStateRegulationNote(Integer reasonCodeId);
	public String getIRSlevyNote(Integer reasonCodeId);
	public String getLegalHoldNote(Integer reasonCodeId);
	
	// Admin Search Criteria
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
	
	public ArrayList<LookupVO> getTransferSLBucksReasonCodes() throws BusinessServiceException;
	
	public String getStateForZip(String zipCode) throws BusinessServiceException;
	
	public List<SPNMonitorVO> getSPNetList(Integer buyerId) throws BusinessServiceException;
	public List<LookupVO> getPerformanceLevelList() throws BusinessServiceException;
	
	public List<LookupVO> getMinimumRatings() throws BusinessServiceException;
	
	public List<LookupVO> getProviderDistanceList() throws BusinessServiceException;
	
	public List<LookupVO> getProviderTopSelectList() throws BusinessServiceException;
	
	public ArrayList<LookupVO> getPartStatus()	throws BusinessServiceException;
	
	//to check whether buyer has permission to view prov docs
	public boolean getViewDocPermission(String userName) throws BusinessServiceException;
	
	/**
	 * This method retrieves parent node id for a subCategoryId.
	 * @param Integer subCategoryId
	 * @return Integer
	 * throws DelegateException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws DelegateException;
	public List<LookupVO> getSkills() throws DelegateException;
	/**
	 * This method is used to fetch all markets by startindex and endindex
	 */
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex);
	/**
	 * This method retrieves ratings for insurance.
	 * @return InsuranceRatingsLookupVO
	 * throws DelegateException
	 */
	public InsuranceRatingsLookupVO getInsuranceRatings() throws DelegateException;
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param buyerId,source
	 * @return List<LookupVO>
	 * @throws DelegateException
	 */
	public List<LookupVO> retrieveLookUpDocumentByBuyerId(Integer buyerId, Integer source)throws DelegateException;
	/**
	 * This method retrieves category id for a buyerId.
	 * @param Integer buyerId
	 * @return List
	 * throws DelegateException
	 */
	public List<BuyerSkuCategoryVO> fetchBuyerSkuCategories(Integer buyerId) throws DelegateException;
	/**
	 * This method retrieves sku name for a categoryid.
	 * @param Integer buyerId
	 * @return List
	 * throws DelegateException
	 */
	public List<BuyerSkuVO>fetchBuyerSkuNameByCategory(Integer categoryId) throws DelegateException;
	/**
	 * This method retrieves sku details for a skuId.
	 * @param Integer skuId
	 * @return BuyerSkuTaskForSoVO
	 * throws DelegateException
	 */
	public BuyerSkuTaskForSoVO fetchBuyerSkuDetailBySkuId(Integer skuId)throws DelegateException;
	/**
	 * This method retrieves bid price of the selected skuId.
	 * @param Integer skuId
	 * @return BuyerSkuTaskForSoVO
	 * throws DelegateException
	 */
	public Double fetchBidPriceBySkuId(Integer skuId)throws DelegateException;
	/**
	 * This method retrieves template details for a serviceTypeTemplateId for a particular sku id.
	 * @param Integer serviceTypeTemplateId
	 * @return LuServiceTypeTemplateVO
	 * throws DelegateException
	 */
	
	public LuServiceTypeTemplateVO fetchServiceTypeTemplate(Integer serviceTypeTemplateId) throws DelegateException;
	/**
	 * This method retrieves skill tree details for a  particular node id.
	 * @param Integer nodeId
	 * @return SkillTreeForSkuVO
	 * throws DelegateException
	 */
	public SkillTreeForSkuVO fetchSkillTreeDetailBySkuId(Integer nodeId) throws DelegateException;
	/**
	 * This method retrieves template details for a  particular template Id.
	 * @param Integer templateId
	 * @return BuyerSOTemplateDTO
	 * throws DelegateException
	 */
	public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) throws DelegateException;
	public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId) throws DelegateException;
	public List<SPNetTierReleaseVO> fetchRoutingPriorities(String spnId);
	/**
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public String getLeadGenFeesLink(Integer vendorId) throws DataServiceException;
	public Integer checkLegalHoldPermission(String userName) throws BusinessServiceException;
}