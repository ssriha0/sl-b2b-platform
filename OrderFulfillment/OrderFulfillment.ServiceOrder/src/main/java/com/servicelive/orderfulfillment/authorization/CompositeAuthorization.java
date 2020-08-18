package com.servicelive.orderfulfillment.authorization;

import java.util.Collections;
import java.util.List;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

/**
 * User: Mustafa Motiwala
 * Date: Apr 27, 2010
 * Time: 4:28:24 PM
 */
public class CompositeAuthorization extends BaseServiceOrderAuthorization{
    private List<BaseServiceOrderAuthorization> authorizations = Collections.emptyList();

    @Override
    protected void evaluateAuthorization(Identification id, ServiceOrder so) throws ServiceOrderException {
        boolean authorized = false;
        ServiceOrderException soAuthException = null;
        for(BaseServiceOrderAuthorization authorization: authorizations){
            try{
                authorization.evaluateAuthorization(id, so);
                authorized = true;
                break;
            }catch(ServiceOrderException ignored){
            	if (soAuthException == null) {
            		soAuthException = new ServiceOrderException("Unauthorized access");
        }
            	soAuthException.addError(">> " + ignored.getMessage());
            	continue; //ignore exception as we want to test the whole list.
            }catch(IllegalArgumentException ignored){
            	if (soAuthException == null) {
            		soAuthException = new ServiceOrderException("Unauthorized access");
    }
            	soAuthException.addError(">> " + ignored.getMessage());
            	continue; //ignore exception as we want to test the whole list.
            }
        }
        if(!authorized && soAuthException != null) throw soAuthException;
    }

    public void setAuthorizations(List<BaseServiceOrderAuthorization> authorizations) {
        this.authorizations = authorizations;
    }
}
