/**
 * 
 */
package com.servicelive.manage1099.format;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import name.benjaminjwhite.zdecimal.DataException;
import name.benjaminjwhite.zdecimal.DecimalOverflowException;
import name.benjaminjwhite.zdecimal.PackDec;

import com.servicelive.manage1099.beans.VendorAmountBean;
import com.servicelive.manage1099.constants.DatumConstants;
import com.servicelive.manage1099.constants.ValidationConstants;
import com.servicelive.manage1099.encode.Cryptography;
import com.servicelive.manage1099.encode.WriteEBCDIC;
import com.servicelive.manage1099.util.CommonUtil;

/**
 * @author mjoshi1
 * 
 */
public class FormatData extends FormatFields {
	
	private  String vendor_id = "";
	private  String amount = "";
	private  String taxpayer_id_number_type = "";
	private  String ein_no = "";
	private  String dba_name = "";
	private  String business_name = "";
	private  Date last_payment_date = null;
	private  String city = "";
	private  String street1 = "";
	private  String street2 = "";
	private  String state = "";
	private  String zip = "";
	private  String zip4 = ""; 
	
	
	public String formatRecord() {

		return "";
	}


	/**
	 *  This method formats all the fields that are pulled from database in required format.
	 *  The values after formatting are placed in a Map and returned.
	 *
	 * @param startIndexKey
	 * @param vendorMap
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public Map<Integer, List> formatResultMap(Map<Integer, VendorAmountBean> vendorMap, Map<Integer, ArrayList<VendorAmountBean>> vendorW9HistoryMap) throws Exception {

		
		Map<Integer, List> resultMapArray = new HashMap();
		
		// The Cryptography constructor should be outside teh loop,
		// the constructor has db call. 
		Cryptography cryptography = new Cryptography();

		if (vendorMap != null) {

			int index = 0;
			
			final Collection c = vendorMap.values();
			
			final Iterator itr = c.iterator();

			while (itr.hasNext()) {
								
				VendorAmountBean vendorAmountBean = (VendorAmountBean)itr.next();
				if(vendorW9HistoryMap.containsKey(Integer.parseInt(vendorAmountBean.getVendor_id()))){
					Collections.reverse(vendorW9HistoryMap.get(Integer.parseInt(vendorAmountBean.getVendor_id())));
					
					for(VendorAmountBean vendorHistBean : vendorW9HistoryMap.get(Integer.parseInt(vendorAmountBean.getVendor_id()))){
						if(Float.valueOf(vendorHistBean.getAmount()) > 0){
							final List<byte[]> resultList = new ArrayList<byte[]>();

							 // Extract all the values from the vendor History Bean.
							  extractValuesFromVendorBean(vendorHistBean, vendorAmountBean);
							
							// Please don't change the order in which the resultList is adding the elements (order in which methods
							  // are being called to populate the resultlist).
							// The order has to be maintained because the data is written in file with same order.
							
							// Adds to the list the first four static values.
							addInitialStaticValuesToList(resultList);
							// Adds the middle values to result list.
							addToResultList(resultList);
							// Add more values to the list.
							addMoreToResultList(cryptography,resultList);
							// Add last remaining values to the list.
							addTailValuesToResultList(resultList);

							// Add the resultList to the next index in the Map.
							resultMapArray.put(Integer.valueOf(index), resultList);

							index++;
						}
					}
					
				}else{
					final List<byte[]> resultList = new ArrayList<byte[]>();

					 // Extract all the values from the vendor Bean.
					  extractValuesFromVendorBean(vendorAmountBean, null);
					
					// Please don't change the order in which the resultList is adding the elements (order in which methods
					  // are being called to populate the resultlist).
					// The order has to be maintained because the data is written in file with same order.
					
					// Adds to the list the first four static values.
					addInitialStaticValuesToList(resultList);
					// Adds the middle values to result list.
					addToResultList(resultList);
					// Add more values to the list.
					addMoreToResultList(cryptography,resultList);
					// Add last remaining values to the list.
					addTailValuesToResultList(resultList);

					// Add the resultList to the next index in the Map.
					resultMapArray.put(Integer.valueOf(index), resultList);

					index++;
				}
			}
			
		}

		return resultMapArray;
	}


	/**
	 * @param resultList
	 */
	private void addTailValuesToResultList(final List<byte[]> resultList) {
		resultList.add(addBlankPadding(DatumConstants.OACCOUNTINGCENTER, 6).getBytes());

		resultList.add(getNumberInEBCDIC(0));

		resultList.add(getNumberInEBCDIC(CommonUtil.roundNumber(amount)));

		resultList.add(getNumberInEBCDIC(0));

		resultList.add(getNumberInEBCDIC(0));

		resultList.add(getNumberInEBCDIC(0));

		resultList.add(getNumberInEBCDIC(0));

		resultList.add(addBlankPadding(DatumConstants.SINGLEBLANK, 29).getBytes());
	}


	/**
	 * @param cryptography
	 * @param resultList
	 */
	private void addMoreToResultList(Cryptography cryptography,
			final List<byte[]> resultList) {
	
		String taxpayer_id_number_type_formatted = taxpayer_id_number_type;  
			if (taxpayer_id_number_type != null && taxpayer_id_number_type.length()>0) {

			if(taxpayer_id_number_type.trim().equals("1")){
				taxpayer_id_number_type_formatted=DatumConstants.OTININD;
			}else{
				taxpayer_id_number_type_formatted=DatumConstants.OSSNIND;
			}
			
			resultList.add(addBlankPadding(taxpayer_id_number_type_formatted, 1).getBytes());
		} else {
			System.out.println("WARNING : Tin/Ssn INDICATOR MISSING - Tin/Ssn indicator missing for vendor="+vendor_id);	
			resultList.add(DatumConstants.SINGLEBLANK.getBytes());
		}

		
		// Decrypt the value of ein no. before adding it to the resultList
		String decryptedStr = cryptography.decodeString(ein_no);
		//String encryptedStr = Cryptography.encryptKey(decryptedStr);
		
		// printMissingVendorDetails method is only for printing vendor details for missing 
		// vendor data reported - strictly for trouble shooting.
		printMissingVendorDetails(decryptedStr);
	
		resultList.add(addBlankPadding(decryptedStr, 9).getBytes());
	}


	/**
	 * @param decryptedStr
	 */
	private void printMissingVendorDetails(String decryptedStr) {
		if(ValidationConstants.MISSING_VENDORS_LIST.contains(vendor_id)){
			System.out.println("INFO : MISSING VENDOR ID FOUND ="+vendor_id+" db_name="+dba_name+" business_name="+business_name+" zip="+zip+"  ein_no="+ ein_no+" decryptedStr="+decryptedStr+ " amount="+amount);
		 }
		if(ein_no==null || ein_no.length()<1){
			System.out.println("WARNING : EIN MISSING  - EIN no. missing for vendor="+vendor_id);
		}
		if((ein_no!=null && ein_no.length()>1) && (decryptedStr==null || decryptedStr.length()<1)){
			System.out.println("WARNING : EIN FORMAT INCORRECT  - EIN no. in wrong format for vendor="+vendor_id+ " EIN="+ein_no);
		}
	}


	/**
	 * @param resultList
	 */
	private void addToResultList(final List<byte[]> resultList) {
		String dateStr = getDateString(last_payment_date);

		resultList.add(addBlankPadding(dateStr, 6).getBytes());

		String oname_no_of_lines = getNumberOfLinesForOName(dba_name, business_name, street1, street2);

		resultList.add(oname_no_of_lines.getBytes());

		String oname = getOName(dba_name, business_name, street1, street2);

		resultList.add(oname.getBytes());

		resultList.add(addBlankPadding(city, 20).getBytes());

		resultList.add(addBlankPadding(state, 2).getBytes());

		resultList.add(DatumConstants.SINGLEBLANK.getBytes());

		resultList.add(formatZipCode(zip, zip4).getBytes());
	}


	/**
	 * @param resultList
	 */
	private void addInitialStaticValuesToList(final List<byte[]> resultList) {
		resultList.add(addBlankPadding(DatumConstants.OAPLID, 3).getBytes());

		resultList.add(addBlankPadding(DatumConstants.OCLIENTCODE, 2).getBytes());

		resultList.add(addBlankPadding(DatumConstants.OVENNO, 9).getBytes());

		resultList.add(addBlankPadding(DatumConstants.OVENDORTYPE, 1).getBytes());
	}


	/**
	 * pull all the values from the vendor bean for formatting.
	 * @param vendorAmountBean
	 */
	private void extractValuesFromVendorBean(VendorAmountBean vendorAmountBean, VendorAmountBean vendorAmountBeanCurent) {
		  vendor_id = vendorAmountBean.getVendor_id();
		  amount = vendorAmountBean.getAmount();
		  taxpayer_id_number_type = vendorAmountBean.getTaxpayer_id_number_type();
		  ein_no = vendorAmountBean.getEin_no();
		  dba_name = vendorAmountBean.getDba_name();
		  business_name = vendorAmountBean.getBusiness_name();
		  last_payment_date = vendorAmountBean.getLast_payment_date();
		  //Address should always be the latest address and should not be over written
		  if(null !=vendorAmountBeanCurent){
			  city = vendorAmountBeanCurent.getCity();
			  street1 = vendorAmountBeanCurent.getStreet1();
			  street2 = vendorAmountBeanCurent.getStreet2();
			  state = vendorAmountBeanCurent.getState();
			  zip = vendorAmountBeanCurent.getZip();
			  zip4 = vendorAmountBeanCurent.getZip4();
		  }else{
			  city = vendorAmountBean.getCity();
			  street1 = vendorAmountBean.getStreet1();
			  street2 = vendorAmountBean.getStreet2();
			  state = vendorAmountBean.getState();
			  zip = vendorAmountBean.getZip();
			  zip4 = vendorAmountBean.getZip4();
		  }
	}

	
	
	
	
	

	/**
	 * @throws DataException
	 * @throws DecimalOverflowException
	 */
	private String getStringValueInEBCDIC(String amount) {

		String encodedAmount = "";
		// packed deicmal 7 byte required
		byte[] packedDecimal7 = new byte[7];
		try {
			// packed decimal 16 byte
			byte[] packedDecimal16 = PackDec.stringToPack(amount);
			System.arraycopy(packedDecimal16, 9, packedDecimal7, 0, 7);
			encodedAmount = new String(packedDecimal7);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			encodedAmount = new String(packedDecimal7);
		}

		return encodedAmount;
	}
	
	
	
	/**
	 * @throws DataException
	 * @throws DecimalOverflowException
	 */
	private String getNumberValueInEBCDIC(int amount) {

		String encodedAmount = "";
		// packed deicmal 7 byte required
		byte[] packedDecimal7 = new byte[7];
		try {
			// packed decimal 16 byte
			byte[] packedDecimal16 = PackDec.longToPack(amount);
			System.arraycopy(packedDecimal16, 9, packedDecimal7, 0, 7);
			encodedAmount = new String(packedDecimal7);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			encodedAmount = new String(packedDecimal7);
		}

		return encodedAmount;
	}
	
	/**
	 * @throws DataException
	 * @throws DecimalOverflowException
	 */
	private byte[] getNumberInEBCDIC(int amount) {

		
		// packed deicmal 7 byte required
		byte[] packedDecimal7 = new byte[7];
		try {
			// packed decimal 16 byte
			byte[] packedDecimal16 = PackDec.longToPack(amount);
			System.arraycopy(packedDecimal16, 9, packedDecimal7, 0, 7);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		return packedDecimal7;
	}
	

	/**
	 * @param last_payment_date
	 * @return
	 */
	private String getDateString(Date last_payment_date) {
		String dateStr = "";
		if (last_payment_date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("MMddyy");
			dateStr = formatter.format(last_payment_date);

		}
		return dateStr;
	}

	/**
	 *  In case if the amount value is not in number format. check for some vasic chars that
	 *  we can remove.
	 * @param amount
	 * @return
	 */
	public static String formatAmountValue(String amount) {
		if(amount!=null){
				// Remove commas from the amount
				amount = amount.replaceAll(",", "");
				// Remove dollar sign from amount
				amount = amount.replaceAll("$", "");
				
				// Remove double quotes
				amount = amount.replaceAll("\"", "");
				// Remove single quotes
				amount = amount.replaceAll("\'", "");
		 }
		return amount;
	}

	public static void converToPackedDec() {
		byte[] pd = PackDec.longToPack(345);
		String value = new String(pd);
		// System.out.println(value);
		// System.out.println(pd);
	}

	public static void main(String[] args) {

		FormatData fd = new FormatData();
		StringBuilder stringBuilder = new StringBuilder();

		System.out.println(fd.getNumberValueInEBCDIC(904));
		try {
			
			String num = new String(fd.getNumberInEBCDIC(904));
			
			WriteEBCDIC.writeToOutputFileTest(num.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// System.out.println(roundNumber("asdf")); }
	 
	
	
	
	
	

}
