package com.newco.marketplace.web.action.widgets.sovoid;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
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
 * $Revision: 1.13 $ $Author: akashya $ $Date: 2008/05/21 23:33:07 $
 */
public class ServiceOrderVoidAction extends SLBaseAction implements Preparable, ServiceConstants, OrderConstants, ModelDriven<SOCancelDTO> {

	private static final long serialVersionUID = -2787135447668972269L;


	private static final Logger logger = Logger
            .getLogger(ServiceOrderVoidAction.class);


    private ISOMonitorDelegate soMonitorDelegate = null;
	private IFinanceManagerDelegate financeManagerDelegate;
    
    private String selectedSO = "";
    private SOCancelDTO soCancelDto = new SOCancelDTO();

    public ServiceOrderVoidAction(SOMonitorDelegateImpl somDelegate) {
        this.soMonitorDelegate = somDelegate;
    }

    public String execute() throws Exception
    {
      ServiceOrdersCriteria context = get_commonCriteria();
      String strMessage = "";
      HttpServletResponse response = ServletActionContext.getResponse();
	  response.setContentType("text/xml");
	  response.setHeader("Cache-Control", "no-cache");
	  String strErrCd = "";
	  ProcessResponse prResp = null;
	  
	  if (null != context && (OrderConstants.BUYER_ROLEID == context.getRoleId() || OrderConstants.SIMPLE_BUYER_ROLEID == context.getRoleId()))
	  	  {
		  	soCancelDto.setBuyerId(context.getCompanyId());
		  	prResp = soMonitorDelegate.serviceOrderVoid(soCancelDto);
		  	strErrCd = prResp.getCode();
		  	strMessage = prResp.getMessages().get(0);
		  			  			  	
	  	  }
		  else
		  {
			strMessage = OrderConstants.PROVIDER_OPERATION_NOT_PERMITTED;
			strErrCd = SYSTEM_ERROR_RC;
			logger.debug(OrderConstants.PROVIDER_OPERATION_NOT_PERMITTED);
		  }
		  AjaxResultsDTO actionResults = new AjaxResultsDTO();
		  
	      if(strErrCd.equalsIgnoreCase(SYSTEM_ERROR_RC)){
	      	actionResults.setActionState(0);
	      }
	      // Success, No Error
	      else
	      {
	      	actionResults.setActionState(1);
	      	
	      	//financeManagerDelegate.sendBuyerCancellationEmail(context.getCompanyId(), soId);
	      }
	      actionResults.setResultMessage(strMessage);
	      response.getWriter().write(actionResults.toXml());
	      return NONE;
    }

    public void prepare() throws Exception {
    	createCommonServiceOrderCriteria();
    }

    public String voidSO(SOCancelDTO soCancelDto) throws Exception {
        soMonitorDelegate.serviceOrderVoid(soCancelDto);
        return null;
    }

	public String getSelectedSO() {
		return selectedSO;
	}

	public void setSelectedSO(String selectedSO) {
		this.selectedSO = selectedSO;
	}

	public void setModel(Object x){
		soCancelDto = (SOCancelDTO) x;
	}


	public SOCancelDTO getModel() {
		return soCancelDto;
	}

	public IFinanceManagerDelegate getFinanceManagerDelegate()
	{
		return financeManagerDelegate;
	}

	public void setFinanceManagerDelegate(
			IFinanceManagerDelegate financeManagerDelegate)
	{
		this.financeManagerDelegate = financeManagerDelegate;
	}

}
/*
 * Maintenance History
 * $Log: ServiceOrderVoidAction.java,v $
 * Revision 1.13  2008/05/21 23:33:07  akashya
 * I21 Merged
 *
 * Revision 1.12.6.1  2008/05/17 20:34:49  gjacks8
 * added simple buyer role
 *
 * Revision 1.12  2008/04/26 01:13:52  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.6.1  2008/04/01 22:04:17  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.10  2008/03/27 18:57:58  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.9.10.1  2008/03/24 17:54:55  dmill03
 * cp
 *
 * Revision 1.9  2008/02/26 18:18:11  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.8.18.1  2008/02/25 19:50:39  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */