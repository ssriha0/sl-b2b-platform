package com.newco.marketplace.util;

import java.net.InetAddress;

import com.servicelive.common.CommonUtil;

public class PTDUtil {
	public static String getMailBodyText(){
		String str="";
		try{ 
		String machineName = InetAddress.getLocalHost().getHostName()+"( "+(InetAddress.getLocalHost().getHostAddress())+" )";
		str = "Process Name: PTD File Processor\n" +
		"Date : "+CommonUtil.getCurrentTimeStamp()+"\nServer Id: "+ machineName +"\n"+
		"Details: ";
		}catch(Exception e){e.printStackTrace();}
		return str;
	}
	
	public static String getUpdatedTransactionsEmailText()
	{
		String str="";
		try{ 
		String machineName = InetAddress.getLocalHost().getHostName()+"( "+(InetAddress.getLocalHost().getHostAddress())+" )";
		str = "Process Name: PTD File Processor\n" +
		"Date : "+CommonUtil.getCurrentTimeStamp()+"\nServer Id: "+ machineName +"\n"+
		"Details of Fullfillment Group Id's updated :\n\n " + "FullfillmentGroupId" + "\t" + "Action Code Id" + "\t" + "Action Code Desc" + "\n\n";
		}catch(Exception e){e.printStackTrace();}
		return str;
	}
}
