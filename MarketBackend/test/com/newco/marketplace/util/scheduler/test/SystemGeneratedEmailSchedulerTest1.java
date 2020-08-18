package com.newco.marketplace.util.scheduler.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.systemgeneratedemail.SystemGeneratedEmailBOImpl;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EventTemplateVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.ProviderDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOContactDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOLoggingVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOScheduleDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.systemgeneratedemail.ISystemGeneratedEmailDao;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.daoImpl.systemgeneratedemail.SystemGeneratedEmailDaoImpl;




public class SystemGeneratedEmailSchedulerTest1{

	private static final Logger LOGGER = Logger.getLogger(SystemGeneratedEmailSchedulerTest1.class);
	SystemGeneratedEmailBOImpl bo;
	SystemGeneratedEmailDaoImpl dao= new SystemGeneratedEmailDaoImpl();
	AlertDaoImpl alertDao = new AlertDaoImpl();
	
	//TODO: Verify
	private ISystemGeneratedEmailDao emailDao;
	List<SOLoggingVO> soLoggingVOS= new ArrayList<SOLoggingVO>();
	List<EventTemplateVO> eventTemplateVOs = new ArrayList<EventTemplateVO>();
	
	@Before
	public void setUp() {

		bo= new SystemGeneratedEmailBOImpl();
		emailDao= mock(SystemGeneratedEmailDaoImpl.class);
		bo.setiSystemGeneratedEmailDao(emailDao);
		SOLoggingVO soLoggingVO = new SOLoggingVO();
		soLoggingVO.setSoLoggingId(730301317L);
		soLoggingVO.setServiceOrderNo("571-6861-1664-15");
		soLoggingVO.setActionId(36);
		soLoggingVOS.add(soLoggingVO);
		EventTemplateVO eventTemplateVO = new EventTemplateVO();
		eventTemplateVO.setActionId(3);
		eventTemplateVO.setBuyerId(3000);
		eventTemplateVO.setEventId(4);
		eventTemplateVO.setTemplateId(350);
		eventTemplateVO.setEventType("ORDER_CREATED");
		eventTemplateVOs.add(eventTemplateVO);
	}
	
	
	
	/*@Ignore()
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
		Assert.assertTrue(true);
	}
	
	@Ignore
	@Test
	public void processReminderEmail(){
		Class<? extends SystemGeneratedEmailBOImpl> classObj=bo.getClass();
		
		try {
				Method processSystemGeneratedEmail = classObj.getDeclaredMethod("processReminderEmail");
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
		Assert.assertTrue(true);
	}
	*/
	//TODO : verify
	/*@Ignore("not yet ready , Please ignore.")
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
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(false);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		Assert.assertTrue(true);
	}*/
	
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
					when(alertDao.addAlertTask((AlertTask) Arrays.asList(alertTask))).thenReturn(0L);
					bo.addAlertListToQueue(Arrays.asList(alertTask));
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
	
	@Test
	public void getSOLoggingDetails(){
		Set<Integer> buyerIds= new HashSet<Integer>();
		Set<Integer> actionIds= new HashSet<Integer>();
		List<SOLoggingVO> soLoggingVO=new ArrayList<SOLoggingVO>();
		buyerIds.add(3000);
		buyerIds.add(3333);
		actionIds.add(1);
		actionIds.add(15);
		Long counterValue= 730301533L;
		Long maxValue = 730301537L;
		try {
			when(emailDao.getSOLoggingDetails(buyerIds, actionIds, counterValue, maxValue)).thenReturn(soLoggingVOS);
			soLoggingVO=bo.getSOLoggingDetails(buyerIds, actionIds,counterValue, maxValue);
			LOGGER.info(soLoggingVO.get(0).getServiceOrderNo());
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(!soLoggingVO.isEmpty());
		
	}
	
	
	@Test
	public void getBuyerEventTemplateDetails(){
		List<EventTemplateVO> eventDataVO=new ArrayList<EventTemplateVO>();
		try {
			when(emailDao.getEventTemplateDetailsForBuyer()).thenReturn(eventTemplateVOs);
			eventDataVO=bo.getBuyerEventTemplateDetails();
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(!eventDataVO.isEmpty());
		
		
	}
	
	
	@Test
	public void getEmailDataForBuyer(){
		List<EmailDataVO> eventTemplateVO=new ArrayList<EmailDataVO>();
		List<EmailDataVO> result=new ArrayList<EmailDataVO>();
		Set<Integer> buyerIds= new HashSet<Integer>();
		buyerIds.add(3000);
		buyerIds.add(3333);
		eventTemplateVO.add(populateEmailData("BUYER_NAME", "Nest"));
		eventTemplateVO.add(populateEmailData("BUYER_SIGNATURE", "Thanks"));
		try {
			when(emailDao.getEmailDataForBuyer(buyerIds)).thenReturn(eventTemplateVO);
			result=bo.getEmailDataForBuyer(buyerIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertTrue(!eventTemplateVO.isEmpty());
		
		
	}
	
	
	@Test
	public void getEmailDataForBuyerEvent(){
		List<EmailDataVO> eventTemplateVO=new ArrayList<EmailDataVO>();
		Set<Integer> buyerEventIds= new HashSet<Integer>();
		buyerEventIds.add(113);
		buyerEventIds.add(114);
		List<EmailDataVO> emailData=new ArrayList<EmailDataVO>();
		emailData.add(populateEmailData("BUYER_SIGNATURE", "Thanks"));
		emailData.add(populateEmailData("BUYER_SIGNATURE", "Thanks Nest"));
		try {
			when(emailDao.getEmailDataForBuyer(buyerEventIds)).thenReturn(emailData);
			eventTemplateVO=bo.getEmailDataForBuyerEvent(buyerEventIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertTrue(!eventTemplateVO.isEmpty());
		
		
	}
	
	
	@Test
	public void getEmailParameterForTemplate(){
		List<EmailDataVO> eventTemplateVO=new ArrayList<EmailDataVO>();
		List<EmailDataVO> emailData=new ArrayList<EmailDataVO>();
		Set<Integer> templateIds= new HashSet<Integer>();
		EmailDataVO emailData1 = new EmailDataVO();
		EmailDataVO emailData2 = new EmailDataVO();
		EmailDataVO emailData3 = new EmailDataVO();
		EmailDataVO emailData4 = new EmailDataVO();
		templateIds.add(356);
		templateIds.add(351);
		emailData1.setTemplateId(356);
		emailData1.setParamKey("LAST_NAME");
		emailData.add(emailData1);
		emailData2.setTemplateId(356);
		emailData2.setParamKey("FIRST_NAME");
		emailData.add(emailData2);
		emailData3.setTemplateId(351);
		emailData3.setParamKey("LAST_NAME");
		emailData.add(emailData3);
		emailData4.setTemplateId(351);
		emailData4.setParamKey("FIRST_NAME");
		emailData.add(emailData4);
		
		try {
			when(emailDao.getEmailParameterForTemplate(templateIds)).thenReturn(emailData);
			eventTemplateVO=bo.getEmailParameterForTemplate(templateIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertTrue(!eventTemplateVO.isEmpty());
		
		
	}
	
	
	@Test
	public void getServiceOrderContactDetails(){
		String soId="100-0039-3120-19";
		SOContactDetailsVO contactVO= new SOContactDetailsVO();
		SOContactDetailsVO contact= new SOContactDetailsVO();
		contact.setFirstName("JAMES");
		contact.setLastName("COJAMES");
		try {
			when(emailDao.getContactDetailsForServiceOrder(soId)).thenReturn(contact);
			contactVO=bo.getServiceOrderContactDetails(soId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(contactVO);
	}
	
	
	@Test
	public void getServiceOrderScheduleDetails(){
		String soId="308-2817-7891-17";
		List<SOScheduleDetailsVO> contactVO= new ArrayList<SOScheduleDetailsVO>();
		List<SOScheduleDetailsVO> contacts= new ArrayList<SOScheduleDetailsVO>();
		SOScheduleDetailsVO scheduleDetailsVO=new SOScheduleDetailsVO();
		scheduleDetailsVO.setServiceEndDate(getTimestamp("2018-09-28 00:00:00"));
		scheduleDetailsVO.setServiceStartDate(getTimestamp("2018-09-28 00:00:00"));
		scheduleDetailsVO.setServiceStartTime("05:00 PM");
		scheduleDetailsVO.setServiceEndTime("09:00 PM");
		contacts.add(scheduleDetailsVO);
		try {
			when(emailDao.getScheduleDetailsForServiceOrder(soId)).thenReturn(contacts);
			contactVO=bo.getServiceOrderScheduleDetails(soId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(!contactVO.isEmpty());
	}
	
	 
	@Test
	public void getProviderDetailsForVendorId(){
		Integer vendorId=10202;
		ProviderDetailsVO result= new ProviderDetailsVO();
		ProviderDetailsVO detailsVO=new ProviderDetailsVO();
		try {
			when(emailDao.getProviderDetailsForVendorId(vendorId)).thenReturn(detailsVO);
			result=bo.getProviderDetailsForVendorId(vendorId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(result);
	}
	


private Timestamp getTimestamp(String timestamp){

	Timestamp timeStamp = Timestamp.valueOf(timestamp);
	return timeStamp;
}
}
