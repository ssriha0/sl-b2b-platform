package com.servicelive.spn.buyer.membermanagement;

import static com.servicelive.spn.common.SPNBackendConstants.ACTION_TYPE_PROVIDER_FIRM;
import static com.servicelive.spn.common.SPNBackendConstants.MODIFIED_BY_USER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_REMOVED_FIRM;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.spn.common.ActionMapperEnum;

/**
 * 
 * 
 * 
 */
@Validation
public class SPNProviderFirmDetailsNetworksAction extends SPNProviderDetailsBaseAction implements ModelDriven<SPNProviderDetailsNetworksModel> {

	private static final long serialVersionUID = -20100414L;
	private SPNProviderDetailsNetworksModel model = new SPNProviderDetailsNetworksModel();

	public String displayNetworkInfoPageAjax() {
		Integer vendorId = getInteger("vendorId");
		Integer vendorResourceId = getInteger("vendorResourceId");
		String firstName = getString("firstName");
		String lastName = getString("lastName");

		getRequest().setAttribute("vendorId", vendorId);
		getRequest().setAttribute("vendorResourceId", vendorResourceId);
		getRequest().setAttribute("firstName", firstName);
		getRequest().setAttribute("lastName", lastName);

		if(logger.isDebugEnabled()) {
			logger.debug("displayNetworkInfoPageAjax Parameters Here:");
			logger.debug("vendorId: " + vendorId);
			logger.debug("vendorResourceId: " + vendorResourceId);
			logger.debug("Name: " + firstName + " " + lastName);
		}

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
		Integer vendorId = getInteger("vendorId");
		// SL-12300 : If current and new reason status are same, the previous status will be fetched for an active entry from the
		// spnet_provider_network_override table for the given spnId and provider firm id.
		// If there is no such entry hardcoded values will be used for currentWfState.
		if(wfState.equals(currentWfState)){
			currentWfState = getLookupService().findLastFirmNetworkStatus(spnId, vendorId);
			if(null == currentWfState){
				if((WF_STATUS_PF_SPN_MEMBER).equals(wfState) || (WF_STATUS_PF_SPN_REMOVED_FIRM).equals(wfState)){
					currentWfState = WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;
				}else if((WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE).equals(wfState)){
					currentWfState = WF_STATUS_PF_SPN_MEMBER;
				}
			}
		}
		List<LookupSPNStatusOverrideReason> reasons = getLookupService().findByWfState(wfState, currentWfState);

		List<LabelValueBean> reasonOptions = new ArrayList<LabelValueBean>();
		LabelValueBean lvBean;
		for (LookupSPNStatusOverrideReason reason : reasons) {
			lvBean = new LabelValueBean(reason.getDescription(), String.valueOf(reason.getId()));
			reasonOptions.add(lvBean);
		}
		getRequest().setAttribute("reasonOptions", reasonOptions);
		return "success_reasons";
	}

	public String changeNetworkStatusAjax() {
		Integer providerFirmId = getProviderFirmIdFromParameter();
		Integer spnId = getInteger("networkId");
		String wfState = getString("wfState");
		// SL-12300 : Including validity date as per new requirement.
		// The new network status will be override the current until this validity date.
		if(wfState==""){
			wfState = getString("networkStatus").replace("%20", " ");
		}
		logger.info("wfState>>"+wfState);
		String applyAll = getString("applyAll");
		boolean applyAllNetworks = false;
		if("true".equals(applyAll)){
			applyAllNetworks = true;
		}
		Integer buyerId = getCurrentBuyerId();
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
		String modifiedBy = getCurrentBuyerResourceUserName();
		Integer reasonId = getInteger("reason");//TODO needs to be mapped.
		final LookupSPNStatusOverrideReason reason;
		if (reasonId != null) {
			reason = new LookupSPNStatusOverrideReason();
			reason.setId(reasonId);
		} else {
			reason = null;
		}

		String returnString = "success_network_status";
		getModel().setErrorMessage(null);		

		try {
			LookupSPNWorkflowState state = new LookupSPNWorkflowState(wfState);
			List<Integer> spnIds = new ArrayList<Integer>();
			if(applyAllNetworks){
				List<SPNProviderFirmState> firmStates = getProviderFirmStateService().findSearchableProviderFirmStatus(buyerId, providerFirmId);
				// SL-12300 : Removing alias from the list.
				List<SPNProviderFirmState> firmStateslistWithoutAlias = removeAliasSPNFromFirmList(firmStates);
				for (SPNProviderFirmState states : firmStateslistWithoutAlias) {
					SPNHeader spnHeader = states.getProviderFirmStatePk().getSpnHeader();
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
			Integer actionMapid = ActionMapperEnum.getActiomMapperEnum(MODIFIED_BY_USER, wfState, ACTION_TYPE_PROVIDER_FIRM).getValue();
			logger.info("actionMapid>>"+actionMapid);
			LookupSPNStatusActionMapper lookupSPNStatusActionMapper = new LookupSPNStatusActionMapper(actionMapid);
			getProviderFirmStateService().updateProviderFirmStatusOverride(providerFirmId, overrideComment, reason, state, modifiedBy, lookupSPNStatusActionMapper, spnIds, validityDate);
		} catch (Exception e) {
			logger.error("Exception Occured during Overrider Status Functionality for Provider Firm. Values are SPNID =["+spnId+"] And Provider Firm Id = ["+providerFirmId+"].",e);
			e.printStackTrace();
			getModel().setErrorMessage("We're sorry, a system error occurred. Please try again or contact your administrator.");
			returnString = "failure_network_status";
		}

		return returnString;
	}

	private void initNetworks() {
		Integer buyerId = getCurrentBuyerId();
		Integer count = 0;
		Integer providerFirmId = getProviderFirmIdFromParameter();
		List<SPNProviderFirmState> firmStates = getProviderFirmStateService().findSearchableProviderFirmStatus(buyerId, providerFirmId);
		//get only the original spn's for display Jira:SL-19025
		List<SPNProviderFirmState> firmStateslistWithoutAlias = removeAliasSPNFromFirmList(firmStates);
		List<SPNProviderDetailsNetworkDTO> networkList = new ArrayList<SPNProviderDetailsNetworkDTO>();
		for (SPNProviderFirmState state : firmStateslistWithoutAlias) {
			SPNProviderDetailsNetworkDTO network = mapToDto(state);
			networkList.add(network);
			// SL-12300 : Finding firm count to display in the popup when submitting override.
			if(!network.getHasAliasInd()){
				count++;
			}
		}
		getModel().setNetworks(networkList);
		getModel().setSpnCount(count);
	}


	/**
	 * get only the original spn's for display. SL 19025
	 * @param firmStates
	 * @return firmStateslistWithoutAlias
	 */
	private List<SPNProviderFirmState> removeAliasSPNFromFirmList(List<SPNProviderFirmState> firmStates){
		List<SPNProviderFirmState> firmStateslistWithoutAlias = new ArrayList<SPNProviderFirmState>();
		if(null != firmStates && firmStates.size()>0){
			for(SPNProviderFirmState providerFirmState: firmStates){
				if(null != providerFirmState && null != providerFirmState.getProviderFirmStatePk()
						&& null != providerFirmState.getProviderFirmStatePk().getSpnHeader()){
					//if aliasOriginalSpnId is null, then it is not an alias.
					if(null == providerFirmState.getProviderFirmStatePk().getSpnHeader().getAliasOriginalSpnId()
							&& !providerFirmState.getProviderFirmStatePk().getSpnHeader().getIsAlias()){
						firmStateslistWithoutAlias.add(providerFirmState);
					}
				}
			}
		}
		return firmStateslistWithoutAlias;
	}
	private SPNProviderDetailsNetworkDTO mapToDto(SPNProviderFirmState state) {
		SPNHeader spnHeader = state.getProviderFirmStatePk().getSpnHeader();
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
		//network.setNetworkGroup(Integer.valueOf(String.valueOf(state.getPerformanceLevel().getId())));
		network.setNetworkGroup(Integer.valueOf(String.valueOf(1))); // FIXME does this need to be fixed?
		network.setNetworkDescription((String) "GoldCEG"); // FIXME does this need to be fixed?
		return network;
	}

	public String getProviderCountAjax() {
		String networkIdStr = getRequest().getParameter("networkId");
		String apply = getRequest().getParameter("applyAll");
		Integer networkIdInt = null;
		boolean applyAllNetworks = false;
		if("true".equals(apply)){
			applyAllNetworks = true;
		}
		if (StringUtils.isNumeric(networkIdStr)) {
			networkIdInt = Integer.parseInt(networkIdStr);
		}

		Integer providerFirmId = getProviderFirmIdFromParameter();
		Integer providerCount =0;
		Integer buyerId = getCurrentBuyerId();
		try {
			if (networkIdInt != null && providerFirmId != null) {
				if(!applyAllNetworks){
					providerCount = getProviderFirmStateService().getCountsOfImpactedServiceProOnFirmStatusOverride(networkIdInt, providerFirmId);
				}else{
					providerCount = getProviderFirmStateService().getCountOfImpactedServiceProForFirmBuyer(buyerId, providerFirmId);
				}


			} else {
				providerCount = -1;
			}
		} catch (Exception e) {
			logger.error("getProviderCountAjax failed for networkId=" + networkIdInt + " providerFirmId=" + providerFirmId +": "+e);
			providerCount = -1;
		}

		getModel().setProviderCount(providerCount);

		return "success";
	}

	/**
	 * @return the model
	 */
	public SPNProviderDetailsNetworksModel getModel() {
		return model;
	}

}
