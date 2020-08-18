package com.newco.marketplace.web.action.financemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.dto.FMControllerDTO;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageAccountsTabDTO;
import com.newco.marketplace.web.dto.FMTransferFundsDTO;
import com.newco.marketplace.web.dto.IMessage;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FMControllerAction extends SLFinanceManagerBaseAction implements Preparable, ModelDriven<FMControllerDTO> {
	private static final long serialVersionUID = 1L;
	private FMControllerDTO fmControllerModel = new FMControllerDTO();
	
	TermsAndConditionsVO termsVO = new TermsAndConditionsVO();
	TermsAndConditionsVO termsLicenseVO = new TermsAndConditionsVO();
	
	private String leadGenFeesLink;
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		getBuyerBitFlag();
		initPageHeader();
		populateLookup();
		this.setLegalHoldPermissionAttribute();
		this.getEntityWalletControlId();
		this.getEntityWalletControlBanner();
		getCreditCardTokenUrl();
		getCreditCardTokenAPICrndl();
		getCreditCardAuthTokenizeUrl();
		getCreditCardAuthTokenizeXapiKey();
	}


	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT

	public String execute() throws Exception {
		String role = get_commonCriteria().getRoleType();

		//SL-21117: Revenue Pull code change starts

		String user = get_commonCriteria().getSecurityContext().getSlAdminUName();

		//Code change ends
			   
		String resultPage = getResultPage();

		tabs();
		String gotoTab = getParameter("defaultTab");
		if(gotoTab!=null && gotoTab.equals(OrderConstants.FM_HISTORY ))
		{
			PagingCriteria PagingCriteria= handleFMHistoryPagingCriteria();
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			sessionMap.put(FM_HISTORY_PAGING_CRITERIA_KEY, PagingCriteria);
		}
		if (gotoTab == null || !gotoTab.equals(OrderConstants.FM_HISTORY )){
			getRequest().getSession().removeAttribute("fmOverHistDTO");
			getRequest().getSession().removeAttribute(FM_HISTORY_PAGING_CRITERIA_KEY);
		}

		//SL-21117: Revenue Pull code change starts

		_setTabs(role,gotoTab,user);

		//Code change ends

		//SL-19293 Fetching the link for the lead gen. fees pdf. The page will be redirected to this pdf link when user clicks on 'Lead Gen. Fees' 
		//in ServiceLive Wallet. 
		if(OrderConstants.PROVIDER_ROLEID == get_commonCriteria().getRoleId()){
			Integer leadsTCIndicator = (Integer) getRequest().getSession().getAttribute("newLeadsTCIndicator");
			if(null != leadsTCIndicator){
				getLeadGenFeesLink(get_commonCriteria().getCompanyId());
			}
		}
		return resultPage;		
	}

	//SL-19293 Method to fetch the link for the lead gen. fees pdf.
	private void getLeadGenFeesLink(Integer vendorId) {
		try {
			leadGenFeesLink = lookupdelegate.getLeadGenFeesLink(vendorId);
			getRequest().getSession().setAttribute("leadGenFeesLink",leadGenFeesLink);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public PagingCriteria handleFMHistoryPagingCriteria() {
		PagingCriteria pagingCriteria = getPagingCriteria();

		if (pagingCriteria != null) {
			if (getRequest().getParameter("startIndex") != null) {
				if (!getRequest().getParameter("startIndex").equals("")) {
					String l = getRequest().getParameter("startIndex");
					pagingCriteria.setStartIndex(new Integer(getRequest().getParameter("startIndex")).intValue());
				}
			}
			if (getRequest().getParameter("endIndex") != null) {
				if (!getRequest().getParameter("endIndex").equals("")) {
					String k = getRequest().getParameter("endIndex");
					pagingCriteria.setEndIndex(new Integer(getRequest().getParameter("endIndex")).intValue());
				}
			}
			if (getRequest().getParameter("pageSize") != null) {
				if (!getRequest().getParameter("pageSize").equals("")) {
					String k = getRequest().getParameter("pageSize");
					pagingCriteria.setPageSize(new Integer(getRequest().getParameter("pageSize")).intValue());
				}
			}
		}
		else
			return  new PagingCriteria();

		return pagingCriteria;
	}

	public PagingCriteria getPagingCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(FM_HISTORY_PAGING_CRITERIA_KEY))
		{
			return (PagingCriteria)theMap.get(FM_HISTORY_PAGING_CRITERIA_KEY);
		}
		return null;
	}



	public String displayBucksAgreement(){
		try {
			termsVO = lookupdelegate.getTermsConditionsContent(SOConstants.ACCEPT_TERMS_AND_COND_BUX_FOR_PROVIDER);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bucksContent = termsVO.getTermsCondContent();
		getRequest().getSession().setAttribute("bucksContent",bucksContent);
		return "bucks";
	}

	public String displayLicenses(){
		try {
			termsLicenseVO = lookupdelegate.getTermsConditionsContent(SOConstants.ACCEPT_TERMS_AND_COND_STATE_LICENSES);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String licenseContent = termsLicenseVO.getTermsCondContent();
		getRequest().getSession().setAttribute("licenseContent",licenseContent);
		return "license";
	}

	private String getResultPage()
	{
		boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();
		String role = get_commonCriteria().getRoleType();

		//SL-21117: Revenue Pull code change starts

		String user = get_commonCriteria().getSecurityContext().getSlAdminUName();

		//Code change ends

		ArrayList<LookupVO> creditCardLists = null;
		if (isSLAdmin)
		{
			//The roleId must be buyer or provider
			Integer roleId = get_commonCriteria().getSecurityContext().getRoleId();

			if(roleId.equals(OrderConstants.BUYER_ROLEID))
			{
				role = "Buyer";
			}
			else if(roleId.equals(OrderConstants.PROVIDER_ROLEID))
			{
				role = "Provider";
			}
			else if(roleId.equals(OrderConstants.SIMPLE_BUYER_ROLEID)){

				role = "SimpleBuyer";
			}
			else
			{
				role = "NewCo";

			}
         
			initAdminWidgets(); // initialize the widgets needed for the admin
			reasonCodeNotesValue();
			entityWalletHoldBalance();
			
		}
		try {
			creditCardLists = financeManagerDelegate.getCreditCardTypeList();
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSession().setAttribute("creditCardTypeMap", creditCardLists);
		tabs();
		//TODO
		String gotoTab = getParameter("defaultTab");
		if (gotoTab == null || !gotoTab.equals(OrderConstants.FM_HISTORY )){
			getRequest().getSession().removeAttribute("fmOverHistDTO");
		}
		//getSession().setAttribute("tab", getParameter("tab"));


		//SL-21117: Revenue Pull code change starts

		_setTabs(role,gotoTab,user);

		//Code change ends

		return role;
	}
	
	private void initAdminWidgets() {
		List<LookupVO> reasonCodes = null;
		List<FMManageAccountsTabDTO> fmManageAccountsList = null;
		try {
			reasonCodes = new CopyOnWriteArrayList(financeManagerDelegate.getTransferSLBucksReasonCodes());
			if (!(boolean) getSession().getAttribute("legalHoldPermission")) {
				for (LookupVO SLBucksReasonCode : reasonCodes) {
					if ((SLBucksReasonCode.getId() == LedgerConstants.TRANSFER_REASONCODE_IRS_LEVY)
							|| (SLBucksReasonCode.getId() == LedgerConstants.TRANSFER_REASONCODE_LEGAL_HOLD)) {
						reasonCodes.remove(SLBucksReasonCode);
					}
				}
			}
			fmManageAccountsList = financeManagerDelegate.getRefundAccountDetails(get_commonCriteria().getCompanyId());
		} catch (Exception e) {

		}

		getSession().setAttribute("reasonCodes", reasonCodes);
		getSession().setAttribute("fmManageAccountsList", fmManageAccountsList);
	}
	
	public void reasonCodeNotesValue() {
		String stateRegulationNote = null;
		Integer stateRegulationReasonCodeId = OrderConstants.State_Regulations_Reason_Code;
		stateRegulationNote = financeManagerDelegate.getStateRegulationNote(stateRegulationReasonCodeId);
		getSession().setAttribute("StateRegulationNote", stateRegulationNote);

		if ((boolean) getSession().getAttribute("legalHoldPermission")) {
			String IRSlevyNote = null;
			String legalHoldNote = null;

			Integer IRSlevyReasonCodeId = OrderConstants.IRS_Levy_Reason_Code;
			Integer legalHoldReasonCodeId = OrderConstants.Legal_Hold_Reason_Code;

			IRSlevyNote = financeManagerDelegate.getIRSlevyNote(IRSlevyReasonCodeId);
			legalHoldNote = financeManagerDelegate.getLegalHoldNote(legalHoldReasonCodeId);

			getSession().setAttribute("IRSLevyNote", IRSlevyNote);
			getSession().setAttribute("LegalHoldNote", legalHoldNote);
		}

	}
	
	public void entityWalletHoldBalance() {
		
		if ((boolean) getSession().getAttribute("legalHoldPermission")) {
			double walletHoldBalance = 0.0;
			double permissibleHoldBalance = 0.0;
			
			Integer companyId = get_commonCriteria().getCompanyId();
			
			String roleType = this._commonCriteria.getRoleType();
			
			AjaxCacheVO ajax = new AjaxCacheVO();

			ajax.setCompanyId(companyId);
			if(roleType.equals("SimpleBuyer"))
				ajax.setRoleType(OrderConstants.BUYER_ROLE);
			else
				ajax.setRoleType(roleType.toUpperCase());

			double availBalance = financeManagerDelegate.getAvailableBalance(ajax);

			walletHoldBalance = financeManagerDelegate.getWalletHoldBalance(companyId);
			permissibleHoldBalance=availBalance >= walletHoldBalance?walletHoldBalance:availBalance;
			
			String abFormat = java.text.NumberFormat.getCurrencyInstance().format(permissibleHoldBalance);
			setAttribute("PermissibleHoldBalance", abFormat);
		}
	}

	private void initPageHeader() {
		String dateString = DateUtils.getHeaderDate();
		setAttribute("dateString", dateString);
	}

	private void tabs() {
		HashMap<String, String> map = new HashMap<String, String>();
		boolean buyerFlag = false;
		if(get_commonCriteria().getRoleType()!= null && 
				(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.BUYER) || 
						get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)		
						)) { buyerFlag = true; }
		try {
			// retriving FM FinancialProfile Tab DTO from database
			FMFinancialProfileTabDTO fmFinacialProfileDTO = new FMFinancialProfileTabDTO();
			fmFinacialProfileDTO = financeManagerDelegate
					.getVendorDetails(get_commonCriteria().getCompanyId()
							.toString(), buyerFlag);
			fmFinacialProfileDTO.validate();
			// retriving FM Manage Accounts Tab DTO from database
			FMManageAccountsTabDTO manageAccountsTabDTO = financeManagerDelegate
					.getAccountDetails(get_commonCriteria().getCompanyId());
			FMManageAccountsTabDTO manageAccountsTabDTOCC = financeManagerDelegate.getActiveCreditCardDetails(get_commonCriteria().getCompanyId());

			if(manageAccountsTabDTO != null && manageAccountsTabDTOCC != null){
				manageAccountsTabDTO.setCardHolderName(manageAccountsTabDTOCC.getCardHolderName());
				manageAccountsTabDTO.setCardTypeId(manageAccountsTabDTOCC.getCardTypeId());
				manageAccountsTabDTO.setCardNumber(manageAccountsTabDTOCC.getCardNumber());
				manageAccountsTabDTO.setExpirationMonth(manageAccountsTabDTOCC.getExpirationMonth());
				manageAccountsTabDTO.setExpirationYear(manageAccountsTabDTOCC.getExpirationYear());
				manageAccountsTabDTO.setBillingAddress1(manageAccountsTabDTOCC.getBillingAddress1());
				manageAccountsTabDTO.setBillingCity(manageAccountsTabDTOCC.getBillingCity());
				manageAccountsTabDTO.setBillingZip(manageAccountsTabDTOCC.getBillingZip());
			}


			boolean bankAccountInfoEmpty = manageAccountsTabDTO.isBankAccountInfoEmpty();
			boolean creditInfoEmpty = true;
			if (manageAccountsTabDTOCC  != null ){
				creditInfoEmpty = manageAccountsTabDTOCC.isCreditCardInfoEmpty();
			}
			if((bankAccountInfoEmpty && creditInfoEmpty) || (!bankAccountInfoEmpty && !creditInfoEmpty)){
				manageAccountsTabDTO.validate();
				//manageAccountsTabDTO.creditCardValidate();
			}else if(!bankAccountInfoEmpty){
				manageAccountsTabDTO.validate();
			}else if(!creditInfoEmpty){
				//manageAccountsTabDTO.creditCardValidate();
			}



			// Generating the HashMap for tab icons
			map = setTabIcon(map, OrderConstants.FM_FINANCIAL_PROFILE_TAB,
					fmFinacialProfileDTO.getErrors(), fmFinacialProfileDTO
					.getWarnings());
			map = setTabIcon(map, OrderConstants.FM_MANAGE_ACCOUNTS_TAB,
					manageAccountsTabDTO.getErrors(), manageAccountsTabDTO
					.getWarnings());
			map = setTabIcon(map, OrderConstants.FM_MANAGE_ACCOUNTS_TAB,
					manageAccountsTabDTO.getErrors(), manageAccountsTabDTO
					.getWarnings());

		} catch (Exception e) {
			e.printStackTrace();
		}
		getSession().setAttribute("hasMapForTabIcons", map);

	}

	private void populateLookup(){

		if(getSession().getServletContext().getAttribute(Constants.CALENDAR_LIST) == null){

			TreeMap<String,Object> calendarList = new TreeMap<String, Object>();
			calendarList.put("0-0", "Please Select");
			calendarList.put("1-1DAY", "1 Day");
			calendarList.put("2-1WEEK", "1 Week");
			calendarList.put("3-2WEEK", "2 Weeks");
			calendarList.put("4-1MONTH", "1 Month");
			calendarList.put("5-6MONTH", "6 Months");
			calendarList.put("6-1YEAR", "1 Year");

			getSession().getServletContext().setAttribute(Constants.CALENDAR_LIST, calendarList);
		}
	}


	public String buttonWidgetTransferSLFunds() throws Exception
	{
		FMTransferFundsDTO data = null;
		ProcessResponse processResponse = new ProcessResponse();
		FMControllerDTO controllerModel = getModel();
		data = controllerModel.getTransferFundsDTO();
		Integer walletControlId = 0;
		try {
			walletControlId = (Integer) getSession().getAttribute("EntityWalletControlId");
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		SecurityContext secContext = get_commonCriteria().getSecurityContext();
		data.setFromAccount(secContext.getCompanyId());
		data.setToAccount(secContext.getCompanyId());
		data.setRoleType(secContext.getRole());
		data.setUser(secContext.getUsername());
		data.setAdminUser(secContext.getSlAdminUName());
		data.setWalletControlId(walletControlId);
		data.setEntityId(secContext.getCompanyId());
			
		List<IMessage> messages = data.validate();

		if(messages != null && messages.size() > 0) {
			setAttribute("tslbErrors", getMessageString(messages));
		}
		else {
			processResponse = financeManagerDelegate.transferSLBucks(data);
						
			if(processResponse.getMessages() != null && processResponse.getMessages().size() > 0) {

				setAttribute("tslbErrors",processResponse.getMessages().get(0));
			}
			else {

				setAttribute("tslbSuccess","Successfully transfered SL Bucks!");
				//need to clean up the form data...
				data.setReasonCode(-1);
				data.setAmount(null);
				data.setNote("");
			}

		}

		return getResultPage(); 	
	}

	private String getMessageString(List<IMessage> messages) {
		String returnString="";

		Iterator<IMessage> iterator = messages.iterator();
		while(iterator.hasNext()){
			returnString = returnString + iterator.next().getMsg() +"<br>";
		}

		return returnString;
	}

	public FMControllerDTO getModel()
	{
		return fmControllerModel;
	}


	public FMControllerDTO getFmControllerModel() {
		return fmControllerModel;
	}


	public void setFmControllerModel(FMControllerDTO fmControllerModel) {
		this.fmControllerModel = fmControllerModel;
	}

	public String getLeadGenFeesLink() {
		return leadGenFeesLink;
	}


	public void setLeadGenFeesLink(String leadGenFeesLink) {
		this.leadGenFeesLink = leadGenFeesLink;
	}
	
	public String setToken() {
		
		//ServletActionContext.getContext().getApplication().put("authToken", fmControllerModel.getTransferFundsDTO().getAuthToken());
		//ServletActionContext.getContext().getApplication().put("tokenLife", fmControllerModel.getTransferFundsDTO().getTokenLife());
		
		// Enable above code once we set the token in application scope
		getSession().setAttribute("authToken", fmControllerModel.getTransferFundsDTO().getAuthToken());
		getSession().setAttribute("tokenLife", fmControllerModel.getTransferFundsDTO().getTokenLife());
		return "tokenResponse";
	}
}
