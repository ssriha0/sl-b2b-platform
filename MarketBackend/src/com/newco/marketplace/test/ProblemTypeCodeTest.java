package com.newco.marketplace.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemLookupVO;

public class ProblemTypeCodeTest extends TestCase{
	int wfStateId = 0;
	
	@Override
	protected void setUp() throws Exception{
		wfStateId = 170;
		}
	public void testProblemTypeListTest(){
		ArrayList arrProblemTypeList = null;
		ProblemLookupVO problemLkpVo = null;
		IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		try{																
			//arrProblemTypeList = serviceOrderBO.queryListSoProblem(wfStateId);
			for (int i=0;i<arrProblemTypeList.size();i++){
				problemLkpVo = (ProblemLookupVO)arrProblemTypeList.get(i);
				System.out.println("substatusid : " + problemLkpVo.getSo_substatus_id());
				System.out.println("desc :" + problemLkpVo.getDescr());
				System.out.println("order :" + problemLkpVo.getSort_order());
				
			}
		assertTrue(true);
		}catch(Exception e){
			System.out.println("Error in executing testcase testProblemTypeListTest :" + e.getMessage());
		}
	}
	public static void main(String args[]){

	}
}
