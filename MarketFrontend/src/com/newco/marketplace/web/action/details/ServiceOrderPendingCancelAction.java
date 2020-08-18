package com.newco.marketplace.web.action.details;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.serviceorder.PendingCancelHistoryVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOPendingCancelDTO;

import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.service.ManageScopeService;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;


public class ServiceOrderPendingCancelAction extends SLDetailsBaseAction implements Preparable, ServiceConstants, OrderConstants, ModelDriven<SOPendingCancelDTO> {

    private static final Logger logger = Logger.getLogger(ServiceOrderPendingCancelAction.class.getName());
    private static final long serialVersionUID = 1L;
    static private String SOD_ACTION = "/soDetailsController.action";
    private ISODetailsDelegate detailsDelegate;
    private IFinanceManagerDelegate financeManagerDelegate;
	private ManageScopeService manageScopeService;

    private SOPendingCancelDTO soPendingCancelDto = new SOPendingCancelDTO();
    
    //SL-19820
    String soId;
  
    public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public ServiceOrderPendingCancelAction(ISODetailsDelegate detailsDelegate) {
        this.detailsDelegate = detailsDelegate;
    }
    
    public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}
	

	public String pendingCancelSO() throws Exception {
		
		String strResponse = "";
		String strErrorMessage = "";
		String strErrorCode = "";
		String strSoId = "";
		int intStatusCd = 0;
		int intBuyerId = 0;
		String strLoggedInUser = "";
		String adminUserName="";
		String adminResourecId="";
		String userName="";
		String userId="";
		ProcessResponse prResp = null;
		
		try{
			ServiceOrdersCriteria context = get_commonCriteria();
			//SL-19820
			//strSoId = getSession().getAttribute(OrderConstants.SO_ID).toString();
			strSoId = getParameter("soId");
			setAttribute(SO_ID, strSoId);
			this.soId = strSoId;
			soPendingCancelDto.setSoId(strSoId);
			intBuyerId = context.getCompanyId();
			
			if(context.getSecurityContext().isAdopted() )
			{
			adminUserName=	context.getFName() + " " + context.getLName() ;
			adminResourecId=context.getSecurityContext().getAdminResId().toString();
			userName=getDetailsDelegate().getUserName(context.getRoleId(), context.getSecurityContext().getVendBuyerResId());
			}
			else
			{
			userName = 	context.getFName() + " " + context.getLName() ;
			
			}
			//Setting UserId 
			if(context.getSecurityContext().getVendBuyerResId()!=null)
			{
			userId=context.getSecurityContext().getVendBuyerResId().toString();
			}
			strLoggedInUser = context.getFName() + " " + context.getLName() ;
		
			soPendingCancelDto.setRoleId(context.getRoleId());
			soPendingCancelDto.setAdminResourecId(adminResourecId);
			soPendingCancelDto.setAdminUserName(adminUserName);
			soPendingCancelDto.setUserId(userId);
			soPendingCancelDto.setUserName(userName);
			
			

			prResp = getDetailsDelegate().serviceOrderPendingCancel(soPendingCancelDto);
			strErrorCode = prResp.getCode();
			strErrorMessage = prResp.getMessages().get(0);
			if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
			{	
				//Go to common error page in case of business logic failure error or fatal error
				strResponse = ERROR; 
				//SL-19820
				//this.setReturnURL(SOD_ACTION);
				this.setReturnURL(SOD_ACTION+"?soId="+this.soId);
				this.setErrorMessage(strErrorMessage);
			}else 
			{
			
				strResponse = SUCCESS;
				if (prResp.isError()) {
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "true");
					getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE+"_"+this.soId, "true");
					setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "true");
				}
				else{
					//SL-19820
					//setCurrentSOStatusCodeInSession(OrderConstants.CANCELLED_STATUS);
					setCurrentSOStatusCodeInRequest(OrderConstants.CANCELLED_STATUS);
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "false");
					getSession().setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE+"_"+this.soId, "false");
					setAttribute(Constants.SESSION.CANCELLATION_REQUEST_FAILURE, "false");
					
				}
					
				
			}
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId, strErrorMessage);
			setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			logger.debug("strResponse : " + strResponse);
			//Redisplay the tabs
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		logger.debug("----End of ServiceOrderCancelAction.cancelSO----");
		
		return strResponse;
	}
	
    
	
	public String somPendingCancelSO() throws Exception {
		ServiceOrdersCriteria context = get_commonCriteria();
		String strMessage = "";

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		String strSoId = "";
		String adminUserName = "";
		String adminResourecId = "";
		String userName = "";
		String userId = "";
		ProcessResponse prResp = null;
		String strErrCd = "";
		


		if (context.getSecurityContext().isAdopted()) {
			adminUserName = context.getFName() + " " + context.getLName();
			adminResourecId = context.getSecurityContext().getAdminResId()
					.toString();
			userName = getDetailsDelegate().getUserName(context.getRoleId(),
					context.getSecurityContext().getVendBuyerResId());
		} else {
			userName = context.getFName() + " " + context.getLName();	
			
		}
		//Setting UserId 
		if (context.getSecurityContext().getVendBuyerResId() != null) {
			userId = context.getSecurityContext().getVendBuyerResId()
					.toString();
		}
		soPendingCancelDto.setRoleId(context.getRoleId());
		soPendingCancelDto.setAdminResourecId(adminResourecId);
		soPendingCancelDto.setAdminUserName(adminUserName);
		soPendingCancelDto.setUserId(userId);
		soPendingCancelDto.setUserName(userName);

		prResp = getDetailsDelegate().serviceOrderPendingCancel(
				soPendingCancelDto);
		
		strErrCd = prResp.getCode();
		strMessage = prResp.getMessages().get(0);

		AjaxResultsDTO actionResults = new AjaxResultsDTO();

		if (strErrCd.equalsIgnoreCase(SYSTEM_ERROR_RC)) {
			actionResults.setActionState(0);
		}
		// Success, No Error
		else {
			actionResults.setActionState(1);

		}
		actionResults.setResultMessage(strMessage);
		response.getWriter().write(actionResults.toXml());
		return NONE;
	}
	
	public String getPendingCancelInfo() throws Exception {
	
		String strMessage = "";

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String soId = (String) getRequest().getParameter("servicOrderId");

		PendingCancelPriceVO buyerDetails = getDetailsDelegate()
				.getPendingCancelBuyerDetails(soId);
		PendingCancelPriceVO providerDetails = getDetailsDelegate()
				.getPendingCancelProviderDetails(soId);
		ServiceOrdersCriteria context = get_commonCriteria();
		PendingCancelPriceVO providerPrevPriceDetails = null;
		Double balance=null;
		double buyerPvsAmount=0.0d;
		boolean isSoLevelAutoACH=false;
		try
		{
			if(context.getRoleId()!=null && context.getRoleId().intValue()==3)
			{	
				// to fetch buyer previous amount.
				providerPrevPriceDetails = getDetailsDelegate().getPendingCancelBuyerPriceDetails(soId);
				if( null != providerPrevPriceDetails && null!= providerPrevPriceDetails.getPrice()) {
					 buyerPvsAmount = providerPrevPriceDetails.getPrice().doubleValue();
				}
				// get funding type id from so_hdr
				    Integer soFundingTypeId=0;
		            try{
		            	soFundingTypeId=manageScopeService.getSoLevelFundingTypeId(soId);}
		            catch(Exception e){}
		           if(soFundingTypeId.intValue()==40){
		            	isSoLevelAutoACH=true;
		            }
				if (!isSoLevelAutoACH) {
					Integer buyerId = context.getSecurityContext().getCompanyId();
					BigDecimal availableBalance = manageScopeService.getAvailableBalance(buyerId);
					if(availableBalance!=null)
					{
						balance=availableBalance.doubleValue();
					}
				}
			
		}
		}
		catch(Exception e)
		{
			
		}
		DateFormat providerDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		NumberFormat formatter = new DecimalFormat("###0.00");

		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		actionResults.setActionState(1);
		if(null!=buyerDetails)
		{
			if(null!=buyerDetails
					.getPrice())
			{
		actionResults.setAddtionalInfo1(formatter.format(buyerDetails
				.getPrice()));
			}
			if(null!=buyerDetails.getComments())
			{
				String buyerCom=buyerDetails.getComments();
				buyerCom=buyerCom.replaceAll("&","&amp;");
				buyerCom=buyerCom.replaceAll("<","&lt;");
				buyerCom=buyerCom.replaceAll(">","&gt;");
				buyerCom=buyerCom.replaceAll("\"/","&quot;"); 
				buyerCom=buyerCom.replaceAll("'","&apos;");
 
	    	actionResults.setAddtionalInfo2(buyerCom);
			}
			if(null!=buyerDetails
				.getEntryDate())
			{
		actionResults.setAddtionalInfo3(providerDateFormat.format(buyerDetails
				.getEntryDate()));
			}
		}
		if(null!=providerDetails)
		{
			if(null!=providerDetails
				.getPrice())
			{
		actionResults.setAddtionalInfo4(formatter.format(providerDetails
				.getPrice()));
			}
			if(null!=providerDetails.getComments())
			{
				String proCom=providerDetails.getComments();
				proCom=proCom.replaceAll("&","&amp;");
				proCom=proCom.replaceAll("<","&lt;");
				proCom=proCom.replaceAll(">","&gt;");
				proCom=proCom.replaceAll("\"/","&quot;"); 
				proCom=proCom.replaceAll("'","&apos;");
				
		actionResults.setAddtionalInfo5(proCom);
			}
			if(null!=providerDetails.getEntryDate())
			{
		actionResults.setAddtionalInfo6(providerDateFormat
				.format(providerDetails.getEntryDate()));
			}
		}
		if(null!=balance)
		{
			actionResults.setAddtionalInfo7(formatter.format(balance));	
		}
		actionResults.setAddtionalInfo8(formatter.format(buyerPvsAmount));
		if(isSoLevelAutoACH){
		    actionResults.setAddtionalInfo9("true");
		}else{
			actionResults.setAddtionalInfo9("false");	
		}
		
		actionResults.setResultMessage(strMessage);
		
		response.getWriter().write(actionResults.toXml());

		return NONE;
	}
	
	public String display() throws Exception 
	{
		String soId = (String) getRequest().getParameter("servicOrderId");
		List<PendingCancelHistoryVO> pendingCancelHistory=getDetailsDelegate().getPendingCancelHistory(soId);
		List<PendingCancelHistoryVO> cancelHistory=new ArrayList<PendingCancelHistoryVO>();
//        int size= 0;
//        if(pendingCancelHistory!=null)
//        {
//        	size=pendingCancelHistory.size()-1;
//        }
        
		double buyerPrice=0.00;
		double providerPrice=0.00;
		//int index=size;
		for (PendingCancelHistoryVO history : pendingCancelHistory) {
			
			if (history.getRoleId() != null
			&& history.getWithdrawFlag() != null
					&& history.getWithdrawFlag().booleanValue() == false && history.getPrice()!=null) {
				
				
				if(history.getRoleId().intValue() == 3)
				{
				buyerPrice = history.getPrice();
				}
				if(history.getRoleId().intValue() == 1)
				{
				providerPrice = history.getPrice();
				}
			}
			
			if (history.getRoleId() != null
					
					&& history.getWithdrawFlag() != null
					&& history.getWithdrawFlag().booleanValue() == true ) {
				NumberFormat formatter = new DecimalFormat("###0.00");

				
				if(history.getRoleId().intValue() == 3)
				{
				history.setComments("Price Withdrawn $"+formatter.format(buyerPrice));
				}
				if(history.getRoleId().intValue() == 1)
				{
				history.setComments("Price Withdrawn $"+formatter.format(providerPrice));

				}
			}
			cancelHistory.add(0, history);
			//index=index-1;
		}
		
		//Sl-19820
		//getSession().setAttribute("pendingCancelHistory", cancelHistory);
		setAttribute("pendingCancelHistory", cancelHistory);
		
		return SUCCESS;
		
	}
	
	

    public String execute() throws Exception 
    {
    	return SUCCESS;        
    }
    

    public void prepare() throws Exception {
    	createCommonServiceOrderCriteria();
    }
    
	public void setModel(SOPendingCancelDTO x){
		soPendingCancelDto = x;
	}


	public SOPendingCancelDTO getModel() {
		return soPendingCancelDto;
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

	public ManageScopeService getManageScopeService() {
		return manageScopeService;
	}

	public void setManageScopeService(ManageScopeService manageScopeService) {
		this.manageScopeService = manageScopeService;
	}
	
	
	
}
