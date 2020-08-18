package com.newco.marketplace.web.action.widgets.spendlimit;

import java.util.ArrayList;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.IncreaseSpendLimitDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.security.SecuredAction;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
/**
 * 
 * $Revision: 1.18 $ $Author: gjacks8 $ $Date: 2008/05/27 23:49:22 $
 *
 */
public class ServiceOrderIncreaseSpendLimitAction extends SLBaseAction implements Preparable, OrderConstants, ServiceConstants, ModelDriven<IncreaseSpendLimitDTO>{

    private static final Logger logger = Logger
            .getLogger("ServiceOrderIncreaseSpendLimitAction");
    private static final long serialVersionUID = 101L;

    private ISOMonitorDelegate soMonitorDelegate = null;
    private IncreaseSpendLimitDTO soIncSLDto = new IncreaseSpendLimitDTO();
    
	  
	String strErrorCode = "";
	String strErrorMessage = "";
	String strResponse = "";
    
    private String selectedSO = "";
    private String groupId = null;
    private String selectedRowIndex = "";
    private double currentSpendLimit = 0.0; // Not used
    private double currentLimitLabor = 0.0;
    private double currentLimitParts = 0.0;
    private double totalSpendLimit= 0.0;
    private double totalSpendLimitParts= 0.0;
    private double increaseLimit = 0.0;
    private String incSpendLimitComment = "";
    
    public ServiceOrderIncreaseSpendLimitAction(SOMonitorDelegateImpl soMonitorDelegate) {
        this.soMonitorDelegate = soMonitorDelegate;
    }
    @SecuredAction(securityTokenEnabled = true)
    public String execute() throws Exception {
     	logger.info("----Start of ServiceOrderIncreaseSpendLimitAction.increaseSpendLimit----");
	
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		String strMessage = "";
		
		createCommonServiceOrderCriteria();
		ServiceOrdersCriteria context = get_commonCriteria();
		ProcessResponse processResponse = null;

		if (context != null &&
			 context.getSecurityContext() != null && 
			 (context.getSecurityContext().getRoleId() == OrderConstants.BUYER_ROLEID || context.getSecurityContext().getRoleId() == OrderConstants.SIMPLE_BUYER_ROLEID)){
			/*processResponse = soMonitorDelegate.increaseSpendLimit(getSelectedSO(),
														getGroupId(),
														getCurrentLimitLabor(),
														getCurrentLimitParts(),
														getTotalSpendLimit(),
														getTotalSpendLimitParts(),
														getIncSpendLimitComment(),
														context.getSecurityContext().getCompanyId());*/
		}
		else
		{
			strMessage = OrderConstants.PROVIDER_OPERATION_NOT_PERMITTED;
			logger.debug(OrderConstants.PROVIDER_OPERATION_NOT_PERMITTED);
		}
		
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
    	strMessage = processResponse.getMessages().get(0);
		if(processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC)){
        	actionResults.setActionState(0);
        	actionResults.setResultMessage(strMessage);
        }
        else{
        	actionResults.setActionState(1);
        	actionResults.setResultMessage(strMessage);
        	actionResults.setAddtionalInfo1(getSelectedRowIndex());
        	actionResults.setAddtionalInfo2(new Double(getTotalSpendLimit()).toString());
        	actionResults.setAddtionalInfo3(new Double(getTotalSpendLimitParts()).toString());
        }
       
		
		response.getWriter().write(actionResults.toXml());
		logger.info("----End of ServiceOrderIncreaseSpendLimitAction.increaseSpendLimit----");
		return NONE;
    }


	public double getCurrentSpendLimit() {
		return currentSpendLimit;
	}

	public void setCurrentSpendLimit(double currentSpendLimit) {
		this.currentSpendLimit = currentSpendLimit;
	}

	public double getTotalSpendLimit() {
		return totalSpendLimit;
	}

	public void setTotalSpendLimit(double totalSpendLimit) {
		this.totalSpendLimit = totalSpendLimit;
	}

	public double getIncreaseLimit() {
		return increaseLimit;
	}

	public void setIncreaseLimit(double increaseLimit) {
		this.increaseLimit = increaseLimit;
	}

	public String getIncSpendLimitComment() {
		return incSpendLimitComment;
	}

	public void setIncSpendLimitComment(String incSpendLimitComment) {
		this.incSpendLimitComment = incSpendLimitComment;
	}

	public String getSelectedSO() {
		return selectedSO;
	}

	public void setSelectedSO(String selectedSO) {
		this.selectedSO = selectedSO;
	}

	public double getTotalSpendLimitParts() {
		return totalSpendLimitParts;
	}

	public void setTotalSpendLimitParts(double totalSpendLimitParts) {
		this.totalSpendLimitParts = totalSpendLimitParts;
	}

	public String getSelectedRowIndex() {
		return selectedRowIndex;
	}

	public void setSelectedRowIndex(String selectedRowIndex) {
		this.selectedRowIndex = selectedRowIndex;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public ISOMonitorDelegate getSoMonitorDelegate() {
		return soMonitorDelegate;
	}

	public void setSoMonitorDelegate(ISOMonitorDelegate soMonitorDelegate) {
		this.soMonitorDelegate = soMonitorDelegate;
	}

	public double getCurrentLimitLabor() {
		return currentLimitLabor;
	}

	public void setCurrentLimitLabor(double currentLimitLabor) {
		this.currentLimitLabor = currentLimitLabor;
	}

	public double getCurrentLimitParts() {
		return currentLimitParts;
	}

	public void setCurrentLimitParts(double currentLimitParts) {
		this.currentLimitParts = currentLimitParts;
	}
	public void prepare() throws Exception {	
	}
	
  public String increaseSpendLimit() throws Exception{

	  String returnVal = "";
	  //SL-21045 --changes --START
	  BigDecimal currentSpendLimitLabor = null; 
	  BigDecimal currentSpendLimitParts = null;
	  BigDecimal DBSpendLimitLabor = null;
	  BigDecimal DBSpendLimitParts = null;
	  InitialPriceDetailsVO initialPriceDetailsVO = null;
	  BigDecimal DBTotalSpendLimit = null,currentTotalSpendLimit = null;
	  String errorMessageForIncreasePrice = "";
	  int result = 0;
	  IncreaseSpendLimitDTO model = getModel();
	  if(model.isTaskLevelPriceInd()){
	  	model.setTotalSpendLimit(getRequest().getParameter("totalSpendLimitAmt"));
	  	model.setTotalSpendLimitParts(getRequest().getParameter("increaseLimitParts"));
	  }
	    model.setIncreasedSpendLimitReason(getRequest().getParameter("increasedSpendLimitReason"));
	    model.setIncreasedSpendLimitReasonId(getRequest().getParameter("increasedSpendLimitReasonId"));
	    model.setIncreasedSpendLimitComment(getRequest().getParameter("increasedSpendLimitNotes"));
	    if(!model.isTaskLevelPriceInd()){
	    	model.setIncreasedSpendLimitComment(getRequest().getParameter("increasedSpendLimitNotesWidget"));
	    	model.setIncreasedSpendLimitReasonId(getRequest().getParameter("increasedSpendLimitReasonIdWidget"));
	    	model.setIncreasedSpendLimitReason(getRequest().getParameter("increasedSpendLimitReasonWidget"));
	    }
	    if(null!=model &&(StringUtils.isNotBlank(model.getTotalSpendLimit())) &&(StringUtils.isNotBlank(model.getTotalSpendLimitParts()))){
	    		currentSpendLimitLabor =  new BigDecimal(model.getTotalSpendLimit());
	    		currentSpendLimitParts = new BigDecimal(model.getTotalSpendLimitParts());
	    		currentTotalSpendLimit = currentSpendLimitLabor.add(currentSpendLimitParts);
	    }
	    //fetching the spend limit details from DB
	     initialPriceDetailsVO =   getInitialSpendLimit(model.getSelectedSO());
	    if(null!=initialPriceDetailsVO && null!=initialPriceDetailsVO.getInitialLaborPrice() && null!=initialPriceDetailsVO.getInitialPartsPrice()){ //null check
	    	 DBSpendLimitLabor =  new BigDecimal(initialPriceDetailsVO.getInitialLaborPrice());
	    	 DBSpendLimitParts = new BigDecimal(initialPriceDetailsVO.getInitialPartsPrice());
	    	 DBTotalSpendLimit = DBSpendLimitLabor.add(DBSpendLimitParts);
	    	 errorMessageForIncreasePrice = Constants.ERROR_MSG_FOR_INCR_PRICE;
	    }
	    if(null!=currentTotalSpendLimit && null!= DBTotalSpendLimit){
	    	 result = currentTotalSpendLimit.compareTo(DBTotalSpendLimit);
	    }
	    AjaxResultsDTO actionResults = new AjaxResultsDTO();
		if(result>0){ //currentTotalSpendLimit is greater than DBTotalSpendLimit
		ProcessResponse processResponse = null;
		processResponse = soMonitorDelegate.increaseSpendLimit(model);
		
		strErrorCode = processResponse.getCode();
		strErrorMessage = processResponse.getMessages().get(0);		
		
	
		if(processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC)){
        	actionResults.setActionState(0);
        	actionResults.setResultMessage(strErrorMessage);
	}
        else{
        	actionResults.setActionState(1);
        	actionResults.setResultMessage(strErrorMessage);
        	actionResults.setAddtionalInfo1(getSelectedRowIndex());
        	actionResults.setAddtionalInfo2(model.getTotalSpendLimit());
        	actionResults.setAddtionalInfo3(model.getTotalSpendLimitParts());
	}
		}else{
			actionResults.setActionState(0);
			actionResults.setResultMessage(errorMessageForIncreasePrice);
		}
		//SL-21045 --changes --END
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(actionResults.toXml());
		return NONE;

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
		 initialPriceDetailsVO = soMonitorDelegate.getInitialPrice(soId);
	 }catch (Exception e) {
		 logger.error("Exception in getInitialSpendLimit:"+ e.getMessage());
	}
	 
	  return initialPriceDetailsVO;
  }

	public IncreaseSpendLimitDTO getModel() {
		return soIncSLDto;
	}
}
