package com.newco.marketplace.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ReasonLookupVO;

public class RejectReasonCodeTest extends TestCase{
	
	
	@Override
	protected void setUp() throws Exception{
		}
	public void testRejectCodeListTest(){
		ArrayList arrReasonCodeList = null;
		ReasonLookupVO reasonLkpVo = null;
		IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		try{																
			arrReasonCodeList = serviceOrderBO.queryListRejectReason();
			for (int i=0;i<arrReasonCodeList.size();i++){
				reasonLkpVo = (ReasonLookupVO)arrReasonCodeList.get(i);
				System.out.println("id : " + reasonLkpVo.getId());
				System.out.println("desc :" + reasonLkpVo.getDescr());
				System.out.println("order :" + reasonLkpVo.getSort_order());
				
			}
		assertTrue(true);
		}catch(Exception e){
			System.out.println("Error in executing testcase testRejectCodeListTest :" + e.getMessage());
		}
	}
	public static void main(String args[]){

	}
}
