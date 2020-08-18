package com.servicelive.spn.buyer.membermanagement;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNServiceProviderState;

/**
 * 
 * 
 * 
 */
@Validation
public class SPNProviderDetailsNetworksAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsNetworksModel> {

	private static final long serialVersionUID = 20010210L;
	private SPNProviderDetailsNetworksModel model = new SPNProviderDetailsNetworksModel();

	
	public String displayNetworkInfoPageAjax()
	{
		Integer vendorId = getInteger("vendorId");
		Integer vendorResourceId = getInteger("vendorResourceId");		
		String firstName = getString("firstName");
		String	lastName = getString("lastName");
	
		getRequest().setAttribute("vendorId", vendorId);
		getRequest().setAttribute("vendorResourceId", vendorResourceId);
		getRequest().setAttribute("firstName", firstName);
		getRequest().setAttribute("lastName", lastName);
		
		logger.debug("displayNetworkInfoPageAjax Parameters Here:");
		logger.debug("vendorId:" + vendorId);
		logger.debug("vendorResourceId:" + vendorResourceId);
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
				if((WF_STATUS_SP_SPN_APPROVED).equals(wfState) || (WF_STATUS_SP_SPN_REMOVED).equals(wfState)){
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
		Integer spnId = getInteger("networkId");
		String wfState = getString("wfState");
		// SL-12300 : Including validity date as per new requirement.
		// The new network status will be override the current until this validity date.
		if(wfState==""){
			wfState = getString("networkStatus").replace("%20", " ");
		}
		logger.info("wfState>>"+wfState);
		String overrideComment = getString("comment");
		try{
			overrideComment = URLDecoder.decode(overrideComment , "UTF-8");
			overrideComment = overrideComment.replaceAll("-prcntg-", "%");
		}catch(UnsupportedEncodingException e){
			logger.error("Exception while decoding due to "+e.getMessage());
		}
		if(overrideComment==""){
			overrideComment = getString("comments");
		}
		logger.info("comments>>"+overrideComment);
		String validityDate = getString("validityDate");
		logger.info("validityDate>>"+validityDate);
		String applyAll = getString("applyAll");
		Integer buyerId = getCurrentBuyerId();
		boolean applyAllNetworks = false;
		if("true".equals(applyAll)){
			applyAllNetworks = true;
		}
		String modifiedBy = getCurrentBuyerResourceUserName();
		Integer reasonId = getInteger("reason");//TODO needs to be mapped.
		final LookupSPNStatusOverrideReason reason;
		if(reasonId != null) {
			reason = new LookupSPNStatusOverrideReason();
			reason.setId(reasonId);
		} else {
			reason = null;
		}
		List<Integer> spnIds = new ArrayList<Integer>();
		if(applyAllNetworks){
			List<SPNServiceProviderState> states = getServiceProviderStateService().findServiceProviderState(buyerId, serviceProviderId);
			//SL-12300 : Removing alias spn's from the list. 
			List<SPNServiceProviderState> providersListWithoutAlias = removeAliasSPNFromProvidersList(states);
			for(SPNServiceProviderState state:providersListWithoutAlias) {
				SPNHeader spnHeader = state.getServiceProviderStatePk().getSpnHeader();
				// SL-12300 : As per the requirement buyer cannot override Firm/Provider of a particular SPN
				// if that SPN is Edited. Hence that info. need to be fetched from spnet_hdr.
				Map<String, Object> map = getProviderFirmStateService().getSPNEditInfo(spnHeader.getSpnId());
				if(null == map){
					spnIds.add(spnHeader.getSpnId());
				}
			}
		}
		if(spnIds==null || spnIds.size()==0){
			spnIds.add(spnId);
		}
		LookupSPNWorkflowState state = new LookupSPNWorkflowState(wfState);
		logger.info("state id>>"+state.getId()+"<<descr>>"+state.getDescription());
		try {
			getServiceProviderStateService().updateServiceProviderStatusOverride(spnIds, serviceProviderId, overrideComment, reason, state, modifiedBy, validityDate);
		} catch (Exception e) {
			e.printStackTrace();
			//FIXME need to add to logger
		}
		return "success_network_status";
	}

	private void initNetworks()
	{
		Integer buyerId = getCurrentBuyerId();
		Integer count = 0;
		Integer serviceProviderId = getServiceProviderId();
		List<SPNServiceProviderState> states = getServiceProviderStateService().findServiceProviderState(buyerId, serviceProviderId);
		//get only the original spn's for display Jira:SL-19025
		List<SPNServiceProviderState> providersListWithoutAlias = removeAliasSPNFromProvidersList(states);
		List<SPNProviderDetailsNetworkDTO> networkList = new ArrayList<SPNProviderDetailsNetworkDTO>();
		for(SPNServiceProviderState state:providersListWithoutAlias) {
			SPNProviderDetailsNetworkDTO network = mapToDto(state);
			networkList.add(network);
			// SL-12300 : Finding firm count to display in the popup when submitting override.
			if(!network.getHasAliasInd()){
				count++;
			}
		}
		getModel().setNetworks(networkList);
		getModel().setSpnCount(count);
		// Only want to display "Change Network Groups" section for Service Pro details.  Do not show for Provider Firm details.
		getRequest().setAttribute("showNetworkGroupSection", Boolean.TRUE);
	}

	/**
	 * get only the original spn's for display. SL 19025
	 * @param providerList
	 * @return providersListWithoutAlias
	 */
	private List<SPNServiceProviderState> removeAliasSPNFromProvidersList(List<SPNServiceProviderState> providerList ){
		List<SPNServiceProviderState> providersListWithoutAlias = new ArrayList<SPNServiceProviderState>();

		if(null != providerList && providerList.size()>0){
			for(SPNServiceProviderState provider: providerList){
				if(null != provider && null != provider.getServiceProviderStatePk()
						&& null != provider.getServiceProviderStatePk().getSpnHeader()){
					//if aliasOriginalSpnId is null, then it is not an alias.
					if(null == provider.getServiceProviderStatePk().getSpnHeader().getAliasOriginalSpnId()
							&& !provider.getServiceProviderStatePk().getSpnHeader().getIsAlias()){
						providersListWithoutAlias.add(provider);
					}
				}
			}
		}
		return providersListWithoutAlias;
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
			network.setSpnOverrideEffectiveDate(state.getStatusOverrideEffectiveDate());
		}else{
			network.setOverrideInd(false);
			network.setSpnOverrideEffectiveDate("");
		}
		// SL-12300 : As per the new requirement buyer cannot override Firm/Provider of a particular SPN
		// if that SPN is Edited. Hence that info. need to be fetched from spnet_hdr.
		// Re-using the isSPNEdited() method in ProviderFirmStateService.java
		Map<String, Object> map = getProviderFirmStateService().getSPNEditInfo(spnHeader.getSpnId());
		if(null != map && !map.isEmpty()){
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			network.setHasAliasInd((Boolean) map.get("isAliasInd"));
			network.setSpnEditEffectiveDate(formatter.format(map.get("effectiveDate")));
		} else{
			network.setHasAliasInd(false);
			network.setSpnEditEffectiveDate("");
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
