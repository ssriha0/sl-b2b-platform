package com.newco.marketplace.test;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;

public class ServiceOrderNoteTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SOMonitorDelegateImpl test = new SOMonitorDelegateImpl();
		ServiceOrderNote snote = new ServiceOrderNote();
		snote.setNote("This this is harish");
		snote.setSubject("This is a subject");
		//test.serviceOrderAddNote(2, snote);
	}
}
