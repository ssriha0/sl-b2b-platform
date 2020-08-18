package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.getTeamMembers.ViewTeamMembersResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;

public class ViewTeamMembersTest {

	private NewMobileGenericMapper newMobileMapper;
	private NewMobileGenericValidator mobileValidator;
	private APIRequestVO apiVO;
	private INewMobileGenericBO newMobileGenericBO;
	@Before
	public void setUp() {
		newMobileMapper=new NewMobileGenericMapper();
		mobileValidator=new NewMobileGenericValidator();
		newMobileGenericBO = new NewMobileGenericBOImpl();
		apiVO = new APIRequestVO(null);
		apiVO.setProviderId("10202");
		apiVO.setProviderResourceId(10254);
	}
	
	@Test
	public void validateNoManageTeamPermissionTeamMember(){
		try {
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			Assert.assertNotNull(apiVO.getProviderResourceId());
			when(newMobileGenericBO.isManageTeamPermission(apiVO.getProviderResourceId())).thenReturn(false);
			ViewTeamMembersResponse viewTeamMembersResponse =new ViewTeamMembersResponse();
			viewTeamMembersResponse=mobileValidator.validateTeamMember(apiVO,viewTeamMembersResponse);
			Assert.assertNotNull(viewTeamMembersResponse.getResults());
			Assert.assertEquals(viewTeamMembersResponse.getResults().getError().get(0).getCode(),"3131");
			Assert.assertEquals(viewTeamMembersResponse.getResults().getError().get(0).getMessage(),"User does not have permission to view team member details.");
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateIsManageTeamPermissionTeamMember(){
		try {
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.isManageTeamPermission(apiVO.getProviderResourceId())).thenReturn(true);
			ViewTeamMembersResponse viewTeamMembersResponse =new ViewTeamMembersResponse();
			viewTeamMembersResponse=mobileValidator.validateTeamMember(apiVO,viewTeamMembersResponse);
			Assert.assertNotNull(viewTeamMembersResponse.getResults());
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateNoResourceActive(){
		try {
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			Assert.assertNotNull(apiVO.getProviderId());
			when(newMobileGenericBO.isManageTeamPermission(apiVO.getProviderResourceId())).thenReturn(true);
			when(newMobileGenericBO.isResourceActive(apiVO.getProviderId())).thenReturn(false);
			ViewTeamMembersResponse viewTeamMembersResponse =new ViewTeamMembersResponse();
			viewTeamMembersResponse=mobileValidator.validateTeamMember(apiVO,viewTeamMembersResponse);
			Assert.assertNotNull(viewTeamMembersResponse.getResults());
			Assert.assertEquals(viewTeamMembersResponse.getResults().getError().get(0).getCode(),"3132");
			Assert.assertEquals(viewTeamMembersResponse.getResults().getError().get(0).getMessage(),"User does not have any registered service providers. Please add a new provider.");
		}
		catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateIsResourceActive(){
		try {
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			Assert.assertNotNull(apiVO.getProviderId());
			when(newMobileGenericBO.isManageTeamPermission(apiVO.getProviderResourceId())).thenReturn(true);
			when(newMobileGenericBO.isResourceActive(apiVO.getProviderId())).thenReturn(true);
			ViewTeamMembersResponse viewTeamMembersResponse =new ViewTeamMembersResponse();
			viewTeamMembersResponse=mobileValidator.validateTeamMember(apiVO,viewTeamMembersResponse);
			Assert.assertNull(viewTeamMembersResponse.getResults());
		}
		catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
}
