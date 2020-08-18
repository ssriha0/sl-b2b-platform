package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;

public class AssignOrReassignSOTest {
	private MobileGenericValidator validator;
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	AssignVO assignVO;
	AssignVO vo;
	private static ResultsCode invalidResourceId;	
	private static ResultsCode resourceNotUnderFirm;
	private static ResultsCode permissionError;
	String soId;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mobileBO = new MobileGenericBOImpl();
		validator.setMobileGenericBO(mobileBO);
		genericDao = new MobileGenericDaoImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		assignVO = new AssignVO();
		vo=new AssignVO();
		invalidResourceId = ResultsCode.INVALID_RESOURCE_ID;	
		resourceNotUnderFirm=ResultsCode.RESOURCE_NOT_UNDER_FIRM;
		permissionError=ResultsCode.PERMISSION_ERROR;	
		soId="555-6444-4185-52";
	}
	
	@Test
	public void validateResourceId(){
		List<ErrorResult>invalidResourceErrorList= new ArrayList<ErrorResult>();
		try {
		
			assignVO.setRequestFor("Assign");
			assignVO.setResourceId(0);
			vo.setAssignmentType("FIRM");
			vo.setWfStateId(150);
			
			when(genericDao.getProviderSODetails(soId)).thenReturn(vo);
			invalidResourceErrorList = validator.validateAssignOrReassignSO(assignVO);
			Assert.assertSame(invalidResourceId.getMessage(), invalidResourceErrorList.get(0).getMessage());
		} catch (Exception e) {			
			e.printStackTrace();
		}
	
	}	
	@Test
	public void validateResourceUnderFirm(){
		List<ErrorResult>invalidResUnderFirmErrorList;
		try {
			List<Integer> resourceIds = new ArrayList<Integer>();
			resourceIds.add(10254);
			resourceIds.add(23678);
			resourceIds.add(104411);
			resourceIds.add(45361);
			
			assignVO.setRequestFor("Assign");
			assignVO.setResourceId(23768);
			assignVO.setSoId(soId);
			assignVO.setFirmId(10202);
			vo.setAssignmentType("FIRM");
			vo.setWfStateId(150);
			
			when(genericDao.getProviderSODetails(soId)).thenReturn(vo);
			when(genericDao.getRoutedResourcesUnderFirm(assignVO)).thenReturn(resourceIds);
			
			invalidResUnderFirmErrorList = validator.validateAssignOrReassignSO(assignVO);
			Assert.assertSame(invalidResUnderFirmErrorList.get(0).getMessage(),resourceNotUnderFirm.getMessage());
		} catch (Exception e) {			
			e.printStackTrace();
		}
	
	}
	@Test
	public void validateRole(){
		List<ErrorResult>invalidRole;
		try {
			
			List<Integer> resourceIds = new ArrayList<Integer>();
			resourceIds.add(10254);
			resourceIds.add(23678);
			resourceIds.add(104411);
			resourceIds.add(45361);
			assignVO.setRequestFor("Assign");
			assignVO.setResourceId(23678);
			assignVO.setUrlResourceId(10254);
			assignVO.setRoleId(2);
			assignVO.setSoId(soId);
			assignVO.setFirmId(10202);			
			vo.setAssignmentType("FIRM");
			vo.setWfStateId(150);
			
			
			when(genericDao.getProviderSODetails(soId)).thenReturn(vo);
			when(genericDao.getRoutedResourcesUnderFirm(assignVO)).thenReturn(resourceIds);
			
			invalidRole = validator.validateAssignOrReassignSO(assignVO);
			Assert.assertSame(invalidRole.get(0).getMessage(),permissionError.getMessage());
		} catch (Exception e) {			
			e.printStackTrace();
		}
	
	}
	
}
	

