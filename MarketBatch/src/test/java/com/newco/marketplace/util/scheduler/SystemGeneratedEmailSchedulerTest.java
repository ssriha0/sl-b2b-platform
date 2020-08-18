/**
 * 
 */
package com.newco.marketplace.util.scheduler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.systemgeneratedemail.SystemGeneratedEmailBOImpl;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.daoImpl.systemgeneratedemail.SystemGeneratedEmailDaoImpl;
import com.newco.marketplace.util.constants.SystemGeneratedEmailConstants;

import junit.framework.Assert;

/**
 * @author lprabha
 *
 */
@Ignore
public class SystemGeneratedEmailSchedulerTest {
	
	SystemGeneratedEmailBOImpl bo = new SystemGeneratedEmailBOImpl();
	SystemGeneratedEmailDaoImpl dao= new SystemGeneratedEmailDaoImpl();
	AlertDaoImpl alertDao = new AlertDaoImpl();
	
//	@Ignore("not yet ready , Please ignore.")
	@Test
	public void processSystemGeneratedEmailTest(){
		Class<? extends SystemGeneratedEmailBOImpl> classObj=bo.getClass();
		
		try {
				Method processSystemGeneratedEmail = classObj.getDeclaredMethod("processSystemGeneratedEmail");
				processSystemGeneratedEmail.setAccessible(true);
				processSystemGeneratedEmail.invoke(bo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			}			
	}
	
//	@Ignore("not yet ready , Please ignore.")
	@Test
	public void updateSystemGeneratedEmailCounter(){
		Class<? extends SystemGeneratedEmailDaoImpl> classObj=dao.getClass();
		
		try {
				Method updateSystemGeneratedEmailCounter = classObj.getDeclaredMethod("updateSystemGeneratedEmailCounter");
				updateSystemGeneratedEmailCounter.setAccessible(true);
				updateSystemGeneratedEmailCounter.invoke(dao,34828608);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			}	
		Assert.assertTrue(true);
	}
	
//	@Ignore("not yet ready , Please ignore.")
	@Test
	public void getEmailAlertTask(){
		HashMap<String, Object> vContext = new AOPHashMap();
		List<EmailDataVO> emailDataVO= new ArrayList<EmailDataVO>();
		Class<? extends SystemGeneratedEmailBOImpl> classObj=bo.getClass();
		emailDataVO.add(populateEmailData("FACEBOOK_LINK", "https://www.facebook.com/servicelive/"));
		emailDataVO.add(populateEmailData("BUYER_NAME", "Test_3333/"));
		emailDataVO.add(populateEmailData("BUYER_LOGO", "service.png"));
		emailDataVO.add(populateEmailData("TWITTER_LINK", "https://twitter.com/servicelive"));
		emailDataVO.add(populateEmailData("INSTAGRAM_LINK", "https://www.instagram.com/sears/"));
		emailDataVO.add(populateEmailData("PINTEREST_LINK", "https://www.pinterest.com/Sears/"));
		for (EmailDataVO emailDatum : emailDataVO) 
				vContext.put(emailDatum.getParamKey(), emailDatum.getParamValue());
		AlertTask alertTask = new AlertTask();
		alertTask.setTemplateInputValue(vContext.toString());

		
				try {
					alertDao.addAlertTask((AlertTask) Arrays.asList(alertTask));
				} catch (DataServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Assert.assertTrue(false);
				}
				Assert.assertTrue(true);
			}	
		
	private EmailDataVO populateEmailData(String key,String value){
		EmailDataVO emailDataVO= new EmailDataVO();
		emailDataVO.setParamKey(key);
		emailDataVO.setParamValue(value);
		return emailDataVO;
	}

}