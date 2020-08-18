package com.newco.marketplace.web.action.widgets.note;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestMessageVO;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeSODetailsVO;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;

import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.security.SecuredAction;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

public class ServiceOrderAddNoteAction extends SLBaseAction implements Preparable, ModelDriven {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4554262328714714537L;

    private ISOMonitorDelegate soMonitorDelegate;
    private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;
    private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
    private static final Logger logger = Logger.getLogger("ServiceOrderAddNoteAction");

    private ServiceOrderNoteDTO note = new ServiceOrderNoteDTO();
    private String selectedSO = "";
    private String groupId = null;
    private INotificationService notificationService;
	private IRelayServiceNotification relayNotificationService;
    
    
    
    
    public INotificationService getNotificationService() {
		return notificationService;
	}
	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	public ServiceOrderAddNoteAction(ISOMonitorDelegate soMonitorDelegate) {
        this.soMonitorDelegate = soMonitorDelegate;
    }  
    public String execute() throws Exception {
    	return SUCCESS;
    }
    
    
    public void prepare() throws Exception {
    	createCommonServiceOrderCriteria();
    }

    @SecuredAction(securityTokenEnabled = true)
    public String addNote() throws Exception {
    	HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        String strMessage = "";
        ProcessResponse processResponse = null;
        ServiceOrderNoteDTO soNoteDTO = (ServiceOrderNoteDTO) getModel();
        soNoteDTO.setSoId(getSelectedSO());
        if (StringUtils.isNotBlank(groupId)) {
        	soNoteDTO.setSoId(groupId);
        }
      
        
        soNoteDTO.setModifiedBy(get_commonCriteria().getTheUserName());
        soNoteDTO.setNoteTypeId(SOConstants.GENERAL_NOTE);
        
        soNoteDTO.setRadioSelection(request.getParameter("radioSelection"));
        if(soNoteDTO.getRadioSelection() != null && 
				soNoteDTO.getRadioSelection().equals(SOConstants.SEND_EMAIL_ALERT)){
			soNoteDTO.setEmailTobeSent(true);
		}
        
        if(soNoteDTO.getRadioSelection() != null && 
				soNoteDTO.getRadioSelection().equals(SOConstants.NO_EMAIL_ALERT)){
			soNoteDTO.setRadioSelection(SOConstants.SEND_EMAIL_ALERT);
		}
        LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext().getRoles();
        processResponse = soMonitorDelegate.serviceOrderAddNote(get_commonCriteria().getVendBuyerResId(), get_commonCriteria().getRoleId(), soNoteDTO,lvRoles);
        

        AjaxResultsDTO actionResults = new AjaxResultsDTO();
        if(processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC)){
        	actionResults.setActionState(0);
        	actionResults.setResultMessage(strMessage);
        }  
        else   {
        	actionResults.setActionState(1);
        	strMessage = "Notes added to Service Order";
        	//add method to call web service
        	int noteInd = -1;
			if(!StringUtils.isBlank(soNoteDTO.getRadioSelection())){
				noteInd=Integer.valueOf(soNoteDTO.getRadioSelection());
			}
			Integer buyerId=buyerOutBoundNotificationService.getBuyerIdForSo(getSelectedSO());
			if(StringUtils.isNotBlank(soNoteDTO.getSubject())&&StringUtils.isNotBlank(soNoteDTO.getMessage())){
		
			 if(SOConstants.SEARS_BUYER.equals(buyerId)){
			      if(noteInd==SOConstants.PUBLIC_NOTE){
			    	  RequestMessageVO  soNote=new RequestMessageVO();
			    	  soNote.setSoId(getSelectedSO());
			    	  soNote.setServiceOrderTxtDS(soNoteDTO.getSubject().trim()+" "+soNoteDTO.getMessage().trim());
			    	  RequestMsgBody requestMsgBody= buyerOutBoundNotificationService.getNPSNotificationRequestForNotes(soNote);
			    	   if(null!=requestMsgBody){
			    	          BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService.callService(requestMsgBody, getSelectedSO());
			    	          if(null!=failoverVO){
			    	          buyerOutBoundNotificationJMSService.callJMSService(failoverVO);}
			    	   }
       	      }
			 }else{ 
			//Calls method in SLBase Action 
			 insertAddNoteNPSInhomeNotificationMessages(soNoteDTO);
			 }
			    // Relay buyer notification
				if(noteInd==SOConstants.PUBLIC_NOTE && null!=lvRoles && null!=lvRoles.getRoleId() && lvRoles.getRoleId().intValue()==1){		
				boolean	relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId);
					if(relayServicesNotifyFlag){
						relayNotificationService.sentNotificationRelayServices("public_note_added_by_provider",soId);
					}				
				}		
			 
			 
			}
         	actionResults.setResultMessage(strMessage);
        	
        	
        }
        response.getWriter().write(actionResults.toXml());

        return NONE;
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
   
   
     public void setModel(Object x){
    	 note = (ServiceOrderNoteDTO) x;	
     }
	
     public Object getModel() {
		return note;
     }
     

    public String getSelectedSO() {
        return selectedSO;
    }
    public void setSelectedSO(String selectedSO) {
        this.selectedSO = selectedSO;
    }

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	
	
	
}
