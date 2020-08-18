/**
 * 
 */
package com.servicelive.manage1099.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;
import com.servicelive.manage1099.beans.AmountBean;
import com.servicelive.manage1099.constants.FileConstants;
import com.servicelive.manage1099.format.DataHandler;
import com.servicelive.manage1099.format.FormatData;
import com.servicelive.manage1099.util.CommonUtil;

/**
 * @author mjoshi1
 * 
 */
public class FileHandler {
	//private static final Logger logger = Logger.getLogger(FileHandler.class);
	/**
	 * Deletes the output file.
	 */
	public static void delete1099File(String filePath) {
		try {
			
			File f = new File(filePath);

			
			if (f.exists()) {
				// Do not delete the file before the file is PGP encrypted
				// Check if we want to delete the file.
				// We may want to keep the file in test mode.
				if(FileConstants.DELETE_FILE.equalsIgnoreCase("true") || FileConstants.DELETE_FILE.equalsIgnoreCase("yes")){
					System.out.println("About to delete file... "+filePath);
				//	 f.delete();
				}
			}
		} catch (Exception e) {
			System.out
					.println("[FileHandler.deleteFile() Exception]  - Error deleting the output file. Please ensure you have proper permissions and the file is not opened in another application."+e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
	
	/**
	 * Deletes the output file.
	 */
	public static void deleteFile(String filePath) {
		try {
			
			File f = new File(filePath);

			
			if (f.exists()) {
				
					 f.delete();
				
			}
		} catch (Exception e) {
			System.out
					.println("[FileHandler.deleteFile() Exception]  - Error deleting the output file. Please ensure you have proper permissions and the file is not opened in another application."+e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
	
	
	
	
	
	
	/**
	 * Loads the provider_id and amount value from the file to a hashmap. The
	 * provider id is loaded as the key and amount as value.
	 * 
	 * @return
	 */

	public static void loadValidationFileToMap(HashMap<Integer, String> providerAmountMap, String filePath) {

		try {

			File f = new File(filePath);

			if (f.exists()) {
				
				BufferedReader input =  new BufferedReader(new FileReader(f));
			      try {
			        String line = null; //not declared within while loop
			        
			        while (( line = input.readLine()) != null){
			          
			        	String[] VendorIdAndAmount = DataHandler.getVendorIdAndAmount(line);
			        	if(VendorIdAndAmount[0]!=null && VendorIdAndAmount[0].trim().length()>1){
			        		providerAmountMap.put(Integer.valueOf(VendorIdAndAmount[0]), VendorIdAndAmount[1]);
			        	}
			        }
			        
			      }
			      catch(Exception e)
			      {
			    	  e.printStackTrace();
			      }
			      finally {
			    	try
			    	{
			    	if(input!=null)
			        input.close();
			    	}
			    	catch(Exception e)
			    	{
			    	//logging error as this can never occur	
			    	//logger.error("Caught inside: <FileHandler::loadValidationFileToMap()>:Error: Got an exception that should not occur", e);
			    	
			    	}
			      }


			}

		} catch (Exception e) {
			System.out
					.println("Error loading the vendor Id and amount from the following file: "+filePath+"    error" + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean generateReportFileFromData(int slProviderCount,int boProviderCount, HashMap<Integer, String> missingVendorsInBOMap,
			HashMap<Integer, String> missingVendorsInSLMap, HashMap<Integer, AmountBean> mismatchAmountMap, HashMap<Integer, AmountBean> matchedAmountMap) {
		// Delete the file if it exists already
		deleteFile(FileConstants.VALIDATION_REPORT);
		
		String[] header= new String[]{"Vendor Id","BO Amount","SL Amount"};

		try {
			// Create file
			FileWriter fstream = new FileWriter(CommonUtil.suffixTimeStamp(FileConstants.VALIDATION_REPORT), true);
			BufferedWriter writer = new BufferedWriter(fstream);
			
			writer.write("************************************************************ \n");
			writer.write("             1099 DATA COMPARISON (SL vs. BO)                  \n");
			writer.write("************************************************************ \n\n");
			

			writer.write("Summary : \n");
			writer.write("Total number of providers in SL file="+slProviderCount+"\n");
			writer.write("Total number of providers in BO file="+boProviderCount+"\n");
			if(missingVendorsInBOMap!=null){
				writer.write("Total count of missing vendors in BO file=" +missingVendorsInBOMap.size()+ "\n");
			}
			if(missingVendorsInSLMap!=null){
				writer.write("Total count of missing vendors in Service Live 1099 file=" +missingVendorsInSLMap.size()+ "\n");
			}
			if(mismatchAmountMap!=null){
				writer.write("Total count of mis-matched amounts=" +mismatchAmountMap.size()+ "\n");
			}
			
			if(matchedAmountMap!=null){
				writer.write("Total count of matched amounts=" +matchedAmountMap.size()+ "\n");
			}
			writer.write("\n\n");
			
			
			writer.write("Details : \n");
			if(missingVendorsInBOMap!=null){

				writer.write("----------------------------------------------------------------------- \n");
				writer.write(" Missing vendors in BO file : Total Count(" +missingVendorsInBOMap.size()+ ")\n");
				writer.write("----------------------------------------------------------------------- \n");

				if(missingVendorsInBOMap.keySet()!=null){
					writer.write(missingVendorsInBOMap.keySet().toString());
				}
				
			}
			writer.write("\n\n\n");
			if(missingVendorsInSLMap!=null){

				writer.write("----------------------------------------------------------------------- \n");
				writer.write(" Missing vendors in Service Live 1099 file : Total Count(" +missingVendorsInSLMap.size()+ ")\n");
				writer.write("----------------------------------------------------------------------- \n");

				if(missingVendorsInSLMap.keySet()!=null){
					writer.write(missingVendorsInSLMap.keySet().toString());
				}
			}
			
			writer.write("\n\n\n");
			if(mismatchAmountMap!=null){
				Set<Integer> keySet = mismatchAmountMap.keySet();
				writer.write("----------------------------------------------------------------------- \n");
				writer.write("Amount mis-match for the following providers : Total Count(" +mismatchAmountMap.size()+ ")\n");
				writer.write("\n");
				String headerString=FormatData.addSpacing(header);
				writer.write(headerString+"\n");
				
				writer.write("-----------------------------------------------------------------------\n");
				
				
				if(keySet!=null){
					for (Integer vendorId : keySet) {
						AmountBean amountBean = mismatchAmountMap.get(vendorId);
						String[] record = new String[3];
						record[0]= String.valueOf(vendorId);
						record[1]= amountBean.getBoAmount();
						record[2]=amountBean.getSlAmount();
						String recordString=FormatData.addSpacing(record);
						writer.write(recordString);
						writer.write(" \n");
					}
				
				}
			}
			writer.write("\n\n\n");
			if(matchedAmountMap!=null){
				Set<Integer> keySet = matchedAmountMap.keySet();
				writer.write("----------------------------------------------------------------------- \n");
				writer.write("Amount matched for the following providers : Total Count(" +matchedAmountMap.size()+ ")\n");
				writer.write("\n");
				String headerString=FormatData.addSpacing(header);
				writer.write(headerString+"\n");
				writer.write("----------------------------------------------------------------------- \n");
				
				
				if(keySet!=null){
					for (Integer vendorId : keySet) {
						AmountBean amountBean = matchedAmountMap.get(vendorId);
						String[] record = new String[3];
						record[0]= String.valueOf(vendorId);
						record[1]= amountBean.getBoAmount();
						record[2]=amountBean.getSlAmount();
						String recordString=FormatData.addSpacing(record);
						writer.write(recordString);
						writer.write(" \n");
					}
				
				}
			}

			writer.write("\n\n\n");
			
			// Close the output stream
			writer.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return true;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		FileHandler fh = new FileHandler();
		HashMap map = new HashMap();
		
		fh.loadValidationFileToMap( map, "C:\\Projects\\R3_8_Nov24\\ServiceLive1099\\dist\\buyerAmount3.csv");
		
		System.out.println(map.keySet());
		System.out.println(map.values());
		
		
	}
	
	
	
	

}
