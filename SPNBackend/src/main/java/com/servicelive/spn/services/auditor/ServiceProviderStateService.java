package com.servicelive.spn.services.auditor;

import static com.servicelive.spn.common.SPNBackendConstants.ACTION_TYPE_SERVICE_PROVIDER;
import static com.servicelive.spn.common.SPNBackendConstants.MODIFIED_BY_USER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_PERF_LEVEL_CHANGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.domain.spn.network.SPNServiceProviderState;
import com.servicelive.domain.spn.network.SPNServiceProviderStateHistory;
import com.servicelive.spn.common.ActionMapperEnum;
import com.servicelive.spn.dao.network.SPNServiceProviderStateDao;
import com.servicelive.spn.dao.network.SPNServiceProviderStateHistoryDao;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.buyer.BuyerServices;
import com.servicelive.spn.services.interfaces.IServiceProviderStateService;

/**
 * 
 * @author svanloon
 *
 */
public class ServiceProviderStateService extends BaseServices implements IServiceProviderStateService {

	private BuyerServices buyerServices;
	private SPNServiceProviderStateDao spnServiceProviderStateDao;
	private SPNServiceProviderStateHistoryDao spnServiceProviderStateHistoryDao;
	private VelocityEngine velocityEngine;

	@Transactional
	public void updateServiceProviderStatusPerformanceLevel(Integer spnId, Integer serviceProviderId, String performanceLevelChangeComment, LookupPerformanceLevel performanceLevel, String modifiedBy) throws Exception {
		Integer statusUpdateActionId = ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER, WF_STATUS_SP_SPN_PERF_LEVEL_CHANGE , ACTION_TYPE_SERVICE_PROVIDER).getValue();
		if(-1 == statusUpdateActionId.intValue()) {
			logger.error("Trying to get Action Modified for performanceLevel Which doesnt exists in ENUM");
			throw new  Exception ("Trying to get Action Modified for performanceLevel Which doesnt exists in ENUM");
		}

		LookupSPNStatusActionMapper lookupSPNStatusActionMapper = new LookupSPNStatusActionMapper(statusUpdateActionId);
		spnServiceProviderStateDao.updateServiceProviderStatusPerformanceLevel(spnId, serviceProviderId, performanceLevelChangeComment, performanceLevel, lookupSPNStatusActionMapper, modifiedBy);
	}
	
	@Transactional
	public void updateServiceProviderStatusOverride(List<Integer> spnIds, Integer serviceProviderId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, String validityDate) throws Exception {
		
		
		String id = (String) state.getId();
		/*if(id.equals("SP SPN APPROVED")) {
			statusUpdateActionId = ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER + "#" + id+ "#" + ACTION_TYPE_SERVICE_PROVIDER).getValue();
		} else if(id.equals("SP SPN OUT OF COMPLIANCE")) {
			statusUpdateActionId = 18;
		} else  if(id.equals("SP SPN REMOVED")) {
			statusUpdateActionId = 19;
		}*/
		Integer statusUpdateActionId = ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER, id, ACTION_TYPE_SERVICE_PROVIDER).getValue();
		if(-1 == statusUpdateActionId.intValue()) {
			logger.error("Trying to get Action Modified for *"+ id + "* Which doesnt exists in ENUM");
			throw new  Exception ("Trying to get Action Modified for *"+ id + "* Which doesnt exists in ENUM");
		}

		LookupSPNStatusActionMapper lookupSPNStatusActionMapper = new LookupSPNStatusActionMapper(statusUpdateActionId);
		// SL-12300 : Including validity date as per new requirement.
		spnServiceProviderStateDao.updateServiceProviderStatusOverride(spnIds, serviceProviderId, overrideComment, reason, state, modifiedBy, lookupSPNStatusActionMapper, validityDate);
	}

	@Transactional
	public List<SPNServiceProviderState> findServiceProviderState(Integer buyerId, Integer serviceProviderId) {
		List<SPNServiceProviderState> list = spnServiceProviderStateDao.findServiceProviderState(buyerId, serviceProviderId);
		for(SPNServiceProviderState item: list) {
			if(item.getPerformanceLevel() != null && item.getPerformanceLevel().getDescription() != null) {
				// this code is here just to initialize performanceLevel.
			}
		}
		return list;
	}

	@Transactional
	private List<SPNServiceProviderStateHistory> findServiceProviderStateHistory(Integer buyerId, Integer serviceProviderId) {
    	List<SPNServiceProviderStateHistory> list = spnServiceProviderStateHistoryDao.findServiceProviderStateHistory(buyerId, serviceProviderId);
    	return list;		
	}

	@Transactional
	public List<SPNProviderDetailsFirmHistoryRowDTO> findServiceProviderStateHistoryAsDto(Integer buyerId, Integer serviceProviderId) {
		List<SPNServiceProviderStateHistory> serviceProviderStateHistoryList =  findServiceProviderStateHistory(buyerId, serviceProviderId);
		
		List<SPNProviderDetailsFirmHistoryRowDTO> list = new ArrayList<SPNProviderDetailsFirmHistoryRowDTO>(); 
		// we might want to sort this list also.
		for (SPNServiceProviderStateHistory history : serviceProviderStateHistoryList) {
			// pulling out alias SPNs
			if(history.getSpnHeader().getAliasOriginalSpnId() != null) {
				continue;
			}
			SPNProviderDetailsFirmHistoryRowDTO dto = mapToDto(history);
			list.add(dto);
		}
		return list;
	}
	
	private SPNProviderDetailsFirmHistoryRowDTO mapToDto(SPNServiceProviderStateHistory history) {
		SPNProviderDetailsFirmHistoryRowDTO dto = new SPNProviderDetailsFirmHistoryRowDTO();
		dto.setAction(history.getLookupSPNStatusActionMapper().getActionDescription());
		// SL-12300
		// Including validity date in the history table for network status change in provider profile.
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
			BuyerResource buyerResource = getBuyerServices().getBuyerResourceForUserName(modifiedBy);
			if(buyerResource == null) {
				dto.setChangeEnteredByID(null);
				dto.setChangeEnteredByName("System User");
			} else {
				dto.setChangeEnteredByID(buyerResource.getResourceId());
				dto.setChangeEnteredByName(buyerResource.getContact().getFirstName() + " " + buyerResource.getContact().getLastName()); 
			}
			dto.setComment(findComment(history));
			dto.setReason(findReason(history));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		dto.setDateChanged(history.getModifiedDate());
		dto.setNetwork(history.getSpnHeader().getSpnName());
		
		return dto;
	}

	private String findReason(SPNServiceProviderStateHistory history) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
		String template = history.getLookupSPNStatusActionMapper().getDescription();
		if(history.getLookupSPNStatusOverrideReason() == null) {
			if(null != template && template.contains("${override_reason_description}")){
				return  "The Provider's status is changed to Approved";
			}
			return template;
		}
		String reason = history.getLookupSPNStatusOverrideReason().getDescription();
		if(reason == null || reason.trim().length() == 0) {
			if(null != template && template.contains("${override_reason_description}")){
				return  "The Provider's status is changed to Approved";
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

	
	private String findComment(SPNServiceProviderStateHistory history) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
		
		String template = history.getLookupSPNStatusActionMapper().getDisplayableComments();
		if(template == null) {
			return null;
		}

		final String statusOverrideComment;
		if(history.getStatusOverrideComments() == null) {
			statusOverrideComment = "";
		} else {
			statusOverrideComment = history.getStatusOverrideComments();
		}
		final String performanceLevelOverrideComment;
		if(history.getLookupSPNStatusActionMapper() != null && history.getLookupSPNStatusActionMapper().getId().intValue() == ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER, WF_STATUS_SP_SPN_PERF_LEVEL_CHANGE , ACTION_TYPE_SERVICE_PROVIDER).getValue().intValue()){
			performanceLevelOverrideComment = history.getPerformanceLevelChangeComments();
		} else {
			performanceLevelOverrideComment = "";
		}
		VelocityContext context = new VelocityContext();
		context.put("status_override_comments", statusOverrideComment);
		context.put("perflevel_override_comments", performanceLevelOverrideComment);
		java.io.StringWriter writer = new java.io.StringWriter();
		getVelocityEngine().evaluate(context, writer, "velocity template", template);
		String result = writer.toString();
		return result;
	}
	
	

	/**
	 * @param spnServiceProviderStateDao the spnServiceProviderStateDao to set
	 */
	public void setSpnServiceProviderStateDao(SPNServiceProviderStateDao spnServiceProviderStateDao) {
		this.spnServiceProviderStateDao = spnServiceProviderStateDao;
	}

	/**
	 * @param spnServiceProviderStateHistoryDao the spnServiceProviderStateHistoryDao to set
	 */
	public void setSpnServiceProviderStateHistoryDao(SPNServiceProviderStateHistoryDao spnServiceProviderStateHistoryDao) {
		this.spnServiceProviderStateHistoryDao = spnServiceProviderStateHistoryDao;
	}

	/**
	 * @param buyerServices the buyerServices to set
	 */
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
	 * @param velocityEngine the velocityEngine is set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * @return the velocityEngine
	 */
	private VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		// TODO Auto-generated method stub
		
	}

}
