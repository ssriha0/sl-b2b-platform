package com.newco.marketplace.web.action.details;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.AddonServiceRowDTO;
import com.newco.marketplace.web.dto.AddonServicesDTO;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.dto.SOWSelBuyerRefDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;


public class SODetailsCloseAndPayAction extends SLDetailsBaseAction implements
        Preparable, ServiceConstants, OrderConstants {
    private static final Logger logger = Logger.getLogger(SODetailsCloseAndPayAction.class);
   
    private static final long serialVersionUID = 100501;// arbitrary number to get rid
    static private String SOD_ACTION = "/soDetailsController.action";
    
    IFinanceManagerDelegate financeManagerDelegate;
    IServiceOrderUpsellBO  serviceOrderUpsellBO;
    
    private SOCompleteCloseDTO soCloseDto = new SOCompleteCloseDTO();
    public String defaultTab;
	public String message;
	//SL-19820
	String soId;
	
    public void setSoId(String soId) {
		this.soId = soId;
	}

	public SODetailsCloseAndPayAction(ISODetailsDelegate delegate) {
        this.detailsDelegate = delegate;
    }

    public void prepare() throws Exception {
    	logger.debug("----Start of SODetailsCloseAndPayAction.prepare----");
    	double partsSpendLimit = 0.0;
    	double laborSpendLimit = 0.0;
    	double totalSpendLimit = 0.0;
    	String resComment = "";
    	double finalPartsPrice = 0.0;
    	double finalLaborPrice = 0.0;
    	double finalPrice = 0.0;
    	
    	createCommonServiceOrderCriteria();
    	//SL-19820
    	String strSoId = getParameter("soId");
    	setAttribute(OrderConstants.SO_ID, strSoId);
    	this.soId = strSoId;
    	String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
    	getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
    	ServiceOrderDTO soDTO = null;
			
    	
		try{
			soDTO = getDetailsDelegate().getServiceOrder(strSoId, get_commonCriteria().getRoleId(), null);				
					
		}catch(Exception e){
			logger.error("Exception while trying to fetch SO Details");
		}
		setAttribute(THE_SERVICE_ORDER, soDTO);
		setCurrentSOStatusCodeInRequest(soDTO.getStatus());
        //ServiceOrderDTO soDTO = getCurrentServiceOrderFromSession();
        List<SoAutoCloseDetailVo> soAutoCloseInfoList=new ArrayList<SoAutoCloseDetailVo>();

        getUpsellInfo(soDTO);
        //String strSoId =getSession().getAttribute(OrderConstants.SO_ID).toString();
        Boolean isPaymentInfoViewable = isPermissionAvaialble("ViewCustomerPayment");
        AdditionalPaymentVO additionalPaymentVO = getAdditionalPaymentInfo(strSoId,isPaymentInfoViewable);
        resComment = soDTO.getResolutionComment();
        if (soDTO.getPartsSpendLimit() != null)
        	partsSpendLimit = soDTO.getPartsSpendLimit();
        if (soDTO.getLaborSpendLimit() != null)
        	laborSpendLimit = soDTO.getLaborSpendLimit();
        totalSpendLimit = partsSpendLimit + laborSpendLimit;
        finalPartsPrice = soDTO.getFinalPartsPrice();
        finalLaborPrice = soDTO.getFinalLaborPrice();
        finalPrice = finalLaborPrice + finalPartsPrice;

		AddonServicesDTO addonServicesDTO = new AddonServicesDTO();
		soCloseDto.setAddonServicesDTO(addonServicesDTO);
        
        // Handle Upsell Payment
        if (
				   additionalPaymentVO != null &&
				   soDTO.getUpsellInfo() != null &&
				   soDTO.getUpsellInfo().getProviderPaidTotal() != null &&
				   soDTO.getShowUpsellInfo() &&
				   soDTO.getUpsellInfo().getProviderPaidTotal() > 0
		   	)
		{
        	if(soDTO.getUpsellInfo().getAddonServicesList() != null){
    			soCloseDto.getAddonServicesDTO().setAddonServicesList(soDTO.getUpsellInfo().getAddonServicesList());	
    		}
        	soCloseDto.getAddonServicesDTO().setProviderPaidTotal(soDTO.getUpsellInfo().getProviderPaidTotal());
			updateAddonPaymentInfo(soCloseDto.getAddonServicesDTO(), isPaymentInfoViewable,additionalPaymentVO);
		}
        soCloseDto.setPermitTaskList(soDTO.getPermitTaskList());
        Double maxLabor =soDTO.getSoTaskMaxLabor();
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		maxLabor = Double.valueOf(twoDForm.format(maxLabor.doubleValue()));
		soCloseDto.setSoMaxLabor(maxLabor.toString());
        soCloseDto.setSoFinalMaxLabor(soDTO.getSoFinalMaxLabor());
        soCloseDto.setPermitTaskAddonPrice(soDTO.getPermitTaskAddonPrice());
		soCloseDto.setTaskLevelPricing(soDTO.isTaskLevelPriceInd());
        soCloseDto.setResComments(resComment);
        soCloseDto.setPartSpLimit(partsSpendLimit);
        soCloseDto.setLaborSpLimit(laborSpendLimit);
        soCloseDto.setTotalSpLimit(totalSpendLimit);
        soCloseDto.setFinalLaborPrice(String.valueOf(finalLaborPrice));
        soCloseDto.setFinalPartPrice(String.valueOf(finalPartsPrice));
        soCloseDto.setFinalPrice(finalPrice);
       
        soCloseDto.setInvoiceParts(soDTO.getInvoiceParts());
        soCloseDto.setInvoicePartsPricingModel(soDTO.getInvoicePartsPricingModel());
        soCloseDto.setRoleId(_commonCriteria.getRoleId());
        
        getDetailsDelegate().setInvoiceDocuments(soCloseDto);
	    
        getDetailsDelegate().getSoAutoCloseCompletionList(strSoId);
        
        soAutoCloseInfoList=getDetailsDelegate().getSoAutoCloseCompletionList(strSoId);

        if(soDTO.getUpsellInfo() != null){
        	soCloseDto.setUpsellInfo(soDTO.getUpsellInfo());
        } 
        
        //SL-20926
        soCloseDto.setAdditionalPaymentVO(additionalPaymentVO) ;
       
        //SL-19820
        //getSession().setAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO, soCloseDto);
        getSession().setAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO+"_"+this.soId, soCloseDto);
        setAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO, soCloseDto);
        getRequest().setAttribute(Constants.SESSION.SOD_SO_AUTOCLOSED_INFO_DTO,soAutoCloseInfoList);
        
        //Priority 5B
        //Set warning message if the provider has entered invalid model and/or serial no
        if(OrderConstants.INHOME_BUYER.equalsIgnoreCase(soDTO.getBuyerID())){
        	soCloseDto.setBuyerID(soDTO.getBuyerID());
        	setModelSerialPanel();
        }
        logger.debug("----End of SODetailsCloseAndPayAction.prepare----");
    }

   

	
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String closeAndPay() throws Exception {
    	logger.debug("----Start of SODetailsCloseAndPayAction.closeAndPay----");
		String strResponse = "";
		String strErrorCode = "";
		String strSoId = "";
		Integer intBuyerId = null;
		int intRoleType = 0;
		double finalPartsPrice = 0.0;
    	double finalLaborPrice = 0.0;
    	ProcessResponse prResp = null;
    	SOCompleteCloseDTO soCloseDto = null;
		String defaultSelectedTab="";
		String strErrorMessage = "";
		try{
			//SL-19820
			//strSoId =getSession().getAttribute(OrderConstants.SO_ID).toString();
			strSoId = getAttribute(OrderConstants.SO_ID).toString();
			ServiceOrdersCriteria context = get_commonCriteria();
			intBuyerId = context.getSecurityContext().getCompanyId();
			intRoleType = context.getSecurityContext().getRoleId();
			//SL-19820
			//soCloseDto = (SOCompleteCloseDTO)getSession().getAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO);
			soCloseDto = (SOCompleteCloseDTO)getAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO);
			finalPartsPrice = Double.parseDouble(soCloseDto.getFinalPartPrice());
			finalLaborPrice =  Double.parseDouble(soCloseDto.getFinalLaborPrice());
			
			//SL-20926 : check for addon issues
			boolean addonIssue = false;
			List<AddonServiceRowDTO> addons = null;
			if(null != soCloseDto){
				if(null != soCloseDto.getUpsellInfo()){
					addons = soCloseDto.getUpsellInfo().getAddonServicesList();
				}
				if(!((null == addons || (null != addons && addons.isEmpty())) && null == soCloseDto.getAdditionalPaymentVO())) {
					addonIssue = this.checkForAddonIssues(addons, soCloseDto.getAdditionalPaymentVO());
		      	}
			}
			
	        if(addonIssue){
	        	strResponse = OrderConstants.ADDON_RESPONSE;
	        	getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId, OrderConstants.ADDON_CLOSE_ERROR);
				defaultSelectedTab = SODetailsUtils.ID_CLOSE_AND_PAY;
	        }
	        else if (soCloseDto.getTotalSpLimit() - soCloseDto.getFinalPrice() < 0) {
				strResponse = "spendLimitValidation";
				defaultTab = "Close and Pay";
				message = "spendLimitValidation";
				defaultSelectedTab = SODetailsUtils.ID_CLOSE_AND_PAY;
			} else {
			prResp = getDetailsDelegate().serviceOrderClose(intBuyerId, strSoId,  soCloseDto);
			strErrorCode = prResp.getCode();
			if(null!=prResp.getMessages() && !prResp.getMessages().isEmpty()){
			    strErrorMessage = prResp.getMessages().get(0);
			}
			strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
			if (strErrorCode.equalsIgnoreCase(USER_ERROR_RC))
			{
				 defaultSelectedTab = SODetailsUtils.ID_CLOSE_AND_PAY;
				 this.setErrorMessage(strErrorMessage);
			}
			else if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
			{	
				//Go to common error page in case of business logic failure error or fatal error
				strResponse = ERROR; 
				//Sl-19820
				//this.setReturnURL(SOD_ACTION);
				this.setReturnURL(SOD_ACTION+"?soId="+this.soId);
				this.setErrorMessage(strErrorMessage);
			}
			else
			{
				defaultSelectedTab = SODetailsUtils.ID_RATE_PROVIDER;
				//SL-19820
				//setCurrentSOStatusCodeInSession(OrderConstants.CLOSED_STATUS);
				setCurrentSOStatusCodeInRequest(OrderConstants.CLOSED_STATUS);
				
				//Priority 5B changes
				//update invalid_model_serial_ind in so_workflow-controls to null
				//after closing so that it exits the queue
				if(null != intBuyerId && OrderConstants.INHOME_BUYER.equalsIgnoreCase(intBuyerId.toString())){
					if(null != getRequest().getAttribute(Constants.MODEL_SERIAL_IND)){
						getDetailsDelegate().updateModelSerialInd(strSoId, null);
					}
				}
			}
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId, strErrorMessage);
			setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			}
			
			logger.debug(strSoId + " - strResponse : " + strResponse);
			
			//Redisplay the tabs
		    logger.debug(strSoId + " - defaultSelectedTab : " + defaultSelectedTab);
			this.setDefaultTab( defaultSelectedTab );

		} catch (BusinessServiceException e) {
			logger.error(strSoId + " - Exception in closing the service order: ", e);
			strResponse = ERROR; 
		}
		logger.debug("----End of SODetailsCloseAndPayAction.closeAndPay----");
		return strResponse;
    }

    /**
	 * priority 5B changes
	 * Set warning messages if model/serial no is invalid
	 * @param soLoggingVO
	 * @return 
	 * @throws BusinessServiceException
	 */
	private void setModelSerialPanel(){
		
		ServiceOrderDTO soDTO = getCurrentServiceOrderFromRequest();
		if(null != soDTO){
			
			List<String> errorMsgs = new ArrayList<String>();
			List<SOWSelBuyerRefDTO> customRefs = new ArrayList<SOWSelBuyerRefDTO>();
			
			//if model/serial no is invalid
			//indicator will be set in so_workflow_controls
			if(null != soDTO.getInvalidModelSerialInd()){
				
				if(Constants.BOTH.equalsIgnoreCase(soDTO.getInvalidModelSerialInd())){
					errorMsgs.add(Constants.MODEL_INVALID_ERROR);
					errorMsgs.add(Constants.SERIAL_INVALID_ERROR);
				}
				else if(InHomeNPSConstants.MODEL.equalsIgnoreCase(soDTO.getInvalidModelSerialInd())){
					errorMsgs.add(Constants.MODEL_INVALID_ERROR);
				}
				else if(InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(soDTO.getInvalidModelSerialInd())){
					errorMsgs.add(Constants.SERIAL_INVALID_ERROR);
				}
			}
			
			//Set model number, serial number and invalid indicator in request
			if(null != soDTO.getSelByerRefDTO() && !soDTO.getSelByerRefDTO().isEmpty()){
				for(SOWSelBuyerRefDTO reference : soDTO.getSelByerRefDTO()){
					if(null != reference && 
							(InHomeNPSConstants.MODEL.equalsIgnoreCase(reference.getRefType()) || 
									InHomeNPSConstants.SERIAL_NUMBER.equalsIgnoreCase(reference.getRefType()))){
						customRefs.add(reference);
					}
				}
			}
			
			getRequest().setAttribute(Constants.MODEL_SERIAL_ERROR, errorMsgs);
			getRequest().setAttribute(Constants.MODEL_SERIAL_VALUES, customRefs);
			getRequest().setAttribute(Constants.MODEL_SERIAL_IND, soDTO.getInvalidModelSerialInd());
		}
	}    
    
	public AdditionalPaymentVO getAdditionalPaymentInfo(String soId, Boolean isSecuredInfoViewable) throws Exception
	{
		return getServiceOrderUpsellBO().getAdditionalPaymentInfo(soId,isSecuredInfoViewable);
	}
    
	/**
	 * SL-20926 changes
	 * Check if addon issues exists for the order, if
	 * AddOn qty = 0 but having additional payment details entry
	 * AddOn qty > 0 but having no additional payment details
	 * AddOn qty > 0 but the payment type in additional payment details is blank
	 * @param addons
	 * @param additionalPaymentVO
	 * @return boolean
	 * throws BusinessServiceException 
	 */
	public boolean checkForAddonIssues(List<AddonServiceRowDTO> addons,AdditionalPaymentVO additionalPaymentVO) 
			throws BusinessServiceException{
		
		boolean addonIssues = false;
		boolean hasQty = false;
		
		//check if any addon is having qty > 0
		if(null != addons && !addons.isEmpty()){
			for(AddonServiceRowDTO addon : addons){
				if(null != addon && null != addon.getQuantity() && addon.getQuantity() > 0){
					hasQty = true;
					break;
				}
			}
		}
			
		//if addon qty = 0 but having additional payment details or
		//addon qty > 0 but having no additional payment details or
		//addon qty > 0 but the payment type in additional payment details is blank
		if((!hasQty && null != additionalPaymentVO) || 
				(hasQty && null == additionalPaymentVO) || 
				(hasQty && null != additionalPaymentVO && (StringUtils.isBlank(additionalPaymentVO.getPaymentType())))){
				
			addonIssues = true;
		}
		return addonIssues;
	}
    
    public ISODetailsDelegate getDetailsDelegate() {
        return detailsDelegate;
    }

    public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
        this.detailsDelegate = detailsDelegate;
    }

    public String getSoId() {
    	//SL-19820
        //return (this.getCurrentServiceOrderId());
    	return soId;
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

	public IServiceOrderUpsellBO getServiceOrderUpsellBO() {
		return serviceOrderUpsellBO;
	}

	public void setServiceOrderUpsellBO(IServiceOrderUpsellBO serviceOrderUpsellBO) {
		this.serviceOrderUpsellBO = serviceOrderUpsellBO;
	}
	
	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
