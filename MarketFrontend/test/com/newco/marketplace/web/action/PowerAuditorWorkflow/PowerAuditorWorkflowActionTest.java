package com.newco.marketplace.web.action.PowerAuditorWorkflow;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.action.provider.PowerAuditorWorkflowAction;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;

public class PowerAuditorWorkflowActionTest {
	private PowerAuditorWorkflowAction action;
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;

	public String firmError;

	@Before
	public void setUp() {
		
		action = new PowerAuditorWorkflowAction();
		powerAuditorWorkflowDelegate=mock(IPowerAuditorWorkflowDelegate.class);
		action.setPowerAuditorWorkflowDelegate(powerAuditorWorkflowDelegate);

	}
	@Test
	public void getGridData() throws DelegateException{
        String firm_id = "10202";
        Integer validFirmIdOrNot = 1;
       
        when(powerAuditorWorkflowDelegate.getValidFirmId(firm_id)).thenReturn(validFirmIdOrNot);
        validFirmIdOrNot = powerAuditorWorkflowDelegate.getValidFirmId(firm_id);
        Assert.assertEquals(1, validFirmIdOrNot);
	}
	


}


