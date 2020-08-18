package com.newco.marketplace.persistence.daoImpl.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;




import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.BuyerSOTemplateForSkuDTO;
import com.newco.marketplace.dto.vo.BuyerSOTemplateForSkuVO;
import com.newco.marketplace.dto.vo.BuyerSkuCategoryVO;
import com.newco.marketplace.dto.vo.BuyerSkuTaskForSoVO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DocumentForSkuVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;
import com.newco.marketplace.dto.vo.LeadFeesLinkVO;
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
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.cache.QueryCacheImpl;
import com.newco.marketplace.persistence.cache.QueryKey;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * $Revision: 1.34 $ $Author: pjoy0 $ $Date: 2008/06/08 16:14:44 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class LookupDaoImpl extends ABaseImplDao implements LookupDao {

	private static final Logger logger = Logger.getLogger(LookupDaoImpl.class.getName());
	private QueryCacheImpl queryCacheImpl;

	/**
	 * @param queryCacheImpl
	 *            the queryCacheImpl to set
	 */
	public void setQueryCacheImpl(QueryCacheImpl queryCacheImpl) {
		this.queryCacheImpl = queryCacheImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.lookup.LookupDao#getProviderRespReason(com.newco.marketplace.dto.vo.LuProviderRespReasonVO)
	 */
	public ArrayList<LuProviderRespReasonVO> getProviderRespReason(LuProviderRespReasonVO luProviderRespReasonVO) throws DataServiceException {

		ArrayList<LuProviderRespReasonVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.reason", luProviderRespReasonVO);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.reason query  for lu_so_provider_resp_reason table Failed", exception);
		}
		return al;
	}
	
	public List<LookupVO> getLuAutocloseRules() throws DataServiceException
	{
		List<LookupVO> autocloseRulesList= new ArrayList<LookupVO>();
		autocloseRulesList= (ArrayList) queryForList("lookup.autocloseRules");
		return autocloseRulesList;
	}

	public ArrayList<SkillNodeVO> getSkillTreeMainCategories() throws DataServiceException {

		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.skillTreeMainCategories", null);

		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.skillTreeMainCategories query for skill_tree table Failed", exception);
		}
		return al;
	}

	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeMainCategories(String buyerId, String stateCd) throws DataServiceException {

		HashMap<String, String> paramMap = new HashMap();

		paramMap.put("buyerId", buyerId);
		paramMap.put("stateCd", stateCd);
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.blackedoutskillTreeMainCategories", paramMap);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.blackedoutskillTreeMainCategories query for skill_tree table Failed", exception);
		}
		return al;
	}

	public ArrayList<SkillNodeVO> getSkillTreeCategoriesOrSubCategories(Integer selectedId) throws DataServiceException {

		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.skillTreeCategoriesOrSubCategories", selectedId);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.skillTreeMainCategories query for skill_tree table Failed", exception);
		}
		return al;
	}

	public ArrayList<SkillNodeVO> getNotBlackedOutSkillTreeCategoriesOrSubCategories(Integer selectedId, String buyerId, String stateCd) throws DataServiceException {

		HashMap paramMap = new HashMap();
		paramMap.put("selectedId", selectedId);
		paramMap.put("buyerId", buyerId);
		paramMap.put("stateCd", stateCd);
		ArrayList<SkillNodeVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.blackedoutskillTreeCategoriesOrSubCategories", paramMap);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.blackedoutskillTreeCategoriesOrSubCategories query for skill_tree table Failed", exception);
		}
		return al;
	}

	public List<LookupVO> getStateCodes() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = (ArrayList) queryForList("lookup.statecodes", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getStateCodes() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.statecodes query failed", ex);

		}
		return al;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.lookup.LookupDao#getMarkets()
	 */
	public List<LookupVO> getMarkets() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.markets", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getMarkets() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.getMarkets() query failed", ex);
		}

		return al;
	}

	public List<LookupVO> getDistricts() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.districts", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getDistricts() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.districts query failed", ex);
		}

		return al;
	}

	public List<LookupVO> getRegions() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.regions", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getRegions() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.regions query failed", ex);
		}

		return al;
	}

	public List<LookupVO> getProviderFirmStatuses() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.provider_firm_statuses", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getProviderFirmStatuses() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.provider_firm_statuses query failed", ex);
		}

		return al;
	}

	public List<LookupVO> getPrimarySkills() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.primary_skills", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getPrimarySkills() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.primary_skills query failed", ex);
		}

		return al;
	}

	public List<LookupVO> getAuditableItems() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.statecodes", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getAuditableItems() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.getStateCodes query failed", ex);
		}

		return al;
	}

	public List<LookupVO> getBackgroundStatuses() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.background_check_statuses", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getBackgroundStatuses() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.background_check_statuses query failed", ex);
		}

		return al;
	}

	public List<LookupVO> getProviderNetworks() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = queryForList("lookup.select_provider_networks", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getProviderNetworks() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.select_provider_network query failed", ex);
		}

		return al;
	}

	public ArrayList<LookupVO> getPercentOwnedList() throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = (ArrayList) queryForList("lookup.percentowned", null);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getPercentOwnedList() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.getPercentOwnedList() query failed", ex);

		}
		return al;
	}

	public List<ServiceTypesVO> getNotBlackedOutServiceTypes(Integer selectedId, String buyerId, String stateCd) throws DataServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ServiceTypesVO> returnArray = null;
		paramMap.put("node_id", selectedId);
		paramMap.put("stateCd", stateCd);
		paramMap.put("buyerId", buyerId);
		try {
			returnArray = (List<ServiceTypesVO>) queryForList("servicetypesblackedout.query", paramMap);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.shippingCarrier query  for lu_shipping_carrier table Failed", exception);
		}

		return returnArray;
	}

	public ArrayList<LookupVO> getLanguages() throws DataServiceException {
		ArrayList<LookupVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.languages", null);

		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getStateCodes() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.Languages query failed", ex);
		}
		return al;
	}

	public ArrayList<LookupVO> getCredentials() throws DataServiceException {
		ArrayList<LookupVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.credentials", null);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getCredentials() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.Credentials query failed", ex);
		}
		return al;
	}

	public ArrayList<LookupVO> getCredentialCategory(Integer credentialType) throws DataServiceException {
		ArrayList<LookupVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.credentialCategory", credentialType);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getCredentialCategory() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.Credentials Category query failed", ex);
		}
		return al;
	}

	public ArrayList<LookupVO> getCredCategoryForBuyer(Map<String, Integer> hm) throws DataServiceException {
		ArrayList<LookupVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.credentialCategoryForBuyer", hm);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getCredentialCategoryForBuyer() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.Credentials Category query failed", ex);
		}
		return al;
	}

	public ArrayList<LookupVO> getPhoneTypes() throws DataServiceException {
		ArrayList<LookupVO> al = null;

		try {
			al = (ArrayList) queryForList("lookup.phonetypes", null);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getPhoneTypes() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.getPhoneTypes query failed", ex);

		}
		return al;
	}

	public ArrayList<LookupVO> getShippingCarrier() throws DataServiceException {

		ArrayList<LookupVO> sc = null;
		try {
			sc = (ArrayList) queryForList("lookup.shippingCarrier", null);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.shippingCarrier query  for lu_shipping_carrier table Failed", exception);
		}
		return sc;
	}

	public ArrayList<LuBuyerRefVO> getBuyerRef(String buyerId) throws DataServiceException {
		ArrayList<LuBuyerRefVO> buyerRef = null;
		try {
			buyerRef = (ArrayList) queryForList("lookup.buyerReference", buyerId);

		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.buyerReference query  for buyer_reference_type table Failed", exception);
		}
		return buyerRef;
	}
	
	public LuBuyerRefVO getBuyerRef(String buyerId,String refType) throws DataServiceException {
		LuBuyerRefVO buyerRef = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		paramMap.put("refType", refType);
		try {
			buyerRef = (LuBuyerRefVO) queryForObject("lookup.buyerReference.value", paramMap);

		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.buyerReference query  for buyer_reference_type table Failed", exception);
		}
		return buyerRef;
	}

	public HashMap getServiceBlackout(Integer nodeId, String zip) throws DataServiceException {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		HashMap resultMap = new HashMap();
		paramMap.put("nodeId", nodeId);
		paramMap.put("zip", zip);
		try {
			resultMap = (HashMap) queryForObject("lookup.serviceBlackout", paramMap);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.shippingCarrier query  for lu_shipping_carrier table Failed", exception);
		}
		return resultMap;
	}

	public TermsAndConditionsVO getTermsConditionsContent() throws DataServiceException {
		TermsAndConditionsVO TermsAndConditionsContent = null;
		try {
			TermsAndConditionsContent = (TermsAndConditionsVO) queryForObject("lookup.TermsAndConditions", null);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.lookup.TermsAndConditions query  for lu_terms_cond table Failed", exception);
		}
		return TermsAndConditionsContent;
	}

	public TermsAndConditionsVO getTermsConditionsContent(String termsAndCond) throws DataServiceException {
		TermsAndConditionsVO TermsAndConditionsContent = null;
		try {
			TermsAndConditionsContent = (TermsAndConditionsVO) queryForObject("lookup.AcceptTermsAndConditions", termsAndCond);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.AcceptTermsAndConditions query  for lu_terms_cond table Failed", exception);
		}
		return TermsAndConditionsContent;
	}
	
	//SL-19293 Method to fetch the link for the lead gen. fees pdf.
	public String getLeadGenFeesLink(Integer vendorId) throws DataServiceException{
		List<LeadFeesLinkVO> leadGenFeesLink = null;
		try {
			leadGenFeesLink = queryForList("lookup.getLeadGenFeesLink.query", vendorId);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.getLeadGenFeesLink query  for  table Failed", exception);
		}
		if(null != leadGenFeesLink){
			if(leadGenFeesLink.size() == 1 && (OrderConstants.LEAD_GEN_FEES_DEFAULT_PDF).equalsIgnoreCase(leadGenFeesLink.get(0).getStateCode())){
				return leadGenFeesLink.get(0).getPdfLink();
			}
			else if(leadGenFeesLink.size() == 2){
				if(!(OrderConstants.LEAD_GEN_FEES_DEFAULT_PDF).equalsIgnoreCase(leadGenFeesLink.get(0).getStateCode())){
					return leadGenFeesLink.get(0).getPdfLink();
				}
				else{
					return leadGenFeesLink.get(1).getPdfLink();
				}
			}
			else{
				logger.info("Duplicate entries in lu_lead_fees_pdfs");
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.lookup.LookupDao#getTimeIntervals()
	 */
	public List<LookupVO> getTimeIntervals() throws DataServiceException {

		List<LookupVO> toReturn = null;

		try {
			toReturn = queryForList("lookup.timeIntervals", null);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.timeIntervals query  for lu_time_interval table Failed", exception);
		}
		return toReturn;
	}

	public LocationVO getZipCodeEntry(String zip) throws DataServiceException {
		LocationVO lvo = null;
		try {
			if (zip != null && StringUtils.isNotEmpty(zip)) {
				lvo = (LocationVO) queryForObject("zip.queryLatLong", zip);
			}
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - zip.queryLatLong query  for zip_geocode table Failed", exception);
		}
		return lvo;
	}

	public String getZipTimeZone(String zip) throws DataServiceException {
		String timezone = null;
		try {
			if (StringUtils.isNotEmpty(zip)) {
				timezone = (String) queryForObject("zip.queryTimezone", zip);
			}
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - zip.queryTimezone query  for zip_geocode table Failed", exception);
		}
		return timezone;
	}
/*
	public Long getNextIdentifierFromFulfillmentEntryId(Long fulfillmentEntryId) {
		String stanId = "";
		if (fulfillmentEntryId != null) {
			stanId = fulfillmentEntryId.toString();
			if (stanId.length() == 9) {
				stanId = stanId.substring(3);
			} else if (stanId.length() == 8) {
				stanId = stanId.substring(2);
			} else if (stanId.length() == 7) {
				stanId = stanId.substring(1);
			}
		} else {
			Random generator = new Random();
			int randomNo = generator.nextInt(899999) + 100000;
			stanId = Integer.valueOf(randomNo).toString();
		}
		return new Long(stanId);
	}

	public Integer getNextIdentifier(String key) {
		Integer nextId = null;
		if (key != null && StringUtils.isNotEmpty(key)) {
			nextId = (Integer) insert("nextIdentifier.update", key);
			if (key.equals(FullfillmentConstants.LEDGER_TRANS_ID))
				nextId = nextId + 100040000;
			else if (key.equals(FullfillmentConstants.LEDGER_ENTRY_ID))
				nextId = nextId + 100040000;
			else if (FullfillmentConstants.STAN_ID.equals(key)) {
				long max = FullfillmentConstants.IDENTIFIER_FULLFILLMENT_STAN_MAX + 1;
				double next = nextId;
				nextId = (int) (next % max);
			}
		}
		return nextId;
	}
*/
	public List<LookupVO> loadCompanyRole(Integer roleId) throws DBException {
		List<LookupVO> result = null;
		try {
			result = queryForList("company_role.query", roleId);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LookupDAOImpl.loadCompanyRole()", ex);
			throw new DBException("General Exception @LookupDAOImpl.loadCompanyRole() due to " + ex.getMessage());
		}
		return result;

	}

	public List<LookupVO> getEntityStatusList(String entityType) throws DataServiceException {
		return queryForList("getStatusesForCompanyProfile.query", entityType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.lookup.LookupDao#getSPNStatuses()
	 */
	public List<LookupVO> getSPNStatuses() throws DataServiceException {
		return queryForList("spn_status_all.query", null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.lookup.LookupDao#getLanguageById()
	 */
	public LookupVO getLanguageById(Integer languageId) throws DataServiceException {
		return (LookupVO) queryForObject("language_by_id.query", languageId);
	}

	public String getDaylightSavingsFlg(String zip) throws DataServiceException {
		String dlsFlag = null;
		try {

			if (zip != null && StringUtils.isNotEmpty(zip)) {

				List<String> params = new ArrayList<String>();
				params.add(zip);
				QueryKey queryKey = new QueryKey("queryDayLightFlag", params);
				String result = (String) queryCacheImpl.get(queryKey);

				if (result == null) {
					// logger.info("********Fetching value from Database for
					// zip="
					// + zip);
					dlsFlag = (String) queryForObject("zip.queryDayLightFlag", zip);
					queryCacheImpl.put(queryKey, dlsFlag);
				} else {
					// logger.info("********Fetching value from Hash-Map for
					// zip="
					// + zip);
					dlsFlag = result;
				}
			}
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - zip.queryDayLightFlag query  for zip_geocode table Failed", exception);
		}
		return dlsFlag;
	}

	public String getStateForZip(String zipCode) throws DataServiceException {
		String state = null;
		try {
			if (zipCode != null && StringUtils.isNotEmpty(zipCode)) {
				state = (String) queryForObject("zip.queryStateForZip", zipCode);
			}
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - zip.queryStateForZip query  for zip_geocode table Failed", exception);
		}
		return state;
	}

	public List<LookupVO> getMinimumRatings() throws DataServiceException {
		List<LookupVO> ratingsList = null;
		try {
			ratingsList = queryForList("lookup.minimumrating", null);

		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getMinimumRatings() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.minimumrating query failed", ex);
		}
		return ratingsList;

	}

	public List<LookupVO> getResourceDistList() throws DataServiceException {
		List<LookupVO> distanceList = null;
		try {
			distanceList = queryForList("lookup.resourcedistance", null);

		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getResourceDistList() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.resourcedistance query failed", ex);
		}
		return distanceList;
	}

	public List<LookupVO> getProviderTopSelectList() throws DataServiceException {
		List<LookupVO> selectTopList = null;
		try {
			selectTopList = queryForList("lookup.provider_top_select", null);

		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getproviderTopSelectList() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.provider_top_select query failed", ex);
		}
		return selectTopList;
	}

	public List<LookupVO> getTaxPayerTypeIdList() throws DataServiceException {
		try {
			List<LookupVO> list = queryForList("lookup.getTaxPayerTypeIdList", null);
			return list;
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getTaxPayerTypeIdList() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.getTaxPayerTypeIdList query failed", ex);
		}

	}

	public List<LookupVO> getPerformanceLevels() throws DataServiceException {
		return queryForList("spn_performance_levels.query", null);
	}

	
	public ArrayList<LookupVO> getPartStatus() throws DataServiceException {

		ArrayList<LookupVO> sc = null;
		try {
			sc = (ArrayList) queryForList("lookup.partStatus", null);
		} catch (Exception exception) {
			throw new DataServiceException("LookupDao - lookup.partStatus query  for lu_part_status table Failed", exception);
		}
		return sc;
	}

	/**
	 * This method retrieves parent node id for a subCategoryId.
	 * @param Integer subCategoryId
	 * @return Integer
	 * throws DataServiceException
	 */
	public Integer getParentNodeId(Integer subCategoryId) throws DataServiceException{
		try{
			Integer parentNodeId= (Integer)queryForObject("getParentNodeFromSkillTree.query", subCategoryId);
			return parentNodeId;
		}catch(Exception ex){
			logger.error("LookupDaoImpl.getParentNodeId() - Exception : ", ex);
			throw new DataServiceException("LookupDao - getParentNodeFromSkillTree.query failed", ex);
		}		
	}
	/**
	 * This method retrieves skills from lu_service_type_template table.	 
	 * @return List
	 * throws DataServiceException
	 */
	public ArrayList<LookupVO> getServiceTypeTemplate() throws DataServiceException {
		ArrayList<LookupVO> al = null;
		try {
			al = (ArrayList) queryForList("lookup.serviceTypeTemplate", null);

		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getStateCodes() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.Languages query failed", ex);
		}
		return al;
	}
	/**
	 * This method retrieves markets between the startIndex and endIndex.	 
	 * @return List
	 * throws DataServiceException
	 */
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex) throws DataServiceException {
		List<LookupVO> al = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("startIndex", sIndex);
		paramMap.put("endIndex", eIndex);		
		try {
			al = queryForList("lookup.markets.byIndex", paramMap);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getMarkets() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.getMarkets() query failed", ex);
		}

		return al;
}
	/**
	 * This method retrieves ratings for insurance.
	 * @return InsuranceRatingsLookupVO
	 * throws DataServiceException
	 */
	public List<LookupVO> getInsuranceRatings() throws DataServiceException{
		List<LookupVO> insuranceList = new ArrayList<LookupVO>();
		try {
			insuranceList = queryForList("lookup.insuranceRating", null);

		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getMinimumRatings() - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("LookupDao - lookup.minimumrating query failed", ex);
		}
		return insuranceList;
	}
	
	/**
	 * This method is used to fetch all substatus
	 * 
	 * @return List<LookupVO>
	 */
	public List<LookupVO> getSubStatusList(Integer statusId){
		List<LookupVO> subStatusList = null;
		try{			
			subStatusList = queryForList("subStatusList.query", statusId);						
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return subStatusList;
	}
	public List<LookupVO> getNotBlackedOutStateCodes() throws DataServiceException {
		List<LookupVO> al = null;

		try {
			al = (ArrayList) queryForList("lookup.getnotblackoutstatecodes", null);
		} catch (Exception ex) {
			logger.error("[LookupDaoImpl.getStateCodes() - Exception] ", ex);
			throw new DataServiceException("LookupDao - lookup.statecodes query failed", ex);

		}
		return al;
	}
	
	public List<String> getCountryList() throws DBException{
		List<String> result;
		try {
			result = queryForList("getCountryList.query", null);
		} catch (Exception ex) {
			
			throw new DBException("General Exception @LookupDAOImpl.getCountryList() due to " + ex.getMessage(), ex);
		}
		return result;
		
	}
	/**
	 * Retrieves document types  by buyer id, to be listed in document manager
	 * @param buyerId,source
	 * @return List<LookupVO>
	 * @throws DataServiceException
	  */
	public List<LookupVO> retrieveLookUpDocumentByBuyerId(Integer buyerId,Integer source) 
	{
		List<LookupVO> buyerLookupDoc = null;
		try {
			buyerLookupDoc =  queryForList("lookup.LookUpDocument", source);
			} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            //throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
       	    }
		return buyerLookupDoc;
	}
	/**
	 * This method retrieves category id for a buyerId.
	 * @param Integer buyerId
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuCategoryVO> fetchBuyerSkuCategories(Integer buyerId)
	{
		List<BuyerSkuCategoryVO> buyerSkuCategoryList = null;
		try {
			buyerSkuCategoryList =  queryForList("lookup.buyerSkuCategoryList",buyerId);
			} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            //throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
       	    }
		return buyerSkuCategoryList;
	}
	/**
	 * This method retrieves sku name for a categoryid.
	 * @param Integer buyerId
	 * @return List
	*/
	@SuppressWarnings("unchecked")
	public List<BuyerSkuVO>fetchBuyerSkuNameByCategory(Integer categoryId)
	{
		List<BuyerSkuVO> buyerSkuNameList = null;
		try {
			buyerSkuNameList =  queryForList("lookup.buyerSkuNameList",categoryId);
			} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            //throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
       	    }
		return buyerSkuNameList;
	}
	
	/**
	 * This method retrieves sku details for a skuid.
	 * @param Integer buyerId
	 * @return List
	*/
	@SuppressWarnings("unchecked")
	public BuyerSkuTaskForSoVO fetchBuyerSkuDetailBySkuId(Integer skuId)
	{
		BuyerSkuTaskForSoVO buyerSkuDetailBySkuId=null;
		try {
			buyerSkuDetailBySkuId =  (BuyerSkuTaskForSoVO) queryForObject("lookup.buyerSkuDetailBySkuId",skuId);
			} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            //throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
       	    }
		return buyerSkuDetailBySkuId;
	}
	/**
	 * This method retrieves bid price for the selected  skuid.
	 * @param Integer serviceTypeTemplateId
	 * @return LuServiceTypeTemplateVO
	*/
	public Double fetchBidPriceBySkuId(Integer skuId)
	{
		Double skuBidPrice=0.0;
		try {
			skuBidPrice =  (Double) queryForObject("lookup.bidPriceBySkuId",skuId);
			} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            //throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
       	    }
		return skuBidPrice;
		
	}
	/**
	 * This method retrieves template details for a serviceTypeTemplateId on the basis of skuid.
	 * @param Integer serviceTypeTemplateId
	 * @return LuServiceTypeTemplateVO
	*/
	public LuServiceTypeTemplateVO fetchServiceTypeTemplate(Integer serviceTypeTemplateId)
	{
		LuServiceTypeTemplateVO luServiceTemplate=null;
		try {
		luServiceTemplate=(LuServiceTypeTemplateVO) queryForObject("lookup.buyerFetchTemplateDetail",serviceTypeTemplateId);
		} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            //throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
       	    }
		return luServiceTemplate;
	}
	public SkillTreeForSkuVO fetchSkillTreeDetailBySkuId(Integer nodeId) 
	{
		SkillTreeForSkuVO buyerSkillTreeDetail=null;
		try {
		buyerSkillTreeDetail=(SkillTreeForSkuVO) queryForObject("lookup.buyerSkillTreeDetail", nodeId);
		} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            //throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
       	    }
		return buyerSkillTreeDetail;
	}
	/**
	 * This method retrieves template details  on the basis of template id.
	 * @param Integer templateId
	 * @return BuyerSOTemplateDTO
	*/
	public BuyerSOTemplateForSkuVO fetchBuyerTemplateDetailBySkuId(Integer templateId) 
	{
		
		BuyerSOTemplateForSkuVO templateDetail=null;
		templateDetail=(BuyerSOTemplateForSkuVO) queryForObject("lookup.buyerTemplateDetail",templateId);
			return templateDetail;
	
	}
	/**
	 * This method retrieves all documents related to template  on the basis of template id.
	 * @param Integer documentType
	 * @return BuyerSOTemplateDTO
	*/
	@SuppressWarnings("unchecked")
	public  List<DocumentVO> retrieveDocumentByTitleAndEntityID(String title,Integer buyerId)
	{
		               
		List<DocumentVO> documentVO = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("buyer_id", Integer.toString(buyerId.intValue()));
		try {
			documentVO = queryForList("lookup.query_buyerdocument_by_title", params);
			
        }
        catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
        }                
        return documentVO; 
      }
	
	/**
	 * Fetch view prov doc permission for buyer
	 * @param String userName
	 * @return String
	*/
	@SuppressWarnings("unchecked")
	public boolean getViewDocPermission(String userName){
		boolean viewDocPermission = false;
		try {
			Integer count = (Integer)queryForObject("lookup.buyerViewDocPermission", userName);	
			if(1 == count){
				viewDocPermission = true;
			}
        }
        catch (Exception ex) {
        	logger.error("Error while retrieving viewDocPermission in BuyerDocumentDaoImpl.getViewDocPermission()", ex);
        }                
        return viewDocPermission; 
	}
	
	public ServiceOrder fetchSODetails(String soId){
		ServiceOrder so = new ServiceOrder();
		try{
		so = (ServiceOrder)queryForObject("lookup.fetchSODetails.query",soId);
		
		}
		catch (Exception ex) {
        	logger.error("Error while retrieving viewDocPermission in fetchSODetails()", ex);
        } 
		return so;
	}
	
	public String getStateRegulationNote(Integer stateRegulationReasonCodeId) {
		String stateRegulationNote = null;
		try {
			stateRegulationNote = (String) queryForObject("lookup.getStateRegulationNote.query", stateRegulationReasonCodeId);
		} catch (DataAccessException e) {
			logger.error("Error in fetching state regulation note : ", e);
			e.printStackTrace();
		}
		return stateRegulationNote;
	}

	public String getIRSlevyNote(Integer IRSlevyReasonCodeId) {
		String getIRSlevyNote = null;
		try {
			getIRSlevyNote = (String) queryForObject("lookup.getIRSlevyNote.query", IRSlevyReasonCodeId);
		} catch (DataAccessException e) {
			logger.error("Error in fetching IRS levy note : ", e);
			e.printStackTrace();
		}
		return getIRSlevyNote;
	}

	public String getLegalHoldNote(Integer legalHoldReasonCodeId) {
		String legalHoldNote = null;
		try {
			legalHoldNote = (String) queryForObject("lookup.getLegalHoldNote.query", legalHoldReasonCodeId);
		} catch (DataAccessException e) {
			logger.error("Error in fetching legal hold note : ", e);
			e.printStackTrace();
		}
		return legalHoldNote;
	}
	
	public Integer checkLegalHoldPermission(String adminUserName) {
		Integer isLegalHoldPermissionAssigned = null;
	/*	Map <String,Object> params = new HashMap<String, Object>();
		params.put("firstName", firstName);
		params.put("lastName", lastName);*/
		try {
			isLegalHoldPermissionAssigned = (Integer) queryForObject("lookup.checkLegalHoldPermission.query", adminUserName);
		} catch (DataAccessException e) {
			logger.error("Error in fetching legal hold pemission status : ", e);
			e.printStackTrace();
		}
		return isLegalHoldPermissionAssigned;
	}
}
