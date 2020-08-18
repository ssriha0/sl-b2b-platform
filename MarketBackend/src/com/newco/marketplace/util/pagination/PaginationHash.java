package com.newco.marketplace.util.pagination;

import java.util.HashMap;

import com.newco.marketplace.interfaces.OrderConstants;

public class PaginationHash extends HashMap{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2660268796237055019L;
	private static PaginationHash queryCollection;
	private PaginationHash(){
		
	}
	
	public static PaginationHash getInstance()
	{   
		if (queryCollection != null) 
		{
			return queryCollection;
		}
		else 
		{
			queryCollection = new PaginationHash();
			queryCollection.init();
			return queryCollection;
		}
		
	}
	public static HashMap<String,String> getQueryCollection() {
		return queryCollection;
	}

/*
 * This method holds all the business Keys and Query Names in the hash for pagination
 * The left part is the key which holds the name that will get referred by the Action layer
 * The right part is the value which holds the name of the database iBatis query
 * 
 */	
	private void init(){
		queryCollection.put(OrderConstants.SOM_COUNT_FOR_BUYER,OrderConstants.SOM_COUNT_FOR_BUYER_QUERY);
		queryCollection.put(OrderConstants.SOM_COUNT_FOR_PROVIDER,OrderConstants.SOM_COUNT_FOR_PROVIDER_QUERY);
		
	}
	
public static void main(String h[]){
	PaginationHash s = PaginationHash.getInstance();
	System.out.println(s.get("SOM_COUNT_FOR_BUYER"));
	
}
	
}
