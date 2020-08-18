/*package com.newco.marketplace.test;

import java.sql.Timestamp;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.daoImpl.so.order.ServiceOrderDaoImpl;
import com.newco.marketplace.persistence.daoImpl.survey.SurveyDAOImpl;
import com.sears.os.context.ContextValue;

public class ReleaseSOTest extends TestCase {

	SurveyDAOImpl impl = null;
	public ReleaseSOTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testReleaseSO(){
		// insert a new service order
		insertServiceOrder();
		// update the service order using Request Reschedule method
		boolean success = false;
		String soId = "002-8888-9999-3060";
		String newStartTime = "10:00";
		String newEndTime = "12:00";
		Integer subStatus = new Integer(12);
		Timestamp newStartDate = Timestamp.valueOf("2007-10-30 00:00:00.0");
		Timestamp newEndDate = null;
		IServiceOrderBO serv = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");

		// select service order info using request resched info method
		ServiceOrderRescheduleVO serviceOrder = null;
		try{
			serviceOrder = serv.getRescheduleRequestInfo(soId);
		} catch (BusinessServiceException bse){
			bse.printStackTrace();
		}
		// assert that dates and times for new schedule match
		assertEquals(serviceOrder.getNewDateStart(), newStartDate);
		assertEquals(serviceOrder.getNewDateEnd(), newEndDate);
		assertEquals(serviceOrder.getNewTimeStart(), newStartTime);
		assertEquals(serviceOrder.getNewTimeEnd(), newEndTime);
		// assert that insert was successful
		assertTrue(success);
		
		// clean up database
		deleteServiceOrder();
	}
	
	
	private void insertServiceOrder(){
		ServiceOrder s = new ServiceOrder();
		Buyer buyer = new Buyer();
		s.setBuyer(buyer);
		VendorResource resource = new VendorResource();
		Contact contact = new Contact();
		contact.setContactId(1839);
		s.setAcceptedResource(resource);
		s.getAcceptedResource().setResourceId(31);
	    s.setSoId("002-8888-9999-3060");
		s.getBuyer().setBuyerId(110);
		s.getBuyer().setBuyerContact(contact);
		
		//s.getAcceptedResource().setResourceId(1);
		s.setWfStateId(155);
		s.setBuyerTermsCond("No problems ");
		s.setAcceptedVendorId(19);
		
		ServiceOrderDaoImpl serv = (ServiceOrderDaoImpl)MPSpringLoaderPlugIn.getCtx().getBean(MPConstants.SERVICE_BUSINESSBEAN);
		
		try{
			// calling insert method in ServiceOrderDaoImpl
			serv.insert(s);
		}catch (Exception ex){
			System.out.print("############"+ ex+"##################");
		}
	}
	
	private void deleteServiceOrder(){
		ServiceOrderDaoImpl serv = (ServiceOrderDaoImpl)MPSpringLoaderPlugIn.getCtx().getBean(MPConstants.SERVICE_BUSINESSBEAN);
		try{
			serv.deleteLogEntry("002-8888-9999-3060");
			serv.deleteSO("002-8888-9999-3060");
		} catch (DataServiceException dse){
			dse.printStackTrace();
		}
	}
	
}
*/