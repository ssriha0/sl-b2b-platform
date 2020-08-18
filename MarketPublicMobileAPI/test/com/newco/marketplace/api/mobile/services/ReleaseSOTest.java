package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSORequest;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSOResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ReleaseSOTest {

	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericMapper mobileMapper;
	private NewMobileGenericBOImpl newMobileGenericBO;
	private MobileSOReleaseVO releaseVO;
	private MobileReleaseSORequest request;
	private String soId;
	private Integer providerId;
	private Integer providerResourceId;
	private ProcessResponse processResponse;
	private Integer roleId;

	@Before
	public void setUp() {
		mobileValidator=new NewMobileGenericValidator();
		mobileMapper=new NewMobileGenericMapper();
		newMobileGenericBO = new NewMobileGenericBOImpl();
		releaseVO=new MobileSOReleaseVO();
		request=new MobileReleaseSORequest();
		processResponse=new ProcessResponse();
		processResponse.setCode("00");
		request.setReason("10");
		request.setComments("test");
		soId="511-4869-1316-95";
		providerId=10202;
		providerResourceId=10254;
		releaseVO.setReason("10");
		releaseVO.setComments("test");
		releaseVO.setSoId("511-4869-1316-95");
		releaseVO.setFirmId("10202");
		releaseVO.setResourceId(10254);
		roleId=3;
		
	}
	
	@Test
	public void mapReleaseRequestToVO(){
		releaseVO = mobileMapper.mapReleaseRequestToVO(request,soId,providerId,providerResourceId,roleId);
		System.out.println(releaseVO.getFirmId()+"\n"+releaseVO.getResourceId());
		Assert.assertNotNull(releaseVO);
		Assert.assertEquals(releaseVO.getReason(),"10");
		Assert.assertEquals(releaseVO.getComments(),"test");
		Assert.assertEquals(releaseVO.getSoId(),"511-4869-1316-95");
		Assert.assertEquals(releaseVO.getFirmId(),"10202");
		Assert.assertEquals(releaseVO.getResourceId(),10254);
	}
	
	@Test
	public void validateNoReasonCode(){
		try{
			releaseVO.setReason(null);
			MobileReleaseSOResponse releaseSOResponse= new MobileReleaseSOResponse();
			releaseSOResponse=mobileValidator.validateReleaseRequest(releaseVO, releaseSOResponse);
			Assert.assertNotNull(releaseSOResponse.getResults());
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getCode(),"3120");
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getMessage(),"Reason code is mandatory.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void validatemandatoryComments1(){
		try{
			releaseVO.setReason("13");
			releaseVO.setComments(null);
			MobileReleaseSOResponse releaseSOResponse= new MobileReleaseSOResponse();
			releaseSOResponse=mobileValidator.validateReleaseRequest(releaseVO, releaseSOResponse);
			Assert.assertNotNull(releaseSOResponse.getResults());
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getCode(),"3123");
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getMessage(),"Comments is mandatory if reason code selected is  \u2018Others (Enter Comments)\u2019.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void validatemandatoryComments2(){
		try{
			releaseVO.setReason("14");
			releaseVO.setComments(null);
			MobileReleaseSOResponse releaseSOResponse= new MobileReleaseSOResponse();
			releaseSOResponse=mobileValidator.validateReleaseRequest(releaseVO, releaseSOResponse);
			Assert.assertNotNull(releaseSOResponse.getResults());
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getCode(),"3123");
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getMessage(),"Comments is mandatory if reason code selected is  \u2018Others (Enter Comments)\u2019.");
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
			when(newMobileGenericBO.validateServiceOrderStatus(releaseVO.getSoId(),MPConstants.RELEASE_SO_ACTION)).thenReturn(false);
			MobileReleaseSOResponse releaseSOResponse= new MobileReleaseSOResponse();
			releaseSOResponse=mobileValidator.validateReleaseRequest(releaseVO, releaseSOResponse);
			Assert.assertNotNull(releaseSOResponse.getResults());
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getCode(),"3121");
			Assert.assertEquals(releaseSOResponse.getResults().getError().get(0).getMessage(),"Invalid Service Order Status.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void validRequest(){
		try{
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(releaseVO.getSoId(),MPConstants.RELEASE_SO_ACTION)).thenReturn(true);
			MobileReleaseSOResponse releaseSOResponse= new MobileReleaseSOResponse();
			releaseSOResponse=mobileValidator.validateReleaseRequest(releaseVO, releaseSOResponse);
			Assert.assertNotNull(releaseSOResponse.getResults());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void createReleaseResponse(){
		MobileReleaseSOResponse releaseSOResponse=null;
		releaseSOResponse=mobileMapper.createReleaseResponse(releaseSOResponse, processResponse);
		Assert.assertEquals("0000",releaseSOResponse.getResults().getResult().get(0).getCode());
	}

}
