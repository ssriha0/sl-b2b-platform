/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.saveFilter.FilterCriterias;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterRequest;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterResponse;
import com.newco.marketplace.api.mobile.beans.so.search.SearchIntegerElement;
import com.newco.marketplace.api.mobile.beans.so.search.advance.Appointment;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.dto.vo.serviceorder.FilterCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;

/**
 * @author Infosys
 *
 */
public class SaveFilterTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private SaveFilterRequest request;
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		genericDao = new MobileGenericDaoImpl();
		mobileBO = new MobileGenericBOImpl();
		mapper = new MobileGenericMapper();
		request = new SaveFilterRequest();
		
		request.setFilterName("My test filter");
		
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		validator.setMobileGenericBO(mobileBO);
		try {
			when(genericDao.isFilterExists(10254, request.getFilterName())).thenReturn(true);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void validateFilterCriteriaMandatory(){
		
		
		try {
			SaveFilterResponse response = validator.validateSaveFilter(request,10254);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());
			Assert.assertEquals("3053", response.getResults().getError().get(0).getCode());
			
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateFilterCriteriaMandatory1(){
		
		
		try {
			FilterCriterias filterCriterias = new FilterCriterias();
			request.setFilterCriterias(filterCriterias);
			SaveFilterResponse response = validator.validateSaveFilter(request,10254);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());
			Assert.assertEquals("3053", response.getResults().getError().get(0).getCode());
			
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateFilterCriteriaAppointment(){
		
		
		try {
			FilterCriterias filterCriterias = new FilterCriterias();
			
			Appointment appointment = new Appointment();
			appointment.setAppointmentFilter("Range");
			
			filterCriterias.setAppointment(appointment);
			request.setFilterCriterias(filterCriterias);
			
			SaveFilterResponse response = validator.validateSaveFilter(request,10254);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());
			Assert.assertEquals("3006", response.getResults().getError().get(0).getCode());
			
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateFilterCriteriaAppointment1(){
		
		
		try {
			
			FilterCriterias filterCriterias = new FilterCriterias();
			
			Appointment appointment = new Appointment();
			appointment.setAppointmentFilter("Range");
			appointment.setStartRange("2015-07-05");
			appointment.setEndRange("2015-07-04");
			
			filterCriterias.setAppointment(appointment);
			request.setFilterCriterias(filterCriterias);
			
			
			SaveFilterResponse response = validator.validateSaveFilter(request,10254);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());
			Assert.assertEquals("3007", response.getResults().getError().get(0).getCode());
			
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void validateFilterNameExists(){
		
		
		try {
			
			FilterCriterias filterCriterias = new FilterCriterias();
			
			Appointment appointment = new Appointment();
			appointment.setAppointmentFilter("Today");
			
			filterCriterias.setAppointment(appointment);
			request.setFilterCriterias(filterCriterias);
			
			
			try {
				when(genericDao.isFilterExists(10254, request.getFilterName())).thenReturn(true);
			} catch (DataServiceException e) {
				e.printStackTrace();
			}
			
			SaveFilterResponse response = validator.validateSaveFilter(request,10254);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());
			Assert.assertEquals("3052", response.getResults().getError().get(0).getCode());
			
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void noValidationError(){
		try {
			
			FilterCriterias filterCriterias = new FilterCriterias();
			
			Appointment appointment = new Appointment();
			appointment.setAppointmentFilter("Today");
			
			filterCriterias.setAppointment(appointment);
			request.setFilterCriterias(filterCriterias);
			
			try {
				when(genericDao.isFilterExists(10254, request.getFilterName())).thenReturn(false);
			} catch (DataServiceException e) {
				e.printStackTrace();
			}
			
			
			SaveFilterResponse response = validator.validateSaveFilter(request,10254);
			Assert.assertNull(response);
			
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	@Test
	public void mapRequestToVO(){
		try {
			
			request = getSaveFilterRequest();
			
			FiltersVO filtersVO = mapper.mapSaveFilterRequest(request, 10254);
			
			Assert.assertNotNull(filtersVO);
			Assert.assertEquals(1, filtersVO.getFilterId());
			Assert.assertEquals("My test filter", filtersVO.getFilterName());
			
			List<FilterCriteriaVO> criteriaVOList = filtersVO.getCriteriaList();
			
			Assert.assertNotNull(criteriaVOList);
			Assert.assertEquals(10, criteriaVOList.size());
			
		} catch (Exception e) {
		}
	}
	
	private SaveFilterRequest getSaveFilterRequest(){
		
		FilterCriterias filterCriterias = new FilterCriterias();
		
		SearchIntegerElement market = new SearchIntegerElement();
		List<Integer> marketList = new ArrayList<Integer>();
		marketList.add(351);
		marketList.add(352);
		market.setValue(marketList);
		filterCriterias.setMarkets(market);
		
		
		SearchIntegerElement providerResource = new SearchIntegerElement();
		List<Integer> providerResourceList = new ArrayList<Integer>();
		providerResourceList.add(10254);
		providerResourceList.add(23768);
		providerResource.setValue(providerResourceList);
		filterCriterias.setServiceProIds(providerResource);
		
		SearchIntegerElement status = new SearchIntegerElement();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(110);
		statusList.add(150);
		statusList.add(155);
		status.setValue(statusList);
		filterCriterias.setStatuses(status);
		
		Appointment appointment = new Appointment();
		appointment.setAppointmentFilter("Today");
		filterCriterias.setAppointment(appointment);
		
		filterCriterias.setFlaggedOnlyInd(true);
		filterCriterias.setUnAssignedInd(false);
		
		request.setFilterCriterias(filterCriterias);
		request.setFilterId(1);
		return request;
		
	}
}
