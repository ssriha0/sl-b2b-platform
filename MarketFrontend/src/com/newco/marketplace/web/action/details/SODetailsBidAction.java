package com.newco.marketplace.web.action.details;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.webservices.base.response.ProcessResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.provider.ITermsAndCondDelegate;
import com.newco.marketplace.web.dto.ServiceOrderBidModel;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.activitylog.client.IActivityLogHelper;

public class SODetailsBidAction extends SLDetailsBaseAction implements ModelDriven<ServiceOrderBidModel>,
		Preparable {
	private static final long serialVersionUID = 9162755137166698001L;
	private static final Logger logger = Logger.getLogger(SODetailsBidAction.class);
	private ISODetailsDelegate detailsDelegate;
	private ITermsAndCondDelegate termsAndCondDelegate;
	private IServiceOrderBO serviceOrderBO;
	private ServiceOrderDao serviceOrderDao;
	
	private static String SAVE_BID_TO_DATABASE_FAILED = "saveBidToDatabaseFailed";
	private static String SAVE_BID_TO_ACTIVITY_LOG_FAILED = "saveBidToActivityLogFailed";
	private static String SAVE_BID_ERROR_MESSAGE = "saveBidErrorMessage";
	
	private ServiceOrderBidModel model = new ServiceOrderBidModel();
	//Sl-19820
	String soId;
	String resourceId;
	// Activity Log
	private IActivityLogHelper helper;

	public void setHelper(IActivityLogHelper helper)
	{
		this.helper = helper;
	}

	/**
	 * 
	 */
	public SODetailsBidAction()
	{ super();
	}

	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}
	
	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}

	public SODetailsBidAction(ISODetailsDelegate delegate,ITermsAndCondDelegate termsAndCondDelegate) {
		this.detailsDelegate = delegate;
		this.termsAndCondDelegate = termsAndCondDelegate;
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		
	}
	
	
	

	public String execute() throws Exception {
		
		// Initialize initial Dates
		//Sl-19820
		//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		String soId = getParameter("soId");
		String resId = getParameter("resId");
		ServiceOrderDTO dto = null;
		try{
			if(StringUtils.isNotBlank(resId)){
				dto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), Integer.parseInt(resId));
			}else{
				dto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			}
		}catch(Exception e){
			logger.error("Exception while trying to fetch SO Details");
		}
		setAttribute(THE_SERVICE_ORDER, dto);
		setAttribute(SO_ID, soId);
		setAttribute("routedResourceId",resId);
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		setAttribute("tab", getParameter("tab"));
		String bidderResourceId = getParameter("bidderResourceId");
		if(bidderResourceId!=null){
			setAttribute("bidderResourceId",bidderResourceId );
		}
		
		SimpleDateFormat day_sdf = new SimpleDateFormat("dd");
		SimpleDateFormat month_sdf = new SimpleDateFormat("MM");
		SimpleDateFormat year_sdf = new SimpleDateFormat("yyyy");
		String dayStr = day_sdf.format(dto.getServiceDate1());
		String monthStr = month_sdf.format(dto.getServiceDate1());
		String yearStr = year_sdf.format(dto.getServiceDate1());
		
		setAttribute("startDay", Integer.parseInt(dayStr));
		setAttribute("startMonth", Integer.parseInt(monthStr));
		setAttribute("startYear", Integer.parseInt(yearStr));
		
		
		setAttribute("hour24", TimeUtils.get24HourIntegerFromString(dto.getServiceTimeStart()));
		DateFormat df = new SimpleDateFormat( "MM/dd/yy" );
		String now = df.format(new Date());
		setAttribute("currDate",now);
		
		df = new SimpleDateFormat( "MM" );
		String currMM = df.format(new Date());
		setAttribute("currMM",currMM);
		
		df = new SimpleDateFormat( "dd" );
		String currDD = df.format(new Date());
		setAttribute("currDD",currDD);
		
		df = new SimpleDateFormat( "yyyy" );
		String curryyyy = df.format(new Date());
		setAttribute("curryyyy",curryyyy);
		
		DateFormat tmf = new SimpleDateFormat("H");
		String tmNow = tmf.format(new Date()); 
		setAttribute("currHour24",tmNow);
		
		model.setServiceDate1(dto.getServiceDate1());
		model.setServiceDate2(dto.getServiceDate2());
		model.setServiceWindow(dto.getServiceWindow());
		
		// Set default bid expiration time.
		String bidExpirationDateString = "";
		SimpleDateFormat bidExpFormat = new SimpleDateFormat("MM/dd/yyyy");
		if(dto.getServiceDate2() != null)
		{
			bidExpirationDateString = bidExpFormat.format(dto.getServiceDate2());
		}
		else if(dto.getServiceDate1() != null)
		{
			bidExpirationDateString = bidExpFormat.format(dto.getServiceDate1());
		}
		model.setBidExpirationDatepicker(bidExpirationDateString);
		
		//setting the sealed bid flag
		model.setSealedBidInd(dto.isSealedBidInd());
		
		checkForRecentSuccessfulBid();
		
		initDropdowns();
		return SUCCESS;
	}


	public  Integer get24HourIntegerFromString(String time)
	{
		if(time == null)
			return null;
		
		if(time.length() < 4)
			return null;
		
		int index = time.indexOf(":");
		String hourStr = time.substring(0, index);
		int hours = Integer.parseInt(hourStr);
		if(time.equalsIgnoreCase("12:00 am"))
			hours = 0;						
		if(time.endsWith("pm") && hours != 12)
			hours += 12;
		
		return hours;
	}
	
	
	private void checkForRecentSuccessfulBid()
	{
		//SL-19820
		//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO dto= getCurrentServiceOrderFromRequest();
		List<RoutedProvider> routedProviders = dto.getRoutedResources();
		Integer companyId = get_commonCriteria().getCompanyId();
		//SL-19820
		//Integer routedResourceId = Integer.valueOf((String) getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
		Integer routedResourceId = null;
		if(StringUtils.isNotBlank(getParameter("resId"))){
				routedResourceId = Integer.parseInt(getParameter("resId"));
		}
		
		boolean showBidSuccessMessage = false;
		for(RoutedProvider provider : routedProviders)
		{
			// See if we are from the same company
			if(companyId.intValue() == provider.getVendorId().intValue())
			{
				if(routedResourceId.intValue() == provider.getResourceId().intValue())
				{
					DateFormat df = new SimpleDateFormat( "MM/dd/yy hh:mm aa" );
					String now = df.format(new Date());
					if(provider.getProviderRespDate() != null && now.equalsIgnoreCase(df.format(provider.getProviderRespDate())))
					{
						showBidSuccessMessage = true;
					}
				}				
			}
		}
		
		getRequest().setAttribute("showBidSuccessMessage", showBidSuccessMessage);

		getRequest().setAttribute(SAVE_BID_ERROR_MESSAGE, null);
		//TODO SL-19820 : not sure
		//I think these values should be in session
		if(showBidSuccessMessage == false)
		{
			if(getSession().getAttribute(SAVE_BID_TO_DATABASE_FAILED) != null && (Boolean)getSession().getAttribute(SAVE_BID_TO_DATABASE_FAILED) == true)
			{
				getRequest().setAttribute(SAVE_BID_ERROR_MESSAGE, "We are sorry, our system was unable to save your bid information. Please try again");
			}
			if(getSession().getAttribute(SAVE_BID_TO_ACTIVITY_LOG_FAILED) != null && (Boolean)getSession().getAttribute(SAVE_BID_TO_ACTIVITY_LOG_FAILED) == true)
			{
				getRequest().setAttribute(SAVE_BID_ERROR_MESSAGE, "We are sorry, our system was unable to save your bid. Please try again.");
			}
		}
		getSession().setAttribute(SAVE_BID_TO_DATABASE_FAILED, null);
		getSession().setAttribute(SAVE_BID_TO_ACTIVITY_LOG_FAILED, null);
		
	}
	
	public String submitBid() throws Exception
	{
		String comment = getModel().getComment();
				
		String newSpecifiedDateStr = getModel().getNewDateBySpecificDate();
		
		String newDateByRangeToStr = null;
		String newDateByRangeFromStr = null;		
		String newStartTime = null;
		String newEndTime = null;
		
		Integer expireHour = null;
		String expireDateStr = null;
		String expireHourStr = null;
		
//		String dateFromStr = getModel().getNewDateByRangeFrom();
//		String dateToStr = getModel().getNewDateByRangeTo();
//		Boolean termsAndCond = getModel().getTermsAndConditions();
		
		Double totalLabor = 0.0D;
		Double totalHours = 0.0D;
		Double totalLaborParts = 0.0D;
		Double laborRate = 0.0D;
		Double partsMaterials = getModel().getPartsMaterials();
		
		if (getModel().getBidPriceType().equals("rate")) {
			if (getModel().getLaborRate() != null && getModel().getTotalHours() != null) {
				totalHours = getModel().getTotalHours();
				laborRate = getModel().getLaborRate();
				totalLabor = laborRate * totalHours;				
			}
		} else {
			totalLabor = getModel().getTotalLabor();
			totalHours = null;
			laborRate = null;
		}
		
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		Integer vendorId = get_commonCriteria().getCompanyId();
		String username = get_commonCriteria().getTheUserName();
		boolean serviceDateSpecifiedAsARange = false;
		Long buyerResourceId = null;
		Long buyerId = null;
		Integer routedResourceId = null;		
		//SL-19820
		//String routedResourceIdStr = (String)getSession().getAttribute("routedResourceId");
		String routedResourceIdStr = getParameter("resId");
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		
		
		// Only use bid expiration date info if this checkbox is checked.
		if(getModel().getBidExpirationFieldsShow())
		{
			expireHour = getModel().getBidExpirationHour();
			expireDateStr = getModel().getBidExpirationDatepicker();
			expireHourStr = getTimeStringFromInt(getModel().getBidExpirationHour());			
		}

		
		// Only use Change Service Date info if this checkbox is checked
		if(getModel().getRequestNewServiceDate())
		{
			newDateByRangeToStr = getModel().getNewDateByRangeTo();
			newDateByRangeFromStr = getModel().getNewDateByRangeFrom();		
			newStartTime = this.getTimeStringFromInt(getModel().getHourFrom());
			newEndTime = this.getTimeStringFromInt(getModel().getHourTo());			
		}
		
		if(StringUtils.isNumeric(routedResourceIdStr))
			routedResourceId = Integer.parseInt(routedResourceIdStr);
		
		if(totalLabor != null)
			totalLaborParts += totalLabor;
		if(partsMaterials != null)
			totalLaborParts += partsMaterials;
		
		Date currentDate = new Date();
		
		DateFormat dtf = new SimpleDateFormat("MM/dd/yy hh:mm aa");
		Date newDateByRangeFrom = null;
		Date newDateByRangeTo = null;
		Date bidExpirationDate = null;

		String dateTimeStr = "";
		if(StringUtils.isNotEmpty(newDateByRangeFromStr))
		{
			dateTimeStr = newDateByRangeFromStr + " " + newStartTime;
			newDateByRangeFrom = dtf.parse(dateTimeStr);				
		}		
		if(StringUtils.isNotEmpty(newDateByRangeToStr))
		{
			dateTimeStr = newDateByRangeToStr + " " + newEndTime;
			newDateByRangeTo = dtf.parse(dateTimeStr);
		}
		
		
		if(StringUtils.isNotEmpty(newSpecifiedDateStr))
		{
			dateTimeStr = newSpecifiedDateStr + " " + newStartTime;
			newDateByRangeFrom = dtf.parse(dateTimeStr);

			dateTimeStr = newSpecifiedDateStr + " " + newEndTime;
			newDateByRangeTo = dtf.parse(dateTimeStr);			
		}		
		
		
		if(StringUtils.isNotEmpty(expireDateStr))
		{
			dateTimeStr = expireDateStr + " " + expireHourStr;
			bidExpirationDate = dtf.parse(dateTimeStr);
		}
		
		//SL-19820
		//String soID = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soID = getParameter("soId");
		//ServiceOrderDTO currentSO = getCurrentServiceOrderFromSession();
		ServiceOrderDTO currentSO = null;
		try{
			if(null != routedResourceId) {
				currentSO = getDetailsDelegate().getServiceOrder(soID, get_commonCriteria().getRoleId(), routedResourceId);
			}
			else {
				currentSO = getDetailsDelegate().getServiceOrder(soID, get_commonCriteria().getRoleId(), null);
			}
		}catch(Exception e){
			logger.error("Exception while trying to fetch SO Details");
		}
		/*if(soID == null)
		{			
			if(currentSO != null)
				soID = currentSO.getSoId();
		}*/
		if(currentSO != null)
		{
			if(StringUtils.isNumeric(currentSO.getBuyerID()))
			{
				buyerId = Long.parseLong(currentSO.getBuyerID());
			}
			if(StringUtils.isNumeric(currentSO.getRoutedResourceId()))
				vendorId = Integer.parseInt((currentSO.getRoutedResourceId()));

			// If expiration date is not set explicitly, use Service Order date.
			// If we have a date range, use the end date
			if(bidExpirationDate == null)
			{
				String expireHourTmpStr = "05:00 PM";
				if(currentSO.getServiceDate2()  != null)
				{
					bidExpirationDate = currentSO.getServiceDate2();
					expireHourTmpStr = currentSO.getServiceTimeEnd();
				}				
				else if(currentSO.getServiceDate1() != null)
				{
					bidExpirationDate = currentSO.getServiceDate1();
					expireHourTmpStr = "05:00 PM";
				}
				else if(currentSO.getServiceOrderDate() != null)
				{
					bidExpirationDate = currentSO.getServiceOrderDate();
				}
				expireHour = getTimeIntFromString(expireHourTmpStr);
			}
			
			// for bulletin board order, route it to a provider as necessary
			if (currentSO.getPriceModel().equals(Constants.PriceModel.BULLETIN) && getModel().getBidderResourceId() != null) {
				routedResourceId = getModel().getBidderResourceId();
				boolean alreadyRouted = false;
				for (RoutedProvider prov : currentSO.getRoutedResources()) {
					if (prov.getResourceId().equals(routedResourceId)) {
						alreadyRouted = true;
						break;
					}
				}

				if (!alreadyRouted) {
					List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
					RoutedProvider routedProvider = new RoutedProvider();
					routedProvider.setRoutedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					routedProvider.setResourceId(routedResourceId);
					routedProvider.setSoId(soID);
					routedProvider.setPriceModel(currentSO.getPriceModel());
					routedResources.add(routedProvider);
					serviceOrderDao.insertRoutedResources(routedResources);
					
					currentSO.getRoutedResources().add(routedProvider);
				}
			}
		}
		
		boolean saveBidToDatabaseFailed = false;
		boolean saveBidToActivityLogFailed = false;

		
		Long bidId = null;
		try
		{
			helper.markBidsFromProviderAsExpired(soID, new Long(routedResourceId));
			bidId = helper.logBid(soID, new Long(vendorId), new Long(routedResourceId), buyerId, buyerResourceId, totalHours, laborRate,
					totalLabor, partsMaterials, bidExpirationDate, newDateByRangeFrom, newDateByRangeTo, serviceDateSpecifiedAsARange,
					comment, username);
		}
		catch (RuntimeException e)
		{
			// TODO Auto-generated catch block
			logger.error("Failed to save bid to ActivityLog:" + "soID=" + soID + "\n" + "vendorId=" + vendorId + "\n" + "resourceId="
					+ resourceId + "\n" + "buyerId=" + buyerId + "\n" + "buyerResourceId=" + buyerResourceId + "\n" + "totalHours="
					+ totalHours + "\n" + "laborRate=" + laborRate + "\n" + "totalLabor=" + totalLabor + "\n" + "partsMaterials="
					+ partsMaterials + "\n" + "bidExpirationDate=" + bidExpirationDate + "\n" + "newDateRangeFrom="
					+ newDateByRangeFromStr + "\n" + "newDateRangeTo=" + newDateByRangeToStr + "\n" + "comment=" + comment + "\n"
					+ "username=" + username + "\n");
			saveBidToActivityLogFailed = true;
		}		
		if(bidId == null || bidId < 0)
		{
			saveBidToActivityLogFailed = true;
		}

				
		
		if(saveBidToActivityLogFailed == false)
		{
			try
			{
				BigDecimal totalHoursBD = null;
				if (totalHours != null) {
					totalHoursBD = new BigDecimal(totalHours);
				}
				ProcessResponse response = detailsDelegate.processCreateBid(soID, routedResourceId, vendorId, currentDate, new BigDecimal(totalLabor), totalHoursBD,
						new BigDecimal(partsMaterials), bidExpirationDate, getTimeStringFromInt(expireHour), newDateByRangeTo,
						newDateByRangeFrom, newStartTime, newEndTime, comment, new BigDecimal(totalLaborParts), securityContext);
                if(response.isError()){
                    saveBidToDatabaseFailed = true;
                }
			}
			catch (RuntimeException e1)
			{
				saveBidToDatabaseFailed = true;
			}

		}
		//TODO : SL-19820 These values should be in session
        if(saveBidToActivityLogFailed || saveBidToDatabaseFailed){
            if(saveBidToDatabaseFailed)
            {
                getSession().setAttribute(SAVE_BID_TO_DATABASE_FAILED, saveBidToDatabaseFailed);
            }
            if(saveBidToActivityLogFailed)
            {
                getSession().setAttribute(SAVE_BID_TO_ACTIVITY_LOG_FAILED, saveBidToActivityLogFailed);
            }
        }else {
            this.setSuccessMessage(OrderConstants.BID_SUBMITTED);
            //SL-19820
            //getSession().setAttribute(Constants.SESSION.SOD_MSG, OrderConstants.BID_SUBMITTED);
            getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soID, OrderConstants.BID_SUBMITTED);
            setAttribute(Constants.SESSION.SOD_MSG, OrderConstants.BID_SUBMITTED);
        }
		
        //SL-19820
        this.soId = soID;
		this.resourceId = routedResourceIdStr;
		setDefaultTab("Summary");
		
		return GOTO_COMMON_DETAILS_CONTROLLER;
	}
	
	private Integer getTimeIntFromString(String time){
		Integer hour = 0;
		if ( time != null && time.length() == 8){
			// "05:00 PM".subSequence(0, 1)
			String h1 = (String) time.subSequence(0, 1);
			String h2 = (String) time.subSequence(1, 2);
			Integer th1 = Integer.parseInt(h1);
			Integer th2 = Integer.parseInt(h2);
			hour = Integer.parseInt(h1)*10 + Integer.parseInt(h2);
			String amOrPm = (String) time.subSequence(6, 8);
			if(amOrPm.equalsIgnoreCase("pm")){
				hour += 12 ;
			}
		}
		
		return hour;
	}
	private String getTimeStringFromInt(Integer hour)
	{
		if(hour == null)
			return "00:00 am";
		if(hour < 0)
			return "00:00 am";
		if(hour > 24)
			return "00:00 am";
		
		String hrStr;
		int tmpHour = hour;
		String padding = "";
		if(hour < 10 || (hour >=13 && hour <= 21))
		{
			padding = "0";
		}
		else
		{
			padding = "";
		}
		if(tmpHour > 12)
			tmpHour -= 12;
		
		String amOrPm;
		if(hour < 12)
			amOrPm = "am";
		else
			amOrPm = "pm";
		
		hrStr = padding + tmpHour + ":00 " + amOrPm;
		
		return hrStr;
	}

	
	private void initDropdowns()
	{
		LabelValueBean hr;
		List<LabelValueBean> hourDropdownList = new ArrayList<LabelValueBean>();
		int h;
		String padding;
		String amOrPm;
    	for (int hour = 0; hour < 24; ++hour)
		{
    		if(hour == 0)
    		{
    			h = 12;
    			amOrPm = "am";    			
    		}
    		else if(hour == 12)
    		{
    			h = 12;
    			amOrPm = "pm";    			
    		}
    		else if(hour < 12)
    		{
    			h = hour;
    			amOrPm = "am";
    		}
    		else
    		{
    			h = hour-12;
    			amOrPm = "pm";
    		}
    		
    		if(h<10 )
    			padding = " ";
    		else
    			padding = "";
    		
    		
    		hr = new LabelValueBean(padding + h+":00 " + amOrPm, hour + "");
			hourDropdownList.add(hr);
		}
    	getModel().setHourDropdownList(hourDropdownList);
	}
	
	public ITermsAndCondDelegate getTermsAndCondDelegate()
	{
		return termsAndCondDelegate;
	}

	public void setTermsAndCondDelegate(ITermsAndCondDelegate termsAndCondDelegate)
	{
		this.termsAndCondDelegate = termsAndCondDelegate;
	}

	public ServiceOrderBidModel getModel()
	{
		return model;
	}

	public void setModel(ServiceOrderBidModel model)
	{
		this.model = model;
	}


	public IServiceOrderBO getServiceOrderBO()
	{
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO)
	{
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
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

}
