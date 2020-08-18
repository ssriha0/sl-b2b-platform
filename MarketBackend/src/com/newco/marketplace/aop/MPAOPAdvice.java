package com.newco.marketplace.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.routing.tiered.SoTierRoutingHistoryBO;
import com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO;
import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerUserProfileDao;
import com.newco.marketplace.persistence.iDao.ledger.TransactionDao;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.persistence.iDao.promo.PromoDAO;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.promo.PromoContentVO;
import com.newco.marketplace.vo.promo.PromoVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.receipts.SOReceiptsVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.routing.tiered.vo.SoTierRoutingHistoryVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;

public class MPAOPAdvice extends BaseAOPAdvice {

	private static final Logger logger = Logger.getLogger(MPAOPAdvice.class);
	
	private ServiceOrderDao serviceOrderDao;
	private IOrderGroupDao orderGroupDAO;
	private PromoDAO promoDAO;
	private IBuyerBO buyerBO;
	private BuyerDao buyerDao;
	private IContactDao contactDao;
	private TransactionDao transactionDao;
	private IBuyerUserProfileDao buyerUserProfileDao;
	private ISPNetworkBO spnetworkBO;
	private SoTierRoutingHistoryBO soTierRoutingHistoryBo;
	private IWalletBO walletBO;

	@SuppressWarnings("unchecked")
	public void before(Method method, Object[] args, Object target) {
		
		try{
			String methodName = method.getName();
			if (logger.isDebugEnabled())
			  logger.debug("MPAOPAdvice-->before()-->MethodName="+methodName);
			
			if(OrderConstants.METHOD_REROUTE_SO.equals(methodName)){
				
				//Fetch the AOP Types - Alert, Logging, Caching from database using MethodName/Action
				List<AOPAdviceVO> alAOPAdviceVOs =  getAOPAdvices(methodName);
				int iSize = alAOPAdviceVOs.size();
				
				
				Map<String, Object> hmParams = null;
	
				//Using Reflection, Call AOPMapper class's method to initialize the HashMap with method arguments
				AOPMapper aopParamMap = new AOPMapper(args);
				Method methodNameParam = null;
				SecurityContext securityContext = null;
				try{
					methodNameParam = aopParamMap.getClass().getMethod(methodName);
					securityContext = (SecurityContext)args[args.length - 1];
				}
				catch(NoSuchMethodException me){
					if (iSize != 0) {
						// if AOP has been configured in the database, we should not
						// be here
						logger.error("MPAOPAdvice-->before()-->NoSuchMethodException-->", me);
					}
					return;
				}
				catch(ClassCastException ce){
					
					logger.error("MPAOPAdvice-->afterReturning()-->ClassCastException-->" +
							"The method "+methodName+" last argument is not SecurityContext ",ce);
				}
				
				hmParams = (HashMap<String, Object>)methodNameParam.invoke(aopParamMap);
				hmParams.put(AOPConstants.AOP_METHOD_NAME, methodName);
				
				//Fetch the Service Order details from database and 
				ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder((String)hmParams.get(AOPConstants.AOP_SO_ID));
	
				if(serviceOrder!=null){
					hmParams = aopParamMap.mapServiceOrder(hmParams, serviceOrder,securityContext);
					hmParams.put(AOPConstants.AOP_SERVICE_ORDER, serviceOrder);				
				}
	
				
				for(AOPAdviceVO aopAdviceVO:alAOPAdviceVOs){
					
					//Check for AOP Advice name
					//Caching
					if(AOPConstants.AOP_ADVICE_CACHING.equals(aopAdviceVO.getAopName())){
						Integer cachingEventId = aopAdviceVO.getCachingEventId();
						AOPAdviceVO cachingAdviceVO = getCachingEvent(cachingEventId);
						if (logger.isDebugEnabled()) {
							logger.debug("MPAOPAdvice-->before-->CACHING-->CachingEventId="+cachingEventId);						
							logger.debug("MPAOPAdvice-->before-->CACHING-->CachingEventClass="+cachingAdviceVO.getCachingEventClass());
						}
						
						hmParams.put(AOPConstants.AOP_CACHING_EVENT_ID, cachingEventId);
						hmParams.put(AOPConstants.AOP_CACHING_EVENT_CLASS, cachingAdviceVO.getCachingEventClass());
						try{
							getCachingAdvice().callCacheEvent(hmParams);
						}
						catch(Exception e){
							logger.error("MPAOPAdvice-->before()-->EXCEPTION-->", e);
							continue;
						}
						break;
					}
					
				}
			}
		}
		catch(Exception e){
			logger.warn("MPAOPAdvice-->before()-->EXCEPTION-->"+e.getMessage()); // Don't log stack trace unnecessarily
		}
	}

	@SuppressWarnings("unchecked")
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		
		try{
			
			String methodName = method.getName();			
			if (logger.isDebugEnabled())
			  logger.debug("MPAOPAdvice-->afterReturning()-->MethodName="+methodName);

			if(returnValue != null){
				if (returnValue instanceof com.newco.marketplace.webservices.base.response.ProcessResponse){ 
					if(((ProcessResponse)returnValue).isError()){
						logger.warn("Profile : afterReturning : end:" + method.getName());
						return;
					}
				} 
			}
			
			//Fetch the AOP Types - Alert, Logging, Caching from database using MethodName/Action
			List<AOPAdviceVO> alAOPAdviceVOs =  getAOPAdvices(methodName);
			int iSize = alAOPAdviceVOs.size();
			
			Map<String, Object> hmParams = null;

			//Using Reflection, Call AOPMapper class's method to initialize the HashMap with method arguments
			AOPMapper aopParamMap = new AOPMapper(args);
			Method methodNameParam = null;
			SecurityContext securityContext = null;
			
			
			
			try{
				methodNameParam = aopParamMap.getClass().getMethod(methodName);
				securityContext = (SecurityContext)args[args.length - 1];
			}
			catch(NoSuchMethodException me){				
				logger.warn("Profile : afterReturning : NoSuchMethod:" + method.getName());
				if (iSize != 0) {
					// if AOP has been configured in the database, we should not
					// be here
					logger.error("MPAOPAdvice-->afterReturning()-->NoSuchMethodException-->", me);
				}
				return;
			} catch(ClassCastException ce){
				
				logger.error("MPAOPAdvice-->afterReturning()-->ClassCastException-->" +
						"The method "+methodName+" last argument is not SecurityContext ",ce);
			} finally {
				
			}
			
			hmParams = (Map<String, Object>)methodNameParam.invoke(aopParamMap);
			
			
			hmParams.put(AOPConstants.AOP_METHOD_NAME, methodName);
			hmParams.put(AOPConstants.AOP_SECURITY_CONTEXT, securityContext);
			
			
			if(OrderConstants.METHOD_ROUTE_ORDER_GROUP.equals(methodName) || OrderConstants.METHOD_ROUTE_GROUP_WITH_PROVIDERS.equals(methodName)
					|| OrderConstants.METHOD_SEND_ALL_PROVIDERS_EXCEPT_ACCEPTED_GRP.equals(methodName)){
				String groupId = (String)hmParams.get(AOPConstants.AOP_SO_GROUP_ID);
				OrderGroupVO orderGroup = orderGroupDAO.getOrderGroupByGroupId(groupId);
				if(orderGroup!=null){
					hmParams = aopParamMap.mapGroupOrder(hmParams, orderGroup,securityContext);
					hmParams.put(AOPConstants.AOP_GROUP_SERVICE_ORDER, orderGroup);				
				}
			}else{
				hmParams = populateServiceOrder(methodName, hmParams, aopParamMap, securityContext);
			}
			//Some methods need things mapped to the hash after the so in set. Methods needing this should
			//create a [methodName]PostSOInjection method. This prevents extra hits to the DB.
			hmParams = executePostSOInjectionMethod(aopParamMap, methodName, hmParams);
			logger.debug("MPAOPAdvice-afterReturning-Passed executePostSOInjectionMethod successfully");
			/*
			 * The following change is done for the JIRA SL-12333 to send email only to newly routed providers
			 * in the cae of B2C users
			 */
			if(OrderConstants.SIMPLE_BUYER_ROLEID==securityContext.getRoleId()){
				hmParams.put(AOPConstants.AOP_SO_TIER_ID, 2);
			}
			processAdvices(methodName, alAOPAdviceVOs, hmParams);
			afterProcessingAdvices(hmParams);
		} catch(Throwable th) {
			logger.error("MPAOPAdvice-->afterReturning()-->EXCEPTION-->", th);
		}
	}

	/**
	 * This method should be used if any post processing is necessary after processing the advices
	 * 
	 * @param hmParams
	 * @throws Exception
	 */
	private void afterProcessingAdvices(Map<String, Object> hmParams) throws Exception {
		if(OrderConstants.METHOD_ROUTE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME)) ||
				OrderConstants.METHOD_REROUTE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))) {
			serviceOrderDao.updateRoutedProvidersEmailSentFlag((ServiceOrder)hmParams.get(AOPConstants.AOP_SERVICE_ORDER));
		}
		
	}

	private void processAdvices(String methodName, List<AOPAdviceVO> alAOPAdviceVOs, Map<String, Object> hmParams) {
		for (AOPAdviceVO aopAdviceVO : alAOPAdviceVOs) {
			try {
				// Check for AOP Advice name
				// For Alert
				if (logger.isDebugEnabled())
				  logger.debug("MPAOPAdvice-afterReturning-AOPTYPE-" + aopAdviceVO.getAopName());
				if (AOPConstants.AOP_ADVICE_ALERT.equals(aopAdviceVO.getAopName())) {
					hmParams.put(AOPConstants.AOP_ROUTE_SO_PROVIDER_PROMO_CONTENT, "");
					hmParams.put(AOPConstants.AOP_REROUTE_SO_PROVIDER_PROMO_CONTENT, "");
					ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
					Integer buyerId = (Integer) hmParams.get(AOPConstants.AOP_BUYER_ID);
					if (so != null) {
						buyerId = so.getBuyer().getBuyerId();
					}
					Buyer buyer = buyerBO.getBuyer(buyerId);
					Integer roleId = getBuyerDao().getBuyerRole(buyer.getUserName());
					List<PromoVO> activePromoVOs = promoDAO.findActivatedPromosBySoId((String) hmParams.get(AOPConstants.AOP_SO_ID));
					for (PromoVO promoVO : activePromoVOs) {
						if (promoVO.getRoleID() == roleId) {
							for (PromoContentVO promoContent : promoVO.getPromoContent()) {
								if (promoContent.getContentLocation().indexOf(aopAdviceVO.getActionName()) > -1) {
									hmParams.put(promoContent.getContentLocation(), promoContent.getContent());
									break;
								}
							}
						}
					}
					/* adding new code */
					if (OrderConstants.METHOD_REPORT_A_PROBLEM.equals(methodName)) {
						// Try getting contact info from by username
						Integer contactId = contactDao.getContactIdByUserName(so.getCreatorUserName());
						if (contactId != null) {
							Contact contact = new Contact();
							contact.setContactId(contactId);
							contact = contactDao.query(contact);
							populateContact(hmParams, contactId, contact);
						}
					}
					/* end of new code */
					handleAOPAlerts(hmParams, aopAdviceVO);
				}
				// Logging
				else if (AOPConstants.AOP_ADVICE_LOGGING.equals(aopAdviceVO.getAopName())) {
					handleAOPLogging(aopAdviceVO, hmParams);
				}
				// Caching
				else if (AOPConstants.AOP_ADVICE_CACHING.equals(aopAdviceVO.getAopName())) {
					handleAOPCaching(aopAdviceVO, hmParams);
				}
			} catch (Exception e) {
				if (logger.isDebugEnabled())
				 logger.debug("MPAOPAdvice-The AOP-" + aopAdviceVO.getAopName() + " failed! Continue with other AOP Types.\r\nEXCEPTION:" + e.getMessage(), e);
				continue;
			}

		}
	}

	private Map<String, Object> populateServiceOrder(String methodName, Map<String, Object> hmParams, AOPMapper aopParamMap, SecurityContext securityContext) throws Exception {
		//Fetch the Service Order details from database 
		ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder((String)hmParams.get(AOPConstants.AOP_SO_ID));
		
		if(serviceOrder == null){
			String soId = orderGroupDAO.getFirstSoIdForGroup((String)hmParams.get(AOPConstants.AOP_SO_ID));
			serviceOrder = serviceOrderDao.getServiceOrder(soId);
		}
		
		hmParams = aopParamMap.mapServiceOrder(hmParams, serviceOrder,securityContext);
		hmParams.put(AOPConstants.AOP_SERVICE_ORDER, serviceOrder);
		hmParams.put(AOPConstants.AOP_SO_SPN_ID, serviceOrder.getSpnId());
		
		Contact contact = contactDao.getByBuyerId(((Buyer)serviceOrder.getBuyer()).getBuyerId());
		
		if(contact != null){
			hmParams.put(AOPConstants.AOP_BUYERUSERNAME, contact.getFirstName() + " " + contact.getLastName());
		}
		
		hmParams.put(AOPConstants.AOP_BUYERUSERNAME, contact.getFirstName() + " " + contact.getLastName());
		if(OrderConstants.METHOD_PROCESS_SUPPORT_ADD_NOTE.equals(methodName)) {
			processAddNote(hmParams, serviceOrder, contact);
		} else if(OrderConstants.METHOD_REASSIGN_SERVICE_ORDER.equals(methodName)) {
		    processReasssignSO(hmParams, serviceOrder, contact);                    
		} else if(OrderConstants.METHOD_ACCEPT_SERVICEORDER.equals(methodName)) {
			processAcceptSO(hmParams, serviceOrder);
			createSoReceipt(hmParams, securityContext,serviceOrder);
		}
		else if(OrderConstants.METHOD_SEND_ALL_PROVIDER_REJECT_ALERT.equals(methodName)) {
			Buyer buyer=serviceOrder.getBuyer();
			Contact contact1=contactDao.getByBuyerId(buyer.getBuyerId());
			hmParams.put(AOPConstants.AOP_BUYERUSERNAME,contact1.getFirstName()+" "+ contact1.getLastName());						
			// The following code added to get the four parameter key names to insert into alert task table by putting
			// into HashMap hmParams
		} else if(OrderConstants.METHOD_REJECT_SERVICEORDER.equals(methodName)) {					
			processRejectServiceOrder(hmParams, securityContext, serviceOrder);
		} else if(OrderConstants.METHOD_ISSUE_RESOLUTION.equals(methodName)) {
			processIssueResolution(hmParams, securityContext, serviceOrder);	
		} else if(OrderConstants.METHOD_ROUTE_SO.equals(methodName)) {
			processRoute(methodName, hmParams, aopParamMap, securityContext,serviceOrder);
		}
		else if(OrderConstants.METHOD_REROUTE_SO.equals(methodName))
		{
			processReRoute(hmParams, securityContext);
		}
		return hmParams;
	}

	private void processAddNote(Map<String, Object> hmParams, ServiceOrder serviceOrder, Contact contact) {
		// Checking for null values
		if(StringUtils.isNotBlank(serviceOrder.getCreatorUserName()))
			hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, serviceOrder.getCreatorUserName());
		else
			hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, "NA");
		hmParams.put(AOPConstants.AOP_USERNAME, contact.getFirstName() + " " + contact.getLastName());
		hmParams.put(AOPConstants.AOP_USER_EMAIL, AlertConstants.SERVICE_LIVE_MAILID_SO_SUPPORT);
	}

	private void processReasssignSO(Map<String, Object> hmParams, ServiceOrder serviceOrder, Contact contact) {
		hmParams.put(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_FNAME,contact.getFirstName());
		hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID,serviceOrder.getAcceptedVendorId());
		hmParams.put(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_LNAME,contact.getLastName());
		String strDate = DateUtils.getFormatedDate(serviceOrder.getServiceDate1(), "MMM dd, yyyy");					
		hmParams.put(AOPConstants.AOP_DATE_OF_SERVICE, strDate);
	}

	private void processAcceptSO(Map<String, Object> hmParams, ServiceOrder serviceOrder) throws DBException {
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT, serviceOrder.getSpendLimitLabor() + serviceOrder.getSpendLimitParts());	
		Buyer buyer=serviceOrder.getBuyer();
		Contact contact1=contactDao.getByBuyerId(buyer.getBuyerId());
		Contact acceptedVendorName=contactDao.getByVendorId(serviceOrder.getAcceptedVendorId());
		hmParams.put(AOPConstants.AOP_BUYERUSERNAME,contact1.getFirstName()+" "+ contact1.getLastName());
		hmParams.put(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_FNAME,acceptedVendorName.getFirstName());
		hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID,serviceOrder.getAcceptedVendorId());
		hmParams.put(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_LNAME,acceptedVendorName.getLastName());
	}

	private void processRejectServiceOrder(Map<String, Object> hmParams, SecurityContext securityContext, ServiceOrder serviceOrder) throws DBException {
		Buyer buyer=serviceOrder.getBuyer();
		Contact contact1=contactDao.getByBuyerId(buyer.getBuyerId());
		String userMail=contact1.getEmail();
		hmParams.put(AOPConstants.AOP_USER_EMAIL,userMail);
		
		Contact provider=new Contact();
		provider.setContactId(contactDao.getContactIdByUserName(securityContext.getUsername()));
		provider=contactDao.query(provider);			
		hmParams.put(AOPConstants.AOP_FIRSTNAME,provider.getFirstName());
		hmParams.put(AOPConstants.AOP_LASTNAME,provider.getLastName());
		hmParams.put(AOPConstants.AOP_USER_EMAIL,userMail);					
		hmParams.put(AOPConstants.AOP_BUYERUSERNAME,contact1.getFirstName()+" "+ contact1.getLastName());								
	}

	private void processIssueResolution(Map<String, Object> hmParams, SecurityContext securityContext, ServiceOrder serviceOrder) throws DBException {
		Contact contact;
		contact=null;
		// Try getting contact info from by username
		if(contact == null) {
			String userName = (String)hmParams.get(AOPConstants.AOP_MODIFIED_BY);
			Integer contactId = contactDao.getContactIdByUserName(userName);
			if(contactId != null) {
				if(OrderConstants.ROLE_PROVIDER.equals(securityContext.getRole())) {			
					Buyer buyer=serviceOrder.getBuyer();
					Contact contact1=contactDao.getByBuyerId(buyer.getBuyerId());
		            populateContact(hmParams, contactId, contact1);
				} else {
		        	int vendorId=serviceOrder.getAcceptedVendorId();
					Contact contact1=contactDao.getByVendorId(vendorId);
		            populateContact(hmParams, contactId, contact1);
		        }
			}
		}
	}

	private void processRoute(String methodName, Map<String, Object> hmParams, AOPMapper aopParamMap, SecurityContext securityContext,ServiceOrder serviceOrder) throws Exception {
		Contact contact;
		int buyerID = securityContext.getCompanyId();
		
		contact = contactDao.getByBuyerId(buyerID);
		String userMail = "SYSTEM";
		if (contact != null && StringUtils.isNotBlank(contact.getEmail())) {
			userMail = contact.getEmail();
			if(StringUtils.isNotBlank(contact.getAltEmail())&& !contact.getAltEmail().equalsIgnoreCase(contact.getEmail())){
				userMail = userMail + AlertConstants.EMAIL_DELIMITER + contact.getAltEmail()+ AlertConstants.EMAIL_DELIMITER;
			}
		}
		
		if (logger.isDebugEnabled())
		  logger.debug("MPAOPAdvice-afterReturning-BuyerID-" + buyerID + "-UserMail-" + userMail);
		hmParams.put(AOPConstants.AOP_USER_EMAIL, userMail);
		createSoReceipt(hmParams, securityContext,serviceOrder);
		populateDataForTierRouting(hmParams, aopParamMap, securityContext);
		
		
	}

	/**
	 * @param hmParams
	 * @param securityContext
	 * @throws BusinessServiceException 
	 */
	private void populateDataForTierRouting(Map<String, Object> hmParams,
			AOPMapper aopParamMap, SecurityContext securityContext) throws BusinessServiceException {
		@SuppressWarnings("unused")
		Integer spnId = (Integer)hmParams.get(AOPConstants.AOP_SO_SPN_ID);
		
		//There are three different processRouteSO method, the following is only application for the method
		//that has got more than 3 arguments and buyer is not a simple buyer
		if (aopParamMap.getParams().length > 3 && securityContext.getRoleId() != OrderConstants.SIMPLE_BUYER_ROLEID) {
			hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.SYSTEM_ROLEID);
			Integer tierId = (Integer) aopParamMap.getParams()[3];
			if (tierId == OrderConstants.NON_TIER) {
				populateEmtyTierOrderHist(hmParams);
				return;
			}
			else if(tierId.equals(OrderConstants.OVERFLOW)){
				hmParams.put(AOPConstants.AOP_TIER_OVERFLOW, Boolean.TRUE);
			}
			
			// populate provider count , this is differant from Normal SO provider count, hence overiding it
			if(hmParams.get(AOPConstants.AOP_VENDOR_RESOURCE_LIST)!= null){
				@SuppressWarnings("unchecked")
				List<Integer> providerCnt = (List<Integer>)hmParams.get(AOPConstants.AOP_VENDOR_RESOURCE_LIST);
				hmParams.put(AOPConstants.AOP_PROVIDER_LIST_COUNT, providerCnt.size());
			}
			
			String soId = (String)hmParams.get(AOPConstants.AOP_SO_ID);
			hmParams.put(AOPConstants.AOP_SO_TIER_ID, tierId);
			SPNetHeaderVO spn = spnetworkBO.getSPNetHeaderInfo((Integer)hmParams.get(AOPConstants.AOP_SO_SPN_ID));
			SoTierRoutingHistoryVO tierHist = 
				soTierRoutingHistoryBo.getSoTierRoutingHistBySoIdAndTierId(soId, tierId);
			if (spn != null && spn.getSpnName() != null && tierHist != null && tierHist.getReasonDesc() != null) {
				populateTieredOrderHistoryTemplate(hmParams, spn, tierHist);
				
			} else {
				logger.error("MPAOPAdvice-->afterReturning()-->populateDataForTierRouting()-->Either SPN name is null or there is no " +
						"Tier routing history for SO ID " + soId + " and tier ID " + tierId );
				populateEmtyTierOrderHist(hmParams);
			}
		}
		else{
			populateEmtyTierOrderHist(hmParams);		
		}
	}

	/**
	 * @param hmParams
	 * @param spn
	 * @param tierHist
	 */
	private void populateTieredOrderHistoryTemplate(Map<String, Object> hmParams, SPNetHeaderVO spn,
			SoTierRoutingHistoryVO tierHist) {
		// populate provider count , this is differant from Normal SO provider count, hence overiding it
		if(hmParams.get(AOPConstants.AOP_VENDOR_RESOURCE_LIST)!= null){
			@SuppressWarnings("unchecked")
			List<Integer> providerCnt = (List<Integer>)hmParams.get(AOPConstants.AOP_VENDOR_RESOURCE_LIST);
			hmParams.put(AOPConstants.AOP_PROVIDER_LIST_COUNT, providerCnt.size());
		}	
		hmParams.put(AOPConstants.AOP_TIER_SPN_TITLE, SPNConstants.SPN_TEXT);
		hmParams.put(AOPConstants.AOP_SO_SPN_NAME, spn.getSpnName());
		hmParams.put(AOPConstants.AOP_TIER_PRIORITY_TITLE, SPNConstants.PRIORITY_TEXT);	
		hmParams.put(AOPConstants.AOP_TIER_REASON_TITLE, SPNConstants.REASON_TEXT);				
		hmParams.put(AOPConstants.AOP_TIER_ROUTING_REASON_DESC, tierHist.getReasonDesc());
		hmParams.put(AOPConstants.AOP_TIER_LINE_BREAK, SPNConstants.LINE_BREAK_TEXT);
		hmParams.put(AOPConstants.AOP_TIER_SEP, SPNConstants.COLON_TEXT);
	}

	/**
	 * @param hmParams
	 */
	private void populateEmtyTierOrderHist(Map<String, Object> hmParams) {
		hmParams.put(AOPConstants.AOP_SO_TIER_ID, "");
		hmParams.put(AOPConstants.AOP_SO_SPN_NAME, "");
		hmParams.put(AOPConstants.AOP_TIER_ROUTING_REASON_DESC, "");
		hmParams.put(AOPConstants.AOP_TIER_SPN_TITLE, "");
		hmParams.put(AOPConstants.AOP_TIER_PRIORITY_TITLE, "");	
		hmParams.put(AOPConstants.AOP_TIER_REASON_TITLE, "");				
		hmParams.put(AOPConstants.AOP_TIER_LINE_BREAK, "");
		hmParams.put(AOPConstants.AOP_TIER_SEP, "");
	}

	/**
	 * @param hmParams
	 * @param securityContext
	 * @throws DataServiceException
	 */
	private void createSoReceipt(Map<String, Object> hmParams, SecurityContext securityContext,ServiceOrder serviceOrder) throws SLBusinessServiceException {
		SOReceiptsVO soReceiptsVO = new SOReceiptsVO();
		soReceiptsVO.setLedgerEntityID(serviceOrder.getBuyer().getBuyerId());
		soReceiptsVO.setSoID((String)hmParams.get(AOPConstants.AOP_SO_ID));
		soReceiptsVO.setEntityTypeID(OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER);
		soReceiptsVO.setLedgerEntryRuleID(OrderConstants.LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE);
		soReceiptsVO = transactionDao.getSOTransactionReceiptInfo(soReceiptsVO);
		if(soReceiptsVO != null) {
			hmParams.put(AOPConstants.AOP_LEDGER_TRANID_POST, soReceiptsVO.getTransactionID().toString());
			hmParams.put(AOPConstants.AOP_TRANS_AMOUNT_POST, soReceiptsVO.getTransactionAmount());
		} else {
			hmParams.put(AOPConstants.AOP_LEDGER_TRANID_POST, "No Transaction Id");
			hmParams.put(AOPConstants.AOP_TRANS_AMOUNT_POST, "NA");
		}
		soReceiptsVO = new SOReceiptsVO();
		soReceiptsVO.setLedgerEntityID(serviceOrder.getBuyer().getBuyerId());
		soReceiptsVO.setSoID((String)hmParams.get(AOPConstants.AOP_SO_ID));
		soReceiptsVO.setEntityTypeID(OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER);
		soReceiptsVO.setLedgerEntryRuleID(OrderConstants.LEDGER_ENTRY_RULE_ID_SO_SPEND_LIMIT);				
		soReceiptsVO = transactionDao.getSOTransactionReceiptInfo(soReceiptsVO);
		
		if(soReceiptsVO != null) {
			Double transAmount = walletBO.getCurrentSpendingLimit(soReceiptsVO.getSoID());
			hmParams.put(AOPConstants.AOP_LEDGER_TRANID_RES, soReceiptsVO.getTransactionID().toString());
			hmParams.put(AOPConstants.AOP_TRANS_AMOUNT_RES, transAmount);
		} else {
			hmParams.put(AOPConstants.AOP_LEDGER_TRANID_RES, "No Transaction Id");
			hmParams.put(AOPConstants.AOP_TRANS_AMOUNT_RES, "NA");
		}
	}
	


	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	private void processReRoute(Map<String, Object> hmParams, SecurityContext securityContext) throws DBException {
		Contact contact;
		int buyerID = securityContext.getCompanyId();
		SOReceiptsVO soReceiptsVO = new SOReceiptsVO();
		soReceiptsVO.setLedgerEntityID(buyerID);
		contact = contactDao.getByBuyerId(buyerID);
		String userMail = "SYSTEM";
		if (contact != null && StringUtils.isNotBlank(contact.getEmail())) {
			userMail = contact.getEmail();
		}
		
		if (logger.isDebugEnabled())
		  logger.debug("MPAOPAdvice-afterReturning-BuyerID-" + buyerID + "-UserMail-" + userMail);
		
		hmParams.put(AOPConstants.AOP_USER_EMAIL, userMail);
		soReceiptsVO.setSoID((String)hmParams.get(AOPConstants.AOP_SO_ID));
		soReceiptsVO.setEntityTypeID(OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER);
		soReceiptsVO = transactionDao.getRepostSOTransactionReceiptInfo(soReceiptsVO);
		if(soReceiptsVO != null) {
				hmParams.put(AOPConstants.AOP_LEDGER_TRANID_RES, soReceiptsVO.getTransactionID().toString());
			if (soReceiptsVO.getLedgerEntryRuleID().equals(OrderConstants.LEDGER_ENTRY_RULE_ID_SO_REROUTE_FEE))
				hmParams.put(AOPConstants.AOP_TRANS_AMOUNT_RES, soReceiptsVO.getTransactionAmount() + " more");
			else
				hmParams.put(AOPConstants.AOP_TRANS_AMOUNT_RES, soReceiptsVO.getTransactionAmount() + " less");
		} else {
			hmParams.put(AOPConstants.AOP_LEDGER_TRANID_RES, "No Transaction Id");
			hmParams.put(AOPConstants.AOP_TRANS_AMOUNT_RES, "NA");
		}
		//Route and Re-Route uses the same log template file. So blank out the Tire related parameters
		populateEmtyTierOrderHist(hmParams);
	}

	/**
	 * @param hmParams
	 * @param contactId
	 * @param contact1
	 * @throws DBException
	 */
	private void populateContact(Map<String, Object> hmParams,
			Integer contactId, Contact contact1) throws DBException {
		Contact contact;
		contact = new Contact();
		contact.setContactId(contactId);
		contact = contactDao.query(contact);
		hmParams.put(AOPConstants.AOP_CONTACT,contact);
		hmParams.put(AOPConstants.AOP_FIRSTNAME, contact.getFirstName());
		hmParams.put(AOPConstants.AOP_LASTNAME, contact.getLastName());
		hmParams.put(AOPConstants.AOP_USERNAME,contact1.getFirstName()+" "+contact1.getLastName());
		String userMail=contact1.getEmail();
		hmParams.put(AOPConstants.AOP_USER_EMAIL,userMail);
	}

	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
	public IOrderGroupDao getOrderGroupDAO() {
		return orderGroupDAO;
	}
	public void setOrderGroupDAO(IOrderGroupDao orderGroupDAO) {
		this.orderGroupDAO = orderGroupDAO;
	}

	public PromoDAO getPromoDAO() {
		return promoDAO;
	}

	public void setPromoDAO(PromoDAO promoDAO) {
		this.promoDAO = promoDAO;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public BuyerDao getBuyerDao() {
		return buyerDao;
	}

	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}


	/**
	 * @return the contactDao
	 */
	public IContactDao getContactDao() {
		return contactDao;
	}

	/**
	 * @param contactDao the contactDao to set
	 */
	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}

	public IBuyerUserProfileDao getBuyerUserProfileDao() {
		return buyerUserProfileDao;
	}

	public void setBuyerUserProfileDao(IBuyerUserProfileDao buyerUserProfileDao) {
		this.buyerUserProfileDao = buyerUserProfileDao;
	}
	/**
	 * @return the transactionDao
	 */
	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	/**
	 * @param transactionDao the transactionDao to set
	 */
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	/**
	 * @param spnetworkBO the spnetworkBO to set
	 */
	public void setSpnetworkBO(ISPNetworkBO spnetworkBO) {
		this.spnetworkBO = spnetworkBO;
	}

	/**
	 * @param soTierRoutingHistoryBo the soTierRoutingHistoryBo to set
	 */
	public void setSoTierRoutingHistoryBo(
			SoTierRoutingHistoryBO soTierRoutingHistoryBo) {
		this.soTierRoutingHistoryBo = soTierRoutingHistoryBo;
	}

	
}
