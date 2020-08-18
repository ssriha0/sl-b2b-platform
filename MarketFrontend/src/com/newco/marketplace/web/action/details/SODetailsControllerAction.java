package com.newco.marketplace.web.action.details;

import static com.newco.marketplace.constants.Constants.SESSION.NUMBER_OF_ORDERS_IN_GROUP;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.survey.ExtendedSurveyBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.action.wizard.SOWControllerAction;
import com.newco.marketplace.web.delegates.AdminBuyerSearchDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.ProviderWidgetResultsDTO;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.security.OrderAssociationPage;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.reasoncode.services.ManageReasonCodeService;
import com.newco.marketplace.interfaces.SurveyConstants;

/**
 * $Revision: 1.42 $ $Author: nsanzer $ $Date: 2008/06/10 17:32:48 $
 */

/*
 * Maintenance History 
 * $Log: SODetailsControllerAction.java,v $
 * Revision 1.42  2008/06/10 17:32:48  nsanzer
 * Fix for posting fee being left blank on the SO review and summary screens.  Now retrieves posting fee from the buyer table, for the review screen and the so_hdr table for the summary screen.
 *
 * Revision 1.41  2008/06/09 15:18:39  nsanzer
 * Fix for posting fee being left blank on the SO Summary screen.
 *
 * Revision 1.40  2008/05/29 20:23:12  rgurra0
 * fix Defect #115 SO Close tab
 *
 * Revision 1.39  2008/05/29 16:23:27  gjacks8
 * cleaned imports added simple role to prevent adminDash
 *
 * Revision 1.38  2008/05/21 23:33:10  akashya
 * I21 Merged
 *
 * Revision 1.37.6.1  2008/05/16 15:00:01  pjoy0
 * Fixed as per Sears53390 : Provider able to remove an order from another prov
 *
 * Revision 1.37  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.33.4.1  2008/04/23 11:41:34  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.36  2008/04/23 05:19:30  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.34  2008/04/15 17:57:47  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.33.16.1  2008/04/08 20:56:22  paugus2
 * CR # 50053
 *
 * Revision 1.33  2008/02/29 21:05:31  ajain04
 * Moved code from execute to prepare for saving displayTab in session.  Also, added a check to see if the data was already in session.  This is a fix for defect #48366 - when a user added a note, displayTab was not passed in request and the old code was setting the old value in session to null.  This was causing issues when returning to SOM from SOD.
 *
 * Revision 1.32  2008/02/26 18:18:02  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.31.2.1  2008/02/25 23:15:19  iullah2
 * distance calculation on SOM,SOD
 *
 * Revision 1.31  2008/02/14 23:44:55  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.30  2008/02/08 23:42:21  langara
 * Sears00046767,Sears00046898,Sears00046730,Sears00047094,Sears00047102
 *
 * Revision 1.29.4.1  2008/02/08 20:48:11  langara
 * Sears00046767,Sears00046898,Sears00046730,Sears00047094,Sears00047102
 *
 * Revision 1.29  2008/01/23 20:01:33  usawant
 * Modified for defect fix Sears00045171.
 *
 * Revision 1.28  2008/01/15 16:46:04  sali030
 * return to SOM
 *
 * Revision 1.27  2007/12/28 20:05:05  cgarc03
 * Set 'todaysDate' attribute in prepare().
 *
 * Revision 1.26  2007/12/13 23:53:23  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.25 2007/12/12 03:11:23  hravi terms and condition for accepting a SO
 * 
 * Revision 1.24 2007/12/07 15:37:13  mhaye05 replaced if (x != null && x.length > 0) with if (StringUtils.isNotEmpty(x)
 * 
 * Revision 1.23 2007/12/06 02:29:37 mhaye05 removed System.out.println's
 * 
 * Revision 1.22 2007/11/14 21:58:51 mhaye05 changed reference to SOW_SO_ID or
 * THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 * 
 */
@OrderAssociationPage
public class SODetailsControllerAction extends SLDetailsBaseAction implements
		Preparable {

	
	
	private static final Logger logger = Logger.getLogger(SOWControllerAction.class);
	private static final long serialVersionUID = 1L;
	private static final int DETAILS_CODE_ERROR = 0; 
	private static final int DETAILS_CODE_SERVICE_ORDER = 1; 
	private static final int DETAILS_CODE_GROUP_ORDER = 2;
	private static final String CURRENT_SO = "currentSO";
	private static final String CANCELLATION_FEE="cancellationFee";
	
	private int detailsCode = DETAILS_CODE_ERROR;	
	private String groupId=null;
	private ISOMonitorDelegate soMonitorDelegate;
	private AdminBuyerSearchDelegate buyerSearchDelegate;
	private ManageReasonCodeService manageReasonCodeService;
	protected ExtendedSurveyBO extendedSurveyBO;
	
	//SL-19820
	String id = null;
	
	public ManageReasonCodeService getManageReasonCodeService() {
		return manageReasonCodeService;
	}
	public void setManageReasonCodeService(
			ManageReasonCodeService manageReasonCodeService) {
		this.manageReasonCodeService = manageReasonCodeService;
	}
	public SODetailsControllerAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}
	public AdminBuyerSearchDelegate getBuyerSearchDelegate() {
		return buyerSearchDelegate;
	}

	public void setBuyerSearchDelegate(AdminBuyerSearchDelegate buyerSearchDelegate) {
		this.buyerSearchDelegate = buyerSearchDelegate;
	}
	public ExtendedSurveyBO getExtendedSurveyBO() {
		return extendedSurveyBO;
	}	

	public void setExtendedSurveyBO(ExtendedSurveyBO extendedSurveyBO) {
		this.extendedSurveyBO = extendedSurveyBO;
	}
	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	
	//SL-19820
	String somErrorMsg;
	String somError;
	String soDetails;
	
	public String getSomError() {
		return somError;
	}
	public void setSomError(String somError) {
		this.somError = somError;
	}
	public String getSomErrorMsg() {
		return somErrorMsg;
	}
	public void setSomErrorMsg(String somErrorMsg) {
		this.somErrorMsg = somErrorMsg;
	}
	public void prepare() throws Exception{	
		long start = System.currentTimeMillis();
		createCommonServiceOrderCriteria();
		Integer resourceId = get_commonCriteria().getVendBuyerResId(); //set resourceId to vendor ID initially
		String soId = getParameter("soId");
		//get the soId from request irrespective of any condition
		//Commenting code as part of SL-19820
		/*if (getRequest().getParameter("message") != null) {
			soId = getSession().getAttribute(OrderConstants.SO_ID).toString();
		}	*/

		int remainingTimeToAcceptSO = 0;
		
		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
		if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
		{
			if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
			{
				getSession().setAttribute("viewOrderPricing",true);
			}
		}
		//SL 15642 End-Changes for permission based price display in Service Order Monitor
		
		if(StringUtils.isEmpty(soId))
		{
			groupId = getParameter("groupId");
			//groupId should be passed in request
			//Commenting code as part of SL-19820
			/*if(StringUtils.isEmpty(groupId)){
				groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
			}*/
		}
		
		String resId = getParameter("resId");
		if (StringUtils.isNotBlank(resId)){
			//Commenting code as part of SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID,getRequest().getParameter("resId"));
			setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID,resId);
			resourceId = Integer.parseInt(resId); //set resourceId to routed resource, if present
		}
				// save the displayTab of SOM in session - defect 48366.  This was
		// currently being done in the execute method.  Also, when a note or 
		// support ticket is added, displayTab was not passed in the url and hence
		// was getting set to null.  This was causing issues when users were 
		// returning to SOM
		
		//SL-19820
		/*if (getSession().getAttribute(OrderConstants.DISPLAY_TAB) == null)
			getSession().setAttribute(OrderConstants.DISPLAY_TAB, getParameter(OrderConstants.DISPLAY_TAB));*/
		
		if(StringUtils.isNotBlank(groupId)){
			id = groupId;
		}else{
			id = soId;
		}
		if(getSession().getAttribute(OrderConstants.DISPLAY_TAB+"_"+id) == null){
			getSession().setAttribute(OrderConstants.DISPLAY_TAB+"_"+id, getParameter(OrderConstants.DISPLAY_TAB));
		}
		String displayTab = (String)getSession().getAttribute(OrderConstants.DISPLAY_TAB+"_"+id);
		setAttribute(OrderConstants.DISPLAY_TAB, displayTab);
		
		getSession().setAttribute(Constants.FROM_ORDER_MANAGEMENT+"_"+id, getParameter(Constants.FROM_ORDER_MANAGEMENT));
		setAttribute(Constants.FROM_ORDER_MANAGEMENT, getParameter(Constants.FROM_ORDER_MANAGEMENT));
		
		getSession().setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT+"_"+id, getParameter(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT));
		setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT, getParameter(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT));
		
		getSession().setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR+"_"+id, getParameter(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR));
		setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, getParameter(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR));
		
		//Prod issue fix
		String fromWFM = getParameter(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		if(StringUtils.isNotBlank(fromWFM)){
			getSession().setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, fromWFM);
			setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, fromWFM);
		}else{
			setAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR, getSession().getAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR));
		}
		
		String fromOM = getParameter(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT);
		if(StringUtils.isNotBlank(fromOM)){
			getSession().setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT, fromOM);
			setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT, fromOM);
		}else{
			setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT, getSession().getAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT));
		}
		
		String errorMsgGrp = (String)getSession().getAttribute("somMessageGrpOrderAlreadyAccepted_"+id);
		String errorMsgCancelled = (String)getSession().getAttribute("somMessageOrderCancelled_"+id);
		String errorMsgOrder = (String)getSession().getAttribute("somMessageOrderAlreadyAccepted_"+id);
		if(StringUtils.isNotBlank(errorMsgGrp)){
			this.somError = "somMessageGrpOrderAlreadyAccepted";
			this.somErrorMsg = errorMsgGrp;
		}
		else if(StringUtils.isNotBlank(errorMsgCancelled)){
			this.somError = "somMessageOrderCancelled";
			this.somErrorMsg = errorMsgCancelled;
		}
		else if(StringUtils.isNotBlank(errorMsgOrder)){
			this.somError = "somMessageOrderAlreadyAccepted";
			this.somErrorMsg = errorMsgOrder;
		}
		
		
		if (StringUtils.isNotEmpty(groupId))
		{
			loadOrderGroup(groupId);
			setAttribute("displayTax", false);
			setAttribute("laborTaxPercentage", 0.00);
			setAttribute("partsTaxPercentage" , 0.00);
			detailsCode = DETAILS_CODE_GROUP_ORDER;		
				try{
					remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptGrpOrder(groupId, resourceId);
				}
				catch(Exception e){
				}
		}
		else if (StringUtils.isNotEmpty(soId))
		{
			_loadServiceOrder(soId);
			
			ServiceOrderDTO currentSO = getCurrentServiceOrderFromRequest();
			if (null != currentSO) {
				if (String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(currentSO.getBuyerID())
						|| String.valueOf(BuyerConstants.TECH_TALK_BUYER_ID).equals(currentSO.getBuyerID())) {
					setAttribute("displayTax", true);
					setAttribute("laborTaxPercentage", null != currentSO.getLaborTaxPercent() ? currentSO.getLaborTaxPercent().doubleValue() : 0.00);
					setAttribute("partsTaxPercentage" , null != currentSO.getPartsTaxPercent() ? currentSO.getPartsTaxPercent().doubleValue() : 0.00);
				} else {
					setAttribute("displayTax", false);
					setAttribute("laborTaxPercentage", 0.00);
					setAttribute("partsTaxPercentage" , 0.00);
				}
			} else {
				setAttribute("displayTax", false);
				setAttribute("laborTaxPercentage", 0.00);
				setAttribute("partsTaxPercentage" , 0.00);
			}
			
			detailsCode = DETAILS_CODE_SERVICE_ORDER;		
			
				try{
					remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptSO(soId,resourceId);
				}
				catch(Exception e){
				}
		}
		//Changing code as part of SL-19820
		else if (getAttribute(OrderConstants.SO_ID) != null)
		{
			soId = getAttribute(OrderConstants.SO_ID).toString();
			_loadServiceOrder(soId);
			
			ServiceOrderDTO currentSO = getCurrentServiceOrderFromRequest();
			if (null != currentSO) {
				if (String.valueOf(BuyerConstants.RELAY_BUYER_ID).equals(currentSO.getBuyerID())
						|| String.valueOf(BuyerConstants.TECH_TALK_BUYER_ID).equals(currentSO.getBuyerID())) {
					setAttribute("displayTax", true);
					setAttribute("laborTaxPercentage", null != currentSO.getLaborTaxPercent() ? currentSO.getLaborTaxPercent().doubleValue() : 0.00);
					setAttribute("partsTaxPercentage" , null != currentSO.getPartsTaxPercent() ? currentSO.getPartsTaxPercent().doubleValue() : 0.00);
				} else {
					setAttribute("displayTax", false);
					setAttribute("laborTaxPercentage", 0.00);
					setAttribute("partsTaxPercentage" , 0.00);
				}
			} else {
				setAttribute("displayTax", false);
				setAttribute("laborTaxPercentage", 0.00);
				setAttribute("partsTaxPercentage" , 0.00);
			}
			
			detailsCode = DETAILS_CODE_SERVICE_ORDER;			
		}
		else
		{
			setAttribute("displayTax", false);
			setAttribute("laborTaxPercentage", 0.00);
			setAttribute("partsTaxPercentage" , 0.00);
			detailsCode = DETAILS_CODE_ERROR;
		}
		
		//Changing code as part of SL-19820
		//getSession().setAttribute("xTimerSeconds",remainingTimeToAcceptSO);
		setAttribute("xTimerSeconds",remainingTimeToAcceptSO);
		
		if(getParameter("bidderResourceId") != null){
			//Changing code as part of SL-19820
			//getSession().setAttribute("bidderResourceId",getParameter("bidderResourceId"));
			setAttribute("bidderResourceId",getParameter("bidderResourceId"));
		}
		

		/*
		 * calling lookup and putting reject reasons code in servlet context as
		 * application mode
		 */
		this.getRejectReasons();
		this.getAcceptServiceOrderTermsAndCond();
		
		// Set today's date for datepicker/calendar
		setAttribute("todaysDate", getDateString());
		
		boolean isProviderAdmin = _commonCriteria.getSecurityContext().isPrimaryInd();
		boolean isDispatchInd = _commonCriteria.getSecurityContext().isDispatchInd();
		setAttribute("isProviderAdmin", isProviderAdmin);
		setAttribute("isDispatchInd", isDispatchInd);
	
		// Set txn date only for non-grouped order
		if (StringUtils.isEmpty(groupId) ) {
			//Commenting code SL-19820
			//ServiceOrderDTO soDTO = (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
			ServiceOrderDTO soDTO = (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);
			preprocessFinTxnForPenalty(soDTO);
		}
		HttpSession session  = getSession();
		Map<String, UserActivityVO> activities = ((SecurityContext) getSession().getAttribute(
			"SecurityContext")).getRoleActivityIdList();
		boolean incSpendLimit = false;
		if(activities != null && activities.containsKey("56")){
			incSpendLimit = true;
		}
		session.setAttribute("incSpendLimit", incSpendLimit);
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
		    logger.info("Time Taken to complete prepare method:>>>>>"+(end-start));
		}
	}

	private void preprocessFinTxnForPenalty(ServiceOrderDTO soDTO) {
		boolean isWithin24Hours = false;
		if (soDTO != null && soDTO.getServiceDate1() != null) {

			//04:35 PM  //GMT timezone
			String strStartTime = soDTO.getServiceTimeStart();

			//2009-11-26 00:00:00.00 //GMT timezone
			Timestamp appointmentDate = soDTO.getServiceDate1();

			long millisecondsFor24Hours = 1000 * 60 * 60 * 24;

			// If successful perform financial transaction for penalty amount 
			// if service time is within 24 hours
			// else perform financial transaction without penalty
			if(TimeUtils.isPastXTime(appointmentDate, strStartTime, millisecondsFor24Hours)) {					
				isWithin24Hours = true;
			}
			getRequest().setAttribute("isWithin24Hours", String.valueOf(isWithin24Hours).toLowerCase());
		}
	}

	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT
	public String execute() throws Exception
	{	long start = System.currentTimeMillis();
		
		if (get_commonCriteria() == null)
			return LOGIN;

		if (detailsCode == DETAILS_CODE_ERROR)
		{
			this.setErrorMessage("SOID not found");
			return ERROR;
		}
		if(detailsCode == DETAILS_CODE_SERVICE_ORDER)
		{
			return displayServiceOrder();
		}
		
		if(detailsCode == DETAILS_CODE_GROUP_ORDER)
		{
			return displayOrderGroup();
		}
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
		    logger.info("Time Taken to complete execute in action:>>>>>"+(end-start));
		}
		return ERROR;
		
	}
	
	
	public String displayServiceOrder() throws Exception
	{	long start = System.currentTimeMillis();
		if (get_commonCriteria() == null)
			return LOGIN;
		
		int routedVendorId = 0;
		int vendorId = get_commonCriteria().getCompanyId();
		boolean temp = false;
		boolean flag = false;
		String userRoleType = get_commonCriteria().getRoleType();
		
		//SL-21465
		String serviceOrderId = getParameter(OrderConstants.SORT_COLUMN_PB_SOID);
		int resourceId=get_commonCriteria().getVendBuyerResId();
		//int roleId =get_commonCriteria().getRoleId();
	    //String  type= get_commonCriteria().getRoleType();
		
		String isEstimationRequest="";
		String estimateId="";
		EstimateVO estimateVo = null;
		if(userRoleType.equalsIgnoreCase("Buyer")){
			estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(serviceOrderId);
			
		}else{
			estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(serviceOrderId,vendorId,resourceId);
		}
		
		
		if(null != estimateVo && null != estimateVo.getBuyerRefValue() && estimateVo.getBuyerRefValue().equals("ESTIMATION")){
			isEstimationRequest="true";
			if(null != estimateVo.getEstimationId()){
				estimateId=String.valueOf(estimateVo.getEstimationId());
			}
		}
		//SL-21821
				if (userRoleType.equals("Provider")) {
					getRequest().setAttribute("isProvider" ,"true");
				}
		//END
		
		//Commenting code Sl-19820
		//Integer theCStatus = (Integer) (getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		Integer theCStatus = (Integer) (getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		//String userRoleType = get_commonCriteria().getRoleType();
		//Commenting code Sl-19820
		//ServiceOrderDTO soDTO = (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
		ServiceOrderDTO soDTO = (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);
		Integer subStatus = soDTO.getSubStatus();
		//SL-21887
		if("true".equals(isEstimationRequest)){
			getRequest().setAttribute("showEstimateBtn" ,"true");
			if((null != theCStatus) && (theCStatus.equals(160) || theCStatus.equals(180) || theCStatus.equals(125))){
				getRequest().setAttribute("showEstimateBtn" ,"false");
			}
			if((null != subStatus) && (!subStatus.equals(135) || !subStatus.equals(60))){
				getRequest().setAttribute("showEstimateBtn" ,"false");
			}

		}
		int buyerId = Integer.parseInt(soDTO.getBuyerID());
		populateReasonForSpendLimit(buyerId);
		if (getRequest().getParameter("message") != null) {
			//Commenting code SL-19820
			//TODO
			/*getSession().setAttribute(
							Constants.SESSION.SOD_SPENT_LIMIT_CHECK,
							"The maximum spending limit for the order is less than the final cost of the order. Please correct to proceed with Close & Pay!");*/
			setAttribute(Constants.SESSION.SOD_SPENT_LIMIT_CHECK,
					"The maximum spending limit for the order is less than the final cost of the order. Please correct to proceed with Close & Pay!");
		}else{
			//Commenting code SL-19820
			//getSession().removeAttribute(Constants.SESSION.SOD_SPENT_LIMIT_CHECK);
			getRequest().removeAttribute(Constants.SESSION.SOD_SPENT_LIMIT_CHECK);
		}
		Integer roleId = get_commonCriteria().getSecurityContext().getRoleId();
		//To fetch the associated details for assurant buyer
		if(OrderConstants.BUYER_ROLEID == roleId.intValue()) {
			List<AssociatedIncidentVO> incidents = detailsDelegate.getAssociatedIncidents(soDTO.getId());
			soDTO.setAssociatedIncidents(incidents);
			//Commenting code SL-19820
			//getSession().setAttribute(THE_SERVICE_ORDER,soDTO);
			setAttribute(THE_SERVICE_ORDER,soDTO);
		}	
		boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();

		if (isSLAdmin) {
			roleId = get_commonCriteria().getSecurityContext().getRoleId();

			if(roleId.equals(OrderConstants.SIMPLE_BUYER_ROLEID))
			{
	            // Go into buyer mode to access the Service Order
		        get_commonCriteria().getSecurityContext().setCompanyId(new Integer(soDTO.getBuyerContact().getCompanyID()));
		        get_commonCriteria().getSecurityContext().setRole(OrderConstants.SIMPLE_BUYER);
		        get_commonCriteria().getSecurityContext().setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
		        get_commonCriteria().setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
		        vendorId = new Integer(new Integer(soDTO.getBuyerContact().getCompanyID()));
			}
			else if (!roleId.equals(OrderConstants.PROVIDER_ROLEID)) {
	           // Go into buyer mode to access the Service Order
	           get_commonCriteria().getSecurityContext().setCompanyId(new Integer(soDTO.getBuyerContact().getCompanyID()));
	           get_commonCriteria().getSecurityContext().setRole(OrderConstants.BUYER);
	           get_commonCriteria().getSecurityContext().setRoleId(OrderConstants.BUYER_ROLEID);
	           get_commonCriteria().setRoleId(OrderConstants.BUYER_ROLEID);
	           vendorId = new Integer(new Integer(soDTO.getBuyerContact().getCompanyID()));
			}
		}

		if (userRoleType == null) {
			return ERROR;
		}
		/*
		String destinationSubTab;
		String soId = getParameter(OrderConstants.SO_ID);
		if(soId == null) {
			soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		}
		
		String groupId;
		PBFilterVO filter;
		if(soId == null) {
			groupId = getParameter("groupId");
			if(groupId == null) {
				groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
			}
			filter = detailsDelegate.getDestinationTabForSO(groupId);
		} else {
			filter = detailsDelegate.getDestinationTabForSO(soId);
		}
		destinationSubTab = filter.getDestinationSubTab();
		*/
		String destinationSubTab;
		//SL-19820
		if(/*getSession().*/getAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR) != null) {
			String soId = getParameter(OrderConstants.SO_ID);
			if(soId == null) {
				//Commenting code SL-19820
				//soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
				soId = (String)getAttribute(OrderConstants.SO_ID);
			}
			//Commenting code SL-19820
			//ServiceOrderDTO serviceOrderDTO = (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
			ServiceOrderDTO serviceOrderDTO = (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);
			boolean isBuyer = get_commonCriteria().getSecurityContext().isBuyer();
			ProviderWidgetResultsDTO widget = buyerSearchDelegate.getInfoForProviderWidget(soId,isBuyer,Integer.parseInt(serviceOrderDTO.getBuyerID()));
			if(widget!=null) {
				widget.setSoId(soId);
				if(serviceOrderDTO!=null && serviceOrderDTO.getProviderContact()!=null && serviceOrderDTO.getProviderContact().getCompanyID()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getProviderContact().getCompanyID()))
					widget.setProviderFirmId(serviceOrderDTO.getProviderContact().getCompanyID());
				if(serviceOrderDTO!=null && serviceOrderDTO.getProviderContact()!=null && serviceOrderDTO.getProviderContact().getCompanyName()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getProviderContact().getCompanyName()))
					widget.setProviderFirmName(serviceOrderDTO.getProviderContact().getCompanyName());
				if(serviceOrderDTO!=null && serviceOrderDTO.getAcceptedResourceId()!=null && !"".equalsIgnoreCase(serviceOrderDTO.getAcceptedResourceId().toString()))
					widget.setProviderID(serviceOrderDTO.getAcceptedResourceId().toString());
				if(serviceOrderDTO!=null && serviceOrderDTO.getAcceptedResource()!=null && serviceOrderDTO.getAcceptedResource().getResourceContact()!=null)
				{
					Contact c = serviceOrderDTO.getAcceptedResource().getResourceContact();
					String providerName = "";
					if(!"".equals(c.getFirstName()))
						providerName = c.getFirstName();
					if(!"".equals(c.getLastName()))
						providerName = providerName +" "+c.getLastName();
					widget.setProviderName(providerName);
				}
				//Commenting code SL-19820
				//getSession().setAttribute("widgetProResultsDTOObj",widget);
				getSession().setAttribute("widgetProResultsDTOObj_"+id,widget);
			}
			destinationSubTab = getParameter("tabForWFM");
			if(StringUtils.isBlank(destinationSubTab)){
				destinationSubTab = getDestinationSubTab();
			}
		} else {
			//Commenting code SL-19820
			//getSession().setAttribute(Constants.FROM_ORDER_MANAGEMENT, getParameter(Constants.FROM_ORDER_MANAGEMENT));
			/*getSession().setAttribute(Constants.FROM_ORDER_MANAGEMENT+"_"+id, getParameter(Constants.FROM_ORDER_MANAGEMENT));
			setAttribute(Constants.FROM_ORDER_MANAGEMENT, getParameter(Constants.FROM_ORDER_MANAGEMENT));
			
			getSession().setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT+"_"+id, getParameter(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT));
			setAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT, getParameter(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT));*/
			
			destinationSubTab = getParameter("defaultTab");
		}
		
		String priceModel = null;
		//Commenting code SL-19820
		/*if(getSession().getAttribute(THE_SERVICE_ORDER) != null)
		{
			priceModel = ((ServiceOrderDTO)getSession().getAttribute(THE_SERVICE_ORDER)).getPriceModel();
		}*/
		if(getAttribute(THE_SERVICE_ORDER) != null)
		{
			priceModel = ((ServiceOrderDTO)getAttribute(THE_SERVICE_ORDER)).getPriceModel();
		}
	
		if (theCStatus != null) {
			boolean isBuyer = userRoleType.equalsIgnoreCase("Buyer") || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(userRoleType); 
			if (theCStatus.equals(OrderConstants.CLOSED_STATUS)) {
				HashMap hm = new HashMap();
				// SLT-1643 Checking for displaying Old/New Ratings
				String surveyStatus = extendedSurveyBO.getServiceOrderFlowType(soId);
				boolean ratingsTabVisibility = !SurveyConstants.NO_TAB.equalsIgnoreCase(surveyStatus);
				
				if (isBuyer) {
					if (SurveyConstants.CSAT_NPS_OLDFLOW.equalsIgnoreCase(surveyStatus)) {
						if (soDTO.isBuyerHasRatedProvider()) {
							hm.put(SODetailsUtils.ID_VIEW_RATINGS, new Boolean(true));
						}
					} else {
						hm.put(SODetailsUtils.ID_VIEW_RATINGS_NEW, ratingsTabVisibility);
					}
				}
				
				if (userRoleType.equals("Provider")) {
					if (soDTO.isProviderHasRatedBuyer()) {
						if (SurveyConstants.CSAT_NPS_OLDFLOW.equalsIgnoreCase(surveyStatus)) {
							hm.put(SODetailsUtils.ID_VIEW_RATINGS, new Boolean(true));
						} else {
							hm.put(SODetailsUtils.ID_VIEW_RATINGS_NEW, ratingsTabVisibility);
						}
					}
					getRequest().setAttribute("isProvider", "true");
				}
				
				_setTabInfo(userRoleType, theCStatus, destinationSubTab, hm, priceModel,estimateId,isEstimationRequest);		
				
			} else if (theCStatus.equals(OrderConstants.COMPLETED_STATUS) && isBuyer) {
				
				// SLT-1643 Checking for displaying New Ratings
				String surveyStatus = extendedSurveyBO.getServiceOrderFlowType(soId);
				if (!(SurveyConstants.CSAT_NPS_OLDFLOW.equalsIgnoreCase(surveyStatus)
						|| SurveyConstants.NO_TAB.equalsIgnoreCase(surveyStatus))) {
					HashMap hm = new HashMap();
					hm.put(SODetailsUtils.ID_VIEW_RATINGS_NEW, new Boolean(true));
					_setTabInfo(userRoleType, theCStatus, destinationSubTab, hm, priceModel,estimateId,isEstimationRequest);
				} else {
					_setTabInfo(userRoleType, theCStatus,destinationSubTab, priceModel,estimateId,isEstimationRequest);
				}
				
			} else {
				_setTabInfo(userRoleType, theCStatus,destinationSubTab, priceModel,estimateId,isEstimationRequest);
			}
		} else {			
			_setTabInfo(userRoleType, theCStatus, destinationSubTab, priceModel,estimateId,isEstimationRequest);
		}
		getRequest().setAttribute("estimateId" ,estimateId);
		getRequest().setAttribute("isEstimationRequest" ,isEstimationRequest);

		String fromPage = getParameter("manageDocsFromSOM");
		if (fromPage != null) {
			if (fromPage.equalsIgnoreCase("fromDocumentSOMWidget")) {
				getRequest().setAttribute("theTab", "Summary");
				getRequest().setAttribute("theAnchor", "documents_top");
			}
		}
		getRoutedResources();
		getReasonCodesForReschedule();
		// SL-19240 adding method for fetching reschedule reason codes
		getBuyerRescheduleReasonCodes();
		
		getPermitTypes();		
		if (soDTO != null) {
			List<RoutedProvider> routedResources = soDTO.getRoutedResources();
			if (get_commonCriteria() != null) {
				String role = get_commonCriteria().getRoleType();
				if (role != null) {
					if (role.equalsIgnoreCase("provider")) {
						if (soDTO.getPriceModel().equals(Constants.PriceModel.BULLETIN)) {
							temp = true;
						} else if (routedResources != null) {
							for (RoutedProvider rp : routedResources) {
								routedVendorId = rp.getVendorId();
								if (vendorId == routedVendorId) {
									temp = true;
								}
							}
						}
						if (soDTO.getAcceptedVendorId() != null) {
							if (vendorId == soDTO.getAcceptedVendorId()) {
								flag = true;
							}
						}
						if (soDTO.getStatus().equals(ROUTED_STATUS)) {
							if (temp) {
								return SUCCESS;
							} else {
								//Commenting code SL-19820
								//getSession().setAttribute("soDetails", "1");
								setAttribute("soDetails", "1");
								this.soDetails = "1";
								return "soMonitor";
							}

						} else if (soDTO.getStatus().equals(ACCEPTED_STATUS)
								|| soDTO.getStatus().equals(ACTIVE_STATUS)
								|| soDTO.getStatus().equals(COMPLETED_STATUS)
								|| soDTO.getStatus().equals(PROBLEM_STATUS)
								|| soDTO.getStatus().equals(CLOSED_STATUS)
								|| soDTO.getStatus().equals(CANCELLED_STATUS)
								|| soDTO.getStatus().equals(PENDING_CANCEL_STATUS)) {
							if (flag) {
								return SUCCESS;
							} else {
								//Commenting code SL-19820
								//getSession().setAttribute("soDetails", "1");
								setAttribute("soDetails", "1");
								this.soDetails = "1";
								return "soMonitor";
							}
						}
						//Commenting code SL-19820
						//getSession().setAttribute("soDetails", "1");
						setAttribute("soDetails", "1");
						this.soDetails = "1";
						return "soMonitor";
					}

				}
			}
		}	
		long end = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
		    logger.info("Time Taken to complete displayServiceOrder():>>>>>"+(end-start));
		}
		return SUCCESS;
	}
	
	private void populateReasonForSpendLimit(int buyerId){
		List<ReasonCode> SLIreasonCodes = manageReasonCodeService.getAllCancelReasonCodes(buyerId,OrderConstants.TYPE_SPEND_LIMIT);
		getRequest().setAttribute(Constants.SPEND_LIMIT_INCREASE_REASON_CODES, SLIreasonCodes);
	}
	private String getDestinationSubTab() throws Exception {
		String destinationSubTab;
		
		//Commenting code SL-19820
		/*if (getSession().getAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE)!= null) {
			destinationSubTab = SODetailsUtils.ID_SUMMARY;
			getSession().removeAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE);
			return destinationSubTab;
		}*/
		String soId = getParameter(OrderConstants.SO_ID);
		if(soId == null) {
			//Commenting code SL-19820
			//soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
			soId = (String)getAttribute(OrderConstants.SO_ID);
		}
		if (getSession().getAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE+"_"+soId)!= null) {
			destinationSubTab = SODetailsUtils.ID_SUMMARY;
			getSession().removeAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE+"_"+soId);
			return destinationSubTab;
		}
		
		//GroupedSOs have individual sos
		groupId = detailsDelegate.getGroupId(soId);
		//Commenting code SL-19820
		//getSession().removeAttribute("GID");
		getRequest().removeAttribute("GID");
		if(groupId !=null) {
			//Commenting code SL-19820
			//getSession().setAttribute("GID", groupId);
			setAttribute("GID", groupId);
		}
		
		PBFilterVO filter;
		Integer wfStateId=(Integer) (getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		if(soId == null) {
			groupId = getParameter("groupId");
			if(groupId == null) {
				//Commenting code SL-19820
				//groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
				groupId = (String)getAttribute(OrderConstants.GROUP_ID);
			}
			filter = detailsDelegate.getDestinationTabForSO(groupId);
			
		} else {
			filter = detailsDelegate.getDestinationTabForSO(soId);
		}
		if(filter != null) {
			if((null!=filter.getFilterId()) && (95 == filter.getFilterId().intValue()) && (null!=wfStateId) && (130 == wfStateId.intValue()))
			{
				destinationSubTab=SODetailsUtils.ID_RESPONSE_HISTORY;
			}
			else
			{
				destinationSubTab = filter.getDestinationSubTab();
			}
		} else {
				destinationSubTab = "";
		}
		
		return destinationSubTab;
	}

	public void loadOrderGroup(String groupId)
	{
		OrderGroupVO orderGroupVO = soMonitorDelegate.getOrderGroupById(groupId);
		List<ServiceOrderSearchResultsVO> soList = soMonitorDelegate.getServiceOrdersForGroup(groupId);
		ServiceOrderDTO childOrder=null;
		// Set extra OrderGroupVO widget attributes from order child SO       
			Integer roleId = ((SecurityContext) getSession().getAttribute("SecurityContext")).getRoleId();
		if(soList!=null){
			ServiceOrderDTO serviceOrderDTO = ((ArrayList<ServiceOrderDTO>)ObjectMapper.convertVOToDTO(soList.subList(0, 1), roleId)).get(0);
			try{
				childOrder=getDetailsDelegate().getServiceOrder(serviceOrderDTO.getSoId(), get_commonCriteria().getRoleId(), null);
			}
			catch (DataServiceException e)
			{
				e.printStackTrace();
			}
			orderGroupVO.setStatus(serviceOrderDTO.getPrimaryStatus());
			orderGroupVO.setBuyerWidget(serviceOrderDTO.getBuyerWidget());
			orderGroupVO.setEndCustomerWidget(serviceOrderDTO.getEndCustomerWidget());
			orderGroupVO.setLocationWidget(serviceOrderDTO.getLocationWidget());
			orderGroupVO.setAppointmentDates(serviceOrderDTO.getAppointmentDates());
			orderGroupVO.setServiceWindow(serviceOrderDTO.getServiceWindow());
			orderGroupVO.setEndCustomerPrimaryPhoneNumberWidget(serviceOrderDTO.getEndCustomerPrimaryPhoneNumberWidget());
			orderGroupVO.setZip(serviceOrderDTO.getZip5());
			orderGroupVO.setResourceDispatchAddress(serviceOrderDTO.getResourceDispatchAddress());
			// Set these session variables for use in QuickLinks and various tabs.
			//Commenting code as part of SL-19820
			//setCurrentSOStatusCodeInSession(serviceOrderDTO.getStatus());
			setCurrentSOStatusCodeInRequest(serviceOrderDTO.getStatus());
			orderGroupVO.setRoutedResourcesForFirm(childOrder.getRoutedResourcesForFirm());
			orderGroupVO.setCarSO(childOrder.isCarSO());
			//Commenting code as part of SL-19820
			/*getSession().setAttribute(OrderConstants.GROUP_ID, groupId);
			getSession().setAttribute(THE_GROUP_ORDER, orderGroupVO);
			getSession().setAttribute(NUMBER_OF_ORDERS_IN_GROUP, soList.size());
			getSession().setAttribute(OrderConstants.SO_ID, null);
			getSession().setAttribute(THE_SERVICE_ORDER, null);*/
			//Setting the values in request scope
			setAttribute(OrderConstants.GROUP_ID, groupId);
			setAttribute(THE_GROUP_ORDER, orderGroupVO);
			setAttribute(NUMBER_OF_ORDERS_IN_GROUP, soList.size());
			setAttribute(OrderConstants.SO_ID, null);
			setAttribute(THE_SERVICE_ORDER, null);
        }else{
        	 detailsCode=DETAILS_CODE_ERROR;
        }
	}

	public String displayOrderGroup() throws Exception
	{
		if (get_commonCriteria() == null)
			return LOGIN;
				
		String roleType = get_commonCriteria().getRoleType();
		//Commenting code Sl-19820
		//Integer status = (Integer) (getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		Integer status = (Integer) (getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		String destinationSubTab = getParameter("defaultTab");
		
		if(null == destinationSubTab || destinationSubTab.equals("")) {
			destinationSubTab = getDestinationSubTab();
		} 
		
		setOrderGroupTabs(groupId, status, roleType, destinationSubTab);
		return SUCCESS;
	}
	
	
	public ServiceOrderDTO getModel() {
		return null;
	}
	
	public List getRoutedResources()
	{
		//Commenting code SL-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soID = (String) getAttribute(OrderConstants.SO_ID);
		List<SOContactDTO> contactList = null;
		try {
			contactList = getDetailsDelegate().getRoutedResources(soID);
			//Commenting code SL-19820
			//getSession().setAttribute("contactList", contactList);
			setAttribute("contactList", contactList);
			
		}catch(Exception e){
		}
		return contactList;
	}
	
	/**
	 * Method to load required for cancellation popup 
	 * 
	 * @param ServiceOrderDTO soDTO
	 * @return 
	 */
	public String loadForCancelServiceOrder()
	{
		//SL-19820
		//ServiceOrderDTO soDTO = (ServiceOrderDTO)getSession().getAttribute(THE_SERVICE_ORDER);
		ServiceOrderDTO soDTO = (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);;
		boolean tripChargeInd = getDetailsDelegate().validateFeature(_commonCriteria.getCompanyId(), Constants.TRIP_CHARGE_OVERRIDE);
		getRequest().setAttribute(BuyerFeatureConstants.TRIP_CHARGE_OVERRIDE, tripChargeInd);
		Double cancelFee = getDetailsDelegate().getCancelFeeForBuyer(_commonCriteria.getCompanyId());
		List<ReasonCode> reasonCodes = manageReasonCodeService.getAllCancelReasonCodes(_commonCriteria.getCompanyId(),OrderConstants.TYPE_CANCEL);
		preprocessFinTxnForPenalty(soDTO);
		getRequest().setAttribute(CURRENT_SO, soDTO);
		getRequest().setAttribute(CANCELLATION_FEE, cancelFee);
		getRequest().setAttribute(BuyerFeatureConstants.TRIP_CHARGE_OVERRIDE, tripChargeInd);
		getRequest().setAttribute(Constants.CANCEL_REASON_CODES, reasonCodes);
		getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS, new HashMap<Integer,ManageTaskVO>());
		return SUCCESS;
	}
	
	/**
	 * Method fetches reason codes for rescheduling for the following substatuses
	 * Substatuses are 22,23,24,25 which implies Rescheduled by End User, 
	 * Rescheduled by Provider, Rescheduled due to End Customer No Show, 
	 * Rescheduled due to Provider No Show
	 * 
	 * @return ArrayList<LookupVO>
	 */
	public ArrayList<LookupVO> getReasonCodesForReschedule()
	{
		ArrayList<LookupVO> reasons=null;
		List<Integer> codeList = Arrays.asList(REASON_LIST);
		try {
			reasons = getDetailsDelegate().getSubstatusDesc(codeList);
			getSession().setAttribute("reasonCodeList", reasons);
		}catch(Exception e){
			logger.error("Error in getReasonCodesForReschedule"+e);
		}
		return reasons;
	}
	
	// SL-19240 adding method for fetching reschedule reason codes
	/**
	 * This method return the reschedule reason codes from the
	 * lu_reschedule_reason_codes table for a buyer
	 * @return ArrayList<LookupVO>
	 */
	public ArrayList<LookupVO> getBuyerRescheduleReasonCodes(){
		ArrayList<LookupVO> reasons=null;
		try {
			reasons = getDetailsDelegate().getRescheduleReasonCodes(OrderConstants.BUYER_ROLEID);
			
			getSession().setAttribute("rescheduleReasonList", reasons);
		}catch(Exception e){
			logger.error("Error in getBuyerRescheduleReasonCodes"+e);
		}
		return reasons;
	}
	
	/**
	 * Method fetches the different permit types
	 * 
	 * @return ArrayList<LookupVO>
	 */
	public ArrayList<LookupVO> getPermitTypes()
	{
		ArrayList<LookupVO> permitTypes=null;
	
		try {
			permitTypes = getDetailsDelegate().getPermitTypes();
			getSession().setAttribute("permitTypeList", permitTypes);
		}catch(Exception e){
			logger.error("Error in getPermitTypes"+e);
		}
		return permitTypes;
	}
	public ISOMonitorDelegate getSomDelegate()
	{
		return soMonitorDelegate;
	}

	public void setSomDelegate(ISOMonitorDelegate somDelegate)
	{
		this.soMonitorDelegate = somDelegate;
	}
	public String getSoDetails() {
		return soDetails;
	}
	public void setSoDetails(String soDetails) {
		this.soDetails = soDetails;
	}
	
}
