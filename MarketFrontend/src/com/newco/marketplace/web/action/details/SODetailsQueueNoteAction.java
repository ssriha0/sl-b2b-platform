/**
 * 
 */
package com.newco.marketplace.web.action.details;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestMessageVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ajax.SOQueueNoteDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.newco.marketplace.web.security.SecuredAction;

/**
 * The class acts as a controller action for entering the notes record in so_notes table and 
 * the queued_date with requeue date in wfm_so_queues table. 
 * SODetailsQueueNoteAction.java
 * @author Munish Joshi
 * Jan 29, 2009
 */
public class SODetailsQueueNoteAction extends SLDetailsBaseAction implements Preparable, OrderConstants, ServiceConstants , ModelDriven<SOQueueNoteDTO>{

	private static final Logger logger = Logger.getLogger(SODetailsQueueNoteAction.class.getName());
	
	// TODO : Want to autoinject this object from beans LATER!
	private SOQueueNoteDTO sOQueueNoteDTO = new SOQueueNoteDTO();
	// Reuse the ServiceOrderNoteDTO class
	private final ServiceOrderNoteDTO soNoteDTO = new ServiceOrderNoteDTO();
	
	private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }

	// Prepare the security context information and other session/login information.
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		
	}
	// Injected through Spring beans.
	private ISODetailsDelegate detailsDelegate;

	
	
	public SODetailsQueueNoteAction( ISODetailsDelegate detailsDelegate){
		this.detailsDelegate = detailsDelegate;
	}
	
	private IBuyerOutBoundNotificationService  buyerOutBoundNotificationService;
    private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
    private INotificationService notificationService;

    
    
	
	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
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

	/**
	 * Method to update the queue notes from the SO details page.
	 * @return returns struts 2 string for success.
	 * @throws Exception The exception
	 */
	@SecuredAction(securityTokenEnabled = true)
	public String addSODetailQueueNote() throws Exception {
		//logger.info("Inside addSODetailQueueNote() method of SODetailsQueueNoteAction class with params. sOQueueNoteDTO="+sOQueueNoteDTO);
		String responseString=StringUtils.EMPTY;	
		
		
		ProcessResponse prResp = new ProcessResponse();
		boolean isMaxFollowUpCountReached = false;
		try {
		
			// Extract the SOid or Group id
			extractSOidORGroupId();
			//to prevent multiple submissions
			String uniqueId = (String)getSession().getAttribute("uniqueId");			
			if(uniqueId == sOQueueNoteDTO.getSoId()){
				return SUCCESS;
			}			
			else if(null != sOQueueNoteDTO.getSoId()){
				getSession().setAttribute("uniqueId",sOQueueNoteDTO.getSoId());
			}	
			// Clean up the note text if, entered
			purgeNoteText();
			// Set note it private or public
			checkPrivatePublicAccess();
			// Allow setting the empty notes in this case.
			soNoteDTO.setEmptyNoteAllowed(true);
			// Only in case of a new Call Back queue, check for the max follow up allowed queues count.
			if(sOQueueNoteDTO.isNewCallBackQueue()){
				// In case of a Requeue for a new Call Back queue. Check if date is valid.
				if(sOQueueNoteDTO.getTaskState() != null && sOQueueNoteDTO.getTaskState().equalsIgnoreCase(OrderConstants.REQUEUE)) {
					if(isDateTimeValid()){
						isMaxFollowUpCountReached = isMaxFollowUpCountReached(sOQueueNoteDTO);
					}
				}else{
					isMaxFollowUpCountReached = isMaxFollowUpCountReached(sOQueueNoteDTO);
					
				}
			}
			
			// display error message if user is trying to create more than allowed Follow-Up queues.
			if( sOQueueNoteDTO.isNewCallBackQueue() && isMaxFollowUpCountReached){
				// TODO : Take text msgs out of this file.
				prResp.setCode(USER_ERROR_RC);
				prResp.setMessage("Maximum Follow-Up Queues allowed is " +OrderConstants.MAX_FOLLOWUP_ALLOWED);
				
			}
			else{
				// handle note based on the action appropriately.
				prResp = handleQueueNote(prResp);
				
			}
			
			String strErrorCode = prResp.getCode();
			responseString = getProcessResponseString(prResp);
			
			String status = SUCCESS;
			status = setStatusForReponse(strErrorCode);
			responseString = status+ OrderConstants.STRICT_DELIMITER + sOQueueNoteDTO.getQueueID() + OrderConstants.STRICT_DELIMITER + responseString;
			responseString = responseString + OrderConstants.STRICT_DELIMITER +sOQueueNoteDTO.getUniqueNumber();
			
			if(!isMaxFollowUpCountReached){
				if(isDateTimeValid()){
					responseString = responseString + OrderConstants.STRICT_DELIMITER +getDateTimeForDisplay();
				}
			}
			
			if(sOQueueNoteDTO.getRandomNumUnclaim()!=null){
				responseString = responseString + OrderConstants.STRICT_DELIMITER + sOQueueNoteDTO.getRandomNumUnclaim();
			}
			
			// Response inputStream string is displayed used Ajax on the calling page.
			inputStream = new ByteArrayInputStream(responseString.getBytes());
			
		} catch (Exception e) {
			logger.error("Exception occurred in storing notes to the Queue ", e);
			responseString = "exception" +OrderConstants.STRICT_DELIMITER+ sOQueueNoteDTO.getQueueID();
			responseString = responseString + OrderConstants.STRICT_DELIMITER + "An unexpected error occurred. Please contact the help desk if the error occurs again.";
			responseString = responseString + OrderConstants.STRICT_DELIMITER +sOQueueNoteDTO.getUniqueNumber();
			inputStream = new ByteArrayInputStream(responseString.getBytes());
			
		}
		getSession().setAttribute("uniqueId",null);
		return SUCCESS;

	}// end method addSODetailQueueNote()

	/**
	 * setStatusForReponse
	 * String 
	 * @param strErrorCode
	 * @return
	 */
	private String setStatusForReponse(String strErrorCode) {
		String status;
		if (strErrorCode.equalsIgnoreCase(USER_ERROR_RC) || strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
		{	
			 status= ERROR ;			
			
		}else{
			 status = SUCCESS;
		}
		return status;
	}

	/**
	 * handleQueueNote
	 * ProcessResponse 
	 * @param prResp
	 * @return
	 * @throws Exception
	 * @throws BusinessServiceException
	 */
	private ProcessResponse handleQueueNote(ProcessResponse prResp) throws Exception, com.newco.marketplace.exception.core.BusinessServiceException {
		// if task is endstate don't bother about validating the requeue date, its not required.
		if (sOQueueNoteDTO.getTaskState() != null && sOQueueNoteDTO.getTaskState().equalsIgnoreCase(OrderConstants.ENDSTATE)) {
			// Update the complete indicator
			// Date not required
			prResp = updateCompleteIndicatorAndNote(prResp);
			
		} else if(sOQueueNoteDTO.getTaskState() != null && sOQueueNoteDTO.getTaskState().equalsIgnoreCase(OrderConstants.REQUEUE)) {
			// Update the requeue date.
			// Date required only in this case.
			if(sOQueueNoteDTO.getRequeueDate() == null || !isDateTimeValid() ){
				prResp.setCode(ServiceConstants.USER_ERROR_RC);
				prResp.setMessage("Correct date format is mm/dd/yyyy");
				
			} else if(sOQueueNoteDTO.getRequeueDate() == null || !isDateTimeInFuture()){
				prResp.setCode(ServiceConstants.USER_ERROR_RC);
				prResp.setMessage("Requeue Date/Time should be in future.");
				
			}else{
			   // Update requeue date time . 
				prResp = updateRequeueDateTimeAndNote(prResp);
			}

		} else {
			
			prResp.setCode(ServiceConstants.USER_ERROR_RC);
			prResp.setMessage("The action has no state");
			
		}
		return prResp;
	}

	/**
	 * isDateTimeValid
	 * boolean 
	 * @return
	 */
	private boolean isDateTimeValid() {
		sOQueueNoteDTO.setRequeueDate(TimeUtils.adjustYearValue(sOQueueNoteDTO.getRequeueDate()));
		return TimeUtils.isDateTimeValid(sOQueueNoteDTO.getRequeueDate(), sOQueueNoteDTO.getRequeueTime(),
				"MM/dd/yyyy" , "hh:mm a");
	}
	
	
	
	
	/**
	 * 
	 * isDateTimeInFuture
	 * boolean 
	 * @return
	 */
	
	private boolean isDateTimeInFuture(){
		
		return TimeUtils.isDateTimeInFuture(sOQueueNoteDTO.getRequeueDate(), sOQueueNoteDTO.getRequeueTime(),
				"MM/dd/yyyy" , "hh:mm a");
	}

	/**
	 * updateRequeueDateTimeAndNote
	 * ProcessResponse 
	 * @param prResp
	 * @return
	 * @throws Exception
	 * @throws BusinessServiceException
	 */
	private ProcessResponse updateRequeueDateTimeAndNote(ProcessResponse prResp) throws Exception,
			com.newco.marketplace.exception.core.BusinessServiceException {
		if(sOQueueNoteDTO.isPrimaryQueue()){
			
			if(soNoteDTO.getMessage()==null || soNoteDTO.getMessage().trim().equals(StringUtils.EMPTY)){
				prResp.setCode(ServiceConstants.USER_ERROR_RC);
				// set the error message.
				prResp.setMessage("The Note text is required for primary queue.");
				
			} else{
				prResp = serviceOrderAddNote();
				Long noteId = (Long)prResp.getObj();
				sOQueueNoteDTO.setNoteId(noteId);
				int result = updateRequeueDateTime(sOQueueNoteDTO);
			
				
			}
			
			// Note is required.
		}else{
			prResp = serviceOrderAddNote();
			Long noteId = (Long)prResp.getObj();
			sOQueueNoteDTO.setNoteId(noteId);
			int result = updateRequeueDateTime(sOQueueNoteDTO);
			
		}
		return prResp;
	}

	/**
	 * updateCompleteIndicatorAndNote
	 * ProcessResponse 
	 * @param prResp
	 * @return
	 * @throws Exception
	 * @throws BusinessServiceException
	 */
	private ProcessResponse updateCompleteIndicatorAndNote(ProcessResponse prResp) throws Exception,
			com.newco.marketplace.exception.core.BusinessServiceException {
		if(sOQueueNoteDTO.isPrimaryQueue()){
			// can only call this after purgeNoteText had put the message in soNoteDTO
			if(soNoteDTO.getMessage() == null || soNoteDTO.getMessage().trim().equals(StringUtils.EMPTY)){
				prResp.setCode(ServiceConstants.USER_ERROR_RC);
				// set the error message.
				prResp.setMessage("The Note text is required for primary queue.");
				
			} else{
				prResp = serviceOrderAddNote();
				Long noteId = (Long)prResp.getObj();
				sOQueueNoteDTO.setNoteId(noteId);
				updateCompleteIndicator(sOQueueNoteDTO);
				
			}
			// Note is required.
		}else{
			// Add the noteId generated while inserting note, which is a randon number
			// to the sOQueueNoteDTO object. 
			prResp = serviceOrderAddNote();
			Long noteId = (Long)prResp.getObj();
			sOQueueNoteDTO.setNoteId(noteId);
			updateCompleteIndicator(sOQueueNoteDTO);
			
		}
		return prResp;
	}


	/**
	 * serviceOrderAddNote ProcessResponse
	 * 
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProcessResponse serviceOrderAddNote() throws com.newco.marketplace.exception.core.BusinessServiceException {
		ProcessResponse prResp;
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		
		LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext().getRoles();
		
		
		if(get_commonCriteria().getSecurityContext().isSlAdminInd()){
			if(get_commonCriteria().getSecurityContext().getAdminRoleId() == -1) {
				resourceId = get_commonCriteria().getSecurityContext().getVendBuyerResId();
				lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getRoleId());
			}
			else {
				resourceId = get_commonCriteria().getSecurityContext().getAdminResId();
				lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getAdminRoleId());
			}
			lvRoles.setRoleName(OrderConstants.NEWCO_ADMIN);
			lvRoles.setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
			lvRoles.setVendBuyerResId(get_commonCriteria().getSecurityContext().getAdminResId());
		}
		
		//Resource Id is set as Entity Id
		soNoteDTO.setEntityId(resourceId);
		
		soNoteDTO.setSubject(StringEscapeUtils.unescapeHtml(sOQueueNoteDTO.getTaskCode()));
		
		prResp = detailsDelegate.serviceOrderAddNote(lvRoles, soNoteDTO, resourceId);
		
		//
		
		//call the webservice to add note in xml
	
		if(prResp.getCode().equals(ServiceConstants.VALID_RC)&&StringUtils.isNotBlank(soNoteDTO.getSubject())&&StringUtils.isNotBlank(soNoteDTO.getMessage())){
		int noteInd=Integer.valueOf(soNoteDTO.getRadioSelection());
		Integer buyerId=buyerOutBoundNotificationService.getBuyerIdForSo(soNoteDTO.getSoId());
		 if(SOConstants.SEARS_BUYER.equals(buyerId)){
		      if(noteInd==SOConstants.PUBLIC_NOTE){
		    	  RequestMessageVO  soNote=new RequestMessageVO();
		    	  soNote.setSoId(soNoteDTO.getSoId());
		    	  soNote.setServiceOrderTxtDS(soNoteDTO.getSubject().trim()+" "+soNoteDTO.getMessage().trim());
		    	  RequestMsgBody requestMsgBody= buyerOutBoundNotificationService.getNPSNotificationRequestForNotes(soNote);
		    	    if(null!=requestMsgBody){
		    	         BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, soNoteDTO.getSoId());
		    	          if(null!=failoverVO){
		    	               buyerOutBoundNotificationJMSService.callJMSService(failoverVO);}
		    	          }
    	      }
		 }  
		//Add Note NPS Inhome Notification
		 insertAddNoteNPSInhomeNotificationMessages(soNoteDTO);
	   }
		//
		return prResp;
	}


	
	
	public void insertAddNoteNPSInhomeNotificationMessages(
			ServiceOrderNoteDTO soNoteDTO) {

		soId = soNoteDTO.getSoId();
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
			Integer roleId = lvRoles.roleId;
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
	
	/**
	 * checkPrivatePublicAccess
	 * void 
	 */
	private void checkPrivatePublicAccess() {
		if (sOQueueNoteDTO.getPri() == null) {
			soNoteDTO.setRadioSelection(SO_NOTE_PUBLIC_ACCESS);
			// Email should be sent if 'Public' is selected - SL-7687
			soNoteDTO.setEmailTobeSent(true);
		} else {
			soNoteDTO.setRadioSelection(SO_NOTE_PRIVATE_ACCESS);
			// Email should not be sent if 'Private' is selected - SL-7687
			soNoteDTO.setEmailTobeSent(false);
		}
	}


	/**
	 * purgeNoteText
	 * void 
	 */
	private void purgeNoteText() {
		/*Check if the message text is more than the specified limit. The javascript check on screen stops the user
		from entering characters more than the specified limit, but special characters (Newline char) can still be
		there. So, if the message length is more than the limit, remove the special characters.*/
		if (sOQueueNoteDTO.getNoteText() != null) {
			
			
			
			if (sOQueueNoteDTO.getNoteText().length() > OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH) {
				String purgedMessage = StringEscapeUtils.unescapeHtml(sOQueueNoteDTO.getNoteText().trim().replaceAll("\\n", StringUtils.EMPTY));
				// Remove strict de-limiter from the user message.
				purgedMessage = purgedMessage.replaceAll(OrderConstants.STRICT_DELIMITER, StringUtils.EMPTY);
				sOQueueNoteDTO.setNoteText(purgedMessage);
				soNoteDTO.setMessage(purgedMessage); // String special
														// character
			} else {
				String purgedMessage = StringEscapeUtils.unescapeHtml(sOQueueNoteDTO.getNoteText().trim());
				purgedMessage = purgedMessage.replaceAll(OrderConstants.STRICT_DELIMITER, StringUtils.EMPTY);
				sOQueueNoteDTO.setNoteText(purgedMessage);
				soNoteDTO.setMessage(purgedMessage);
			}
		}
	}


	/**
	 * extractSOidORGroupId
	 * void 
	 */
	private void extractSOidORGroupId() throws Exception {
		//SL-19820
		//String groupId = (String)getSession().getAttribute("GID");//
		//TODO : For grouped SO & SO in group, groupId should be passed.
		String groupId = getParameter("groupId");
		setAttribute(GROUP_ID, groupId);
		
		if(StringUtils.isBlank(groupId))
		{
			//SL-19820
			//final String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
			final String soId = getParameter("soId");
			setAttribute(SO_ID, soId);

			if(StringUtils.isBlank(soId)){
				throw new Exception("Exception occurred because we don't have Group id or SO ID in the session. GID="+groupId+"  SO_ID="+soId);
			}
			
			//SL-19820
			groupId = detailsDelegate.getGroupedId(soId);
			if(StringUtils.isNotBlank(groupId)){
				sOQueueNoteDTO.setGroupId(groupId);
				soNoteDTO.setGroupId(groupId);
			}else{
				sOQueueNoteDTO.setSoId(soId);
				soNoteDTO.setSoId(soId);		
			}
		}
		else{
			sOQueueNoteDTO.setGroupId(groupId);
			soNoteDTO.setGroupId(groupId);	
		}
		
		soNoteDTO.setNoteTypeId(SOConstants.GENERAL_NOTE);
	}


	/**
	 * 
	 * updateCompleteIndicator
	 * int 
	 * @param sOQueueNoteDTO
	 * @return
	 * @throws Exception
	 */
	private int updateCompleteIndicator(SOQueueNoteDTO sOQueueNoteDTO) throws Exception{
		int result = 0;
		if(sOQueueNoteDTO.isNewCallBackQueue()){
			result = insertNewCallBackQueue(sOQueueNoteDTO);
		} else{
			detailsDelegate.updateCompleteIndicator(sOQueueNoteDTO);
		}
		return result;
		
	}
	
	/**
	 * 
	 * updateRequeueDateTime
	 * int 
	 * @param sOQueueNoteDTO
	 * @return
	 * @throws Exception
	 */
	private int updateRequeueDateTime(SOQueueNoteDTO sOQueueNoteDTO) throws Exception{
		int result = 0;
		if(sOQueueNoteDTO.isNewCallBackQueue()){
			result= insertNewCallBackQueue(sOQueueNoteDTO);
		} else{
			detailsDelegate.updateRequeueDateTime(sOQueueNoteDTO);
		}
		return result;
		
	}
	
	private int insertNewCallBackQueue(SOQueueNoteDTO sOQueueNoteDTO) throws Exception{
		
		return detailsDelegate.insertNewCallBackQueue(sOQueueNoteDTO);
		
	}
	
		

	public void setModel(Object obj) {
		sOQueueNoteDTO= (SOQueueNoteDTO)obj;
	}
	
	public SOQueueNoteDTO getModel() {
		return sOQueueNoteDTO;
	}
	
	/**
	 * 
	 * getDateTimeForDisplay
	 * String 
	 * @return
	 */
	
	private String getDateTimeForDisplay(){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat fullDf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date requeueDate = null;
		try {
			requeueDate = TimeUtils.combineDateTimeFromFormat(df.parse(sOQueueNoteDTO.getRequeueDate()), sOQueueNoteDTO.getRequeueTime(), "yyyy-MM-dd" ,  "hh:mm a");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return requeueDate==null?"":fullDf.format(requeueDate);
	}
	
	/**
	 * 
	 * isMaxFollowUpCountReached
	 * boolean 
	 * @param sOQueueNoteDTO
	 * @return
	 * @throws Exception
	 */
	
	private boolean isMaxFollowUpCountReached(SOQueueNoteDTO sOQueueNoteDTO)throws Exception{
		boolean result;
		result = detailsDelegate.isMaxFollowUpCountReached(sOQueueNoteDTO);
		return result;
	}
	

}
