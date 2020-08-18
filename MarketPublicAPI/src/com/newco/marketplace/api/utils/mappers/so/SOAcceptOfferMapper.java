/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 22-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so;

import java.util.HashSet;
import java.util.Set;

import com.servicelive.orderfulfillment.domain.type.EntityType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.offer.CounterOfferResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOContactLocation;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
/**
 * This class would act as a Mapper class for mapping  ProcessResponse to
 * CounterOfferResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAcceptOfferMapper {
	private Logger logger = Logger.getLogger(SOAcceptOfferMapper.class);
	
	/**
	 * This method is for mapping ProcessResponse to
	 * CounterOfferResponse object
	 * 
	 * @param pResponse ProcessResponse
  	 * @throws DataException
	 * @return CounterOfferResponse
	 */
	public CounterOfferResponse mapSOAcceptOfferMapper(ProcessResponse pResponse)
			throws DataException {
		logger.info("Entering SOAcceptOfferMapper.mapSOAcceptOfferMapper()");
		CounterOfferResponse counterOfferResponse= new CounterOfferResponse();
		Results results = new Results();
		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
			results = Results.getSuccess(ResultsCode.COUNTER_OFFER_SUBMITTED.getMessage());
		} else {
			logger.info("SOAcceptOfferMapper.mapSOAcceptOfferMapper(): Accept Counter Offer Request Failed");
			results = Results.getError(pResponse.getMessages().get(0),
										ResultsCode.FAILURE.getCode());
		}
		counterOfferResponse.setResults(results);
		logger.info("Leaving SOAcceptOfferMapper.mapSOAcceptOfferMapper()");
		return counterOfferResponse;
	}
	
	public CounterOfferResponse mapSOAcceptOfferMapper(OrderFulfillmentResponse response) {
	    CounterOfferResponse returnVal = new CounterOfferResponse();
	    if(response.getErrors().isEmpty()){
	        returnVal.setResults(Results.getSuccess(ResultsCode.COUNTER_OFFER_SUBMITTED.getMessage()));
	    }else{
	        returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode()));
	    }
	    return returnVal;
	}

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
        returnVal.setEntityType(EntityType.PROVIDER);
        returnVal.setEntityId(src.getEntityId());
        
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
            phone.setPhoneType(PhoneType.PRIMARY);
            phone.setPhoneNo(src.getFaxNo());
            phones.add(phone);
        }
        returnVal.setPhones(phones);
        returnVal.addContactLocation(ContactLocationType.PROVIDER);

        return returnVal;
    }

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
