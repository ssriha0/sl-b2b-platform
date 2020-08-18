package com.newco.marketplace.validator.order.util;

import java.util.HashMap;

public class ParamMap {
	
	private HashMap _map = new HashMap();
	
	public ParamMap()
	{
		
	}
	
	public void put( String key, Object type)
	{
		_map.put(key, type);
	}
	
	
	public Object get(String key) throws Exception 
	{
		return _map.get(key);
	}

}
