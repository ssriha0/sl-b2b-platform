package com.newco.batch.background;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.persistance.daoImpl.providerBackground.ProviderBackgroundCheckDaoImpl;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.provider.ProviderBackgroundCheckVO;


public class BackgroundCheckRecertificationProcessTest{
	
	BackgroundCheckRecertificationProcess process;
	ProviderBackgroundCheckDaoImpl providerDaoImpl;
	
	@Test
	public void getProviderInfoNotificationDataTest(){
		process = new BackgroundCheckRecertificationProcess();
		providerDaoImpl = mock(ProviderBackgroundCheckDaoImpl.class);
		process.setProviderBackgroundCheckDao(providerDaoImpl);
		
		
		List<ProviderBackgroundCheckVO> backgroundCheckList = new ArrayList<ProviderBackgroundCheckVO>();
		ProviderBackgroundCheckVO providerCheckVO = new ProviderBackgroundCheckVO();
		Date d = new Date();
		java.sql.Date reveriDate = new java.sql.Date(d.getTime());
		providerCheckVO.setSpnName("New SPN");
		providerCheckVO.setNotificationId(30);
		providerCheckVO.setResourceFirstName("Brain");
		providerCheckVO.setResourceLastName("ScBrain");
		providerCheckVO.setResourceId(10254);
		providerCheckVO.setVendorId(10202);
		providerCheckVO.setReVerficationDate(reveriDate);
		
		ProviderBackgroundCheckVO providerCheckVO1 = new ProviderBackgroundCheckVO();
		java.sql.Date reveriDate1 = new java.sql.Date((new GregorianCalendar(2014, 10, 15)).getTime().getTime());
		providerCheckVO1.setSpnName(" SHC RE Facilities");
		providerCheckVO1.setNotificationId(7);
		providerCheckVO1.setResourceFirstName("james");
		providerCheckVO1.setResourceLastName("RoJames");
		providerCheckVO1.setResourceId(10302);
		providerCheckVO1.setVendorId(10202);
		providerCheckVO1.setReVerficationDate(reveriDate1);
		
		backgroundCheckList.add(providerCheckVO);
		backgroundCheckList.add(providerCheckVO1);
		
		List<ProviderBackgroundCheckVO> backgroundCheckListResult = new ArrayList<ProviderBackgroundCheckVO>();
		try {
			when(providerDaoImpl.getProviderInfoForNotification()).thenReturn(backgroundCheckList);
			backgroundCheckListResult = providerDaoImpl.getProviderInfoForNotification();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(backgroundCheckList, backgroundCheckListResult);
	}
	
	@Test
	 public void checkFor30DaysTest(){
		 process = new BackgroundCheckRecertificationProcess();
		 DateUtils utils = mock(DateUtils.class);
		 
		 Calendar c = Calendar.getInstance();
		 c.set(Calendar.YEAR, 2014);
		 c.set(Calendar.MONTH, 10);
		 c.set(Calendar.DATE, 14);
		 Date reverficationDate = c.getTime();
		 System.out.println(reverficationDate);
		 
		
		 boolean returnValue = false;
		 try{
			 returnValue = process.checkFor30Days(reverficationDate);
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertFalse(returnValue);
	}
	
}
