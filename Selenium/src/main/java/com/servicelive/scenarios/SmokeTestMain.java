package com.servicelive.scenarios;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.servicelive.logger.LoggerClass;

public class SmokeTestMain {
	
	public static Spn spn;
	public static Campaign campaign;
	public static CreateSOAPIATT createSoApiAtt;
	public static CreateSOAPIHSR createSoApiHsr;
	public static BuyerBid buyerBid;
	public static RIInjectionFlow rIInjectionFlow;
	
	
	public static void main(String[] args) {
		
		spn = new Spn();
		campaign = new Campaign();
		createSoApiAtt = new CreateSOAPIATT();
		createSoApiHsr = new CreateSOAPIHSR();
		buyerBid = new BuyerBid();
		
		Properties properties=new Properties();
		LoggerClass loggerClass=new LoggerClass();
		String result = "";
		String spnException = "";
		String campaignException = "";
		String createSoApiAttException = "";
		String createSoApiHsrException = "";
		String buyerBidException = "";
		String rIException = "";
		StringBuilder sb = new StringBuilder();
		String spnName = "";
		String campaignName= "";
		String attSoId ="";
		String hsrSoId ="";
		String bidSoId ="";
		String riSoId ="";
	
		String envName = "";
		if (args.length > 0) {
            envName = args[0];
            envName=envName+".properties";
        }
		else
		{
			 envName="ENV3.properties";
		}
		
		loggerClass.debugTracker("Smoke Test is currently been performed on : "+envName.replace(".properties", ""));
	
		try {
			properties.load(RIInjectionFlow.class.getClassLoader().getResourceAsStream(envName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			result = "Error while loading properties";
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			result = "Error while loading properties";
		}		
		
		
	if(StringUtils.isBlank(result))
	{
		result = spn.createSpn(properties);
		String spnResult = (result.contains("Error")||result.contains("fail")?"FAIL":"SUCCESS");
		if(spnResult.equals("FAIL")){
			 spnException =  result.substring(result.indexOf(">>")+2);
		}else{
			spnName =  result.substring(result.indexOf(">>")+2);
		}
		
		result = campaign.createCampaign(properties);
		String campaignResult = (result.contains("Error")||result.contains("fail")?"FAIL":"SUCCESS");
		if(campaignResult.equals("FAIL")){
			campaignException =  result.substring(result.indexOf(">>")+2);
		}else{
			campaignName = result.substring(result.indexOf(">>")+2);
		}
		
		result = createSoApiAtt.createATTSO(properties);
		String createAttResult = (result.contains("Error")||result.contains("fail")?"FAIL":"SUCCESS");
		if(createAttResult.equals("FAIL")){
			createSoApiAttException =  result.substring(result.indexOf(">>")+2);
		}
			attSoId = result.substring(result.indexOf("@@")+2);
			
		result = createSoApiHsr.createHSRSO(properties);
		String createHsrResult = (result.contains("Error")||result.contains("fail")?"FAIL":"SUCCESS");
		if(createHsrResult.equals("FAIL")){
			 createSoApiHsrException =  result.substring(result.indexOf(">>")+2);
		}
			 hsrSoId = result.substring(result.indexOf("@@")+2);
			 
		result = buyerBid.createBid(properties);
		String buyerBidResult = (result.contains("Error")||result.contains("fail")?"FAIL":"SUCCESS");
		if(buyerBidResult.equals("FAIL")){
			 buyerBidException =  result.substring(result.indexOf(">>")+1);
		}
			bidSoId = result.substring(result.indexOf("@@")+2);
			
		result = rIInjectionFlow.createRIOrder(properties);
		String rIResult = (result.contains("Error")||result.contains("fail")?"FAIL":"SUCCESS");
		if(rIResult.equals("FAIL")){
			rIException =  result.substring(result.indexOf(">>")+2);
		}
			riSoId = result.substring(result.indexOf("@@")+2);
		
		
	    sb.append("<html>");
	    sb.append("<head>");
	    sb.append("<title>Title");
	    sb.append("</title>");
	    sb.append("</head>");
	    sb.append("<body> <h3>Smoke Test Results!!</h3>");
	    sb.append("<table border='1'><tr>");
	    sb.append("<td bgcolor='#A7ABAD'><b>Scenario</b></td>");
	    sb.append("<td bgcolor='#A7ABAD'><b>Result</b></td>");
	    sb.append("<td bgcolor='#A7ABAD'><b>SOID</b></td>");
	    sb.append("<td bgcolor='#A7ABAD'><b>Exception</b></td></tr>");
	    
		sb.append("<tr>");
	    sb.append("<td>SPN Creation</td>");
	    sb.append("<td>"+spnResult+"</td>");
	    sb.append("<td>"+spnName+"</td>");
	    sb.append("<td>"+spnException+"</td>");
	    sb.append("</tr>");
	    
	   	sb.append("<tr>");
	    sb.append("<td>Campaign Creation</td>");
	    sb.append("<td>"+campaignResult+"</td>");
	    sb.append("<td>"+campaignName+"</td>");
	    sb.append("<td>"+campaignException+"</td>");
	    sb.append("</tr>");
	    
	    sb.append("<tr>");
	    sb.append("<td>ATT Order - API</td>");
	    sb.append("<td>"+createAttResult+"</td>");
	    sb.append("<td>"+attSoId+"</td>");
	    sb.append("<td>"+createSoApiAttException+"</td>");
	    sb.append("</tr>");
	    
	    sb.append("<tr>");
	    sb.append("<td>HSR Order - API</td>");
	    sb.append("<td>"+createHsrResult+"</td>");
	    sb.append("<td>"+hsrSoId+"</td>");
	    sb.append("<td>"+createSoApiHsrException+"</td>");
	    sb.append("</tr>");
	    
	   	sb.append("<tr>");
	    sb.append("<td>Bid Order Completion</td>");
	    sb.append("<td>"+buyerBidResult+"</td>");
	    sb.append("<td>"+bidSoId+"</td>");
	    sb.append("<td>"+buyerBidException+"</td>");
	    sb.append("</tr>");
	    
	    sb.append("<tr>");
	    sb.append("<td>RI Order Completion</td>");
	    sb.append("<td>"+rIResult+"</td>");
	    sb.append("<td>"+riSoId+"</td>");
	    sb.append("<td>"+rIException+"</td>");
	    sb.append("</tr>");
	    
	    sb.append("</body>");
	    sb.append("</html>");
	    
	    FileWriter fstream = null;
		try {
			fstream = new FileWriter(properties.getProperty("smokeTestResultLocn"));
			 BufferedWriter out = new BufferedWriter(fstream);
			 out.write(sb.toString());
			 out.flush();
			 out.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	else
	{
			loggerClass.debugTracker(result);
	}
									}
	
					}
