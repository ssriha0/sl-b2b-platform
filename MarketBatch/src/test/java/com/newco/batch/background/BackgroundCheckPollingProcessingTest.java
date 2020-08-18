package com.newco.batch.background;


import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.newco.batch.background.vo.BackgroundCheckStatusVO;
import com.newco.marketplace.business.iBusiness.audit.IAuditBusinessBean;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.vo.provider.BackgroundChkStatusVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;


public class BackgroundCheckPollingProcessingTest{
	
	BackgroundCheckPollingProcess process;
	IAuditBusinessBean myAuditBusinessBean;
	VendorResourceDao vendorResourceDao;
	BackgroundCheckStatusVO myBackgroundChkStatusVO;
	String bgCheckStatus;
	
	@Test
	public void setChangeCommentTest(){
		process = new BackgroundCheckPollingProcess();
		TMBackgroundCheckVO previous=new TMBackgroundCheckVO();
		TMBackgroundCheckVO next=new TMBackgroundCheckVO();

		process.setChangeComment(previous, next);
		System.out.println(next.getChangedComment());
		Assert.assertEquals(next.getChangedComment(),"");
	}
	
	@Test
	public void processNewLineTest(){
		process = new BackgroundCheckPollingProcess();
		try {
			String inputLine="AGUIA02160513|SERV601790681|AGUIA02160513|AGUIA02160513|JOHN|BRANCO|AGUIAR|||P|O|Y|2014-09-19|2016-09-16||";
					
			BackgroundCheckStatusVO aBackgroundCheckStatusVO = new BackgroundCheckStatusVO();
			aBackgroundCheckStatusVO=process.parseLineNew(inputLine);
			
			Assert.assertEquals(aBackgroundCheckStatusVO.getPlusoneKey(),"AGUIA02160513");

		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getDaysBetweenDatesTest(){
		process = new BackgroundCheckPollingProcess();
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.YEAR, 2014);
		calender.set(Calendar.MONTH, 10);
		calender.set(Calendar.DATE, 16);
		 
		Date today = calender.getTime();		
		Calendar c = Calendar.getInstance();
		 c.set(Calendar.YEAR, 2014);
		 c.set(Calendar.MONTH, 10);
		 c.set(Calendar.DATE, 26);
		 Date reverficationDate = c.getTime();
		 
	   Double difference=process.getDaysBetweenDates(today, reverficationDate);
	   Assert.assertEquals(difference.intValue(),10);

		
	}
	@Test
	public void auditProvider(){
		bgCheckStatus="";
		process = new BackgroundCheckPollingProcess();
		//myBackgroundChkStatusVO.setTechId("10202");
		
		BackgroundChkStatusVO backgroundCheckStatusVO;
		//when(vendorResourceDao.insertBackgroundError(backgroundCheckStatusVO));
		process.auditProvider(myBackgroundChkStatusVO, bgCheckStatus);
        System.out.println("ok");
		
		
		
		
	}
	
	/*@Test
	public void  convertDtotoVo(){
		process = new BackgroundCheckPollingProcess();
		BackgroundCheckStatusVO myBackgroundCheckStatusVO=new  BackgroundCheckStatusVO();
		myBackgroundCheckStatusVO.setPlusoneKey("AGUIA02160513");
		BackgroundChkStatusVO  result= process.convertDtotoVo(myBackgroundCheckStatusVO);
		Assert.assertEquals(myBackgroundCheckStatusVO.getPlusoneKey(),result.getPlusoneKey());

		
	}*/


	
	
	
	
	
	
}
