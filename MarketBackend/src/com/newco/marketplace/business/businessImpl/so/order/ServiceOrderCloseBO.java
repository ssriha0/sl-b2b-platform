	package com.newco.marketplace.business.businessImpl.so.order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.staging.IStagingBO;
import com.newco.marketplace.business.businessImpl.staging.ShcStagingMapper;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderCloseBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.fee.PromoConstants;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStagingVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.oms.OMSStagingBridge;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderAddonDAO;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.validator.order.ServiceOrderValidator;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.servicelive.wallet.serviceinterface.IWalletBO;


public class ServiceOrderCloseBO extends BaseOrderBO implements IServiceOrderCloseBO {

	private Logger logger = Logger.getLogger(ServiceOrderCloseBO.class);
	private ILedgerFacilityBO transBo;
	private IFinanceManagerBO financeManagerBO;
	private IStagingBO stagingBO;
	private IServiceOrderAddonDAO serviceOrderAddonDAO;
	private OMSStagingBridge omsStagingBridge;
	private ServiceOrderBO serviceOrderBOTarget;
	private PromoBO promoBO;
	private IWalletBO walletBO;
	private Double totalAddOnServiceFee;

	public ProcessResponse processCloseSO(Integer buyerId, String serviceOrderID, double finalPartsPrice, double finalLaborPrice, SecurityContext securityContext)
			throws BusinessServiceException {
		logger.debug("----Start of ServiceOrderCloseBO.processCloseSO----");
		ProcessResponse processResp = new ProcessResponse();
		int intWfStateId = 0;
		Calendar calendar = Calendar.getInstance();
		boolean blnPaySuccess = false;
		List<String> responseMessages = new ArrayList<String>();
		int intUpdStatus = 0;
		int intOrderCompleteStatus = 0;

		try {
			ValidatorResponse validatorResp = new ServiceOrderValidator().validate(serviceOrderID, buyerId);
			if (validatorResp.isError())
			{
				logger.error(serviceOrderID + " - close service order failed validation, reason: " + validatorResp.getMessages());
				processResp.setCode(USER_ERROR_RC);
				processResp.setMessages(validatorResp.getMessages());
				return processResp;
			}
			ServiceOrder serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderID);
			if (serviceOrder == null)
			{
				logger.error(serviceOrderID + " - Service Order not found for soID:" + serviceOrderID);				
				return setErrorMsg(processResp, SERVICE_ORDER_OBJ_NOT_FOUND);
			}
			
			if(serviceOrder.getVendorResourceContact() == null && serviceOrder.getVendorResourceLocation() == null)
			{
				logger.error(serviceOrderID + " - Vendor information not found");				
				return setErrorMsg(processResp, SERVICE_ORDER_PROVIDER_DETAILS_NOT_FOUND);
			}
			
			if(serviceOrder.getBuyerResource() == null)
			{
				logger.error(serviceOrderID + " - Buyer Resource information not found");				
				return setErrorMsg(processResp, SERVICE_ORDER_BUYER_DETAILS_NOT_FOUND);
			}
			Double upsellAmt = serviceOrderBOTarget.calcUpsellAmount(serviceOrder);
			Double soProjectBalance = serviceOrderBOTarget.getSOProjectBalance(serviceOrder.getSoId());
			if (soProjectBalance < MoneyUtil.getRoundedMoney(finalPartsPrice + finalLaborPrice + upsellAmt)){
				logger.error(serviceOrderID + " - Insufficient project funding to close this service order.");				
				return setErrorMsg(processResp, SERVICE_ORDER_INSUFFICIENT_FUNDS_PROJECT);
			}
			
			intWfStateId = serviceOrder.getWfStateId();
			
			// Check if the buyer is authorized to close the service order
			if (!isAuthorizedBuyer(buyerId, serviceOrder)) {
				logger.error(serviceOrderID + " - Buyer is not authorized");				
				return setErrorMsg(processResp, BUYER_IS_NOT_AUTHORIZED);
			}
			
			if (intWfStateId == 0)
			{
				logger.error(serviceOrderID + " - WFState is not found");				
				return setErrorMsg(processResp, SERVICE_ORDER_WFSTATE_NOT_FOUND);
			}
			
			// Fulfillment check: see if we have sent fulfillment requests for this SO and have received valid responses before allowing close & pay.
			// This code is commented until we have stabilized the VLBC communication

			boolean ifFullfillmentEntryExists = walletBO.checkValueLinkReconciledIndicator(serviceOrder.getSoId());
			if (! ifFullfillmentEntryExists)
			{
				logger.error(serviceOrderID + " - cannot close order. Fullfillment entry not found");	
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				return setErrorMsg(processResp, FULLFILLMENT_ENTRY_NOT_FOUND);
			}
			

			boolean canClose = checkStateForClose(intWfStateId);
			logger.debug("canClose : " + canClose);
			if (canClose) {

				// Change Status of SO
				serviceOrder.setWfStateId(CLOSED_STATUS);
				serviceOrder.setWfSubStatusId(null);
				Timestamp ts = new Timestamp(calendar.getTimeInMillis());
				serviceOrder.setLastStatusChange(ts);
				serviceOrder.setLastChngStateId(intWfStateId);
				serviceOrder.setClosedDate(ts);
				intUpdStatus = getServiceOrderDao().updateSOStatus(serviceOrder);
				
				//set the final price in serviceorder so that its available in MarketPlaceTransactionVO
				serviceOrder.setLaborFinalPrice(finalLaborPrice);
				serviceOrder.setPartsFinalPrice(finalPartsPrice);

				//Now get their account details
				Account acct = financeManagerBO.getAccountDetails(buyerId);
				
				if (intUpdStatus > 0) {
					MarketPlaceTransactionVO marketVO = new MarketPlaceTransactionVO();
					marketVO.setServiceOrder(serviceOrder);
					marketVO.setUserTypeID(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
					marketVO.setBuyerID(buyerId);
					marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT);
					marketVO.setVendorId(serviceOrder.getAcceptedVendorId());
					marketVO.setUserName(securityContext.getUsername());
					
					marketVO.setAccountId(acct.getAccount_id());
					marketVO.setAutoACHInd(new Boolean(securityContext.isAutoACH()).toString());
					updateSoFinalPrice(serviceOrder);

					marketVO.getServiceOrder().setServiceFeePercentage(serviceOrder.getServiceFeePercentage());
					
					blnPaySuccess = this.transBo.closeServiceOrderLedgerAction(marketVO);


					if (blnPaySuccess) {
						intOrderCompleteStatus = getServiceOrderDao()
								.updateTotalOrdersComplete(buyerId, serviceOrder.getAcceptedResourceId());
					}
					if (intOrderCompleteStatus > 0) {
						processResp.setCode(VALID_RC);
						processResp.setSubCode(VALID_RC);
						responseMessages.add(CLOSESO_SUCCESS);
						
						// send email receipts to buyer and provider
						financeManagerBO.sendBuyerSOClosedEmail(buyerId, serviceOrderID);
						financeManagerBO.sendProviderSOClosedEmail(serviceOrder.getAcceptedVendorId(), serviceOrderID);
						
					} else {
						processResp.setCode(SYSTEM_ERROR_RC);
						processResp.setSubCode(SYSTEM_ERROR_RC);
						responseMessages.add(CLOSESO_FAILURE);
					}
				} else {
					processResp.setCode(SYSTEM_ERROR_RC);
					processResp.setSubCode(SYSTEM_ERROR_RC);
					responseMessages.add(CLOSESO_FAILURE);
				}
			}
			else
			{
				logger.error(serviceOrderID + " - Cannot close ServiceOrder - WFStateID: " + intWfStateId);
				processResp.setCode(SYSTEM_ERROR_RC);
				processResp.setSubCode(SYSTEM_ERROR_RC);
				responseMessages.add(CLOSESO_FAILURE);
			}
			processResp.setMessages(responseMessages);
		} catch (DataServiceException dse) {
			logger.error(serviceOrderID = " - DataServiceException", dse);
			String error = "";
			error = CLOSESO_FAILURE;
			throw new BusinessServiceException(error, dse);
		}
		
		logger.debug("----End of ServiceOrderBO.processCloseSO----");
		return processResp;
	}

	/**
	 * Description:
	 * @param serviceOrder
	 * @return
	 * @throws BusinessServiceException
	 * @throws BusinessServiceException
	 */
	private ServiceOrder updateSoFinalPrice(ServiceOrder serviceOrder) throws com.newco.marketplace.exception.BusinessServiceException, BusinessServiceException {

		serviceOrder.setUpsellAmt(serviceOrderBOTarget.calcUpsellAmount(serviceOrder));
		double serviceFeePercentage = promoBO.getPromoFee(
				serviceOrder.getSoId(), serviceOrder.getBuyerId().longValue(), PromoConstants.SERVICE_FEE_TYPE);
		double laborCost = serviceOrder.getLaborFinalPrice();
		double partsCost = serviceOrder.getPartsFinalPrice();
		//double addOnTotal = serviceOrder.getUpsellAmt();
		double finalOrderServiceFeeAmount = MoneyUtil.getRoundedMoneyCustom((laborCost+ partsCost) * serviceFeePercentage);
		
		serviceOrder.getSoPrice().setFinalServiceFee(finalOrderServiceFeeAmount);
		serviceOrder.getSoPrice().setDiscountedSpendLimitLabor(laborCost);
		serviceOrder.getSoPrice().setDiscountedSpendLimitParts(partsCost);
		serviceOrder.getSoPrice().setSoId(serviceOrder.getSoId());
		serviceOrder.setServiceFeePercentage(serviceFeePercentage);
		
		serviceOrderBOTarget.updateSOPricing(serviceOrder.getSoPrice());
		
		
		return serviceOrder;
	}

	private boolean checkStateForClose(int wfState) {
		if (wfState == COMPLETED_STATUS) {
			return true;
		} else {
			return false;
		}
	}

	public void stageUpsellPaymentAndSku(String soId) {
		
		try{
			ServiceOrderStagingVO stagingUpsellData = serviceOrderAddonDAO.loadUpsellInfo(soId);
			StagingDetails stagingDetails = ShcStagingMapper.mapStagingVOToStagingDetails(stagingUpsellData);
			setTotalAddOnServiceFee(stagingUpsellData.getTotalAddOnServiceFee());
			String stagingWebServiceEndPointURL = PropertiesUtils.getPropertyValue
			                                               (Constants.AppPropConstants.STAGE_ORDER_WEBSERVICE_END_POINT_URL);
			//Please do not remove following lines because they are required for debugging QA/Stress environment
			//stagingWebServiceEndPointURL = "http://localhost/omsstaging/services/StageOrderSEI";
			//stagingWebServiceEndPointURL = "http://localhost:8080/omsstaging/services/StageOrderSEI";
			logger.info("\n\n=========== Invoking OMSStaging WebService to stage upsell payment and sku.\n=========== WebService URL: " 
					+ stagingWebServiceEndPointURL +"\n");
				
			
			logger.info("****Invoking the ServiceLive web service to stage service Order info...");
			//Set the Endpoint url for WebService
			try {
								
				boolean isSuccess = omsStagingBridge.stageUpsoldInfo(stagingDetails);				
				// If false, the UpSoldInfo hasn't been updated since no staging data.Hence staging the data and calling the stageUpsoldInfo method again
				if(!isSuccess){					
					getServiceOrderDao().stageShcOrder(soId);
					isSuccess=omsStagingBridge.stageUpsoldInfo(stagingDetails);
					logger.info("****Invoked the stored procedure to stage data and called 'stageUpsoldInfo' webservice again...");
				}				
			} catch(Exception e) {
				String error = "Error invoking ServiceLive webservice to persist upsell payment info.";
				logger.error(error, e);
				throw e;		
			}		
		}catch(Exception e){
			String message = "Error occurred while persisting upsell payment info thru webService Call.";
			logger.error(message);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderCloseBO#stageFinalPrice(java.lang.String, double)
	 */
	public void stageFinalPrice(String serviceOrderID, double finalPrice) throws BusinessServiceException {
		
		// Retrieve Permit Price
		ServiceOrderCustomRefVO customRef = financeManagerBO.getCustomReferenceObject(OrderConstants.PERMIT_PRICE_REFERENCE_KEY, serviceOrderID);
		Double permitPrice = 0.0;
		if(customRef != null && StringUtils.isNotBlank(customRef.getRefValue())) {
			logger.debug("Custom Reference Permit Price Found. value=" + customRef.getRefValue());
			try {
				permitPrice = Double.parseDouble(customRef.getRefValue());
			} catch(NumberFormatException nfe) {
				logger.debug("Custom Reference is not a parsable double.  Defaulting permit price to 0.0");
				permitPrice = 0.0;
			}
		}
		
		// Update labor and permit price in staging
		if(stagingBO != null) {
			ServiceOrder serviceOrder = null;
			try {
				serviceOrder = getServiceOrderDao().getServiceOrder(serviceOrderID);
				double serviceFeePercentage = promoBO.getPromoFee(
						serviceOrder.getSoId(), serviceOrder.getBuyerId().longValue(), PromoConstants.SERVICE_FEE_TYPE);
				serviceOrder.setServiceFeePercentage(serviceFeePercentage);
				Double upsellAmt = serviceOrderBOTarget.calcUpsellAmount(serviceOrder);
				serviceOrder.setUpsellAmt(upsellAmt);
			} catch (DataServiceException dsEx) {
				throw new BusinessServiceException(dsEx.getMessage(), dsEx);
			}
			stagingBO.updateFinalPrice(serviceOrder, finalPrice, permitPrice,getTotalAddOnServiceFee());
		}
	}
	
	/** R16_1_1: SL-21270:Fetching finalLaborPrice and finalPartsPrice from so_hdr
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrderPriceVO getFinalPrice(String soId) throws BusinessServiceException{
		ServiceOrderPriceVO serviceOrderPriceVO = null;
		try{
			serviceOrderPriceVO = getServiceOrderDao().getFinalPrice(soId);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in getFinalPrice() of ServiceOrderCloseBO",e);
		}
		return serviceOrderPriceVO;
	}
	
	/** R16_1_1: SL-21270:Fetching addon details
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public AdditionalPaymentVO getAddonDetails(String soId)
			throws BusinessServiceException {
		AdditionalPaymentVO additionalPaymentVO = null;
		try{
			additionalPaymentVO = getServiceOrderDao().getAddonDetails(soId);
		}
		catch(DataServiceException e){
			throw new BusinessServiceException("Exception in getAddonDetails() of ServiceOrderCloseBO",e);
		}
		return additionalPaymentVO;
	}
	public ILedgerFacilityBO getTransBo() {
		return transBo;
	}

	public void setTransBo(ILedgerFacilityBO transBo) {
		this.transBo = transBo;
	}

	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	public IStagingBO getStagingBO()
	{
		return stagingBO;
	}

	public void setStagingBO(IStagingBO stagingBO)
	{
		this.stagingBO = stagingBO;
	}


	public IServiceOrderAddonDAO getServiceOrderAddonDAO() {
		return serviceOrderAddonDAO;
	}


	public void setServiceOrderAddonDAO(IServiceOrderAddonDAO serviceOrderAddonDAO) {
		this.serviceOrderAddonDAO = serviceOrderAddonDAO;
	}


	public ServiceOrderBO getServiceOrderBOTarget() {
		return serviceOrderBOTarget;
	}

	public void setServiceOrderBOTarget(ServiceOrderBO serviceOrderBOTarget) {
		this.serviceOrderBOTarget = serviceOrderBOTarget;
	}

	public OMSStagingBridge getOmsStagingBridge() {
		return omsStagingBridge;
}

	public void setOmsStagingBridge(OMSStagingBridge omsStagingBridge) {
		this.omsStagingBridge = omsStagingBridge;
	}

	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}

	public PromoBO getPromoBO() {
		return promoBO;
	}
	
	public Double getTotalAddOnServiceFee() {
		return totalAddOnServiceFee;
	}

	public void setTotalAddOnServiceFee(Double totalAddOnServiceFee) {
		this.totalAddOnServiceFee = totalAddOnServiceFee;
	}

	
}
