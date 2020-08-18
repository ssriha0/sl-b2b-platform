package com.newco.marketplace.business.businessImpl.so.order;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.api.mobile.beans.vo.WarrantyHomeReasonInfoVO;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.alert.EmailBean;
import com.newco.marketplace.business.businessImpl.so.pdf.PDFGenerator;
import com.newco.marketplace.business.businessImpl.so.pdf.PrintPaperWorkGenerator;
import com.newco.marketplace.business.businessImpl.vibePostAPI.PushNotificationAlertTask;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderCloseBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpdateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo2;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailsResponseVO;
import com.newco.marketplace.dto.vo.provider.ProviderDetailWithSOAccepted;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ClosedOrdersRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ClosedServiceOrderVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.CounterOfferReasonsVO;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.IncreaseSpendLimitRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.MarketMakerProviderResponse;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PendingCancelHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ReasonLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.ResponseSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProviderResponseVO;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderPendingCancelHistory;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryListVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryVO;
import com.newco.marketplace.dto.vo.so.order.SoCancelVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.skillTree.ProviderIdsVO;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.gis.InsuffcientLocationException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.document.IDocumentDao;
import com.newco.marketplace.persistence.iDao.ledger.ICreditCardDao;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IWarrantyDao;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.newco.marketplace.persistence.iDao.survey.ExtendedSurveyDAO;
import com.newco.marketplace.persistence.iDao.survey.SurveyDAO;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.Cryptography128;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.util.gis.GISUtil;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.validator.order.SOIncreaseSpendLimitValidator;
import com.newco.marketplace.validator.order.SORejectOrderValidator;
import com.newco.marketplace.validator.order.ServiceOrderValidator;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.LastClosedOrderVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.WarrantyVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderIvrDetailsVO;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.ValidationRulesVO;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;


public class ServiceOrderBO extends BaseOrderBO implements IServiceOrderBO {

	private static final Logger logger = Logger.getLogger(ServiceOrderBO.class);

	//RandomGUID randomNo = RandomGUIDThreadSafe.getInstance();

	private PromoBO promoBO;
	private ILedgerFacilityBO transBo;
	private SurveyDAO surveyDao;
	private ExtendedSurveyDAO extendedSurveyDAO;
	private IDocumentDao documentDao;
	private IDocumentBO documentBO;
	private IServiceOrderUpdateBO serviceOrderUpdateBO;
	private ILookupBO lookupBO;
	private IFinanceManagerBO financeManagerBO;
	private IOrderGroupDao orderGroupDao;
	private ProBuyerBOFactory proBuyerBOFactory;
	private IServiceOrderCloseBO soCloseBO;
	private IServiceOrderUpsellBO serviceOrderUpsellBO;
	private IWalletBO walletBO;
	private WalletRequestBuilder walletReqBuilder = new WalletRequestBuilder();
	private ProviderSearchDao providerSearchDao;
	private IProviderInfoPagesBO providerInfoPagesBO;
	private ICreditCardDao creditCardDao; 
	private Cryptography cryptography;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private Cryptography128 cryptography128;
	private IWarrantyDao warrantyDao;
	private ILookupDAO lookupDAO;
	private PushNotificationAlertTask pushNotificationAlertTask;
	
	private IRelayServiceNotification relayNotificationService;
	
	public ServiceOrderBO() {
	}

	public ProcessResponse processAcceptServiceOrder(String serviceOrderId, Integer resourceId, Integer companyId, Integer termAndCond, boolean validate, boolean isRescheduleRequest, boolean isIndividualOrder, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		processResp.setCode(VALID_RC);
		ServiceOrder soObj = new ServiceOrder();
		List<String> errorMessages = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		boolean acceptable = false;

		try {
			if (this.isSOInEditMode(serviceOrderId)) {
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessage(ORDER_BEING_EDITED);
			} else {
				soObj = this.getServiceOrderDao().getServiceOrder(serviceOrderId);
				acceptable = determineAcceptability(isIndividualOrder, processResp, soObj);
				ArrayList<HashMap> routedAL = this.getServiceOrderDao().queryRoutedSO(serviceOrderId);
				boolean isRoutedResource = this.isRoutedResource(resourceId, routedAL);
				if (!isRoutedResource) {
					logger.debug("ResourceID(" + resourceId + ") does not match Routerd resourceID for ServiceOrder {" + serviceOrderId + "}.");
					processResp.setCode(USER_ERROR_RC);
					errorMessages.add(new String("ResourceID(" + resourceId + ") does not match Routerd resourceID for ServiceOrder {" + serviceOrderId + "}."));
					processResp.setMessages(errorMessages);
				} else if (isRoutedResource && acceptable) {
					VendorResource vendorResource = new VendorResource();
					vendorResource.setResourceId(resourceId);
					soObj.setAcceptedResource(vendorResource);
					soObj.setAcceptedDate(new Timestamp(calendar.getTimeInMillis()));
					soObj.setAcceptedVendorId(companyId);
					soObj.setWfStateId(OrderConstants.ACCEPTED_STATUS);
					// If sub-status is not null, update the sub-status
					Integer derivedSubStatus = deriveSubStatus(soObj, isRescheduleRequest);
					updateDerviedSubStatus(soObj.getSoId(), derivedSubStatus, securityContext);
					soObj.setSoTermsCondId(termAndCond);
					soObj.setProviderSOTermsCondInd(1);

					Timestamp ts = new Timestamp(calendar.getTimeInMillis());
					soObj.setProviderTermsCondDate(ts.toString());
					int updateQueryResultCount = 0;
				    	
					if (soObj.getFundingTypeId() == CommonConstants.CONSUMER_FUNDING_TYPE){
						fundConsumerFundedOrder(processResp, soObj, errorMessages, false);	
					}
					
					if (validate == true && processResp.getCode() != USER_ERROR_RC) {
						updateQueryResultCount = this.getServiceOrderDao().updateAccepted(soObj);
						if (updateQueryResultCount > 0) {
							processResp.setCode(VALID_RC);
							errorMessages.add(VALID_MSG);
						} else {
							processResp.setCode(USER_ERROR_RC);
							errorMessages.add("This service order is no longer available to accept.");
						}
					} else {
						processResp.setCode(VALID_RC);
						errorMessages.add(VALID_MSG);
					}
					processResp.setMessages(errorMessages);
				}
			}
		} catch (DataServiceException dse) {
			logger.debug("Exception thrown querying SO", dse);
			processResp.setCode(SYSTEM_ERROR_RC);
			errorMessages.add("Datastore error");
			processResp.setMessages(errorMessages);
		} catch (BusinessServiceException bse) {
			logger.debug("Exception thrown accepting SO", bse);
			processResp.setCode(SYSTEM_ERROR_RC);
			errorMessages.add("BusinessService error");
			processResp.setMessages(errorMessages);
		}

		return processResp;

	}

	/**
	 * Description: Do the funding for a consumer funded order.  If authorization of the card fails
	 * send an email to the buyer and provider and expire the service order.
	 * 
	 * @param processResp
	 * @param so
	 * @param errorMessages
	 * @throws BusinessServiceException
	 * @throws DataServiceException 
	 */
	private void fundConsumerFundedOrder(ProcessResponse processResp,
			ServiceOrder so, List<String> errorMessages, boolean conditional)
			throws BusinessServiceException, DataServiceException {
		// prepare variables for use such as MarketPlaceTransactionVO
		Integer buyerId = so.getBuyer().getBuyerId();
		String acctId = so.getAccountId();
		MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(so, buyerId);
		marketVO.setUserName(so.getBuyer().getUserName());
		marketVO.setBuyerID(buyerId);
		marketVO.setAutoACHInd("false");
		marketVO.setAccountId(new Long(acctId));
		SLAccountVO account = new SLAccountVO();
		if ( "0".equals(acctId)){
			account = financeManagerBO.getActiveAccountDetails(buyerId);
			if(account.getAccount_id()!=null){
			acctId = account.getAccount_id().toString();
			so.setAccountId(acctId);
			marketVO.setAccountId(new Long(acctId));
			}
		}
		//Determine if this a post or an edit by looking for previous post transactions for this order.
		int posted = lookupBO.getPostedStatus(so.getSoId());

		FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(so, buyerId);

		if (conditional){
			Double soProjectBalance = walletBO.getCurrentSpendingLimit(so.getSoId());
			Double fundingAmountCC = so.getFundingAmountCC();
			if (fundingAmountCC == null){
				fundingAmountCC = 0.0;
			}
			double amtToFund = MoneyUtil.subtract(
				fundVO.getAvailableBalance(),
				(fundingAmountCC + so.getSpendLimitLabor() + so.getSpendLimitParts() + so.getUpsellAmt())); 
			if (amtToFund < 0){
				fundVO.setEnoughFunds(false);
				fundVO.setAmtToFund(Math.abs(amtToFund));
			}
		}

		if (fundVO.isEnoughFunds()) {
			doPostOrIncrease(so, marketVO, posted, fundVO);
		}else{
			//Check for credit card for this order.
			if (acctId != null){
				//authorize the card for the amount and do deposit
				WalletResponseVO response = authAndDeposit(so, buyerId, acctId, fundVO);
				response.getCreditCard().setEntityId(buyerId);
				//check the response to see if authorized and completed
				if (response.getCreditCard().isAuthorized()){
					financeManagerBO.sendCCDepositConfirmationEmail((CreditCardVO)response.getCreditCard(), fundVO.getAmtToFund(), response.getTransactionId().intValue(),SIMPLE_BUYER);
					doPostOrIncrease(so, marketVO, posted, fundVO);
				}else{
					expireSOAndSendEmails(processResp, errorMessages, so);
				}
			}else{
				expireSOAndSendEmails(processResp, errorMessages, so);
			}
		}
	}

	/**
	 * Description: Determines if a post operation needs to occur.  Only happens if posted value is 0.
	 * 
	 * @param so
	 * @param marketVO
	 * @param posted
	 * @param fundVO
	 * @throws BusinessServiceException
	 * @throws DataServiceException 
	 */
	private void doPostOrIncrease(ServiceOrder so, MarketPlaceTransactionVO marketVO, int posted, FundingVO fundVO) throws BusinessServiceException, DataServiceException {
		if (posted > 0 ){ //the order has already been posted and this is an increase spend limit
			// Move funds to buyers V2 (project balance) from their V1 via an increase an spend limit.
			transBo.increaseSpendLimit(marketVO, fundVO.getAmtToFund(), so.getSoId(), fundVO.getAmtToFund());
		}else{
			// This is indeed a post.
			transBo.postConsumerOrder(marketVO, fundVO);
		}
	}


	/**
	 * Description: Do the funding for a consumer funded order.  If authorization of the card fails
	 * send an email to the buyer and provider and expire the service order.
	 * 
	 * @param processResp
	 * @param so
	 * @param errorMessages
	 * @throws BusinessServiceException
	 * @throws DataServiceException 
	 */
	public void fundConsumerFundedOrder(ProcessResponse processResp,
			String soId, List<String> errorMessages)
			throws BusinessServiceException, DataServiceException {
		ServiceOrder so = this.getServiceOrderDao().getServiceOrder(soId);
		// prepare variables for use such as MarketPlaceTransactionVO
		Integer buyerId = so.getBuyer().getBuyerId();
		String acctId = so.getAccountId();
		MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(so, buyerId);
		marketVO.setUserName(so.getBuyer().getUserName());
		marketVO.setBuyerID(buyerId);
		marketVO.setAutoACHInd("false");
		if(acctId!=null){
		marketVO.setAccountId(new Long(acctId));
		}
		//Determine if this a post or an edit by looking for previous post transactions for this order.
		int posted = lookupBO.getPostedStatus(soId);

		//determine if buyer has enough funds in his available balance
		FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(so, buyerId);
		if (fundVO.isEnoughFunds()) {
			if (posted > 0 ){ //the order has already been posted and this is an increase spend limit
			// Move funds to buyers V2 (project balance) from their V1 via an increase an spend limit.
			transBo.increaseSpendLimit(marketVO, fundVO.getAmtToFund(), so.getSoId(), fundVO.getAmtToFund());
		}else{
				// This is indeed a post.
				transBo.routeServiceOrderLedgerAction(marketVO, fundVO);
			}
		}else{
			//Check for credit card for this order.
			if (acctId != null){
				//authorize the card for the amount and do deposit
				WalletResponseVO response = authAndDeposit(so, buyerId, acctId, fundVO);
				//check the response to see if authorized and completed
				if (response.getCreditCard().isAuthorized()){
					if (posted > 0 ){ //the order has already been posted and this is an increase spend limit
						// Move funds to buyers V2 (project balance) from their V1 via an increase an spend limit.
						transBo.increaseSpendLimit(marketVO, fundVO.getAmtToFund(), so.getSoId(), fundVO.getAmtToFund());
					}else{
					// Move funds to buyers V2 (project balance) via an increase an spend limit.
						transBo.routeServiceOrderLedgerAction(marketVO, fundVO);
					}
				}else{
					expireSOAndSendEmails(processResp, errorMessages, so);
				}
			}else{
				expireSOAndSendEmails(processResp, errorMessages, so);
			}
		}
	}

	/**
	 * @param processResp
	 * @param errorMessages
	 * @param so
	 * @throws DataServiceException
	 */
	private void expireSOAndSendEmails(ProcessResponse processResp,	List<String> errorMessages, ServiceOrder so) throws DataServiceException {
		//expire so due to card not being authorized.
		so.setWfStateId(EXPIRED_STATUS);
		getServiceOrderDao().updateSOStatus(so);
		//send emails to buyer and provider
		financeManagerBO.sendFailedToAcceptSOEmails(so);
		//set response for proper error
		processResp.setCode(USER_ERROR_RC);
		errorMessages.add("This order is no longer available for acceptance. The buyer may be reposting this order in the future.");
	}

	/**
	 * @param so
	 * @param buyerId
	 * @param acctId
	 * @param fundVO
	 * @return <code>WalletResponseVO</code>
	 * @throws DataServiceException
	 * @throws SLBusinessServiceException
	 */
	private WalletResponseVO authAndDeposit(ServiceOrder so,
			Integer buyerId, String acctId, FundingVO fundVO)
			throws DataServiceException, SLBusinessServiceException {
		CreditCardVO cc = new CreditCardVO();
		cc.setCardId(new Long(acctId));
		cc = creditCardDao.getCardDetailsByAccountId(cc);
		cc.setTransactionAmount(fundVO.getAmtToFund());
		WalletVO request = walletReqBuilder.depositBuyerFunds(cc.getUserName(), new Long(acctId), new Long(buyerId), 
				cc.getBillingState(), null, null, so.getSoId(), null, fundVO.getAmtToFund(), 
				new Long(CommonConstants.CONSUMER_FUNDING_TYPE), false);

		//Decrypt card number for use in auth
		CryptographyVO cryptographyVO = new CryptographyVO();
		//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO.setInput(cc.getEncCardNo());
		
		//Commenting the code for SL-18789
		//cryptographyVO = cryptography.decryptKey(cryptographyVO);
		cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
		cryptographyVO = cryptography128.decryptKey128Bit(cryptographyVO);
		
		cc.setCardNo(cryptographyVO.getResponse());
		request.setCreditCard(SLCreditCardVO.adapt(cc));
		request.setFundingTypeId(new Long(so.getFundingTypeId()));
		WalletResponseVO response = walletBO.depositBuyerFundsWithCreditCard(request);
		return response;
	}
	
	
	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	/**
	 * Description: Determine the acceptability of a service order based on various criteria.
	 * @param isIndividualOrder
	 * @param processResp
	 * @param soObj
	 * @return <code>boolean</code>
	 */
	public boolean determineAcceptability(boolean isIndividualOrder, ProcessResponse processResp, ServiceOrder soObj) {
		boolean acceptable;
		if (isIndividualOrder && StringUtils.isNotBlank(soObj.getGroupId())) {
			processResp.setCode(USER_ERROR_RC);
			processResp.setMessage(ORDER_HAS_GROUPED_NOW);
			acceptable = false;
		} else if (soObj.getWfStateId().equals(OrderConstants.ROUTED_STATUS)) {
			acceptable = true;
		} else if (soObj.getWfStateId().equals(OrderConstants.ACCEPTED_STATUS)) {
				processResp.setCode(USER_ERROR_RC);
			processResp.setMessage(ORDER_ACCEPTED_BY_ANOTHER_PROVIDER);
				acceptable = false;

		} else if (soObj.getWfStateId().equals(OrderConstants.CANCELLED_STATUS) || soObj.getWfStateId().equals(OrderConstants.VOIDED_STATUS)) {
			processResp.setCode(USER_ERROR_RC);
			processResp.setMessage(ORDER_IN_CANCELLED_STATUS);
			acceptable = false;
		} else {
			processResp.setCode(USER_ERROR_RC);
			processResp.setMessage("The service order cannot be accepted in this state: " + soObj.getWfStateId());
			acceptable = false;
		}
		return acceptable;
	}
	/**
	 * Description: Determine the acceptability of a service order based on various criteria.
	 * @param isIndividualOrder
	 * @param processResp
	 * @param soObj
	 * @return <code>boolean</code>
	 */
	public boolean determineAcceptabilityForMobile(boolean isIndividualOrder,String groupId,int wfStatus) {
		boolean acceptable;
		if (isIndividualOrder && StringUtils.isNotBlank(groupId)) {
			acceptable = false;
		} else if (wfStatus == OrderConstants.ROUTED_STATUS) {
			acceptable = true;
		} else if (wfStatus == OrderConstants.ACCEPTED_STATUS) {
				acceptable = false;
        } else if (wfStatus == OrderConstants.CANCELLED_STATUS || wfStatus == OrderConstants.VOIDED_STATUS) {
			acceptable = false;
		} else {
			acceptable = false;
		}
		return acceptable;
	}
	/**
	 * @param soObj
	 * @param isRescheduleRequest
	 * @param securityContext
	 * @throws BusinessServiceException
	 */
	private void updateDerviedSubStatus(String soId, Integer derivedSubStatus, SecurityContext securityContext) throws BusinessServiceException {
		if (derivedSubStatus != null) {
			// get the bo bean again for AOP to fire
			IServiceOrderBO bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
			bo.updateSOSubStatus(soId, derivedSubStatus, securityContext);
		}
	}

	public void sendallProviderResponseExceptAccepted(String soId, SecurityContext securityContext) {
		// placeholder function for AOP alert for providers
		// who neither accepted nor rejected SO.

	}

	/**
	 * Derives the sub-status for the service order depending on date
	 * calculation method and next day task.
	 * 
	 * @param Service
	 *            Order Object
	 * @param isRescheduleRequest
	 * @return the sub-status ID
	 */
	private Integer deriveSubStatus(ServiceOrder soObj, boolean isRescheduleReqeust) {
		String dateCalculationMethod = getDateCalculationMethod(soObj);
		if (StringUtils.isNotBlank(dateCalculationMethod)) {
			if (isRescheduleReqeust) {
				return OrderConstants.RESCHEDULED_CONFIRMED_SUBSTATUS;
			} else {
				for (ServiceOrderTask task : soObj.getTasks()) {
					if (dateCalculationMethod.equals(OrderConstants.KEY_NEXT_DATE)) {
						return OrderConstants.RESCHEDULED_CONFIRMED_SUBSTATUS;
					}
				}
				if (dateCalculationMethod.equals(OrderConstants.KEY_PROMISED_DATE)) {
					return OrderConstants.RESCHEDULED_CONFIRMED_SUBSTATUS;
				} else
					return OrderConstants.SCHEDULING_NEEDED_SUBSTATUS;
			}// End of If-Else
		}// End of If
		return null;
	}

	/**
	 * This method get the date calculation method that was set in ESB layer as
	 * either <code>OrderConstants.KEY_DELIVERY_DATE</code> or
	 * <code>OrderConstants.KEY_PROMISED_DATE</code>. The method returns null if
	 * the date calculation method is not set.
	 * 
	 * @param Service
	 *            Order Object
	 * @return <code>OrderConstants.KEY_DELIVERY_DATE</code>/
	 *         <code>OrderConstants.KEY_PROMISED_DATE</code>.
	 */
	public String getDateCalculationMethod(ServiceOrder soObj) {
		String dateCalculationMethod = null;
		if (soObj.getCustomRefs() != null && soObj.getCustomRefs().size() > 0) {
			for (ServiceOrderCustomRefVO vo : soObj.getCustomRefs()) {
				if (vo.getRefType().equals(OrderConstants.CUSTOM_REF_DATE_CALCULATION_METHOD)) {
					if (vo.getRefValue().equals(OrderConstants.KEY_DELIVERY_DATE)) {
						dateCalculationMethod = OrderConstants.KEY_DELIVERY_DATE;
					}
					if (vo.getRefValue().equals(OrderConstants.KEY_PROMISED_DATE)) {
						dateCalculationMethod = OrderConstants.KEY_PROMISED_DATE;
					}
					if (vo.getRefValue().equals(OrderConstants.KEY_NEXT_DATE)) {
						dateCalculationMethod = OrderConstants.KEY_NEXT_DATE;
					}
					if (vo.getRefValue().equals(OrderConstants.KEY_CURRENT_DATE)) {
						dateCalculationMethod = OrderConstants.KEY_CURRENT_DATE;
					}

				}// End of If
			}// End of For
		}// End of If
		return dateCalculationMethod;
	}

	/**
	 * Renamed from processCreatePreDraftSO to doProcessCreatePreDraftSO to
	 * avoid AOP call. All method starting with process will be intercepted by
	 * AOP.
	 */
	public ProcessResponse doProcessCreatePreDraftSO(ServiceOrder so, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();

		logger.info("INSIDE ServiceOrderBO ----> processCreatePreDraftSO()");
		try {
			// basic request validation
			String orderNo = generateOrderNo(so.getBuyer().getBuyerId());
			if (orderNo == null) {
				new Throwable("Service Order Id is null");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("createDraft:: generated service order " + orderNo + " for buyerResourceId: " + so.getBuyerResourceId());
			}
			processResp.setObj(orderNo);

			so.setSoId(orderNo);

			// When the order is posted first time, the buyer bid is saved in SO
			// header for Business Object reporting purposes
			double initialPrice = MoneyUtil.add(so.getSpendLimitLabor(), so.getSpendLimitParts());
			so.setInitialPrice(initialPrice);
			
			populateSORelatedIds(so);
			so = populateBuyerAttributes(so);

			// RG -- part of data mining
			so = populateSOPrice(so);

			try {
				// beginWork();
				getServiceOrderDao().insert(so);
				if (logger.isDebugEnabled())
					logger.debug("createDraft:: so: " + orderNo + " has been persisted...");

				// setup a valid process response
				processResp.setCode(VALID_RC);
				processResp.setMessage(VALID_MSG);
				processResp.setObj(so.getSoId());

				// associate Buyer Documents with SO
				int buyerId = securityContext.getCompanyId();

				// retrieve and associate buyer logo with this SO
				if(null != so.getLogoDocumentId() && so.getLogoDocumentId() >0){
					updateSoHdrLogoDocument(so.getSoId(), so.getLogoDocumentId());
				}else{
					retrieveLogoForBuyer(buyerId, orderNo);
				}
				if (logger.isDebugEnabled())
					logger.debug("createDraft: service order: " + so.getSoId() + " has been created");

			} catch (Throwable t1) {
				logger.error("Creation of service order failed, reason: " + t1.getMessage());
				logger.error("Rolling back insert of service order: " + so.getSoId());
				try {
					rollbackWork();
					logger.error("service order: " + so.getSoId() + " was successfully rolled back");
				} catch (Throwable t2) {
					logger.error("service order: " + so.getSoId() + " failed to roll back");

				}
				throw t1;
			}
		} catch (Throwable t3) {
			logger.error("createDraft:: error:  " + t3.getMessage(), t3);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t3.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		return processResp;
	}

	/**
	 * 
	 * Object level synchronized does not work in this case.
	 * 
	 * @param so
	 * @param subStatusFlag
	 * @return
	 */
	private ProcessResponse createDraftSO(ServiceOrder so, Integer subStatusFlag) {

		ProcessResponse processResp = new ProcessResponse();

		try {
			// generate unique service order number
			String orderNo = generateOrderNo(so.getBuyer().getBuyerId());
			if (logger.isDebugEnabled())
				logger.debug("createDraft:: generated service order " + orderNo + " for buyerResourceId: " + so.getBuyerResourceId());
			processResp.setObj(orderNo);

			so.setSoId(orderNo);
			so.setWfStateId(new Integer(DRAFT_STATUS));
			// When the order is posted first time, the buyer bid is saved in SO
			// header for Business Object reporting purposes
			double initialPrice = MoneyUtil.add(so.getSpendLimitLabor(), so.getSpendLimitParts());
			so.setInitialPrice(initialPrice);

			if (subStatusFlag > 0) {
				so.setWfSubStatusId(subStatusFlag);
			}
			/* Set the Attention-required status based on the flag - ends */

			populateSORelatedIds(so);
			populateBuyerAttributes(so);

			// RG -- part of data mining
			so = populateSOPrice(so);

			// GJ - Orders should be passed in GMT
			// convert date/time to GMT
			// GivenTimeZoneToGMT(so);

			getServiceOrderDao().insertSOForWS(so);
			if (logger.isDebugEnabled())
				logger.debug("createDraft:: so: " + orderNo + " has been persisted...");

			// setup a valid process response
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			processResp.setObj(so.getSoId());
			if (logger.isDebugEnabled())
				logger.debug("createDraft: service order: " + so.getSoId() + " has been created");
		} catch (Throwable t3) {
			logger.error("createDraft:: error:  " + t3.getMessage(), t3);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t3.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		return processResp;
	}

	// The two methods (processCreateDraftSO and processIncidentReopen) have
	// same method body refactored to private method createDraftSOForWS
	// because both methods require different AOP advice; do not change -
	// AUTHORITY
	public ProcessResponse processCreateDraftSO(ServiceOrder so, Integer subStatus, SecurityContext securityContext) throws BusinessServiceException {
		return createDraftSOForWS(so, subStatus, securityContext, false);
	}

	// This does the same as above, but does not trigger aopAdvice. This is used by esb.
	public ProcessResponse noAdviceProcessCreateDraftSO(ServiceOrder so, Integer subStatus, SecurityContext securityContext) throws BusinessServiceException {
		return createDraftSOForWS(so, subStatus, securityContext, false);
	}

	/**
	 * This method will be used only by OrderDispatchRequest class and it uses
	 * cache while loading buyer details. It is required for performance
	 * improvement. In case of OrderDispatchRequest buyer Id is always 1000. And
	 * for this id generally buyer details do not change so it is worth using
	 * cache. - shekhar
	 * 
	 * @param so
	 * @param subStatus
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse processCreateDraftSOBatch(ServiceOrder so, Integer subStatus, SecurityContext securityContext) throws BusinessServiceException {
		return createDraftSOForWS(so, subStatus, securityContext, true);
	}

	// The two methods (processCreateDraftSO and processIncidentReopen) have
	// same method body refactored to private method createDraftSOForWS
	// because both methods require different AOP advice; do not change -
	// AUTHORITY
	public ProcessResponse processIncidentReopen(ServiceOrder so, Integer subStatus, SecurityContext securityContext) throws BusinessServiceException {
		return createDraftSOForWS(so, subStatus, securityContext, false);
	}

	// The two methods (processCreateDraftSO and processIncidentReopen) have
	// same method body refactored to other private method
	// because both methods require different AOP advice; do not change -
	// AUTHORITY
	private ProcessResponse createDraftSOForWS(ServiceOrder so, Integer subStatus, SecurityContext securityContext, boolean useCache) throws BusinessServiceException {
		ProcessResponse processResponse = createDraftSO(so, subStatus);
		if (processResponse.isError()) {
			String message = "Unexpected error occured in ServiceOrderBO.processCreateDraftSO called from OMS due to error:" + processResponse.getMessages().get(0);
			logger.error(message);
			throw new BusinessServiceException(message);
		}
		return processResponse;
	}

	public ProcessResponse processCreateDraftSO(ServiceOrder so, SecurityContext securityContext) {
		return createDraftSO(so, new Integer(0));
	}

	/* Added for BuyerUploadTool */
	public ProcessResponse processCreateDraftSO(ServiceOrder so) {
		return createDraftSO(so, new Integer(0));
	}

	public List<ServiceOrderCustomRefVO> getCustomReferenceFields(String soId) throws BusinessServiceException {
		List<ServiceOrderCustomRefVO> customRefs = null;
		try {
			customRefs = getServiceOrderDao().getCustomReferenceFields(soId);
		} catch (DataServiceException dsEx) {
			String strMessage = "Unexpected error while retrieving custom ref fields for so:" + soId;
			logger.error(strMessage, dsEx);
			throw new BusinessServiceException(strMessage, dsEx);
		}
		return customRefs;
	}
	
	public InitialPriceDetailsVO getInitialPrice(String soId) throws BusinessServiceException{
		InitialPriceDetailsVO initialPrice=new InitialPriceDetailsVO();
		try {
			initialPrice = getServiceOrderDao().getInitialPrice(soId);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving Initial price for so:"+soId;
			logger.error(strMessage, ex);
			throw new BusinessServiceException(strMessage, ex);
		}
		return initialPrice;
	}

	private MarketPlaceTransactionVO getMarketPlaceTransactionVO(ServiceOrder serviceOrder, Integer buyerId) {
		MarketPlaceTransactionVO marketVO = new MarketPlaceTransactionVO();
		marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_POST_SO);
		marketVO.setBuyerID(buyerId);

		marketVO.setUserTypeID(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
		marketVO.setLedgerEntryRuleId(LedgerConstants.RULE_ID_APPLY_POSTING_FEE_VIRTUAL);
		marketVO.setServiceOrder(serviceOrder);
		return marketVO;

	}

	private ProcessResponse processCommonRouteSO(Integer buyerId, ServiceOrder serviceOrder, SecurityContext securityContext) {

		ProcessResponse processResp = new ProcessResponse();
		String serviceOrderID = serviceOrder.getSoId();
		try {
			ValidatorResponse validatorResp = new ServiceOrderValidator().validate(serviceOrderID, buyerId);

			if (validatorResp.isError()) {
				if (logger.isDebugEnabled()) {
					logger.error("Route service order failed validation, reason: " + validatorResp.getMessages());
				}
				processResp.setObj(serviceOrderID);
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());

				return processResp;
			}

			if (serviceOrder == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND, serviceOrderID);
			}

			// Check if the buyer is authorized for the service order
			if (!isAuthorizedBuyer(buyerId, serviceOrder)) {
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED, serviceOrderID);
			}

			// check for status draft or routed
			if (!checkStateForRoute(serviceOrder.getSoId())) {
				return setErrorMsg(processResp, SERVICE_ORDER_MUST_BE_IN_DRAFT_STATE, serviceOrderID);
			}

			AjaxCacheVO avo = new AjaxCacheVO();
			avo.setCompanyId(buyerId);
			avo.setRoleType("BUYER");
			// Check if the buyer has enough funds

			MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);

			// check max spend limit first
			if (buyerOverMaxLimit(serviceOrder.getAccessFee(), serviceOrder.getSpendLimitLabor(), serviceOrder.getSpendLimitParts(), securityContext)) {
				return setErrorMsg(processResp, BUYER_OVER_MAX_SPEND_LIMIT, serviceOrderID);
			} else if (securityContext != null) {
				if (securityContext.isIncreaseSpendLimitInd())
					return setErrorMsg(processResp, BUYER_OVER_MAX_SPEND_LIMIT, serviceOrderID);
			}

			FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
			// check available funds next
			if (!fundVO.isEnoughFunds()) {
				if (!securityContext.isAutoACH() && securityContext.getRoleId().intValue() != SIMPLE_BUYER_ROLEID) {
					return setErrorMsg(processResp, BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS, serviceOrderID);
				}
			}

			if (serviceOrder.getStatus().equalsIgnoreCase("Draft")) {
				if (serviceOrder.getFundingTypeId().intValue() == LedgerConstants.FUNDING_TYPE_NON_FUNDED) {
					Buyer buyer = getServiceOrderDao().getBuyerAttributes(buyerId);

					serviceOrder.setFundingTypeId(buyer.getFundingTypeId());
				}

			}
			logger.debug("processCommonRouteSO()-->canRoute=true");
		} catch (Throwable t) {
			logger.error("Re route service order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		return processResp;
	}

	/*
	 * This method is used for first time frontend routing (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #processRouteSO(java.lang.Integer, java.lang.String,
	 * com.newco.marketplace.auth.SecurityContext)
	 */
	public ProcessResponse processRouteSO(Integer buyerId, String serviceOrderID, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		logger.info("processRouteSO()-->START");
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderID);
			// apply so promotions if any
			promoBO.applySOPromotions(serviceOrderID, serviceOrder.getBuyerId().longValue());

			processResp = processCommonRouteSO(buyerId, serviceOrder, securityContext);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			if (!processResp.getCode().equals(USER_ERROR_RC)) {
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
				marketVO.setUserName(securityContext.getUsername());
				marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
				if (securityContext.getRoleId().intValue() == SIMPLE_BUYER_ROLEID)
					marketVO.setAccountId(securityContext.getAccountID());
				else
					marketVO.setAccountId(acct.getAccount_id());
				//*** lock the buyer until current transaction finishes
				lockBuyer(buyerId);
				//***
				// make double entry transaction
				FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
				// check available funds next
				transBo.routeServiceOrderLedgerAction(marketVO, fundVO);

				serviceOrder.setWfStateId(ROUTED_STATUS);
				serviceOrder.setWfSubStatusId(null);
				serviceOrder.setLastChngStateId(serviceOrder.getWfStateId());
				Timestamp ts = new Timestamp(calendar.getTimeInMillis());
				serviceOrder.setLastStatusChange(ts);
				/*
				 * Setting the routed_date only if it is the first time else
				 * having the old value
				 */
				if (serviceOrder.getRoutedDate() == null) {
					serviceOrder.setRoutedDate(ts);
				}
				// Setting initial routed date upon first time routing
				if (serviceOrder.getInitialRoutedDate() == null) {
					serviceOrder.setInitialRoutedDate(ts);
				}
				/* set routed date for all routed providers */
				setRoutedDateTimeForProviders(serviceOrder.getSoId());
				getServiceOrderDao().updateSOStatus(serviceOrder);
				// update the initial posted labor and parts price
				updateInitialPostedSOPrice(serviceOrder);
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_REROUTED);

			}

		} catch (Throwable t) {
			logger.error("Route service order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		logger.info("processRouteSO()-->FINISH");
		return processResp;
	}

	/* insert routed date for all routed providers */
	private void setRoutedDateTimeForProviders(String soId) throws Exception {
		getServiceOrderDao().updateRoutedDateForProviders(soId);

	}

	// Professional Buyer RePost
	public ProcessResponse processReRouteSO(Integer buyerId, String serviceOrderID, boolean skipAlertLogging, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		logger.info("processReRouteSO()-->START");
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderID);
			// apply so promotions if any
			promoBO.applySOPromotions(serviceOrderID, serviceOrder.getBuyerId().longValue());

			processResp = processCommonRouteSO(buyerId, serviceOrder, securityContext);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			if (!processResp.getCode().equals(USER_ERROR_RC)) {
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
				marketVO.setUserName(securityContext.getUsername());
				marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
				marketVO.setAccountId(acct.getAccount_id());
				//*** lock the buyer until current transaction finishes
				lockBuyer(buyerId);
				//***
				// make double entry transaction
				FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
				// check available funds next
				transBo.routeServiceOrderLedgerAction(marketVO, fundVO);

				serviceOrder.setWfStateId(ROUTED_STATUS);
				serviceOrder.setWfSubStatusId(null);
				serviceOrder.setLastChngStateId(serviceOrder.getWfStateId());
				Timestamp ts = new Timestamp(calendar.getTimeInMillis());
				serviceOrder.setLastStatusChange(ts);
				/*
				 * Setting the routed_date only if it is the first time else
				 * having the old value
				 */
				if (serviceOrder.getRoutedDate() == null) {
					serviceOrder.setRoutedDate(ts);
				}
				
				/* set routed date for all routed providers */
				setRoutedDateTimeForProviders(serviceOrder.getSoId());
				getServiceOrderDao().updateSOStatus(serviceOrder);
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_REROUTED);

			}

		} catch (Throwable t) {
			logger.error("Re route service order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		logger.info("processReRouteSO()-->FINISH");
		return processResp;
	}

	/*
	 * This method is used for automatic routing TierId 0 means non-tiered
	 * routing (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #processRouteSO(java.lang.Integer, java.lang.String, java.util.List,
	 * com.newco.marketplace.auth.SecurityContext)
	 */
	public ProcessResponse processRouteSO(Integer buyerId, String serviceOrderID, List<Integer> routedResourcesIds, Integer tierId, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		logger.info("processRouteSO()-->START");
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderID);
			// apply so promotions if any
			promoBO.applySOPromotions(serviceOrderID, serviceOrder.getBuyerId().longValue());

			processResp = processCommonRouteSO(buyerId, serviceOrder, securityContext);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			if (!processResp.getCode().equals(USER_ERROR_RC)) {
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
				marketVO.setUserName(securityContext.getUsername());
				marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
				marketVO.setAccountId(acct.getAccount_id());
				if (null != routedResourcesIds && routedResourcesIds.size() > 0) {
					//*** lock the buyer until current transaction finishes
					lockBuyer(buyerId);
					//***
					FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
					transBo.routeServiceOrderLedgerAction(marketVO, fundVO);
					Timestamp ts = new Timestamp(calendar.getTimeInMillis());
					serviceOrder.setWfStateId(ROUTED_STATUS);
					serviceOrder.setWfSubStatusId(null);
					serviceOrder.setLastChngStateId(serviceOrder.getWfStateId());
					serviceOrder.setLastStatusChange(ts);
					/*
					 * Setting the routed_date only if it is the first time else
					 * having the old value
					 */
					if (serviceOrder.getRoutedDate() == null) {
						serviceOrder.setRoutedDate(ts);
					}
					// Setting initial routed date upon first time routing
					if (serviceOrder.getInitialRoutedDate() == null) {
						serviceOrder.setInitialRoutedDate(ts);
					}
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_ROUTED);
					getServiceOrderDao().updateSOStatus(serviceOrder);
					// update the initial posted labor and parts price
					updateInitialPostedSOPrice(serviceOrder);

					// Insert into so_routed_providers
					List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
					RoutedProvider routedProvider = null;

					for (Integer resourceId : routedResourcesIds) {
						routedProvider = new RoutedProvider();
						routedProvider.setRoutedDate(ts);
						routedProvider.setResourceId(resourceId);
						routedProvider.setSoId(serviceOrderID);
						routedProvider.setSpnId(serviceOrder.getSpnId());
						routedProvider.setTierId(tierId);
						routedProvider.setPriceModel(serviceOrder.getPriceModel());
						routedResources.add(routedProvider);
					}

					getServiceOrderDao().insertRoutedResources(routedResources);
				} else {
					// get bean again so AOP can fire
					IServiceOrderBO bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
					// do not update No PROVIDERS sub status for Tier Routing,
					// it is handled in TRCOntroller
					if (tierId == null) {
						bo.updateSOSubStatus(serviceOrder.getSoId(), OrderConstants.NO_PROVIDER_SUBSTATUS, securityContext);
					}
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_ROUTED);
				}
			}

		} catch (Throwable t) {
			logger.error("route service order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		logger.info("processRouteSO()-->FINISH");
		return processResp;
	}

	// Used for Simple Buyer - CC Funding amount - enough balance check
	public ProcessResponse processRouteSO(Integer buyerId, String serviceOrderID, List<Integer> routedResourcesIds, Double fundingAmountCC, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		logger.info("processRouteSO()-->START");
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderID);
			// apply so promotions if any
			promoBO.applySOPromotions(serviceOrderID, serviceOrder.getBuyerId().longValue());

			if (fundingAmountCC != null) {
				serviceOrder.setFundingAmountCC(fundingAmountCC);
				if (fundingAmountCC != null && fundingAmountCC.doubleValue() > 0.0) {
					serviceOrder.setFundingCCReqd(true);
				} else {
					serviceOrder.setFundingCCReqd(false);
				}
			}

			processResp = processCommonRouteSO(buyerId, serviceOrder, securityContext);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			if (!processResp.getCode().equals(USER_ERROR_RC)) {
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
				marketVO.setFundingTypeId(serviceOrder.getFundingTypeId());
				marketVO.setUserName(securityContext.getUsername());
				marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
				if (securityContext.getRoleId().intValue() == SIMPLE_BUYER_ROLEID)
					marketVO.setAccountId(securityContext.getAccountID());
				else
					marketVO.setAccountId(acct.getAccount_id());
				if (null != routedResourcesIds && routedResourcesIds.size() > 0) {
					//*** lock the buyer until current transaction finishes
					lockBuyer(buyerId);
					//***
					FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
					transBo.routeServiceOrderLedgerAction(marketVO, fundVO);
					Timestamp ts = new Timestamp(calendar.getTimeInMillis());
					serviceOrder.setWfStateId(ROUTED_STATUS);
					serviceOrder.setWfSubStatusId(null);
					serviceOrder.setLastChngStateId(serviceOrder.getWfStateId());
					serviceOrder.setLastStatusChange(ts);
					/*
					 * Setting the routed_date only if it is the first time else
					 * having the old value
					 */
					if (serviceOrder.getRoutedDate() == null) {
						serviceOrder.setRoutedDate(ts);
					}
					// Setting initial routed date upon first time routing
					if (serviceOrder.getInitialRoutedDate() == null) {
						serviceOrder.setInitialRoutedDate(ts);
					}
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_ROUTED);
					getServiceOrderDao().updateSOStatus(serviceOrder);
					// update the initial posted labor and parts price
					updateInitialPostedSOPrice(serviceOrder);

					// Insert into so_routed_providers
					List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
					RoutedProvider routedProvider = null;
					
					for (Integer resourceId : routedResourcesIds) {
						routedProvider = new RoutedProvider();
						routedProvider.setRoutedDate(ts);
						routedProvider.setResourceId(resourceId);
						routedProvider.setSoId(serviceOrderID);
						routedProvider.setPriceModel(serviceOrder.getPriceModel());
						routedResources.add(routedProvider);

					}

					getServiceOrderDao().insertRoutedResources(routedResources);

					// insert into so promo if any for providers

				} else {
					// get bean again so AOP can fire
					IServiceOrderBO bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
					bo.updateSOSubStatus(serviceOrder.getSoId(), OrderConstants.MISSING_INFORMATION_SUBSTATUS, securityContext);
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_ROUTED);
				}
			}

		} catch (Throwable t) {
			logger.error("route service order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		logger.info("processRouteSO()-->FINISH");
		return processResp;
	}

	// Simple Buyer RePost
	public ProcessResponse processReRouteSO(Integer buyerId, String serviceOrderID, List<Integer> routedResourcesIds, Double fundingAmountCC, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		logger.info("processReRouteSO()-->START");
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderID);
			// apply so promotions if any
			promoBO.applySOPromotions(serviceOrderID, serviceOrder.getBuyerId().longValue());

			if (fundingAmountCC != null) {
				serviceOrder.setFundingAmountCC(fundingAmountCC);
				if (fundingAmountCC != null && fundingAmountCC.doubleValue() > 0.0) {
					serviceOrder.setFundingCCReqd(true);
				} else {
					serviceOrder.setFundingCCReqd(false);
				}
			}

			processResp = processCommonRouteSO(buyerId, serviceOrder, securityContext);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			if (!processResp.getCode().equals(USER_ERROR_RC)) {
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
				marketVO.setFundingTypeId(serviceOrder.getFundingTypeId());
				marketVO.setUserName(securityContext.getUsername());
				marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
				if (securityContext.getRoleId().intValue() == SIMPLE_BUYER_ROLEID)
					marketVO.setAccountId(securityContext.getAccountID());
				else
					marketVO.setAccountId(acct.getAccount_id());
				if (null != routedResourcesIds && routedResourcesIds.size() > 0) {
					//*** lock the buyer until current transaction finishes
					lockBuyer(buyerId);
					//***
					FundingVO fundVO = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
					transBo.routeServiceOrderLedgerAction(marketVO, fundVO);

					Timestamp ts = new Timestamp(calendar.getTimeInMillis());
					serviceOrder.setWfStateId(ROUTED_STATUS);
					serviceOrder.setWfSubStatusId(null);
					serviceOrder.setLastChngStateId(serviceOrder.getWfStateId());
					serviceOrder.setLastStatusChange(ts);
					/*
					 * Setting the routed_date only if it is the first time else
					 * having the old value
					 */
					if (serviceOrder.getRoutedDate() == null) {
						serviceOrder.setRoutedDate(ts);
					}

					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_ROUTED);
					getServiceOrderDao().updateSOStatus(serviceOrder);

					// Insert into so_routed_providers
					List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
					RoutedProvider routedProvider = null;
					
					for (Integer resourceId : routedResourcesIds) {
						routedProvider = new RoutedProvider();
						routedProvider.setRoutedDate(ts);
						routedProvider.setResourceId(resourceId);
						routedProvider.setSoId(serviceOrderID);
						routedProvider.setPriceModel(serviceOrder.getPriceModel());
						routedResources.add(routedProvider);
					}

					getServiceOrderDao().insertRoutedResources(routedResources);

					// insert into so promo if any for providers

				} else {
					// get bean again so AOP can fire
					IServiceOrderBO bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
					bo.updateSOSubStatus(serviceOrder.getSoId(), OrderConstants.MISSING_INFORMATION_SUBSTATUS, securityContext);
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					processResp.setObj(SERVICE_ORDER_SUCCESSFULLY_ROUTED);
				}
			}

		} catch (Throwable t) {
			logger.error("route service order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(t.getMessage());
			processResp.setObj(FAILED_SERVICE_ORDER_NO);
		}
		logger.info("processReRouteSO()-->FINISH");
		return processResp;
	}

	public ProcessResponse processVoidSOForWS(int buyerId, String soId, SecurityContext securityContext) throws BusinessServiceException {
		return this.processVoidSO(buyerId, soId, securityContext);
	}

	/*-----------------------------------------------------------------
	 * This is method will be used void the SO
	 * @param 		soId - ServiceOrder Id
	 * @param		buyerId - Buyer Id
	 * @returns 	ProcessResponse
	 * This needs to be better transactionalized - Distibuted Transactions
	 *-----------------------------------------------------------------*/
	public ProcessResponse processVoidSO(int buyerId, String soId, SecurityContext securityContext) throws BusinessServiceException {
		logger.info("----Start of ServiceOrderBO.processVoidSO----");
		int intWfStateId = 0;
		int intUpdCnt = 0;
		boolean blnCanVoid = false;
		boolean doLedgerTrans = true;
		Calendar calendar = Calendar.getInstance();
		List<String> arr = new ArrayList<String>();
		ProcessResponse processResp = new ProcessResponse();
		try {
			if (isSOInEditMode(soId)) {
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessage("Unable to complete action.  Service Order is currently being edited.");
				return processResp;
			}// end if
			ValidatorResponse validatorResp = new ServiceOrderValidator().validate(soId, buyerId);
			if (validatorResp.isError()) {
				logger.error("void service order failed validation, reason: " + validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				return processResp;
			}

			// Get the service order object from the database
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(soId);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			// Check if the buyer is authorized to cancel the service order
			if (!isAuthorizedBuyer(buyerId, serviceOrder))
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);

			intWfStateId = serviceOrder.getWfStateId();
			if (intWfStateId == 0) {
				return setErrorMsg(processResp, SERVICE_ORDER_WFSTATE_NOT_FOUND);
			}
			if (intWfStateId < ROUTED_STATUS){
				doLedgerTrans = false;
			}else{
				//Determine if this order has been posted.  In the case of funding_type 70,
				//there may no need to do the ledger transactions for a void.
				if (serviceOrder.getFundingTypeId() == CommonConstants.CONSUMER_FUNDING_TYPE){
					int posted = lookupBO.getPostedStatus(serviceOrder.getSoId());
					if (posted == 0){
				doLedgerTrans = false;
					}
				}
			}

			blnCanVoid = checkStateForVoid(intWfStateId);
			Timestamp ts = new Timestamp(calendar.getTimeInMillis());
			serviceOrder.setLastChngStateId(intWfStateId);
			serviceOrder.setVoidedDate(ts);
			serviceOrder.setLastStatusChange(ts);

			// Check to void the service order
			if (blnCanVoid) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				serviceOrder.setWfStateId(OrderConstants.VOIDED_STATUS);
				serviceOrder.setWfSubStatusId(null);
				serviceOrder.setLastChngStateId(serviceOrder.getWfStateId());
				serviceOrder.setVoidedDate(ts);
				serviceOrder.setLastStatusChange(ts);
				map.put("status", VOIDED);
				map.put("penality", new Double(0.0));

				// Updating of SO
				intUpdCnt = getServiceOrderDao().updateSOStatus(serviceOrder);
				if (intUpdCnt > 0) {
					// If this is a child order of a grouped order; then
					// re-price the entire group
					String groupId = serviceOrder.getGroupId();
					if (StringUtils.isNotBlank(groupId)) {
						try {
							// The OrderGroupBO is not injected at object level
							// because OrderGroupBO has cyclic dependency on
							// ServiceOrderBO itself
							IOrderGroupBO orderGroupBO = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean(ABaseRequestDispatcher.ORDER_GROUP_BUSINESS_OBJECT_REFERENCE);
							boolean applyTripCharge = true;
							boolean updateOriginalGroupPrice = true;
							orderGroupBO.rePriceOrderGroup(groupId, applyTripCharge, updateOriginalGroupPrice);
						} catch (com.newco.marketplace.exception.BusinessServiceException bsEx) {
							throw new BusinessServiceException(bsEx.getMessage(), bsEx);
						}
					}

					boolean okToVoid = true;
					// make double entry transaction
					MarketPlaceTransactionVO marketVO = new MarketPlaceTransactionVO();
					marketVO.setServiceOrder(serviceOrder);
					marketVO.setUserTypeID(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
					marketVO.setBuyerID(buyerId);
					marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_VOID_SO);
					marketVO.setUserName(securityContext.getUsername());
					marketVO.setAccountId(acct.getAccount_id());
					marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
					if (doLedgerTrans) {
					logger.info("calling transaction BO to void service order");
						okToVoid = transBo.voidServiceOrderLedgerAction(marketVO);
					}
					if (okToVoid) {
						processResp.setCode(VALID_RC);
						processResp.setSubCode(VALID_RC);
						arr.add(VOIDSO_SUCCESS);
					} else {
						processResp.setCode(USER_ERROR_RC);
						processResp.setSubCode(USER_ERROR_RC);
						arr.add(VOIDSO_FAILURE);
					}
					logger.info("----ServiceOrderBO.processVoidSO 9----");
					processResp.setMessages(arr);

				} else {
					logger.info("----ServiceOrderBO.processVoidSO 10----");
					return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE_VOID);
				}
			}
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] ", dse);
			String error = "";
			error = VOIDSO_FAILURE;
			throw new BusinessServiceException(error, dse);
		} catch(BusinessServiceException bse){
			logger.error("wallet threw an error ", bse);
			throw bse;
		} catch(Exception e){
			logger.error("generic exception in service order", e);
			throw new BusinessServiceException(e);
		}

		logger.info("----End of ServiceOrderBO.processVoidSO----");
		return processResp;
	}

	public ProcessResponse sendCancelEmailInActiveStatus(String soID, int buyerID) {
		ProcessResponse processResp = new ProcessResponse();

		// pull the buyer contact information
		Buyer buyer = new Buyer();
		try {
			buyer.setBuyerId(buyerID);
			buyer = getBuyerDao().query(buyer);
			EmailBean emailer = new EmailBean();
			emailer.sendGenericEmail(buyer.getBuyerContact().getEmail(), "registration@servicelive.com", "Service order " + soID + " has been cancelled in ACTIVE status.");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setSubCode(SYSTEM_ERROR_RC);
			return processResp;
		}

		processResp.setCode(VALID_RC);
		processResp.setSubCode(VALID_RC);
		return processResp;
	}

	/*-----------------------------------------------------------------
	 * This is method will be used to invoke AOP in Accepted State for Cancellation
	 * @param 		soId - ServiceOrder Id
	 * @param		buyerId - Buyer Id
	 * @param		cancelComment - Cancellation comment in Accepted State
	 * @param		buyerName - Buyer Name, Needed for AOP
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/

	public ProcessResponse processCancelSOInAccepted(int buyerId, String soId, String cancelComment, String buyerName, SecurityContext securityContext) throws BusinessServiceException {
		logger.debug("----Start of ServiceOrderBO.processCancelSOInAccepted----");
		int intWfStateId = 0;
		int intUpdCnt = 0;
		long timeMiliSecondsFor24 = 0;
		String strStartTime = "";
		Integer roleId=0;
		String roleType=null;
		boolean blnCanCancel = false;
		Calendar calendar = Calendar.getInstance();
		List<String> arr = new ArrayList<String>();
		ProcessResponse processResp = new ProcessResponse();
		try {

			// Get the service order object from the
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(soId);
			intWfStateId = serviceOrder.getWfStateId();
			ValidatorResponse validatorResp = new ServiceOrderValidator().validateCancelInAccepted(soId, buyerId, cancelComment);
			if (validatorResp.isError()) {
				logger.error("cancel service order failed validation, reason: " + validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				return processResp;
			}
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			// Check if the buyer is authorized to cancel the service order
			if (!isAuthorizedBuyer(buyerId, serviceOrder))
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);

			if (intWfStateId == 0) {
				return setErrorMsg(processResp, SERVICE_ORDER_WFSTATE_NOT_FOUND);
			}

			blnCanCancel = checkStateForCancellationInAccepted(intWfStateId);

			if (blnCanCancel) {
				Timestamp ts = new Timestamp(calendar.getTimeInMillis());
				serviceOrder.setLastChngStateId(intWfStateId);
				serviceOrder.setWfSubStatusId(null);
				serviceOrder.setCancelledDate(ts);
				serviceOrder.setLastStatusChange(ts);
				serviceOrder.setWfStateId(OrderConstants.CANCELLED_STATUS);
				intUpdCnt = getServiceOrderDao().updateSOStatus(serviceOrder);
				if (intUpdCnt > 0) {
					MarketPlaceTransactionVO marketVO = new MarketPlaceTransactionVO();
					marketVO.setServiceOrder(serviceOrder);
					marketVO.setUserTypeID(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
					marketVO.setBuyerID(buyerId);
					marketVO.setVendorId(serviceOrder.getAcceptedVendorId());
					marketVO.setAccountId(acct.getAccount_id());
					marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
					if (logger.isDebugEnabled())
						logger.debug("marketVO : " + marketVO.toString());

					if (serviceOrder.getServiceDate1() != null) {
						strStartTime = serviceOrder.getServiceTimeStart();
						Timestamp appointmentDate = Timestamp.valueOf(serviceOrder.getServiceDate1().toString());
						timeMiliSecondsFor24 = 24 * 60 * 60 * 1000;
						// If successful perform financial transaction for
						// penalty
						// amount if service time is within 24 hours
						// else perform financial transaction without penalty
						if (TimeUtils.isPastXTime(appointmentDate, strStartTime, timeMiliSecondsFor24)) {

							marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_CANCEL_SO);
							marketVO.setUserName(securityContext.getUsername());
								getTransBo().cancelServiceOrderLedgerAction(marketVO);
								if (marketVO.getServiceOrder().getCancellationFee() > 0) {
									Double cancellationFee = marketVO.getServiceOrder().getCancellationFee();
									getServiceOrderDao().updateFinalLaborPrice(soId, cancellationFee);
									marketVO.getServiceOrder().setLaborFinalPrice(cancellationFee);

									// Send emails to buyer and provider
									// regarding the cancelled SO
									Integer vendorId = serviceOrder.getAcceptedResource().getVendorId();
									roleId=securityContext.getRoleId();
									if(OrderConstants.BUYER_ROLEID==roleId)
									{
										roleType=OrderConstants.BUYER;
									}else{
										if(OrderConstants.SIMPLE_BUYER_ROLEID==roleId)
										{
											roleType=OrderConstants.SIMPLE_BUYER;
										}
										else
										{
											roleType=OrderConstants.PROVIDER;
										}
									}
									financeManagerBO.sendBuyerSOCancelEmail(buyerId, vendorId, soId,roleType);
									financeManagerBO.sendProviderSOCancelEmail(buyerId, vendorId, soId);
								}
						} else {
							marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY);
							marketVO.setUserName(securityContext.getUsername());
							transBo.cancelServiceOrderWOPenaltyLedgerAction(marketVO);
							getServiceOrderDao().updateServiceOrderCancellationFee(soId, new Double(0.0));
						}
						processResp.setCode(VALID_RC);
						processResp.setSubCode(VALID_RC);
					}
					arr.add(ACCEPTED_CANCELSO_SUCCESS);
				} else {
					processResp.setCode(SYSTEM_ERROR_RC);
					processResp.setSubCode(SYSTEM_ERROR_RC);
					arr.add(ACCEPTED_CANCELSO_FAILURE);
				}
				processResp.setMessages(arr);
			} else {
				return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE_CANCEL);
			}
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] ", dse);
			throw new BusinessServiceException(ACCEPTED_CANCELSO_FAILURE, dse);
		} catch(BusinessServiceException bse){
			logger.error("[BusinessServiceException] ", bse);
			throw bse;
		} catch(Exception e){
			logger.error("[Exception] ", e);
			throw new BusinessServiceException(ACCEPTED_CANCELSO_FAILURE, e);
		}

		logger.debug("----End of ServiceOrderBO.processCancelSOInAccepted----");
		return processResp;
	}

	/*-----------------------------------------------------------------
	 * This is method will be used in Active State for Cancellation
	 * @param 		soId - ServiceOrder Id
	 * @param		buyerId - Buyer Id
	 * @param		cancelAmt - Cancellation Amount in Active State
	 * @param		buyerName - Buyer Name, Needed for AOP
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse processCancelRequestInActive(int buyerId, String soId, double cancelAmt, String buyerName, SecurityContext securityContext) throws BusinessServiceException {
		logger.debug("----Start of ServiceOrderBO.processCancelRequestInActive----");
		int intWfStateId = 0;
		boolean blnCanCancel = false;
		List<String> arr = new ArrayList<String>();
		ProcessResponse processResp = new ProcessResponse();
		try {

			// Get the service order object from the
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(soId);
			intWfStateId = serviceOrder.getWfStateId();
			ValidatorResponse validatorResp = new ServiceOrderValidator().validateCancelInActive(soId, buyerId, cancelAmt);
			if (validatorResp.isError()) {
				logger.error("cancel service order failed validation, reason: " + validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				return processResp;
			}

			// Check if the buyer is authorized to cancel the service order
			if (!isAuthorizedBuyer(buyerId, serviceOrder))
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);

			if (intWfStateId == 0) {
				return setErrorMsg(processResp, SERVICE_ORDER_WFSTATE_NOT_FOUND);
			}

			blnCanCancel = checkStateForCancellationInActive(intWfStateId);

			if (blnCanCancel) {
				if (getServiceOrderDao().updateSOSubStatus(soId, OrderConstants.CANCELLATION_REQUEST_SUBSTATUS) == 1) {
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					arr.add(REQUEST_CANCELSO_SUCCESS);
					processResp.setMessages(arr);
				} else {
					return setErrorMsg(processResp, CANCELLATION_REQUEST_SUBSTATUS_FAILURE);
				}
			} else {
				return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE_CANCEL);
			}

		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			String error = "";
			error = ACCEPTED_CANCELSO_FAILURE;
			throw new BusinessServiceException(error, dse);
		}
		logger.debug("----End of ServiceOrderBO.processCancelRequestInActive----");
		return processResp;

	}
	/**
	 * Method process the cancel request for an accepted service order from OMS 
	 * @param String soId
	 * @param SecurityContext
	 * @return void
	 * @throws BusinessServiceException
	 */
	public void processCancelSOInAcceptedForWS(String soId, SecurityContext securityContext) throws BusinessServiceException{
		int intUpdCnt = 0;
		int buyerId = 0;
		int oldWfStateId = 0;
		Calendar calendar = Calendar.getInstance();
		long timeMiliSecondsFor24 = 0;
		String strStartTime = ""; 
		String roleType=null;
		Integer roleId=0;
		try{
			// Get the service order object using SO ID
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(soId);
			buyerId = serviceOrder.getBuyer().getBuyerId();
			oldWfStateId = serviceOrder.getWfStateId();
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			Timestamp ts = new Timestamp(calendar.getTimeInMillis());
			serviceOrder.setLastChngStateId(oldWfStateId);
			serviceOrder.setWfSubStatusId(null);
			serviceOrder.setCancelledDate(ts);
			serviceOrder.setLastStatusChange(ts);
			serviceOrder.setWfStateId(OrderConstants.CANCELLED_STATUS);
			intUpdCnt = getServiceOrderDao().updateSOStatus(serviceOrder);
			if(intUpdCnt>0){
				MarketPlaceTransactionVO marketVO = new MarketPlaceTransactionVO();
				if (serviceOrder.getServiceDate1() != null) {
					strStartTime = serviceOrder.getServiceTimeStart();
					Timestamp appointmentDate = Timestamp.valueOf(serviceOrder.getServiceDate1().toString());
					timeMiliSecondsFor24 = 24 * 60 * 60 * 1000;
				marketVO.setServiceOrder(serviceOrder);
				marketVO.setUserTypeID(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
				marketVO.setBuyerID(buyerId);
				marketVO.setVendorId(serviceOrder.getAcceptedVendorId());
				marketVO.setAccountId(acct.getAccount_id());
				marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());				
					marketVO.setUserName(securityContext.getUsername());
					// If successful perform financial transaction for
					// penalty
					// amount if service time is within 24 hours
					// else perform financial transaction without penalty
					if (TimeUtils.isPastXTime(appointmentDate, strStartTime, timeMiliSecondsFor24)) {	
						marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_CANCEL_SO);
						transBo.cancelServiceOrderLedgerAction(marketVO);
						if (marketVO.getServiceOrder().getCancellationFee() > 0) {
							Double cancellationFee = marketVO.getServiceOrder().getCancellationFee();
							getServiceOrderDao().updateFinalLaborPrice(soId, cancellationFee);
							marketVO.getServiceOrder().setLaborFinalPrice(cancellationFee);
							// Send emails to buyer and provider
							// regarding the cancelled SO
							Integer vendorId = serviceOrder.getAcceptedResource().getVendorId();
							roleId=securityContext.getRoleId();
							if(OrderConstants.BUYER_ROLEID==roleId)
							{
								roleType=OrderConstants.BUYER;
							}else{
								if(OrderConstants.SIMPLE_BUYER_ROLEID==roleId)
								{
									roleType=OrderConstants.SIMPLE_BUYER;
								}
								else
								{
									roleType=OrderConstants.PROVIDER;
								}
							}
							financeManagerBO.sendBuyerSOCancelEmail(buyerId, vendorId, soId,roleType);
							financeManagerBO.sendProviderSOCancelEmail(buyerId, vendorId, soId);
						}
				}
				else
				{
				marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY);
				transBo.cancelServiceOrderWOPenaltyLedgerAction(marketVO);
				getServiceOrderDao().updateServiceOrderCancellationFee(soId, new Double(0.0));				
			}			
			}	
		}
		}catch(DataServiceException dse){
			logger.error("Error occurred at processCancelSOInAcceptedForWS method in ServiceOrderBO",dse);
			throw new BusinessServiceException("Error occurred at processCancelSOInAcceptedForWS method in ServiceOrderBO");
		}
	}
	/**
	 * Method process the cancel request for an accepted service order from OMS 
	 * @param String soId
	 * @param SecurityContext
	 * @return void
	 * @throws BusinessServiceException
	 */
	public void processCancelSOInActiveForWS(String soId, SecurityContext securityContext) throws BusinessServiceException{
		try{
			getServiceOrderDao().updateSOSubStatus(soId, OrderConstants.CANCELLATION_REQUEST_SUBSTATUS);		
		}catch(DataServiceException dse){
			logger.error("Error occurred at processCancelSOInActiveForWS method in ServiceOrderBO");
			throw new BusinessServiceException("Error occurred at processCancelSOInActiveForWS method in ServiceOrderBO");
		}		
	}
	/*-----------------------------------------------------------------
	 * This is method will be to close ServiceOrder
	 * @param 		serviceOrderID - ServiceOrder Id
	 * @param		buyerId - Buyer Id
	 * @param		finalPartsPrice - Final Parts Price
	 * @param 		finalLaborPrice - Final Labor Price
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse processCloseSO(Integer buyerId, String serviceOrderID, double finalPartsPrice, double finalLaborPrice, SecurityContext securityContext) throws BusinessServiceException {
		logger.debug("----Start of ServiceOrderBO.processCloseSO----");
		ProcessResponse processResp = soCloseBO.processCloseSO(buyerId, serviceOrderID, finalPartsPrice, finalLaborPrice, securityContext);
		logger.debug("----End of ServiceOrderBO.processCloseSO----");
		return processResp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #processGetSONotes
	 * (com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote)
	 */
	public ProcessResponse processGetSONotes(ServiceOrderNote soNote) {
		ProcessResponse processResp = new ProcessResponse();
		try {
			ArrayList<ServiceOrderNote> soNotesList = (ArrayList<ServiceOrderNote>) getServiceOrderDao().getSONotes(soNote);
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			processResp.setObj(soNotesList);
			return processResp;
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(e.getMessage());
			processResp.setObj(null);
		}
		return processResp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #processGetAllSONotes
	 * (com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote)
	 */
	public ProcessResponse processGetAllSONotes(ServiceOrderNote soNote) {
		ProcessResponse processResp = new ProcessResponse();
		try {
			ArrayList<ServiceOrderNote> soNotesList = (ArrayList<ServiceOrderNote>) getServiceOrderDao().getAllSONotes(soNote);
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			processResp.setObj(soNotesList);
			return processResp;
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(e.getMessage());
			processResp.setObj(null);
		}
		return processResp;
	}

	/**
	 * Function to get all the Deleted Notes for SL Administrator.
	 */
	public ProcessResponse processGetDeletedSONotes(ServiceOrderNote soNote) {
		ProcessResponse processResp = new ProcessResponse();
		try {
			ArrayList<ServiceOrderNote> soNotesList = (ArrayList<ServiceOrderNote>) getServiceOrderDao().getSODeletedNotes(soNote);
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			processResp.setObj(soNotesList);
			return processResp;
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(e.getMessage());
			processResp.setObj(null);
		}
		return processResp;
	}

	public ProcessResponse processUpdatePart(Part part) throws BusinessServiceException {
		return processUpdatePart(part, new Integer(0));
	}

	public ProcessResponse processUpdatePart(Part part, Integer partSupplier) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			getServiceOrderDao().addOrUpdatePart(part);
			// now update the parts required field in the database

			if (partSupplier.intValue() > 0) {
				try {
					ServiceOrder serviceOrderVO = getServiceOrderDao().getServiceOrder(part.getSoId());

					if (null != serviceOrderVO) {
						serviceOrderVO.setPartsSupplier(partSupplier);
						getServiceOrderDao().updatePartsSuppier(serviceOrderVO);
					}

				} catch (Exception ex) {
					logger.error("[DataServiceException] " + ex);
					final String error = "Error in adding Part to service Order.";
					processResp.setCode(SYSTEM_ERROR_RC);
					processResp.setMessage(error);
					processResp.setObj(part);
					throw new BusinessServiceException(error, ex);
				}
			}
			// setup a valid process response
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			processResp.setObj(part);

		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] ", dse);
			final String error = "Error in adding Part to service Order.";
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(error);
			processResp.setObj(part);
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(ex.getMessage());
			processResp.setObj(part);
			logger.error("[Exception] ", ex);
		}
		return processResp;
	}

	public ProcessResponse updatePartsShippingInfo(String soId, List<Part> parts, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			getServiceOrderDao().updateSOPartsShippingInfo(parts);
			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			final String error = "Error in updating parts ship" + "ping info for SO.";
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(error);
			processResp.setObj(null);
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(ex.getMessage());
			processResp.setObj(null);
			logger.error("Unexpected error occurred while updating parts shipping info for SO:" + soId, ex);
		}
		return processResp;
	}

	public ProcessResponse processSupportAddNote(Integer resourceId, Integer roleId, String soId, String subject, String message, Integer noteTypeId, String createdByName, String modifiedBy, Integer entityId, String privateInd, boolean isEmailToSent, SecurityContext securityContext) throws BusinessServiceException {
		return processAddNote(resourceId, roleId, soId, subject, message, noteTypeId, createdByName, modifiedBy, entityId, privateInd, isEmailToSent, false, securityContext);
	}

	public ProcessResponse processAddNote(Integer resourceId, Integer roleId, String soId, String subject, String message, Integer noteTypeId, String createdByName, String modifiedBy, Integer entityId, String privateInd, boolean isEmailToSent, boolean isEmptyNoteAllowed, SecurityContext securityContext) throws BusinessServiceException {

		ServiceOrderNote note = new ServiceOrderNote();
		note.setEmptyNoteAllowed(isEmptyNoteAllowed);
		note.setSoId(soId);
		note.setNoteTypeId(noteTypeId);
		note.setSubject(subject);
		note.setNote(message);
		note.setRoleId(roleId);
		
		//SL-19050
		//Code added to set read_ind for buyers as 1 .
		if(roleId == 3)
		{
			note.setReadInd(1);
		}
		else
		{
			note.setReadInd(0);	
		}
		
		
		if (StringUtils.isEmpty(privateInd) || !StringUtils.isNumeric(privateInd)) {
			privateInd = "0";
		}
		note.setPrivateId(Integer.parseInt(privateInd));
		// Modified to insert Created By Name
		note.setCreatedByName(createdByName);
		// Added to insert ModifiedBy
		note.setModifiedBy(modifiedBy);
		// Added to insert EntityId
		note.setEntityId(entityId);

		note.setCreatedDate(new Date(System.currentTimeMillis()));

		ProcessResponse processResp = new ProcessResponse();

		try {
			// basic request validation
			ValidatorResponse validatorResp = new ServiceOrderValidator().validate(resourceId, roleId, note);

			if (validatorResp.isError()) {
				logger.debug("processAddNote()--> validate() ERRORS-->" + (note == null ? "NULL" : note.getSoId()) + " reason: " + validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				return processResp;
			}

			// Need to make sure the soId/groupId has an existing Service Order
			// or Group Order in DB
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(soId);
			OrderGroupVO orderGroup = null;
			if (serviceOrder == null) {
				orderGroup = orderGroupDao.getOrderGroupByGroupId(soId);
			}

			if (serviceOrder == null && orderGroup == null) {
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessage("Error: " + note.getSoId() + " does not exist as a ServiceOrder or GroupOrder.  Note will not be added");
				return processResp;
			}

			//note.setNoteId(randomNo.generateGUID());
			
		
			getServiceOrderDao().insertNote(note);

			// setup a valid process response
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			processResp.setObj(note.getNoteId());

		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			final String error = "Error in adding Note to service Order.";
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(error);
			processResp.setObj(null);
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage(ex.getMessage());
			processResp.setObj(null);
			logger.error("Unexpected error occurred while adding note for SO:" + soId, ex);
		}

		return processResp;
	}

	public ProcessResponse updateSOSchedule(String soId, Timestamp startDate, Timestamp endDate, String startTime, String endTime, int buyerId, boolean validate, SecurityContext securityContext) {
		ProcessResponse processResponseObj = new ProcessResponse();
		try {
			processResponseObj = rescheduleSO(soId, startDate, endDate, startTime, endTime, null, buyerId, true, securityContext);
		} catch (Throwable t) {
			logger.error("Reschedule Service Order:: error:  " + t.getMessage(), t);
			processResponseObj.setCode(SYSTEM_ERROR_RC);
			processResponseObj.setMessage(t.getMessage());
		}
		return processResponseObj;
	}

	private ProcessResponse updateSOSchedule(String soID, Timestamp startDate, Timestamp endDate, String startTime, String endTime, int buyerId, SecurityContext securityContext) throws DataServiceException {
		return updateSOSchedule(getServiceOrderDao().getServiceOrder(soID), startDate, endDate, startTime, endTime, buyerId, false, securityContext);
	}

	/**
	 * Updates the so schedule directly without a request.
	 * 
	 * @param String
	 *            soID
	 * @param Timestamp
	 *            startDate
	 * @param Timestamp
	 *            endDate - if null a fixed date is assumed instead of a range
	 * @param String
	 *            startTime - hh:mm AM/PM
	 * @param String
	 *            endTime - hh:mm AM/PM
	 * @param int buyerId
	 * @param boolean validate
	 */
	private ProcessResponse updateSOSchedule(ServiceOrder so, Timestamp startDate, Timestamp endDate, String startTime, String endTime, int buyerId, boolean validate, SecurityContext securityContext) {

		ProcessResponse processResponseObj = new ProcessResponse();
		try {
			// make sure the required fields are present at least a start date
			// should be present
			// and not in the past
			// get the date with the time string - compare to GMT
			if (startDate == null) {
				processResponseObj.setCode(USER_ERROR_RC);
				processResponseObj.setMessage(SERVICE_ORDER_MISSING_RESCHEDULING_DATE);
				return processResponseObj;
			} else if (TimeUtils.getServiceDateInGMT(TimeUtils.combineDateTime(startDate, startTime)).before(TimeUtils.getCurrentGMT())) {
				processResponseObj.setCode(USER_ERROR_RC);
				processResponseObj.setMessage(SERVICE_ORDER_INVALID_RESCHEDULING_DATE);
				return processResponseObj;
			}

			// if the startdate and enddate are the same AND starttime and
			// endtime are the same
			// null them out to trigger a fixed date type
			if (endDate != null && endDate.compareTo(startDate) == 0) {
				// now check time
				if (endTime != null && endTime.length() > 0 && endTime.equals(startTime)) {
					endTime = null;
					endDate = null;
				}
			}
			// validate reschedule date
			if (validate && so.getServiceDate1() != null) {
				// the new start date must be after the current date
				if (TimeUtils.getServiceDateInGMT(TimeUtils.getCurrentGMT()).after(TimeUtils.combineDateTime(startDate, startTime))) {
					processResponseObj.setCode(USER_ERROR_RC);
					processResponseObj.setMessage(SERVICE_ORDER_INVALID_RESCHEDULING_DATE);
					return processResponseObj;
				}
			}
			// validate buyerid from serviceorder
			if (!isAuthorizedBuyer(buyerId, so)) {
				processResponseObj.setCode(USER_ERROR_RC);
				processResponseObj.setMessage(BUYER_IS_NOT_AUTHORIZED);
				return processResponseObj;
			}
			// validate ServiceOrder status - service order must be open
			if (validate && !isValidServiceOrderStatus(so.getWfStateId().intValue())) {
				processResponseObj.setCode(SYSTEM_ERROR_RC);
				processResponseObj.setMessage(SERVICE_ORDER_WFSTATE_NOT_VALID_RESCHEDULING);
				return processResponseObj;
			}

			so.setServiceDate1(startDate);
			so.setServiceDate2(endDate);
			so.setServiceTimeStart(startTime);
			so.setServiceTimeEnd(endTime);

			if (so.getWfStateId().intValue() == OrderConstants.ACTIVE_STATUS) {
				so.setWfStateId(OrderConstants.ACCEPTED_STATUS);
			}

			if (validate == true) {
				getServiceOrderDao().updateSOReschedule(so);
			}
			processResponseObj.setCode(VALID_RC);
		} catch (Throwable t) {
			logger.error("Reschedule Service Order:: error:  " + t.getMessage(), t);
			processResponseObj.setCode(SYSTEM_ERROR_RC);
			processResponseObj.setMessage(t.getMessage());

		}
		return processResponseObj;
	}

	public ProcessResponse updateSOSpendLimit(String soId, Double totalSpendLimitLabor, Double totalSpendlimitParts, String increasedSpendLimitComment, int buyerId, boolean validate, boolean skipAlertLogging, SecurityContext securityContext) throws BusinessServiceException {

		double increasedAmount = 0.0;
		ProcessResponse processResp = new ProcessResponse();
		List<String> arr = new ArrayList<String>();
		ServiceOrder serviceOrder = null;
		IncreaseSpendLimitRequestVO incSpendLimitReqVO = null;
		try {
			incSpendLimitReqVO = new IncreaseSpendLimitRequestVO();
			incSpendLimitReqVO.setServiceOrderID(soId);
			serviceOrder = getServiceOrderDao().getServiceOrder(incSpendLimitReqVO.getServiceOrderID());
			MarketPlaceTransactionVO service = setUpVO(buyerId, serviceOrder);

			incSpendLimitReqVO.setIncreaseSpendLimit(totalSpendLimitLabor);
			incSpendLimitReqVO.setIncreaseSpendLimitParts(totalSpendlimitParts);
			incSpendLimitReqVO.setComment(increasedSpendLimitComment);
			incSpendLimitReqVO.setBuyerId(buyerId);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			// basic request validation
			ValidatorResponse validatorResp = new SOIncreaseSpendLimitValidator().validate(incSpendLimitReqVO, validate);

			if (validatorResp.isError()) {
				return setProcessResp(processResp, incSpendLimitReqVO, validatorResp);
			}

			// if service order is not found returns the error

			if (serviceOrder == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}

			// checks whether buyer is associated with the service order
			if (!validatecallinguser(incSpendLimitReqVO.getBuyerId(), serviceOrder))
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);

			int wfState = serviceOrder.getWfStateId().intValue();
			// returns error if the work flow state has error
			if (wfState < 0)
				return setErrorMsg(processResp, SERVICE_ORDER_WFSTATE_NOT_FOUND);
			// checks whether the status is appropriate to increase spend limit
			boolean result = checkStateForIncreseSpendLimit(wfState);
			// allow increase spend limit for orders which are children of any
			// group // see TODO in SOPricingTabDTO
			if (StringUtils.isNotBlank(serviceOrder.getGroupId()) && (wfState == DRAFT_STATUS || wfState == EXPIRED_STATUS)) {
				result = true;
			}

			AjaxCacheVO avo = new AjaxCacheVO();
			avo.setCompanyId(buyerId);
			avo.setRoleType("BUYER");
			if (result) {
				double initialLaborSpendLimitPrim = 0.0;
				double initialPartsSpendLimitPrim = 0.0;
				Double initialLaborSpendLimit = serviceOrder.getSpendLimitLabor();
				Double initialPartsSpendLimit = serviceOrder.getSpendLimitParts();
				if (initialLaborSpendLimit != null) {
					initialLaborSpendLimitPrim = initialLaborSpendLimit.doubleValue();
				}
				if (initialPartsSpendLimit != null) {
					initialPartsSpendLimitPrim = initialPartsSpendLimit.doubleValue();
				}
				if (incSpendLimitReqVO.getIncreaseSpendLimitParts() == null) {
					incSpendLimitReqVO.setIncreaseSpendLimitParts(new Double(initialPartsSpendLimitPrim));
				}
				// validates the initial price,increase spend limit and updates
				if ( (MoneyUtil.add(initialLaborSpendLimitPrim,initialPartsSpendLimitPrim) <= MoneyUtil.add(incSpendLimitReqVO.getIncreaseSpendLimit().doubleValue(),
						incSpendLimitReqVO.getIncreaseSpendLimitParts().doubleValue()))	|| !validate){
					// check whether the user have the amount in sears account
					// to increase spend limit
					double availableBalance = transBo.getAvailableBalance(avo);
					double totalSlLabor = totalSpendLimitLabor != null ? totalSpendLimitLabor.doubleValue() : 0.0;
					double totalSlParts = totalSpendlimitParts != null ? totalSpendlimitParts.doubleValue() : 0.0;
					increasedAmount = MoneyUtil.getRoundedMoney(totalSlLabor + totalSlParts - initialLaborSpendLimitPrim - initialPartsSpendLimitPrim);
					if (logger.isDebugEnabled()) {
						logger.debug("availableBalance: " + availableBalance);
						logger.debug("increasedAmount: " + increasedAmount);
					}
					if (buyerOverMaxLimit(serviceOrder.getAccessFee(), totalSlLabor, totalSlParts, securityContext)) {
						return setErrorMsg(processResp, BUYER_OVER_MAX_SPEND_LIMIT);
					} else if (securityContext != null) {
						if (securityContext.isIncreaseSpendLimitInd())
							return setErrorMsg(processResp, BUYER_OVER_MAX_SPEND_LIMIT);
					}
					serviceOrder.setSpendLimitLabor(totalSpendLimitLabor);
					serviceOrder.setSpendLimitParts(totalSpendlimitParts);
					serviceOrder.setSpendLimitIncrComment(increasedSpendLimitComment);
					// Check for enough funds if its Pre-funded,
					// Ignore for Non-funded and Direct-Funded
					// simple buyer must have the funds in their account
					if ((serviceOrder.getFundingTypeId() != null) 
							&& (LedgerConstants.SHC_FUNDING_TYPE == serviceOrder.getFundingTypeId().intValue()
								|| LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER == serviceOrder.getFundingTypeId().intValue())) {
						service.setAutoACHInd("true");
					}
					if (serviceOrder.getFundingTypeId() != null && LedgerConstants.FUNDING_TYPE_NON_FUNDED != serviceOrder.getFundingTypeId().intValue()) {
						// check available funds next
						MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
						FundingVO fundVo = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
						if (!fundVo.isEnoughFunds()) {
							if (!securityContext.isAutoACH()) {
								String message = SERVICE_ORDER_NOT_SUFFICIENT_FUNDS_AVAILABLE;
								if (securityContext.getRoleId().intValue() == SIMPLE_BUYER_ROLEID) {
									message = SIMPLE_BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS;
								}
								return setErrorMsg(processResp, message, soId);
							}
						}
					}

					// save the service order
					if (validate == true) {
						getServiceOrderDao().updateLimit(serviceOrder);

						// updating so_price table 
						updateDiscountedSpendLimit(soId,totalSpendLimitLabor,totalSpendlimitParts);

						// Resetting the response history removing
						// resetResponseHistory call - it clears out
						// the routed providers info and when released so, and
						// then re-route, it re-routes
						// to the guys that rejected or released in the past if
						// there is a need for clearing
						// this out, we need to come up with an alternative on
						// handling this issue for now,
						// remove just the ones that conditionally accepted
						if (wfState == ROUTED_STATUS) {
							getServiceOrderDao().removeConditionalFromRoutedProviders(soId);
						}
					}

					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					// Creating the double entries
					service.setUserName(securityContext.getUsername());
					service.setAccountId(acct.getAccount_id());
					if (increasedAmount > 0){
						if(!(serviceOrder.getWfStateId().equals(OrderConstants.DRAFT_STATUS))){
							transBo.increaseSpendLimit(service, increasedAmount, soId, increasedAmount);
						}		
					}else{
						return setErrorMsg(processResp, SPEND_LIMIT_LESSTHAN_INTIALPRICE);
					}
				} else {
					return setErrorMsg(processResp, SPEND_LIMIT_LESSTHAN_INTIALPRICE);
				}
				arr.add(SUCCESSFUL_SPEND_LIMIT_INCREASE);
				processResp.setMessages(arr);
			} else {
				return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE);
			}
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] ", dse);
			throw new BusinessServiceException("Error in increasing the spend limit.", dse);
		} catch (Exception ex) {
			logger.error("[Exception] ", ex);
		}
		return processResp;
	}

	private ProcessResponse updateSOSpendLimit(String soId, Double totalSpendLimitLabor, Double totalSpendlimitParts, String increasedSpendLimitComment, int buyerId, boolean validate, SecurityContext securityContext, Double delta) throws BusinessServiceException {

		double increasedAmount = 0.0;
		ProcessResponse processResp = new ProcessResponse();
		ServiceOrder serviceOrder = null;
		IncreaseSpendLimitRequestVO incSpendLimitReqVO = null;
		try {
			incSpendLimitReqVO = new IncreaseSpendLimitRequestVO();
			incSpendLimitReqVO.setServiceOrderID(soId);
			serviceOrder = getServiceOrderDao().getServiceOrder(incSpendLimitReqVO.getServiceOrderID());
			MarketPlaceTransactionVO service = setUpVO(buyerId, serviceOrder);

			incSpendLimitReqVO.setIncreaseSpendLimit(totalSpendLimitLabor);
			incSpendLimitReqVO.setIncreaseSpendLimitParts(totalSpendlimitParts);
			incSpendLimitReqVO.setComment(increasedSpendLimitComment);
			incSpendLimitReqVO.setBuyerId(buyerId);

			// basic request validation
			ValidatorResponse validatorResp = new SOIncreaseSpendLimitValidator().validate(incSpendLimitReqVO, validate);
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			if (validatorResp.isError()) {
				return setProcessResp(processResp, incSpendLimitReqVO, validatorResp);
			}

			// if service order is not found returns the error

			if (serviceOrder == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}

			// checks whether buyer is associated with the service order
			if (!validatecallinguser(incSpendLimitReqVO.getBuyerId(), serviceOrder))
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);

			int wfState = serviceOrder.getWfStateId().intValue();
			// returns error if the work flow state has error
			if (wfState < 0)
				return setErrorMsg(processResp, SERVICE_ORDER_WFSTATE_NOT_FOUND);
			// checks whether the status is appropriate to increase spend limit
			boolean result = checkStateForIncreseSpendLimit(wfState);

			AjaxCacheVO avo = new AjaxCacheVO();
			avo.setCompanyId(buyerId);
			avo.setRoleType("BUYER");
			if (result) {
				double initialLaborSpendLimitPrim = 0.0;
				double initialPartsSpendLimitPrim = 0.0;
				Double initialLaborSpendLimit = serviceOrder.getSpendLimitLabor();
				Double initialPartsSpendLimit = serviceOrder.getSpendLimitParts();
				if (initialLaborSpendLimit != null) {
					initialLaborSpendLimitPrim = initialLaborSpendLimit.doubleValue();
				}
				if (initialPartsSpendLimit != null) {
					initialPartsSpendLimitPrim = initialPartsSpendLimit.doubleValue();
				}
				if (incSpendLimitReqVO.getIncreaseSpendLimitParts() == null) {
					incSpendLimitReqVO.setIncreaseSpendLimitParts(new Double(initialPartsSpendLimitPrim));
				}
				// validates the initial price,increase spend limit and updates
					if ( (MoneyUtil.add(initialLaborSpendLimitPrim,initialPartsSpendLimitPrim) <= MoneyUtil.add(incSpendLimitReqVO.getIncreaseSpendLimit().doubleValue(),
							incSpendLimitReqVO.getIncreaseSpendLimitParts().doubleValue()))	|| !validate) {
					// check whether the user have the amount in sears account
					// to increase spend limit
					double availableBalance = transBo.getAvailableBalance(avo);
					double totalSlLabor = totalSpendLimitLabor != null ? totalSpendLimitLabor.doubleValue() : 0.0;
					double totalSlParts = totalSpendlimitParts != null ? totalSpendlimitParts.doubleValue() : 0.0;
					increasedAmount = 
						MoneyUtil.subtract( 
							totalSlLabor + totalSlParts - initialLaborSpendLimitPrim, 
							initialPartsSpendLimitPrim);  
					if (logger.isDebugEnabled()) {
						logger.debug("availableBalance: " + availableBalance);
						logger.debug("increasedAmount: " + increasedAmount);
					}
					if (buyerOverMaxLimit(serviceOrder.getAccessFee(), totalSlLabor, totalSlParts, securityContext)) {
						return setErrorMsg(processResp, BUYER_OVER_MAX_SPEND_LIMIT);
					} else if (securityContext != null) {
						if (securityContext.isIncreaseSpendLimitInd())
							return setErrorMsg(processResp, BUYER_OVER_MAX_SPEND_LIMIT);
					}
					serviceOrder.setSpendLimitLabor(totalSpendLimitLabor);
					serviceOrder.setSpendLimitParts(totalSpendlimitParts);
					serviceOrder.setSpendLimitIncrComment(increasedSpendLimitComment);
					// Check for enough funds if its Pre-funded,
					// Ignore for Non-funded and Direct-Funded
					// simple buyer must have the funds in their account
					if (serviceOrder.getFundingTypeId() != null 
							&& (LedgerConstants.SHC_FUNDING_TYPE == serviceOrder.getFundingTypeId().intValue()
							|| LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER == serviceOrder.getFundingTypeId().intValue())) {
						service.setAutoACHInd("true");
					}
					if (serviceOrder.getFundingTypeId() != null && LedgerConstants.FUNDING_TYPE_NON_FUNDED != serviceOrder.getFundingTypeId().intValue()) {
						// check available funds next
						MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
						serviceOrder.setFundingAmountCC(delta);
						FundingVO fundVo = checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
						if (!fundVo.isEnoughFunds()) {
							if (!securityContext.isAutoACH()) {
								String message = SERVICE_ORDER_NOT_SUFFICIENT_FUNDS_AVAILABLE;
								if (securityContext.getRoleId().intValue() == SIMPLE_BUYER_ROLEID) {
									message = SIMPLE_BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS;
								}
								return setErrorMsg(processResp, message, soId);
							}
						}
					}

					// save the service order
					if (validate == true) {
						getServiceOrderDao().updateLimit(serviceOrder);

						// updating so_price table 
						updateDiscountedSpendLimit(soId,totalSpendLimitLabor,totalSpendlimitParts);

						// Resetting the response history
						// removing resetResponseHistory call - it clears out
						// the routed providers info
						// and when released so, and then re-route, it re-routes
						// to
						// the guys that rejected or released in the past
						// if there is a need for clearing this out, we need to
						// come up
						// with an alternative on handling this issue
						// for now, remove just the ones that conditionally
						// accepted
						if (wfState == ROUTED_STATUS) {
							getServiceOrderDao().removeConditionalFromRoutedProviders(soId);
						}
					}
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					List<String> arr = new ArrayList<String>();
					arr.add(SUCCESSFUL_SPEND_LIMIT_INCREASE);
					processResp.setMessages(arr);
					// Creating the double entries
					service.setUserName(securityContext.getUsername());
					service.setAccountId(acct.getAccount_id());
					if (increasedAmount < 0){
						transBo.decreaseSpendLimit(service, Math.abs(increasedAmount), Math.abs(increasedAmount));
					}else{
						transBo.increaseSpendLimit(service, increasedAmount, soId, increasedAmount);
					}
				} else
					return setErrorMsg(processResp, SPEND_LIMIT_LESSTHAN_INTIALPRICE);
			} else {
				return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE);
			}
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] ", dse);
			throw new BusinessServiceException("Error in increasing the spend limit.", dse);
		} catch (Exception ex) {
			logger.error("[Exception] ", ex);
		}
		return processResp;
	}

	private ProcessResponse setProcessResp(ProcessResponse processResp, IncreaseSpendLimitRequestVO incSpendLimitReqVO, ValidatorResponse validatorResp) {
		if (logger.isDebugEnabled())
			logger.debug("updateSOSpendLimit:: failed to update spend limit to service order: " + (incSpendLimitReqVO == null ? "NULL" : incSpendLimitReqVO.getServiceOrderID()) + " reason: " + validatorResp.getMessages());
		processResp.setCode(USER_ERROR_RC);
		processResp.setMessages(validatorResp.getMessages());
		return processResp;
	}

	/**
	 * Description: Sets values into MarketPlaceTransactionVO
	 * 
	 * @param buyerId
	 * @param serviceOrder
	 * @return <code>MarketPlaceTransactionVO</code>
	 */
	private MarketPlaceTransactionVO setUpVO(int buyerId, ServiceOrder serviceOrder) {
		// Setting up the MarketPlaceTransactionVO object with required data
		MarketPlaceTransactionVO service = new MarketPlaceTransactionVO();
		service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW);
		service.setLedgerEntryRuleId(LedgerConstants.RULE_ID_INCREASE_SPEND_LIMIT);
		service.setCCInd(false);
		service.setBuyerID(buyerId);
		service.setServiceOrder(serviceOrder);
		return service;
	}

	public ProcessResponse rejectServiceOrder(int resourceId, String soId, int reasonId, int responseId, String modifiedBy, String reasonDesc, SecurityContext securityContext) throws BusinessServiceException {
		logger.debug("----Start of SOChangeBOImpl.rejectServiceOrder----");
		String servOrderId = "";
		int updCnt = 0;
		ProcessResponse processResp = new ProcessResponse();
		try {
			if (this.isSOInEditMode(soId)) {
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessage("Unable to complete action.  Service Order is currently being edited.");

			}// end if
			else {
				ResponseSoVO responseSoVO = new ResponseSoVO();
				responseSoVO.setResourceId(resourceId);
				responseSoVO.setSoId(soId);
				responseSoVO.setReasonId(reasonId);
				responseSoVO.setResponseId(responseId);
				responseSoVO.setModifiedBy(modifiedBy);
				responseSoVO.setReasonDesc(reasonDesc);
				if (logger.isDebugEnabled())
					logger.debug("responseVO data : " + responseSoVO.toString());

				// basic request validation
				ValidatorResponse validatorResp = new SORejectOrderValidator().validate(responseSoVO);

				if (validatorResp.isError()) {
					if (logger.isDebugEnabled())
						logger.debug("rejectServiceOrder:: failed to reject service order: " + (responseSoVO == null ? "NULL" : responseSoVO.getSoId()) + " reason: " + validatorResp.getMessages());
					processResp.setCode(USER_ERROR_RC);
					processResp.setMessages(validatorResp.getMessages());
					return processResp;
				}

				if (responseSoVO != null) {
					servOrderId = responseSoVO.getSoId();
				} else {
					return setErrorMsg(processResp, MISSING_INPUT);
				}
				ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(servOrderId);
				if (serviceOrder == null) {
					return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
				}

				int wfState = serviceOrder.getWfStateId().intValue();
				// returns error if the work flow state has error
				if (wfState < 0)
					return setErrorMsg(processResp, WFSTATE_NOT_FOUND);

				// checks whether the status is appropriate to reject the so
				boolean result = checkStateofSOforReject(wfState);

				if (result) {
					updCnt = getServiceOrderDao().updateProviderResponse(responseSoVO);
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					if (updCnt == 0) {
						processResp.setObj(PROVIDER_IS_NOT_AUTHORIZED);
						return setErrorMsg(processResp, PROVIDER_IS_NOT_AUTHORIZED);
					} else {

						processResp.setCode(VALID_RC);
						processResp.setSubCode(VALID_RC);
						List<String> arr = new ArrayList<String>();
						arr.add(REJECT_SUCCESS);
						processResp.setMessages(arr);
					}

				} else {
					return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE_REJECT);
				}
			}

		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			final String error = "Error in rejecting service Order.";
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			logger.info("[Exception] " + ex);
		}
		logger.debug("----End of SOChangeBOImpl.rejectServiceOrder----");
		return processResp;
	}

	public boolean isRejectAlertNeeded(String soId) {
		boolean isAlertNeeded = false;
		int intRoutedProviderCnt = -1;
		int intRejectResponseCnt = -2;
		try {
			intRoutedProviderCnt = getServiceOrderDao().getRoutedProviderCount(soId);
			intRejectResponseCnt = getServiceOrderDao().getRejectResponseCount(soId);
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
		}
		if (intRoutedProviderCnt == intRejectResponseCnt) {
			isAlertNeeded = true;
		}
		return isAlertNeeded;
	}

	public void sendAllProviderRejectAlert(String soId, SecurityContext securityContext) {
		logger.debug("Sending all provider reject email to buyer for soId : " + soId);
	}

	public ArrayList<ReasonLookupVO> queryListRejectReason() throws BusinessServiceException {
		ArrayList<ReasonLookupVO> arrRejectReasonList = null;
		try {
			arrRejectReasonList = getServiceOrderDao().queryListRejectReason();
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			final String error = "Error in retrieving reject reason code";
			throw new BusinessServiceException(error, dse);
		}
		return arrRejectReasonList;
	}

	public boolean enoughBuyerFunds(AjaxCacheVO avo, ServiceOrder serviceOrder, MarketPlaceTransactionVO mvo, double increaseAmt) throws BusinessServiceException {
		Double accessfee = null;

		try {
			accessfee = serviceOrder.getPostingFee();
		} catch (Exception e) {
			logger.error("enoughBuyerFunds()-->EXCEPTION-->", e);
		}

		if (serviceOrder.getSpendLimitLabor() == null) {
			return false;
		}

		if (!processFundingType(serviceOrder.getFundingTypeId())) {
			return true;
		}
		Integer state = null;
		state = serviceOrder.getWfStateId();

		Double total = new Double(accessfee + serviceOrder.getSpendLimitLabor());
		if (null != serviceOrder.getSpendLimitParts()) {
			total = MoneyUtil.add(total,serviceOrder.getSpendLimitParts());
		}
		Double availBal = transBo.getAvailableBalance(avo);
		if (serviceOrder.getFundingAmountCC() != null) {
			if (serviceOrder.getFundingAmountCC().doubleValue() > availBal) {
				return false;
			} else {
				return true;
			}
		}
		// Check increase amount before total.
		if (increaseAmt > 0.0) {
			if (increaseAmt > availBal) {
				return false;
			} else {
				return true;
			}
		}
		if (state == DRAFT_STATUS) {
			if (total > availBal) {
				return false;
			} else {
				return true;
			}

		}
		return true;
	}

	public boolean buyerOverMaxLimit(Double postingFee, Double labor, Double parts, SecurityContext securityContext) throws BusinessServiceException {

		if (null == labor) {
			return false;
		}

		if (null == postingFee) {
			postingFee = new Double(0.00);
		}

		double max = OrderConstants.NO_SPEND_LIMIT;
		;
		if (null != securityContext) {
			max = securityContext.getMaxSpendLimitPerSO();
		}
		double total = postingFee.doubleValue() + labor.doubleValue();
		if (null != parts) {
			total += parts.doubleValue();
		}

		// check total against available balance
		if (max == OrderConstants.NO_SPEND_LIMIT || max >= total) {
			return false;
		} else {
			return true;
		}
	}

	private boolean processFundingType(Integer fundingType) {
		boolean result = true;

		// Always return false if FundingTypeId is null or its Non-funded or
		// Direct-Funded
		if (fundingType == null || LedgerConstants.FUNDING_TYPE_NON_FUNDED == fundingType.intValue() 
				|| LedgerConstants.SHC_FUNDING_TYPE == fundingType.intValue() 
				|| LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER == fundingType.intValue()) {
			result = false;
		}

		return result;
	}

	public boolean checkStateForRoute(String serviceOrderId) {
		Integer state = null;
		try {
			state = getServiceOrderDao().checkWfState(serviceOrderId);
			if (state == null) {
				state = DRAFT_STATUS;
			}
		} catch (DataServiceException e) {
			logger.error("error " + e);
		}
		if (state == DRAFT_STATUS || state == ROUTED_STATUS || state == EXPIRED_STATUS) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkStateForComplete(int wfState) {
		boolean result = false;

		if (wfState == ACTIVE_STATUS)
			return true;

		return result;
	}

	private boolean checkStateForVoid(int wfState) {
		boolean result = false;

		if (wfState == ROUTED_STATUS)
			return true;
		else if (wfState == EXPIRED_STATUS)
			return true;
		else if (wfState == DRAFT_STATUS)
			return true;

		return result;
	}

	private boolean checkStateForCancellationInAccepted(int wfState) {
		boolean result = false;

		if (wfState == ACCEPTED_STATUS)
			return true;

		return result;
	}

	private boolean checkStateForCancellationInActive(int wfState) {
		boolean result = false;

		if (wfState == ACTIVE_STATUS)
			return true;

		return result;
	}

	private boolean isValidServiceOrderStatus(int wfStateId) {

		if (wfStateId == OrderConstants.CANCELLED_STATUS || wfStateId == OrderConstants.COMPLETED_STATUS || wfStateId == OrderConstants.VOIDED_STATUS || wfStateId == OrderConstants.DELETED_STATUS || wfStateId == OrderConstants.CLOSED_STATUS) {
			return false;
		} else {
			return true;
		}
	}

	protected boolean validatecallinguser(Integer buyerID, ServiceOrder serviceOrder) {

		if (serviceOrder.getBuyer().getBuyerId().equals(buyerID)) {
			return true;
		} else {
			logger.error("The BuyerId in ServiceOrder = [" + serviceOrder.getBuyer().getBuyerId() + "] is not matching with buyerId passed in business method = [" + buyerID + "] - Not Authorized !!!");
			return false;
		}
	}

	public boolean checkStateForIncreseSpendLimit(int wfState) {

		boolean result = false;

		if (wfState == ACCEPTED_STATUS)
			return true;
		else if (wfState == ACTIVE_STATUS)
			return true;
		else if (wfState == PROBLEM_STATUS)
			return true;
		else if (wfState == CONDITIONAL_OFFER_STATUS)
			return true;
		else if (wfState == ROUTED_STATUS)
			return true;
		// For OMS injection, price update is allowed for the following.
		else if (wfState == COMPLETED_STATUS)
			return true;
		else if (wfState == DRAFT_STATUS)
			return true;
		else if (wfState == EXPIRED_STATUS)
			return true;
		return result;
	}

	// validates the work flow order associated with the service order to
	// reject service Order
	public boolean checkStateofSOforReject(int wfState) {

		boolean result = false;

		if (wfState == ROUTED_STATUS)
			return true;
		return result;
	}

	public List<SoChangeDetailVo> getSoChangeDetailVoList(String soId) throws DataServiceException {
		List<SoChangeDetailVo> list = getLoggingDao().getSOChangeDetail(soId);
		return list;
	}

	
	public String getServiceLocTimeZone(String soId) throws DataServiceException 
	{	
		String serviceLocationTimezone=getServiceOrderDao().getServiceLocTimeZone_soHdr(soId);
		return serviceLocationTimezone;
	}
	
	/*-----------------------------------------------------------------
	 * Checks if the service order is in proper state for problem
	 * @param 		wfStateId - State of Service Order
	 * @returns 	Boolean
	 *-----------------------------------------------------------------*/
	private boolean checkStateofSOforProblem(int wfState) {
		boolean result = false;

		if (wfState == ACTIVE_STATUS)
			result = true;
		else if (wfState == COMPLETED_STATUS)
			result = true;
		else if (wfState == ACCEPTED_STATUS)
			result = true;
		else if (wfState == PROBLEM_STATUS)
			result = true;
		return result;
	}

	/*-----------------------------------------------------------------
	 * Checks if the service order is in proper state for issue 
	 * resolution
	 * @param 		wfStateId - State of Service Order
	 * @returns 	Boolean
	 *-----------------------------------------------------------------*/
	private boolean checkStateofSOforResolution(int wfState) {
		boolean result = false;

		if (wfState == PROBLEM_STATUS)
			result = true;

		logger.info("result in checkStateofSOforResolution: " + result);

		return result;
	}

	/*-----------------------------------------------------------------
	 * Get the problem details to display on resolution screen
	 * @param 		soId - ServiceOrder Id
	 * @returns 	ProblemResolutionSoVO
	 *-----------------------------------------------------------------*/
	public ProblemResolutionSoVO getProblemDesc(String soId) throws BusinessServiceException {
		logger.info("----Start of ServiceOrderBO.getProblemDesc----");
		ProblemResolutionSoVO pbResolutionVo = null;

		try {
			pbResolutionVo = getServiceOrderDao().getProblemDesc(soId);
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			final String error = "Error in retrieving problem description";
			throw new BusinessServiceException(error, dse);
		}
		logger.info("----End of ServiceOrderBO.getProblemDesc----");
		return pbResolutionVo;
	}

	/*-----------------------------------------------------------------
	 * This is method will be used to add problem
	 * @param 		strSoId - ServiceOrder Id
	 * @param		intSubStatusId - problem id
	 * @param		strComment - problem or issue resolution description
	 * @param		intEntityId - Either it is ProviderId or BuyerId
	 * @param		intRoleType - RoleType - it is Provider or Buyer
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse reportProblem(String strSoId, int intSubStatusId, String strComment, int intEntityId, int intRoleType, String pbDesc, String loggedInUser, boolean skipAlertLogging, SecurityContext securityContext) throws BusinessServiceException {
		logger.debug("----Start of ServiceOrderBO.reportProblem----");
		int updCnt = 0;
		int oldWfState = 0;
		int newWfState = 0;
		boolean stChkResult = false;
		List<String> arr = new ArrayList<String>();

		ProcessResponse processResp = new ProcessResponse();
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(strSoId);
			if (serviceOrder == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}

			oldWfState = serviceOrder.getWfStateId().intValue();

			// returns error if the work flow state has error
			if (oldWfState <= 0)
				return setErrorMsg(processResp, WFSTATE_NOT_FOUND);

			stChkResult = checkStateofSOforProblem(oldWfState);
			if (stChkResult) {
				newWfState = OrderConstants.PROBLEM_STATUS;

				serviceOrder.setWfStateId(newWfState);
				serviceOrder.setWfSubStatusId(intSubStatusId);
				serviceOrder.setProblemResolutionComment(strComment);
				serviceOrder.setLastChngStateId(oldWfState);

				// Set problem report date to current Timestamp
				java.util.Date currentDateTime = new java.util.Date();
				Timestamp currentTimestamp = new Timestamp(currentDateTime.getTime());
				serviceOrder.setProblemDate(currentTimestamp);

				updCnt = getServiceOrderDao().reportProblemResolution(serviceOrder);

				if (updCnt == 0) {
					processResp.setCode(USER_ERROR_RC);
					processResp.setSubCode(USER_ERROR_RC);
					arr.add(PROBLEM_ADD_FAILURE);
				} else {
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);
					arr.add(PROBLEM_ADD_SUCCESS);
				}
				processResp.setMessages(arr);

			} else {
				return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE_PROBLEM);
			}

		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			String error = "";
			error = PROBLEM_ADD_FAILURE;
			throw new BusinessServiceException(error, dse);
		}
		logger.debug("----End of ServiceOrderBO.reportProblem----");
		return processResp;
	}

	/*-----------------------------------------------------------------
	 * This is method will be used to add resolution
	 * @param 		strSoId - ServiceOrder Id
	 * @param		intSubStatusId - problem id
	 * @param		strComment - problem or issue resolution description
	 * @param		intEntityId - Either it is ProviderId or BuyerId
	 * @param		intRoleType - RoleType - it is Provider or Buyer
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse reportResolution(String strSoId, int intSubStatusId, String strComment, int intEntityId, int intRoleType, String strPbDesc, String strPbDetails, String strLoggedInUser, SecurityContext securityContext) throws BusinessServiceException {
		logger.debug("----Start of ServiceOrderBO.reportResolution----");
		String strStartTime = "";
		int updCnt = 0;
		int oldWfState = 0;
		int currentState = 0;
		int newWfState = 0;
		boolean blnChkResult = false;
		boolean blnDateCheck = false;
		long timeMiliSeconds = 0;

		List<String> arr = new ArrayList<String>();

		ProcessResponse processResp = new ProcessResponse();
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(strSoId);
			if (serviceOrder == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}

			oldWfState = serviceOrder.getLastChngStateId();
			currentState = serviceOrder.getWfStateId();
			if (logger.isDebugEnabled()) {
				logger.debug("oldWfState : " + oldWfState);
				logger.debug("currentState : " + currentState);
			}
			// returns error if the work flow state has error
			if (currentState <= 0)
				return setErrorMsg(processResp, WFSTATE_NOT_FOUND);

			// checks whether the status is appropriate to report a resolution
			blnChkResult = checkStateofSOforResolution(currentState);
			if (blnChkResult) {
				if (serviceOrder.getServiceDate1() != null) {
					java.util.Date today = new java.util.Date();
					java.sql.Timestamp tsToday = new java.sql.Timestamp(today.getTime());
					if (logger.isDebugEnabled()) {
						logger.debug("time start : " + serviceOrder.getServiceTimeStart());
					}
					strStartTime = serviceOrder.getServiceTimeStart();
					timeMiliSeconds = TimeUtils.convertStringTimeToMilliSeconds(strStartTime);
					java.sql.Timestamp tsServDate = java.sql.Timestamp.valueOf(serviceOrder.getServiceDate1().toString());
					long tsToday1 = tsToday.getTime();
					long tsServDate1 = tsServDate.getTime() + timeMiliSeconds;

					if (logger.isDebugEnabled()) {
						logger.debug(tsToday1);
						logger.debug(tsServDate1);
					}

					if (tsServDate1 >= tsToday1) {
						logger.debug("tsServDate1>=tsToday1");
						blnDateCheck = true;
					}
				}

				if (logger.isDebugEnabled())
					logger.debug("blnDateCheck : " + blnDateCheck);

				if (blnDateCheck) {
					newWfState = ACCEPTED_STATUS;
				} else {
					newWfState = ACTIVE_STATUS;
				}

				serviceOrder.setWfStateId(newWfState);
				if (intSubStatusId == 0)
					serviceOrder.setWfSubStatusId(null);
				serviceOrder.setProblemResolutionComment(strComment);
				serviceOrder.setLastChngStateId(currentState);

				// Set problem report date to null
				serviceOrder.setProblemDate(null);

				updCnt = getServiceOrderDao().reportProblemResolution(serviceOrder);

				if (updCnt == 0) {
					processResp.setCode(SYSTEM_ERROR_RC);
					processResp.setSubCode(SYSTEM_ERROR_RC);
					arr.add(RESOLUTION_ADD_FAILURE);
				} else {
					processResp.setCode(VALID_RC);
					processResp.setSubCode(VALID_RC);

					arr.add(RESOLUTION_ADD_SUCCESS + newWfState);
				}
				processResp.setMessages(arr);

			} else {
				return setErrorMsg(processResp, UNAPPROPRIATE_WFSTATE_RESOLUTION);
			}

		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			String error = "";
			error = RESOLUTION_ADD_FAILURE;
			throw new BusinessServiceException(error, dse);
		}
		logger.debug("----End of ServiceOrderBO.reportProblemResolution----");
		return processResp;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO#getAllProviders(java.lang.String, java.lang.Integer)
	 */
	public List<RoutedProvider> getAllProviders(String soId, Integer buyerId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting all providers for soId=" + soId + " and buyerId=" + buyerId);
		}
		List<RoutedProvider> routedProviders;
		List<Integer> resourceIds = new ArrayList<Integer>();
		List<Integer> vendorIds = new ArrayList<Integer>();
		ServiceOrder so;
		try {
			so = getServiceOrderDao().getServiceOrder(soId);
			routedProviders = so.getRoutedResources();
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
			routedProviders = null;
			so = null;
		}
			

		convertTimesToGivenTimezone(routedProviders, resourceIds, vendorIds, so);
		// Get the ratings data only when buyer id is available
		if (buyerId != null) {
			populateRatingMktmakerresponseSocount(soId, buyerId,
					routedProviders, resourceIds, vendorIds);
		}
		return routedProviders;
	}

	/**
	 * @param routedProviders
	 * @param resourceIds
	 * @param vendorIds
	 * @param so
	 */
	private void convertTimesToGivenTimezone(
			List<RoutedProvider> routedProviders, List<Integer> resourceIds,
			List<Integer> vendorIds, ServiceOrder so) {
		for (RoutedProvider routedProvider : routedProviders) {
			if (routedProvider.getConditionalChangeDate1() != null && routedProvider.getConditionalStartTime() != null) {
				final HashMap<String, Object> conditionalDate1 = TimeUtils.convertGMTToGivenTimeZone(routedProvider.getConditionalChangeDate1(), routedProvider.getConditionalStartTime(), so.getServiceLocationTimeZone());
				if (conditionalDate1 != null && !conditionalDate1.isEmpty()) {
					routedProvider.setConditionalChangeDate1((Timestamp) conditionalDate1.get(OrderConstants.GMT_DATE));
					routedProvider.setConditionalStartTime((String) conditionalDate1.get(OrderConstants.GMT_TIME));
					if (so.getServiceLocationTimeZone() != null) {
						routedProvider.setServiceLocationTimeZone(so.getServiceLocationTimeZone());
					}
				}
			}

			if (routedProvider.getConditionalChangeDate2() != null && routedProvider.getConditionalEndTime() != null) {
				final HashMap<String, Object> conditionalDate2 = TimeUtils.convertGMTToGivenTimeZone(routedProvider.getConditionalChangeDate2(), routedProvider.getConditionalEndTime(), so.getServiceLocationTimeZone());
				if (conditionalDate2 != null && !conditionalDate2.isEmpty()) {
					routedProvider.setConditionalChangeDate2((Timestamp) conditionalDate2.get(OrderConstants.GMT_DATE));
					routedProvider.setConditionalEndTime((String) conditionalDate2.get(OrderConstants.GMT_TIME));
				}
			}
			if (routedProvider.getConditionalExpirationDate() != null) {
				Date expDate = (Date) routedProvider.getConditionalExpirationDate();
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
				String formatedDate = sdf.format(expDate);
				String expTime = TimeUtils.getTimePart(formatedDate);
				final HashMap<String, Object> expirationDate = TimeUtils.convertGMTToGivenTimeZone(routedProvider.getConditionalExpirationDate(), expTime, so.getServiceLocationTimeZone());
				if (expirationDate != null && !expirationDate.isEmpty()) {
					routedProvider.setConditionalExpirationDate((Timestamp) expirationDate.get(OrderConstants.GMT_DATE));
					routedProvider.setConditionalExpirationTime((String) expirationDate.get(OrderConstants.GMT_TIME));
				}
			}
			if (so.getServiceLocationTimeZone() != null) {

				String dlsFlag = "";
				try {
					dlsFlag = getLookupBO().getDaylightSavingsFlg(so.getServiceLocation().getZip());
				} catch (DataServiceException dataServiceException) {
					logger.error("Exception :" + dataServiceException);
				}
				if ("Y".equals(dlsFlag)) {
					TimeZone tz = TimeZone.getTimeZone(so.getServiceLocationTimeZone());
					Timestamp timeStampDate = null;
					try {
						java.util.Date dt = (java.util.Date) TimeUtils.combineDateTime(so.getServiceDate1(), so.getServiceTimeStart());
						timeStampDate = new Timestamp(dt.getTime());
					} catch (ParseException pe) {
						logger.error("Exception :" + pe);
					}
					if (null != timeStampDate) {
						boolean isDLSActive = tz.inDaylightTime(timeStampDate);
						if (isDLSActive) {
							routedProvider.setServiceLocationTimeZone(getDSTTimezone(so));
						} else {
							routedProvider.setServiceLocationTimeZone(getStandardTimezone(so));
						}
					}
				} else {
					routedProvider.setServiceLocationTimeZone(getStandardTimezone(so));

				}
			}
			resourceIds.add(routedProvider.getResourceId());
			vendorIds.add(routedProvider.getVendorId());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO#getRoutedProvidersWithBasicInfo(java.lang.String)
	 */
	public List<RoutedProvider> getRoutedProvidersWithBasicInfo(String soId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting all providers wtih basic info for soId=" + soId);
		}
		List<RoutedProvider> routedProviders = null;
		try {
			routedProviders = getServiceOrderDao().getRoutedProvidersWithBasicInfo(soId);
		} catch (DataServiceException e) {
			logger.error("Error getting routed providers with basic info for so id " + soId, e);
		}
		return routedProviders;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO#getRescheduleInfo(java.lang.String)
	 */
	public RescheduleVO getRescheduleInfo(String soId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting reschedule info for soId=" + soId);
		}
		RescheduleVO rescheduleVo = null;
		try {
			rescheduleVo = getServiceOrderDao().getRescheduleInfo(soId);
		} catch (DataServiceException e) {
			logger.error("Error getting reschedule info for so id " + soId, e);
		}
		return rescheduleVo;
	}
	
	
	public RescheduleVO getBuyerRescheduleInfo(String soId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting Buyer reschedule info for soId=" + soId);
		}
		RescheduleVO rescheduleVo = null;
		try {
			rescheduleVo = getServiceOrderDao().getBuyerRescheduleInfo(soId);
		} catch (DataServiceException e) {
			logger.error("Error getting Buyer reschedule info for so id " + soId, e);
		}
		return rescheduleVo;
	}

    public Integer getBuyerId(String soId){
    	Integer buyerId=null;
    	try{
    		buyerId= getServiceOrderDao().getBuyerIdForSo(soId);
    	}catch(DataServiceException dse){
    		logger.error("Error getting buyer id  for so id:" + soId + dse.getMessage());
    	}
    	return buyerId;
    }
	/**
	 * @param soId
	 * @param buyerId
	 * @param routedProviders
	 * @param resourceIds
	 * @param vendorIds
	 * @throws BusinessServiceException
	 */
	private void populateRatingMktmakerresponseSocount(String soId, Integer buyerId, 
			List<RoutedProvider> routedProviders, List<Integer> resourceIds, List<Integer> vendorIds){
		List<SurveyRatingsVO> providerStarList = null;
		Map<Integer, MarketMakerProviderResponse> providersResponseMap = null;
		Map<Integer, Integer> soCountsMap = null;
		// Get the Rating data for the routed providers
		ProviderIdsVO vendorResourceIds = this.extractVendorResourceIds(routedProviders);
		try {
			//providerStarList = getSurveyDao().getFastLookupRating(buyerId, vendorResourceIds.getProviderIds());
			providerStarList = getExtendedSurveyDAO().getBuyerMyRatings(buyerId, vendorResourceIds.getProviderIds());
			providersResponseMap = getMarketProvidersResponseMap(soId, resourceIds);
			soCountsMap = getServiceOrderDao().getPostedSoCountForVendors(vendorIds);
			for (RoutedProvider routedProvider : routedProviders) {
				// Set rating data into RoutedProvider
				if (providerStarList != null) {
					for (SurveyRatingsVO stars : providerStarList) {
						if (routedProvider.getResourceId().intValue() == stars.getVendorResourceID().intValue()) {
							routedProvider.setProviderStarRating(stars);
						}
					}
						
				}
				// Set Market Maker Provider Response into RoutedProvider
				routedProvider.setMktMakerProvResponse(providersResponseMap.get(routedProvider.getResourceId()));
				
				// Set SO Count into RoutedProvider
				Integer soCount = 0;
				if (routedProvider.getVendorId() != null && soCountsMap.get(routedProvider.getVendorId().longValue()) != null) {
					soCount = soCountsMap.get(routedProvider.getVendorId().longValue());
				}
				routedProvider.setPostedSOCount(soCount);
			}
		} catch (Exception e) {
			logger.error("Error occured in ServiceOrderBO.getAllProviders() for soId=" + soId + " and buyerId" + buyerId, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO#getMarketProvidersResponseMap(java.lang.String, java.util.List)
	 */
	public Map<Integer, MarketMakerProviderResponse> getMarketProvidersResponseMap(String soId, List<Integer> resourceIds) 
		throws BusinessServiceException {
		
		Map<Integer, MarketMakerProviderResponse> providersResponse = new HashMap<Integer, MarketMakerProviderResponse>();
		List<MarketMakerProviderResponse> marketMakerProvidersResponse = new ArrayList<MarketMakerProviderResponse>();
		try {
			if (StringUtils.isNotBlank(soId) && resourceIds != null && resourceIds.size() > 0) {
				marketMakerProvidersResponse = getServiceOrderDao().getMarketMakerProvidersResponse(soId, resourceIds);
			}			
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}
		for (MarketMakerProviderResponse marketMakerProviderResponse : marketMakerProvidersResponse) {
			providersResponse.put(marketMakerProviderResponse.getResourceId(), marketMakerProviderResponse);
		} 
		return providersResponse;		
	}

	/**
	 * Method to update Market comments from SOD response status page
	 * 
	 * @param routedProviders
	 *            List of RoutedProvider
	 * @return ProcessResponse The Value Object containing Resource Ids for
	 *         given providers
	 */

	public ProcessResponse updateMktMakerComments(RoutedProvider routedProvider) {
		ProcessResponse processResp = new ProcessResponse();
		try {

			getServiceOrderDao().updateMktMakerComments(routedProvider.getMktMakerProvResponse());

		} catch (DataServiceException dse) {
			logger.error("Error updating market maker comment for so ID " + routedProvider.getSoId()
					+ " and " + routedProvider.getResourceId(), dse);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("Datastore error");
			return processResp;
		}

		return processResp;
	}

	public List<LookupVO> getCallStatusList() {
		return getServiceOrderDao().getCallStatusList();
	}

	/**
	 * Method to return Vendor Resource Ids from a list of RoutedProvider
	 * 
	 * @param routedProviders
	 *            List of RoutedProvider
	 * @return ProviderIdsVO The Value Object containing Resource Ids for given
	 *         providers
	 */
	private ProviderIdsVO extractVendorResourceIds(List<RoutedProvider> routedProviders) {
		ProviderIdsVO providerIds = new ProviderIdsVO();
		ArrayList<Integer> ids = null;

		if (routedProviders.size() > 0) {
			ids = new ArrayList<Integer>();
		}
		for (int i = 0; i < routedProviders.size(); i++) {
			ids.add(new Integer(routedProviders.get(i).getResourceId()));
		}
		providerIds.setProviderIds(ids);
		return providerIds;
	}

	private static void GMTToGivenTimeZone(ServiceOrder serviceOrder) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		HashMap<String, Object> rescheduleDate1 = null;
		HashMap<String, Object> rescheduleDate2 = null;
		String startTime = null;
		String endTime = null;

		startTime = serviceOrder.getServiceTimeStart();
		endTime = serviceOrder.getServiceTimeEnd();
		if (serviceOrder.getServiceDate1() != null && startTime != null) {
			serviceOrder.setServiceDateGMT1(serviceOrder.getServiceDate1());
			serviceOrder.setServiceTimeStartGMT(serviceOrder.getServiceTimeStart());
			serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate1(), startTime, serviceOrder.getServiceLocationTimeZone());
			if (serviceDate1 != null && !serviceDate1.isEmpty()) {
				serviceOrder.setServiceDate1((Timestamp) serviceDate1.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeStart((String) serviceDate1.get(OrderConstants.GMT_TIME));
			}
		}
		if (serviceOrder.getServiceDate2() != null && endTime != null) {
			serviceOrder.setServiceDateGMT2(serviceOrder.getServiceDate2());
			serviceOrder.setServiceTimeEndGMT(serviceOrder.getServiceTimeEnd());
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate2(), endTime, serviceOrder.getServiceLocationTimeZone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				serviceOrder.setServiceDate2((Timestamp) serviceDate2.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeEnd((String) serviceDate2.get(OrderConstants.GMT_TIME));
			}
		}

		// handle reschedule times
		startTime = serviceOrder.getRescheduleServiceTimeStart();
		endTime = serviceOrder.getRescheduleServiceTimeEnd();
		if (serviceOrder.getRescheduleServiceDate1() != null && startTime != null) {

			rescheduleDate1 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getRescheduleServiceDate1(), startTime, serviceOrder.getServiceLocationTimeZone());
			if (rescheduleDate1 != null && !rescheduleDate1.isEmpty()) {
				serviceOrder.setRescheduleServiceDate1((Timestamp) rescheduleDate1.get(OrderConstants.GMT_DATE));
				serviceOrder.setRescheduleServiceTimeStart((String) rescheduleDate1.get(OrderConstants.GMT_TIME));
			}
		}
		if (serviceOrder.getRescheduleServiceDate2() != null && endTime != null) {
			rescheduleDate2 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getRescheduleServiceDate2(), endTime, serviceOrder.getServiceLocationTimeZone());
			if (rescheduleDate2 != null && !rescheduleDate2.isEmpty()) {
				serviceOrder.setRescheduleServiceDate2((Timestamp) rescheduleDate2.get(OrderConstants.GMT_DATE));
				serviceOrder.setRescheduleServiceTimeEnd((String) rescheduleDate2.get(OrderConstants.GMT_TIME));
			}
		}
		

	}
	
	public static void GMTToGivenTimeZoneForSlots(ServiceOrder serviceOrder) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		String startTime = null;
		String endTime = null;
		
		for (ServiceDatetimeSlot serviceDatetimeSlot : serviceOrder.getServiceDatetimeSlots()) {
			
			startTime = serviceDatetimeSlot.getServiceStartTime();
			endTime = serviceDatetimeSlot.getServiceEndTime();
			
			if (serviceDatetimeSlot.getServiceStartDate() != null && startTime != null) {
				serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(serviceDatetimeSlot.getServiceStartDate(), startTime, serviceOrder.getServiceLocationTimeZone());
				if (serviceDate1 != null && !serviceDate1.isEmpty()) {
					serviceDatetimeSlot.setServiceStartDate((Timestamp) serviceDate1.get(OrderConstants.GMT_DATE));
					serviceDatetimeSlot.setServiceStartTime((String) serviceDate1.get(OrderConstants.GMT_TIME));
					serviceDatetimeSlot.setTimeZone(serviceOrder.getServiceLocationTimeZone());
				}
			}
			
			if (serviceDatetimeSlot.getServiceEndDate() != null && endTime != null) {

				serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(serviceDatetimeSlot.getServiceEndDate(), endTime, serviceOrder.getServiceLocationTimeZone());
				if (serviceDate2 != null && !serviceDate2.isEmpty()) {
					serviceDatetimeSlot.setServiceEndDate((Timestamp) serviceDate2.get(OrderConstants.GMT_DATE));
					serviceDatetimeSlot.setServiceEndTime((String) serviceDate2.get(OrderConstants.GMT_TIME));
					serviceDatetimeSlot.setTimeZone(serviceOrder.getServiceLocationTimeZone());
				}
			}
		}
			
		
	}
	
	public void GivenTimeZoneToGMTForSlots(ServiceOrder serviceOrder) {

		String serviceLocationTimeZone = null;
		if (serviceOrder.getServiceLocation() != null && serviceOrder.getServiceLocation().getZip() != null) {
			serviceLocationTimeZone = getServiceOrderDao().getServiceLocationTimeZone(serviceOrder.getServiceLocation().getZip());
		} else {
			serviceLocationTimeZone = OrderConstants.EST_ZONE;
		}

		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		String startTime = null;
		String endTime = null;
		
		for (ServiceDatetimeSlot serviceDatetimeSlot : serviceOrder.getServiceDatetimeSlots()) {
			
			startTime = serviceDatetimeSlot.getServiceStartTime();
			endTime = serviceDatetimeSlot.getServiceEndTime();

			if (serviceDatetimeSlot.getServiceStartDate() != null && startTime != null) {

				serviceDate1 = TimeUtils.convertToGMT(serviceDatetimeSlot.getServiceStartDate(), startTime, serviceLocationTimeZone);
				if (serviceDate1 != null && !serviceDate1.isEmpty()) {
					serviceDatetimeSlot.setServiceStartDate((Timestamp) serviceDate1.get(OrderConstants.GMT_DATE));
					serviceDatetimeSlot.setServiceStartTime((String) serviceDate1.get(OrderConstants.GMT_TIME));
				}
			}
			if (serviceDatetimeSlot.getServiceEndDate() != null && endTime != null) {
				serviceDate2 = TimeUtils.convertToGMT(serviceDatetimeSlot.getServiceEndDate(), endTime, serviceLocationTimeZone);
				if (serviceDate2 != null && !serviceDate2.isEmpty()) {
					serviceDatetimeSlot.setServiceEndDate((Timestamp) serviceDate2.get(OrderConstants.GMT_DATE));
					serviceDatetimeSlot.setServiceEndTime((String) serviceDate2.get(OrderConstants.GMT_TIME));
				}
			}
		}
		
		TimeZone timezone = TimeZone.getTimeZone(serviceLocationTimeZone);
		int offset = Math.round(timezone.getOffset((new Date()).getTime()) / (1000 * 60 * 60));
		
		serviceOrder.setServiceDateTimezoneOffset(new Integer(offset));
		serviceOrder.setServiceLocationTimeZone(serviceLocationTimeZone);
	}

	/**
	 * 
	 */
	public void GivenTimeZoneToGMT(ServiceOrder serviceOrder) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		String startTime = null;
		String endTime = null;

		startTime = serviceOrder.getServiceTimeStart();
		endTime = serviceOrder.getServiceTimeEnd();
		String serviceLocationTimeZone = null;
		if (serviceOrder.getServiceLocation() != null && serviceOrder.getServiceLocation().getZip() != null) {
			serviceLocationTimeZone = getServiceOrderDao().getServiceLocationTimeZone(serviceOrder.getServiceLocation().getZip());
		} else {
			serviceLocationTimeZone = OrderConstants.EST_ZONE;
		}
		if (serviceOrder.getServiceDate1() != null && startTime != null) {

			serviceDate1 = TimeUtils.convertToGMT(serviceOrder.getServiceDate1(), startTime, serviceLocationTimeZone);
			if (serviceDate1 != null && !serviceDate1.isEmpty()) {
				serviceOrder.setServiceDate1((Timestamp) serviceDate1.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeStart((String) serviceDate1.get(OrderConstants.GMT_TIME));
			}
		}
		if (serviceOrder.getServiceDate2() != null && endTime != null) {
			serviceDate2 = TimeUtils.convertToGMT(serviceOrder.getServiceDate2(), endTime, serviceLocationTimeZone);
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				serviceOrder.setServiceDate2((Timestamp) serviceDate2.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeEnd((String) serviceDate2.get(OrderConstants.GMT_TIME));
			}
		}
		if (null != serviceLocationTimeZone) {
			TimeZone timezone = TimeZone.getTimeZone(serviceLocationTimeZone);
			int offset = Math.round(timezone.getOffset((new Date()).getTime()) / (1000 * 60 * 60));
			serviceOrder.setServiceDateTimezoneOffset(new Integer(offset));
		}
		serviceOrder.setServiceLocationTimeZone(serviceLocationTimeZone);
	}

	/**
	 * Renamed it from getServiceOrder to avoid AOP call. All method starting
	 * with process and get will be intercenpted by AOP
	 */
	public ServiceOrder getServiceOrder(String soId) throws BusinessServiceException {
		ServiceOrder serviceOrder = null;
		List<ServiceDatetimeSlot> soDateTimeSlots = null;

		try {
			serviceOrder = getServiceOrderDao().getServiceOrder(soId);
		   

			if (serviceOrder != null) {
				GMTToGivenTimeZone(serviceOrder);
				if(OrderConstants.ROUTED.equalsIgnoreCase(serviceOrder.getStatus())) {
					if(serviceOrder.getServiceDateTypeId()==3){
					 soDateTimeSlots = getSODateTimeSlots(soId);
					 serviceOrder.setServiceDatetimeSlots(soDateTimeSlots);
					 if(null !=serviceOrder.getServiceDatetimeSlots()){
					    GMTToGivenTimeZoneForSlots(serviceOrder);
					 }
					}
				}
				
			} else {
				if (logger.isDebugEnabled())
					logger.debug("getServiceOrder() - Error getting service order for " + soId);
			}

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return serviceOrder;
	}
	
	//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
		public String getMethodOfRouting(String soId)throws BusinessServiceException
		{
			String methodOfRouting=null;
			try {
				methodOfRouting = getServiceOrderDao().getMethodOfRouting(soId);

				} catch (DataServiceException e) {
				logger.error("Exception :" + e);
			}
			return methodOfRouting;
			
		}
	/**
	 * For getting service order details to be displayed in cancellation pop up
	 */
	public SoCancelVO getServiceOrderForCancel(String soId) throws BusinessServiceException {
		SoCancelVO soCancelVO = null;

		try {
			soCancelVO = getServiceOrderDao().getServiceOrderForCancel(soId);

			if (soCancelVO != null) {
				HashMap<String, Object> serviceDate1 = null;
				HashMap<String, Object> serviceDate2 = null;
				HashMap<String, Object> rescheduleDate1 = null;
				HashMap<String, Object> rescheduleDate2 = null;
				String startTime = null;
				String endTime = null;

				startTime = soCancelVO.getServiceTimeStart();
				endTime = soCancelVO.getServiceTimeEnd();
				if (soCancelVO.getServiceDate1() != null && startTime != null) {

					serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(soCancelVO.getServiceDate1(), startTime, soCancelVO.getServiceLocationTimeZone());
					if (serviceDate1 != null && !serviceDate1.isEmpty()) {
						soCancelVO.setServiceDate1((Timestamp) serviceDate1.get(OrderConstants.GMT_DATE));
						soCancelVO.setServiceTimeStart((String) serviceDate1.get(OrderConstants.GMT_TIME));
					}
				}
				if (soCancelVO.getServiceDate2() != null && endTime != null) {
					serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(soCancelVO.getServiceDate2(), endTime, soCancelVO.getServiceLocationTimeZone());
					if (serviceDate2 != null && !serviceDate2.isEmpty()) {
						soCancelVO.setServiceDate2((Timestamp) serviceDate2.get(OrderConstants.GMT_DATE));
						soCancelVO.setServiceTimeEnd((String) serviceDate2.get(OrderConstants.GMT_TIME));
					}
				}
			} else {
				if (logger.isDebugEnabled())
					logger.debug("getServiceOrderForCancel() - Error getting service order for " + soId);
			}

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return soCancelVO;
	}
	
	public ServiceOrder getGroupedServiceOrders(String groupId) throws BusinessServiceException{
		ServiceOrder serviceOrders = new ServiceOrder();

		try {
			serviceOrders = getServiceOrderDao().getGroupedServiceOrders(groupId);

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return serviceOrders;
	}
	
	public ServiceOrder getServiceOrdersForGroup(String groupId,List<String> responseFilters) throws BusinessServiceException {

		ServiceOrder serviceOrders = new ServiceOrder();

		try {
			serviceOrders = getServiceOrderDao().getServiceOrdersForGroup(groupId,responseFilters);

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return serviceOrders;
	}

	public List<String> getServiceOrderIDsForGroup(String groupId) throws BusinessServiceException {

		List<String> soIdList = new ArrayList<String>();

		try {
			soIdList = getServiceOrderDao().getServiceOrderIDsForGroup(groupId);
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}

		return soIdList;
	}

	public boolean isValidServiceOrder(String soID) {
		boolean valid = false;
		if (getServiceOrderDao().checkForValidServiceOrderID(soID) > 0) {
			valid = true;
		}
		return valid;
	}

	public boolean isAValidServiceOrder(Integer resourceId, String soId) {
		return getServiceOrderDao().checkIfResourceAcceptedServiceOrder(resourceId, soId);
	}

	public List<String> findValidServiceOrders(Integer resourceId, String last6SoId) {
		return getServiceOrderDao().findValidServiceOrders(resourceId, last6SoId);
	}

	public ByteArrayOutputStream getPDFForSO(String soId) {

		PDFGenerator pdf = new PDFGenerator();
		ServiceOrder so = new ServiceOrder();
		ByteArrayOutputStream out = null;
		List<DocumentVO> docList = null;
		DocumentVO documentVO = null;
		ProviderDocumentVO acceptedProviderVO = null;
		try {
			so = getServiceOrderDao().getServiceOrder(soId);
			Integer buyerId = so.getBuyerId();
			boolean isPermitInd = buyerFeatureSetBO.validateFeature(buyerId, "TASK_LEVEL");
			so.getBuyer().setPermitInd(isPermitInd);
			GMTToGivenTimeZone(so);
			docList = getDocumentDao().getDocumentsMetaDataByEntity(soId, null);			
			if (so != null && null != so.getLogoDocumentId()) {
				try {
					documentVO = getDocumentBO().retrieveBuyerDocumentByDocumentId(so.getLogoDocumentId());
				} catch (BusinessServiceException e) {
					logger.error("Unable to get document", e);
				}
			}
			//change made for sl-17429:provider image for non -RI buyers
			if(!(so.getWfStateId().equals(OrderConstants.VOIDED_STATUS)||
					so.getWfStateId().equals(OrderConstants.DELETED_STATUS)||
					so.getWfStateId().equals(OrderConstants.DRAFT_STATUS)||
					so.getWfStateId().equals(OrderConstants.ROUTED_STATUS))){
				acceptedProviderVO = providerInfoPagesBO.getProviderPrimaryPhoto(so.getAcceptedResourceId());
				if(acceptedProviderVO.getDocDetails() != null && acceptedProviderVO.getDocDetails().getBlobBytes() !=null){
					if(DocumentUtils.IsResizable(acceptedProviderVO.getDocDetails().getBlobBytes(), SOPDFConstants.DEFAULT_PROV_PICTURE_WIDTH, SOPDFConstants.DEFAULT_PROV_PICTURE_HEIGHT)){
					byte[] resizedProvPicture = DocumentUtils.resizeoImage(acceptedProviderVO.getDocDetails().getBlobBytes(), SOPDFConstants.DEFAULT_PROV_PICTURE_WIDTH, SOPDFConstants.DEFAULT_PROV_PICTURE_HEIGHT);
					acceptedProviderVO.getDocDetails().setBlobBytes(resizedProvPicture);
				}}
				so.setAcceptedProviderDocument(acceptedProviderVO);
			}
			/* Sl-16834 : If location is null, get the location of the buyer 
			 * admin and set*/
			LocationVO primBuyerLocation = so.getAltBuyerResource().getBuyerResPrimaryLocation();
			if(null == primBuyerLocation){
				primBuyerLocation = so.getBuyer().getBuyerPrimaryLocation();
				so.getAltBuyerResource().setBuyerResPrimaryLocation(primBuyerLocation);
			}
			//SL-17873 removing the Cancelled and Deleted tasks from service order object
			List<ServiceOrderTask> activeTaskList = new ArrayList<ServiceOrderTask>();
			if(null!=so.getTasks()){			
				for(ServiceOrderTask soTask: so.getTasks()){
					if(soTask.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)){
						activeTaskList.add(soTask);
					}
				}
			}
			so.setTasks(activeTaskList);
			//removed
			/*//get assignment type
			String  assignmentType = getAssignmentType(soId);
			so.setAssignmentType(assignmentType);*/
		} catch (DataServiceException e) {
			logger.error("Failed to create PDF for " + soId + " service order", e);
		} catch(BusinessServiceException ex){
			logger.error("Failed to create PDF for " + soId + " service order", ex);
		} catch(Exception exx){
			logger.error("Failed to create PDF for " + soId + " service order", exx);
		}
		if (so == null) {
			logger.error("Failed to create PDF for " + soId + " service order, reason: Service Order Does not Exist");
		} else {

			try { 
				// SPM-1340 : Resolution Comments to be appended in PDF
				// Including a mobile Indicator to identify the getPDF() call from getPDFForMobile() method.
				// Here indicator is false
				boolean mobileIndicator = false;
				boolean customerCopyOnlyInd = false;
				
				//SL-20760
				//Code added to fetch phone number and email address for buyer 513539 
				if(SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId())
				{
					String constellationPhone = providerSearchDao.getApplicationConstantValueFromDB(SOPDFConstants.CONSTELLATION_BUYER_PHONE_NO);
					if(StringUtils.isNotBlank(constellationPhone))
					{
						so.setConstellationBuyerPhone(constellationPhone);
					}
					
					String constellationEmail = providerSearchDao.getApplicationConstantValueFromDB(SOPDFConstants.CONSTELLATION_BUYER_EMAIL_ADDRESS);
					if(StringUtils.isNotBlank(constellationEmail))
					{
						so.setConstellationBuyerEmail(constellationEmail);
					}
					
				}
				
				out = pdf.getPDF(so, docList, documentVO, null, null, mobileIndicator, customerCopyOnlyInd); 
			} catch (Throwable t) {
				logger.error("Failed to create PDF for " + so.getSoId() + " service order", t);
			}
		}
		return out;
	}
	
	public ByteArrayOutputStream getPDFForMobile(String soId, List<Signature> signatureList, AddonPayment addOnPaymentDetails, boolean customerCopyOnlyInd) {
		
		PDFGenerator pdf = new PDFGenerator();
		ServiceOrder so = new ServiceOrder();
		ByteArrayOutputStream out = null;
		List<DocumentVO> docList = null;
		DocumentVO documentVO = null;
		ProviderDocumentVO acceptedProviderVO = null;
		try {
			so = getServiceOrderDao().getServiceOrder(soId);
			Integer buyerId = so.getBuyerId();
			boolean isPermitInd = buyerFeatureSetBO.validateFeature(buyerId, "TASK_LEVEL");
			so.getBuyer().setPermitInd(isPermitInd);
			GMTToGivenTimeZone(so);
			docList = getDocumentDao().getDocumentsMetaDataByEntity(soId, null);			
			if (so != null && null != so.getLogoDocumentId()) {
				try {
					documentVO = getDocumentBO().retrieveBuyerDocumentByDocumentId(so.getLogoDocumentId());
				} catch (BusinessServiceException e) {
					logger.error("Unable to get document", e);
				}
			}
			//change made for sl-17429:provider image for non -RI buyers
			if(!(so.getWfStateId().equals(OrderConstants.VOIDED_STATUS)||
					so.getWfStateId().equals(OrderConstants.DELETED_STATUS)||
					so.getWfStateId().equals(OrderConstants.DRAFT_STATUS)||
					so.getWfStateId().equals(OrderConstants.ROUTED_STATUS))){
				acceptedProviderVO = providerInfoPagesBO.getProviderPrimaryPhoto(so.getAcceptedResourceId());
				if(acceptedProviderVO.getDocDetails() != null && acceptedProviderVO.getDocDetails().getBlobBytes() !=null){
					if(DocumentUtils.IsResizable(acceptedProviderVO.getDocDetails().getBlobBytes(), SOPDFConstants.DEFAULT_PROV_PICTURE_WIDTH, SOPDFConstants.DEFAULT_PROV_PICTURE_HEIGHT)){
					byte[] resizedProvPicture = DocumentUtils.resizeoImage(acceptedProviderVO.getDocDetails().getBlobBytes(), SOPDFConstants.DEFAULT_PROV_PICTURE_WIDTH, SOPDFConstants.DEFAULT_PROV_PICTURE_HEIGHT);
					acceptedProviderVO.getDocDetails().setBlobBytes(resizedProvPicture);
				}}
				so.setAcceptedProviderDocument(acceptedProviderVO);
			}
			/* Sl-16834 : If location is null, get the location of the buyer 
			 * admin and set*/
			LocationVO primBuyerLocation = so.getAltBuyerResource().getBuyerResPrimaryLocation();
			if(null == primBuyerLocation){
				primBuyerLocation = so.getBuyer().getBuyerPrimaryLocation();
				so.getAltBuyerResource().setBuyerResPrimaryLocation(primBuyerLocation);
			}
			//SL-17873 removing the Cancelled and Deleted tasks from service order object
			List<ServiceOrderTask> activeTaskList = new ArrayList<ServiceOrderTask>();
			if(null!=so.getTasks()){			
				for(ServiceOrderTask soTask: so.getTasks()){
					if(soTask.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)){
						activeTaskList.add(soTask);
					}
				}
			}
			so.setTasks(activeTaskList);
			//removed
			/*//get assignment type
			String  assignmentType = getAssignmentType(soId);
			so.setAssignmentType(assignmentType);*/
		} catch (DataServiceException e) {
			logger.error("Failed to create PDF for " + soId + " service order", e);
		} catch(BusinessServiceException ex){
			logger.error("Failed to create PDF for " + soId + " service order", ex);
		} catch(Exception exx){
			logger.error("Failed to create PDF for " + soId + " service order", exx);
		}
		if (so == null) {
			logger.error("Failed to create PDF for " + soId + " service order, reason: Service Order Does not Exist");
		} else {

			try {
				// SPM-1340 : Resolution Comments to be appended in PDF
				// Including a mobile Indicator to identify the getPDF() call from getPDFForMobile() method.
				boolean mobileIndicator = true;
				
				//SL-20760
				//Code added to fetch phone number and email address for buyer 513539 
				if(SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == so.getBuyerId())
				{
					String constellationPhone = providerSearchDao.getApplicationConstantValueFromDB(SOPDFConstants.CONSTELLATION_BUYER_PHONE_NO);
					if(StringUtils.isNotBlank(constellationPhone))
					{
						so.setConstellationBuyerPhone(constellationPhone);
					}
					
					String constellationEmail = providerSearchDao.getApplicationConstantValueFromDB(SOPDFConstants.CONSTELLATION_BUYER_EMAIL_ADDRESS);
					if(StringUtils.isNotBlank(constellationEmail))
					{
						so.setConstellationBuyerEmail(constellationEmail);
					}
					
				}
				
				out = pdf.getPDF(so, docList, documentVO, signatureList, addOnPaymentDetails, mobileIndicator, customerCopyOnlyInd);
			} catch (Throwable t) {
				logger.error("Failed to create PDF for " + so.getSoId() + " service order", t);
			}
		}
		return out;
	}
	
	//for SL_15642

	//for creating a merged PDF
	
	public ByteArrayOutputStream printPaperwork(List<String> checkedSoIds,
			List<String> checkedOptions) {

		PrintPaperWorkGenerator paperWorkGenerator = new PrintPaperWorkGenerator();
		ByteArrayOutputStream out = null;
		DocumentVO documentVO = null;
		List<DocumentVO> documentVOs = new ArrayList<DocumentVO>();
		ProviderDocumentVO acceptedProviderVO = null;
		List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
		ServiceOrder serviceOrder = new ServiceOrder();
		List<DocumentVO> docList = new ArrayList<DocumentVO>();
		List<List<DocumentVO>> docLists = new ArrayList<List<DocumentVO>>();
		for(String soId:checkedSoIds){
			try {

				try{
					serviceOrder = getServiceOrderDao().getServiceOrder(soId);
				}
				catch (DataServiceException e) {
					logger.error("Failed to get Service Order for " + soId + " service order", e);
				}
				Integer buyerId = serviceOrder.getBuyerId();
				boolean isPermitInd = false;
				if(serviceOrder.getPriceType()!= null && serviceOrder.getPriceType().equals("TASK_LEVEL")){
					isPermitInd = true;
				}
				serviceOrder.getBuyer().setPermitInd(isPermitInd);
				docList = getDocumentDao().getDocumentsMetaDataByEntity(soId, null);			
				GMTToGivenTimeZone(serviceOrder);
				if (serviceOrder != null && null != serviceOrder.getLogoDocumentId()) {
					try {
						documentVO = getDocumentBO().retrieveBuyerDocumentByDocumentId(serviceOrder.getLogoDocumentId());
					} catch (BusinessServiceException e) {
						logger.error("Unable to get document", e);
					}
				}

				if(!(serviceOrder.getWfStateId().equals(OrderConstants.VOIDED_STATUS)||
						serviceOrder.getWfStateId().equals(OrderConstants.DELETED_STATUS)||
						serviceOrder.getWfStateId().equals(OrderConstants.DRAFT_STATUS)||
						serviceOrder.getWfStateId().equals(OrderConstants.ROUTED_STATUS))){
					acceptedProviderVO = providerInfoPagesBO.getProviderPrimaryPhoto(serviceOrder.getAcceptedResourceId());
					if(acceptedProviderVO.getDocDetails() != null && acceptedProviderVO.getDocDetails().getBlobBytes() !=null){
						if(DocumentUtils.IsResizable(acceptedProviderVO.getDocDetails().getBlobBytes(), SOPDFConstants.DEFAULT_PROV_PICTURE_WIDTH, SOPDFConstants.DEFAULT_PROV_PICTURE_HEIGHT)){
							byte[] resizedProvPicture = DocumentUtils.resizeoImage(acceptedProviderVO.getDocDetails().getBlobBytes(), SOPDFConstants.DEFAULT_PROV_PICTURE_WIDTH, SOPDFConstants.DEFAULT_PROV_PICTURE_HEIGHT);
							acceptedProviderVO.getDocDetails().setBlobBytes(resizedProvPicture);
						}}
					serviceOrder.setAcceptedProviderDocument(acceptedProviderVO);
				}
				
				
				LocationVO primBuyerLocation = serviceOrder.getAltBuyerResource().getBuyerResPrimaryLocation();
				if(null == primBuyerLocation){
					primBuyerLocation = serviceOrder.getBuyer().getBuyerPrimaryLocation();
					serviceOrder.getAltBuyerResource().setBuyerResPrimaryLocation(primBuyerLocation);
				}


				List<ServiceOrderTask> activeTaskList = new ArrayList<ServiceOrderTask>();
				if(null!=serviceOrder.getTasks()){			
					for(ServiceOrderTask soTask: serviceOrder.getTasks()){
						if(soTask.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)){
							activeTaskList.add(soTask);
						}
					}
				}
				serviceOrder.setTasks(activeTaskList);
				//get assignment type
				//removed
				/*				String  assignmentType = getAssignmentType(soId);
				serviceOrder.setAssignmentType(assignmentType);*/
				
				//SL-20760
				//Code added to fetch phone number and email address for buyer 513539 
				if(SOPDFConstants.CONSTELLATION_HOME_BUYER_ID == serviceOrder.getBuyerId())
				{
					String constellationPhone = providerSearchDao.getApplicationConstantValueFromDB(SOPDFConstants.CONSTELLATION_BUYER_PHONE_NO);
					if(StringUtils.isNotBlank(constellationPhone))
					{
						serviceOrder.setConstellationBuyerPhone(constellationPhone);
					}
					
					String constellationEmail = providerSearchDao.getApplicationConstantValueFromDB(SOPDFConstants.CONSTELLATION_BUYER_EMAIL_ADDRESS);
					if(StringUtils.isNotBlank(constellationEmail))
					{
						serviceOrder.setConstellationBuyerEmail(constellationEmail);
					}	
				}
				
				serviceOrders.add(serviceOrder);
				documentVOs.add(documentVO);
				docLists.add(docList);
			} catch(BusinessServiceException ex){
				logger.error("Failed to create PDF for " + soId + " service order", ex);
			} catch(Exception exx){
				logger.error("Failed to create PDF for " + soId + " service order", exx);
			}
		}
		if (serviceOrders == null) {
			logger.error("Failed to create PDF for " + checkedSoIds+ " service order, reason: Service Order Does not Exist");
		} else {

			try {
				out = paperWorkGenerator.printPaperwork(serviceOrders,documentVOs,checkedOptions,docLists);
			} catch (Throwable t) {
				logger.error("Failed to create PDF for " + checkedSoIds + " service order", t);
			}
		}
		return out;
	}

	public ProcessResponse processCreateBid(String soId, Integer resourceId,
			Date bidDate, BigDecimal totalLabor, BigDecimal totalHours, 
			BigDecimal partsMaterials, Date bidExpirationDate,  String bidExpirationTime,
			Date newServiceDateRangeTo, Date newServiceDateRangeFrom,
			String newServiceStartTime, String newServiceEndTime,
			String comment, BigDecimal totalLaborParts, SecurityContext securityContext) {
		
		ProcessResponse processResp = new ProcessResponse();
		
		try {
			ServiceOrder so = getServiceOrderDao().getServiceOrder(soId);
			String serviceLocationTimeZone = so.getServiceLocationTimeZone();
		
			Integer wfState = so.getWfStateId();
			String priceModel = so.getPriceModel();

			if (!wfState.equals(ROUTED_STATUS) && !priceModel.equals(Constants.PriceModel.ZERO_PRICE_BID)) {
				processResp = setErrorMsg(processResp, SO_BID_OFFER_CAN_NOT_COMPLETE);
				return processResp;
			}
			
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setSoId(soId);
			routedProvider.setResourceId(resourceId);
			routedProvider.setPriceModel(so.getPriceModel());
			
			// dates
			setDatesAndTimesToRoutedProvider(convertDateToTimestamp(newServiceDateRangeFrom),
					convertDateToTimestamp(newServiceDateRangeTo), newServiceStartTime, 
					newServiceEndTime, convertDateToTimestamp(bidExpirationDate),
					serviceLocationTimeZone, routedProvider);
			
			routedProvider.setProviderRespDate(convertDateToTimestamp(bidDate));
			routedProvider.setTotalLaborBid(totalLabor);
			routedProvider.setTotalHoursBid(totalHours);
			routedProvider.setPartsAndMaterialsBid(partsMaterials);
			routedProvider.setConditionalSpendLimit(totalLaborParts.doubleValue());
			routedProvider.setProviderRespComment(comment);
			routedProvider.setProviderRespId(CONDITIONAL_OFFER);
			if (newServiceDateRangeTo != null)
				routedProvider.setProviderRespReasonId(RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
			else {
				routedProvider.setProviderRespReasonId(SPEND_LIMIT);;
			}
			
			int result = getServiceOrderDao().createConditionalOffer(routedProvider);
			if (result > 0) {
				processResp.setCode(VALID_RC);
				processResp.setObj(SO_CONDITIONAL_OFFER_SUCCESS);
			} else {
				processResp = setErrorMsg(processResp, SO_CONDITIONAL_OFFER_UPDATE_NOT_POSSIBLE);
			}
		
		} catch (DataServiceException dse) {
			logger.debug("Exception thrown querying SO", dse);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("Datastore error");
			return processResp;

		} 
		
		return processResp;
	}
	
	public Timestamp convertDateToTimestamp (Date date) {
		if (date == null ) return null;
		return new Timestamp(date.getTime());
	}
	
	private void setDatesAndTimesToRoutedProvider(
			Timestamp newServiceDateStart, Timestamp newServiceDateEnd,
			String newServiceTimeStart, String newServiceTimeEnd,
			Timestamp expirationDate, String serviceLocationTimeZone,
			RoutedProvider routedProvider) {
		HashMap<String, Object> date1Map = null;
		HashMap<String, Object> date2Map = null;
		if (newServiceDateStart != null && newServiceTimeStart != null) {
			date1Map = TimeUtils.convertToGMT(newServiceDateStart, newServiceTimeStart, serviceLocationTimeZone);
			if (!date1Map.isEmpty()) {
				routedProvider.setConditionalChangeDate1((Timestamp) date1Map.get(OrderConstants.GMT_DATE));
				routedProvider.setConditionalStartTime((String) date1Map.get(OrderConstants.GMT_TIME));
			}
		} else {
			routedProvider.setConditionalChangeDate1(newServiceDateStart);
			routedProvider.setConditionalStartTime(newServiceTimeStart);
		}
		if (newServiceDateEnd != null && newServiceTimeEnd != null) {
			date2Map = TimeUtils.convertToGMT(newServiceDateEnd, newServiceTimeEnd, serviceLocationTimeZone);
			if (!date2Map.isEmpty()) {
				routedProvider.setConditionalChangeDate2((Timestamp) date2Map.get(OrderConstants.GMT_DATE));
				routedProvider.setConditionalEndTime((String) date2Map.get(OrderConstants.GMT_TIME));
			}
		} else {
			routedProvider.setConditionalChangeDate2(newServiceDateEnd);
			routedProvider.setConditionalEndTime(newServiceTimeEnd);
		}

		if (expirationDate != null) {
			routedProvider.setConditionalExpirationDate(TimeUtils.convertToGMT(expirationDate, serviceLocationTimeZone));

		} else {
			routedProvider.setConditionalExpirationDate(expirationDate);
		}

	}


	private RoutedProvider createConditionalOffer(String priceModel, Integer vendorID, Timestamp conditionalDate1, Timestamp conditionalDate2, String conditionalStartTime, String conditionalEndTime, String serviceLocationTimeZone, Timestamp conditionalExpirationDate, Double incrSpendLimit, String serviceOrderID, Integer resourceID, Long currentTime, List<Integer> selectedCounterOfferReasonsList) {

		RoutedProvider conditionalOffer = new RoutedProvider();
		conditionalOffer.setPriceModel(priceModel);
		
		if (conditionalDate1 != null && conditionalStartTime != null) {
			HashMap<String, Object> date1Map = TimeUtils.convertToGMT(conditionalDate1, conditionalStartTime, serviceLocationTimeZone);
			if (!date1Map.isEmpty()) {
				conditionalOffer.setConditionalChangeDate1((Timestamp) date1Map.get(OrderConstants.GMT_DATE));
				conditionalOffer.setConditionalStartTime((String) date1Map.get(OrderConstants.GMT_TIME));
			}
		} else {
			conditionalOffer.setConditionalChangeDate1(conditionalDate1);
			conditionalOffer.setConditionalStartTime(conditionalStartTime);
		}
		if (conditionalDate2 != null && conditionalEndTime != null) {
			HashMap<String, Object> date2Map = TimeUtils.convertToGMT(conditionalDate2, conditionalEndTime, serviceLocationTimeZone);
			if (!date2Map.isEmpty()) {
				conditionalOffer.setConditionalChangeDate2((Timestamp) date2Map.get(OrderConstants.GMT_DATE));
				conditionalOffer.setConditionalEndTime((String) date2Map.get(OrderConstants.GMT_TIME));
			}
		} else {
			conditionalOffer.setConditionalChangeDate2(conditionalDate2);
			conditionalOffer.setConditionalEndTime(conditionalEndTime);
		}

		if (conditionalExpirationDate != null) {
			conditionalOffer.setConditionalExpirationDate(TimeUtils.convertToGMT(conditionalExpirationDate, serviceLocationTimeZone));
		} else {
			conditionalOffer.setConditionalExpirationDate(conditionalExpirationDate);
		}

		conditionalOffer.setConditionalSpendLimit(incrSpendLimit);
		conditionalOffer.setModifiedDate(new Timestamp(currentTime));
		conditionalOffer.setProviderRespDate(new Timestamp(currentTime));

		conditionalOffer.setSoId(serviceOrderID);
		conditionalOffer.setResourceId(resourceID);
		conditionalOffer.setVendorId(vendorID);
		conditionalOffer.setProviderRespId(CONDITIONAL_OFFER);

		if (incrSpendLimit != null && conditionalDate1 != null) {
			conditionalOffer.setProviderRespReasonId(RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
		} else if (incrSpendLimit != null && conditionalDate1 == null) {
			conditionalOffer.setProviderRespReasonId(SPEND_LIMIT);
		} else {
			conditionalOffer.setProviderRespReasonId(RESCHEDULE_SERVICE_DATE);
		}
		conditionalOffer.setSelectedCounterOfferReasonsList(selectedCounterOfferReasonsList);
		return conditionalOffer;
	}

	private ProcessResponse validateThenCreate(String serviceOrderID, String priceModel, Integer resourceID, Timestamp conditionalExpirationDate, String serviceLocationTimeZone, Timestamp conditionalDate1, Timestamp conditionalDate2, String conditionalStartTime, String conditionalEndTime, Double incrSpendLimit, Integer vendorID,List<Integer> selectedCounterOfferReasonsList,List<Integer> resourceIds) throws DataServiceException {
		Integer wfState = getServiceOrderDao().checkWfState(serviceOrderID);

		if (!wfState.equals(ROUTED_STATUS)) {
			if (wfState.equals(ACCEPTED_STATUS)) {
				return setErrorMsg(new ProcessResponse(), SO_CONDITIONAL_OFFER_WFSTATE_ERROR);
			}
			return setErrorMsg(new ProcessResponse(), SO_CONDITIONAL_OFFER_CAN_NOT_COMPLETE);
		}

		// Check if there exists a conditional offer already
		//SL-15642:when the user has set list of resourceIds
		if(null == resourceIds || 0 == resourceIds.size()){
			resourceIds.add(resourceID);
		}
		if(null != resourceIds && 0 != resourceIds.size()){
			for(Integer resourceId : resourceIds){
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setSoId(serviceOrderID);
				routedProvider.setResourceId(resourceId);
			routedProvider.setPriceModel(priceModel);
	
			Integer existingConditionalOffer = getServiceOrderDao().checkConditionalOfferResp(routedProvider);
			if (existingConditionalOffer != null) {
				return setErrorMsg(new ProcessResponse(), SO_CONDITIONAL_OFFER_ALREADY_EXISTS);
			}
		}
		}

		// create currentTimeStamp
		TimeZone tz = TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE);
		Calendar calendar = Calendar.getInstance(tz);
		Long currentTime = calendar.getTimeInMillis();

		// set expirationTime
		final Long expirationTime;
		if (conditionalExpirationDate != null) {
			expirationTime = conditionalExpirationDate.getTime();
			// Expiration DateTime should be greater than current
			// datetime
			if (currentTime.longValue() >= expirationTime.longValue()) {
				return setErrorMsg(new ProcessResponse(), SO_CONDITIONAL_EXPIRATION_DATE_ERROR);
			}
		} else { 
			expirationTime = null;
		}

		// set conditionalTime
		// set conditionalSecondTime
		final Long conditionalTime;
		final Long conditionalSecondTime;
		if (conditionalDate1 != null && conditionalStartTime != null && expirationTime != null) {
			conditionalTime = TimeUtils.combineDateAndTime(conditionalDate1, conditionalStartTime, serviceLocationTimeZone).getTime();

			// The expiration datetime should not exceed the conditional
			// offer start date
			if (expirationTime.longValue() >= conditionalTime.longValue()) {
				return setErrorMsg(new ProcessResponse(), SO_CONDITIONAL_EXPIRATION_DATE_CAN_NOT_BE_AFTER_START_DATE);
			}

			if (conditionalDate2 != null && conditionalEndTime != null) {
				conditionalSecondTime = TimeUtils.combineDateAndTime(conditionalDate2, conditionalEndTime, serviceLocationTimeZone).getTime();// conditionalDate2.getTime();

				if (conditionalTime.longValue() >= conditionalSecondTime.longValue()) {
					return setErrorMsg(new ProcessResponse(), SO_CONDITIONAL_OFFER_END_DATE_CAN_NOT_BE_AFTER_START_DATE);
				}
			} else {
				conditionalSecondTime = null;
			}
		} else {
			conditionalTime = null;
			conditionalSecondTime = null;
		}
		
		int result = 0;
		// create offer and then store
		if(null != resourceIds && 0 != resourceIds.size()){
			for(Integer resourceId : resourceIds){
				RoutedProvider conditionalOffer = createConditionalOffer(priceModel, vendorID, conditionalDate1, conditionalDate2, conditionalStartTime, conditionalEndTime, serviceLocationTimeZone, conditionalExpirationDate, incrSpendLimit, serviceOrderID, resourceId, currentTime, selectedCounterOfferReasonsList);
				result = getServiceOrderDao().createConditionalOffer(conditionalOffer);
			}
		}

		if (result > 0) {
			ProcessResponse processResp = new ProcessResponse();
			processResp.setCode(VALID_RC);
			processResp.setObj(SO_CONDITIONAL_OFFER_SUCCESS);
			return processResp;
		}

		return setErrorMsg(new ProcessResponse(), SO_CONDITIONAL_OFFER_UPDATE_NOT_POSSIBLE);
	}


	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #createConditionalOffer(java.lang.String, java.lang.Integer,
	 * java.lang.Integer, java.sql.Timestamp, java.sql.Timestamp,
	 * java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.Double,
	 * java.lang.String)
	 */
	public RoutedProvider createConditionalOffer(String serviceOrderId, Integer resourceId, Integer vendorId, Timestamp conditionalDate1, Timestamp conditionalDate2, String conditionalStartTime, String conditionalEndTime, Timestamp conditionalExpirationDate, Double conditionalPrice, String serviceLocationTimeZone) {

		RoutedProvider conditionalOffer = new RoutedProvider();
		HashMap<String, Object> date1Map = null;
		HashMap<String, Object> date2Map = null;
		TimeZone tz = TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE);
		Calendar calendar = Calendar.getInstance(tz);
		if (conditionalDate1 != null && conditionalStartTime != null) {
			date1Map = TimeUtils.convertToGMT(conditionalDate1, conditionalStartTime, serviceLocationTimeZone);
			if (!date1Map.isEmpty()) {
				conditionalOffer.setConditionalChangeDate1((Timestamp) date1Map.get(OrderConstants.GMT_DATE));
				conditionalOffer.setConditionalStartTime((String) date1Map.get(OrderConstants.GMT_TIME));
			}
		} else {
			conditionalOffer.setConditionalChangeDate1(conditionalDate1);
			conditionalOffer.setConditionalStartTime(conditionalStartTime);
		}
		if (conditionalDate2 != null && conditionalEndTime != null) {
			date2Map = TimeUtils.convertToGMT(conditionalDate2, conditionalEndTime, serviceLocationTimeZone);
			if (!date2Map.isEmpty()) {
				conditionalOffer.setConditionalChangeDate2((Timestamp) date2Map.get(OrderConstants.GMT_DATE));
				conditionalOffer.setConditionalEndTime((String) date2Map.get(OrderConstants.GMT_TIME));
			}
		} else {
			conditionalOffer.setConditionalChangeDate2(conditionalDate2);
			conditionalOffer.setConditionalEndTime(conditionalEndTime);
		}

		if (conditionalExpirationDate != null) {
			conditionalOffer.setConditionalExpirationDate(TimeUtils.convertToGMT(conditionalExpirationDate, serviceLocationTimeZone));

		} else {
			conditionalOffer.setConditionalExpirationDate(conditionalExpirationDate);
		}

		conditionalOffer.setConditionalSpendLimit(conditionalPrice);

		conditionalOffer.setModifiedDate(new Timestamp(calendar.getTimeInMillis()));
		conditionalOffer.setProviderRespDate(new Timestamp(calendar.getTimeInMillis()));

		conditionalOffer.setSoId(serviceOrderId);
		conditionalOffer.setResourceId(resourceId);
		conditionalOffer.setVendorId(vendorId);
		conditionalOffer.setProviderRespId(CONDITIONAL_OFFER);

		if (conditionalPrice != null && conditionalDate1 != null)
			conditionalOffer.setProviderRespReasonId(RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
		else if (conditionalPrice != null && conditionalDate1 == null)
			conditionalOffer.setProviderRespReasonId(SPEND_LIMIT);
		else
			conditionalOffer.setProviderRespReasonId(RESCHEDULE_SERVICE_DATE);
		return conditionalOffer;
	}

	public ProcessResponse acceptConditionalOffer(String soId, Integer resourceId, Integer vendorId, Integer resReasonId, Date startDate, Date endDate, String startTime, String endTime, Double spendLimit, Integer buyerId, boolean isIndividualOrder, SecurityContext securityContext) {
		List<String> errorMessages = new ArrayList<String>();
		ProcessResponse pr = new ProcessResponse();
		try {
			Account acct = financeManagerBO.getAccountDetails(buyerId); //TODO Do we need this?
			ServiceOrder serviceOrderVO = getServiceOrderDao().getServiceOrder(soId);
			SLAccountVO account = new SLAccountVO();

			if (acct.getAccountId() == null || 0l == acct.getAccountId()){
				account = financeManagerBO.getActiveAccountDetails(buyerId);
				if( account.getAccount_id()!=null){
				String acctId = account.getAccount_id().toString();
				serviceOrderVO.setAccountId(acctId);
				}
			}else{
				String acctId = acct.getAccount_id().toString();
				serviceOrderVO.setAccountId(acctId);
			}
			
			if (this.isSOInEditMode(soId)) {
				ProcessResponse temp = new ProcessResponse();
				temp.setCode(USER_ERROR_RC);
				temp.setMessage("Unable to complete action.  Service Order is currently being edited.");
				return temp;
			}

			

			
			Integer wfState = getServiceOrderDao().checkWfState(soId);
			if (!wfState.equals(ROUTED_STATUS)) {
				ProcessResponse temp = new ProcessResponse();
				temp.setCode(USER_ERROR_RC);
				temp.setMessage("Service Order is not in routed state");
				return temp;
			}

			RoutedProvider routedProvider = findRoutedProvider(serviceOrderVO, resourceId);
			// TODO: What happens when routed provider is null?
			Integer providerRespId = routedProvider.getProviderRespId();
			if (!CONDITIONAL_OFFER.equals(providerRespId)) {
				ProcessResponse temp = new ProcessResponse();
				temp.setCode(USER_ERROR_RC);
				temp.setMessage("Provider withdraw conditional Offer");
				return temp;
			}

			final Double oldSpendLimitLabour = serviceOrderVO.getSpendLimitLabor();
			final Double oldSpendLimitParts = serviceOrderVO.getSpendLimitParts(); 
			final Double newSpendLimitParts;
			if (serviceOrderVO.getPriceModel().equals(Constants.PriceModel.ZERO_PRICE_BID)) {
				newSpendLimitParts = Double.valueOf(routedProvider.getPartsAndMaterialsBid().doubleValue());
			} else { 
				newSpendLimitParts = serviceOrderVO.getSpendLimitParts();
			}
			// new parts limit will be same as oldSpendLimitParts for NYP
			// until we have a change in UI by which provider 
			// can increase parts limit and labor limit separately
			final Double newSpendLimitLabour;
			final Double delta;
			if (oldSpendLimitLabour != null && spendLimit != null) {
				newSpendLimitLabour = spendLimit - newSpendLimitParts;
				// At present, all increased goes to labor, parts limit remains the same
				delta = spendLimit - (oldSpendLimitLabour + oldSpendLimitParts);
			} else {
				newSpendLimitLabour = null;
				delta = spendLimit;
			}
			
			boolean updated = true;
			final ProcessResponse pr1;
			final ProcessResponse pr2;
			if (resReasonId.intValue() == OrderConstants.RESCHEDULE_SERVICE_DATE.intValue()) {
				if (startDate != null) {
					final Timestamp endDateTs;
					if(endDate != null) {
						endDateTs = new Timestamp(endDate.getTime());
					} else {
						endDateTs = null;
					}
					pr1 = updateSOSchedule(serviceOrderVO, new Timestamp(startDate.getTime()), endDateTs, startTime, endTime, buyerId, false, securityContext);
				} else {
					pr1 = new ProcessResponse();
				}

				if (pr1.getCode().equals(USER_ERROR_RC)) {
					updated = false;
					pr.setCode(pr1.getCode());
					pr.setMessage(pr1.getMessages().get(0));
				}
				 pr2 = new ProcessResponse();
			} else if (resReasonId.intValue() == OrderConstants.SPEND_LIMIT.intValue()) {
				// check for funds
				// check max spend limit first
				if (buyerOverMaxLimit(serviceOrderVO.getAccessFee(), spendLimit, new Double(0), securityContext)) {
					return setErrorMsg(pr, BUYER_OVER_MAX_SPEND_LIMIT, soId);
				} else if (securityContext != null) {
					if (securityContext.isIncreaseSpendLimitInd()) {
						return setErrorMsg(pr, BUYER_OVER_MAX_SPEND_LIMIT, soId);
					}
				}

				// check available funds next
				AjaxCacheVO avo = new AjaxCacheVO();
				avo.setCompanyId(buyerId);
				avo.setRoleType("BUYER");
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrderVO, buyerId); //TODO do we need this?
				serviceOrderVO.setFundingAmountCC(delta); 
				FundingVO fundVo = checkBuyerFundsForIncreasedSpendLimit(serviceOrderVO, buyerId);
				if (!fundVo.isEnoughFunds() && 
						serviceOrderVO.getFundingTypeId() != CommonConstants.CONSUMER_FUNDING_TYPE) {
					if (!securityContext.isAutoACH()) {
						String message = BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS;
						if (securityContext.getRoleId().intValue() == SIMPLE_BUYER_ROLEID) {
							message = SIMPLE_BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS;
						}
						return setErrorMsg(pr, message, soId);
					}
				}else if (serviceOrderVO.getFundingTypeId() == CommonConstants.CONSUMER_FUNDING_TYPE){
					fundConsumerFundedOrder(pr, serviceOrderVO, errorMessages, true);	
				}

				if (pr.getCode() == USER_ERROR_RC) {
					pr.setMessages(errorMessages);
					return setErrorMsg(pr, "This order is no longer available for acceptance. The buyer may be reposting this order in the future.", soId);
				} else {

				pr1 = updateSOSpendLimit(soId, newSpendLimitLabour, newSpendLimitParts, null, buyerId, false, securityContext, delta);
				if (pr1.getCode().equals(USER_ERROR_RC)) {
					updated = false;
					pr.setCode(pr1.getCode());
					pr.setMessage(pr1.getMessages().get(0));
				}
				}
				pr2 = new ProcessResponse();
			} else if (resReasonId.intValue() == OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT.intValue()) {
				if (startDate != null) {
					pr1 = updateSOSchedule(soId, new Timestamp(startDate.getTime()), endDate != null ? new Timestamp(endDate.getTime()) : null, startTime, endTime, buyerId, securityContext);
				} else {
					pr1 = new ProcessResponse();
				}
				if (pr1.getCode().equals(USER_ERROR_RC)) {
					updated = false;
					pr.setCode(pr1.getCode());
					pr.setMessage(pr1.getMessages().get(0));
					pr2 = new ProcessResponse();
				} else {
					
					//(*)(*)(*) SL-10731 fix (*)(*)(*)(*)(*)(*)(*)(*)(*)(*)
					// check for funds
					// check max spend limit first
					if (buyerOverMaxLimit(serviceOrderVO.getAccessFee(), spendLimit, new Double(0), securityContext)) {
						return setErrorMsg(pr, BUYER_OVER_MAX_SPEND_LIMIT, soId);
					} else if (securityContext != null) {
						if (securityContext.isIncreaseSpendLimitInd()) {
							return setErrorMsg(pr, BUYER_OVER_MAX_SPEND_LIMIT, soId);
						}
					}

					// check available funds next
					AjaxCacheVO avo = new AjaxCacheVO();
					avo.setCompanyId(buyerId);
					avo.setRoleType("BUYER");
					MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrderVO, buyerId); //TODO do we need this?
					serviceOrderVO.setFundingAmountCC(delta); 
					FundingVO fundVo = checkBuyerFundsForIncreasedSpendLimit(serviceOrderVO, buyerId);
					if (!fundVo.isEnoughFunds() && 
							serviceOrderVO.getFundingTypeId() != CommonConstants.CONSUMER_FUNDING_TYPE) {
						if (!securityContext.isAutoACH()) {
							String message = BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS;
							if (securityContext.getRoleId().intValue() == SIMPLE_BUYER_ROLEID) {
								message = SIMPLE_BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS;
							}
							return setErrorMsg(pr, message, soId);
						}
					}else if(serviceOrderVO.getFundingTypeId() == CommonConstants.CONSUMER_FUNDING_TYPE){
						fundConsumerFundedOrder(pr, serviceOrderVO, errorMessages, true);	
					}
					//(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)(*)
					pr2 = updateSOSpendLimit(soId, newSpendLimitLabour, newSpendLimitParts, null, buyerId, false, false, securityContext);
					if (pr2.getCode().equals(USER_ERROR_RC)) {
						updated = false;
						pr.setCode(pr2.getCode());
						pr.setMessage(pr2.getMessages().get(0));
					}
				}
			} else {
				pr1 = new ProcessResponse();
				pr2 = new ProcessResponse();
			}

			final ProcessResponse pr3;
			if (updated == true) {
				pr3 = this.processAcceptServiceOrder(soId, resourceId, vendorId, null, true, true, isIndividualOrder, securityContext);
				if (pr3.getCode().equals(USER_ERROR_RC)) {
					pr.setCode(pr3.getCode());
					pr.setMessage(pr3.getMessages().get(0));
				}
			} else {
				pr3 = new ProcessResponse();
			}

			if (VALID_RC.equals(pr1.getCode()) && (pr2.getCode().equals("") || VALID_RC.equals(pr2.getCode())) && VALID_RC.equals(pr3.getCode())) {

				if (startDate != null && startTime != null) {
					HashMap<String, Object> date1Map = TimeUtils.convertToGMT(new Timestamp(startDate.getTime()), startTime, serviceOrderVO.getServiceLocationTimeZone());
					if (!date1Map.isEmpty()) {
						serviceOrderVO.setServiceDate1((Timestamp) date1Map.get(OrderConstants.GMT_DATE));
						serviceOrderVO.setServiceTimeStart((String) date1Map.get(OrderConstants.GMT_TIME));
					}
				}

				if (endDate != null && endTime != null) {
					HashMap<String, Object> date2Map = TimeUtils.convertToGMT(new Timestamp(endDate.getTime()), endTime, serviceOrderVO.getServiceLocationTimeZone());
					if (!date2Map.isEmpty()) {
						serviceOrderVO.setServiceDate2((Timestamp) date2Map.get(OrderConstants.GMT_DATE));
						serviceOrderVO.setServiceTimeEnd((String) date2Map.get(OrderConstants.GMT_TIME));
					}
				} else if (!(resReasonId.intValue() == OrderConstants.SPEND_LIMIT.intValue())) {
					serviceOrderVO.setServiceDate2(null);
					serviceOrderVO.setServiceTimeEnd(null);
				}

				serviceOrderVO.setSpendLimitLabor(newSpendLimitLabour);
				serviceOrderVO.setSpendLimitParts(newSpendLimitParts);
				VendorResource vendorResource = new VendorResource();
				vendorResource.setResourceId(resourceId);
				serviceOrderVO.setAcceptedResource(vendorResource);
				Calendar calendar = Calendar.getInstance();
				serviceOrderVO.setAcceptedDate(new Timestamp(calendar.getTimeInMillis()));
				serviceOrderVO.setAcceptedVendorId(vendorId);
				serviceOrderVO.setWfStateId(OrderConstants.ACCEPTED_STATUS);
				if (serviceOrderVO.getWfStateId().intValue() == OrderConstants.ACTIVE_STATUS) {
					serviceOrderVO.setWfStateId(OrderConstants.ACCEPTED_STATUS);
				}
				logger.info("acceptConditionalOffer(): resReasonId = " + resReasonId);
				if (resReasonId.intValue() == OrderConstants.RESCHEDULE_SERVICE_DATE.intValue()) {
					getServiceOrderDao().updateSOReschedule(serviceOrderVO);
					logger.info("acceptConditionalOffer(): DONE updateSOReschedule");
				} else if (resReasonId.intValue() == OrderConstants.SPEND_LIMIT.intValue()) {
					getServiceOrderDao().updateLimit(serviceOrderVO);
					updateDiscountedSpendLimit(soId, spendLimit,newSpendLimitParts);
					logger.info("acceptConditionalOffer(): DONE updateLimit and updateSOPrice");
				} else if (resReasonId.intValue() == OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT.intValue()) {
					getServiceOrderDao().updateSOReschedule(serviceOrderVO);
					getServiceOrderDao().updateLimit(serviceOrderVO);
					updateDiscountedSpendLimit(soId, spendLimit,newSpendLimitParts);
					logger.info("acceptConditionalOffer(): DONE updateSOReschedule, updateLimit and updateSOPrice");
				} else {
					logger.warn("acceptConditionalOffer(): PROBLEM !!");
				}
				// Resetting the response history
				if (wfState.intValue() == ROUTED_STATUS) {
					// removing resetResponseHistory call - it
					// clears out the routed providers info
					// and when released so, and then re-route, it
					// re-routes to
					// the guys that rejected or released in the
					// past
					// if there is a need for clearing this out, we
					// need to come up
					// with an alternative on handling this issue
					// for now, remove just the ones that
					// conditionally accepted
					// getServiceOrderDao().resetResponseHistory(soId);
					getServiceOrderDao().removeConditionalFromRoutedProviders(soId);
					getServiceOrderDao().updateAccepted(serviceOrderVO);
				}

				pr.setCode(VALID_RC);
				pr.setMessage(VALID_MSG);

			}


		} catch (BusinessServiceException bse) {
			logger.debug("Exception thrown accepting SO", bse);
			pr.setCode(SYSTEM_ERROR_RC);
			errorMessages.add(new String("BusinessService error"));
			pr.setMessages(errorMessages);
			return pr;

		} catch (DataServiceException dae) {
			logger.debug("Exception thrown accepting SO", dae);
			pr.setCode(SYSTEM_ERROR_RC);
			errorMessages.add(new String("BusinessService error"));
			pr.setMessages(errorMessages);
			return pr;

		}

		return pr;

	}

	/*-----------------------------------------------------------------
	 * This is method will be used to complete ServiceOrder
	 * @param 		serviceOrderID - ServiceOrder Id
	 * @param		buyerId - Buyer Id
	 * @param		resolutionDescr - Resolution Description
	 * @param		partsFinalPrice - Final Parts Price
	 * @param 		laborFinalPrice - Final Labor Price
	 * @param 		partList - Parts List
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse processCompleteSO(String strSoId, String resolutionDescr, int providerId, double partsFinalPrice, double laborFinalPrice, List<Part> partList, List<BuyerReferenceVO> buyerRefs, SecurityContext securityContext) throws BusinessServiceException {
		logger.debug("----Start of ServiceOrderBO.processCompleteSO----");
		int oldWfState = 0;

		List<String> arr = new ArrayList<String>();
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		boolean blnComplete = false;
		int intUpdPartCount = 0;
		int intUpdCnt = 0;
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(strSoId);
			providerId = serviceOrder.getAcceptedResource().getResourceId();

			if (serviceOrder == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}

			ValidatorResponse validatorResp = new ServiceOrderValidator().validateCompleteSO(strSoId, resolutionDescr, providerId, partsFinalPrice, laborFinalPrice);
			if (validatorResp.isError()) {
				logger.error("complete service order failed validation, reason: " + validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				return processResp;
			}

			oldWfState = serviceOrder.getWfStateId().intValue();

			if (oldWfState <= 0)
				return setErrorMsg(processResp, WFSTATE_NOT_FOUND);

			blnComplete = checkStateForComplete(oldWfState);

			if (blnComplete == true) {

				serviceOrder.setLastChngStateId(oldWfState);
				serviceOrder.setWfStateId(COMPLETED_STATUS);
				serviceOrder.setWfSubStatusId(AWAITING_PAYMENT_SUBSTATUS);
				Timestamp ts = new Timestamp(calendar.getTimeInMillis());
				serviceOrder.setCompletedDate(ts);
				serviceOrder.setLastStatusChange(ts);
				serviceOrder.setLaborFinalPrice(laborFinalPrice);
				serviceOrder.setPartsFinalPrice(partsFinalPrice);
				serviceOrder.setResolutionDs(resolutionDescr);

				updateDiscountedSpendLimit(strSoId, laborFinalPrice,partsFinalPrice);

				intUpdCnt = getServiceOrderDao().update(serviceOrder);
				logger.info("done with SO update " + intUpdCnt);
				if (intUpdCnt > 0) {
					if (partList != null && partList.size() > 0) {
						// Update PartInfo
						intUpdPartCount = getServiceOrderDao().updateParts(partList);
					}

					if (true)// if ((intUpdPartCount > 0) || (partList.size() ==
					// 0))
					{
						processResp.setCode(VALID_RC);
						processResp.setSubCode(VALID_RC);
						arr.add(COMPLETESO_SUCCESS);
//					} else {
//						processResp.setCode(SYSTEM_ERROR_RC);
//						processResp.setSubCode(SYSTEM_ERROR_RC);
//						arr.add(COMPLETESO_FAILURE);
					}
				} else {
					processResp.setCode(SYSTEM_ERROR_RC);
					processResp.setSubCode(SYSTEM_ERROR_RC);
					arr.add(COMPLETESO_FAILURE);
				}

				// insert/update custom reference values
				logger.info("Calling update customer ref ");
				updateCustomRefs(strSoId, providerId, buyerRefs);

				// Check if auto_ach or refund has to be issued. For SHC only at
				// this point.
				logger.info("Calling change escrow on behalf of the buyer ");
				changeEscrowOnBehalfOfTheBuyer(serviceOrder);

				processResp.setMessages(arr);
			} else {
				return setErrorMsg(processResp, INAPPROPRIATE_WFSTATE_COMPLETE);
			}

		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] ", dse);
			String error = "";
			error = COMPLETESO_FAILURE;
			throw new BusinessServiceException(error, dse);
		} catch(BusinessServiceException bse){
			logger.error("wallet threw an error ", bse);
			throw bse;
		} catch(Exception e){
			logger.error("generic exception in service order", e);
			throw new BusinessServiceException(e);
		}
		logger.debug("----End of ServiceOrderBO.CompleteServiceOrder----");
		return processResp;
	}

	/**
	 * Description: Issue auto_ach or refund due to addons increasing or
	 * decreasing the spend limit. Do this as a provider on behalf of the Buyer
	 * 
	 * @param securityContext
	 * @param serviceOrder
	 * @throws BusinessServiceException
	 * @throws DataServiceException
	 */
	private void changeEscrowOnBehalfOfTheBuyer(ServiceOrder serviceOrder) throws BusinessServiceException, DataServiceException {
		// Check if auto_ach has to be issued to possible increase due to addons
		Integer buyerId = serviceOrder.getBuyerResource().getBuyerId();
		Account acct = financeManagerBO.getAccountDetails(buyerId);
		Buyer buyer = getServiceOrderDao().getBuyerAttributes(buyerId);
		boolean isShc = LedgerConstants.SHC_FUNDING_TYPE == serviceOrder.getFundingTypeId();
		boolean hasBankAccount = (acct != null && acct.isActive_ind());

		logger.info("changeEscrowOnBehalfOfTheBuyer ---> soId=" + serviceOrder.getSoId() + " isShc=" + isShc + " hasBankAccount=" + hasBankAccount);

		// check if we are increasing or decreasing the funds
		Double soProjectBalance = walletBO.getCurrentSpendingLimit(serviceOrder.getSoId());
		Double upsellAmt = calcUpsellAmount(serviceOrder);
		Double spendLimitIncreaseAmt = (serviceOrder.getSpendLimitLabor() + serviceOrder.getSpendLimitParts()) - soProjectBalance;
		Double totalIncrease = MoneyUtil.add(upsellAmt, spendLimitIncreaseAmt);

		logger.info("soProjectBalance=" + soProjectBalance + " upsellAmt=" + upsellAmt + " spendLimitLabor=" + serviceOrder.getSpendLimitLabor() + " spendLimitParts=" + serviceOrder.getSpendLimitParts());

		MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(serviceOrder, buyerId);
		marketVO.setUserName(buyer.getUserName());
		marketVO.setBuyerID(buyerId);
		marketVO.setAutoACHInd("true");
		marketVO.setAccountId(acct.getAccount_id());
		if (totalIncrease > 0) {
				// increase funding
//				transBo.increaseSpendLimit(marketVO, spendLimitIncreaseAmt, upsellAmt, serviceOrder.getSoId(), totalIncrease);
				transBo.increaseSpendLimitCompletion(marketVO, spendLimitIncreaseAmt, upsellAmt, serviceOrder.getSoId(), totalIncrease);
		} else if (totalIncrease < 0) {
			// decrease funding
			transBo.decreaseSpendLimit(marketVO, Math.abs(totalIncrease), Math.abs(totalIncrease));
		}
	}

	/**
	 * Description: Update Custom References for this order if they have been
	 * changed.
	 * 
	 * @param strSoId
	 * @param providerId
	 * @param buyerRefs
	 */
	private void updateCustomRefs(String strSoId, int providerId, List<BuyerReferenceVO> buyerRefs) {
		for (BuyerReferenceVO buyRef : buyerRefs) {
			ServiceOrderCustomRefVO socrVo = new ServiceOrderCustomRefVO();
			socrVo.setsoId(strSoId);
			socrVo.setModifiedDate(new java.sql.Date(System.currentTimeMillis()));
			socrVo.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
			socrVo.setModifiedBy(new Integer(providerId).toString());
			socrVo.setRefType(buyRef.getReferenceType());
			socrVo.setRefTypeId(buyRef.getBuyerRefTypeId());
			socrVo.setRefValue(buyRef.getReferenceValue());
			if (strSoId != null && socrVo.getRefTypeId() != null && socrVo.getRefValue() != null)
				insertSoCustomReference(socrVo); // Try update; if no records
			// found, then insert

		}
	}

	/**
	 * Reschedule a service order. This will fire the requestReschedule or
	 * updateSchedule depending on status
	 * 
	 * Draft � Perform direct update to SO schedule dates Posted / Expired �
	 * Perform direct update to SO schedule dates and perform re-route logic
	 * Completed / Canceled / Voided / Closed � return error (cannot perform
	 * reschedule action in X status) Accepted / Active / Problem � Perform
	 * Reschedule Request action
	 * 
	 * @param serviceOrderId
	 * @param newStartDate
	 * @param newEndDate
	 * @param newStartTime
	 * @param newEndTime
	 * @param requestorRole
	 * @param companyId
	 * @param scheduleType
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProcessResponse rescheduleSO(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, String newStartTime, String newEndTime, Integer requestorRole, Integer companyId, boolean convertGMT, SecurityContext securityContext) throws BusinessServiceException {

		ProcessResponse processResp = new ProcessResponse();
		ServiceOrder so = null;
		try {
			so = getServiceOrderDao().getServiceOrder(serviceOrderId);
		} catch (DataServiceException dse) {
			logger.error("Could not verify current service order information - database error. ", dse);
			throw new BusinessServiceException("Could not verify current service order information - database error. ", dse);
		}
		if (so != null && so.getWfStateId().intValue() != OrderConstants.CANCELLED_STATUS && so.getWfStateId().intValue() != OrderConstants.CLOSED_STATUS && so.getWfStateId().intValue() != OrderConstants.COMPLETED_STATUS && so.getWfStateId().intValue() != OrderConstants.VOIDED_STATUS && so.getWfStateId().intValue() != OrderConstants.DELETED_STATUS) {

			// determine schedule type
			String scheduleType = OrderConstants.FIXED_DATE;
			if (newEndDate != null && newEndTime != null) {
				scheduleType = OrderConstants.RANGE_DATE;
			}

			// returns error if the work flow state has error
			if (so.getWfStateId().intValue() <= 0) {
				return setErrorMsg(processResp, WFSTATE_NOT_FOUND);
			}

			if (so.getWfStateId().intValue() == OrderConstants.ACCEPTED_STATUS || so.getWfStateId().intValue() == OrderConstants.ACTIVE_STATUS || so.getWfStateId().intValue() == OrderConstants.PROBLEM_STATUS) {

				processResp = requestRescheduleSO(so, newStartDate, newEndDate, newStartTime, newEndTime, requestorRole, companyId, scheduleType, convertGMT, securityContext);

			} else if (so.getWfStateId().intValue() == OrderConstants.DRAFT_STATUS || so.getWfStateId().intValue() == OrderConstants.ROUTED_STATUS || so.getWfStateId().intValue() == OrderConstants.EXPIRED_STATUS) {

				processResp = updateSOSchedule(so, newStartDate, newEndDate, newStartTime, newEndTime, companyId, convertGMT, securityContext);

				if (so.getWfStateId().intValue() == OrderConstants.ROUTED_STATUS || so.getWfStateId().intValue() == OrderConstants.EXPIRED_STATUS) {

					// Resetting the response history
					// removing resetResponseHistory call - it clears out the
					// routed providers info
					// and when released so, and then re-route, it re-routes to
					// the guys that rejected or released in the past
					// if there is a need for clearing this out, we need to come
					// up
					// with an alternative on handling this issue
					// for now, remove just the ones that conditionally accepted
					if (so.getWfStateId() == ROUTED_STATUS) {
						try {
							getServiceOrderDao().removeConditionalFromRoutedProviders(so.getSoId());
						} catch (DataServiceException dse) {
							logger.error("Could not reroute service order - database error. ", dse);
							throw new BusinessServiceException("Could not reroute service order - database error. ", dse);
						}
					}
					// flip status to draft from expired to allow re-route
					so.setWfStateId(DRAFT_STATUS);
					try {
						// get the bo bean again for AOP to fire
						IServiceOrderBO bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
						getServiceOrderDao().updateSOStatus(so);
						bo.processReRouteSO(companyId, so.getSoId(), false, securityContext);
					} catch (DataServiceException e) {
						logger.error(e.getMessage(), e);
						throw new BusinessServiceException("Could not reroute service order - database error. ", e);
					}
				}
			}
		} else if (so == null) {
			return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
		} else {
			return setErrorMsg(processResp, "This service order has been completed, closed, or canceled.");
		}
		return processResp;

	}

	public ProcessResponse requestRescheduleSO(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, String newStartTime, String newEndTime, Integer subStatus, Integer requestorRole, Integer companyId, String scheduleType, SecurityContext securityContext) throws BusinessServiceException {

		return requestRescheduleSO(serviceOrderId, newStartDate, newEndDate, newStartTime, newEndTime, subStatus, requestorRole, companyId, scheduleType, true, securityContext);
	}

	public ProcessResponse rescheduleSOComments(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, String newStartTime, String newEndTime, Integer subStatus, Integer requestorRole, Integer companyId, String scheduleType, String comments, SecurityContext securityContext) throws BusinessServiceException {
		return requestRescheduleSO(serviceOrderId, newStartDate, newEndDate, newStartTime, newEndTime, subStatus, requestorRole, companyId, scheduleType, true, securityContext);
	}

	public ProcessResponse requestRescheduleSO(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, String newStartTime, String newEndTime, Integer subStatus, Integer requestorRole, Integer companyId, String scheduleType, boolean convertGMT, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResponseObj = new ProcessResponse();
		try {
			processResponseObj = rescheduleSO(serviceOrderId, newStartDate, newEndDate, newStartTime, newEndTime, requestorRole, companyId, convertGMT, securityContext);
		} catch (Throwable t) {
			logger.error("Reschedule Service Order:: error:  " + t.getMessage(), t);
			processResponseObj.setCode(SYSTEM_ERROR_RC);
			processResponseObj.setMessage(t.getMessage());
		}
		return processResponseObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #requestRescheduleSO(java.lang.String, java.sql.Timestamp,
	 * java.sql.Timestamp, java.lang.String, java.lang.String,
	 * java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	private ProcessResponse requestRescheduleSO(ServiceOrder so, Timestamp newStartDate, Timestamp newEndDate, String newStartTime, String newEndTime, Integer requestorRole, Integer companyId, String scheduleType, boolean convertGMT, SecurityContext securityContext) throws BusinessServiceException {

		int success = 0;
		List<String> messageArray = new ArrayList<String>();

		// do validation on user, service order, and status
		ProcessResponse processResp = new ProcessResponse();

		Timestamp newStartTimeCombined = new Timestamp(TimeUtils.combineDateAndTime(newStartDate, newStartTime, so.getServiceLocationTimeZone()).getTime());
		if (so.getRescheduleServiceDate1() != null) {
			return setErrorMsg(processResp, "Reschedule request is already pending.  You may only have one reschedule request at a time.");
		}

		if (requestorRole.intValue() == OrderConstants.BUYER_ROLEID) {
			if (so.getBuyer().getBuyerId().intValue() != companyId.intValue()) {
				return setErrorMsg(processResp, "User does not have access to this service order.");
			}
		} else if (requestorRole.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (so.getAcceptedVendorId().intValue() != companyId.intValue()) {
				return setErrorMsg(processResp, "User does not have access to this service order.");
			}
		}
		// do validation on dates and times
		java.util.Date today = new java.util.Date();
		java.sql.Date now = new java.sql.Date(today.getTime());

		if (newStartDate == null) {
			processResp.setCode(USER_ERROR_RC);
			processResp.setSubCode(USER_ERROR_RC);
			messageArray.add("Must have a start date.");
			processResp.setMessages(messageArray);
			return processResp;
		}
		if (newStartTimeCombined.compareTo(now) < 0) {
			processResp.setCode(USER_ERROR_RC);
			processResp.setSubCode(USER_ERROR_RC);
			messageArray.add("Start Date must be in the future.");
			processResp.setMessages(messageArray);
			return processResp;
		}
		if (newEndDate != null) {
			// check if start < end
			Timestamp newEndTimeCombined = new Timestamp(TimeUtils.combineDateAndTime(newEndDate, newEndTime, so.getServiceLocationTimeZone()).getTime());
			if (newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {
				processResp.setCode(USER_ERROR_RC);
				processResp.setSubCode(USER_ERROR_RC);
				messageArray.add("Start Date must be prior to End Date.");
				processResp.setMessages(messageArray);
				return processResp;
			}
		}
		// end validation

		ServiceOrderRescheduleVO reschedule = new ServiceOrderRescheduleVO();
		HashMap<String, Object> serviceDate1Map = new HashMap<String, Object>();
		HashMap<String, Object> serviceDate2Map = new HashMap<String, Object>();

		reschedule.setScheduleType(scheduleType);
		if (newStartDate != null & newStartTime != null) {
			if (convertGMT) {
				serviceDate1Map = TimeUtils.convertToGMT(newStartDate, newStartTime, so.getServiceLocationTimeZone());
				reschedule.setNewDateStart((Timestamp) serviceDate1Map.get(OrderConstants.GMT_DATE));
				reschedule.setNewTimeStart((String) serviceDate1Map.get(OrderConstants.GMT_TIME));
			} else {
				reschedule.setNewDateStart(newStartDate);
				reschedule.setNewTimeStart(newStartTime);
			}
		} else {
			reschedule.setNewDateStart(newStartDate);
			reschedule.setNewTimeStart(newStartTime);
		}
		if (newEndDate != null && newEndTime != null) {
			if (convertGMT) {
				serviceDate2Map = TimeUtils.convertToGMT(newEndDate, newEndTime, so.getServiceLocationTimeZone());
				reschedule.setNewDateEnd((Timestamp) serviceDate2Map.get(OrderConstants.GMT_DATE)); // if
				// fixed,
				// will
				// not
				// have
				// these
				// values
				reschedule.setNewTimeEnd((String) serviceDate2Map.get(OrderConstants.GMT_TIME));
			} else {
				reschedule.setNewDateEnd(newEndDate);
				reschedule.setNewTimeEnd(newEndTime);
			}
		} else {
			reschedule.setNewDateEnd(newEndDate); // if fixed, will not have
			// these values
			reschedule.setNewTimeEnd(newEndTime);
		}

		reschedule.setSoId(so.getSoId());

		// make a db call to insert new values in the resched_* fields
		try {
			success = getServiceOrderDao().updateSODateTime(reschedule);
		} catch (DataServiceException dse) {
			logger.error("Could not update new requested schedule in requestRescheduleSO - database error. ", dse);
			throw new BusinessServiceException("Could not update new requested schedule in requestRescheduleSO - database error. ", dse);
		}
		if (success > 0) {
			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
			messageArray.add("Reschedule Request processed successfully.");
			logger.info("Reschedule Request processed successfully.");
		} else {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setSubCode(SYSTEM_ERROR_RC);
			messageArray.add("Reschedule Request failed.  Please try again.");
			logger.error("Reschedule Request failed.");
		}
		processResp.setMessages(messageArray);

		logger.info("Done with requestRescheduleSO method in ServiceOrderRescheduleBO...");
		return processResp;
	}

	public ServiceOrderRescheduleVO getRescheduleRequestInfo(String serviceOrderId) throws BusinessServiceException {
		logger.info("Starting getRescheduleRequestInfo method in ServiceOrderRescheduleBO...");
		ServiceOrderRescheduleVO soResched = new ServiceOrderRescheduleVO();
		// call the database based on serviceOrderId and get all the info
		try {
			// should also pull reason from logging table??
			soResched = getServiceOrderDao().getRescheduleRequestInfo(serviceOrderId);
		} catch (DataServiceException dse) {
			logger.error("Could not retrieve new requested schedule in getRescheduleRequestInfo - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not retrieve new requested schedule in getRescheduleRequestInfo - database error. ", dse);
		}

		logger.info("Done with getRescheduleRequestInfo method in ServiceOrderRescheduleBO...");
		return soResched;
	}
	public String getAssignmentType(String soId) throws BusinessServiceException{
		try{
		return getServiceOrderDao().getAssignmentType(soId);
		}catch (DataServiceException dse) {
			logger.error("Could not get assignment type - database error. ", dse);
			throw new BusinessServiceException("Could not get assignment type - database error. ", dse);
		}
	}

	public ProcessResponse respondToRescheduleRequest(String serviceOrderId, boolean isRequestAccepted, Integer role, Integer companyId, SecurityContext securityContext) throws BusinessServiceException {
		logger.info("Starting respondToRescheduleRequest method in ServiceOrderRescheduleBO...");
		int success = 0;

		// do validation on user, service order, and status
		ProcessResponse processResp = new ProcessResponse();
		ServiceOrder serviceOrder = null;
		try {
			serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderId);
		} catch (DataServiceException dse) {
			logger.error("Could not verify current service order information - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not verify current service order information - database error. ", dse);
		}

		if (serviceOrder == null) {
			return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
		} else {
			// returns error if the work flow state has error
			if (serviceOrder.getWfStateId().intValue() <= 0) {
				return setErrorMsg(processResp, WFSTATE_NOT_FOUND);
			}
			if (role.intValue() == OrderConstants.BUYER_ROLEID) {
				if (serviceOrder.getBuyer().getBuyerId().intValue() != companyId.intValue()) {
					return setErrorMsg(processResp, "User does not have access to this service order.");
				}
			} else if (role.intValue() == OrderConstants.PROVIDER_ROLEID) {
				if (serviceOrder.getAcceptedVendorId().intValue() != companyId.intValue()) {
					return setErrorMsg(processResp, "User does not have access to this service order.");
				}
			}
		}
		// end validation

		if (isRequestAccepted) {
			// if request is accepted, send an alert and assign new values (the
			// ones in resched fields) to actual fields
			// also, make sure the order is in proper state
			// also, use logging table to insert the reason for reschedule
			// (sub-status)
			ServiceOrderRescheduleVO reschedule = getRescheduleRequestInfo(serviceOrderId);
			reschedule.setOldDateStart(reschedule.getNewDateStart());
			reschedule.setOldDateEnd(reschedule.getNewDateEnd());
			reschedule.setOldTimeStart(reschedule.getNewTimeStart());
			reschedule.setOldTimeEnd(reschedule.getNewTimeEnd());
			reschedule.setNewDateStart(null);
			reschedule.setNewDateEnd(null);
			reschedule.setNewTimeStart(null);
			reschedule.setNewTimeEnd(null);

			// if was active, set state back to accepted
			if (serviceOrder.getWfStateId() != null && serviceOrder.getWfStateId().intValue() == ACTIVE_STATUS) {
				reschedule.setWfStateId(new Integer(ACCEPTED_STATUS));
				reschedule.setWfSubStateId(null);
				java.util.Date today = new java.util.Date();
				Timestamp datetime = new Timestamp(today.getTime());
				reschedule.setLastStatusChange(datetime);
				reschedule.setLastWfStateId(new Integer(ACTIVE_STATUS));
			}

			try {
				success = getServiceOrderDao().updateSODateTimeFinal(reschedule);
			} catch (DataServiceException dse) {
				logger.error("Could not update new requested schedule in respondToRescheduleRequest - database error. ", dse);
				logger.error("Exception :" + dse);
				throw new BusinessServiceException("Could not update new requested schedule in respondToRescheduleRequest - database error. ", dse);
			}

			List<String> messageArray = new ArrayList<String>();
			if (success > 0) {
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				messageArray.add("Cancel/Reject Reschedule Request processed successfully.");
				logger.info("Reschedule Request processed successfully.");
			} else {
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				messageArray.add("Cancel/Reject Reschedule Request failed.  Please try again.");
				logger.error("Reschedule Request failed.");
			}
			processResp.setMessages(messageArray);
		} else {
			// if request is declined, send an alert and remove new values from
			// the db
			processResp = cancelRescheduleRequest(serviceOrderId, role, companyId, securityContext);
		}

		logger.info("Done with respondToRescheduleRequest method in ServiceOrderRescheduleBO...");
		return processResp;
	}

	// splitting the methods for accept or reject reschedule..start

	public ProcessResponse acceptRescheduleRequest(String serviceOrderId, boolean isRequestAccepted, Integer role, Integer companyId, SecurityContext securityContext) throws BusinessServiceException

	{

		logger.info("Starting acceptRescheduleRequest method in ServiceOrderRescheduleBO...");
		int success = 0;

		// do validation on user, service order, and status
		ProcessResponse processResp = new ProcessResponse();
		ServiceOrder serviceOrder = null;
		try {
			serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderId);
		} catch (DataServiceException dse) {
			logger.error("Could not verify current service order information - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not verify current service order information - database error. ", dse);
		}

		if (serviceOrder == null) {
			return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
		} else {
			// returns error if the work flow state has error
			if (serviceOrder.getWfStateId().intValue() <= 0) {
				return setErrorMsg(processResp, WFSTATE_NOT_FOUND);
			}
			if (role.intValue() == OrderConstants.BUYER_ROLEID) {
				if (serviceOrder.getBuyer().getBuyerId().intValue() != companyId.intValue()) {
					return setErrorMsg(processResp, "User does not have access to this service order.");
				}
			} else if (role.intValue() == OrderConstants.PROVIDER_ROLEID) {
				if (serviceOrder.getAcceptedVendorId().intValue() != companyId.intValue()) {
					return setErrorMsg(processResp, "User does not have access to this service order.");
				}
			}
		}
		// end validation

		if (isRequestAccepted) {
			// if request is accepted, send an alert and assign new values (the
			// ones in resched fields) to actual fields
			// also, make sure the order is in proper state
			// also, use logging table to insert the reason for reschedule
			// (sub-status)
			ServiceOrderRescheduleVO reschedule = getRescheduleRequestInfo(serviceOrderId);
			reschedule.setOldDateStart(reschedule.getNewDateStart());
			reschedule.setOldDateEnd(reschedule.getNewDateEnd());
			reschedule.setOldTimeStart(reschedule.getNewTimeStart());
			reschedule.setOldTimeEnd(reschedule.getNewTimeEnd());
			reschedule.setNewDateStart(null);
			reschedule.setNewDateEnd(null);
			reschedule.setNewTimeStart(null);
			reschedule.setNewTimeEnd(null);
			boolean isRescheduleRequest = true;
			Integer derivedSubStatus = deriveSubStatus(serviceOrder, isRescheduleRequest);
			reschedule.setWfSubStateId(derivedSubStatus);

			// if was active, set state back to accepted
			if (serviceOrder.getWfStateId() != null && serviceOrder.getWfStateId().intValue() == ACTIVE_STATUS) {
				reschedule.setWfStateId(new Integer(ACCEPTED_STATUS));

				java.util.Date today = new java.util.Date();
				Timestamp datetime = new Timestamp(today.getTime());
				reschedule.setLastStatusChange(datetime);
				reschedule.setLastWfStateId(new Integer(ACTIVE_STATUS));
			}

			// Set the substatus of the service order for the order history
			updateDerviedSubStatus(serviceOrder.getSoId(), derivedSubStatus, securityContext);

			try {
				success = getServiceOrderDao().updateSODateTimeFinal(reschedule);

			} catch (DataServiceException dse) {
				logger.error("Could not update new requested schedule in respondToRescheduleRequest - database error. ", dse);
				logger.error("Exception :" + dse);
				throw new BusinessServiceException("Could not update new requested schedule in respondToRescheduleRequest - database error. ", dse);
			}

			List<String> messageArray = new ArrayList<String>();
			if (success > 0) {
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				messageArray.add("Cancel/Reject Reschedule Request processed successfully.");
				logger.info("Reschedule Request processed successfully.");
			} else {
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				messageArray.add("Cancel/Reject Reschedule Request failed.  Please try again.");
				logger.error("Reschedule Request failed.");
			}
			processResp.setMessages(messageArray);
		} else {
			// if request is declined, send an alert and remove new values from
			// the db
			processResp = cancelRescheduleRequest(serviceOrderId, role, companyId, securityContext);
		}

		logger.info("Done with acceptRescheduleRequest method in ServiceOrderRescheduleBO...");
		return processResp;

	}

	public ProcessResponse rejectRescheduleRequest(String serviceOrderId, boolean isRequestAccepted, Integer role, Integer companyId, SecurityContext securityContext) throws BusinessServiceException {
		logger.info("Starting rejectToRescheduleRequest method in ServiceOrderRescheduleBO...");
		int success = 0;

		// do validation on user, service order, and status
		ProcessResponse processResp = new ProcessResponse();
		ServiceOrder serviceOrder = null;
		try {
			serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderId);
		} catch (DataServiceException dse) {
			logger.error("Could not verify current service order information - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not verify current service order information - database error. ", dse);
		}

		if (serviceOrder == null) {
			return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
		} else {
			// returns error if the work flow state has error
			if (serviceOrder.getWfStateId().intValue() <= 0) {
				return setErrorMsg(processResp, WFSTATE_NOT_FOUND);
			}
			if (role.intValue() == OrderConstants.BUYER_ROLEID) {
				if (serviceOrder.getBuyer().getBuyerId().intValue() != companyId.intValue()) {
					return setErrorMsg(processResp, "User does not have access to this service order.");
				}
			} else if (role.intValue() == OrderConstants.PROVIDER_ROLEID) {
				if (serviceOrder.getAcceptedVendorId().intValue() != companyId.intValue()) {
					return setErrorMsg(processResp, "User does not have access to this service order.");
				}
			}
		}
		// end validation

		if (isRequestAccepted) {
			// if request is accepted, send an alert and assign new values (the
			// ones in resched fields) to actual fields
			// also, make sure the order is in proper state
			// also, use logging table to insert the reason for reschedule
			// (sub-status)
			ServiceOrderRescheduleVO reschedule = getRescheduleRequestInfo(serviceOrderId);
			reschedule.setOldDateStart(reschedule.getNewDateStart());
			reschedule.setOldDateEnd(reschedule.getNewDateEnd());
			reschedule.setOldTimeStart(reschedule.getNewTimeStart());
			reschedule.setOldTimeEnd(reschedule.getNewTimeEnd());
			reschedule.setNewDateStart(null);
			reschedule.setNewDateEnd(null);
			reschedule.setNewTimeStart(null);
			reschedule.setNewTimeEnd(null);

			// if was active, set state back to accepted
			if (serviceOrder.getWfStateId() != null && serviceOrder.getWfStateId().intValue() == ACTIVE_STATUS) {
				reschedule.setWfStateId(new Integer(ACCEPTED_STATUS));
				reschedule.setWfSubStateId(null);
				java.util.Date today = new java.util.Date();
				Timestamp datetime = new Timestamp(today.getTime());
				reschedule.setLastStatusChange(datetime);
				reschedule.setLastWfStateId(new Integer(ACTIVE_STATUS));
			}

			try {
				success = getServiceOrderDao().updateSODateTimeFinal(reschedule);
			} catch (DataServiceException dse) {
				logger.error("Could not update new requested schedule in respondToRescheduleRequest - database error. ", dse);
				logger.error("Exception :" + dse);
				throw new BusinessServiceException("Could not update new requested schedule in respondToRescheduleRequest - database error. ", dse);
			}

			List<String> messageArray = new ArrayList<String>();
			if (success > 0) {
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				messageArray.add("Cancel/Reject Reschedule Request processed successfully.");
				logger.info("Reschedule Request processed successfully.");
			} else {
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				messageArray.add("Cancel/Reject Reschedule Request failed.  Please try again.");
				logger.error("Reschedule Request failed.");
			}
			processResp.setMessages(messageArray);
		} else {
			// if request is declined, send an alert and remove new values from
			// the db
			processResp = cancelRescheduleRequest(serviceOrderId, role, companyId, securityContext);
		}

		logger.info("Done with rejectToRescheduleRequest method in ServiceOrderRescheduleBO...");
		return processResp;

	}

	// splitting the methods for accept or reject reschedule..End

	public ProcessResponse cancelRescheduleRequest(String serviceOrderId, Integer role, Integer companyId, SecurityContext securityContext) throws BusinessServiceException {
		logger.info("Starting cancelRescheduleRequest method in ServiceOrderRescheduleBO...");
		int result = 0;
		List<String> messageArray = new ArrayList<String>();

		// do validation on user, service order, and status
		ProcessResponse processResp = new ProcessResponse();
		try {
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderId);
			if (serviceOrder == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			} else {
				// returns error if the work flow state has error
				if (serviceOrder.getWfStateId().intValue() <= 0) {
					return setErrorMsg(processResp, WFSTATE_NOT_FOUND);
				}
				
				if (serviceOrder.getRescheduleServiceDate1() == null) {
					return setErrorMsg(processResp, "There is no pending reschedule request to cancel");
				}
				
				if (role.intValue() == OrderConstants.BUYER_ROLEID) {
					if (serviceOrder.getBuyer().getBuyerId().intValue() != companyId.intValue()) {
						return setErrorMsg(processResp, "User does not have access to this service order.");
					}
				} else if (role.intValue() == OrderConstants.PROVIDER_ROLEID) {
					if (serviceOrder.getAcceptedVendorId().intValue() != companyId.intValue()) {
						return setErrorMsg(processResp, "User does not have access to this service order.");
					}
				}
			}

		} catch (DataServiceException dse) {
			logger.error("Could not verify current service order information - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not verify current service order information - database error. ", dse);
		}
		// end validation

		// remove the new values from the db, and send an alert?
		try {
			result = getServiceOrderDao().updateCancelReschedule(serviceOrderId);
		} catch (DataServiceException dse) {
			logger.error("Could not cancel new requested schedule in cancelRescheduleRequest - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not cancel new requested schedule in cancelRescheduleRequest - database error. ", dse);
		}

		if (result < 1) {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setSubCode(SYSTEM_ERROR_RC);
			messageArray.add("Cancel/Reject Reschedule Request failed.  Please try again.");
			logger.error("Reschedule Request failed.");
		} else {
			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
			messageArray.add("Cancel/Reject Reschedule Request processed successfully.");
			logger.info("Reschedule Request processed successfully.");
		}
		processResp.setMessages(messageArray);

		// also remove stuff from logging table?
		logger.info("Done with cancelRescheduleRequest method in ServiceOrderRescheduleBO...");
		return processResp;
	}

	// /////////////////////////////////
	// ///////End Reschedule SO/////////
	// /////////////////////////////////

	public ProcessResponse releaseServiceOrderInActive(String soId, Integer reasonCode, String providerComment, Integer resourceId, SecurityContext securityContext) throws BusinessServiceException {
		return releaseSO(soId, providerComment, resourceId);
	}

	public ProcessResponse releaseServiceOrderInAccepted(String soId, Integer reasonCode, String providerComment, Integer resourceId, SecurityContext securityContext) throws BusinessServiceException {
		return releaseSO(soId, providerComment, resourceId);
	}

	public ProcessResponse releaseServiceOrderInProblem(String soId, Integer reasonCode, String providerComment, Integer resourceId, SecurityContext securityContext) throws BusinessServiceException {
		return releaseSO(soId, providerComment, resourceId);
	}

	public void releaseSOProviderAlert(String soId, SecurityContext securityContext) {
		// placeholder function for AOP alert for providers when an order is
		// released from accepted state.
		// DO NOT DELETE!!!!!!!!!!!! Thanks.
		// -The Authorities.
	}

	private ProcessResponse releaseSO(String soId, String providerComment, Integer resourceId) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		List<String> messageArray = new ArrayList<String>();
		ServiceOrder so = null;
		// do validation on user, service order, and status
		try {
			so = getServiceOrderDao().getServiceOrder(soId);
			if (so == null) {
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			} else {
				// returns error if the work flow state has error
				if (so.getWfStateId().intValue() <= 0) {
					return setErrorMsg(processResp, WFSTATE_NOT_FOUND);
				}
			}
		} catch (DataServiceException dse) {
			logger.error("Could not verify current service order information - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not verify current service order information - database error. ", dse);
		}

		if (providerComment == null || "".equals(providerComment)) {
			processResp.setCode(USER_ERROR_RC);
			processResp.setSubCode(USER_ERROR_RC);
			messageArray.add("Must enter a comment.");
			processResp.setMessages(messageArray);
			return processResp;
		}

		// end validation

		// update so_hdr to clear out all provider related info, reschedule
		// info, accepted date, act_arrival dates,
		// provider_terms_cond_resp and date? , so_substatus_id,
		// change the status of the order based on current status
		// (if was active, change to 'expired', if was accepted, change to
		// 'routed')
		ServiceOrder newServiceOrderInfo = new ServiceOrder();
		newServiceOrderInfo.setSoId(soId);
		int success = 0;
		boolean routeFlag = false;
		try {
			if (so.getWfStateId().intValue() == OrderConstants.ACCEPTED_STATUS) {
				newServiceOrderInfo.setWfStateId(OrderConstants.ROUTED_STATUS);
				newServiceOrderInfo.setExpiredDate(so.getExpiredDate());
				newServiceOrderInfo.setRoutedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				success = getServiceOrderDao().updateSoHdrForReleaseSO(newServiceOrderInfo);
			} else if (so.getWfStateId().intValue() == OrderConstants.ACTIVE_STATUS) {
				routeFlag = this.deriveReleaseStatusFlag(so, routeFlag);
				if (routeFlag) {
					newServiceOrderInfo.setWfStateId(OrderConstants.ROUTED_STATUS);
					newServiceOrderInfo.setExpiredDate(so.getExpiredDate());
					newServiceOrderInfo.setRoutedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					processResp.setObj(OrderConstants.ROUTED_STATUS);
				} else {
					newServiceOrderInfo.setWfStateId(OrderConstants.EXPIRED_STATUS);
					newServiceOrderInfo.setExpiredDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					newServiceOrderInfo.setRoutedDate(so.getRoutedDate());
					processResp.setObj(OrderConstants.EXPIRED_STATUS);
				}
				success = getServiceOrderDao().updateSoHdrForReleaseSO(newServiceOrderInfo);
			} else if (so.getWfStateId().intValue() == OrderConstants.PROBLEM_STATUS) {
				newServiceOrderInfo.setWfStateId(OrderConstants.ROUTED_STATUS);
				newServiceOrderInfo.setExpiredDate(so.getExpiredDate());
				newServiceOrderInfo.setRoutedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				success = getServiceOrderDao().updateSoHdrForReleaseSO(newServiceOrderInfo);
			} else {
				// error - cannot release in any other state
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				messageArray.add("Cannot release service order in this state.");
				logger.error("Release Request failed.");
			}
		} catch (DataServiceException dse) {
			logger.error("Could not update so_hdr information in releaseServiceOrder - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not update so_hdr information in releaseServiceOrder - database error. ", dse);
		}
		if (success < 1) {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setSubCode(SYSTEM_ERROR_RC);
			messageArray.add("Could not update service order information.");
		}
		// update so_routed_providers records
		// this query will also clear out info from so_location and so_contact
		// tables
		int successRP = 0;
		try {
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setResourceId(so.getAcceptedResourceId());
			routedProvider.setSoId(soId);
			routedProvider.setProviderRespId(OrderConstants.RELEASED);
			routedProvider.setProviderRespReasonId(null);
			routedProvider.setProviderRespComment(providerComment);
			routedProvider.setPriceModel(so.getPriceModel());

			Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
			routedProvider.setProviderRespDate(providerRespDate);

			successRP = getServiceOrderDao().updateRoutedProviderRecords(routedProvider);
		} catch (DataServiceException dse) {
			logger.error("Could not update so_routed_providers information in releaseServiceOrder - database error. ", dse);
			logger.error("Exception :" + dse);
			throw new BusinessServiceException("Could not update so_hdr information in releaseServiceOrder - database error. ", dse);
		}
		if (successRP < 1) {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setSubCode(SYSTEM_ERROR_RC);
			messageArray.add("Could not update provider information.");
		}
		if (success > 0 && successRP > 0) {
			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
			messageArray.add("Release Service Order request processed successfully.");
			logger.info("Release Service Order request processed successfully.");
		}
		processResp.setMessages(messageArray);

		return processResp;
	}

	/**
	 * The method checks whether the current date is greater than the service
	 * date or not. If greater, it sets the value of routeFlag as false, else as
	 * true(indicating whether the status needs to be updated to expired/routed)
	 * 
	 * @param ServiceOrder
	 *            so
	 * @param boolean routeFlag
	 * @return routeFlag
	 **/
	private boolean deriveReleaseStatusFlag(ServiceOrder so, boolean routeFlag) {
		Timestamp appointmentDate = null;
		String appontmentTime = null;
		String serviceDateTypeId = so.getServiceDateTypeId() != null ? so.getServiceDateTypeId().toString() : "";
		if (OrderConstants.FIXED_DATE.equals(serviceDateTypeId)) {
			appointmentDate = so.getServiceDate1();
			appontmentTime = so.getServiceTimeStart();
		} else if (OrderConstants.RANGE_DATE.equals(serviceDateTypeId)) {
			appointmentDate = so.getServiceDate2();
			appontmentTime = so.getServiceTimeEnd();
		} else {
			if (StringUtils.isNotBlank(so.getServiceDate2().toString())) {
				appointmentDate = so.getServiceDate2();
				appontmentTime = so.getServiceTimeEnd();
			} else {
				appointmentDate = so.getServiceDate1();
				appontmentTime = so.getServiceTimeStart();
			}
		}

		if (!TimeUtils.isPastCurrentTime(appointmentDate, appontmentTime, Locale.US, so.getServiceLocationTimeZone())) {
			routeFlag = true;
		}
		return routeFlag;
	}

	public ProcessResponse withdrawConditionalAcceptance(String serviceOrderId, Integer resourceID, Integer providerRespId, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse pr = new ProcessResponse();
		RoutedProvider rop = new RoutedProvider();
		int x;

		try {
			if (this.isSOInEditMode(serviceOrderId)) {
				pr.setCode(USER_ERROR_RC);
				pr.setMessage("Unable to complete action.  Service Order is currently being edited.");

			}// end if
			else {

				rop.setSoId(serviceOrderId);
				rop.setResourceId(resourceID);
				rop.setProviderRespId(providerRespId);

				Integer wfState = getServiceOrderDao().checkWfState(rop.getSoId());

				//SL-19238 : Removing wf_state check
				/*if (wfState.equals(ROUTED_STATUS)) {*/
					x = getServiceOrderDao().updateProviderResponseConditionalOffer(rop);
					if (x == 0) {
						pr.setMessage(OrderConstants.NO_CONDITIONAL_OFFER_ASSOCIATED);
						pr.setCode(USER_ERROR_RC);
					} else {
						pr.setMessage(OrderConstants.SUCESSFULLY_WITH_DRAWN_OFFER);
						pr.setCode(VALID_RC);
					}
				/*} else {
					pr.setMessage(OrderConstants.NO_CONDITIONAL_OFFER_ASSOCIATED_WITH_WFSTATE);
					pr.setCode(USER_ERROR_RC);
				}*/
			}

		}// end else

		catch (DataServiceException e) {
			logger.info("[DataServiceException]" + e);
			throw new BusinessServiceException("Could not withdraw conditional offer", e);

		}// end catch

		return pr;

	}// end method

	/**
	 * This method will make sure that generated order number does not exist in
	 * db.
	 * 
	 * @param buyerId
	 * @return
	 * @throws Exception
	 */
	/*
	 * private String getNewOrderNo(int buyerId) throws Exception { String so_id
	 * = null; //try 3 times for (int i = 0; i < 3; i++) { so_id =
	 * generateOrderNo(buyerId); Object obj =
	 * getServiceOrderDao().getServiceOrder(so_id); if (obj == null) { return
	 * so_id; } logger.info("SO id already exist in db, generating new one."); }
	 * return so_id; }
	 */

	public String generateOrderNo(int buyerId) throws Exception {
		StringBuffer sb = new StringBuffer();
		StringBuffer sbOrder = new StringBuffer();
		String sourceId = "";
		String ts = "";
		String random = "";

		try {
			// buyer source is the 1st part of the service order
			sourceId = getBuyerSourceId(buyerId);
			if ((sourceId == null) || (sourceId.length() == 0)) {
				sourceId = "1";
			} else {
				if (sourceId.length() > 1) {
					sourceId = sourceId.substring(1, 2);
				}
			}
			// current time stamp is the 2nd part of the service order
			// it is at least 13 characters as of today...
			ts = String.valueOf(Calendar.getInstance().getTimeInMillis());
			// remove first 3 chars
			ts = ts.substring(3, 13);

			// a random number is the 3rd part of the key
			random = String.valueOf(RandomUtils.nextInt());
			random = StringUtils.substring(random, 0, 2);

			// put the 3 pieces together
			sb.append(sourceId);
			sb.append(ts);
			sb.append(random);

			// construct the service order format XXX-XXXX-XXXX-XXXX
			sbOrder.append(sb.substring(0, 3));
			sbOrder.append(SERVICE_ORDER_DELIMITER);
			sbOrder.append(sb.substring(3, 7));
			sbOrder.append(SERVICE_ORDER_DELIMITER);
			sbOrder.append(sb.substring(7, 11));
			sbOrder.append(SERVICE_ORDER_DELIMITER);
			sbOrder.append(sb.substring(11));

		} catch (Exception e) {
			logger.error("cannot create a service order, reason: " + e.getMessage());
			throw e;
		}

		return sbOrder.toString();
	}

	private String getBuyerSourceId(int buyerId) throws Exception {
		Buyer buyer = new Buyer();
		try {
			buyer.setBuyerId(buyerId);
			buyer = getBuyerDao().query(buyer);
			if (logger.isDebugEnabled())
				logger.debug("getBuyerSourceId: returned buyer: " + buyer + " for buyerId: " + buyerId);

			// verify that the buyer exists if not throw exception...
			if (buyer == null)
				throw new Exception("buyer id: " + buyerId + " does NOT exist...");

			// verify that the buyer exists if not throw exception...
			if (buyer.getSourceId() == null)
				throw new Exception("buyer id: " + buyerId + " source id does NOT exist...");

		} catch (Exception e) {
			logger.error("getBuyerSourceId:: error looking up buyer, reason: " + e.getMessage());
			throw e;
		}

		// return source id from buyer
		return buyer.getSourceId();
	}

	public ProcessResponse updateSOSubStatus(String serviceOrderId, Integer subStatusId, SecurityContext context) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		if (subStatusId == OrderConstants.NO_SUBSTATUS) {
			subStatusId = null;
		}
		try {
			getServiceOrderDao().updateSOSubStatus(serviceOrderId, subStatusId);

			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			final String error = "Error in updating SO sub Status";
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			logger.info("[Exception] " + ex);
		}
		return processResp;
	}
	
	public ProcessResponse updateSOCustomReference(String soId, String referenceType, String referenceValue, String oldReferenceValue, SecurityContext securityContext)throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			getServiceOrderDao().updateSOCustomReference(soId, referenceType, referenceValue);

			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + dse);
			final String error = "Error in updating SO Custom Reference";
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			logger.info("[Exception] " + ex);
		}
		return processResp;
		
	}

	public void setLockMode(String serviceOrderId, Integer editLockMode) throws BusinessServiceException {
		try {
			getServiceOrderDao().updateLockEditMode(serviceOrderId, editLockMode);
		} catch (DataServiceException e) {
			logger.info("[DataServiceException]" + e);
			final String error = "Error in seting the EditLockMode";
			throw new BusinessServiceException(error, e);
		}

	}

	public ProcessResponse processUpdateDraftSO(ServiceOrder serviceOrder, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			populateSORelatedIds(serviceOrder);
			if (StringUtils.isBlank(serviceOrder.getGroupId())) {
				populateSOPrice(serviceOrder);
			}
			// convert date/time to gmt
			GivenTimeZoneToGMT(serviceOrder);

			// / for simple buyer update the contact info...
			populateSBContactInfo(serviceOrder);

			// Only if order is posted first time, the buyer bid is updated in
			// SO header for Business Object reporting purposes
			Integer serviceOrderState = getServiceOrderDao().checkWfState(serviceOrder.getSoId());
			if (serviceOrderState == null || serviceOrderState == DRAFT_STATUS) {
				double initialPrice = MoneyUtil.add(serviceOrder.getSpendLimitLabor(), serviceOrder.getSpendLimitParts());
				serviceOrder.setInitialPrice(initialPrice);
			}
			getServiceOrderDao().updateServiceOrder(serviceOrder);
			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);

		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("BusinessService error");
			throw new BusinessServiceException("Error in processUpdateDraftSO", dse);
		} catch (Exception ex) {
			logger.error("[Exception] " + ex);
			throw new BusinessServiceException(ex);
		}
		return processResp;
	}

	private void populateSBContactInfo(ServiceOrder so) throws BusinessServiceException {
		try {
			Buyer buyer = getServiceOrderDao().getBuyerAttributes(so.getBuyer().getBuyerId());
			if (buyer != null) {
				Integer roleId = getBuyerDao().getBuyerRole(buyer.getUserName());
				if (roleId != null && roleId.equals(OrderConstants.SIMPLE_BUYER_ROLEID)) {

					so.setEndUserContact(null);

					Contact serviceContact = so.getServiceContact();
					if(null==serviceContact.getLastName()){
						serviceContact.setLastName(buyer.getBuyerContact().getLastName());
					}
					if(null==serviceContact.getFirstName()){
						serviceContact.setFirstName(buyer.getBuyerContact().getFirstName());
					}
					if(null==serviceContact.getEmail()){
						serviceContact.setEmail(buyer.getBuyerContact().getEmail());
					}
					if(null==serviceContact.getEntityTypeId()){
						serviceContact.setEntityTypeId(new Integer(10));
					}
					if(null==serviceContact.getEntityId()){
						serviceContact.setEntityId(buyer.getBuyerId());
					}

					Contact bsContact = so.getBuyerSupportContact();
					if(null==bsContact.getLastName()){
						bsContact.setLastName(buyer.getBuyerContact().getLastName());
					}
					if(null==bsContact.getFirstName()){
						bsContact.setFirstName(buyer.getBuyerContact().getFirstName());
					}
					if(null==bsContact.getEmail()){
						bsContact.setEmail(buyer.getBuyerContact().getEmail());
					}
					if(null==bsContact.getEntityTypeId()){
						bsContact.setEntityTypeId(new Integer(10));
					}
					if(null==bsContact.getEntityId()){
						bsContact.setEntityId(buyer.getBuyerId());
					}

				}

			}
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			final String error = "Error while getting buyer additional details";
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			logger.error("[Exception] " + ex);
		}
	}

	/* Added for BuyerUploadTool */

	public ProcessResponse processUpdateDraftSO(ServiceOrder serviceOrder) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			populateSORelatedIds(serviceOrder);
			if (StringUtils.isBlank(serviceOrder.getGroupId())) {
				populateSOPrice(serviceOrder);
			}
			// convert date/time to gmt
			GivenTimeZoneToGMT(serviceOrder);

			// / for simple buyer update the contact info...
			populateSBContactInfo(serviceOrder);

			getServiceOrderDao().updateServiceOrder(serviceOrder);
			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("BusinessService error");
			throw new BusinessServiceException("Error in processUpdateDraftSO", dse);
		} catch (Exception ex) {
			logger.error("[Exception] " + ex);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("BusinessService error");
		}
		return processResp;
	}

	public void updateSoHdrLogoDocument(String serviceOrderId, Integer logoDocumentId) throws BusinessServiceException {
		try {
			getServiceOrderDao().updateSoHdrLogoDocument(serviceOrderId, logoDocumentId);
		} catch (DataServiceException e) {
			logger.info("[DataServiceException]" + e);
			throw new BusinessServiceException("Error in updating logoDocument", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #isBuyerAssociatedToServiceOrder
	 * (com.newco.marketplace.dto.vo.serviceorder.ServiceOrder,
	 * java.lang.Integer)
	 */
	public boolean isBuyerAssociatedToServiceOrder(ServiceOrder serviceOrderVO, Integer buyerId) throws BusinessServiceException {

		boolean isAssociated = false;

		if (serviceOrderVO.getBuyer().getBuyerId().intValue() == buyerId.intValue()) {
			isAssociated = true;
		}
		return isAssociated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #isVendorAssociatedToServiceOrder
	 * (com.newco.marketplace.dto.vo.serviceorder.ServiceOrder,
	 * java.lang.Integer)
	 */
	public boolean isVendorAssociatedToServiceOrder(ServiceOrder serviceOrderVO, Integer vendorId) throws BusinessServiceException {

		boolean isAssociated = false;

		VendorResourceDao vendorResourceDao = (VendorResourceDao) MPSpringLoaderPlugIn.getCtx().getBean(Constants.ApplicationContextBeans.VENDOR_RESOURCE_DAO);

		VendorResource vendorResource = new VendorResource();
		//FIXME: Why is the vendorId being assigned to the resourceId ???
		vendorResource.setResourceId(vendorId);

		try {
			vendorResource = vendorResourceDao.query(vendorResource);

			if (null != serviceOrderVO.getAcceptedVendorId() && null != vendorResource && serviceOrderVO.getAcceptedVendorId().intValue() == vendorResource.getVendorId().intValue()) {
				isAssociated = true;
			} else {
				// check to see if the service order was routed to the vendor
				List<RoutedProvider> routedProviders = 
					getServiceOrderDao().getRoutedProvidersWithBasicInfo(serviceOrderVO.getSoId());
				for (RoutedProvider rp : routedProviders) {
					if (rp.getResourceId().intValue() == vendorId.intValue() || rp.getVendorId().intValue() == vendorId.intValue()) {
						isAssociated = true;
						break;
					}
				}
			}
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Unable to retrieve vendor data for this service order", e);
		}
		return isAssociated;
	}

	/***************************************************************************
	 * Private Methods
	 **************************************************************************/

	/**
	 * Determines if the service order, corresponding to the to provided soID,
	 * is currently in Edit Mode
	 */
	public boolean isSOInEditMode(String soID) throws BusinessServiceException {
		ServiceOrder so = null;

		try {
			so = this.getServiceOrder(soID);

		}// end try
		catch (BusinessServiceException bse) {
			if (logger.isDebugEnabled())
				logger.debug("Exception thrown while fetching service order(" + soID + ").");
			throw bse;

		}// end catch

		if (so == null) {
			throw new BusinessServiceException("Unable to locate service order(" + soID + ").");

		}// end if
		else {
			if (so.getLoctEditInd() != null && so.getLoctEditInd().equals(new Integer(OrderConstants.SO_EDIT_MODE_FLAG))) {
				return true;
			} else {
				return false;
			}
		}

	}// end method isSOInEditMode

	private ServiceOrder populateSOPrice(ServiceOrder so) {
		ServiceOrderPriceVO soPriceVO = new ServiceOrderPriceVO();

		soPriceVO.setSoId(so.getSoId());
		soPriceVO.setOrigSpendLimitLabor(so.getSpendLimitLabor());
		soPriceVO.setOrigSpendLimitParts(so.getSpendLimitParts());
		soPriceVO.setDiscountedSpendLimitLabor(so.getSpendLimitLabor());
		soPriceVO.setDiscountedSpendLimitParts(so.getSpendLimitParts());

		so.setSoPrice(soPriceVO);

		return so;

	}

	private ServiceOrder populateSORelatedIds(ServiceOrder so) {
		try {
			if (so.getServiceLocation() == null) {
				SoLocation soLocation = new SoLocation();
				soLocation.setLocnTypeId(10);
				so.setServiceLocation(soLocation);
			}

			so.getServiceLocation().setLocnTypeId(10);
//			if (so.getServiceLocation().getLocationId() == null) {
//				so.getServiceLocation().setLocationId(randomNo.generateGUID().intValue());
//			}

		} catch (Exception e) {
			logger.info("Caught exception and ignoring", e);
		}

		try {
			if (so.getBuyerSupportLocation() == null) {
				SoLocation soLocation = new SoLocation();
				soLocation.setLocnTypeId(30);
				so.setBuyerSupportLocation(soLocation);
			}
			so.getBuyerSupportLocation().setLocnTypeId(30);
//			if (so.getBuyerSupportLocation().getLocationId() == null) {
//				so.getBuyerSupportLocation().setLocationId(randomNo.generateGUID().intValue());
//			}
		} catch (Exception e) {
			logger.info("Caught exception and ignoring", e);
		}

		try {
			if (so.getServiceContact() == null) {
				Contact soContact = new Contact();
				soContact.setContactTypeId(10);
				so.setServiceContact(soContact);
			}
			so.getServiceContact().setContactTypeId(10);
//			if (so.getServiceContact().getContactId() == null) {
//				//so.getServiceContact().setContactId(randomNo.generateGUID().intValue());
//			}
			if (so.getServiceContact().getPhones() != null) {
				List<PhoneVO> phonesVo = so.getServiceContact().getPhones();
				List<PhoneVO> phones = new ArrayList<PhoneVO>();
				if (CollectionUtils.size(phonesVo) > 0) {
					Iterator<PhoneVO> phonesItr = phonesVo.iterator();
					while (phonesItr.hasNext()) {
						PhoneVO phoneVo = (PhoneVO) phonesItr.next();
						phoneVo.setSoId(so.getSoId());
//						phoneVo.setSoContactId(so.getServiceContact().getContactId());
//						if (phoneVo.getSoContactPhoneId() == null) {
//							//phoneVo.setSoContactPhoneId(randomNo.generateGUID().intValue());
//						}
						phones.add(phoneVo);
					}
					so.getServiceContact().setPhones(phones);
				}
			}

		} catch (Exception e) {
			logger.info("Caught exception and ignoring", e);
		}

		try {
			if (so.getBuyerSupportContact() == null) {
				Contact soContact = new Contact();
				soContact.setContactTypeId(10);
				so.setBuyerSupportContact(soContact);
			}
			so.getBuyerSupportContact().setContactTypeId(10);
//			if (so.getBuyerSupportContact().getContactId() == null) {
//				so.getBuyerSupportContact().setContactId(randomNo.generateGUID().intValue());
//			}
		} catch (Exception e) {
			logger.info("Caught exception and ignoring", e);
		}

		try {
			if (so.getEndUserContact() == null) {
				Contact soContact = new Contact();
				soContact.setContactTypeId(10);
				so.setEndUserContact(soContact);
			}
			so.getEndUserContact().setContactTypeId(10);
//			if (so.getEndUserContact().getContactId() == null) {
//				so.getEndUserContact().setContactId(randomNo.generateGUID().intValue());
//			}
		} catch (Exception e) {
			logger.info("Caught exception and ignoring", e);
		}

		// create tasks from request
		List<ServiceOrderTask> reqTasks = so.getTasks();
		ArrayList<ServiceOrderTask> tasks = new ArrayList<ServiceOrderTask>();
		if (reqTasks != null) {
			if (CollectionUtils.size(reqTasks) > 0) {
				Iterator<ServiceOrderTask> tasksIter = reqTasks.iterator();
				while (tasksIter.hasNext()) {
					ServiceOrderTask reqTask = tasksIter.next();
//					try {
//						if (reqTask.getTaskId() == null) {
//							//reqTask.setTaskId(randomNo.generateGUID().intValue());
//						}
						tasks.add(reqTask);
//					} catch (Exception e) {
//						logger.info("Caught exception and ignoring", e);
//					}
				}
				so.setTasks(tasks);
			}
		}

		// add the parts if there are any...
		List<Part> reqParts = so.getParts();
		ArrayList<Part> parts = new ArrayList<Part>();

		if (reqParts != null) {
			if (CollectionUtils.size(reqParts) > 0) {
				Iterator<Part> partsIter = reqParts.iterator();
				while (partsIter.hasNext()) {
					Part reqPart = partsIter.next();
//					try {
//						if (reqPart.getPartId() == null) {
//							//reqPart.setPartId(randomNo.generateGUID().intValue());
//						}
//					} catch (Exception e) {
//						logger.info("Caught exception and ignoring", e);
//					}
//					boolean phoneCheck = false;
//					if (reqPart.getPickupContact() != null && reqPart.getPickupContact().getPhones() != null && reqPart.getPickupContact().getPhones().size() > 0) {
//						phoneCheck = true;
//
//					}
					try {
						if (reqPart.getPickupLocation() != null) {
//							if (reqPart.getPickupLocation().getLocationId() == null && (reqPart.getPickupContact().getEmail() != null || reqPart.getPickupContact().getBusinessName() != null || reqPart.getPickupContact().getFirstName() != null || reqPart.getPickupContact().getLastName() != null || !reqPart.getPickupLocation().getCity().equals("") || !reqPart.getPickupLocation().getStreet1().equals("") || !reqPart.getPickupLocation().getStreet2().equals("") || !reqPart.getPickupLocation().getAptNo().equals("") || !reqPart.getPickupLocation().getStreet1().equals("") || !reqPart.getPickupLocation().getZip().equals("") || !reqPart.getPickupLocation().getState().equals("") || phoneCheck)) {
//								reqPart.getPickupLocation().setLocationId(randomNo.generateGUID().intValue());
//							}
							reqPart.getPickupLocation().setLocnTypeId(40);
						}
					} catch (Exception e) {
						logger.info("Caught exception and ignoring", e);
					}
					try {
						if (reqPart.getPickupContact() != null) {
//							if (reqPart.getPickupContact().getContactId() == null && (reqPart.getPickupContact().getEmail() != null || reqPart.getPickupContact().getBusinessName() != null || reqPart.getPickupContact().getFirstName() != null || reqPart.getPickupContact().getLastName() != null || !reqPart.getPickupLocation().getCity().equals("") || !reqPart.getPickupLocation().getStreet1().equals("") || !reqPart.getPickupLocation().getStreet2().equals("") || !reqPart.getPickupLocation().getAptNo().equals("") || !reqPart.getPickupLocation().getStreet1().equals("") || !reqPart.getPickupLocation().getZip().equals("") || !reqPart.getPickupLocation().getState().equals("") || phoneCheck)) {
//								reqPart.getPickupContact().setContactId(randomNo.generateGUID().intValue());
//							}

							reqPart.getPickupContact().setContactTypeId(10);
							if (reqPart.getPickupContact().getPhones() != null) {
								List<PhoneVO> phonesVo = reqPart.getPickupContact().getPhones();
								List<PhoneVO> phones = new ArrayList<PhoneVO>();
								if (CollectionUtils.size(phonesVo) > 0) {
									Iterator<PhoneVO> phonesItr = phonesVo.iterator();
									while (phonesItr.hasNext()) {
										PhoneVO phoneVo = (PhoneVO) phonesItr.next();
										phoneVo.setSoId(so.getSoId());
//										phoneVo.setSoContactId(reqPart.getPickupContact().getContactId());
//										if (phoneVo.getSoContactPhoneId() == null) {
//											//phoneVo.setSoContactPhoneId(randomNo.generateGUID().intValue());
//										}
										phones.add(phoneVo);
									}
									reqPart.getPickupContact().setPhones(phones);
								}
							}
						}
					} catch (Exception e) {
						logger.info("Caught exception and ignoring", e);
					}
					parts.add(reqPart);
				}
				so.setParts(parts);
			}
		}
		if (so.getCustomRefs() != null && so.getCustomRefs().size() > 0) {
			for (ServiceOrderCustomRefVO vo : so.getCustomRefs()) {
				// fill service order ID
				vo.setsoId(so.getSoId());
			}
		}
		return so;
	}

	public ProcessResponse deleteDraftSO(String soId, SecurityContext securityContext) throws BusinessServiceException {
		ServiceOrder serviceOrder = getServiceOrder(soId);
		ProcessResponse pr = null;
		if (null != serviceOrder) {
			pr = deleteDraftSO(serviceOrder.getBuyer().getBuyerId(), serviceOrder.getSoId(),serviceOrder.getGroupId(), securityContext);
			if (pr.getCode().equals(ProcessResponse.VALID_RC)) {
				// If this is a child order of a grouped order; then re-price
				// the entire group
				String groupId = serviceOrder.getGroupId();
				if (StringUtils.isNotBlank(groupId)) {
					try {
						IOrderGroupBO orderGroupBO = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean(ABaseRequestDispatcher.ORDER_GROUP_BUSINESS_OBJECT_REFERENCE);
						boolean applyTripCharge = false;
						boolean updateOriginalGroupPrice = true;
						orderGroupBO.rePriceOrderGroup(groupId, applyTripCharge, updateOriginalGroupPrice);
					} catch (com.newco.marketplace.exception.BusinessServiceException bsEx) {
						throw new BusinessServiceException(bsEx.getMessage(), bsEx);
					}
				}
			}
		} else {
			pr = new ProcessResponse();
			pr.setMessage(OrderConstants.DELETE_DRAFT_FAILURE);
			pr.setCode(USER_ERROR_RC);
		}
		return pr;
	}

	/**
	 * Delete the Draft SO
	 */
	private ProcessResponse deleteDraftSO(Integer buyerID, String soId,String groupId, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse pr = new ProcessResponse();
		String status = "false";
		try {
			if (isSOInEditMode(soId)) {
				pr.setCode(USER_ERROR_RC);
				pr.setMessage("Unable to complete action.  Service Order is currently being edited.");
			} else {
				Integer wfState = getServiceOrderDao().checkWfState(soId);
				if (wfState.equals(DRAFT_STATUS)) {
					status = getServiceOrderDao().deleteServiceOrder(soId,groupId);
					if ("true".equals(status)) {
						try {
							documentDao.deleteDocumentEntity(soId);
						} catch (DataServiceException e) {
							logger.error(e.getMessage());
						}
						pr.setMessage(OrderConstants.SUCESSFULLY_DELETED_DRAFT);
						pr.setCode(VALID_RC);
					} else {
						pr.setMessage(OrderConstants.DELETE_DRAFT_FAILURE);
						pr.setCode(USER_ERROR_RC);
					}
				} else {
					pr.setMessage(OrderConstants.SERVICE_ORDER_NOT_IN_DRAFT_STATE);
					pr.setCode(USER_ERROR_RC);
				}
			}
		} catch (DataServiceException e) {
			logger.info("[DataServiceException]" + e);
			throw new BusinessServiceException("Could not delete draft so", e);
		}
		return pr;
	}



	/*-----------------------------------------------------------------
	 * This method is called from a batch process to mark orders as active
	 * @param 		soId - ServiceOrder Id
	 * @param		role - ServiceLive RoleId
	 * @param		userName - System
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse activateAcceptedSO(String soId, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		Timestamp now = new Timestamp(calendar.getTimeInMillis());
		ServiceOrder so = new ServiceOrder();
		so.setSoId(soId);
		so.setWfStateId(OrderConstants.ACTIVE_STATUS);
		so.setLastChngStateId(so.getWfStateId());
		so.setWfSubStatusId(null);
		so.setLastStatusChange(now);
		so.setActivatedDate(now);

		try {
			getServiceOrderDao().updateSOStatus(so);

			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			final String error = "Error in updating SO Status";
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			logger.error("[Exception] " + ex);
		}
		return processResp;
	}

	/*-----------------------------------------------------------------
	 * This method is called from a batch process to mark orders as expired
	 * @param 		soId - ServiceOrder Id
	 * @param		role - ServiceLive RoleId
	 * @param		userName - System
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse expirePostedSO(String soId, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		Timestamp now = new Timestamp(calendar.getTimeInMillis());
		ServiceOrder so = new ServiceOrder();
		so.setSoId(soId);
		so.setWfStateId(OrderConstants.EXPIRED_STATUS);
		so.setWfSubStatusId(null);
		so.setLastChngStateId(so.getWfStateId());
		so.setLastStatusChange(now);
		so.setExpiredDate(now);

		try {
			if (this.isSOInEditMode(soId)) {
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessage("Unable to complete action.  Service Order is currently being edited.");

			}// end if
			else {
				getServiceOrderDao().updateSOStatus(so);

				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
			}
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			final String error = "Error in updating SO Status";
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			logger.error("[Exception] " + ex);
		}
		return processResp;
	}

	public ProcessResponse expireConditionalOffer(String soId, Integer vendorResourceId, String groupId,
			SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		try {
			processResp = withdrawConditionalAcceptance(soId, vendorResourceId, OrderConstants.CONDITIONAL_OFFER, securityContext);
			
			// If the SO is a grouped order then remove the conditional offer from the routed group details
			if ((null != groupId) && (null != processResp.getCode()) && processResp.getCode().equals(VALID_RC)){
				RoutedProvider rop = new RoutedProvider();
				rop.setSoId(groupId);
				rop.setResourceId(vendorResourceId);
				rop.setProviderRespId(OrderConstants.CONDITIONAL_OFFER);
				int parentOrdUpdCnt = orderGroupDao.withdrawGroupConditionalAcceptance(rop);
				if (parentOrdUpdCnt==0) {
					processResp.setMessage(OrderConstants.NO_CONDITIONAL_OFFER_ASSOCIATED);
					processResp.setCode(ServiceConstants.USER_ERROR_RC);
				} else {
					processResp.setMessage(OrderConstants.SUCESSFULLY_WITH_DRAWN_OFFER);
					processResp.setCode(ServiceConstants.VALID_RC);
				}
			}
		} catch (BusinessServiceException bse) {
			processResp.setCode(USER_ERROR_RC);
			processResp.setSubCode(USER_ERROR_RC);
		}
		catch (DataServiceException dse) {
			processResp.setCode(USER_ERROR_RC);
			processResp.setSubCode(USER_ERROR_RC);
		}
		return processResp;
	}

	public void deleteOldServiceOrders(Integer numberDaysOld) throws BusinessServiceException {
		try {
			getServiceOrderDao().deleteOldServiceOrders(numberDaysOld - 1);
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
	}

	/**
	 * Stub for change of scope request. Functionality will be added later -
	 * right now AOP wraps this method and sends an alert.
	 */
	public ProcessResponse processChangeOfScope(ServiceOrder so, SecurityContext securityContext) {
		ProcessResponse response = new ProcessResponse();
		logger.debug("Change of scope method fired - AOP should send an alert");
		response.setCode(ServiceConstants.VALID_RC);
		return response;
	}

	/**
	 * Stub for pickup location change. AOP wraps this method and sends an
	 * alert.
	 */
	public ProcessResponse processChangePartPickupLocation(String soID, SecurityContext securityContext) {
		ProcessResponse response = new ProcessResponse();
		logger.debug("processChangePartPickupLocation method fired - AOP should send an alert");
		response.setCode(ServiceConstants.VALID_RC);
		return response;
	}

	private ServiceOrder populateBuyerAttributes(ServiceOrder so) throws BusinessServiceException {
		try {
			Buyer buyer;
			// Using cache
			// if (useCache) {
			// buyer = getBuyer(so.getBuyer().getBuyerId());
			// } else {
			buyer = getServiceOrderDao().getBuyerAttributes(so.getBuyer().getBuyerId());
			// }

			if (buyer != null && (so.getWfStateId() == null || so.getWfStateId() == OrderConstants.DRAFT_STATUS)) {
				if (buyer.getFundingTypeId() != null) {
					so.setFundingTypeId(buyer.getFundingTypeId());
				}
				so.setPostingFee(buyer.getPostingFee());
				so.setCancellationFee(buyer.getCancellationFee());
				promoBO.applyPromoPostingFee(so, buyer);
			}
		} catch (DataServiceException dse) {
			logger.error("[DataServiceException] " + dse);
			final String error = "Error while getting buyer additional details";
			throw new BusinessServiceException(error, dse);
		} catch (Exception ex) {
			logger.error("[Exception] " + ex);
		}

		return so;
	}

	/*
	 * private Buyer getBuyer(Integer buyerId) throws DataServiceException {
	 * final String key = "buyer" + "_buyerId_" + buyerId; Buyer buyer =
	 * (Buyer)SimpleCache.getInstance().get(key); if (buyer == null) { buyer =
	 * getServiceOrderDao().getBuyerAttributes(buyerId);
	 * SimpleCache.getInstance().put(key, buyer, SimpleCache.FIVE_MINUTES); }
	 * return buyer; }
	 */
	/*-----------------------------------------------------------------
	 * This method is called from a Completion Record - EditCompletion 
	 * till buyer has not closed it to set it back to Active.
	 * @param 		soId - ServiceOrder Id
	 * @returns 	ProcessResponse
	 *-----------------------------------------------------------------*/
	public ProcessResponse editCompletionRecordForSo(String soId, SecurityContext ctx) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		Calendar calendar = Calendar.getInstance();
		List<String> arr = new ArrayList<String>();
		Timestamp now = new Timestamp(calendar.getTimeInMillis());
		ServiceOrder so = new ServiceOrder();
		so.setSoId(soId);
		so.setWfStateId(OrderConstants.ACTIVE_STATUS);
		so.setLastChngStateId(so.getWfStateId());
		so.setWfSubStatusId(null);
		so.setLastStatusChange(now);
		so.setActivatedDate(now);

		int updCnt = 0;
		try {
			ServiceOrder serviceOrderVO = getServiceOrderDao().getServiceOrder(soId);
			if (serviceOrderVO.getWfStateId().equals(OrderConstants.CLOSED_STATUS)) {
				processResp.setCode(USER_ERROR_RC);
				processResp.setSubCode(USER_ERROR_RC);
				arr.add(EDIT_COMPLETIONRECORD_FAILURE);
				arr.add(EDIT_COMPLETIONRECORD_FAILURE_ALREADY_COMPLETED);
				processResp.setMessages(arr);
				return processResp;
			}

			updCnt = getServiceOrderDao().updateSOStatus(so);
			if (updCnt > 0) {
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				arr.add(EDIT_COMPLETIONRECORD_SUCCESS);
			} else {
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				arr.add(EDIT_COMPLETIONRECORD_FAILURE);
			}

			processResp.setMessages(arr);

		} catch (DataServiceException dse) {
			throw new BusinessServiceException("Error in updating SO Status", dse);
		} catch (Exception ex) {
			logger.info("Caught exception and ignoring", ex);
		}
		return processResp;
	}

	public ArrayList getRoutedResources(String soId, String companyId) throws BusinessServiceException {
		ArrayList result = null;
		try {
			result = getServiceOrderDao().getRoutedResources(soId, companyId);
		} catch (DataServiceException e) {
			logger.error("OnSiteVisitBOImpl :Unable to get the routed resources list for given service order ");
			throw new BusinessServiceException(e.getMessage(), e);
		}
		return result;
	}

	public List<Integer> getRoutedResourceIds(String soId) throws com.newco.marketplace.exception.BusinessServiceException {
		List<Integer> routedResourcesIds = new ArrayList<Integer>();
		;
		try {
			List<RoutedProviderResponseVO> routedResources = this.getRoutedResourcesResponseInfo(soId);
			for (RoutedProviderResponseVO responseSoVO : routedResources) {
				routedResourcesIds.add(responseSoVO.getResourceId());
			}
		} catch (Exception e) {
			logger.error("Unable to get the Ids of routed resources for service order " + soId, e);
			throw new com.newco.marketplace.exception.BusinessServiceException("Unable to get the Ids of routed resources service order " + soId, e);
		}
		return routedResourcesIds;
	}

	public List<RoutedProviderResponseVO> getRoutedResourcesResponseInfo(String soId) throws com.newco.marketplace.exception.BusinessServiceException {
		List<RoutedProviderResponseVO> routedResources = null;
		try {
			routedResources = getServiceOrderDao().getRoutedResourcesResponseInfo(soId);
		} catch (DataServiceException e) {
			logger.error("Unable to get the Ids of routed resources for service order " + soId, e);
			throw new com.newco.marketplace.exception.BusinessServiceException("Unable to get the Ids of routed resources service order " + soId, e);
		}
		return routedResources;
	}

	public ProcessResponse deleteRoutedProviders(String soId) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			getServiceOrderDao().deleteRoutedProviders(soId);
			processResp.setCode(VALID_RC);
			processResp.setSubCode(VALID_RC);
		} catch (Exception e) {
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setSubCode(SYSTEM_ERROR_RC);
			throw new BusinessServiceException("Error in deleting routed Providers -->SoId " + soId, e);
		}
		return processResp;

	}

	public ProcessResponse saveReassignSO(SoLoggingVo soLoggingVO, ServiceOrderNote soNote, String reassignReason, SecurityContext securityContext) {
		ProcessResponse processResp = new ProcessResponse();
		List<String> messageArray = new ArrayList<String>();
		int success = 0;
		int updateSOHdrStatus = 0;
		int updateSOContactStatus;
		int updateSoRoutedProvider=0;
		boolean relayServicesNotifyFlag = false;
		
		try {
			//set reassignreason in loggingVo to update in so_routed_provider table
			if(null!=soLoggingVO && StringUtils.isNotEmpty(reassignReason)){
			     soLoggingVO.setReassignReason(reassignReason);
			  }
			updateSOHdrStatus = getServiceOrderDao().soHdrTableUpdate(soLoggingVO);
			updateSOContactStatus = getServiceOrderDao().soContactTableUpdate(soLoggingVO);
			updateSoRoutedProvider=getServiceOrderDao().soRoutedProviderUpdate(soLoggingVO);
			getServiceOrderDao().soLoggingInsert(soLoggingVO);
			getServiceOrderDao().soNotesInsert(soNote);
			if (updateSOHdrStatus > 0 && updateSOContactStatus > 0 && updateSoRoutedProvider > 0 )
				success = 1;
			if (success > 0) {
				processResp.setCode(VALID_RC);
				processResp.setSubCode(VALID_RC);
				messageArray.add("The service order has been reassigned successfully.");
				logger.info("Reassignment of service order successful");
				ServiceOrder serviceOrder=getServiceOrder(soLoggingVO.getServiceOrderNo());
				
				if(null!=serviceOrder)
				{
					pushNotificationAlertTask.AddAlert(serviceOrder, "Assigned Service Order - PUSH");
					relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(serviceOrder.getBuyerId(),soLoggingVO.getServiceOrderNo());
					if(relayServicesNotifyFlag){
						relayNotificationService.sentNotificationRelayServices(CommonConstants.PROVIDER_REASSIGN_API_EVENT, soLoggingVO.getServiceOrderNo());
					}
				}
			} else {
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				messageArray.add("Reassignment of service order failed. Please try again.");
				logger.error("Reassignment of service order failed.");
			}
			processResp.setMessages(messageArray);

		} catch (Throwable t) {
			logger.error("Reassignment of Service Order:: error:  " + t.getMessage(), t);
			processResp.setCode(SYSTEM_ERROR_RC);
		}
		return processResp;
	}

	public boolean isValidMainCategory(Integer mainCategoryId) throws DataServiceException {
		int cnt;
		try {
			cnt = getServiceOrderDao().getMainServiceCategoryCount(mainCategoryId);
			if (cnt > 0)
				return true;

		} catch (DataServiceException e) {
			logger.info("Caught exception and ignoring", e);
		}
		return false;
	}

	public ProcessResponse updateServiceOrder(ServiceOrder matchingSO, ServiceOrder updatedSO, SecurityContext securityContext, String clientStatus, String templateName) throws BusinessServiceException {

		IProBuyerUpdateBO proBuyerUpdateBO = proBuyerBOFactory.getProBuyerUpdateBOByClientId(securityContext.getClientId());

		Map<String, Object> buyerParameters = new HashMap<String, Object>();
		buyerParameters.put(OrderConstants.CommonProBuyerParameters.CLIENT_STATUS, clientStatus);
		buyerParameters.put(OrderConstants.CommonProBuyerParameters.TEMPLATE_NAME, templateName);

		return proBuyerUpdateBO.updateServiceOrder(matchingSO, updatedSO, buyerParameters, securityContext);

	}

	/**
	 * The custom reference value must be flagged in the database thru the
	 * buyer_reference table as the service identifier (so_identifier = 1)
	 * 
	 * @param customReferenceValue
	 * @param buyerID
	 * @return
	 */
	public ServiceOrder getByCustomReferenceValue(String customReferenceValue, Integer buyerID) throws BusinessServiceException {
		ServiceOrder so = null;
		try {
			so = getServiceOrderDao().getServiceOrder(customReferenceValue, buyerID);
		} catch (DataServiceException e) {
			logger.error("Ignoring exception getByCustomReferenceValue " + e.getMessage(), e);
		}
		return so;
	}

	public ServiceOrder getByCustomReferenceTypeValue(String customReferenceType, String customReferenceValue, Integer buyerID) throws BusinessServiceException {
		ServiceOrder so = null;
		try {
			so = getServiceOrderDao().getServiceOrder(customReferenceType, customReferenceValue, buyerID);
		} catch (DataServiceException e) {
			logger.error("Ignoring exception getByCustomReferenceTypeValue " + e.getMessage(), e);
		}
		return so;
	}

	public Buyer getBuyerAttrFromBuyerId(Integer buyerId) throws BusinessServiceException {
		Buyer buyer = null;
		logger.info("buyerId " + buyerId);
		try {
			buyer = getServiceOrderDao().getBuyerAttributes(buyerId);
		} catch (Exception ex) {
			logger.error("[Exception] " + ex);
		}
		/**
		 * We will need to do something here, it is non perfect by a large
		 * margin
		 */
		ServiceOrder aDummyServiceOrder = new ServiceOrder();
		aDummyServiceOrder.setPostingFee(buyer.getPostingFee());
		// And then call
		promoBO.applyPromoPostingFee(aDummyServiceOrder, buyer);
		// and then
		buyer.setPostingFee(aDummyServiceOrder.getPostingFee());

		return buyer;
	}

	public String getDSTTimezone(ServiceOrder so) {
		String DSTTimezone = "";
		String timezone = so.getServiceLocationTimeZone();
		if ("EST5EDT".equals(timezone)) {
			DSTTimezone = "EDT";
		} else if ("AST4ADT".equals(timezone)) {
			DSTTimezone = "ADT";
		} else if ("CST6CDT".equals(timezone)) {
			DSTTimezone = "CDT";
		} else if ("MST7MDT".equals(timezone)) {
			DSTTimezone = "MDT";
		} else if ("PST8PDT".equals(timezone)) {
			DSTTimezone = "PDT";
		} else if ("HST".equals(timezone)) {
			DSTTimezone = "HADT";
		} else if ("Etc/GMT+1".equals(timezone)) {
			DSTTimezone = "CEDT";
		} else if ("AST".equals(timezone)) {
			DSTTimezone = "AKDT";
		}
		return DSTTimezone;
	}

	public String getStandardTimezone(ServiceOrder so) {
		String StandardTimezone = "";
		String timezone = so.getServiceLocationTimeZone();
		if ("EST5EDT".equals(timezone)) {
			StandardTimezone = "EST";
		} else if ("AST4ADT".equals(timezone)) {
			StandardTimezone = "AST";
		} else if ("CST6CDT".equals(timezone)) {
			StandardTimezone = "CST";
		} else if ("MST7MDT".equals(timezone)) {
			StandardTimezone = "MST";
		} else if ("PST8PDT".equals(timezone)) {
			StandardTimezone = "PST";
		} else if ("HST".equals(timezone)) {
			StandardTimezone = "HAST";
		} else if ("Etc/GMT+1".equals(timezone)) {
			StandardTimezone = "CET";
		} else if ("AST".equals(timezone)) {
			StandardTimezone = "AKST";
		} else if ("Etc/GMT-9".equals(timezone)) {
			StandardTimezone = "PST-7";
		} else if ("MIT".equals(timezone)) {
			StandardTimezone = "PST-3";
		} else if ("NST".equals(timezone)) {
			StandardTimezone = "PST-4";
		} else if ("Etc/GMT-10".equals(timezone)) {
			StandardTimezone = "PST-6";
		} else if ("Etc/GMT-11".equals(timezone)) {
			StandardTimezone = "PST-5";
		}
		return StandardTimezone;
	}

	public boolean isAssociatedToViewSOAsPDF(String soId, Integer roleId, Integer requestingUserId) throws BusinessServiceException {

		boolean userIsAssociated = false;

		try {
			// make sure user requesting SOId is associated with service order
			ServiceOrder serviceOrderVO = this.getServiceOrderDao().getServiceOrder(soId);
			if (null != serviceOrderVO) {
				if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
					userIsAssociated = isBuyerAssociatedToServiceOrder(serviceOrderVO, requestingUserId);
				} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
					userIsAssociated = isVendorAssociatedToServiceOrder(serviceOrderVO, requestingUserId);
				}
			}

		} catch (DataServiceException e) {
			logger.error("[DataServiceException] " + e);
			throw new BusinessServiceException("Error retrieving service order document by document id", e);
		}
		return userIsAssociated;

	}

	public void insertSoCustomReference(ServiceOrderCustomRefVO soRefVO) {
		try {
			int updates = getServiceOrderDao().updateSoCustomReference(soRefVO);
			if (updates == 0) {
				this.getServiceOrderDao().insertSoCustomReference(soRefVO);
			}
		} catch (Exception e) {
			logger.info("Exception in insertBuyerCustomReference(" + soRefVO.getsoId() + "," + soRefVO.getRefType() + ") - ignoring", e);
		}
	}
	public void insertSoLogging(SoLoggingVo soLoggingVO) {
		try {
			this.getServiceOrderDao().soLoggingInsert(soLoggingVO);
		} catch (Exception e) {
			logger.info("Exception in insertSoLogging", e);
		}
	}
	/**
	 * Updates the discounted spend limit in the SO_PRICE table.
	 * 
	 * @param soId
	 * @param discountedSpendLimitLabor
	 * @param discountedSpendLimitParts
	 * @throws BusinessServiceException
	 */
	private void updateDiscountedSpendLimit(String soId, double discountedSpendLimitLabor, double discountedSpendLimitParts) throws BusinessServiceException {
		logger.info("Updating conditional price = " + discountedSpendLimitLabor + " parts price = " + discountedSpendLimitParts );
		ServiceOrderPriceVO soPriceVO = new ServiceOrderPriceVO();
		soPriceVO.setSoId(soId);
		soPriceVO.setDiscountedSpendLimitLabor(discountedSpendLimitLabor);
		soPriceVO.setDiscountedSpendLimitParts(discountedSpendLimitParts);
		try {
			getServiceOrderDao().updateSOPrice(soPriceVO);
			logger.info("Service Order " + soId + " Discounted spend limit - Update successful.");
		} catch (DataServiceException dataEx) {
			logger.error("Unexpected error occured while updating discounted spend limit for order " + soId, dataEx);
			throw new BusinessServiceException("Unexpected error occured while discounted spend limit for " + soId, dataEx);
		}
	}
	
	public void updateSOPricing(ServiceOrderPriceVO soPriceVO) throws BusinessServiceException {
		logger.info("Updating so pricing for so: " + soPriceVO.getSoId());
		try {
			getServiceOrderDao().updateSOPrice(soPriceVO);
			logger.info("Service Order " + soPriceVO.getSoId() + " Discounted spend limit - Update successful.");
		} catch (DataServiceException dataEx) {
			logger.error("Unexpected error occured while updating discounted spend limit for order " + soPriceVO.getSoId(), dataEx);
			throw new BusinessServiceException("Unexpected error occured while discounted spend limit for " + soPriceVO.getSoId(), dataEx);
		}
	}


	public void updateServiceContact(ServiceOrder updatedServiceOrder, ServiceOrder so, SecurityContext securityContext) {
		try {
			serviceOrderUpdateBO.updateServiceContact(updatedServiceOrder, so, securityContext);
		} catch (BusinessServiceException e) {
			logger.error("Exception :" + e);
		}
	}

	public void updateServiceLocation(String soId, SoLocation location) {
		getServiceOrderDao().updateServiceLocation(soId, location);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #checkBuyerFundsForIncreasedSpendLimit
	 * (com.newco.marketplace.dto.vo.serviceorder.ServiceOrder,
	 * java.lang.Integer)
	 */
public FundingVO checkBuyerFundsForIncreasedSpendLimit(ServiceOrder serviceOrder, Integer buyerId) throws BusinessServiceException {
		FundingVO fundingVO = new FundingVO();
		fundingVO.setEnoughFunds(true);
		fundingVO.setDecreaseFunds(false);
		AjaxCacheVO avo = new AjaxCacheVO();
		avo.setCompanyId(buyerId);
		avo.setRoleType("BUYER");
		// Check if the buyer has enough funds
		serviceOrder.setBuyer(new Buyer());
		serviceOrder.getBuyer().setBuyerId(buyerId);
		serviceOrder = populateBuyerAttributes(serviceOrder);
		Double accessfee = 0.0;

		// Don't bother calculating the funding for Non-funded orders (Funding
		// type id = 10)
		if (serviceOrder.getFundingTypeId() != LedgerConstants.FUNDING_TYPE_NON_FUNDED) {
			try {
				accessfee = serviceOrder.getPostingFee();
			} catch (Exception e) {
				logger.error("enoughBuyerFunds()-->EXCEPTION-->", e);
			}
			// Get the project funding for this SO.
			Double soProjectBalance = walletBO.getCurrentSpendingLimit(serviceOrder.getSoId());
			// Get Upsell item total for this SO
			Double upsellAmt = calcUpsellAmount(serviceOrder);
			// Subtract the part, labor, posting fee and upsell total from the
			// SO's project funding.
			Double newSpendLimit = 0.0;
			//Only if service order is in a completed or closed status we need to compute addon sku price 
			if(serviceOrder.getWfStateId() != null && serviceOrder.getWfStateId() >= COMPLETED_STATUS)
			{
				newSpendLimit = MoneyUtil.subtract(
					(serviceOrder.getSpendLimitLabor() + serviceOrder.getSpendLimitParts() + upsellAmt),
					soProjectBalance);
			}
			else
			{
				newSpendLimit = MoneyUtil.subtract(
					(serviceOrder.getSpendLimitLabor() + serviceOrder.getSpendLimitParts()),
					soProjectBalance);
			}
			// Only apply posting fee calculation to Draft orders.
			if (serviceOrder.getWfStateId() != null && serviceOrder.getWfStateId() == OrderConstants.DRAFT_STATUS) {
				newSpendLimit = MoneyUtil.add(newSpendLimit, accessfee);
			}
			fundingVO.setSoProjectBalance(soProjectBalance);
			fundingVO.setSoId(serviceOrder.getSoId());
			fundingVO.setAvailableBalance(0.0);

			if (serviceOrder.getFundingTypeId() != LedgerConstants.SHC_FUNDING_TYPE 
					&& serviceOrder.getFundingTypeId() != LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER) {
				// Get the buyer's current (available balance + project funds)
				Double availableBalance = walletBO.getBuyerAvailableBalance(avo.getCompanyId());

				// Credit Card check
				if (serviceOrder.getFundingAmountCC() != null) {
					if (serviceOrder.getFundingAmountCC().doubleValue() > availableBalance) {
						fundingVO.setEnoughFunds(false);
						fundingVO.setAmtToFund(serviceOrder.getFundingAmountCC().doubleValue() - availableBalance);
					}
				}

				// Check increase /decrease amount before total.
				if (newSpendLimit > 0.0) {
					if (newSpendLimit > availableBalance) {
						fundingVO.setEnoughFunds(false);
						fundingVO.setAmtToFund(newSpendLimit - availableBalance);
					} else if (soProjectBalance > 0.0) {
						fundingVO.setEnoughFunds(true);
						fundingVO.setIncreaseAmt(newSpendLimit);
					}
				} else if (newSpendLimit < 0.0) {
					fundingVO.setDecreaseFunds(true);
					fundingVO.setAmtToRefund(newSpendLimit);
				}
				fundingVO.setAvailableBalance(availableBalance);
			} else if (serviceOrder.getFundingTypeId() == LedgerConstants.SHC_FUNDING_TYPE 
					|| serviceOrder.getFundingTypeId() == LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER) {
				// If they are pre-funded skip the check for available balance
				// and fund the full amount
				// Credit Card check
				fundingVO.setEnoughFunds(true);
				if (serviceOrder.getFundingAmountCC() != null) {
					fundingVO.setAmtToFund(serviceOrder.getFundingAmountCC().doubleValue());
				}
				// Check increase /decrease amount before total.
				if (newSpendLimit > 0.0) {
					fundingVO.setAmtToFund(newSpendLimit);
				} else if (newSpendLimit < 0.0) {
					fundingVO.setDecreaseFunds(true);
					fundingVO.setAmtToRefund(newSpendLimit);
				}
			}
		}

		return fundingVO;
	}

	/**
	 * Description: Retrieve upsell items from SO or by doing a read from the DB
	 * 
	 * @param serviceOrder
	 * @return <code>Double</code>
	 * @param string
	 */
	private void populateFundingVO(FundingVO fundingVO, boolean enoughFunds, Double increasedSpendLimit, Double availableBalance, String soId) {
		// build FundingVo
		fundingVO.setAvailableBalance(availableBalance);
		fundingVO.setEnoughFunds(enoughFunds);
		fundingVO.setIncreaseAmt(increasedSpendLimit);
		fundingVO.setAmtToFund(increasedSpendLimit - Math.abs(availableBalance));
		fundingVO.setSoId(soId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #calcUpsellAmount(com.newco.marketplace.dto.vo.serviceorder.ServiceOrder)
	 */
	public Double calcUpsellAmount(ServiceOrder serviceOrder) {
		Double upsellAmt = 0.0;
		if (serviceOrder.getUpsellInfo() == null || serviceOrder.getUpsellInfo().isEmpty()) {
			// try to read from DB
			List<ServiceOrderAddonVO> addOns = serviceOrderUpsellBO.getAddonsbySoId(serviceOrder.getSoId());
			if (addOns != null) {
				upsellAmt = getUpsellTotals(addOns, serviceOrder);
			}
		} else {
			upsellAmt = getUpsellTotals(serviceOrder.getUpsellInfo(), serviceOrder);
		}
		serviceOrder.setUpsellAmt(upsellAmt);
		return upsellAmt;
	}

	/**
	 * Description: Loop through add-ons and get a total price for all
	 * 
	 * @param addOns
	 * @return <code>Double</code>
	 */
	private Double getUpsellTotals(List<ServiceOrderAddonVO> addOns, ServiceOrder so) {
		Double upsellTotal = 0.0;
		Integer buyerId = so.getBuyer()!= null ? so.getBuyer().getBuyerId() : -1;
		for (ServiceOrderAddonVO serviceOrderAddonVO : addOns) {
			boolean skipAddOn = false;
			if(buyerId.intValue() == BuyerConstants.HSR_BUYER_ID && 
					(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT.equals(serviceOrderAddonVO.getCoverage())) ){
				skipAddOn = true;
			}
			if(!skipAddOn){
			upsellTotal = upsellTotal + MoneyUtil.getRoundedMoney(serviceOrderAddonVO.getQuantity() * MoneyUtil.getRoundedMoney(serviceOrderAddonVO.getRetailPrice() * (1 - serviceOrderAddonVO.getMargin())));
		}
		}
		return MoneyUtil.getRoundedMoney(upsellTotal);
	}

	public ServiceOrderCustomRefVO getCustomRefValue(String customRefTypeId, String soId) {
		ServiceOrderCustomRefVO customRef = new ServiceOrderCustomRefVO();
		try {
			customRef = getServiceOrderDao().getCustomReferenceObject(customRefTypeId, soId);
		} catch (Exception e) {
			logger.error("error occurred while getting Customref Value for given Key & soId");
		}

		return customRef;
	}
	
	public ServiceOrderCustomRefVO getCustomReferenceValue(String customRefTypeId, String soId) {
		ServiceOrderCustomRefVO customRef = new ServiceOrderCustomRefVO();
		try {
			customRef = getServiceOrderDao().getCustomReferenceValue(customRefTypeId, soId);
		} catch (Exception e) {
			logger.error("error occurred while getting Customref Value for given Key & soId");
		}

		return customRef;
	}
	
	public ServiceOrder getServiceOrderStatusAndCompletdDate(String soId) throws BusinessServiceException {
		try {
			return getServiceOrderDao().getServiceOrderStatusAndCompletedDate(soId);
		} catch (DataServiceException e) {
			logger.error("error occurred while getting Status and Completed date for given soId");
		}
		return null;

	}

	/**
	 * Returns the associated incidents
	 * 
	 * @param soId
	 * @return List<AssociatedIncidentVO> associatedIncidents
	 * @throws BusinessServiceException
	 */
	public List<AssociatedIncidentVO> getAssociatedIncidents(String soId) throws BusinessServiceException {
		List<AssociatedIncidentVO> associatedIncidents = null;
		try {
			associatedIncidents = getServiceOrderDao().getAssociatedIncidents(soId);
		} catch (Exception e) {
			logger.error("error occurred while getting associated incidents for the given service order");
			throw new BusinessServiceException("General Exception @ServiceOrderBO.getAssociatedIncidents() due to " + e.getMessage());
		}
		return associatedIncidents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #getCustomReferenceValueByType
	 * (com.newco.marketplace.dto.vo.serviceorder.ServiceOrder,
	 * java.lang.String)
	 */
	public String getCustomReferenceValueByType(ServiceOrder so, String customRefType) {
		String customRefValue = null;
		List<ServiceOrderCustomRefVO> customRefs = so.getCustomRefs();
		if (customRefs != null && !customRefs.isEmpty()) {
			for (ServiceOrderCustomRefVO customRef : customRefs) {
				if (customRefType.equals(customRef.getRefType())) {
					customRefValue = customRef.getRefValue();
					break;
				}
			}
		}
		return customRefValue;
	}

	/*
	 * Method associates the buyer documents(with Document Category Id as 3 and
	 * deleted indicator as 0) to a particular SO
	 * 
	 * @param soId
	 * 
	 * @param buyerId
	 * 
	 * @param roleId
	 * 
	 * @param userId
	 * 
	 * @throws BusinessServiceException
	 */
	public void associateBuyerDocumentsToSO(String soId, int buyerId, int roleId, int userId) throws BusinessServiceException {
		List<DocumentVO> buyerDocuments = new ArrayList<DocumentVO>();
		try {
			// get the buyer reference documents
			buyerDocuments = documentBO.retrieveBuyerDocumentsByBuyerIdAndCategory(buyerId, Constants.BuyerAdmin.DOC_CATEGORY_ID, roleId, userId);
			if (null != buyerDocuments && !buyerDocuments.isEmpty()) {
				for (DocumentVO docVo : buyerDocuments) {
					docVo.setSoId(soId);
					docVo.setCompanyId(buyerId);
					documentBO.insertServiceOrderDocument(docVo);
				}
			}
		} catch (BusinessServiceException bse) {
			logger.error("Error retrieving buyer documents");
			throw new BusinessServiceException("Exception @ServiceOrderBO.associateBuyerDocumentsToSO() due to " + bse.getMessage());
		} catch (DataServiceException dse) {
			// TODO Need to change.Used this,since
			// documentBO.insertServiceOrderDocument() is throwing
			// DataServiceException
			logger.error("Error inserting buyer documents to SO");
			throw new BusinessServiceException("Exception @ServiceOrderBO.associateBuyerDocumentsToSO() due to " + dse.getMessage());
		}
	}

	/*
	 * This method retrieves one logo document id for this buyer and associates
	 * it with the newly created SO.
	 * 
	 * @param soId
	 * 
	 * @param buyerId
	 * 
	 * @throws BusinessServiceException
	 */
	public void retrieveLogoForBuyer(int buyerId, String soId) throws BusinessServiceException {
		try {
			Integer logoDocumentId = documentBO.retrieveLogoForBuyer(buyerId, Constants.DocumentTypes.CATEGORY.LOGO);
			// associate the logo document id with the newly created SO.
			if (null != logoDocumentId) {
				updateSoHdrLogoDocument(soId, logoDocumentId);
			}
		} catch (BusinessServiceException ex) {
			logger.error("Error retrieving buyer Logo documents");
			throw new BusinessServiceException("Exception in ServiceOrderBO.retrieveBuyerLogoDocumentByBuyerIdAndCategory() due to " + ex.getMessage());
		}
	}

	/*
	 * This method retrieves all the service providers name under a firm.
	 * 
	 * @param resourceId
	 * 
	 * @return ArrayList
	 * 
	 * @throws BusinessServiceException
	 */
	public ArrayList<ProviderDetail> queryServiceProviders(Integer resourceId) throws BusinessServiceException {
		ArrayList<ProviderDetail> arrServiceProvidersList = null;
		try {
			arrServiceProvidersList = getServiceOrderDao().queryServiceProviders(resourceId);
		} catch (BusinessServiceException dse) {
			logger.error("[BusinessServiceException] " + dse);
			final String error = "Error in retrieving Service Provider Names";
			throw new BusinessServiceException(error, dse);
		}
		return arrServiceProvidersList;
	}

	/*
	 * This method retrieves all the service providers name under a firm.
	 * 
	 * @param resourceId
	 * 
	 * @return ArrayList
	 * 
	 * @throws BusinessServiceException
	 */
	public ArrayList<ProviderDetail> queryMarketNames(Integer resourceId) throws BusinessServiceException {
		ArrayList<ProviderDetail> arrServiceProvidersList = null;
		try {
			arrServiceProvidersList = getServiceOrderDao().queryMarketNames(resourceId);
		} catch (BusinessServiceException dse) {
			logger.error("[BusinessServiceException] " + dse);
			final String error = "Error in retrieving Service Provider Market Names";
			throw new BusinessServiceException(error, dse);
		}
		return arrServiceProvidersList;
	}
	
	// SLT-2138: Get vendor id for push notification
	public ServiceOrder getServiceOrderForPushNotfcn(String soId) throws BusinessServiceException {
		ServiceOrder order = null;
		try {
			order = getServiceOrderDao().getServiceOrder(soId);
			if (null != order)
				logger.info("ServiceOrderBO.getServiceOrderForPushNotfcn()-->" + order.getSoId());
		} catch (Exception bse) {
			logger.error("[BusinessServiceException] " + bse.getMessage());
			final String error = "Error in retrieving Service Order for the Id : " + soId
					+ "in ServiceOrderBO.getServiceOrderForPushNotfcn";
			throw new BusinessServiceException(error, bse);
		}
		return order;
	}

	// SLT-2138: Get primary resource id for push notification
	public Integer getPrimaryResourceIdForPushNotfcn(Integer acceptedVendorId) throws BusinessServiceException {
		Integer primaryResourceId = null;
		try {
			primaryResourceId = getServiceOrderDao().getPrimaryResourceId(acceptedVendorId);
			if (null != primaryResourceId)
				logger.info("ServiceOrderBO.getPrimaryResourceIdForPushNotfcn()-->" + primaryResourceId);
		} catch (BusinessServiceException bse) {
			logger.error("[BusinessServiceException] " + bse.getMessage());
			final String error = "Error in retrieving primaryResourceId in ServiceOrderBO.getPrimaryResourceIdForPushNotfcn";
			throw new BusinessServiceException(error, bse);
		}
		return primaryResourceId;
	}

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}	

	

	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public ILedgerFacilityBO getTransBo() {
		return transBo;
	}

	public void setTransBo(ILedgerFacilityBO transBo) {
		this.transBo = transBo;
	}

	public IServiceOrderUpdateBO getServiceOrderUpdateBO() {
		return serviceOrderUpdateBO;
	}

	public void setServiceOrderUpdateBO(IServiceOrderUpdateBO serviceOrderUpdateBO) {
		this.serviceOrderUpdateBO = serviceOrderUpdateBO;
	}		

	public SurveyDAO getSurveyDao() {
		return surveyDao;
	}

	public void setSurveyDao(SurveyDAO surveyDao) {
		this.surveyDao = surveyDao;
	}	

	public ExtendedSurveyDAO getExtendedSurveyDAO() {
		return extendedSurveyDAO;
	}

	public void setExtendedSurveyDAO(ExtendedSurveyDAO extendedSurveyDAO) {
		this.extendedSurveyDAO = extendedSurveyDAO;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	/** @return the promoBO */
	public PromoBO getPromoBO() {
		return promoBO;
	}

	/**
	 * @param promoBO
	 *            the promoBO to set
	 */
	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}

	public IOrderGroupDao getOrderGroupDao() {
		return orderGroupDao;
	}

	public void setOrderGroupDao(IOrderGroupDao orderGroupDao) {
		this.orderGroupDao = orderGroupDao;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public void assurantAlertForNeedsAttentionSubStatus(String serviceOrderId, Integer subStatusId, SecurityContext securityContext) {
		// This is only for AOP call , sends an Email Alert for Sub Status
		// changed to NEEDS ATTENTION
	}

	public ProBuyerBOFactory getProBuyerBOFactory() {
		return proBuyerBOFactory;
	}

	public void setProBuyerBOFactory(ProBuyerBOFactory proBuyerBOFactory) {
		this.proBuyerBOFactory = proBuyerBOFactory;
	}

	public IServiceOrderCloseBO getSoCloseBO() {
		return soCloseBO;
	}

	public void setSoCloseBO(IServiceOrderCloseBO soCloseBO) {
		this.soCloseBO = soCloseBO;
	}

	public IServiceOrderUpsellBO getServiceOrderUpsellBO() {
		return serviceOrderUpsellBO;
	}

	public void setServiceOrderUpsellBO(IServiceOrderUpsellBO serviceOrderUpsellBO) {
		this.serviceOrderUpsellBO = serviceOrderUpsellBO;
	}

	/**
	 * Returns the list of service orders which satisfies the Search criteria
	 * specified in SearchRequestVO searchRequest
	 * 
	 * @param searchRequest
	 *            SearchRequestVO
	 * @return serviceOrderSearchResults List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSet(SearchRequestVO searchRequest) {
		logger.info("INSIDE ServiceOrderBO ----> getSearchResultSet()");
		List<ServiceOrder> serviceOrderSearchResults = new ArrayList<ServiceOrder>();
		try {

			serviceOrderSearchResults = this.getServiceOrderDao().getSearchResultSet(searchRequest);
		} catch (Exception e) {
			logger.error("error occurred while searching service orders.");

		}
		logger.info("INSIDE ServiceOrderBO ----> getSearchResultSet() Ends");
		return serviceOrderSearchResults;
	}
	
	/**
	 * Returns the list of service orders which satisfies the Search criteria
	 * specified in SearchRequestVO searchRequest
	 * 
	 * @param searchRequest
	 *            SearchRequestVO
	 * @return serviceOrderSearchResults List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSetPaged(SearchRequestVO searchRequest) {
		logger.info("INSIDE ServiceOrderBO ----> getSearchResultSet()");
		List<ServiceOrder> serviceOrderSearchResults = new ArrayList<ServiceOrder>();
		try {

			serviceOrderSearchResults = this.getServiceOrderDao().getSearchResultSetPaged(searchRequest);
		} catch (Exception e) {
			logger.error("error occurred while searching service orders.");

		}
		logger.info("INSIDE ServiceOrderBO ----> getSearchResultSet() Ends");
		return serviceOrderSearchResults;
	}

	/**
	 * Returns the list of language Ids corresponding to language names
	 * 
	 * @param languageNames
	 *            List<String>
	 * @return languageIds List<Integer>
	 * 
	 */
	public List<Integer> getLanguageIds(List<String> languageNames) {
		List<Integer> languageIds = new ArrayList<Integer>();
		try {

			languageIds = this.getServiceOrderDao().getLanguageIds(languageNames);
		} catch (Exception e) {
			logger.error("error occurred while getting language Ids.");

		}
		logger.info("INSIDE ServiceOrderBO ----> getLanguageIds() Ends");
		return languageIds;

	}

	/**
	 * Returns the list of logged objects for a serviceOrder based on soId
	 * 
	 * @param soId
	 *            String
	 * @return soLogList List<SoLoggingVo>
	 * 
	 */

	public List<SoLoggingVo> getSoLogDetails(String soId) throws DataServiceException {
		List<SoLoggingVo> soLogList = getLoggingDao().getSoLogDetails(soId);
		return soLogList;
	}
	
	
	public List<SoLoggingVo> getSoRescheduleLogDetails(String soId) throws DataServiceException {
		List<SoLoggingVo> soLogList = getLoggingDao().getSoRescheduleLogDetails(soId);
		return soLogList;
	}

	/**
	 * This method rescheules the service dates of an SO in Posted status.
	 * 
	 * @param serviceOrderId
	 * @param newStartDate
	 * @param newEndDate
	 * @param newStartTime
	 * @param newEndTime
	 * @param companyId
	 * @param convertGMT
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse processRescheduleForRoutedStatus(String serviceOrderId, Timestamp newStartDate, Timestamp newEndDate, String newStartTime, String newEndTime, Integer companyId, boolean convertGMT, SecurityContext securityContext) throws BusinessServiceException {

		ProcessResponse processResponseObj = new ProcessResponse();
		try {
			processResponseObj = rescheduleSO(serviceOrderId, newStartDate, newEndDate, newStartTime, newEndTime, null, companyId, true, securityContext);
		} catch (Throwable t) {
			logger.error("Reschedule Service Order:: error:  " + t.getMessage(), t);
			processResponseObj.setCode(SYSTEM_ERROR_RC);
			processResponseObj.setMessage(t.getMessage());
		}
		return processResponseObj;

	}

	/**
	 * This method inserts the initial_posted_labor_spend_limit and
	 * initial_posted_parts_spend_limit of service order while its Posted
	 * 
	 * @param serviceOrderId
	 * @return
	 */
	private void updateInitialPostedSOPrice(ServiceOrder serviceOrder) {
		try {
			serviceOrder.setInitialPostedLaborSpendLimit(serviceOrder.getSpendLimitLabor());
			serviceOrder.setInitialPostedPartsSpendLimit(serviceOrder.getSpendLimitParts());
			getServiceOrderDao().updateInitialPostedSOPrice(serviceOrder);
		} catch (DataServiceException e) {
			logger.error("Error occured while updating SO Initial Posted Price:  ", e);
		}
	}

	/**
	 * Method to update spend limit for WS
	 * 
	 * @param updatedSO
	 * @param changed
	 * @param securityContext
	 * @return ProcessResponse
	 * @throws BusinessServiceException
	 */
	public ProcessResponse processUpdateSpendLimitforWS(ServiceOrder updatedSO, Map<String, Object> changed, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		if (changed.containsKey(OrderConstants.PRICE_DECREASE)) {
			Double decreasedPrice = (Double) changed.get(OrderConstants.PRICE_DECREASE);
			processResp = decreaseSOSpendLimit(updatedSO, decreasedPrice, updatedSO.getBuyer().getBuyerId());
		} else if (changed.containsKey(OrderConstants.PRICE_INCREASE)) {
			processResp = updateSOSpendLimit(updatedSO.getSoId(), updatedSO.getSpendLimitLabor(), updatedSO.getSpendLimitParts(), "", updatedSO.getBuyer().getBuyerId(), true, true, securityContext);
		}
		if (ServiceConstants.VALID_RC.equals(((ProcessResponse) processResp).getCode())) {			
			//Update the so_group_price               
            if(StringUtils.isNotBlank(updatedSO.getGroupId())){         
                  boolean applyTripCharge = false;
                  boolean updateOriginalGroupPrice =false;
                  IOrderGroupBO orderGroupBO = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean(ABaseRequestDispatcher.ORDER_GROUP_BUSINESS_OBJECT_REFERENCE);
                  orderGroupBO.rePriceOrderGroup(updatedSO.getGroupId(), applyTripCharge, updateOriginalGroupPrice);
            }                 

			List<String> arr = new ArrayList<String>();
			arr.add(OrderConstants.SUCCESSFUL_SPEND_LIMIT_UPDATE);
			processResp.setMessages(arr);
		}
		return processResp;
	}

	/**
	 * This method decreases the SO spend limit
	 * 
	 * @param updatedSO
	 * @param decreasedAmount
	 * @param buyerId
	 * @throws DataServiceException
	 * @throws BusinessServiceException
	 */
	private ProcessResponse decreaseSOSpendLimit(ServiceOrder updatedSO, Double decreasedAmount, int buyerId) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		try {
			getServiceOrderDao().updateLimit(updatedSO);
			// remove just the PROVIDERS that conditionally accepted
			if (updatedSO.getWfStateId() == ROUTED_STATUS) {
				getServiceOrderDao().removeConditionalFromRoutedProviders(updatedSO.getSoId());
			}
			Buyer buyer = getServiceOrderDao().getBuyerAttributes(buyerId);
			// Now get their account details
			Account acct = financeManagerBO.getAccountDetails(buyerId);
			if (acct != null && acct.isActive_ind()) { // They are Auto_ach
				// enabled
				MarketPlaceTransactionVO marketVO = getMarketPlaceTransactionVO(updatedSO, buyerId);
				marketVO.setUserName(buyer.getUserName());
				marketVO.setBuyerID(buyerId);
				marketVO.setAutoACHInd(OrderConstants.TRUE);
				marketVO.setAccountId(acct.getAccount_id());
				if(!(updatedSO.getWfStateId().equals(OrderConstants.DRAFT_STATUS))){
					transBo.decreaseSpendLimit(marketVO, Math.abs(decreasedAmount), Math.abs(decreasedAmount));
				}				
			}
		} catch (DataServiceException e) {
			throw new BusinessServiceException("General Exception @ServiceOrderBO.decreaseSOSpendLimit() due to " + e.getMessage(), e);
		}
		processResp.setCode(VALID_RC);
		processResp.setSubCode(VALID_RC);
		return processResp;
	}

	/**
	 * This method reroutes the So for WS
	 * 
	 * @param so
	 * @param securityContext
	 * @return
	 */
	public ProcessResponse processReRouteSOForWS(ServiceOrder so, SecurityContext securityContext) {
		// AOP logging is associated with the processReRouteSOForWS method
		// get the bo bean again for AOP to fire
		IServiceOrderBO bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
		ProcessResponse processResp = bo.processReRouteSO(so.getBuyer().getBuyerId(), so.getSoId(), true, securityContext);
		return processResp;
	}

	/**
	 * This method updates the tasks for OMS orders. Now its used only for
	 * logging purpose
	 * 
	 * @param ServiceOrder
	 * @param SecurityContext
	 * @return void
	 */
	public void processUpdateTask(ServiceOrder so, SecurityContext securityContext) {
		ProcessResponse response = new ProcessResponse();
		logger.debug("processUpdateTask method in ServiceORderBO method fired - AOP should insert a log in so_logging table");
		response.setCode(ServiceConstants.VALID_RC);
	}

	/**
	 * Sets the wallet client bo.
	 * 
	 * @param walletClientBO
	 *            the new wallet client bo
	 */
	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public Date getRoutedDateForResourceId(String soId, Integer resourceId) throws BusinessServiceException {
		Date routedDt = null;
		try {
			routedDt = getServiceOrderDao().getRoutedDateForResourceId(soId, resourceId);
		} catch (DataServiceException e) {
			logger.error("Error occurred while in getRoutedDateForResourceId due to " + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return routedDt;
	}
	public Date getRoutedDateForFirm(String soId, Integer vendorId) throws BusinessServiceException {
		Date routedDt = null;
		try {
			routedDt = getServiceOrderDao().getRoutedDateForFirm(soId, vendorId);
		} catch (DataServiceException e) {
			logger.error("Error occurred while in getRoutedDateForFirm due to " + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return routedDt;
	}
	
	
	public int getTheRemainingTimeToAcceptSOForFirm(String soId, Integer vendorId) throws BusinessServiceException {
		Date routedDate = getRoutedDateForFirm(soId, vendorId);
		return getTheRemainingTimeToAcceptSO(routedDate);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #getTheRemainingTimeToAcceptSO(java.lang.String, java.lang.Integer)
	 */
	public int getTheRemainingTimeToAcceptSO(String soId, Integer resourceId) throws BusinessServiceException {
		Date routedDate = getRoutedDateForResourceId(soId, resourceId);
		return getTheRemainingTimeToAcceptSO(routedDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO
	 * #getTheRemainingTimeToAcceptSO(java.util.Date)
	 */
	public int getTheRemainingTimeToAcceptSO(Date routedDate) throws BusinessServiceException {
		int remainingSecondsToAcceptSO = 0;
		try {
			if (routedDate != null) {
				String timeToWaitSec = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.WAIT_TIME_TO_ACCEPT_SERVICE_ORDER);
				int waitSeconds = Integer.parseInt(timeToWaitSec);
				Calendar routedCalWithWaitTime = Calendar.getInstance();
				routedCalWithWaitTime.setTime(routedDate);
				routedCalWithWaitTime.add(Calendar.SECOND, waitSeconds);

				Calendar currentCal = Calendar.getInstance();

				long remainingTimeinMS = ((routedCalWithWaitTime.getTimeInMillis()) - (currentCal.getTimeInMillis()));
				if (remainingTimeinMS > 0) {
					remainingSecondsToAcceptSO = new Long(remainingTimeinMS / 1000).intValue();
				} else { // this means wait time is done
					remainingSecondsToAcceptSO = 0;
				}
			}

		} catch (Exception e) {
			logger.error("Error occurred while in getTheRemainingTimeToAcceptSO due to " + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return remainingSecondsToAcceptSO;
	}

	/**
	 * getProviderDistanceFromServiceLocation is used to fetch the distance of
	 * each provider from service location
	 * 
	 * @param soId
	 * @param List
	 *            <RoutedProvider>
	 * @return List<RoutedProvider>
	 */
	public List<RoutedProvider> getProviderDistanceFromServiceLocation(String soId, List<RoutedProvider> routedProviderList) {

		ServiceOrder so = null;
		try {
			so = getServiceOrderDao().getServiceOrder(soId);
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		String zip = so.getServiceLocation().getZip();
		LocationVO locationVO = providerSearchDao.getZipLatAndLong(zip);
		HashMap<String, String> serviceLocationCoordinates = new HashMap<String, String>();
		serviceLocationCoordinates.put("long", String.valueOf(locationVO.getLongitude()));
		serviceLocationCoordinates.put("lat", String.valueOf(locationVO.getLatitude()));
		for (RoutedProvider routedProvider : routedProviderList) {
			if (null != routedProvider.getProviderLocation() && null != routedProvider.getProviderLocation().getLongitude() && null != routedProvider.getProviderLocation().getLatitude()) {
				HashMap<String, String> providerCoordinates = new HashMap<String, String>();
				providerCoordinates.put("long", String.valueOf(routedProvider.getProviderLocation().getLongitude()));
				providerCoordinates.put("lat", String.valueOf(routedProvider.getProviderLocation().getLatitude()));

				try {
					double distanceInMiles = GISUtil.getDistanceInMiles(serviceLocationCoordinates, providerCoordinates);
					routedProvider.setDistanceFromBuyer(Math.round(distanceInMiles));
				} catch (InsuffcientLocationException e) {
					logger.error("Error occurred calculating distanceInMiles in ServiceOrderBO --> getAllProviders() ", e);
				}

			} else {
				routedProvider.setDistanceFromBuyer(null);
			}
		}

		return routedProviderList;
	}

	/**
	 * This method gets the Substatus id for the given substatus description
	 * 
	 * @param subStatus
	 *            ,status
	 * @return Integer
	 */
	public Integer getSubStatusId(String subStatus, int status) {
		Integer id = null;
		try {
			id = this.getServiceOrderDao().getSubStatusId(subStatus, status);
		} catch (Exception ex) {
			logger.error("Error occurred while getting substatus id.");

		}
		return id; 
	}

	/**
	 * Method gets the reasons list for the selected counter offer condition
	 * @param providerRespId
	 * @return List<CounterOfferReasonsVO>
	 * @throws BusinessServiceException
	 */
	public List<CounterOfferReasonsVO> getReasonsForSelectedCounterOffer(int providerRespId) throws BusinessServiceException{
		List<CounterOfferReasonsVO> counterOfferReasonsList = null;
		try{
			counterOfferReasonsList = this.getServiceOrderDao().getReasonsForSelectedCounterOffer(providerRespId);
		}catch(DataServiceException dse){
			throw new BusinessServiceException("Error occured while getting selected counter offer reasons in ServiceOrderBO.getReasonsForSelectedCounterOffer()",dse);
		}
		return counterOfferReasonsList;
	}
	
	private void lockBuyer(Integer buyerId) {
		getBuyerDao().lockBuyer(buyerId);
	}

	public ProviderSearchDao getProviderSearchDao() {
		return providerSearchDao;
	}

	public void setProviderSearchDao(ProviderSearchDao providerSearchDao) {
		this.providerSearchDao = providerSearchDao;
	}


	
	
	private RoutedProvider findRoutedProvider(ServiceOrder serviceOrderVO, Integer resourceId) {
		for(RoutedProvider provider: serviceOrderVO.getRoutedResources()) {
			if(provider.getResourceId().equals(resourceId)) {
				return provider;
			}
		}
		return null;
	}

	public List<ServiceOrderCustomRefVO> getServiceOrdersByCustomRefValue(String customRefType, String customRefValue, String soId, List statusIds) {

		try {
			return getServiceOrderDao().getCustomReferenceList(customRefType, customRefValue, soId, statusIds);
		} catch (Exception e) {
			logger.error("error occurred while getting Customref Value for given Key & soId");
		}
		return null;
	}




	public ProcessResponse processCreateConditionalOffer(String serviceOrderID,
			Integer resourceID, Integer vendorOrBuyerID,
			Timestamp conditionalDate1, Timestamp conditionalDate2,
			String conditionalStartTime, String conditionalEndTime,
			Timestamp conditionalExpirationDate, Double incrSpendLimit,
			List<Integer> selectedCounterOfferReasonsList,
			SecurityContext securityContext,List<Integer> resourceIds) {
		logger.info("processCreateConditionalOffer() SO_ID: " + serviceOrderID + " | incrSpendLimit: " + incrSpendLimit);

		final ServiceOrder so;
		try {
			so = getServiceOrderDao().getServiceOrder(serviceOrderID);
		} catch (DataServiceException dse) {
			ProcessResponse processResp = new ProcessResponse();
			logger.debug("Exception thrown querying SO", dse);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("Datastore error");
			return processResp;
		} 

		try {
			Double totalSpendLimit = so.getSpendLimitLabor() + so.getSpendLimitParts();
			if (this.isSOInEditMode(serviceOrderID)) {
				ProcessResponse processResp = new ProcessResponse();
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessage("Unable to complete action.  Service Order is currently being edited.");
				return processResp;
			} else if (incrSpendLimit != null && incrSpendLimit <= totalSpendLimit) {
				ProcessResponse processResp = new ProcessResponse();
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessage("Counter offer should be greater than existing price.");
				return processResp;
			}
		} catch (BusinessServiceException bse) {
			ProcessResponse processResp = new ProcessResponse();
			logger.debug("Exception thrown querying SO", bse);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("Business Service error");
			return processResp;
		}
		

		try {
			return validateThenCreate(serviceOrderID, so.getPriceModel(), resourceID, conditionalExpirationDate, so.getServiceLocationTimeZone(), conditionalDate1, conditionalDate2, conditionalStartTime, conditionalEndTime, incrSpendLimit, vendorOrBuyerID,selectedCounterOfferReasonsList,resourceIds);
		} catch (DataServiceException dse) {
			ProcessResponse processResp = new ProcessResponse();
			logger.debug("Exception thrown querying SO", dse);
			processResp.setCode(SYSTEM_ERROR_RC);
			processResp.setMessage("Datastore error");
			return processResp;

		} 
	}
	
	public Double getSOProjectBalance(String soId) throws BusinessServiceException
	{
		Double soProjectBalance = walletBO.getCurrentSpendingLimit(soId);
		return soProjectBalance;
	}
	/**
	 * Method fetches unit no, order no, buyer id and so contact phone no for a given SO ID
	 * @param String soId
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String getSoCustomReferencesWS(String soId){
		ServiceOrderIvrDetailsVO ivrDetails = new ServiceOrderIvrDetailsVO();
		List<PhoneVO> phoneDetails = null;
		String unitNo = null;
		String orderNo = null;
		String phoneNo = "0";
		StringBuilder sb = new StringBuilder();
		String response = FAILURE;
	
		try{
			ivrDetails = getServiceOrderDao().getSoCustomReferencesWS(soId);			
			
			sb.append(VALID).append(PIPE_DELIMITER).append(soId).append(PIPE_DELIMITER);
			
			if(null != ivrDetails){
				List<ServiceOrderCustomRefVO> soCustRefs = ivrDetails.getSoCustRefs();
				for(ServiceOrderCustomRefVO custRef : soCustRefs){
					if(custRef.getRefType().equals(CUSTOM_REF_UNIT_NUM)){
						unitNo = custRef.getRefValue();
					}else if(custRef.getRefType().equals(CUSTOM_REF_ORDER_NUM)){
						orderNo = custRef.getRefValue();
					}				
				}
				phoneDetails = ivrDetails.getPhoneDetails();
			}		
			
			if(null!=unitNo){
				if(unitNo.length() < UNIT_NUMBER_LENGTH){
					unitNo = formatCustRefValue(UNIT_NUMBER_LENGTH,unitNo);
				}
				sb.append(unitNo).append(PIPE_DELIMITER);
			}else{
				sb.append("0").append(PIPE_DELIMITER);
			}
			
			if(null!=orderNo){
				if(orderNo.length()< ORDER_NUMBER_LENGTH){
					orderNo = formatCustRefValue(ORDER_NUMBER_LENGTH,orderNo);
				}
				sb.append(orderNo).append(PIPE_DELIMITER);
			}else{
				sb.append("0").append(PIPE_DELIMITER);
			}
			sb.append(ivrDetails.getBuyerId()).append(PIPE_DELIMITER);
			
			if(null!=phoneDetails || phoneDetails.size()!=0){
				for(PhoneVO phoneVO : phoneDetails){
					if(null == phoneVO.getEntityTypeId()){
						phoneNo = phoneVO.getPhoneNo();
						break;
					}else if(phoneVO.getEntityTypeId() == BUYER_ENTITY_ID){
						phoneNo = phoneVO.getPhoneNo();
					}
				}
			}
			sb.append(phoneNo);
			response = sb.toString();
		}catch(DataServiceException dse){
			logger.error("Error Occurred while retrieving IVR details: " +dse);
		}catch(Exception ex){
			logger.error("Error Occurred while retrieving IVR details: " +ex);
		}
		
		return response;
	}
	
	private String formatCustRefValue(int length, String custRefValue){
		int custRefValueLen=0;
		custRefValueLen = custRefValue.length();		
		int noOfZeros = length - custRefValueLen;
		
		for(int k=0; k<noOfZeros; k++){
			custRefValue="0"+custRefValue;
		}
		return custRefValue;
	}
	
	public ArrayList<LookupVO> getSubstatusDesc(List<Integer> id) throws BusinessServiceException {
		ArrayList<LookupVO> substatusList= null;
		try {
			substatusList = getServiceOrderDao().getSubstatusDesc(
					id);
		} catch (DataServiceException e) {
			logger.error("ServiceOrderBO :Unable to fetch the substatus list.");
			throw new BusinessServiceException(e.getMessage(), e);
		}
		return substatusList;
	}
	
	public ArrayList<LookupVO> getRescheduleReasonCodes(Integer roleId) throws BusinessServiceException {
		ArrayList<LookupVO> reasonCodeList= null;
		try {
			reasonCodeList = getServiceOrderDao().getRescheduleReasonCodes(roleId);
		} catch (DataServiceException e) {
			logger.error("ServiceOrderBO :Unable to fetch the reschedule reason list.");
			throw new BusinessServiceException(e.getMessage(), e);
		}
		return reasonCodeList;
	}
	
	public ArrayList<LookupVO> getPermitTypes() throws BusinessServiceException {
		ArrayList<LookupVO> permitTypesList= null;
		try {
			permitTypesList = getServiceOrderDao().getPermitTypes();
		} catch (DataServiceException e) {
			logger.error("ServiceOrderBO :Unable to fetch the permit type list.");
			throw new BusinessServiceException(e.getMessage(), e);
		}
		return permitTypesList;
	}
	public IProviderInfoPagesBO getProviderInfoPagesBO() {
		return providerInfoPagesBO;
	}

	public void setProviderInfoPagesBO(IProviderInfoPagesBO providerInfoPagesBO) {
		this.providerInfoPagesBO = providerInfoPagesBO;
	}
	
	public ICreditCardDao getCreditCardDao() {
		return creditCardDao;
	}

	public void setCreditCardDao(ICreditCardDao creditCardDao) {
		this.creditCardDao = creditCardDao;
	}
	
	public Map getTaskPrice(Integer taskId) throws BusinessServiceException, DataServiceException {
		return getServiceOrderDao().getTaskPrice(taskId);
	}
	
	public List <ProviderResultVO> getRoutedResourcesForFirm(String soId, String vendorId, String resourceId, Boolean manageSOFlag, ServiceOrder serviceOrder) 
		throws BusinessServiceException {
		List <ProviderResultVO> providerList = null;
		List <ProviderResultVO> providerListForGroup = null;
		try {
			providerList = getServiceOrderDao().getRoutedResourcesForFirm(soId, vendorId, resourceId, manageSOFlag);

			if (providerList == null) {
				logger.debug("getRoutedResourcesForFirm() - No routed resources found for SO:" + soId + " Provider Firm:" + vendorId+ " Resource:" + resourceId);
			}else{
				if(null!=serviceOrder.getGroupId()){
					providerListForGroup = getServiceOrderDao().getRoutedResourcesListForFirmForGroup(soId, vendorId, resourceId, manageSOFlag, serviceOrder);
					if(null!=providerListForGroup){
						for(ProviderResultVO provider : providerList){
							if(provider.getProviderRespid() == 2){
								int resId = provider.getResourceId();
								for(ProviderResultVO groupResult : providerListForGroup){
									if(groupResult.getResourceId() == resId){
										provider.setGroupCondIncrSpendLimit(groupResult.getGroupCondIncrSpendLimit());
									}
								}
							}
						}						
					}					
				}				
				GMTToGivenTimeZoneForProviderList(providerList,serviceOrder);
			}

		} catch (DataServiceException e) {
			logger.error("Exception in getRoutedResourcesForFirm:" + e);
		}
		return providerList;
	}
	public List <ProviderResultVO> getRoutedProviderListForFirm (String soId, String vendorId)throws BusinessServiceException{
		List <ProviderResultVO> providerList = null;
		try{
		providerList = getServiceOrderDao().getRoutedResourcesForFirm(soId, vendorId, null, false);
		}catch (DataServiceException e) {
			logger.error("Exception in getRoutedResourcesForFirm:" + e);
		}
		return providerList;
	}
	public List<PendingCancelHistoryVO> getPendingCancelHistory(String soId) throws DataServiceException

	{
	List<PendingCancelHistoryVO> pendingCancelHistoryVO=getServiceOrderDao().getPendingCancelHistory(soId);
		return pendingCancelHistoryVO;
		
			
	}
	
	public PendingCancelPriceVO getPendingCancelBuyerDetails(String soId) throws DataServiceException
	{
		PendingCancelPriceVO pendingCancelPriceVO=getServiceOrderDao().getPendingCancelBuyerDetails(soId);
		return pendingCancelPriceVO;
		
	}
		
	public PendingCancelPriceVO getPendingCancelBuyerPriceDetails(String soId) throws DataServiceException
	{
		PendingCancelPriceVO pendingCancelPriceVO=getServiceOrderDao().getPendingCancelBuyerPriceDetails(soId);
		return pendingCancelPriceVO;
	}
	
	public PendingCancelPriceVO getPendingCancelProviderDetails(String soId) throws DataServiceException
	{
		PendingCancelPriceVO pendingCancelPriceVO=getServiceOrderDao().getPendingCancelProviderDetails(soId);
		return pendingCancelPriceVO;
		
	}

	
	
	private static void GMTToGivenTimeZoneForProviderList(List <ProviderResultVO> providerList, ServiceOrder serviceOrder){
		for(ProviderResultVO provider : providerList){
			HashMap<String, Object> serviceDate1 = null;
			HashMap<String, Object> serviceDate2 = null;
			String startTime = null;
			String endTime = null;
			startTime = provider.getConditionalStartTime();
			endTime = provider.getConditionalEndTime();
			
			if (provider.getConditionalChangeDate1() != null && startTime != null) {

				serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(provider.getConditionalChangeDate1(), startTime, serviceOrder.getActualServiceLocationTimeZone());
				if (serviceDate1 != null && !serviceDate1.isEmpty()) {
					provider.setConditionalChangeDate1((Timestamp) serviceDate1.get(OrderConstants.GMT_DATE));
					provider.setConditionalStartTime((String) serviceDate1.get(OrderConstants.GMT_TIME));
					provider.setServiceDate1((Date)provider.getConditionalChangeDate1());
				}
			}
			if (provider.getConditionalChangeDate2() != null && endTime != null) {

				serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(provider.getConditionalChangeDate2(), endTime, serviceOrder.getActualServiceLocationTimeZone());
				if (serviceDate2 != null && !serviceDate2.isEmpty()) {
					provider.setConditionalChangeDate2((Timestamp) serviceDate2.get(OrderConstants.GMT_DATE));
					provider.setConditionalEndTime((String) serviceDate2.get(OrderConstants.GMT_TIME));
					provider.setServiceDate2((Date)provider.getConditionalChangeDate2());
				}
			}
			if(provider.getOfferExpirationDate()!=null){
				provider.setOfferExpirationDateString(TimeUtils
						.convertTimeStampInGMTtoTimeZone(provider
								.getOfferExpirationDate(), serviceOrder
								.getActualServiceLocationTimeZone()));
			}
		}		
	}

	public List<SoAutoCloseDetailVo> getSoAutoCloseCompletionList(String soId) throws DataServiceException {
			List<SoAutoCloseDetailVo> list = getLoggingDao().getSoAutoCloseCompletionList(soId);
			return list;
		}
	
	
	public Double getBuyerMaxTransactionLimit(Integer resourceId,Integer buyerId)  throws BusinessServiceException {
			
			Double maxTransactionLimit  = 0.0;		
			try {
				maxTransactionLimit = (Double) getServiceOrderDao().getBuyerMaxTransactionLimit(resourceId,buyerId);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new BusinessServiceException(ex.getMessage(), ex);
			}
			return maxTransactionLimit;
		}
	
	
	public String  getUserName(Integer roleId,Integer resourceId) throws DataServiceException
	{
		String userName =null;
		
		if(resourceId!=null &&  roleId!=null && roleId.intValue()==3)
		{
			userName=getLoggingDao().getBuyerUserName(roleId,resourceId);
		}
		if(resourceId!=null && roleId!=null &&  roleId.intValue()==1)
		{
			userName=getLoggingDao().getProviderUserName(roleId,resourceId);
		}
   return userName;
	}
	
	/**
	 * Get the response based on the response filter
	 * @param soId
	 * @param responseFilters
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrder getServiceOrder(String soId,List<String> responseFilters) throws BusinessServiceException {
		ServiceOrder serviceOrder = null;

		try {
			serviceOrder = getServiceOrderDao().getServiceOrder(soId,responseFilters);

			if (serviceOrder != null) {
				GMTToGivenTimeZone(serviceOrder);
				if(null !=serviceOrder.getServiceDatetimeSlots() && serviceOrder.getServiceDatetimeSlots().size()>0 ){
				    GMTToGivenTimeZoneForSlots(serviceOrder);
				  }
			} else {
				if (logger.isDebugEnabled())
					logger.debug("getServiceOrder() - Error getting service order for " + soId);
			}

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return serviceOrder;
	}
	
	/**
	 * Get the service order with a reduced set of result map
	 * @param serviceOrderID
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrderForAPI(String soId) throws BusinessServiceException {
		ServiceOrder serviceOrder = null;

		try {
			serviceOrder = getServiceOrderDao().getServiceOrderForAPI(soId);

			if (serviceOrder != null) {
				GMTToGivenTimeZone(serviceOrder);
			} else {
				if (logger.isDebugEnabled())
					logger.debug("getServiceOrder() - Error getting service order for " + soId);
			}

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return serviceOrder;
	}
	/**
	 * Get the response based on the response 
	 * @param soId
	 * @param responseForSpendLimit
	 * @return
	 * @throws BusinessServiceException
	 */
	public HashMap<String,ServiceOrderSpendLimitHistoryVO>getSpendLimitForAPI(List<String>soIdList)throws BusinessServiceException{
		    
		HashMap<String,ServiceOrderSpendLimitHistoryVO>spendLimitIncrease=new HashMap<String,ServiceOrderSpendLimitHistoryVO>();
		   try{
			   spendLimitIncrease=getServiceOrderDao().getSpendLimitIncreaseForAPI(soIdList); 
		      }
		   catch (DataServiceException e) {
			   logger.error("Exception :" + e);
		}
		return spendLimitIncrease;
	}
	
//This method updates the so_workflow_controls table for service order created using sku
	
	public void  updateSkuIndicator(String soId)
	{
		try {
			serviceOrderUpdateBO.updateSkuIndicator(soId);
		} catch (Exception e) {
			logger.error("Exception :" + e);
			
		}
	}
	//This  method to fetch the sku indicator column of  so_workflow_controls table to identify the type of  serive order
	public Boolean fetchSkuIndicatorFromSoWorkFlowControl(String soID)
	{
		Boolean fetchSkuIndicatorFromWfmc=false;
		try {
			fetchSkuIndicatorFromWfmc=serviceOrderUpdateBO.fetchSkuIndicatorFromSoWorkFlowControl(soID);
		} catch (Exception e) {
			logger.error("Exception :" + e);
			
		}
		return fetchSkuIndicatorFromWfmc;
	}



	public List<Integer> getRoutedProvidersForFirm(String soId,Integer vendorId)
			throws BusinessServiceException{
		try{
		return getServiceOrderDao().getRoutedProvidersForFirm(soId, vendorId);
		}catch(DataServiceException e){
			throw new BusinessServiceException("exception in getRoutedProvidersForFirm() of ServiceOrderBO",e);
		}
	}
	
	//SL-15642: check whether so is car routed
	public boolean isCARroutedSO(String soId) throws BusinessServiceException{
		try{
			return getServiceOrderDao().isCARroutedSO(soId);
		}catch(DataServiceException e){
			throw new BusinessServiceException("exception in isCARroutedSO() of ServiceOrderBO",e);
		}
	}

	public boolean isAuthorizedToViewSODetls(String soId, String providerId) throws BusinessServiceException{
		// TODO Auto-generated method stub
		try{
			return getServiceOrderDao().isAuthorizedToViewSODetls(soId,providerId);
		}catch(DataServiceException e){
			throw new BusinessServiceException("exception in isAuthorizedToViewSODetls() of ServiceOrderBO",e); 
		}

	}
	
	public boolean isAuthorizedToViewGroupSODetls(String groupId, String providerId) throws BusinessServiceException{
		// TODO Auto-generated method stub
		try{
			return getServiceOrderDao().isAuthorizedToViewGroupSODetls(groupId,providerId);
		}catch(DataServiceException e){
			throw new BusinessServiceException("exception in isAuthorizedToViewSODetls() of ServiceOrderBO",e); 
		}

	}
	
	public ServiceOrder getServiceOrderGroup(String groupId,List<String> responseFilters) throws BusinessServiceException {
		ServiceOrder serviceOrder = null;

		try {
			serviceOrder = getServiceOrderDao().getServiceOrder(groupId,responseFilters);
			//TODO
			if (serviceOrder != null) {
				GMTToGivenTimeZone(serviceOrder);
			} else {
				if (logger.isDebugEnabled())
					logger.debug("getServiceOrder() - Error getting service order for " + groupId);
			}

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return serviceOrder;
	}
	
	public ServiceOrder getServiceOrderPriceDetails(ServiceOrder serviceOrder,Integer infolevel) throws BusinessServiceException{
		{	
			try {
				serviceOrder = getServiceOrderDao().getServiceOrderPriceDetails(serviceOrder,infolevel);
			} catch (DataServiceException e) {
				logger.error("Exception :" + e);
			}
			return serviceOrder;
		}
	}
	
	public String getVendorBusinessName(Integer vendorId)throws BusinessServiceException{
		String businessName = null;
		try {
			businessName = getServiceOrderDao().getVendorBusinessName(vendorId);
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return businessName;
	}
	
	public String getRescheduleReason(Integer reasonCode)throws BusinessServiceException{
		String reason = null;
		try {
			reason = getServiceOrderDao().getRescheduleReason(reasonCode);
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return reason;
	}
	
	// to fetch schedule history
	public List<PreCallHistory> getScheduleHistory(String soId,
			Integer acceptedVendorId)
	{
		List<PreCallHistory> callHistory =null;
		try {
			callHistory = getServiceOrderDao().getScheduleHistory(soId,acceptedVendorId);

			} catch (Exception e) {
			logger.error("Exception :" + e);
		}
		return callHistory;
		
	}
	
	/**
	 * getAcceptedFirmDetails
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProviderFirmVO getAcceptedFirmDetails(Integer vendorId)throws BusinessServiceException{
		ProviderFirmVO providerFirmVO = new ProviderFirmVO();
		try {
			providerFirmVO = getServiceOrderDao().getAcceptedFirmDetails(vendorId);
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return providerFirmVO;
	}
	public ProviderFirmVO getFirmLevelDetailsSoId(String soId)throws BusinessServiceException{
		ProviderFirmVO providerFirmVO = new ProviderFirmVO();
		try {
			providerFirmVO = getServiceOrderDao().getAcceptedFirmDetailsSoId(soId);
		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return providerFirmVO;
	}
	//to check whether SO is tier routed
	public boolean checkTierRoute(String soId){
		boolean tierRoute = false;
		try{
			tierRoute = getServiceOrderDao().checkTierRoute(soId);
			
		}catch (Exception e) {
			logger.error("Exception in ServiceOrderBO.checkTierRoute due to:" + e.getMessage());
		}
		return tierRoute;
	}

	public List<ServiceOrderTask> getActiveTasks(String soId) {
		List<ServiceOrderTask> activeTasks = new ArrayList<ServiceOrderTask>();
		try{
			activeTasks = getServiceOrderDao().getActiveTasks(soId);
			
		}catch (Exception e) {
			logger.error("Exception in ServiceOrderBO.activeTasks due to:" + e.getMessage());
		}
		return activeTasks;
	}
	
	//For checking Non Funded feature for an SO
	public boolean checkNonFunded(String soId) {
		boolean isNonFunded = false;
		
		try {
			isNonFunded = getServiceOrderDao().checkNonFunded(soId);
			
		} catch (Exception e) {
			logger.error("Exception in ServiceOrderBO.checkNonFunded due to:" + e.getMessage());
		}
		return isNonFunded;
	}
	public boolean isNonFundedBuyer(Integer buyerId){
		boolean isNonFunded = false;
		try {
			isNonFunded = getServiceOrderDao().isNonFundedBuyer(buyerId);
			
		} catch (Exception e) {
			logger.error("Exception in ServiceOrderBO.isNonFundedBuyer due to:" + e);
		}
		return isNonFunded;
	}
	
	//SL-19050
	//For marking note as Read
		public void markSOAsRead(String noteId) throws BusinessServiceException
		{
			try {
				getServiceOrderDao().markSOAsRead(noteId);
				
			} catch (Exception e) {
				logger.error("Exception in ServiceOrderBO.markSOAsRead due to:" + e);
			}
			
		}
		
		
		//For marking note as UnRead
				public void markSOAsUnRead(String noteId) throws BusinessServiceException
				{
					try {
						getServiceOrderDao().markSOAsUnRead(noteId);
						
					} catch (Exception e) {
						logger.error("Exception in ServiceOrderBO.markSOAsUnRead due to:" + e);
					}
					
				}
/**
 * @param invoiceIds
 * @return
 * @throws BusinessServiceException
 * method to retrieve documents for invoiceids				
 */
				public List<InvoiceDocumentVO>  getInvoiceDocuments(List<Integer> invoiceIds)throws BusinessServiceException{
					
					 List<InvoiceDocumentVO> invoiceDocuments = new ArrayList<InvoiceDocumentVO>();

					try {
						invoiceDocuments = getServiceOrderDao().getInvoiceDocumentList(invoiceIds);
						return invoiceDocuments;
						
					} catch (Exception e) {
						logger.error("Exception in ServiceOrderBO getInvoiceDocuments due to:" + e);
						return null;  
					}
				}
	
				
	/**
	* get closure method
	* @param soId
	* @return
	* @throws BusinessServiceException
	*/			
	public String getMethodOfClosure(String soId) throws BusinessServiceException {
		try{
			return getServiceOrderDao().getMethodOfClosure(soId);
			
		}catch(DataServiceException e){
			logger.error("Exception while trying to get method of closure :" + e);
			throw new BusinessServiceException("Exception in getMethodOfClosure() of SODetailsDelegateImpl",e);
		}	
	}
	
	
	/**SL-20400 ->To find out the duplicate service orders with the given unit number and 
	order number combination in ServiceLive database for InHome Buyer (3000). */
	public ServiceOrder getDuplicateSOInHome(String customUnitNumber,String customOrderNumber) throws BusinessServiceException {
		ServiceOrder serviceOrder = null;
		try {
			serviceOrder = getServiceOrderDao().getDuplicateSOInHome(customUnitNumber,customOrderNumber);
		} catch (DataServiceException dsEx) {
			String strMessage = "Unexpected error while retrieving Duplicate SO for InHome";
			logger.error(strMessage, dsEx);
			throw new BusinessServiceException(strMessage, dsEx);
		}
		return serviceOrder;
	}

	/**
	 * Used to log request and response Duplicate SO's for InHome Buyer (3000).
	 * @param String request,String response, String buyerId
	 * @return Integer
	 */	
	public Integer logDuplicateSORequestResponse(String request,String response, Integer buyerId,String apiName) throws BusinessServiceException  {
		Integer loggingId =null;
		try{
			 loggingId = getServiceOrderDao().logDuplicateSORequestResponse(request,response,buyerId,apiName); 
		}catch (DataServiceException dsEx) {
			String strMessage = "Unexpected error in logDuplicateSORequestResponse";
			logger.error(strMessage, dsEx);
			throw new BusinessServiceException(strMessage, dsEx);
		}
		return loggingId;
	} 

	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

	/**SL 20853 to fetch additional_payment_encryption flag
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getAdditionalPaymentEncryptFlag(String flag) throws BusinessServiceException{
		try{
			return creditCardDao.getEncryptFlag(flag);
		}
		catch(DataServiceException e){
			logger.error("Exception while trying to get additional payment encryption flag :" + e);
			throw new BusinessServiceException("Exception in getAdditionalPaymentEncryptFlag() of SODetailsDelegateImpl",e);
		}	
	}
	/**@Description: Fetching original OrderId and warranty provider for the service order
	 * @param serviceOrderId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SOWorkflowControlsVO getSoWorkflowControl(String soId) throws BusinessServiceException{
		SOWorkflowControlsVO controlsVO =null;
		try{
			controlsVO = getServiceOrderDao().getSoWorkflowControls(soId);
		}catch (DataServiceException e) {
			logger.error("Exception in fetching original order id and warranty provider for the order" + e);
			throw new BusinessServiceException("Exception in fetching original order id and warranty provider for the order",e);
		}
		return controlsVO;
		
	}
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO#isLessThanSpendLimitLabour(java.lang.String)
	 */
	/*public boolean isLessThanSpendLimitLabour(String soId) throws BusinessServiceException{
		try{
			return getServiceOrderDao().isLessThanSpendLimitLabour(soId);
		}catch(DataServiceException e){
			throw new BusinessServiceException("exception in isLessThanSpendLimitLabour() of ServiceOrderBO",e);
		}
	}*/
	/**Priority 5B changes
	 * update invalid_model_serial_ind column in so_workflow_controls
	 * @param soId
	 * @param ind
	 * @throws BusinessServiceException
	 */
	public void updateModelSerialInd(String soId, String ind) throws BusinessServiceException{
		try{
			getServiceOrderDao().updateModelSerialInd(soId, ind);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in updateModelSerialInd() of ServiceOrderBO",e);
		}	
	}
	
	/**
	 * priority 5B changes
	 * get buyer first name & last name
	 * @param buyerId
	 * @return 
	 * @throws BusinessServiceException
	 */
	public String getBuyerName(Integer buyerResId) throws BusinessServiceException{
		try{
			return getServiceOrderDao().getBuyerName(buyerResId);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in getBuyerName() of ServiceOrderBO",e);
		}	
	}
	
	/**
	 * priority 5B changes
	 * get the validation rules for the fields
	 * @param fields
	 * @return List<ValidationRulesVO>
	 * @throws BusinessServiceException
	 */
	public List<ValidationRulesVO> getValidationRules(List<String> fields) throws BusinessServiceException{
		try{
			return getServiceOrderDao().getValidationRules(fields);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in getValidationRules() of ServiceOrderBO",e);
		}
	}
	
	/**
	 * priority 5B changes
	 * delete custom reference by type
	 * @param soId
	 * @param refType
	 * @throws BusinessServiceException
	 */
	public void deleteSOCustomReference(String soId, String refType) throws BusinessServiceException{
		try{
			ServiceOrderCustomRefVO custRefVO = new ServiceOrderCustomRefVO();
			custRefVO.setsoId(soId);
			
			if(InHomeNPSConstants.MODEL.equalsIgnoreCase(refType)){
				custRefVO.setRefTypeId(InHomeNPSConstants.MODEL_REF_ID);
			}
			else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(refType)){
				custRefVO.setRefTypeId(InHomeNPSConstants.SERIAL_REF_ID);
			}
			getServiceOrderDao().deleteCustomRefByBuyerRefID(custRefVO);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in deleteSOCustomReference() of ServiceOrderBO",e);
		}
	}
	
	/**
	 * SL-21070
	 * Method fetches the lock_edit_ind of the so
	 * @param soId
	 * @return int lockEditInd
	 */
	public int getLockEditInd(String soId){
		return  getServiceOrderDao().getLockEditInd(soId); 
	}
	/** Get Estimate Details
	 * @param soId
	 * @param estimationId
	 * @return EstimateVO
	 */
	public EstimateVO getEstimate(String soId, Integer estimationId) throws BusinessServiceException{
		EstimateVO estimateVO = null;
		try{
			estimateVO = getServiceOrderDao().getEstimate(soId, estimationId);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in getEstimate() of ServiceOrderBO",e);
		}
		return estimateVO;
	}
	
	public EstimateVO getEstimateMainDetails(String soId, Integer estimationId) throws BusinessServiceException{
		EstimateVO estimateVO = null;
		try{
			estimateVO = getServiceOrderDao().getEstimateMainDetails(soId, estimationId);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in getEstimate() of ServiceOrderBO",e);
		}
		return estimateVO;
	}
	
	public void insertEstimateHistory(EstimateVO estimateVO) throws BusinessServiceException{
		
		try{
			 getServiceOrderDao().insertEstimateHistory(estimateVO);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in insertEstimateHistory() of ServiceOrderBO",e);
		}
	
	}
	


	/**
	 * @param soId
	 * @param estimateId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean validateEstimate(String soId, Integer estimateId) throws BusinessServiceException{
		boolean valid = false;
		try{

			valid =  getServiceOrderDao().validateEstimate(soId, estimateId);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in deleteSOCustomReference() of ServiceOrderBO",e);
		}
		return valid;
	}	

	/**
	 * @param soId
	 * @param estId
	 * @param status
	 * @param comments
	 * @param source
	 * @param modifiedBy
	 * @param customerName
	 * @return
	 * @throws BusinessServiceException
	 */

	public void updateEstimateStatus(String soId, Integer estId, String status, 
			String comments,String source, String modifiedBy,String customerName) throws BusinessServiceException{
		try{			
			getServiceOrderDao().updateEstimateStatus(soId, estId,status,comments,source,modifiedBy,customerName);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in deleteSOCustomReference() of ServiceOrderBO",e);
		}
	}

	/**
	 * Fetch the estimation details of an SO
	 * @param soId
	 * @param vendorId
	 * @return List<EstimateVO>
	 * @throws BusinessServiceException
	 */
	public List<EstimateVO> getEstimationDetails(String soId,Integer vendorId) throws BusinessServiceException{

		List<EstimateVO> estimateDetails = null;
		try {
			estimateDetails = getServiceOrderDao().getEstimationDetails(soId,vendorId); 
		}
		catch(DataServiceException e){
			throw new BusinessServiceException(e);
		}
		return estimateDetails;
	}

	public List<ClosedServiceOrderVO> getClosedServiceOrders(ClosedOrdersRequestVO closedOrdersRequestVO) throws BusinessServiceException{
		logger.info("INSIDE ServiceOrderBO ----> getClosedServiceOrders()");
		List<ClosedServiceOrderVO> serviceOrderResults = null;
		try {
			serviceOrderResults = this.getServiceOrderDao().getClosedOrders(closedOrdersRequestVO);
		} catch(DataServiceException e){
			throw new BusinessServiceException(e);
		}
		logger.info("INSIDE ServiceOrderBO ----> getClosedServiceOrders() Ends");
		return serviceOrderResults;
	}



	/**
	 * To fetch the insurance details
	 */
	public Map<String,List<LicensesAndCertVO>> getVendorInsuranceDetails(List<String> firmIdList) throws BusinessServiceException {
		Map<String,List<LicensesAndCertVO>> insuranceVOs = new HashMap<String,List<LicensesAndCertVO>>();
		List<LicensesAndCertVO> insuranceList = null;
		try {
			insuranceList=getServiceOrderDao().getVendorInsuranceDetails(firmIdList);
			if(null !=insuranceList && !insuranceList.isEmpty()){
				for(LicensesAndCertVO liceCertVO:insuranceList){
					if(null != liceCertVO && StringUtils.isNotBlank(liceCertVO.getFirmId())){
						String firmId = liceCertVO.getFirmId();
						if(insuranceVOs.containsKey(firmId)){
							List<LicensesAndCertVO> insList = insuranceVOs.get(firmId);
							if(null != insList && !insList.isEmpty()){
								insList.add(liceCertVO);
								insuranceVOs.put(firmId, insList);
							}
						}else{
							List<LicensesAndCertVO> insList = new ArrayList<LicensesAndCertVO>();
							insList.add(liceCertVO);
							insuranceVOs.put(firmId, insList);
						}
					}
				}
			}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return insuranceVOs;
	}

	/**
	 * To fetch the license details
	 */
	public Map<String,List<LicensesAndCertVO>> getVendorLicenseDetails(List<String> firmIdList) throws BusinessServiceException {
		Map<String,List<LicensesAndCertVO>> licenseVOs = new HashMap<String,List<LicensesAndCertVO>>();
		List<LicensesAndCertVO> licenseList = null;
		try {
			licenseList=getServiceOrderDao().getVendorLicenseDetails(firmIdList);

			if(null !=licenseList && !licenseList.isEmpty()){
				for(LicensesAndCertVO liceCertVO:licenseList){
					if(null != liceCertVO && StringUtils.isNotBlank(liceCertVO.getFirmId())){
						String firmId = liceCertVO.getFirmId();
						if(licenseVOs.containsKey(firmId)){
							List<LicensesAndCertVO> licList = licenseVOs.get(firmId);
							if(null != licList && !licList.isEmpty()){
								licList.add(liceCertVO);
								licenseVOs.put(firmId, licList);
							}
						}else{
							List<LicensesAndCertVO> licList = new ArrayList<LicensesAndCertVO>();
							licList.add(liceCertVO);
							licenseVOs.put(firmId, licList);
						}
					}
				}
			}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return licenseVOs;
	}

	/**
	 * To fetch the last closed service order for firms
	 */
	public Map<String,LastClosedOrderVO> getLastClosedOrder(List<String> firmIdList) throws BusinessServiceException {
		Map<String,LastClosedOrderVO> closedOrderVOs = new HashMap<String, LastClosedOrderVO>();
		List<LastClosedOrderVO> lastClosedOrderList = null;
		try {
			lastClosedOrderList=getServiceOrderDao().getLastClosedOrder(firmIdList);
			
			if(null != lastClosedOrderList && !lastClosedOrderList.isEmpty()){
				for(LastClosedOrderVO lastOrderVO : lastClosedOrderList){
					if(null != lastOrderVO){
					closedOrderVOs.put(lastOrderVO.getFirmId(), lastOrderVO);
					}
				}
			}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return closedOrderVOs;
	}

	/**
	 * To fetch the warranty and Policies & Procedures information for firms
	 */
	public Map<String,WarrantyVO> getWarrantyPolicies(List<String> firmIdList) throws BusinessServiceException {
		Map<String,WarrantyVO> warrantyVOs = new HashMap<String, WarrantyVO>();
		List<WarrantyVO>warrantyList = null;
		try {
			warrantyList = getServiceOrderDao().getWarrantyDetails(firmIdList);
			if(null != warrantyList && !warrantyList.isEmpty()){

				//construct a map with firmId as key and list of services as value
				for(WarrantyVO warrantyVO : warrantyList){
					if(null != warrantyVO && StringUtils.isNotBlank(warrantyVO.getVendorID())){
						String firmId = warrantyVO.getVendorID();
						if(StringUtils.isNotBlank(warrantyVO.getWarrOfferedLabor()) && StringUtils.isNotBlank(warrantyVO.getWarrPeriodLabor())){
							if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getWarrOfferedLabor()))
							{
								warrantyVO.setWarrPeriodLabor(lookupDAO.getWarrantyPeriod(Integer.parseInt(warrantyVO.getWarrPeriodLabor())));
							}
						}

						if(StringUtils.isNotBlank(warrantyVO.getWarrOfferedParts()) && StringUtils.isNotBlank(warrantyVO.getWarrPeriodParts())){		
							if(PublicAPIConstant.ONE.equals(warrantyVO.getWarrOfferedParts()))
							{
								warrantyVO.setWarrPeriodParts(lookupDAO.getWarrantyPeriod(Integer.parseInt(warrantyVO.getWarrPeriodParts())));
							}
						}
						warrantyVOs.put(firmId, warrantyVO);
					}
				}
			}
		} catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
		return warrantyVOs;
	}

	public IWarrantyDao getWarrantyDao() {
		return warrantyDao;
	}

	public void setWarrantyDao(IWarrantyDao warrantyDao) {
		this.warrantyDao = warrantyDao;
	}

	public ILookupDAO getLookupDAO() {
		return lookupDAO;
	}

	public void setLookupDAO(ILookupDAO lookupDAO) {
		this.lookupDAO = lookupDAO;
	}

	/**
	 * Method to fetch the details of the requested firms
	 * @param firmDetailRequestVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public FirmDetailsResponseVO getFirmDetails(FirmDetailRequestVO firmDetailRequestVO)throws BusinessServiceException {
		FirmDetailsResponseVO detailsResponseVO = new FirmDetailsResponseVO();
		try{
			List<String>responseFilter = firmDetailRequestVO.getFilter();
			Map<String,BasicFirmDetailsVO> basicFirmDetailsVOs = null;
			Map<String,List<LicensesAndCertVO>> insuranceDetailsVOs = null;
			Map<String,List<LicensesAndCertVO>> credentialDetailsVOs = null;
			Map<String,LastClosedOrderVO> lastClosedOrderVOs = null;
			Map<String,WarrantyVO> warrantPolicyVOs = null;
			Map<String,List<ReviewVO>> reviewVOs = null;
			Map<String,List<FirmServiceVO>> firmServiceVOs = null;
			//fetching the basic firm details
			basicFirmDetailsVOs = getBasicFirmDetails(firmDetailRequestVO.getFirmId());
			detailsResponseVO.setBasicDetailsVOs(basicFirmDetailsVOs);

			if(null != responseFilter && !responseFilter.isEmpty()){
				for(String filter : responseFilter){
					//fetching the last closed order details
					if(PublicAPIConstant.LASTORDER.equalsIgnoreCase(filter)){
						lastClosedOrderVOs = getLastClosedOrder(firmDetailRequestVO.getFirmId());
						detailsResponseVO.setLastClosedOrderVOs(lastClosedOrderVOs);
					}
					//fetching the contact details
					else if(PublicAPIConstant.CONTACT.equalsIgnoreCase(filter)){
						//basicFirmDetailsVOs = getBasicFirmDetails(firmDetailRequestVO.getFirmId());
						// the details already fetched with basic details
					}
					//fetching the warranty details
					else if (PublicAPIConstant.WARRANTY.equalsIgnoreCase(filter)||PublicAPIConstant.POLICY.equalsIgnoreCase(filter)){
						warrantPolicyVOs = getWarrantyPolicies(firmDetailRequestVO.getFirmId());
						detailsResponseVO.setWarrantPolicyVOs(warrantPolicyVOs);
					}
					//fetching the insurance details
					else if(PublicAPIConstant.INSURANCES.equalsIgnoreCase(filter)){
						insuranceDetailsVOs = getVendorInsuranceDetails(firmDetailRequestVO.getFirmId());
						detailsResponseVO.setInsuranceDetailsVOs(insuranceDetailsVOs);
					}
					//fetching the credential details
					else if(PublicAPIConstant.CREDENTIALS.equalsIgnoreCase(filter)){
						credentialDetailsVOs = getVendorLicenseDetails(firmDetailRequestVO.getFirmId());
						detailsResponseVO.setCredentialDetailsVOs(credentialDetailsVOs);
					}
					//fetching the review details
					else if(PublicAPIConstant.REVIEWS.equalsIgnoreCase(filter)){
						reviewVOs = getVendorReviewDetails(firmDetailRequestVO.getFirmId());
						detailsResponseVO.setReviewVOs(reviewVOs);

					}
					//fetching the service details
					else if (PublicAPIConstant.SERVICES.equalsIgnoreCase(filter)){
						firmServiceVOs = getVendorServiceDetails(firmDetailRequestVO.getFirmId());
						detailsResponseVO.setFirmServiceVOs(firmServiceVOs);
					}
				}
			}
		}
		catch(BusinessServiceException e){
			throw new BusinessServiceException(e);
		}
		return detailsResponseVO;

	}
	/**
	 * Method to fetch the valid firms
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<String> getValidProviderFirms(List<String> firmIds) throws BusinessServiceException {
		List<String> validFirms = null;
		try {
			validFirms = getServiceOrderDao().getValidProviderFirms(firmIds);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException(e);
		}
		return validFirms;
	}
	/**
	 * method to fetch the basic minimum details of a firm
	 * @param firmIds
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map<String,BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds)throws BusinessServiceException  {

		Map<String,BasicFirmDetailsVO> basicFirmDetailsVOs =new HashMap<String, BasicFirmDetailsVO>();

		try {

			List<BasicFirmDetailsVO> basicDetails = getServiceOrderDao().getBasicFirmDetails(firmIds);
			
			//SL-21446-Relay Services: Fetching the company logo if available
			Map <Long,String> companyLogo =  getServiceOrderDao().getCompanyLogo(firmIds);
			
			// Fetching the default image form application_properties table
			String defaultLogo = getServiceOrderDao().getDefaultLogo(PublicAPIConstant.DEFAULT_FIRM_LOGO);
			//Fetching the document path
			String staticUrl = getServiceOrderDao().getStaticUrl(PublicAPIConstant.FIRM_LOGO_PATH);
			String firmLogoSaveLocation = getServiceOrderDao().getStaticUrl(Constants.AppPropConstants.FIRM_LOGO_SAVE_LOC);
			logger.info("firmLogoSaveLocation:"+firmLogoSaveLocation);
			
			long start = System.currentTimeMillis();
			Map <Long,Long>numberOfEmployees = getServiceOrderDao().getNoOfEmployees(firmIds);
			Map <Integer,BigDecimal>aggregateRating =  getServiceOrderDao().getAggregateRating(firmIds);
			Map <Integer,Long>reviewCount =  getServiceOrderDao().getOverallReviewCount(firmIds);
			long end = System.currentTimeMillis();
			logger.info("Time taken to fetch aggregate rating and review count :"+(end-start));
			if(null != basicDetails && !basicDetails.isEmpty()){
				for (BasicFirmDetailsVO basicVO : basicDetails) {
					if(null != basicVO && null != basicVO.getFirmId()){
						if(null != numberOfEmployees && !numberOfEmployees.isEmpty()){
							if(null != numberOfEmployees.get(new Long(basicVO.getFirmId()))){
								basicVO.setNumberOfEmployees(numberOfEmployees.get(new Long(basicVO.getFirmId())).intValue());
							}
								 
						}
						if(null != aggregateRating && !aggregateRating.isEmpty()){
							if(null != aggregateRating.get(basicVO.getFirmId())){
								basicVO.setFirmAggregateRating(aggregateRating.get(basicVO.getFirmId()).doubleValue());
							}
						}
						if(null != reviewCount && !reviewCount.isEmpty()){
							if( null != reviewCount.get(basicVO.getFirmId())){
								basicVO.setReviewCount(reviewCount.get(basicVO.getFirmId()).intValue());
							}			
						}
						//SL-21446-Adding company logo url
						if(null != companyLogo && !companyLogo.isEmpty() && null != companyLogo.get(new Long(basicVO.getFirmId()))){
							//if company logo is avaliable
							//basicVO.setCompanyLogoUrl(staticUrl+companyLogo.get(basicVO.getFirmId()));	
							
							String logoPath=companyLogo.get(new Long(basicVO.getFirmId()));
							logger.info("logoPath:"+logoPath);
							logoPath=logoPath.replace(firmLogoSaveLocation,"");
							basicVO.setCompanyLogoUrl(staticUrl+logoPath);	

						}
						else{
							//company logo not available
							basicVO.setCompanyLogoUrl(defaultLogo);
						}
						basicFirmDetailsVOs.put(basicVO.getFirmId().toString(), basicVO);
					}
				}
			}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return basicFirmDetailsVOs;
	}

	/**
	 * fetch the service details for a list of firm
	 * @param firmIds
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map<String,List<FirmServiceVO>> getVendorServiceDetails(List<String> firmIdList) throws BusinessServiceException {

		Map<String,List<FirmServiceVO>> firmServiceVOs = new HashMap<String,List<FirmServiceVO>>();
		List<FirmServiceVO> servicesList = null;

		try{
			//fetch the services for the list of firms
			servicesList = getServiceOrderDao().getVendorServiceDetails(firmIdList);
			if(null != servicesList && !servicesList.isEmpty()){

				//construct a map with firmId as key and list of services as value
				for(FirmServiceVO serviceVO : servicesList){
					if(null != serviceVO && StringUtils.isNotBlank(serviceVO.getFirmId())){
						String firmId = serviceVO.getFirmId();
						if(firmServiceVOs.containsKey(firmId)){
							List<FirmServiceVO> serviceList = firmServiceVOs.get(firmId);
							if(null != serviceList && !serviceList.isEmpty()){
								serviceList.add(serviceVO);
								firmServiceVOs.put(firmId, serviceList);
							}
						}else{
							List<FirmServiceVO> serviceList = new ArrayList<FirmServiceVO>();
							serviceList.add(serviceVO);
							firmServiceVOs.put(firmId, serviceList);
						}
					}
				}
			}

		}catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}

		return firmServiceVOs;
	}

	/**
	 * fetch the review details for a list of firm
	 * @param firmIds
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map<String,List<ReviewVO>> getVendorReviewDetails(List<String> firmIdList) throws BusinessServiceException {

		Map<String,List<ReviewVO>> firmReviewVOs = new HashMap<String,List<ReviewVO>>();
		List<ReviewVO> reviewsList = null;

		try{
			//fetch the reviews for the list of firms
			reviewsList = getServiceOrderDao().getVendorReviewDetails(firmIdList);
			if(null != reviewsList && !reviewsList.isEmpty()){

				//construct a map with firmId as key and list of reviews as value
				for(ReviewVO reviewVO : reviewsList){
					if(null != reviewVO && null != reviewVO.getFirmId()){
						String firmId = reviewVO.getFirmId().toString();
						if(firmReviewVOs.containsKey(firmId)){
							List<ReviewVO> reviewList = firmReviewVOs.get(firmId);
							if(null != reviewList && !reviewList.isEmpty()){
								reviewList.add(reviewVO);
								firmReviewVOs.put(firmId, reviewList);
							}
						}else{
							List<ReviewVO> reviewList = new ArrayList<ReviewVO>();
							reviewList.add(reviewVO);
							firmReviewVOs.put(firmId, reviewList);
						}
					}
				}
			}

		}catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}

		return firmReviewVOs;
	}
	
	
	/**
	 * Fetch the routed provider details of an SO
	 * @param soId
	 * @return List<RoutedProvider>
	 * @throws BusinessServiceException
	 */
	public List<RoutedProvider> getRoutedProviders(String soId) throws BusinessServiceException{

		List<RoutedProvider> routedProviderDetails = null;
		try {
			routedProviderDetails = getServiceOrderDao().getRoutedProviders(soId);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException(e);
		}
		return routedProviderDetails;
	}
	
	
		/**
		 * Fetch the sealed bid ind of SO
		 * @param soId
		 * @return Boolean
		 * @throws BusinessServiceException
	 	*/
		public Boolean fetchSealedBidIndicator(String soId) throws BusinessServiceException
		{		Boolean sealedBidInd=false;
			try {
				sealedBidInd=getServiceOrderDao().fetchSealedBidIndicator(soId);
			} catch (Exception e) {
				throw new BusinessServiceException(e);
			}
			return sealedBidInd;
		}
	
	/**@Description : validate the reason code entered by the buyer for the reschedule
	 * @param reason
	 * @param validateErrors
	 * @return
	 * @throws BusinessServiceException
	 */
	public String validateReasonCodes(String reason,List<String> validateErrors)throws BusinessServiceException{
		Integer reasonCode = null;
		try{
			if(StringUtils.isNumeric(reason)){
			   reasonCode = Integer.parseInt(reason);
			   boolean isValid = false;
			   ArrayList<LookupVO> reasonCodes = getRescheduleReasonCodes(OrderConstants.BUYER_ROLEID);
			 
			   for(LookupVO reasonCodeVO : reasonCodes){
					if(null!=reasonCode && reasonCodeVO.getId().intValue()==reasonCode.intValue()){
						isValid = true;
						reason = reasonCodeVO.getType();
						break;
					}
			     }
			     if(!isValid){
			    	 validateErrors.add(OrderConstants.INVALID_REASON_CODE_ERROR);
			     }
			}else{
				validateErrors.add(OrderConstants.INVALID_REASON_CODE_ERROR);
		    }
		}catch(Exception e){
			throw new BusinessServiceException("Exception in validateReasonCodes() of ServiceOrderBO",e);
		}
		return reason;
		
	}
	/**@Description: Fetching the logo document id for the service order
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getLogoDocumentId(String soId)throws BusinessServiceException {
		Integer logoDocumentId =null;
		try{
			logoDocumentId = getServiceOrderDao().getLogoDocumentId(soId);
		}catch (Exception e) {
			logger.error("Error while retrieving document Id  in SOWizardFetchDelegateImpl.getLogoDocumentId()", e);
			throw new BusinessServiceException(e);
		}
		return logoDocumentId;
	}
	
	public HashMap<String, Object> getDocumentDetails(String soId,String documentName)throws BusinessServiceException{
		HashMap<String, Object> docDetails = new HashMap<String, Object>();
		
		try{
			docDetails = getServiceOrderDao().getDocumentDetails(soId,documentName);
		}catch(Exception e){
			logger.info("Exception in ProviderUploadDocumentService.getFirmId() "+e.getMessage());
		}
		return docDetails;
	}
	
	/**
	 * Determines if the service order is unique with the combination of buyer id and unique id
	 */
	public String isUniqueSo(Integer buyerId, String uniqueId) throws DataServiceException {
		String soId = null;
		try {
			soId =  getServiceOrderDao().isServiceOrderUnique(buyerId, uniqueId);
		}// end try
		catch (DataServiceException bse) {
			if (logger.isDebugEnabled())
				logger.debug("Exception thrown while fetching service order with unique id " + uniqueId + " for buyer " + buyerId);
			throw bse;

		}// end catch
		return soId;
	}// end method isUniqueSo
	
	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
    public  List<ServiceDatetimeSlot> getSODateTimeSlots(String soId) throws DataServiceException{
		
		return getServiceOrderDao().getSODateTimeSlots(soId);
	}
    public  void updateAcceptedServiceDatetimeSlot(ServiceDatetimeSlot serviceDatetimeSlot) throws BusinessServiceException{
    	getServiceOrderDao().updateAcceptedServiceDatetimeSlot(serviceDatetimeSlot);
    }

	public ServiceDatetimeSlot getSODateTimeSlot(String soId, Integer preferenceInd) throws DataServiceException {
		return getServiceOrderDao().getSODateTimeSlot(soId, preferenceInd);
	}

	public void updateCorelationIdWithSoId(HashMap<String, Object> corelationIdAndSoidMap) throws DataServiceException {
		getServiceOrderDao().updateCorelationIdWithSoId(corelationIdAndSoidMap);
	 }
	
	public void saveWarrantyHomeReasons(WarrantyHomeReasonInfoVO warrantyHomeReasonInfoVO) throws BusinessServiceException {
		getServiceOrderDao().saveWarrantyHomeReasons(warrantyHomeReasonInfoVO);
	}



public List<ProviderDetailWithSOAccepted> providerDetailWithSOAccepted(
		Integer buyerId, Integer days) throws BusinessServiceException {
	List<ProviderDetailWithSOAccepted> providerDetailWithSOAccepted = null;
	try {
		providerDetailWithSOAccepted = getServiceOrderDao().getAvailableProviderAcceptedSO(buyerId, days);
	} catch (DataServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return providerDetailWithSOAccepted;
}

public PushNotificationAlertTask getPushNotificationAlertTask() {
	return pushNotificationAlertTask;
}

public void setPushNotificationAlertTask(PushNotificationAlertTask pushNotificationAlertTask) {
	this.pushNotificationAlertTask = pushNotificationAlertTask;
}

//SLT-4491
@Override
public Integer getValidProvider(Integer providerId) throws BusinessServiceException {
	 Integer vendorId=null;
	 try{
		 vendorId= (Integer)getServiceOrderDao().getValidProvider(providerId);
		 logger.info("ServiceOrderBO.getValidProvider --> " +providerId);
	 }catch(Exception ex){
		 logger.error("[ServiceOrderBO.getValidProvider - Exception] "+ ex);
		 throw new BusinessServiceException(ex.getMessage(), ex);
	 }
	 return vendorId;
}
	

public List<SoLoggingVo2> getSoRescheduleLogDetailsAnyRoles(String soId) throws DataServiceException {
	List<SoLoggingVo2> soLogList = getLoggingDao().getSoRescheduleLogDetailsAnyRoles(soId);
	return soLogList;
}	

 
}
