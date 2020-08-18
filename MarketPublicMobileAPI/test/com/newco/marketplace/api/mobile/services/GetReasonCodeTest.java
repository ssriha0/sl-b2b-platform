/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.GetReasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;

/**
 * @author Infosys
 *
 */
public class GetReasonCodeTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private static ResultsCode error;
	private static String[] reasonCodesTypes ={"Reject","ReleaseByprovider","ReleasebyFirm","Reschedule",
		                                      "CounterOfferPrice","CounterOfferReschedule","PreCallCustNotAvailable","PreCallConfirmScedule",
		                                      "IncreaseLabor","IncreasePart","TypeofProblem"};
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mapper = new MobileGenericMapper();
		error = ResultsCode.INVALID_REASONCODE_TYPE;
	}
	@Test
	public void validateReasonCodesType(){
		List<String> reasonCodeRequest = new ArrayList<String>();
		boolean isValid = true;
		reasonCodeRequest.add("Reject");
		reasonCodeRequest.add("IncreasePart");
		reasonCodeRequest.add("ReleasebyFirm");
		List<String> reasonCodeTypeDB = Arrays.asList(reasonCodesTypes);
		List<String> invalidReasons = validator.validateGetReasonCodes(reasonCodeRequest, reasonCodeTypeDB);
		if(null != invalidReasons && !invalidReasons.isEmpty()){
			isValid = false;
		}
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void createErrorResponseReasonCode(){
		GetReasonCodesResponse response =null;
		response =mapper.createErrorResponseReasonCode(error);
		Assert.assertNotNull(response);
	}
}
