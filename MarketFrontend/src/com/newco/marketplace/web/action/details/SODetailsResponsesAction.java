package com.newco.marketplace.web.action.details;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.FirmDTO;
import com.newco.marketplace.web.dto.ResponseStatusDTO;
import com.newco.marketplace.web.dto.ResponseStatusDTOComparator;
import com.newco.marketplace.web.dto.ResponseStatusTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.service.ManageScopeService;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.servicelive.activitylog.domain.ActivityAssociationType;
import com.servicelive.activitylog.domain.ActivityLog;
import com.servicelive.activitylog.domain.ActivityStatusType;
import com.servicelive.activitylog.domain.ActivityViewStatusName;
import com.servicelive.activitylog.domain.BidActivity;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;

public class SODetailsResponsesAction extends SLDetailsBaseAction implements Preparable, ModelDriven<ResponseStatusDTO> {

	private static final long serialVersionUID = -1012317355353884568L;
	private static final Logger logger = Logger.getLogger(SODetailsResponsesAction.class.getName());
	
	private ResponseStatusDTO responseStatusDto = new ResponseStatusDTO();
	private ManageScopeService manageScopeService;
	
	//SL-19820
	private ISOMonitorDelegate soMonitorDelegate;
	
	//SL-19820
	String soID;
	String groupID;	
	
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

	public ManageScopeService getManageScopeService() {
		return manageScopeService;
	}

	public void setManageScopeService(ManageScopeService manageScopeService) {
		this.manageScopeService = manageScopeService;
	}

	public SODetailsResponsesAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		//getRequest().getAttribute(Constants.SESSION.SOD_MSG);		
	}
	
	public String viewResponses() {
		loadResponses(false);
		
		return SUCCESS;
	}
	
	private void loadResponses(boolean filterByFollowup) {
		// This line should be next to last on all the detail tabs
		populateDTO(filterByFollowup);
		
		//SL-19820
		//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO dto= getCurrentServiceOrderFromRequest();
		if (dto != null) {
			setAttribute("priceModelBid", Constants.PriceModel.ZERO_PRICE_BID.equals(dto.getPriceModel()));
		} else {
			setAttribute("priceModelBid", false);
		}
		setAttribute("filterFollowupBids", filterByFollowup);
	}
	
	public String markBidRead() {
		String username = get_commonCriteria().getTheUserName();
		getDetailsDelegate().markBidAsReadByBuyer(getActivityId(), "", username);
		boolean filterFollowup = isFilteredByFollowup();
		loadResponses(filterFollowup);
		return SUCCESS;
	}
	
	public String flagForFollowup() {
		String username = get_commonCriteria().getTheUserName();
		getDetailsDelegate().markBidAsRequiringFollowUpByBuyer(getActivityId(), "", username);
		setAttribute("filterFollowupBids", Boolean.FALSE);
		loadResponses(false);
		return SUCCESS;
	}
	
	public String viewFollowupBids() {
		setAttribute("filterFollowupBids", Boolean.TRUE);
		loadResponses(true);
		return SUCCESS;
	}
	
	private Long getActivityId()
	{
		String str = getParameter("activityId");
		if(StringUtils.isNumeric(str))
			return Long.parseLong(str);
		
		return null;
	}
	
	private boolean isFilteredByFollowup()
	{
		String str = getParameter("filterFollowup");
		return str != null && str.equals("true");
	}
	
	private void populateDTO(boolean filterByFollowup)
	{
		SecurityContext context = (SecurityContext)getSession().getAttribute("SecurityContext");
		
		ResponseStatusTabDTO responseStatusTabDto = null;
		
		String groupId = getParameter(GROUP_ID);

		Integer companyId = context.getCompanyId();
		
		//Sl-19820
		//Integer status = (Integer)(getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		Integer status = null;
		if(StringUtils.isNotBlank(getParameter("status"))){
			status = Integer.parseInt(getParameter("status"));
			setAttribute(THE_SERVICE_ORDER_STATUS_CODE, status);
		}
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId = getParameter("soId");
		String id = null;
		if(StringUtils.isBlank(groupId)){
			groupId = null;
		}else{
			id = groupId;
		}
		if(StringUtils.isBlank(soId)){
			soId = null;
		}else{
			id = soId;
		}
		setAttribute(OrderConstants.SO_ID, soId);
		setAttribute("groupOrderId", groupId);
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
		if(StringUtils.isBlank(groupId) == true)
		{	
			responseStatusTabDto = detailsDelegate.getResponseStatusDto(soId, status,companyId);
		}
		else
		{			
			responseStatusTabDto = detailsDelegate.getResponseStatusDtoForGroup(groupId, status, companyId);
			if(null != responseStatusTabDto){
				if(responseStatusTabDto.isTaskLevelIsOn()){
					setAttribute("taskLevelIsOn", "true");
				}
				else{
					setAttribute("taskLevelIsOn", "false");
				}
			}
		}
		
		boolean slAdmin = false;
		ArrayList<LabelValueBean> callStatusList = null;
		if ((context.getAdminResId() != null && context.getAdminResId() != -1) || context.getSLAdminInd())
		{
			slAdmin = true;
			ArrayList<LookupVO> callStatus= (ArrayList<LookupVO>)detailsDelegate.getCallStatusList();
			
			Iterator<LookupVO> it = callStatus.iterator();
			LookupVO entry = null;
			callStatusList = new ArrayList<LabelValueBean>();
			while (it.hasNext())
			{
				entry =	(LookupVO)it.next();
				
				callStatusList.add(new LabelValueBean(String.valueOf(entry.getType()), String.valueOf(entry.getDescr())));
				
			}
		}

		// Get permissions to see Tiered Routing info/column
		Boolean showTieredRoutingInfo = getDetailsDelegate().validateFeature(companyId, BuyerFeatureConstants.TIER_ROUTE);
		getModel().setShowTieredRoutingInfo(showTieredRoutingInfo);
		
		//SL-19820
		//getSession().setAttribute("callStatusList", callStatusList);
		setAttribute("callStatusList", callStatusList);
		
		sortResponseStatusDtoList(responseStatusTabDto.getResponseStatusDtoList());
		
		//SL-19820
		//ServiceOrderDTO soDto = (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
		ServiceOrderDTO soDto = null;
		try{
			if(StringUtils.isNotBlank(soId)){
				if(org.apache.commons.lang.StringUtils.isNotBlank(getParameter("resId"))) {
					Integer resId = Integer.parseInt(getParameter("resId"));
					setAttribute("routedResourceId", resId);
					soDto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), resId);
				}
				else {
					soDto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
				}
			}
		}catch(Exception e){
			logger.error("Exception while trying to fetch SO Details");
		}
		setAttribute(THE_SERVICE_ORDER, soDto);
		
		if (StringUtils.isBlank(groupId)) {
			addActivityLogInfo(responseStatusTabDto.getResponseStatusDtoList(), filterByFollowup, soId);
		}
		
		if (soDto != null) {
			if (soDto.isTaskLevelPriceInd()) {
				setAttribute("taskLevelIsOn", "true");

			} else {
				setAttribute("taskLevelIsOn", "false");

			}
		}
		if(StringUtils.isNotBlank(responseStatusTabDto.getRoutingCriteria())){
			List<ResponseStatusDTO> responseList= responseStatusTabDto.getResponseStatusDtoList();
			
			//separating the provider list based on routed date
			List<Timestamp> routedDates = new ArrayList<Timestamp>();
			Map<Timestamp, Object> providerOfferMap = new TreeMap<Timestamp, Object>();
			 
			//getting the list of routed dates
			for(ResponseStatusDTO responseDto : responseList){
				if(null != responseDto && !routedDates.contains(responseDto.getRoutedDate())){
					routedDates.add(responseDto.getRoutedDate());
				}
			}
			//group the providers based on routed date
			for(Timestamp routedDate : routedDates){
				List<ResponseStatusDTO> dtoList = new ArrayList<ResponseStatusDTO>();
				for(ResponseStatusDTO responseDto : responseList){
					if(0 == routedDate.compareTo(responseDto.getRoutedDate())){
						dtoList.add(responseDto);
					}
				}
				providerOfferMap.put(routedDate, dtoList);
			}
			 
			if("provider".equals(responseStatusTabDto.getRoutingCriteria())){
				//sorting providers based on performance
				Map<Timestamp, Object> providerMap = new TreeMap<Timestamp, Object>();
				for(Entry providers : providerOfferMap.entrySet()){
					List<ResponseStatusDTO> dtoList = (List<ResponseStatusDTO>)providers.getValue();
					sortProviders(dtoList);
					providerMap.put((Timestamp)providers.getKey(), dtoList);
				}
				providerOfferMap.putAll(providerMap);
				//calculating the rank
				int rank = 0;
				int size = responseStatusTabDto.getResponseStatusDtoList().size();
				Iterator itr = providerOfferMap.keySet().iterator();
				while(itr.hasNext()){
					Timestamp date = (Timestamp)itr.next();
					List<ResponseStatusDTO> providerList = (List<ResponseStatusDTO>)providerOfferMap.get(date);
					for(ResponseStatusDTO responseDto : providerList){
						rank = rank + 1;
						responseDto.setRank(rank + "/" + size);
					}
				}
			}
			else if("firm".equals(responseStatusTabDto.getRoutingCriteria())){				
				Map<Timestamp, Object> providerMap = new TreeMap<Timestamp, Object>();
				int rank = 0;
				int size = 0;
				//get the total no. of firms
				List<Integer> firmList = new ArrayList<Integer>();
				List<Integer> tempfirm = new ArrayList<Integer>();
				for(Entry provEntry : providerOfferMap.entrySet()){
					List<ResponseStatusDTO> providerList = (List<ResponseStatusDTO>)provEntry.getValue();
					for(ResponseStatusDTO dto : providerList){
						if(!tempfirm.contains(dto.getVendorId())){
							tempfirm.add(dto.getVendorId());
						}
					}
					firmList.addAll(tempfirm);
					tempfirm.clear();
				}
				size = firmList.size();
				
				//grouping providers based on firms
				Iterator itr = providerOfferMap.keySet().iterator();
				while(itr.hasNext()){
					Timestamp rtdate = (Timestamp)itr.next();
					List<ResponseStatusDTO> providerList = (List<ResponseStatusDTO>)providerOfferMap.get(rtdate);
					//adding the firms to a list
					List<FirmDTO> firmlist = new ArrayList<FirmDTO>();
					for(ResponseStatusDTO responseDto : providerList){
						if(null != responseDto){
							FirmDTO firm = new FirmDTO();
							firm.setFirmId(responseDto.getVendorId());
							firm.setFirmscore(responseDto.getFirmScore());
							firm.setRoutedDate(responseDto.getRoutedDate());
							firmlist.add(firm);
						}
					}
					Set<FirmDTO> firmSet = new TreeSet<FirmDTO>(firmlist);
					List<FirmDTO> firms = new ArrayList<FirmDTO>(firmSet);
					
					//sort firms based on performance
					sortFirms(firms);
					
					//calculating the rank
					for(FirmDTO firm : firms){
						rank = rank + 1;
						firm.setFirmRank(rank + "/" + size);
					}
					
					//for each firm get the providers
					Map<FirmDTO, List<ResponseStatusDTO>> firmMap = new LinkedHashMap<FirmDTO, List<ResponseStatusDTO>>();
					for(FirmDTO firm : firms){
						if(null != firms){
							List<ResponseStatusDTO> dtoList = new ArrayList<ResponseStatusDTO>();
							for(ResponseStatusDTO provider : providerList){
								if(null != provider && firm.getFirmId().intValue() == provider.getVendorId().intValue()){
									dtoList.add(provider);
								}
							}
							firmMap.put(firm, dtoList);
						}
					}
					providerMap.put(rtdate, firmMap);
				}
				providerOfferMap.putAll(providerMap);
			}
			
			setAttribute("providerOfferMap", providerOfferMap);
			setAttribute("criteriaLevel", responseStatusTabDto.getRoutingCriteria());
		    
		}
	    setAttribute("providerOfferList", responseStatusTabDto.getResponseStatusDtoList());
	    setAttribute("wfStateId", responseStatusTabDto.getWfStateId());
	    setAttribute("slAdmin", slAdmin);
	    
		setAttribute("newCommentCnt", getDetailsDelegate().getNewBidNoteCount(soId));
		setAttribute("newConditionalAcceptedCnt", getDetailsDelegate().getNewBidCount(soId));

		boolean showCommunication;
		if (StringUtils.isBlank(groupId)) {
			showCommunication = detailsDelegate.validateServiceOrderFeature(soId,
				OrderConstants.ALLOW_COMMUNICATION);
		}
		else {
			//FIXME need to figure out how to handle communication for a group. consider scenarios of
			// individual service orders having communication, then a group having communication, then
			// the group breaking up, etc.
			showCommunication = false;
		}

		setAttribute("showCommunication", showCommunication);
	}

	private void addActivityLogInfo(ArrayList<ResponseStatusDTO> responseStatusDtoList, boolean filterByFollowup, String soId) {
		if (responseStatusDtoList == null || responseStatusDtoList.isEmpty()) {
			return;
		}
		
		List<ActivityLog> allOrderBids = this.detailsDelegate.getAllBidsForOrder(soId);
		Map<Integer, ActivityLog> mostRecentProviderBids = new HashMap<Integer, ActivityLog>();
		Map<Integer, List<ActivityLog>> previousProviderBids = new HashMap<Integer, List<ActivityLog>>();

		separateBidsIntoRecentAndOldByProvider(allOrderBids, mostRecentProviderBids, previousProviderBids);

		try{
			for (Iterator<ResponseStatusDTO> i = responseStatusDtoList.iterator(); i.hasNext(); ) {
				ResponseStatusDTO responseStatus = i.next();
				ActivityLog counterOffer = mostRecentProviderBids.get(responseStatus.getResourceId());
				if (OrderConstants.CONDITIONAL_OFFER.equals(responseStatus.getResponseId()) 
						|| (responseStatus.getResponseId() == null && counterOffer != null)) {
					responseStatus.setResponseId(OrderConstants.CONDITIONAL_OFFER);
					boolean removed = false;
					if (filterByFollowup) {
						if (counterOffer == null) {
							i.remove();
							removed = true;
						} else {
							if (counterOffer.getCurrentTargetViewStatus() == null ||
									counterOffer.getCurrentTargetViewStatus().getViewStatusName() != ActivityViewStatusName.REQUIRES_FOLLOW_UP) {
								i.remove();
								removed = true;
							}
						}
					}
					if (!removed) {
						if (counterOffer != null) {
							responseStatus.setActivityId(counterOffer.getActivityId());
							if (counterOffer.getTargetType() == ActivityAssociationType.Buyer) {
								if(counterOffer.getCurrentTargetViewStatus() != null && counterOffer.getCurrentTargetViewStatus().getViewStatusName() != null)
									responseStatus.setViewStatusName(counterOffer.getCurrentTargetViewStatus().getViewStatusName().name());
							}
							
							if (counterOffer instanceof BidActivity) {
								BidActivity bidActivity = (BidActivity) counterOffer; 
								if (isExpired(bidActivity)) {
									responseStatus.setResponseId(OrderConstants.EXPIRED);
									if (responseStatus.getPartsAndMaterialsBid() != null && responseStatus.getTotalLaborBid() != null) {
										responseStatus.setConditionalSpendLimit(responseStatus.getPartsAndMaterialsBid().doubleValue() + responseStatus.getTotalLaborBid().doubleValue());
									}
									responseStatus.setResponseComment(bidActivity.getComment());
								}
							}
						}
						
					}
					
				} else {
					if (filterByFollowup) {
						i.remove();  // Remove all items that do not have counter-offers in the activity log, since they can not be flagged for followup
					}
				}
			}	
		}catch(Exception e){
			logger.info("Bid error");
		}
			
	}
	
	private boolean isExpired(BidActivity bidActivity) {		
				
		if (bidActivity.getCurrentStatus().getStatus().equals(ActivityStatusType.EXPIRED)) {
        	return true;
		} 
		else if (bidActivity.getExpirationDate() != null) {
			
			return isExpirationDatePast(bidActivity);
		} 
		else {
			return false;
		}
	}
	
	private boolean isExpirationDatePast(BidActivity bidActivity) {
		Date serviceLocationExpirationDate = bidActivity.getExpirationDate();  // expiration date and time in service location timezone (although date object shows timezone as server time)
		//SL-19820
		//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO dto = getCurrentServiceOrderFromRequest();
		TimeZone serviceLocationTimeZone = null;
		if(null != dto){
			serviceLocationTimeZone = TimeZone.getTimeZone(dto.getServiceLocationTimeZone());
			if (serviceLocationTimeZone.getID().equals("GMT")) {
				String standardTimeZone = convertToStandardTimeZone(dto.getServiceLocationTimeZone());
				serviceLocationTimeZone = TimeZone.getTimeZone(standardTimeZone);
			}
		}
		Calendar currentTime = Calendar.getInstance();
		
		Date serverTimeExpirationDate = TimeChangeUtil.convertDateBetweenTimeZones(serviceLocationExpirationDate, serviceLocationTimeZone, currentTime.getTimeZone());
		Date currentDate = currentTime.getTime();
		
		return serverTimeExpirationDate.before(currentDate);
	}

	// converts timezone string from Daylight to Standard (PDT to PST)
	private String convertToStandardTimeZone(String serviceLocationTimeZone) {
		if (serviceLocationTimeZone.length() == 3 && serviceLocationTimeZone.substring(1, 2).equals("D")) {
			return serviceLocationTimeZone.substring(0, 1) + "S" + serviceLocationTimeZone.substring(2, 3);
		}
		return serviceLocationTimeZone;
	}

	private void separateBidsIntoRecentAndOldByProvider(
			List<ActivityLog> allOrderBids,
			Map<Integer, ActivityLog> mostRecentProviderBids,
			Map<Integer, List<ActivityLog>> previousProviderBids) {

		if (allOrderBids == null || allOrderBids.isEmpty()) return;
		
		Map<Integer, List<ActivityLog>> allBidsByProvider = new HashMap<Integer, List<ActivityLog>>(allOrderBids.size());
		
		for (ActivityLog activity : allOrderBids) {
			Long providerResourceIdAsLong = activity.getProviderResourceId();
			if (providerResourceIdAsLong == null) continue;
			
			Integer providerResourceId = providerResourceIdAsLong.intValue();			
			boolean recordActivityAsMostRecent = false;

			if (mostRecentProviderBids.containsKey(providerResourceId)) {
				ActivityLog previousActivity = mostRecentProviderBids.get(providerResourceId);
				if (previousActivity.getCreatedOn() != null && previousActivity.getCreatedOn().before(activity.getCreatedOn())) {
					recordActivityAsMostRecent = true;
				}
			}
			else {
				recordActivityAsMostRecent = true;
			}
			
			if (recordActivityAsMostRecent) {
				mostRecentProviderBids.put(providerResourceId, activity);
			}
			/*
			if (allBidsByProvider.containsKey(providerResourceId)) {
				List<ActivityLog> previousProviderBidsList = allBidsByProvider.get(providerResourceId);
			}			
			else {
			*/
				List<ActivityLog> previousProviderBidsList = null;
				if (previousProviderBids.containsKey(providerResourceId)) {
					previousProviderBidsList = previousProviderBids.get(providerResourceId);
				}
				else {
					previousProviderBidsList = new ArrayList<ActivityLog>();
					previousProviderBids.put(providerResourceId, previousProviderBidsList);
				}
				previousProviderBidsList.add(activity);
			//}
		}		
	}

	private void sortResponseStatusDtoList(ArrayList<ResponseStatusDTO> responseStatusList) {
		Collections.sort(responseStatusList, new ResponseStatusDTOComparator());
		
		// Go through the sorted list and for each provider, mark how many other providers from this list
		// are in the same firm
		if (responseStatusList.size() > 0) {
			int index = 0;
			int firmStartIndex = 0;
			Integer currentVendorId = responseStatusList.get(index).getVendorId();
			
			for (index = 1; index < responseStatusList.size(); index++) {
				if (!currentVendorId.equals(responseStatusList.get(index).getVendorId())) {
					for (int j = firmStartIndex; j < index; j++) {
						responseStatusList.get(j).setSelectedProvidersInFirm(index - firmStartIndex);
					}
					currentVendorId = responseStatusList.get(index).getVendorId();
					firmStartIndex = index;
				}
			}
			
			for (int j = firmStartIndex; j < index; j++) {
				responseStatusList.get(j).setSelectedProvidersInFirm(index - firmStartIndex);
			}
		}		
	}
	
	public String acceptConditionalOffer(){
		//SL-19820
		//String groupId = (String) getSession().getAttribute(GROUP_ID);
		String groupId = getParameter("groupId");
		if(StringUtils.isBlank(groupId)){
			groupId = null;
		}
		setAttribute(GROUP_ID, groupId);
		
		String id = null;
		if(StringUtils.isNotBlank(getParameter("groupId"))){
			id = getParameter("groupId");
			this.groupID = getParameter("groupId");
		}
		if(StringUtils.isNotBlank(getParameter("soId"))){
			id = getParameter("soId");
			this.soID = getParameter("soId");
		}
		
		ProcessResponse pr;
		responseStatusDto = ((ResponseStatusDTO)getModel());
		logger.debug("Date1:"+responseStatusDto.getConditionalChangeDate1());
		logger.debug("Date2:"+responseStatusDto.getConditionalChangeDate2());
		logger.debug("StartTime"+responseStatusDto.getConditionalStartTime());
		logger.debug("EndTime"+responseStatusDto.getConditionalEndTime());
		logger.debug("VendorId"+responseStatusDto.getVendorId());
		logger.debug("ResourceID"+responseStatusDto.getResourceId());
		logger.debug("ResponseReasonId"+responseStatusDto.getResponseReasonId());

		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date conditionalChangeDate1 = null;
		Date conditionalChangeDate2	= null;
		String firstChildSoId = "";
		boolean taskLevelPricing=false;
		try{
			
			double increasedPrice = 0.0;
			String priceModel = "";
			Double spendLimitTotal = new Double(0.0);
			SecurityContext context = (SecurityContext)getSession().getAttribute("SecurityContext");
			Double conditionalSpendLimit  = responseStatusDto.getConditionalSpendLimit() != null ? responseStatusDto.getConditionalSpendLimit() :0.00;
			double max = OrderConstants.NO_SPEND_LIMIT;	
			double buyerAvialableBalance=OrderConstants.NO_SPEND_LIMIT;
			if (!context.isAutoACH()) {
				Integer buyerId = context.getCompanyId();
				BigDecimal availableBalance;
				try {
					availableBalance = manageScopeService.getAvailableBalance(buyerId);
					if(availableBalance!=null)
					{
						buyerAvialableBalance=availableBalance.doubleValue();
					}
				} catch (Exception e) {
					logger.info("Exception in getting available balance"+ e.getMessage());
				}
				
			}
			if (null != context) {
				max = context.getMaxSpendLimitPerSO();
			}
			if(groupId != null)
			{
				//SL-19820
				//OrderGroupVO orderGroupVO = (OrderGroupVO)getSession().getAttribute(THE_GROUP_ORDER);
				OrderGroupVO orderGroupVO = soMonitorDelegate.getOrderGroupById(groupId);
				
				if (orderGroupVO.getFinalSpendLimitLabor() != null) {
					spendLimitTotal = orderGroupVO.getFinalSpendLimitLabor();
					if (orderGroupVO.getFinalSpendLimitParts() != null) {
						spendLimitTotal += orderGroupVO.getFinalSpendLimitParts();
					}
				}
			}
			else 
			{
				//SL-19820
				String soId = getParameter("soId");
				setAttribute(SO_ID, soId);
				ServiceOrderDTO dto = null;
				try{
					dto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
				}catch(Exception e){
					logger.error("Exception while trying to fetch SO");
				}
				setAttribute(THE_SERVICE_ORDER, dto);				
				//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
				
				if(dto!=null && dto.isTaskLevelPriceInd())
				{
					taskLevelPricing= true;
				}
				priceModel = dto != null ? dto.getPriceModel() : "";
				if(Constants.PriceModel.NAME_PRICE.equals(priceModel))
				{				
					Double labor  = dto.getLaborSpendLimit() ;
					Double parts  = dto.getPartsSpendLimit();
					
					if (null != labor) {
						spendLimitTotal +=labor.doubleValue();
					}
					if (null != parts) {
						spendLimitTotal += parts.doubleValue();
					}					
				}				
			}
			//SL-19820
			//String serviceorderId =(String)getSession().getAttribute(OrderConstants.SO_ID);
			String serviceorderId = getParameter("soId");
			setAttribute(SO_ID, serviceorderId);
			
            Integer soFundingTypeId=0;
            try
            {
            	soFundingTypeId=manageScopeService.getSoLevelFundingTypeId(serviceorderId);
            }
            catch(Exception e)
            {
            }
            boolean isSoLevelAutoACH=false;
            if(soFundingTypeId.intValue()==40){
            	isSoLevelAutoACH=true;
            }
			increasedPrice =  MoneyUtil.subtract(conditionalSpendLimit.doubleValue(), spendLimitTotal.doubleValue());
			logger.info("Bid Price requested:"+ conditionalSpendLimit);
			//Code to handle bid order incase the bid exceeds the buyer available balance for non auto funded buyer.
			if ((Constants.PriceModel.ZERO_PRICE_BID.equals(priceModel) && (groupId == null) && !isSoLevelAutoACH && buyerAvialableBalance < conditionalSpendLimit)) {
				String avialableBlanceMsg = "Your wallet does not have enough funding to cover this new combined maximum";
				this.setSuccessMessage(avialableBlanceMsg);
				//SL-19820
				//getSession().setAttribute(Constants.SESSION.SOD_MSG,avialableBlanceMsg);
				getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+id,avialableBlanceMsg);
				setAttribute(Constants.SESSION.SOD_MSG,avialableBlanceMsg);
				this.setDefaultTab( SODetailsUtils.ID_RESPONSE_STATUS);
				return "spendLimitError";
			}
			
			if ((max != OrderConstants.NO_SPEND_LIMIT  && max < increasedPrice) ||
					(!Constants.PriceModel.NAME_PRICE.equals(priceModel) && (groupId == null)&& max != OrderConstants.NO_SPEND_LIMIT  && max < conditionalSpendLimit)) {
				String spendLimitErrorMsg = OrderConstants.BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR;
				this.setSuccessMessage(spendLimitErrorMsg);
				////SL-19820
				//getSession().setAttribute(Constants.SESSION.SOD_MSG,spendLimitErrorMsg );
				getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+id,spendLimitErrorMsg );
				setAttribute(Constants.SESSION.SOD_MSG,spendLimitErrorMsg );
				this.setDefaultTab( SODetailsUtils.ID_RESPONSE_STATUS);
				return "spendLimitError";
			}
			if(responseStatusDto.getConditionalChangeDate1()!= null && (!("").equals(responseStatusDto.getConditionalChangeDate1()))){
				conditionalChangeDate1 = df.parse(responseStatusDto.getConditionalChangeDate1());
			}
			if(responseStatusDto.getConditionalChangeDate2()!= null && (!("").equals(responseStatusDto.getConditionalChangeDate2()))){
				conditionalChangeDate2 = df.parse(responseStatusDto.getConditionalChangeDate2());
			}
		}catch(ParseException pe){
			pe.printStackTrace();
		}
		if (StringUtils.isNotBlank(groupId)) {
			// retain one child orderId to return to the child details after group acceptance
			firstChildSoId = detailsDelegate.getFirstChildInGroup(groupId);
			
			pr = detailsDelegate.acceptConditionalOfferForGroup(groupId, responseStatusDto.getResourceId(),
					responseStatusDto.getVendorId(), responseStatusDto.getResponseReasonId(), conditionalChangeDate1, conditionalChangeDate2,
					responseStatusDto.getConditionalStartTime(), responseStatusDto.getConditionalEndTime(),
					responseStatusDto.getConditionalSpendLimit(), get_commonCriteria().getCompanyId());
		} 
		else {
			pr = detailsDelegate.acceptConditionalOffer(getParameter("soId"), responseStatusDto.getResourceId(),
					responseStatusDto.getVendorId(), responseStatusDto.getResponseReasonId(), conditionalChangeDate1, conditionalChangeDate2,
					responseStatusDto.getConditionalStartTime(), responseStatusDto.getConditionalEndTime(),
					responseStatusDto.getConditionalSpendLimit(), get_commonCriteria().getCompanyId());
		}

		if(pr.getCode().equals(ServiceConstants.USER_ERROR_RC)){
			this.setReturnURL("/financeManagerController_execute.action?defaultTab=" + OrderConstants.FM_MANAGE_FUNDS);
			if(null != pr.getMessages()){
				this.setErrorMessage(pr.getMessages().get(0));
			}else{
				this.setErrorMessage(OrderConstants.SO_CONDITIONAL_OFFER_NOT_ACCEPTED);
			}
			return ERROR;
		}
		
		if(StringUtils.isNotBlank(firstChildSoId)){
			//SL-19820
			//getSession().setAttribute(SO_ID, firstChildSoId);
			setAttribute(SO_ID, firstChildSoId);
			// set group Id = null so that it won't be accessed in details controller as this group no more  exists
			//getSession().setAttribute(GROUP_ID, null);
			setAttribute(GROUP_ID, null);
			this.soID = firstChildSoId;
			this.groupID = null;
			id = firstChildSoId;
		}
		if(taskLevelPricing)
		{
			this.setSuccessMessage(OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED_TASK_LEVEL);
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_MSG, OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED_TASK_LEVEL);	
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+id, OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED_TASK_LEVEL);	
			setAttribute(Constants.SESSION.SOD_MSG, OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED_TASK_LEVEL);	
		}
		else
		{
		this.setSuccessMessage(OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED);
		//SL-19820
		//getSession().setAttribute(Constants.SESSION.SOD_MSG, OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED);
		getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+id, OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED);
		setAttribute(Constants.SESSION.SOD_MSG, OrderConstants.SO_CONDITIONAL_OFFER_ACCEPTED);
		}
		this.setDefaultTab( SODetailsUtils.ID_RESPONSE_HISTORY);
		//SL-19820
		//return GOTO_COMMON_DETAILS_CONTROLLER;
		return "details_success";


	}
	
	//to sort criteria list based on performance score
	private void sortFirms(List<FirmDTO> firms) {
		Collections.sort(firms, new Comparator<Object>(){
			public int compare(final Object obj1, final Object obj2){
				final FirmDTO firm1 = (FirmDTO)obj1;
				final FirmDTO firm2 = (FirmDTO)obj2;
				final double score1 = firm1.getFirmscore();
				final double score2 = firm2.getFirmscore();
				if(score2 < score1){
					return -1;
				}
				else{
					return (score2 > score1)? 1 : 0;
				}
			}
		});
	}
	
	
	private void sortProviders(List<ResponseStatusDTO> providers) {
		Collections.sort(providers, new Comparator<Object>(){
			public int compare(final Object obj1, final Object obj2){
				final ResponseStatusDTO provider1 = (ResponseStatusDTO)obj1;
				final ResponseStatusDTO provider2 = (ResponseStatusDTO)obj2;
				final double score1 = provider1.getScore();
				final double score2 = provider2.getScore();
				if(score2 < score1){
					return -1;
				}
				else{
					return (score2 > score1)? 1 : 0;
				}
			}
		});
	}

	public ResponseStatusDTO getModel() {
		return responseStatusDto;
	}

	public void setModel(ResponseStatusDTO responseStatusDto) {

        this.responseStatusDto = responseStatusDto;

	}

	public ResponseStatusDTO getResponseStatusDto() {
		return responseStatusDto;
	}

	public void setResponseStatusDto(ResponseStatusDTO responseStatusDto) {
		this.responseStatusDto = responseStatusDto;
	}

	public ISOMonitorDelegate getSoMonitorDelegate() {
		return soMonitorDelegate;
	}

	public void setSoMonitorDelegate(ISOMonitorDelegate soMonitorDelegate) {
		this.soMonitorDelegate = soMonitorDelegate;
	}
	
	

}
