package com.newco.marketplace.web.action.details;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ValidationRulesVO;
import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.AddonServiceRowDTO;
import com.newco.marketplace.web.dto.AddonServicesDTO;
import com.newco.marketplace.web.dto.DropdownOptionDTO;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.dto.SOPartsDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.servicelive.common.CommonConstants;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

public class SODetailsCompleteForPaymentAction extends SLDetailsBaseAction
		implements Preparable, ServiceConstants, OrderConstants, ModelDriven<SOCompleteCloseDTO>{

	private static final long serialVersionUID = 10002;// arbitrary number to
														// get rid
	private static final Logger logger = Logger.getLogger(SODetailsCompleteForPaymentAction.class.getName());
    static private String SOD_ACTION = "/soDetailsController.action";
    private SOCompleteCloseDTO model = new SOCompleteCloseDTO();    
	private ISODetailsDelegate ISODetailDelegate;
	private IServiceOrderUpsellBO upsellBO;
	private IW9RegistrationBO w9RegistrationBO;
	ArrayList<LookupVO> shippingCarrier; // dropdown list. same for all parts.	
	private List<IError> foo;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	/**	IBuyerBO - skipping use of a delegate */
	private IBuyerBO buyerBo;

	public SODetailsCompleteForPaymentAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
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

	public void prepare() throws Exception
	{
		this.clearFieldErrors();
   	 	this.clearActionErrors();
		createCommonServiceOrderCriteria();
		
		if(model == null)
			model = new SOCompleteCloseDTO();
		
		model.setEntityId(get_commonCriteria().getCompanyId());
		
		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);	
		String soId = getParameter("soId");	
		model.setSoId(soId);
		setAttribute(SO_ID, soId);
		this.soId = soId;
		
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
		setAttribute("errors",getSession().getAttribute("errors_"+soId));
		setAttribute("modelerrors",getSession().getAttribute("modelerrors_"+soId));
		getSession().removeAttribute("errors_"+soId);
		getSession().removeAttribute("modelerrors_"+soId);

		//oldPrepare();
		newPrepare();
		boolean isW9ExistsFlag = false;
		Integer companyId = get_commonCriteria().getCompanyId();
		String role = get_commonCriteria().getRoleType();
		if (role.equalsIgnoreCase(OrderConstants.PROVIDER))
		{
			isW9ExistsFlag=w9RegistrationBO.isW9Exists(companyId);
			getEinData(isW9ExistsFlag,companyId);
			getProviderThreshold(isW9ExistsFlag,companyId);
			isDobNotAvailableWithSSN(isW9ExistsFlag,companyId);
			getCompleteSOAmount(isW9ExistsFlag, companyId);
		}
		
		this.setErrorMessage("");		
		//SL-19820
		//ServiceOrderDTO soDto = getCurrentServiceOrderFromSession();
		ServiceOrderDTO soDto = null;
		try{
			soDto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			
			// set extra tax column for relay buyers
			setAttribute("displayTax", BuyerConstants.RELAY_BUYER_ID ==  Integer.parseInt(soDto.getBuyerID()) || BuyerConstants.TECH_TALK_BUYER_ID ==  Integer.parseInt(soDto.getBuyerID()));
		}catch(Exception e){
			logger.error("Exception while trying to fetch SO Details");
		}
		setAttribute(THE_SERVICE_ORDER, soDto);
		setCurrentSOStatusCodeInRequest(soDto.getStatus());
		model.setNonFunded(soDto.getNonFundedInd());
		
		//check to see if autoclose on for this buyer
		boolean isautocloseOn=false;
		isautocloseOn=buyerFeatureSetBO.validateFeature(Integer.parseInt(soDto.getBuyerID()), BuyerFeatureConstants.AUTO_CLOSE);
		model.setAutocloseOn(isautocloseOn);
		//SL-19820
		//getSession().setAttribute("isAutocloseOn", isautocloseOn);
		setAttribute("isAutocloseOn", isautocloseOn);
		//end of autoclose check;
		
		boolean isValidateOnsiteVisitOn=false;
		isValidateOnsiteVisitOn=buyerFeatureSetBO.validateFeature(Integer.parseInt(soDto.getBuyerID()), BuyerFeatureConstants.VALIDATE_TIME_ONSITE);
		model.setValidateTimeOnsite(isValidateOnsiteVisitOn);
	
		//fetching the completion document count
		Integer completionDocCount=null;
		boolean isRequiredDocsInd=false;
		completionDocCount=detailsDelegate.retrieveDocTypesCountByBuyerId(Integer.parseInt(soDto.getBuyerID()), OrderConstants.COMPLETION_SOURCE);
		if(null!=completionDocCount && completionDocCount>0){
			isRequiredDocsInd=true;
		}else{
			isRequiredDocsInd=false;
		}
		//Sl-19820
		//getSession().setAttribute("isRequiredDocsInd", isRequiredDocsInd);
		setAttribute("isRequiredDocsInd", isRequiredDocsInd);
		setMaxValueForInvoicePartsHSR();
		//SL-19820
		SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO+"_"+soId);
		
		
		setAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO, completeCloseDTO);
	}
	
	public void setMaxValueForInvoicePartsHSR(){
		String maxValueForInvoicePartsHSR= PropertiesUtils.getPropertyValue("HSR_MAX_VALUE_INVOICE_PARTS");
		logger.info("maxVlueForInvoicePartsHSR is "+maxValueForInvoicePartsHSR);
		//SL-19820
		//getSession().setAttribute(Constants.HSR_MAX_VALUE_INVOICE_PARTS, maxValueForInvoicePartsHSR);
		setAttribute(Constants.HSR_MAX_VALUE_INVOICE_PARTS, maxValueForInvoicePartsHSR);
	}
	
	private void initHSRPartsInvoicePanel()
	{	
		model = getModel();

		//Setting values of invoice parts already persisted
		//SL-19820
		//ServiceOrderDTO soDTO = getCurrentServiceOrderFromSession();
		ServiceOrderDTO soDTO = getCurrentServiceOrderFromRequest();
		if(soDTO!=null && soDTO.getInvoiceParts()!=null && soDTO.getInvoiceParts().size()>0 ){
			List<ProviderInvoicePartsVO> listFromSoDTO = soDTO.getInvoiceParts();
			List<InvoiceDocumentVO> invoiceDocList =null;
			for(ProviderInvoicePartsVO partVo:listFromSoDTO){
			    boolean partsInvoiceDocUploaded= false;
			    invoiceDocList =partVo.getInvoiceDocuments();
				if(null != invoiceDocList && invoiceDocList.size() > 0){
					partsInvoiceDocUploaded = true;
				}
				partVo.setInvoiceDocExists(partsInvoiceDocUploaded);
			}
			
			model.setInvoiceParts(listFromSoDTO);
		}
		//SL-19820
		//SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
		/*SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO) getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
		
		if(completeCloseDTO !=null && completeCloseDTO.getInvoiceParts()!=null  
				&&  completeCloseDTO.getInvoiceParts().size()>0){
			List<ProviderInvoicePartsVO> list = new ArrayList<ProviderInvoicePartsVO>();
			for (ProviderInvoicePartsVO providerInvoicePartsVO : completeCloseDTO.getInvoiceParts()) {
				if(providerInvoicePartsVO!=null && providerInvoicePartsVO.getFinalPrice()!=null){
					list.add(providerInvoicePartsVO);
				}
			}
			if(list.size()>0){
				model.setInvoiceParts(list);
			}
		}*/
		
		
		//get the coverage type custom reference for the SO
		model.setCoverageType(buyerBo.getCustomReference(model.getSoId(), InHomeNPSConstants.COVERAGE_TYPE_LABOR));
	}
		
		// SLM:86 -set the provider invoice parts in the model
	
	private void setProviderInvoiceParts(){
		
		ServiceOrderDTO soDTO = getCurrentServiceOrderFromRequest();
		if(soDTO!=null && soDTO.getInvoiceParts()!=null && soDTO.getInvoiceParts().size()>0 ){
			List<ProviderInvoicePartsVO> listFromSoDTO = soDTO.getInvoiceParts();
			model.setInvoiceParts(listFromSoDTO);
		}
	}
	
	

	private void initAddOnServicesPanel()
	{		
		model = getModel();
		
		// Only want to do all the initialization once
		if(model.getAddonServicesDTO() != null)
		{
			model.setAddonServicesDTO(new AddonServicesDTO());
		}

		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String)getAttribute(OrderConstants.SO_ID);
		List<ServiceOrderAddonVO> addons = upsellBO.getAddonsbySoId(soId);		
		
		//SL-19820
		/*SOCompleteCloseDTO completeCloseDTO = 
			(SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);*/
		SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO) getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
		AddonServicesDTO addonServicesDTO = null;
//			completeCloseDTO.getAddonServicesDTO();
		// Convert Raw Data to DTO
		
		
		if( completeCloseDTO == null )
			addonServicesDTO = new AddonServicesDTO();
		else
			addonServicesDTO = completeCloseDTO.getAddonServicesDTO();
		if( addonServicesDTO == null )
			addonServicesDTO = new AddonServicesDTO();
		List<AddonServiceRowDTO> serviceRowList = 
			addonServicesDTO.getAddonServicesList();

		//SL-19820
		//ServiceOrderDTO soDto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO soDto= getCurrentServiceOrderFromRequest();
		Integer intBuyerId = soDto.getBuyerID()!= null ? Integer.valueOf(soDto.getBuyerID()) : -1;
		
		model.setBuyerID(soDto.getBuyerID());
		
		Double providerPaidTotal = 0.0;	
		Double providerTaxPaidTotal = 0.0;
		Double endCustomerChargeTotal = 0.0;
		Double endCustomerSubtotalTotal = 0.0;
		Double sumOfEndCustomerCharges = 0.0;
		String addonCheckbox = "false";
		if( serviceRowList.size() <= 0 )
		{
			for(ServiceOrderAddonVO vo : addons)
			{
				boolean skipReqAddon = false;
				Double endCustomerPrice = vo.getRetailPrice();
				//Double providerPaid = vo.getRetailPrice() - MoneyUtil.getRoundedMoney((vo.getMargin() * vo.getRetailPrice()));
				//Rounding up the provider payment if the third digit after decimal is 5
				Double providerPaid = MoneyUtil.getRoundedMoney(vo.getQuantity() * (vo.getRetailPrice() - (vo.getRetailPrice() * vo.getMargin())));
				
				if( (OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT.equals(vo.getCoverage()) &&
						(intBuyerId.intValue() == BuyerConstants.HSR_BUYER_ID) ) ){
					skipReqAddon = true;
					//providerPaid = new Double(0.00);
				}
				
				//providerPaid = MoneyUtil.getRoundedMoney(providerPaid);
				Double subtotal = MoneyUtil.getRoundedMoney(endCustomerPrice * vo.getQuantity());
				
				/* if we want to skip Req addon, don't add it to ProviderPaidTotal */
				if(!skipReqAddon){
					//providerPaidTotal += MoneyUtil.getRoundedMoney(providerPaid * vo.getQuantity());
					providerPaidTotal += providerPaid;
				}
				endCustomerChargeTotal += endCustomerPrice;
				endCustomerSubtotalTotal += subtotal;
				providerTaxPaidTotal += subtotal * vo.getTaxPercentage() / (1 + vo.getTaxPercentage() );
				
				if(vo.isMiscInd() == false)
					sumOfEndCustomerCharges += endCustomerPrice;			
				
				addonServicesDTO.addAddonService(vo.getAddonId(),vo.getSku(), vo.getDescription(), vo.getQuantity(), vo.getQuantity() > 0 ? providerPaid : null, endCustomerPrice, vo.getQuantity() > 0 ? subtotal : null, vo.isMiscInd(), vo.getMargin(), vo.getCoverage(), skipReqAddon, vo.isAutoGenInd(),vo.getAddonPermitTypeId(),vo.getTaxPercentage(),vo.getSkuGroupType());
				if(vo != null && vo.getQuantity() > 0) {
					addonCheckbox = "true";
				}
			}
		}
		else
		{
			List<AddonServiceRowDTO> newDTOList = new ArrayList<AddonServiceRowDTO>(serviceRowList.size());
			Integer ctr = 0;
			for(ServiceOrderAddonVO vo : addons)
			{
				Integer quantity = serviceRowList.get(ctr).getQuantity();
				Double endCustomerCharge = 0.0;
				Double providerPaid = 0.0;
				Double subtotal = 0.0;
				Double margin = 0.0;
				String description = "";
				Integer permitType = -1;
				boolean skipReqAddon = false;
				
				if(vo.isMiscInd() == false)
				{
					description = vo.getDescription();
					endCustomerCharge = vo.getRetailPrice();
					if (endCustomerCharge== null)
					{
						endCustomerCharge = 0.0;
					}
					margin = vo.getMargin();
					sumOfEndCustomerCharges += endCustomerCharge;					
				}
				else
				{
					description = serviceRowList.get(ctr).getDescription();
					providerPaid = serviceRowList.get(ctr).getProviderPaid();
					if(vo.isAutoGenInd()&&vo.getQuantity()>0&&vo.getCoverage().equalsIgnoreCase("CC")){
						quantity = vo.getQuantity();
						endCustomerCharge = vo.getRetailPrice();
					}else{
						endCustomerCharge = serviceRowList.get(ctr).getEndCustomerSubtotal();
					}					
					permitType = serviceRowList.get(ctr).getPermitType();
					margin = vo.getMargin();
				}
				if (endCustomerCharge== null)
				{
					endCustomerCharge = 0.0;
				}
				if (margin== null)
				{
					margin = 0.0;
				}
				if (quantity== null)
				{
					quantity = 0;
				}
				
				//providerPaid = MoneyUtil.getRoundedMoney(quantity * MoneyUtil.getRoundedMoney(endCustomerCharge - MoneyUtil.getRoundedMoney((margin * endCustomerCharge))));
				//Rounding up the provider payment if the third digit after decimal is 5
				providerPaid = MoneyUtil.getRoundedMoney(quantity * (endCustomerCharge - (endCustomerCharge * margin)));
				subtotal = MoneyUtil.getRoundedMoney(quantity * endCustomerCharge);
						
				if( (OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT.equals(vo.getCoverage()) &&
						(intBuyerId.intValue() == BuyerConstants.HSR_BUYER_ID) ) ){
					skipReqAddon = true;
					//providerPaid = new Double(0.00);
				}
			
				
				/* if we want to skip Req addon, don't add it to ProviderPaidTotal */
				if(!skipReqAddon){
					providerPaidTotal += providerPaid;
				}
				endCustomerChargeTotal += subtotal;
				endCustomerSubtotalTotal += subtotal;
				providerTaxPaidTotal += subtotal * vo.getTaxPercentage() / (1 + vo.getTaxPercentage());
						
				newDTOList.add( new AddonServiceRowDTO(vo.getAddonId(),vo.getSku(), description, quantity, quantity > 0 ? providerPaid : null, endCustomerCharge, quantity > 0 ? subtotal : null, vo.isMiscInd(), vo.getMargin(), vo.getCoverage(),skipReqAddon, vo.isAutoGenInd(),permitType, vo.getTaxPercentage(), vo.getSkuGroupType()) );
				ctr++;
				if(vo != null && quantity > 0) {
					addonCheckbox = "true";
				}
			}
			
			if(addons!=null && addons.size()<serviceRowList.size()){
				Double endCustomerCharge = 0.0;
				Double providerPaid = 0.0;
				Double subtotal = 0.0;
				Double margin = 0.0;
				for(int count=ctr;count<serviceRowList.size();count++){
					AddonServiceRowDTO addonDTO = (AddonServiceRowDTO)serviceRowList.get(count);
					providerPaid = serviceRowList.get(count).getProviderPaid();
					endCustomerCharge = serviceRowList.get(count).getEndCustomerSubtotal();
					margin = serviceRowList.get(count).getMargin();
					Integer quantity = serviceRowList.get(count).getQuantity();
					if(addonDTO.getSku()!=null && addonDTO.getSku().equals("99888")){
                        addonDTO.setMisc(true);
                        } 				
					if (endCustomerCharge== null)
					{
						endCustomerCharge = 0.0;
					}
					if (margin== null)
					{
						margin = 0.0;
					}
					if (quantity== null)
					{
						quantity = 0;
					}
					if(quantity > 0) {
						addonCheckbox = "true";
					}
					newDTOList.add(addonDTO);
					subtotal = MoneyUtil.getRoundedMoney(quantity * endCustomerCharge);
					endCustomerChargeTotal += subtotal;
					endCustomerSubtotalTotal += subtotal;
					providerPaidTotal += providerPaid;
					providerTaxPaidTotal += subtotal * serviceRowList.get(count).getTaxPercentage() / (1 + serviceRowList.get(count).getTaxPercentage());
					
				}
			}
			
			
			
			addonServicesDTO.setAddonServicesList(newDTOList);
		}
		addonServicesDTO.setAddonCheckbox(addonCheckbox);

		if( providerPaidTotal == 0.0 )
			providerPaidTotal = null;
		addonServicesDTO.setProviderPaidTotal(MoneyUtil.getRoundedMoney(providerPaidTotal));
		addonServicesDTO.setEndCustomerChargeTotal(MoneyUtil.getRoundedMoney(endCustomerChargeTotal));
		if( endCustomerSubtotalTotal == 0.0 )
			endCustomerSubtotalTotal = null;
		addonServicesDTO.setEndCustomerSubtotalTotal(MoneyUtil.getRoundedMoney(endCustomerSubtotalTotal));
		addonServicesDTO.setSumOfEndCustomerChargesMinusMisc(MoneyUtil.getRoundedMoney(sumOfEndCustomerCharges));
		addonServicesDTO.setProviderTaxPaidTotal(MoneyUtil.getRoundedMoney(providerTaxPaidTotal));
		
		//Assuming if errors are present in the completeCloseDTO object then this workflow is from Completion 
		//so not changing additional payment
		if(null==completeCloseDTO){
			initAdditionalPayment(addonServicesDTO, soId);
		}else if(null!=completeCloseDTO && null!=completeCloseDTO.getErrors() && completeCloseDTO.getErrors().isEmpty()){
			initAdditionalPayment(addonServicesDTO, soId);
		}
		//SPM-1356:overriding credit card number with partially hidden credit card number(eg: ************1881)
		//when validation error occur in the completion page and the link shown below credit card number id "Edit".
		if(null != addonServicesDTO.getEditOrCancel() && ("edit").equals(addonServicesDTO.getEditOrCancel())){
			addonServicesDTO.setCreditCardNumber(addonServicesDTO.getCreditCardNumberActual());
		}

		//SL 3678- start
		logger.info("initAddonServices Panel before calling get urls");
		getSession().setAttribute("userName", get_commonCriteria()
				.getTheUserName());
		addonServicesDTO.setUserName(get_commonCriteria()
				.getTheUserName());
		getCreditCardTokenUrl();
		getCreditCardTokenAPICrndl();
		getCreditCardAuthTokenizeUrl();
		getCreditCardAuthTokenizeXapiKey();
		logger.info("initAddonServices Panel before calling get urls");
		//SL 3678- end
		
		model.setAddonServicesDTO(addonServicesDTO);
	}

	private void initAdditionalPayment(AddonServicesDTO addonServicesDTO, String soID)
	{
		if(addonServicesDTO == null)
			return;
		
		if(StringUtils.isBlank(soID))
			return;
		
		
		AdditionalPaymentVO additionalPayment = null;
		try
		{
			additionalPayment = upsellBO.getAdditionalPaymentInfo(soID);
			if(additionalPayment == null){
				return;
			}
		}
		catch (BusinessServiceException e)
		{
			e.printStackTrace();
			return;
		}
		
		if (additionalPayment != null){
			String paymentType = additionalPayment.getPaymentType();
			if(StringUtils.isNotBlank(paymentType))
			{
				if(paymentType.equals(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK))
					addonServicesDTO.setPaymentRadioSelection(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK);
				else
					addonServicesDTO.setPaymentRadioSelection(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT);
			}
			else
			{
				addonServicesDTO.setPaymentRadioSelection(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK);
			}
		}
		
		
		if(addonServicesDTO.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK))
		{
			addonServicesDTO.setCheckAmount(additionalPayment.getPaymentAmount());
			if(StringUtils.isNumeric(additionalPayment.getCheckNo()))
			{
				addonServicesDTO.setCheckNumber(additionalPayment.getCheckNo());	
			}
			else
			{
				logger.error("Upsell Additional Info - check number is not a valid integer");
			}
			
		}
		else // Credit Card Information
		{
			addonServicesDTO.setEditOrCancel("edit");
			addonServicesDTO.setCreditCardNumber(additionalPayment.getCardNo());
			addonServicesDTO.setCreditCardNumberActual(additionalPayment.getCardNo());
			//variable set to by pass validation and retain credit card no during edit completion.
			addonServicesDTO.setTokenizedMasked(additionalPayment.isTokenizedANdMasked());
			addonServicesDTO.setEnCreditCardNo(additionalPayment.getEncryptedCardNo());
			if(StringUtils.isNotBlank(additionalPayment.getPaymentType()))
			{
				/*SLT-2591 and SLT-2592: Disable Amex
				 if(additionalPayment.getPaymentType().equals(CreditCardConstants.CARD_ID_AMEX_STR))
					addonServicesDTO.setSelectedCreditCardType(CreditCardConstants.CARD_ID_AMEX);
				else*/ if(additionalPayment.getPaymentType().equals(CreditCardConstants.CARD_ID_DISCOVER_STR))
					addonServicesDTO.setSelectedCreditCardType(CreditCardConstants.CARD_ID_DISCOVER);
				else if(additionalPayment.getPaymentType().equals(CreditCardConstants.CARD_ID_MASTERCARD_STR))
					addonServicesDTO.setSelectedCreditCardType(CreditCardConstants.CARD_ID_MASTERCARD);
				
				else if(additionalPayment.getPaymentType().equals(CreditCardConstants.CARD_ID_MASTERCARD_STR))
					addonServicesDTO.setSelectedCreditCardType(CreditCardConstants.CARD_ID_SEARS);				
				else if(additionalPayment.getPaymentType().equals(CreditCardConstants.CARD_ID_SEARS_CHARGE_PLUS_STR))
					addonServicesDTO.setSelectedCreditCardType(CreditCardConstants.CARD_ID_SEARS_PLUS);
				else if(additionalPayment.getPaymentType().equals(CreditCardConstants.CARD_ID_COMMERCIAL_ONE_STR))
					addonServicesDTO.setSelectedCreditCardType(CreditCardConstants.CARD_ID_SEARS_COMMERCIAL);
				
				else if(additionalPayment.getPaymentType().equals(CreditCardConstants.CARD_ID_VISA_STR))
					addonServicesDTO.setSelectedCreditCardType(CreditCardConstants.CARD_ID_VISA);
			}
			addonServicesDTO.setSelectedMonth(additionalPayment.getCardExpireMonth());
			addonServicesDTO.setSelectedYear(additionalPayment.getCardExpireYear());
			addonServicesDTO.setPreAuthNumber(additionalPayment.getAuthNumber());
			addonServicesDTO.setAmtAuthorized(additionalPayment.getPaymentAmount());
		}
	}
	
	
	private void resetAddOnServicesPanel(SOCompleteCloseDTO model)
	{		
		//model = getModel();
		
		// Only want to do all the initialization once
		if(model.getAddonServicesDTO() == null)
		{
			return;
		}

		if(model.getAddonServicesDTO() == null)
		{
			return;
		}
		
		
		String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		List<ServiceOrderAddonVO> addons = upsellBO.getAddonsbySoId(soId);		

		// Convert Raw Data to DTO
		AddonServicesDTO addonServicesDTO = model.getAddonServicesDTO();
		Double providerPaidTotal = 0.0;	
		Double endCustomerChargeTotal = 0.0;
		Double endCustomerSubtotalTotal = 0.0;
		Double sumOfEndCustomerCharges = 0.0;
		Double providerTaxPaidTotal = 0.0;
		int i=0;
		if (addonServicesDTO.getAddonServicesList() != null)
		{
			for (ServiceOrderAddonVO vo : addons)
			{
				Double endCustomerPrice = vo.getRetailPrice();
				Double providerPaid = 0.0;
				Double subtotal = 0.0;
				Double providerTaxPaid = 0.0;

				if(i >= addonServicesDTO.getAddonServicesList().size())
					break;
				
				if ( addonServicesDTO.getAddonServicesList().get(i) != null)
				{
					addonServicesDTO.getAddonServicesList().get(i).setSku(vo.getSku());
					if (vo.isMiscInd() == false)
					{
						endCustomerPrice = vo.getRetailPrice();
						providerPaid = endCustomerPrice - (vo.getMargin() * endCustomerPrice);
						subtotal = endCustomerPrice * addonServicesDTO.getAddonServicesList().get(i).getQuantity();
						providerTaxPaid = subtotal * vo.getTaxPercentage() / (1 + vo.getTaxPercentage());
						sumOfEndCustomerCharges += endCustomerPrice;
						addonServicesDTO.getAddonServicesList().get(i).setDescription(vo.getDescription());
						addonServicesDTO.getAddonServicesList().get(i).setProviderPaid(providerPaid);
						addonServicesDTO.getAddonServicesList().get(i).setEndCustomerCharge(endCustomerPrice);
						addonServicesDTO.getAddonServicesList().get(i).setEndCustomerSubtotal(subtotal);
						addonServicesDTO.getAddonServicesList().get(i).setProviderTax(providerTaxPaid);
						addonServicesDTO.getAddonServicesList().get(i).setMisc(false);
					}
					else
					// Misc SKUs here
					{
						endCustomerPrice = addonServicesDTO.getAddonServicesList().get(i).getEndCustomerCharge();
						providerPaid = endCustomerPrice - (vo.getMargin() * endCustomerPrice);
						sumOfEndCustomerCharges += endCustomerPrice;
						subtotal = endCustomerPrice * addonServicesDTO.getAddonServicesList().get(i).getQuantity();
						providerTaxPaid = subtotal * vo.getTaxPercentage() / (1 + vo.getTaxPercentage());
						addonServicesDTO.getAddonServicesList().get(i).setEndCustomerSubtotal(subtotal);
						addonServicesDTO.getAddonServicesList().get(i).setMisc(true);
					}
				}
				providerPaidTotal += providerPaid;
				providerTaxPaidTotal += providerTaxPaid;
				endCustomerChargeTotal += endCustomerPrice;
				endCustomerSubtotalTotal += subtotal;

				i++;
			}
		}
		addonServicesDTO.setProviderPaidTotal(MoneyUtil.getRoundedMoney(providerPaidTotal));
		addonServicesDTO.setProviderTaxPaidTotal(MoneyUtil.getRoundedMoney(providerTaxPaidTotal));
		addonServicesDTO.setEndCustomerChargeTotal(MoneyUtil.getRoundedMoney(endCustomerChargeTotal));
		addonServicesDTO.setEndCustomerSubtotalTotal(MoneyUtil.getRoundedMoney(endCustomerSubtotalTotal));
		addonServicesDTO.setSumOfEndCustomerChargesMinusMisc(MoneyUtil.getRoundedMoney(sumOfEndCustomerCharges));		
		
		
		// Payment Radio buttons
		HashMap paymentRadio = new HashMap();
		paymentRadio.put("0", "Customer Payment by Check"); //TODO - put this string in properties file
		paymentRadio.put("1", "Customer Payment by Credit Card");//TODO - put this string in properties file
		addonServicesDTO.setPaymentRadio(paymentRadio);
		
				

		
		model.setAddonServicesDTO(addonServicesDTO);
		
		
	}
	
	
	

	// TODO move this to a more accessible location like SLBaseAction
	public static List<DropdownOptionDTO> getCreditCardOptions()
	{
		List<DropdownOptionDTO> options = new ArrayList<DropdownOptionDTO>();
		//SLT-2591 and SLT-2592: Disable Amex
		//options.add(new DropdownOptionDTO("American Express", CreditCardConstants.CARD_ID_AMEX + "", null));
		options.add(new DropdownOptionDTO("Discover", CreditCardConstants.CARD_ID_DISCOVER + "", null));		
		options.add(new DropdownOptionDTO("Visa", CreditCardConstants.CARD_ID_VISA + "", null));
		options.add(new DropdownOptionDTO("Mastercard", CreditCardConstants.CARD_ID_MASTERCARD + "", null));
		options.add(new DropdownOptionDTO("SYW Master Card", CreditCardConstants.CARD_ID_SEARS_MASTERCARD + "", null));

		options.add(new DropdownOptionDTO("Sears", CreditCardConstants.CARD_ID_SEARS + "", null));				
		options.add(new DropdownOptionDTO("Sears Commercial", CreditCardConstants.CARD_ID_SEARS_COMMERCIAL + "", null));		
		options.add(new DropdownOptionDTO("Sears Plus", CreditCardConstants.CARD_ID_SEARS_PLUS + "", null));
		
		return options;
	}	
	
	private void initPartsPanel() throws Exception
	{
		
		model = getModel();
		
		ArrayList<SOPartsDTO> partList = new ArrayList<SOPartsDTO>();
		
		//SL-19820
		//ServiceOrderDTO soDto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO soDto= getCurrentServiceOrderFromRequest();
		
		if (soDto.getPartsList() != null)
		{
			partList = soDto.getPartsList();
			for (int j = 0; j < partList.size(); j++)
			{
				SOPartsDTO part = partList.get(j);
				if (part.getCoreReturnCarrierId() == null)
					part.setCoreReturnCarrierId(-1);
			}
		}		

		
		model.setPartList(partList);
		
		//Get Return Carrier Dropdown list for parts
		setShippingCarrier(getDetailsDelegate().getShippingCarrier());

		int partCount = 0;		
		if (model.getPartList() != null)
			partCount = model.getPartList().size();
		setAttribute("partCount", partCount);
	
		// If prior values have been entered by the user, use those values to init the model.
		/*SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);	
		if( null!=completeCloseDTO && null!=completeCloseDTO.getPartList())
		{
			ArrayList<SOPartsDTO> partListPrePopulated= completeCloseDTO.getPartList();
			if(partList != null && partCount> 0){
				for (int loopIndex=0; loopIndex<partCount;loopIndex++) {
					partList.get(loopIndex).setCoreReturnCarrierId(partListPrePopulated.get(loopIndex).getCoreReturnCarrierId());
					partList.get(loopIndex).setCoreReturnTrackingNumber(partListPrePopulated.get(loopIndex).getCoreReturnTrackingNumber());
				}
			}
			model.setPartList(partList);
		}*/
	}
	
	
	
	public String displayPage() throws Exception {
		//SL-19820
		//SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
		SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO) getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
		List <ProviderResultVO> providerList = null;
		if(completeCloseDTO != null && completeCloseDTO.getAddonServicesDTO() != null)
		{
			model.setAddonServicesDTO(completeCloseDTO.getAddonServicesDTO());
			setModel(model);
			//resetAddOnServicesPanel(model);
			initAddOnServicesPanel();
		}
		else
		{
			initAddOnServicesPanel();
		}
		if ("3000".equals(model.getBuyerID())) {
			 initHSRPartsInvoicePanel();
			 initReasonForPriceDifference();
			//Priority 5B changes
			initModelSerialValidationRules();
		}
		if(Constants.TECH_TALK_BUYER.equals(model.getBuyerID())){
			populateDepositionCodes();
			getSession().setAttribute(Constants.DEPOSITION_CODE_LIST, model.getDepositionCodes());
		}
		if(checkIfAssignedToFirm()){
			providerList = getRoutedProviderListForFirm(model.getEntityId());
		}
		model.setProviderList(providerList);
		initGeneralCompletionInfoPanel();
		initBuyerRefsPanel();		
		initPartsPanel();
		
		initDropdowns();
		setModel(model);
		return SUCCESS;
	}
	
	private void populateDepositionCodes() {
		model.setDepositionCodes(getDetailsDelegate().getAllDepositionCodes());
	}

	//retrieves the routed provider list for a firm 
	private List <ProviderResultVO> getRoutedProviderListForFirm (Integer companyId) throws BusinessServiceException{
		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		List <ProviderResultVO> routedResourcesForFirm = new ArrayList<ProviderResultVO>();
		if(null !=companyId){
		routedResourcesForFirm =  getDetailsDelegate().getRoutedProviderListForFirm(
				soId,companyId.toString());
		}
		List <ProviderResultVO> routedProvExceptCounterOffer = new ArrayList <ProviderResultVO>();
		if(null != routedResourcesForFirm && !routedResourcesForFirm.isEmpty()) {
			for(ProviderResultVO provRes: routedResourcesForFirm) {
				if(!OrderConstants.CONDITIONAL_OFFER.equals(provRes.getProviderRespid())) {
					routedProvExceptCounterOffer.add(provRes);
				}
			}
		}
		return routedProvExceptCounterOffer;
	}

	// This method for setting the reasons for price difference
	private void initReasonForPriceDifference() {
		
		model = getModel();
		
		// Setting from the data base if the from page is from completion record
		//SL-19820
		//SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
        SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO) getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);

			     if(completeCloseDTO!=null && completeCloseDTO.getSoPartLaborPriceReason()!=null
			    		    && completeCloseDTO.getSoPartLaborPriceReason().size()>0){

			    	   for (SOPartLaborPriceReasonVO partLaborPriceReasonVO: completeCloseDTO.getSoPartLaborPriceReason()) {
			        		 
			    		   if(partLaborPriceReasonVO.getReasonCodeId()!= null && !"".equalsIgnoreCase(partLaborPriceReasonVO.getReasonCodeId())){
				    		   if("Parts".equalsIgnoreCase(partLaborPriceReasonVO.getPriceType())){
			        	    		  model.setNonMaxPriceForPartCheck("true");
			        	    		  model.setSelectReasonForParts(partLaborPriceReasonVO.getReasonCodeId());
			            	    	  if(partLaborPriceReasonVO.getReasonComments()!= null 
			            	    			  && !"".equalsIgnoreCase(partLaborPriceReasonVO.getReasonComments()))
			            	    		  model.setOtherReasonTextParts(partLaborPriceReasonVO.getReasonComments());
			        			 }else if("Labor".equalsIgnoreCase(partLaborPriceReasonVO.getPriceType())){
			        					 model.setNonMaxPriceForLaborCheck("true");
			        					 model.setSelectReasonForLabor(partLaborPriceReasonVO.getReasonCodeId());
			            	    	     if(partLaborPriceReasonVO.getReasonComments()!= null 
			            	    	    		 && !"".equalsIgnoreCase(partLaborPriceReasonVO.getReasonComments()))
			            	    		  model.setOtherReasonText(partLaborPriceReasonVO.getReasonComments());
			        			 		}
		        	         }
					}
			    	   
			    	   
			       }else if(completeCloseDTO!=null){
							if(completeCloseDTO.getNonMaxPriceForLaborCheck()!=null){
								model.setNonMaxPriceForLaborCheck(completeCloseDTO.getNonMaxPriceForLaborCheck());
								model.setSelectReasonForLabor(completeCloseDTO.getSelectReasonForLabor());
								if(completeCloseDTO.getOtherReasonText()!=null)
								    model.setOtherReasonText(completeCloseDTO.getOtherReasonText());
							}
							if(completeCloseDTO.getNonMaxPriceForPartCheck()!=null){
								model.setNonMaxPriceForPartCheck(completeCloseDTO.getNonMaxPriceForPartCheck());
							    model.setSelectReasonForParts(completeCloseDTO.getSelectReasonForParts());
							    if(completeCloseDTO.getOtherReasonTextParts()!=null)
							    model.setOtherReasonTextParts(completeCloseDTO.getOtherReasonTextParts());
							}
				  				
	            }	
			     
	}
          
		
	

	private void initDropdowns()
	{
		// Credit Card Authorization dropdowns
		//model = getModel();
		model.getAddonServicesDTO().setMonthOptions(getMonthOptions());
		model.getAddonServicesDTO().setYearOptions(getYearOptions(Calendar.getInstance().get(Calendar.YEAR), 10));
		model.getAddonServicesDTO().setCreditCardOptions(getCreditCardOptions());		
	}


	public SOCompleteCloseDTO getModel() {
		return model;
	}

	
	public String completeSo()throws Exception
	{		
		
		logger.debug("----Start of SODetailsCompleteForPaymentAction.completeSo----");
		//SOCompleteCloseDTO soCompleteDto = (SOCompleteCloseDTO)getRequest().getAttribute("soCompleteDto");
		model = getModel();
		model.setAssignedToFirm(checkIfAssignedToFirm());
		logger.info("checking for deposition code");
		logger.info("selected deposition code: "+model.getSelectedDepositionCode());
		model.setDepositionCodes((List<DepositionCodeDTO>) getSession().getAttribute(Constants.DEPOSITION_CODE_LIST));
		if(model.getSelectedDepositionCode()!=null && !model.getSelectedDepositionCode().equals("-1")){
			getDetailsDelegate().insertOrUpdateDepositionCode(model.getSoId(), model.getSelectedDepositionCode());	
		}	
		if(model.getAddonServicesDTO() != null){
			String paymentType = getRequest().getParameter("pmttype");
			
			String buyerId = null;
			ServiceOrderDTO soDto = getCurrentServiceOrderFromRequest();
			if(soDto != null) {
				buyerId = soDto.getBuyerID();
			}
	
			// Necessary little hack because we did not use ModelDriven to setup this radio button.
			if (null == paymentType && (String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(buyerId) || String.valueOf(
					BuyerConstants.TECH_TALK_BUYER_ID).equals(buyerId))) {
				model.getAddonServicesDTO().setPaymentRadioSelection("CA");
				model.getAddonServicesDTO().setCheckNumber("3333777733337777");
				model.getAddonServicesDTO().setCheckAmount(model.getEndCustomerDueFinalTotal());
				// model.setValidateCC(false);
			} else {
				model.getAddonServicesDTO().setPaymentRadioSelection( paymentType );
			}
		}
		//This value in DTO is set to  validate credit card Number
		if(null!= model.getAddonServicesDTO() && StringUtils.isNotBlank(model.getAddonServicesDTO().getCreditCardNumber())){
			String ccNum = model.getAddonServicesDTO().getCreditCardNumber();
			if(!StringUtils.contains(ccNum, CommonConstants.SPECIAL_CHARACTER)
					&& StringUtils.isBlank(model.getAddonServicesDTO().getEditOrCancel())){
				  model.getAddonServicesDTO().setEditOrCancel("added");
			}
		}
		// SLM-86:: Need to set the already existing invoice parts of the service order in the model.
	    setProviderInvoiceParts();
		
		String strResponse = "";
		try{
			logger.debug("soCompleteDto : " + model.toString());
			
			logger.info("before get_commonCriteria SLComplete action");
			if(null!=get_commonCriteria() && null!=get_commonCriteria().getSecurityContext()){
			 LoginCredentialVO lvRoles = new LoginCredentialVO();
			 //Integer resourceIds = get_commonCriteria().getVendBuyerResId();
			 lvRoles= get_commonCriteria().getSecurityContext().getRoles();
			 logger.info("lvRoles"+lvRoles);
			 
			 if(get_commonCriteria().getSecurityContext().isSlAdminInd()){
				/*	lvRoles.setRoleName(OrderConstants.NEWCO_ADMIN);
					lvRoles.setCompanyId(new Integer(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION)); 
					 
					//If the admin-role id is not set, use the role id 
					if(get_commonCriteria().getSecurityContext().getAdminRoleId() == -1){
						resourceIds = get_commonCriteria().getSecurityContext().getVendBuyerResId();
						lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getRoleId());
					}else{
						resourceIds = get_commonCriteria().getSecurityContext().getAdminResId(); 
						lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getAdminRoleId());
					}
					
					lvRoles.setVendBuyerResId(get_commonCriteria().getSecurityContext().getAdminResId());
				*/}
			 
			if(null!=lvRoles){
				String createdBy=lvRoles.getLastName() + ", " + lvRoles.getFirstName();
				String modifiedBy=lvRoles.getUsername();
				logger.info("createdBy"+createdBy);
				logger.info("modifiedBy"+modifiedBy);
				model.setCreatedBy(createdBy);
				model.setModifiedBy(modifiedBy);
				if(null!=lvRoles.getRoleId()){
				model.setRoleIds(lvRoles.getRoleId().toString());
				}	
			 }
			}
			
			//preprocessPartsPanel(model.getPartList());
			preprocessBuyerRefsPanel(model);
			preprocessGeneralInfoPanel(model);
			//setting user name
			model.setUserName(get_commonCriteria().getTheUserName());
			getRequest().setAttribute(Constants.SESSION.SOD_SO_COMPLETE,"completeSO");
			ProcessResponse prResp = getDetailsDelegate().completeSO(model);
			
			strResponse = getResponseCode(prResp);
			
			
		} catch (BusinessServiceException e) {
			logger.error("business service exception in completing the SO: ", e);
		} catch (Exception e) {
			logger.error("Exception in completing the SO: ", e);
		}
		logger.debug("----End of SODetailsCompleteForPaymentAction.completeSO----");
		
		foo = model.getErrors();
		setAttribute("foo", foo);
		setAttribute("errors", model.getErrors());
		//SL-19820
		setAttribute("modelerrors", getModel().getErrors());
		/*getSession().setAttribute("errors", model.getErrors());
		getSession().setAttribute("modelerrors", getModel().getErrors());*/
		getSession().setAttribute("errors_"+this.soId, model.getErrors());
		getSession().setAttribute("modelerrors_"+this.soId, getModel().getErrors());
		setModel(model);
		return strResponse;
	}
	
	private boolean checkIfAssignedToFirm()throws BusinessServiceException{
		//check if the so is assigned to firm
		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		String  assignmentType = getDetailsDelegate().getAssignmentType(soId);
		//SL-19820
		//getSession().getServletContext().setAttribute(Constants.SESSION.SO_ASSIGNMENT_TYPE, assignmentType);
		setAttribute(Constants.SESSION.SO_ASSIGNMENT_TYPE, assignmentType);
		if(null != assignmentType && assignmentType.equals(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM)){
			return true;
		}
		return false;
	}
	
	private void preprocessGeneralInfoPanel(SOCompleteCloseDTO model)
	{
		if(model == null)
			return;
		
		//SL-19820
		//ServiceOrderDTO soDto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO soDto = getCurrentServiceOrderFromRequest();
		if(soDto == null)
			return;
		
		model.setLaborSpLimit(soDto.getLaborSpendLimit());
		model.setPartSpLimit(soDto.getPartsSpendLimit());
		model.setBuyerID(soDto.getBuyerID());
	}

	private void preprocessBuyerRefsPanel(SOCompleteCloseDTO model)
	{
		if(model == null)
			return;
		
		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		
		List<BuyerReferenceVO> voList = null;
		try
		{
			voList = buyerBo.getProviderReferences(soId);
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
		}
		
		if(voList == null || voList.size() == 0)
			return;
		
		int i=0;
		for(BuyerReferenceVO formBuyerReference : model.getBuyerRefs())
		{
			if(formBuyerReference != null)
			{	
				//Commenting out these line to validate the custom reference values during Edit completion. SL-17508 
				//if(StringUtils.isNotBlank(formBuyerReference.getReferenceValue()))
				//{
					if(voList.get(i) != null)
					{
						voList.get(i).setReferenceValue(formBuyerReference.getReferenceValue());
					}
				//}
			}
			i++;
		}
		
		model.setBuyerRefs(voList);
	}
	
	
	
	private void preprocessPartsPanel(List<SOPartsDTO> partsDto)
	{
		SOPartsDTO soPartDto = null;
		int intPartIdCnt = 0;
		Integer carrierId = 0;
		String trackingNumber = "";
		String strCarrierName = "";
		String strTrackingNoName = "";
				
		// Old way of getting part count
//		String partIdCountParam = getRequest().getParameter("partIdCount");
//		if(partIdCountParam != null)
//			intPartIdCnt = Integer.parseInt( partIdCountParam );
		
		// New way of getting part count
		
		intPartIdCnt = partsDto.size();
		String carrierNameParam;
		if (intPartIdCnt > 0){
			for (int i=0;i<intPartIdCnt;i++){
				strCarrierName = "returnCarrierId" + i + "";
				strTrackingNoName = "returnTrackingNumber" + i + "";
				carrierNameParam = getRequest().getParameter(strCarrierName);
				if(StringUtils.isNumeric(carrierNameParam))
				{
					carrierId = new Integer(Integer.parseInt(carrierNameParam));
					soPartDto.setCoreReturnCarrierId(carrierId);
				}
				trackingNumber = getRequest().getParameter(strTrackingNoName);
				soPartDto = (SOPartsDTO)partsDto.get(i);
				soPartDto.setCoreReturnTrackingNumber(trackingNumber);
			}
		}		
	}
	
	
	private String getResponseCode(ProcessResponse processResponse)
	{
		String defaultSelectedTab = SODetailsUtils.ID_COMPLETE_FOR_PAYMENT;
		String strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
		String strErrorCode = processResponse.getCode();
		String strErrorMessage = processResponse.getMessages().get(0);
		if (strErrorCode.equalsIgnoreCase(USER_ERROR_RC))
		{	
			strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
			defaultSelectedTab = SODetailsUtils.ID_COMPLETE_FOR_PAYMENT;
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO, getModel());
			String soId = (String) getAttribute(OrderConstants.SO_ID);
			getSession().setAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO+"_"+soId, getModel());
			setAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO, getModel());
			
		}else if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
		{	
			//Go to common error page in case of business logic failure error or fatal error
			strResponse = ERROR; 
			//SL-19820
			//this.setReturnURL(SOD_ACTION);
			this.setReturnURL(SOD_ACTION+"?soId="+this.soId);
			this.setErrorMessage(strErrorMessage);
		}
		// Successful process, now handle Addon Services here
		else 
		{
			strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
			//SL-19820
			//setCurrentSOStatusCodeInSession(OrderConstants.COMPLETED_STATUS);
			setCurrentSOStatusCodeInRequest(OrderConstants.COMPLETED_STATUS);
		}
		
		getRequest().setAttribute("soCompleteDto", model);
		//SL-19820
		//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
		getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId, strErrorMessage);
		setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
		logger.debug("strResponse : " + strResponse);
		
		//Redisplay the tabs
	    logger.debug("defaultSelectedTab : " + defaultSelectedTab);
		this.setDefaultTab( defaultSelectedTab );
		
		//SL-19820
		//Refetch the service order from database
		//getSession().setAttribute(REFETCH_SERVICE_ORDER, new Boolean(true));
	
		return strResponse;		
	}

	public ISODetailsDelegate getISODetailDelegate() {
		return ISODetailDelegate;
	}

	public void setISODetailDelegate(ISODetailsDelegate detailDelegate) {
		ISODetailDelegate = detailDelegate;
	}

	public IBuyerBO getBuyerBo() {
		return buyerBo;
	}

	public void setBuyerBo(IBuyerBO buyerBo) {
		this.buyerBo = buyerBo;
	}
	
	private void initBuyerRefsPanel()
	{
		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		  
		try
		{ 
			//SL-19820
			//SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
			SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO) getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
			List<BuyerReferenceVO> buyerRefsType= buyerBo.getProviderReferences(soId);
			if( null!=completeCloseDTO && null!=completeCloseDTO.getBuyerRefs()){
				List<BuyerReferenceVO> buyerRefsValue= completeCloseDTO.getBuyerRefs();
				Integer buyerRefsSize=buyerRefsType.size();
				if(buyerRefsType != null && buyerRefsSize> 0 && buyerRefsValue!=null && buyerRefsValue.size()>0){
					for (int loopIndex=0; loopIndex<buyerRefsSize;loopIndex++) {
						buyerRefsType.get(loopIndex).setReferenceValue(buyerRefsValue.get(loopIndex).getReferenceValue());
					}
				}
			}
			model.setBuyerRefs(buyerRefsType);
		}
		catch (DataServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private void initGeneralCompletionInfoPanel()
	{
		//SL-19820
		//ServiceOrderDTO soDTO = getCurrentServiceOrderFromSession();
		ServiceOrderDTO soDTO = getCurrentServiceOrderFromRequest();
		if(soDTO == null)
			return;
		
		model = getModel();
		if (soDTO.isTaskLevelPriceInd()) {
			Double maxLabor = soDTO.getSoTaskMaxLabor();
			Double finalMaxLabor = soDTO.getSoFinalMaxLabor();
			DecimalFormat twoDForm = new DecimalFormat("#.##");

			if ((String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(soDTO.getBuyerID()) || String.valueOf(
					BuyerConstants.TECH_TALK_BUYER_ID).equals(soDTO.getBuyerID()))) {
				if (null != soDTO.getLaborSpendLimit()) {
					maxLabor = (soDTO.getLaborSpendLimit() - soDTO.getPrePaidPermitPrice()) / (1 + (null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent()
							.doubleValue() : 0.00));
				}

				if (null != soDTO.getFinalLaborPrice()) {
					finalMaxLabor = (soDTO.getFinalLaborPrice() - soDTO.getFinalPermitPrice()) / (1 + (null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent()
							.doubleValue() : 0.00));
				}
			}

			maxLabor = Double.valueOf(twoDForm.format(maxLabor.doubleValue()));
			finalMaxLabor = Double.valueOf(twoDForm.format(finalMaxLabor.doubleValue()));
			model.setSoMaxLabor(maxLabor.toString());
			model.setSoFinalMaxLabor(finalMaxLabor);
			model.setSoInitialMaxLabor(maxLabor.toString());
			model.setPermitTaskList(soDTO.getPermitTaskList());

			model.setTaskLevelPricing(soDTO.isTaskLevelPriceInd());
			if (soDTO.getPrePaidPermitPrice() != null) {
				model.setPrepaidPermitPrice(soDTO.getPrePaidPermitPrice().doubleValue());
			} else {
				soDTO.setPrePaidPermitPrice(0.0);
			}
		} else {

			Double maxLabor = null;
			Double finalMaxLabor = null;

			if ((String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(soDTO.getBuyerID()) || String.valueOf(
					BuyerConstants.TECH_TALK_BUYER_ID).equals(soDTO.getBuyerID()))
					&& null != soDTO.getLaborSpendLimit()) {
				maxLabor = (soDTO.getLaborSpendLimit() / (1 + (null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent()
						.doubleValue() : 0.00)));
				finalMaxLabor = (soDTO.getFinalLaborPrice() / (1 + (null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent()
						.doubleValue() : 0.00)));
			} else {
				maxLabor = soDTO.getLaborSpendLimit();
				finalMaxLabor = soDTO.getFinalLaborPrice();
			}

			model.setSoMaxLabor(maxLabor.toString());
			model.setSoFinalMaxLabor(finalMaxLabor);
			model.setSoInitialMaxLabor(maxLabor.toString());
		}
		double providerAddonPrice=0.0;
		double customerAddonPrice=0.0;
		double providerTaxPaid=0.0;
		model.setPermitTaskAddonPrice(soDTO.getPermitTaskAddonPrice());
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		if(model.getAddonServicesDTO()!=null && model.getAddonServicesDTO().getProviderPaidTotal()!=null){
			providerAddonPrice = model.getAddonServicesDTO().getProviderPaidTotal()-model.getPermitTaskAddonPrice();
			if(providerAddonPrice<=0){
				providerAddonPrice =0.0;
			}
			providerAddonPrice = Double.valueOf(twoDForm.format(providerAddonPrice)).doubleValue();
			
		}
		if(model.getAddonServicesDTO()!=null && model.getAddonServicesDTO().getEndCustomerSubtotalTotal()!=null){
			customerAddonPrice = model.getAddonServicesDTO().getEndCustomerSubtotalTotal()-model.getPermitTaskAddonPrice();
			if(customerAddonPrice<=0){
				customerAddonPrice =0.0;
			}
			customerAddonPrice = Double.valueOf(twoDForm.format(customerAddonPrice)).doubleValue();
			
		}

		if(model.getAddonServicesDTO()!=null && model.getAddonServicesDTO().getProviderTaxPaidTotal()!=null){
			providerTaxPaid = model.getAddonServicesDTO().getProviderTaxPaidTotal();
			if(providerTaxPaid<=0){
				providerTaxPaid =0.0;
			}
			providerTaxPaid = Double.valueOf(twoDForm.format(providerTaxPaid)).doubleValue();
			
		}
		model.setAddonProviderPrice(providerAddonPrice);
		model.setAddonCustomerPrice(customerAddonPrice);
		model.setProviderTaxPaidTotal(providerTaxPaid);
		
		model.setTaskLevelPricing(soDTO.isTaskLevelPriceInd());
		model.setResComments(soDTO.getResolutionComment());

		model.setFinalLaborPrice(soDTO.getLaborSpendLimit().toString());
		model.setFinalLaborTaxPrice("0.00");
		model.setFinalPartsTaxPrice("0.00");
		model.setFinalLaborPriceNew(soDTO.getFinalLaborPriceNew().toString());
		model.setFinalPartsPriceNew(soDTO.getFinalPartsPriceNew().toString());

		if (String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(soDTO.getBuyerID())
				|| String.valueOf(BuyerConstants.TECH_TALK_BUYER_ID).equals(soDTO.getBuyerID())) {
			model.setLaborTaxPercentage(null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent().doubleValue() : 0.00);
			model.setPartsTaxPercentage(null != soDTO.getPartsTaxPercent() ? soDTO.getPartsTaxPercent().doubleValue() : 0.00);

			model.setLaborSpLimit(soDTO.getLaborSpendLimit() / (1 + model.getLaborTaxPercentage()));
			model.setPartSpLimit(soDTO.getPartsSpendLimit() / (1 + model.getPartsTaxPercentage()));

			model.setLaborSpLimitTax(soDTO.getLaborSpendLimit() - model.getLaborSpLimit());
			model.setPartsSpLimitTax(soDTO.getPartsSpendLimit() - model.getPartSpLimit());
		} else {
			model.setLaborTaxPercentage(0.0);
			model.setPartsTaxPercentage(0.0);

			model.setLaborSpLimitTax(0.0);
			model.setPartsSpLimitTax(0.0);
			
			model.setLaborSpLimit(soDTO.getLaborSpendLimit());
			model.setPartSpLimit(soDTO.getPartsSpendLimit());
		}
		if(null != soDTO.getPartLaborPriceReason() && !soDTO.getPartLaborPriceReason().isEmpty()){
		for (SOPartLaborPriceReasonVO partLaborPriceReasonVO: soDTO.getPartLaborPriceReason()) {
			if(partLaborPriceReasonVO!=null){
		  		   if(partLaborPriceReasonVO.getReasonCodeId()!= null && !"".equalsIgnoreCase(partLaborPriceReasonVO.getReasonCodeId())){
		  			if("Labor".equalsIgnoreCase(partLaborPriceReasonVO.getPriceType())){
		  			   model.setSelectReasonForLabor(partLaborPriceReasonVO.getReasonCodeId());
      	    	     if(partLaborPriceReasonVO.getReasonComments()!= null 
      	    	    		 && !"".equalsIgnoreCase(partLaborPriceReasonVO.getReasonComments()))
      	    		  model.setOtherReasonText(partLaborPriceReasonVO.getReasonComments());
  			 		}
		  		   }
				}

 	         }
		
		}		
	/*	if ("3000".equals(model.getBuyerID())) {
			model.setFinalPartPrice(soDTO.getPartsSpendLimit().toString());			
		}*/
		
		// If prior values have been entered by the user, use those values to init the model.
		//Sl-19820
		//SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);	
		SOCompleteCloseDTO completeCloseDTO = (SOCompleteCloseDTO) getAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
		if(completeCloseDTO != null)
		{
			if(StringUtils.isNotBlank(completeCloseDTO.getResComments())){
				model.setResComments(completeCloseDTO.getResComments() + "");
			}else{
				model.setResComments(soDTO.getResolutionComment());
			}
			
			if(completeCloseDTO.getFinalPartPrice()!=null)
				model.setFinalPartPrice(completeCloseDTO.getFinalPartPrice() + "");
				model.setFinalPartsTaxPrice(completeCloseDTO.getFinalPartsTaxPrice() + "");
			if(StringUtils.isNotBlank(completeCloseDTO.getFinalLaborPrice()) && SLStringUtils.IsParsableNumber(completeCloseDTO.getFinalLaborPrice()))
				model.setFinalLaborPrice(completeCloseDTO.getFinalLaborPrice() + "");
				model.setFinalLaborTaxPrice(completeCloseDTO.getFinalLaborTaxPrice() + "");
			if(completeCloseDTO.getSoMaxLabor()!=null)
				model.setSoMaxLabor(completeCloseDTO.getSoMaxLabor() + "");
			if(StringUtils.isNotBlank(completeCloseDTO.getSoInitialMaxLabor()) && SLStringUtils.IsParsableNumber(completeCloseDTO.getSoInitialMaxLabor()))
				model.setSoInitialMaxLabor(completeCloseDTO.getSoInitialMaxLabor());
				model.setLaborSpLimitTax(Double.parseDouble(new DecimalFormat("##.##").format(Double.parseDouble(completeCloseDTO.getSoInitialMaxLabor()) *  model.getLaborTaxPercentage())));
			if(completeCloseDTO.getPermitTaskAddonPrice()!=0.0)
				model.setPermitTaskAddonPrice(completeCloseDTO.getPermitTaskAddonPrice());
			if(completeCloseDTO.getEndCustomerDueFinalTotal()!=0.0)
				model.setEndCustomerDueFinalTotal(completeCloseDTO.getEndCustomerDueFinalTotal());
			if(completeCloseDTO.getPermitTaskList()!=null && completeCloseDTO.getPermitTaskList().size()!=0){
				model.setPermitTaskList(completeCloseDTO.getPermitTaskList());
			}
			if (completeCloseDTO.getProviderTaxPaidTotal() != 0.0 ) {
				model.setProviderTaxPaidTotal(completeCloseDTO.getProviderTaxPaidTotal());
			}
			if(completeCloseDTO.getAddonServicesDTO()!=null && completeCloseDTO.getAddonServicesDTO().getCheckNumber()!=null){
				model.getAddonServicesDTO().setCheckNumber(completeCloseDTO.getAddonServicesDTO().getCheckNumber());
			}
			if(completeCloseDTO.getAddonServicesDTO()!=null && completeCloseDTO.getAddonServicesDTO().getCheckAmount()!=null){
				model.getAddonServicesDTO().setCheckAmount(completeCloseDTO.getAddonServicesDTO().getCheckAmount());
			}
			model.setFinalWithoutAddons(completeCloseDTO.getFinalWithoutAddons());
			model.setCustChargeWithoutAddons(completeCloseDTO.getCustChargeWithoutAddons());
			model.setCustChargeFinal(completeCloseDTO.getCustChargeFinal());
			model.setFinalTotal(completeCloseDTO.getFinalTotal());
			if(completeCloseDTO.getPermitWarningStatusInd()!=null){
			model.setPermitWarningStatusInd(completeCloseDTO.getPermitWarningStatusInd());
		}
		}
		String editCompletion =(String)getRequest().getAttribute(OrderConstants.EDIT_COMPLETION);
		double finalPrice = Double.valueOf(twoDForm.format(soDTO.getFinalLaborPrice()+soDTO.getFinalPartsPrice())).doubleValue();
		double permitTaskAddonPrice= Double.valueOf(twoDForm.format(soDTO.getPermitTaskAddonPrice())).doubleValue();
		double providerPrice = Double.valueOf(twoDForm.format(model.getAddonProviderPrice()+finalPrice)).doubleValue();
		double custPrice =  Double.valueOf(twoDForm.format(model.getAddonCustomerPrice()+permitTaskAddonPrice)).doubleValue();
		double maxLabor = 0.0;
		
		if (String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(soDTO.getBuyerID())
				|| String.valueOf(BuyerConstants.TECH_TALK_BUYER_ID).equals(soDTO.getBuyerID()) && null != soDTO.getLaborSpendLimit()) {
			maxLabor = (soDTO.getLaborSpendLimit() / (1 + (null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent().doubleValue()
					: 0.00)));
		} else if (null != soDTO.getSoFinalMaxLabor()) {

			maxLabor = Double.valueOf(twoDForm.format(soDTO.getSoFinalMaxLabor())).doubleValue();
		}
		//if(null!=editCompletion && editCompletion.equalsIgnoreCase(OrderConstants.EDIT_COMPLETION)){
			// model.setFinalWithoutAddons("$"+((Double)finalPrice).toString());
			model.setFinalWithoutAddons("$"+String.format("%.2f", finalPrice));
			// model.setCustChargeWithoutAddons("$"+((Double)permitTaskAddonPrice).toString());
			model.setCustChargeWithoutAddons("$"+String.format("%.2f", permitTaskAddonPrice));
			// model.setFinalTotal("$"+((Double)providerPrice).toString());
			model.setFinalTotal("$"+String.format("%.2f", providerPrice));
			//model.setCustChargeFinal("$"+((Double)custPrice).toString());
			model.setCustChargeFinal("$"+String.format("%.2f", custPrice));
			model.setSoMaxLabor(((Double)maxLabor).toString());
			if(model.getEndCustomerDueFinalTotal()<=0.00 && custPrice>0.00){
				model.setEndCustomerDueFinalTotal(custPrice);
			}
		//}
	}
	
	
	private void newPrepare() throws Exception
	{
		String fromPage = "";
		//SL-19820 
		/*fromPage = getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETION_RECORD) != null
		  ?getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETION_RECORD).toString():"";*/
	
		fromPage = getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETION_RECORD+"_"+this.soId) != null
		  ?getSession().getAttribute(Constants.SESSION.SOD_SO_COMPLETION_RECORD+"_"+this.soId).toString():"";
		
		if (fromPage.equalsIgnoreCase("completionrecord"))
		{			
			getRequest().setAttribute(OrderConstants.EDIT_COMPLETION,OrderConstants.EDIT_COMPLETION); 
			getSession().removeAttribute(Constants.SESSION.SOD_SO_COMPLETION_RECORD+"_"+this.soId); 
		}
	}
	
	private void getEinData(boolean isW9ExistsFlag,Integer companyId) throws Exception{
		
		//SL-19820
		/*getSession().setAttribute("w9isExist", "false");
		getSession().setAttribute("w9isExistWithSSNInd", "false");*/
		setAttribute("w9isExist", "false");
		setAttribute("w9isExistWithSSNInd", "false");
		if (isW9ExistsFlag) {
			//getSession().setAttribute("w9isExist", "true");
			setAttribute("w9isExist", "true");
			if (w9RegistrationBO.isAvailableWithSSNInd(companyId))
			{
				//getSession().setAttribute("w9isExistWithSSNInd", "true");
				setAttribute("w9isExistWithSSNInd", "true");
			}
		}
	}
	
	public void getCompleteSOAmount(boolean isW9ExistsFlag,Integer companyId)throws Exception
	{
		//SL-19820
		/*getSession().setAttribute("w9isExist", "false");
		getSession().setAttribute("completeSOAmount", "0.0");*/
		setAttribute("w9isExist", "false");
		setAttribute("completeSOAmount", "0.0");

		if (isW9ExistsFlag) {
			//SL-19820
			//getSession().setAttribute("w9isExist", "true");
			setAttribute("w9isExist", "true");
			Double amount = w9RegistrationBO.getCompleteSOAmount(companyId);
	
			if (amount!=null)
			{
				//SL-19820
				//getSession().setAttribute("completeSOAmount", amount);
				setAttribute("completeSOAmount", amount);
			}			
		}		
	}
	
	public void getProviderThreshold(boolean isW9ExistsFlag,Integer companyId)throws Exception
	{
		//SL-19820
		//getSession().setAttribute("w9isExist", "false");
		setAttribute("w9isExist", "false");
		
		if (isW9ExistsFlag) {
			//getSession().setAttribute("w9isExist", "true");
			setAttribute("w9isExist", "true");
			Double threshold = w9RegistrationBO.getProviderThreshold();	
			if(threshold!=null)
			{
				//SL-19820
				//getSession().setAttribute("providerThreshold", threshold);
				setAttribute("providerThreshold", threshold);
			}
			else
			{
				//getSession().setAttribute("providerThreshold", "");
				setAttribute("providerThreshold", "");
			}
				
		}		
	}
	
	private void isDobNotAvailableWithSSN(boolean isW9ExistsFlag,Integer companyId) throws Exception{
		
		//SL-19820
		/*getSession().setAttribute("w9isExist", "false");
		getSession().setAttribute("w9isDobNotAvailable", "false");*/
		setAttribute("w9isExist", "false");
		setAttribute("w9isDobNotAvailable", "false");
		if (isW9ExistsFlag) {
			//SL-19820
			//getSession().setAttribute("w9isExist", "true");
			setAttribute("w9isExist", "true");
			if (w9RegistrationBO.isDobNotAvailableWithSSN(companyId))
			{
				//SL-19820
				//getSession().setAttribute("w9isDobNotAvailable", "true");
				setAttribute("w9isDobNotAvailable", "true");
			}
		}
	}
	
	 /**Priority 5B changes
	 * get the validation rules for validating
	 * model number & serial number
	 */
	 private void initModelSerialValidationRules(){
		 	
		 try{
			 
			 List<String> fields = new ArrayList<String>();
			 fields.add(InHomeNPSConstants.MODEL);
			 fields.add(InHomeNPSConstants.SERIAL_NUMBER);
			 
			 List<ValidationRulesVO> validationRules = getDetailsDelegate().getValidationRules(fields);
			 if(null != validationRules && !validationRules.isEmpty()){
				 
				 List<ValidationRulesVO> modelRules = new ArrayList<ValidationRulesVO>();
				 List<ValidationRulesVO> seriallRules = new ArrayList<ValidationRulesVO>();
				 
				 for(ValidationRulesVO rule : validationRules){
					 if(null != rule){
						 if(InHomeNPSConstants.MODEL.equalsIgnoreCase(rule.getField())){
							 modelRules.add(rule);
						 }
						 else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(rule.getField())){
							 seriallRules.add(rule);
						 }
					 }
				 }
				 
				 getRequest().setAttribute(Constants.MODEL_RULE, modelRules);
				 getRequest().setAttribute(Constants.SERIAL_RULE, seriallRules);
			 }
			 
		 }catch(Exception e){
			 logger.error("Exception in initModelSerialValidationRules() : "+ e);
		 }
		 
	 }
	
	public ArrayList<LookupVO> getShippingCarrier()
	{
		return shippingCarrier;
	}

	public void setShippingCarrier(ArrayList<LookupVO> shippingCarrier)
	{
		this.shippingCarrier = shippingCarrier;
	}

	public IServiceOrderUpsellBO getUpsellBO()
	{
		return upsellBO;
	}

	public void setUpsellBO(IServiceOrderUpsellBO upsellBO)
	{
		this.upsellBO = upsellBO;
	}

	public void setModel(SOCompleteCloseDTO model)
	{
		this.model = model;
	}

	public List<IError> getFoo()
	{
		return foo;
	}

	public void setFoo(List<IError> foo)
	{
		this.foo = foo;
	}

	public IW9RegistrationBO getW9RegistrationBO() {
		return w9RegistrationBO;
	}

	public void setW9RegistrationBO(IW9RegistrationBO registrationBO) {
		w9RegistrationBO = registrationBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}
	public String setToken() {
		
		//ServletActionContext.getContext().getApplication().put("authToken", model.getAddonServicesDTO().getAuthToken());
		//ServletActionContext.getContext().getApplication().put("tokenLife", model.getAddonServicesDTO().getTokenLife());
		
		// Enable above code once we set the token in application scope
		getSession().setAttribute("authToken", model.getAddonServicesDTO().getAuthToken());
		getSession().setAttribute("tokenLife", model.getAddonServicesDTO().getTokenLife());
		return "tokenResponse";
	}
	
}
