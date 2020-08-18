package com.servicelive.spn.buyer.membermanagement;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNServiceProviderState;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_REMOVED_FIRM;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE;

/**
 * 
 * 
 * 
 */
@Validation
public class SPNProviderFirmDetailsAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsNetworksModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8300651612859458998L;
	private SPNProviderDetailsNetworksModel model = new SPNProviderDetailsNetworksModel();

	
	public String displayNetworkInfoTabAjax()
	{
		Integer vendorId = getInteger("vendorId");
		String vendorName = getString("vendorName");		
		String firstName = getString("firstName");
		String	lastName = getString("lastName");
			
		
		getRequest().setAttribute("vendorId", vendorId);
		getRequest().setAttribute("firstName", firstName);
		getRequest().setAttribute("lastName", lastName);
		getRequest().setAttribute("vendorName", vendorName);
		
		logger.debug("displayNetworkInfoPageAjax Parameters Here:");
		logger.debug("vendorId:" + vendorId);
		logger.debug("Name:" + firstName + " " + lastName);
		
		return SUCCESS;
	}
	
	public String displayNetworksAjax() {
		initNetworks();
		return "success_networks";
	}

	public String displayReasonsAjax() {
		String wfState = getString("wfState");
		// SL-12300 : Fetching reason codes based on current and new network status.
		String currentWfState = getString("existingStatus");
		Integer spnId = getInteger("spnId");
		Integer resourceId = getInteger("resourceId");
		// SL-12300 : If current and new reason status are same, the previous status will be fetched for an active entry from the
		// spnet_provider_network_override table for the given spnId and provider resource id.
		// If there is no such entry hardcoded values will be used for currentWfState.
		if(wfState.equals(currentWfState)){
			currentWfState = getLookupService().findLastProviderNetworkStatus(spnId, resourceId);
			if(null == currentWfState){
				if((WF_STATUS_PF_SPN_MEMBER).equals(wfState) || (WF_STATUS_PF_SPN_REMOVED_FIRM).equals(wfState)){
					currentWfState = WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;
				}else if((WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE).equals(wfState)){
					currentWfState = WF_STATUS_PF_SPN_MEMBER;
				}else if((WF_STATUS_SP_SPN_APPROVED).equals(wfState) || (WF_STATUS_SP_SPN_REMOVED).equals(wfState)){
					currentWfState = WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE;
				}else if((WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE).equals(wfState)){
					currentWfState = WF_STATUS_SP_SPN_APPROVED;
				}
			}
		}
		List<LookupSPNStatusOverrideReason> reasons = getLookupService().findByWfState(wfState, currentWfState);		
		List<LabelValueBean> reasonOptions = new ArrayList<LabelValueBean>();
		LabelValueBean lvBean;
		for(LookupSPNStatusOverrideReason reason:reasons)
		{
			lvBean = new LabelValueBean(reason.getDescription(), reason.getId() + "");
			reasonOptions.add(lvBean);
		}
		getRequest().setAttribute("reasonOptions", reasonOptions);
		return "success_reasons";
	}

	public String changeNetworkStatusAjax() {
		Integer serviceProviderId = getServiceProviderId();
		List<Integer> spnIds = new ArrayList<Integer>();
		Integer spnId = getInteger("networkId");
		List<Integer>spnIdList=new ArrayList<Integer>();
		spnIdList.add(spnId);
		String wfState = getString("wfState");
		String overrideComment = getString("comment");
		String modifiedBy = getCurrentBuyerResourceUserName();
		Integer reasonId = getInteger("reason");//TODO needs to be mapped.
		LookupSPNStatusOverrideReason reason = new LookupSPNStatusOverrideReason();
		reason.setId(reasonId);
		spnIds.add(spnId);
		LookupSPNWorkflowState state = new LookupSPNWorkflowState(wfState);
		try {
			getServiceProviderStateService().updateServiceProviderStatusOverride(spnIdList, serviceProviderId, overrideComment, reason, state, modifiedBy, null);
		} catch (Exception e) {
			e.printStackTrace();
			//FIXME need to add to logger
		}
		return "success_network_status";
	}

	private void initNetworks()
	{
		Integer buyerId = getCurrentBuyerId();
		Integer serviceProviderId = getServiceProviderId();
		List<SPNServiceProviderState> states = getServiceProviderStateService().findServiceProviderState(buyerId, serviceProviderId);
		List<SPNProviderDetailsNetworkDTO> networkList = new ArrayList<SPNProviderDetailsNetworkDTO>();
		for(SPNServiceProviderState state:states) {
			SPNProviderDetailsNetworkDTO network = mapToDto(state);
			networkList.add(network);
		}
		getModel().setNetworks(networkList);
	}

	private SPNProviderDetailsNetworkDTO mapToDto(SPNServiceProviderState state) {
		SPNHeader spnHeader = state.getServiceProviderStatePk().getSpnHeader();
		SPNProviderDetailsNetworkDTO network = new SPNProviderDetailsNetworkDTO();
		network.setNetworkId(spnHeader.getSpnId());
		network.setNetworkName(spnHeader.getSpnName());
		network.setStatus(String.valueOf(state.getWfState().getDescription()));
		// SL-12300 : Setting the current network status id to the dto variable.
		network.setStatusId(String.valueOf(state.getWfState().getId()));
		if(null != state.getStatusOverrideInd()){
			network.setOverrideInd(state.getStatusOverrideInd());
		}else{
			network.setOverrideInd(false);
		}
		// this assumes that all service providers have a performance level.
		network.setNetworkGroup(Integer.valueOf(String.valueOf(state.getPerformanceLevel().getId())));
		network.setNetworkDescription((String)state.getPerformanceLevel().getDescription());
		return network;
	}

	/**
	 * @return the model
	 */
	public SPNProviderDetailsNetworksModel getModel() {
		return model;
	}
}
