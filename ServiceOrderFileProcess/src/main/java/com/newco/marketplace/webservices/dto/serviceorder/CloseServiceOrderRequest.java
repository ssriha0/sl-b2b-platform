/*
 * CloseServiceOrderRequest.java
 */
package com.newco.marketplace.webservices.dto.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author blars04
 *
 */
public class CloseServiceOrderRequest extends ABaseWebserviceRequest 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8877392739831432581L;
	private String serviceOrderID = null;
    private String message = null;
    double finalPartsPrice = 0.0;
    double finalLaborPrice = 0.0;    
    
	/* (non-Javadoc)
	 * @see com.sears.os.vo.request.ABaseServiceRequestVO#toString()
	 */
	@Override
	public String toString() 
    {
        return ToStringBuilder.reflectionToString(this);
        
	}

    
    /* *************************************************************************
     *                      Accessors
     * *************************************************************************
     */

   
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * @return the serviceOrderID
     */
    public String getServiceOrderID() {
        return serviceOrderID;
    }


    /**
     * @param serviceOrderID the serviceOrderID to set
     */
    public void setServiceOrderID(String serviceOrderID) {
        this.serviceOrderID = serviceOrderID;
    }


	public double getFinalPartsPrice() {
		return finalPartsPrice;
	}


	public void setFinalPartsPrice(double finalPartsPrice) {
		this.finalPartsPrice = finalPartsPrice;
	}


	public double getFinalLaborPrice() {
		return finalLaborPrice;
	}


	public void setFinalLaborPrice(double finalLaborPrice) {
		this.finalLaborPrice = finalLaborPrice;
	}

    
}//end class