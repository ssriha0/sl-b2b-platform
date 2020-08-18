/**
 * 
 */
package com.newco.marketplace.webservices.validation.so.close;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

/**
 * @author blars04
 *
 */
public class CloseValidator extends ABaseValidator implements OrderConstants {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4922530367603894051L;

	/**
     * 
     */
    public CloseValidator() {
        // TODO Auto-generated constructor stub
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
    
}//end class