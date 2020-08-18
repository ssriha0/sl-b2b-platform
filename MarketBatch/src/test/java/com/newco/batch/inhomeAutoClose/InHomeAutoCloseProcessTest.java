package com.newco.batch.inhomeAutoClose;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.inhomeautoclose.InHomeAutoCloseProcessBOImpl;
import com.newco.marketplace.inhomeautoclose.InHomeAutoCloseProcessBO;
import com.newco.marketplace.inhomeautoclose.vo.InHomeAutoCloseProcessVO;


public class InHomeAutoCloseProcessTest { 
			
		private InHomeAutoCloseProcessBO inhomeautoCloseProcessBO;
		private InHomeAutoCloseProcess process;
		 
		 @Test  
		public void process() throws Exception{
			 inhomeautoCloseProcessBO= mock(InHomeAutoCloseProcessBOImpl.class); 
			 process= new InHomeAutoCloseProcess();
			 process.setInhomeautoCloseProcessBO(inhomeautoCloseProcessBO);
			List<InHomeAutoCloseProcessVO> serviceOrderList= new ArrayList<InHomeAutoCloseProcessVO>();
			List<InHomeAutoCloseProcessVO> test= new ArrayList<InHomeAutoCloseProcessVO>();
			InHomeAutoCloseProcessVO inHomeAutoCloseProcessVO=new InHomeAutoCloseProcessVO(); 
			inHomeAutoCloseProcessVO.setSoId("111-1112-1112");
			inHomeAutoCloseProcessVO.setNoOfRetries(0);		
			test.add(inHomeAutoCloseProcessVO);
		    when(inhomeautoCloseProcessBO.getListOfServiceOrdersToAutoClose()).thenReturn(test) ; 
			Assert.assertNotNull(serviceOrderList);  			
			}
		

		public InHomeAutoCloseProcessBO getInhomeautoCloseProcessBO() {
			return inhomeautoCloseProcessBO;
		}

		public void setInhomeautoCloseProcessBO(
				InHomeAutoCloseProcessBO inhomeautoCloseProcessBO) {
			this.inhomeautoCloseProcessBO = inhomeautoCloseProcessBO;
		}

		
		
		



}
