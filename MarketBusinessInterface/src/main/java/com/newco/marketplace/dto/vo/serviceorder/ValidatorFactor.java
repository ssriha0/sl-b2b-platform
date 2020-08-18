package com.newco.marketplace.dto.vo.serviceorder;

import com.newco.marketplace.webservices.base.IValidator;



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
	
	public IValidator createValidator( Object test ) throws Exception
	{
		if(test.equals("routeValidator"))
			return  new RouteValidator();
		return null;
	}
	

}
