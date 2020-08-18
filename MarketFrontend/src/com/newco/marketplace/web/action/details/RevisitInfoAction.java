package com.newco.marketplace.web.action.details;

import org.apache.log4j.Logger;


import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.RevisitNeededInfoDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.activitylog.client.IActivityLogHelper;
import org.apache.commons.lang.StringUtils;

public class RevisitInfoAction extends SLDetailsBaseAction implements Preparable,ModelDriven{
	

	private static final long serialVersionUID = -7920137428157585765L;
	private static final Logger logger = Logger.getLogger("RevisitInfoAction");
	private ISODetailsDelegate soDetailsManager;
	private RevisitNeededInfoDTO revisitNeededInfoDTO = new RevisitNeededInfoDTO();
	private IActivityLogHelper helper;

	public RevisitInfoAction(ISODetailsDelegate delegate) {
		this.soDetailsManager = delegate;
	}
	
	public String revisitInfo(){
		logger.info("Entering the RevisitInfoAction.revisitInfo()");
		String soId=getParameter(TRIP_SO_ID);
		String tripNoFromURL=getParameter(SO_TRIP_NO);
		Integer soTripNo=0;
		
		if(null != tripNoFromURL && StringUtils.isNumeric(tripNoFromURL)){
			soTripNo=Integer.parseInt(tripNoFromURL);
		}
		RevisitNeededInfoDTO revisitDTO=new RevisitNeededInfoDTO();
		try{
			revisitDTO=soDetailsManager.getTripRevisitDetails(soId,soTripNo);
			
			if(null != revisitDTO){
				revisitNeededInfoDTO=revisitDTO;
			}
		}
		catch (Exception e) {
			logger.error("Exception in  RevisitInfoAction - revisitInfo(): "+e);	
		}
		return SUCCESS;
	}

	public void prepare() throws Exception {
		logger.info("Entering Prepare of RevisitInfoAction");
		createCommonServiceOrderCriteria();
		
	}
	
	
	public Object getModel() {
		return revisitNeededInfoDTO;
	}
	
	public void setModel(Object x){
		revisitNeededInfoDTO = (RevisitNeededInfoDTO) x;	
	}

	public ISODetailsDelegate getSoDetailsManager() {
		return soDetailsManager;
	}

	public void setSoDetailsManager(ISODetailsDelegate soDetailsManager) {
		this.soDetailsManager = soDetailsManager;
	}

	public IActivityLogHelper getHelper() {
		return helper;
	}

	public void setHelper(IActivityLogHelper helper) {
		this.helper = helper;
	}

	public RevisitNeededInfoDTO getRevisitNeededInfoDTO() {
		return revisitNeededInfoDTO;
	}

	public void setRevisitNeededInfoDTO(RevisitNeededInfoDTO revisitNeededInfoDTO) {
		this.revisitNeededInfoDTO = revisitNeededInfoDTO;
	}
	
	
	
}