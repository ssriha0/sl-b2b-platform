package com.newco.marketplace.webservices.test.provider;

import java.util.ArrayList;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.request.providerSearch.ProviderSearchRequest;
import com.newco.marketplace.dto.response.providerSearch.ProviderSearchResponse;
import com.newco.marketplace.webservices.sei.providerSearch.ProviderSearchSEI;


public class ProviderCompleteTest { 
	public static void main(String[] args) {
		ProviderSearchSEI providerSearchSEI =(ProviderSearchSEI)MPSpringLoaderPlugIn.getCtx().getBean("providerSearchImpl");
		ProviderSearchRequest request = new ProviderSearchRequest();
		request.setBuyerZipCode(60527);
		ArrayList<Integer> listOfIds = new ArrayList<Integer>();
		listOfIds.add(Integer.valueOf(11));
		//listOfIds.add(102);
		//listOfIds.add(310);
		request.setSkillNodeIds(listOfIds);
		//CancelSOResponse response = new CancelSOResponse();
		ProviderSearchResponse response = providerSearchSEI.getProviderList(request);
		System.out.println("final response="+response);
		System.out.println("response size="+response.getProviderResult().size());
		/*if(response.isHasError())
		{
			System.out.println(response.getCode()+" "+response.getSubCode());
			String []msg = response.getMessages();
			for(int i=0;i<msg.length;i++)
				System.out.println(" "+msg[i].toString());
		}
		else
			System.out.println("The Result of the cancellation is "+response.getOrderStatus()+"**********************");
		*/
	}
}

