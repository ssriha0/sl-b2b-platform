//package com.newco.test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import test.newco.test.marketplace.web.action.BaseStrutsTestCase;
//
//import com.newco.marketplace.dto.vo.survey.SurveyVO;
//import com.newco.marketplace.web.action.details.ServiceOrderRateAction;
//
//public class ServiceOrderRateTest extends
//		BaseStrutsTestCase<ServiceOrderRateAction> {
//
//	public ServiceOrderRateTest() {
//	}
//
//	protected void setUp() throws Exception {
//		super.setUp();
//	}
//
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//
//	public void testRetrieveQuestions() {
//		try {
//			ServiceOrderRateAction action = createAction(
//					ServiceOrderRateAction.class, "/sorate",
//					"retrieveQuestions");
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("surveyId", "10");
//			proxy.getInvocation().getInvocationContext().setParameters(params);
//			String result = proxy.execute();
//			SurveyVO vo = (SurveyVO)request.getSession().getAttribute("surveyvo");
//			System.out.println(vo.getQuestions().size());
//			assertEquals(result, "success");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void testSaveResponse() {
//		fail("Not yet implemented");
//	}
//
//
//	
//	
//
//}
