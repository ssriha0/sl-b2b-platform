package com.newco.marketplace.web.action.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.constants.Constants.AppPropConstants;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;

import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.MoneyUtil;

import com.newco.marketplace.utils.DateUtils;

import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.AddonServicesDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;
import com.sears.os.service.ServiceConstants;

/**
 * $Revision: 1.39 $ $Author: glacy $ $Date: 2008/04/26 01:13:54 $
 */

/*
 * Maintenance History
 * $Log: SLDetailsBaseAction.java,v $
 * Revision 1.39  2008/04/26 01:13:54  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.37.6.1  2008/04/23 11:41:50  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.38  2008/04/23 05:19:28  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.37  2008/02/26 18:18:12  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.36.10.1  2008/02/25 23:15:19  iullah2
 * distance calculation on SOM,SOD
 *
 * Revision 1.36  2008/01/25 00:16:40  mhaye05
 * now pulling role_id from common criteria not session
 *
 * Revision 1.35  2008/01/18 19:25:14  mhaye05
 * roleId is now pulled from the _commonCriteria not the session attribute
 *
 * Revision 1.34  2007/12/04 14:49:44  pbhinga
 * Changes done to populate correct information in Quick Links section in SOD
 *
 * Revision 1.33  2007/11/21 15:36:05  dmill03
 * *** empty log message ***
 *
 * Revision 1.32  2007/11/14 21:58:53  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public abstract class SLDetailsBaseAction extends SLBaseAction implements ServiceConstants{

	
	private static Logger logger = Logger.getLogger(SLDetailsBaseAction.class);
	private String errorMessage = "none";
	private String returnURL;
	private String defaultTab;
	private String successMessage;
	
	private   boolean soIsLoaded = false; 
	private ServiceOrderDTO theCurrentSO = null;
	

	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}

	
	
	protected void _setTabInfo(String detailsRole, Integer detailsStatus, String defaultSelectedTab, String priceModel,String estimationId,String isEstimationRequest) throws BusinessServiceException
	{
		Map sessionAttributes = ActionContext.getContext().getSession();
		
		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
		Boolean viewOrderPricing=false;
		if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
		{
			if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
			{
				viewOrderPricing=true;
			}
		}
		//SL 15642 End-Changes for permission based price display in Service Order Monitor
		if(detailsRole != null && detailsStatus != null && defaultSelectedTab == null)
		{
			boolean isAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();
			//Commenting code SL-19820
			//sessionAttributes.put(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, SODetailsUtils.ID_SUMMARY, priceModel, isAdmin,viewOrderPricing));
			setAttribute(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, SODetailsUtils.ID_SUMMARY, priceModel, isAdmin,viewOrderPricing,estimationId,isEstimationRequest));
		}
		else if(detailsRole != null && detailsStatus != null && defaultSelectedTab != null)
		{
			//Commenting code SL-19820
			//sessionAttributes.put(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, defaultSelectedTab, priceModel, false,viewOrderPricing));
			setAttribute(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, defaultSelectedTab, priceModel, false,viewOrderPricing,estimationId,isEstimationRequest));
		}
		else
		{
			throw new BusinessServiceException("Please check the role, status, and default status");
		}
	}

	protected void _setTabInfo(String detailsRole, Integer detailsStatus, String defaultSelectedTab, HashMap hm, String priceModel,String estimateId,String isEstimateRequest) throws BusinessServiceException
	{
		Map sessionAttributes = ActionContext.getContext().getSession();
		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
		Boolean viewOrderPricing=false;
		if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
		{
			if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
			{
				viewOrderPricing=true;
			}
		}
		//SL 15642 End-Changes for permission based price display in Service Order Monitor
		
		if(detailsRole != null && detailsStatus != null && defaultSelectedTab == null)
		{	
			//Commenting code SL-19820
			//sessionAttributes.put(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, SODetailsUtils.ID_SUMMARY, hm, priceModel,viewOrderPricing));
			setAttribute(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, SODetailsUtils.ID_SUMMARY, hm, priceModel,viewOrderPricing,estimateId,isEstimateRequest));
		}
		else if(detailsRole != null && detailsStatus != null && defaultSelectedTab != null)
		{
			//Commenting code SL-19820
			//sessionAttributes.put(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, defaultSelectedTab, hm, priceModel,viewOrderPricing));
			setAttribute(TAB_ATTRIBUTE, SODetailsUtils.setTabs(detailsRole, detailsStatus, defaultSelectedTab, hm, priceModel,viewOrderPricing,estimateId,isEstimateRequest));
		}
		else
		{
			throw new BusinessServiceException("Please check the role, status, and default status");
		}
	}
	
	protected void setOrderGroupTabs(String groupId, Integer status, String roleType, String defaultTab)
	{		
		//Commenting code Sl-19820
		//getSession().setAttribute(TAB_ATTRIBUTE, SODetailsUtils.setOrderGroupTabs(groupId, status, roleType, defaultTab));
		setAttribute(TAB_ATTRIBUTE, SODetailsUtils.setOrderGroupTabs(groupId, status, roleType, defaultTab));
	}

	//clean up any necessary session attributes
	protected void clearSessionAttributes()
	{
		Map sessionAttributes = ActionContext.getContext().getSession();
		sessionAttributes.remove("tabList");
	}
	

	protected void _loadServiceOrder( String soId ) throws BusinessServiceException 
	{
		long start = System.currentTimeMillis();
		// Not a group order, clear session variable
		//Commenting code as part of SL-19820
		/*getSession().setAttribute(OrderConstants.GROUP_ID, null);
		getSession().setAttribute(THE_GROUP_ORDER, null);*/
		setAttribute(OrderConstants.GROUP_ID, null);
		setAttribute(THE_GROUP_ORDER, null);
		
		//this code may not be needed anymore as we are not loading the SO in session
		/*if(isSoIsLoaded())
		{
			if(getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE) != null &&
			   getSession().getAttribute(THE_SERVICE_ORDER) != null	)
			{
				ServiceOrderDTO theCurrentOrder
					= getCurrentServiceOrderFromSession();
				Integer theCurrentStatus 
					= getCurrentServiceOrderStatusFromSession();
				if(theCurrentOrder.getStatus() != null &&
					theCurrentStatus != null)
				{
					if(!theCurrentOrder.getStatus().equals(theCurrentStatus))
					{
						_refetchServiceOrderFromDB(true, null);
					}
				}
				
			}
			return;
		}
		Object dStatus = getSession().getAttribute(REFETCH_SERVICE_ORDER);
		if(dStatus != null)
		{
			boolean doRefetch = ((Boolean)dStatus).booleanValue();
			if(doRefetch)
			{
				//_refetchServiceOrderFromDB(true, null);
				//getSession().setAttribute(REFETCH_SERVICE_ORDER,new Boolean(false));
				doSORefetch(true);
				return;
			}
		}*/
		try 
		{	
			//Commenting code as part of SL-19820
			//String sodRoutedResourceId = (String) getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID);
			String sodRoutedResourceId = (String) getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID);
			if(StringUtils.isNotBlank(sodRoutedResourceId)) {
				Integer resId = Integer.parseInt(sodRoutedResourceId);
				theCurrentSO = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), resId);
			}
			else
				theCurrentSO = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
						
			/*if(theCurrentSO != null)
			{
				setSoIsLoaded(true);
				getSession().setAttribute(REFETCH_SERVICE_ORDER, new Boolean(false));
			}*/
		} catch (DataServiceException e) {
			//setSoIsLoaded(false);
			throw new BusinessServiceException("Data Access exception");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException("fatal Data Access exception");
		}
		if(theCurrentSO != null)
		{
			//Commenting code as part of SL-19820
			//getSession().setAttribute(THE_SERVICE_ORDER, theCurrentSO);
			setAttribute(THE_SERVICE_ORDER, theCurrentSO);
		}
		else
		{
			//Commenting code as part of SL-19820
			//getSession().setAttribute(THE_SERVICE_ORDER, null);
		}
		if(theCurrentSO != null && theCurrentSO.getStatus() != null)
		{
			//Commenting code as part of SL-19820
			//setCurrentSOStatusCodeInSession(theCurrentSO.getStatus());
			setCurrentSOStatusCodeInRequest(theCurrentSO.getStatus());
		}
		else
		{
			//Commenting code as part of SL-19820
			//setCurrentSOStatusCodeInSession(null);
			setCurrentSOStatusCodeInRequest(null);
		}
		if(soId != null)
		{
			//Commenting code as part of SL-19820
			//getSession().setAttribute(OrderConstants.SO_ID, soId);
			setAttribute(OrderConstants.SO_ID, soId);
		}
		else
		{
			//Commenting code as part of SL-19820
			//getSession().setAttribute(OrderConstants.SO_ID, null);
			setAttribute(OrderConstants.SO_ID, null);
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
		    logger.info("Time Taken to complete _loadServiceOrder:>>>>>"+(end-start));
		}
	}
	
	protected ServiceOrderDTO getCurrentServiceOrderFromSession(){
		return (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
	}
	
	protected ServiceOrderDTO getCurrentServiceOrderFromRequest(){
		return (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);
	}
	
	protected Integer getCurrentServiceOrderStatusFromSession() {
		return (Integer)getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE);
	}
	
	protected String getCurrentServiceOrderId() {
		return (String)getSession().getAttribute(OrderConstants.SO_ID);
	}
	
	protected void doSORefetch(boolean dofetch) throws BusinessServiceException
	{
		
	 if(dofetch)
	 {
		//Changing code SL-19820
		/*Object dStatus = getSession().getAttribute(REFETCH_SERVICE_ORDER);
		String soId = getCurrentServiceOrderId();*/
		
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		if(/*dStatus != null*/true)
		{
			//boolean doRefetch = ((Boolean)dStatus).booleanValue();
			if(/*doRefetch*/true)
			{
				//_refetchServiceOrderFromDB(true, null);
				try {
					theCurrentSO = getDetailsDelegate().getServiceOrder(soId, _commonCriteria.getRoleId(), null);
					/*setCurrentSOStatusCodeInSession(theCurrentSO.getStatus());
					getSession().setAttribute(THE_SERVICE_ORDER, theCurrentSO);
					getSession().setAttribute(REFETCH_SERVICE_ORDER, new Boolean(false));*/
					setCurrentSOStatusCodeInRequest(theCurrentSO.getStatus());
					setAttribute(THE_SERVICE_ORDER, theCurrentSO);
					
				} catch (DataServiceException e) {
					e.printStackTrace();
				}
				
			}
		}
	 }
	}
	
	
	protected ServiceOrderDTO _refetchServiceOrderFromDB(boolean reUseSessionSO, String soId) throws BusinessServiceException
	{
			if(reUseSessionSO || (soId == null && reUseSessionSO))
			{
				setSoIsLoaded(false);
			}
			else if(soId != null && reUseSessionSO)
			{
				// IGNORE THE SO STORED IN SESSION
				setSoIsLoaded(false);
				//_loadServiceOrder(soId);
			}
			else if(soId != null && !reUseSessionSO)
			{
				setSoIsLoaded(false);
				//_loadServiceOrder(soId);
			}
			else
			{
				throw new BusinessServiceException("Service Order Id null");
			}
			return getCurrentServiceOrderFromSession();
	}

	
	protected String getProcessResponseString(ProcessResponse prResp){
		String strErrorCode = prResp.getCode();
		String strErrorMessage = prResp.getMessages().get(0);
		String strResponse = "";
		if (strErrorCode.equalsIgnoreCase(USER_ERROR_RC) || strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
		{	
			strResponse = strErrorMessage;			
			
		}else 
		{	
			strResponse = NOTE_ADD_SUCCESS;
		}
		return strResponse;
		
		
	}
	

	public void getUpsellInfo(ServiceOrderDTO dto) {
		// Fetch the Addons related to ServiceOrder
		List<ServiceOrderAddonVO> addons = getDetailsDelegate().getUpsellBO().getAddonsbySoId(dto.getId());
		// Convert Raw Data to DTO
		AddonServicesDTO addonServicesDTO = new AddonServicesDTO();
		Double providerPaidTotal = 0.0;	
		Double providerTaxPaidTotal = 0.0;
		Double endCustomerChargeTotal = 0.0;
		Double endCustomerSubtotalTotal = 0.0;
		Integer intBuyerId = dto.getBuyerID()!= null ? Integer.valueOf(dto.getBuyerID()) : -1;
		
		for(ServiceOrderAddonVO vo : addons)
		{
			Double endCustomerPrice = vo.getRetailPrice();
			//Double providerPaid = vo.getRetailPrice() - MoneyUtil.getRoundedMoney((vo.getMargin() * vo.getRetailPrice()));
			//Rounding up the provider payment if the third digit after decimal is 5
			//Double providerPaid = MoneyUtil.getRoundedMoney(vo.getQuantity() * (1 - vo.getMargin()) * vo.getRetailPrice());
			//Double providerPaid = MoneyUtil.getRoundedMoney(vo.getQuantity() * (vo.getRetailPrice() - (vo.getRetailPrice() * vo.getMargin())));
			int quantity=vo.getQuantity();
			Double providerPaid=0.0;
			Double providerTaxPaid = 0.0;
			if(quantity > 0){
				providerPaid = MoneyUtil.getRoundedMoney(vo.getQuantity() * (vo.getRetailPrice() - (vo.getRetailPrice() * vo.getMargin())));
			}else{
				providerPaid = MoneyUtil.getRoundedMoney((vo.getRetailPrice() - (vo.getRetailPrice() * vo.getMargin())));
			}
			
			boolean skipReqAddon = false;
			if( (OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT.equals(vo.getCoverage()) &&
					(intBuyerId.intValue() == BuyerConstants.HSR_BUYER_ID) ) ){
						skipReqAddon = true;
			}
			
			//providerPaid = MoneyUtil.getRoundedMoney(providerPaid);
			Double subtotal = MoneyUtil.getRoundedMoney(endCustomerPrice * vo.getQuantity());
			if(vo.getQuantity() > 0){
				endCustomerPrice = subtotal;
				//providerPaid = MoneyUtil.getRoundedMoney(providerPaid * vo.getQuantity());
				endCustomerChargeTotal += endCustomerPrice;
				endCustomerSubtotalTotal += subtotal;
				providerTaxPaid = MoneyUtil.getRoundedMoney(endCustomerPrice * vo.getTaxPercentage() / (1 + vo.getTaxPercentage()));
				providerTaxPaidTotal += providerTaxPaid;
				if(!skipReqAddon){
					providerPaidTotal += providerPaid;
				}
			}
			
			addonServicesDTO.addAddonService(vo.getAddonId(), vo.getSku(), vo.getDescription(), vo.getQuantity(), providerPaid, endCustomerPrice, subtotal, vo.isMiscInd(), vo.getMargin(), vo.getCoverage(), skipReqAddon, vo.isAutoGenInd(),vo.getAddonPermitTypeId(), vo.getTaxPercentage(),vo.getSkuGroupType());
		}
		addonServicesDTO.setProviderPaidTotal(providerPaidTotal);
		addonServicesDTO.setEndCustomerChargeTotal(endCustomerChargeTotal);
		addonServicesDTO.setEndCustomerSubtotalTotal(endCustomerSubtotalTotal);
		addonServicesDTO.setProviderTaxPaidTotal(providerTaxPaidTotal);
		dto.setUpsellInfo(addonServicesDTO);
		if(addonServicesDTO.getAddonServicesList().size() > 0)
			dto.setShowUpsellInfo(true);
		else
			dto.setShowUpsellInfo(false);	
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
			// addonServiceDTO.setPaymentAmountDisplayStr("$"+ additionalPaymentVO.getPaymentAmount()) ; //UIUtils.formatDollarAmount());
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
					 if(additionalPaymentVO.getPaymentType().equals(CreditCardConstants.CARD_ID_AMEX_STR))
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
	
	public ServiceOrderDTO modifySummaryDTOForInvoiceParts(ServiceOrderDTO dto){
		  int countOfParts =  0;
		  int countOfInvoice = 0;
		  BigDecimal totalEstProviderPayment= new BigDecimal("0.0");
		  List<ProviderInvoicePartsVO> invoiceParts = dto.getInvoiceParts();
		  List<InvoiceDocumentVO> invoiceDocList =null;
		  List<String> uniqueInvoiceNoList = new ArrayList<String>();
		  if(null!= invoiceParts && !(invoiceParts.isEmpty())){
			countOfParts=invoiceParts.size();
			for(ProviderInvoicePartsVO partVo:invoiceParts){
				boolean partsInvoiceDocUploaded= false;
				if(null!= partVo){
					if(StringUtils.isNotEmpty(partVo.getInvoiceNo())){
						uniqueInvoiceNoList.add(partVo.getInvoiceNo());
					}
					//Checking part status to calculate total est Provider Payment.
					if(StringUtils.isNotEmpty(partVo.getPartStatus())
							&& partVo.getPartStatus().equals(MPConstants.PARTS_STATUS_INSTALLED)
							&& null!=partVo.getEstProviderPartsPayment()){
						totalEstProviderPayment = totalEstProviderPayment.add(partVo.getEstProviderPartsPayment());
					}
					invoiceDocList =partVo.getInvoiceDocuments();
					if(null != invoiceDocList && invoiceDocList.size() > 0){
						partsInvoiceDocUploaded = true;
					}
					partVo.setInvoiceDocExists(partsInvoiceDocUploaded);
				}
				
			}
			
			uniqueInvoiceNoList =removeDuplicates(uniqueInvoiceNoList);
		    if(null != uniqueInvoiceNoList && !(uniqueInvoiceNoList.isEmpty())){
		    	countOfInvoice = uniqueInvoiceNoList.size();
		    }
		    
		    //Sorting Invoice parts based on part status
		    sortInvoicePartList(invoiceParts);
		  }
		//setting count of invoice parts and invoice number to display in summary page of invoice parts
		dto.setInvoiceParts(invoiceParts);
		dto.setCountOfParts(countOfParts);
		dto.setCountOfInvoice(countOfInvoice);
		//R 12_0_2 Setting proof of permit status,total est provider payment.
		dto.setTotalEstproviderPayment(totalEstProviderPayment.doubleValue());
		return dto;
		
	}
	public  List<String> removeDuplicates(List<String> list) {
        // Store unique items in result.
	    List<String> result = new ArrayList<String>();
        // Record encountered Strings in HashSet.
		HashSet<String> set = new HashSet<String>();
        // Loop over argument list.
		for (String item : list) {
           // If String is not in set, add it to the list and the set.
		    if (!set.contains(item)) {
			    result.add(item);
			    set.add(item);
		    }
		}
		return result;
	    }
	
	/**
	 * Sort the invoicePartList as below logic
	 * 1. If invoice number is null or not present, sort those records based on part status sort ascending
	 * 2. If invoice number is present, sort records by primarily invoice number and secondary part status	
	 * @param invoicePartList
	 */
	private void sortInvoicePartList(List<ProviderInvoicePartsVO> invoicePartList){
		List<ProviderInvoicePartsVO> invoicePartListWithoutInvoiceNo = new ArrayList<ProviderInvoicePartsVO>();
		List<ProviderInvoicePartsVO> invoicePartListWithInvoiceNo = new ArrayList<ProviderInvoicePartsVO>();
		if(null != invoicePartList && invoicePartList.size()>0){
			//separate into two lists one containing parts w/o invoice number and other having invoice number
			for(ProviderInvoicePartsVO invoicePart: invoicePartList){
				if(null != invoicePart){
					if(null != invoicePart.getInvoiceNo() && invoicePart.getInvoiceNo().length()>0){
						invoicePartListWithInvoiceNo.add(invoicePart);
					}else{
						invoicePartListWithoutInvoiceNo.add(invoicePart);
					}
				}
			}
			
			//sort invoicePartListWithoutInvoiceNo based on Part Status
			Collections.sort(invoicePartListWithoutInvoiceNo, new Comparator<ProviderInvoicePartsVO>() {
				public int compare(ProviderInvoicePartsVO o1,
						ProviderInvoicePartsVO o2) {
					int compareValue = 0;
					if(StringUtils.isNotEmpty(o1.getPartStatus()) &&  StringUtils.isNotEmpty(o2.getPartStatus())){
						compareValue = o1.getPartStatus().compareTo(o2.getPartStatus());
					}
					return compareValue;
				}
			});
			//sort invoicePartListWithInvoiceNo based on invoiceNo, partStatus
			Collections.sort(invoicePartListWithInvoiceNo, new Comparator<ProviderInvoicePartsVO>() {

				public int compare(ProviderInvoicePartsVO o1,
						ProviderInvoicePartsVO o2) {
					int compareValue = o1.getInvoiceNo().compareTo(o2.getInvoiceNo());
					if(compareValue == 0){
						if(StringUtils.isNotEmpty(o1.getPartStatus()) &&  StringUtils.isNotEmpty(o2.getPartStatus())){
							compareValue = o1.getPartStatus().compareTo(o2.getPartStatus());
						}
					}
					return compareValue;
				}
			});
			//clear the original invoicePartList
			invoicePartList.clear();
			//add the sorted lists to invoicePartList
			invoicePartList.addAll(invoicePartListWithoutInvoiceNo);
			invoicePartList.addAll(invoicePartListWithInvoiceNo);
		}
	}
	protected boolean isSoIsLoaded() {
		return soIsLoaded;
	}

	protected void setSoIsLoaded(boolean soIsLoaded) {
		this.soIsLoaded = soIsLoaded;
	}

	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService service) {
		this.notificationService = service;
	}
	
	//SL 3678- start
	
		public String getCreditCardTokenUrl() {
			logger.info("SLDetailsBaseAction getCreditCardTokenUrl : start ");
		String creditCardTokenUrl = "";
		if (getSession().getAttribute("CreditCardTokenUrl") != null) {
			creditCardTokenUrl = (String) getSession().getAttribute("CreditCardTokenUrl");
		} else {
			creditCardTokenUrl = PropertiesUtils.getFMPropertyValue(AppPropConstants.NEW_CREDIT_CARD_TOKEN_URL);
			getSession().setAttribute("CreditCardTokenUrl", creditCardTokenUrl);
		}
		logger.info("SLDetailsBaseAction getCreditCardTokenUrl : " + creditCardTokenUrl);
		return creditCardTokenUrl;
	}

	public String getCreditCardTokenAPICrndl() {
		logger.info("SLDetailsBaseAction getCreditCardTokenAPICrndl : start ");
		String creditCardTokenAPICrndl = "";
		if (getSession().getAttribute("CreditCardTokenAPICrndl") != null) {
			creditCardTokenAPICrndl = (String) getSession().getAttribute("CreditCardTokenAPICrndl");
		} else {
			creditCardTokenAPICrndl = PropertiesUtils.getFMPropertyValue(AppPropConstants.NEW_CREDIT_CARD_TOKEN_CRENDL);
			getSession().setAttribute("CreditCardTokenAPICrndl", creditCardTokenAPICrndl);
		}
		logger.info("SLDetailsBaseAction getCreditCardTokenAPICrndl : " + creditCardTokenAPICrndl);
		return creditCardTokenAPICrndl;
	}
	
	public String getCreditCardAuthTokenizeUrl() {
		logger.info("SLDetailsBaseAction getCreditCardAuthTokenizeUrl : start ");
		String creditCardAuthTokenizeUrl = "";
		if (getSession().getAttribute("CreditCardAuthTokenizeUrl") != null) {
			creditCardAuthTokenizeUrl = (String) getSession().getAttribute("CreditCardAuthTokenizeUrl");
		} else {
			creditCardAuthTokenizeUrl = PropertiesUtils.getFMPropertyValue(AppPropConstants.NEW_CREDIT_CARD_AUTH_TOKENIZE_URL);
			getSession().setAttribute("CreditCardAuthTokenizeUrl", creditCardAuthTokenizeUrl);
		}
		logger.info("SLDetailsBaseAction getCreditCardAuthTokenizeUrl : " + creditCardAuthTokenizeUrl);
		return creditCardAuthTokenizeUrl;
	}
	
	
	  public String getCreditCardAuthTokenizeXapiKey() {
		  logger.info("SLDetailsBaseAction getCreditCardAuthTokenizeXapiKey : start ");
		    String creditCardAuthTokenizeXapiKey = "";
		    if (getSession().getAttribute("CreditCardAuthTokenizeXapikey") != null) {
		      creditCardAuthTokenizeXapiKey = (String)getSession().getAttribute("CreditCardAuthTokenizeXapikey");
		    } else {
		      creditCardAuthTokenizeXapiKey = PropertiesUtils.getFMPropertyValue("webservices.pci.creditcardauthtokenize.xapikey");
		      getSession().setAttribute("CreditCardAuthTokenizeXapikey", creditCardAuthTokenizeXapiKey);
		    } 
		    logger.info("SLDetailsBaseAction getCreditCardAuthTokenizeXapiKey : " + creditCardAuthTokenizeXapiKey);
		    return creditCardAuthTokenizeXapiKey;
		  }
	//SL 3678 - end
}