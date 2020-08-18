package com.servicelive.spn.buyer.auditor.newapplicants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.spn.core.SPNBaseAction;




	
	
	

/**
 * 
 * 
 *
 */
public class SPNAuditorCriteriaAndCredentialsTabAction extends SPNBaseAction
implements ModelDriven<SPNCriteriaAndCredentialsTabModel>
{
	private static final long serialVersionUID = 0L;
	private String resultUrl;

	private SPNCriteriaAndCredentialsTabModel model = new SPNCriteriaAndCredentialsTabModel();
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String viewTabAjax() throws Exception
	{
		
		initMockData();
		
		
		return SUCCESS;
	}
	
	public String getProviderRequirementsListAjax() {
		//String resultUrl1 = getRequest().getScheme() + "://"+ getRequest().getServerName() + ":" + getRequest().getServerPort() + "/MarketFrontend/spnMonitorAction_getProviderRequirementsList.action?&spnID=" + model.getNetworkId() + "&vendorId=" + model.getProviderFirmId();
		resultUrl = getRequest().getScheme() + "://"+ getRequest().getServerName() + ":" + getRequest().getServerPort() + "/MarketFrontend/spnMonitorAction_getProviderRequirementsList.action?&spnID=" + model.getNetworkId() + "&vendorId=" + model.getProviderFirmId();
		return SUCCESS;
	}
	
	public String getCompanyRequirementsListAjax(){
//		resultUrl = servletRequest.getScheme() + "://"+ servletRequest.getServerName() + ":" + servletRequest.getServerPort() + "/sso/secure/Login.action?targetApp=spn";
		resultUrl = getRequest().getScheme() + "://"+ getRequest().getServerName() + ":" + getRequest().getServerPort() + "/MarketFrontend/spnMonitorAction_getCompanyRequirementsList.action?&spnID=" + model.getNetworkId() + "&vendorId=" + model.getProviderFirmId();
		return SUCCESS;
	}

	private void initMockData()
	{
		model = getModel();
		
		List<SPNDocumentRowDTO> documents = new ArrayList<SPNDocumentRowDTO>();
		
		SPNDocumentRowDTO doc;

		
		
		for(int i=0 ; i<5 ; i++)
		{
			doc = new SPNDocumentRowDTO();
			doc.setStatus("Incomplete");
			doc.setStatusIcon("status-yellow.png");
			doc.setTitle("Doc title " + i);
			doc.setNumPages(Integer.valueOf(i));
			doc.setLastAuditDate(new Date());
			doc.setLastAuditorID("" + (100 + i));
			doc.setLastAuditorName("John Jonez");
			doc.setComments("commment " + i);
			doc.setAction("Pending Approval");
			
			doc.setAction("foo action");
			documents.add(doc);
		}
		model.setDocuments(documents);		
	}
	
	
	public SPNCriteriaAndCredentialsTabModel getModel()
	{
		return model;
	}

	public String getResultUrl() {
		return resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}
	
		
}
