package com.servicelive.manage1099.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import com.servicelive.manage1099.log.Log;

public class CommonUtil {
	/**
	 * Count number of occurrences of arg2 in arg1.
	 * 
	 * @param arg1
	 * @param arg2
	 * @return int
	 */
	public static int countOccurrences(String arg1, String arg2) {

		int count = 0;
		int index = 0;

		if (arg1 != null && arg2 != null) {
			while ((index = arg1.indexOf(arg2, index)) != -1) {
				++index;
				++count;
			}
		}
		return count;

	}
	
	/**
	 * 
	 * @param original
	 * @param toReplace
	 * @param replaceWith
	 * @return
	 */
	
	public static String replaceFirstOccurrence(String original, String toReplace, String replaceWith) {

		StringBuilder sb = new StringBuilder();
		if (original != null && toReplace != null && replaceWith!=null) {
			
			int indexOf = original.indexOf(toReplace);
			sb.append(original.substring(0, indexOf));
			sb.append(replaceWith);
			sb.append(original.substring(indexOf+toReplace.length()));
			
		
		}

		return sb.toString();

	}

	
	/**
	 * 
	 * @param fileNamePrefix
	 * @return
	 */
	
	public static String suffixTimeStamp(final String fileNamePrefix){
        StringBuilder fileName = new StringBuilder();
        String fileNameExt = "";
        String nowYYYYMMDD = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        // If the extension is already passed extract the file extension and re-append it , after time stamp.
        if(fileNamePrefix!=null && fileNamePrefix.indexOf(".")>0){
        	fileNameExt = fileNamePrefix.substring(fileNamePrefix.lastIndexOf("."));
         	fileName.append(fileNamePrefix.substring(0,fileNamePrefix.lastIndexOf(".")));
            fileName.append(nowYYYYMMDD);
            fileName.append(fileNameExt);
        } 
        else{
	        fileName.append(fileNamePrefix);
	        fileName.append(nowYYYYMMDD);
        }
  	    return fileName.toString();
     }
	
	
	
	
	/**
	 * 
	 * @param original
	 * @param toReplace
	 * @param replaceWith
	 * @return
	 */

	public static String replaceAllOccurrences(String original, String toReplace, String replaceWith) {

		String newOriginal=original;
		
		
		if (original != null && toReplace != null && replaceWith != null) {

			newOriginal=original.replaceAll(toReplace, replaceWith);

		}

		return newOriginal;

	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public static int roundNumber(String number) {
	
		int num = 0;
	
		try {
			if (number != null) {
				num = Math.round(Float.valueOf(number));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			Log.writeLog(Level.WARNING, "[FormatData].roundNumber(Exception" + e.toString());
			e.printStackTrace();
		}
	
		return num;
	
	}

	
	

}
