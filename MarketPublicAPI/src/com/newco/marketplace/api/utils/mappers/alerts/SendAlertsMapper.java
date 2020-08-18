/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.alerts;

import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.alerts.Parameter;
import com.newco.marketplace.api.beans.alerts.SendAlertsRequest;
import com.newco.marketplace.api.beans.alerts.SendAlertsResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.interfaces.AlertConstants;
/**
 * This class would act as a Mapper class for SendAlert API
 * 
 * @author Infosys
 * @version 1.0
 */
public class SendAlertsMapper implements AlertConstants{
	private Logger logger = Logger.getLogger(SendAlertsMapper.class);
	
	/**
	 * This method is used for SendAlert request and converts POJO
	 * request object(SendAlertsRequest) to AlertTask.
	 * 
	 * @param sendAlertsRequest SendAlertsRequest
	 * @return AlertTask
	 * @throws DataException
	 */
	public  AlertTask getVOForSendAlerts(SendAlertsRequest sendAlertsRequest) 
														throws DataException {
		if (sendAlertsRequest == null) {
			logger.error("Send ALert Request: POJO object created "
					+ "from request XML String did not have any value.");
			throw new DataException("POJO Object created from XML did not "
					+ "have any values");
		}
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setAlertTo(sendAlertsRequest.getDestination());
		alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
		alertTask.setModifiedBy(PublicAPIConstant.API);
		alertTask.setTemplateId(new Integer(sendAlertsRequest.getTemplateID()));
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setAlertTypeId(new Integer(sendAlertsRequest.getAlertType()));
		alertTask.setPriority(PublicAPIConstant.PRIORITY1);
			
		if (sendAlertsRequest.getParameters() != null) {
			if (sendAlertsRequest.getParameters().getParameterList()!= null
					&& sendAlertsRequest.getParameters().
												getParameterList().size() > 0) {
				Iterator<Parameter> parameterList = sendAlertsRequest.
										getParameters().getParameterList()
							.iterator();
				String templateString = "";
				while (parameterList.hasNext()) {
						Parameter parameter = (Parameter) parameterList.next();
						templateString = templateString + parameter.getName();
						templateString = templateString + "=";
						templateString = templateString + parameter.getValue();
						templateString = templateString + "|";
				}
				alertTask.setTemplateInputValue(templateString);
			}
		}
		return alertTask;
	}
	
	/**
	 * This method is for creating SendAlertsResponse Object .
	 * 
	 * @param isSuccess boolean 
	 * @return SendAlertsResponse
	 */
	public SendAlertsResponse mapSendAlertsResponse(boolean isSuccess) {
		if (logger.isInfoEnabled())
			logger.info("Inside SendAlertsMapper.mapSendAlertsResponse()");
		Results results = null;
		if (isSuccess) {
			results = Results.getSuccess(
					ResultsCode.SEND_ALERT_SUCCESSFUL.getMessage());
		} else {
			results=Results.getError(
					ResultsCode.SEND_ALERT_NOT_SUCCESSFUL.getMessage(),
					ResultsCode.SEND_ALERT_NOT_SUCCESSFUL.getCode());
		}
		SendAlertsResponse sendAlertsResponse = new SendAlertsResponse(results);
		return sendAlertsResponse;
	}
		
}
