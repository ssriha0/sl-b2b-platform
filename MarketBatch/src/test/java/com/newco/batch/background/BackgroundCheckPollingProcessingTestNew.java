package com.newco.batch.background;


import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.newco.batch.background.vo.BackgroundCheckStatusVO;
import com.newco.marketplace.business.iBusiness.audit.IAuditBusinessBean;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;


public class BackgroundCheckPollingProcessingTestNew{
	
	BackgroundCheckPollingProcess process;
	IAuditBusinessBean myAuditBusinessBean;
	VendorResourceDao vendorResourceDao;
	String bgCheckStatus;
    
	public static void main(String[] args) {
		BackgroundCheckPollingProcessingTestNew mainObj = new BackgroundCheckPollingProcessingTestNew();
		BackgroundCheckStatusVO myBackgroundChkStatusVO = mainObj.processRecordTest();
		System.out.println("Verification Date "+ myBackgroundChkStatusVO.getVerificationDate());
		
	}
	
	public BackgroundCheckStatusVO testAudit(){
		BackgroundCheckStatusVO myBackgroundChkStatusVO = processRecordTest();
		return myBackgroundChkStatusVO;
	}
	
	public BackgroundCheckStatusVO processRecordTest(){
		process = new BackgroundCheckPollingProcess();
		BackgroundCheckStatusVO aBackgroundCheckStatusVO =null;
		try {
			String inputLine="BGK06BA015092|SERV601790681|99316|99316|Jesse||HaJesse||Y|Y|Y|Y|2014-06-12|2016-05-09||";
			aBackgroundCheckStatusVO = new BackgroundCheckStatusVO();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
			VendorResourceDao vendorResourceDao = (VendorResourceDao) ctx.getBean("vendorResourceDao");
			myAuditBusinessBean = (IAuditBusinessBean) ctx.getBean("auditBusinessBean");
			DataSourceTransactionManager tm = (DataSourceTransactionManager) ctx.getBean("dsTransactionManagerMain");
			process.setTm(tm);
			process.setVendorResourceDao(vendorResourceDao);
			process.setMyAuditBusinessBean(myAuditBusinessBean);
			aBackgroundCheckStatusVO=process.processRecord(inputLine, aBackgroundCheckStatusVO, true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aBackgroundCheckStatusVO;
	}
	
}
