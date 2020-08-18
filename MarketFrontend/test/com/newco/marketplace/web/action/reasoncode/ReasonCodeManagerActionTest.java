package com.newco.marketplace.web.action.reasoncode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.servicelive.domain.reasoncodemgr.ReasonCode;

public class ReasonCodeManagerActionTest 
{
	private ReasonCodeManagerAction action;
	@Test
	public void testSetCreatedDate(){
		action = new ReasonCodeManagerAction();
		List<ReasonCode> list = new ArrayList<ReasonCode>();
		ReasonCode rc = new ReasonCode();
		rc.setReasonCode("Reason");
		rc.setReasonCodeType("1");
		rc.setCreatedDate(new Date());
		list.add(rc);
		
		action.setCreatedDate(list);
	}

}
