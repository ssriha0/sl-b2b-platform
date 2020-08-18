/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.accept.SOAcceptRejectReleaseResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;

/**
 * This class would act as a mapper class for mapping service order to
 * SOCreateResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAcceptMapper {
	private Logger logger = Logger.getLogger(SOAcceptMapper.class);

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
	public SOAcceptRejectReleaseResponse mapServiceOrder(ProcessResponse pResponse) throws DataException {
		logger.info("Entering SOAcceptMapper.mapServiceOrder()");
		SOAcceptRejectReleaseResponse soAcceptResponse = new SOAcceptRejectReleaseResponse();
		Results results = new Results();
				
		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
			results=Results.getSuccess(pResponse.getMessages().get(0));
			results = Results.getSuccess(ResultsCode.ACCEPT_RESULT_CODE
					.getMessage());
		} else {
			logger.info("Setting result and message as Failure when the " +
												"Accept operation is failure");
			results= Results.getError(pResponse.getMessages().get(0),
					ResultsCode.FAILURE.getCode());
		}
		soAcceptResponse.setResults(results);
		logger.info("Leaving SOAcceptMapper.mapServiceOrder()");
		return soAcceptResponse;
	}
	
	
	/**
	 * Create Process response with proper Error/Success code and message
	 * @param response : {@link OrderFulfillmentResponse}
	 * @return {@link ProcessResponse}
	 * */
	public ProcessResponse mapProcessResponse(OrderFulfillmentResponse response){
		return mapProcessResponse(response, false);
	}

	/**
	 * Create Process response with proper Error/Success code and message.
	 * @param isSOInEditMode True when SO in Edit Mode, Else it will be false
	 * @param response : {@link OrderFulfillmentResponse}
	 * @return {@link ProcessResponse}
	 * */
	public ProcessResponse mapProcessResponse(
			OrderFulfillmentResponse ofResponse, boolean isSOInEditMode) {
		ProcessResponse returnVal = new ProcessResponse();
		if(!ofResponse.isSignalAvailable() && isSOInEditMode){
			returnVal.setCode(ServiceConstants.USER_ERROR_RC);
			returnVal.setMessage(OrderConstants.ORDER_BEING_EDITED);
		}
		else if(!ofResponse.getErrors().isEmpty()){
			returnVal.setCode(ServiceConstants.USER_ERROR_RC);
			returnVal.setMessage(ofResponse.getErrorMessage());
		}else{
			returnVal.setCode(ServiceConstants.VALID_RC);
			returnVal.setMessage("Request Successfully executed.");
		}
		return returnVal;
	}
	
	/**
	 * Maps {@link Contact} to {@link SOContact}
	 * <br><strong> <i>Dependency : Code is duplicated in OfUtils</i></strong>
	 * @param src {@link Contact}
	 * @return {@link SOContact}
	 * */
	public SOContact mapContact(Contact src){
		SOContact returnVal = new SOContact();
		SOPhone phone = new SOPhone();
		/*Setup the primary object.*/
		returnVal.setBusinessName(src.getBusinessName());
		returnVal.setContactTypeId(ContactType.PRIMARY);
		returnVal.setEmail(src.getEmail());
		returnVal.setFirstName(src.getFirstName());
		returnVal.setLastName(src.getLastName());
		returnVal.setHonorific(src.getHonorific());
		returnVal.setMi(src.getMi());
		returnVal.setSuffix(src.getSuffix());
		returnVal.setEntityId(src.getEntityId());
		returnVal.setEntityType(com.servicelive.orderfulfillment.domain.type.EntityType.PROVIDER);

		/*Set Phone info:*/
		Set<SOPhone> phones = new HashSet<SOPhone>();
		if(StringUtils.isNotBlank( src.getPhoneNo())){
			phone.setPhoneClass(PhoneClassification.WORK);
			phone.setPhoneType(PhoneType.PRIMARY);
			phone.setPhoneNo(src.getPhoneNo());
			phones.add(phone);
		}
		if(StringUtils.isNotBlank(src.getFaxNo())){
			phone = new SOPhone();
			phone.setPhoneClass(PhoneClassification.FAX);
			phone.setPhoneType(PhoneType.FAX);
			phone.setPhoneNo(src.getFaxNo());
			phones.add(phone);
		}
		if(StringUtils.isNotBlank(src.getCellNo())){
			phone = new SOPhone();
			phone.setPhoneClass(PhoneClassification.MOBILE);
			phone.setPhoneType(PhoneType.PRIMARY);
			phone.setPhoneNo(src.getCellNo());
			phones.add(phone);
		}
		returnVal.setPhones(phones);
		returnVal.addContactLocation(ContactLocationType.PROVIDER);

		return returnVal;
	}
	
	/**
	 * 
	 * Maps Provider Contact to SOLocation.
	 * <br><strong> <i>Dependency : Code is duplicated in OfUtils</i></strong>
	 * @param src : {@link Contact} of vendor resource
	 * @return {@link SOLocation}
	 */
	public SOLocation mapLocation(Contact src) {
		SOLocation location = new SOLocation();
		location.setAptNumber(src.getAptNo());
		location.setCity(src.getCity());
		location.setStreet1(src.getStreet_1());
		location.setStreet2(src.getStreet_2());
		location.setState(src.getStateCd());
		location.setCountry(src.getCountry());
		location.setZip(src.getZip());
		location.setSoLocationTypeId(LocationType.PROVIDER);
		return location;
	}
}
