package com.servicelive.esb.service;

import com.servicelive.esb.BaseESBTestCase;
import com.servicelive.esb.dto.JobCodeInfo;

public class JobCodeMarginServiceTest extends BaseESBTestCase {

	private JobCodeMarginService jobCodeMarginService;
	private JobCodeInfo[] codeInfos;

	/**
	 * This should really be an integration test. For time being, leaving it in
	 * here
	 * 
	 * TODO: Replace with a test that uses mocks instead.
	 * 
	 * @throws Exception
	 */

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		codeInfos = new JobCodeInfo[] { new JobCodeInfo("57307", "DSS/SATDIS") };
		
		codeInfos = jobCodeMarginService.retrieveInfo(codeInfos);
	}

	public void testShouldGetMarginRate() {
		assertEquals(0.4f, codeInfos[0].getMarginRate());
	}

	public void testShouldGetLeadTimeDays() {
		assertEquals(3, codeInfos[0].getLeadTimeDays());
	}
	
	public void testShouldGetInclusionDesc() {
		assertEquals("Unpack and prepare television stand for assemblyInspect television stand for any damage or defectsInspect assembly site and provide quote for any additional work requiredAssemble new television stand per manufacturer specificationsClean up any job", codeInfos[0].getInclusionDesc());
	}

	public void testShouldGetStockNumber() {
		assertEquals("57307", codeInfos[0].getStockNumber());
	}

	public void testShouldGetJobCodeDesc() {
		assertEquals("STAND W/HT", codeInfos[0].getJobCodeDesc());
	}

	public void setJobCodeMarginService(
			JobCodeMarginService jobCodeMarginService) {
		this.jobCodeMarginService = jobCodeMarginService;
	}
}
