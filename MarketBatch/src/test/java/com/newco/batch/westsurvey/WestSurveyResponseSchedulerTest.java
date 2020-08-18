/**
 * 
 */
package com.newco.batch.westsurvey;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.businessImpl.survey.SurveyBOImpl;
import com.newco.marketplace.business.businessImpl.westsurvey.WestSurveyBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.dto.vo.survey.WestSurveyVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author karthik_hariharan01
 *
 */
public class WestSurveyResponseSchedulerTest {
	
	public static final Properties bootstrapConfig = new Properties();
	private ServiceOrderBO serviceorderBO;
	private SurveyBOImpl surveyBO;
	private SurveyVO surveyVO;
	private SurveyVO surveyVOOne;
	private WestSurveyBO westSurveyBO;
	WestSurveyBO bo = new WestSurveyBO();
	
	


	@Test
	public void UpdateWestSurveyConversionValuesTest(){
		Class<? extends WestSurveyBO> classObj=bo.getClass();
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setIntValueQuestion2(6);
		vo.setIntValueQuestion3(7);
		vo.setIntValueQuestion4(2);
		vo.setIntValueQuestion5(2);
		vo.setIntValueQuestion6(2);
		
		try { 
			Method updateWestSurveyConversionValues = classObj.getDeclaredMethod("updateWestSurveyConversionValues", WestSurveyVO.class);
			updateWestSurveyConversionValues.setAccessible(true);
			vo = (WestSurveyVO) updateWestSurveyConversionValues.invoke(bo, vo);
			
			
		} catch (SecurityException e) {
			e.printStackTrace();
			//if exception occurs, assert fail
			Assert.assertTrue(false);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertEquals(vo.getTimelinessRating(), new Integer(4));
		Assert.assertEquals(vo.getCommunicationsRating(), new Integer(4));
		Assert.assertEquals(vo.getProfessionalRating(), new Integer(4));
		Assert.assertEquals(vo.getQualityRating(), new Integer(3));
		Assert.assertEquals(vo.getValueRating(), new Integer(3));
		Assert.assertEquals(vo.getCleanlinessRating(), new Integer(3));
	}
	
	@Test
	public void invalidQ2ValueTest_1(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("asa");
		vo.setQuestion3("7");
		vo.setQuestion4("1");
		vo.setQuestion5("2");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void invalidQ2ValueTest_2(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("0");
		vo.setQuestion3("7");
		vo.setQuestion4("1");
		vo.setQuestion5("2");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void invalidQ2ValueTest_3(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2(null);
		vo.setQuestion3("7");
		vo.setQuestion4("1");
		vo.setQuestion5("2");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void invalidQ3ValueTest(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("8");
		vo.setQuestion3("12#");
		vo.setQuestion4("1");
		vo.setQuestion5("2");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void invalidQ4ValueTest(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("8");
		vo.setQuestion3("6");
		vo.setQuestion4("@#@@#");
		vo.setQuestion5("2");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void invalidQ5ValueTest(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("8");
		vo.setQuestion3("6");
		vo.setQuestion4("8");
		vo.setQuestion5("8");
		vo.setQuestion6("23sda");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	
	@Test
	public void Q2greaterThan10Test(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("11");
		vo.setQuestion3("6");
		vo.setQuestion4("1");
		vo.setQuestion5("1");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void Q3greaterThan10Test(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("8");
		vo.setQuestion3("14");
		vo.setQuestion4("2");
		vo.setQuestion5("1");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void Q4greaterThan2Test(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("8");
		vo.setQuestion3("7");
		vo.setQuestion4("3");
		vo.setQuestion5("1");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void Q5greaterThan2Test(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("8");
		vo.setQuestion3("7");
		vo.setQuestion4("1");
		vo.setQuestion5("3");
		vo.setQuestion6("2");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void Q6greaterThan2Test(){
	
		WestSurveyVO vo = new WestSurveyVO();
		
		vo.setQuestion2("8");
		vo.setQuestion3("7");
		vo.setQuestion4("2");
		vo.setQuestion5("1");
		vo.setQuestion6("10");
		
		String soId=null;
		try {
			soId=bo.saveSurveyResponse(vo, new Integer(1000));
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNull(soId);
		
		
	}
	
	@Test
	public void Q6nullTest(){

		WestSurveyVO vo = new WestSurveyVO();
		vo.setIntValueQuestion2(6);
		vo.setIntValueQuestion3(7);
		vo.setIntValueQuestion4(2);
		vo.setIntValueQuestion5(2);
		vo.setIntValueQuestion6(null);
		Class<? extends WestSurveyBO> classObj=bo.getClass();
		ServiceOrder so=new ServiceOrder();
		
			
			try {
				
			Method updateWestSurveyConversionValues = classObj.getDeclaredMethod("updateWestSurveyConversionValues", WestSurveyVO.class);
			updateWestSurveyConversionValues.setAccessible(true);
			vo = (WestSurveyVO) updateWestSurveyConversionValues.invoke(bo, vo);
						
			Assert.assertEquals(vo.getCleanlinessRating(),new Integer(3));
			
		} catch (SecurityException e) {
			e.printStackTrace();
			//if exception occurs, assert fail
			Assert.assertTrue(false);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
				
	}
}



