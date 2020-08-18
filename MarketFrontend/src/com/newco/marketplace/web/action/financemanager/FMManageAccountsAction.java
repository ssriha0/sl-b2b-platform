package com.newco.marketplace.web.action.financemanager;

import java.util.ArrayList;
import java.util.HashMap;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.dto.FMManageAccountsTabDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FMManageAccountsAction extends SLFinanceManagerBaseAction implements Preparable, ModelDriven<FMManageAccountsTabDTO>
{
	private static final long serialVersionUID = 1L;

	private FMManageAccountsTabDTO manageAccountsTabDTO = new FMManageAccountsTabDTO();
	
	private FMManageAccountsTabDTO manageAccountsTabDTO2 = new FMManageAccountsTabDTO();
	
	private ArrayList <LookupVO> creditCardLists;
	
	private ArrayList <LookupVO> accountTypeLists;
	
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
		initProviderManageAccounts();
	}

	public String execute() throws Exception
	{
		String role = get_commonCriteria().getRoleType();
		if(OrderConstants.BUYER_ROLE.equalsIgnoreCase(role))
		{
			initProviderManageAccounts();
		}
		if(OrderConstants.PROVIDER_ROLE.equalsIgnoreCase(role))
		{
			initProviderManageAccounts();			
		}
		
		boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();
		setAttribute("isSLAdmin", isSLAdmin);
		
		return role;
	}
	public void initProviderManageAccounts() throws Exception{		
				
		if(getRequest().getSession().getAttribute("ManageAccountsTabDTO") != null){
			manageAccountsTabDTO = (FMManageAccountsTabDTO)getRequest().getSession().getAttribute("ManageAccountsTabDTO");
			//For SLA login, retrieves the value of Auto Funding Indicator 
			//for checkbox state display
			manageAccountsTabDTO.setAutoACHInd(new Boolean(get_commonCriteria().getSecurityContext().isAutoACH()).toString());
		}else{			
			manageAccountsTabDTO = financeManagerDelegate.getActiveAccountDetails(get_commonCriteria().getCompanyId());
			//For SLA login, retrieves the value of Auto Funding Indicator
			//for checkbox statedisplay
			manageAccountsTabDTO.setAutoACHInd(new Boolean(get_commonCriteria().getSecurityContext().isAutoACH()).toString());
			if (manageAccountsTabDTO.getAccountId() != null )
			{
				manageAccountsTabDTO.checkForEnabledAccount();
			}
			manageAccountsTabDTO2 = financeManagerDelegate.getActiveCreditCardDetails(get_commonCriteria().getCompanyId());
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getCardId() != null){
				manageAccountsTabDTO.setCardId(manageAccountsTabDTO2.getCardId());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getCardId() != null){
				manageAccountsTabDTO.setOldCardId(manageAccountsTabDTO2.getCardId().toString());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getEntityId() != null){
				manageAccountsTabDTO.setEntityId(manageAccountsTabDTO2.getEntityId());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getEncCardNo() != null){
				manageAccountsTabDTO.setEncCardNo(manageAccountsTabDTO2.getEncCardNo());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getCardNumber() != null){
				manageAccountsTabDTO.setCardNumber(manageAccountsTabDTO2.getCardNumber());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getMaskedCardNumber() != null){
				manageAccountsTabDTO.setMaskedCardNumber(manageAccountsTabDTO2.getMaskedCardNumber());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getTokenizeCardNumber() != null){
				manageAccountsTabDTO.setTokenizeCardNumber(manageAccountsTabDTO2.getTokenizeCardNumber());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getCardAccountType() != null){
				manageAccountsTabDTO.setCardAccountType(manageAccountsTabDTO2.getCardAccountType());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getCardTypeId() != null){
				manageAccountsTabDTO.setCardTypeId(manageAccountsTabDTO2.getCardTypeId());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getExpirationMonth() != null){
				manageAccountsTabDTO.setExpirationMonth(manageAccountsTabDTO2.getExpirationMonth());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getExpirationYear() != null){
				manageAccountsTabDTO.setExpirationYear(manageAccountsTabDTO2.getExpirationYear());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getCardHolderName() != null){
				manageAccountsTabDTO.setCardHolderName(manageAccountsTabDTO2.getCardHolderName());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getBillingAddress1() != null){
				manageAccountsTabDTO.setBillingAddress1(manageAccountsTabDTO2.getBillingAddress1());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getBillingAddress2() != null){
				manageAccountsTabDTO.setBillingAddress2(manageAccountsTabDTO2.getBillingAddress2());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getBillingCity() != null){
				manageAccountsTabDTO.setBillingCity(manageAccountsTabDTO2.getBillingCity());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getBillingState() != null){
				manageAccountsTabDTO.setBillingState(manageAccountsTabDTO2.getBillingState());
			}
			if(manageAccountsTabDTO2 != null && manageAccountsTabDTO2.getBillingZip() != null){
				manageAccountsTabDTO.setBillingZip(manageAccountsTabDTO2.getBillingZip());
			}
		}
		if(manageAccountsTabDTO != null && manageAccountsTabDTO.getAccountId() != null){
			getSession().setAttribute(OrderConstants.FM_BUTTON_MODE, OrderConstants.FM_EDIT_BUTTON_MODE);
		}
		if(manageAccountsTabDTO != null && manageAccountsTabDTO.getEscheatAccountId() != null){
			getSession().setAttribute(OrderConstants.FM_ESCHEATMENT_BUTTON_MODE , OrderConstants.FM_EDIT_BUTTON_MODE);
		}
		if(manageAccountsTabDTO != null && manageAccountsTabDTO.getCardId() != null){
			getSession().setAttribute(OrderConstants.FM_CARD_BUTTON_MODE, OrderConstants.FM_EDIT_BUTTON_MODE);
		}
		
		
			// setting the userName
			getSession().setAttribute("userName", get_commonCriteria()
					.getTheUserName());
			manageAccountsTabDTO.setUserName(get_commonCriteria()
					.getTheUserName());
		manageAccountsTabDTO.setCreditCardAuthTokenizeUrl(getCreditCardAuthTokenizeUrl());
		manageAccountsTabDTO.setCreditCardTokenUrl(getCreditCardTokenUrl());
		manageAccountsTabDTO.setCreditCardTokenAPICrndl(getCreditCardTokenAPICrndl());
		manageAccountsTabDTO.setCreditCardAuthTokenizeXapikey(getCreditCardAuthTokenizeXapiKey());
		
	
			
		getRequest().getSession().setAttribute("ManageAccountsTabDTO", manageAccountsTabDTO);
		accountTypeLists = financeManagerDelegate.getAccountTypeList();
		removeCCType(accountTypeLists);
		getSession().setAttribute("accountTypeMap", accountTypeLists);
		creditCardLists = financeManagerDelegate.getCreditCardTypeList();
		getSession().setAttribute("creditCardTypeMap", creditCardLists);
	}

	private void removeCCType(ArrayList<LookupVO> accountTypeLists){
		if(accountTypeLists != null && accountTypeLists.size() >0){
			LookupVO lookupVO = null;
			int iRemoveVO = -1;
			for(int i=0; i<accountTypeLists.size(); i++){
				lookupVO = (LookupVO)accountTypeLists.get(i);
				if(lookupVO.getId() != null && LedgerConstants.CC_ACCOUNT_TYPE == lookupVO.getId().intValue()){
					iRemoveVO = i;
				}
			}
			if(iRemoveVO != -1){
				accountTypeLists.remove(iRemoveVO);
			}
		}
	}
	
	public String saveAccounts() throws Exception{

		return saveAccount(false);

			}
	
	public String saveEscheatAccounts() throws Exception{
		
		return saveAccount(true); 
		
		}

	/**
	 * Description:  Saves whether bank account is enabled for Auto ACH funding
	 * @return
	 * @throws Exception
	 */
	public String saveAutoACHAccounts() throws Exception {
		String role = get_commonCriteria().getRoleType();
		getRequest().getSession().setAttribute("ManageAccountsTabDTO", manageAccountsTabDTO);
		determineRoleId();

		// Sets the enabled_ind to 1 for ACH Auto approval, for SLA login
		if (getSession().getAttribute(IS_ADMIN).equals(Boolean.TRUE)) {
			if ((get_commonCriteria().getRoleId() != null) && (OrderConstants.BUYER_ROLEID == get_commonCriteria().getRoleId().intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == get_commonCriteria().getRoleId().intValue())) {
				manageAccountsTabDTO.setSaveAutoFundInd(OrderConstants.ENABLED_IND);

				if (manageAccountsTabDTO.getAutoACHInd().equalsIgnoreCase("true")) {
					get_commonCriteria().getSecurityContext().setAutoACH(true);
				} else {
					get_commonCriteria().getSecurityContext().setAutoACH(false);
				}
			}
		}
		financeManagerDelegate.saveAutoACHInfo(manageAccountsTabDTO, get_commonCriteria().getCompanyId());
		getRequest().getSession().removeAttribute("ManageAccountsTabDTO");
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}

	/**
	 *  Description:  Determines EntityTypeId from common criteria.
	 */
	private void determineRoleId() {
		if(get_commonCriteria().getRoleId() != null && OrderConstants.BUYER_ROLEID == get_commonCriteria().getRoleId().intValue()){
			manageAccountsTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
		}
		if(get_commonCriteria().getRoleId() != null && OrderConstants.SIMPLE_BUYER_ROLEID == get_commonCriteria().getRoleId().intValue()){
			manageAccountsTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
		}				
		else if(get_commonCriteria().getRoleId() != null && OrderConstants.PROVIDER_ROLEID == get_commonCriteria().getRoleId().intValue()){
			manageAccountsTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_PROVIDER);
		}
		else if(get_commonCriteria().getRoleId() != null && OrderConstants.NEWCO_ADMIN_ROLEID == get_commonCriteria().getRoleId().intValue()){
			manageAccountsTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION);
		}
	}

	public String saveCreditCardInfo() throws Exception{
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute(
				"hasMapForTabIcons");
		manageAccountsTabDTO.creditCardValidate();
		map = setTabIcon(map, OrderConstants.FM_MANAGE_ACCOUNTS_TAB,
				manageAccountsTabDTO.getErrors(), manageAccountsTabDTO
						.getWarnings());
		getSession().setAttribute("hasMapForTabIcons", map);
		if((manageAccountsTabDTO.getErrors()!=null && manageAccountsTabDTO.getErrors().size()>0) 
				|| (manageAccountsTabDTO.getWarnings()!=null && manageAccountsTabDTO.getWarnings().size()>0) ){
			setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		}
		else{
			if(get_commonCriteria().getRoleId() != null && OrderConstants.BUYER_ROLEID == get_commonCriteria().getRoleId().intValue()){
				manageAccountsTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
				manageAccountsTabDTO.setEntityId(get_commonCriteria().getCompanyId());
			}
			if(get_commonCriteria().getRoleId() != null && OrderConstants.SIMPLE_BUYER_ROLEID == get_commonCriteria().getRoleId().intValue()){
				manageAccountsTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
				manageAccountsTabDTO.setEntityId(get_commonCriteria().getCompanyId());
			}
			else if(get_commonCriteria().getRoleId() != null && OrderConstants.PROVIDER_ROLEID == get_commonCriteria().getRoleId().intValue()){
				manageAccountsTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_PROVIDER);
				manageAccountsTabDTO.setEntityId(get_commonCriteria().getCompanyId());
			}
			CreditCardVO ccVo = financeManagerDelegate.saveCreditCardInfo(manageAccountsTabDTO, get_commonCriteria().getCompanyId(),get_commonCriteria().getTheUserName());
			getRequest().getSession().removeAttribute("ManageAccountsTabDTO");
			get_commonCriteria().getSecurityContext().setAccountID(ccVo.getCardId());
			setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
		}
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public String editAccounts() throws Exception{
		String role = get_commonCriteria().getRoleType();
		manageAccountsTabDTO.setAccountDescription("");
		manageAccountsTabDTO.setAccountType("-1");
		manageAccountsTabDTO.setRoutingNumber("");
		manageAccountsTabDTO.setConfirmRoutingNumber("");
		manageAccountsTabDTO.setAccountNumber("");
		manageAccountsTabDTO.setConfirmAccountNumber("");
		manageAccountsTabDTO.setFinancialInstitution("");
		manageAccountsTabDTO.setAccountHolder("");
		manageAccountsTabDTO.setErrors(null);
		manageAccountsTabDTO.setWarnings(null);
		manageAccountsTabDTO.setAccountId(null);
		manageAccountsTabDTO.setAutoACHInd("false");
		get_commonCriteria().getSecurityContext().setAutoACH(false);
		getRequest().getSession().setAttribute("ManageAccountsTabDTO", manageAccountsTabDTO);
		setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		getRequest().getSession().setAttribute(OrderConstants.FM_BUTTON_MODE, OrderConstants.FM_CANCEL_BUTTON_MODE);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public String editEscheatAccounts() throws Exception{
		String role = get_commonCriteria().getRoleType();
		manageAccountsTabDTO.setEscheatAccountDescription("");
		manageAccountsTabDTO.setEscheatAccountType("-1");
		manageAccountsTabDTO.setEscheatRoutingNumber("");
		manageAccountsTabDTO.setEscheatConfirmRoutingNumber("");
		manageAccountsTabDTO.setEscheatAccountNumber("");
		manageAccountsTabDTO.setEscheatConfirmAccountNumber("");
		manageAccountsTabDTO.setEscheatFinancialInstitution("");
		manageAccountsTabDTO.setEscheatAccountHolder("");
		manageAccountsTabDTO.setErrors(null);
		manageAccountsTabDTO.setWarnings(null);
		manageAccountsTabDTO.setEscheatAccountId(null);
		manageAccountsTabDTO.setEscheatAutoACHInd("false");
		get_commonCriteria().getSecurityContext().setAutoACH(false);
		get_commonCriteria().getSecurityContext().setAutoACH(false);
		getRequest().getSession().setAttribute("ManageAccountsTabDTO", manageAccountsTabDTO);
		setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		getRequest().getSession().setAttribute(OrderConstants.FM_ESCHEATMENT_BUTTON_MODE, OrderConstants.FM_CANCEL_BUTTON_MODE);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public String editCreditCardAccounts() throws Exception{
		String role = get_commonCriteria().getRoleType();
		manageAccountsTabDTO.setCardTypeId(null);
		manageAccountsTabDTO.setCardAccountType("-1");
		manageAccountsTabDTO.setCardHolderName("");
		manageAccountsTabDTO.setCardNumber("");
		manageAccountsTabDTO.setExpirationMonth("");
		manageAccountsTabDTO.setExpirationYear("");
		manageAccountsTabDTO.setBillingAddress1("");
		manageAccountsTabDTO.setBillingAddress2("");
		manageAccountsTabDTO.setBillingCity("");
		manageAccountsTabDTO.setBillingState("");
		manageAccountsTabDTO.setBillingZip("");
		manageAccountsTabDTO.setErrors(null);
		manageAccountsTabDTO.setWarnings(null);
		manageAccountsTabDTO.setCardId(null);
		manageAccountsTabDTO.setMaskedCardNumber("");
		manageAccountsTabDTO.setTokenizeCardNumber("");
		manageAccountsTabDTO.setResponseCode("");
		manageAccountsTabDTO.setResponseMessage("");
		manageAccountsTabDTO.setCreditCardErrorMessage("");
		getRequest().getSession().setAttribute("ManageAccountsTabDTO", manageAccountsTabDTO);
		setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		getRequest().getSession().setAttribute(OrderConstants.FM_CARD_BUTTON_MODE, OrderConstants.FM_CANCEL_BUTTON_MODE);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public String cancelAccounts() throws Exception{
		String role = get_commonCriteria().getRoleType();
		getRequest().getSession().removeAttribute("ManageAccountsTabDTO");
		setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		getSession().setAttribute(OrderConstants.FM_BUTTON_MODE, OrderConstants.FM_EDIT_BUTTON_MODE);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public String cancelEscheatmentAccounts() throws Exception{
		String role = get_commonCriteria().getRoleType();
		getRequest().getSession().removeAttribute("ManageAccountsTabDTO");
		setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		getSession().setAttribute(OrderConstants.FM_ESCHEATMENT_BUTTON_MODE, OrderConstants.FM_EDIT_BUTTON_MODE);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;   
	}
	
	public String cancelCreditCardAccounts() throws Exception{
		String role = get_commonCriteria().getRoleType();
		getRequest().getSession().removeAttribute("ManageAccountsTabDTO");
		setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
		getSession().setAttribute(OrderConstants.FM_CARD_BUTTON_MODE, OrderConstants.FM_EDIT_BUTTON_MODE);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}

	
	public FMManageAccountsTabDTO getModel() {
		if(getRequest().getSession().getAttribute("ManageAccountsTabDTO") != null){
			manageAccountsTabDTO = (FMManageAccountsTabDTO)getRequest().getSession().getAttribute("ManageAccountsTabDTO");
		}
		return manageAccountsTabDTO;
	}

	public String saveAccount(boolean isEscheatment)
			throws DataServiceException {

		HashMap<String, String> map = (HashMap<String, String>) getRequest()
				.getSession().getAttribute("hasMapForTabIcons");

		// bank account
		if (!isEscheatment) {
			manageAccountsTabDTO.validate();
			map = setTabIcon(map, OrderConstants.FM_MANAGE_ACCOUNTS_TAB,
					manageAccountsTabDTO.getErrors(), manageAccountsTabDTO
							.getWarnings());
			getSession().setAttribute("hasMapForTabIcons", map);

			getRequest().getSession().setAttribute("ManageAccountsTabDTO",
					manageAccountsTabDTO);

			if (manageAccountsTabDTO.getRoutingNumber().startsWith("X")
					|| manageAccountsTabDTO.getAccountNumber().startsWith("X")) {
				setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
				getSession().setAttribute(OrderConstants.FM_BUTTON_MODE,
						OrderConstants.FM_EDIT_BUTTON_MODE);
			} else {

				if ((manageAccountsTabDTO.getErrors() != null && manageAccountsTabDTO
						.getErrors().size() > 0)
						|| (manageAccountsTabDTO.getWarnings() != null && manageAccountsTabDTO
								.getWarnings().size() > 0)) {
					getRequest().getSession().setAttribute("isEscheatValidated", false);
					setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
				} else {
					determineRoleId();

					financeManagerDelegate.saveFinancialAccounts(
							manageAccountsTabDTO, get_commonCriteria()
									.getCompanyId());

					getRequest().getSession().removeAttribute(
							"ManageAccountsTabDTO");

					setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);

					getSession().setAttribute(OrderConstants.FM_BUTTON_MODE,
							OrderConstants.FM_EDIT_BUTTON_MODE);

				}
			}
			return GOTO_COMMON_FINANCE_MGR_CONTROLLER;

		}

		else {
			// escheat bank account
			manageAccountsTabDTO.validateEscheatAccount();
			map = setTabIcon(map, OrderConstants.FM_MANAGE_ACCOUNTS_TAB,
					manageAccountsTabDTO.getErrors(), manageAccountsTabDTO
							.getWarnings());
			getSession().setAttribute("hasMapForTabIcons", map);

			getRequest().getSession().setAttribute("ManageAccountsTabDTO",
					manageAccountsTabDTO);

			if (manageAccountsTabDTO.getEscheatRoutingNumber().startsWith("X")
					|| manageAccountsTabDTO.getEscheatAccountNumber()
							.startsWith("X")) {
				setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
				getSession().setAttribute(
						OrderConstants.FM_ESCHEATMENT_BUTTON_MODE,
						OrderConstants.FM_EDIT_BUTTON_MODE);
			} else {

				if ((manageAccountsTabDTO.getErrors() != null && manageAccountsTabDTO
						.getErrors().size() > 0)
						|| (manageAccountsTabDTO.getWarnings() != null && manageAccountsTabDTO
								.getWarnings().size() > 0)) {
					getRequest().getSession().setAttribute("isEscheatValidated", true);
					setDefaultTab(OrderConstants.FM_MANAGE_ACCOUNTS);
				} else {
					determineRoleId();

					financeManagerDelegate.saveEscheatFinancialAccounts(
							manageAccountsTabDTO, get_commonCriteria()
									.getCompanyId());

					getRequest().getSession().removeAttribute(
							"ManageAccountsTabDTO");

					setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);

					getSession().setAttribute(
							OrderConstants.FM_ESCHEATMENT_BUTTON_MODE,
							OrderConstants.FM_EDIT_BUTTON_MODE);

				}
			}
			return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
		}
	}

	public FMManageAccountsTabDTO getManageAccountsTabDTO()
	{
		return getModel();
	}

	public void setManageAccountsTabDTO(FMManageAccountsTabDTO manageAccountsTabDTO) {
		this.manageAccountsTabDTO = manageAccountsTabDTO;
	}

	public String update()
	{
		FMManageAccountsTabDTO dto = getModel();
		if(dto != null)
		{
			int i=0;
			i=i;
		}
		
		String role = get_commonCriteria().getRoleType();
		return role;
	}

	public ArrayList<LookupVO> getAccountTypeLists() {
		return (ArrayList<LookupVO>)getSession().getAttribute("accountTypeMap");
	}

	public void setAccountTypeLists(ArrayList<LookupVO> accountTypeLists) {
		this.accountTypeLists = accountTypeLists;
	}

	public ArrayList<LookupVO> getCreditCardLists() {
		return creditCardLists;
	}

	public void setCreditCardLists(ArrayList<LookupVO> creditCardLists) {
		this.creditCardLists = creditCardLists;
	}
	
	

}
