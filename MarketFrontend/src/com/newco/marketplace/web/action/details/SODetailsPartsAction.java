package com.newco.marketplace.web.action.details;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOPartsListDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SODetailsPartsAction extends SLDetailsBaseAction implements Preparable, ModelDriven<SOPartsListDTO>{
	
	private static final Logger logger = Logger.getLogger(SODetailsPartsAction.class.getName());
	private ISODetailsDelegate detailsDelegate;
	
	private SOPartsListDTO soPartsListDTO = new SOPartsListDTO();
	
	//SL-19820
	String soId;
	String resourceId;
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public SOPartsListDTO getModel() {
		return soPartsListDTO;
	}
	
	public SODetailsPartsAction( ISODetailsDelegate detailsDelegate)
	{
		this.detailsDelegate = detailsDelegate;
	}
	

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		String soId = getParameter("soId");
		setAttribute(SO_ID, soId);
		this.soId = soId;
		String resId = getParameter("resId");
		this.resourceId = resId;
		setAttribute(Constants.SESSION.SOD_ROUTED_RES_ID,resId);
	}
	
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	
	public String updatePartsInfo()
	{
		try{
			SOPartsListDTO parts = getModel();
			if(parts != null){ 
				parts.validate();
				if(parts.getErrors().size() == 0){				
					updateSOPartsShippingInfo(parts.getSoId(), parts.getPartsList());   
				}else{
					setAttribute("errors", parts.getErrors());
				}
			}
				
			
		}
		catch(Exception e ){
			logger.error("updatePartsInfo()-->EXCEPTION-->", e);
		}

		return GOTO_COMMON_DETAILS_CONTROLLER;		
	}
	
	public void updateSOPartsShippingInfo(String soId, List partsShippingInfoDTO)
	{
		
		try{
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			SecurityContext securityContext = (SecurityContext) sessionMap.get(Constants.SESSION.SECURITY_CONTEXT);
			detailsDelegate.updateSOPartsShippingInfo(soId, partsShippingInfoDTO, securityContext);
		}
		catch(BusinessServiceException bse ){
			logger.error("updateSOSubStatus()-->EXCEPTION-->", bse);
		}
	}


	/*public List getPartsList() {
		return partsList;
	}


	public void setPartsList(List partsList) {
		this.partsList = partsList;
	}*/
	
}
