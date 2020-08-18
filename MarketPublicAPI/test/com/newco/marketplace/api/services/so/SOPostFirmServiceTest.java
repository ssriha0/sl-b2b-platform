package com.newco.marketplace.api.services.so;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.api.services.so.SOPostFirmService;
@Ignore
public class SOPostFirmServiceTest {
    private SOPostFirmService service;
    private ServiceOrder soDetails;
    private ProcessResponse response;
    
	@Ignore
	public void validateServiceOrder() {
		service=new SOPostFirmService();	
	    soDetails = new ServiceOrder();
	    response = null;
	    //To Test Correct Date
	    //StartDate is 2017-07-07 00:00:00=1499331544000ms
		//EndDate is 2017-07-08 00:00:00=1499504344000ms
	    Timestamp startDate=new Timestamp(Long.parseLong("1499331544000"));
		Timestamp endDate=new Timestamp(Long.parseLong("1499504344000"));
		//To Test incorrect date
	    //StartDate is 2012-07-07 00:00:00=1341651544000
		//EndDate is 2012-07-08 00:00:00=1341737944000
		//Timestamp startDate=new Timestamp(Long.parseLong("1341651544000"));
		//Timestamp endDate=new Timestamp(Long.parseLong("1341737944000"));
		soDetails.setServiceDate1(startDate);
		soDetails.setServiceDate2(endDate);
		soDetails.setServiceTimeStart("05:30 AM");
		soDetails.setServiceTimeEnd("02:00 PM");	
		response=service.validateServiceOrder(soDetails,soDetails.getBuyerId());
		try {
			Assert.assertEquals("1", response.getCode());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
