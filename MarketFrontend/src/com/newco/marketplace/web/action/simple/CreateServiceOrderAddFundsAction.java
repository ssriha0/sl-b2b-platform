package com.newco.marketplace.web.action.simple;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.web.action.base.ISimpleServiceOrderAction;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderAddFundsDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderCreateAccountDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.CreditCardDTO;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.newco.marketplace.web.validator.sow.SOWValidatorFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class CreateServiceOrderAddFundsAction extends SLSimpleBaseAction 
				implements Preparable, ModelDriven<CreateServiceOrderAddFundsDTO>, ISimpleServiceOrderAction
{
	private static final Logger logger = Logger.getLogger(CreateServiceOrderAddFundsAction.class.getName());
	private ICreateServiceOrderDelegate delegate;
	private ISOMonitorDelegate serviceOrderDelegate;
	
	private CreateServiceOrderAddFundsDTO createServiceOrderAddFundsDTO = new CreateServiceOrderAddFundsDTO();
	private CreateServiceOrderDescribeAndScheduleDTO describeDTO;
	
	private static final long serialVersionUID = 0L;

	public CreateServiceOrderAddFundsAction(ICreateServiceOrderDelegate delegate)
	{
		this.delegate = delegate;
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		loadAddress();
		buildMonthYearList();
		loadCreditCardType();
		loadExistingCreditCard();
		loadTermsAndCondText();
		Map<String, Object> balanceMap = loadBalance(serviceOrderDelegate);
		if(balanceMap != null){
			createServiceOrderAddFundsDTO.setAvailableBalance((Double)balanceMap.get("availBalance"));
			createServiceOrderAddFundsDTO.setCurrentBalance((Double)balanceMap.get("currentBalance"));
		}
		setDescribeDTO((CreateServiceOrderDescribeAndScheduleDTO)
				SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO));
		calculateFundsNeeded();
		checkToShowTaxWidget();

	}
	
	/** 
	 * get the Buyer Terms and Conditions text  and SL Bucks Text
	 * @throws DelegateException
	 */
	private void loadTermsAndCondText() throws DelegateException {
		
		CreateServiceOrderCreateAccountDTO defaultAccountDTO = new CreateServiceOrderCreateAccountDTO();
		try {
			delegate.loadAccount(defaultAccountDTO);
		} catch(DelegateException delegateEx) {
			addActionError("Exception Occured while processing the request due to " + delegateEx.getMessage());
			logger.error("Error while getting Terms and Conditions text");
			throw delegateEx;
		}
		getRequest().setAttribute("accountDTO", defaultAccountDTO);

		
	}

	public String displayPage() throws Exception
	{
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
		/* Ideal Solution: But comment out for time being
		// Buyer doesn't owe any money, and need not add any funds, just post the service order and redirect to confirmation page
		if (createServiceOrderAddFundsDTO.getTransactionAmount().doubleValue() <= 0.0d) {
			Map<String, Object> resultMap = null;
			try {
				resultMap = postSO();
			} catch (DelegateException de) {
				logger.error("Post Service Order Failed",de);
			}
			
			if (resultMap != null) {
				getRequest().setAttribute("hasError", "false");
				getRequest().setAttribute("addFundsMsg", resultMap.get("authMessage"));
				setAttribute("mmhURL", PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MMH_REGISTRATION_URL) );
				//setAttribute( "email", createServiceOrderAddFundsDTO.getEmail() );
				setAttribute("firstName", get_commonCriteria().getFName() );
				setAttribute("lastName", get_commonCriteria().getLName() );
				setAttribute("zipCode", createServiceOrderAddFundsDTO.getZipcode() );
				setAttribute("messageType", "Order Confirmation");
				return "to_confirmation_page";
			} else {
				CreditCardDTO creditCardDTO = createServiceOrderAddFundsDTO.getNewCreditCard();				
				if(creditCardDTO != null){
					creditCardDTO.setSecurityCode(null);				
					createServiceOrderAddFundsDTO.setNewCreditCard(creditCardDTO);
				}
				getRequest().setAttribute("hasError", "true");
				getRequest().setAttribute("addFundsMsg", "Post service order failed");
			}
		}
		*/
		
		CreateServiceOrderAddFundsDTO addFundsDTO = 
			(CreateServiceOrderAddFundsDTO)SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_ADD_FUNDS_DTO);

		if(addFundsDTO != null){						
			mapCreateServiceOrderAddFundsDTO(addFundsDTO);			
		}				
		
		// The next line gets set in this tag <body onload=func>
		setAttribute("onloadFunction", "fnShowDiv('sameAddressAsBilling','newAddress')");
		getRequest().setAttribute("SB_PAYMENT_PAGE", true);
		return SUCCESS;
	}
	
	private Map<String, Object> postSO() throws DelegateException {
		Map<String, Object> resultMap = getCreateServiceOrderDelegate().
						postSimpleBuyerServiceOrder(createServiceOrderAddFundsDTO, get_commonCriteria().getCompanyId(), get_commonCriteria().getTheUserName());
		// after posting  order get doc from temp to so doc tables
		String simpleBuyerId = (String)getSession().getAttribute(Constants.SESSION.SIMPLE_BUYER_GUID);
		String soId;
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
		.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		soId = serviceOrderWizardBean.getSoId();
		
		if(soId != null && simpleBuyerId != null)
		{
			getCreateServiceOrderDelegate().saveDocuments(simpleBuyerId, soId,null);
		}
		
		return resultMap;
	}
	
	public CreateServiceOrderAddFundsDTO getModel()
	{
//		model = 
//			(CreateServiceOrderAddFundsDTO)
//						SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
       return createServiceOrderAddFundsDTO;
	}

	
	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return delegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.delegate = createServiceOrderDelegate;
	}

	public String next() throws Exception{
		createServiceOrderAddFundsDTO = getModel();
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<String, Object> resultMap = null;
		boolean errorWarningFlag = Boolean.FALSE;
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();

		/** if SO is already posted take user to find providers page to start with new SO */
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
		try{
			SOWContactLocationDTO contactLocationDTO = (SOWContactLocationDTO)sessionMap.get("buyer_primary_location");
			createServiceOrderAddFundsDTO.validate();
			
			if(createServiceOrderAddFundsDTO.getErrors() != null
					&& createServiceOrderAddFundsDTO.getErrors().size() > 0){
				for(int i=0;i<createServiceOrderAddFundsDTO.getErrors().size();i++){
					logger.error(createServiceOrderAddFundsDTO.getErrors().get(i).getMsg());
				}
				CreditCardDTO creditCardDTO = createServiceOrderAddFundsDTO.getNewCreditCard();				
				if(creditCardDTO != null){
					creditCardDTO.setSecurityCode(null);				
					createServiceOrderAddFundsDTO.setNewCreditCard(creditCardDTO);
				}
			}else{
				// Save the information
				SimpleServiceOrderWizardBean serviceOrderSimpleWizardBean = null;
				serviceOrderSimpleWizardBean = (SimpleServiceOrderWizardBean) sessionMap
							.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
				SOWValidatorFacility validatorFacility = SOWValidatorFacility.getInstance();
				
				if(serviceOrderSimpleWizardBean != null){
					HashMap<String, Object> tabDTOs = serviceOrderSimpleWizardBean.getTabDTOs();
					if (tabDTOs == null) {
						tabDTOs = new HashMap<String, Object>();
					}
					tabDTOs.put(OrderConstants.SSO_ADD_FUNDS_DTO, createServiceOrderAddFundsDTO);
					// 2. Call validate and this will validate and populate the errors
					validatorFacility.validate(null, OrderConstants.SOW_EDIT_MODE,	tabDTOs);
					
					
					additionalErrorMessageHandling(tabDTOs);
					
					
					
					errorWarningFlag = validatorFacility.isErrorsWarningExist(tabDTOs);
				}
				
				if(!errorWarningFlag){
					if(createServiceOrderAddFundsDTO.getSameAddressAsBilling().intValue() == 1){
						CreditCardDTO ccDto = createServiceOrderAddFundsDTO.getNewCreditCard();
						if(ccDto == null){
							ccDto = new CreditCardDTO();
						}
						ccDto.setBillingAddress1(contactLocationDTO.getStreetName1());
						ccDto.setBillingAddress2(contactLocationDTO.getStreetName2());
						ccDto.setBillingApartmentNumber(contactLocationDTO.getAptNo());
						ccDto.setBillingCity(contactLocationDTO.getCity());
						ccDto.setBillingLocationName(contactLocationDTO.getLocationName());
						ccDto.setBillingState(contactLocationDTO.getState());
						ccDto.setBillingZipCode(contactLocationDTO.getZip());
						createServiceOrderAddFundsDTO.setNewCreditCard(ccDto);
					}					
					
					if(StringUtils.isNotBlank(createServiceOrderAddFundsDTO.getTaxPayerId())){
							if(createServiceOrderAddFundsDTO.getEinSsnInd()!=null && createServiceOrderAddFundsDTO.getEinSsnInd().equals("1"))
								getCreateServiceOrderDelegate().saveTaxPayerId(buyerId, createServiceOrderAddFundsDTO.getTaxPayerId());
							else if (createServiceOrderAddFundsDTO.getEinSsnInd()!=null && createServiceOrderAddFundsDTO.getEinSsnInd().equals("2"))
								getCreateServiceOrderDelegate().saveSSN(get_commonCriteria().getCompanyId(), createServiceOrderAddFundsDTO.getTaxPayerId());
					}
					if(describeDTO.getWfStateId()!=null && describeDTO.getWfStateId().intValue()==110)
						sessionMap.put("isRouted", true);
					else
						sessionMap.put("isRouted", false);
					
					String soId = serviceOrderSimpleWizardBean.getSoId();
					createServiceOrderAddFundsDTO.setSoId(soId);
					
					resultMap = getCreateServiceOrderDelegate().postSimpleBuyerServiceOrder(createServiceOrderAddFundsDTO, buyerId, get_commonCriteria().getTheUserName());
					// after posting  order get doc from temp to so doc tables
					String simpleBuyerId = (String)getSession().getAttribute(Constants.SESSION.SIMPLE_BUYER_GUID);
					
					//Map<String, Object> sessionMap = ActionContext.getContext().getSession();
					SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
					.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
					soId = serviceOrderWizardBean.getSoId();
					Integer entityId = _commonCriteria.getVendBuyerResId();
					if(soId != null && simpleBuyerId != null)
					{
						getCreateServiceOrderDelegate().saveDocuments(simpleBuyerId, soId, entityId);
					}
					
					if(resultMap != null){
						if(((Boolean)resultMap.get("isAuthSuccess")).booleanValue() == true){
							getRequest().setAttribute("hasError", "false");
							getRequest().setAttribute("addFundsMsg", resultMap.get("authMessage"));
							setAttribute( "mmhURL", PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MMH_REGISTRATION_URL) );
							//setAttribute( "email", createServiceOrderAddFundsDTO.getEmail() );
							setAttribute( "firstName", get_commonCriteria().getFName() );
							setAttribute( "lastName", get_commonCriteria().getLName() );
							setAttribute( "zipCode", createServiceOrderAddFundsDTO.getZipcode() );
							setAttribute("messageType", "Order Confirmation");
							
							return "to_confirmation_page";
						}else{
							CreditCardDTO creditCardDTO = createServiceOrderAddFundsDTO.getNewCreditCard();				
							if(creditCardDTO != null){
								creditCardDTO.setSecurityCode(null);				
								createServiceOrderAddFundsDTO.setNewCreditCard(creditCardDTO);
							}
							getRequest().setAttribute("hasError", "true");
							getRequest().setAttribute("addFundsMsg", resultMap.get("authMessage"));
						}
					}else{
						CreditCardDTO creditCardDTO = createServiceOrderAddFundsDTO.getNewCreditCard();				
						if(creditCardDTO != null){
							creditCardDTO.setSecurityCode(null);				
							createServiceOrderAddFundsDTO.setNewCreditCard(creditCardDTO);
						}
						getRequest().setAttribute("hasError", "true");
						getRequest().setAttribute("addFundsMsg", "Post service order failed");
					}
				}else{
					getRequest().setAttribute("hasError", "true");
					getRequest().setAttribute("addFundsMsg", "You have errors or warnings. Click on link above to go to the section with errors");
					CreditCardDTO creditCardDTO = createServiceOrderAddFundsDTO.getNewCreditCard();				
					if(creditCardDTO != null){
						creditCardDTO.setSecurityCode(null);				
						createServiceOrderAddFundsDTO.setNewCreditCard(creditCardDTO);
					}
				}
			}
		}catch(DelegateException de){
			CreditCardDTO creditCardDTO = createServiceOrderAddFundsDTO.getNewCreditCard();				
			if(creditCardDTO != null){
				creditCardDTO.setSecurityCode(null);				
				createServiceOrderAddFundsDTO.setNewCreditCard(creditCardDTO);
			}
			logger.error("Error authenticating credit card ");
			getRequest().setAttribute("hasError", "true");
			getRequest().setAttribute("addFundsMsg", "Error occured. Try again");
		}
		return SUCCESS;
	}


	// Collect errors from all CSO dto's and compile them into the Add Refund DTO.
	// Prepend link to appropriate section in fieldId data member.
	private void additionalErrorMessageHandling(HashMap<String, Object> tabDTOs)
	{
		Set set = tabDTOs.keySet();
		Iterator iterator =(Iterator) set.iterator();
		List<IError> newList = new ArrayList<IError>();
		while (iterator.hasNext()){
			String key = (String)iterator.next();
			if(key != null)
			{
				//TODO it was crashing with class cast exception with string
				if (!key.equalsIgnoreCase(OrderConstants.SO_ID))  
				{
					SOWBaseTabDTO tab = (SOWBaseTabDTO)tabDTOs.get(key);
					if(tab.getErrors() != null && tab.getErrors().size() > 0)
					{							
						for(IError error: tab.getErrors())
						{
							String fieldId = "";
																	
							if(tab.getClass() != null)
							{
								
								if(tab.getClass().getName().indexOf("CreateServiceOrderDescribeAndScheduleDTO") >- 0)
									fieldId = "<a href='${contextPath}/csoDescribeAndSchedule_displayPage.action'>Describe And Schedule:</a>";
								else if(tab.getClass().getName().indexOf("CreateServiceOrderFindProvidersDTO") >= 0)
									fieldId = "<a href='${contextPath}/csoFindProviders_displayPage.action'>Find Providers:</a>";
							}
							
							fieldId += error.getFieldId();
							IError newError =  new SOWError(fieldId, error.getMsg(), error.getMsgType());								
							newList.add(newError);
						}
					}								
				}
			}
		}
		
		createServiceOrderAddFundsDTO.getErrors().addAll(newList);
		
	}
	
	public String previous() throws Exception {
		createServiceOrderAddFundsDTO = getModel();
//		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
//		
//		sessionMap.put(OrderConstants.SSO_ADD_FUNDS_DTO, createServiceOrderAddFundsDTO);
//		//SSoWSessionFacility.getInstance().evaluateSSOWBeanState(createServiceOrderAddFundsDTO);						
		
//		 save into session
		Map<String, Object> sessionMap = (Map<String, Object>)ActionContext.getContext().getSession();
		
		
		/** if SO is already posted take user to find providers page to start with new SO */
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
		
		SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean)
							sessionMap.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		if(serviceOrderWizardBean != null){
			HashMap<String, Object> soDTOs = serviceOrderWizardBean.getTabDTOs();
			if(soDTOs != null){
				soDTOs.put(OrderConstants.SSO_ADD_FUNDS_DTO, createServiceOrderAddFundsDTO);
				
				sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
			}
		}
		return "go_to_review_action";
	}

	private void buildMonthYearList(){
		Map<String, Integer> monthList = new TreeMap<String,Integer>();
		Map<String, Integer> yearList = new TreeMap<String,Integer>();
		for(int i=1;i<=12;i++){
			String str = new Integer(i).toString();
			if(str.length() == 1){
				str = "0"+str;
			}
			monthList.put(str, i);
		}

		Calendar calendar = Calendar.getInstance();
		
		int currentYear = calendar.get(Calendar.YEAR);
		for(int i=currentYear;i<currentYear+10;i++){
			String str = new Integer(i).toString().substring(2);
			yearList.put(str, i);
		}
		createServiceOrderAddFundsDTO.setMonthList(monthList);
		createServiceOrderAddFundsDTO.setYearList(yearList);		
	}	
	
	private void loadCreditCardType(){
		ArrayList<LookupVO> list = null;
		try {
			Map<String,Object> sessionMap = ActionContext.getContext().getSession();
			if(sessionMap.get("cred_card_type_list") == null){
				list = getLookupManager().getCreditCardTypeList();
				
				if(list == null){
					list = new ArrayList<LookupVO>();
				}
				sessionMap.put("cred_card_type_list", list);
			}else{
				list = (ArrayList<LookupVO>)sessionMap.get("cred_card_type_list");
			}
			createServiceOrderAddFundsDTO.setCreditCardTypeList(list);
			
		} catch (BusinessServiceException e) {
			logger.error("Error loading credit card types");
		}
	}
	
	private void loadExistingCreditCard(){
		Integer buyerId = get_commonCriteria().getCompanyId();
		CreditCardDTO creditCardDTO = null;
		try {
			creditCardDTO = getCreateServiceOrderDelegate().getActiveCreditCardDetails(buyerId);
		
			if(creditCardDTO != null){		
				
				
				ArrayList<LookupVO> creditCardList = createServiceOrderAddFundsDTO.getCreditCardTypeList();
				
				if(creditCardList != null && creditCardList.size() > 0){
					
					for(LookupVO lookupVo : creditCardList){
						if(StringUtils.isNotBlank(creditCardDTO.getCreditCardType())){
							int cardType = Integer.parseInt(creditCardDTO.getCreditCardType());
							if(lookupVo.getId().intValue() == cardType){
								createServiceOrderAddFundsDTO.setHasExistingCreditCard(Boolean.TRUE);
								creditCardDTO.setCreditCardName(lookupVo.getType());
								break;
							}
						}
					}
				}
				createServiceOrderAddFundsDTO.setExistingCreditCard(creditCardDTO);
			}
		}catch (DelegateException e) {			
			logger.error("Error loading buyer existing credit card");
		}
	}
		
	private void loadAddress(){
		Integer buyerId = get_commonCriteria().getCompanyId();
		Integer buyerResourceId = get_commonCriteria().getVendBuyerResId();
		SOWContactLocationDTO contactLocationDTO = null;
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		try {
			if(sessionMap.get("buyer_primary_location") == null){
				contactLocationDTO = getCreateServiceOrderDelegate().loadBuyerPrimaryLocation(buyerId, buyerResourceId);
				
				sessionMap.put("buyer_primary_location", contactLocationDTO);
			}else{
				contactLocationDTO = (SOWContactLocationDTO)sessionMap.get("buyer_primary_location");
			}
			
			if(createServiceOrderAddFundsDTO == null){
				createServiceOrderAddFundsDTO = new CreateServiceOrderAddFundsDTO();
			}
			if(contactLocationDTO != null){
				createServiceOrderAddFundsDTO.setCity(contactLocationDTO.getCity());
				createServiceOrderAddFundsDTO.setState(contactLocationDTO.getState());
				createServiceOrderAddFundsDTO.setStreet1(contactLocationDTO.getStreetName1());
				createServiceOrderAddFundsDTO.setStreet2(contactLocationDTO.getStreetName2());
				createServiceOrderAddFundsDTO.setZipcode(contactLocationDTO.getZip());
				createServiceOrderAddFundsDTO.setApartmentNo(contactLocationDTO.getAptNo());
				createServiceOrderAddFundsDTO.setLocationName(contactLocationDTO.getLocationName());
			}
		} catch (DelegateException e) {
			logger.error("Error loading buyer primary location");
		}		
	}
	
	private void mapCreateServiceOrderAddFundsDTO(CreateServiceOrderAddFundsDTO addFundsDTO){
		if(addFundsDTO != null){
			createServiceOrderAddFundsDTO.setExistingCardSecurityCode(addFundsDTO.getExistingCardSecurityCode());
			createServiceOrderAddFundsDTO.setUseExistingCard(addFundsDTO.getUseExistingCard());
			
			createServiceOrderAddFundsDTO.setCheckboxSaveThisCard(addFundsDTO.getCheckboxSaveThisCard());
			createServiceOrderAddFundsDTO.setNewCreditCard(addFundsDTO.getNewCreditCard());
			createServiceOrderAddFundsDTO.setSameAddressAsBilling(addFundsDTO.getSameAddressAsBilling());
			//createServiceOrderAddFundsDTO.setTransactionAmount(addFundsDTO.getTransactionAmount());
			createServiceOrderAddFundsDTO.setBuyerTermsAndConditionAgreeInd(addFundsDTO.getBuyerTermsAndConditionAgreeInd());
		}
	}
	

	private void calculateFundsNeeded() throws BusinessServiceException{
		
			CreateServiceOrderDescribeAndScheduleDTO descAndSchedule = getDescribeDTO();
			
			if(descAndSchedule != null){
				String totalLimit = descAndSchedule.getTotalLimit();
				String postingFee = descAndSchedule.getPostingFee();
				if(StringUtils.isNotBlank(totalLimit)){			
					Double totalLimitValue = new Double(totalLimit);
					Double postingFeeValue = new Double(postingFee);
					double orderValue = totalLimitValue.doubleValue() - postingFeeValue.doubleValue();
					
					createServiceOrderAddFundsDTO.setTotalSpendLimit(totalLimitValue);
					
					Double availableBalanace = createServiceOrderAddFundsDTO.getAvailableBalance();
					
					Map<String, Object> sessionMap = ActionContext.getContext().getSession();
					SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
																			.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
					String soId = serviceOrderWizardBean.getSoId();
					double postSOLedgerAmount = 0.0;
					if(soId != null && !"".equals(soId)){
						postSOLedgerAmount = serviceOrderDelegate.getPostSOLedgerAmount(soId);
					}
					
					
					if(postSOLedgerAmount > 0.0){
						if(orderValue > postSOLedgerAmount){
							//increase
							double increaseValue = orderValue - postSOLedgerAmount;
							if(availableBalanace != null){
								//if(increaseValue > availableBalanace.doubleValue()){
									createServiceOrderAddFundsDTO.setTransactionAmount(increaseValue - availableBalanace.doubleValue());
								//}
								//else{
								//	createServiceOrderAddFundsDTO.setTransactionAmount(0.0);
								//}
							}
							else{
								//if(increaseValue > 0.0){
									createServiceOrderAddFundsDTO.setTransactionAmount(increaseValue);
								//}
								//else{
								//	createServiceOrderAddFundsDTO.setTransactionAmount(0.0);
								//}
							}
						}
						else if(orderValue < postSOLedgerAmount){
							//decrease
							createServiceOrderAddFundsDTO.setTransactionAmount(orderValue - postSOLedgerAmount);
						}
						else{
							createServiceOrderAddFundsDTO.setTransactionAmount(0.0);
						}
					}
					else{
						if(availableBalanace != null){
							double fundsNeeded = totalLimitValue.doubleValue() - availableBalanace.doubleValue();
							
							//if(fundsNeeded > 0){
								createServiceOrderAddFundsDTO.setTransactionAmount(fundsNeeded);
							//}else{
							//	createServiceOrderAddFundsDTO.setTransactionAmount(0.0);
							//}
						}
					}
					
				}			
			}
		
	}


	private void checkToShowTaxWidget(){
		Integer buyerId = get_commonCriteria().getCompanyId();
		boolean showTaxWidget = delegate.checkTaxPayerIdRequired(buyerId, createServiceOrderAddFundsDTO.getTransactionAmount());
		
		createServiceOrderAddFundsDTO.setShowTaxPayerWidget(showTaxWidget);
	}
	
	public ISOMonitorDelegate getServiceOrderDelegate() {
		return serviceOrderDelegate;
	}

	public void setServiceOrderDelegate(ISOMonitorDelegate serviceOrderDelegate) {
		this.serviceOrderDelegate = serviceOrderDelegate;
	}

	public CreateServiceOrderDescribeAndScheduleDTO getDescribeDTO() {
		return describeDTO;
	}

	public void setDescribeDTO(CreateServiceOrderDescribeAndScheduleDTO describeDTO) {
		this.describeDTO = describeDTO;	
		CharSequence charSequence = ","; 
		if(StringUtils.isNotEmpty(describeDTO.getServiceDate1Text()) 
				&& StringUtils.isNotEmpty(describeDTO.getServiceDate2Text())
				&& !describeDTO.getServiceDate1Text().contains(charSequence)){
			Date appointDate1 = Timestamp.valueOf(describeDTO.getServiceDate1Text()+" 00:00:00.0");
			Date appointDate2 = Timestamp.valueOf(describeDTO.getServiceDate2Text()+" 00:00:00.0");					
			createServiceOrderAddFundsDTO.setServiceDate1Text(DateUtils.getFormatedDate(appointDate1, "MMM d, yyyy"));
			createServiceOrderAddFundsDTO.setServiceDate2Text(DateUtils.getFormatedDate(appointDate2, "MMM d, yyyy"));
		}else{
			if(StringUtils.isNotEmpty(describeDTO.getFixedServiceDate())
					&& !describeDTO.getFixedServiceDate().contains(charSequence)){
				Date appointDate1 = Timestamp.valueOf(describeDTO.getFixedServiceDate()+" 00:00:00.0");
				createServiceOrderAddFundsDTO.setFixedServiceDate(DateUtils.getFormatedDate(appointDate1, "MMM d, yyyy"));
			}				
		}
	}
}
