package com.servicelive.spn.buyer.auditor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import com.servicelive.spn.services.auditor.AuditorSearchService;
import com.servicelive.spn.common.detached.BackgroundCheckHistoryVO;
import com.servicelive.spn.common.detached.BackgroundInformationVO;
import com.servicelive.spn.common.detached.SearchBackgroundInformationVO;

import static org.mockito.Mockito.*;

public class SPNAuditorSearchTabActionTest {

	private SPNAuditorSearchTabAction spnSearchAction;
	private AuditorSearchService auditorSearchService;
	
	@Test
	public void testDisplayBackgroundHistoryAjax(){
		auditorSearchService = mock(AuditorSearchService.class);
		spnSearchAction = new SPNAuditorSearchTabAction();
		spnSearchAction.setAuditorSearchService(auditorSearchService);
		BackgroundCheckHistoryVO voValues = new BackgroundCheckHistoryVO();
		voValues.setVendorId(10202);
		voValues.setResourceId(10254);
		
		List<BackgroundCheckHistoryVO> backgroundHistVOList = new ArrayList<BackgroundCheckHistoryVO>();
		BackgroundCheckHistoryVO histVO = new BackgroundCheckHistoryVO();
		histVO.setBackgroundStatus("Clear");
		histVO.setDisplayDate(new Date());
		histVO.setChangingComments("Background Reverification date was changed");
		BackgroundCheckHistoryVO histVO1 = new BackgroundCheckHistoryVO();
		histVO1.setBackgroundStatus("Not Cleared");
		histVO1.setDisplayDate(new Date());
		histVO1.setChangingComments("new plus one file has been recieved");
		backgroundHistVOList.add(histVO);
		backgroundHistVOList.add(histVO1);
		
		List<BackgroundCheckHistoryVO> voResult = new ArrayList<BackgroundCheckHistoryVO>();
		try {
			when(auditorSearchService.getBackgroundCheckHistoryDetails(voValues)).thenReturn(backgroundHistVOList);
			voResult = auditorSearchService.getBackgroundCheckHistoryDetails(voValues);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(backgroundHistVOList, voResult);
	}
	
	
	@Test
	public void testSearchBackgroundInformationCountAjax(){
		auditorSearchService = mock(AuditorSearchService.class);
		spnSearchAction = new SPNAuditorSearchTabAction();
		spnSearchAction.setAuditorSearchService(auditorSearchService);
		SearchBackgroundInformationVO searchBackgroundInformationVO = new SearchBackgroundInformationVO();
		searchBackgroundInformationVO.setBuyerId(1000);
		searchBackgroundInformationVO.setSpnId(74);
		searchBackgroundInformationVO.setStateCd("IL");
		searchBackgroundInformationVO.setStatus("PF SPN MEMBER");
		
		Integer count =17000;
		
		Integer countResult =17000;
		try {
			when(auditorSearchService.getBackgroundInformationCount(searchBackgroundInformationVO)).thenReturn(count);
			countResult = auditorSearchService.getBackgroundInformationCount(searchBackgroundInformationVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(count, countResult);
	}
	
	
	@Test
	public void testSearchBackgroundInformationAjax(){
		auditorSearchService = mock(AuditorSearchService.class);
		spnSearchAction = new SPNAuditorSearchTabAction();
		spnSearchAction.setAuditorSearchService(auditorSearchService);
		SearchBackgroundInformationVO searchBackgroundInformationVO = new SearchBackgroundInformationVO();
		searchBackgroundInformationVO.setBuyerId(1000);
		searchBackgroundInformationVO.setSpnId(74);
		searchBackgroundInformationVO.setStateCd("IL");
		searchBackgroundInformationVO.setStatus("PF SPN MEMBER");
	
		List<BackgroundInformationVO> backgroundInfoList = new ArrayList<BackgroundInformationVO>();
		BackgroundInformationVO backgroundInformationVO = new BackgroundInformationVO();
		backgroundInformationVO.setVendorBusinessName("Home Sweet Home Theatre");
		backgroundInformationVO.setVendorId(10202);
		backgroundInformationVO.setResourceId(10254);
		backgroundInformationVO.setProviderFirstName("John");
		backgroundInformationVO.setProviderLastName("Sam");
		backgroundInformationVO.setBackgroundState("Clear");		
		
		
		List<BackgroundInformationVO> backgroundInfoListResult = new ArrayList<BackgroundInformationVO>();
		try {
			when(auditorSearchService.getBackgroundInformation(searchBackgroundInformationVO)).thenReturn(backgroundInfoList);
			backgroundInfoListResult = auditorSearchService.getBackgroundInformation(searchBackgroundInformationVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(backgroundInfoList, backgroundInfoListResult);
	}

	
}