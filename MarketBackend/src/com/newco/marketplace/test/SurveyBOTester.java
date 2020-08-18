/**
 * 
 */
package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.SurveyConstants;


/**
 * @author schavda
 *
 */
public class SurveyBOTester implements SurveyConstants {
	public static void main(String[] args) {
		String choice = "1";
		
		try {
			ISurveyBO surveyBO = (ISurveyBO)MPSpringLoaderPlugIn.getCtx().getBean("surveyBO");
			ServiceOrderBO sob = (ServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
			
			ServiceOrder so = sob.getServiceOrder("001-7618-9501-2716");
			System.out.println(so.getBuyerToProviderResults().getResponseHdrId());
			System.out.println(so.getProviderToBuyerResults());
			System.exit(0);
			
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}
	
	}

}
