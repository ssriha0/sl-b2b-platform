package com.newco.marketplace.web.action.details;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.IncreaseSpendLimitDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * 
 * $Revision: 1.7 $ $Author: nsanzer $ $Date: 2008/06/03 17:36:57 $
 */

/*
 * Maintenance History
 * $Log: SODetailsIncreaseSpendLimitAction.java,v $
 * Revision 1.7  2008/06/03 17:36:57  nsanzer
 * Fix for the increase of spend limit on summary tab of SO details page for simple buyers.
 *
 * Revision 1.6  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.4.28.1  2008/04/23 11:41:34  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.5  2008/04/23 05:19:30  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.4  2007/12/13 23:53:21  mhaye05
 * replaced hard coded strings with constants
 *
 */
public class SODetailsIncreaseSpendLimitAction 
extends SLDetailsBaseAction implements Preparable, OrderConstants, ServiceConstants, ModelDriven<IncreaseSpendLimitDTO> {

	private static final long serialVersionUID = 10100;// arbitrary number to
	private static final Logger logger = Logger
            .getLogger("ServiceOrderIncreaseSpendLimitAction");
    private IncreaseSpendLimitDTO soIncSLDto = new IncreaseSpendLimitDTO();
    static private String SOD_ACTION = "/soDetailsController.action";
       
    private ISODetailsDelegate ISODetailDelegate;
    private IRelayServiceNotification relayNotificationService;

	public SODetailsIncreaseSpendLimitAction(ISODetailsDelegate delegate, IRelayServiceNotification relayNotificationService) {
    	this.detailsDelegate = delegate;
    	this.relayNotificationService = relayNotificationService;
    }
    //SL-19820
	String soID;

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public void prepare() throws Exception {
		
		//SL-19820
    	/*ServiceOrderDTO soDTO = new ServiceOrderDTO();
		soDTO = getCurrentServiceOrderFromSession();*/
		String soId = getParameter("selectedSO");
		setAttribute(SO_ID, soId);
		this.soID = soId;
    	
    	createCommonServiceOrderCriteria();
    	ServiceOrdersCriteria context = get_commonCriteria();
    	System.out.println("CompanyId : " + context.getSecurityContext().getCompanyId());
		
		soIncSLDto = (IncreaseSpendLimitDTO)getSession().getAttribute(Constants.SESSION.SOD_INC_SO_DTO+"_"+soId);

		ApplicationContext appContext = MPSpringLoaderPlugIn.getCtx();
		IFinanceManagerBO financeManagerBO = (IFinanceManagerBO) appContext.getBean("financeManagerBO"); 
		//Now get their account details
		Account acct = financeManagerBO.getAccountDetails(context.getSecurityContext().getCompanyId());

		if (soIncSLDto == null){
			soIncSLDto = new IncreaseSpendLimitDTO();
			soIncSLDto.setBuyerId(context.getSecurityContext().getCompanyId() + "");
			//SL-19820
			//soIncSLDto.setSelectedSO(soDTO.getId());
			soIncSLDto.setSelectedSO(soId);
			soIncSLDto.setGroupId(getRequest().getParameter("groupId"));
			soIncSLDto.setCurrentSpendLimit(getRequest().getParameter("currentSpendLimit"));
			soIncSLDto.setCurrentLimitLabor(getRequest().getParameter("currentLimitLabor"));
			soIncSLDto.setCurrentLimitParts(getRequest().getParameter("currentLimitParts"));
			soIncSLDto.setIncreaseLimit(getRequest().getParameter("increaseLimit"));
			soIncSLDto.setIncreaseLimitParts(getRequest().getParameter("increaseLimitParts"));
			soIncSLDto.setTotalSpendLimit(getRequest().getParameter("totalSpendLimit"));
			soIncSLDto.setTotalSpendLimitParts(getRequest().getParameter("totalSpendLimitParts"));
			soIncSLDto.setIncreasedSpendLimitComment(getRequest().getParameter("increasedSpendLimitNotes_detail"));
			soIncSLDto.setIncreasedSpendLimitReason(getRequest().getParameter("increasedSpendLimitReason_detail"));
			soIncSLDto.setIncreasedSpendLimitReasonId(getRequest().getParameter("increasedSpendLimitReasonId_detail"));
			soIncSLDto.setAutoACH(context.getSecurityContext().isAutoACH());
			soIncSLDto.setAccountID(acct.getAccount_id());
			
		}
		getRequest().setAttribute("soIncSLDto", soIncSLDto);
		//SL-19820
		//getSession().removeAttribute(Constants.SESSION.SOD_INC_SO_DTO);
		getSession().removeAttribute(Constants.SESSION.SOD_INC_SO_DTO+"_"+soId);
    }

	public String execute() throws Exception {
		 return SUCCESS;
	 }
	
	
    public void setModel(Object x){
    	soIncSLDto = (IncreaseSpendLimitDTO) x;
	}


	public IncreaseSpendLimitDTO getModel() {
		return soIncSLDto;
	}

	 
	public String increaseSpendLimit() throws Exception {
     	logger.info("----Start of ServiceOrderIncreaseSpendLimitAction.increaseSpendLimit----");
     	  //SL-21045 --changes --START
	      BigDecimal currentSpendLimitLabor = null; 
	  	  BigDecimal currentSpendLimitParts = null;
	  	  BigDecimal DBSpendLimitLabor = null;
	  	  BigDecimal DBSpendLimitParts = null;
	  	  InitialPriceDetailsVO initialPriceDetailsVO = null;
	  	  BigDecimal DBTotalSpendLimit = null,currentTotalSpendLimit = null;
	  	  String errorMessageForIncreasePrice = "";
	  	  int result = 0;
	  	  //SL-21045 --changes --END
		ProcessResponse pResp = null;
		String strResponse = "";
		String strErrorCode = "";
		String strErrorMessage = "";
		String defaultSelectedTab = "";
		IncreaseSpendLimitDTO soIncreaseSpendLimitdto = (IncreaseSpendLimitDTO)getModel();
		soIncreaseSpendLimitdto.setIncreasedSpendLimitReason(getRequest().getParameter("increasedSpendLimitReason_detail"));
		soIncreaseSpendLimitdto.setIncreasedSpendLimitComment(getRequest().getParameter("increasedSpendLimitNotes_detail"));
		soIncSLDto = (IncreaseSpendLimitDTO)getRequest().getAttribute("soIncSLDto");
		soIncSLDto.setGroupId(getRequest().getParameter("groupId"));
		soIncSLDto.setCurrentLimitLabor(getRequest().getParameter("currentLimitLabor"));
		soIncSLDto.setCurrentLimitParts(getRequest().getParameter("currentLimitParts"));
		soIncSLDto.setIncreasedSpendLimitReason(getRequest().getParameter("increasedSpendLimitReason_detail"));
		soIncSLDto.setIncreasedSpendLimitReasonId(getRequest().getParameter("increasedSpendLimitReasonId_detail"));
		soIncSLDto.setIncreasedSpendLimitComment(getRequest().getParameter("increasedSpendLimitNotes_detail"));
		createCommonServiceOrderCriteria();
		//SL-21045 --changes --START
		//setting the spend limit labor as the current labor price if there is no change in labor  
		if(StringUtils.isBlank(soIncreaseSpendLimitdto.getTotalSpendLimit())){
			soIncreaseSpendLimitdto.setTotalSpendLimit(getRequest().getParameter("currentLimitLabor"));
		}
		if(null!=soIncreaseSpendLimitdto && (StringUtils.isNotBlank(soIncreaseSpendLimitdto.getTotalSpendLimit())) &&(StringUtils.isNotBlank(soIncreaseSpendLimitdto.getTotalSpendLimitParts()))){
		 currentSpendLimitLabor =  new BigDecimal(soIncreaseSpendLimitdto.getTotalSpendLimit());
		 currentSpendLimitParts = new BigDecimal(soIncreaseSpendLimitdto.getTotalSpendLimitParts());
		 currentTotalSpendLimit = currentSpendLimitLabor.add(currentSpendLimitParts);
		}
		//fetching the spend limit details from DB
		 initialPriceDetailsVO =   getInitialSpendLimit(soIncreaseSpendLimitdto.getSelectedSO());
		 if(null!=initialPriceDetailsVO && null!=initialPriceDetailsVO.getInitialLaborPrice() && null!=initialPriceDetailsVO.getInitialPartsPrice()){ //null check
		    	DBSpendLimitLabor =  new BigDecimal(initialPriceDetailsVO.getInitialLaborPrice());
		    	DBSpendLimitParts = new BigDecimal(initialPriceDetailsVO.getInitialPartsPrice());
		    	DBTotalSpendLimit = DBSpendLimitLabor.add(DBSpendLimitParts);
		    }
		 if(null!=DBTotalSpendLimit && null!=currentTotalSpendLimit){//null checks
			 errorMessageForIncreasePrice = Constants.ERROR_MSG_FOR_INCR_PRICE;
	    	result = currentTotalSpendLimit.compareTo(DBTotalSpendLimit);
	    }
		 //SL-21045 --changes --END
		ServiceOrdersCriteria context = get_commonCriteria();
		soIncSLDto.setBuyerId(context.getSecurityContext().getCompanyId() + "");
		boolean relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(Integer.parseInt(soIncSLDto.getBuyerId()), this.soId);
		System.out.println("RoleId : " + context.getSecurityContext().getRoleId());
		if (context != null &&
			 context.getSecurityContext() != null && 
			 (context.getSecurityContext().getRoleId() == OrderConstants.BUYER_ROLEID || 
					 context.getSecurityContext().getRoleId() == OrderConstants.SIMPLE_BUYER_ROLEID)){
			if(result>0){ //currentTotalSpendLimit is greater than DBTotalSpendLimit
			pResp = getDetailsDelegate().increaseSpendLimit(soIncreaseSpendLimitdto);
			
			strErrorCode = pResp.getCode();
			strErrorMessage = pResp.getMessages().get(0);
			if (strErrorCode.equalsIgnoreCase(USER_ERROR_RC))
			{	
				//Go to common error page in case of business logic failure error or fatal error
				//Sl-19820
				//strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
				strResponse = SUCCESS;
				//Sl-19820
				//getSession().setAttribute(Constants.SESSION.SOD_ERR_LIST,soIncSLDto.getErrors());
				getSession().setAttribute(Constants.SESSION.SOD_ERR_LIST+"_"+this.soID,soIncSLDto.getErrors());
				setAttribute(Constants.SESSION.SOD_ERR_LIST,soIncSLDto.getErrors());
				soIncSLDto.setErrors(null);
				//SL-19820
				//getSession().setAttribute(Constants.SESSION.SOD_INC_SO_DTO, soIncSLDto);
				getSession().setAttribute(Constants.SESSION.SOD_INC_SO_DTO+"_"+this.soID, soIncSLDto);
				setAttribute(Constants.SESSION.SOD_INC_SO_DTO, soIncSLDto);
				defaultSelectedTab = SODetailsUtils.ID_SUMMARY;
			}else if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
			{	
				//Go to common error page in case of business logic failure error or fatal error
				strResponse = ERROR; 
				this.setReturnURL(SOD_ACTION+"?soId="+this.soID);
				this.setErrorMessage(strErrorMessage);
			}else 
			{
				//Sl-19820
				//strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
				strResponse = SUCCESS;
				//Refetch the service order from database
				//SL-19820 : not needed
				//getSession().setAttribute(REFETCH_SERVICE_ORDER, new Boolean(true));
				// relay notification changes for increasing spend limit
//				logger.info("Checking for relay notification");
//				if (relayServicesNotifyFlag) {
//					logger.info("relayServicesNotifyFlag = "+relayServicesNotifyFlag);
//					if(0 < result){
//						Map<String, String> params = new HashMap<String, String>();
//						params.put("newLaborPrice", currentSpendLimitLabor.toString());
//						params.put("newPartsPrice", currentSpendLimitParts.toString());
//						params.put("oldLaborPrice", DBSpendLimitLabor.toString());
//						params.put("oldPartsPrice", DBSpendLimitParts.toString());
//						logger.info("sending WH to relay");
//						relayNotificationService.sentNotificationRelayServices(MPConstants.ORDER_SPENDLIMIT_UPDATE_FROM_FRONTEND, soId, params);	
//					}
//				}
			}
			//SL-21045 --changes --START
		}else{
			// not allowing user to increase spend if the new price is less than the original price
			strResponse = SUCCESS; 
			defaultSelectedTab = SODetailsUtils.ID_SUMMARY;
			this.setErrorMessage(errorMessageForIncreasePrice);
			strErrorMessage = errorMessageForIncreasePrice;
		}//SL-21045 --changes --END
		}
		else
		{
			strErrorMessage = OrderConstants.PROVIDER_OPERATION_NOT_PERMITTED;
			logger.debug(OrderConstants.PROVIDER_OPERATION_NOT_PERMITTED);
			strResponse = ERROR; 
			this.setReturnURL(SOD_ACTION+"?soId="+this.soID);
			this.setErrorMessage(strErrorMessage);
		}
		getRequest().setAttribute("soIncSLDto", soIncSLDto);
		//SL-19820
		//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
		getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+this.soID, strErrorMessage);
		setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
		logger.debug("strResponse : " + strResponse);
		this.setDefaultTab( defaultSelectedTab );
		logger.info("----End of ServiceOrderIncreaseSpendLimitAction.increaseSpendLimit----");
		return strResponse;
    }
	/**
	 * This method is used to fetch spend limit details of so
	 * 
	 * @param soid
	 * @return InitialPriceDetailsVO
	 */
	 private InitialPriceDetailsVO getInitialSpendLimit(String soId){
		  InitialPriceDetailsVO initialPriceDetailsVO = null;
		  
		 try{
			 initialPriceDetailsVO = getDetailsDelegate().getInitialPrice(soId);
		 }catch (Exception e) {
			 logger.error("Exception in getInitialSpendLimit:"+ e.getMessage());
		}
		 
		  return initialPriceDetailsVO;
	  }
	public ISODetailsDelegate getISODetailDelegate() {
		return ISODetailDelegate;
	}

	public void setISODetailDelegate(ISODetailsDelegate detailDelegate) {
		ISODetailDelegate = detailDelegate;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
    
}
