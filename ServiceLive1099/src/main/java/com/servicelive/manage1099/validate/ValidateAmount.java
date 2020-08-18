/**
 * 
 */
package com.servicelive.manage1099.validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.servicelive.manage1099.beans.AmountBean;
import com.servicelive.manage1099.beans.VendorAmountBean;
import com.servicelive.manage1099.constants.FileConstants;
import com.servicelive.manage1099.constants.ValidationConstants;
import com.servicelive.manage1099.file.FileHandler;
import com.servicelive.manage1099.util.CommonUtil;

/**
 * @author mjoshi1
 * 
 */
public class ValidateAmount {
	
	
	private static HashMap<Integer, String> missingVendorsInBOMap = null;
	private static HashMap<Integer, String> missingVendorsInSLMap = null;
	private static HashMap<Integer, AmountBean> mismatchAmountMap = null;
	private static HashMap<Integer, AmountBean> matchedAmountMap = null;
	

	public static void validateAmount(String vendorId, String amount) {

	}

	public static void validateVendorAmount(Map<Integer, VendorAmountBean> vendorMap) {

		// vendorSubMap HashMap that contains the 1099 vendor_id and amount from
		// Slive database that we write to file.
		HashMap<Integer, String> vendorSubMap = createSubMapForVendor(vendorMap);

		// providerBOMap HashMap that contains the 1099 vendor_id and amount
		// from BO file that we feed to the app.
		HashMap<Integer, String> vendorBOMap = new HashMap<Integer, String>();
		FileHandler.loadValidationFileToMap(vendorBOMap, ValidationConstants.VALIDATE_AGAINST_FILE);

		// Compare the amount and providers in both maps and populate missingVendorsInBOMap, 
		// missingVendorsInSLMap and mismatchAmountMap
		compareData(vendorSubMap, vendorBOMap);
		
		int slProviderCount=0;
		int boProviderCount=0;
		// Write the data to the text file as a report.
		if(vendorSubMap!=null){
			slProviderCount=vendorSubMap.size();
		}

		if(vendorBOMap!=null){
			boProviderCount=vendorBOMap.size();
		}
		

		
		generateReport(slProviderCount, boProviderCount);

	}
	
	
	
	

	/**
	 * Creates a subMap that only contains the vendor_id and amount.
	 * 
	 * @param vendorMap
	 * @return
	 */
	private static HashMap<Integer, String> createSubMapForVendor(Map<Integer, VendorAmountBean> vendorMap) {

		HashMap<Integer, String> vendorSubMap = new HashMap<Integer, String>();

		if (vendorMap != null) {

			Collection c = vendorMap.values();

			Iterator itr = c.iterator();

			while (itr.hasNext()) {
				VendorAmountBean vendorAmountBean = (VendorAmountBean) itr.next();
				String vendor_id = vendorAmountBean.getVendor_id();
				String amount = vendorAmountBean.getAmount();
				vendorSubMap.put(Integer.valueOf(vendor_id), amount);

			}

		}
		return vendorSubMap;
	}

	/**
	 * 
	 * @param vendorSubMap
	 * @param vendorBOMap
	 */
	static private void compareData(HashMap<Integer, String> vendorSubMap, HashMap<Integer, String> vendorBOMap) {
		missingVendorsInBOMap = new HashMap<Integer, String>();
		missingVendorsInSLMap = new HashMap<Integer, String>();
		mismatchAmountMap = new HashMap<Integer, AmountBean>();
		matchedAmountMap = new HashMap<Integer, AmountBean>();
		/*
		 * Check for missing vendor_ids in BO file.
		 */

		if (vendorSubMap != null) {

			Collection c = vendorSubMap.keySet();

			Iterator itr = c.iterator();

			while (itr.hasNext()) {
				Integer vendor = (Integer) itr.next();
				if (vendorBOMap != null) {
					
					if (!vendorBOMap.containsKey(vendor)) {
						missingVendorsInBOMap.put(vendor, vendorSubMap.get(vendor));
					}
					
				}
			}

		}
		

		/*
		 * Check for missing vendor_ids in our SL file.
		 */


		if (vendorBOMap != null) {

			Collection c = vendorBOMap.keySet();

			Iterator itr = c.iterator();

			while (itr.hasNext()) {
				Integer vendor = (Integer) itr.next();
				if (vendorSubMap != null) {
					if (!vendorSubMap.containsKey(vendor)) {
						missingVendorsInSLMap.put(vendor, vendorBOMap.get(vendor));
					}
				}
			}
			
		}
		
		
		
		/*
		 * Check for un-matched amount in BO and SL
		 */


		if (vendorBOMap != null) {

			Collection c = vendorBOMap.keySet();

			Iterator itr = c.iterator();

			while (itr.hasNext()) {
				Integer vendor = (Integer) itr.next();
				if (vendorSubMap != null) {
					if (vendorSubMap.containsKey(vendor)) {
						// If the amounts are not same add entry to hashmap for un-matched balances.
						AmountBean amountBean = new AmountBean();
						amountBean.setBoAmount(vendorBOMap.get(vendor));
						amountBean.setSlAmount(vendorSubMap.get(vendor));
						
						if(!isAmountSame(vendor, vendorSubMap, vendorBOMap)){
							
							mismatchAmountMap.put(vendor, amountBean);
						} else{
							
							matchedAmountMap.put(vendor, amountBean);
						}
						
					}
				}
			}
			
		}
		

	}
	
	
	/**
	 * 
	 * @param vendor
	 * @param vendorSubMap
	 * @param vendorBOMap
	 * @return
	 */
		
	 private static boolean isAmountSame(Integer vendor, HashMap<Integer, String> vendorSubMap, HashMap<Integer, String> vendorBOMap){
		 
		 boolean isAmountSame = false;
		 if(vendorBOMap!=null && vendorSubMap!=null){
			 int slAmount = CommonUtil.roundNumber(vendorSubMap.get(vendor));
			 int boAmount = CommonUtil.roundNumber(vendorBOMap.get(vendor));
			 
			// System.out.println("compare SL ="+slAmount+ " and BO ="+boAmount);
			 if(slAmount==boAmount){
				 isAmountSame = true; 
			 }
			 
		 }
		 
		 return isAmountSame;
		 
	 }
	
	
	 /**
	  * Take the hashMaps and generate a report file.
	  * @return
	  */
	public static boolean generateReport(int slProviderCount,int boProviderCount ) {
		return FileHandler.generateReportFileFromData(slProviderCount, boProviderCount , missingVendorsInBOMap, missingVendorsInSLMap, mismatchAmountMap, matchedAmountMap);
		
	}
	 
	 
	 
	
	
	

}
