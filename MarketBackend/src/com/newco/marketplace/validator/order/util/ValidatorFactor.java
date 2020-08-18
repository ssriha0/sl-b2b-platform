package com.newco.marketplace.validator.order.util;

import com.newco.marketplace.dto.vo.serviceorder.RouteValidator;



public class ValidatorFactor {
	
private static final ValidatorFactor _factory = new ValidatorFactor();
	
	private ValidatorFactor()
	{}
	
	public static ValidatorFactor getInstance() 
	{
		if( _factory  == null)
		{
			return new ValidatorFactor();
		}
		return _factory;
	}
	
	public IValidator createValidator( Object test ) throws Exception {
		if(test.equals("routeValidator"))
			return (IValidator) new RouteValidator();
		
		return null;
	}
	

}
