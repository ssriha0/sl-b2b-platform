package com.servicelive.spn.services.auditor;

import static com.servicelive.spn.common.SPNBackendConstants.ACTION_TYPE_PROVIDER_FIRM;
import static com.servicelive.spn.common.SPNBackendConstants.MODIFIED_BY_USER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_REMOVED_FIRM;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.spn.network.SPNProviderFirmStateHistory;
import com.servicelive.domain.spn.network.SPNServiceProviderState;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.common.ActionMapperEnum;
import com.servicelive.spn.common.RoleEnum;
import com.servicelive.spn.common.SystemUserEnum;
import com.servicelive.spn.common.detached.SPNAuditorQueueCountVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.UserDao;
import com.servicelive.spn.dao.network.SPNProviderFirmStateDao;
import com.servicelive.spn.dao.network.SPNProviderFirmStateHistoryDao;
import com.servicelive.spn.dao.network.SPNServiceProviderStateDao;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.buyer.BuyerServices;
import com.servicelive.spn.services.interfaces.IAuditorQueueCountService;
import com.servicelive.spn.services.interfaces.IProviderFirmStateService;
import com.servicelive.spn.services.network.OnDemandMemberMaintenanceService;

/**
 * 
 * @author svanloon
 *
 */
@Service
public class ProviderFirmStateService extends BaseServices implements IProviderFirmStateService {

	private IAuditorQueueCountService auditorQueueCountService;
	private SPNProviderFirmStateDao spnProviderFirmStateDao;
	private SPNProviderFirmStateHistoryDao spnProviderFirmStateHistoryDao;
	private BuyerServices buyerServices;
	private UserDao userDao;
	private VelocityEngine velocityEngine;
	private SPNServiceProviderStateDao spnServiceProviderStateDao;
	private OnDemandMemberMaintenanceService onDemandMemberMaintenanceService; 

	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProviderFirmStatusOverride(Integer providerFirmId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, List<Integer> spnIds, String validityDate) throws Exception {
		// SL-12300 : Including validity date as per new requirement.
		spnProviderFirmStateDao.updateProviderFirmStatusOverride(providerFirmId, overrideComment, reason, state, modifiedBy, lookupSPNStatusActionMapper, spnIds, validityDate);
		if (state != null && state.getId() != null) {
			if (WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE.equals((String) state.getId())) {
				Integer actionMapid = ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER, WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE, ACTION_TYPE_PROVIDER_FIRM).getValue();
				LookupSPNStatusActionMapper spOCCActionMapper = new LookupSPNStatusActionMapper(actionMapid);
				LookupSPNWorkflowState spState = new LookupSPNWorkflowState();
				spState.setId(WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
				spnServiceProviderStateDao.updateAllServiceProStatusOverrideForFirmOverride(providerFirmId, overrideComment, reason, spState, modifiedBy, spOCCActionMapper, null, spnIds);
			} else if (WF_STATUS_PF_SPN_REMOVED_FIRM.equals((String) state.getId())) {
				Integer actionMapid = ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER, WF_STATUS_SP_SPN_REMOVED, ACTION_TYPE_PROVIDER_FIRM).getValue();
				LookupSPNStatusActionMapper spOCCActionMapper = new LookupSPNStatusActionMapper(actionMapid);
				LookupSPNWorkflowState spState = new LookupSPNWorkflowState();
				spState.setId(WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
				spnServiceProviderStateDao.updateAllServiceProStatusOverrideForFirmOverride(providerFirmId, overrideComment, reason, spState, modifiedBy, spOCCActionMapper, null, spnIds);
			}else if (WF_STATUS_PF_SPN_MEMBER.equals((String) state.getId())){
				//Run On demand Job to comply the 
				for(Integer spn:spnIds){
					onDemandMemberMaintenanceService.complyProviderFirm(spn, providerFirmId);
				}
				
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNProviderFirmState> findSearchableProviderFirmStatus(Integer buyerId, Integer providerFirmId) {
		List<LookupSPNWorkflowState> states = new ArrayList<LookupSPNWorkflowState>();
		List<String> st = spnProviderFirmStateDao.getValidSearchableStatusList();
		for(String state :st) {
			states.add(new LookupSPNWorkflowState(state));
		}
		return spnProviderFirmStateDao.findProviderFirmStatus(buyerId, providerFirmId, states);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private List<SPNProviderFirmStateHistory> findProviderFirmStatusHistory(Integer buyerId, Integer providerFirmId) {
		return spnProviderFirmStateHistoryDao.findProviderFirmStatusHistory(buyerId, providerFirmId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNProviderDetailsFirmHistoryRowDTO> findProviderFirmStatusHistoryAsDto(Integer buyerId, Integer providerFirmId) {
		List<SPNProviderFirmStateHistory> serviceProviderStateHistoryList = findProviderFirmStatusHistory(buyerId, providerFirmId);
		List<SPNProviderDetailsFirmHistoryRowDTO> list = new ArrayList<SPNProviderDetailsFirmHistoryRowDTO>();
		// we might want to sort this list also.
		for (SPNProviderFirmStateHistory history : serviceProviderStateHistoryList) {
			// pulling out alias SPNs
			if (history.getSpnHeader().getAliasOriginalSpnId() != null) {
				continue;
			}
			SPNProviderDetailsFirmHistoryRowDTO dto = mapToDto(history);
			list.add(dto);
		}
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState getAndLockSPNProviderFirmState(Integer spnId, Integer providerFirmId, String reviewedByUserName, Date originalModifiedDate) throws Exception {
		SPNProviderFirmState modifiedSPNProviderFirmState = lock(reviewedByUserName, spnId, providerFirmId, originalModifiedDate);
		return modifiedSPNProviderFirmState;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState getAndLockSPNProviderFirmState(Integer buyerId, LookupSPNWorkflowState state, String reviewedByUserName) throws Exception {
		List<SPNAuditorQueueCountVO> auditorQueueCountVOs = auditorQueueCountService.getCountsWithDetail(buyerId, state);
		for (SPNAuditorQueueCountVO spnAuditorQueueCountVO : auditorQueueCountVOs) {
			Integer providerFirmId = spnAuditorQueueCountVO.getProviderFirmId();
			Integer spnId = spnAuditorQueueCountVO.getSpnId();
			SPNProviderFirmState modifiedSPNProviderFirmState = getAndLockSPNProviderFirmState(spnId, providerFirmId, reviewedByUserName, spnAuditorQueueCountVO.getModifiedDate());
			if (modifiedSPNProviderFirmState != null) {
				return modifiedSPNProviderFirmState;
			}
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState update(Integer spnId, Integer providerFirmId, LookupSPNWorkflowState state, String modifiedBy, String comment, LookupSPNStatusActionMapper lookupSPNStatusActionMapper) throws Exception {
		SPNProviderFirmState spnProviderFirmState = spnProviderFirmStateDao.findBySpnIdAndProviderFirmId(spnId, providerFirmId);
		spnProviderFirmState.setWfState(state);
		spnProviderFirmState.setModifiedBy(modifiedBy);
		spnProviderFirmState.setModifiedDate(CalendarUtil.getNow());
		spnProviderFirmState.setComment(comment);
		if (lookupSPNStatusActionMapper != null) {
			spnProviderFirmState.setLookupSPNStatusActionMapper(lookupSPNStatusActionMapper);
		}
		return spnProviderFirmStateDao.update(spnProviderFirmState);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState unlock(Integer spnId, Integer providerFirmId) throws Exception {
		SPNProviderFirmState spnProviderFirmState = spnProviderFirmStateDao.findBySpnIdAndProviderFirmId(spnId, providerFirmId);
		spnProviderFirmState.setReviewedBy(null);
		spnProviderFirmState.setReviewedDate(null);
		spnProviderFirmState.setModifiedDate(CalendarUtil.getNow());
		return spnProviderFirmStateDao.update(spnProviderFirmState);
	}

	private SPNProviderDetailsFirmHistoryRowDTO mapToDto(SPNProviderFirmStateHistory history) {
		SPNProviderDetailsFirmHistoryRowDTO dto = new SPNProviderDetailsFirmHistoryRowDTO();
		if (history.getLookupSPNStatusActionMapper() != null && history.getLookupSPNStatusActionMapper().getActionDescription() != null) {
			dto.setAction(history.getLookupSPNStatusActionMapper().getActionDescription());
		} else {
			dto.setAction("Error retrieving Action");
		}
		// SL-12300
		// Including validity date in the history table for network status change in firm profile.
		String validityDate = "";
		if(null!=history.getStatusOverrideEffectiveDate() && history.getStatusOverrideInd()){
			if(!history.getStatusOverrideEffectiveDate().contains("No")){
				validityDate = "until "+ history.getStatusOverrideEffectiveDate();
			}else{
				validityDate = history.getStatusOverrideEffectiveDate();
			}
		}
		dto.setValidityDate(validityDate);
		String modifiedBy = history.getModifiedBy();
		try {
			if (!SystemUserEnum.isSystemUser(modifiedBy)) {
				User user = userDao.findById(modifiedBy);
				if (user != null) {
					if (RoleEnum.PROVIDER_ROLE.getRoleId().intValue() == user.getRole().getId().intValue()) {
						//TODO 
					} else if (RoleEnum.ENTERPRISE_BUYER_ROLE.getRoleId().intValue() == user.getRole().getId().intValue()) {
						BuyerResource buyerResource = getBuyerServices().getBuyerResourceForUserName(modifiedBy);
						if (buyerResource != null) {
							dto.setChangeEnteredByID(buyerResource.getResourceId());
							dto.setChangeEnteredByName(buyerResource.getContact().getFirstName() + " " + buyerResource.getContact().getLastName());
						}
					}
				} else {
					//If we Dont find any user we can consider that SYSTEM  user has done something  ( may be Prod Support Changed the status using the DB script ) 
					dto.setChangeEnteredByID(null);
					dto.setChangeEnteredByName(modifiedBy);
				}
			} else {
				//Action was taken by System user
				dto.setChangeEnteredByID(null);
				dto.setChangeEnteredByName("System User");
			}
			dto.setComment(findComment(history));
			dto.setReason(findReason(history));
		} catch (Exception e) {
			logger.error("", e);
		}
		dto.setDateChanged(history.getModifiedDate());
		dto.setNetwork(history.getSpnHeader().getSpnName());

		return dto;
	}

	private String findReason(SPNProviderFirmStateHistory history) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {

		if (history.getLookupSPNStatusActionMapper() == null) {
			return "Need to initialize ActionMapper";
		}
		String template = history.getLookupSPNStatusActionMapper().getDescription();
		if (history.getLookupSPNStatusOverrideReason() == null) {
			if(null != template && template.contains("${override_reason_description}")){
				return  "The Provider Firm's status is changed to Approved";
			}
			return template;
		}
		String reason = history.getLookupSPNStatusOverrideReason().getDescription();
		if (reason == null || reason.trim().length() == 0) {
			if(null != template && template.contains("${override_reason_description}")){
				return  "The Provider Firm's status is changed to Approved";
			}
			return template;
		}
		VelocityContext context = new VelocityContext();
		context.put("override_reason_description", reason);
		java.io.StringWriter writer = new java.io.StringWriter();
		getVelocityEngine().evaluate(context, writer, "velocity template", template);
		String result = writer.toString();
		return result;
	}

	private String findComment(SPNProviderFirmStateHistory history) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {

		if (history.getLookupSPNStatusActionMapper() == null) {
			return "Need to initialize ActionMapper";
		}

		String template = history.getLookupSPNStatusActionMapper().getDisplayableComments();
		if (template == null) {
			return "";
		}
		final String statusOverrideComment;
		if (history.getStatusOverrideComments() == null) {
			statusOverrideComment = "";
		} else {
			statusOverrideComment = history.getStatusOverrideComments();
		}
		String comments = history.getComment() == null ? " " : history.getComment(); // this is regular comment put by SPN Auditor
		VelocityContext context = new VelocityContext();
		context.put("status_override_comments", statusOverrideComment);
		context.put("regular_comments", comments);
		java.io.StringWriter writer = new java.io.StringWriter();
		getVelocityEngine().evaluate(context, writer, "velocity template", template);
		String result = writer.toString();
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private SPNProviderFirmState lock(String reviewedByUser, Integer spnId, Integer providerFirmId, Date originalModifiedDate) throws Exception {
		return spnProviderFirmStateDao.lock(reviewedByUser, spnId, providerFirmId, originalModifiedDate);
	}

	public SPNProviderFirmState findProviderFirmState(Integer spnId, Integer providerFirmId) throws Exception {
		return spnProviderFirmStateDao.findBySpnIdAndProviderFirmId(spnId, providerFirmId);
	}

	public SPNProviderFirmState findProviderFirmStateByprimaryKey(Integer spnId, Integer providerFirmId) throws Exception {
		return spnProviderFirmStateDao.findByPrimaryKey(spnId, providerFirmId);
	}

	/**
	 * @param auditorQueueCountService the auditorQueueCountService to set
	 */
	public void setAuditorQueueCountService(IAuditorQueueCountService auditorQueueCountService) {
		this.auditorQueueCountService = auditorQueueCountService;
	}

	/**
	 * @param spnProviderFirmStateDao the spnProviderFirmStateDao to set
	 */
	public void setSpnProviderFirmStateDao(SPNProviderFirmStateDao spnProviderFirmStateDao) {
		this.spnProviderFirmStateDao = spnProviderFirmStateDao;
	}

	/**
	 * @param spnProviderFirmStateHistoryDao the spnProviderFirmStateHistoryDao to set
	 */
	public void setSpnProviderFirmStateHistoryDao(SPNProviderFirmStateHistoryDao spnProviderFirmStateHistoryDao) {
		this.spnProviderFirmStateHistoryDao = spnProviderFirmStateHistoryDao;
	}

	public void setBuyerServices(BuyerServices buyerServices) {
		this.buyerServices = buyerServices;
	}

	/**
	 * @return the buyerServices
	 */
	private BuyerServices getBuyerServices() {
		return buyerServices;
	}

	/**
	 * 
	 * @param velocityEngine
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * @return the velocityEngine
	 */
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.interfaces.IProviderFirmStateService#getCountsOfImpactedServiceProOnFirmStatusOverride(java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer getCountsOfImpactedServiceProOnFirmStatusOverride(Integer spnId, Integer providerFirmId) throws Exception {
		//If Business wants this with some Status count we can count the status in JAVA itself
		List<SPNServiceProviderState> spList = spnServiceProviderStateDao.findAllServiceProForSpnAndFirm(spnId, providerFirmId);
		if (spList.isEmpty()) {
			return null;
		}
		return Integer.valueOf(spList.size());
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.interfaces.IProviderFirmStateService#getCountsOfImpactedServiceProOnFirmStatusOverride(java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer getCountsOfImpactedServiceProForFirmBuyer(Integer buyerId,Integer providerFirmId) throws Exception {
		//If Business wants this with some Status count we can count the status in JAVA itself
		List<SPNServiceProviderState> spList = spnServiceProviderStateDao.findAllServiceProForFirmAndBuyer(buyerId, providerFirmId);
		if (spList.isEmpty()) {
			return null;
		}
		Integer providerCount = 0;
		Set<Integer> providerIds = new HashSet<Integer>(); 
		HashMap<String, Integer>  spnMap = new HashMap<String, Integer> ();
		for (SPNServiceProviderState state : spList) {
			SPNHeader spnHeader = state.getServiceProviderStatePk().getSpnHeader();
			providerIds.add(state.getServiceProviderStatePk().getServiceProvider().getId());
		}
		providerCount = providerIds.size();
		//return Integer.valueOf(spList.size());
		return providerCount;
	}
	
	// SL-12300 : Method to fetch the count of approved service providers of a particular provider firm for all the spn's of a particular buyer.
	// Providers in edited and alias spn will not be considered.
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer getCountOfImpactedServiceProForFirmBuyer(Integer buyerId,Integer providerFirmId) throws Exception {
		Integer providerCount = spnServiceProviderStateDao.countOfServiceProForFirmAndBuyer(buyerId, providerFirmId);
		if (providerCount.equals(0)) {
			return null;
		}else{
			return providerCount;
		}
	}

	/**
	 * @param spnServiceProviderStateDao the spnServiceProviderStateDao to set
	 */
	public void setSpnServiceProviderStateDao(SPNServiceProviderStateDao spnServiceProviderStateDao) {
		this.spnServiceProviderStateDao = spnServiceProviderStateDao;
	}

	/**
	 * @param onDemandMemberMaintenanceService the onDemandMemberMaintenanceService to set
	 */
	public void setOnDemandMemberMaintenanceService(
			OnDemandMemberMaintenanceService onDemandMemberMaintenanceService) {
		this.onDemandMemberMaintenanceService = onDemandMemberMaintenanceService;
	}
	
	// Method to find whether an SPN is edited or not.
	public Map<String, Object> getSPNEditInfo(Integer spnId) {
		return spnProviderFirmStateDao.getSPNEditInfo(spnId);
	}
}
