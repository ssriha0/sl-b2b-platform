package com.newco.marketplace.web.action.details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.core.BusinessServiceException;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ReleaseServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.servicelive.activitylog.client.IActivityLogHelper;

/**
 * $Revision: 1.10 $ $Author: glacy $ $Date: 2008/04/26 01:13:48 $
 */

/*
 * Maintenance History
 * $Log: ReleaseSOAction.java,v $
 * Revision 1.10  2008/04/26 01:13:48  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.8.6.1  2008/04/23 11:41:35  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.9  2008/04/23 05:19:30  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.8  2008/02/26 18:18:03  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.7.14.1  2008/02/25 23:15:19  iullah2
 * distance calculation on SOM,SOD
 *
 * Revision 1.7  2008/01/02 16:24:34  zizrale
 * setting the role
 *
 * Revision 1.6  2007/12/13 23:53:24  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.5  2007/12/11 20:33:41  zizrale
 * remove message if success...
 *
 * Revision 1.4  2007/12/05 21:29:30  zizrale
 * session states, error validation, cleanup
 *
 * Revision 1.3  2007/11/20 23:17:38  zizrale
 * using process response, doing validation, cleanup
 *
 * Revision 1.2  2007/11/14 21:58:51  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class ReleaseSOAction extends SLDetailsBaseAction implements Preparable,ModelDriven{
	

	private static final long serialVersionUID = -7920137428157585765L;
	private static final Logger logger = Logger.getLogger("ReleaseSOAction");
	private ISODetailsDelegate soDetailsManager;
	private ReleaseServiceOrderDTO release = new ReleaseServiceOrderDTO();
	
	private IMobileGenericBO mobileGenericBO;

	
	private IActivityLogHelper helper;
  //Sl-19820 
	String soId;
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public void setHelper(IActivityLogHelper helper)
	{
		this.helper = helper;
	}
	
	public ReleaseSOAction(ISODetailsDelegate delegate) {
		this.soDetailsManager = delegate;
	}
	
	public String releaseServiceOrder(){
		
		logger.info("Entering the ReleaseSOAction.releaseServiceOrder()");
		
		String result = null;
		ProcessResponse processResponse = null;
		Integer resourceID = get_commonCriteria().getVendBuyerResId();
		Integer vendorId = get_commonCriteria().getCompanyId();
		ArrayList<LuProviderRespReasonVO> releaseReasonsMap = new ArrayList();
		
		releaseReasonsMap=(ArrayList<LuProviderRespReasonVO>)getSession().getAttribute("releaseResonCodeMap");
		//sl-19820
		//String soID = (String)getSession().getAttribute(OrderConstants.SO_ID);
		//Integer stateId = (Integer)getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE);soId
		String soID =getParameter("soId");
		this.soId=soID;
		Integer stateId=null;
		if(null!=getParameter("statusId")){
			
			stateId=Integer.parseInt(getParameter("statusId"));
		}
		int roleId = get_commonCriteria().getRoleId().intValue();
		boolean isError = false;
		
		if(soID != null)
		{
			ReleaseServiceOrderDTO releaseSO = (ReleaseServiceOrderDTO)getModel();
			//SL-20195:
			//a) Setting the reasonCode Map to session the first time it is fetched from the DB.
			//b) Use this reasonCode Map to get the reasonText when Release button is clicked and set it to DTO.
			if (null != releaseReasonsMap) {
				for (LuProviderRespReasonVO reasonVO : releaseReasonsMap) {
					if (null!=releaseSO.getReleaseReasonCode() && reasonVO.getRespReasonId() == releaseSO.getReleaseReasonCode().intValue()) {
						releaseSO.setReasonText(reasonVO.getDescr());
					}
				}
			}
			if (roleId != OrderConstants.PROVIDER_ROLEID){
				result = "Only a provider can release service order.";
				isError = true;
			}
			
			
			if (stateId == null){
				// get based on soId
				ServiceOrderDTO so = null;
				try{
					so = soDetailsManager.getServiceOrder(soID, roleId, null);
				}catch(Exception e){
					logger.error("Problem retrieving service order info in ReleaseSOAction.");
				}
				if (so != null){
					stateId = so.getStatus();
					get_commonCriteria().setStatusId(stateId);
                    //also get the accepted resource id since the logged in resource id could be different depending on the company
                    resourceID = so.getAcceptedResourceId();
				}else{
					result = "Can not find status of the service order.";
					isError = true;
				}
			}
			
	        if(!isError)
	        {	
	        	String  assignmentType="";	
	        	try{
	        		if(releaseSO.getReasonText().equals("Other (Enter Comments)")&& StringUtils.isBlank(releaseSO.getComment())){
	        			releaseSO.setComment(null);
	        		}
	        		assignmentType = soDetailsManager.getAssignmentType(soID);
	        	}catch(Exception e){
					logger.error("Problem retrieving assignmentType");
				}
	        	releaseSO.setSoId(soID);	
	        	releaseSO.setStatusId(stateId);
	        	releaseSO.setRole(roleId);
	        	releaseSO.setVendorId(vendorId);
				if(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM.equals(assignmentType) || (null != releaseSO.getReleaseFromFirmInd() && (releaseSO.getReleaseFromFirmInd().equals(1)))){
					releaseSO.setProviderRespId(SOConstants.PROVIDER_RESP_RELEASE_BY_FIRM);
				}else{
					releaseSO.setResourceId(resourceID);
					releaseSO.setProviderRespId(SOConstants.PROVIDER_RESP_RELEASE_BY_PROVIDER);
				}
				/**This method will check the existence of pending reschedule request for the service Order*/
				InHomeRescheduleVO rescheuleVo= null;
				boolean isServicedateUpdated=false;
				try {
					rescheuleVo= getServiceDateInfo(soID);
				} catch (BusinessServiceException e) {
					logger.error("Exception in getting service date informations for the service order"+ e);
				}
				processResponse = soDetailsManager.releaseServiceOrder(releaseSO);
				/*try {
					rescheuleVoAfter=getServiceDateInfo(soID);
				} catch (BusinessServiceException e) {
					logger.error("Exception in getting service date informations for the service order"+ e);
				}*/
				// expire all bids
				helper.markBidsFromProviderAsExpired(soID, null);
				
				if(!processResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC))
				{
					//Modified for SL-19820
					getSession().setAttribute("message"+"_"+soID, "Provider could not release the order");	        	
					
					return "releaseSoError";
				}
				else
				{	
					// set new state based on old:
					//sl-19820 setting into request
					if (stateId == OrderConstants.ACCEPTED_STATUS){
						//setCurrentSOStatusCodeInSession(OrderConstants.ROUTED_STATUS);
						setCurrentSOStatusCodeInRequest(OrderConstants.ROUTED_STATUS);
					} else if (stateId == OrderConstants.ACTIVE_STATUS){	
						//setCurrentSOStatusCodeInSession((Integer)processResponse.getObj());
						setCurrentSOStatusCodeInRequest((Integer)processResponse.getObj());
					}
					//no message for this one if successful
					getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soID, "");
					isServicedateUpdated = compareServiceDateInfo(rescheuleVo);
					if(isServicedateUpdated){
						rescheuleVo.setSoId(soID);
						rescheuleVo.setBuyerId(rescheuleVo.getBuyerId());
						try {
							insertNotificationForInHomeReschedule(rescheuleVo,rescheuleVo.getBuyerId());
						} catch (Exception e) {
							logger.error("Exception in Notifying NPS for Reschedule for inhome order"+e);
						}
					}
					//SL-21848
					if(null != soId && null != vendorId && null != resourceID && null != releaseSO.getReleaseFromFirmInd() && releaseSO.getReleaseFromFirmInd().equals(1) ){
						SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("soId", soId);
						param.put("vendorId", vendorId);
						param.put("userName", securityContext.getUsername());
						param.put("acceptSource", "Web");
						logger.info("Map param values to update the estimation to declined:"+param);
						mobileGenericBO.updateSOEstimateAsDeclined(param);
					}
					
					return SUCCESS;
				}
	        }
		}
		
				
		this.setReturnURL("/serviceOrderMonitor.action");
		this.setErrorMessage(result);
		return ERROR;
	}

	public void prepare() throws Exception {
		logger.info("Entering Prepare of ReleaseSOAction");
		createCommonServiceOrderCriteria();
		
	}
	
	public String getReleaseReasonCodes(){
		try{
			//SL-19820
			//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
			String soId = getParameter("soId");
			setAttribute("soId", soId);
			ServiceOrderDTO seviceOrder=null;
			
			seviceOrder=soDetailsManager.getServiceOrder(soId,OrderConstants.PROVIDER_ROLEID , null);
			if(null!=seviceOrder){
				setAttribute(THE_SERVICE_ORDER, seviceOrder);
			}
			
			String  assignmentType = soDetailsManager.getAssignmentType(soId);
			LuProviderRespReasonVO luReasonVO = new LuProviderRespReasonVO();
			ArrayList<LuProviderRespReasonVO> releaseReasons = new ArrayList();
			if(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM.equals(assignmentType)){
				luReasonVO.setSearchByResponse(SOConstants.PROVIDER_RESP_RELEASE_BY_FIRM);
			}else{
				luReasonVO.setSearchByResponse(SOConstants.PROVIDER_RESP_RELEASE_BY_PROVIDER);
			}
			releaseReasons = soDetailsManager.getReleaseReasonCodes(luReasonVO);
			setAttribute("releaseReasonCodes", releaseReasons);
			getSession().setAttribute("releaseResonCodeMap", releaseReasons);
		}catch(Exception e){
			logger.info("error in ReleaseSOAction->getReleaseReasonCodes()"+e);
		}
		return SUCCESS;
	}
	/**@Description:This method will be called before and after calling signal for releasing service order
	 * to check service time window is updated,if yes notify NPS incase of inhome orders
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public InHomeRescheduleVO getServiceDateInfo(String soId) throws BusinessServiceException{
		InHomeRescheduleVO serviceDateInfo = new InHomeRescheduleVO();
		serviceDateInfo.setSoId(soId);
		try{
			serviceDateInfo = notificationService.getSoDetailsForReschedule(serviceDateInfo);
		}catch(Exception e){
			logger.error("Exception in getting service date informations for the service order"+ e);
			throw new BusinessServiceException(e.getMessage());
		}
		return serviceDateInfo;
	}
	/**@Description:Validate an existing reschedule request placed by buyer is exists or not
	 * @param rescheuleVo
	 * @return
	 */
    private boolean compareServiceDateInfo(InHomeRescheduleVO rescheuleVo) {
		boolean isUpdated=false;
		if(null!= rescheuleVo && null != rescheuleVo.getBuyerRescheduleServiceDate1()){
			isUpdated=true;
			rescheuleVo.setReleasIndicator(1);
		}
		return isUpdated;
	}
	/**@Description:This will insert the details into outboundnotification table for inhome
	 * @param rescheuleVoAfter 
	 * @throws Exception 
	 * 
	 */
	private void insertNotificationForInHomeReschedule(InHomeRescheduleVO rescheuleVo,Integer buyerId) throws Exception{
		boolean isEligibleForNPSNotification=false;
		isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,rescheuleVo.getSoId());
		if(isEligibleForNPSNotification){
			notificationService.insertNotification(rescheuleVo);
			//R12.0 SL-20408: invoke Service Operations API on provider release after buyer reschedule
			rescheuleVo.setReleaseFlag(true);
			//SLT-4048:Flag to decide whether to send  Reschedule event to NPS through Platform
			if(BuyerOutBoundConstants.ON.equalsIgnoreCase(BuyerOutBoundConstants.INHOME_OUTBOUND_STOP_RESCHEDULE_EVENT_FLAG_INPLATFORM)){
			notificationService.insertNotificationServiceOperationsAPI(rescheuleVo.getSoId(),rescheuleVo);
			}
		}
	}
	public Object getModel() {
		return release;
	}
	
	public void setModel(Object x){
		release = (ReleaseServiceOrderDTO) x;	
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	
	

	
	
}