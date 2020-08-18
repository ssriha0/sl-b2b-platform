/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.cancel.SOCancelResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class would act as a Mapper class for mapping service order to
 * SOCancelResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOCancelMapper {
	private Logger logger = Logger.getLogger(SOCancelMapper.class);

	/**
	 * This method is for mapping Mapping service order cancel result to
	 * SOCancelResponse Object.
	 * 
	 * @param pResponse
	 *            ProcessResponse
	 * @param serviceOrder
	 *            ServiceOrder
	 * @throws DataException
	 * @return SOCancelResponse
	 */
	public SOCancelResponse mapServiceOrder(ProcessResponse pResponse,
			ServiceOrder serviceOrder) throws DataException {
		logger.info("Entering SOCancelMapper.mapServiceOrder()");
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		Results results = new Results();
		OrderStatus orderStatus = new OrderStatus();
		if (serviceOrder != null) {
			logger.info("Setting service order details to OrderStatus object of Response object");
			orderStatus.setSoId(serviceOrder.getSoId());
			
			if (null != serviceOrder.getStatus()) {
				orderStatus.setStatus(serviceOrder.getStatus());
			} else {
				orderStatus.setStatus("");
			}
			
			if (null != serviceOrder.getSubStatus()) {
				orderStatus.setSubstatus((serviceOrder.getSubStatus()));
			} else {
				orderStatus.setSubstatus("");
			}

			if (null != serviceOrder.getCreatedDate()) {
				orderStatus.setCreatedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getCreatedDate()));
			} else {
				orderStatus.setCreatedDate("");
			}

			if (null != serviceOrder.getRoutedDate()) {
				orderStatus.setPostedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getRoutedDate()));
			}
			
			if (null != serviceOrder.getAcceptedDate()) {
				orderStatus.setAcceptedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getAcceptedDate()));
			}
			
			if (null != serviceOrder.getActivatedDate()) {
				orderStatus.setActiveDate(CommonUtility.sdfToDate
						.format(serviceOrder.getActivatedDate()));
			}

			if (null != serviceOrder.getCompletedDate()) {
				orderStatus.setCompletedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getCompletedDate()));
			}
			
			if (null != serviceOrder.getClosedDate()) {
				orderStatus.setClosedDate(CommonUtility.sdfToDate
						.format(serviceOrder.getClosedDate()));
			}
		}
		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
			results = Results.getSuccess(pResponse.getMessages().get(0));
		} else {
			logger.info("Setting result and message as Failure when the " +
												"Cancel Operation has failed");

			results = Results.getError(pResponse.getMessages().get(0),
					ResultsCode.FAILURE.getCode());
		}
		soCancelResponse.setResults(results);
		soCancelResponse.setOrderstatus(orderStatus);
		logger.info("Leaving SOCancelMapper.mapServiceOrder()");
		return soCancelResponse;
	}
}
