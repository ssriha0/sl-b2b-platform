package com.servicelive.wallet.alert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.dao.IAlertDao;
import com.servicelive.wallet.alert.dao.ITemplateDao;
import com.servicelive.wallet.alert.vo.TemplateVO;


public class AlertBOTest {

   private AlertBO bo;
   private ITemplateDao templateDao;
   private IAlertDao alertDao;
   
   @Test
   public void testSendEscheatmentACHFailuretoProdSupp(){
	   bo = new AlertBO();
	   templateDao = mock(ITemplateDao.class);
	   alertDao = mock(IAlertDao.class);
	   bo.setTemplateDao(templateDao);
	   bo.setAlertDao(alertDao);
	   
	   TemplateVO template = new TemplateVO();
	   template.setTemplateTypeId(3);
	   template.setTemplateFrom("abc");
	   template.setPriority("2");
	   
	   String transId = "123456" ;
	   Double amount =100.00;
	   String returnDesc = "Desc";
	   int templateId = 10;
	   
	   try {
		when(templateDao.getTemplateById(templateId)).thenReturn(template);
		bo.sendEscheatmentACHFailuretoProdSupp(transId, amount, returnDesc, templateId);
	} catch (DataServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

}
