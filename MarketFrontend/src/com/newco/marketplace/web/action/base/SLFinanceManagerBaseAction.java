package com.newco.marketplace.web.action.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants.AppPropConstants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.PIIThresholdVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.delegates.IFMPersistDelegate;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SLTabDTO;


public abstract class SLFinanceManagerBaseAction extends SLBaseAction {
	protected IFMPersistDelegate fmPersistDelegate;
	protected IFinanceManagerDelegate financeManagerDelegate;
	protected ILookupDelegate lookupdelegate;
	protected FMFinancialProfileTabDTO financialProfileTabDTO = new FMFinancialProfileTabDTO();	
	protected ArrayList <LookupVO> percentageLists;
	protected String maxWalletHistoryExportLimit;

	private String defaultTab;

	
	public String getDefaultTab() {
		return defaultTab;
	}
	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public void initFinancialProflie()throws Exception{
		if (null == (FMFinancialProfileTabDTO) getRequest().getSession().getAttribute("FinancialProfileTabDTO")) {
			
			SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute("SecurityContext");
			String role = securityContext.getRole();
			boolean buyerFlag=false;
			if (role != null && (role.equalsIgnoreCase(OrderConstants.BUYER) || role.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)))
				buyerFlag = true; 
			financialProfileTabDTO = financeManagerDelegate.getVendorDetails(get_commonCriteria().getCompanyId().toString(), buyerFlag);
			getSession().setAttribute("providerMaxWithdrawalLimit", financialProfileTabDTO.getProviderMaxWithdrawalLimit());
			getSession().setAttribute("providerMaxWithdrawalNo", financialProfileTabDTO.getProviderMaxWithdrawalNo());
			getRequest().getSession().setAttribute("FinancialProfileTabDTO", financialProfileTabDTO);
		}

		percentageLists = financeManagerDelegate.getPercentOwnedList();
		getSession().setAttribute("percentageMap", percentageLists);
	}
	
	public void setLegalHoldPermissionAttribute() {
		boolean legalHoldPermission = false;
		Integer adminUserPermission = null;
		try {
			adminUserPermission = lookupdelegate
					.checkLegalHoldPermission(getSession().getAttribute("legalHoldEnabledAdmin").toString());
			legalHoldPermission = (adminUserPermission != null) ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		getSession().setAttribute("legalHoldPermission", legalHoldPermission);
	}
	
	public void getEntityWalletControlId() {
		Integer walletControlId = 0;
		try {
			walletControlId = financeManagerDelegate.getWalletControlId(get_commonCriteria().getCompanyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		getSession().setAttribute("EntityWalletControlId", walletControlId);
	}
	
	public void getEntityWalletControlBanner(){
		String walletBanner = "";
		try {
			walletBanner = financeManagerDelegate.getWalletControlBanner((Integer) getSession().getAttribute("EntityWalletControlId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		getSession().setAttribute("EntityWalletControlBanner", walletBanner);		
	}
	
	private void loadAvailableAndCurrentBalance() {
		double availBalance = 0.0;
		double currBalance =0.0;

		if(get_commonCriteria() == null)
			return;
		
		Integer companyId = get_commonCriteria().getCompanyId();
		
		String roleType = this._commonCriteria.getRoleType();

		AjaxCacheVO ajax = new AjaxCacheVO();

		ajax.setCompanyId(companyId);
		if(roleType.equals("SimpleBuyer"))
			ajax.setRoleType(OrderConstants.BUYER_ROLE);
		else
			ajax.setRoleType(roleType.toUpperCase());

		availBalance = financeManagerDelegate.getAvailableBalance(ajax);
		currBalance = financeManagerDelegate.getCurrentBalance(ajax);
		String abFormat = java.text.NumberFormat.getCurrencyInstance().format(
				availBalance);
		setAttribute("AvailableBalance", abFormat);
		abFormat = java.text.NumberFormat.getCurrencyInstance().format(
				currBalance);
		setAttribute("CurrentBalance", abFormat);
		if (roleType.toUpperCase().equalsIgnoreCase(BUYER)){
			setAttribute("roleType", 3);
		}
		if (roleType.toUpperCase().equalsIgnoreCase(SIMPLE_BUYER)){
			setAttribute("roleType", 5);
		}
		if (roleType.toUpperCase().equalsIgnoreCase(PROVIDERS)){
			setAttribute("roleType", 1);
		}
	}
	
	// Fetch buyer bitFlag and threshold limit and set in session
	public void getBuyerBitFlag() throws Exception {
		String role = get_commonCriteria().getRoleType();
		PIIThresholdVO pIIThresholdVO = new PIIThresholdVO();
		if (role.equalsIgnoreCase(OrderConstants.BUYER) || role.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)){
			pIIThresholdVO = fmPersistDelegate.getBuyerThresholdMap(OrderConstants.BUYER);
		//setting the value in session only for buyer roles	
		/*}
		if(pIIThresholdVO!=null && !role.equalsIgnoreCase(OrderConstants.PROVIDER)) 
		{*/
				getSession().setAttribute("buyerBitFlag", pIIThresholdVO.getThresholdIndex());
				getSession().setAttribute("buyerThreshold", pIIThresholdVO.getThresholdValue());
		}else{
			getSession().setAttribute("buyerBitFlag", false);
			getSession().setAttribute("buyerThreshold", 0D);
		}
	}
	
	public void _setTabs(String role, String selected, String user) {
		
		if(role == null)
			return;
		
		loadAvailableAndCurrentBalance();
		ArrayList<SLTabDTO> tabList = new ArrayList<SLTabDTO>();
		SLTabDTO tab;
		String icon = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getSession().getAttribute(
				"hasMapForTabIcons");
		if (map != null && map.size() > 0) {
			
			//Common for SLAdmin, Buyers and Providers
			icon = null;
			tab = new SLTabDTO("", "fmOverviewHistory_overview.action?rand=" + Math.random(), OrderConstants.FM_OVERVIEW,
					icon, selected, null);
			tabList.add(tab);

			if(!role.equals(OrderConstants.NEWCO_ADMIN)){
				icon = map.get(OrderConstants.FM_FINANCIAL_PROFILE_TAB);
				tab = new SLTabDTO("tab2", "fmFinancialProfile_execute.action?rand=" + Math.random(),OrderConstants.FM_FINANCIAL_PROFILE,
						icon, selected, null);
				tabList.add(tab);
			}

			icon = map.get(OrderConstants.FM_MANAGE_ACCOUNTS_TAB);
			tab = new SLTabDTO("tab3", "fmManageAccounts_execute.action?rand=" + Math.random(),OrderConstants.FM_MANAGE_ACCOUNTS,
					icon, selected, null);
			tabList.add(tab);

			icon = map.get(OrderConstants.FM_MANAGE_FUNDS_TAB);
			tab = new SLTabDTO("tab4", "fmManageFunds_execute.action?rand=" + Math.random(),OrderConstants.FM_MANAGE_FUNDS,
					icon, selected, null);
			tabList.add(tab);
			
			//SL-21117: Revenue Pull Code Change Starts
			boolean valid = false;
			
			List<String> permittedUsers = null;
			try {
				permittedUsers = fmPersistDelegate.getPermittedUsers();
			} catch (BusinessServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(permittedUsers.size() > 0){

				String[] array = new String[permittedUsers.size()];

				for( int i = 0; i < permittedUsers.size(); i++ ){
					array[i] = permittedUsers.get(i).toString().replaceAll(",", "");
				}

				if((Arrays.asList(array).contains(user))){
					valid = true;
				}
			}
            
			if(role.equals(OrderConstants.NEWCO_ADMIN) && valid){
			icon = null;
			tab = new SLTabDTO("tab5", "fmRevenuePull_execute.action?rand=" + Math.random(), OrderConstants.FM_REVENUE_PULL,
					icon, selected, null);
			tabList.add(tab);
			}
			//Code change ends
			
			icon = null;
			tab = new SLTabDTO("tab6", "fmOverviewHistory_history.action?rand=" + Math.random(), OrderConstants.FM_HISTORY,
					icon, selected, null);
			tabList.add(tab);
			if(!role.equals(OrderConstants.NEWCO_ADMIN)){
				icon = null;
				tab = new SLTabDTO("tab7", "fmReports_reports.action?rand=" + Math.random(), OrderConstants.FM_REPORTS,
						icon, selected, null);
				tabList.add(tab);
			}
		}
		setAttribute("tabList", tabList);
	}

	public static HashMap<String, String> setTabIcon(
			HashMap<String, String> map, String tabName, List<IError> errors,
			List<IWarning> warnings) {
			
		if (errors != null && errors.size() > 0) {
			map.put(tabName, OrderConstants.ICON_ERROR);
			if (!tabName.equals(OrderConstants.FM_MANAGE_FUNDS_TAB)) {
				map.put(OrderConstants.FM_MANAGE_FUNDS_TAB,
						OrderConstants.ICON_INCOMPLETE);
			}
		} else if (warnings != null && warnings.size() > 0) {
			map.put(OrderConstants.FM_MANAGE_FUNDS_TAB,
					OrderConstants.ICON_INCOMPLETE);
			map.put(tabName, OrderConstants.ICON_INCOMPLETE);
		} else if ((errors == null || errors.size() == 0) && (warnings == null  ||warnings.size() == 0)) {
			map.put(tabName, OrderConstants.ICON_COMPLETE);
			if (map.get(OrderConstants.FM_FINANCIAL_PROFILE_TAB) != null
					&& map.get(OrderConstants.FM_MANAGE_ACCOUNTS_TAB) != null
					&& map.get(OrderConstants.FM_FINANCIAL_PROFILE_TAB).equals(
							OrderConstants.ICON_COMPLETE)
					&& map.get(OrderConstants.FM_MANAGE_ACCOUNTS_TAB).equals(
							OrderConstants.ICON_COMPLETE)) {
				map.put(OrderConstants.FM_MANAGE_FUNDS_TAB,
						OrderConstants.ICON_COMPLETE);
			} else {
				map.put(OrderConstants.FM_MANAGE_FUNDS_TAB,
						OrderConstants.ICON_INCOMPLETE);
			}
		}

		return map;
	}
	
	public String getCreditCardTokenUrl() {
		String creditCardTokenUrl = "";
		if (getSession().getAttribute("CreditCardTokenUrl") != null) {
			creditCardTokenUrl = (String) getSession().getAttribute("CreditCardTokenUrl");
		} else {
			creditCardTokenUrl = PropertiesUtils.getFMPropertyValue(AppPropConstants.NEW_CREDIT_CARD_TOKEN_URL);
			getSession().setAttribute("CreditCardTokenUrl", creditCardTokenUrl);
		}
		return creditCardTokenUrl;
	}

	public String getCreditCardTokenAPICrndl() {
		String creditCardTokenAPICrndl = "";
		if (getSession().getAttribute("CreditCardTokenAPICrndl") != null) {
			creditCardTokenAPICrndl = (String) getSession().getAttribute("CreditCardTokenAPICrndl");
		} else {
			creditCardTokenAPICrndl = PropertiesUtils.getFMPropertyValue(AppPropConstants.NEW_CREDIT_CARD_TOKEN_CRENDL);
			getSession().setAttribute("CreditCardTokenAPICrndl", creditCardTokenAPICrndl);
		}
		return creditCardTokenAPICrndl;
	}
	
	public String getCreditCardAuthTokenizeUrl() {
		String creditCardAuthTokenizeUrl = "";
		if (getSession().getAttribute("CreditCardAuthTokenizeUrl") != null) {
			creditCardAuthTokenizeUrl = (String) getSession().getAttribute("CreditCardAuthTokenizeUrl");
		} else {
			creditCardAuthTokenizeUrl = PropertiesUtils.getFMPropertyValue(AppPropConstants.NEW_CREDIT_CARD_AUTH_TOKENIZE_URL);
			getSession().setAttribute("CreditCardAuthTokenizeUrl", creditCardAuthTokenizeUrl);
		}
		return creditCardAuthTokenizeUrl;
	}
	
	public String getCreditCardAuthTokenizeXapiKey() {
		String creditCardAuthTokenizeXapiKey = "";
		if (getSession().getAttribute("CreditCardAuthTokenizeXapikey") != null) {
			creditCardAuthTokenizeXapiKey = (String) getSession().getAttribute("CreditCardAuthTokenizeXapikey");
		} else {
			creditCardAuthTokenizeXapiKey = PropertiesUtils.getFMPropertyValue(AppPropConstants.NEW_CREDIT_CARD_AUTH_TOKENIZE_XAPIKEY);
			getSession().setAttribute("CreditCardAuthTokenizeXapikey", creditCardAuthTokenizeXapiKey);
		}
		return creditCardAuthTokenizeXapiKey;
	}
	
	
	
	public String getMaxWalletHistoryExportLimit() {
		return maxWalletHistoryExportLimit;
	}
	public void setMaxWalletHistoryExportLimit(String maxWalletHistoryExportLimit) {
		this.maxWalletHistoryExportLimit = maxWalletHistoryExportLimit;
	}
	
	public IFMPersistDelegate getFmPersistDelegate() {
		return fmPersistDelegate;
	}

	public void setFmPersistDelegate(IFMPersistDelegate fmPersistDelegate) {
		this.fmPersistDelegate = fmPersistDelegate;
	}
	public IFinanceManagerDelegate getFinanceManagerDelegate() {
		return financeManagerDelegate;
	}

	public void setFinanceManagerDelegate(
			IFinanceManagerDelegate financeManagerDelegate) {
		this.financeManagerDelegate = financeManagerDelegate;
	}
	public ILookupDelegate getLookupdelegate() {
		return lookupdelegate;
	}
	public void setLookupdelegate(ILookupDelegate lookupdelegate) {
		this.lookupdelegate = lookupdelegate;
	}
	
	
	
}
