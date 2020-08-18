package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO;
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
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;
import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;

public class LookupDelegateImpl implements ILookupDelegate { 
	private static final Logger logger = Logger
			.getLogger(LookupDelegateImpl.class.getName());

	private ILookupBO lookupBO = null;
	private ISPNetworkBO spnetworkBO;

	public ArrayList<LuProviderRespReasonVO> getLuProviderRespReason(
			LuProviderRespReasonVO luReasonVO) throws BusinessServiceException {
		// TODO Auto-generated method stub
		lookupBO = this.getLookupBO();
		ArrayList<LuProviderRespReasonVO> al = null;
		try {
			al = lookupBO.getProviderRespReason(luReasonVO);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error(e);
		}
		return al;
		// TODO convert from vo to dto
		// soDTOList = ObjectMapper.convertVOToDTO(searchList);
	}
	
	public List<LookupVO> getLuAutocloseRules() throws BusinessServiceException
	{
		List<LookupVO> autocloseRulesList= new ArrayList<LookupVO>();
		try{
		autocloseRulesList=lookupBO.getLuAutocloseRules();
		} catch (DataServiceException e) {
		e.printStackTrace();
		}
		return autocloseRulesList;
		
	}

	public ArrayList<SkillNodeVO> getSkillTreeMainCategories()
			throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList<SkillNodeVO>) getLookupBO()
					.getSkillTreeMainCategories();
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return al;
	}

	public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(
			Integer selectedId) throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList<SkillNodeVO>) getLookupBO()
					.getSkillTreeCategoriesOrSubCategories(selectedId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return al;
	}
	
	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId,String stateCd)
	throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList<SkillNodeVO>) getLookupBO()
					.getNotBlackedOutSkillTreeMainCategories(buyerId,stateCd);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return al;
	}

public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(
	Integer selectedId,String buyerId,String stateCd) throws BusinessServiceException {
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList<SkillNodeVO>) getLookupBO()
					.getNotBlackedOutSkillTreeCategoriesOrSubCategories(selectedId,buyerId,stateCd);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return al;
	}

	public List<LookupVO> getStateCodes() {
		return lookupBO.getStateCodes();
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getMarkets()
	 */
	public List<LookupVO> getMarkets() {
		return lookupBO.getMarkets();
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getDistricts()
	 */
	public List<LookupVO> getDistricts() {
		return lookupBO.getDistricts();
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getRegions()
	 */
	public List<LookupVO> getRegions() {
		return lookupBO.getRegions();
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getProviderFirmStatuses()
	 */
	public List<LookupVO> getProviderFirmStatuses() {
		return lookupBO.getProviderFirmStatuses();
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getAuditableItems()
	 */
	public List<LookupVO> getAuditableItems() {
		return lookupBO.getAuditableItems();
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getBackgroundStatuses()
	 */
	public List<LookupVO> getBackgroundStatuses() {
		return lookupBO.getBackgroundStatuses();
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getProviderNetworks()
	 */
	public List<LookupVO> getProviderNetworks() {
		return lookupBO.getProviderNetworks();
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ILookupDelegate#getPrimarySkills()
	 */
	public List<LookupVO> getPrimarySkills() {
		return lookupBO.getPrimarySkills();
	}
	
	
	
	public ArrayList<LookupVO> getPercentOwnedList()throws BusinessServiceException{
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getPercentOwnedList();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<LookupVO> getAccountTypeList() throws BusinessServiceException{
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getAccountTypeList();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<LookupVO> getCreditCardTypeList() throws BusinessServiceException{
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getCreditCardTypeList();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<LookupVO> getLanguages() throws BusinessServiceException {
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getLanguages();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;

	}

	public ArrayList<LookupVO> getCredentials() throws BusinessServiceException {
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getCredentials();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;

	}

	public ArrayList<LookupVO> getCredentialCategory(Integer credentialType)
			throws BusinessServiceException {
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getCredentialCategory(credentialType);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<LookupVO> getCredCategoryForBuyer(Map<String,Integer> hm) 
		throws BusinessServiceException {
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try{
		list = lookupBO.getCredCategoryForBuyer(hm);
		}catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<LookupVO> getPhoneTypes() throws BusinessServiceException {
		// TODO Auto-generated method stub
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getPhoneTypes();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;
		// TODO convert from vo to dto
		// soDTOList = ObjectMapper.convertVOToDTO(searchList);
	}

	public ArrayList<LookupVO> getShippingCarrier()
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> sc = null;
		try {
			sc = lookupBO.getShippingCarrier();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return sc;

	}

	public boolean getIfZipInServiceBlackout(Integer nodeId, String zip)
			throws BusinessServiceException {
		try {
			return lookupBO.getIfZipInServiceBlackout(nodeId, zip);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	public LocationVO checkIfZipISValid(String zip)
			throws BusinessServiceException {
		LocationVO locationVo = null;
		try {
			locationVo = lookupBO.checkIFZipExists(zip);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return locationVo;
	}
	public String getDaylightSavingsFlg(String zip)	throws BusinessServiceException {
		String dls = null;
		try {
			dls = lookupBO.getDaylightSavingsFlg(zip);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return dls;
	}
	public ArrayList<LuBuyerRefVO> getBuyerRef(String buyerId) {
		ArrayList<LuBuyerRefVO> bRef = new ArrayList<LuBuyerRefVO>();

		try {
			bRef = lookupBO.getBuyerRef(buyerId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bRef;
	}

	public List<LookupVO> getPerformanceLevelList() throws BusinessServiceException
	{
		List<LookupVO> list = null;
		
		try
		{
			list = lookupBO.getPerformanceLevels();
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public TermsAndConditionsVO getTermsConditionsContent(String termsAndCond)
			throws DataServiceException {

		return getLookupBO().getTermsConditionsContent(termsAndCond);
	}
	
	//SL-19293 Method to fetch the link for the lead gen. fees pdf.
	public String getLeadGenFeesLink(Integer vendorId)
			throws DataServiceException {
		return getLookupBO().getLeadGenFeesLink(vendorId);
	}

	public TermsAndConditionsVO getTermsConditionsContent()
			throws DataServiceException {
		return getLookupBO().getTermsConditionsContent();
	}

	public List<LookupVO> getEntityStatusList(String entityType) throws BusinessServiceException {
		return getLookupBO().getEntityStatusList(entityType);
	}

	public List<LookupVO> loadReasonCodeList(LookupVO lookupVO) throws BusinessServiceException {
		return getLookupBO().loadReasonCodeList(lookupVO);
	}
	
	public ArrayList<LookupVO> getTransferSLBucksReasonCodes() throws BusinessServiceException{
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> list = null;
		try {
			list = lookupBO.getTransferSLBucksReasonCodes();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getStateForZip(String zipCode) throws BusinessServiceException{
		logger.info("Inside LookupBoImpl.java");
		lookupBO = this.getLookupBO();
		String state="";
		try {
			state = lookupBO.getStateForZip(zipCode);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return state;
	}
	
	public List<SPNMonitorVO> getSPNetList(Integer buyerId) throws BusinessServiceException{
		return getSpnetworkBO().getSpnListForBuyer(buyerId);
	}
	
	public List<SPNetTierReleaseVO> fetchRoutingPriorities(String spnId){
		return getSpnetworkBO().fetchRoutingPriorities(spnId);
	}

	/**
	 * @return the spnetworkBO
	 */
	public ISPNetworkBO getSpnetworkBO() {
		return spnetworkBO;
	}

	/**
	 * @param spnetworkBO the spnetworkBO to set
	 */
	public void setSpnetworkBO(ISPNetworkBO spnetworkBO) {
		this.spnetworkBO = spnetworkBO;
	}

	public List<LookupVO> getMinimumRatings() throws BusinessServiceException {
		List<LookupVO> minimumRatingList = null;
		try {
			minimumRatingList = lookupBO.getMinimumRatings();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Exception occurred while getting list of minimum ratings");
		}
		return minimumRatingList;
		
	}

	public List<LookupVO> getProviderDistanceList() throws BusinessServiceException {
		List<LookupVO> resourceDistList = null;
		try {
			resourceDistList = lookupBO.getResourceDistList();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Exception occurred while getting list of provider distance list");
		}
		return resourceDistList;

	}

	public List<LookupVO> getProviderTopSelectList() throws BusinessServiceException {
		List<LookupVO> resourceDistList = null;
		try {
			resourceDistList = lookupBO.getProviderTopSelectList();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Exception occurred while getting list of top select for providers");
		}
		return resourceDistList;
		
	}

	
	public ArrayList<LookupVO> getPartStatus()	throws BusinessServiceException { 
		lookupBO = this.getLookupBO();
		ArrayList<LookupVO> sc = null;
		try {
			sc = lookupBO.getPartStatus();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
		return sc;
	}

	/**
	 * This method retrieves parent node id for a subCategoryId.
	 * @param Integer subCategoryId
	 * @return Integer
	 * throws DelegateException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws DelegateException{
		Integer parentNodeId = null;
		try{
			parentNodeId = lookupBO.getParentNodeId(subCategoryId);
			
		}catch(BusinessServiceException ex){
			throw new DelegateException("Exception occurred at LookupDelegate.getParentNodeId method : ",ex);
		}
		return parentNodeId;
	}
	public List<LookupVO> getSkills() throws DelegateException {
		try{	
			return lookupBO.getServiceTypeTemplates();
		}catch(BusinessServiceException ex){
			throw new DelegateException("Exception occurred at LookupDelegate.getParentNodeId method : ",ex);
		}
	}
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex) {
		return lookupBO.getMarketsByIndex(sIndex,eIndex);
	}
	/**
	 * This method retrieves ratings for insurance.
	 * @return InsuranceRatingsLookupVO
	 * throws DelegateException
	 */
	public InsuranceRatingsLookupVO getInsuranceRatings() throws DelegateException {
		InsuranceRatingsLookupVO insuranceRatings = new InsuranceRatingsLookupVO(); 
		try {
			insuranceRatings = lookupBO.getInsuranceRatings();
		} catch (BusinessServiceException e) {
			logger.error("Exception occurred while getting list of insurance ratings");
		}
		return insuranceRatings;
		
	}
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param buyerId,source
	 * @return List<LookupVO>
	 * @throws DelegateException
	 */
	public List<LookupVO> retrieveLookUpDocumentByBuyerId(Integer buyerId, Integer source) throws DelegateException
	{
		List<LookupVO> buyerLookUpDoc = null;			
		try {		
			buyerLookUpDoc = lookupBO.retrieveLookUpDocumentByBuyerId(buyerId,source);
		} 
		catch (BusinessServiceException bse) {            
			throw new DelegateException("Error retrieving buyer documents and logo in SLDocumentDelegateImpl.retrieveDocTypeByBuyerId()", bse);
		}			
		return buyerLookUpDoc;
	}
	/*Method to fetch sku category on the basis of buyer id for service order creation by sku*/
	public List<BuyerSkuCategoryVO> fetchBuyerSkuCategories(Integer buyerId)  throws DelegateException
	{
		List<BuyerSkuCategoryVO> buyerLookUpDoc = null;			
		try {
			buyerLookUpDoc = lookupBO.fetchBuyerSkuCategories(buyerId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return buyerLookUpDoc;
	}
	/*Ajax Method to fetch sku name on the basis of category id for service order creation by sku*/
	public List<BuyerSkuVO>fetchBuyerSkuNameByCategory(Integer categoryId) throws DelegateException
	{
		List<BuyerSkuVO> buyerSkuName = null;			
		try {
			buyerSkuName = lookupBO.fetchBuyerSkuNameByCategory(categoryId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return buyerSkuName;
	}
	/*Ajax Method to fetch sku detail on the basis of sku id for service order creation by sku*/
	public BuyerSkuTaskForSoVO fetchBuyerSkuDetailBySkuId(Integer skuId)throws DelegateException
	{
		BuyerSkuTaskForSoVO buyerSkuDetailBySkuId=null;
		try {
			buyerSkuDetailBySkuId = lookupBO.fetchBuyerSkuDetailBySkuId(skuId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buyerSkuDetailBySkuId;
	}
	/*Ajax Method to fetch bid price on the basis of sku id for service order creation by sku*/
	public Double fetchBidPriceBySkuId(Integer skuId)throws DelegateException
	{
		Double skuBidPrice=0.0;
		try {
			skuBidPrice = lookupBO.fetchBidPriceBySkuId(skuId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return skuBidPrice;
		
	}
	/*Ajax Method to fetch template detail on the basis of sku id for service order creation by sku*/
	public LuServiceTypeTemplateVO fetchServiceTypeTemplate(Integer serviceTypeTemplateId) throws DelegateException
	{
		LuServiceTypeTemplateVO luServiceTemplate=null;
		try {
			luServiceTemplate=lookupBO.fetchServiceTypeTemplate(serviceTypeTemplateId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return luServiceTemplate;
	}
	/*Ajax Method to fetch skill tree detail on the basis of node id for service order creation by sku*/
	public SkillTreeForSkuVO fetchSkillTreeDetailBySkuId(Integer nodeId) throws DelegateException
	{
		SkillTreeForSkuVO buyerSkillTreeDetail=null;
		try {
			buyerSkillTreeDetail=lookupBO.fetchSkillTreeDetailBySkuId( nodeId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buyerSkillTreeDetail;
	}
	/*Ajax Method to fetch template detail on the basis of template id for service order creation by sku*/
	public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) throws DelegateException
	{
	
		BuyerSOTemplateForSkuVO templateDetail=null;
		try {
			templateDetail=lookupBO.fetchBuyerTemplateDetailBySkuId( templateId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return templateDetail;
	
	}
	public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId)
	{
		List<DocumentVO> documentForVO=null;
		try {
			documentForVO=lookupBO.retrieveDocumentByTitleAndEntityID(title,buyerId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return documentForVO;
		
	}
	
	public boolean getViewDocPermission(String userName) throws BusinessServiceException
	{
		boolean viewDocPermission = false;
		try {
			viewDocPermission = lookupBO.getViewDocPermission(userName);
		} catch (BusinessServiceException e) {
			logger.error("Error while retrieving viewDocPermission in LookupDelegateImpl.getViewDocPermission()", e);
			e.printStackTrace();
		}
			return viewDocPermission;
		
	}
	
	public String getStateRegulationNote(Integer reasonCodeId){
		String stateRegulationNote = null;
		try {
			stateRegulationNote  = lookupBO.getStateRegulationNote(reasonCodeId);
		} catch (Exception e) {
			logger.error("Error while retrieving viewDocPermission in LookupDelegateImpl.getStateRegulationNote() : ", e);
			e.printStackTrace();
		}
		return stateRegulationNote;
	}
	
	public String getIRSlevyNote(Integer reasonCodeId) {
		String IRSlevyNote = null;
		try {
			IRSlevyNote = lookupBO.getIRSlevyNote(reasonCodeId);
		} catch (Exception e) {
			logger.error("Error while retrieving viewDocPermission in LookupDelegateImpl.getIRSlevyNote() : ", e);
			e.printStackTrace();
		}
		return IRSlevyNote;
	}
	
	public String getLegalHoldNote(Integer reasonCodeId) {
		String legalHoldNote = null;
		try {
			legalHoldNote = lookupBO.getLegalHoldNote(reasonCodeId);
		} catch (Exception e) {
			logger.error("Error while retrieving viewDocPermission in LookupDelegateImpl.getLegalHoldNote() : ", e);
			e.printStackTrace();
		}
		return legalHoldNote;
	}
	
	public Integer checkLegalHoldPermission(String userName) throws BusinessServiceException {
		return getLookupBO().checkLegalHoldPermission(userName);
	}
}
