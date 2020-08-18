/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.alerts;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.alerts.SendAlertsRequest;
import com.newco.marketplace.api.beans.alerts.SendAlertsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.alerts.SendAlertsMapper;
import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;

/**
 * This class is a service class for inserting entry to alert_task table for 
 * sending email
 * 
 * @author Infosys
 * @version 1.0
 */
public class SendAlertsService extends BaseService{
	
	private Logger logger = Logger.getLogger(SendAlertsService.class);
	private SendAlertsMapper sendAlertsMapper=null;
	private EmailAlertBO emailAlertBO;

	/**
	 * Constructor
	 */
	
	public SendAlertsService () {
		super (PublicAPIConstant.sendAlert.REQUEST_XSD,
				PublicAPIConstant.sendAlert.RESPONSE_XSD, 
				PublicAPIConstant.sendAlert.NAMESPACE, 
				PublicAPIConstant.sendAlert.RESOURCES_SCHEMAS,
				PublicAPIConstant.sendAlert.SCHEMALOCATION,	
				SendAlertsRequest.class,
				SendAlertsResponse.class);
	}	
	
	
	
	/**
	 * This method dispatches the send Alert service order request.
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {		
		logger.info("Entering execute method of SendAlertsService");	
		SendAlertsRequest request  = null;	
		SendAlertsResponse sendAlertsResponse = null;
		
		try {
			request  = (SendAlertsRequest) apiVO.getRequestFromPostPut();	
			AlertTask alertTask = sendAlertsMapper.getVOForSendAlerts(request);
			boolean isInserted = emailAlertBO.insertToAlertTask(alertTask);
			sendAlertsResponse = sendAlertsMapper.mapSendAlertsResponse(isInserted);
		}catch (Exception e ) {
			sendAlertsResponse.setResults(
				Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		if(logger.isInfoEnabled()){
			logger.info("Exiting  SendAlertsService.execute()");
		}	
		return sendAlertsResponse;
	}

	public void setSendAlertsMapper(SendAlertsMapper sendAlertsMapper) {
		this.sendAlertsMapper = sendAlertsMapper;
	}
	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}


	

}
