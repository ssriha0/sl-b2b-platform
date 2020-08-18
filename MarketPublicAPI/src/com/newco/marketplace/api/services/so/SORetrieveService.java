/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.retrieve.SORetrieveRequest;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SORetrieveMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
/**
 * This class would act as a Servicer class for Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SORetrieveService {

	private XStreamUtility conversionUtility;
	private Logger logger = Logger.getLogger(SORetrieveService.class);
	private SORetrieveMapper retrieveMapper;
	private IServiceOrderBO serviceOrderBO;
	private SecurityProcess securityProcess;

	/**
	 * This method is for retrieving service order.
	 * 
	 * @param serviceOrderRequest String
	 * @param soId String
	 * @throws Exception Exception
	 * @return String
	 */
	public  HashMap<String, Object> dispatchRetrieveServiceRequest(String serviceOrderRequest,String soId)
			throws Exception {
		logger.info("Entering SORetrieveService.dispatchRetrieveServiceRequest()");
		String stringResponse = null;
		List<String> responseFilter=null;
		HashMap<String, Object> returnMap=new HashMap<String, Object>();
		SORetrieveRequest soRetrieveRequest =conversionUtility.
									getRetrieveRequestObject(serviceOrderRequest);
		if(null!=soRetrieveRequest.getResponseFilter()){
		responseFilter= soRetrieveRequest.getResponseFilter().getShowSection();
		}
		SecurityContext securityContext = securityProcess.getSecurityContext(
				soRetrieveRequest.getIdentification().getUsername(), soRetrieveRequest
						.getIdentification().getPassWord());
		if (securityContext == null) {
			logger.error("SecurityContext is null");
			throw new NullPointerException(CommonUtility.getMessage(
										PublicAPIConstant.SECURITY_ERROR_CODE));

		}
		ServiceOrder serviceOrder=serviceOrderBO.getServiceOrder(soId);

		if(null!=serviceOrder)
		{
			if ((serviceOrder.getBuyer() == null)
					|| (serviceOrder.getBuyer().getBuyerId().intValue() != securityContext
							.getCompanyId().intValue())) {
				returnMap.put(PublicAPIConstant.ERROR_KEY,CommonUtility
										.getMessage(PublicAPIConstant.RETRIEVE_ACCES_ERROR_CODE));
			} else {
			List<ServiceOrderNote> serviceOrderNoteList = serviceOrder
					.getSoNotes();
			List<ServiceOrderNote> updatedServciceOrderNoteList 
											= new ArrayList<ServiceOrderNote>();
			for (ServiceOrderNote serviceOrderNote : serviceOrderNoteList) {
				if (serviceOrderNote.getPrivateId() == 0)
					updatedServciceOrderNoteList.add(serviceOrderNote);
			}
			serviceOrder.setSoNotes(updatedServciceOrderNoteList);
			SORetrieveResponse retrievedServiceOrder = retrieveMapper
					.adaptRequest(serviceOrder, responseFilter);
			stringResponse = conversionUtility.getRetrieveResponseXML(
					retrievedServiceOrder, responseFilter);
			returnMap.put(PublicAPIConstant.SERVICEORDER_KEY, stringResponse);
			}
		}
		else
		{
			returnMap.put(PublicAPIConstant.ERROR_KEY, CommonUtility.getMessage(PublicAPIConstant.RETRIEVE_ERROR_CODE));
		}
		logger.info("Leaving SORetrieveService.dispatchRetrieveServiceRequest()");
		return returnMap;
	}


	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}


	public void setRetrieveMapper(SORetrieveMapper retrieveMapper) {
		this.retrieveMapper = retrieveMapper;
	}


	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	
}