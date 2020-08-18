package com.newco.marketplace.admintools.ach;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FinanceAdminTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {
			if(args == null){
				System.out.println("Insuffcient Arguments for specified action"+args.length);
				System.exit(0);		
			}
			if (args[0].equals("acknowledgement") && args.length == 3){

				Integer processLogId = new Integer(args[1]);
				String processOwner = args[2];
				ResetAcknowledgementFiles.resetAcknowledgementData(processLogId, processOwner);

			}
			else if (args[0].equals("origination") && args.length == 2){
				String controlFileName = args[1];
				ResetOrginationFileRecords.resetOriginationData(controlFileName);
			}
			else if (args[0].equals("GLFeedFile") && args.length == 3){
				
				Date date1 = null;
				Date date2 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(args[1] != null){
					date1 = sdf.parse(args[1]);
				}
				if(args[2] != null){
					date2 = sdf.parse(args[2]);
				}
				GenerateGLFile.writeGLFeed(date1, date2);
				
			}
			else{
				System.out.println("Insuffcient Arguments for specified action -->" + args[0]);
				System.exit(0);				
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
