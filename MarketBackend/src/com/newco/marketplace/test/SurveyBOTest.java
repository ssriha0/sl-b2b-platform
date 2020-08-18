/*package com.newco.marketplace.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.marketplace.security.impl.SurveyDAOImpl;
import com.newco.marketplace.utils.UIUtils;
import com.sears.os.context.ContextValue;

public class SurveyBOTest extends TestCase {

	SurveyDAOImpl impl = null;
	public SurveyBOTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetbuyerRatings() {
		try {
			ISurveyBO surveyBO = (ISurveyBO)MPSpringLoaderPlugIn.getCtx().getBean("surveyBO");
			
			SurveyRatingsVO surveySummary = surveyBO.getRatings(1,0,28);
			SurveyRatingsVO surveySummary1 = surveyBO.getRatings(3,2,0);
			SurveyRatingsVO surveySummary2 = surveyBO.getRatings(1,15,0);
			
			List<Integer> vendorResourceIDs = new ArrayList<Integer>();
			vendorResourceIDs.add(31);
			vendorResourceIDs.add(32);
			vendorResourceIDs.add(33);
			
			List<SurveyRatingsVO> surveySummarys = surveyBO.getQuickRatings(111, vendorResourceIDs);
			
			for(int i=0;i<surveySummarys.size();i++){
				System.out.println(surveySummarys.get(i).getVendorResourceID()+" "+surveySummarys.get(i).getHistoricalRating());
			}
			System.out.println("Score Number value for score=1.69. Expected=7. Actual=" + UIUtils.calculateScoreNumber(1.69));
			System.out.println("Score Number value for score=1.79. Expected=7. Actual=" + UIUtils.calculateScoreNumber(1.79));
			System.out.println("Score Number value for score=2.09. Expected=8. Actual=" + UIUtils.calculateScoreNumber(2.09));
			System.out.println("Score Number value for score=3.50. Expected=14. Actual=" + UIUtils.calculateScoreNumber(3.50));
			System.out.println("Score Number value for score=4. Expected=16. Actual=" + UIUtils.calculateScoreNumber(4));
			
			System.out.println("Score Number value for score=4.88. Expected=20. Actual=" + UIUtils.calculateScoreNumber(4.88));
			System.out.println("Score Number value for score=0. Expected=0. Actual=" + UIUtils.calculateScoreNumber(0));
			System.out.println("Score Number value for score=0.01. Expected=0. Actual=" + UIUtils.calculateScoreNumber(0.01));
			System.out.println("Score Number value for score=0.12. Expected=0. Actual=" + UIUtils.calculateScoreNumber(0.12));
			System.out.println("Score Number value for score=0.13. Expected=1. Actual=" + UIUtils.calculateScoreNumber(0.13));
			System.out.println("Score Number value for score=0.61. Expected=2. Actual=" + UIUtils.calculateScoreNumber(0.61));
			
			assertEquals(7,  UIUtils.calculateScoreNumber(1.69));
			assertEquals(7,  UIUtils.calculateScoreNumber(1.79));
			assertEquals(8,  UIUtils.calculateScoreNumber(2.09));
			assertEquals(14, UIUtils.calculateScoreNumber(3.50));
			assertEquals(16, UIUtils.calculateScoreNumber(4));
			assertEquals(20, UIUtils.calculateScoreNumber(4.88));
			assertEquals(0,  UIUtils.calculateScoreNumber(0));
			assertEquals(0,  UIUtils.calculateScoreNumber(0.01));
			assertEquals(0,  UIUtils.calculateScoreNumber(0.12));
			assertEquals(1,  UIUtils.calculateScoreNumber(0.13));
			assertEquals(2,  UIUtils.calculateScoreNumber(0.61));
			assertEquals(20, UIUtils.calculateScoreNumber(5));
			
			System.out.println("Test complete");
		} catch (DataServiceException e) {
			e.printStackTrace();
		}

	}

	public void testGetproviderRatings() {
		//fail("Not yet implemented");
	}

}
*/