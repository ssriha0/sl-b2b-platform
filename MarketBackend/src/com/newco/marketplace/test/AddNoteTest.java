package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
public class AddNoteTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddNoteTest testObj = new AddNoteTest();
		testObj.testAddServiceOrderNote();
		testObj.testgetServiceOrderNote();
	}
	
	private void testAddServiceOrderNote() {
		ServiceOrderNote son = new ServiceOrderNote();
		IServiceOrderBO x = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		son.setSoId("001-6434041234-31");
		son.setNote("testing note1");
		son.setSubject("Test subject");
		son.setModifiedBy("tester");
		son.setCreatedByName("tester Name");
		son.setRoleId(2);	
		SecurityContext securityContext = null;
		boolean isEmailToBeSent = true;
		try{
			x.processAddNote(111, 3, "001-6434041234-31", "Test subject", "testing note1", 2, null, null, 1,"0", isEmailToBeSent, false, securityContext);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void testgetServiceOrderNote() {		
		IServiceOrderBO x = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
	}

}
