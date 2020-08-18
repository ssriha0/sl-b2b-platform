package com.newco.marketplace.web.action.details;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.AddonServiceRowDTO;
import com.newco.marketplace.web.dto.AddonServicesDTO;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

public class SODetailsCompletionRecordAction extends SLDetailsBaseAction implements Preparable, ServiceConstants, OrderConstants {

	private static final long serialVersionUID = -512519231751606219L;
	private static final Logger logger = Logger.getLogger(SODetailsCompletionRecordAction.class);
    private SOCompleteCloseDTO soCloseDto = new SOCompleteCloseDTO();
    private IBuyerFeatureSetBO buyerFeatureSetBO;
   
    static private String SOD_ACTION = "/soDetailsController.action";
    /**	IBuyerBO - skipping use of a delegate */
	private IBuyerBO buyerBo;
	
	public SODetailsCompletionRecordAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	public ISODetailsDelegate getDetailsDelegate() {
        return detailsDelegate;
    }

    public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
        this.detailsDelegate = detailsDelegate;
    }
    
    //SL-19820
    String soId;
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		logger.debug("----Start of SODetailsCompletionRecordAction.prepare----");
    	double partsSpendLimit = 0.0;
    	double laborSpendLimit = 0.0;
    	double totalSpendLimit = 0.0;
    	String resComment = "";
    	double finalPartsPrice = 0.0;
    	double finalLaborPrice = 0.0;
    	double finalPrice = 0.0;
    	int status = 0;
    	
    	
    	createCommonServiceOrderCriteria();
    	//SL-19820
        //ServiceOrderDTO soDTO = getCurrentServiceOrderFromSession();
    	String strSoId = getParameter("soId");
    	setAttribute(OrderConstants.SO_ID, strSoId);
    	this.soId = strSoId;
    	
    	String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
    	getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
    	ServiceOrderDTO soDTO = null;
		try{
			soDTO = getDetailsDelegate().getServiceOrder(strSoId, get_commonCriteria().getRoleId(), null);
		}catch(Exception e){
			logger.error("Exception while trying to fetch SO Details");
		}
		setAttribute(THE_SERVICE_ORDER, soDTO);
		setCurrentSOStatusCodeInRequest(soDTO.getStatus());
		
        List<SoAutoCloseDetailVo> soAutoCloseInfoList=new ArrayList<SoAutoCloseDetailVo>();
		if (soDTO != null) {

			// set extra tax column for relay buyers
			setAttribute("displayTax", BuyerConstants.RELAY_BUYER_ID == Integer.parseInt(soDTO.getBuyerID())  || BuyerConstants.TECH_TALK_BUYER_ID ==  Integer.parseInt(soDTO.getBuyerID()) );

			if (String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(soDTO.getBuyerID())
					|| String.valueOf(BuyerConstants.TECH_TALK_BUYER_ID).equals(soDTO.getBuyerID())) {

				setAttribute("laborTaxPercentage", null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent().doubleValue() : 0.00);
				setAttribute("partsTaxPercentage", null != soDTO.getPartsTaxPercent() ? soDTO.getPartsTaxPercent().doubleValue() : 0.00);
			} else {
				setAttribute("laborTaxPercentage", 0.00);
				setAttribute("partsTaxPercentage", 0.00);
			}

			if (soDTO.getStatus() != null) {
				status = (soDTO.getStatus()).intValue();
			}
			resComment = soDTO.getResolutionComment();
			partsSpendLimit = soDTO.getPartsSpendLimit();
			laborSpendLimit = soDTO.getLaborSpendLimit();
			totalSpendLimit = partsSpendLimit + laborSpendLimit;
			finalPartsPrice = soDTO.getFinalPartsPrice();
			finalLaborPrice = soDTO.getFinalLaborPrice();
			finalPrice = finalLaborPrice + finalPartsPrice;

			getUpsellInfo(soDTO);

			AdditionalPaymentVO additionalPaymentVO = getAdditionalPaymentInfo(strSoId);

			AddonServicesDTO addonServicesDTO = new AddonServicesDTO();
			soCloseDto.setAddonServicesDTO(addonServicesDTO);

			if (additionalPaymentVO != null && additionalPaymentVO.getPaymentAmount() != null && additionalPaymentVO.getPaymentAmount() > 0) {
				addonServicesDTO.setEndCustomerSubtotalTotal(additionalPaymentVO.getPaymentAmount());
				if (soDTO.getUpsellInfo() != null && soDTO.getUpsellInfo().getAddonServicesList() != null) {
					soCloseDto.getAddonServicesDTO().setAddonServicesList(soDTO.getUpsellInfo().getAddonServicesList());
					
					double taxTotal =0.0;
					if (null != addonServicesDTO.getAddonServicesList()) {
						for (AddonServiceRowDTO addons : addonServicesDTO.getAddonServicesList()) {
							taxTotal += (addons.getEndCustomerSubtotal() * addons.getTaxPercentage() / (1 +  addons.getTaxPercentage()));
						}
					}
					addonServicesDTO.setProviderTaxPaidTotal(taxTotal);
					
				}

				soCloseDto.getAddonServicesDTO().setUpsellPaymentIndicator(soDTO.getShowUpsellInfo());
				if (soDTO.getUpsellInfo() != null && soDTO.getUpsellInfo().getProviderPaidTotal() != null)
					addonServicesDTO.setPaymentAmount(soDTO.getUpsellInfo().getProviderPaidTotal());
				else
					addonServicesDTO.setPaymentAmount(0.00);
				if (_commonCriteria.getRoleId().intValue() == PROVIDER_ROLEID) {
					soCloseDto.getAddonServicesDTO().setLoggedInUser("1");// 1
																			// for
																			// provider
				}
				if (StringUtils.equals(additionalPaymentVO.getPaymentType(), OrderConstants.UPSELL_PAYMENT_TYPE_CHECK)) {
					if (StringUtils.isNotBlank(additionalPaymentVO.getCheckNo())) {
						addonServicesDTO.setPaymentInformation("Payment via check #"
								+ additionalPaymentVO.getCheckNo()
								+ " was submitted on "
								+ DateUtils.getDateAndTimeFromString(additionalPaymentVO.getPaymentReceivedDate(),
										DateUtils.DATE_TIME_FORMAT_WITH_TIMEZONE));
					} else // for cash
					{
						addonServicesDTO.setPaymentInformation("Payment was submitted on "
								+ DateUtils.getDateAndTimeFromString(additionalPaymentVO.getPaymentReceivedDate(),
										DateUtils.DATE_TIME_FORMAT_WITH_TIMEZONE));
					}
				} else // for credit cards, there can be only cash, check or
						// credit, first two are already handled above
				{
					addonServicesDTO.setPaymentInformation("Payment via credit card "
							+ additionalPaymentVO.getCardNo()
							+ " was submitted on "
							+ DateUtils.getDateAndTimeFromString(additionalPaymentVO.getPaymentReceivedDate(),
									DateUtils.DATE_TIME_FORMAT_WITH_TIMEZONE));
				}
				updateAddonPaymentInfo(addonServicesDTO, true, additionalPaymentVO);

			}
			soCloseDto.setPermitTaskList(soDTO.getPermitTaskList());
			Double maxLabor = soDTO.getSoTaskMaxLabor();
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			maxLabor = Double.valueOf(twoDForm.format(maxLabor.doubleValue()));
			soCloseDto.setSoMaxLabor(maxLabor.toString());
			soCloseDto.setSoFinalMaxLabor(soDTO.getSoFinalMaxLabor());
			soCloseDto.setTaskLevelPricing(soDTO.isTaskLevelPriceInd());
			soCloseDto.setBuyerRefs(buyerBo.getProviderReferences(strSoId));
			soCloseDto.setResComments(resComment);
			soCloseDto.setPartSpLimit(partsSpendLimit);
			soCloseDto.setLaborSpLimit(laborSpendLimit);
			soCloseDto.setPermitTaskAddonPrice(soDTO.getPermitTaskAddonPrice());
			soCloseDto.setTotalSpLimit(totalSpendLimit);
			soCloseDto.setFinalLaborPrice(String.valueOf(finalLaborPrice));
			soCloseDto.setFinalPartPrice(String.valueOf(finalPartsPrice));
			soCloseDto.setFinalPrice(finalPrice);
			soCloseDto.setBuyerID(soDTO.getBuyerID());
			if (soDTO != null && soDTO.getInvoiceParts() != null && soDTO.getInvoiceParts().size() > 0) {
				List<ProviderInvoicePartsVO> listFromSoDTO = soDTO.getInvoiceParts();
				List<InvoiceDocumentVO> invoiceDocList = null;
				for (ProviderInvoicePartsVO partVo : listFromSoDTO) {
					boolean partsInvoiceDocUploaded = false;
					invoiceDocList = partVo.getInvoiceDocuments();
					if (null != invoiceDocList && invoiceDocList.size() > 0) {
						partsInvoiceDocUploaded = true;
					}
					partVo.setInvoiceDocExists(partsInvoiceDocUploaded);
				}

				soCloseDto.setInvoiceParts(soDTO.getInvoiceParts());
			}

			soCloseDto.setInvoicePartsPricingModel(soDTO.getInvoicePartsPricingModel());
			// SL-20632
			soCloseDto.setRoleId(_commonCriteria.getRoleId());

			try {
				getDetailsDelegate().setInvoiceDocuments(soCloseDto);
			} catch (Exception e) {
				logger.info(" error in setting the invoice documents " + e);
			}

			soCloseDto.setSoPartLaborPriceReason(soDTO.getPartLaborPriceReason());
			getDetailsDelegate().getSoAutoCloseCompletionList(strSoId);

			soAutoCloseInfoList = getDetailsDelegate().getSoAutoCloseCompletionList(strSoId);
			// check to see if autoclose on for this buyer
			boolean isautocloseOn = false;
			isautocloseOn = buyerFeatureSetBO.validateFeature(Integer.parseInt(soDTO.getBuyerID()), BuyerFeatureConstants.AUTO_CLOSE);
			// SL-19820
			// getSession().setAttribute("isAutocloseOn", isautocloseOn);
			setAttribute("isAutocloseOn", isautocloseOn);

		} else {
			setAttribute("laborTaxPercentage", 0.00);
			setAttribute("partsTaxPercentage", 0.00);
		}
        getRequest().setAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO, soCloseDto);
        getRequest().setAttribute(Constants.SESSION.SOD_SO_AUTOCLOSED_INFO_DTO,soAutoCloseInfoList);
        getRequest().setAttribute(Constants.SESSION.SOD_SO_STATUS, status);
        
        if (status == 160){
        	//SL-19820
        	//getSession().setAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO, soCloseDto);
        	//We will be also setting it in session to be used while edit completion
        	getSession().setAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO+"_"+strSoId, soCloseDto);
        	setAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO, soCloseDto);
        }
        logger.debug("----End of SODetailsCompletionRecordAction.prepare----");
	}
	
	public String execute() throws Exception {
			String strResponse = "";
			Integer intRoleType = 0;
			createCommonServiceOrderCriteria();
			ServiceOrdersCriteria context = get_commonCriteria();
			intRoleType = context.getSecurityContext().getRoleId();
			System.out.println("intRoleType : " + intRoleType);
			if (intRoleType == OrderConstants.PROVIDER_ROLEID)
			{
				//SL-19820
				//ServiceOrderDTO soDTO = getCurrentServiceOrderFromSession();
				ServiceOrderDTO soDTO = getCurrentServiceOrderFromRequest();
				if (soDTO.getStatus().intValue() == COMPLETED_STATUS)
					strResponse = "successComplete";
				else
					strResponse = "successClose";
			}
			if (intRoleType == OrderConstants.BUYER_ROLEID || intRoleType == OrderConstants.SIMPLE_BUYER_ROLEID)
				strResponse = SUCCESS;
			
           return strResponse;
        }

	
	
	public String editCompletionRecordForSo() {
		logger.debug("----Start of SODetailsCompletionRecordAction.editCompletionRecordForSo----");
		String strResponse = "";
		SecurityContext ctx = null;
		String strSoId = "";
		String strErrorMessage = "";
		String strErrorCode = "";
		String defaultSelectedTab="";
		ProcessResponse prResp = null;
		String fromPage = "";
		
		
		try{
			//Sl-19820
			//strSoId = getSession().getAttribute(OrderConstants.SO_ID).toString();
			strSoId = getAttribute(OrderConstants.SO_ID).toString();
			ctx = (SecurityContext) getSession().getAttribute("SecurityContext");
			prResp = getDetailsDelegate().editCompletionRecordForSo(strSoId, ctx);
			strErrorCode = prResp.getCode();
			strErrorMessage = prResp.getMessages().get(0);
			//Sl-19820
			//soCloseDto = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
			soCloseDto = (SOCompleteCloseDTO)getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
				
			if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
			{	
				//Go to common error page in case of business logic failure error or fatal error
				strResponse = ERROR; 
				//Sl-19820
				//this.setReturnURL(SOD_ACTION);
				this.setReturnURL(SOD_ACTION+"?soId="+strSoId);
				this.setErrorMessage(strErrorMessage);
			}else
			{
				strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
				defaultSelectedTab = SODetailsUtils.ID_COMPLETE_FOR_PAYMENT;
				fromPage = "completionrecord";
				//Sl-19820
				//getSession().setAttribute(Constants.SESSION.SOD_SO_COMPLETION_RECORD,fromPage); 
				getSession().setAttribute(Constants.SESSION.SOD_SO_COMPLETION_RECORD+"_"+strSoId,fromPage); 
			}
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+strSoId, strErrorMessage);
			setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			logger.debug("strResponse : " + strResponse);
			
			//Redisplay the tabs
		    logger.debug("defaultSelectedTab : " + defaultSelectedTab);
			this.setDefaultTab( defaultSelectedTab );
			
		} catch (BusinessServiceException e) {
			logger.error("Exception in editCompletionRecordForSo: ",e);
		}
		logger.debug("----End of SODetailsCompletionRecordAction.editCompletionRecordForSo----");
		return strResponse;
	}
	
	/**
     * 
     * @param addonServiceDTO
     * @param isPaymentInfoViewable
     * @param additionalPaymentVO
     * @throws NumberFormatException
     */
	protected void updateAddonPaymentInfo(AddonServicesDTO addonServiceDTO,
			Boolean isPaymentInfoViewable,
			AdditionalPaymentVO additionalPaymentVO)
			throws NumberFormatException {
		String paymentType = additionalPaymentVO.getPaymentType();
		
		if(additionalPaymentVO.getPaymentAmount()!= null  && additionalPaymentVO.getPaymentAmount() > 0 ){
			addonServiceDTO.setUpsellPaymentIndicator(true);
			//addonServiceDTO.setPaymentAmountDisplayStr("$"+ additionalPaymentVO.getPaymentAmount()) ; //UIUtils.formatDollarAmount());
			addonServiceDTO.setPaymentAmountDisplayStr("$"+ String.format("%.2f", additionalPaymentVO.getPaymentAmount())) ; // Changes added under SL-17523
			addonServiceDTO.setPaymentReceivedDateDisplayFormattedStr(DateUtils.getDateAndTimeFromString(additionalPaymentVO.getPaymentReceivedDate(),DateUtils.DATE_TIME_FORMAT_WITH_TIMEZONE));
			if (StringUtils.equals(paymentType, OrderConstants.UPSELL_PAYMENT_TYPE_CHECK))
			{
				if (StringUtils.isNotBlank(additionalPaymentVO.getCheckNo()) && !additionalPaymentVO.getCheckNo().equals("null")) {
					addonServiceDTO.setCheckNumber(additionalPaymentVO.getCheckNo());
					addonServiceDTO.setPaymentByCheck(true);
					addonServiceDTO.setCheckAmount(additionalPaymentVO.getPaymentAmount());
				}
				else {
					addonServiceDTO.setPaymentByCash(true);
				}
			}
			//Credit card information
			else {
				addonServiceDTO.setPaymentByCC(true);//meaning its by Credit card
				addonServiceDTO.setCreditCardNumber(additionalPaymentVO.getCardNo());
				addonServiceDTO.setExpYear(additionalPaymentVO.getCardExpireYear());
				addonServiceDTO.setExpMonth(additionalPaymentVO.getCardExpireMonth());
				addonServiceDTO.setSelectedCreditCardTypeStr(additionalPaymentVO.getPaymentType()!= null ? additionalPaymentVO.getPaymentType() : "");
				if(StringUtils.isNotBlank(additionalPaymentVO.getPaymentType()))
				{
					/*SLT-2591 and SLT-2592: Disable Amex
					 * if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_AMEX_STR))
						addonServiceDTO.setSelectedCreditCardTypeStr(CreditCardConstants.CARD_ID_AMEX_DESC);
					else*/ if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_DISCOVER_STR))
						addonServiceDTO.setSelectedCreditCardTypeStr(CreditCardConstants.CARD_ID_DISCOVER_DESC);
					else if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_MASTERCARD_STR))
						addonServiceDTO.setSelectedCreditCardTypeStr(CreditCardConstants.CARD_ID_MASTERCARD_DESC);
					else if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_MASTERCARD_STR))
						addonServiceDTO.setSelectedCreditCardTypeStr(CreditCardConstants.CARD_ID_MASTERCARD_DESC);			
					else if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_SEARS_CHARGE_PLUS_STR))
						addonServiceDTO.setSelectedCreditCardTypeStr(CreditCardConstants.CARD_ID_SEARS_PLUS_DESC);
					else if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_COMMERCIAL_ONE_STR))
						addonServiceDTO.setSelectedCreditCardTypeStr(CreditCardConstants.CARD_ID_SEARS_COMMERCIAL_DESC);
					else if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_VISA_STR))
						addonServiceDTO.setSelectedCreditCardTypeStr(CreditCardConstants.CARD_ID_VISA_DESC);
				}
				
				if(isPaymentInfoViewable){
					addonServiceDTO.setPreAuthNumber(additionalPaymentVO.getAuthNumber() != null ? additionalPaymentVO.getAuthNumber() : "Not Available" );
				}
				else {
					addonServiceDTO.setPreAuthNumber(null);
				}
			}
		}
	}
	
	public AdditionalPaymentVO getAdditionalPaymentInfo(String soId) throws Exception
	{
		return this.getDetailsDelegate().getUpsellBO().getAdditionalPaymentInfo(soId);
	}
	public IBuyerBO getBuyerBo() {
		return buyerBo;
	}

	public void setBuyerBo(IBuyerBO buyerBo) {
		this.buyerBo = buyerBo;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}
    	
}
