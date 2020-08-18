package com.newco.marketplace.web.validator.sow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.newco.marketplace.interfaces.OrderConstants;

public class LookupHash {

	public static HashMap<String, String> getAllTabSequenceInfo(){
		//		This is commented out as i don't have a servlet context 
		//		HashMap<String, String> tabSequenceHash =  (HashMap<String, String>) ServletActionContext.getServletContext().getAttribute(OrderConstants.TAB_SEQUENCE_INFO_KEY);
		HashMap<String, String> tabSequenceHash =  null;
		if(tabSequenceHash == null ) {
			
			tabSequenceHash= new HashMap<String, String>();
			tabSequenceHash.put(OrderConstants.SOW_SOW_TAB,"1");
			tabSequenceHash.put(OrderConstants.SOW_ADDITIONAL_INFO_TAB,"2");
			tabSequenceHash.put(OrderConstants.SOW_PARTS_TAB,"3");
			tabSequenceHash.put(OrderConstants.SOW_PROVIDERS_TAB,"4");
			tabSequenceHash.put(OrderConstants.SOW_PRICING_TAB,"5");
			tabSequenceHash.put(OrderConstants.SOW_REVIEW_TAB, "6");
		} 
		else {
			return tabSequenceHash;
		}
		return tabSequenceHash;
	}
	
	public static String getMyTab(HashMap tabSequenceHash, String tabId){
		//		This is commented out as i don't have a servlet context 
		//		HashMap<String, String> tabSequenceHash =  (HashMap<String, String>) ServletActionContext.getServletContext().getAttribute(OrderConstants.TAB_SEQUENCE_INFO_KEY);
		String myKey = null;
		if (tabSequenceHash!= null && tabId!=null ) {
		Set mySet = tabSequenceHash.keySet();
		Iterator iterator = mySet.iterator();
		
		while (iterator.hasNext()){
			String strKey = (String)iterator.next();
			String strValue = (String)tabSequenceHash.get(strKey);
			if ( tabId.equals(strValue) ) {
				myKey = strKey;
				break;
			}
		}
	}
		return myKey;
	} 
	
	public static HashMap getTabActionNames(){
		HashMap<String,String> actionHash = new HashMap();
		actionHash= new HashMap<String, String>();
		actionHash.put(OrderConstants.SOW_SOW_TAB,"Scope of Work");
		actionHash.put(OrderConstants.SOW_ADDITIONAL_INFO_TAB,"Additional Info");
		actionHash.put(OrderConstants.SOW_PARTS_TAB,"Parts");
		actionHash.put(OrderConstants.SOW_PROVIDERS_TAB,"Providers");
		actionHash.put(OrderConstants.SOW_PRICING_TAB,"Pricing");
		actionHash.put(OrderConstants.SOW_REVIEW_TAB, "Review");	
		return actionHash;
	}
	public static String getMyActionName(String tabIdentifier){
		HashMap<String, String> map = getTabActionNames();
		String str = (String)map.get(tabIdentifier);
		return str;
	}
	
	
}
