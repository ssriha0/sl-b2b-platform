/*
 * ServiceOrderResponse.java
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.response.ABaseServiceResponseVO;

/**
 * @author blars04
 *
 */
public class ServiceOrderResponse extends ABaseServiceResponseVO 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9085509238039946989L;
	private boolean error = false;
	private String orderId = null;

    
    @Override
	public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
        
    }//end method toString()
    
	
	/* *************************************************************************
	 * 							Accessors
	 * 
	 * *************************************************************************
	 */
	public String getOrderId()
	{
		return this.orderId;
		
	}//end method getOrderId()
	
	public boolean isError()
	{
		return this.error;
		
	}//end method isError()
	
	
	public void setError(boolean error)
	{
		this.error = error;
		
	}//end method setError()
	
	public void setOrderId(String id)
	{
		this.orderId = id;
		
	}//end method setOrderId()
	
	
}//end class