package com.newco.marketplace.validator.order;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.CreditCardValidatonUtil;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public class ServiceOrderValidator extends ABaseValidator implements OrderConstants{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7789877088453194773L;

	public ValidatorResponse validate(String soID,Integer buyerID)
    {
        ValidatorResponse response = new ValidatorResponse(VALID_RC, VALID_RC);

        //service order number is required
        if (StringUtils.isBlank(soID))
        {
            this.setError(response, SERVICE_ORDER_NUMBER_REQUIRED);
        }
                
        //buyerID is required
        if (buyerID == null)
        {
            this.setError(response, BUYER_ID_REQUIRED);
        }
        
        return response;
        
    }//end method
	
	//For Complete
	public ValidatorResponse validateCompleteSO(String soId, String resolutionDescr, int providerId, double partsFinalPrice, double laborFinalPrice){

		ValidatorResponse response = new ValidatorResponse(VALID_RC, VALID_RC);

        //service order number is required
        if (StringUtils.isBlank(soId))
        {
            this.setError(response, SERVICE_ORDER_NUMBER_REQUIRED);
        }
                
        //buyerID is required
        if (providerId == 0)
        {
            this.setError(response, PROVIDER_ID_REQUIRED);
        }
        
        //Resolution Description is required
        if (resolutionDescr.equalsIgnoreCase(""))
        {
            this.setError(response, RESOLUTION_DESCR_REQUIRED);
        }
      //SLT-1237
        if (resolutionDescr != null && resolutionDescr.length()!=0 && CreditCardValidatonUtil.validateCCNumbers(resolutionDescr)) {
        	
        	this.setError(response, RESOLUTION_DESCR_CREDITCARD_VALIDATION_MSG);
        }
        
        return response;
	}

	
	public ValidatorResponse validate(Integer resourceId, Integer roleId, ServiceOrderNote note) {
		
		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
		
		if(note == null) {
			setError(resp, NOTE_OBJ_REQUIRED);
			return resp;
		}
		
		// check some required fields
		if (isEmpty(resourceId)){
			if(OrderConstants.BUYER_ROLEID == roleId.intValue()){
				setError(resp, BUYER_ID_REQUIRED);
			}
			else if(OrderConstants.PROVIDER_ROLEID == roleId.intValue()){
				setError(resp, PROVIDER_ID_REQUIRED);
			} 
		}
		
		//if (isEmpty(note.getNoteTypeId()))
			//setError(resp, NOTE_TYPE_REQUIRED);
		
		// TODO : Check if the empty notes are allowed to be stored in the database.
		if(!note.isEmptyNoteAllowed()){
			if(StringUtils.isEmpty(note.getNote()))
				setError(resp, NOTE_TEXT_REQUIRED);
		}
		/*if(StringUtils.isEmpty(note.getNote()))
			setError(resp, NOTE_TEXT_REQUIRED);*/
				
		if(StringUtils.isEmpty(note.getSoId()))
			setError(resp, SERVICE_ORDER_NUMBER_REQUIRED);

		return resp;
	}
	
	public ValidatorResponse validate(String soID, String userName, Integer buyerID)
    {
        ValidatorResponse response = new ValidatorResponse(VALID_RC, VALID_RC);

        //service order number is required
        if (StringUtils.isBlank(soID))
        {
            this.setError(response, SERVICE_ORDER_NUMBER_REQUIRED);
        }
        
        //userName is required
        if (StringUtils.isBlank(userName))
        {
            this.setError(response, USER_NAME_REQUIRED);
        }
        
        //buyerID is required
        if (buyerID == null)
        {
            this.setError(response, BUYER_ID_REQUIRED);
        }
        
        return response;
        
    }//end method
	
	//public ValidatorResponse validate(String soID,Integer resourceID,String conditionalDate1,String conditionalStartTime,String conditionalEndTime,String conditionalExpirationDate,Double incrSpendLimit)
    //{
	public ValidatorResponse validate(String serviceOrderID,Integer resourceID, Integer vendorID,Timestamp conditionalDate1,String conditionalStartTime,String conditionalEndTime,Timestamp conditionalExpirationDate,Double incrSpendLimit)
    {		
        ValidatorResponse response = new ValidatorResponse(VALID_RC, VALID_RC);
     
        if (StringUtils.isBlank(serviceOrderID))
        {
            this.setError(response, SERVICE_ORDER_NUMBER_REQUIRED);
            return response;
        }
        
        if (resourceID == null)
        {
            this.setError(response, RESOURCE_ID_REQUIRED);
            return response;
        }
        
        if (vendorID == null)
        {
            this.setError(response, VENDOR_ID_REQUIRED);
            return response;
        }
        
        if ((conditionalDate1 == null || StringUtils.isEmpty(conditionalDate1.toString())) && incrSpendLimit == null)
        {
        	this.setError(response, SO_CONDITIONAL_START_DATE_OR_SPEND_LIMIT_REQUIRED);
        	 return response;
        }
        
        if (StringUtils.isEmpty(conditionalStartTime) && incrSpendLimit == null && (conditionalDate1 == null || StringUtils.isEmpty(conditionalDate1.toString())))
        {
        	this.setError(response, SO_CONDITIONAL_START_TIME_REQUIRED);
        	 return response;
        }
        
        if (StringUtils.isEmpty(conditionalEndTime) && incrSpendLimit == null && (conditionalDate1 == null || StringUtils.isEmpty(conditionalDate1.toString())))
        {
        	this.setError(response, SO_CONDITIONAL_END_TIME_REQUIRED);
        	 return response;
        }
        
        if (conditionalExpirationDate == null || StringUtils.isEmpty(conditionalExpirationDate.toString()))
        {
        	this.setError(response, SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED);
        	 return response;
        }
        return response;
    }
	
	/*-----------------------------------------------------------------
     * This is method will be used to validate CANCEL SO
     * @param 		soId - ServiceOrder Id
     * @param		buyerId - Buyer Id
     * @param		cancelComment - Cancellation comment in Accepted State
     * @param		cancelAmt - Cancellation amount in Active State, 0 in case of Accepted State
     * @returns 	ValidatorResponse
     *-----------------------------------------------------------------*/
	public ValidatorResponse validateCancelInAccepted(String soId, int buyerId, String cancelComment)
    {
        ValidatorResponse response = new ValidatorResponse(VALID_RC, VALID_RC);

        //service order number is required
        if (StringUtils.isBlank(soId))
        {
        	response.setCode(SYSTEM_ERROR_RC);
        	response.getMessages().add(SERVICE_ORDER_NUMBER_REQUIRED);
        }
        //buyerId is required
        if (buyerId == 0)
        {
        	response.setCode(SYSTEM_ERROR_RC);
        	response.getMessages().add(BUYER_ID_REQUIRED);
        }
        //Comments required in Accepted State
        if (cancelComment.equalsIgnoreCase("")){
        	this.setError(response, CANCEL_COMMENT_REQUIRED);
        }
        return response;
        
    }//end method
	
	/*-----------------------------------------------------------------
     * This is method will be used to validate CANCEL SO
     * @param 		soId - ServiceOrder Id
     * @param		buyerId - Buyer Id
     * @param		cancelComment - Cancellation comment in Accepted State
     * @param		cancelAmt - Cancellation amount in Active State, 0 in case of Accepted State
     * @returns 	ValidatorResponse
     *-----------------------------------------------------------------*/
	public ValidatorResponse validateCancelInActive(String soId, int buyerId, double cancelAmt)
    {
        ValidatorResponse response = new ValidatorResponse(VALID_RC, VALID_RC);

        //service order number is required
        if (StringUtils.isBlank(soId))
        {
        	response.setCode(SYSTEM_ERROR_RC);
        	response.getMessages().add(SERVICE_ORDER_NUMBER_REQUIRED);
        }
        //buyerId is required
        if (buyerId == 0)
        {
        	response.setCode(SYSTEM_ERROR_RC);
        	response.getMessages().add(BUYER_ID_REQUIRED);
        }
        //Amount required in Active State
        if (cancelAmt < 0.0){
        	response.setCode(SYSTEM_ERROR_RC);
        	this.setError(response, CANCEL_AMT_REQUIRED);
        }
        return response;
        
    }//end method
	
	/*-----------------------------------------------------------------
     * This is method will be used to validate VOID SO
     * @param 		soId - ServiceOrder Id
     * @param		buyerId - Buyer Id
     * @returns 	ValidatorResponse
     *-----------------------------------------------------------------*/
	public ValidatorResponse validateVoid(String soId, int buyerId)
    {
        ValidatorResponse response = new ValidatorResponse(VALID_RC, VALID_RC);

        //service order number is required
        if (StringUtils.isBlank(soId))
        {
            this.setError(response, SERVICE_ORDER_NUMBER_REQUIRED);
        }
        //buyerId is required
        if (buyerId == 0)
        {
            this.setError(response, BUYER_ID_REQUIRED);
        }
        return response;
     }//end method
	
}
