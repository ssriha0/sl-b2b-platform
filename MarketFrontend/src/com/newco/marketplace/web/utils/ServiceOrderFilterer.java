package com.newco.marketplace.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.newco.marketplace.web.constants.StateConstants;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderFilterExtractDTO;

public class ServiceOrderFilterer {

	private static ServiceOrderFilterExtractDTO filtersDto;

	public static ServiceOrderFilterExtractDTO getFiltersDto(
			ArrayList<ServiceOrderDTO> soResults, String role) {
		TreeMap<Integer, String> statusMapList = new TreeMap<Integer, String>();
		TreeMap<Integer, String> subStatusMapList = new TreeMap<Integer, String>();
		HashMap statusMap = new HashMap();
		System.out.println(role);
		if(role.equalsIgnoreCase("buyer")){
			statusMap = StateConstants.getStatusMapBuyer();
		}
		if(role.equalsIgnoreCase("provider")){
			statusMap = StateConstants.getStatusMapProvider();			
		}
		HashMap subStatusMap = StateConstants.getSubStatusMap();

		for (int i = 0; i < soResults.size() && !soResults.isEmpty(); i++) {
			if (soResults.get(i) != null) {
				try {
					if (soResults.get(i).getStatus() != null) {
						statusMapList.put(soResults.get(i).getStatus(),
								(String) statusMap.get(soResults.get(i)
										.getStatus()));
						// System.out.println(soResults.get(i).getStatusString());
					}
				} catch (Exception e) {
					System.out
							.println("status is null inside ServiceOrderFilterer at index "
									+ i);
				}
				try {
					if (soResults.get(i).getSubStatus() != null) {
						subStatusMapList.put(soResults.get(i).getSubStatus(),
								(String) subStatusMap.get(soResults.get(i)
										.getSubStatus()));
						// System.out.println(soResults.get(i).getSubStatusString());
					}
				} catch (Exception e) {
					System.out
							.println("substatus is null inside ServiceOrderFilterer at index "
									+ i);
				}
			}
		}
		List statusList = new ArrayList(statusMapList.values());
		List subStatusList = new ArrayList(subStatusMapList.values());
		filtersDto = new ServiceOrderFilterExtractDTO();
		filtersDto.setStatus(statusList);
		filtersDto.setSubStatus(subStatusList);
		return filtersDto;
	}

	
	public static ArrayList<ServiceOrderDTO> getFilteredData(ArrayList<ServiceOrderDTO> filteredList, ArrayList<ServiceOrderDTO> originalList, String filterBy){
		
;		if(filterBy.equalsIgnoreCase("all")){
			return originalList;
		}
		if(filteredList != null && originalList != null){
			for(int i = 0; i<originalList.size(); i++){
				
				if(originalList.get(i).getStatusString().equalsIgnoreCase(filterBy)){
					filteredList.add(originalList.get(i));
				}
				
			}			
		}
		return filteredList;
	}
	
	public static ArrayList<ServiceOrderDTO> getFilteredDataSub(ArrayList<ServiceOrderDTO> filteredList, ArrayList<ServiceOrderDTO> originalList, String filterBy){
		
		if(filterBy.equalsIgnoreCase("all")){
			return filteredList;
		}
		return null;
	}
}
