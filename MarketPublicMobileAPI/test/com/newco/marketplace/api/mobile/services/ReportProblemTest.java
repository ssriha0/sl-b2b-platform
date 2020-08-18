package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.so.reportAProblem.ReportProblemVO;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemRequest;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;

public class ReportProblemTest {

	private NewMobileGenericMapper mobileMapper;
	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericBOImpl newMobileGenericBO;
	private ReportProblemRequest request;
	private ReportProblemVO reportProblemVO;
	private String soId;
	private Integer providerResourceId;
	private Integer roleId;

	@Before
	public void setUp() {
		mobileValidator=new NewMobileGenericValidator();
		mobileMapper=new NewMobileGenericMapper();
		newMobileGenericBO = new NewMobileGenericBOImpl();
		reportProblemVO=new ReportProblemVO();
		request=new ReportProblemRequest();
		request.setProblemCode(12);
		request.setProblemDescription("problemDescription");
		soId="511-4869-1316-95";
		providerResourceId=10254;
		roleId=3;
		reportProblemVO.setSoId(soId);
		reportProblemVO.setResourceId(10254);
		reportProblemVO.setProblemReasonCode(12);
		reportProblemVO.setProblemDescriptionComments("problemDescriptionComments");
	}
	@Test
	public void mapReportProblemRequest(){
		reportProblemVO = mobileMapper.mapReportProblemRequest(request,soId, providerResourceId,roleId);
		Assert.assertEquals(reportProblemVO.getSoId(),"511-4869-1316-95");
		Assert.assertEquals(reportProblemVO.getResourceId(),10254);
		Assert.assertEquals(reportProblemVO.getProblemDescriptionComments(),"problemDescription");
		Assert.assertEquals(reportProblemVO.getProblemReasonCode(),12);
	}
	
	@Test
	public void validateNoReasonCode(){
		try{
			reportProblemVO.setProblemReasonCode(null);
			ReportProblemResponse response = new ReportProblemResponse();
			response=mobileValidator.validateReportProblemRequest(reportProblemVO);
			Assert.assertNotNull(response.getResults());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3120");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Reason code is mandatory.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateReportProblemRequest(){
		try{
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(reportProblemVO.getSoId(),MPConstants.REPORT_PROBLEM_v3_1)).thenReturn(true);
			ReportProblemResponse response = new ReportProblemResponse();
			response=mobileValidator.validateReportProblemRequest(reportProblemVO);
			Assert.assertNull(response.getResults());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateInvalidSOStatus(){
		try{
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(reportProblemVO.getSoId(),MPConstants.REPORT_PROBLEM_v3_1)).thenReturn(false);
			ReportProblemResponse response = new ReportProblemResponse();
			response=mobileValidator.validateReportProblemRequest(reportProblemVO);
			Assert.assertNotNull(response.getResults());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3121");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Service Order Status.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
