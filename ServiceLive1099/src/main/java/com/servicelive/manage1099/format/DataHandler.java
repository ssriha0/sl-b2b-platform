/**
 * 
 */
package com.servicelive.manage1099.format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.servicelive.manage1099.beans.VendorAmountBean;
import com.servicelive.manage1099.constants.FileConstants;
import com.servicelive.manage1099.constants.ValidationConstants;

/**
 * @author mjoshi1
 *
 */
public class DataHandler {
	
	

	
	/**
	 * extract from result set and store values in hashMap.
	 * @param vendorMap
	 * @param resultSet
	 * @throws SQLException
	 */
	
	public static void populateVendorDetailsMapFromResult(Map<Integer, VendorAmountBean> vendorMap, ResultSet resultSet, boolean creditDebitQuery)
			throws SQLException {
		
		
		if (resultSet != null) {

			while (resultSet.next()) {
				
				Integer vendor_id = resultSet.getInt("vendor_id");
				String amount = resultSet.getString("amount");
				String taxpayer_id_number_type = resultSet.getString("taxpayer_id_number_type");
				String ein_no = resultSet.getString("ein_no");
				String dba_name = resultSet.getString("dba_name");
				String business_name = resultSet.getString("business_name");
				Date last_payment_date = resultSet.getDate("last_payment_date");
				String city = resultSet.getString("city");
				String street1 = resultSet.getString("street1");
				String street2 = resultSet.getString("street2");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				String zip4 = resultSet.getString("zip4");

				// Check if we already have the vendor_id in the hashmap
				// if we already have that vendor_id in hashMap adjust the amount in class VendorAmountBean 
				
				if (vendorMap.containsKey(vendor_id)) {
					Float adjustedAmount=null;

					VendorAmountBean vendorObject = vendorMap.get(vendor_id);
					
					// Right now , its the same operation (i.e. addition) for both queries.
					if(creditDebitQuery){
						adjustedAmount = (Float.valueOf(vendorObject.getAmount()) + Float.valueOf(amount));
					}else{
						adjustedAmount = (Float.valueOf(vendorObject.getAmount()) - Float.valueOf(amount));
					}
					
					vendorObject.setAmount(String.valueOf(adjustedAmount));


				} else {

					VendorAmountBean vendorObject = new VendorAmountBean();
					vendorObject.setVendor_id(String.valueOf(vendor_id));
					vendorObject.setAmount(amount);
					vendorObject.setTaxpayer_id_number_type(taxpayer_id_number_type);
					vendorObject.setEin_no(ein_no);
					vendorObject.setDba_name(dba_name);
					vendorObject.setBusiness_name(business_name);
					vendorObject.setLast_payment_date(last_payment_date);
					vendorObject.setCity(city);
					vendorObject.setStreet1(street1);
					vendorObject.setStreet2(street2);
					vendorObject.setState(state);
					vendorObject.setZip(zip);
					vendorObject.setZip4(zip4);

					
					vendorMap.put(vendor_id, vendorObject);


				}

			}

		}
		

	}

	public static void populateVendorDetailsFromResult(
			VendorAmountBean vendorObject, ResultSet resultSet, boolean isAdd)
			throws SQLException {
		if (resultSet != null) {

			while (resultSet.next()) {
				Integer vendor_id = resultSet.getInt("vendor_id");
				String amount = resultSet.getString("amount");
				Date last_payment_date = resultSet.getDate("last_payment_date");

				if(vendor_id.equals(Integer.parseInt(vendorObject.getVendor_id()))){
					Float adjustedAmount = null;
					if (isAdd) {
						adjustedAmount = (Float.valueOf(vendorObject
								.getAmount()) + Float.valueOf(amount));
					} else {
						adjustedAmount = (Float.valueOf(vendorObject
								.getAmount()) - Float.valueOf(amount));
					}
					vendorObject.setAmount(String.valueOf(adjustedAmount));
					//Setting the greater value of last payment date
					if(null == vendorObject.getLast_payment_date() || last_payment_date.after(vendorObject.getLast_payment_date())){
						vendorObject.setLast_payment_date(last_payment_date);
					}
				}
			}
		}
	}
	
	/**
	 * Extract vendor Id and Amount value from the line read.
	 * @param line The line read from the validation file.
	 * @return return the String array that contains vendorId on first index and 
	 * amount on second index.
	 */
	
	public static String[] getVendorIdAndAmount(String line) {
	
		String[] vendorIdAmount = new String[2];
		if (line != null) {
	
			StringTokenizer st = new StringTokenizer(line, ",");
	
			int i = 1;
			// Assuming 1st token is vendor id
			while (st.hasMoreElements()) {
	
				if (i == ValidationConstants.VENDOR_ID_INDEX_IN_FILE) {
					String vendorId = st.nextToken();
					if (vendorId != null) {
						vendorIdAmount[0] = vendorId.trim();
					}
				} else if (i == ValidationConstants.AMOUNT_INDEX_IN_FILE) {
	
					String amount = st.nextToken();
				    amount = FormatData.formatAmountValue(amount);
					if (amount != null) {
						vendorIdAmount[1] = amount.trim();
					}
				} else {
					st.nextToken();
				}
	
				i++;
			}
	
		}
	
		return vendorIdAmount;
	}

	public static void populateVendorW9HistoryFromResult(
			Map<Integer, ArrayList<VendorAmountBean>> vendorW9HistoryMap, String endDate, ResultSet resultSet) throws SQLException {
		  SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (resultSet != null) {
			int count = 0;
			while (resultSet.next()) {
				count++;
					//Getting values from the resultset
					Date last_payment_date = resultSet.getDate("modified_date");
					Integer vendor_id = resultSet.getInt("vendor_id");
					String taxpayer_id_number_type = resultSet.getString("taxpayer_id_number_type");
					String ein_no = resultSet.getString("ein_no");
					String dba_name = resultSet.getString("dba_name");
					String business_name = resultSet.getString("business_name");
					String city = resultSet.getString("city");
					String street1 = resultSet.getString("street1");
					String street2 = resultSet.getString("street2");
					String state = resultSet.getString("state");
					String zip = resultSet.getString("zip");
					String zip4 = resultSet.getString("zip4");
					
					//Setting the values in the VendorAmountBean
					VendorAmountBean vendorObject = new VendorAmountBean();
					vendorObject.setVendor_id(String.valueOf(vendor_id));
					vendorObject.setTaxpayer_id_number_type(taxpayer_id_number_type);
					//Hard coding to handle a data issue
					/*if(vendorObject.getVendor_id().equals("24690") && null == vendorObject.getTaxpayer_id_number_type()){
						vendorObject.setTaxpayer_id_number_type("1");
					}*/					
					vendorObject.setEin_no(ein_no);
					vendorObject.setDba_name(dba_name);
					vendorObject.setBusiness_name(business_name);
					vendorObject.setLast_payment_date(last_payment_date);
					vendorObject.setAmount("0");
					vendorObject.setCity(city);
					vendorObject.setStreet1(street1);
					vendorObject.setStreet2(street2);
					vendorObject.setState(state);
					vendorObject.setZip(zip);
					vendorObject.setZip4(zip4);

				// Check if we already have the vendor_id in the hashmap
				// if exists check if the previous w9 entry is of this year then only add to the list
				// Intention is to have only this year values and one value prior to that if any
				if (vendorW9HistoryMap.containsKey(vendor_id)) {
					List<VendorAmountBean> vendorW9HistoryList = vendorW9HistoryMap.get(vendor_id);
					VendorAmountBean prevVendorObject = vendorW9HistoryList.get(vendorW9HistoryList.size()-1);
					try {
						if(simpleDateformat.format(prevVendorObject.getLast_payment_date()).equals(FileConstants.W9_ENTRY_YEAR) && vendorObject.getLast_payment_date().before(sdf.parse(endDate))){
							vendorW9HistoryList.add(vendorObject);
						}
					} catch (/*Parse*/Exception e) {
						e.printStackTrace();
					}
				} else {// if vendor id does not exist then create a new list of vendor beans and add it to the list
					try {
						if(simpleDateformat.format(vendorObject.getLast_payment_date()).equals(FileConstants.W9_ENTRY_YEAR) && vendorObject.getLast_payment_date().before(sdf.parse(endDate))){
							List<VendorAmountBean> vendorW9HistoryList = new ArrayList<VendorAmountBean>();
							vendorW9HistoryList.add(vendorObject);
							
							vendorW9HistoryMap.put(vendor_id, (ArrayList<VendorAmountBean>) vendorW9HistoryList);
						}
					} catch (/*Parse*/Exception e) {
						e.printStackTrace();
					}
				}

			}

		}
	}
	
	
	

	
	

}
