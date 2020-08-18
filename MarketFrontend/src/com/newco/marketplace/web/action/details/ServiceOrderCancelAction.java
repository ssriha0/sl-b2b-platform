package com.newco.marketplace.web.action.details;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 01:13:47 $
 */

/*
 * Maintenance History
 * $Log: ServiceOrderCancelAction.java,v $
 * Revision 1.8  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.5.28.1  2008/04/01 22:03:59  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.6  2008/03/27 18:57:50  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.5.32.1  2008/03/25 18:29:53  mhaye05
 * code cleanup
 *
 * Revision 1.5  2007/12/13 23:53:24  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.4  2007/11/14 21:58:51  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class ServiceOrderCancelAction extends SLDetailsBaseAction implements Preparable, ServiceConstants, OrderConstants, ModelDriven<SOCancelDTO> {

    private static final Logger logger = Logger.getLogger(ServiceOrderCancelAction.class.getName());
    private static final long serialVersionUID = 1L;
    static private String SOD_ACTION = "/soDetailsController.action";
    private ISODetailsDelegate detailsDelegate;
    private IFinanceManagerDelegate financeManagerDelegate;
    
    private SOCancelDTO soCancelDto = new SOCancelDTO();
    //SL-19820
    String soID;
  
    public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public ServiceOrderCancelAction(ISODetailsDelegate detailsDelegate) {
        this.detailsDelegate = detailsDelegate;
    }
    
    public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}

	public String cancelSO() throws Exception {
		logger.debug("----Start of ServiceOrderCancelAction.cancelSO----");
		
		String strResponse = "";
		String strErrorMessage = "";
		String strErrorCode = "";
		String strSoId = "";
		int intStatusCd = 0;
		int intBuyerId = 0;
		String strLoggedInUser = "";
		ProcessResponse prResp = null;
		
		try{
			ServiceOrdersCriteria context = get_commonCriteria();
			//Sl-19820
			//strSoId = getSession().getAttribute(OrderConstants.SO_ID).toString();
			//intStatusCd = Integer.parseInt(getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE).toString());
			//soCancelDto.setSoId(strSoId);
			intBuyerId = context.getCompanyId();
			strSoId = soCancelDto.getSoId();
			soCancelDto.setBuyerId(intBuyerId);
			soCancelDto.setStatusCd(soCancelDto.getWfStateId());
			strLoggedInUser = context.getFName() + " " + context.getLName() ;
			logger.debug("strLoggedInUser in action: " + strLoggedInUser);
			soCancelDto.setBuyerName(strLoggedInUser);
			HashMap<Integer, ManageTaskVO> scopeChangeTasks= null;
			//SL-19820
			/*if(null!=getSession().getAttribute("scopeChangeTasks")){
				scopeChangeTasks = (HashMap<Integer, ManageTaskVO>) getSession()
				.getAttribute("scopeChangeTasks");
			}*/
			if(null != getSession().getAttribute("scopeChangeTasks_"+strSoId)){
				scopeChangeTasks = (HashMap<Integer, ManageTaskVO>) getSession()
				.getAttribute("scopeChangeTasks_"+strSoId);
			}
			if(soCancelDto.isProviderPaymentCheck()){
				soCancelDto.setScopeChangeTasks(scopeChangeTasks);
			}
			
			// for pending cancel state
			
			String adminUserName ="";
			String adminResourecId="";
			String userName ="";
			String	userId ="";
			
			if (context.getSecurityContext().isAdopted()) {
			adminUserName = context.getFName() + " " + context.getLName();
				adminResourecId = context.getSecurityContext().getAdminResId()
						.toString();
				userName = getDetailsDelegate().getUserName(context.getRoleId(),
						context.getSecurityContext().getVendBuyerResId());
				soCancelDto.setAdminUserName(adminUserName);
				soCancelDto.setAdminResourecId(adminResourecId);
				soCancelDto.setUserName(userName);	
			} else {
			userName = context.getFName() + " " + context.getLName();				
				soCancelDto.setUserName(userName);
			}
			//setting UserId
			if (context.getSecurityContext().getVendBuyerResId() != null) {
			userId = context.getSecurityContext().getVendBuyerResId()
						.toString();
				soCancelDto.setUserId(userId);
			}	
					
			
			prResp = getDetailsDelegate().serviceOrderCancel(soCancelDto);
			strErrorCode = prResp.getCode();
			strErrorMessage = prResp.getMessages().get(0);
			if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
			{	
				//Go to common error page in case of business logic failure error or fatal error
				strResponse = ERROR; 
				//SL-19820
				//this.setReturnURL(SOD_ACTION);
				this.setReturnURL(SOD_ACTION+"?soId="+strSoId);
				this.setErrorMessage(strErrorMessage);
			}else 
			{
				//if success close pop-up and return to SOD
				strResponse = SUCCESS;
				if (prResp.isError()) {
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "true");
					getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE+"_"+strSoId, "true");
					setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "true");
				}
				else{
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "false");
					getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE+"_"+strSoId, "false");
					setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "false");
					if(strErrorMessage.contains("Pending")){
						//SL-19820
						//setCurrentSOStatusCodeInSession(OrderConstants.PENDING_CANCEL_STATUS);
						setCurrentSOStatusCodeInRequest(OrderConstants.PENDING_CANCEL_STATUS);
					}else{
						//SL-19820
						//setCurrentSOStatusCodeInSession(OrderConstants.CANCELLED_STATUS);
						setCurrentSOStatusCodeInRequest(OrderConstants.CANCELLED_STATUS);
					}
				}
					
				
			}
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+strSoId, strErrorMessage);
			setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			soID = strSoId;
			logger.debug("strResponse : " + strResponse);
			//Redisplay the tabs
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		logger.debug("----End of ServiceOrderCancelAction.cancelSO----");
		
		return strResponse;
	}
	


    public String execute() throws Exception 
    {
    	return SUCCESS;        
    }
    

    public void prepare() throws Exception {
    	createCommonServiceOrderCriteria();
    }
    
	public void setModel(SOCancelDTO x){
		soCancelDto = x;
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
