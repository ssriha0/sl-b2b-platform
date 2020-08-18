package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.so.reportAProblem.ReportProblemVO;
import com.newco.marketplace.api.mobile.beans.resolveProblem.ResolveProblemOnSORequest;
import com.newco.marketplace.api.mobile.beans.resolveProblem.ResolveProblemResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;

public class ResolveProblemTest {

	private NewMobileGenericMapper mobileMapper;
	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericBOImpl newMobileGenericBO;
	private ResolveProblemOnSORequest request;
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
		request=new ResolveProblemOnSORequest();
		request.setResolutionComments("resolutionComments");
		soId="511-4869-1316-95";
		providerResourceId=10254;
		reportProblemVO.setSoId(soId);
		reportProblemVO.setResourceId(10254);
		reportProblemVO.setResolutionComments("resolutionComments");
		roleId=3;
	}
	
	@Test
	public void mapResolveProblemRequest(){
		reportProblemVO = mobileMapper.mapResolveProblemRequest(request,soId, providerResourceId,roleId);
		Assert.assertEquals(reportProblemVO.getSoId(),"511-4869-1316-95");
		Assert.assertEquals(reportProblemVO.getResourceId(),10254);
		Assert.assertEquals(reportProblemVO.getResolutionComments(),"resolutionComments");
	}
	
	@Test
	public void validateResolveProblemRequest(){
		try{
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(reportProblemVO.getSoId(),MPConstants.RESOLVE_PROBLEM_v3_1)).thenReturn(true);
			ResolveProblemResponse response = new ResolveProblemResponse();
			response=mobileValidator.validateResolveProblemRequest(reportProblemVO);
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
			when(newMobileGenericBO.validateServiceOrderStatus(reportProblemVO.getSoId(),MPConstants.RESOLVE_PROBLEM_v3_1)).thenReturn(false);
			ResolveProblemResponse response = new ResolveProblemResponse();
			response=mobileValidator.validateResolveProblemRequest(reportProblemVO);
			Assert.assertNotNull(response.getResults());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3121");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Service Order Status.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateResolutionComments(){
		try{
			reportProblemVO.setResolutionComments(null);
			ResolveProblemResponse response = new ResolveProblemResponse();
			response=mobileValidator.validateResolveProblemRequest(reportProblemVO);
			Assert.assertNotNull(response.getResults());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3122");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Comments is mandatory.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
