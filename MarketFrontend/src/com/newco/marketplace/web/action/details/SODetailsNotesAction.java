package com.newco.marketplace.web.action.details;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestMessageVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * 
 * $Revision: 1.36 $ $Author: nsanzer $ $Date: 2008/06/12 16:08:05 $
 */

/*
 * Maintenance History
 * $Log: SODetailsNotesAction.java,v $
 * Revision 1.36  2008/06/12 16:08:05  nsanzer
 * Fix private notes for Buyers and Providers - They now show up in the list.
 *
 * Revision 1.35  2008/06/06 19:53:19  nsanzer
 * Fix private notes for SLA - should only be seen by other SLA users  SLA Public notes should be viewable to buyer/provider
 *
 * Revision 1.34  2008/04/26 01:13:48  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.31.6.1  2008/04/01 22:04:01  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.32  2008/03/27 18:57:51  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.31.10.1  2008/03/25 18:29:53  mhaye05
 * code cleanup
 *
 * Revision 1.31  2008/02/26 18:18:03  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.30.10.1  2008/02/25 23:15:19  iullah2
 * distance calculation on SOM,SOD
 *
 * Revision 1.30  2008/01/24 23:50:52  mhaye05
 * now pulling role_id from common criteria not session
 *
 * Revision 1.29  2008/01/24 19:35:52  dmill03
 * Updated for PowerBuyer feature
 *
 * Revision 1.28  2008/01/21 13:21:45  hrajago
 * To view all notes and deleted notes.
 *
 * Revision 1.27  2008/01/16 12:17:13  hrajago
 * Added Created By Name, ModifiedBy and entity Id
 *
 * Revision 1.26  2008/01/08 21:21:45  pbhinga
 * Changed code to fix defect# Sears00044441. Added validation for subject and message in notes and support tabs in SOD (posted orders) to allow only 30 / 750 chars respectively.
 *
 * Revision 1.25  2007/12/31 19:31:59  langara
 * Sears00044443
 *
 * Revision 1.24  2007/12/13 23:53:24  mhaye05
 * replaced hard coded strings with constants
 *
 */
public class SODetailsNotesAction extends SLDetailsBaseAction implements Preparable, OrderConstants, ServiceConstants, ModelDriven<ServiceOrderNoteDTO>
{
	
	private static final long serialVersionUID = 10101;// arbitrary number to get rid
												// of warning
	private static final Logger logger = Logger.getLogger(SODetailsNotesAction.class.getName());
	
	private ServiceOrderNoteDTO soNoteDTO = new ServiceOrderNoteDTO();
	private ISODetailsDelegate detailsDelegate;
	private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;
	private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
	
    private INotificationService notificationService;

	private IRelayServiceNotification relayNotificationService;
    
    //SL-19820
    String soID;
    String groupId;
    String resourceId;

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public SODetailsNotesAction(ISODetailsDelegate detailsDelegate){
		this.detailsDelegate = detailsDelegate;
	}
	
	/**
	 * Struts 2.x method for adding a generic note to a service order
	 * @return
	 * @throws Exception
	 */
	public String addDetailGeneralNote() throws Exception
	{
		return this.addNote(SOConstants.GENERAL_NOTE);
		
	}//end method addDetailGeneralNote()
	
	
	/**
	 * Struts 2.x method for adding a support note to a service order
	 * @return
	 * @throws Exception
	 */
	public String addDetailSupportNote() throws Exception
	{
        return this.addNote(SOConstants.SUPPORT_NOTE);
	    	
	}//end method addDetailSupportNote()
	
	
	/*
	 * Adds service order not to system
	 */
	private String addNote(Integer noteType)
	{
		String soID = null;
		String defaultSelectedTab = "";
		String strErrorCode = "";
		String strErrorMessage = "";
		String strResponse = "";
		ProcessResponse prResp = null;
		
		//Sl-19820
		//soID = getCurrentServiceOrderId();
		soID = (String) getAttribute(SO_ID);
		if(soID == null)
		{
			//SL-19820
			//soID = (String)getSession().getAttribute(GROUP_ID);
			soID = (String) getAttribute(GROUP_ID);
		}
		
		if(soID == null)
			//SL-19820
			//return GOTO_COMMON_DETAILS_CONTROLLER;
			return SUCCESS;
		
		try
		{
			if (noteType == SOConstants.GENERAL_NOTE)
				defaultSelectedTab = SODetailsUtils.ID_NOTES;
			else
				defaultSelectedTab = SODetailsUtils.ID_SUPPORT;

			//SL-19820: The below variable is not used anywhere.
			//Hence commenting
			//String flow = (String) getSession().getAttribute(Constants.SESSION.WORKFLOW_DISPLAY_TAB);
	
			ServiceOrderNoteDTO soNoteDTO = (ServiceOrderNoteDTO)getModel();
			
			if(soNoteDTO.getRadioSelection() != null && 
					soNoteDTO.getRadioSelection().equals(SOConstants.SEND_EMAIL_ALERT)){
				soNoteDTO.setEmailTobeSent(true);				
			}
			
			if(soNoteDTO.getRadioSelection() != null && 
					soNoteDTO.getRadioSelection().equals(SOConstants.NO_EMAIL_ALERT)){
				soNoteDTO.setRadioSelection(SOConstants.SEND_EMAIL_ALERT);
			}
			
			soNoteDTO.setNoteTypeId(noteType);
			
			//For Support Note
			if(SOConstants.SUPPORT_NOTE.equals(noteType)){
				soNoteDTO.setSubject(soNoteDTO.getSubjectSupport());
				soNoteDTO.setMessage(soNoteDTO.getMessageSupport());
			}
			
			/*Check if the message text is more than the specified limit. The javascript check on screen stops the user
			from entering characters more than the specified limit, but special characters (Newline char) can still be
			there. So, if the message length is more than the limit, remove the special characters.*/
			if(soNoteDTO.getMessage() != null && 
					soNoteDTO.getMessage().length() > OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH) {
				String purgedMessage = soNoteDTO.getMessage().trim().replaceAll("\\n","");
				// Remove strict de-limiter from the user message.
				purgedMessage = purgedMessage.replaceAll(OrderConstants.STRICT_DELIMITER,""); 
				soNoteDTO.setMessage(purgedMessage); //String special character
			}
			
			
			soNoteDTO.setSoId(soID);
			
			int errCnt = 0;
			if (noteType == SOConstants.GENERAL_NOTE) {
				soNoteDTO.validate("Note");
			} else {
				soNoteDTO.validate("SupportNote");
				soNoteDTO.setEmailTobeSent(true);
			}
			
			
			if (soNoteDTO.getErrors() != null)
				errCnt = soNoteDTO.getErrors().size();
			if ( errCnt > 0){
				//Sl-19820
				//strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
				strResponse = SUCCESS;
				//getSession().setAttribute(Constants.SESSION.SOD_NOTE_DTO, soNoteDTO);
				getSession().setAttribute(Constants.SESSION.SOD_NOTE_DTO+"_"+soID, soNoteDTO);
				setAttribute(Constants.SESSION.SOD_NOTE_DTO, soNoteDTO);
			}else
			{	
				LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext().getRoles();
				Integer resourceId = get_commonCriteria().getVendBuyerResId();
				Integer serviceliveRoleId=new Integer(0);
				if(null!=lvRoles && null!=lvRoles.getRoleId())
					serviceliveRoleId=lvRoles.getRoleId();	
				/*
				 * Added code to insert 
				 * Created By Name - User Name (First and Last Name), 
				 * Modified By - User Id,
				 * Entity Id - Resource Id
				 * 	- Covansys (Offshore)
				 */
				if(get_commonCriteria().getSecurityContext().isSlAdminInd()){
					lvRoles.setRoleName(OrderConstants.NEWCO_ADMIN);
					lvRoles.setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
					
					//If the admin-role id is not set, use the role id 
					if(get_commonCriteria().getSecurityContext().getAdminRoleId() == -1){
						resourceId = get_commonCriteria().getSecurityContext().getVendBuyerResId();
						lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getRoleId());
					}else{
						resourceId = get_commonCriteria().getSecurityContext().getAdminResId(); 
						lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getAdminRoleId());
					}
					
					lvRoles.setVendBuyerResId(get_commonCriteria().getSecurityContext().getAdminResId());
				}
				//Resource Id is set as Entity Id
				soNoteDTO.setEntityId(resourceId);
				
			
				prResp = detailsDelegate.serviceOrderAddNote(lvRoles, soNoteDTO, resourceId);
				
				strErrorCode = prResp.getCode();
				strErrorMessage = prResp.getMessages().get(0);
				
				if (strErrorCode.equalsIgnoreCase(USER_ERROR_RC))
				{	
					//Sl-19820
					//strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
					strResponse = SUCCESS;
					//getSession().setAttribute(Constants.SESSION.SOD_NOTE_DTO, soNoteDTO);
					getSession().setAttribute(Constants.SESSION.SOD_NOTE_DTO+"_"+soID, soNoteDTO);
					setAttribute(Constants.SESSION.SOD_NOTE_DTO, soNoteDTO);
				}else if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
				{	
					//Go to common error page in case of business logic failure error or fatal error
					defaultSelectedTab = "";
					strResponse = ERROR; 
					this.setReturnURL(this.lookupReturnAction(noteType));
					this.setErrorMessage(strErrorMessage);
				}else 
				{
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.SOD_MSG, NOTE_ADD_SUCCESS);
					getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soID, NOTE_ADD_SUCCESS);
					setAttribute(Constants.SESSION.SOD_MSG, NOTE_ADD_SUCCESS);
					//strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
					strResponse = SUCCESS;
					//call the webservice to add note in xml
					int noteInd = -1;
					if(!StringUtils.isBlank(soNoteDTO.getRadioSelection())){
						noteInd=Integer.valueOf(soNoteDTO.getRadioSelection());
					}
					Integer buyerId=buyerOutBoundNotificationService.getBuyerIdForSo(soID);
					if(StringUtils.isNotBlank(soNoteDTO.getSubject())&&StringUtils.isNotBlank(soNoteDTO.getMessage())){
					 if(SOConstants.SEARS_BUYER.equals(buyerId)){
					      if(noteInd==SOConstants.PUBLIC_NOTE){
					    	  RequestMessageVO  soNote=new RequestMessageVO();
					    	  soNote.setSoId(soID);
					    	  soNote.setServiceOrderTxtDS(soNoteDTO.getSubject().trim()+" "+soNoteDTO.getMessage().trim());
					    	  RequestMsgBody requestMsgBody= buyerOutBoundNotificationService.getNPSNotificationRequestForNotes(soNote);
					    	    if(null!=requestMsgBody){
					    	         BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, soID);
					    	          if(null!=failoverVO){
					    	               buyerOutBoundNotificationJMSService.callJMSService(failoverVO);}
					    	          }
		        	      }
					 } 
					//Add NPS Note notification for inhome 
					 insertAddNoteNPSInhomeNotificationMessages(soNoteDTO);
					//Refetch the service order from database
					 //SL-19820 : This is not needed
					//getSession().setAttribute(REFETCH_SERVICE_ORDER, new Boolean(true));
					 
					    // Relay buyer notification
						if(noteInd==SOConstants.PUBLIC_NOTE && serviceliveRoleId.intValue()==1){		
						boolean	relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soID);
							if(relayServicesNotifyFlag){
								relayNotificationService.sentNotificationRelayServices("public_note_added_by_provider",soID);
							}				
						}		
					 
				}
				}
			}
			
			getRequest().setAttribute("soNoteDTO", soNoteDTO);
			//Redisplay the tabs
		    logger.debug("defaultSelectedTab : " + defaultSelectedTab);
			this.setDefaultTab( defaultSelectedTab );
			
		}catch (BusinessServiceException e) {
			logger.info("Exception in cancelling the SO: ", e);
		}
		//SL-19820
		this.soID = StringUtils.isNotBlank(getParameter("soId"))?getParameter("soId"):null;
		this.groupId = StringUtils.isNotBlank(getParameter("groupId"))?getParameter("groupId"):null;
		return strResponse;
	}//end method addNote(Integer)

	
	
	
	
	public void insertAddNoteNPSInhomeNotificationMessages(
			ServiceOrderNoteDTO soNoteDTO) {

		String soId = soNoteDTO.getSoId();
		InHomeSODetailsVO inHomeSODetailsVO = new InHomeSODetailsVO();
		InHomeSODetailsVO result = new InHomeSODetailsVO();
		inHomeSODetailsVO.setSoId(soId);
		try {
			result = notificationService
					.getSoDetailsForNotes(inHomeSODetailsVO);
	        LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext().getRoles();
	      //add method to call web service
        	int noteInd = -1;
			if(!StringUtils.isBlank(soNoteDTO.getRadioSelection())){
				noteInd=Integer.valueOf(soNoteDTO.getRadioSelection());
			}
			
			Integer empId = result.getVendorId();
			Integer noteTypeId = noteInd;
			Integer roleId = get_commonCriteria().getSecurityContext().getRoleId();
			String subjMessage = soNoteDTO.getSubject()
					+ InHomeNPSConstants.SEPERATOR + soNoteDTO.getMessage();
			String createdBy=lvRoles.getLastName() + ", " + lvRoles.getFirstName();
			boolean isEligibleForNPSNotification = false;
			isEligibleForNPSNotification = notificationService
					.validateNPSNotificationEligibility(result.getBuyerId(),
							soId);
			if (isEligibleForNPSNotification) {
				// Call Insertion method
				
				
				InHomeSODetailsVO inHomeSODetails = new InHomeSODetailsVO();
				inHomeSODetails.setSoId(soId);
				inHomeSODetails.setNoteTypeId(noteTypeId);
				inHomeSODetails.setRoleId(roleId);
				inHomeSODetails.setSubjMessage(subjMessage);
				inHomeSODetails.setCreatedBy(createdBy);
				inHomeSODetails.setEmpId(empId);
				if(get_commonCriteria().getSecurityContext().isSlAdminInd()){
					inHomeSODetails.setSlAdmin(Boolean.TRUE);
				}
				notificationService.insertNotification(inHomeSODetails);

			}
		} catch (Exception e) {
			logger.error(" error in inserting notification for add Note" + e);
		}
	}
	
	/*
	 * 
	 */
	private String lookupReturnAction(Integer noteType)
	{
		//SL-19820
		String parameters = "soId="+this.soID+"&groupId="+this.groupId+"&resId="+this.resourceId;
		if (noteType == null)
			return SODetailsUtils.TAB_PROV_PS_1+"?"+parameters;
		else if (noteType.equals(SOConstants.SUPPORT_NOTE))
			return SODetailsUtils.TAB_SUPPORT+"&"+parameters;
		else if (noteType.equals(SOConstants.GENERAL_NOTE))
			return SODetailsUtils.TAB_SO_NOTES+"&"+parameters +SODetailsUtils.generateUniqueURLPart();
		else
		    return SODetailsUtils.TAB_PROV_PS_1+"?"+parameters;
		
	}//end method lookupReturnAction(Integer)
	

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		clearSessionAttributes();
		
		//SL-19820
		String id = getParameter("soId");
		if(StringUtils.isBlank(id)){
			id = getParameter("groupId");
		}
		ServiceOrderNoteDTO soNoteDTO = (ServiceOrderNoteDTO)getSession().getAttribute(Constants.SESSION.SOD_NOTE_DTO+"_"+id);
		if (soNoteDTO == null)
			soNoteDTO = (ServiceOrderNoteDTO)getModel();
		else
			setModel(soNoteDTO);
		getSession().removeAttribute(Constants.SESSION.SOD_NOTE_DTO+"_"+id);
		setHideAddNotes();
	}
	
	
	private void setHideAddNotes()
	{
		//Sl-19820
		//String soID = getCurrentServiceOrderId();
		String id = null;
		String soID = getParameter("soId");
		if(StringUtils.isBlank(soID)){
			soID = null;
		}else{
			id = soID;
		}
		this.soID = soID;
		setAttribute(OrderConstants.SO_ID, soID);
		
		String resId = getParameter("resId");
		setAttribute("routedResourceId", resId);
		this.resourceId = resId;
		
		String groupId = getParameter("groupId");
		if(StringUtils.isBlank(groupId)){
			groupId = null;
		}else{
			id = groupId;
		}
		this.groupId = groupId;
		setAttribute(GROUP_ID, groupId);
		
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		Boolean hideAddNotes = false;

		if(soID != null)
		{
			Integer roleID = _commonCriteria.getRoleId(); 
			
			ServiceOrderDTO soDTO=null;
			try
			{
				if(getDetailsDelegate() != null)
					soDTO = getDetailsDelegate().getServiceOrder(soID, roleID, null);
			}
			catch(Exception e)
			{
				
			}
			Integer statusId = null;
			if(soDTO != null)
			{
				statusId = soDTO.getStatus();
			}
			if(statusId != null)
			{
				if(statusId == OrderConstants.CLOSED_STATUS)
				{
					hideAddNotes = true;
				}
			}
		}
		setAttribute("hideAddNotes", hideAddNotes);		
	}
	

	public String execute() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * Struts 2.0 method that will return a list of note objects in the model
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadNotes() throws Exception
	{
		if(get_commonCriteria() == null)
			return "error";
		
		if(get_commonCriteria().getSecurityContext() == null)
			return "error";
		
		String soID = null;
		ServiceOrderNoteDTO soNoteDTO = null;
		soNoteDTO = (ServiceOrderNoteDTO) getModel();
		String noteType = getRequest().getParameter("noteType");
		Integer noteTypeId = null;
		String returnPage = "";
		Integer roleId = null;
		Integer queryRoleId = null;
		String groupId = null;
		/*queryRoleId is used for the query.  If the user is on the
		notes tab, then we set the roleId = null since the notes tab
		allows both buyers&providers the ability to view each other's notes.
		If the user is on the support tab, then the role id is set to the provider/buyer
		role id since only the role type logged on can see the support notes.
		The roleId variable is set later after the results are returned so that the JSP
		can use it*/
		
		logger.info("*****  Entering loadNotes()  *****");
		
		//Sl-19820
		//soID = getCurrentServiceOrderId();
		soID = (String)getAttribute(OrderConstants.SO_ID);
		groupId = getParameter(OrderConstants.GROUP_ID);
		//Sl-19820
		setAttribute(OrderConstants.GROUP_ID, groupId);
		
		if(soID == null && groupId == null)
		{
			logger.debug("Service Order ID or Group Order ID cannot be found");
			this.setReturnURL(this.lookupReturnAction(SOConstants.GENERAL_NOTE));
			this.setErrorMessage("Service Order # not found");
			return ERROR;			
		}
		
		if(noteType == null)
		{
			logger.debug("Note Type is NULL");
			this.setReturnURL(this.lookupReturnAction(SOConstants.GENERAL_NOTE));
			this.setErrorMessage("Service Order # not found");
			return ERROR;			
		}
		

		if (get_commonCriteria().getSecurityContext().getRoles() != null)
		{
			roleId = get_commonCriteria().getSecurityContext().getRoles().getRoleId();
			queryRoleId = roleId;
    	}

		List <Integer> noteTypes = new ArrayList<Integer>();
		if (noteType.equals("general")){
			noteTypes.add(new Integer(2));
			noteTypes.add(new Integer(3));
			soNoteDTO.setNoteTypeIds(noteTypes);
			returnPage = GENERAL_NOTE_SUCCESS;
		}else if (noteType.equals("support")){
			noteTypeId = 1;
			soNoteDTO.setNoteTypeId(noteTypeId);
			returnPage = SUPPORT_NOTE_SUCCESS;
		}
		
		if(StringUtils.isBlank(groupId) == false)
		{
			soNoteDTO.setSoId(groupId);			
			logger.debug("Group Order ID is (" + groupId + ").");
		}
		else
		{
			soNoteDTO.setSoId(soID);			
			logger.debug("Service Order ID is (" + soID + ").");
		}
			
		
    	soNoteDTO.setRoleId(queryRoleId);
    	
    	
    	
    	/*
    	 * Added to get all the Notes and Deleted notes for SL Administrator
    	 */
    	SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
    	
    	
    	if (securityContext != null)
    	{
    		
    		//SL-19050 -New buyer feature set check
    		if((queryRoleId == 3 || queryRoleId == 2) && null!=securityContext.getCompanyId() )
        	{
        		
        	soNoteDTO.setNoteFeatureInd(detailsDelegate.validateFeature(securityContext.getCompanyId(), "NOTES_FEATURE"));
        	
        	}
    		else
    		{
    			soNoteDTO.setNoteFeatureInd(false);
    		}
        	
    		
    		
    		if (securityContext.isSlAdminInd())
    		{
    			// Group Level Notes
    			soNoteDTO.setGroupNotes(detailsDelegate.serviceOrderGetAllGroupNotes(soNoteDTO));
    			
    			
    			//If the login user is SL Administrator then all the notes and deleted notes will be retrieved
    			soNoteDTO.setNotes(detailsDelegate.serviceOrderGetAllNotes(soNoteDTO));
    			soNoteDTO.setDeletedNotes(detailsDelegate.serviceOrderGetDeletedNotes(soNoteDTO));    			    				
    		}
    		else // Regular Buyer or Provider
    		{
    			if(groupId != null)
    			{
    				// Group Level Notes
    				List<ServiceOrderNote> groupNotes = detailsDelegate.serviceOrderGetAllNotes(soNoteDTO); 
        			soNoteDTO.setGroupNotes(groupNotes);
        		}
    			else // Child/Single Service Orders
    			{
        			// Group Level Notes
        			soNoteDTO.setGroupNotes(detailsDelegate.serviceOrderGetAllGroupNotes(soNoteDTO));
        			
        			
        			//If the user is non - SL administrator then notes specific to the user is fetched.
        			//SL_ADMIN private notes will also not be displayed. They are removed here.    			
        			List<ServiceOrderNote> finalNotes = removeAdminPrivateNotes(detailsDelegate.serviceOrderGetNotes(soNoteDTO));    			
        			soNoteDTO.setNotes(finalNotes);
        			
        			//Deleted notes will not be displayed.    			
        			soNoteDTO.setDeletedNotes(null);    				
    			}
    		}
    	}
    	else
    	{
    		//If security context is null, it will fetch the notes related to the current login user
    		soNoteDTO.setNotes(detailsDelegate.serviceOrderGetNotes(soNoteDTO));
    	}
    	logger.debug("Number of notes for Service Order ID(" + soID + ") is (" + (soNoteDTO.getNotes() == null ? "empty" : soNoteDTO.getNotes().size())+ ").");
		
    	logger.info("*****  Exiting loadNotes()  *****");
    	
    	//Setting for the JSP page
    	soNoteDTO.setRoleId(roleId);
    	
    	if(null == soNoteDTO.getRadioSelection() || 
    			soNoteDTO.getRadioSelection().equals("")){
    		soNoteDTO.setRadioSelection(SOConstants.NO_EMAIL_ALERT);
    	}
    		
    	return returnPage;
	}//end method loadNotes()

	private List<ServiceOrderNote> removeAdminPrivateNotes(List<ServiceOrderNote> notes) {
		
		//SL_ADMIN private notes will also not be displayed. They are removed here.
		List<ServiceOrderNote> finalNotes = new ArrayList<ServiceOrderNote> ();
		for (ServiceOrderNote son : notes) {
			if((son.getEntityId() != null && son.getEntityId() > 0) || (son.getPrivateId() != 1)) {
				if((son.getRoleId() == OrderConstants.NEWCO_ADMIN_ROLEID && son.getPrivateId() != 1) || son.getRoleId() != OrderConstants.NEWCO_ADMIN_ROLEID)
					finalNotes.add(son);
			}
		}
		
		return finalNotes;
	}
	
	
	/*
	 * SL-19050 -Mark Note as Read for buyers
	 */
	public String markAsRead()
	{
		String noteId=getParameter("noteId");
		try
		{
			detailsDelegate.markSOAsRead(noteId);
			
		}catch (DataServiceException e) {
			logger.info("Exception in marking note as Read: ", e);
		}
	
		return SUCCESS;
	}//end method markAsRead()
	
	
	/*
	 * SL-19050 -Mark Note as UnRead for buyers
	 */
	public String markAsUnRead()
	{
		String noteId=getParameter("noteId");
		try
		{
			detailsDelegate.markSOAsUnRead(noteId);
			
		}catch (DataServiceException e) {
			logger.info("Exception in marking note as Read: ", e);
		}
	
		return SUCCESS;
	}//end method markAsRead()
	
	
	
	public void setModel(Object x){
		soNoteDTO = (ServiceOrderNoteDTO) x;	
	}
	
	
	public ServiceOrderNoteDTO getModel() {
		return soNoteDTO;
	}


	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}


	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}


	public IBuyerOutBoundNotificationJMSService getBuyerOutBoundNotificationJMSService() {
		return buyerOutBoundNotificationJMSService;
	}


	public void setBuyerOutBoundNotificationJMSService(
			IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService) {
		this.buyerOutBoundNotificationJMSService = buyerOutBoundNotificationJMSService;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
	
	
}//end class