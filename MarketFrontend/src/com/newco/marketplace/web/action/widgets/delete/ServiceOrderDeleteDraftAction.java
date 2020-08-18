/**
 * 
 */
package com.newco.marketplace.web.action.widgets.delete;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * @author rambewa
 *
 */
public class ServiceOrderDeleteDraftAction extends SLBaseAction implements Preparable,  ModelDriven<SOCancelDTO> {

	private static final Logger logger = Logger.getLogger(ServiceOrderDeleteDraftAction.class);
	private static final long serialVersionUID = 9L;
	private ISOMonitorDelegate soMonitorDelegate = null;
	private String selectedSO = null;
	private SOCancelDTO soCancelDto  = new SOCancelDTO();
	
	
	public ServiceOrderDeleteDraftAction(SOMonitorDelegateImpl somDelegate){
		 this.soMonitorDelegate = somDelegate;
		}

	public String execute() throws Exception
	{
	
	ServiceOrdersCriteria context = get_commonCriteria();
	ProcessResponse processResponse = null;
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache");
	if (null != context &&
			null != context.getSecurityContext() && 
		  (OrderConstants.BUYER_ROLEID == context.getSecurityContext().getRoleId()
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
		
	   AjaxResultsDTO actionResults = new AjaxResultsDTO();
	   if(ServiceConstants.USER_ERROR_RC.equals(processResponse.getCode())){
		   	actionResults.setActionState(0);
	   }
	   else{
	   		actionResults.setActionState(1);
	   }
	   if(null!=processResponse.getMessages()){
		   actionResults.setResultMessage(processResponse.getMessages().get(0));
	   }
	   response.getWriter().write(actionResults.toXml());
	   return NONE;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
	}

	public String getSelectedSO() {
		return selectedSO;
	}

	public void setSelectedSO(String selectedSO) {
		this.selectedSO = selectedSO;
	}

	public SOCancelDTO getSoCancelDto() {
		return soCancelDto;
	}

	public void setSoCancelDto(SOCancelDTO soCancelDto) {
		this.soCancelDto = soCancelDto;
	}

	public SOCancelDTO getModel() {
		// TODO Auto-generated method stub
		return soCancelDto;
	}

	
	



}
