package com.newco.marketplace.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.audit.IAuditProfileBO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.vo.audit.AuditVO;

public class AuditProfileTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AuditVO auditVo = new AuditVO();
		String [] str = {"1"};
		
		auditVo.setAuditKeyId(5094);
		auditVo.setAuditLinkId(1);
		//auditVo.setAuditTaskId();
		auditVo.setReasonCodeIds(str);
		auditVo.setResourceId(0);
		auditVo.setReviewedBy("auditor10");
		auditVo.setVendorId(5094);
		//auditVo.setWfState();
		auditVo.setWfStateId(-1);
		auditVo.setWfStatusId(1);
		
		
		System.out.println("Loaded applicationContext.xml");
		ApplicationContext ctx;

		ctx = new ClassPathXmlApplicationContext("resources/spring/applicationContextJunit.xml");
		IAuditProfileBO auditProfileBO = (IAuditProfileBO)ctx.getBean("auditProfileBo");
		//IAuditProfileBO auditProfileBO = (IAuditProfileBO)BeanFactory.getBean("auditProfileBo");
		
		try {
			auditProfileBO.updateWFStatusAndReasonCodes(auditVo, Boolean.FALSE);
		} catch (AuditException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done executing");
	}

}
