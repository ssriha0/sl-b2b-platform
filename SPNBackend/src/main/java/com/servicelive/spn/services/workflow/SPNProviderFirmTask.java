package com.servicelive.spn.services.workflow;

import static com.servicelive.spn.common.SPNBackendConstants.ACTION_TYPE_PROVIDER_FIRM;
import static com.servicelive.spn.common.SPNBackendConstants.ACTION_TYPE_PROVIDER_FIRM_SPN_AUDITOR;
import static com.servicelive.spn.common.SPNBackendConstants.MODIFIED_BY_USER;

import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.common.ActionMapperEnum;
import com.servicelive.spn.common.SystemUserEnum;


/**
 * 
 * @author svanloon
 *
 */
public abstract class SPNProviderFirmTask {
	private Integer spnId;
	private Integer providerFirmId;
	private String modifiedBy;
	private LookupSPNWorkflowState lookupSPNWorkflowState;
	private String comment;
	private LookupSPNStatusActionMapper lookupSPNStatusActionMapper;
	

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy 
	 * @param state 
	 */
	public SPNProviderFirmTask(Integer spnId, Integer providerFirmId, String modifiedBy, String state) {
		this(spnId, providerFirmId, modifiedBy, state, null);
	}
	
	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @param state
	 * @param comment
	 */
	public SPNProviderFirmTask(Integer spnId, Integer providerFirmId, String modifiedBy, String state, String comment) {
		this.spnId = spnId;
		this.providerFirmId = providerFirmId;
		this.modifiedBy = modifiedBy;
		this.lookupSPNWorkflowState = createLookupSPNWorkflowState(state);
		this.comment = comment;
	}

	/**
	 * 
	 * @param state
	 * @return LookupSPNWorkflowState
	 */
	protected LookupSPNWorkflowState createLookupSPNWorkflowState(String state) {
		LookupSPNWorkflowState lookupSPNWorkflowState1 = new LookupSPNWorkflowState();
		lookupSPNWorkflowState1.setId(state);
		return lookupSPNWorkflowState1;
	}

	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}

	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	/**
	 * @return the providerFirmId
	 */
	public Integer getProviderFirmId() {
		return providerFirmId;
	}

	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the lookupSPNWorkflowState
	 */
	public LookupSPNWorkflowState getLookupSPNWorkflowState() {
		return lookupSPNWorkflowState;
	}

	/**
	 * @param lookupSPNWorkflowState the lookupSPNWorkflowState to set
	 */
	public void setLookupSPNWorkflowState(
			LookupSPNWorkflowState lookupSPNWorkflowState) {
		this.lookupSPNWorkflowState = lookupSPNWorkflowState;
	}
	/**
	 * 
	 * @return String
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the lookupSPNStatusActionMapper
	 */
	public LookupSPNStatusActionMapper getLookupSPNStatusActionMapper() {
		return lookupSPNStatusActionMapper;
	}

	/**
	 * @param lookupSPNStatusActionMapper the lookupSPNStatusActionMapper to set
	 */
	public void setLookupSPNStatusActionMapper(
			LookupSPNStatusActionMapper lookupSPNStatusActionMapper) {
		this.lookupSPNStatusActionMapper = lookupSPNStatusActionMapper;
	}
	
	/**
	 * @param modifiedBy
	 * @param state
	 */
	protected  void setActionMapperForTask(String modifiedBy, String state) {
		Integer statusUpdateActionId = Integer.valueOf(-1); 
		if(SystemUserEnum.isSystemUser(modifiedBy)){
			statusUpdateActionId = ActionMapperEnum.getActiomMapperEnum(modifiedBy, state, ACTION_TYPE_PROVIDER_FIRM).getValue();
		}
		else {
			statusUpdateActionId = ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER, state, ACTION_TYPE_PROVIDER_FIRM_SPN_AUDITOR).getValue();
		}
		//If we do not have Action Mapped for combination supplied set to NULL so that when we make the status_action_mapper be non nullable column we can catch the NON mapped combination
		if(statusUpdateActionId.intValue() > -1){
			LookupSPNStatusActionMapper lookupSPNStatusActionMapper = new LookupSPNStatusActionMapper(statusUpdateActionId);
			setLookupSPNStatusActionMapper(lookupSPNStatusActionMapper);
		}
		else {
			setLookupSPNStatusActionMapper(null);
		}
		
	}
	
}
