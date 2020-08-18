package com.newco.marketplace.web.utils;

import java.util.Comparator;

import com.newco.marketplace.web.dto.ServiceOrderDTO;

public class ServiceOrderSortComparator implements Comparator<ServiceOrderDTO>{
	
	String sortBy;
	private String c_soNumber;
	public ServiceOrderSortComparator(String sortBy){
		this.sortBy = sortBy;
	}
	
	public int compare(ServiceOrderDTO one, ServiceOrderDTO two){
		
		if(sortBy.equalsIgnoreCase("c_status")){
		
			return one.getStatusString().compareTo(two.getStatusString());
			
		} else if (sortBy.equalsIgnoreCase("c_soNumber")){
			
			
			
			return one.getId().compareTo(two.getId());
				
		} else if (sortBy.equalsIgnoreCase("c_title")){
			
			return one.getTitle().compareTo(two.getTitle());
			
		} else if (sortBy.equalsIgnoreCase("c_serviceDate")){
			
			return one.getServiceOrderDateString().compareTo(two.getServiceOrderDateString());
			
		} else if (sortBy.equalsIgnoreCase("c_location")){
			
			return one.getLocationId().compareTo(two.getLocationId());
			
		} else {
			
			return one.getStatusString().compareTo(two.getStatusString());
	
		}
	}
}
