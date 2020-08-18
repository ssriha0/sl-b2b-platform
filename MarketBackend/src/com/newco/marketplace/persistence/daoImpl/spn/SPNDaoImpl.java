package com.newco.marketplace.persistence.daoImpl.spn;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SPNFilterCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.spn.BackgroundCheckHistoryVO;
import com.newco.marketplace.dto.vo.spn.BackgroundInfoProviderVO;
import com.newco.marketplace.dto.vo.spn.ComplianceCriteriaVO;
import com.newco.marketplace.dto.vo.spn.ProviderMatchApprovalCriteriaVO;
import com.newco.marketplace.dto.vo.spn.ProviderMatchingCountsVO;
import com.newco.marketplace.dto.vo.spn.SPNAgreeDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNApprovalCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNComplianceVO;
import com.newco.marketplace.dto.vo.spn.SPNCriteriaDBVO;
import com.newco.marketplace.dto.vo.spn.SPNCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNExclusionsVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNInvitationNoInterestVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNMemberSearchRequestVO;
import com.newco.marketplace.dto.vo.spn.SPNMemberSearchResultVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNNetworkResourceVO;
import com.newco.marketplace.dto.vo.spn.SPNProvUploadedDocsVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderProfileBuyerVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderRequirementsVO;
import com.newco.marketplace.dto.vo.spn.SPNSkillVO;
import com.newco.marketplace.dto.vo.spn.SPNSummaryVO;
import com.newco.marketplace.dto.vo.spn.SPNetCommMonitorVO;
import com.newco.marketplace.dto.vo.spn.SearchBackgroundInfoProviderVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.persistence.iDao.spn.SPNDao;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.sears.os.dao.impl.ABaseImplDao;



/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:45 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNDaoImpl extends ABaseImplDao implements SPNDao {

	private static final Logger logger = Logger.getLogger(SPNDaoImpl.class.getName());
	
	private DocumentService documentService;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getSPNSummariesByBuyerId(java.lang.Integer)
	 */
	public List<SPNSummaryVO> getSPNSummariesByBuyerId(Integer buyerId)
			throws DataServiceException {
		
		List<SPNSummaryVO> list = null;
		
		try {
			list = queryForList("spn_hdr_summary_by_buyer.query", buyerId);
			if (null != list) {
				// need to set total matches
				for (SPNSummaryVO spnSummaryVO : list) {
					spnSummaryVO.setMatchesCnt(new Integer(
							spnSummaryVO.getInvitedCnt().intValue() + 
							spnSummaryVO.getApplicantCnt().intValue() +
							spnSummaryVO.getNotInterestedCnt().intValue() +
							spnSummaryVO.getMemberCnt().intValue() +
							spnSummaryVO.getInactiveCnt().intValue() +
							spnSummaryVO.getRemovedCnt().intValue()));
				}
				
			} else {
				list = new ArrayList<SPNSummaryVO>();
			}
		} catch (DataAccessException e) {
			logger.error("Error returned trying to SPN summary data for buyer: " + buyerId,e);
			throw new DataServiceException("Error returned trying to SPN summary data for buyer: " + buyerId,e);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getSPNHeaderBySPNId(java.lang.Integer)
	 */
	public SPNHeaderVO getSPNHeaderBySPNId(Integer spnId)
			throws DataServiceException {
		
		SPNHeaderVO spnHeaderVO = null;
		
		try {
			spnHeaderVO = (SPNHeaderVO)queryForObject("spn_header_by_spn_id.query", spnId);
		} catch (DataAccessException e) {
			logger.error("Error returned trying to SPN header for: " + spnId,e);
			throw new DataServiceException("Error returned trying to retrieve SPN header for: " + spnId,e);
		}
		return spnHeaderVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#deleteSPN(java.lang.Integer)
	 */
	public void deleteSPN(Integer spnId) throws DataServiceException {
		// TODO remove from template
		
		try {
			delete("spn_docs.delete", spnId);
			delete("spn_criteria.delete",spnId);
			delete("spn_network_summary.delete",spnId);
			delete("spn_network.delete",spnId);
			delete("spn_campaigns.delete",spnId);
			delete("spn_header.delete", spnId);
		} catch (DataAccessException e) {
			logger.error("Error returned trying to delete SPN: " + spnId,e);
			throw new DataServiceException("Error returned trying to delete SPN: " + spnId,e);
		}
		return;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#insertSPN(com.newco.marketplace.dto.vo.spn.SPNHeaderVO)
	 */
	public Integer insertSPN(SPNHeaderVO spnHeaderVO) throws DataServiceException {
		Integer spnId = null;
		try {
			spnId = (Integer) insert("spn_header.insert", spnHeaderVO);
			loadCriteria(spnId, spnHeaderVO.getSpnCriteriaVO());
			loadSPNRefDocuments(spnId, spnHeaderVO.getSpnRelatedDocumentIds());
		} catch (DataAccessException e) {
			logger.error("Error returned trying to insert SPN",e);
			throw new DataServiceException("Error returned trying to insert SPN",e);
		}
		return spnId;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#updateSPN(com.newco.marketplace.dto.vo.spn.SPNHeaderVO)
	 */
	public void updateSPN(SPNHeaderVO spnHeaderVO) throws DataServiceException {

		try {
			update("spn_header.update", spnHeaderVO);

			delete("spn_criteria.delete", spnHeaderVO.getSpnId());
			loadCriteria(spnHeaderVO.getSpnId(), spnHeaderVO.getSpnCriteriaVO());
			
			delete("spn_docs.delete", spnHeaderVO.getSpnId());
			loadSPNRefDocuments(spnHeaderVO.getSpnId(), spnHeaderVO.getSpnRelatedDocumentIds());
		} catch (DataAccessException e) {
			logger.error("Error returned trying to update SPN",e);
			throw new DataServiceException("Error returned trying to update SPN",e);
		}
		return;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getSPNCriteriaBySPNId(java.lang.Integer)
	 */
	public SPNCriteriaVO  getSPNCriteriaBySPNId(Integer spnId)
			throws DataServiceException {
		List<SPNCriteriaDBVO> spcCriteriaDBVOList;
		List<SPNSkillVO> skillList = new ArrayList<SPNSkillVO>();
		
		SPNCriteriaVO spnCriteriaVO = new SPNCriteriaVO();
		spnCriteriaVO.setSkills(skillList);
		
		try {
			spcCriteriaDBVOList = queryForList("spn_criteria_by_spn_id.query", spnId);
			for(SPNCriteriaDBVO spnCriteriaDBVO : spcCriteriaDBVOList) {
				switch (spnCriteriaDBVO.getCriteriaTypeId()) {
					case Constants.SPN.CRITERIA_TYPE.RESOURCE_CRED:
						spnCriteriaVO.setResourceCredTypeId(spnCriteriaDBVO.getField1());
						spnCriteriaVO.setResourceCredCategoryId(spnCriteriaDBVO.getField2());
						break;
					case Constants.SPN.CRITERIA_TYPE.VENDOR_CRED:
						spnCriteriaVO.setVendorCredTypeId(spnCriteriaDBVO.getField1());
						spnCriteriaVO.setVendorCredCategoryId(spnCriteriaDBVO.getField2());
						break;
					case Constants.SPN.CRITERIA_TYPE.VENDOR_GENERAL_INS:
						spnCriteriaVO.setInsGeneralLiability(spnCriteriaDBVO.isField11());
						spnCriteriaVO.setInsGeneralLiabilityMinAmt(spnCriteriaDBVO.getField6());
						break;
					case Constants.SPN.CRITERIA_TYPE.VENDOR_AUTO_INS:
						spnCriteriaVO.setInsAutoLiability(spnCriteriaDBVO.isField11());
						spnCriteriaVO.setInsAutoLiabilityMinAmt(spnCriteriaDBVO.getField6());
						break;
					case Constants.SPN.CRITERIA_TYPE.VENDOR_WORKMAN_INS:
						spnCriteriaVO.setInsWorkmanComp(spnCriteriaDBVO.isField11());
						spnCriteriaVO.setInsWorkmanCompMinAmt(spnCriteriaDBVO.getField6());
						break;
					case Constants.SPN.CRITERIA_TYPE.STAR_RATING:
						spnCriteriaVO.setStarRating(spnCriteriaDBVO.getField6());
						spnCriteriaVO.setStarRatingIncludeNonRated(spnCriteriaDBVO.isField11());
						break;
					case Constants.SPN.CRITERIA_TYPE.SKILL:
						SPNSkillVO skillVO = new SPNSkillVO();
						skillVO.setCriteriaId(spnCriteriaDBVO.getCriteriaId());
						skillVO.setMainCategory(spnCriteriaDBVO.getField1());
						skillVO.setCategory(spnCriteriaDBVO.getField2());
						skillVO.setSubCategory(spnCriteriaDBVO.getField3());
						skillVO.setSkill(spnCriteriaDBVO.getField4());
						skillList.add(skillVO);
						break;
					case Constants.SPN.CRITERIA_TYPE.LANGUAGE:
						spnCriteriaVO.setLanguageId(spnCriteriaDBVO.getField1());
						break;
					case Constants.SPN.CRITERIA_TYPE.MIN_SO_CLOSED:
						spnCriteriaVO.setMinSOClosed(spnCriteriaDBVO.getField1());
						break;
					default:
						break;
				}
			}
		} catch (DataAccessException e) {
			logger.error("Error returned trying to get criteria for SPNId: " + spnId,e);
			throw new DataServiceException("Error returned trying to get criteria for SPNId: " + spnId,e);
		}
		return spnCriteriaVO;
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#deleteSPNHeaderSummary()
	 */
	public void deleteSPNHeaderSummary() throws DataServiceException {
		try {
			delete("spn_network_summary.delete",null);
		} catch (DataAccessException e) {
			logger.error("Error deleting SPN Summary table",e);
			throw new DataServiceException("Error deleting SPN Summary table",e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#loadSPNHeaderSummarty()
	 */
	public void loadSPNHeaderSummarty() throws DataServiceException {
		try {
			insert("spn_hdr_summary.insert", null);
		} catch (DataAccessException e) {
			logger.error("Error loading SPN Summary table",e);
			throw new DataServiceException("Error loading SPN Summary table",e);
		}
		return;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#lockSPN(java.lang.Integer)
	 */
	public void lockSPN(Integer spnId) throws DataServiceException {
		try {
			update("spn_header_lock.update", spnId);
		} catch (DataAccessException e) {
			logger.error("Error locking SPN: " + spnId,e);
			throw new DataServiceException("Error locking SPN: " + spnId,e);
		}
		return;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#insertSPNNetworkRecords(java.lang.Integer, java.util.List)
	 */
	public void insertSPNNetworkRecords(Integer spnId,
			List<ProviderResultVO> servicePros) throws DataServiceException {
		
		Map<String, Integer> data = null;
		for (ProviderResultVO providerResultVO : servicePros) {
			data = new HashMap<String, Integer>();
			data.put("spn_id", spnId);
			data.put("resource_id", providerResultVO.getResourceId());
			data.put("spn_status_id", Constants.SPN.STATUS.INVITED);

			Integer spnNetworkId = (Integer)insert("spn_network.insert", data);
			update("spn_network_zip.update", spnNetworkId);
		}
		
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getActiveSPNCampaigns()
	 */
	public List<SPNCampaignVO> getActiveSPNCampaigns()
			throws DataServiceException {
		
		return queryForList("spn_active_campaigns.query", null);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#updateSPNCampaignMatchCnt(java.lang.Integer, java.lang.Integer)
	 */
	public void updateSPNCampaignMatchCnt (Integer spnCampaignId, Integer matchCount) throws DataServiceException {
		SPNCampaignVO campaignVO = new SPNCampaignVO();
		campaignVO.setCampaignId(spnCampaignId);
		campaignVO.setTotalProviderCnt(matchCount);
		update("spn_campaign_match_cnt.update", campaignVO);
		return;
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getNetworkResourceBySPNIdAndResourceId(java.lang.Integer, java.lang.Integer)
	 */
	public SPNNetworkResourceVO getNetworkResourceBySPNIdAndResourceId(
			Integer spnId, Integer resourceId) throws DataServiceException {
		SPNNetworkResourceVO resourceVo = null;
		Map<String, Integer> data = new HashMap<String,Integer>();
		
		data.put("spn_id", spnId);
		data.put("resource_id", resourceId);
		
		resourceVo = (SPNNetworkResourceVO)queryForObject("spn_network_resource_by_spn_id_and_resource_id.query", data);
		return resourceVo;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#approveMembers(java.util.List)
	 */
	public void approveMembers(List<Integer> spnNetworkIds)
			throws DataServiceException {
		try {
			update("approve_members.update", spnNetworkIds);
		} catch (DataAccessException e) {
			throw new DataServiceException("Unable to approve members",e);
		}	
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#removeMembers(java.util.List)
	 */
	public void removeMembers(List<Integer> spnNetworkIds)
			throws DataServiceException {
		try {
			update("remove_members.update", spnNetworkIds);
		} catch (DataAccessException e) {
			throw new DataServiceException("Unable to remove members",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#createNewSPNCampaign(com.newco.marketplace.dto.vo.spn.SPNCampaignVO)
	 */
	public Integer createNewSPNCampaign(SPNCampaignVO campaign) throws DataServiceException {
		Integer insertId = null;
		try {
			insertId = (Integer)insert("spn_campaign.insert", campaign);
		} catch (DataAccessException e) {
			logger.error("Error creating SPN: ",e);
			throw new DataServiceException("Error creating SPN: ",e);
		}
		return insertId;
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#loadAllSPNCampaigns(java.lang.Integer)
	 */
	public List<SPNCampaignVO> loadAllSPNCampaigns(Integer spnId) throws DataServiceException {
		List<SPNCampaignVO> list = new ArrayList<SPNCampaignVO>();
		try {
			list = queryForList("spn_campaigns.query", spnId);
		} catch (DataAccessException e) {
			logger.error("Error retriving SPN campaign summary data for spn: ",e);
			throw new DataServiceException("Error retriving SPN campaign summary data for spn: ",e);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#deleteSPNCampaign(java.lang.Integer)
	 */
	public Integer deleteSPNCampaign(Integer campaignId) throws DataServiceException {
		Integer insertId = null;
		try {
			insertId = (Integer)update("spn_campaign_delete_campaign.update", campaignId);
		} catch (DataAccessException e) {
			logger.error("Error 'soft deleting' the SPN: " + campaignId,e);
			throw new DataServiceException("Error 'soft deleting' the SPN: " + campaignId,e);
		}
		return insertId;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#updateSPN(com.newco.marketplace.dto.vo.spn.SPNHeaderVO)
	 */
	public void updateProviderNetworkStatus(SPNNetworkResourceVO networkResourceVO) throws DataServiceException {

		try {
			update("spn_network_interested_status.update", networkResourceVO);

		} catch (DataAccessException e) {
			logger.error("Error returned trying to update SPN",e);
			throw new DataServiceException("Error returned trying to update SPN",e);
		}
		return;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getAllSPNS()
	 */
	public List<SPNHeaderVO> getAllSPNS() throws DataServiceException {
		List<SPNHeaderVO> list = new ArrayList<SPNHeaderVO>();
		try {
			list = queryForList("spn_provider_network.query",null);
		} catch (DataAccessException e) {
			logger.error("Error retriving SPN Networks:",e);
			throw new DataServiceException("Error retriving SPN Networks:",e);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getSPNInviteListForResource(java.lang.Integer)
	 */
	public List<SPNHeaderVO> getSPNInviteListForResource(Integer resourceId) throws DataServiceException
	{
		List<SPNHeaderVO> list = new ArrayList<SPNHeaderVO>();
		try {
			list = queryForList("spn_provider_network_by_resource.query", resourceId);
		} catch (DataAccessException e) {
			logger.error("Error retriving SPN Networks:",e);
			throw new DataServiceException("Error retriving SPN Networks:",e);
		}
		return list;	
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getSPNInviteListForAdmin(java.lang.Integer)
	 */
	public List<SPNHeaderVO> getSPNInviteListForAdmin(Integer vendorId) throws DataServiceException
	{
		List<SPNHeaderVO> list = new ArrayList<SPNHeaderVO>();
		try {
			list = queryForList("spn_provider_network_by_company.query", vendorId);
		} catch (DataAccessException e) {
			logger.error("Error retriving SPN Network by company:",e);
			throw new DataServiceException("Error retriving SPN Networks by company:",e);
		}
		return list;	
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getIsAdminIndicator(java.lang.Integer)
	 */
	public Integer getIsAdminIndicator(Integer resourceId) throws DataServiceException{
		Integer primaryIndicator = 0;
		try{
			primaryIndicator = (Integer)queryForObject("select.providerResourceIsProviderAdmin", resourceId);
		}catch (DataAccessException e) {
			logger.error("Error retriving provider primary indicator:",e);
			throw new DataServiceException("Error retriving provider primary indicator:",e);
		}
		return primaryIndicator;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getMembersByStatus(com.newco.marketplace.dto.vo.serviceorder.CriteriaMap)
	 */
	public List<SPNMemberSearchResultVO> getMembersByStatus(CriteriaMap criteria)
			throws DataServiceException {
		List<SPNMemberSearchResultVO> results = queryForList("spn_member_search_by_status.query", buildSPNSearchRequestVO(criteria));
		return results;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getPaginationCount(com.newco.marketplace.dto.vo.serviceorder.CriteriaMap)
	 */
	public Integer getPaginationCount(CriteriaMap criteria)
			throws DataServiceException {
		return (Integer)queryForObject("spn_member_search_by_status_count.query",buildSPNSearchRequestVO(criteria));
	}

	/**
	 * @param spnId
	 * @param docs
	 * @throws DataServiceException
	 */
	private void loadSPNRefDocuments (Integer spnId, List<DocumentVO> docs) throws DataAccessException {
		if (null != docs ) {
			Map<String,Integer> document = new HashMap<String,Integer>();
			document.put("spn_id", spnId);
			for (DocumentVO docVO : docs) {
				document.put("document_id", docVO.getDocumentId());
				insert("spn_ref_doc.insert",document);
			}
		}
	}

	/**
	 * @param spnId
	 * @param criteriaVO
	 * @throws DataServiceException
	 */
	private void loadCriteria(Integer spnId, SPNCriteriaVO criteriaVO) throws DataAccessException {
		SPNCriteriaDBVO criteriaDBVO = null;
		
		// Resource Credentials
		if (null != criteriaVO.getResourceCredTypeId()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.RESOURCE_CRED));
			criteriaDBVO.setField1(criteriaVO.getResourceCredTypeId());
			criteriaDBVO.setField2(criteriaVO.getResourceCredCategoryId());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// Vendor Credentials
		if (null != criteriaVO.getVendorCredTypeId()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.VENDOR_CRED));
			criteriaDBVO.setField1(criteriaVO.getVendorCredTypeId());
			criteriaDBVO.setField2(criteriaVO.getVendorCredCategoryId());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// Vendor General Liability Insurance
		if (criteriaVO.isInsGeneralLiability()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.VENDOR_GENERAL_INS));
			criteriaDBVO.setField6(criteriaVO.getInsGeneralLiabilityMinAmt());
			criteriaDBVO.setField11(criteriaVO.isInsGeneralLiability());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// Vendor Auto Liability Insurance
		if (criteriaVO.isInsAutoLiability()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.VENDOR_AUTO_INS));
			criteriaDBVO.setField6(criteriaVO.getInsAutoLiabilityMinAmt());
			criteriaDBVO.setField11(criteriaVO.isInsAutoLiability());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// Vendor Workman's Comp Insurance
		if (criteriaVO.isInsWorkmanComp()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.VENDOR_WORKMAN_INS));
			criteriaDBVO.setField6(criteriaVO.getInsWorkmanCompMinAmt());
			criteriaDBVO.setField11(criteriaVO.isInsWorkmanComp());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// Star Rating
		if (null != criteriaVO.getStarRating()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.STAR_RATING));
			criteriaDBVO.setField6(criteriaVO.getStarRating());
			criteriaDBVO.setField11(criteriaVO.isStarRatingIncludeNonRated());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// language
		if (null != criteriaVO.getLanguageId()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.LANGUAGE));
			criteriaDBVO.setField1(criteriaVO.getLanguageId());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// min service orders closed
		if (null != criteriaVO.getMinSOClosed()) {
			criteriaDBVO = new SPNCriteriaDBVO();
			
			criteriaDBVO.setSpnId(spnId);
			criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.MIN_SO_CLOSED));
			criteriaDBVO.setField1(criteriaVO.getMinSOClosed());
			
			insert("spn_criteria.insert",criteriaDBVO);
		}
		
		// skills
		if (null != criteriaVO.getSkills()) {
			for (SPNSkillVO skillVO : criteriaVO.getSkills()) {
				criteriaDBVO = new SPNCriteriaDBVO();
				
				criteriaDBVO.setSpnId(spnId);
				criteriaDBVO.setCriteriaTypeId(new Integer(Constants.SPN.CRITERIA_TYPE.SKILL));
				criteriaDBVO.setField1(skillVO.getMainCategory());
				criteriaDBVO.setField2(skillVO.getCategory());
				criteriaDBVO.setField3(skillVO.getSubCategory());
				criteriaDBVO.setField4(skillVO.getSkill());
				insert("spn_criteria.insert",criteriaDBVO);
			}
		}
	}

	private SPNMemberSearchRequestVO buildSPNSearchRequestVO (CriteriaMap criteria) {
		SPNMemberSearchRequestVO requestVO = new SPNMemberSearchRequestVO();
		
		// set up search criteria
		SearchCriteria searchCriteria = (SearchCriteria)criteria.get(OrderConstants.SEARCH_CRITERIA_KEY);
		requestVO.setSpnId(new Integer(searchCriteria.getSearchValue()));
		
		List<Integer> searchStatuses = new ArrayList<Integer>();
		boolean	searchTypeNumeric = NumberUtils.isNumber(searchCriteria.getSearchType());
		if (searchTypeNumeric){
			switch (Integer.parseInt(searchCriteria.getSearchType())) {
			case Constants.SPN.STATUS.MEMBER:
				searchStatuses.add(Integer.parseInt(searchCriteria.getSearchType()));
				break;
			case Constants.SPN.STATUS.APPLICANT:
				searchStatuses.add(Integer.parseInt(searchCriteria.getSearchType()));
				break;
			case Constants.SPN.STATUS.INACTIVE:
				searchStatuses.add(new Integer(Constants.SPN.STATUS.INACTIVE));
				searchStatuses.add(new Integer(Constants.SPN.STATUS.REMOVED));
				searchStatuses.add(new Integer(Constants.SPN.STATUS.NOT_INTERESTED));
				break;
			default:
				break;
			}
		}
		requestVO.setSearchStatuses(searchStatuses);
		
		// set up filter
		SPNFilterCriteria filterCriteria = (SPNFilterCriteria)criteria.get(OrderConstants.FILTER_CRITERIA_KEY);
		if (null != filterCriteria) {
			if (!filterCriteria.isAllMarkets() && null != filterCriteria.getMarketId()) {
				requestVO.setMarketId(filterCriteria.getMarketId());
			}
		}
		
		// set up paging
		PagingCriteria pagingCriteria = (PagingCriteria)criteria.get(OrderConstants.PAGING_CRITERIA_KEY);
		if (null != pagingCriteria) {
			requestVO.setStartIndex(new Integer(pagingCriteria.getStartIndex()));
			requestVO.setNumberOfRecords(new Integer(pagingCriteria.getPageSize()));
		}
		
		// sorting is by default only for now
		requestVO.setSortColumnName("c.last_name, c.first_name");
		requestVO.setSortOrder("ASC");
		return requestVO;
	}
		
	public List<SPNProviderProfileBuyerVO> getProviderProfileSpns(Integer resourceId) throws DataServiceException{
		List<SPNProviderProfileBuyerVO> providerSpnsList = new ArrayList<SPNProviderProfileBuyerVO>();

		try{
			providerSpnsList = queryForList("spn_provider_profile_buyers.query", resourceId);
		}catch(DataAccessException e) {
			logger.error("Error retriving provider SPN profile :",e);
			throw new DataServiceException("Error retriving provider SPN profile :",e);
		}
		
		return providerSpnsList;
		
	}
	/**
	 * Returns a list of SPNMonitorVO for the vendor id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @return spnList
	 * @throws DataServiceException
	 */
	public List<SPNMonitorVO> getSPNMonitorList(Integer vendorId) throws DataServiceException{
		List<SPNMonitorVO> spnList = new ArrayList <SPNMonitorVO>();
		
		try{
			spnList = queryForList("miniSPNMonitorList.select", vendorId);			
		}catch(DataAccessException e) {
			logger.error("Error retriving provider SPN profile :",e);
			throw new DataServiceException("Error retriving provider SPN profile :",e);
		}
		
		return spnList;
	}
	
	/**
	 * gets the SPN details for provider admin
	 * @param companyId
	 * @return List<SPNetCommMonitorVO> 
	 * @throws DataServiceException
	 */
	public List<SPNetCommMonitorVO> getSPNetCommDetailsForPA(Integer companyId) throws DataServiceException{
		List<SPNetCommMonitorVO> spnDetails = new ArrayList<SPNetCommMonitorVO>();
		try {
			spnDetails = queryForList("spnet_details_for_provider_admin.query", companyId);			
		} catch (DataAccessException e) {
			logger.error("Error retriving SPN Details for Provider Admin:",e);
			throw new DataServiceException("Error retriving SPN Details for Provider Admin:",e);
		}
		return spnDetails;		
	}	
	/**
	 * Returns the count if the vendor has applied for atleast one SPN invitation.  If no SPNs are
	 * found, then zero is returned.
	 * @param vendorId
	 * @return count
	 * @throws DataServiceException
	 */
	public Integer isVendorSPNApplicant(Integer vendorId) throws DataServiceException{
		Integer count = 0;		
		try{
			count = (Integer)queryForObject("vendorSPNApplicantCount.select", vendorId);
		}catch(DataAccessException e) {
			logger.error("Error retriving count :",e);
			throw new DataServiceException("Error retriving count :",e);
		}
		
		return count;
	}
	/**
	 * Returns a list of SPNMainMonitorVO for the vendor id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @return SPNMainMonitorVO list
	 * @throws DataServiceException
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList (Integer vendorId) throws DataServiceException{
		 Map map = new HashMap();
		 map.put("vendorId", vendorId);
		 String query = "spnMainMonitorList.select"; //This would return only basic properties for the SPN
		 return getSPNMainMonitorListCommon(query,map);
	}
	
	/**
	 * 
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList (Integer vendorId,Integer spnId) throws DataServiceException{
		 Map map = new HashMap();
		 map.put("vendorId", vendorId);
		 map.put("spnId", spnId);
		 String query = "spnMainMonitorList.select.all"; //This would return everything needed for the specific spn
		 return getSPNMainMonitorListCommon(query,map);
	}
	/**
	 * This is generic method for returning list of SPnList 
	 * @param map
	 * @return
	 * @throws DataServiceException
	 */
	private List<SPNMainMonitorVO> getSPNMainMonitorListCommon(String query,Map<String,Integer> map) throws DataServiceException {
		List<SPNMainMonitorVO> spnMainMonitorList = new ArrayList<SPNMainMonitorVO>();
		List<SPNAgreeDocumentVO> spnAgreeDocumentVO = new ArrayList<SPNAgreeDocumentVO>();
		try{
			spnMainMonitorList = queryForList(query, map);
			if(null!=spnMainMonitorList){
				for(SPNMainMonitorVO spnMainMonitorVO : spnMainMonitorList){
					spnAgreeDocumentVO = spnMainMonitorVO.getSpnAgreeDocuments();
					if(null!=spnAgreeDocumentVO){
						for( SPNAgreeDocumentVO agreeDocVO: spnAgreeDocumentVO){
							agreeDocVO.setDocumentData(documentService.getDocumentBlobByDocumentId(agreeDocVO.getDocId()));
						}
					}
				}
			}
			
		}catch(DataAccessException e){
			logger.error("Error retriving SPN Main Monitor List for Provider Admin:",e);
			throw new DataServiceException("Error retriving SPN Main Monitor List for Provider Admin:",e);
		}		
		return spnMainMonitorList;
	}
	
	/**
	 * This method is used to get a list of buyer agreements for the spn id. If
	 * no documents are found, then an empty list is returned.
	 * 
	 * @param spnId
	 * @return List<SPNDocumentVO>
	 * @throws DataServiceException
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModal(Integer spnID)
			throws DataServiceException {
		List<SPNDocumentVO> spnDocList = null;
		try {
			spnDocList = queryForList("spn_buyer_agreement_document.query", spnID);
			if(null != spnDocList){
				for(SPNDocumentVO spnDocumentVO : spnDocList){					
					spnDocumentVO.setDocumentContent(documentService.getDocumentBlobByDocumentId(spnDocumentVO.getDocId()));				
				}
			}else{
				spnDocList = new ArrayList<SPNDocumentVO>();
			}
		} catch (DataAccessException e) {
			logger.error(
					"Error returned trying to get document list from the database for spnId : "
							+ spnID, e);
			throw new DataServiceException(
					"Error returned trying to get document list from the database for spnId : "
							+ spnID, e);
		}
		return spnDocList;
	}
	
	/**
	 * This method is used to get the document content for a document id.
	 * 
	 * @param docID
	 * @return List<SPNDocumentVO>
	 * @throws DataServiceException 
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModalDocument(Integer docId)
			throws DataServiceException {
		List<SPNDocumentVO> spnDocList = null;
		try {
			spnDocList = queryForList("spn_buyer_agreement_retrieve_document.query", docId);	
			if(null !=spnDocList){
				for(SPNDocumentVO spnDocumentVO : spnDocList){
					
					spnDocumentVO.setDocumentContent(documentService.getDocumentBlobByDocumentId(spnDocumentVO.getDocId()));				
				}
			}else{
				spnDocList = new ArrayList<SPNDocumentVO>();
			}			
		} catch (DataAccessException e) {
			logger.error(
					"Error returned trying to get document content from the database for docId : "
							+ docId, e);
			throw new DataServiceException(
					"Error returned trying to get document content from the database for docId : "
							+ docId, e);
		}
		return spnDocList;
	}
	/**
	 * Submits Buyer Agreements of a SPN by a provider. 
	 * @param firmId
	 * @param spnId
	 * @throws DataServiceException
	 */
	public void submitSPNBuyerAgreement (Integer firmId,Integer spnId,String modifiedBy, Boolean auditRequired) throws DataServiceException{
		try {
			Date time=new Date();
			SPNDocumentVO agreementVO=new SPNDocumentVO();
			agreementVO.setFirmId(firmId);
			agreementVO.setSpnId(spnId);
			agreementVO.setTime(time);
			String providerState = (String)queryForObject("spn_buyer_agreement_retrieve_state.query", agreementVO);
			if(StringUtils.isNotBlank(providerState)){
				if(providerState.equalsIgnoreCase(SPNConstants.APPLICANT_INCOMPLETE)){
					if(auditRequired ==null || auditRequired.booleanValue()==true){
						agreementVO.setState(SPNConstants.REAPPLICANT);
					}else{
						agreementVO.setState(SPNConstants.SPN_MEMBER);
					}	
				}else{
					agreementVO.setState(SPNConstants.APPLICANT);
				}
			}else{
				agreementVO.setState(SPNConstants.REAPPLICANT);
			}
			agreementVO.setModifiedBy(modifiedBy);
			update("spn_provider_firm_state.update",agreementVO );
					
		} catch (DataAccessException e) {
			logger.error(
					"Error returned while trying to submit Buyer Agreements for spn :"+spnId
							+"by provider:"+ firmId, e);
			throw new DataServiceException(
					"Error returned while trying to submit Buyer Agreements for spn :"+spnId
							+"by provider:"+ firmId, e);
		}
	}
	
	/**
	 * Submits Buyer Agreements of a SPN by a provider. 
	 * @param firmId
	 * @param spnDocId
	 * @throws DataServiceException
	 */
	public void submitSPNBuyerAgreementForDoc (Integer firmId, String username, Integer spnDocId) throws DataServiceException{
		SPNDocumentVO agreementVO = new SPNDocumentVO();
		Date time = new Date();
		agreementVO.setFirmId(firmId);
		agreementVO.setDocId(spnDocId);
		agreementVO.setTime(time);
		agreementVO.setState("DOC APPROVED");
		agreementVO.setModifiedBy(username);
		
		delete("spn_submit_buyer_agreement.delete", agreementVO);
		insert("spn_submit_buyer_agreement.insert", agreementVO);
	}
		
	/**
	 * Changes the state of the provider to PF SPN INTRESTED
	 * @param spnId
	 * @param vendorId
	 * @throws DataServiceException
	 */
	public void acceptInvite(Integer spnId,Integer vendorId, String modifiedBy) throws DataServiceException{
		try {
			SPNDocumentVO agreementVO=new SPNDocumentVO();
			agreementVO.setFirmId(vendorId);
			agreementVO.setSpnId(spnId);
			agreementVO.setState(SPNConstants.INTERESTED);
			agreementVO.setModifiedBy(modifiedBy);
			update("spn_provider_firm_state_interested.update",agreementVO );
		} catch (DataAccessException e) {
			logger.error(
					"Error returned while trying to change state for spn :"+spnId
							+"by provider:"+ vendorId, e);
			throw new DataServiceException(
					"Error returned while trying to change state for spn :"+spnId
							+"by provider:"+ vendorId, e);
		}
	}
	/**
	 * Changes the state of the provider to PF SPN INTRESTED
	 * @param spnId
	 * @param vendorId
	 * @param rejectId
	 * @param rejectReason
	 * @throws DataServiceException
	 */
	public void rejectInvite(String rejectId,String rejectReason,Integer spnId,Integer vendorId,String modifiedBy) throws DataServiceException{
		try {
			SPNDocumentVO agreementVO=new SPNDocumentVO();
			agreementVO.setFirmId(vendorId);
			agreementVO.setSpnId(spnId);
			agreementVO.setState(SPNConstants.NOT_INTERESTED);
			agreementVO.setRejectId(rejectId);
			agreementVO.setRejectReason(rejectReason);
			agreementVO.setModifiedBy(modifiedBy);
			update("spn_provider_firm_state_reject.update",agreementVO );
		} catch (DataAccessException e) {
			logger.error(
					"Error returned while trying to change state for spn :"+spnId
							+"by provider:"+ vendorId, e);
			throw new DataServiceException(
					"Error returned while trying to change state for spn :"+spnId
							+"by provider:"+ vendorId, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#loadProviderInvitation(java.lang.Integer, java.lang.Integer)
	 */
	public SPNMainMonitorVO loadProviderInvitation(Integer spnId, Integer vendorId) throws DataServiceException{
		SPNMainMonitorVO invitationVO = new SPNMainMonitorVO();
		List<SPNInvitationNoInterestVO> list = null;
		try {
			Map<String,Integer> queryParamsMap = new HashMap<String,Integer>();
			queryParamsMap.put("spn_id", spnId);
			queryParamsMap.put("prov_firm_id", vendorId);
			invitationVO=(SPNMainMonitorVO)queryForObject("spnInvitationDetails.query", queryParamsMap);
			if (null != invitationVO) {
				list = queryForList("spn_no_invite.query", null);
				invitationVO.setSpnInvitationNoInterestVO(list);
			}
		} catch (DataAccessException e) {
			logger.error("Error returned while trying to get details of spn :"+spnId, e);
			throw new DataServiceException("Error returned while trying to get details of spn :"+spnId, e);
		}
		return invitationVO;
	}
	/**
	 * Inserts into spnet_uploaded_document_state table for provider uploaded document
	 * @param spnProvUploadedDocsVO
	 * @return id
	 * @throws DataServiceException
	 */
	public int saveUploadedDocumentState (SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException{
		Integer id = null;
		try {
			id = (Integer) getSqlMapClient().insert("spnet.uploaded.document.state.insert", spnProvUploadedDocsVO);
		} catch (DataAccessException ex) {
			
			logger.info("DataAccessException @SPNDaoImpl.saveUploadedDocumentState() due to"
					+ ex);
			throw new DataServiceException(
					"DataAccessException @SPNDaoImpl.saveUploadedDocumentState() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
		
			logger.info("SQL Exception @SPNDaoImpl.saveUploadedDocumentState() due to"
					+ex);
			throw new DataServiceException(
					"General Exception @SPNDaoImpl.saveUploadedDocumentState() due to "
							+ ex.getMessage());
		}		
		return id;
	}
	
	/**
	 * Inserts into spnet_provider_firm_document table for provider uploaded document
	 * @param spnProvUploadedDocsVO
	 * @return id
	 * @throws DataServiceException
	 */
	public int saveProviderUploadedDocument (SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException{
		Integer id = null;
		try {
			id = (Integer) getSqlMapClient().insert("spnet.provider.uploaded.document.insert", spnProvUploadedDocsVO);
		} catch (DataAccessException ex) {
			
			logger.info("DataAccessException @SPNDaoImpl.saveProviderUploadedDocument() due to"
					+ex);
			throw new DataServiceException(
					"DataAccessException @SPNDaoImpl.saveProviderUploadedDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
		
			logger.info("SQL Exception @SPNDaoImpl.saveProviderUploadedDocument() due to"
					+ ex);
			throw new DataServiceException(
					"General Exception @SPNDaoImpl.saveProviderUploadedDocument() due to "
							+ ex.getMessage());
		}		
		return id;
	}
	/**
	 * Updates spnet_provider_firm_document table for provider uploaded document
	 * @param spnProvUploadedDocsVO
	 * @return 
	 * @throws DataServiceException
	 */
	public void updateProviderDocumetStatus(SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException {
		Integer updateSucessful = 0;
		try {
			updateSucessful = update("spnet.provider.uploaded.document.update", spnProvUploadedDocsVO);
			if(updateSucessful >0){
				update("spnet.provider.document.delete", spnProvUploadedDocsVO);
			}

		} catch (DataAccessException e) {
			logger.error("Error returned trying to update SPN",e);
			throw new DataServiceException("Error returned trying to update SPN",e);
		}
		return;
	}
	/**
	 * Updates spnet_uploaded_document_state table for provider uploaded document
	 * @param spnProvUploadedDocsVO
	 * @return 
	 * @throws DataServiceException
	 */
	public void updateDocumetUploadedStatus(SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException {

		try {
			update("spnet.uploaded.document.state.update", spnProvUploadedDocsVO);

		} catch (DataAccessException e) {
			logger.error("Error returned trying to update SPN",e);
			throw new DataServiceException("Error returned trying to update SPN",e);
		}
		return;
	}
	
	public void uploadDocumentUploadedIdAndStatus(SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException {

		try {
			update("spnet.uploaded.document.id.and.state.update", spnProvUploadedDocsVO);

		} catch (DataAccessException e) {
			logger.error("Error returned trying to update SPN",e);
			throw new DataServiceException("Error returned trying to update SPN",e);
		}
		return;
	}
	

	/**
	 * Returns the list of approval criteria for the given SPN
	 * @param spnId
	 * @return List<SPNApprovalCriteriaVO>
	 * @throws DataServiceException
	 */
	public List<SPNApprovalCriteriaVO> getApprovalCriteriaList(Integer spnId) throws DataServiceException {
		List<SPNApprovalCriteriaVO> spnApprovalCriteriaVOList = null;
		try {
			spnApprovalCriteriaVOList = queryForList("spnet.getApprovalCriteriaForSPN.select",spnId);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting the approval criteria for SPN",e);
			throw new DataServiceException("Error returned while getting the approval criteria for SPN",e);
		}
		return spnApprovalCriteriaVOList;
	}
	
	/**
	 * Returns the list of matching providers count
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public List<ProviderMatchingCountsVO> getMatchingProviderstList(ProviderMatchApprovalCriteriaVO providerMatchApprovalCriteriaVO) throws DataServiceException{
		List<ProviderMatchingCountsVO> matchingProvidersList = null;
		try {
			matchingProvidersList= queryForList("spnet.providerCountsForApprovalCriteria.select", providerMatchApprovalCriteriaVO);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting the approval criteria for SPN",e);
			throw new DataServiceException("Error returned while getting the approval criteria for SPN",e);
		}
		return matchingProvidersList;
	}
	
	/**
	 * Returns the count if the vendor has applied for atleast one SPN invitation.  If no SPNs are
	 * found, then zero is returned.
	 * @param vendorId
	 * @return count
	 * @throws DataServiceException
	 */
	public Integer getTotalProviderCount(Integer vendorId) throws DataServiceException{
		Integer count = 0;		
		try{
			count = (Integer)queryForObject("spnet.totalProvidersCount.select", vendorId);
		}catch(DataAccessException e) {
			logger.error("Error retriving count of total providers:",e);
			throw new DataServiceException("Error retriving count of total providers :",e);
		}		
		return count;
	}
	/**
	 * Returns the count if the vendor has applied for atleast one SPN invitation.  If no SPNs are
	 * found, then zero is returned.
	 * @param vendorId
	 * @return count
	 * @throws DataServiceException
	 */
	public Integer isProviderSPNApplicant(Integer vendorId) throws DataServiceException{
		Integer count = 0;		
		try{
			count = (Integer)queryForObject("providerSPNApplicantCount.select", vendorId);
		}catch(DataAccessException e) {
			logger.error("Error retriving count :",e);
			throw new DataServiceException("Error retriving count :",e);
		}
		
		return count;
	}
	

	/**
	 * Returns the list of matching providers requirements
	 * @param SPNMainMonitorVO spnMainMonitorVO
	 * @return List<SPNProviderRequirementsVO>
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getMatchingProviderRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws DataServiceException{
		List<SPNProviderRequirementsVO> matchingProvidersList = null;
		try {			
			matchingProvidersList= queryForList("spnet.criteriaMatchingProviders.select",spnMainMonitorVO);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting matching providers requirements list",e);
			throw new DataServiceException("Error returned while getting matching providers requirements list",e);
		}
		return matchingProvidersList;
	}
	
	/**
	 * Returns the list of complete providers requirements for the spn
	 * @param spnId
	 * @return List<SPNProviderRequirementsVO>
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getSPNProviderRequirementsList(Integer spnId) throws DataServiceException{
		List<SPNProviderRequirementsVO> spnProviderRequirementsList = null;
		try {
			spnProviderRequirementsList= queryForList("spnet.providerRequirementsCrteria.select",spnId);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting comeplete providers requirements list",e);
			throw new DataServiceException("Error returned while getting complete providers requirements list",e);
		}
		return spnProviderRequirementsList;
	} 
	
	/**
	 * Returns the list of matching providers requirements
	 * @param SPNMainMonitorVO spnMainMonitorVO
	 * @return List<SPNProviderRequirementsVO>
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getMatchingCompanyRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws DataServiceException{
		List<SPNProviderRequirementsVO> matchingCompanyRequirementsList = null;
		try {			
			matchingCompanyRequirementsList= queryForList("spnet.matchingCompanyCriteria.select",spnMainMonitorVO);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting matching providers requirements list",e);
			throw new DataServiceException("Error returned while getting matching providers requirements list",e);
		}
		return matchingCompanyRequirementsList;
	}
	
	/**
	 * Returns the list of complete providers requirements for the spn
	 * @param spnId
	 * @return List<SPNProviderRequirementsVO>
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getSPNCompanyRequirementsList(Integer spnId) throws DataServiceException{
		List<SPNProviderRequirementsVO> spnCompanyRequirementsList = null;
		try {
			spnCompanyRequirementsList= queryForList("spnet.companyRequirementsCrteria.select",spnId);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting comeplete providers requirements list",e);
			throw new DataServiceException("Error returned while getting complete providers requirements list",e);
		}
		return spnCompanyRequirementsList;
	} 
	
	
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		List<SPNComplianceVO> complianceList = queryForList("spnet.compliance.getFirmCompliance",complianceCriteriaVO);
		return complianceList;
		}
	
	public Date getFirmComplianceDate() throws DataServiceException {
		Date date= (Date) queryForObject("spnet.compliance.getFirmComplianceDate",null);
		return date;	
	}
	public Date getProviderComplianceDate() throws DataServiceException {
		Date date= (Date) queryForObject("spnet.compliance.getProviderComplianceDate",null);
		return date;	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getFirmComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		Integer count= (Integer) queryForObject("spnet.compliance.getFirmComplianceCount",complianceCriteriaVO);
		return count;
		}
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		List<SPNComplianceVO> complianceList = queryForList("spnet.compliance.getProviderCompliance",complianceCriteriaVO);
		return complianceList;
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getProviderComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		Integer count= (Integer) queryForObject("spnet.compliance.getProviderComplianceCount",complianceCriteriaVO);
		return count;
		}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getRequirementsforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException{
		List<SPNComplianceVO> requirementList = queryForList("spnet.getRequirementsforFirmCompliance",complianceCriteriaVO);
		return requirementList;
		
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getBuyersforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		List<String> buyerList = queryForList("spnet.getBuyersforFirmCompliance",complianceCriteriaVO);
		return buyerList;
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getSPNforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		List<String> SPNList = queryForList("spnet.getSPNforFirmCompliance",complianceCriteriaVO);
		return SPNList;
		}
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getRequirementsforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException{
		List<SPNComplianceVO> requirementList = queryForList("spnet.getRequirementsforProviderCompliance",complianceCriteriaVO);
		return requirementList;
		
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getBuyersforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		List<String> buyerList = queryForList("spnet.getBuyersforProviderCompliance",complianceCriteriaVO);
		return buyerList;
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getSPNforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException {
		List<String> SPNList = queryForList("spnet.getSPNforProviderCompliance",complianceCriteriaVO);
		return SPNList;
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getProviderNamesforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException{
		List<SPNComplianceVO> providerNameList = queryForList("spnet.getProviderNamesforProviderCompliance",complianceCriteriaVO);
		return providerNameList;
		
		}
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.spn.SPNDao#getSPMMainMonitorListWithFilters(java.lang.Integer, java.lang.Boolean, java.util.List, java.lang.String)
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorListWithFilters(
			Integer vendorId, Boolean filterAppliedInd,
			List<String> selectedBuyerValues, List<String> selectedMemStatus) throws DataServiceException{
		Map map = new HashMap();
		 map.put("vendorId", vendorId);
		 map.put("filterAplliedInd", filterAppliedInd);
		 map.put("selectedBuyerValues", selectedBuyerValues);
		 map.put("selectedMemStatus", selectedMemStatus);
		 String query = "spnMainMonitorList.select"; //This would return only basic properties for the SPN
		 return getSPNMainMonitorListCommon(query,map);
	}

	public List<SPNExclusionsVO> getCompanyExceptionsApplied(int spnId, int vendorId){
		Map map = new HashMap();
		map.put("vendorId", vendorId);
		map.put("spnId", spnId);
		String query = "spnCompanyExceptionsList.select"; //This would return only basic properties for the SPN
		return queryForList(query,map);
	}
	
	public List<SPNExclusionsVO> getResourceExceptionsApplied(int spnId, int vendorId){
		Map map = new HashMap();
		map.put("vendorId", vendorId);
		map.put("spnId", spnId);
		String query = "spnResourceExceptionsList.select"; //This would return only basic properties for the SPN
		return queryForList(query,map);
	}
	
	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return Integer
	 * @throws DataServiceException
	 */
	//SL-19387
	//Fetching Background Check details count of resources from db
	public Integer getBackgroundInformationCount(SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO) throws DataServiceException {
		Integer count=0;
		try {			
			count =(Integer)queryForObject("monitor.provider.background.getBackgroundInformationCount",searchBackgroundInfoProviderVO);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting Background Information Count",e);
			throw new DataServiceException("Error returned while getting Background Information Count",e);
		}
		return count;
		}

	
	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return List<BackgroundInfoProviderVO>
	 * @throws DataServiceException
	 */
	//SL-19387
	//Fetching Background Check details of resources from db
	public List<BackgroundInfoProviderVO> getBackgroundInformation(SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO) throws DataServiceException {
		
		List<BackgroundInfoProviderVO> backgroundInfoList =new ArrayList<BackgroundInfoProviderVO>();
		try {			
			backgroundInfoList = queryForList("monitor.provider.background.getBackgroundInformation",searchBackgroundInfoProviderVO);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting Background Information",e);
			throw new DataServiceException("Error returned while getting Background Information",e);
		}

		return backgroundInfoList;
		}
	
	//SL-19387
	public List<SPNMonitorVO> getSPNProviderList (Integer vendorId) throws DataServiceException{
		
		List<SPNMonitorVO> spnProviderList = new ArrayList<SPNMonitorVO>();
		try {			
			spnProviderList = queryForList("spn_provider_all.query",vendorId);
		}catch (DataAccessException e) {
			logger.error("Error returned while getting SPN Provider List",e);
			throw new DataServiceException("Error returned while getting SPN Provider List",e);
		}

		return spnProviderList;
	}

	public List<BackgroundCheckHistoryVO> getBackgroundCheckHistoryDetails(BackgroundCheckHistoryVO bgHistVO) throws DataServiceException {
		List<BackgroundCheckHistoryVO> backgroundHistList = null;
		try{
		backgroundHistList = queryForList("spn_provider_backgroundCheckHistoryDetails.query",bgHistVO);
				
		}
		catch(DataAccessException e){
			logger.error("Error returned trying to retrieve BackgroundCheckHistoryDetails", e);
			throw new DataServiceException("Error returned trying to retrieve BackgroundCheckHistoryDetails", e);
		}
		return backgroundHistList;
	}

	public String getProviderName(Integer resourceId)throws DataServiceException {
		String name = null;
		try{
			name = (String) queryForObject("spn_provider_getProviderName.query",resourceId);
		}
		catch(DataAccessException e){
			logger.error("Error returned trying to retrieve ProviderName", e);
			throw new DataServiceException("Error returned trying to retrieve ProviderName", e);
		}
		return name;
	}
}