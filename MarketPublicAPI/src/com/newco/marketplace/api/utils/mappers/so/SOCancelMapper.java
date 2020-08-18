/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 01-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.cancel.SOCancelResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class would act as a Mapper class for mapping service order to
 * SOCreateResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOCancelMapper {
	private Logger logger = Logger.getLogger(SOCancelMapper.class);

	/**
	 * This method is for mapping Mapping service order cancel result to
	 * SOCreateResponse Object.
	 * 
	 * @param pResponse
	 *            ProcessResponse
	 * @param serviceOrder
	 *            ServiceOrder
	 * @throws DataException
	 * @return SOCreateResponse
	 */
	public SOCancelResponse mapServiceOrder(ProcessResponse pResponse,
			ServiceOrder serviceOrder) throws DataException {
		logger.info("Entering SOCancelMapper.mapServiceOrder()");
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
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

			result.setMessage(pResponse.getMessages().get(0));
			result.setCode(PublicAPIConstant.ONE);
			errorResult.setCode(PublicAPIConstant.ZERO);
			errorResult.setMessage("");
		} else {
			logger
					.info("Setting result and message as Failure when the Cancel operation is failure");
			result.setMessage("");
			result.setCode(PublicAPIConstant.ZERO);
			errorResult.setCode(pResponse.getCode());
			if(null!=pResponse.getMessages().get(0)){
			errorResult.setMessage(pResponse.getMessages().get(0));
			}else{
				errorResult.setMessage("");
			}
		}
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soCancelResponse.setOrderstatus(orderStatus);
		soCancelResponse.setResults(results);
		soCancelResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soCancelResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soCancelResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soCancelResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving SOCancelMapper.mapServiceOrder()");
		return soCancelResponse;
	}
}
