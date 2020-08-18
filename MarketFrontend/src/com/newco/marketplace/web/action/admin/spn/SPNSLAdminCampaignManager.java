package com.newco.marketplace.web.action.admin.spn;


import org.apache.log4j.Logger;

import com.newco.marketplace.web.action.spn.SPNBaseAction;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.dto.AdminSPNManagedCampaignDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.AjaxHelperUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SPNSLAdminCampaignManager extends SPNBaseAction
						implements Preparable, ModelDriven<AdminSPNManagedCampaignDTO>{

	private static final Logger logger = Logger.getLogger("SPNSLAdminCampaignManager");
	private AdminSPNManagedCampaignDTO adminSPNDTO = new AdminSPNManagedCampaignDTO();
	
	
	public SPNSLAdminCampaignManager( ISPNBuyerDelegate buyerSpnDelegate){
		this.buyerSpnDelegate = buyerSpnDelegate;
	}
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		loadMarkets();
		loadAllActiveSPNetwork();
	}
	
	public String displayPage() throws Exception {
		return SUCCESS;
	}
	
	public String createNewSPNCampaign() throws Exception {
		AdminSPNManagedCampaignDTO campaignModel = getModel();
		if(campaignModel.getSelectedSPN() != -1 &&
		   campaignModel.getNewCampaign().getCampaignStateDate() != null &&
		   campaignModel.getNewCampaign().getCampaignEndDate() != null &&
		   campaignModel.getNewCampaign().getCampaignStateDate().before(campaignModel.getNewCampaign().getCampaignEndDate()))
		{
			buyerSpnDelegate.createNewSPNCampaign(campaignModel.getNewCampaign());
		}
		else
		{
			_configureForAjaxResponse();
			AjaxResultsDTO actionResponse = new AjaxResultsDTO();
			actionResponse.setActionState(1);
			if(campaignModel.getSelectedSPN() == -1){
				actionResponse.setResultMessage("Please select a SPN before added campaign dates");
			}
			else
			{
				actionResponse.setResultMessage("Please verify campaign start and end dates");
			}
			getResponse().getWriter().write(actionResponse.toXml());
			return NONE;
		}
		return loadCampaigns();
	}
	
	public String deleteSPNCampaign() throws Exception {
		AdminSPNManagedCampaignDTO campaignModel = getModel();
		buyerSpnDelegate.deleteSPNCampaign(campaignModel.getSelectedCampaignId());
		return NONE;
	}
	
	public String loadCampaigns() throws Exception {
		_configureForAjaxResponse();
		AdminSPNManagedCampaignDTO campaignModel = getModel();
		AjaxResultsDTO ajaxResults =
				AjaxHelperUtil.serializeSPNCampaign(
									buyerSpnDelegate.fetchActiveCampaigns(campaignModel.getSelectedSPN()),
									true);
		getResponse().getWriter().write(ajaxResults.toXmlDeepCopy());
		return NONE;
	}
	
	public AdminSPNManagedCampaignDTO getModel() {
		return adminSPNDTO;
	}
	
	public void setModel(AdminSPNManagedCampaignDTO adminSPNDTO) {
		this.adminSPNDTO = adminSPNDTO;
	}

}
