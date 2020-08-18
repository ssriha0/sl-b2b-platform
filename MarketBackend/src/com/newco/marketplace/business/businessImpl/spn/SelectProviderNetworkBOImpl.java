package com.newco.marketplace.business.businessImpl.spn;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.CredentialParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.LanguageParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.MinimumSoClosedParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.SPNInsuranceParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.StarParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.VendorCredentialParameterBean;
import com.newco.marketplace.business.businessImpl.skillTree.MarketplaceSearchBean;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.dto.vo.providerSearch.InsuranceResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderLanguageResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.providerSearch.SPNProviderSearchVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNMemberSearchResults;
import com.newco.marketplace.dto.vo.spn.SPNNetworkResourceVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderProfileBuyerVO;
import com.newco.marketplace.dto.vo.spn.SPNSkillVO;
import com.newco.marketplace.dto.vo.spn.SPNSummaryVO;
import com.newco.marketplace.dto.vo.spn.SPNetCommMonitorVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDao;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.persistence.iDao.spn.SPNDao;
import com.newco.marketplace.vo.PaginationVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:45 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SelectProviderNetworkBOImpl implements ISelectProviderNetworkBO {

	private static final Logger logger = Logger.getLogger(SelectProviderNetworkBOImpl.class.getName());
	
	private SPNDao spnDAO;
	private ProviderSearchDao providerSearchDao;
	private MarketplaceSearchBean marketplaceSkillSearch;
	private IMasterCalculatorBO masterCalculatorBO;
	private BuyerDao buyerDao;
	private IVendorCredentialsDao vendorCredentialDAO;
	private LookupDao lookupDAO;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#getSPNByBuyerId(java.lang.Integer)
	 */
	public List<SPNSummaryVO> getSPNByBuyerId(Integer buyerId)
			throws BusinessServiceException {
		List<SPNSummaryVO> returnList = null;

		try {
			returnList = spnDAO.getSPNSummariesByBuyerId(buyerId);
			if (null == returnList) {
				returnList = new ArrayList<SPNSummaryVO>();
			}
		} catch (DataServiceException e) {
			logger.error("Error returned trying to get SPN list for buyerId:" + buyerId,e);
			throw new BusinessServiceException("Error returned trying to get SPN list for buyerId:" + buyerId,e);
		}
			
		return returnList;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#deleteSPN(java.lang.Integer)
	 */
	public void deleteSPN(Integer spnId) throws BusinessServiceException {
		try {
			spnDAO.deleteSPN(spnId);
		} catch (Exception e) {
			logger.error("Error returned trying to delete SPN for SPN_ID:" + spnId,e);
			throw new BusinessServiceException("Error returned trying to delete SPN for SPN_ID:" + spnId,e);
		}
		return;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#insertSPN(com.newco.marketplace.dto.vo.spn.SPNHeaderVO)
	 */
	public Integer insertSPN(SPNHeaderVO spnHeaderVO)
			throws BusinessServiceException {
		
		Integer spnId = null;
		try {
			spnId = spnDAO.insertSPN(spnHeaderVO);
		} catch (Exception e) {
			logger.error("Error returned trying to insert SPN",e);
			throw new BusinessServiceException("Error returned trying to insert SPN",e);
		}
		return spnId;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#updateSPN(com.newco.marketplace.dto.vo.spn.SPNHeaderVO)
	 */
	public void updateSPN(SPNHeaderVO spnHeaderVO)
			throws BusinessServiceException {
		try {
			spnDAO.updateSPN(spnHeaderVO);
		} catch (Exception e) {
			logger.error("Error returned trying to update SPN",e);
			throw new BusinessServiceException("Error returned trying to update SPN",e);
		}
		return;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#getSPNBySPNId(java.lang.Integer)
	 */
	public SPNHeaderVO getSPNBySPNId(Integer spnId)
			throws BusinessServiceException {
		
		SPNHeaderVO spnHeaderVO = null;
		try {
			spnHeaderVO = spnDAO.getSPNHeaderBySPNId(spnId);	
			spnHeaderVO.setSpnCriteriaVO(getSPNCriteriaBySPNId(spnId));
		} catch (DataServiceException e) {
			logger.error("Error returned trying to retrieve SPN header for: " + spnId,e);
			throw new BusinessServiceException("Error returned trying to retrieve SPN header for: " + spnId,e);
		}
		return spnHeaderVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#getSPNCriteriaBySPNId(java.lang.Integer)
	 */
	public SPNCriteriaVO getSPNCriteriaBySPNId(Integer spnId)
			throws BusinessServiceException {
		SPNCriteriaVO spnCriteriaVO = null;
		
		try {
			spnCriteriaVO = spnDAO.getSPNCriteriaBySPNId(spnId);
		} catch (DataServiceException e) {
			logger.error("Error returned trying to retrieve SPN criteria for: " + spnId,e);
			throw new BusinessServiceException("Error returned trying to retrieve SPN criteria for: " + spnId,e);
		}
		return spnCriteriaVO;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#loadSPNSummaryTable()
	 */
	public void loadSPNSummaryTable() throws BusinessServiceException {
		try {
			spnDAO.deleteSPNHeaderSummary();
			spnDAO.loadSPNHeaderSummarty();
		} catch (DataServiceException e) {
			logger.error("Error returned trying to load SPN summary table",e);
			throw new BusinessServiceException("Error returned trying to load SPN summary table",e);
		}
		return;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#runSPNCampaign()
	 */
	public void runSPNCampaign() throws BusinessServiceException {
		
		// get all campaigns that need to be run
		
		try {
			List<SPNCampaignVO> campaigns = spnDAO.getActiveSPNCampaigns();
			
			// loop through each campaign and run it
			int serviceProsInvited = 0;
			for (SPNCampaignVO spnCampaignVO : campaigns) {
				if (spnCampaignVO.isCampaignIsTargetedToAllMarkets()) {
					List<LookupVO> markets = lookupDAO.getMarkets();
					for (LookupVO market : markets) {
						int matchCounter = inviteNewServiceProsForSPN(spnCampaignVO.getSpnId(), new Integer(market.getType()));
						serviceProsInvited += matchCounter;
					}
				} else {
					serviceProsInvited = inviteNewServiceProsForSPN(spnCampaignVO.getSpnId(), spnCampaignVO.getMarketId());
				}
				
				// update match count if we invited anybody
				if (serviceProsInvited != 0) {
					spnDAO.updateSPNCampaignMatchCnt(spnCampaignVO.getCampaignId(), 
						new Integer(spnCampaignVO.getTotalProviderCnt().intValue() + serviceProsInvited));
					
					// lock spn
					spnDAO.lockSPN(spnCampaignVO.getSpnId());
				}
			}
		} catch (DataServiceException e) {
			logger.error("Error running spn campains",e);
			throw new BusinessServiceException("Error running spn campains",e);
		}
		
		return;
	}

	/**
	 * @param spnId
	 * @param marketId
	 * @param mainCategory
	 * @param skillNodes
	 * @param skillTypes
	 * @return
	 * @throws BusinessServiceException
	 */
	private List<ProviderResultVO> getProviderListBySkillForSPN(Integer spnId, Integer marketId, Integer mainCategory,
			List<Integer> skillNodes, List<Integer> skillTypes) throws BusinessServiceException {

		List<SPNProviderSearchVO> listOfProviders = null;
		List<SkillNodeVO> parentSkillStructure;
		// collectionOfParentStructs will contain the parent hierarchy for each skill passed in
		List<List<SkillNodeVO>> collectionOfParentStructs = new ArrayList<List<SkillNodeVO>>(); 
		
		// separate criteria and figure out what needs to be queried
		List<Integer> skillTypeIds = skillTypes;
		
		// if the user did not select a skill type we want the list to be null
		if (null != skillTypeIds && skillTypeIds.size() == 1 && skillTypeIds.get(0).intValue() == -1) {
			skillTypeIds = null;
		}

		try {
			if(skillNodes != null && skillNodes.size() > 0){
				for (Integer skillNodeId : skillNodes) {
					int iSkillNodeId = skillNodeId.intValue();
					parentSkillStructure = marketplaceSkillSearch.getParentSkillStructById(iSkillNodeId);
					// add the hierarchy for the node to the collection
					collectionOfParentStructs.add(parentSkillStructure);
				}
				
				// get service providers that match the skill node(s)
				for (List<SkillNodeVO> parentStruct : collectionOfParentStructs) {
					List<Integer> skillNodeIds = new ArrayList<Integer>();
	
					for (SkillNodeVO skillNode : parentStruct) {
						skillNodeIds.add(skillNode.getNodeId());
					}
					
					listOfProviders = providerSearchDao.getNewServiceProsBySkillForSPN(spnId, marketId, mainCategory, skillNodeIds, skillTypeIds);
				}
			}
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Unable to get list of service pros that meet passed in skills",e);
		}
		
		// build ProviderResultVO's
		List<ProviderResultVO> providerResultsVOList = new ArrayList<ProviderResultVO>();
		ProviderResultVO providerResultVO = null;
		for (SPNProviderSearchVO searchVO : listOfProviders) {
			providerResultVO = new ProviderResultVO();
			
			SurveyRatingsVO surveyRatingsVO = new SurveyRatingsVO();
			surveyRatingsVO.setHistoricalRating(searchVO.getAggregateRatingScoreByNode());
			surveyRatingsVO.setNumberOfRatingsReceived(searchVO.getAggregateRatingCount());
			
			providerResultVO.setResourceId(searchVO.getResourceId());
			providerResultVO.setProviderStarRating(surveyRatingsVO);
			providerResultVO.setTotalSOCompleted(searchVO.getTotalSOCompleted());
			providerResultVO.setVendorID(searchVO.getVendorId());
			
			List<InsuranceResultVO> insuranceResultList = new ArrayList<InsuranceResultVO>();
			InsuranceResultVO insuranceResultVO = null;
			
			if (null != searchVO.getGeneralInsAmount()) {
				insuranceResultVO = new InsuranceResultVO();
				insuranceResultVO.setVendorInsuranceTypes(Constants.SPN.CRITERIA_TYPE.VENDOR_GENERAL_INS);
				insuranceResultVO.setAmount(searchVO.getGeneralInsAmount());
				insuranceResultList.add(insuranceResultVO);
			}
			if (null != searchVO.getAutoInsAmount()) {
				insuranceResultVO = new InsuranceResultVO();
				insuranceResultVO.setVendorInsuranceTypes(Constants.SPN.CRITERIA_TYPE.VENDOR_AUTO_INS);
				insuranceResultVO.setAmount(searchVO.getAutoInsAmount());
				insuranceResultList.add(insuranceResultVO);
			}
			if (null != searchVO.getWorkmanCompInsAmount()) {
				insuranceResultVO = new InsuranceResultVO();
				insuranceResultVO.setVendorInsuranceTypes(Constants.SPN.CRITERIA_TYPE.VENDOR_WORKMAN_INS);
				insuranceResultVO.setAmount(searchVO.getWorkmanCompInsAmount());
				insuranceResultList.add(insuranceResultVO);
			}
			
			providerResultVO.setVendorInsuranceTypes(insuranceResultList);
			
			try {
				providerResultVO.setVendorCredentials((List<VendorCredentialsVO>)vendorCredentialDAO.queryCredByVendorId(providerResultVO.getVendorID().intValue()));
			} catch (com.newco.marketplace.exception.DataServiceException e) {
				throw new BusinessServiceException("Unable to get list vendor credentials",e);
			}
			
			// you must load the languages here !!!!
			providerResultVO.setLanguages((ArrayList<ProviderLanguageResultsVO>)providerSearchDao.getProviderLanguagesForOneResource(searchVO.getResourceId()));
			providerResultsVOList.add(providerResultVO);
		}
		return providerResultsVOList;
	}

	
	public String getBuyerBusinessName(Integer buyerId)throws Exception {
		Buyer buyer = new Buyer();
		try {
			buyer.setBuyerId(buyerId);
			buyer = getBuyerDao().query(buyer);
		}catch (Exception e) {
			logger.error("getBuyerSourceId:: error looking up buyer, reason: "
					+ e.getMessage());
			throw e;
		
		}
		return buyer.getBusinessName();
	}

	/**
	 * Creates a new Select Provider Network Campaign entry 
	 * @param campaign - Value object used to represent input data for a SPN Campaign
	 * @throws BusinessServiceException
	 */
	public Integer createNewSPNCampaign(SPNCampaignVO campaign) throws BusinessServiceException {
		Integer insertId = null;
		try {
			insertId = getSpnDAO().createNewSPNCampaign(campaign);
		} catch (DataServiceException e) {
			logger.error("Error returned trying to create a SPN campaign",e);
			throw new BusinessServiceException("Error returned trying to create a SPN campaign",e);
		}
		return insertId;
		
	}

	/**
	 * Retrieves a list of Select Provider Network Campaigns
	 * @param spnId
	 * @throws BusinessServiceException
	 */
	public List<SPNCampaignVO> loadAllSPNCampaigns(Integer spnId) throws BusinessServiceException {
		List<SPNCampaignVO> returnList = null;	
		try {
			returnList = getSpnDAO().loadAllSPNCampaigns(spnId);
			if (null == returnList) {
				returnList = new ArrayList<SPNCampaignVO>();
			}
		} catch (DataServiceException e) {
			logger.error("Error returned trying to get SPN list for spnId:" + spnId,e);
			throw new BusinessServiceException("Error returned trying to get SPN list for spnId:" + spnId,e);
		}
		return returnList;
	}

	/**
	 * Used to create a 'soft delete' on a Select Provider Network Campaign
	 * Data is not permanetly removed 
	 * @param campaignId - id of current selected campaign
	 * @throws BusinessServiceException
	 */
	public Integer deleteSPNCampaign(Integer campaignId) throws BusinessServiceException {
		Integer insertId = null;
		try {
			insertId = getSpnDAO().deleteSPNCampaign(campaignId);
		}catch(DataServiceException e) {
			logger.error("Error returned trying to remove campaign:" + campaignId,e);
			throw new BusinessServiceException("Error returned trying to remove campaign:" + campaignId,e);
		}
		return insertId;
	}
	
	
	public void updateStatusByProviderResponse(SPNNetworkResourceVO networkResourceVO) throws BusinessServiceException{
		try {
			spnDAO.updateProviderNetworkStatus(networkResourceVO);
		} catch (Exception e) {
			logger.error("Error returned trying to insert SPN",e);
			throw new BusinessServiceException("Error returned trying to insert SPN",e);
		}
		return;
	}
	
	
	public List<SPNHeaderVO> getAllSPNS() throws BusinessServiceException {
		List<SPNHeaderVO> returnList = null;	
		try {
			returnList = getSpnDAO().getAllSPNS();
			if (null == returnList) {
				returnList = new ArrayList<SPNHeaderVO>();
			}
		} catch (DataServiceException e) {
			logger.error("Error retriving SPN Networks:",e);
			throw new BusinessServiceException("Error retriving SPN Networks:",e);
		}
		return returnList;
	}
	
	public List<SPNHeaderVO> loadAllSpnNetworkInvites(Integer resourceId, Integer companyId) throws BusinessServiceException{
		List<SPNHeaderVO> spnList = new ArrayList<SPNHeaderVO>();
		
		try{
			boolean isAdmin = isProviderAdmin(resourceId); 
			
			if(isAdmin)
			{
				spnList = spnDAO.getSPNInviteListForAdmin(companyId);
			}
			else
			{
				spnList = spnDAO.getSPNInviteListForResource(resourceId);
			}
			
			
			if (null == spnList) {
				spnList = new ArrayList<SPNHeaderVO>();
			}
			
		}catch(Exception e){
			logger.error("Error retriving SPN Networks:",e);
			throw new BusinessServiceException("Error retriving SPN Networks:",e);
		}
		return spnList;
	}
	
	
	public boolean isProviderAdmin(Integer resourceId) throws BusinessServiceException{
		boolean isAdmin = false;
		try{
			Integer primaryIndicator = spnDAO.getIsAdminIndicator(resourceId);
			if(primaryIndicator!= null && primaryIndicator.intValue() == 1){
				isAdmin = true;
			}
		}catch(Exception e){
			logger.error("Error retriving provider primary Indicator for Id: " + resourceId,e);
			throw new BusinessServiceException("Error retriving provider primary Indicatorfor Id: " + resourceId,e);
		}
		
		return isAdmin;
		
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#removeMembers(java.util.List)
	 */
	public void removeMembers(List<Integer> spnNetworkIds)
			throws BusinessServiceException {
		try {
			spnDAO.removeMembers(spnNetworkIds);
		} catch (DataServiceException e) {
			logger.error("Error setting status to 'Removed'",e);
			throw new BusinessServiceException("Error setting status to 'Removed'",e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#approveMembers(java.util.List)
	 */
	public void approveMembers(List<Integer> spnNetworkIds)
			throws BusinessServiceException {
		try {
			spnDAO.approveMembers(spnNetworkIds);
		} catch (DataServiceException e) {
			logger.error("Error setting status to 'Member'",e);
			throw new BusinessServiceException("Error setting status to 'Member'",e);
		}
	}

	public List<SPNProviderProfileBuyerVO> getProviderProfileBuyers(Integer resourceId) throws BusinessServiceException{
		List<SPNProviderProfileBuyerVO> spnBuyersList = null;
		try{
			spnBuyersList = spnDAO.getProviderProfileSpns(resourceId);
		}catch(Exception e){
			logger.error("Error retriving providerSPNs List for Id: " + resourceId,e);
			throw new BusinessServiceException("Error retriving providerSPNs List for Id: " + resourceId,e);
		}
		
		return spnBuyersList;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#getSPNResources(com.newco.marketplace.dto.vo.serviceorder.CriteriaMap)
	 */
	public SPNMemberSearchResults getSPNResources(CriteriaMap criteria)
			throws BusinessServiceException {
		
		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO paginationVo = null;
		int totalRecordCount = 0;

		SPNMemberSearchResults searchList = new SPNMemberSearchResults();
		try {
			if(criteria != null){			
				PagingCriteria pagingCriteria = (PagingCriteria) criteria.get(OrderConstants.PAGING_CRITERIA_KEY);
								
				totalRecordCount = spnDAO.getPaginationCount(criteria);
				searchList.setSearchResults(spnDAO.getMembersByStatus(criteria));
				
				if(pagingCriteria != null){
					paginationVo = criteriaHandler._getPaginationDetail(
							totalRecordCount, pagingCriteria.getPageSize(),
							pagingCriteria.getStartIndex(), pagingCriteria
									.getEndIndex());			
					searchList.setPaginationVo(paginationVo);				
				}

			}
		}catch (DataServiceException e) {
			throw new BusinessServiceException("Unable to get search results",e);
		}
		return searchList;
	}

	/**
	 * This method is used to invite service providers to a spn.  The result of running this method
	 * will be new entries in the database
	 * @param spnId
	 * @param marketId
	 * @return number of service pros invited
	 * @throws BusinessServiceException
	 */
	private int inviteNewServiceProsForSPN (Integer spnId, Integer marketId) throws BusinessServiceException {	
		
		SPNCriteriaVO spnCriteriaVO = getSPNCriteriaBySPNId(spnId);
		Integer mainServiceCategoryId = null;
		List<ProviderResultVO> servicePros = null;
		
		try {
			if (null != spnCriteriaVO) {
				List<Integer> skillNodeIds = new ArrayList<Integer>();
				List<Integer> skillTypeIds = new ArrayList<Integer>();
				
				List<SPNSkillVO> skills = spnCriteriaVO.getSkills();
				if (null != skills && skills.size() > 0) {
					mainServiceCategoryId = skills.get(0).getMainCategory();
					
					// if the user has selected a skill node for the first task, then all
					// tasks have them.
					if (null != skills.get(0).getCategory()) {
						for (SPNSkillVO spnSkillVO : skills) {
							if(null != spnSkillVO.getSubCategory() 
									&& spnSkillVO.getSubCategory().intValue() != -1){
								skillNodeIds.add(spnSkillVO.getSubCategory());
							} else {
								if(null != spnSkillVO.getCategory()
									&& spnSkillVO.getCategory().intValue() != -1){
								skillNodeIds.add(spnSkillVO.getCategory());
								} else {
									skillNodeIds.add(spnSkillVO.getMainCategory());
								}
							}
							
							if(null != spnSkillVO.getSkill()){
								skillTypeIds.add(spnSkillVO.getSkill());
							} else {
								// For a particular skill node id we need to get the corresponding skill type id
								// If he didn't select a skill type, then add -1 to that particular skill node and 
								// don't apply % decrement 
								skillTypeIds.add(-1);
							}
						}
					} else {
						skillNodeIds.add(mainServiceCategoryId);
						skillTypeIds.add(-1);
					}
				}
				
				// get initial list
				servicePros = getProviderListBySkillForSPN(spnId, marketId, mainServiceCategoryId, skillNodeIds, skillTypeIds);

				// filter
				if (servicePros.size() > 0) {
					masterCalculatorBO.getFilteredProviderList(
							(ArrayList<RatingParameterBean>)buildRatingBean(spnCriteriaVO), 
							(ArrayList<ProviderResultVO>)servicePros);
					
					// insert into database
					if (servicePros.size() > 0) {
						spnDAO.insertSPNNetworkRecords(spnId, servicePros);
					}
				}
			}
		} catch (DataServiceException e) {
			logger.error("Error returned trying to retrieve new service pros for spn",e);
			throw new BusinessServiceException("Error returned trying to retrieve new service pros for spn",e);
		}
		
		return servicePros.size();
	}
	
	/**
	 * @param spnCriteriaVO
	 * @return
	 */
	private List<RatingParameterBean> buildRatingBean(SPNCriteriaVO spnCriteriaVO) {
		List<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();
		
		if (null != spnCriteriaVO.getResourceCredTypeId()) {
			CredentialParameterBean credentialBean = new CredentialParameterBean();
			credentialBean.setCredentialTypeId(spnCriteriaVO.getResourceCredTypeId());
			credentialBean.setCredentialId(spnCriteriaVO.getResourceCredCategoryId());
			ratingParamBeans.add(credentialBean);
		}
		
		if (null != spnCriteriaVO.getVendorCredTypeId()) {
			VendorCredentialParameterBean credentialBean = new VendorCredentialParameterBean();
			credentialBean.setCredentialTypeId(spnCriteriaVO.getVendorCredTypeId());
			credentialBean.setCredentialCategoryId(spnCriteriaVO.getVendorCredCategoryId());
			ratingParamBeans.add(credentialBean);
		}
		
		if (spnCriteriaVO.isInsGeneralLiability() || spnCriteriaVO.isInsAutoLiability() || spnCriteriaVO.isInsWorkmanComp()) {
			SPNInsuranceParameterBean insuranceBean = new SPNInsuranceParameterBean();
			ratingParamBeans.add(insuranceBean);
			if (spnCriteriaVO.isInsGeneralLiability()) {
				insuranceBean.setGeneralInsuranceChecked(spnCriteriaVO.isInsGeneralLiability());
				insuranceBean.setGeneralInsuranceMinAmount(spnCriteriaVO.getInsGeneralLiabilityMinAmt());
			}
			
			if (spnCriteriaVO.isInsAutoLiability()) {
				insuranceBean.setAutoInsuranceChecked(spnCriteriaVO.isInsAutoLiability());
				insuranceBean.setAutoInsuranceMinAmount(spnCriteriaVO.getInsAutoLiabilityMinAmt());
			}
			
			if (spnCriteriaVO.isInsWorkmanComp()) {
				insuranceBean.setWorkmanCompInsuranceChecked(spnCriteriaVO.isInsWorkmanComp());
				insuranceBean.setWorkmanCompInsuranceMinAmount(spnCriteriaVO.getInsWorkmanCompMinAmt());
			}
		}
		
		if (null != spnCriteriaVO.getStarRating()) {
			StarParameterBean starBean = new StarParameterBean();
			starBean.setNumberOfStars(spnCriteriaVO.getStarRating());
			starBean.setIncludeNonRated(spnCriteriaVO.isStarRatingIncludeNonRated());
			ratingParamBeans.add(starBean);
		}
		
		if (null != spnCriteriaVO.getLanguageId()) {
			LanguageParameterBean langBean = new LanguageParameterBean();
			List<Integer> langs = new ArrayList<Integer>();
			langs.add(spnCriteriaVO.getLanguageId());
			langBean.setSelectedLangs(langs);
			ratingParamBeans.add(langBean);
		}
		
		if (null != spnCriteriaVO.getMinSOClosed()) {
			MinimumSoClosedParameterBean minBean = new MinimumSoClosedParameterBean();
			minBean.setMinSoClosed(spnCriteriaVO.getMinSOClosed());
			ratingParamBeans.add(minBean);
		}

		return ratingParamBeans;
	}
	
	/**
	 * gets the SPN details for provider admin
	 * @param companyId
	 * @return SPNetCommMonitorVO
	 * @throws BusinessServiceException
	 */
	public List<SPNetCommMonitorVO> loadSPNetCommDetailsForPA(Integer companyId) throws BusinessServiceException {
		List <SPNetCommMonitorVO> spnetDetails = null;
		try {
			spnetDetails = spnDAO.getSPNetCommDetailsForPA(companyId);
			if (null == spnetDetails) {
				spnetDetails = new ArrayList<SPNetCommMonitorVO>();
			} else {
				for (SPNetCommMonitorVO spnetCommMonitorVOint : spnetDetails) {
					
					// Invitation status
					String invstatus = spnetCommMonitorVOint.getPfInvStatus();
					if(invstatus.equals(PF_INVITED_TO_SPN)){ 
						spnetCommMonitorVOint.setIsInvitation(SUCCESS_IND); 
					}else if(invstatus.equals(PF_SPN_NOT_INTERESTED)){
						spnetCommMonitorVOint.setIsNotInterestedInv(SUCCESS_IND);
					}
					if(spnetCommMonitorVOint.getIsMember() && APPLICANT_INCOMPLETE.equals(spnetCommMonitorVOint.getPfInvStatus())){
						spnetCommMonitorVOint.setIsInvitation(SUCCESS_IND); 
					}
					
					// Invitation Subject
					String spnName = spnetCommMonitorVOint.getSpnName();
					String communicationSubject = spnetCommMonitorVOint.getCommunicationSubject();
					String newCommmSubj = setSPNNameInSubject(spnName, communicationSubject);
					if (StringUtils.isNotBlank(newCommmSubj)) {
						spnetCommMonitorVOint.setCommunicationSubject(newCommmSubj);
					}
					
					// Check if invitation/campaign has expired
					Date campaignExpiryDate = spnetCommMonitorVOint.getCampEndDate();
					if (campaignExpiryDate.compareTo(new Date()) < 0) {
						spnetCommMonitorVOint.setIsInvExpired(SUCCESS_IND);
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Error retriving SPN Details for Provider Admin: ", ex);
			throw new BusinessServiceException("Error retriving SPN Details for Provider Admin: ", ex);
		}
		
		return spnetDetails;		
	}
	
	/**
	 * set the SPN name in communication subject for communication monitor
	 * @param spnName
	 * @param commSubj
	 * @return newCommSubj
	 */
	private String setSPNNameInSubject(String spnName,String commSubj){
		String newCommSubj = "";
		try{
			VelocityEngine ve = new VelocityEngine();
			StringWriter sw = new StringWriter();
			VelocityContext vContext = new VelocityContext();		
			vContext.put(SPN_NAME, spnName);			
			ve.evaluate(vContext, sw, VELOCITY_TEMPLATE, commSubj);
			if (sw == null){
				throw new Exception("Could not generate the message from template!");
			}			
			newCommSubj = sw.getBuffer().toString();			
		}catch(Exception e){
			logger.error("Error occurred while creating communication subject using velocity : ", e);			
		}
		return newCommSubj;		
	}
	/**
	 * This method is used to get a list of buyer agreements for the spn id. If
	 * no documents are found, then an empty list is returned.
	 * 
	 * @param spnId
	 * @return List<SPNDocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModal(Integer spnId)
			throws BusinessServiceException {
		List<SPNDocumentVO> returnList = null;
		try {
			returnList = spnDAO.getSPNBuyerAgreeModal(spnId);
			if (null == returnList) {
				returnList = new ArrayList<SPNDocumentVO>();
			}
		} catch (DataServiceException e) {
			logger.error(
					"Error returned trying to get document list for spnId:"
							+ spnId, e);
			throw new BusinessServiceException(
					"Error returned trying to get document list for spnId:"
							+ spnId, e);
		}
		return returnList;
	}

	/**
	 * This method is used to get the document content for a document id.
	 * 
	 * @param docId
	 * @return List<SPNDocumentVO>
	 * @throws BusinessServiceException 
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModalDocument(Integer docId)
			throws BusinessServiceException {
		List<SPNDocumentVO> returnList = null;
		try {
			returnList = spnDAO.getSPNBuyerAgreeModalDocument(docId);			
		} catch (DataServiceException e) {
			logger.error(
					"Error returned trying to get document content for docId:"
							+ docId, e);
			throw new BusinessServiceException(
					"Error returned trying to get document content for docId:"
							+ docId, e);
		}
		return returnList;
	}
	/**
	 * Submits Buyer Agreements of a SPN by a provider. 
	 * @param firmId
	 * @param spnId
	 * @throws BusinessServiceException
	 */
	public void submitSPNBuyerAgreement(Integer firmId,Integer spnId,String modifiedBy,Boolean auditRequired) throws BusinessServiceException {
		try {
			spnDAO.submitSPNBuyerAgreement(firmId,spnId,modifiedBy,auditRequired);			
		} catch (DataServiceException e) {
			logger.error("Error returned while trying to submit Buyer Agreements for spn :"+spnId+" by provider:"+ firmId, e);
			throw new BusinessServiceException("Error returned while trying to submit Buyer Agreements for spn :"+spnId+" by provider:"+ firmId, e);
		}
	}
	
	public void submitSPNBuyerAgreementForDoc (Integer firmId, String username, Integer spnDocId) throws BusinessServiceException {
		try {
			spnDAO.submitSPNBuyerAgreementForDoc(firmId, username, spnDocId);			
		} catch (DataServiceException e) {
			logger.error("Error returned while trying to submit Buyer Agreements for spn :"+spnDocId+" by provider:"+ firmId, e);
			throw new BusinessServiceException("Error returned while trying to submit Buyer Agreements for spn :"+spnDocId+" by provider:"+ firmId, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO#loadProviderInvitation(java.lang.Integer, java.lang.Integer)
	 */
	public SPNMainMonitorVO loadProviderInvitation(Integer spnId, Integer vendorId)
			throws BusinessServiceException {
		SPNMainMonitorVO invitationVO = new SPNMainMonitorVO();
		try {
			invitationVO = spnDAO.loadProviderInvitation(spnId, vendorId);
		} catch (DataServiceException e) {
			// Do not log exception, the exception should have already been logged at root
			throw new BusinessServiceException("Error returned while trying to get details of spn :"+spnId, e);
		}
		return invitationVO;
	}
	
	/**
	 * Changes the state of the provider to PF SPN NOT INTRESTED
	 * @param spnId
	 * @param vendorId
	 * @param rejectId
	 * @param rejectReason
	 * @throws BusinessServiceException
	 */
	public void rejectInvite(String rejectId,String rejectReason,Integer spnId,Integer vendorId,String modifiedBy) throws BusinessServiceException{
		
		try{
			spnDAO.rejectInvite(rejectId,rejectReason,spnId,vendorId,modifiedBy);
		}catch(DataServiceException e){
			logger.error("Error returned trying to change the state of the provider",e);
			throw new BusinessServiceException("Error returned trying to change the state of the provider",e);
		}		
	}
	/**
	 * @return the providerSearchDao
	 */
	public ProviderSearchDao getProviderSearchDao() {
		return providerSearchDao;
	}

	/**
	 * @param providerSearchDao the providerSearchDao to set
	 */
	public void setProviderSearchDao(ProviderSearchDao providerSearchDao) {
		this.providerSearchDao = providerSearchDao;
	}

	/**
	 * @return the marketplaceSkillSearch
	 */
	public MarketplaceSearchBean getMarketplaceSkillSearch() {
		return marketplaceSkillSearch;
	}

	/**
	 * @param marketplaceSkillSearch the marketplaceSkillSearch to set
	 */
	public void setMarketplaceSkillSearch(
			MarketplaceSearchBean marketplaceSkillSearch) {
		this.marketplaceSkillSearch = marketplaceSkillSearch;
	}

	/**
	 * @return the masterCalculatorBO
	 */
	public IMasterCalculatorBO getMasterCalculatorBO() {
		return masterCalculatorBO;
	}

	/**
	 * @param masterCalculatorBO the masterCalculatorBO to set
	 */
	public void setMasterCalculatorBO(IMasterCalculatorBO masterCalculatorBO) {
		this.masterCalculatorBO = masterCalculatorBO;
	}
	
	
	public BuyerDao getBuyerDao() {
		return buyerDao;
	}

	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}

	/**
	 * @return the vendorCredentialDAO
	 */
	public IVendorCredentialsDao getVendorCredentialDAO() {
		return vendorCredentialDAO;
	}

	/**
	 * @param vendorCredentialDAO the vendorCredentialDAO to set
	 */
	public void setVendorCredentialDAO(IVendorCredentialsDao vendorCredentialDAO) {
		this.vendorCredentialDAO = vendorCredentialDAO;
	}

	/**
	 * @return the lookupDAO
	 */
	public LookupDao getLookupDAO() {
		return lookupDAO;
	}

	/**
	 * @param lookupDAO the lookupDAO to set
	 */
	public void setLookupDAO(LookupDao lookupDAO) {
		this.lookupDAO = lookupDAO;
	}
	
	/**
	 * @return the spnDAO
	 */
	public SPNDao getSpnDAO() {
		return spnDAO;
	}

	/**
	 * @param spnDAO the spnDAO to set
	 */
	public void setSpnDAO(SPNDao spnDAO) {
		this.spnDAO = spnDAO;
	}
	
	
}
/*
 * Maintenance History:
 * $Log: SelectProviderNetworkBOImpl.java,v $
 * Revision 1.2  2008/05/02 21:23:45  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.26  2008/04/30 22:18:44  mhaye05
 * new tests
 *
 * Revision 1.1.2.25  2008/04/29 15:53:23  mhaye05
 * updates for spn member manager
 *
 * Revision 1.1.2.24  2008/04/28 22:14:39  rgurra0
 * progress in SPN Provider profile
 *
 * Revision 1.1.2.23  2008/04/28 18:54:54  rgurra0
 * added method to get  Provider Profile SPN info
 *
 * Revision 1.1.2.22  2008/04/25 14:46:10  mhaye05
 * removed useless logging
 *
 * Revision 1.1.2.21  2008/04/24 16:01:50  mhaye05
 * fixes for spn campaign running batch job
 *
 * Revision 1.1.2.20  2008/04/23 14:01:01  mhaye05
 * backend for spn camaign batch job
 *
 * Revision 1.1.2.19  2008/04/23 04:19:59  rgurra0
 *  IsProviderAdmin(Integer) method added
 *
 * Revision 1.1.2.18  2008/04/22 20:37:07  cgarc03
 * Latest changes/additions related to loading SPN Invite list on dashboard.
 *
 * Revision 1.1.2.17  2008/04/22 18:45:59  rgurra0
 * loadAllSpnNetworkInvitesForAdmin -- return type changed
 *
 * Revision 1.1.2.16  2008/04/22 17:53:35  rgurra0
 * method to get list of invites
 *
 * Revision 1.1.2.15  2008/04/18 22:46:05  mhaye05
 * updated to allow for spn save and the running of spn campaigns
 *
 * Revision 1.1.2.14  2008/04/18 22:09:46  dmill03
 * *** empty log message ***
 *
 * Revision 1.1.2.13  2008/04/18 00:23:53  mhaye05
 * added logic for inviting service pros to spn
 *
 * Revision 1.1.2.12  2008/04/17 16:46:45  rgurra0
 * added updateSpn Status
 *
 * Revision 1.1.2.11  2008/04/16 23:04:01  dmill03
 * added campaign backend functions
 *
 * Revision 1.1.2.10  2008/04/16 15:43:58  rgurra0
 * method to get Business name
 *
 * Revision 1.1.2.9  2008/04/16 14:43:12  mhaye05
 * updated setSPNBySPNId(spnId) to also get the criteria
 *
 * Revision 1.1.2.8  2008/04/11 14:30:54  mhaye05
 * removed attribute that is not needed anymore
 *
 * Revision 1.1.2.7  2008/04/10 22:23:59  mhaye05
 * updated with removed class
 *
 * Revision 1.1.2.6  2008/04/10 21:49:01  mhaye05
 * updates
 *
 * Revision 1.1.2.5  2008/04/10 20:26:44  mhaye05
 * renamed method
 *
 * Revision 1.1.2.4  2008/04/10 16:18:01  mhaye05
 * updates to all for a scheduled job to load the summary table
 *
 * Revision 1.1.2.3  2008/04/10 00:50:49  mhaye05
 * added additional functions
 *
 * Revision 1.1.2.2  2008/04/09 20:00:49  mhaye05
 * added insert, update, and delete methods
 *
 * Revision 1.1.2.1  2008/04/09 19:31:12  mhaye05
 * Initial check in
 *
 */