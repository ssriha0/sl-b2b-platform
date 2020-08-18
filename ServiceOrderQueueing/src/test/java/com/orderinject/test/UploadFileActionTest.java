package com.orderinject.test;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.orderinject.UploadFileAction;

public class UploadFileActionTest {
   private static Logger logger = Logger.getLogger(UploadFileActionTest.class);
   private static String expectedError ="Invalid correlation id selection." ;
   private UploadFileAction action;
   
   @Test
   public void validateFile(){
	   List<String> actualErrorList = null;
	   String error = null;
	   action = new UploadFileAction();
	   action.setCorrelationId("-1");
	   action.setEnvironmentQue("190833");
	   action.setUploadFileName("Orderinjection.xml");
	   try{
		    action.validate();
		    Map<String, List<String>> validationError = action.getFieldErrors();
		    actualErrorList = validationError.get("correlationId");
		    error = actualErrorList.get(0);
	   }catch (Exception e) {
		logger.error("Exception in validating file:"+ e.getMessage());
	}
	   Assert.assertEquals(expectedError, error);
   }
}
