package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.aop.LoggingAdvice;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;

public class AOPTest {
	public static void main(String[] args) {
		System.out.println("loading applicationContext.xml");
		try {
			LoggingAdvice loggingAdvice = (LoggingAdvice)MPSpringLoaderPlugIn.getCtx().getBean("loggingAdvice");
			
			SoLoggingVo soLoggingVo = new SoLoggingVo();
			soLoggingVo.setNewValue("xyz");
			soLoggingVo.setOldValue("abc");
			soLoggingVo.setServiceOrderNo("001-6434041185-11");
			soLoggingVo.setValue("junk");
			soLoggingVo.setValueName("junk again");
			soLoggingVo.setChangeType(1);
			
			//loggingAdvice.insertLog(hmArguments, templateId)Change(soLoggingVo);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
