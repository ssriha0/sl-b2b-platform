package com.newco.marketplace.api.utils.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartsTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Pricing;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Reference;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.References;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.EsapiUtility;
import com.newco.marketplace.vo.mobile.CustomReferenceVO;
import com.newco.marketplace.vo.mobile.SOPartLaborPriceReasonVO;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.type.CarrierType;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;

/**
 * This class would act as a Mapper class for Mapping Service Object to
 * SODetailsRetrieveResponse Object.
 * 
 * @author Infosys
 * @version 1.0
 */
public class UpdateSOCompletionMapper {
	private Logger logger = Logger.getLogger(UpdateSOCompletionMapper.class);
	private IMobileSOActionsBO mobileSOActionsBO;

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}


	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}


	/**
	 * @param references
	 * @param soId
	 * @param providerId
	 * @return
	 */
	public List<CustomReferenceVO> mapCustomReferences(References references,String soId, String userName) {
	
		List<CustomReferenceVO> customReferenceVOs = new ArrayList<CustomReferenceVO>();
		try {
			boolean mandatoryInd = false;
			
			// Get the references for  the service order from DB 
			List<BuyerReferenceVO>  buyerReferenceVOs = mobileSOActionsBO.getRequiredBuyerReferences(soId,mandatoryInd);
			if (buyerReferenceVOs != null
					&& !buyerReferenceVOs.isEmpty()) {
				for (Reference reference : references.getReference()) {
					if (null != reference) {
						for (BuyerReferenceVO buyerReferenceVO:buyerReferenceVOs) {
							if(null!=buyerReferenceVO){
								String dbRef = StringUtils.trim(buyerReferenceVO.getReferenceType());
								String ref = StringUtils.trim(reference.getReferenceName());

								if (null!= buyerReferenceVO && dbRef.equals(ref)) {
									// if (StringUtils.isNotBlank(reference
										// 	.getReferenceValue())) {
										CustomReferenceVO customReferenceVO = new CustomReferenceVO();
										customReferenceVO.setBuyerRefType(reference.getReferenceName());
										customReferenceVO.setBuyerRefTypeId(buyerReferenceVO.getBuyerRefTypeId());
										customReferenceVO.setBuyerRefValue(reference.getReferenceValue());
										customReferenceVO.setCreatedDate(new Date());
										customReferenceVO.setModifiedDate(new Date());
										customReferenceVO.setModifiedBy(userName);
										customReferenceVO.setSoId(soId);
										customReferenceVOs.add(customReferenceVO);
									//}
									break;
								}
							}
							
						}
					}
				}
			}
			
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Exception in fetching buyer reference"+e.getMessage());
		}
		
		return customReferenceVOs;

	}


	/**
	 * @param vendorContact
	 * @return
	 */
	public SOContact mapContact(Contact vendorContact) {
		SOContact returnVal = new SOContact();
		SOPhone phone = new SOPhone();
		/*Setup the primary object.*/
		returnVal.setBusinessName(vendorContact.getBusinessName());
		returnVal.setContactTypeId(ContactType.PRIMARY);
		returnVal.setEmail(vendorContact.getEmail());
		returnVal.setFirstName(vendorContact.getFirstName());
		returnVal.setLastName(vendorContact.getLastName());
		returnVal.setHonorific(vendorContact.getHonorific());
		returnVal.setMi(vendorContact.getMi());
		returnVal.setSuffix(vendorContact.getSuffix());
		returnVal.setEntityId(vendorContact.getEntityId());
		returnVal.setEntityType(com.servicelive.orderfulfillment.domain.type.EntityType.PROVIDER);

		/*Set Phone info:*/
		Set<SOPhone> phones = new HashSet<SOPhone>();
		if(StringUtils.isNotBlank( vendorContact.getPhoneNo())){
			phone.setPhoneClass(PhoneClassification.WORK);
			phone.setPhoneType(PhoneType.PRIMARY);
			phone.setPhoneNo(vendorContact.getPhoneNo());
			phones.add(phone);
		}
		if(StringUtils.isNotBlank(vendorContact.getFaxNo())){
			phone = new SOPhone();
			phone.setPhoneClass(PhoneClassification.FAX);
			phone.setPhoneType(PhoneType.FAX);
			phone.setPhoneNo(vendorContact.getFaxNo());
			phones.add(phone);
		}
		if(StringUtils.isNotBlank(vendorContact.getCellNo())){
			phone = new SOPhone();
			phone.setPhoneClass(PhoneClassification.MOBILE);
			phone.setPhoneType(PhoneType.PRIMARY);
			phone.setPhoneNo(vendorContact.getCellNo());
			phones.add(phone);
		}
		returnVal.setPhones(phones);
		returnVal.addContactLocation(ContactLocationType.PROVIDER);

		return returnVal;
	}


	/**
	 * @param vendorContact
	 * @return
	 */
	public SOLocation mapLocation(Contact vendorContact) {
		SOLocation location = new SOLocation();
		location.setAptNumber(vendorContact.getAptNo());
		location.setCity(vendorContact.getCity());
		location.setStreet1(vendorContact.getStreet_1());
		location.setStreet2(vendorContact.getStreet_2());
		location.setState(vendorContact.getStateCd());
		location.setCountry(vendorContact.getCountry());
		location.setZip(vendorContact.getZip());
		location.setSoLocationTypeId(LocationType.PROVIDER);
		return location;
	}
	
	/**
	 * @param securityContextForVendor
	 * @return
	 */
	public Identification createOFIdentityFromSecurityContext(
			SecurityContext securityContextForVendor) {
		int loginRoleId = securityContextForVendor.getRoles().getRoleId();
		switch (loginRoleId) {
		case OrderConstants.BUYER_ROLEID :
		case OrderConstants.SIMPLE_BUYER_ROLEID :
			return getIdentification(securityContextForVendor, EntityType.BUYER);

		case OrderConstants.PROVIDER_ROLEID :
			return getIdentification(securityContextForVendor, EntityType.PROVIDER);

		case OrderConstants.NEWCO_ADMIN_ROLEID :
			return getIdentification(securityContextForVendor, EntityType.SLADMIN);
		}
		return null;
	}

	

	/**
	 * @param securityContext
	 * @param entityType
	 * @return
	 */
	private static Identification getIdentification(SecurityContext securityContext, EntityType entityType){
		Identification id = new Identification();
		id.setEntityType(entityType);
		id.setCompanyId((long)securityContext.getCompanyId());
		id.setResourceId((long)securityContext.getVendBuyerResId());
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}


	/**
	 * @param pricing 
	 * @param soId 
	 * @param userName 
	 * @return
	 */
	public List<SOPartLaborPriceReasonVO> mapLaborPartPriceChangeReasons(Pricing pricing, String soId, String userName) {
		
		List<SOPartLaborPriceReasonVO> reasonVOs = new ArrayList<SOPartLaborPriceReasonVO>();
		
		SOPartLaborPriceReasonVO partReasonVO = new SOPartLaborPriceReasonVO();
		SOPartLaborPriceReasonVO laborReasonVO = new SOPartLaborPriceReasonVO();

		if(pricing.getPartChangeReasonCodeId()!=null){
			partReasonVO.setSoId(soId);
			partReasonVO.setPriceType(MPConstants.PARTS);
			partReasonVO.setReasonCodeId(pricing.getPartChangeReasonCodeId().toString());
			if(pricing.getPartChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON){
				//Added code for ESAPI encoding
				String reasoncomments = EsapiUtility.getEncodedString(pricing.getPartChangeReasonComments());
				partReasonVO.setReasonComments(reasoncomments);
			}
			else{
				partReasonVO.setReasonComments("");
			}
			partReasonVO.setCreatedDate(new Date());
			partReasonVO.setModifiedDate(new Date());
			partReasonVO.setModifiedBy(userName);
			reasonVOs.add(partReasonVO);
		}

		if(pricing.getLabourChangeReasonCodeId()!=null){
			laborReasonVO.setSoId(soId);
			laborReasonVO.setPriceType(MPConstants.LABOR);
			laborReasonVO.setReasonCodeId(pricing.getLabourChangeReasonCodeId().toString());
			if(pricing.getLabourChangeReasonCodeId().intValue() == MPConstants.OTHER_REASON){
				//Added code for ESAPI encoding
				String reasoncomments = EsapiUtility.getEncodedString(pricing.getLabourChangeReasonComments());
				laborReasonVO.setReasonComments(reasoncomments);
			}
			else{
				laborReasonVO.setReasonComments("");
			}
			laborReasonVO.setCreatedDate(new Date());
			laborReasonVO.setModifiedDate(new Date());
			laborReasonVO.setModifiedBy(userName);
			reasonVOs.add(laborReasonVO);
		}
		
		
		return reasonVOs;
	}

  
}