package com.newco.marketplace.web.validator.sow;

import com.newco.marketplace.interfaces.OrderConstants;

public class PersistorFactory {

	private static PersistorFactory persistorFactory = new PersistorFactory();
	
	private PersistorFactory(){}
	
	public static PersistorFactory getInstance(){
		if(persistorFactory == null)
		{
			persistorFactory = new PersistorFactory();
		}
		return persistorFactory;
	}
	
	public IPersistor getPersistor(String type) {
		IPersistor myPersistor = null;
		if (type.equals(OrderConstants.DB_PERSIST)) {
			myPersistor = new DBPersistor();
		}

		return myPersistor;
	}
	
	
}
