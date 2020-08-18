package com.newco.marketplace.web.action.details;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;


public class SODetailsDeleteAction extends SLDetailsBaseAction implements Preparable{

    private static final Logger logger = Logger
            .getLogger(SODetailsDeleteAction.class);

    private static final long serialVersionUID = 1L;
    static private String SOM_ACTION = "/serviceOrderMonitor.action";
    private ISOMonitorDelegate soMonitorDelegate;
    private String reason = null;
	private String comment = null;
	private Integer reasonCode = null;
	public ISOMonitorDelegate getSoMonitorDelegate() {
		return soMonitorDelegate;
	}

	public void setSoMonitorDelegate(ISOMonitorDelegate soMonitorDelegate) {
		this.soMonitorDelegate = soMonitorDelegate;
	}

	public SODetailsDeleteAction(ISOMonitorDelegate soMonitorDelegate) {
		this.soMonitorDelegate = soMonitorDelegate;
	}

    public String execute() throws Exception
    {
      logger.debug("----Start of SODetailsVoidAction.execute----");	
      ServiceOrdersCriteria context = get_commonCriteria();
      String strErrorCode = "";
	  String strErrorMessage = "";
	  String strResponse = "";
	  String soId = "";
	  ProcessResponse processResponse = null;
	  int intBuyerId = 0;
		String strMessage = "";
		String strLoggedInUser = "";
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		SOCancelDTO soCancelDto = new SOCancelDTO();
		soCancelDto.setReason(getReason());
		soCancelDto.setReasonCode(getReasonCode());
		soCancelDto.setCancelComment(getComment());
		intBuyerId = context.getCompanyId();
		soCancelDto.setBuyerId(intBuyerId);
		strLoggedInUser = context.getFName() + " " + context.getLName() ;
		logger.debug("strLoggedInUser in action: " + strLoggedInUser);
		soCancelDto.setBuyerName(strLoggedInUser);
	  try{
		  soId = getSession().getAttribute(OrderConstants.SO_ID).toString();
		  soCancelDto.setSoId(soId);
	  	  if (null != context &&
	  			null != context.getSecurityContext() && 
			  (OrderConstants.BUYER_ROLEID  == context.getSecurityContext().getRoleId()
					  || OrderConstants.SIMPLE_BUYER_ROLEID == context.getSecurityContext().getRoleId()))
			  {
				processResponse = soMonitorDelegate.deleteDraft(soCancelDto);
			  }
			  else
			  {
				processResponse = new ProcessResponse();
				processResponse.setCode(ServiceConstants.USER_ERROR_RC);  
				processResponse.setMessage(OrderConstants.PROVIDER_IS_NOT_AUTHORIZED);
			  }
	  	  strErrorCode = processResponse.getCode();
	  	  strErrorMessage = processResponse.getMessages().get(0);
	  	  if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
	  	  {	
	  		  //Go to common error page in case of business logic failure error or fatal error
	  		  strResponse = ERROR; 
	  		  this.setReturnURL(SOM_ACTION);
	  		  this.setErrorMessage(strErrorMessage);
	  	  }else 
	  	  {
	  		  //if success return to SOD
	  		  strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
	  		  setCurrentSOStatusCodeInSession(OrderConstants.DELETED_STATUS);
	  	  }
	
	  	  getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
	  	  logger.debug("strResponse : " + strResponse);
	} catch (BusinessServiceException e) {
		logger.info("Exception in cancelling the SO: ", e);
	}
    logger.debug("----End of SODetailsVoidAction.execute----");
	return strResponse;
    }

    public void prepare() throws Exception {
    	createCommonServiceOrderCriteria();
    }

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

}
