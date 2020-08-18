/**
 * 
 */
package com.newco.marketplace.web.action.widgets.condoffer;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * @author schavda
 *
 */
public class ServiceOrderCondOfferAction  extends SLBaseAction implements Preparable  {
	 private static final Logger logger = Logger
     .getLogger(ServiceOrderCondOfferAction.class);

	private static final long serialVersionUID = 1L;
	private ISOMonitorDelegate somDelegate = null;
	private String selectedSO = null;
	private String resId = null;
	
	public ServiceOrderCondOfferAction(SOMonitorDelegateImpl somDelegate) {
	 this.somDelegate = somDelegate;
	}
	
	public String execute() throws Exception
	{
	ServiceOrdersCriteria context = get_commonCriteria();
	String strMessage = "";
	ProcessResponse processResponse = null;
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache");
	String soId = getSelectedSO();
	String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID);
	Integer resourceId = Integer.parseInt(getResId());
	
	Integer providerRespId = OrderConstants.CONDITIONAL_OFFER;
	if (context != null &&
		  context.getSecurityContext() != null && 
		  context.getSecurityContext().getRoleId() == OrderConstants.PROVIDER_ROLEID)
		  {
			if(SLStringUtils.isNullOrEmpty(groupId))
			{
				processResponse = somDelegate.withdrawCondOffer(soId, resourceId, providerRespId);
			}
			else
			{
				processResponse = somDelegate.withdrawGroupCondOffer(groupId, resourceId, providerRespId);
			}
			
		  }
		  else
		  {
			processResponse = new ProcessResponse();
			processResponse.setCode(ServiceConstants.USER_ERROR_RC);  
			processResponse.setMessage(OrderConstants.BUYER_OPERATION_NOT_PERMITTED);
		  }
		
	   AjaxResultsDTO actionResults = new AjaxResultsDTO();
	   if(processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC)){
	   	actionResults.setActionState(0);
	   	actionResults.setResultMessage(processResponse.getMessages().get(0));
	   	logger.info(processResponse.getMessages().get(0));
	   }
	   else{
	   	actionResults.setActionState(1);
	   	strMessage = "Service Order Id: " + soId + " - Counter Offer was successfully withdrawn";
	   	actionResults.setResultMessage(strMessage);
	   }
	   response.getWriter().write(actionResults.toXml());
	   return NONE;
	}
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
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
	
}
