package com.servicelive.spn.services.network;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.dao.network.SPNHeaderDao;

public class CreateNetworkServicesTest {
	private CreateNetworkServices service;
	private SPNHeaderDao hdrDao;
	
	@Test
	public void testFindAlias(){
		hdrDao = mock(SPNHeaderDao.class);
		service = new CreateNetworkServices();
		service.setSpnheaderDao(hdrDao);
		Integer spnId = 74;
		List<SPNHeader> hdrs = new ArrayList<SPNHeader>();
		SPNHeader hdr = new SPNHeader();
		hdr.setAliasOriginalSpnId(74);
		hdr.setSpnId(121);
		hdrs.add(hdr);
		SPNHeader hdrResult = new SPNHeader();
		try {
			when(hdrDao.findAliases(spnId)).thenReturn(hdrs);
			hdrResult = service.findAlias(spnId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(hdr, hdrResult);
	}
	
}