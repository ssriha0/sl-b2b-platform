package com.newco.marketplace.test;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ReportProblemTest extends TestCase{
	
	String strSoId = "";
	int intSubStatusId = 0;
	String strComment = "";
	String strCommentType = "";
	int intEntityId = 0;
	int intRoleType = 0;
	String pbDesc = "Test";
	String loggedInUser = "blars002";
	String pbDetails = "";
	
	
	@Override
	protected void setUp() throws Exception{
		intSubStatusId = 2;
		strComment = "There is problem here";
		strSoId = "001-3073-7390-7896";
		intEntityId = 12;
		intRoleType = 3;
		pbDetails = "This is problem";		
		}
	public void testRejectServiceOrderTest(){
		IServiceOrderBO ichangeBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
		SecurityContext securityContext = null;
		try{
		ProcessResponse pr1 = ichangeBO.reportProblem(strSoId, intSubStatusId, strComment, intEntityId, intRoleType, pbDesc, loggedInUser,false,securityContext);
		System.out.println("Message : " + pr1.getMessages().get(0));
		
		System.out.println("Now getting details of problem reported");
		ProblemResolutionSoVO pbResVo = ichangeBO.getProblemDesc(strSoId);
		if (pbResVo != null)
			System.out.println("pbDetails : " + pbResVo.toString());
		else
			System.out.println("pbResVo is null");
		
		
		ProcessResponse pr2 = ichangeBO.reportResolution(strSoId, intSubStatusId, strComment, intEntityId, intRoleType, pbDesc, pbDetails, loggedInUser,securityContext);
		System.out.println("Message : " + pr2.getMessages().get(0));
		
		assertTrue(true);
		
		}catch(Exception e){
			System.out.println("Error in executing testcase testRejectServiceOrderTest :" + e.getMessage());
		}
	}
	public static void main(String args[]){

	}
}
