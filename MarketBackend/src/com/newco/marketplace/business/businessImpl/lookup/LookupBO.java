package com.newco.marketplace.business.businessImpl.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
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
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.lookup.LookupDaoFinance;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.utils.StackTraceHelper;

/**
 * LookupBO.java - LookupBO returns lookup objects from database
 *
 *
 */

public class LookupBO implements ILookupBO  {
	private static final Logger logger = Logger.getLogger(LookupBO.class.getName());

	private LookupDao lookupDao;
	private ILookupDAO provLookupDao;
	private LookupDaoFinance lookupDaoFinance;

	public LookupDao getLookupDao() {
		return lookupDao;
	}

	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	public String getZipTimezone(String zip) {
		try {
			return getLookupDao().getZipTimeZone(zip);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public ArrayList<LuProviderRespReasonVO> getProviderRespReason(LuProviderRespReasonVO luProviderRespReasonVO) throws DataServiceException {
		try {
			return getLookupDao().getProviderRespReason(luProviderRespReasonVO);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}
	public List<LookupVO> getLuAutocloseRules() throws DataServiceException{
		List<LookupVO> autocloseRulesList=new ArrayList<LookupVO>();
		try
		{
			autocloseRulesList=  getLookupDao().getLuAutocloseRules();
		}
		catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return autocloseRulesList;
	}

	public ArrayList<SkillNodeVO> getSkillTreeMainCategories() throws DataServiceException {
		try {
			return getLookupDao().getSkillTreeMainCategories();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}

	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId, String stateCd) throws DataServiceException {
		try {
			return getLookupDao().getNotBlackedOutSkillTreeMainCategories(buyerId, stateCd);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}

	public List<ServiceTypesVO> getNotBlackedOutServiceTypes(Integer selectedId, String buyerId, String stateCd) throws DataServiceException {
		try {
			return getLookupDao().getNotBlackedOutServiceTypes(selectedId, buyerId, stateCd);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}

	public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(Integer selectedId) throws DataServiceException {
		try {
			return getLookupDao().getSkillTreeCategoriesOrSubCategories(selectedId);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}

	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(Integer selectedId, String buyerId, String stateCd) throws DataServiceException {
		try {
			return getLookupDao().getNotBlackedOutSkillTreeCategoriesOrSubCategories(selectedId, buyerId, stateCd);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}

	public List<LookupVO> getStateCodes() {
		try {
			return getLookupDao().getStateCodes();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return new ArrayList<LookupVO>();

	}

	public List<LookupVO> getMarkets() {
		try {
			return getLookupDao().getMarkets();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			logger.info("Caught Exception and ignoring", e);
		}
		return new ArrayList<LookupVO>();
	}

	public List<LookupVO> getDistricts() {
		try {
			return getLookupDao().getDistricts();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			logger.info("Caught Exception and ignoring", e);
		}
		return new ArrayList<LookupVO>();
	}

	public List<LookupVO> getRegions() {
		try {
			return getLookupDao().getRegions();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			logger.info("Caught Exception and ignoring", e);
		}
		return new ArrayList<LookupVO>();
	}

	public List<LookupVO> getProviderFirmStatuses() {
		List<LookupVO> al = null;

		try {
			al = getLookupDao().getProviderFirmStatuses();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			al = new ArrayList<LookupVO>();
		}
		return al;
	}

	public List<LookupVO> getAuditableItems() {
		List<LookupVO> al = null;

		try {
			al = getLookupDao().getAuditableItems();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			al = new ArrayList<LookupVO>();
		}
		return al;
	}

	public List<LookupVO> getBackgroundStatuses() {
		List<LookupVO> al = null;

		try {
			al = getLookupDao().getBackgroundStatuses();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			al = new ArrayList<LookupVO>();
		}
		return al;
	}

	public List<LookupVO> getProviderNetworks() {
		List<LookupVO> al = null;

		try {
			al = getLookupDao().getProviderNetworks();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			al = new ArrayList<LookupVO>();
		}
		return al;
	}

	public List<LookupVO> getPrimarySkills() {
		List<LookupVO> al = null;

		try {
			al = getLookupDao().getPrimarySkills();
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			al = new ArrayList<LookupVO>();
		}
		return al;
	}

	public ArrayList<LookupVO> getPercentOwnedList() throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDao().getPercentOwnedList();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return al;
	}

	public ArrayList<LookupVO> getAccountTypeList() throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDaoFinance().getAccountTypeList();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return al;
	}

	public ArrayList<LookupVO> getCreditCardTypeList() throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDaoFinance().getCreditCardTypeList();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return al;
	}

	public ArrayList<LookupVO> getLanguages() throws DataServiceException {

		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDao().getLanguages();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return al;

	}

	public ArrayList<LookupVO> getCredentials() throws DataServiceException {

		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDao().getCredentials();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		//added for sorting
		Collections.sort(al,new Comparator<LookupVO>() {
        public int compare(LookupVO o1, LookupVO o2) {
	        	if(null==o1.getDescr() && null !=o2.getDescr()){
	        		return 1;
	        	}else if(null!=o1.getDescr() && null ==o2.getDescr()){
	        		return -1;
	        	}else if(null==o1.getDescr() && null ==o2.getDescr()){
	        		return 0;
	        	}else{
	        		return o1.getDescr().trim().compareToIgnoreCase(o2.getDescr().trim());
	        	}
			}
		});
		return al;

	}

	public ArrayList<LookupVO> getCredentialCategory(Integer credentialType) throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDao().getCredentialCategory(credentialType);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return al;
	}

	public ArrayList<LookupVO> getCredCategoryForBuyer(Map<String, Integer> hm) throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDao().getCredCategoryForBuyer(hm);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return al;
	}

	public ArrayList<LookupVO> getPhoneTypes() throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDao().getPhoneTypes();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return al;

	}

	public ArrayList<LookupVO> getShippingCarrier() throws DataServiceException {
		ArrayList<LookupVO> sc = null;
		try {
			sc = getLookupDao().getShippingCarrier();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return sc;

	}

	public boolean getIfZipInServiceBlackout(Integer nodeId, String zip) throws DataServiceException {
		Object resultMap = null;
		try {
			resultMap = getLookupDao().getServiceBlackout(nodeId, zip);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		if (resultMap != null) {
			return true;
		}
		return false;
	}

	public LocationVO checkIFZipExists(String zip) throws DataServiceException {
		return getLookupDao().getZipCodeEntry(zip);
	}

	public ArrayList<LuBuyerRefVO> getBuyerRef(String buyerId) throws DataServiceException {
		try {
			return getLookupDao().getBuyerRef(buyerId);

		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}
	
	public LuBuyerRefVO getBuyerRef(String buyerId,String refType) throws DataServiceException {
		try {
			return getLookupDao().getBuyerRef(buyerId,refType);

		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}

	public TermsAndConditionsVO getTermsConditionsContent() throws DataServiceException {
		TermsAndConditionsVO termsAndConditionContent = null;
		try {
			termsAndConditionContent = getLookupDao().getTermsConditionsContent();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return termsAndConditionContent;

	}

	public TermsAndConditionsVO getTermsConditionsContent(String termsAndCond) throws DataServiceException {
		TermsAndConditionsVO termsAndConditionContent = null;
		try {
			termsAndConditionContent = getLookupDao().getTermsConditionsContent(termsAndCond);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return termsAndConditionContent;

	}
	
	//SL-19293 Method to fetch the link for the lead gen. fees pdf.
	public String getLeadGenFeesLink(Integer vendorId)
			throws DataServiceException {
		String leadGenFeesLink = null;
		try {
			leadGenFeesLink = getLookupDao().getLeadGenFeesLink(vendorId);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return leadGenFeesLink;

	}

	public List<LookupVO> getEntityStatusList(String entityType) throws BusinessServiceException {
		List<LookupVO> resultList = null;
		try {
			resultList = getLookupDao().getEntityStatusList(entityType);
		} catch (DataServiceException e) {
			logger.error("LookupBOImpl --> getEntityStatusList() due to" + e.getMessage());
			throw new BusinessServiceException("LookupBOImpl --> getEntityStatusList() due to " + e.getMessage());
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<LookupVO> loadReasonCodeList(LookupVO lookupVO) throws BusinessServiceException {
		com.newco.marketplace.vo.provider.LookupVO pLookupVO = new com.newco.marketplace.vo.provider.LookupVO();
		List<LookupVO> resultList = null;
		List<com.newco.marketplace.vo.provider.LookupVO> result = null;
		// Converting LookupVO into provider's LookupVO
		pLookupVO.setId(lookupVO.getType());
		pLookupVO.setEntity(lookupVO.getDescr());
		try {
			result = getProvLookupDao().loadReasonCodeListOnClick(pLookupVO);
		} catch (DBException e) {
			logger.error("LookupBOImpl --> loadReasonCodeListOnClick() due to" + e.getMessage());
			throw new BusinessServiceException("LookupBOImpl --> loadReasonCodeListOnClick() due to " + e.getMessage());
		}
		if (result != null) {
			resultList = new ArrayList<LookupVO>();
			for (int i = 0; i < result.size(); i++) {
				LookupVO lookupVo = new LookupVO();
				com.newco.marketplace.vo.provider.LookupVO resultVO = result.get(i);

				lookupVo.setDescr(resultVO.getDescr());
				if (resultVO.getId() != null && !resultVO.getId().equals("")) {
					try {
						lookupVo.setId(Integer.valueOf(resultVO.getId()));
					} catch (NumberFormatException nfe) {
						logger.error("LookupBOImpl --> loadReasonCodeListOnClick() Error converting " + " String to Integer");
					}
				}
				lookupVo.setSortOrder(resultVO.getSortOrder());
				lookupVo.setType(resultVO.getType());

				resultList.add(lookupVo);
			}
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<LookupVO> getReasonCodeListByTaskId(LookupVO lookup) throws BusinessServiceException {
		List<LookupVO> reasonCodeList = new ArrayList<LookupVO>();
		com.newco.marketplace.vo.provider.LookupVO pLookupVO = new com.newco.marketplace.vo.provider.LookupVO();
		List<com.newco.marketplace.vo.provider.LookupVO> result = null;
		try {

			if (lookup != null && lookup.getId() != null) {
				pLookupVO.setId(lookup.getId().toString());
			}

			result = this.getProvLookupDao().loadReasonCodes(pLookupVO);

			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					com.newco.marketplace.vo.provider.LookupVO vo = result.get(i);
					LookupVO lookupVO = new LookupVO();
					try {
						lookupVO.setId(Integer.valueOf(vo.getId()));
					} catch (NumberFormatException nfe) {
						logger.error("LookupBOImpl --> getReasonCodeList() Error converting " + " String to Integer");
					}
					lookupVO.setType(new Integer(vo.getReasonCd()).toString());
					reasonCodeList.add(lookupVO);
				}
			}
		} catch (DBException e) {
			logger.error("LookupBOImpl --> getReasonCodeList() Error getting reason code list ");
			throw new BusinessServiceException("LookupBOImpl --> loadReasonCodes() due to " + e.getMessage());
		}

		return reasonCodeList;
	}

	public List<LookupVO> getCompanyRoles(Integer roleId) throws DataServiceException {
		try {
			return lookupDao.loadCompanyRole(roleId);
		} catch (Exception e) {
			logger.info("Ignoring exception", e);
		}

		return null;
	}

	public String getDaylightSavingsFlg(String zip) throws DataServiceException {
		String flg = getLookupDao().getDaylightSavingsFlg(zip);

		return flg;
	}

	public LookupDaoFinance getLookupDaoFinance() {
		return lookupDaoFinance;
	}

	public void setLookupDaoFinance(LookupDaoFinance lookupDaoFinance) {
		this.lookupDaoFinance = lookupDaoFinance;
	}

	/**
	 * @return the provLookupDao
	 */
	public ILookupDAO getProvLookupDao() {
		return provLookupDao;
	}

	/**
	 * @param provLookupDao the provLookupDao to set
	 */
	public void setProvLookupDao(ILookupDAO provLookupDao) {
		this.provLookupDao = provLookupDao;
	}

	public ArrayList<LookupVO> getTransferSLBucksReasonCodes() throws DataServiceException {

		ArrayList<LookupVO> al = null;

		try {
			al = getLookupDaoFinance().getTransferReasonCodeList();
		} catch (DataServiceException e) {
			logger.info("[LookupBO.getTransferReasonCodeList() - Exception] " + StackTraceHelper.getStackTrace(e));
		}
		return al;

	}

	public String getStateForZip(String zipCode) throws DataServiceException {
		String state = "";
		try {
			state = getLookupDao().getStateForZip(zipCode);
		} catch (DataServiceException e) {
			logger.info("[LookupBO.getTransferReasonCodeList() - Exception] " + StackTraceHelper.getStackTrace(e));
		}
		return state;
	}

	@SuppressWarnings("unchecked")
	public List<LookupVO> getBusinessTypes() {
		try {
			List<com.newco.marketplace.vo.provider.LookupVO> list = getProvLookupDao().loadBusinessTypes();
			ArrayList<LookupVO> newList = new ArrayList<LookupVO>();
			for (com.newco.marketplace.vo.provider.LookupVO vo : list) {
				LookupVO newvo = new LookupVO();
				newvo.setId(new Integer(vo.getId()));
				newvo.setDescr(vo.getDescr());
				newvo.setType(vo.getId());
				newvo.setSortOrder(vo.getSortOrder());
				newList.add(newvo);
			}

			return newList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.lookup.ILookupBO#loadProviderPrimaryIndustry()
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> loadProviderPrimaryIndustry() {
		try {
			List<com.newco.marketplace.vo.provider.LookupVO> list = getProvLookupDao().loadPrimaryIndustry();
			ArrayList<LookupVO> newList = new ArrayList<LookupVO>();
			for (com.newco.marketplace.vo.provider.LookupVO vo : list) {
				LookupVO newvo = new LookupVO();
				newvo.setId(new Integer(vo.getId()));
				newvo.setDescr(vo.getDescr());
				newvo.setType(vo.getType());
				newvo.setSortOrder(vo.getSortOrder());
				newList.add(newvo);
			}

			return newList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.lookup.ILookupBO#loadWorkFlowStatusForEntity(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> loadWorkFlowStatusForEntity(String entity) {
		try {
			List<com.newco.marketplace.vo.provider.LookupVO> list = getProvLookupDao().loadStatus(entity);
			ArrayList<LookupVO> newList = new ArrayList<LookupVO>();
			for (com.newco.marketplace.vo.provider.LookupVO vo : list) {
				LookupVO newvo = new LookupVO();
				newvo.setId(new Integer(vo.getId()));
				newvo.setDescr(vo.getDescr());
				newvo.setType(vo.getType());
				newvo.setSortOrder(vo.getSortOrder());
				newList.add(newvo);
			}

			return newList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.lookup.ILookupBO#getReferral()
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getReferral() {
		try {
			List<com.newco.marketplace.vo.provider.LookupVO> list = getProvLookupDao().loadReferrals();
			ArrayList<LookupVO> newList = new ArrayList<LookupVO>();
			for (com.newco.marketplace.vo.provider.LookupVO vo : list) {
				LookupVO newvo = new LookupVO();
				newvo.setId(new Integer(vo.getId()));
				newvo.setDescr(vo.getDescr());
				newvo.setType(vo.getType());
				newvo.setSortOrder(vo.getSortOrder());
				newList.add(newvo);
			}

			return newList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public List<LookupVO> getMinimumRatings() throws DataServiceException {
		List<LookupVO> minRatingsList = null;

		try {
			minRatingsList = getLookupDao().getMinimumRatings();
		} catch (DataServiceException e) {
			logger.info("Caught Exception in LookupBO.getMinimumRatings and ignoring", e);
		}
		return minRatingsList;

	}

	public List<LookupVO> getResourceDistList() throws DataServiceException {
		List<LookupVO> resourceDistList = null;

		try {
			resourceDistList = getLookupDao().getResourceDistList();
		} catch (DataServiceException e) {
			logger.info("Caught Exception LookupBO.getResourceDistList  and ignoring", e);
		}
		return resourceDistList;

	}

	public List<LookupVO> getProviderTopSelectList() throws DataServiceException {
		List<LookupVO> proTopSelectList = null;

		try {
			proTopSelectList = getLookupDao().getProviderTopSelectList();
		} catch (DataServiceException e) {
			logger.info("Caught Exception LookupBO.getProviderTopSelectList  and ignoring", e);
		}
		return proTopSelectList;
	}

	public List<LookupVO>  getPerformanceLevels() throws DataServiceException
	{
		List<LookupVO> list = null;

		try {
			list = getLookupDao().getPerformanceLevels();
		} catch (DataServiceException e) {
			logger.info("Caught Exception LookupBO.getPerformanceLevels()  and ignoring",e);
		}
		return list;		
	}


	public List<LookupVO> getTaxPayerTypeIdList() throws DataServiceException {
		try {
			List<LookupVO> list = getLookupDao().getTaxPayerTypeIdList();
			return list;
		} catch (DataServiceException e) {
			throw new DataServiceException("couldn't find entries for LookupBO.getTaxPayerTypeIdList", e);
		}
	}
	/**
	 * This method retrieves parent node id for a subCategoryId.
	 * @param Integer subCategoryId
	 * @return Integer
	 * throws BusinessServiceException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws BusinessServiceException{
		try{
			Integer parentNodeId = getLookupDao().getParentNodeId(subCategoryId);
			return parentNodeId;			
		}catch(DataServiceException ex){
			throw new BusinessServiceException("Exception in LookupBO.getParentNodeId() due to : " ,ex);
		}			
	}
	
	public ArrayList<LookupVO> getPartStatus() throws DataServiceException {
		ArrayList<LookupVO> sc = null;
		try {
			sc = getLookupDao().getPartStatus();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return sc;
	}
	/**
	 * This method retrieves skills from lu_service_type_template table.	 
	 * @return List
	 * throws DataServiceException
	 */
	public ArrayList<LookupVO> getServiceTypeTemplates() throws BusinessServiceException {
		ArrayList<LookupVO> sc = null;
		try {
			sc = getLookupDao().getServiceTypeTemplate();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return sc;
	}
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex) {
		try {
			return getLookupDao().getMarketsByIndex(sIndex,eIndex);
		} catch (DataServiceException e) {
			// exception logged by DAO, returning empty list
			logger.info("Caught Exception and ignoring", e);
		}
		return new ArrayList<LookupVO>();
}
	/**
	 * This method is used to fetch all substatus
	 * 
	 * @return List<LookupVO>
	 */
	public List<LookupVO> getSubStatusList(Integer statusId){
		List<LookupVO> subStatusList = null;
		try{			
			subStatusList = getLookupDao().getSubStatusList(statusId);						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return subStatusList;
	}
	/**
	 * This method retrieves ratings for insurance.
	 * @return InsuranceRatingsLookupVO
	 * throws BusinessServiceException
	 */
	public InsuranceRatingsLookupVO getInsuranceRatings()
			throws BusinessServiceException {
		InsuranceRatingsLookupVO insuranceRatings = new InsuranceRatingsLookupVO();
		List<LookupVO> insuranceList = new ArrayList<LookupVO>();
		List<LookupVO> generalLiabilityRatingList = new ArrayList<LookupVO>();
		List<LookupVO> vehicleLiabilityRatingList = new ArrayList<LookupVO>();
		List<LookupVO> workersCompensationRatingList = new ArrayList<LookupVO>();
		List<LookupVO> additionalInsuranceList = new ArrayList<LookupVO>();
		try {
			insuranceList = getLookupDao().getInsuranceRatings();

			for (LookupVO list : insuranceList) {
				if (list.getType().equals("41")) {
					generalLiabilityRatingList.add(list);
				} else if (list.getType().equals("42")) {
					vehicleLiabilityRatingList.add(list);
				} else if (list.getType().equals("43")) {
					workersCompensationRatingList.add(list);
				}
				// SL-10809 Additional Insurance

				else if (list.getType().equals("142")
						|| list.getType().equals("143")
						|| list.getType().equals("144")
						|| list.getType().equals("145")
						|| list.getType().equals("146")
						|| list.getType().equals("147")
						|| list.getType().equals("148")
						|| list.getType().equals("149")
						|| list.getType().equals("150")) {
					additionalInsuranceList.add(list);
				}

			}
			insuranceRatings
					.setGeneralLiabilityRatingList(generalLiabilityRatingList);
			insuranceRatings
					.setVehicleLiabilityRatingList(vehicleLiabilityRatingList);
			insuranceRatings
					.setWorkersCompensationRatingList(workersCompensationRatingList);
			insuranceRatings
					.setAdditionalInsuranceRatingList(additionalInsuranceList);

		} catch (DataServiceException e) {
			logger.info(
					"Caught Exception in LookupBO.getInsuranceRatings and ignoring",
					e);
		}
		return insuranceRatings;

	}
	public List<LookupVO> getNotBlackedOutStateCodes() {
		try {
			return getLookupDao().getNotBlackedOutStateCodes();
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return new ArrayList<LookupVO>();

	}


	public int getPostedStatus(String soId) {
		int posted = 0;
		try{			
			posted = getLookupDaoFinance().getPostedStatus(soId);						
		}catch (Exception e)
		{
			logger.error("getPostedStatus for so_id: " + soId + " failed.", e);
			e.printStackTrace();
		}
		return posted;
	}

	public List<String> getCountryList(){
		List<String> countryList = null;
		try{			
			countryList = getLookupDao().getCountryList();						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return countryList;
	}
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param buyerId,source
	 * @return List<LookupVO>
	 * @throws BusinessServiceException
	  */
	public List<LookupVO> retrieveLookUpDocumentByBuyerId(Integer buyerId, Integer source) throws BusinessServiceException
	{
		
		List<LookupVO> buyerLookUpdoc = null;

		try {
			buyerLookUpdoc = lookupDao.retrieveLookUpDocumentByBuyerId(buyerId,source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyerLookUpdoc;
		}
	/*Method to fetch sku category on the basis of buyer id for service order creation by sku*/
	public List<BuyerSkuCategoryVO> fetchBuyerSkuCategories(Integer buyerId) throws BusinessServiceException
	{
		List<BuyerSkuCategoryVO> buyerSkuCategoryList = null;

		try {
			buyerSkuCategoryList = lookupDao.fetchBuyerSkuCategories(buyerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyerSkuCategoryList;
		
	}
	/*Method to fetch sku name on the basis of category id for service order creation by sku*/
	public List<BuyerSkuVO>fetchBuyerSkuNameByCategory(Integer categoryId) throws BusinessServiceException
	{
		List<BuyerSkuVO> buyerSkuCategoryList = null;

		try {
			buyerSkuCategoryList = lookupDao.fetchBuyerSkuNameByCategory(categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyerSkuCategoryList;
		
	}
	/*Ajax Method to fetch sku detail on the basis of sku id for service order creation by sku*/
	public BuyerSkuTaskForSoVO fetchBuyerSkuDetailBySkuId(Integer skuId) throws BusinessServiceException
	{
		BuyerSkuTaskForSoVO buyerSkuDetailBySkuId=null;
		buyerSkuDetailBySkuId = lookupDao.fetchBuyerSkuDetailBySkuId(skuId);
		return buyerSkuDetailBySkuId;
	}
	/*Ajax Method to fetch bid price on the basis of sku id for service order creation by sku*/
	public Double fetchBidPriceBySkuId(Integer skuId)throws BusinessServiceException
	{
		Double skuBidPrice=0.0;
		skuBidPrice = lookupDao.fetchBidPriceBySkuId(skuId);
		return skuBidPrice;
		
	}
	/*Ajax Method to fetch template detail on the basis of sku id for service order creation by sku*/
	public LuServiceTypeTemplateVO fetchServiceTypeTemplate(Integer serviceTypeTemplateId) throws BusinessServiceException
	{
		LuServiceTypeTemplateVO luServiceTemplate=null;
		luServiceTemplate=lookupDao.fetchServiceTypeTemplate(serviceTypeTemplateId);
		return luServiceTemplate;
	}
	/*Ajax Method to fetch skill tree detail on the basis of node id for service order creation by sku*/
	public SkillTreeForSkuVO fetchSkillTreeDetailBySkuId(Integer nodeId) throws BusinessServiceException
	{
		SkillTreeForSkuVO buyerSkillTreeDetail=null;
		buyerSkillTreeDetail=lookupDao.fetchSkillTreeDetailBySkuId( nodeId);
		return buyerSkillTreeDetail;
	}
	/*Ajax Method to fetch template detail on the basis of template id for service order creation by sku*/
	public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) throws BusinessServiceException
	{
	
		BuyerSOTemplateForSkuVO templateDetail=null;
		templateDetail=lookupDao.fetchBuyerTemplateDetailBySkuId(templateId);
			return templateDetail;
	
	}
	public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId) throws BusinessServiceException
	{
		List<DocumentVO> documentForVO=null;
		documentForVO=lookupDao.retrieveDocumentByTitleAndEntityID(title,buyerId);
		return documentForVO;
		
	}
	//to check whether buyer has permission to view prov docs
	public boolean getViewDocPermission(String userName)throws BusinessServiceException{
		boolean viewDocPermission = false;
		viewDocPermission = lookupDao.getViewDocPermission(userName);
		return viewDocPermission;
	}

	public ServiceOrder fetchSODetails(String soId) throws DataServiceException {
		// TODO Auto-generated method stub
		return lookupDao.fetchSODetails(soId);
	}
	
	public String getStateRegulationNote(Integer reasonCodeId) {
		return lookupDao.getStateRegulationNote(reasonCodeId);
	}

	public String getIRSlevyNote(Integer reasonCodeId) {
		return lookupDao.getIRSlevyNote(reasonCodeId);
	}

	public String getLegalHoldNote(Integer reasonCodeId) {
		return lookupDao.getLegalHoldNote(reasonCodeId);
	}

	public Integer checkLegalHoldPermission(String adminUserName) {
		return lookupDao.checkLegalHoldPermission(adminUserName);
	}
}

