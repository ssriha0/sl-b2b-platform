package com.servicelive.spn.services.network;

import static com.servicelive.spn.common.SPNBackendConstants.ACTION_TYPE_PROVIDER_FIRM;

import static com.servicelive.spn.common.SPNBackendConstants.ACTION_TYPE_SERVICE_PROVIDER;
import static com.servicelive.spn.common.SPNBackendConstants.MODIFIED_BY_MEMBERMAINTENANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.spn.common.ActionMapperEnum;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.MemberMaintenanceBaseVO;
import com.servicelive.spn.common.detached.MemberMaintenanceCriteriaVO;
import com.servicelive.spn.common.detached.MemberMaintenanceDetailsVO;
import com.servicelive.spn.common.detached.MemberMaintenanceProviderFirmVO;
import com.servicelive.spn.common.detached.MemberMaintenanceServiceProviderVO;
import com.servicelive.spn.common.detached.ProviderMatchApprovalCriteriaVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.providermatch.ApprovalCriteriaHelper;
import com.servicelive.spn.services.providermatch.ProviderMatchForApprovalServices;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 
 * @author svanloo
 *
 */
public abstract class MemberMaintenanceService extends BaseServices {
	public  Map<Integer,String> previousCriteriaMap=new HashMap<Integer,String>();

	public Map<Integer, String> getPreviousCriteriaMap() {
		return previousCriteriaMap;
	}

	public void setPreviousCriteriaMap(Map<Integer, String> previousCriteriaMap) {
		this.previousCriteriaMap = previousCriteriaMap;
	}

	public List<Integer> firmexceptionGrace=new ArrayList<Integer>();
	public List<Integer> firmexceptionState=new ArrayList<Integer>();
	public List<Integer> firmexceptionCombined=new ArrayList<Integer>();
	public List<Integer> firmexceptionNull=new ArrayList<Integer>();

	public List<Integer> exceptionGrace=new ArrayList<Integer>();
	public List<Integer> exceptionState=new ArrayList<Integer>();
	public List<Integer> exceptionCombined=new ArrayList<Integer>();
	public List<Integer> exceptionNull=new ArrayList<Integer>();

	

	public List<Integer> getFirmexceptionGrace() {
		return firmexceptionGrace;
	}

	public void setFirmexceptionGrace(List<Integer> firmexceptionGrace) {
		this.firmexceptionGrace = firmexceptionGrace;
	}

	public List<Integer> getFirmexceptionState() {
		return firmexceptionState;
	}

	public void setFirmexceptionState(List<Integer> firmexceptionState) {
		this.firmexceptionState = firmexceptionState;
	}

	public List<Integer> getFirmexceptionCombined() {
		return firmexceptionCombined;
	}

	public void setFirmexceptionCombined(List<Integer> firmexceptionCombined) {
		this.firmexceptionCombined = firmexceptionCombined;
	}

	public List<Integer> getFirmexceptionNull() {
		return firmexceptionNull;
	}

	public void setFirmexceptionNull(List<Integer> firmexceptionNull) {
		this.firmexceptionNull = firmexceptionNull;
	}

	public List<Integer> getExceptionGrace() {
		return exceptionGrace;
	}

	public void setExceptionGrace(List<Integer> exceptionGrace) {
		this.exceptionGrace = exceptionGrace;
	}

	public List<Integer> getExceptionState() {
		return exceptionState;
	}

	public void setExceptionState(List<Integer> exceptionState) {
		this.exceptionState = exceptionState;
	}

	public List<Integer> getExceptionCombined() {
		return exceptionCombined;
	}

	public void setExceptionCombined(List<Integer> exceptionCombined) {
		this.exceptionCombined = exceptionCombined;
	}

	public List<Integer> getExceptionNull() {
		return exceptionNull;
	}

	public void setExceptionNull(List<Integer> exceptionNull) {
		this.exceptionNull = exceptionNull;
	}

	protected  ProviderMatchForApprovalServices providerMatchService;


	@Override
	protected void handleDates(Object entity) {
		((MemberMaintenanceBaseVO) entity).setNewModifiedDate(CalendarUtil.getNow());
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Throwable.class)
	protected abstract void deleteDuplicateMembersOfSPNs(MemberMaintenanceCriteriaVO mmCriteriaVO) throws Exception;
	
	
	/** 
	 * This is template method for processing Provider Firm
	 * @param mmCriteriaVO 
	 * @throws Exception
	 */
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor=Throwable.class)
	protected abstract void processFirm(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception ;
	
	/**
	 * This is template method for processing Service Providers
	 * @param mmCriteriaVO 
	 * @throws Exception
	 */
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor=Throwable.class)
	protected abstract void processServiceProviders(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception ;
	
	/**
	 * 
	 * @param mmCriteriaVO 
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Throwable.class)
	protected void handleSpn(MemberMaintenanceCriteriaVO mmCriteriaVO) throws Exception {
		//Start the process
		long startTimeBeforeApprovalModel=System.currentTimeMillis();
		logger.info("Start time before going inside getApprovalModelForSPN method to get approval criteria is"+startTimeBeforeApprovalModel);
		ApprovalModel approvalModel = providerMatchService
				.getApprovalModelForSPN(mmCriteriaVO.getSpnId());
		
		ProviderMatchApprovalCriteriaVO criteriaVO = ApprovalCriteriaHelper
				.getCriteriaVOFromModel(approvalModel);
		// Step#1
		long start1=System.currentTimeMillis();
		processFirm(mmCriteriaVO, criteriaVO);
		long end1=System.currentTimeMillis();
		logger.info(" time taken to process firm compliance"+(end1-start1));
		// Step#2
		long start2=System.currentTimeMillis();
		processServiceProviders(mmCriteriaVO, criteriaVO);
		long end2=System.currentTimeMillis();
		logger.info(" time taken to process provider complaince"+(start2-end2));
		// step 3
		// if a provider firm is a member of both the current and alias SPN,
		// then
		// delete the alias entries for the providerFirmState and the child
		// ServiceProviderStates
		deleteDuplicateMembersOfSPNs(mmCriteriaVO);		
		long totalTimeTakenforHandleSPN=System.currentTimeMillis()-startTimeBeforeApprovalModel;
		logger.info("Total time taken to complete the job for deleteDuplicateMembersOfSPNs is"+totalTimeTakenforHandleSPN);
		// end the Process

	}
	
	

	protected void log(Integer spnId, String label, List<MemberMaintenanceServiceProviderVO> serviceProviders) {
		if(!logger.isInfoEnabled()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(MemberMaintenanceServiceProviderVO serviceProvider : serviceProviders) {
			if(i%100 == 0 && sb.length() != 0) {
				logger.debug("updating spn[" + spnId + "] - " + label + " = [" + sb.toString() + "]");
				sb = new StringBuilder();
			}
			int serviceProviderId = serviceProvider.getServiceProviderId();
			if(sb.length() != 0) {
				sb.append(",");
			}
			sb.append(serviceProviderId);
			i++;
		}
		logger.debug("updating spn[" + spnId + "] - " + label + " = [" + sb.toString() + "]");
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected List<MemberMaintenanceProviderFirmVO> findOutOfCompliantProviderFirms(List<MemberMaintenanceProviderFirmVO> oocAndMemberProfileFirms, List<MemberMaintenanceProviderFirmVO> compliantProviderFirms) {

		// Create a list of all provider firms that must be taken care of.
		Map<Integer, MemberMaintenanceProviderFirmVO> outOfCompliantFirms = new HashMap<Integer, MemberMaintenanceProviderFirmVO>();
		for(MemberMaintenanceProviderFirmVO providerFirm: oocAndMemberProfileFirms) {
			Integer key = Integer.valueOf(providerFirm.getProviderFirmId());
			outOfCompliantFirms.put(key, providerFirm);
		}

		// Remove provider firms that are now compliant.
		for(MemberMaintenanceProviderFirmVO providerFirm:compliantProviderFirms) {
			Integer key = Integer.valueOf(providerFirm.getProviderFirmId());
			outOfCompliantFirms.remove(key);
		}

		List<MemberMaintenanceProviderFirmVO> results = new ArrayList<MemberMaintenanceProviderFirmVO>();
		results.addAll(outOfCompliantFirms.values());
		return results;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected List<MemberMaintenanceServiceProviderVO> findOutOfCompliantServiceProviders(List<MemberMaintenanceServiceProviderVO> oocAndMemberProfileFirms, List<MemberMaintenanceServiceProviderVO> compliantServiceProviders) {

		// Create a list of all service providers that must be taken care of.
		Map<Integer, MemberMaintenanceServiceProviderVO> outOfCompliantServiceProviders = new HashMap<Integer, MemberMaintenanceServiceProviderVO>();
		for(MemberMaintenanceServiceProviderVO serviceProvider: oocAndMemberProfileFirms) {
			Integer key = Integer.valueOf(serviceProvider.getServiceProviderId());
			outOfCompliantServiceProviders.put(key, serviceProvider);
		}

		// Remove provider firms that are now compliant.
		for(MemberMaintenanceServiceProviderVO serviceProvider:compliantServiceProviders) {
			Integer key = Integer.valueOf(serviceProvider.getServiceProviderId());
			// the following line probably isn't necessary, but I'm not confident that the .remove was removing the value 
			outOfCompliantServiceProviders.put(key, null);
			outOfCompliantServiceProviders.remove(key);
		}

		List<MemberMaintenanceServiceProviderVO> results = new ArrayList<MemberMaintenanceServiceProviderVO>();
		results.addAll(outOfCompliantServiceProviders.values());
		return results;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void updateCompliantProviderFirms(List<MemberMaintenanceProviderFirmVO> compliantProviderFirmList) throws SQLException {
		List<Integer> firmIds = new ArrayList<Integer>();
		if(null!= compliantProviderFirmList && compliantProviderFirmList.size()>0){
			Integer spnId = compliantProviderFirmList.get(0).getSpnId();
			for(MemberMaintenanceProviderFirmVO compliantProviderFirm:compliantProviderFirmList) {
				firmIds.add(Integer.valueOf(compliantProviderFirm.getProviderFirmId()));
			}
			MemberMaintenanceProviderFirmVO compliantProviderFirm = new MemberMaintenanceProviderFirmVO();
			compliantProviderFirm.setSpnWorkflowState((new LookupSPNWorkflowState(WF_STATUS_PF_SPN_MEMBER)));
			compliantProviderFirm.setStatusUpdateActionId(ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_MEMBERMAINTENANCE, WF_STATUS_PF_SPN_MEMBER, ACTION_TYPE_PROVIDER_FIRM).getValue());
			compliantProviderFirm.setProviderFirmIdList(firmIds);
			compliantProviderFirm.setSpnId(spnId);
			updateProviderFirm(compliantProviderFirm);
		}else{
			logger.debug("compliantProviderFirmList is null or list size is zero");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void updateNonCompliantProviderFirms(Integer spnId, List<MemberMaintenanceProviderFirmVO> nonCompliantProviderFirmList) throws SQLException {
		List<Integer> firmIds = new ArrayList<Integer>();
		if(null !=nonCompliantProviderFirmList && nonCompliantProviderFirmList.size()>0){
		for(MemberMaintenanceProviderFirmVO nonCompliantProviderFirm:nonCompliantProviderFirmList) {
			firmIds.add(Integer.valueOf(nonCompliantProviderFirm.getProviderFirmId()));
		}
		MemberMaintenanceProviderFirmVO nonCompliantProviderFirm = new MemberMaintenanceProviderFirmVO();
		LookupSPNWorkflowState state = new LookupSPNWorkflowState();
		state.setId(WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE);
		nonCompliantProviderFirm.setSpnWorkflowState(state);
		nonCompliantProviderFirm.setStatusUpdateActionId(ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_MEMBERMAINTENANCE, WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE, ACTION_TYPE_PROVIDER_FIRM).getValue());
		nonCompliantProviderFirm.setProviderFirmIdList(firmIds);
		nonCompliantProviderFirm.setSpnId(spnId);
		updateProviderFirm(nonCompliantProviderFirm);
		convertServiceProvidersToNonCompliant(firmIds, spnId);
		} else{
			logger.debug("nonCompliantProviderFirmList is null or list size is zero");
		}
		// SL-20081 : Code to update the network status of providers to OOC for Overridden OOC and Removed firms. 
		updateProvidersForOverriddenProviderFirms(spnId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	private void updateProvidersForOverriddenProviderFirms(Integer spnId) throws SQLException{
		@SuppressWarnings("unchecked")
		List<Integer> providerFirmIds = (List<Integer>)getSqlMapClient().queryForList("network.membermaintenance.getOutOfCompliantAndRemovedOverriddenProviderFirms", spnId);
		if(null != providerFirmIds && !providerFirmIds.isEmpty()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("providerFirmIdList", providerFirmIds);
			params.put("now", CalendarUtil.getNow());
			params.put("state", WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
			params.put("removedState", WF_STATUS_SP_SPN_REMOVED);
			params.put("spnId", spnId);
			params.put("statusUpdateActionId", ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_MEMBERMAINTENANCE,  WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE, ACTION_TYPE_PROVIDER_FIRM).getValue());
			getSqlMapClient().update("network.membermaintenance.convertServiceProvidersToNonCompliant", params);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void insertUpdateCompliantServiceProviders(List<MemberMaintenanceServiceProviderVO> compliantServiceProviderList, Integer spnId) throws SQLException {
		List<Integer> serviceProviderIdsForUpdate = new ArrayList<Integer>();
		MemberMaintenanceServiceProviderVO compliantServiceProviderForUpdate = new MemberMaintenanceServiceProviderVO();
		LookupSPNWorkflowState state = new LookupSPNWorkflowState();
		state.setId(WF_STATUS_SP_SPN_APPROVED);
		if(null!=compliantServiceProviderList && compliantServiceProviderList.size()>0){
			for(MemberMaintenanceServiceProviderVO compliantServiceProvider:compliantServiceProviderList) {
				compliantServiceProvider.setSpnWorkflowState(state);
				if(compliantServiceProvider.getCreatedDate() == null) {
					compliantServiceProvider.setSpnId(spnId);
					compliantServiceProvider.setCreatedDate(CalendarUtil.getNow());
					insertServiceProvider(compliantServiceProvider);
				} else {
					serviceProviderIdsForUpdate.add(Integer.valueOf(compliantServiceProvider.getServiceProviderId()));
				}
			}
			
			//set provider details for update
			if(null!=serviceProviderIdsForUpdate && serviceProviderIdsForUpdate.size()>0){
			compliantServiceProviderForUpdate.setSpnId(spnId);
			compliantServiceProviderForUpdate.setSpnWorkflowState(state);
			compliantServiceProviderForUpdate.setServiceProviderIds(serviceProviderIdsForUpdate);
			updateServiceProvider(compliantServiceProviderForUpdate);
			}
		}else{
			logger.debug("compliantServiceProviderList is null for list size is zero");
		}
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void updateNonCompliantServiceProviders(List<MemberMaintenanceServiceProviderVO> nonCompliantServiceProviderList) throws SQLException {
		List<Integer> serviceProviderIds = new ArrayList<Integer>();
		if(null != nonCompliantServiceProviderList && nonCompliantServiceProviderList.size()>0){
			Integer spnId = nonCompliantServiceProviderList.get(0).getSpnId();
			for(MemberMaintenanceServiceProviderVO nonCompliantServiceProvider:nonCompliantServiceProviderList) {
				serviceProviderIds.add(Integer.valueOf(nonCompliantServiceProvider.getServiceProviderId()));
			}
			MemberMaintenanceServiceProviderVO nonCompliantServiceProvider = new MemberMaintenanceServiceProviderVO();
			LookupSPNWorkflowState state = new LookupSPNWorkflowState(WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
			nonCompliantServiceProvider.setSpnId(spnId);
			nonCompliantServiceProvider.setSpnWorkflowState(state);
			nonCompliantServiceProvider.setServiceProviderIds(serviceProviderIds);
			updateServiceProvider(nonCompliantServiceProvider);	
		}else{
			logger.info("nonCompliantServiceProviderList is null for list size is zero");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void convertServiceProvidersToNonCompliant(List<Integer> providerFirmIds, Integer spnId) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("providerFirmIdList", providerFirmIds);
		params.put("now", CalendarUtil.getNow());
		params.put("state", WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
		params.put("removedState", WF_STATUS_SP_SPN_REMOVED);
		params.put("spnId", spnId);
		params.put("statusUpdateActionId", ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_MEMBERMAINTENANCE,  WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE, ACTION_TYPE_PROVIDER_FIRM).getValue());
		getSqlMapClient().update("network.membermaintenance.convertServiceProvidersToNonCompliant", params);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void updateProviderFirm(MemberMaintenanceProviderFirmVO providerFirm) throws SQLException {
		handleDates(providerFirm);
		getSqlMapClient().update("network.membermaintenance.updateProviderFirm", providerFirm);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void updateServiceProvider(MemberMaintenanceServiceProviderVO serviceProvider) throws SQLException {
		if(serviceProvider == null) {
			// this isn't really necessary, but I'm trying to make sure I'm avoiding a bug.
			return;
		}
		handleDates(serviceProvider);
		LookupSPNWorkflowState spnWorkflowState=null;
		spnWorkflowState = serviceProvider.getSpnWorkflowState();
		if(null != spnWorkflowState){
			if((null != spnWorkflowState.getId()) &&(spnWorkflowState.getId().equals(WF_STATUS_SP_SPN_APPROVED))) {
				
				serviceProvider.setStatusUpdateActionId(ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_MEMBERMAINTENANCE , WF_STATUS_SP_SPN_APPROVED, ACTION_TYPE_SERVICE_PROVIDER).getValue());
			}
			else {
					// assume that it can only be out of compliance.
					serviceProvider.setStatusUpdateActionId(ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_MEMBERMAINTENANCE ,WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE,ACTION_TYPE_SERVICE_PROVIDER).getValue());
			}
			
		}
		getSqlMapClient().update("network.membermaintenance.updateServiceProvider", serviceProvider);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	protected void insertServiceProvider(MemberMaintenanceServiceProviderVO serviceProvider) throws SQLException {
		handleDates(serviceProvider);
		serviceProvider.setStatusUpdateActionId(ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_MEMBERMAINTENANCE ,WF_STATUS_SP_SPN_APPROVED, ACTION_TYPE_SERVICE_PROVIDER).getValue());
		getSqlMapClient().insert("network.membermaintenance.insertServiceProvider", serviceProvider);
	}

	/**
	 * 
	 * @param providerMatchService
	 */
	public void setProviderMatchService(ProviderMatchForApprovalServices providerMatchService) {
		this.providerMatchService = providerMatchService;
	}
	
	/**
	 * @param spnId
	 * @return
	 */
	protected MemberMaintenanceCriteriaVO getMMCriteriaVO(Integer spnId, Integer providerFirmId) {
		MemberMaintenanceCriteriaVO criteriaVO = new MemberMaintenanceCriteriaVO();
		criteriaVO.setSpnId(spnId);
		criteriaVO.setProviderFirmId(providerFirmId);
		return criteriaVO;
	}
	
	/**
	 * @param mmCriteriaVO
	 * @throws Exception
	 * @throws SQLException
	 */
	protected void complyServiceProvider(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO)
			throws Exception, SQLException {
		 
		Integer spnId = mmCriteriaVO.getSpnId();
		Integer providerFirmId = mmCriteriaVO.getProviderFirmId();		
		List<MemberMaintenanceServiceProviderVO> oocAndApprovedServiceProviders = providerMatchService.getOutOfCompliantAndApprovedServiceProvider(mmCriteriaVO);
		// step 3
		List<MemberMaintenanceServiceProviderVO> compliantServiceProviders = providerMatchService.getCompliantServiceProviderForMemberMaintenance(mmCriteriaVO,criteriaVO);
		logger.debug(" ***** Completed getCompliantServiceProviderForMemberMaintenance for : " + spnId + "] And provider Firm Id = ["+providerFirmId +"] at : "+ new java.util.Date());
		
		insertUpdateCompliantServiceProviders(compliantServiceProviders, spnId);
		log(spnId, "compliantServiceProviders", compliantServiceProviders);
		// step 4
		List<MemberMaintenanceServiceProviderVO> noncompliantServiceProviders = findOutOfCompliantServiceProviders(oocAndApprovedServiceProviders, compliantServiceProviders);
		
		updateNonCompliantServiceProviders(noncompliantServiceProviders);
		log(spnId, "noncompliantServiceProviders", noncompliantServiceProviders);
	}
	
	
	
	public void processMainServices(Integer spnId) throws Exception {
		logger.info("processing main service for "+spnId);

		// fetch Services details of all providers for every spn
		List<MemberMaintenanceDetailsVO> mainServicesDetailsList = providerMatchService
				.getMainServices(spnId);

		if (null != mainServicesDetailsList
				&& mainServicesDetailsList.size() > SPNBackendConstants.ZERO) {
			for (MemberMaintenanceDetailsVO firmCredentialNew : mainServicesDetailsList) {
				firmCredentialNew.setCriteriaId(SPNBackendConstants.MAIN_SERVICES_COMPLIANCE_CRITERIA_ID);
				firmCredentialNew.setSpnId(spnId);
				if (true) {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
				}
			}
			// get previous elements for language criteraia for providers if any
			List<MemberMaintenanceDetailsVO> mainServicesPreviousList = providerMatchService
					.getPreviousProviderCredentialDetailsForCriteria(SPNBackendConstants.MAIN_SERVICES_COMPLIANCE_CRITERIA_ID);
			// comparing previous and new elements
			if (null != mainServicesPreviousList
					&& mainServicesPreviousList.size() > 0) {
				logger.info("processing main service criteria comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : mainServicesDetailsList) {
					newProviderCredentialMap.put(credential.getResourceId()
							, credential);
				}
				for (MemberMaintenanceDetailsVO credential : mainServicesPreviousList) {
					previousProviderCredentialMap.put(credential
							.getResourceId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : mainServicesDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : mainServicesPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
			} else {
				// insert provider Main Services Compliance
				providerMatchService
						.insertProviderCredentialStatus(mainServicesDetailsList);
			}

		}
	}

	public void processMainServicesData(Integer spnId,List<Integer> nodeIds) throws Exception {
		logger.info("processing main service for "+spnId);

		// fetch Services details of all providers for every spn
		List<MemberMaintenanceDetailsVO> mainServicesDetailsList = null;
		HashMap<Integer, String> resourceList=new HashMap<Integer, String>();
		HashMap<String, String> processMap=new HashMap<String, String>();
		if(nodeIds.size()>0){
			mainServicesDetailsList=providerMatchService
				.getMainServices(spnId);
			logger.info("processing main service for 001 "+spnId);

			resourceList= providerMatchService
				.getResourceForMainServices(nodeIds);
			logger.info("processing main service for 002 "+spnId);

			processMap=providerMatchService.getCriteriaValueNameForNodeIds(nodeIds);
			logger.info("processing main service for 003 "+spnId);

		}
		
		if (null != mainServicesDetailsList
				&& mainServicesDetailsList.size() > SPNBackendConstants.ZERO) {
			for (MemberMaintenanceDetailsVO firmCredentialNew : mainServicesDetailsList) {
				
				firmCredentialNew.setCriteriaValueName(processMap.get(firmCredentialNew.getSpnCriteriaValue()));
				
				if (evaluateMainServiceOrSkillCompliance(
						firmCredentialNew.getResourceId(),firmCredentialNew.getSpnCriteriaValue(),
						resourceList))  {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
				}
			}
			
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setSpnId(spnId);
			memberMaintenanceCriteriaVO.setCriteriaId(SPNBackendConstants.MAIN_SERVICES_COMPLIANCE_CRITERIA_ID);
			logger.info("processing main service for 004 "+spnId);
			List<MemberMaintenanceDetailsVO> mainServicesPreviousList =null;
			if(previousCriteriaMap.containsKey(new Long(spnId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(SPNBackendConstants.MAIN_SERVICES_COMPLIANCE_CRITERIA_ID))
						{
			
			
			// get previous elements for language criteraia for providers if any
			 mainServicesPreviousList = providerMatchService
					.getPreviousProviderCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
			// comparing previous and new elements
						}
			}
				logger.info("processing main service criteria comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				if(null!=mainServicesDetailsList && mainServicesDetailsList.size()>0){
				for (MemberMaintenanceDetailsVO credential : mainServicesDetailsList) {
					newProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				if(null!=mainServicesPreviousList && mainServicesPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO credential : mainServicesPreviousList) {
					previousProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				if(null!=mainServicesDetailsList && mainServicesDetailsList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : mainServicesDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
				}
				if(null!=mainServicesPreviousList && mainServicesPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : mainServicesPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId().toString()+"|"+
					firmCredentialPrevious.getSpnCriteriaValue().toString())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
				}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
			

		}
	}
	
	
	public void processCategoriesData(Integer spnId,List<Integer> nodeIds,int criteriaId) throws Exception {
		logger.info("processing category data for "+spnId);

		// fetch category details of all providers for every spn
		List<MemberMaintenanceDetailsVO> mainServicesDetailsList = null;
		HashMap<Integer, String> resourceList=new HashMap<Integer, String>();

		HashMap<String, String> processMap=new HashMap<String, String>();
		if(nodeIds.size()>0){
			if(criteriaId==SPNBackendConstants.CATEGORY_CRITERIA_ID){
			mainServicesDetailsList=providerMatchService
				.getCategory(spnId);
			}
			else
			{
				mainServicesDetailsList=providerMatchService
						.getSubCategory(spnId);	
			}
			resourceList= providerMatchService
				.getResourceForCategory(nodeIds);
			
			processMap=providerMatchService.getCriteriaValueNameForNodeIds(nodeIds);
		}
		
		if (null != mainServicesDetailsList
				&& mainServicesDetailsList.size() > SPNBackendConstants.ZERO) {
			for (MemberMaintenanceDetailsVO firmCredentialNew : mainServicesDetailsList) {
				
				firmCredentialNew.setCriteriaValueName(processMap.get(firmCredentialNew.getSpnCriteriaValue()));
				
				if (evaluateMainServiceOrSkillCompliance(
						firmCredentialNew.getResourceId(),firmCredentialNew.getSpnCriteriaValue(),
						resourceList)) {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
				}
			}
			
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setSpnId(spnId);
			memberMaintenanceCriteriaVO.setCriteriaId(criteriaId);
			List<MemberMaintenanceDetailsVO> mainServicesPreviousList =null;
			if(previousCriteriaMap.containsKey(new Long(spnId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(criteriaId))
						{
			
			// get previous elements for language criteraia for providers if any
			mainServicesPreviousList = providerMatchService
					.getPreviousProviderCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
			// comparing previous and new elements
						}
			}
				logger.info("processing main service criteria comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				if(null!=mainServicesDetailsList && mainServicesDetailsList.size()>0){
				for (MemberMaintenanceDetailsVO credential : mainServicesDetailsList) {
					newProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				if(null!=mainServicesPreviousList && mainServicesPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO credential : mainServicesPreviousList) {
					previousProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				if(null!=mainServicesDetailsList && mainServicesDetailsList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : mainServicesDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
				}
				if(null!=mainServicesPreviousList && mainServicesPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : mainServicesPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId().toString()+"|"+
					firmCredentialPrevious.getSpnCriteriaValue().toString())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
				}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
			

		}
	}
	
	
	public void processSkillsData(Integer spnId,List<Integer> templateIds) throws Exception {
		logger.info("processing skill for "+spnId);

		// fetch Skill details of all providers for every spn
		List<MemberMaintenanceDetailsVO> mainSkillsDetailsList = null;
		Map<String, String> processMap=new HashMap<String, String>();
		Map<Integer, String> resourceList=new HashMap<Integer, String>();

		logger.info("processing skill 001 "+spnId);

		if(templateIds.size()>0){
			mainSkillsDetailsList=providerMatchService
				.getSkills(spnId);

			logger.info("processing skill 002 "+spnId);

			resourceList=providerMatchService
				.getResourceHavingSkills(templateIds,spnId); 
			logger.info("processing skill 003 "+spnId);

			processMap=providerMatchService.getCriteriaValueNameForTemplateIds(templateIds);
		}
		logger.info("processing skill 004 "+spnId);

		
		if (null != mainSkillsDetailsList
				&& mainSkillsDetailsList.size() > SPNBackendConstants.ZERO) {
			for (MemberMaintenanceDetailsVO firmCredentialNew : mainSkillsDetailsList) {
				firmCredentialNew.setCriteriaValueName(processMap.get(firmCredentialNew.getSpnCriteriaValue()));

				if (evaluateMainServiceOrSkillCompliance(
						firmCredentialNew.getResourceId(),firmCredentialNew.getSpnCriteriaValue(),
						resourceList)) {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
				}
			}
			
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setSpnId(spnId);
			memberMaintenanceCriteriaVO.setCriteriaId(SPNBackendConstants.SKILLS_COMPLIANCE_CRITERIA_ID);
			logger.info("processing skill 005 "+spnId);
			List<MemberMaintenanceDetailsVO> mainSkillsPreviousList =null;
			if(previousCriteriaMap.containsKey(new Long(spnId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(SPNBackendConstants.SKILLS_COMPLIANCE_CRITERIA_ID))
						{
			
			
			
			// get previous elements for skill criteraia for providers if any
							mainSkillsPreviousList = providerMatchService
					.getPreviousProviderCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
						}
			}
			// comparing previous and new elements
			logger.info("processing skill 006 "+spnId);

				logger.info("processing skill criteria comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				if(null!=mainSkillsDetailsList && mainSkillsDetailsList.size()>0){
				for (MemberMaintenanceDetailsVO credential : mainSkillsDetailsList) {
					newProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				if(null!=mainSkillsPreviousList && mainSkillsPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO credential : mainSkillsPreviousList) {
					previousProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				if(null!=mainSkillsDetailsList && mainSkillsDetailsList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : mainSkillsDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
				}
				if(null!=mainSkillsPreviousList && mainSkillsPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : mainSkillsPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId().toString()+"|"+
					firmCredentialPrevious.getSpnCriteriaValue().toString())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
				}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
			

		}
	}
	
	
	public void processLanguageData(Integer spnId,List<Integer> languageIds) throws Exception {
		logger.info("processing Language compliance for "+spnId);
		HashMap<String, String> processMap=new HashMap<String, String>();
		Map<Integer, String> resourceList=new HashMap<Integer, String>();

		// fetch language details of all providers for every spn
		List<MemberMaintenanceDetailsVO> mainLanguageDetailsList = null;
		if (languageIds.size() > 0) {
			mainLanguageDetailsList = providerMatchService
					.getResourceLanguages(spnId);
			resourceList = providerMatchService
					.getResourceForLanguageCriteria(languageIds);
			processMap=providerMatchService.getCriteriaValueNameForLanguageIds(languageIds);
		}
		if (null != mainLanguageDetailsList
				&& mainLanguageDetailsList.size() > SPNBackendConstants.ZERO) {
			for (MemberMaintenanceDetailsVO firmCredentialNew : mainLanguageDetailsList) {
				firmCredentialNew.setCriteriaValueName(processMap.get(firmCredentialNew.getSpnCriteriaValue()));

				if (evaluateMainServiceOrSkillCompliance(
						firmCredentialNew.getResourceId(),firmCredentialNew.getSpnCriteriaValue(),
						resourceList)) {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
				}
			}
			
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setSpnId(spnId);
			memberMaintenanceCriteriaVO.setCriteriaId(SPNBackendConstants.LANGUAGE_COMPLIANCE_CRITERIA_ID);
			List<MemberMaintenanceDetailsVO> mainLanguagePreviousList =null;
			if(previousCriteriaMap.containsKey(new Long(spnId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(SPNBackendConstants.LANGUAGE_COMPLIANCE_CRITERIA_ID))
						{
			
			// get previous elements for skill criteraia for providers if any
			mainLanguagePreviousList = providerMatchService
					.getPreviousProviderCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
						}
			}
			// comparing previous and new elements
		
				logger.info("processing language criteria comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				if(null!=mainLanguageDetailsList && mainLanguageDetailsList.size()>0){
				for (MemberMaintenanceDetailsVO credential : mainLanguageDetailsList) {
					newProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				if(null!=mainLanguagePreviousList && mainLanguagePreviousList.size()>0){

				for (MemberMaintenanceDetailsVO credential : mainLanguagePreviousList) {
					previousProviderCredentialMap.put(credential.getResourceId().toString()+"|"+credential.getSpnCriteriaValue().toString()
							, credential);
				}
				}
				
				if(null!=mainLanguageDetailsList && mainLanguageDetailsList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : mainLanguageDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId().toString()+"|"+firmCredentialNew.getSpnCriteriaValue().toString());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
				}
				
				if(null!=mainLanguagePreviousList && mainLanguagePreviousList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : mainLanguagePreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId().toString()+"|"+
					firmCredentialPrevious.getSpnCriteriaValue().toString())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
				}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
			

		}
	}
	
	
	
	public void processSkills(Integer spnId) throws Exception {
		logger.info("processing Skills for "+spnId);

		// fetch Skill details of all providers for every spn
		List<MemberMaintenanceDetailsVO> mainSkillsDetailsList = providerMatchService
				.getSkills(spnId);

		if (null != mainSkillsDetailsList
				&& mainSkillsDetailsList.size() > SPNBackendConstants.ZERO) {
			for (MemberMaintenanceDetailsVO firmCredentialNew : mainSkillsDetailsList) {
				firmCredentialNew.setCriteriaId(SPNBackendConstants.SKILLS_COMPLIANCE_CRITERIA_ID);
				firmCredentialNew.setSpnId(spnId);
				if (evaluateMainSkillsCompliance(
						firmCredentialNew.getCriteriaValueList(),
						firmCredentialNew.getServiceTypeList())) {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					firmCredentialNew
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
				}
			}
			// get previous elements for language criteraia for providers if any
			List<MemberMaintenanceDetailsVO> mainSkillsPreviousList = providerMatchService
					.getPreviousProviderCredentialDetailsForCriteria(SPNBackendConstants.SKILLS_COMPLIANCE_CRITERIA_ID);
			// comparing previous and new elements
			if (null != mainSkillsPreviousList
					&& mainSkillsPreviousList.size() > 0) {
				logger.info("processing main skills criteria comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : mainSkillsDetailsList) {
					newProviderCredentialMap.put(credential.getResourceId()
							, credential);
				}
				for (MemberMaintenanceDetailsVO credential : mainSkillsPreviousList) {
					previousProviderCredentialMap.put(credential
							.getResourceId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : mainSkillsDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : mainSkillsPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
			} else {
				// insert provider skill Compliance
				providerMatchService
						.insertProviderCredentialStatus(mainSkillsDetailsList);
			}

		}
	}
	
	
	public boolean evaluateMainServiceOrSkillCompliance(Integer resourceId,String criteriaValue,
			Map<Integer,String> resourceList) {
		
		if(resourceList.containsKey(new Long(resourceId)))
		{
			
			if(null!=resourceList.get(new Long(resourceId))){
			String[] serviceTypesList=resourceList.get(new Long(resourceId)).split(",");
			if(Arrays.asList(serviceTypesList).contains(criteriaValue))
			{
				return true;
			}
		}	

		}
		
		return false;
	}
	
	public boolean evaluateMainSkillsCompliance(String criteriaValueList,String serviceTypeList){
		if(null!=criteriaValueList && null!=serviceTypeList)
		{
		String[] criteriaValuesList=criteriaValueList.split("@");
		String[] serviceTypesList=serviceTypeList.split("@");
		
		for (String criteriaValue : criteriaValuesList) {
			if(!Arrays.asList(serviceTypesList).contains(criteriaValue))
			{
				return false;
			}
		}
		return true;
		}
		return false;
	}
	
	public boolean evaluteStateException1(String credentialState,String exceptionValue)
	{
		if(null!=credentialState && null!=exceptionValue)
		{
		String stateExceptions[]=exceptionValue.split(",");
		
		for (String state : stateExceptions) {
			if(state.equals(credentialState))
			{
				return true;
			}
		}
		}
			return false;
	}
}
