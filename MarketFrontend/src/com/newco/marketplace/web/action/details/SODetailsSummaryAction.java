package com.newco.marketplace.web.action.details;
 
import static com.newco.marketplace.constants.Constants.SESSION.CAPTCHA_ERROR;
import static com.newco.marketplace.constants.Constants.SESSION.CAPTCHA_ERROR_POSITION;
import static com.newco.marketplace.constants.Constants.SESSION.PRIMARY_VENDOR_INDICATOR;
import static com.newco.marketplace.constants.Constants.SESSION.VENDOR_BUCK_INDICATOR;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.web.dto.SOWSelBuyerRefDTO;
import com.opensymphony.xwork2.ActionContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSubStatusVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegates.provider.ITermsAndCondDelegate;
import com.newco.marketplace.web.dto.AddonServiceRowDTO;
import com.newco.marketplace.web.dto.AddonServicesDTO;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.SODAllBidsDTO;
import com.newco.marketplace.web.dto.SOPartsDTO;
import com.newco.marketplace.web.dto.ServiceOrderBidModel;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.activitylog.domain.ActivityFilter;
import com.servicelive.activitylog.domain.ActivityLog;
import com.servicelive.activitylog.domain.ActivityStatus;
import com.servicelive.activitylog.domain.ActivityStatusType;
import com.servicelive.activitylog.domain.ActivityType;
import com.servicelive.activitylog.domain.BidActivity;
/**
 * $Revision: 1.52 $ $Author: nsanzer $ $Date: 2008/06/02 22:00:26 $
 */

/*
 * Maintenance History: See bottom of file
 */

public class SODetailsSummaryAction extends SLDetailsBaseAction implements Preparable {
	
	private static final long serialVersionUID = -1439603796200438554L;
	private static final String UTF_8 = "UTF-8";
  //These list variables are added for sorting provider name and distance before page loading
	private  List<ProviderResultVO>sortByNameAsc=new ArrayList<ProviderResultVO>();
	private  List<ProviderResultVO>sortByNameDes=new ArrayList<ProviderResultVO>();
	private  List<ProviderResultVO>sortByDistAsc=new ArrayList<ProviderResultVO>();
	private  List<ProviderResultVO>sortByDistDes=new ArrayList<ProviderResultVO>();
	private static final Logger logger = Logger.getLogger(SODetailsSummaryAction.class.getName());
	private ISOMonitorDelegate somDelegate;
	private ITermsAndCondDelegate iTermsAndCondDelegate;
	IServiceOrderUpsellBO  serviceOrderUpsellBO;
	private Integer subStatusIdChanged;
	private String refType;
	private String refVal;
	private String refValOld; 
	private SOPartsDTO partsDto;
	
	// Activity Log
	private IActivityLogHelper helper;
	
	//SL-19820
	String soId;
	String resourceId;
	String groupId;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public SODetailsSummaryAction(ISODetailsDelegate delegate,INotificationService notificationService){
    	this.detailsDelegate = delegate;
    	this.notificationService = notificationService;
    }
	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		
		createCommonServiceOrderCriteria();
		
		//Changing code SL-19820
		/*if (getSession().getServletContext().getAttribute(Constants.SESSION.SOD_SHIP_CAR) == null) {
			ArrayList<LookupVO> shippingCarrier = getDetailsDelegate().getShippingCarrier();
			getSession().getServletContext().setAttribute(Constants.SESSION.SOD_SHIP_CAR, shippingCarrier);
		}*/
		if (getAttribute(Constants.SESSION.SOD_SHIP_CAR) == null) {
			ArrayList<LookupVO> shippingCarrier = getDetailsDelegate().getShippingCarrier();
			setAttribute(Constants.SESSION.SOD_SHIP_CAR, shippingCarrier);
		}
		
		//Changing code SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = getParameter("soId");
		setAttribute(OrderConstants.SO_ID, soId);
		this.soId = soId;
		String groupId = getParameter(GROUP_ID);
		setAttribute(GROUP_ID, groupId);
		String resId = getParameter("resId");
		setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID, resId);
		this.resourceId = resId;
		
		String id = null;
		if(StringUtils.isNotBlank(groupId)){
			id = groupId;
		}else if(StringUtils.isNotBlank(soId)){
			id = soId;
		}
		
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		setAttribute(Constants.SESSION.SOD_MSG, msg);		
		
		String message = (String)getSession().getAttribute("message"+"_"+id);
		getSession().removeAttribute("message"+"_"+id);
		setAttribute("message", message);	
		
		setAttribute(Constants.SESSION.SOD_ERR_LIST, getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+id));
		getSession().removeAttribute(Constants.SESSION.SOD_ERR_LIST+"_"+id);
		
		setAttribute(CAPTCHA_ERROR, getSession().getAttribute(CAPTCHA_ERROR+"_"+id));
		setAttribute(CAPTCHA_ERROR_POSITION, getSession().getAttribute(CAPTCHA_ERROR_POSITION+"_"+id));
		getSession().removeAttribute(CAPTCHA_ERROR+"_"+id);
		getSession().removeAttribute(CAPTCHA_ERROR_POSITION+"_"+id);
		
		String  assignmentType = getDetailsDelegate().getAssignmentType(soId);
		//Changing code SL-19820
		//getSession().getServletContext().setAttribute(Constants.SESSION.SO_ASSIGNMENT_TYPE, assignmentType);
		setAttribute(Constants.SESSION.SO_ASSIGNMENT_TYPE, assignmentType);
		setAttribute("tab", "Summary");

	}

	public String execute() throws Exception {
		
		// This line should be next to last on all the detail tabs
		populateDTO();
		
		setAttribute("userRole", get_commonCriteria().getRoleType());
		ServiceOrderDTO so = null;
		if(null != getCurrentServiceOrderFromRequest()){
			so = getCurrentServiceOrderFromRequest();
		}else if(null != getAttribute("firstServiceOrder")){
			so = (ServiceOrderDTO) getAttribute("firstServiceOrder");
		}
		if(null != so){
			setCurrentSOStatusCodeInRequest(so.getStatus());
			//auto adjudication changes
			if((get_commonCriteria().getRoleId().intValue() == 3 && INHOME_BUYER.equalsIgnoreCase(so.getBuyerID())) 
					&& so.getStatus() != null && COMPLETED_STATUS == so.getStatus().intValue()){
				
				try{
					String methodOfClosure = getDetailsDelegate().getMethodOfClosure(this.soId);
					setAttribute("methodOfClosure", methodOfClosure);
					
					//Priority 5B
			        //Set warning message if the provider has entered invalid model and/or serial no
			        setModelSerialPanel(so);
					
				}catch(Exception e){
					logger.error("Exception while trying to get method of closure :" + e);
				}
			}
		}
		return SUCCESS;
	}
	
	public String loadForWarrantyOrder() {
		try {
			populateDTO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	private void populateDTO() throws Exception
	{
		//SL-19820
		//set the serviceOrderDTO in request
		ServiceOrderDTO soDTO = null;
		if(StringUtils.isNotBlank(soId)){
			if(StringUtils.isNotBlank((String)getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID))) {
				Integer resID = Integer.parseInt((String)getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
				soDTO = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), resID);
			}
			else {
				soDTO = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			}
		}
		
		setAttribute(THE_SERVICE_ORDER, soDTO);
		
		setPanelsDisplayed();
		
		setSummaryDTOForSingleOrder();
		
		if (String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(soDTO.getBuyerID())
				|| String.valueOf(BuyerConstants.TECH_TALK_BUYER_ID).equals(soDTO.getBuyerID())) {

			setAttribute("laborTaxPercentage", null != soDTO.getLaborTaxPercent() ? soDTO.getLaborTaxPercent().doubleValue() : 0.00);
			setAttribute("partsTaxPercentage" , null != soDTO.getPartsTaxPercent() ? soDTO.getPartsTaxPercent().doubleValue() : 0.00);
			
			BigDecimal providerAddonTaxTotal = new BigDecimal(0);
			BigDecimal endCustomerCharge = new BigDecimal(0);
			if (null != soDTO.getUpsellInfo() && null != soDTO.getUpsellInfo().getAddonServicesList() && soDTO.getUpsellInfo().getAddonServicesList().size() > 0) {
				for (AddonServiceRowDTO soAddon : soDTO.getUpsellInfo().getAddonServicesList()) {
					if (null != soAddon.getSku() && !"99888".equals(soAddon.getSku())) {
						endCustomerCharge = BigDecimal.valueOf(soAddon.getEndCustomerCharge())
								.multiply(BigDecimal.valueOf(soAddon.getQuantity()));
						providerAddonTaxTotal = providerAddonTaxTotal.add(BigDecimal.valueOf(
								(endCustomerCharge.doubleValue()*soAddon.getTaxPercentage())
								/(1.0000+soAddon.getTaxPercentage())));
					}
				}
			}
			providerAddonTaxTotal = MoneyUtil.getRoundedMoneyBigDecimal(providerAddonTaxTotal.doubleValue());
			setAttribute("providerAddonTaxTotal", providerAddonTaxTotal);
			
		} else {
			setAttribute("laborTaxPercentage", 0.00);
			setAttribute("partsTaxPercentage" , 0.00);
			setAttribute("providerAddonTaxTotal", 0.00);
		}
		
		setAttribute("userRole", get_commonCriteria().getRoleType());
		setAttribute(OrderConstants.RESCHEDULE_DTO,getSession().getAttribute(OrderConstants.RESCHEDULE_DTO+"_"+soId));
		setAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG,getSession().getAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG+"_"+soId));
		//TODO : Not sure from where this session value is set
		String bidderResourceId =  (String)getSession().getAttribute("bidderResourceId");
		if(bidderResourceId!=null){
			setAttribute("bidderResourceId", bidderResourceId);
		}
		// If this is a buyer, with contact info shared, show this on the page
		String role = get_commonCriteria().getRoleType();
		if (role.toUpperCase().equals(OrderConstants.BUYER_ROLE.toUpperCase())) {
			ServiceOrderDTO dto = (ServiceOrderDTO) getAttribute("summaryDTO");
			if (dto != null) {
				setAttribute("showContactInfoShared", dto.isShareContactInd());
			} else {
				setAttribute("showContactInfoShared",false);
			}
		}		
		
		//SL-19820
		//Remove these attributes from session after setting to request.
		/*getSession().setAttribute(OrderConstants.RESCHEDULE_DTO,null);
		getSession().setAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG,null);*/
		getSession().removeAttribute(OrderConstants.RESCHEDULE_DTO+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG+"_"+soId);
		getSession().setAttribute("bidderResourceId",null);
	}
	
	
	private void setSummaryDTOForSingleOrder() throws Exception
	{
		// If handling a group order, exit/return.
		String groupId = getParameter(GROUP_ID);
		if(StringUtils.isNotBlank(groupId))
			return;
		
		ServiceOrderDTO dto= null;
		try
		{
			//Changing code SL-19820
			//dto = getCurrentServiceOrderFromSession();
			dto = getCurrentServiceOrderFromRequest();
			
			if(dto == null)
			{
				doSORefetch(true);
				//Changing code SL-19820
				//dto = getCurrentServiceOrderFromSession();
				dto = getCurrentServiceOrderFromRequest();
			}
			
			if(dto == null)
			{
				return;
			}
		}
		catch(Exception e)
		{
			logger.warn("Ignoring exception that occurred while trying to get summary DTO for single order.", e);
		}

		dto = modifySummaryDTO(dto);
		/*This method will do the following 
		 * 1) Set Count of parts
		 * 2) Set count of unique invoice based on invoice no
		 * 3) Sort parts not having invoice based on partName
		 * 4) Sort parts having invoice based on invoice no,secondary sort is based on part name.
		 * 5) Calculate total sum of est provider payment
		 * 6) Set Est Provider Payment to NA if part status is not installed.
		 * */
		dto = modifySummaryDTOForInvoiceParts(dto);
		setAttribute("summaryDTO", dto);
		validateParts(dto);
		//String soID = (String)getSession().getAttribute(OrderConstants.SO_ID); 
		String soID = (String)getAttribute(OrderConstants.SO_ID); 
		Boolean isPaymentInfoViewable = isPermissionAvaialble("ViewCustomerPayment");
	    AdditionalPaymentVO additionalPaymentVO = getAdditionalPaymentInfo(soID,isPaymentInfoViewable);
		
	        
	    AddonServicesDTO addonServicesDTO = new AddonServicesDTO();
	    addonServicesDTO.setPaymentAmount(0.0);
	       if (additionalPaymentVO != null)
	       {

	    		   addonServicesDTO.setUpsellPaymentIndicator(true);
	    		   if(dto.getUpsellInfo() != null && dto.getUpsellInfo().getProviderPaidTotal() != null)
	    			   addonServicesDTO.setPaymentAmount(dto.getUpsellInfo().getProviderPaidTotal());
	    		   else
	    			   addonServicesDTO.setPaymentAmount(0.00);
	    		   
	    		   ArrayList<LabelValueBean> statusDatesList = dto.getStatusAndDateList();
	    		   
	    		   if (StringUtils.equals(additionalPaymentVO.getPaymentType(), OrderConstants.UPSELL_PAYMENT_TYPE_CHECK))
	    		   {
	    			   if (dto.getStatus()== OrderConstants.COMPLETED_STATUS)
	    			   {
	    				   addonServicesDTO.setPaymentInformation("Payment from end customer for add-on services has not been received yet");
	    			   }
	    			   if (dto.getStatus()== OrderConstants.CLOSED_STATUS)
	    			   {
	    				  				   
	    				   String orderClosedDate = "";
	    				   for (LabelValueBean statusBean : statusDatesList)
	    				   {
	    					   if (StringUtils.equals(statusBean.getLabel(),OrderConstants.CLOSED))
	    						   {
	    						   orderClosedDate =    statusBean.getValue();
	    						   addonServicesDTO.setPaymentInformation("Payment from end customer was received for add-on services on " + orderClosedDate);
	    						   break;
	    						   }
	    				   }
	    				   
	    			   }

	    		   }
	    		   if (StringUtils.equals(additionalPaymentVO.getPaymentType(), OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT))
	    		   {
    				   String orderCompletedDate = "";
    				   for (LabelValueBean statusBean : statusDatesList)
    				   {
    					   //if (StringUtils.equals(statusBean.getLabel(),"Completed"))
    						//   {
    						   orderCompletedDate =    statusBean.getValue();
    						   addonServicesDTO.setPaymentInformation("Credit card payment from end customer was submitted on "+ orderCompletedDate);
    						   break;
    						  // }
    				   }
	    			   
	    		   }
	    		   
	    		   updateAddonPaymentInfo(addonServicesDTO, isPaymentInfoViewable, additionalPaymentVO);
	    		   
	       }
	    //Sorting summaryDTO based on providername
	     if(dto.getStatus()== OrderConstants.ROUTED_STATUS){
			    if(null!=dto&& null!=dto.getRoutedProvExceptCounterOffer())
			        {
			           List<ProviderResultVO>providerList=sortProviderNameDefault(dto.getRoutedProvExceptCounterOffer());
			           dto.setRoutedProvExceptCounterOffer(providerList);
			        }

	     }
	   
		Integer resourceId = ((SecurityContext) getSession().getAttribute("SecurityContext")).getVendBuyerResId();
	    dto.setLoggedInResourceId(resourceId);
	    setAttribute("summaryDTO", dto);
	    setAttribute("addonServicesDTO", addonServicesDTO);
	    
	    if ((Constants.PriceModel.ZERO_PRICE_BID.equals(dto.getPriceModel()) || Constants.PriceModel.BULLETIN.equals(dto.getPriceModel())) 
	    		&& get_commonCriteria().getRoleType().equals(OrderConstants.ROLE_PROVIDER)) {

	    	SODAllBidsDTO allBidsDTO = this.retrieveAllOrderBidsAsDto();
		    setAttribute("allBidsDTO", allBidsDTO);
	    }
	    
	    setAttribute("providerResourceId", get_commonCriteria().getVendBuyerResId());
		
		if (dto != null && dto.getPartsList() != null)
			setAttribute("partCount", dto.getPartsList().size());
		setAttribute("commentCnt", getDetailsDelegate().getBidNoteCount(dto.getId()));
		
		
	}

	
	public  List<ServiceDatetimeSlot> getSODateTimeSlots(String soId) {
		List<ServiceDatetimeSlot> serviceDatetimeSlots = null;
		try{
			serviceDatetimeSlots = getDetailsDelegate().getSODateTimeSlots(soId);
		}catch(Exception e){
			logger.error("Exception occured in getSODateTimeSlots() while fetching soDateTimeSlots: "+e.getMessage());
		}
		
		return serviceDatetimeSlots;
	}
	
	public  void updateServiceDatetimeSlot(ServiceDatetimeSlot serviceDatetimeSlot) throws BusinessServiceException {
		logger.info("selected slot id "+serviceDatetimeSlot.getSlotId());
		logger.info("Sevice order id "+serviceDatetimeSlot.getSoId());
		getDetailsDelegate().updateAcceptedServiceDatetimeSlot(serviceDatetimeSlot);
	}
	/**
	 * Method to sort providername and distance and return default providername
	 * ascending sort to set in dto;
	 * 
	 * @param routedProvExceptCounterOffer
	 * @return List<ProviderResultVO>
	 */
	private List<ProviderResultVO> sortProviderNameDefault(
			List<ProviderResultVO> routedProvExceptCounterOffer) {
		List<ProviderResultVO> providerVOList = routedProvExceptCounterOffer;
		for (ProviderResultVO vo : providerVOList) {
			if (vo != null
					&& (StringUtils.isNotEmpty(vo.getProviderFirstName()) || StringUtils
							.isNotEmpty(vo.getProviderFirstName()))) {
				vo.setProviderFullName(vo.getProviderFirstName()
						+ vo.getProviderLastName());

			}
		}
	   
		this.getSortByDistAsc().addAll((sortByDistAsc(providerVOList)));
		return this.getSortByDistAsc();
	}
	
		
	
				
	private List<ProviderResultVO> sortByDistAsc(List<ProviderResultVO> providerVOList) {
		//sort by distance ascending
		//sortByDistAsc=providerVOList;
		Collections.sort(providerVOList, new Comparator<ProviderResultVO>() {
			public int compare(ProviderResultVO o1, ProviderResultVO o2) {
				return Double.compare(o1.getDistanceFromBuyer(), o2.getDistanceFromBuyer());
				
//				
//				return String.valueOf(o1.getDistanceFromBuyer())
//						.compareToIgnoreCase(
//								String.valueOf(o2.getDistanceFromBuyer()));
			}
		});
		return providerVOList;
	}

	private List<ProviderResultVO> sortByDistDes(List<ProviderResultVO> providerVOList) {
		//sort by distance descending
		//sortByDistDes=providerVOList;
		Collections.sort(providerVOList, new Comparator<ProviderResultVO>() {
			public int compare(ProviderResultVO o1, ProviderResultVO o2) {
				return Double.compare(o1.getDistanceFromBuyer(), o2.getDistanceFromBuyer());
//				return String.valueOf(o1.getDistanceFromBuyer())
//						.compareToIgnoreCase(
//								String.valueOf(o2.getDistanceFromBuyer()));
			}
		});
		Collections.reverse(providerVOList);
		return providerVOList;
	}

	private List<ProviderResultVO> sortByNameDes(List<ProviderResultVO> providerVOList){
		//sort by name descending
		//sortByNameDes=providerVOList;
			Collections.sort(providerVOList, new Comparator<ProviderResultVO>() {
				public int compare(ProviderResultVO o1, ProviderResultVO o2) {
					return o1.getProviderFullName().compareToIgnoreCase(
							o2.getProviderFullName());
				}
			});
		Collections.reverse(providerVOList);
		return providerVOList;
}
	

	private List<ProviderResultVO> sortByNameAsc(List<ProviderResultVO> providerVOList) {
		// sort by name ascending
		//sortByNameAsc=providerVOList;
				Collections.sort(providerVOList, new Comparator<ProviderResultVO>() {
					public int compare(ProviderResultVO o1, ProviderResultVO o2) {
						return o1.getProviderFullName().compareToIgnoreCase(
								o2.getProviderFullName());
					}
				});
		
		return providerVOList;
	}

	private SODAllBidsDTO retrieveAllOrderBidsAsDto() {
		//Changing code SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID); 
		Integer companyId = get_commonCriteria().getCompanyId();
		//String routedResourceAttr = (String) getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID);
		String routedResourceAttr = (String) getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID);
		Integer routedResourceId = null;
		if (StringUtils.isNotBlank(routedResourceAttr)) {
			routedResourceId = Integer.valueOf(routedResourceAttr);
		} else {
			//ServiceOrderDTO soDto = (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
			ServiceOrderDTO soDto = (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);
			if (soDto != null && null != soDto.getAcceptedResource()) {
				routedResourceId = soDto.getAcceptedResource().getResourceId();
			}
		}
		
		List<ActivityLog> allBidsForOrder = retrieveAllBidsForOrder(soId);
		Map<Long, String> providerNamesMap = retrieveRoutedProvidersNameMap(soId);
		ServiceOrderBidModel currentBidFromCurrentProvider = null;
		List<ServiceOrderBidModel> pastBidsFromCurrentProvider = new ArrayList<ServiceOrderBidModel>();
		List<ServiceOrderBidModel> bidsFromOtherProvidersInMyCompany = new ArrayList<ServiceOrderBidModel>();
		List<ServiceOrderBidModel> bidsFromProvidresInOtherCompanies = new ArrayList<ServiceOrderBidModel>();
		double minimumBid = Double.MAX_VALUE;
		double maximumBid = Double.MIN_VALUE;
		int numberOfCurrentBids = 0;

		for (ActivityLog activity : allBidsForOrder) {
			if (activity instanceof BidActivity) {
				BidActivity bid = (BidActivity) activity;
				ServiceOrderBidModel bidModel = this.createBidModelFromActivityLog(bid);
				if (bidIsFromCurrentProvidersCompany(companyId, bid)) {
					if (bidIsFromCurrentProvider(routedResourceId, bid)) {
						// For the current provider, show the current bid, even if it has expired
						if (bidIsCurrentEvenIfExpired(bid)) {
							// Find if this bid is more recent that the current bid
							if (currentBidFromCurrentProvider == null || bidModel.getDateOfBid().after(currentBidFromCurrentProvider.getDateOfBid())) {
								if (currentBidFromCurrentProvider != null) {
									pastBidsFromCurrentProvider.add(currentBidFromCurrentProvider);
								}
								currentBidFromCurrentProvider = bidModel;
							} else {
								pastBidsFromCurrentProvider.add(bidModel);
							}
						}
						else {
							pastBidsFromCurrentProvider.add(bidModel);
						}
					}
					else {
						if (bidIsCurrent(bid)) {
							bidsFromOtherProvidersInMyCompany.add(bidModel);
						}
					}

					String providerName = providerNamesMap.get(bid.getProviderResourceId());
					if (providerName != null) {
						bidModel.setBidder(providerName);
					}
				}
				else {
					if (bidIsCurrent(bid)) {
						bidsFromProvidresInOtherCompanies.add(bidModel);
					}
					bidModel.setBidder("Provider Bid");
				}
				
				if (bidIsCurrent(bid)) {
					numberOfCurrentBids++;
					minimumBid = Math.min(bid.getBidTotal(), minimumBid);
					maximumBid = Math.max(bid.getBidTotal(), maximumBid);
				}				
			}
		}
		
		if (numberOfCurrentBids == 0) {
			minimumBid = 0;
			maximumBid = 0;
		}
		
		SODAllBidsDTO allBidsDto = new SODAllBidsDTO();
		allBidsDto.setNumberOfBids(numberOfCurrentBids);		
		allBidsDto.setHighBid(maximumBid);
		allBidsDto.setLowBid(minimumBid);
		allBidsDto.setMyCurrentBid(currentBidFromCurrentProvider);
		allBidsDto.setMyPreviousBids(pastBidsFromCurrentProvider);
		allBidsDto.setOtherBidsFromMyCompany(bidsFromOtherProvidersInMyCompany);
		allBidsDto.setAllOtherBids(bidsFromProvidresInOtherCompanies);
		if (!pastBidsFromCurrentProvider.isEmpty()) {
			allBidsDto.setMyPreviousBid(pastBidsFromCurrentProvider.get(0));
		}
		return allBidsDto;
	}

	private Map<Long, String> retrieveRoutedProvidersNameMap(String soId) {
		ServiceOrder so = somDelegate.getServiceOrder(soId);
		if (so == null) return Collections.emptyMap();
		
		List<RoutedProvider> routedResources = so.getRoutedResources();
		if (routedResources == null || routedResources.isEmpty()) return Collections.emptyMap();

		Map<Long, String> providerNamesMap = new HashMap<Long, String>(routedResources.size());
		for (RoutedProvider provider : routedResources) {
			StringBuilder providerName = new StringBuilder(50);
			if (provider.getProviderContact() != null) {
				String providerFirstName = provider.getProviderContact().getFirstName();
				if (StringUtils.isNotEmpty(providerFirstName)) {
					providerName.append(providerFirstName).append(" ");
				}
				
				String providerLastName = provider.getProviderContact().getLastName();
				if (StringUtils.isNotEmpty(providerLastName)) {
					providerName.append(providerLastName);
				}
			}
			else {
				providerName.append("Error getting Provider Name");
			}
			providerNamesMap.put(provider.getResourceId().longValue(),
					providerName.toString().trim());
		}
		
		return providerNamesMap;
	}

	private boolean bidIsCurrent(BidActivity bid) {
		ActivityStatus currentStatus = bid.getCurrentStatus();
		boolean bidIsCurrent = false;
		if (currentStatus != null) {
			ActivityStatusType currentStatusType = currentStatus.getStatus();
			bidIsCurrent = ActivityStatusType.ENABLED.equals(currentStatusType);
		}
		return bidIsCurrent;
	}
	
	private boolean bidIsCurrentEvenIfExpired(BidActivity bid) {
		ActivityStatus currentStatus = bid.getCurrentStatus();
		boolean bidIsCurrent = false;
		if (currentStatus != null) {
			ActivityStatusType currentStatusType = currentStatus.getStatus();
			bidIsCurrent = ActivityStatusType.ENABLED.equals(currentStatusType) || ActivityStatusType.EXPIRED.equals(currentStatusType);
		}
		return bidIsCurrent;
	}

	private boolean bidIsFromCurrentProvider(Integer routedResourceId,
			BidActivity bid) {
		Long providerResourceId = bid.getProviderResourceId();
		boolean bidIsFromCurrentProvider = providerResourceId != null && providerResourceId.intValue() == routedResourceId.intValue();
		return bidIsFromCurrentProvider;
	}

	private boolean bidIsFromCurrentProvidersCompany(Integer companyId,
			BidActivity bid) {
		Long providerId = bid.getProviderId();
		boolean bidIsFromCurrentProvidersCompany = providerId != null && providerId.intValue() == companyId.intValue();
		return bidIsFromCurrentProvidersCompany;
	}
	
	private List<ActivityLog> retrieveAllBidsForOrder(String orderId) {
		ActivityFilter activityFilter = new ActivityFilter();
		activityFilter.addOrderId(orderId);
		activityFilter.addActivityType(ActivityType.Bid);
		List<ActivityLog> orderBids = this.helper.findActivities(activityFilter);
		return orderBids;

	}
	
	
//	private SODAllBidsDTO createBidsFromDB()
//	{
//		String soID = (String)getSession().getAttribute(OrderConstants.SO_ID); 
//		Integer companyId = get_commonCriteria().getCompanyId();
//		String routedResourceAttr = (String) getSession()
//				.getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID);
//		Integer routedResourceId = null;
//		if (routedResourceAttr != null) {
//			routedResourceId = Integer.valueOf(routedResourceAttr);
//		} else {
//			ServiceOrderDTO soDTO = (ServiceOrderDTO) getSession().getAttribute(
//					THE_SERVICE_ORDER);
//			if(soDTO != null) {
//				routedResourceId = soDTO.getAcceptedResource().getResourceId();
//			}
//		}
//		
//		ServiceOrder so = somDelegate.getServiceOrder(soID);
//		
//		SODAllBidsDTO dto = new SODAllBidsDTO();
//
//		
//		List<ServiceOrderBidModel> bidsFromOtherCompanies = new ArrayList<ServiceOrderBidModel>();
//		List<ServiceOrderBidModel> bidsFromMyCompany = new ArrayList<ServiceOrderBidModel>();
//		ServiceOrderBidModel bidModel;
//		
//		// High bid/Low Bid
//		Double lowBid = 9999999d;
//		Double highBid = 0d;
//		
//		
//		List<RoutedProvider> routedResources = so.getRoutedResources();
//		int numBids=0;
//		for(RoutedProvider provider : routedResources)
//		{
//			//Ignore if no value has been set
//			if(provider.getTotalLaborBid() == null || provider.getTotalLaborBid().floatValue() == 0.0f)
//				continue;
//			
//			numBids++;
//			// See if we are from the same company
//			if(companyId.intValue() == provider.getVendorId().intValue())
//			{
//				if(routedResourceId.intValue() != provider.getResourceId().intValue())
//				{
//					bidModel = createBidFromRoutedProvider(provider, true, false, false, so.getServiceLocationTimeZone());
//					bidsFromMyCompany.add(bidModel);
//				}
//				// My Current Bid
//				else
//				{
//					bidModel = createBidFromRoutedProvider(provider, true, false, true, so.getServiceLocationTimeZone());
//					dto.setMyCurrentBid(bidModel);
//				}
//			}
//			// Bid from another company
//			else
//			{
//				bidModel = createBidFromRoutedProvider(provider, true, true, false, so.getServiceLocationTimeZone());
//				bidsFromOtherCompanies.add(bidModel);
//			}				
//			
//			// High bid/Low Bid
//			if(bidModel.getTotal() != null)
//			{
//				if(bidModel.getTotal() > highBid)
//					highBid = bidModel.getTotal(); 
//				if(bidModel.getTotal() < lowBid)
//					lowBid = bidModel.getTotal(); 
//			}
//		}
//		
//		dto.setNumberOfBids(numBids);
//		dto.setAllOtherBids(bidsFromOtherCompanies);
//		dto.setOtherBidsFromMyCompany(bidsFromMyCompany);
//		dto.setLowBid(lowBid);
//		dto.setHighBid(highBid);
//		
//		
//		return dto;
//		
//	}
	
	
	
//	private ServiceOrderBidModel createBidFromRoutedProvider(RoutedProvider provider, boolean initComment, 
//			boolean isOtherCompany, boolean isMyCurrentBid, String serviceLocationTimeZone)
//	{
//		ServiceOrderBidModel bid = new ServiceOrderBidModel();
//
//		// Total Bid
//		if(provider.getConditionalSpendLimit() != null && provider.getConditionalSpendLimit() > 0)
//		{
//			bid.setTotal(provider.getConditionalSpendLimit());
//		}
//		else
//		{
//			BigDecimal total = new BigDecimal(0);
//			if(provider.getConditionalSpendLimit() != null)
//				total.add(provider.getPartsAndMaterialsBid());
//			if(provider.getTotalLaborBid() != null)
//				total.add(provider.getTotalLaborBid());
//			bid.setTotal(total.doubleValue());
//		}
//			
//
//		// Date of Bid
//		bid.setDateOfBid(provider.getProviderRespDate());
//		
//		// Expiration Date
//		if(provider.getConditionalExpirationDate() != null)
//		{
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy hh:mm aa zzz");
//			String expDate = TimeUtils.convertGMTtoTimezone(provider.getConditionalExpirationDate(), serviceLocationTimeZone, simpleDateFormat);
//			bid.setBidExpirationDatepicker(expDate);
//		}
//		
//		// Comment
//		if(initComment)
//			bid.setComment(provider.getProviderRespComment());
//		
//		if(isMyCurrentBid)
//		{
//			// Hours
//			if(provider.getTotalHoursBid() != null)
//				bid.setTotalHours(provider.getTotalHoursBid().doubleValue());
//			else
//				bid.setTotalHours(0.0d);
//			// Labor
//			if(provider.getTotalLaborBid() != null)
//				bid.setTotalLabor(provider.getTotalLaborBid().doubleValue());
//			else
//				bid.setTotalLabor(0.0d);
//			
//			// Materials
//			if(provider.getPartsAndMaterialsBid() != null)
//				bid.setPartsMaterials(provider.getPartsAndMaterialsBid().doubleValue());
//			else
//				bid.setPartsMaterials(0.0d);
//
//			// Service Request Date
//			setBidModelDateChanges(bid, provider.getConditionalChangeDate1(), provider.getConditionalChangeDate2());
//		}
//		// Bidder
//		if(isOtherCompany)
//		{
//			bid.setBidder("Provider Bid");
//		}
//		else
//		{
//			if(provider.getProviderContact() != null)
//			{
//				String bidder = "";
//				if(StringUtils.isNotEmpty(provider.getProviderContact().getFirstName()))
//					bidder += provider.getProviderContact().getFirstName() + " ";
//				if(StringUtils.isNotEmpty(provider.getProviderContact().getLastName()))
//					bidder += provider.getProviderContact().getLastName();
//				bid.setBidder(bidder);
//			}
//			else
//			{
//				bid.setBidder("Error getting Provider Name");
//			}
//		}
//		
//		return bid;
//	}
	
//	private SODAllBidsDTO getBidsFromActivityLog()
//	{
//		SODAllBidsDTO dto = new SODAllBidsDTO();
//		
//		Long providerId = new Long(get_commonCriteria().getCompanyId());
//		Long providerResourceId = new Long(get_commonCriteria().getVendBuyerResId());
//		String soID = (String)getSession().getAttribute(OrderConstants.SO_ID);
//		
//		List<ActivityLog> activityLogs = helper.getProviderBidsForOrder(providerId,
//				providerResourceId, soID);
//		
//		
//		Integer myProviderResourceId  = get_commonCriteria().getVendBuyerResId();
//		Integer companyId = get_commonCriteria().getCompanyId();
//
//		dto.setMyCurrentBid(getMyCurrentBid(companyId, myProviderResourceId, activityLogs));
//		dto.setMyPreviousBid(getMyCurrentBid(companyId, myProviderResourceId, activityLogs));
//		dto.setOtherBidsFromMyCompany(getBidsFromMyCompany(companyId, myProviderResourceId, activityLogs));
//		dto.setAllOtherBids(getBidsFromOtherMyCompanies(companyId, activityLogs));
//		
//		dto.setLowBid(getLowBidFromLogs(activityLogs));
//		dto.setHighBid(getHighBidFromLogs(activityLogs));
//		
//		
//		return dto;
//	}

//	private ServiceOrderBidModel getPreviousBidFromActivityLog()
//	{
//		ServiceOrderBidModel previousBid=null;
//		
//		
//		Long providerId = new Long(get_commonCriteria().getCompanyId());
//		Long providerResourceId = new Long(get_commonCriteria().getVendBuyerResId());
//		String soID = (String)getSession().getAttribute(OrderConstants.SO_ID);
//		
//		List<ActivityLog> activityLogs = null;
//		try
//		{
//			activityLogs = helper.getProviderBidsForOrder(providerId, providerResourceId, soID);
//		}
//		catch (RuntimeException e)
//		{
//			logger.log(Level.ERROR, "Activity Log getProviderBidsForOrder() failed. SO#" + soID);
//		}
//		
//				
//		if(activityLogs != null && activityLogs.size() > 1)
//			previousBid = createBidModelFromActivityLog(activityLogs.get(1));
//		
//		return previousBid;
//	}
	
	
	
//	private Double getLowBidFromLogs(List<ActivityLog> activityLogs)
//	{
//		Double lowBid = new Double(99999999);
//		
//		
//		BidActivity bid;
//		Double totalLabor;
//		for(ActivityLog log : activityLogs)
//		{
//			bid = (BidActivity)log;
//			if(bid.getLaborRate() != null &&  bid.getLaborEffortInHours() != null)
//			{
//				totalLabor = bid.getLaborRate() * bid.getLaborEffortInHours();
//				if(totalLabor < lowBid)
//					lowBid = totalLabor;
//			}
//		}
//		
//		return lowBid;
//	}

//	private Double getHighBidFromLogs(List<ActivityLog> activityLogs)
//	{
//		Double highBid = new Double(0);
//		
//		
//		BidActivity bid;
//		Double totalLabor;
//		for(ActivityLog log : activityLogs)
//		{
//			bid = (BidActivity)log;
//			if(bid.getLaborRate() != null &&  bid.getLaborEffortInHours() != null)
//			{
//				totalLabor = bid.getLaborRate() * bid.getLaborEffortInHours();
//				if(totalLabor > highBid)
//					highBid = totalLabor;
//			}
//		}
//		
//		return highBid;
//	}
	
//	private ServiceOrderBidModel getMyCurrentBid(Integer companyId, Integer resourceid, List<ActivityLog> logs)
//	{
//	
//		// Just get first one for now/testing
//		if(logs != null && logs.size() > 0)
//		{
//			return createBidModelFromActivityLog(logs.get(0));
//		}		
//		
//		return null;
//	}
	
//	private List<ServiceOrderBidModel> getBidsFromOtherMyCompanies(Integer companyId, List<ActivityLog> bids)
//	{
//		List<ActivityLog> otherCompanylogs = getOtherCompanyLogs(companyId, bids);		
//		List<ServiceOrderBidModel> bidsFromOtherCompanies = new ArrayList<ServiceOrderBidModel>();
//		ServiceOrderBidModel bidModel;
//		for(ActivityLog log : otherCompanylogs)
//		{
//			bidModel = createBidModelFromActivityLog(log);
//			bidsFromOtherCompanies.add(bidModel);			
//		}
//		
//		return bidsFromOtherCompanies;
//	}
	
//	private List<ServiceOrderBidModel> getBidsFromMyCompany(Integer companyId, Integer myResourceId, List<ActivityLog> bids)
//	{
//		List<ActivityLog> myCompanyBids = getMyCompanyLogs(companyId, bids);		
//		ServiceOrderBidModel bidModel;
//		List<ServiceOrderBidModel> otherBidsFromMyCompany = new ArrayList<ServiceOrderBidModel>();
//		for(ActivityLog log : myCompanyBids)
//		{
//			if(myResourceId.intValue() == log.getProviderId().intValue())
//			{
//				bidModel = createBidModelFromActivityLog(log);
//				otherBidsFromMyCompany.add(bidModel);
//			}
//		}
//		
//		return otherBidsFromMyCompany;
//	}
	
	
	
//	private List<ActivityLog> getMyCompanyLogs(Integer companyId, List<ActivityLog> bids)
//	{
//		List<ActivityLog> results = new ArrayList<ActivityLog>();
//
//		for(ActivityLog bid : bids)
//		{
//			if(companyId.intValue() == bid.getProviderId().intValue())
//			{
//				results.add(bid);
//			}
//		}		
//		
//		return results;
//	}
	
//	private List<ActivityLog> getOtherCompanyLogs(Integer companyId, List<ActivityLog> bids)
//	{
//		List<ActivityLog> results = new ArrayList<ActivityLog>();
//
//		for(ActivityLog bid : bids)
//		{
//			if(companyId.intValue() != bid.getProviderId().intValue())
//			{
//				results.add(bid);
//			}
//		}		
//		
//		return results;
//	}
	
	private ServiceOrderBidModel createBidModelFromActivityLog(ActivityLog log)
	{
		ServiceOrderBidModel  bidModel = new ServiceOrderBidModel();

		BidActivity b = (BidActivity)log;
		bidModel.setBidder(b.getCreatedBy());
		bidModel.setDateOfBid(b.getCreatedOn());
		if(b.getExpirationDate() != null)
		{
			DateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm aa");
			bidModel.setBidExpirationDatepicker(df.format(b.getExpirationDate()));
						
			if (b.getExpirationDate().before(Calendar.getInstance().getTime())) {
				bidModel.setBidExpired(true);
			} else {
				bidModel.setBidExpired(false);
			}
		}
		bidModel.setCalculatedHourlyRate(b.getLaborRate());
		bidModel.setTotal(b.getBidTotal());
		bidModel.setComment(b.getComment());
		bidModel.setPartsMaterials(b.getMaterialsCost());
		Double totalLabor = 0.0d;
		if (b.getMaximumLaborCost() != null) {
			if(b.getLaborRate() != null && b.getLaborEffortInHours() != null) {
				totalLabor = b.getLaborRate() * b.getLaborEffortInHours();
				bidModel.setLaborRateIsHourly(true);				
			}else{
				totalLabor = b.getMaximumLaborCost();
				bidModel.setLaborRateIsHourly(false);
			}			
		}
		bidModel.setTotalLabor(totalLabor);
		bidModel.setTotalHours(b.getLaborEffortInHours());
		
		
		// Service Request Date
		setBidModelDateChanges(bidModel, b.getServiceDateFrom(), b.getServiceDateTill());		
		
		
		return bidModel;
	}

	
	private void setBidModelDateChanges(ServiceOrderBidModel bid, Date startDate, Date endDate)
	{
		
		if(bid == null)
			return;
		
		// Service Request Date
		if(startDate != null && endDate != null)
		{
			DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );        
	        String fromDate = formatter.format(startDate);				
	        String toDate = formatter.format(endDate);
	        
	        // Different dates then set normally
	        if(!fromDate.equalsIgnoreCase(toDate))
	        {
	        	bid.setNewDateByRangeFrom(fromDate);		        
	        	bid.setNewDateByRangeTo(toDate);
	        }
	        // Set 'fromDate' as 'MM/dd/yy hh:mm aa'
	        // Set 'toDate' as 'hh:mm aa'
	        else
	        {
				DateFormat fmt1 = new SimpleDateFormat( "MM/dd/yyyy hh:mm aa" );        
				DateFormat fmt2 = new SimpleDateFormat( "hh:mm aa" );        

				fromDate = fmt1.format(startDate);
				toDate = fmt2.format(endDate);
				bid.setNewDateByRangeFrom(fromDate);
				bid.setNewDateByRangeTo(toDate);
	        }
		}
		else if(startDate != null)
		{
			DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );        
	        String newDate = formatter.format(startDate);				
			bid.setNewDateBySpecificDate(newDate);
		}
		
		
	}
	

	
	/**
	 * This method sets the indicator depending on whether parts are present or not in the SO.
	 * @param dto 
	 * 
	 */
	private void validateParts(ServiceOrderDTO dto){
		int partsIndicator=0;
		if (dto.getPartsList() != null){
			if(dto.getPartsList().size()==1){
				partsDto = dto.getPartsList().get(0);
				if(StringUtils.isNotBlank(partsDto.getManufacturer())&&StringUtils.isNotBlank(partsDto.getModelNumber())&& dto.getPartsSupplier()==1){
					partsIndicator = 1;	
				}
			}else if (dto.getPartsList().size()>1&&dto.getPartsSupplier()==1){
				partsIndicator = 1;
			}
			
			setAttribute("partsIndicator",partsIndicator);
		}
		
	}
	private ServiceOrderDTO modifySummaryDTO(ServiceOrderDTO dto)
	{

		// Null out some fields based on User Role and Status
		if(dto != null)
		{
			modifyPanelGeneralInformation(dto);			
			modifyPanelContactInformation(dto);			
			modifyPanelPricing(dto);
			//modifyPanelRoutedProviders(dto);
		}
		
				
		
		return dto;		
	}

	

	private void modifyPanelGeneralInformation(ServiceOrderDTO dto)
	{
		if(get_commonCriteria() == null)
			return;
		
		String role = get_commonCriteria().getRoleType();
		if(role == null)
			return;
		
/* Commenting out empty blocks
		if(role.equalsIgnoreCase("provider"))
		{
		}
		else if(role.equalsIgnoreCase("buyer") || role.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER))
		{
		}
*/

		//Check the Sub Status value to Show/Hide "Change" button on the General Information section on JSP
		Integer status = dto.getStatus();
		if(status != null && 
				(status.intValue() == OrderConstants.ACCEPTED_STATUS ||
				 status.intValue() == OrderConstants.ACTIVE_STATUS ||
				 status.intValue() == OrderConstants.COMPLETED_STATUS||
				 status.intValue() == OrderConstants.PROBLEM_STATUS)){
			dto.setShowSubStatusChange(true);
		}
		else{
			dto.setShowSubStatusChange(false);
		}
		
		//Fetch Sub Statuses for the StatusId
		ArrayList<ServiceOrderStatusVO> statusSubStatuses = detailsDelegate.getSubStatusDetails(dto.getStatus(), get_commonCriteria().getRoleId());
		List<ServiceOrderSubStatusVO> subStatuses = null;
		for(ServiceOrderStatusVO statusVO : statusSubStatuses){
			if(dto.getStatus().intValue() == statusVO.getStatusId()){
				subStatuses = statusVO.getServiceOrderSubStatusVO();
				break;
			}
				
		}
		setAttribute("SOStatusSubStatuses", subStatuses);
		getUpsellInfo(dto);
		
		// set extra tax column for relay buyers
		setAttribute("displayTax", BuyerConstants.RELAY_BUYER_ID ==  Integer.parseInt(dto.getBuyerID()) || BuyerConstants.TECH_TALK_BUYER_ID ==  Integer.parseInt(dto.getBuyerID()));
		
		// Set buyerName to be firstName lastNameFirstInitial.
		String buyerName ="";
		
		if(dto.getBuyerContact() != null)
		{
			if(StringUtils.isNotEmpty(dto.getBuyerContact().getFirstName()))
			{				
				buyerName += dto.getBuyerContact().getFirstName() + " ";
			}
			if(StringUtils.isNotEmpty(dto.getBuyerContact().getLastName()))
			{
				buyerName += dto.getBuyerContact().getLastName().substring(0, 1);
				buyerName += ".";
			}
		}
		else
		{
			logger.error("BuyerContact is null for soID=" + dto.getId());
		}
		
		dto.setBuyerName(buyerName);
		
		if(dto.getScheduleHistory()==null){
			List<PreCallHistory> callHistories =  getDetailsDelegate().getScheduleHistory(dto.getId(),dto.getAcceptedVendorId());
			if(callHistories!=null){
				for(PreCallHistory callHistory:callHistories){
					if(callHistory!=null){
						callHistory.setFormattedDate();
						callHistory.setFormattedTime();
						callHistory.setDetailFormattedDate();
					}
				}
			}
			dto.setScheduleHistory(callHistories);
		}
		
	}

	
	
	private void modifyPanelContactInformation(ServiceOrderDTO dto)
	{
		if(get_commonCriteria() == null)
			return;
		
		String role = get_commonCriteria().getRoleType();
		if(role == null)
			return;
		
		Integer status = dto.getStatus();

		if(role.equalsIgnoreCase("provider"))
		{
			boolean shared = dto.isShareContactInd();
			if(
				!shared &&
				(status == OrderConstants.ROUTED_STATUS ||
				status == OrderConstants.CLOSED_STATUS)
				)
			{
				// Contact Information Panel
				SOContactDTO contact = dto.getLocationContact(); 
				if(contact != null)
				{
					contact.setIndividualName(null);
					contact.setStreetAddress(null);
					contact.setPhoneHome(null);
					contact.setPhoneWork(null);
					contact.setPhoneMobile(null);
					contact.setEmail(null);
					contact.setFax(null);
				}
				contact = dto.getLocationAlternateContact(); 
				if(contact != null)
				{
					contact.setIndividualName(null);
					contact.setStreetAddress(null);
					contact.setPhoneHome(null);
					contact.setPhoneWork(null);
					contact.setPhoneMobile(null);
					contact.setEmail(null);
					contact.setFax(null);
				}
				
				dto.setLocationNotes(null);
			}
		}		
	}
	
	private void modifyPanelPricing(ServiceOrderDTO dto)
	{
		if(get_commonCriteria() == null)
			return;
		
		String role = get_commonCriteria().getRoleType();
		if(role == null)
			return;
		
		Integer status = dto.getStatus();

		if(role.equalsIgnoreCase("provider"))
		{
			dto.setAccessFee(null);
			if(status == OrderConstants.ROUTED_STATUS)
			{
				//dto.setTotalSpendLimit(null);
			}
		}
	}
	
	private void modifyPanelRoutedProviders(ServiceOrderDTO dto)
	{
		if(get_commonCriteria() == null)
			return;
		
		String role = get_commonCriteria().getRoleType();
		if(role == null)
			return;
		
		List<ProviderResultVO> routedResourcesForFrim=dto.getRoutedResourcesForFirm();
		
		if(role.equalsIgnoreCase("provider"))
		{
			if(routedResourcesForFrim == null || routedResourcesForFrim.isEmpty()){
				return;
			}else{
				List<ProviderResultVO> routedResourcesForPanel=new ArrayList<ProviderResultVO>();
				for(ProviderResultVO routedResource:routedResourcesForFrim){
					if(routedResource.getProviderRespid()!=2){
						routedResourcesForPanel.add(routedResource);
					}
				}
				dto.setRoutedResourcesForFirm(routedResourcesForPanel);
			}
		}
	}
	
	private void setPanelsDisplayed()
	{
			
		String groupId = getParameter(GROUP_ID);
		if(!StringUtils.isBlank(groupId))
		{
			//setPanelsForOrderGroup();
			setAttribute(GROUP_ID, groupId);
			setPanelsForServiceOrder();
			
			// Get list of Service Orders, need to get the more detailed ServiceOrder VO, rather than the lightly populated
			// ServiceOrderSearchResultsVO
			List<ServiceOrderSearchResultsVO> oldServiceOrders = somDelegate.getServiceOrdersForGroup(groupId);						
			List<ServiceOrderDTO> serviceOrders = new ArrayList<ServiceOrderDTO>();
			ServiceOrderDTO so;
			ServiceOrderDTO first=null;
			for(ServiceOrderSearchResultsVO vo : oldServiceOrders)
			{				
				try
				{
					so = getDetailsDelegate().getServiceOrder(vo.getSoId(), get_commonCriteria().getRoleId(), null);
					getUpsellInfo(so);
					
					// set extra tax column for relay buyers
					setAttribute("displayTax", BuyerConstants.RELAY_BUYER_ID ==  Integer.parseInt(so.getBuyerID()) || BuyerConstants.TECH_TALK_BUYER_ID ==  Integer.parseInt(so.getBuyerID()));
					
					//Sorting summaryDTO based on providername
				    if(so.getStatus()== OrderConstants.ROUTED_STATUS){
				    	if(null!=so&& null!=so.getRoutedProvExceptCounterOffer())
						{
							List<ProviderResultVO>providerList=sortProviderNameDefault(so.getRoutedProvExceptCounterOffer());
							so.setRoutedProvExceptCounterOffer(providerList);
							//Changing code SL-19820
							/*if (getSession().getServletContext().getAttribute(Constants.SESSION.SO_GROUP_PROVIDER_LIST) == null){
							getSession().getServletContext().setAttribute(Constants.SESSION.SO_GROUP_PROVIDER_LIST, providerList);
							}*/
							           
							setAttribute(Constants.SESSION.SO_GROUP_PROVIDER_LIST, providerList);
						}
				    }
					serviceOrders.add(so);
					
					
					if(first == null)
					{
						first = so;
						//Changing code SL-19820
						setAttribute("firstServiceOrder", first);
					}
				}
				catch (DataServiceException e)
				{
					e.printStackTrace();
				}
			}
			
			setAttribute("serviceOrders", serviceOrders);				
		}
		else
		{
			setPanelsForServiceOrder();
		}
		
	}
	
	/*private void sortProvderList(){
		ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		if(null != dto){
		List<ProviderResultVO> sortedProviderList =(List<ProviderResultVO>) new ArrayList(dto.getRoutedProvExceptCounterOffer());
		
		
		}
	}*/
	private void setPanelsForServiceOrder()
	{
		//Changing code SL-19820
		//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO dto = getCurrentServiceOrderFromRequest();
		List<Integer> statusList =Arrays.asList(ACTIVE_ACCEPTED_PROBLEM);
		//SL 15642 Start-Changes for permission based price display in Service Order Details
		if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER)){
			if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59")){
				setAttribute("viewOrderPricing",true);
			}
		}
		 /**This method will display invoice parts section based on role 
		  *type and so status in view/edit mode*/
		if(null != dto){
			List<ProviderInvoicePartsVO> invoiceParts = dto.getInvoiceParts();
			if(null!= invoiceParts && !(invoiceParts.isEmpty())){
				setAttribute("partExistInd","partExist");			
			}else{
				setAttribute("partExistInd","partNotExist");			

			}
			if(null!=dto.getInvoicePartsInd() && "NO_PARTS_REQUIRED".equals(dto.getInvoicePartsInd())){
			setAttribute("partExistInd",dto.getInvoicePartsInd());	 		
			}
			
			setAttribute("showInvoicePartsEdit",false);
			setAttribute("showInvoicePartsView",false);
			Integer status = dto.getStatus();
			String buyerId = dto.getBuyerID();
			String roleType = OrderConstants.PROVIDER;
			if(get_commonCriteria() != null){
				roleType = get_commonCriteria().getRoleType();
			}
			if(INHOME_BUYER.equals(buyerId)){
				 if(OrderConstants.PROVIDER.equalsIgnoreCase(roleType)){
					  if(null != status && statusList.contains(status)){
						  setAttribute("showInvoicePartsEdit",true);
					  }else{
						  setAttribute("showInvoicePartsView",true);
					  }
				 }else if(OrderConstants.BUYER.equalsIgnoreCase(roleType)){
					      setAttribute("showInvoicePartsView",true);
					      
				 }
			}
		}
		
		//SL 15642 End-Changes for permission based price display in Service Order Details
		
		// Price Model - Zero Price Bid or Bulletin Board
		if(
			OrderConstants.SHOW_B2C_PROVIDER_SCREENS &&				
			dto != null &&
			(Constants.PriceModel.ZERO_PRICE_BID.equals(dto.getPriceModel()) || Constants.PriceModel.BULLETIN.equals(dto.getPriceModel())) &&
			get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER)
			)
		{
			setAttribute("showGeneralInfo", true);
			setAttribute("showScopeOfWork",true);
			
			setAttribute("showDocumentsAndPhotos", true);
			setAttribute("showServiceOrderBids", true);	
			
			// Always show bid notes for zero price bid order
			setAttribute("showBidNotes", true);
			
			// Don't show parts or contact info panels for simple buyers' orders
			if (dto.getBuyerRoleId() != null && dto.getBuyerRoleId() != 5) {
				setAttribute("showParts", true);
				setAttribute("showContactInfo", true);
			}
		}
		// Price Model - Name your Price
		else
		{		
			setAttribute("showGeneralInfo", true);
			setAttribute("showScopeOfWork",true);
			
			if(displayContactInfoPanel())
				setAttribute("showContactInfo", true);
			
			// if we are not showing 'contact information' panel
			// then display a little contact section in 'service order details' panel
			
			setAttribute("showDocumentsAndPhotos", true);
			
			// If the buyer is a simple buyer, or if the order has the ALLOW_COMMUNICATION feature set,
			// then show the Bid Notes panel.
			boolean showBidNotes = false;
			if (get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER)) {
				if (dto == null) {
					showBidNotes = false;
				} else if (dto.getBuyerRoleId() != null && dto.getBuyerRoleId() == OrderConstants.SIMPLE_BUYER_ROLEID) {
					showBidNotes = true;
				} else {
					showBidNotes = detailsDelegate.validateServiceOrderFeature(dto.getId(), OrderConstants.ALLOW_COMMUNICATION);
				}
			}
			setAttribute("showBidNotes", showBidNotes);
			//SLm 119 : Displaying Parts Section if service order has parts
			boolean showServiceOrderParts =false;
			if(null != dto){
				List<SOPartsDTO> soPartsList =dto.getPartsList();
				if(null != soPartsList &&(soPartsList.size() > 0)){
					//Parts Exists for the service order
					showServiceOrderParts =true;
				}
			}
			setAttribute("showParts", showServiceOrderParts);
			
			String role="buyer";
			if(get_commonCriteria() != null)
				role = get_commonCriteria().getRoleType();
			
			if(role.equalsIgnoreCase(OrderConstants.BUYER)  || role.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER))
				setAttribute("showPricingBuyer", true);
			else if(role.equalsIgnoreCase("provider"))
				setAttribute("showPricingProvider", true);
			
			if(get_commonCriteria().getRoleType().equalsIgnoreCase("provider")){
				// show provider panel if logged in user is a Provider Admin 
				if(get_commonCriteria().getSecurityContext().isPrimaryInd()||get_commonCriteria().getSecurityContext().isDispatchInd()){
					setAttribute("showProviderPanel", true);
				}
					
				setAttribute("showTermsAndConditions", true);
				setVendorBucksIndicator();
				boolean acceptBlockFlag = true;
				if (hasConditionalOfferPending() || checkCurrentOrderType() == OrderConstants.ORDER_TYPE_CHILD ) { // not allowed at child level; allowed either at group level or for individual order
					acceptBlockFlag = false;
				}
				setAttribute("showAcceptBlock",acceptBlockFlag);
			}
			String groupId = getParameter(GROUP_ID);
			//SL-19820
			String soId = getParameter("soId");
			if((null != dto && (!(dto.isCarSO()))) || (!StringUtils.isBlank(groupId))){
				setCaptcha();
			}else{
				//Changing code SL-19820
				//getSession().setAttribute(Constants.CAPTCHA_ENABLE, "false");
				setAttribute(Constants.CAPTCHA_ENABLE, "false");
			}
			//SL-19820
			String id = null;
			if(StringUtils.isNotBlank(groupId)){
				id = groupId;
			}else if(StringUtils.isNotBlank(soId)){
				id = soId;
			}
			getSession().setAttribute(Constants.CAPTCHA_ENABLE+"_"+id, getAttribute(Constants.CAPTCHA_ENABLE));
		}
	}
	
	private boolean hasConditionalOfferPending() {
		//Changing code Sl-19820
		//String soId = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		Integer routedResourceId = new Integer(0);
		//Changing code Sl-19820
		//String groupId = (String)getSession().getAttribute(GROUP_ID);
		String groupId = (String) getAttribute(GROUP_ID);
		/*if (getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID) != null) {
			routedResourceId = Integer.valueOf((String) getSession()
					.getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
		}*/
		if(StringUtils.isNotBlank(getParameter("resId"))){
			routedResourceId = Integer.parseInt(getParameter("resId"));
			setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID, routedResourceId);
		}

		if (SLStringUtils.isNullOrEmpty(soId) == false) {
			return detailsDelegate.hasCondOfferPending(soId, routedResourceId);
		}
		else if(SLStringUtils.isNullOrEmpty(groupId) == false)
		{
			// retain one child orderId to return to the child details after group acceptance
			String firstChildSoId = detailsDelegate.getFirstChildInGroup(groupId);
			return detailsDelegate.hasCondOfferPending(firstChildSoId, routedResourceId);
		}
		return false;
	}
	
	private int checkCurrentOrderType() {
		int orderType = 0;
		//Changing code SL-19820
		//String groupID = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
		String groupID = (String)getAttribute(OrderConstants.GROUP_ID);
		if (StringUtils.isNotBlank(groupID)) {
			return OrderConstants.ORDER_TYPE_GROUP;
		} else {
			//Changing code SL-19820
			//String soID = (String)getSession().getAttribute(OrderConstants.SO_ID);
			String soID = (String) getAttribute(OrderConstants.SO_ID);
			if (soID != null) {
				ServiceOrderDTO serviceOrderDTO = getCurrentServiceOrderFromRequest();
				if (serviceOrderDTO != null && StringUtils.isNotBlank(serviceOrderDTO.getParentGroupId())) {
					return OrderConstants.ORDER_TYPE_CHILD;
				} else {
					return OrderConstants.ORDER_TYPE_INDIVIDUAL;
				}
			} else {
				logger.error("SO_ID not present in session; return order type = 0 !!");
			}
		}
		logger.info("Order Type = ["+orderType+"]");
		return orderType;
	}
	
	private void setVendorBucksIndicator() {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		if (securityContext.isPrimaryInd()) {
			setAttribute(PRIMARY_VENDOR_INDICATOR, 1);
		}
		else
			setAttribute(PRIMARY_VENDOR_INDICATOR, 0);
		try {
			Integer indicator = iTermsAndCondDelegate.getData(securityContext.getCompanyId());
			//changing code SL-19820
			//getSession().setAttribute(VENDOR_BUCK_INDICATOR, indicator);
			setAttribute(VENDOR_BUCK_INDICATOR, indicator);
		} catch (Exception e) {
			logger.error("Error setting the vendor buck indicator");
		}
		
	}

	private void setCaptcha() {
		String isCaptchaEnable= PropertiesUtils.getPropertyValue("captcha.enable");
		//Changing code SL-19820
		//getSession().setAttribute(Constants.CAPTCHA_ENABLE, isCaptchaEnable);
		setAttribute(Constants.CAPTCHA_ENABLE, isCaptchaEnable);
	}
	
	
	private boolean displayContactInfoPanel()
	{
		//Changing code Sl-19820
		/*String role = (String)getSession().getAttribute("detailsRole");
		String status = (String)getSession().getAttribute("detailsStatus");*/

		String role = get_commonCriteria().getRoleType();
		String status = null;
		if(null != getCurrentServiceOrderFromRequest()){
			status = getCurrentServiceOrderFromRequest().getPrimaryStatus();
		}
		
		if(OrderConstants.PROVIDER_ROLE.equalsIgnoreCase(role))
		{
			if(
					SOConstants.ACCEPTED.equalsIgnoreCase(status) ||
					ACTIVE.equalsIgnoreCase(status) ||
					COMPLETED.equalsIgnoreCase(status) ||
					SOConstants.PROBLEM.equalsIgnoreCase(status)
				)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return true;		
	}
	
	public String acceptServiceOrder(){
		
		String response="";
		Integer routedResourceId= -1;
		String assignee = "";
		String soID;
		//Sl-19820
		//String groupId = (String)getSession().getAttribute(GROUP_ID);
		String groupId = (String) getAttribute(GROUP_ID);
		String soId = (String) getAttribute(SO_ID);
		String firstChildSoId = "";
		String returnValue=SO_ACCEPT_ERROR;
		String orderId="";
		int remainingTimeToAcceptSO = 0;
		ServiceDatetimeSlot sdt = null;
		//Integer resourceId = get_commonCriteria().getVendBuyerResId();
		if(getRequest().getParameter("assignee") != null){
			assignee = (String)getRequest().getParameter("assignee");
		}
		
		if(!StringUtils.isBlank((String)getRequest().getParameter("selectedSlotId")) && !StringUtils.isBlank((String)getRequest().getParameter("selectedPreferenceId"))){
			sdt = new ServiceDatetimeSlot();
			sdt.setSlotId(Integer.valueOf((String)getRequest().getParameter("selectedSlotId")));
			sdt.setPreferenceInd((String)getRequest().getParameter("selectedPreferenceId"));
			sdt.setSoId(soId);
		}
		
		this.setReturnURL("/serviceOrderMonitor.action");
		//Sl-19820
		String id = null;
		if(StringUtils.isNotBlank(groupId)){
			id = groupId;
		}else if(StringUtils.isNotBlank(soId)){
			id = soId;
		}
		//String captchaEnable = (String)getSession().getAttribute(Constants.CAPTCHA_ENABLE);
		String captchaEnable = (String)getSession().getAttribute(Constants.CAPTCHA_ENABLE+"_"+id);
		if (captchaEnable.equals("true")) {
			String code = getParameter("securityCode");
			String position = getParameter("position");
			//Sl-19820
			//String actualCode = (String) getSession().getAttribute("captcha");
			String actualCode = (String) getSession().getAttribute("captcha_"+id);
			
			if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(actualCode)) {
				//SL-19280
				/*getSession().setAttribute(CAPTCHA_ERROR, "The Characters did not match. Please enter the characters  you see.");
				getSession().setAttribute(CAPTCHA_ERROR_POSITION, position);*/
				getSession().setAttribute(CAPTCHA_ERROR+"_"+id, "The Characters did not match. Please enter the characters  you see.");
				getSession().setAttribute(CAPTCHA_ERROR_POSITION+"_"+id, position);
				setAttribute(CAPTCHA_ERROR, "The Characters did not match. Please enter the characters  you see.");
				setAttribute(CAPTCHA_ERROR_POSITION, position);
				this.soId = soId;
				this.groupId = groupId;
				return returnValue;
			}
		}
		
		//Provider Accept
		//SL-19820
		if((String) getAttribute(OrderConstants.SO_ID) == null || ((String) getAttribute(OrderConstants.SO_ID)).length()<1){
			if ((groupId == null || groupId.length() < 1) ){
				this.setErrorMessage("Service Order # not found");
				return ERROR;
			}
		}
		//SL-19820
		if( getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID) == null || ((String) getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID)).length()<1){
			this.setErrorMessage("Problem with Service Order #.");
			return ERROR;
		}
		TermsAndConditionsVO termAndCond = (TermsAndConditionsVO)getSession().getAttribute("acceptSOTermsAndCond");
		soID = (String) getAttribute(OrderConstants.SO_ID);
		if("typeProvider".equals(assignee) || StringUtils.isNotBlank(groupId)){
			if(getRequest().getParameter("acceptedFirmResId") != null){
				routedResourceId=Integer.parseInt((String)getRequest().getParameter("acceptedFirmResId"));
			}else{
				routedResourceId = Integer.parseInt((String) getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
			}
		}else{
			routedResourceId = null;
		}
		if(get_commonCriteria().getRoleType().equalsIgnoreCase("provider")){
			if(StringUtils.isNotBlank(groupId)){
				orderId=groupId;
				returnValue=ERROR;

				try{
					remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptGrpOrder(orderId,routedResourceId);
				}
				catch(Exception e){
				}
				
				//check if timer has expired
				if (remainingTimeToAcceptSO > 0){
					returnValue = SO_ACCEPT_ERROR;
					//Sl-19820
					//getSession().setAttribute("message", "Order " +  orderId + " was not successfully accepted. Please try again.");
					setAttribute("message_"+id, "Order " +  orderId + " was not successfully accepted. Please try again.");
				}else{

					try {
						// retain one child orderId to return to the child details after group acceptance
						firstChildSoId = detailsDelegate.getFirstChildInGroup(groupId);
											
						response = getDetailsDelegate().groupedServiceOrderAccept(	groupId,
								"", //**userName**this field probably needs to be dropped
								routedResourceId, 
								_commonCriteria.getCompanyId(),termAndCond.getTermsCondId(), true,assignee);
						
						
						if(null != sdt){
							updateServiceDatetimeSlot(sdt);	
						}
						
					} catch (BusinessServiceException e) {
						//Once grouped order is accepted, the groupId no longer exist. Hence Business exception.
						//this.setErrorMessage(GROUP_ORDER_ALREADY_ACCEPTED);
						//changing the logic for routing to SOD and SOM to show the error message for grouped orders						
						firstChildSoId = detailsDelegate.getFirstChildInOrigGroup(groupId);
						returnValue=SO_ACCEPT_ERROR;
						//SL-19820
						getRequest().setAttribute("message", response);
						//SL-19820
						//getSession().setAttribute("somMessageGrpOrderAlreadyAccepted", orderId);
						getSession().setAttribute("somMessageGrpOrderAlreadyAccepted_"+id, orderId);
						logger.info("error occured while accepting grouped Order grouped Order-->" + soID);
						//getSession().setAttribute(OrderConstants.SO_ID, firstChildSoId);
						setAttribute(OrderConstants.SO_ID, firstChildSoId);
						// set group Id = null so that it won't be accessed in details controller as this group no more  exists
						//getSession().setAttribute(GROUP_ID, null);
						setAttribute(GROUP_ID, null);
						this.soId = firstChildSoId;
						this.groupId = null;
						e.printStackTrace();
						return returnValue;
					} catch (Exception e){
						//Once grouped order is accepted, the groupId no longer exist. Hence Business exception.
						//this.setErrorMessage(GROUP_ORDER_ALREADY_ACCEPTED);
						//changing the logic for routing to SOD and SOM to show the error message for grouped orders
						firstChildSoId = detailsDelegate.getFirstChildInOrigGroup(groupId);
						returnValue=SO_ACCEPT_ERROR;
						getRequest().setAttribute("message", response);
						//SL-19820
						//getSession().setAttribute("somMessageGrpOrderAlreadyAccepted", orderId);
						getSession().setAttribute("somMessageGrpOrderAlreadyAccepted_"+id, orderId);
						logger.info("error occured while accepting grouped Order grouped Order-->" + soID);
						//getSession().setAttribute(OrderConstants.SO_ID, firstChildSoId);
						setAttribute(OrderConstants.SO_ID, firstChildSoId);
						// set group Id = null so that it won't be accessed in details controller as this group no more  exists
						//getSession().setAttribute(GROUP_ID, null);
						setAttribute(GROUP_ID, null);
						e.printStackTrace();
						this.soId = firstChildSoId;
						this.groupId = null;
						return returnValue;						
					}
				}

			}else{
				orderId=soID;
				this.soId = orderId;
				this.groupId = null;
				
					try{
						if("typeProvider".equals(assignee)){
							remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptSO(orderId,routedResourceId);
						}else{
							remainingTimeToAcceptSO =detailsDelegate.getTheRemainingTimeToAcceptSOForFirm(orderId,_commonCriteria.getCompanyId());
						}
					}
					catch(Exception e){
					}
				
					//check if timer has expired
					if (remainingTimeToAcceptSO > 0){
						returnValue = SO_ACCEPT_ERROR;
						//SL-19820
						//getSession().setAttribute("message", "Order " +  orderId + " was not successfully accepted. Please try again.");
						getSession().setAttribute("message_"+orderId, "Order " +  orderId + " was not successfully accepted. Please try again.");
						setAttribute("message", "Order " +  orderId + " was not successfully accepted. Please try again.");
					}else{
						try{
							response = getDetailsDelegate().serviceOrderAccept(	soID, 
							"", //**userName**this field probably needs to be dropped
							routedResourceId, 
							_commonCriteria.getCompanyId(),termAndCond.getTermsCondId(), true,assignee);
							if(null != sdt){
							updateServiceDatetimeSlot(sdt);	
							}
							
						}catch(Exception e){
							logger.info("error occured while accepting grouped Order grouped Order-->" + soID);
							e.printStackTrace();
							return returnValue;													
						}
					}
			}
			
			if(response.contains("SUCCESS") || response.trim().equalsIgnoreCase("SUCCESS")){
				
				if(StringUtils.isNotBlank(firstChildSoId)){
					//SL-19820
					//getSession().setAttribute(OrderConstants.SO_ID, firstChildSoId);
					setAttribute(OrderConstants.SO_ID, firstChildSoId);
					// set group Id = null so that it won't be accessed in details controller as this group no more  exists
					//getSession().setAttribute(GROUP_ID, null);
					setAttribute(GROUP_ID, null);
					this.soId = firstChildSoId;
					this.groupId = null;
				}
				//this.set_soId(soID);
				//Sl-19820
				//setCurrentSOStatusCodeInSession(OrderConstants.ACCEPTED_STATUS);
				setCurrentSOStatusCodeInRequest(OrderConstants.ACCEPTED_STATUS);
				return "providersuccess";
				
			} else if(response.equalsIgnoreCase(OrderConstants.ORDER_BEING_EDITED)){
				returnValue=SO_ACCEPT_ERROR;
				//SL-19820
				/*getSession().setAttribute("message", "Order " +  orderId
						+ " is currently being edited by the user.");*/
				getSession().setAttribute("message_"+orderId, "Order " +  orderId
						+ " is currently being edited by the user.");
			}else if(response.equalsIgnoreCase(OrderConstants.ORDER_IN_CANCELLED_STATUS)){
				returnValue=SO_ACCEPT_ERROR;
				getRequest().setAttribute("message", response);
				//SL-19820
				//getSession().setAttribute("somMessageOrderCancelled", orderId);
				getSession().setAttribute("somMessageOrderCancelled_"+orderId, orderId);
				setAttribute("somMessageOrderCancelled", orderId);
			} else if(response.equalsIgnoreCase(OrderConstants.ORDER_ACCEPTED_BY_ANOTHER_PROVIDER) 
					|| response.indexOf(OrderConstants.ORDER_ACCEPTED_BY_ANOTHER_PROVIDER_OF) != -1){
				returnValue=SO_ACCEPT_ERROR;
				getRequest().setAttribute("message", response);
				//SL-19820
				//getSession().setAttribute("somMessageOrderAlreadyAccepted", orderId);
				getSession().setAttribute("somMessageOrderAlreadyAccepted_"+orderId, orderId);
				setAttribute("somMessageOrderAlreadyAccepted", orderId);
			}else {
				getRequest().setAttribute("message", response);
				this.setErrorMessage(response);
			}
		} 
		//Buyer Accept
		else if(get_commonCriteria().getRoleType().equalsIgnoreCase("buyer")  || get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)){
			
			//BUYER 
			//Need to configure struts.xml for the flow
			return "buyersuccess";
		}
		return returnValue;
	}

	public AdditionalPaymentVO getAdditionalPaymentInfo(String soId,Boolean isSecureInfoViewable) throws Exception
	{
		return getServiceOrderUpsellBO().getAdditionalPaymentInfo(soId,isSecureInfoViewable);
	}
	
	public String updateSubStatus()
	{
		/*LMA...Sears00044851...*/
		try{
			//SL-19820
			String soId = (String) getAttribute(OrderConstants.SO_ID);
			Integer status = Integer.parseInt(getParameter("statusId"));
			updateSOSubStatus(subStatusIdChanged, soId, status);
		}
		catch(Exception e ){
			logger.error("updateSOSubStatus()-->EXCEPTION-->", e);
		}
		
		return GOTO_COMMON_DETAILS_CONTROLLER;		
	}
	
	/**Priority 5B changes
	 * method to update model & serial no. by buyer agent
	 * @return String
	 */
	public String updateCustomReference(){
		
		try{
			
			String refType = (String) getRequest().getParameter(Constants.REF_TYPE);
			String refVal = URLDecoder.decode((String) getRequest().getParameter(Constants.REF_VAL), UTF_8);
			refVal = refVal.replaceAll("-prcntg-", "%");
			String refValOld = URLDecoder.decode((String) getRequest().getParameter(Constants.REF_OLD_VAL), UTF_8);
			refValOld = refValOld.replaceAll("-prcntg-", "%");
			String soId = (String) getAttribute(OrderConstants.SO_ID);
			String invalidInd = (String) getRequest().getParameter(Constants.INVALID_IND);
						
			if(StringUtils.isNotBlank(soId) && StringUtils.isNotBlank(refType)){
				
				boolean updated = false;
				Map<String, Object> sessionMap = ActionContext.getContext().getSession();
				SecurityContext securityContext = (SecurityContext) sessionMap.get(Constants.SESSION.SECURITY_CONTEXT);
				
				if(StringUtils.isBlank(refValOld) && StringUtils.isNotBlank(refVal)){
					//insert custom ref value
					detailsDelegate.insertSOCustomReference(soId, refType, refVal, securityContext);
					updated = true;
					
				}else if(StringUtils.isNotBlank(refValOld) && StringUtils.isBlank(refVal)){
					//delete custom ref value
					detailsDelegate.deleteSOCustomReference(soId, refType);
					updated = true;
					
				}else if(StringUtils.isNotBlank(refValOld) && StringUtils.isNotBlank(refVal)){
					//update custom ref value
					detailsDelegate.updateSOCustomReference(soId, refType, refVal, refValOld, securityContext);
					updated = true;
				}
				
				if(updated){
					
					//Populate logging object
					SoLoggingVo soLoggingVO = new SoLoggingVo();
					soLoggingVO.setServiceOrderNo(soId);
					soLoggingVO.setActionId(Constants.BUYER_EDIT_ID);
					if(refType.equalsIgnoreCase(InHomeNPSConstants.MODEL)){
						soLoggingVO.setComment(Constants.BUYER_MODEL_EDIT);
					}
					else if(refType.equalsIgnoreCase(InHomeNPSConstants.SERIAL_NUMBER)){
						soLoggingVO.setComment(Constants.BUYER_SERIAL_EDIT);
					}
					soLoggingVO.setOldValue(refValOld);
					soLoggingVO.setNewValue(refVal);
					Date date = new Date();
					soLoggingVO.setCreatedDate(date);
					soLoggingVO.setModifiedDate(date);
					soLoggingVO.setModifiedBy(securityContext.getUsername());
					soLoggingVO.setRoleId(OrderConstants.BUYER_ROLEID);
					soLoggingVO.setCreatedByName(detailsDelegate.getBuyerName(securityContext.getVendBuyerResId()));
					soLoggingVO.setEntityId(securityContext.getCompanyId());
					
					//insert order history
					detailsDelegate.insertSoLogging(soLoggingVO);
					
					//update invalid_model_serial_ind in so_workflow_controls
					if(null != invalidInd){
						if(InHomeNPSConstants.MODEL.equalsIgnoreCase(refType)){
							if(Constants.BOTH.equalsIgnoreCase(invalidInd)){
								invalidInd = InHomeNPSConstants.SERIAL_NUMBER.toUpperCase();
							}else if(InHomeNPSConstants.MODEL.equalsIgnoreCase(invalidInd)){
								invalidInd = null;
							}
						}
						else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(refType)){
							if(Constants.BOTH.equalsIgnoreCase(invalidInd)){
								invalidInd = InHomeNPSConstants.MODEL.toUpperCase();
							}else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(invalidInd)){
								invalidInd = null;
							}
						}
						detailsDelegate.updateModelSerialInd(soId, invalidInd);
					}
				}
			}
			
			//set the error messages and new invalid ind
			ServiceOrderDTO soDTO = new ServiceOrderDTO();
			soDTO.setInvalidModelSerialInd(invalidInd);
			soDTO = detailsDelegate.getCustomReferences(soId, soDTO);
			setModelSerialPanel(soDTO);
		}
		catch(BusinessServiceException e ){
			logger.error("updateCustomReference()-->BusinessServiceException-->", e);
		}
		catch(UnsupportedEncodingException e){
			logger.error("updateCustomReference()-->UnsupportedEncodingException-->", e);
		}
		catch(Exception e ){
			logger.error("updateCustomReference()-->EXCEPTION-->", e);
		}

		return SUCCESS;
	}
	
	
	public String updateBuyerReference(){
		
		try{
			//SL-19820
			String soId = (String) getAttribute(OrderConstants.SO_ID);
			updateSOCustomReference(refType, refVal, refValOld, soId);
		}
		catch(Exception e ){
			logger.error("editBuyerReference()-->EXCEPTION-->", e);
		}
		return GOTO_COMMON_DETAILS_CONTROLLER;
	}
	
	/**
	 * @return the subStatusIdChanged
	 */
	public Integer getSubStatusIdChanged() {
		return subStatusIdChanged;
	}

	/**
	 * @param subStatusIdChanged the subStatusIdChanged to set
	 */
	public void setSubStatusIdChanged(Integer subStatusIdChanged) {
		this.subStatusIdChanged = subStatusIdChanged;
	}

	public ISOMonitorDelegate getSomDelegate() {
		return somDelegate;
	}

	public void setSomDelegate(ISOMonitorDelegate somDelegate) {
		this.somDelegate = somDelegate;
	}
	public IServiceOrderUpsellBO getServiceOrderUpsellBO() {
		return serviceOrderUpsellBO;
	}
	public void setServiceOrderUpsellBO(IServiceOrderUpsellBO serviceOrderUpsellBO) {
		this.serviceOrderUpsellBO = serviceOrderUpsellBO;
	}

	public void setITermsAndCondDelegate(ITermsAndCondDelegate termsAndCondDelegate) {
		iTermsAndCondDelegate = termsAndCondDelegate;
	}

	public IActivityLogHelper getHelper()
	{
		return helper;
	}

	public void setHelper(IActivityLogHelper helper)
	{
		this.helper = helper;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getRefVal() {
		return refVal;
	}

	public void setRefVal(String refVal) {
		this.refVal = refVal;
	}

	public String getRefValOld() {
		return refValOld;
	}

	public void setRefValOld(String refValOld) {
		this.refValOld = refValOld;
	}
	public List<ProviderResultVO> getSortByNameAsc() {
		return sortByNameAsc;
	}

	public void setSortByNameAsc(List<ProviderResultVO> sortByNameAsc) {
		this.sortByNameAsc = sortByNameAsc;
	}

	public List<ProviderResultVO> getSortByNameDes() {
		return sortByNameDes;
	}

	public void setSortByNameDes(List<ProviderResultVO> sortByNameDes) {
		this.sortByNameDes = sortByNameDes;
	}

	public List<ProviderResultVO> getSortByDistAsc() {
		return sortByDistAsc;
	}

	public void setSortByDistAsc(List<ProviderResultVO> sortByDistAsc) {
		this.sortByDistAsc = sortByDistAsc;
	}

	public List<ProviderResultVO> getSortByDistDes() {
		return sortByDistDes;
	}

	public void setSortByDistDes(List<ProviderResultVO> sortByDistDes) {
		this.sortByDistDes = sortByDistDes;
	}

	public String loadTimer(){
		Integer acceptedResourceId= 0;
		String assignee = (String)(getRequest().getParameter("assignee"));
		int orderType = checkCurrentOrderType(); // returns OrderConstants.ORDER_TYPE_xxxxxx
		int remainingTimeToAcceptSO = 0;
		
		// Set text above buttons here.
		if (orderType == OrderConstants.ORDER_TYPE_GROUP) {
			//SL-19820
			//OrderGroupVO orderGroupVO = (OrderGroupVO)getSession().getAttribute(THE_GROUP_ORDER);
			String groupId = (String) getAttribute(GROUP_ID);
			try{
				acceptedResourceId= Integer.parseInt(getRequest().getParameter("acceptedResourceId"));
				remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptGrpOrder(groupId, acceptedResourceId);
			}
			catch(Exception e){
			}
		} else {

			try{
				//Sl-19820
				String soId = (String) getAttribute(SO_ID);
				if("typeProvider".equals(assignee)){
					acceptedResourceId= Integer.parseInt(getRequest().getParameter("acceptedResourceId"));
					remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptSO(soId, acceptedResourceId);
				}else{
					remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptSOForFirm(soId, get_commonCriteria().getCompanyId());
				}
			}
			catch(Exception e){
			}
		}
		setAttribute("xTimerSeconds",remainingTimeToAcceptSO);		
		return SUCCESS;
	}

	public String setSorting() {
		List<ProviderResultVO> listAttribute =new ArrayList<ProviderResultVO>();
		List<ProviderResultVO> providerVOList = new ArrayList<ProviderResultVO>();
		ServiceOrderDTO dto = new ServiceOrderDTO();
		String groupInd = (String)getRequest().getParameter("isGroup");
		if(null != groupInd && TYPE_GROUP.equals(groupInd)){
			providerVOList = (List<ProviderResultVO>)getSession().getServletContext().getAttribute(Constants.SESSION.SO_GROUP_PROVIDER_LIST);
		}else{
			dto=getCurrentServiceOrderFromSession();
			if(null != dto)
			providerVOList = dto.getRoutedProvExceptCounterOffer();
		}
		
		String sortProperty = getRequest().getParameter("sortProperty");
		String sortOrder = getRequest().getParameter("sortOrder");
		
		if (StringUtils.isNotEmpty(sortOrder)
				&& StringUtils.isNotEmpty(sortProperty)) {
			if (sortProperty.equalsIgnoreCase(SORT_PROPERTY_NAME)) {
				for (ProviderResultVO vo : providerVOList) {
					if (vo != null
							&& (StringUtils.isNotEmpty(vo.getProviderFirstName()) || StringUtils
									.isNotEmpty(vo.getProviderFirstName()))) {
						vo.setProviderFullName(vo.getProviderFirstName()
								+ vo.getProviderLastName());

					}
				}
				if (sortOrder.equalsIgnoreCase(SORT_ORDER_ASCENDING)) {
					//listAttribute=getSortByNameAsc();
					listAttribute.addAll(sortByNameAsc(providerVOList));
				} else if (sortOrder.equalsIgnoreCase(SORT_ORDER_DESCENDING)) {
                   // listAttribute=(List<ProviderResultVO>)getRequest().getAttribute("nameDes");
					listAttribute.addAll(sortByNameDes(providerVOList));
				}

			} else if (sortProperty.equalsIgnoreCase(SORT_PROPERTY_DISTANCE)) {
				if (sortOrder.equalsIgnoreCase(SORT_ORDER_ASCENDING)) {
                  // listAttribute=(List<ProviderResultVO>) getRequest().getAttribute("distAsc");
					listAttribute.addAll(sortByDistAsc(providerVOList));
				} else if (sortOrder.equalsIgnoreCase(SORT_ORDER_DESCENDING)) {
                  // listAttribute=(List<ProviderResultVO>) getRequest().getAttribute("distDesc");
					listAttribute.addAll(sortByDistDes(providerVOList));
				}
			}

		}
		
		dto.setRoutedProvExceptCounterOffer(listAttribute);
		setAttribute("summaryDTO", dto);
		return "success";
		
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
	
	/**
	 * priority 5B changes
	 * Set warning messages if model/serial no is invalid
	 * @param soLoggingVO
	 * @return 
	 * @throws BusinessServiceException
	 */
	private void setModelSerialPanel(ServiceOrderDTO soDTO){
		
		if(null != soDTO){
			
			List<String> errorMsgs = new ArrayList<String>();
			List<SOWSelBuyerRefDTO> customRefs = new ArrayList<SOWSelBuyerRefDTO>();
			
			//if model/serial no is invalid
			//indicator will be set in so_workflow_controls
			if(null != soDTO.getInvalidModelSerialInd()){
				
				if(Constants.BOTH.equalsIgnoreCase(soDTO.getInvalidModelSerialInd())){
					errorMsgs.add(Constants.MODEL_INVALID_ERROR);
					errorMsgs.add(Constants.SERIAL_INVALID_ERROR);
				}
				else if(InHomeNPSConstants.MODEL.equalsIgnoreCase(soDTO.getInvalidModelSerialInd())){
					errorMsgs.add(Constants.MODEL_INVALID_ERROR);
				}
				else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(soDTO.getInvalidModelSerialInd())){
					errorMsgs.add(Constants.SERIAL_INVALID_ERROR);
				}
			}
			
			//Set model number, serial number and invalid indicator in request
			if(null != soDTO.getSelByerRefDTO() && !soDTO.getSelByerRefDTO().isEmpty()){
				for(SOWSelBuyerRefDTO reference : soDTO.getSelByerRefDTO()){
					if(null != reference && 
							(InHomeNPSConstants.MODEL.equalsIgnoreCase(reference.getRefType()) || 
									InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(reference.getRefType()))){
						customRefs.add(reference);
					}
				}
			}
			
			getRequest().setAttribute(Constants.MODEL_SERIAL_ERROR, errorMsgs);		
			getRequest().setAttribute(Constants.MODEL_SERIAL_VALUES, customRefs);		
			getRequest().setAttribute(Constants.MODEL_SERIAL_IND, soDTO.getInvalidModelSerialInd());
		}
	}
	
	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
  }	

