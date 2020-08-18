package com.newco.marketplace.test;

import java.sql.Timestamp;
import java.util.List;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ProviderConditionalOfferTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		
		String soID = "001-6434041185-11";
		Integer resourceID = 12;
		Integer vendorID = 1;
		Timestamp conditionalDate1 = null;//Timestamp.valueOf("2007-10-18 00:00:00");
		Timestamp conditionalDate2 = null;//Timestamp.valueOf("2007-10-28 00:00:00");
		String conditionalStartTime = null;//"10:00 AM";
		String conditionalEndTime = null;//"3:00 PM";
		Timestamp conditionalExpirationDate = Timestamp.valueOf("2007-10-16 15:30:00");
		Double incrSpendLimit = 25.00;
		List<Integer> selectedCounterOfferReasonsList = null;
		ProcessResponse response = null;
		SecurityContext securityContext = null;
		response = serviceOrderBO.processCreateConditionalOffer(soID,resourceID ,vendorID,conditionalDate1, conditionalDate2, conditionalStartTime, conditionalEndTime, conditionalExpirationDate, incrSpendLimit,selectedCounterOfferReasonsList,securityContext,null);
		
		System.out.println("The message is "+response);
	}

}
