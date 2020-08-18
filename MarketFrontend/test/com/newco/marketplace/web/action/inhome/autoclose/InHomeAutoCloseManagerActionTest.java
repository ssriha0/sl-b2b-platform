package com.newco.marketplace.web.action.inhome.autoclose;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.dto.inhome.autoclose.InHomeAutoCloseDTO;
import com.servicelive.inhome.autoclose.services.InHomeAutoCloseService;


public class InHomeAutoCloseManagerActionTest {
	private InHomeAutoCloseManagerAction action;
	private InHomeAutoCloseService inHomeAutoCloseService;
	
	@Test
	public void displayPage(){
		
	action = new InHomeAutoCloseManagerAction();
	action.setInHomeAutoCloseService(inHomeAutoCloseService);
	inHomeAutoCloseService = mock(InHomeAutoCloseService.class);
	
	
	boolean activeInd = true;
	Double defaultReimursementRate = 2.5;
	
	List<Double> reimbursementRateList = new ArrayList<Double>();
	/*reimbursementRateList.add(1.5);
	reimbursementRateList.add(2.5);
	reimbursementRateList.add(3.5);*/
	
	List<InHomeAutoCloseDTO> inHomeAutoCloseDTOList = new ArrayList<InHomeAutoCloseDTO>();
	InHomeAutoCloseDTO inhomeDTO = new InHomeAutoCloseDTO();
	inhomeDTO.setExistingOverrideReason("testing override reason");
	inhomeDTO.setModifiedBy("test buyer");
	inhomeDTO.setOverrideDate(new Date());
	inHomeAutoCloseDTOList.add(inhomeDTO);

	
	when(inHomeAutoCloseService.getOverrideFirmList(activeInd)).thenReturn(inHomeAutoCloseDTOList);
	
	reimbursementRateList = inHomeAutoCloseService.getReimbursementRateList();
	Assert.assertNotNull(reimbursementRateList);	
	
	when(inHomeAutoCloseService.getDefaultReimursementRate()).thenReturn(defaultReimursementRate);

	

}
}
