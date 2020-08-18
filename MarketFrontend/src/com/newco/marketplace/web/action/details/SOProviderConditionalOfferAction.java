package com.newco.marketplace.web.action.details;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.CounterOfferReasonsVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ConditionalOfferDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.ordermanagement.api.services.OrderManagementRestClient;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.activitylog.domain.ActivityLog;
import com.servicelive.activitylog.domain.ActivityStatusType;


public class SOProviderConditionalOfferAction extends SLDetailsBaseAction
		implements Preparable, ModelDriven<ConditionalOfferDTO> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5358786096573493456L;
	private static final Logger logger = Logger
			.getLogger("SOProviderConditionalOfferAction");
	private ISODetailsDelegate soDetailsManager;
	// Activity Log
	private IActivityLogHelper helper;
	private OrderManagementRestClient restClient;
	
	private ConditionalOfferDTO conditionalOffer = new ConditionalOfferDTO();
	private List<CounterOfferReasonsVO> counterOfferReasonsList;
	private String  selectedCounterOfferReasons;
	
	//SL-19820
	String soID;
	String groupID;
	String resId = "0";
		
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public SOProviderConditionalOfferAction(ISODetailsDelegate delegate) {
		this.soDetailsManager = delegate;
	}

	public String createConditionalOffer() {
		ProcessResponse processResponse = null;
		logger.info("Entering the SOProviderConditionalOfferAction.createConditionalOffer()");
		String result = null;

		Integer resourceID = null;
		//SL-19820
		/*if(null != getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID)){
			resourceID = Integer.parseInt((String) getSession()
				.getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
		}*/
		if(StringUtils.isNotBlank(getParameter("resId")) && !"0".equals(getParameter("resId"))){
			resourceID = Integer.parseInt(getParameter("resId"));
		}
		else{
			resourceID = get_commonCriteria().getVendBuyerResId();
		}
		Integer vendorID = get_commonCriteria().getCompanyId();
		//SL-19820
		/*String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID);*/
		String soID = getParameter("soId");
		if(StringUtils.isBlank(soID)){
			soID = null;
		}
		this.soID = soID;
		setAttribute(SO_ID, soID);
		String groupId = getParameter("groupId");
		if(StringUtils.isBlank(groupId)){
			groupId = null;
		}
		this.groupID = groupId;
		setAttribute(GROUP_ID, groupId);
		setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID, resourceID);
		if(null != resourceID){
			this.resId = resourceID.toString();
		}
		boolean isError = false;
		List<Integer> counterOfferedResourceIds = getCounterOfferedResourcesForSO();
		if ( null == counterOfferedResourceIds || 0 == counterOfferedResourceIds.size()){
			counterOfferedResourceIds.add(resourceID);
		}
		if (resourceID != null && vendorID != null) {
			logger.info("Date1 "
					+ ((ConditionalOfferDTO) getModel())
							.getConditionalChangeDate1()
					+ " Date2 "
					+ ((ConditionalOfferDTO) getModel())
							.getConditionalChangeDate2()
					+ " Time1 "
					+ ((ConditionalOfferDTO) getModel())
							.getConditionalStartTime()
					+ " Time2 "
					+ ((ConditionalOfferDTO) getModel())
							.getConditionalEndTime());

			ConditionalOfferDTO conditionalOffer = (ConditionalOfferDTO) getModel();

			conditionalOffer.setSelectedCounterOfferReasonsList(getCheckedReasons(conditionalOffer));
			conditionalOffer.setCounterOfferedResources(getCounterOfferedResourcesForSO());

			if(conditionalOffer.getRescheduleServiceDate())
			{
				if(StringUtils.isNotEmpty(conditionalOffer.getSpecificDate()))
				{
					conditionalOffer.setConditionalChangeDate1(conditionalOffer.getSpecificDate());
					conditionalOffer.setConditionalStartTime(conditionalOffer.getSpecificTime());
				}
			}
			
			
			String conditionalDate1 = conditionalOffer
					.getConditionalChangeDate1();
			String conditionalExpirationDate = conditionalOffer
					.getConditionalExpirationDate();
			String conditionalStartTime = conditionalOffer
					.getConditionalStartTime();
			String conditionalEndTime = conditionalOffer
					.getConditionalEndTime();
			

			Double incrSpendLimit = conditionalOffer.getConditionalSpendLimit();

			if ((conditionalDate1 == null || StringUtils
					.isEmpty(conditionalDate1))
					&& incrSpendLimit == null) {
				result = OrderConstants.SO_CONDITIONAL_START_DATE_OR_SPEND_LIMIT_REQUIRED;
				isError = true;
			}

			if (StringUtils.isEmpty(conditionalStartTime)
					&& incrSpendLimit == null
					&& (conditionalDate1 == null || StringUtils
							.isEmpty(conditionalDate1))) {
				result = OrderConstants.SO_CONDITIONAL_START_TIME_REQUIRED;
				isError = true;
			}

			if (StringUtils.isEmpty(conditionalEndTime)
					&& incrSpendLimit == null
					&& (conditionalDate1 == null || StringUtils
							.isEmpty(conditionalDate1))) {
				result = OrderConstants.SO_CONDITIONAL_END_TIME_REQUIRED;
				isError = true;
			}

			if ((incrSpendLimit != null)
					&& (incrSpendLimit < 0.00)) {
				result = OrderConstants.SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT;
				isError = true;
			} //Validate negative spent limit amount entered - Sears00051339

			if (conditionalExpirationDate == null
					|| StringUtils.isEmpty(conditionalExpirationDate)) {
				result = OrderConstants.SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED;
				isError = true;
			}

			// Setting this to true so we don't send the Cond Offer
			//isError = true;
			
			if (!isError) {
				conditionalOffer.setResourceId(resourceID);
				conditionalOffer.setVendorOrBuyerID(vendorID);
				conditionalOffer.setSoId(soID);
				this.updateSelectedCounterOfferReasonsList(conditionalOffer);
				if (!StringUtils.isBlank(groupId)) {
					conditionalOffer.setSoId(groupId);
					processResponse = soDetailsManager
						.createProviderConditionalOfferForGroup(conditionalOffer);
				} 
				else {			
					processResponse = soDetailsManager
						.createProviderConditionalOffer(conditionalOffer);
				}

				if (null != processResponse && processResponse.isError()) {
					this.setReturnURL("/serviceOrderMonitor.action");
					this.setErrorMessage(processResponse.getMessages().get(0));
					return ERROR;
				} else {
					
					//SL-19820
					//ServiceOrderDTO so = getCurrentServiceOrderFromSession();
					ServiceOrderDTO so = null;
					try{
						if(null != soID){
							if(null != resourceID) {
								so = getDetailsDelegate().getServiceOrder(soID, get_commonCriteria().getRoleId(), resourceID);
							}
							else {
								so = getDetailsDelegate().getServiceOrder(soID, get_commonCriteria().getRoleId(), null);
							}
						}
						
					}catch(Exception e){
						logger.error("Exception while trying to fetch SO Details");
					}
					if(so != null)
					{
						logConditionalOfferActivity(counterOfferedResourceIds, vendorID, conditionalOffer, so);
					}
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.SOD_MSG,OrderConstants.SO_CONDITIONAL_OFFER_SUCCESS);
					getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+conditionalOffer.getSoId(),OrderConstants.SO_CONDITIONAL_OFFER_SUCCESS);
					setAttribute(Constants.SESSION.SOD_MSG, SO_CONDITIONAL_OFFER_SUCCESS);
					return SUCCESS;
				}
			}
		} else {
			if (get_commonCriteria().getRoleId().intValue() == 1)
				result = OrderConstants.VENDOR_ID_REQUIRED;
			else if (get_commonCriteria().getRoleId().intValue() == 2)
				result = OrderConstants.BUYER_ID_REQUIRED;
		}

		this.setReturnURL("/soDetailsController.action?soId="+this.soID+"&groupId="+this.groupID+"&resId="+this.resId);
		this.setErrorMessage(result);
		return ERROR;
	}

	private List<Integer> getCheckedReasons(ConditionalOfferDTO conditionalOffer)
	{
		// Find the reason code checkboxes that have been checked that start with
		// 'reason_date_' and 'reason_price'.  And only use them if the
		// appropriate checkbox on the form has been checked.
		Enumeration<String> e = getRequest().getParameterNames();
		List<Integer> reasons = new ArrayList<Integer>();
		String name = null;
		String idStr;
		while (e.hasMoreElements())
		{	
			name = (String)e.nextElement();
			if(conditionalOffer.getRescheduleServiceDate())
			{
				if (StringUtils.contains(name, "reason_date_"))
				{
					idStr = name.substring(12);
					if(StringUtils.isNumeric(idStr))
					{
						reasons.add(Integer.parseInt(idStr));
					}
				}
			}
			if(conditionalOffer.getIncreaseMaxPrice())
			{
				if (StringUtils.contains(name, "reason_price_"))
				{
					idStr = name.substring(13);
					if(StringUtils.isNumeric(idStr))
					{
						reasons.add(Integer.parseInt(idStr));
					}
				}
			}			
		}
		return reasons;
	}
	
	
	private List<Integer> getCounterOfferedResourcesForSO()
	{
		
		List<Integer> resourceIds = new ArrayList<Integer>();
		String[] resourceIdList=getRequest().getParameterValues("checkBox_resource_id") ;
		Integer resourceId;
		if(resourceIdList!=null){
			for(int i=0;i<resourceIdList.length;i++){
				resourceId=Integer.parseInt(resourceIdList[i]);
				resourceIds.add(resourceId);
			}
		}
		return resourceIds;
	}
	
	private void logConditionalOfferActivity(List<Integer> resourceIDs, Integer vendorID, ConditionalOfferDTO conditionalOffer, ServiceOrderDTO so) {
		
		Double materialCost = so.getPartsSpendLimit();
		
		Double incrSpendLimit = so.getLaborSpendLimit();
		if (conditionalOffer.getConditionalSpendLimit() != null) {
			incrSpendLimit = conditionalOffer.getConditionalSpendLimit() - materialCost;
		}
		
		String comment = generateActivityLogComment(conditionalOffer);	
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
		
		try {
			Date serviceDateFrom = null, serviceDateTo = null;
			Boolean isDateSuppliedAsRange = false;
			
			// Ugly date handling
			if (StringUtils.isNotBlank(conditionalOffer.getConditionalChangeDate1()) && 
					StringUtils.isNotBlank(conditionalOffer.getConditionalChangeDate2()) ) {
				isDateSuppliedAsRange = true;
				serviceDateFrom = TimeUtils.convertStringToDate(conditionalOffer.getConditionalChangeDate1(), 
					conditionalOffer.getConditionalStartTime(),sdf);
				serviceDateTo = TimeUtils.convertStringToDate(conditionalOffer.getConditionalChangeDate2(), 
					conditionalOffer.getConditionalEndTime(),sdf);
			} else if (StringUtils.isNotBlank(conditionalOffer.getConditionalChangeDate1()) && 
					StringUtils.isBlank(conditionalOffer.getConditionalChangeDate2())) {
				serviceDateFrom = TimeUtils.convertStringToDate(conditionalOffer.getConditionalChangeDate1(), 
					conditionalOffer.getConditionalStartTime(),sdf);				
			} else {
				
				Calendar fromCalendar = TimeUtils.getDateTime(so.getServiceDate1(), so.getServiceTimeStart(), so.getServiceLocationTimeZone());
				if (fromCalendar != null) {
					serviceDateFrom = fromCalendar.getTime();
				}
				Calendar toCalendar = TimeUtils.getDateTime(so.getServiceDate2(), so.getServiceTimeEnd(), so.getServiceLocationTimeZone());
				if (toCalendar != null) {
					isDateSuppliedAsRange = true;
					serviceDateTo = toCalendar.getTime();
				}
			}
			
			Date conditionalExpirationDate = TimeUtils.convertStringToDate(conditionalOffer.getConditionalExpirationDate(), 
					conditionalOffer.getConditionalExpirationTime(),sdf);
		
			Long buyerContactId = null;
			if (so.getBuyerContact().getIndividualID() != null) {
				buyerContactId = Long.parseLong(so.getBuyerContact().getIndividualID());
			}
			if(buyerContactId == null)
			{
				buyerContactId = Long.parseLong(so.getBuyerID());
				logger.error("Could not extract buyerContactId from so.buyerContact for soID=" + so.getId());
			}
			for(Integer resourceID: resourceIDs){
			Long activityId = helper.logBid( so.getId(), vendorID.longValue(),resourceID.longValue(), Long.parseLong(so.getBuyerID()), buyerContactId,
					null, null, incrSpendLimit, materialCost, conditionalExpirationDate, serviceDateFrom, serviceDateTo, isDateSuppliedAsRange, comment, 
					get_commonCriteria().getTheUserName());
			if(activityId > 0)
				logger.debug("Activity logged.  Id: " + activityId);
			else
				logger.error("Activity logger failed.  Id: " + activityId + " vendorID:" + vendorID + " resourceID:" + resourceID);
			}
		} catch (Exception ex){
			logger.warn("Exception caught while parsing date! DO NOTHING. ", ex );
		} 
	}
	
	private String generateActivityLogComment(ConditionalOfferDTO conditionalOffer) {
		StringBuilder comment = new StringBuilder();
		comment.append("Provider accepted this SO with conditions: ");
		// TODO:  THIS IS NOT GOING TO BE HERE LONG!!!!
		// REALLY BAD CODE, REFACTORING IS ON ITS WAY
		if (conditionalOffer.getConditionalSpendLimit() != null && StringUtils.isNotBlank(conditionalOffer.getConditionalChangeDate1())) {
			comment.append(" increasing order spend limit and requesting new service date.");
		} else if (conditionalOffer.getConditionalSpendLimit() != null && StringUtils.isBlank(conditionalOffer.getConditionalChangeDate1())) {
			comment.append("increasing order spend limit.");
		} else {
			comment.append("requesting new service date.");
		}
		
		return comment.toString();
	}

	public String withDrawCondOffer() {
		Integer resId = null;
		//SL-19820
		/*String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID);*/
		String soID = getParameter("soId");
		if(StringUtils.isBlank(soID)){
			soID = null;
		}
		this.soID = soID;
		setAttribute(SO_ID, soID);
		String groupId = getParameter("groupId");
		if(StringUtils.isBlank(groupId)){
			groupId = null;
		}
		this.groupID = groupId;
		setAttribute(GROUP_ID, groupId);
		
		String resourceId=(String)getRequest().getParameter("resourceId");		
		if(StringUtils.isBlank(resourceId) || "0".equals(resourceId)){
			//SL-19820
			 /*resId = Integer.parseInt((String) getSession().getAttribute(
				Constants.SESSION.SOD_ROUTED_RES_ID));*/
			 resId = get_commonCriteria().getVendBuyerResId();
		}else{
			resId = Integer.parseInt(resourceId);
		}
		
		setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID, resourceId);
		if(StringUtils.isNotBlank(resourceId)){
			this.resId = resourceId;
		}
		
		RoutedProvider rp = new RoutedProvider();
		if(SLStringUtils.isNullOrEmpty(groupId))
		{
			rp.setSoId(soID);
		}
		else
		{
			rp.setSoId(groupId);
			rp.setGroupedOrder(true);
		}
			
		rp.setResourceId(resId);

		rp.setProviderRespId(2);
		ProcessResponse pr = null;
		try {
			pr = soDetailsManager.withDrawCondAcceptance(rp);

            //Changed to capture system erorrs as well as user errors:
			if (ServiceConstants.VALID_RC.equals(pr.getCode())) {
				//SL-19820
                //getSession().setAttribute(Constants.SESSION.SOD_MSG,OrderConstants.SO_CONDITIONAL_OFFER_WITHDRAWN);
				getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+rp.getSoId(),OrderConstants.SO_CONDITIONAL_OFFER_WITHDRAWN);
				setAttribute(Constants.SESSION.SOD_MSG,OrderConstants.SO_CONDITIONAL_OFFER_WITHDRAWN);
                // Save to ActivityLog
                activityLogWithdrawConditionalOffer(resId);
			} else{
                this.setReturnURL("/soDetailsController.action?soId="+this.soID+"&groupId="+this.groupID+"&resId="+this.resId);
				if (null != pr.getMessages()) {
					this.setErrorMessage(pr.getMessages().get(0));
				} else {
					this.setErrorMessage(OrderConstants.SO_CONDITIONAL_OFFER_NOT_WITHDRAWN);
				}
				return ERROR;
			}
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return SUCCESS;

	}

	private void activityLogWithdrawConditionalOffer(Integer providerResourceID)
	{
		//SL-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soID = this.soID;
		Integer vendorID = get_commonCriteria().getCompanyId();

		List<ActivityLog> activityList = helper.getProviderBidsForOrder(
				new Long(vendorID),
				new Long(providerResourceID), soID);
		if(activityList != null)
		{
			Long activityId = -1L;
			String username = get_commonCriteria().getTheUserName();
			
			// Need the last condOffer(highest activity ID)
			for(ActivityLog log : activityList)
			{
				if(!log.getCurrentStatus().getStatus().equals(ActivityStatusType.ENABLED))
					continue;
				if(log.getActivityId() > activityId)
					activityId = log.getActivityId();
			}
			
			if(activityId > 0)
			{
				helper.updateActivityStatus(activityId,
					    ActivityStatusType.DISABLED, "withdraw offer",
					    username);
			}
			else
			{
				logger.error("activityLogWithdrawConditionalOffer() failed to find condOffer for SOID=" + soID + " vendorID=" + vendorID  + " providerResourceID=" + providerResourceID);
			}
		}
		
	}
	
	
	public void prepare() throws Exception {
		logger.info("Entering Prepare of SOProviderConditionalOfferAction");
		createCommonServiceOrderCriteria();

	}

	public ConditionalOfferDTO getModel() {
		return conditionalOffer;
	}

	public void setModel(ConditionalOfferDTO x) {
		conditionalOffer =  x;
	}

	public ConditionalOfferDTO getConditionalOffer() {
		return conditionalOffer;
	}

	public void setConditionalOffer(ConditionalOfferDTO conditionalOffer) {
		this.conditionalOffer = conditionalOffer;
	}


	public List<CounterOfferReasonsVO> getCounterOfferReasonsList() {
		return counterOfferReasonsList;
	}

	public void setCounterOfferReasonsList(
			List<CounterOfferReasonsVO> counterOfferReasonsList) {
		this.counterOfferReasonsList = counterOfferReasonsList;
	}

	public String getSelectedCounterOfferReasons() {
		return selectedCounterOfferReasons;
	}

	public void setSelectedCounterOfferReasons(String selectedCounterOfferReasons) {
		this.selectedCounterOfferReasons = selectedCounterOfferReasons;
	}
	/**
	 * Method tokenizes the reason String and populates the selected counter offer reasons 
	 * in ConditionalOfferDTO
	 * @param conditionalOffer
	 */
	private void updateSelectedCounterOfferReasonsList(ConditionalOfferDTO conditionalOffer) {
		selectedCounterOfferReasons = getRequest().getParameter(OrderConstants.SO_COUNTER_OFFER_REASONS_STR);
		if(null != selectedCounterOfferReasons){			
			try {
				selectedCounterOfferReasons= URLDecoder.decode(selectedCounterOfferReasons , "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Tokenizing the reasons String and updating the selected counter offer reasons
			//in ConditionalOfferDTO
			StringTokenizer reasonTokenizer = new StringTokenizer(selectedCounterOfferReasons, OrderConstants.COMMA_DELIMITER);	
			List<Integer> selectedCounterOfferReasonsList = new ArrayList<Integer>();
			while (reasonTokenizer.hasMoreTokens()) {
				String reasonStr = reasonTokenizer.nextToken();
				int end = reasonStr.indexOf(OrderConstants.DOLLAR_DELIMITER);	
				Integer counterOfferReason = Integer.parseInt(reasonStr.substring(0, end));
				selectedCounterOfferReasonsList.add(counterOfferReason);
			}
			
			conditionalOffer.setSelectedCounterOfferReasonsList(selectedCounterOfferReasonsList);
		}	
	}

	public IActivityLogHelper getHelper()
	{
		return helper;
	}

	public void setHelper(IActivityLogHelper helper)
	{
		this.helper = helper;
	}

	public OrderManagementRestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(OrderManagementRestClient restClient) {
		this.restClient = restClient;
	}
}