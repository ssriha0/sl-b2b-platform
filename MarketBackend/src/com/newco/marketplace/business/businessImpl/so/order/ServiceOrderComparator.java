package com.newco.marketplace.business.businessImpl.so.order;

import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;

public class ServiceOrderComparator {
	
	public final static String SORT_ATTR_SOM_STATUS		 = "status";
	public final static String SORT_ATTR_SOM_SOID		 = "SoId";
	public final static String SORT_ATTR_SOM_SPENDLIMIT  = "SpendLimit";
	public final static String SORT_ATTR_SOM_SERVICEDATE = "ServiceDate";
	public final static String SORT_ATTR_SOM_APPTTIME	 = "TimeToAppointment";
	public final static String SORT_ATTR_SOM_ORDERAGE	 = "AgeOfOrder";
	public final static String SORT_ATTR_SOM_CREATEDDATE = "CreatedDate";
	
	public final static Map<String,Comparator<ServiceOrderSearchResultsVO>> soAscComparatorsMap = new HashMap<String,Comparator<ServiceOrderSearchResultsVO>>();
	
	static {
		soAscComparatorsMap.put(SORT_ATTR_SOM_STATUS, new ServiceOrderStatusAscComparator());
		soAscComparatorsMap.put(SORT_ATTR_SOM_SOID, new ServiceOrderIdAscComparator());
		soAscComparatorsMap.put(SORT_ATTR_SOM_SPENDLIMIT, new ServiceOrderSpendLimitAscComparator());
		soAscComparatorsMap.put(SORT_ATTR_SOM_SERVICEDATE, new ServiceOrderServiceDateAscComparator());
		soAscComparatorsMap.put(SORT_ATTR_SOM_APPTTIME, new ServiceOrderServiceDateAscComparator());
		soAscComparatorsMap.put(SORT_ATTR_SOM_ORDERAGE, new ServiceOrderCreatedDateAscComparator());
		soAscComparatorsMap.put(SORT_ATTR_SOM_CREATEDDATE, new ServiceOrderCreatedDateAscComparator());
	}
	
	public final static Map<String,Comparator<ServiceOrderSearchResultsVO>> soDescComparatorsMap = new HashMap<String,Comparator<ServiceOrderSearchResultsVO>>();
	
	static {
		soDescComparatorsMap.put(SORT_ATTR_SOM_STATUS, new ServiceOrderStatusDescComparator());
		soDescComparatorsMap.put(SORT_ATTR_SOM_SOID, new ServiceOrderIdDescComparator());
		soDescComparatorsMap.put(SORT_ATTR_SOM_SPENDLIMIT, new ServiceOrderSpendLimitDescComparator());
		soDescComparatorsMap.put(SORT_ATTR_SOM_SERVICEDATE, new ServiceOrderServiceDateDescComparator());
		soDescComparatorsMap.put(SORT_ATTR_SOM_APPTTIME, new ServiceOrderServiceDateDescComparator());
		soDescComparatorsMap.put(SORT_ATTR_SOM_ORDERAGE, new ServiceOrderCreatedDateDescComparator());
		soDescComparatorsMap.put(SORT_ATTR_SOM_CREATEDDATE, new ServiceOrderCreatedDateDescComparator());
	}
	
	public final static ServiceOrderResourceIdAscComparator resourceIdComparator = new ServiceOrderResourceIdAscComparator();
}

class ServiceOrderStatusAscComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so1.getSoStatus().compareTo(so2.getSoStatus());
	}
}
class ServiceOrderStatusDescComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so2.getSoStatus().compareTo(so1.getSoStatus());
	}
}
class ServiceOrderIdAscComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so1.getSoId().compareTo(so2.getSoId());
	}
}
class ServiceOrderIdDescComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so2.getSoId().compareTo(so1.getSoId());
	}
}
class ServiceOrderSpendLimitAscComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so1.getSpendLimit().compareTo(so2.getSpendLimit());
	}
}
class ServiceOrderSpendLimitDescComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so2.getSpendLimit().compareTo(so1.getSpendLimit());
	}
}
class ServiceOrderServiceDateAscComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		if(so1.getAppointStartDate() == null)
			return 1;
		if(so2.getAppointStartDate() == null)
			return -1;
		return so1.getAppointStartDate().compareTo(so2.getAppointStartDate());
	}
}
class ServiceOrderServiceDateDescComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		if(so1.getAppointStartDate() == null)
			return 1;
		if(so2.getAppointStartDate() == null)
			return -1;
		return so2.getAppointStartDate().compareTo(so1.getAppointStartDate());
	}
}
class ServiceOrderCreatedDateAscComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so1.getCreatedDate().compareTo(so2.getCreatedDate());
	}
}
class ServiceOrderCreatedDateDescComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		return so2.getCreatedDate().compareTo(so1.getCreatedDate());
	}
}
class ServiceOrderResourceIdAscComparator implements Comparator<ServiceOrderSearchResultsVO> {
	public int compare(ServiceOrderSearchResultsVO so1, ServiceOrderSearchResultsVO so2) {
		String so1ResourceId = so1.getRoutedResourceId();
		String so2ResourceId = so2.getRoutedResourceId();
		return so1ResourceId.compareTo(so2ResourceId);
	}
}
