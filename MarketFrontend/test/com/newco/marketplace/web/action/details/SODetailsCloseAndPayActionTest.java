package com.newco.marketplace.web.action.details;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegatesImpl.SODetailsDelegateImpl;
import com.newco.marketplace.web.dto.AddonServiceRowDTO;

/**
 * This is the test class to test checkForAddonIssues() method
 *
 */
@Ignore
public class SODetailsCloseAndPayActionTest{
	
	private SODetailsCloseAndPayAction closeAndPayAction = null;
	private SODetailsDelegateImpl detailsDelegateImpl = null;
	private List<AddonServiceRowDTO> addons = null;
	AdditionalPaymentVO additionalPaymentVO = null;
	
	@Before
	public void setUp() throws Exception {
		
		detailsDelegateImpl = new SODetailsDelegateImpl();
		closeAndPayAction = new SODetailsCloseAndPayAction(detailsDelegateImpl);
		
		addons = new ArrayList<AddonServiceRowDTO>();
		additionalPaymentVO = new AdditionalPaymentVO();
		
		AddonServiceRowDTO addon = new AddonServiceRowDTO();
		addon.setQuantity(0);
		addons.add(addon);
	}
	
	@Test
	public void testCompleteSO() throws BusinessServiceException{
		
		boolean isError = closeAndPayAction.checkForAddonIssues(addons, additionalPaymentVO);
		Assert.assertEquals(true, isError);
			
	}
	
}
