package com.newco.marketplace.api.mobile.utils.mappers.v2_0;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
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
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;

public class OFMapper {

	public static Identification createBuyerIdentFromSecCtx(SecurityContext securityContext) {
		Identification id = new Identification();
		id.setEntityType(EntityType.BUYER);
		id.setCompanyId((long)securityContext.getCompanyId());
		id.setResourceId((long)securityContext.getVendBuyerResId());
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}
	
	public static Identification createProviderIdentFromSecCtx(SecurityContext securityContext) {
        Identification id = new Identification();
        id.setEntityType(EntityType.PROVIDER);
        id.setCompanyId((long)securityContext.getCompanyId());
        id.setResourceId((long)securityContext.getVendBuyerResId());
        id.setUsername(securityContext.getUsername());
        id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
        id.setRoleId(securityContext.getRoleId());
        return id;
    }
	
	public static Identification createBuyerIdentFromSecCtxResource(SecurityContext securityContext,BuyerResource buyerResource,Contact contact) {
		Identification id = new Identification();
		id.setEntityType(EntityType.BUYER);
		id.setCompanyId((long)securityContext.getCompanyId());
		if(null!=buyerResource.getResourceId()){
			id.setResourceId((long)buyerResource.getResourceId());
		}else{
			 id.setResourceId((long)securityContext.getVendBuyerResId());
		}
		if(StringUtils.isNotBlank(buyerResource.getUserName())){
			id.setUsername(buyerResource.getUserName());
		}else{
			 id.setUsername(securityContext.getUsername());
		}
		if(null!=contact && null!= contact.getFirstName() && null!=contact.getLastName()){
			id.setFullname(contact.getFirstName()+" "+contact.getLastName());
		}else{
			id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		}
		if(null!= buyerResource.getCompanyRoleId()){
			id.setRoleId(buyerResource.getCompanyRoleId());
		}else{
			 id.setRoleId(securityContext.getRoleId());
		}
		return id;
	}
	
	
	public static ProcessResponse mapProcessResponse(OrderFulfillmentResponse response){
		return mapProcessResponse(response, false);
	}

	public static ProcessResponse mapProcessResponse(
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
	
	public static SOContact mapContact(Contact src){
		SOContact returnVal = new SOContact();
		SOPhone phone = new SOPhone();
		//Setup the primary object.
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

		//Set Phone info:
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
	
	public static SOLocation mapLocation(Contact src) {
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
