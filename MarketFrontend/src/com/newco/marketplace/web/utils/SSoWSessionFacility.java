package com.newco.marketplace.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderAddFundsDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderReviewDTO;
import com.newco.marketplace.web.dto.simple.SBLocationFormDTO;
import com.newco.marketplace.web.validator.sow.IPersistor;
import com.newco.marketplace.web.validator.sow.PersistorFactory;
import com.newco.marketplace.web.validator.sow.SOWValidatorFacility;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author Douglas Miller, Sogeti USA, LLC
 *
 * $Revision: 1.11 $ $Author: schavda $ $Date: 2008/05/31 16:10:24 $
 */
public class SSoWSessionFacility {

	private static final Logger logger = Logger.getLogger("SSoWSessionFacility");
	private static SSoWSessionFacility _instance = new SSoWSessionFacility();
	ISOWizardPersistDelegate isoWizardPersistDelegate;
	IFinanceManagerDelegate financeManagerDelegate;

	private SSoWSessionFacility() {
	}

	public static SSoWSessionFacility getInstance() {
		if (_instance == null) {
			getSyncInstance();
		} else {
			getUnSyncInstance();
		}
		return _instance;
	}

	static synchronized public SSoWSessionFacility getSyncInstance() {
		if (_instance == null) {
			_instance = new SSoWSessionFacility();
		}
		return _instance;
	}

	public static SSoWSessionFacility getUnSyncInstance() {
		if (_instance == null) {
			_instance = new SSoWSessionFacility();
		}
		return _instance;
	}
	
	
	public String createSimpleBuyerGUIDId() throws BusinessServiceException {
		
		String id = null;
		try {
			id = new RandomGUID().generateGUID().toString();
		} catch (Exception e) {
			logger.error("Unable to generate GUID for simple buyer id",e);
			throw new BusinessServiceException("Unable to generate GUID for simple buyer id",e);
		}
		return id;
	}
	
	/*
	 * This method evaluates SOW (Service Order Wizard) bean. This typically
	 * gets called when user clicks [Next] or [Previous]
	 * 
	 * @param sowTabDTO
	 *            SOWBaseTabDTO
	 * @param tabNavigationDTO
	 *            TabNavigationDTO
	 * @return serviceOrderWizardBean ServiceOrderWizardBean
	 * @throws BusinessServiceException
	 */
	public void initSessionFactilty(
					ISOWizardFetchDelegate fetchDelegate, String mode)
			throws BusinessServiceException {
		Map<String, Object> sessionMap = 
				ActionContext.getContext().getSession();
		
	
		SimpleServiceOrderWizardBean serviceOrderWizardBean = 
			(SimpleServiceOrderWizardBean) 
					sessionMap.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		String soID = null;
		HashMap<String, Object> soDTOs = null;
		// make sure there is no left over session data
		sessionMap.put("firstorder", null);
		
		//see if the user is logged on - there are common dtos used in create and edit mode
		Boolean isLoggedIn = (Boolean) sessionMap.get(SOConstants.IS_LOGGED_IN);
		CreateServiceOrderDescribeAndScheduleDTO loggedInDescribeScheduleLocationDTO = null;
		if (isLoggedIn != null && isLoggedIn.booleanValue()) {
			loggedInDescribeScheduleLocationDTO =loadBuyerLocationInfo(fetchDelegate);
			Boolean firstOrderLoggedIn = (Boolean) sessionMap.get(OrderConstants.SIMPLE_BUYER_FIRST_SO_LOGGED_IN);
			if (firstOrderLoggedIn != null && firstOrderLoggedIn.booleanValue()) { 
				loggedInDescribeScheduleLocationDTO.setFirstServiceOrderLoggedIn(firstOrderLoggedIn);
			}
		}
		
		if (mode != null && mode.equalsIgnoreCase(OrderConstants.EDIT_MODE)) {
			logger.debug("Beginning edit mode setup");
			if (sessionMap != null && 
				sessionMap.containsKey(OrderConstants.SO_ID)) {
				soID = (String) sessionMap.get(OrderConstants.SO_ID);
			} else {
				logger.debug("Service Order is not available in session.");
				throw new BusinessServiceException(
						"Service Order ID unavailable");
			}
			// load the service order DTOs
			try {
				soDTOs = loadServiceOrderDTO(soID, fetchDelegate);
			} catch (BusinessServiceException bse) {
				logger.debug("Exception thrown while getting service order("+ soID + ")");
				throw bse;
			}
			serviceOrderWizardBean = new SimpleServiceOrderWizardBean();
			serviceOrderWizardBean.setTabDTOs(soDTOs);
			serviceOrderWizardBean.setSoId(soID);
			sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
			sessionMap.put(OrderConstants.APP_MODE, OrderConstants.EDIT_MODE);
			sessionMap.put(OrderConstants.SSO_WIZARD_IN_PROGRESS, OrderConstants.SSO_WIZARD_IN_PROGRESS_VALUE);
			logger.debug("Ending edit mode setup");
		} else if (mode.equalsIgnoreCase(OrderConstants.COPY_MODE)){
			if (sessionMap != null && 
				sessionMap.containsKey(OrderConstants.SO_ID)) {
				soID = (String) sessionMap.get(OrderConstants.SO_ID);
			} else {
				logger.debug("Service Order is not available in session.");
				throw new BusinessServiceException(
						"Service Order ID unavailable");
			}
			// load the service order DTOs
			try {
				soDTOs = loadServiceOrderDTO(soID, fetchDelegate);
				//CreateServiceOrderDescribeAndScheduleDTO describeDTO = (CreateServiceOrderDescribeAndScheduleDTO) soDTOs.get(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
				CreateServiceOrderFindProvidersDTO findProvidersDTO = (CreateServiceOrderFindProvidersDTO) soDTOs.get(OrderConstants.SSO_FIND_PROVIDERS_DTO);
				findProvidersDTO.setSo_id(null);
				CreateServiceOrderReviewDTO reviewDto = (CreateServiceOrderReviewDTO) soDTOs.get(OrderConstants.SSO_ORDER_REVIEW_DTO);
				reviewDto.setOrderNumber(null);
			} catch (BusinessServiceException bse) {
				logger.debug("Exception thrown while getting service order("+ soID + ")");
				throw bse;
			}
			serviceOrderWizardBean = new SimpleServiceOrderWizardBean();
			serviceOrderWizardBean.setTabDTOs(soDTOs);
			serviceOrderWizardBean.setSoId(null);
			sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
			sessionMap.put(OrderConstants.APP_MODE, OrderConstants.COPY_MODE);
			sessionMap.put(OrderConstants.SSO_WIZARD_IN_PROGRESS, OrderConstants.SSO_WIZARD_IN_PROGRESS_VALUE);
			logger.debug("Ending copy mode setup");
		} else if (mode.equalsIgnoreCase(OrderConstants.CREATE_MODE)){
			soDTOs = loadPageDTOs();
			serviceOrderWizardBean = new SimpleServiceOrderWizardBean();
			serviceOrderWizardBean.setTabDTOs(soDTOs);
			serviceOrderWizardBean.setSoId(null);
			if (loggedInDescribeScheduleLocationDTO != null) {
				((Map) serviceOrderWizardBean.getTabDTOs()).put(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO, loggedInDescribeScheduleLocationDTO);
			}
			sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
			sessionMap.put(OrderConstants.APP_MODE, OrderConstants.CREATE_MODE);
			sessionMap.put(OrderConstants.SSO_WIZARD_IN_PROGRESS, OrderConstants.SSO_WIZARD_IN_PROGRESS_VALUE);
		}
	}
	
	private HashMap<String, Object> loadPageDTOs() {
		HashMap<String, Object> _dtoMap = new HashMap<String, Object>();
		CreateServiceOrderFindProvidersDTO providersDTO = new CreateServiceOrderFindProvidersDTO();
		CreateServiceOrderDescribeAndScheduleDTO createSODTO = new CreateServiceOrderDescribeAndScheduleDTO();
		CreateServiceOrderAddFundsDTO addFundsDTO = new CreateServiceOrderAddFundsDTO();
		CreateServiceOrderReviewDTO orderReviewDTO  = new CreateServiceOrderReviewDTO();
		SBLocationFormDTO selectLocationDTO = new SBLocationFormDTO();
		
		_dtoMap.put(OrderConstants.SSO_FIND_PROVIDERS_DTO, providersDTO);
		_dtoMap.put(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO, createSODTO);
		_dtoMap.put(OrderConstants.SSO_ADD_FUNDS_DTO, addFundsDTO);
		_dtoMap.put(OrderConstants.SSO_ORDER_REVIEW_DTO, orderReviewDTO);
		_dtoMap.put(OrderConstants.SSO_SELECT_LOCATION_DTO,  selectLocationDTO);
		return _dtoMap;
	}
	
	public void evaluateSSOWBeanState(SOWBaseTabDTO sowTabDTO) throws BusinessServiceException {
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		SimpleServiceOrderWizardBean serviceOrderSimpleWizardBean = null;
		if ( sessionMap != null && sowTabDTO != null) {
			serviceOrderSimpleWizardBean = (SimpleServiceOrderWizardBean) sessionMap
					.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
			validateAndIntrospectSOWBean(serviceOrderSimpleWizardBean, sowTabDTO);
		}
	}
	
	
	private SimpleServiceOrderWizardBean getSOWBean() {
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean = null;
		if (sessionMap != null) {
			serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
					.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		}
		return serviceOrderWizardBean;
	}
	
	
	private void validateAndIntrospectSOWBean(
			SimpleServiceOrderWizardBean serviceOrderWizardBean,
			SOWBaseTabDTO sowTabDTO) {
		// 1. Get the instance of SOWValidatorFacility
		SOWValidatorFacility validatorFacility = SOWValidatorFacility
				.getInstance();
		HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
		if (tabDTOs == null) {
			tabDTOs = new HashMap<String, Object>();
		}
		tabDTOs.put(sowTabDTO.getTabIdentifier(), sowTabDTO);
		// 2. Call validate and this will validate and populate the errors
		validatorFacility.validate(sowTabDTO, getApplicationMode(),	tabDTOs);
	}
	
	/**
	 * This method evaluates all the sime service order DTOS and persists the
	 * service order database.
	 * 
	 * @param isoWizardPersistDelegate
	 *            ISOWizardPersistDelegate
	 * @return SUCCESS if successfully saved the SO. If it fails to validate any
	 * 		   DTO, it returns the DTO identifier.
	 * @throws BusinessServiceException
	 */
	public String evaluateAndSaveSSO(ISOWizardPersistDelegate isoWizardPersistDelegate)
			throws BusinessServiceException {
		
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
				.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		
		if(serviceOrderWizardBean != null){
			HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
			for (Object tabDTO : tabDTOs.values()) {
				if (((SOWBaseTabDTO)tabDTO).getTabIdentifier().equals(OrderConstants.SSO_ADD_FUNDS_DTO)) {
					continue;
				}
				evaluateSSOWBeanState((SOWBaseTabDTO)tabDTO);
				if (((SOWBaseTabDTO)tabDTO).getErrorsOnly().size() > 0) {
					return ((SOWBaseTabDTO)tabDTO).getTabIdentifier();
				}
			}
			IPersistor persistor = PersistorFactory.getInstance()
											.getPersistor(OrderConstants.DB_PERSIST);
			String soId = (String) sessionMap
											.get(OrderConstants.SO_ID);
			if (soId != null) {
				serviceOrderWizardBean.setSoId(soId);
			}
			else{
				ServiceOrder sso = createBareBonesServiceOrder(isoWizardPersistDelegate);
				serviceOrderWizardBean.setSoId(sso.getSoId());
			}
			if (getBuyerId() != null) {
				persistor.persistSimpleServiceOrder(serviceOrderWizardBean,
								isoWizardPersistDelegate, getBuyerId(),false);
			}
		}
		return com.opensymphony.xwork2.Action.SUCCESS;
	}
	
	public boolean evaluateAndPostSOWBean(ISOWizardPersistDelegate isoWizardPersistDelegate, IFinanceManagerDelegate financeManagerDelegate,ISOWizardFetchDelegate fetchDelegate) 
			throws BusinessServiceException {
		
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
							.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
		CreateServiceOrderAddFundsDTO addFundsDTO = (CreateServiceOrderAddFundsDTO)tabDTOs.get(OrderConstants.SSO_ADD_FUNDS_DTO);
		
		boolean gotoPaymentPage = Boolean.FALSE;
		boolean isRouted = (Boolean) sessionMap.get("isRouted");
		Integer buyerId = getBuyerId();
		String soId = "";
	
				if (buyerId != null) {
					
				IPersistor persistor = PersistorFactory.getInstance()
						.getPersistor(OrderConstants.DB_PERSIST);
				try {
					soId = (String) sessionMap.get(OrderConstants.SO_ID);
					if (soId != null) {
						serviceOrderWizardBean.setSoId(soId);
					}
					else{
						ServiceOrder sso = createBareBonesServiceOrder(isoWizardPersistDelegate);
						serviceOrderWizardBean.setSoId(sso.getSoId());
					}
					if(fetchDelegate.checkStatusForRoute(serviceOrderWizardBean.getSoId())){
						ServiceOrder serviceOrder = persistor.persistSimpleServiceOrder(serviceOrderWizardBean,
								isoWizardPersistDelegate, buyerId,false);
						soId = serviceOrder.getSoId();
						//set the CC Funding Amount
						serviceOrder.setFundingAmountCC(addFundsDTO.getTransactionAmount());
											
						sessionMap.put(OrderConstants.SO_ID, soId);
						if(sessionMap.get("simpleBuyerAccountId")!=null)
							serviceOrder.setSimpleBuyerAccountId(Long.parseLong(sessionMap.get("simpleBuyerAccountId").toString()));
						if(isRouted)
							isoWizardPersistDelegate.processReRouteSimpleBuyerSO(serviceOrder);
						else
							isoWizardPersistDelegate.processRouteSimpleBuyerSO(serviceOrder);
						
						// If SO successfully posted, then send email to buyer with posting fees
						//if(serviceOrder.getPostingFee() > 0.0) {
						/*
						 * The below line commented in order to send the email through Cheetah and not through
						 * the velocity conext.
						 */
						//	financeManagerDelegate.sendBuyerPostingFeeEmail(buyerId, soId);			
						//}
					}else{
						addPostErrors(
								"",
								OrderConstants.SOW_REVIEW_REROUTE_BUSINESS_ERROR
										+ OrderConstants.SERVICE_ORDER_MUST_BE_IN_DRAFT_STATE,
								OrderConstants.SOW_TAB_ERROR,addFundsDTO);
						gotoPaymentPage = true;
					}
				} catch (BusinessServiceException bse) {
					bse.printStackTrace();
					if (bse.getMessage() == null
							|| bse.getMessage().trim().equals("")) {
						addPostErrors(
								"",
								OrderConstants.SOW_REVIEW_REROUTE_SYSTEM_ERROR,
								OrderConstants.SOW_TAB_ERROR,addFundsDTO);
					} else {
						addPostErrors(
								"",
								OrderConstants.SOW_REVIEW_REROUTE_BUSINESS_ERROR
										+ bse.getMessage(),
								OrderConstants.SOW_TAB_ERROR,addFundsDTO);
					}
					gotoPaymentPage = true;
				}
				
			}

		return gotoPaymentPage;
	}

	private void addPostErrors(String fieldId, String errorMsg,
			String errorType,CreateServiceOrderAddFundsDTO addFundsDTO) {
		addFundsDTO.addPostError(fieldId, errorMsg, errorType);
	}
	
	private Integer getBuyerId() throws BusinessServiceException {
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		SecurityContext securityCntxt = (SecurityContext) sessionMap
				.get("SecurityContext");
		Integer buyerId = null;
		if (securityCntxt == null) {
			throw new BusinessServiceException("SecurityContext is null");
		} else {
			buyerId = securityCntxt.getCompanyId();
		}
		return buyerId;
	}
	
	

	private Integer getBuyerResourceId() throws BusinessServiceException {
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		SecurityContext securityCntxt = (SecurityContext) sessionMap
				.get("SecurityContext");
		Integer buyerId = null;
		if (securityCntxt == null) {
			throw new BusinessServiceException("SecurityContext is null");
		} else {
			buyerId = securityCntxt.getVendBuyerResId();
		}
		return buyerId;
	}
	
	private Integer getBuyerResourceContactId() throws BusinessServiceException {
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		SecurityContext securityCntxt = (SecurityContext) sessionMap
				.get("SecurityContext");
		Integer resContactId = null;
		if (securityCntxt == null) {
			throw new BusinessServiceException("SecurityContext is null");
		} else {
			resContactId = securityCntxt.getRoles().getContactId();
		}
		return resContactId;
	}	
	
	private HashMap<String, Object> loadServiceOrderDTO(String soID,
			ISOWizardFetchDelegate delegate) throws BusinessServiceException {
		HashMap<String, Object> dtoMap = null;

		try {
			dtoMap = delegate.getServiceOrderDTOs(soID, OrderConstants.SIMPLE_BUYER_ROLEID);
		} catch (BusinessServiceException bse) {
			logger.debug("Exception thrown while getting service order(" + soID
					+ ")");
			throw bse;

		}

		return dtoMap;

	}
	
	private CreateServiceOrderDescribeAndScheduleDTO loadBuyerLocationInfo(ISOWizardFetchDelegate delegate) throws BusinessServiceException {
		return delegate.loadDefaultLocation(getBuyerId(), getBuyerResourceId());
	}

	public String getSoId() throws BusinessServiceException {
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		String soId = (String) sessionMap.get(OrderConstants.SO_ID);
		return soId;
	}
	
	public String getApplicationMode() {
		Map<String, Object> sessionMap = 
				ActionContext.getContext().getSession();
		String appMode = (String) sessionMap.get(OrderConstants.APP_MODE);
			return appMode;
	}
	
    public SOWBaseTabDTO getTabDTO(String tabName) {
    	SimpleServiceOrderWizardBean bean = getSOWBean();
		if (bean == null)
			return null;
		
		if(bean.getTabDTOs() == null)
			return null;
		
		if((SOWBaseTabDTO) bean.getTabDTOs().get(tabName) == null)
			return null;
		
		return (SOWBaseTabDTO) bean.getTabDTOs().get(tabName);
	}
    
    public void invalidateSessionFacilityStates() {
		
		Map<String, Object> sessionMap = 
				ActionContext.getContext().getSession();
		
		sessionMap.put(OrderConstants.SO_ID, null);	
		sessionMap.put(OrderConstants.SSO_SELECT_LOCATION_DTO, null);
		sessionMap.put(OrderConstants.SSO_FIND_PROVIDERS_DTO, null);
		sessionMap.put(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO, null);
		sessionMap.put(OrderConstants.SSO_CREATE_ACCOUNT_DTO, null);
		sessionMap.put(OrderConstants.SSO_ADD_FUNDS_DTO, null);
		sessionMap.put(OrderConstants.SSO_ORDER_REVIEW_DTO, null);
		//Remove session flag to indicate that the user has exited the Service Order Wizard
		sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_INDICTATOR, null);
		sessionMap.put(OrderConstants.SIMPLE_BUYER_FIRST_SO_LOGGED_IN, null);
		sessionMap.put("firstorder", null);
		
	}
    
    /**
	 * This method creates a skeleton record for a Service Order when user
	 * intends to create a new Service Order using 'Create Service Order'
	 * 
	 * @param isoWizardPersistDelegate
	 *            ISOWizardPersistDelegate
	 * @return serviceOrder ServiceOrder
	 * @throws BusinessServiceException
	 */
	public ServiceOrder createBareBonesServiceOrder(
			ISOWizardPersistDelegate isoWizardPersistDelegate)
			throws BusinessServiceException {
		IPersistor persistor = PersistorFactory.getInstance().getPersistor(
				OrderConstants.DB_PERSIST);
		ServiceOrder serviceOrder = null;
		Integer buyerId = getBuyerId();
		Integer buyerResourceId = getBuyerResourceId();
		Integer buyerResContactId = getBuyerResourceContactId();
		
		if (buyerId != null) {

			serviceOrder = persistor.createBareBonesServiceOrder(
					isoWizardPersistDelegate, buyerId, buyerResourceId, buyerResContactId,null);
		}
		return serviceOrder;

	}
	
}
