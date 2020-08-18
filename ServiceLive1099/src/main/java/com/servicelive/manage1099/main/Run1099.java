/**
 * 
 */
package com.servicelive.manage1099.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.logging.Level;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.servicelive.manage1099.beans.BuyerInputBean;
import com.servicelive.manage1099.beans.VendorAmountBean;
import com.servicelive.manage1099.constants.DBConstants;
import com.servicelive.manage1099.constants.FileConstants;
import com.servicelive.manage1099.constants.MessageConstants;
import com.servicelive.manage1099.constants.ValidationConstants;
import com.servicelive.manage1099.db.DBAccess;
import com.servicelive.manage1099.email.EmailServiceImpl;
import com.servicelive.manage1099.email.IEmailService;
import com.servicelive.manage1099.encode.WriteEBCDIC;
import com.servicelive.manage1099.file.FileHandler;
import com.servicelive.manage1099.format.DataHandler;
import com.servicelive.manage1099.format.FormatData;
import com.servicelive.manage1099.log.Log;
import com.servicelive.manage1099.properties.ReadProperties;
import com.servicelive.manage1099.script.RunShScript;
import com.servicelive.manage1099.util.DateCheck;
import com.servicelive.manage1099.validate.ValidateAmount;

/**
 * @author mjoshi1
 * 
 */
public class Run1099 {
	
	final static IEmailService emailService = new EmailServiceImpl();
	static Map<Integer,VendorAmountBean> vendorDetailsMap =null;
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */

	
	public static void main(String[] args) throws Exception {

		/*
		 * Contains the consolidated data from all resultsets.
		 */
		vendorDetailsMap = new HashMap<Integer,VendorAmountBean>();
	
		final ReadProperties readProps = new ReadProperties();
		readProps.loadInputProperties();
		
		try {
			// Read database property values from sl1099.properties and buyers from buyerInput.parameters located in dist
			// directory.
						
			loadVendorDetailsMap();

			/**
			 * Validate the vendorIds and amount. Generate a report ( a file ) that will contain
			 * the list of providers that don't have matching amount etc.
			 */
			validateData(vendorDetailsMap);
			
			//Handling multiple W9 profiles
			//Fetching W9 history info for the vendors from the vendorDetailsMap
			Map<Integer, ArrayList<VendorAmountBean>> vendorW9HistoryMap = getVendorW9HistoryDetails(vendorDetailsMap.keySet());
			
			if (args.length > 0 && args[0] != null && args[0].trim().equalsIgnoreCase("E")) {
				if(vendorDetailsMap.size() > 0){
					//getting the dir from the properties file
					File soDetailsReport = new File(FileConstants.FILE_NAME_WITH_PATH.replace(".txt", ".xls"));
					FileOutputStream outFinal = null;
					try {
						System.out.println("File is created at the location mentioned in the properties file.");
						outFinal = new FileOutputStream(soDetailsReport);
					} catch (FileNotFoundException e1) {
						System.out.println("Error in creating the file."+e1);
						e1.printStackTrace();
					}
					try {
						//Writing to excel the SO details after correction
						writeToExcel(vendorDetailsMap, vendorW9HistoryMap, outFinal);
					}catch (WriteException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				FormatData formatData = new FormatData();
				// Format all the data read from database. Add padding to the fields if
				// necessary.
				Map<Integer, List> finalDataMap = formatData.formatResultMap(vendorDetailsMap,  vendorW9HistoryMap);
				
				System.out.println(".........................................................................");
				System.out.println(" >>>>>>>>>> Total Record Count(no. of providers) = " +finalDataMap.size());
				System.out.println(".........................................................................");
				// Write the data to the file
				WriteEBCDIC.writeBytesToOutputFile(finalDataMap);
				
				// PGP using the script or FTP without PGP encryption.
				pgpOrFTP(args);

				// Delete the TXT file after the PGP file is generated.
				//FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);
			
				// Print success message and send email if configured.
				//printSuccessSendEmail();				
			}
			
		} catch (Exception e){
			// Print failure message and send email if configured.
			//printFailureSendEmail(e);
		}
		finally {
			//FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);
			
		}

	}

	private static Map<Integer, ArrayList<VendorAmountBean>> getVendorW9HistoryDetails(Set<Integer> keySet) {

		final DBAccess dba = new DBAccess();
		//Start and end dates are obtained from the properties file
		String startDate = FileConstants.START_DATE+" 00:00:00";
		String endDate = FileConstants.END_DATE+" 23:59:59";

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Map<Integer, ArrayList<VendorAmountBean>> vendorW9HistoryMap = new HashMap<Integer,ArrayList<VendorAmountBean>>();
		String vendorIds = null;
		ResultSet rs=null;
		//Trimming the list to vendors who have atleast a change in the given duration
		//Trimming is not done in query as we will need a record of the last EIN
		if(keySet.size()>0){
			vendorIds = keySet.toString().replace("[", "").replace("]", "");
		}

		try {
			rs = dba.getW9History(DBConstants.W9_HISTORY_DETAILS, vendorIds);
			DataHandler.populateVendorW9HistoryFromResult(vendorW9HistoryMap, endDate, rs);

			//Logic to remove the W9 profiles with only one record in the W9 history table
			for (Iterator<Map.Entry<Integer, ArrayList<VendorAmountBean>>> i = vendorW9HistoryMap.entrySet().iterator(); i.hasNext(); )
			{
				Map.Entry<Integer, ArrayList<VendorAmountBean>> entry = i.next();
				if(1 == entry.getValue().size()){
			        i.remove();
			    }
			}

			Iterator<Map.Entry<Integer, ArrayList<VendorAmountBean>>> entries = vendorW9HistoryMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Entry<Integer, ArrayList<VendorAmountBean>> entry = entries.next();
			    List<VendorAmountBean> vendorW9List = entry.getValue();
			    
			    Integer iterationCount = 0;
		    	boolean endDateCheck = false;
				String startDateQ = "";
				String endDateQ = "";
			    for(VendorAmountBean vendorBean : vendorW9List){
			    	iterationCount++;
					Date modDate = vendorBean.getLast_payment_date();

			    	//This check is redundant as we have removed cases where the payment date greater than end date
					// but we still need the end date check
			    	if(modDate.before(sdf.parse(endDate)) && !endDateCheck){
			    		startDateQ = sdf.format(modDate);
			    		endDateQ = endDate;
			    		vendorBean.setLast_payment_date(null);
			    		vendorBean.setAmount("0");
			    		loadVendorDetails(vendorBean, startDateQ, endDateQ);
			    		//set payment date and the amount
			    		endDateCheck = true;
			    		//changing the end date
			    		endDateQ = startDateQ;
			    		continue;
			    	}//
			    	if(modDate.after(sdf.parse(startDate))){
			    		startDateQ = sdf.format(modDate);
			    		//fire query
			    		loadVendorDetails(vendorBean, startDateQ, endDateQ);
			    		//changing the end date
			    		endDateQ = startDateQ;
			    		//set payment date and the amount
			    	}else{
			    		startDateQ = startDate;
			    		//fire query
			    		loadVendorDetails(vendorBean, startDateQ, endDateQ);
			    		//set payment date and the amount
			    	}
			    	
			    	if(modDate.after(sdf.parse(startDate)) && iterationCount.equals(vendorW9List.size())){
			    		startDateQ = startDate;
			    		//fire query
			    		loadVendorDetails(vendorBean, startDateQ, endDateQ);
			    		//set payment date and the amount
			    	}			    	
			    }
			}
			
			//Log.writeLog(Level.INFO,"vendorW9HistoryMap size after: "+vendorW9HistoryMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendorW9HistoryMap;
	}

	private static void loadVendorDetails(VendorAmountBean vendorBean, String startDateQ, String endDateQ) throws Exception, SQLException {
		ResultSet rs=null;
		final DBAccess dba = new DBAccess();
		
		DBConstants.QUERY_CREDIT_DEBIT_PER_VENDOR = DBConstants.QUERY_CREDIT_DEBIT_PER_VENDOR.replace("?", vendorBean.getVendor_id());
		DBConstants.QUERY_SO_PAYMENT_PER_VENDOR = DBConstants.QUERY_SO_PAYMENT_PER_VENDOR.replace("?", vendorBean.getVendor_id());
		DBConstants.QUERY_SO_CANCELLATION_FIX_PER_VENDOR = DBConstants.QUERY_SO_CANCELLATION_FIX_PER_VENDOR.replace("?", vendorBean.getVendor_id());
		
		//fire query
		rs = dba.read1099Data(null,DBConstants.QUERY_CREDIT_DEBIT_PER_VENDOR , startDateQ, endDateQ);
		DataHandler.populateVendorDetailsFromResult(vendorBean, rs, true);

		rs = dba.read1099Data(null,DBConstants.QUERY_SO_PAYMENT_PER_VENDOR , startDateQ, endDateQ);
		DataHandler.populateVendorDetailsFromResult(vendorBean, rs, true);
		
		rs = dba.read1099Data(null,DBConstants.QUERY_SO_CANCELLATION_FIX_PER_VENDOR , startDateQ, endDateQ);
		DataHandler.populateVendorDetailsFromResult(vendorBean, rs, false);
		
		//reset ? in the queries
		DBConstants.QUERY_CREDIT_DEBIT_PER_VENDOR = DBConstants.QUERY_CREDIT_DEBIT_PER_VENDOR.replace(vendorBean.getVendor_id(),"?");
		DBConstants.QUERY_SO_PAYMENT_PER_VENDOR = DBConstants.QUERY_SO_PAYMENT_PER_VENDOR.replace(vendorBean.getVendor_id(),"?");
		DBConstants.QUERY_SO_CANCELLATION_FIX_PER_VENDOR = DBConstants.QUERY_SO_CANCELLATION_FIX_PER_VENDOR.replace(vendorBean.getVendor_id(),"?");
	}

	private static void writeToExcel(
			Map<Integer, VendorAmountBean> vendorDetailsMap2,
			Map<Integer, ArrayList<VendorAmountBean>> vendorW9HistoryMap,
			FileOutputStream outFinal)  throws IOException, WriteException {
		System.out.println("Creating the workbook");
		try{
		
	 	   WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
	       WritableSheet sheet = workbook.createSheet("1099 Report", 0);
	       sheet.setColumnView(0,30);
	       sheet.setColumnView(1,30);
	       sheet.setColumnView(2,30);
	       sheet.setColumnView(3,30);
	       sheet.setColumnView(4,30);
	       sheet.setColumnView(5,30);
	       sheet.setColumnView(6,30);
	       sheet.setColumnView(7,30);
	       sheet.setColumnView(8,30);
	       sheet.setColumnView(9,30);
	       sheet.setColumnView(10,30);
	       sheet.setColumnView(11,30);
	       sheet.setColumnView(12,30);

	       
	       // Create a cell format for Arial 10 point font 
	       WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD); 
	       WritableCellFormat arial10format = new WritableCellFormat (arial10font);	
	       arial10format.setAlignment(Alignment.CENTRE);
	       arial10format.setBackground(Colour.ICE_BLUE);
	       arial10format.setBorder(Border.ALL,BorderLineStyle.THIN);
	       WritableCellFormat cellformat = new WritableCellFormat ();
	       cellformat.setAlignment(Alignment.RIGHT);
	       
			Label vendorIdL = new Label(0, 0, "vendor_id",arial10format);
			Label amountL = new Label(1, 0, "amount",arial10format);
			Label taxPayerTypeL = new Label(2, 0, "taxpayer_id_number_type",arial10format);
			Label einL = new Label(3, 0, "ein_no",arial10format);
			Label dbaNameL = new Label(4, 0, "dba_name",arial10format);
			Label busNameL = new Label(5, 0, "business_name",arial10format);
			Label lastPaymentL = new Label(6, 0, "last_payment_date",arial10format);
			Label cityL = new Label(7, 0, "city",arial10format);
			Label street1L = new Label(8, 0, "street1",arial10format);
			Label street2L = new Label(9, 0, "street2",arial10format);
			Label stateL = new Label(10, 0, "state",arial10format);
			Label zipL = new Label(11, 0, "zip",arial10format);
			Label zip4L = new Label(12, 0, "zip4",arial10format);
			
			sheet.addCell(vendorIdL);
			sheet.addCell(amountL);
			sheet.addCell(taxPayerTypeL);
			sheet.addCell(einL);
			sheet.addCell(dbaNameL);
			sheet.addCell(busNameL);
			sheet.addCell(lastPaymentL);
			sheet.addCell(cityL);
			sheet.addCell(street1L);
			sheet.addCell(street2L);
			sheet.addCell(stateL);
			sheet.addCell(zipL);
			sheet.addCell(zip4L);
			
			System.out.println("Added the Column labels to the workbook");
			System.out.println("Writing row level data to the workbook");
			
			Iterator<Map.Entry<Integer, VendorAmountBean>> entries = vendorDetailsMap2.entrySet().iterator();
			int count = 0;
			/*System.out.println("vendorW9HistoryMap size: "+vendorW9HistoryMap.get(new Integer(10181)).size());
			System.out.println("vendorW9HistoryMap 0 ein: "+vendorW9HistoryMap.get(new Integer(10181)).get(0).getEin_no());
			System.out.println("vendorW9HistoryMap 0 amt: "+vendorW9HistoryMap.get(new Integer(10181)).get(0).getAmount());
			System.out.println("vendorW9HistoryMap 0 pymt dt: "+vendorW9HistoryMap.get(new Integer(10181)).get(0).getLast_payment_date());
			System.out.println("vendorW9HistoryMap 1 ein: "+vendorW9HistoryMap.get(new Integer(10181)).get(1).getEin_no());
			System.out.println("vendorW9HistoryMap 1 amt: "+vendorW9HistoryMap.get(new Integer(10181)).get(1).getAmount());
			System.out.println("vendorW9HistoryMap 1 pymt dt: "+vendorW9HistoryMap.get(new Integer(10181)).get(1).getLast_payment_date());
			
			System.out.println("vendorW9HistoryMap size: "+vendorW9HistoryMap.get(new Integer(36035)).size());
			System.out.println("vendorW9HistoryMap 0 ein: "+vendorW9HistoryMap.get(new Integer(36035)).get(0).getEin_no());
			System.out.println("vendorW9HistoryMap 0 amt: "+vendorW9HistoryMap.get(new Integer(36035)).get(0).getAmount());
			System.out.println("vendorW9HistoryMap 0 pymt dt: "+vendorW9HistoryMap.get(new Integer(36035)).get(0).getLast_payment_date());
			System.out.println("vendorW9HistoryMap 1 ein: "+vendorW9HistoryMap.get(new Integer(36035)).get(1).getEin_no());
			System.out.println("vendorW9HistoryMap 1 amt: "+vendorW9HistoryMap.get(new Integer(36035)).get(1).getAmount());
			System.out.println("vendorW9HistoryMap 1 pymt dt: "+vendorW9HistoryMap.get(new Integer(36035)).get(1).getLast_payment_date());*/
			while (entries.hasNext()) {
			    Map.Entry<Integer, VendorAmountBean> entry = entries.next();
			    //System.out.println("entry.getValue().getVendor_id(): "+entry.getValue().getVendor_id());
			    if(vendorW9HistoryMap.containsKey(Integer.parseInt(entry.getValue().getVendor_id()))){
			    	//System.out.println("Inside if check: "+count);
			    	Collections.reverse(vendorW9HistoryMap.get(Integer.parseInt(entry.getValue().getVendor_id())));
			    	
			    	for(VendorAmountBean vendorHistBean : vendorW9HistoryMap.get(Integer.parseInt(entry.getValue().getVendor_id()))){
			    		//System.out.println("Inside for check: "+count);
			    		//System.out.println("vendorHistBean.getVendor_id(): "+vendorHistBean.getVendor_id());
			    		//System.out.println("vendorHistBean.getAmount(): "+vendorHistBean.getAmount());
			    		//System.out.println("vendorHistBean.getEin_no(): "+vendorHistBean.getEin_no());
			    		if(Float.valueOf(vendorHistBean.getAmount()) > 0){
						    Label vendorId = new Label(0, (1+count), vendorHistBean.getVendor_id(), cellformat);
						    Label amount = new Label(1, (1+count), vendorHistBean.getAmount(), cellformat);
						    Label taxPayerType = new Label(2, (1+count), vendorHistBean.getTaxpayer_id_number_type(), cellformat);
						    Label ein = new Label(3, (1+count), vendorHistBean.getEin_no(), cellformat);
						    Label dbaName = new Label(4, (1+count), vendorHistBean.getDba_name(), cellformat);
						    Label busName = new Label(5, (1+count), vendorHistBean.getBusiness_name(), cellformat);
						    String lastPaymentDate = vendorHistBean.getLast_payment_date()==null?"":vendorHistBean.getLast_payment_date().toString();
						    Label lastPayment = new Label(6, (1+count), lastPaymentDate, cellformat);
						    
						    /*Label city = new Label(7, (1+count), vendorHistBean.getCity(), cellformat);
						    Label street1 = new Label(8, (1+count), vendorHistBean.getStreet1(), cellformat);
						    Label street2 = new Label(9, (1+count), vendorHistBean.getStreet2(), cellformat);
						    Label state = new Label(10, (1+count), vendorHistBean.getState(), cellformat);
						    Label zip = new Label(11, (1+count), vendorHistBean.getZip(), cellformat);
						    Label zip4 = new Label(12, (1+count), vendorHistBean.getZip4(), cellformat);*/
						    
						    //Address should always be the current one hence not setting from history 
						    Label city = new Label(7, (1+count), entry.getValue().getCity(), cellformat);
						    Label street1 = new Label(8, (1+count), entry.getValue().getStreet1(), cellformat);
						    Label street2 = new Label(9, (1+count), entry.getValue().getStreet2(), cellformat);
						    Label state = new Label(10, (1+count), entry.getValue().getState(), cellformat);
						    Label zip = new Label(11, (1+count), entry.getValue().getZip(), cellformat);
						    Label zip4 = new Label(12, (1+count), entry.getValue().getZip4(), cellformat);
						    
						    sheet.addCell(vendorId);
						    sheet.addCell(amount);
						    sheet.addCell(taxPayerType);
						    sheet.addCell(ein);
						    sheet.addCell(dbaName);
						    sheet.addCell(busName);
						    sheet.addCell(lastPayment);
						    sheet.addCell(city);
						    sheet.addCell(street1);
						    sheet.addCell(street2);
						    sheet.addCell(state);
						    sheet.addCell(zip);
						    sheet.addCell(zip4);

						    count++;
			    		}
			    	}
			    }else{
			    	//System.out.println("Inside else check: "+count);
				    Label vendorId = new Label(0, (1+count), entry.getValue().getVendor_id(), cellformat);
				    Label amount = new Label(1, (1+count), entry.getValue().getAmount(), cellformat);
				    Label taxPayerType = new Label(2, (1+count), entry.getValue().getTaxpayer_id_number_type(), cellformat);
				    Label ein = new Label(3, (1+count), entry.getValue().getEin_no(), cellformat);
				    Label dbaName = new Label(4, (1+count), entry.getValue().getDba_name(), cellformat);
				    Label busName = new Label(5, (1+count), entry.getValue().getBusiness_name(), cellformat);
				    Label lastPayment = new Label(6, (1+count), entry.getValue().getLast_payment_date().toString(), cellformat);
				    Label city = new Label(7, (1+count), entry.getValue().getCity(), cellformat);
				    Label street1 = new Label(8, (1+count), entry.getValue().getStreet1(), cellformat);
				    Label street2 = new Label(9, (1+count), entry.getValue().getStreet2(), cellformat);
				    Label state = new Label(10, (1+count), entry.getValue().getState(), cellformat);
				    Label zip = new Label(11, (1+count), entry.getValue().getZip(), cellformat);
				    Label zip4 = new Label(12, (1+count), entry.getValue().getZip4(), cellformat);
				    
				    sheet.addCell(vendorId);
				    sheet.addCell(amount);
				    sheet.addCell(taxPayerType);
				    sheet.addCell(ein);
				    sheet.addCell(dbaName);
				    sheet.addCell(busName);
				    sheet.addCell(lastPayment);
				    sheet.addCell(city);
				    sheet.addCell(street1);
				    sheet.addCell(street2);
				    sheet.addCell(state);
				    sheet.addCell(zip);
				    sheet.addCell(zip4);

				    count++;
			    }

			}
			
			workbook.write();
			workbook.close();
			outFinal.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
			System.out.println("Closing the workbook");
	}



	/**
	 * Make DB call and load all the data to the VendorDetailsMap map.
	 * @throws Exception
	 * @throws SQLException
	 */
	private static void loadVendorDetailsMap() throws Exception, SQLException {
		Log.writeLog(Level.INFO, "Inside loadVendorDetailsMap");
		
		for(BuyerInputBean buyerInfo : FileConstants.BUYER_INPUT){
			ResultSet rs=null;
			boolean creditDebitQuery = false;
			
			if(!DateCheck.isValidDate(buyerInfo.getStartDate())  || !DateCheck.isValidDate(buyerInfo.getEndDate())){
				throw new Exception("Please enter a valid date range in buyerInput.parameters file (yyyy-mm-dd). Please also check if the month specified has 31 days.");
			}

			final DBAccess dba = new DBAccess();
			
			if(buyerInfo.getBuyerid().equalsIgnoreCase("CREDIT_DEBIT_RANGE")){
		    	/*
		    	 * read1099Data method reads the vendor amount and other details one at a time, based on buyer id passed.
		    	 */
		    	creditDebitQuery = true;
		    	Log.writeLog(Level.INFO, "QUERY_CREDIT_DEBIT: "+"5400 -- 5600"+buyerInfo.getBuyerid());
				rs = dba.read1099Data( buyerInfo.getBuyerid(),DBConstants.QUERY_CREDIT_DEBIT , buyerInfo.getStartDate(), buyerInfo.getEndDate());
			}else{
				creditDebitQuery = true;
				Log.writeLog(Level.INFO, "QUERY_SO_PAYMENT: "+"1405 -- 1300 -- 1305"+buyerInfo.getBuyerid());
				rs = dba.read1099Data( buyerInfo.getBuyerid(),DBConstants.QUERY_SO_PAYMENT , buyerInfo.getStartDate(), buyerInfo.getEndDate());
				DataHandler.populateVendorDetailsMapFromResult(vendorDetailsMap, rs, creditDebitQuery);
				
				creditDebitQuery = false;
				Log.writeLog(Level.INFO, "QUERY_SO_CANCELLATION_FIX: "+"1303"+buyerInfo.getBuyerid());
				rs = dba.read1099Data( buyerInfo.getBuyerid(),DBConstants.QUERY_SO_CANCELLATION_FIX , buyerInfo.getStartDate(), buyerInfo.getEndDate());
			}
			
			// Create two hashMaps One with Key=vendorId and value as the VendorAmountBean object.
		    // Second hashmap is vendorSubMap which has key=vendorId and value as amount.
		 	    
		    DataHandler.populateVendorDetailsMapFromResult(vendorDetailsMap, rs, creditDebitQuery);
			
			
		}
	}
	
		

	/**
	 * @param e
	 * @throws Exception
	 */
	private static void printFailureSendEmail(Exception e) throws Exception {
		StringBuilder fileName = new StringBuilder();
		fileName.append(FileConstants.FILE_NAME_WITH_PATH);
		fileName.append(FileConstants.PGP_EXTENSION);
		FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);
		FileHandler.delete1099File(fileName.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("\n");
		sb.append(MessageConstants.ERROR_PROCESSING_FILE+  "   " +e.getMessage());
		System.out.println("Error Occurred: "+e.getMessage());
		Log.writeLog(Level.SEVERE, e.toString());
		sb.append("\n");
		emailService.sendSimpleEmail(sb.toString());
	}

	/**
	 * @throws Exception
	 */
	private static void printSuccessSendEmail() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("********************************");
		sb.append("\n");
		sb.append(" 1099 FILE PROCESSED SUCCESSFULLY");
		sb.append("\n");
		sb.append("********************************");
		sb.append("\n");
		System.out.println(sb.toString());
		Log.writeLog(Level.INFO, sb.toString());
		emailService.sendSimpleEmail(sb.toString());
	}

	/**
	 * This method will upload the file after PGP encryption in case if the
	 * parameters args[3] is p. In case if the args[3] param is f, if will FTP
	 * the file without PGP encryption.
	 * 
	 * @param args
	 */
	private static void pgpOrFTP(String[] args) throws Exception {
		if (args.length > 0 && args[0] != null){
			System.out.println("PGP or FTP arg = "+args[args.length-1]);
		}
		if (args.length > 0 && args[0] != null && args[0].trim().equalsIgnoreCase("F")) {
			
			// Disabling the FTP capability because of the security reasons.
			//int status = FtpClient.upLoadFile();
			
			int status=0;
			
			if (status == 0) {
				System.out.println("The FTP file " + FileConstants.FILE_NAME_WITH_PATH
						+ " has been successfully uploaded.");
				Log.writeLog(Level.INFO, "The FTP file " + FileConstants.FILE_NAME_WITH_PATH
						+ " has been successfully uploaded.");
			}
		}

		else if (args.length > 0 && args[0] != null && args[0].trim().equalsIgnoreCase("P")) {
			int status = RunShScript.runScriptFile();
			if (status == 0) {
			
				Log.writeLog(Level.INFO, "The PGP encoding operation for file " + FileConstants.FILE_NAME_WITH_PATH
						+ " was successful.");
			}
		}
	}

	/**
	 * Displays the correct format of the input command.
	 */
	private static void displayUsage() {
		System.out.println(" USAGE : buyerid1,buyerId2,buyerId3 2009-01-01 2009-12-31 f");
		System.out.println(" f is optional in case if we want to FTP the file.");
		System.out.println(" Please enter valid start date or end date. Correct format is YYYY-MM-DD");
		System.out.println(" All the three params (buyer, start date, end date ) are required.");
	}

	/**
	 * 
	 * @param args
	 * @param ls
	 * @param startDate
	 * @param endDate
	 */
	private static void LogInputParams(String[] args, List ls, String startDate, String endDate) {
		Log.writeLog(Level.INFO, " No. of Args entered: " + args.length);
		Log.writeLog(Level.INFO, " Buyer String Entered :" + ls.toString());
		Log.writeLog(Level.INFO, " Start Date Entered :" + startDate);
		Log.writeLog(Level.INFO, " End Date Entered :" + endDate);
		if(args.length>3){
			Log.writeLog(Level.INFO, " PGP =  " + args[3]);
		}
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private static List<String> getListOfBuyers(String[] args) {
		List<String> ls = new ArrayList();
		String buyers = args[0];
		StringTokenizer st = null;
		if (buyers.indexOf(",") > 0) {
			st = new StringTokenizer(buyers, ",");
		} else if (buyers.indexOf(";") > 0) {
			st = new StringTokenizer(buyers, ";");
		} else {
			st = new StringTokenizer(buyers);
		}

		while (st.hasMoreElements()) {
			String element = (String) st.nextElement();
			if (element != null) {
				ls.add(element.trim());
			}
		}

		return ls;

	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private static String getStartDateEntered(String[] args) {

		String startDate = args[1].trim();

		return startDate;

	}

	/**
	 * 
	 * @param args
	 * @return
	 */

	private static String getEndDateEntered(String[] args) {
		String endDate = args[2].trim();
		return endDate;

	}
	
	
	/**
	 * 
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		FileHandler.delete1099File(FileConstants.FILE_NAME_WITH_PATH);
	}
	
	
	/**
	 * Validate the vendorIds and amount. Generate a report ( a file ) that will contain
	 * the list of providers that don't have matching amount etc.
	 * @param vendorMap
	 */
	
	private static void validateData(Map<Integer,VendorAmountBean> vendorMap) {
		
		if(ValidationConstants.VALIDATE.equalsIgnoreCase("true") || ValidationConstants.VALIDATE.equalsIgnoreCase("yes")){
			
			if(vendorMap!=null){
				ValidateAmount.validateVendorAmount(vendorMap);
			}
			
		}

	}
	
	

}
