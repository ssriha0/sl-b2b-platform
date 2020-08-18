package com.newco.marketplace.webservices.test;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.jboss.logging.Logger;
import org.junit.Test;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.validation.so.draft.CreateDraftValidator;
public class ServiceOrderValidatorTest {
   private static Logger logger = Logger.getLogger(ServiceOrderValidatorTest.class);
   private static ServiceOrder so;
   private CreateDraftValidator validator;
   
   static {
	   so = new ServiceOrder();
	   so.setBuyerId(1000);
	   so.setSowTitle("This is a test service Order");
	   so.setSoId("123-3453-3345-12");
   }
   
   @Test
   public void validateServiceOrder(){
	   validator = new CreateDraftValidator();
	   Map<String, List<WSErrorInfo>> var = null;
	   List<WSErrorInfo> errorList = null;
	   try{
		   var = validator.validateCreateDraft(so);
		   errorList = var.get("error");
	   }catch (Exception e) {
		logger.error("Exception in validating service order:"+ e.getMessage());
	 }
	   Assert.assertNotNull("There is no validation error", errorList);
   }
}
