package com.newco.marketplace.utils.utility;

import java.util.ArrayList;
import java.util.List;

public class ServiceEmailUtilitySingleton
{
	private static ServiceEmailUtilitySingleton singltonInstance = new ServiceEmailUtilitySingleton();
	private List<Long> updateAlertTaskIds = new ArrayList<Long>();
	
	private ServiceEmailUtilitySingleton() {
		
	}

	public static ServiceEmailUtilitySingleton getInstance() {
	return singltonInstance;
	}
	
	public void add(Long data)
	{
		updateAlertTaskIds.add(data);
	}
	
	public List<Long> getAlertTaskIdList()
	{
		return updateAlertTaskIds;
	}
	
	public void clearAllAlertTaskIds()
	{
		updateAlertTaskIds.clear();
	}
}
