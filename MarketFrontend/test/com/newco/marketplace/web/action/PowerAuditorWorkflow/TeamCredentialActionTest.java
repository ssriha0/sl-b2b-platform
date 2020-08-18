package com.newco.marketplace.web.action.PowerAuditorWorkflow;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;


public class TeamCredentialActionTest {
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;

	@Before
	public void setUp() {
		powerAuditorWorkflowDelegate=mock(IPowerAuditorWorkflowDelegate.class);
	}
	
	@Test
	public void editCredentilDetails() throws DelegateException{
		
		Integer tempId=32907;
		Integer auditTaskId=null;
		when(powerAuditorWorkflowDelegate.getAuditTaskId("10254",true,tempId,4)).thenReturn(40252);
		auditTaskId = powerAuditorWorkflowDelegate
				.getAuditTaskId("10254",true,tempId,4);
		Assert.assertNotNull(auditTaskId);
		
		AuditTimeVO auditTimeVo = getAuditTimeVo(auditTaskId);
		AuditTimeVO auditTimeSaveVo = new AuditTimeVO();
		auditTimeSaveVo.setAuditTimeLoggingId(1);
		when(powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo)).thenReturn(auditTimeSaveVo);
		auditTimeSaveVo=powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);
		Assert.assertNotNull(auditTimeSaveVo);
		Assert.assertEquals(1,auditTimeSaveVo.getAuditTimeLoggingId());
		
	}
	
	private AuditTimeVO getAuditTimeVo(Integer auditTaskId){
		AuditTimeVO auditTimeVO = new AuditTimeVO();
		auditTimeVO.setAgentId(100081);
		auditTimeVO.setAgentName("rbutl4799");
		auditTimeVO.setAuditTaskId(auditTaskId);
		auditTimeVO.setStartTime(new Date());
		return auditTimeVO;		
	}
}
