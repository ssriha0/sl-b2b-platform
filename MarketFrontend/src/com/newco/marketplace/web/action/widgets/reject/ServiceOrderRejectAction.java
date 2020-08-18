package com.newco.marketplace.web.action.widgets.reject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.ConditionalOfferDTO;
import com.newco.marketplace.web.dto.RejectServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.activitylog.client.IActivityLogHelper;


public class ServiceOrderRejectAction extends SLDetailsBaseAction implements Preparable {

    private static final Logger logger = Logger
            .getLogger("ServiceOrderRejectAction");
    private static final long serialVersionUID = 100L;
 
    private String soId = "";
    private String groupId;
    private String modifiedBy = "";
    private String requestFrom = "";
    private String reasonText = "";
    
    // The SOM code 
    private String resId;
    private String selectedSO = "";
    private Integer reasonId = 0;
    
    //SL-19820
    String message;
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    private IActivityLogHelper helper;

	public void setHelper(IActivityLogHelper helper)
	{
		this.helper = helper;
	}
    
    public ServiceOrderRejectAction(SOMonitorDelegateImpl somDelegate) {
        this.soMonitorDelegate = somDelegate;
    }

   
    public void prepare() throws Exception {
    	//Need this when accessing from SOM
    	createCommonServiceOrderCriteria();
    	getRejectReasons();
    	logger.debug("resId in prepare reject SO in prepare: " + getRequest().getParameter("resId")); 
		if(StringUtils.isNotBlank( (String) getRequest().getParameter("resId"))){
			//SL-19820
			 //getSession().setAttribute("routedResourceId",getRequest().getParameter("resId"));
			setAttribute("routedResourceId",getRequest().getParameter("resId"));
		 }
		if(getRequest().getParameter("reasonText") != null){
			this.setReasonText(getRequest().getParameter("reasonText"));
		 }
		if(getRequest().getParameter("groupId") != null){
			//SL-19820
			String groupId = getRequest().getParameter("groupId");
			this.setGroupId(groupId);
			setAttribute(GROUP_ID, groupId);
		 }
	}
    
    // THE CLEAN VERSION FOR SOM ONLY. PLEASE DO NOT PUT
    // ANY SO DETAILS CODE IN THIS METHOD!!!!!!
    public String doWidgetServiceOrderReject() throws Exception {
    	logger.debug("----Start of doWidgetServiceOrderReject----");
    	ServiceOrdersCriteria context = get_commonCriteria();
    	List<Integer> checkedResourceID = null;    
    	HttpServletResponse response = ServletActionContext.getResponse();
    	 response.setContentType("text/xml");
         response.setHeader("Cache-Control", "no-cache");
 		String strMessage = "";
 		if (context != null && 
				context.getSecurityContext() != null && 
				context.getSecurityContext().getRoleId() == OrderConstants.PROVIDER_ROLEID)
			{
 			checkedResourceID = getCheckedResourceID();
 			if (checkedResourceID != null && checkedResourceID.size()!=0){
 				
 				RejectServiceOrderDTO rsoDTO = new RejectServiceOrderDTO();
 				rsoDTO.setModifiedBy(context.getTheUserName());
 				rsoDTO.setReasonDesc(this.getReasonText());
 				//rsoDTO.setReasonDesc( getReasonDesc(  getReasonId() != null ? getReasonId().intValue() : 0  ));
 				rsoDTO.setReasonId( getReasonId() != null ? getReasonId().intValue() : 0);
 				//rsoDTO.setResourceId(StringUtils.isNotBlank(getResId()) ? Integer.parseInt(getResId()) : 0);
 				//rsoDTO.setResourceId( context.getVendBuyerResId());
 				rsoDTO.setResponseId( OrderConstants.PROVIDER_RESP_REJECTED );
 				rsoDTO.setSoId(getSelectedSO());
 				rsoDTO.setGroupId(getGroupId());
 				strMessage = soMonitorDelegate.rejectServiceOrder( rsoDTO,checkedResourceID);
 			}
 				
			}
			else
			{
				strMessage = OrderConstants.BUYER_OPERATION_NOT_PERMITTED;
			}
 		logger.debug("strMessage for validations : " + strMessage);
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
        if(strMessage.equalsIgnoreCase("ERROR_OCCURED")){
        	actionResults.setActionState(0);
        	actionResults.setResultMessage(strMessage);
        }
        else{
        	actionResults.setActionState(1);
        	actionResults.setResultMessage(strMessage);
        }
		response.getWriter().write(actionResults.toXml());
		logger.debug("----End of doWidgetServiceOrderReject----");
		return NONE;
    }
    
    
    // TODO REFACTOR ASAP, SO DETAIL CODE SHOULD BE IN ITS
    // OWN ACTION NOT IN THE SOM ACTIONS. SERIOUS BADDNESS!
    public String execute() throws Exception {
     	logger.debug("----Start of ServiceOrderRejectAction.rejectServiceOrder----");
     	String strResponse = null;
     	Integer resourceID = null;
     	String modifiedBy = "";
 		String strMessage = "";
 		ProcessResponse pr = null;
		String reasonDesc  = "";
		List<Integer> checkedResourceID = null;

 		try
		{ 
 			
 			checkedResourceID = getCheckedResourceID();
			
			if (checkedResourceID != null && checkedResourceID.size()!=0)
			{				
				if (getRequestFrom().equalsIgnoreCase("SOM"))
				{
					soId = getSelectedSO();
					logger.debug("reasonDesc : " + this.getReasonText());
					reasonDesc = this.getReasonText();
				}
				//SL-19820
				/*else if(getSession().getAttribute(OrderConstants.SO_ID) != null)
				{
					soId = (String) getSession().getAttribute(OrderConstants.SO_ID);
					if (soId == null)
					{
						soId = (String) getSession().getAttribute("detailsSOId");
					}
					reasonDesc = this.getReasonText();//getReasonDesc(getReasonId());
				}*/
				else if(getParameter("soId") != null)
				{
					soId = getParameter("soId");
					reasonDesc = this.getReasonText();//getReasonDesc(getReasonId());
					
				}

				ServiceOrdersCriteria context = get_commonCriteria();
				if (context != null && context.getSecurityContext() != null
						&& context.getSecurityContext().getRoleId() == OrderConstants.PROVIDER_ROLEID)
				{
					resourceID = context.getSecurityContext().getVendBuyerResId();
					modifiedBy = context.getSecurityContext().getCompanyId() + "";
					groupId =  getGroupId();
					reasonDesc = this.getReasonText();//getReasonDesc(getReasonId());
					if(groupId==null || groupId=="" ){
						//SL-19820
						//groupId = (String)getSession().getAttribute(GROUP_ID);
						groupId = (String)getAttribute(GROUP_ID);
					}
					
					pr = soMonitorDelegate.rejectServiceOrder(checkedResourceID, soId, groupId, getReasonId(),
							SOConstants.PROVIDER_RESP_REJECTED, String.valueOf(resourceID), reasonDesc);
					
	 				if(!pr.isError()){
                        helper.markBidsFromProviderAsExpired(soId, resourceID.longValue());
                     }else{
                         strMessage = pr.getMessages().get(0);
                     }
				}
				else
				{
					strMessage = OrderConstants.BUYER_OPERATION_NOT_PERMITTED;
				}
				logger.debug("strMessage for validations : " + strMessage);

				if (getRequestFrom().equalsIgnoreCase("SOM"))
				{
					ArrayList arrMsgList = (ArrayList) pr.getMessages();
					StringBuffer sbMessages = new StringBuffer();
					for (int i = 0; i < arrMsgList.size(); i++)
					{
						sbMessages.append(arrMsgList.get(i) + "\n");
						logger.debug("reject validation messages :" + arrMsgList.get(i));
					}
					strMessage = sbMessages.toString();
					logger.debug("strMessage : " + strMessage);
					HttpServletResponse response = ServletActionContext.getResponse();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					StringBuffer sb = new StringBuffer();

					response.getWriter().write(sb.toString());
					logger.debug("strMessage came here: " + strMessage);
				}
				else
				{ /* in case of SOD */
					if (pr.getCode().equals("08"))
					{
						this.setReturnURL("/serviceOrderMonitor.action");
						if (null != pr.getMessages())
						{
							this.setErrorMessage(pr.getMessages().get(0));
						}
						else
						{
							this.setErrorMessage(OrderConstants.UNAPPROPRIATE_WFSTATE_REJECT);
						}
						return ERROR;
					}
					this.setSuccessMessage(OrderConstants.REJECT_SUCCESS);
					//SL-19820
					//getSession().setAttribute("msg", OrderConstants.REJECT_SUCCESS);
					this.message = OrderConstants.REJECT_SUCCESS;
					return "som";
				}
			}
			else
			{
				this.setErrorMessage(OrderConstants.UNAPPROPRIATE_WFSTATE_REJECT);
			}
			logger.debug("----End of ServiceOrderRejectAction.rejectServiceOrder----");
		}
		catch (BusinessServiceException e)
		{
			logger.debug("Exception in rejecting the service order: " + e.getMessage());
			if (getRequestFrom().equalsIgnoreCase("SOM"))
			{
				return strResponse;
			}
			else
			{
				this.setReturnURL("/serviceOrderMonitor.action");
				this.setErrorMessage(OrderConstants.REJECT_FAILURE);
				return ERROR;
			}
		}
		logger.debug("----End of ServiceOrderRejectAction.rejectServiceOrder----");
		return strResponse;
	}
   
    private List<Integer> getCheckedResourceID(){
    	
		List<Integer> resourceIDs = new ArrayList<Integer>();
		String resourceId = null;		
		resourceId = getParameter("resId");
		String[] items = resourceId.split(",");
		       for (String resourceids : items){
		    	   if((StringUtils.isNotBlank(resourceids)) &&(StringUtils.isNumeric(resourceids))){
		    		   int resid= Integer.parseInt(resourceids);
		    		   resourceIDs.add(resid);
		    	   }
		       }
		       return resourceIDs;
    }
    
    
    private String getReasonDesc(Integer reasonId)
    {
    	/* accepting reason id and returning back description. Using applicaiton level stored variable, no need to fatch from db */
    	String reasonDesc = "";
		ArrayList<LuProviderRespReasonVO>  al;
		LuProviderRespReasonVO luReasonVO;
		al = (ArrayList<LuProviderRespReasonVO>)getSession().getServletContext().getAttribute("rejectCodes"); 
		if (al != null) {
			for (int i=0; i<al.size(); i++){
				luReasonVO = al.get(i);
				if (luReasonVO.getRespReasonId() == reasonId){
					reasonDesc = luReasonVO.getDescr();
					break;
				}
			}
		}
		return reasonDesc;
    }


	public Integer getReasonId() {
		return reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRequestFrom() {
		return requestFrom;
	}

	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}

	public String getSelectedSO() {
		return selectedSO;
	}

	public void setSelectedSO(String selectedSO) {
		this.selectedSO = selectedSO;
	}


	public String getResId() {
		return resId;
	}


	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
