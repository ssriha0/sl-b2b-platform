package com.newco.marketplace.web.constants;

import java.util.HashMap;

public class StateConstants {

	// Status Buyer
	public static HashMap<Integer, String> getStatusMapBuyer(){
		
		HashMap<Integer, String> statusMap = new HashMap();
		statusMap.put(150, "Accepted");
		statusMap.put(155 ,"Active");
		statusMap.put(120, "Cancelled");
		statusMap.put(180, "Closed");
		statusMap.put(160, "Completed");
		statusMap.put(100, "Draft");
		statusMap.put(130, "Expired");
		statusMap.put(110, "Posted");
		statusMap.put(170, "Problem");
		statusMap.put(125, "Voided");
		
		return statusMap;
	}
	
	// Status Provider
	public static HashMap<Integer, String> getStatusMapProvider(){
		
		HashMap<Integer, String> statusMap = new HashMap();
		statusMap.put(150, "Accepted");
		statusMap.put(155 ,"Active");
		statusMap.put(120, "Cancelled");
		statusMap.put(180, "Closed");
		statusMap.put(160, "Completed");
		statusMap.put(100, "Draft");
		statusMap.put(130, "Expired");
		statusMap.put(170, "Problem");
		statusMap.put(110, "Received");
		statusMap.put(125, "Voided");
		
		return statusMap;
	}	

	public static HashMap<Integer, String> getSubStatusMap(){
		HashMap<Integer,String> subStatusMap = new HashMap();
		subStatusMap.put(1, "Abandoned Work");
		subStatusMap.put(2, "Additional Part Required");
		subStatusMap.put(3, "Additional Work Required");
		subStatusMap.put(4, "Cancelled by Buyer");
		subStatusMap.put(5, "Cancelled by End User");
		subStatusMap.put(7, "End User No Show");
		subStatusMap.put(8, "Job Done");
		subStatusMap.put(9, "No Communication or Notes");
		subStatusMap.put(10, "Out of Scope/Scope Mismatch");
		subStatusMap.put(11, "Part Back Ordered");
		subStatusMap.put(12, "Part on Order");
		subStatusMap.put(13, "Part Received - Hold for Pickup");
		subStatusMap.put(14, "Part Received by End User");
		subStatusMap.put(15, "Part Received by Provider");
		subStatusMap.put(16, "Part Shipped");
		subStatusMap.put(17, "Property Damage");
		subStatusMap.put(18, "Provider No Show");
		subStatusMap.put(19, "Provider Not Qualified to Complete Work");
		subStatusMap.put(20, "Provider On-site");
		subStatusMap.put(21, "Rescheduled");
		subStatusMap.put(22, "Rescheduled by End User");
		subStatusMap.put(23, "Rescheduled by Provider");
		subStatusMap.put(24, "Rescheduled due to End User No Show");
		subStatusMap.put(25, "Rescheduled due to Provider No Show");
		subStatusMap.put(26, "Rework Needed");
		subStatusMap.put(27, "Site Not Ready");
		subStatusMap.put(28, "Maximum Price Increase Needed");
		subStatusMap.put(29, "Unprofessional Action / Behavior");
		subStatusMap.put(30, "Work Not Completed");
		subStatusMap.put(31, "Released by Provider");
		subStatusMap.put(32, "Locked for Buyer Edit");
		subStatusMap.put(33, "Need additional parts");
		subStatusMap.put(34, "Awaiting Payment");
		subStatusMap.put(35, "Documentation Required");	
		return subStatusMap;
	}
}
