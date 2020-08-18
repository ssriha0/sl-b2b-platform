package com.newco.marketplace.web.utils;

import java.util.Comparator;

import com.newco.marketplace.web.dto.ServiceOrderDTO;

public class ServiceOrderReverseSortComparator implements Comparator<ServiceOrderDTO>{
	
	String sortBy;
	public ServiceOrderReverseSortComparator(String sortBy){
		this.sortBy = sortBy;
	}
	
	public int compare(ServiceOrderDTO one, ServiceOrderDTO two){
		
		if(sortBy.equalsIgnoreCase("c_status")){
		
			return two.getStatusString().compareTo(one.getStatusString());
			
		} else if (sortBy.equalsIgnoreCase("c_soNumber")){
			
			return two.getId().compareTo(one.getId());
			
		} else if (sortBy.equalsIgnoreCase("c_title")){
			
			return two.getTitle().compareTo(one.getTitle());
			
		} else if (sortBy.equalsIgnoreCase("c_serviceDate")){
			
			return two.getServiceOrderDateString().compareTo(one.getServiceOrderDateString());
			
		} else if (sortBy.equalsIgnoreCase("c_location")){
			
			return two.getLocationId().compareTo(one.getLocationId());
			
		} else {
			
			return two.getStatusString().compareTo(one.getStatusString());
	
		}
	}
}
