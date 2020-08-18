package com.servicelive.orderfulfillment.common;


import java.math.BigDecimal;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.servicelive.client.SimpleRestClient;


public class SkuTaxCalculation {
	private Logger logger = Logger.getLogger(getClass());
	
	 
	
	public BigDecimal getTaxRateForSku(String sku,String zipCode, String state, String taxServiceUrl){
		logger.info("Inside SkuTaxCalculation.getTaxRateForSku method");
		BigDecimal taxRate = new BigDecimal(0);
		
		try
		{
		//String taxServiceUrl = getTaxServiceUrl(OrderfulfillmentConstants.TAX_SERVICE_KEY);
		logger.info("tax service URL : " + taxServiceUrl);
		SimpleRestClient restClient = new SimpleRestClient(taxServiceUrl, "",
				"", false);

		if (null == sku) {
			return taxRate;
		}
		String payload = new StringBuilder().append("{\"sku\":\"")
				.append(sku)
				.append("\",\"zipcode\":\"")
				.append(zipCode)
				.append("\",\"state\":\"")
				.append(state)
				.append("\"}")
				.toString();
		logger.info("calling tax service...");
		String response = restClient.post("application/json", payload);
		JsonParser parser = new JsonParser();
		JsonElement jsonResponse = parser.parse(response);
		JsonObject jsonObject = jsonResponse.getAsJsonObject();
		if (jsonObject.has("taxRate")) {
			taxRate = new BigDecimal(jsonObject.get("taxRate").getAsDouble()/100);
			logger.info("calculated tax : " + taxRate);
		
		}
		
		}catch( MalformedURLException mue){
			logger.error("Error occurred while calling the tax tax service to calculate tax : ", mue);
		}catch(Exception e){
			logger.error("Error occurred while calculating the tax : ", e);
		}

		return taxRate;
	}
	
	  
}
