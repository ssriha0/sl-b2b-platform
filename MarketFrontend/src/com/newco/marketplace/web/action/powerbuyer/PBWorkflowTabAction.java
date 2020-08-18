package com.newco.marketplace.web.action.powerbuyer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBBuyerFilterSummaryVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.delegates.AdminBuyerSearchDelegate;
import com.newco.marketplace.web.delegates.IPowerBuyerDelegate;
import com.newco.marketplace.web.dto.AdminSearchFormDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsDTO;
import com.newco.marketplace.web.dto.PBBuyerFilterSummariesDTO;
import com.newco.marketplace.web.dto.PBWorkflowTabDTO;
import com.newco.marketplace.web.dto.SLTabDTO;
import com.newco.marketplace.web.dto.ProviderWidgetResultsDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 01:13:53 $
 */

/*
 * Maintenance History: See bottom of file
 */

public class PBWorkflowTabAction extends PBBaseAction implements Preparable,
		ModelDriven<PBWorkflowTabDTO> {
	
	private static final long serialVersionUID = 1L;


	private IPowerBuyerDelegate pbDelegate;
	private AdminBuyerSearchDelegate buyerSearchDelegate;
	private IBuyerBO buyerBO;
	
	//SL-19820
	String pbwfMessage;
	String gotoWFM;
	
	public String getGotoWFM() {
		return gotoWFM;
	}

	public void setGotoWFM(String gotoWFM) {
		this.gotoWFM = gotoWFM;
	}

	public String getPbwfMessage() {
		return pbwfMessage;
	}

	public void setPbwfMessage(String pbwfMessage) {
		this.pbwfMessage = pbwfMessage;
	}
	
	PBWorkflowTabDTO workflowTabDTO = new PBWorkflowTabDTO();
	private AdminSearchFormDTO buyerSearchDTO = new AdminSearchFormDTO();
	

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		
		PBBuyerFilterSummariesDTO summary;
		List<PBBuyerFilterSummariesDTO> list = new ArrayList<PBBuyerFilterSummariesDTO>();
		setAttribute("isSlAdmin","false");
		setAttribute("isAdopted","false");		
		//Reset admin credentials if they have been masquerading as another buyer.
		if (get_commonCriteria().getSecurityContext().isSlAdminInd() && !get_commonCriteria().getSecurityContext().isAdopted()) {
//			if (get_commonCriteria().getSecurityContext().getRoleId() != -1 && get_commonCriteria().getSecurityContext().getRoleId() != OrderConstants.NEWCO_ADMIN_ROLEID){
//				get_commonCriteria().setVendBuyerResId(get_commonCriteria().getSecurityContext().getAdminResId());
//				get_commonCriteria().getSecurityContext().setVendBuyerResId(get_commonCriteria().getSecurityContext().getAdminResId());
//			}
			get_commonCriteria().setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
			get_commonCriteria().getSecurityContext().setRoleId(2);
			get_commonCriteria().setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
			get_commonCriteria().getSecurityContext().setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
		}

		//params for filtering
		Integer buyerRefTypeId = (Integer) getSession().getAttribute("buyerRefTypeId");
		String buyerRefValue = (String) getSession().getAttribute("buyerRefValue");
		String searchByBuyerId = (String) getSession().getAttribute("searchByBuyerId");
		String searchBuyerId = null;

		if(buyerRefTypeId!=null && buyerRefTypeId == 2 && searchByBuyerId!=null && searchByBuyerId.equals("true")){
			searchBuyerId = (String) getSession().getAttribute("buyerRefValue");
			buyerRefTypeId = null;
			buyerRefValue =null;
		}

		List<PBBuyerFilterSummaryVO> summaries = pbDelegate.getBuyerFilterSummaryCounts(get_commonCriteria().getCompanyId(), get_commonCriteria().getSecurityContext().isSlAdminInd(), buyerRefTypeId, buyerRefValue, searchBuyerId);

		for (PBBuyerFilterSummaryVO vo : summaries) {
			summary = new PBBuyerFilterSummariesDTO();
			
			summary.setFilterId(vo.getFilterId());
			summary.setFilterName(vo.getFilterName());
			summary.setCount1(vo.getCount1());
			summary.setCount2(vo.getCount2());
			summary.setCount3(vo.getCount3());
			summary.setExcBuyerList(StringUtils.join(vo.getExcBuyerList(), ","));
			list.add(summary);
		}
		setAttribute("filters", list);
		if (get_commonCriteria().getSecurityContext().isSlAdminInd()){
			setAttribute("isSlAdmin","true");
		}
		if (get_commonCriteria().getSecurityContext().isAdopted()){
			setAttribute("isAdopted","true");
		}
		List<BuyerReferenceVO> refFields;
		if (get_commonCriteria().getCompanyId().equals(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION)) {
			refFields = buyerBO.getAllBuyerReferences();
		} else {
			refFields = buyerBO.getBuyerReferences(get_commonCriteria().getCompanyId());
		}
		String bRefVal = (String) getSession().getAttribute("buyerRefValue");
		if(bRefVal!=null && bRefVal.equals("hash")|| bRefVal!=null && bRefVal.equals("000hash")){
			buyerRefValue="#";
			if(searchByBuyerId!=null && !searchByBuyerId.equals("true")){
				buyerRefTypeId = (Integer) getSession().getAttribute("buyerRefTypeId");
			}
		}	
		workflowTabDTO.setRefFields(refFields);
		
		workflowTabDTO.setBuyerRefTypeId(buyerRefTypeId);
		workflowTabDTO.setBuyerRefValue(buyerRefValue);

		return "success";
	}
	
	public String returnToWFMFromAjaxCall() throws Exception {
		execute();
		List<SLTabDTO> tabList = new ArrayList<SLTabDTO>();
		SLTabDTO tab;
		String icon=null;
		String TAB_WORKFLOW = "Workflow";
		String TAB_CLAIMED = "Current Claimed";
		String TAB_SEARCH = "Search";
		
		tab = new SLTabDTO("", "pbWorkflowTab_execute.action", TAB_WORKFLOW, icon, "true", null);		
		tabList.add(tab);
		tab = new SLTabDTO("tab2", "pbClaimedTab_execute.action", TAB_CLAIMED, icon, "false", null);		
		tabList.add(tab);
		tab = new SLTabDTO("Search","tab2", "/MarketFrontend/monitor/gridHolder.action?tab=Search&wfFlag=1", TAB_SEARCH, icon, "false", null);		
		tabList.add(tab);
		setAttribute("tabList", tabList);		
		return "successWFM";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String claimNext() throws Exception {
		
		PBWorkflowTabDTO dto = getModel();
		String searchBuyerId = null;
		Integer buyerRefTypeId;
		String buyerRefValue;
		try {
			buyerRefTypeId = Integer.valueOf(getRequest().getParameter("refType"));
			String searchByBuyerId = (String) getSession().getAttribute("searchByBuyerId");
			if(buyerRefTypeId!=null && buyerRefTypeId == 2 && searchByBuyerId!=null && searchByBuyerId.equals("true")){
				searchBuyerId = (String) getSession().getAttribute("buyerRefValue");
				buyerRefTypeId = null;
				buyerRefValue =null;
			}	
			else{
			buyerRefValue = getRequest().getParameter("refVal");
			}
		} catch (NumberFormatException e) {
			buyerRefTypeId = null;
			buyerRefValue = null;
		}
		ClaimVO claimedVO = pbDelegate.claimNextSO(dto.getFilterId(), get_commonCriteria().getCompanyId(), get_commonCriteria().getVendBuyerResId(), buyerRefTypeId, buyerRefValue, searchBuyerId);
		getSession().removeAttribute("GID");
		String dest = null;
		if (null != claimedVO  && StringUtils.isNotEmpty(claimedVO.getSoId())){
			claimedVO.setQueueId(dto.getFilterId());
		    if (LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION == get_commonCriteria().getCompanyId()){
		        pbSoId = claimedVO.getSoId();
		    }
	/*	SL-19820
	 * 	getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR,"true");*/
			getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+pbSoId,"true");
			dest = determineDest(claimedVO);
			AdminSearchResultsDTO buyer = buyerSearchDelegate.getSearchResultsForAdminWidget(pbSoId, claimedVO.getBuyerId());
					
			if (buyer != null) {

				Map <String, Object> sessionMap = ActionContext.getContext().getSession();
				
				boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();
				if (isSLAdmin) {
					SecurityContext securityContext = get_commonCriteria().getSecurityContext();
					securityContext.setAdminRoleId(securityContext.getRoleId());
					// add the admin resource Id to the security context when it
					// needs to be return to the Admin WorkFlow tabs
					securityContext.setAdminResId(securityContext.getVendBuyerResId());
					securityContext.setSlAdminUName(securityContext.getUsername());
				}
				//SL-19820 : serviceOrderDTO will be always be null
				//ServiceOrderDTO serviceOrderDTO =(ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
				boolean isBuyer=get_commonCriteria().getSecurityContext().isBuyer();
				ProviderWidgetResultsDTO widget = buyerSearchDelegate.getInfoForProviderWidget(claimedVO.getSoId(),isBuyer,claimedVO.getBuyerId());
				if(widget!=null) {
					widget.setSoId(soId);
					//SL-19820
					/*if(serviceOrderDTO!=null && serviceOrderDTO.getProviderContact()!=null && serviceOrderDTO.getProviderContact().getCompanyID()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getProviderContact().getCompanyID()))
						widget.setProviderFirmId(serviceOrderDTO.getProviderContact().getCompanyID());
					if(serviceOrderDTO!=null && serviceOrderDTO.getProviderContact()!=null && serviceOrderDTO.getProviderContact().getCompanyName()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getProviderContact().getCompanyName()))
						widget.setProviderFirmName(serviceOrderDTO.getProviderContact().getCompanyName());
					if(serviceOrderDTO!=null && serviceOrderDTO.getAcceptedResourceId()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getAcceptedResourceId().toString()))
						widget.setProviderID(serviceOrderDTO.getAcceptedResourceId().toString());
					if(serviceOrderDTO!=null && serviceOrderDTO.getAcceptedResource()!=null && serviceOrderDTO.getAcceptedResource().getResourceContact()!=null)
					{
						Contact c = serviceOrderDTO.getAcceptedResource().getResourceContact();
						String providerName = "";
						if(!"".equals(c.getFirstName()))
							providerName = c.getFirstName();
						if(!"".equals(c.getLastName()))
							providerName = providerName +" "+c.getLastName();
						widget.setProviderName(providerName);
					}*/
					//getSession().setAttribute("widgetProResultsDTOObj",widget);
					getSession().setAttribute("widgetProResultsDTOObj_"+soId,widget);
				}
				//SL-19820 : Since these are buyer specific values, no need to remove from session
				sessionMap.put("buyerId", buyer.getId());
				sessionMap.put("buyerUsername", buyer.getUserName());
				sessionMap.put("vendorId", null);
				sessionMap.put("buyerBusinessName", buyer.getName());
				sessionMap.put("buyerAdmin",buyer.getBuyerName());
				sessionMap.put("buyerLocation", buyer.getCity() + "," + buyer.getState());
				
				//SL-19820 : TODO this is needed while adding notes
				/*if (claimedVO.getParentGroupId() != null) {
					sessionMap.put("GID", claimedVO.getParentGroupId());
					
				}*/
			}				
			
		} else{
			dest = ROUTETOWFMONITOR;
			//getSession().setAttribute(Constants.SESSION.PB_WF_MESSAGE, "pb.workflow.msg.no.so.avail");
			//getSession().setAttribute(Constants.SESSION.GOTO_WORKFLOW_TAB, "true");
			pbwfMessage = "pb.workflow.msg.no.so.avail";
			gotoWFM = "true";
		}
			
		return dest;
	}
	
		
	public PBWorkflowTabDTO getModel() {
		return workflowTabDTO;
	}

	public IPowerBuyerDelegate getPbDelegate() {
		return pbDelegate;
	}

	public void setPbDelegate(IPowerBuyerDelegate pbDelegate) {
		this.pbDelegate = pbDelegate;
	}

	public AdminBuyerSearchDelegate getBuyerSearchDelegate() {
		return buyerSearchDelegate;
	}

	public void setBuyerSearchDelegate(AdminBuyerSearchDelegate buyerSearchDelegate) {
		this.buyerSearchDelegate = buyerSearchDelegate;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

}
/*
 * Maintenance History
 * $Log: PBWorkflowTabAction.java,v $
 * Revision 1.12  2008/04/26 01:13:53  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.10.1  2008/04/23 11:41:47  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:19:33  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2008/02/19 22:35:01  glacy
 * powerbuyer claim fixes for SOW
 *
 * Revision 1.9  2008/01/24 21:53:36  mhaye05
 * changed to now extend PBBaseAction
 *
 * Revision 1.8  2008/01/24 02:13:05  mhaye05
 * needed to set another session attribute that would allow us to redirect to SOW
 *
 * Revision 1.7  2008/01/21 23:02:41  mhaye05
 * changed hard coded text to use constants
 *
 * Revision 1.6  2008/01/21 21:23:58  mhaye05
 * logic added to show message to user on the work flow tab in the event that there are no more service orders available for the filter they selected to get the next service order from
 *
 * Revision 1.5  2008/01/18 22:39:29  mhaye05
 * updated to make sure we use the buyer id when claiming the next so
 *
 * Revision 1.4  2008/01/18 01:59:10  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.3  2008/01/17 21:23:56  cgarc03
 * Renamed PBWorkfolwTabDTO to PBWorkflowDTO.
 *
 * Revision 1.2  2008/01/16 21:59:41  mhaye05
 * updated for workflow monitor tab
 *
 */
