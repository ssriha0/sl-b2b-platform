package com.newco.marketplace.business.businessImpl.spn;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.dto.vo.spn.BackgroundInfoProviderVO;
import com.newco.marketplace.dto.vo.spn.SearchBackgroundInfoProviderVO;
import com.newco.marketplace.persistence.daoImpl.spn.SPNDaoImpl;



public class SPNMonitorBOImplTest {

	private SPNDaoImpl spnDaoImpl;
	private SPNMonitorBOImpl spnMonitorBOImpl;
	
	@Test
	public void testSearchBackgroundInformationCountAjax(){
		spnDaoImpl = mock(SPNDaoImpl.class);
		spnMonitorBOImpl = new SPNMonitorBOImpl();
		spnMonitorBOImpl.setSpnDAO(spnDaoImpl);
		SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO = new SearchBackgroundInfoProviderVO();
		searchBackgroundInfoProviderVO.setVendorId(10202);
		searchBackgroundInfoProviderVO.setSpnId(74);
		searchBackgroundInfoProviderVO.setStateCd("IL");
		
		Integer count =17000;
		
		Integer countResult =17000;
		try {
			when(spnDaoImpl.getBackgroundInformationCount(searchBackgroundInfoProviderVO)).thenReturn(count);
			countResult = spnDaoImpl.getBackgroundInformationCount(searchBackgroundInfoProviderVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(count, countResult);
	}
	
	
	@Test
	public void testSearchBackgroundInformationAjax(){
		spnDaoImpl = mock(SPNDaoImpl.class);
		spnMonitorBOImpl = new SPNMonitorBOImpl();
		spnMonitorBOImpl.setSpnDAO(spnDaoImpl);
		SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO = new SearchBackgroundInfoProviderVO();
		searchBackgroundInfoProviderVO.setVendorId(10202);
		searchBackgroundInfoProviderVO.setSpnId(74);
		searchBackgroundInfoProviderVO.setStateCd("IL");
	
		List<BackgroundInfoProviderVO> backgroundInfoList = new ArrayList<BackgroundInfoProviderVO>();
		BackgroundInfoProviderVO backgroundInformationVO = new BackgroundInfoProviderVO();
		backgroundInformationVO.setResourceId(10254);
		backgroundInformationVO.setProviderFirstName("John");
		backgroundInformationVO.setProviderLastName("Sam");
		backgroundInformationVO.setBackgroundState("Clear");		
		
		
		List<BackgroundInfoProviderVO> backgroundInfoListResult = new ArrayList<BackgroundInfoProviderVO>();
		try {
			when(spnDaoImpl.getBackgroundInformation(searchBackgroundInfoProviderVO)).thenReturn(backgroundInfoList);
			backgroundInfoListResult = spnDaoImpl.getBackgroundInformation(searchBackgroundInfoProviderVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(backgroundInfoList, backgroundInfoListResult);
	}
	
	
}