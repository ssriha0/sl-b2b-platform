/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.business.businessImpl.mobile.MobileSOManagementBOImpl;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.SOStatusVO;
/**
 * @author Infosys
 *
 */
public class GetProviderSOListTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private MobileGenericBOImpl  mobileBO;
	private IMobileSOManagementBO mobileSOManagementBO;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mapper = new MobileGenericMapper();
		
		mobileSOManagementBO = new MobileSOManagementBOImpl();
		mobileBO = new MobileGenericBOImpl();
		
		mobileSOManagementBO =mock(MobileSOManagementBOImpl.class);
		mobileBO.setMobileSOManagementBO(mobileSOManagementBO);
		validator.setMobileGenericBO(mobileBO);
		
		List<SOStatusVO> statusVOList = getSOStatusLIstFromDB();
		
		try {
			when(mobileSOManagementBO.getServiceOrderStatus()).thenReturn(statusVOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void mapCriteria(){
		ProviderParamVO vo = new ProviderParamVO();
		try {
			vo = mapper.mapGetSoListRequest(getApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10254, vo.getResourceId());
		Assert.assertEquals(10202, vo.getVendorId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(300, vo.getPageSize());
		Assert.assertEquals("Accepted", vo.getSoStatus());
	}
	
	@Test
	public void mapCriteriaDefaultValues(){
		ProviderParamVO vo = new ProviderParamVO();
		try {
			vo = mapper.mapGetSoListRequest(getApiRequestWithNoMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10254, vo.getResourceId());
		Assert.assertEquals(10202, vo.getVendorId());
		Assert.assertEquals(1, vo.getPageNo());
		Assert.assertEquals(10, vo.getPageSize());
		Assert.assertEquals("Active", vo.getSoStatus());
		
		
	}
	
	@Test
	public void validatePageSize(){
		ProviderParamVO vo = new ProviderParamVO();
		try {
			vo = mapper.mapGetSoListRequest(getApiRequest());
			vo = validator.validateGetSoRequest(vo);
			
			ResultsCode resultsCode = vo.getValidationCode();
			
			Assert.assertNotNull(resultsCode);
			Assert.assertEquals("0204", resultsCode.getCode());
			Assert.assertEquals("Invalid Page Size.", resultsCode.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateSOStatus(){
		ProviderParamVO vo = new ProviderParamVO();
		try {
			vo = mapper.mapGetSoListRequest(getApiRequest());
			vo.setPageSize(25);
			vo.setSoStatus("Accepted_Posted");
			vo = validator.validateGetSoRequest(vo);
			
			ResultsCode resultsCode = vo.getValidationCode();
			
			Assert.assertNotNull(resultsCode);
			Assert.assertEquals("0202", resultsCode.getCode());
			Assert.assertEquals("Invalid Status.", resultsCode.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validationSuccess(){
		ProviderParamVO vo = new ProviderParamVO();
		try {
			vo = mapper.mapGetSoListRequest(getApiRequest());
			vo.setPageSize(25);
			vo.setSoStatus("Accepted_Problem");
			vo = validator.validateGetSoRequest(vo);
			
			ResultsCode resultsCode = vo.getValidationCode();
			
			Assert.assertNotNull(resultsCode);
			Assert.assertEquals("0000", resultsCode.getCode());
			Assert.assertEquals("Success", resultsCode.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private APIRequestVO getApiRequest() {
		
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_NUMBER, "2");
		requestMap.put(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_SIZE, "300");
		requestMap.put(PublicMobileAPIConstant.AdvancedProviderSOManagement.SO_STATUS, "Accepted");
		
		APIRequestVO apiVO = new APIRequestVO(null);
		apiVO.setRequestFromGetDelete(requestMap);
		apiVO.setProviderResourceId(10254);
		apiVO.setProviderId("10202");
		apiVO.setRoleId(3);
		
		return apiVO;
	}
	private APIRequestVO getApiRequestWithNoMap() {
		
		APIRequestVO apiVO = new APIRequestVO(null);
		apiVO.setProviderResourceId(10254);
		apiVO.setProviderId("10202");
		apiVO.setRoleId(3);
		
		return apiVO;
	}
	
	private List<SOStatusVO> getSOStatusLIstFromDB() {
		List<SOStatusVO> statusVOList =new ArrayList<SOStatusVO>();
		SOStatusVO vo = null;
		
		vo = new SOStatusVO();
		vo.setStatusId(100);
		vo.setStatusString("Draft");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(105);
		vo.setStatusString("Deleted");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(110);
		vo.setStatusString("Routed");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(120);
		vo.setStatusString("Cancelled");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(125);
		vo.setStatusString("Voided");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(130);
		vo.setStatusString("Expired");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(140);
		vo.setStatusString("Conditional Offer");
		statusVOList.add(vo);

		vo = new SOStatusVO();
		vo.setStatusId(150);
		vo.setStatusString("Accepted");
		statusVOList.add(vo);

		vo = new SOStatusVO();
		vo.setStatusId(155);
		vo.setStatusString("Active");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(160);
		vo.setStatusString("Completed");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(170);
		vo.setStatusString("Problem");
		statusVOList.add(vo);
		
		vo = new SOStatusVO();
		vo.setStatusId(180);
		vo.setStatusString("Closed");
		statusVOList.add(vo);
		
		return statusVOList;
	}
}
