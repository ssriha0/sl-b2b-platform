package com.newco.marketplace.api.services.so.v1_2;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.alerts.SmsUnSubscriptionRequest;
import com.newco.marketplace.api.beans.alerts.SmsUnSubscriptionResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.usermanagement.UserManagementBO;

public class SmsUnSubscribeService extends BaseService{
	UserManagementBO userManagementBO;
	public SmsUnSubscribeService() {
	super(PublicAPIConstant.SMS_SUB_REQUEST_XSD,
			PublicAPIConstant.SMS_SUB_RESPONSE_XSD,
			PublicAPIConstant.SORESPONSE_NAMESPACE,
			PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_2,
			PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_2, SmsUnSubscriptionRequest.class,
			SmsUnSubscriptionResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		SmsUnSubscriptionRequest request=new SmsUnSubscriptionRequest();
		SmsUnSubscriptionResponse response=new SmsUnSubscriptionResponse();
		request = (SmsUnSubscriptionRequest) apiVO.getRequestFromPostPut();
		String smsNo=request.getUser().getMobilePhone();
		String firstName=request.getUser().getFirstName();
		String lastName=request.getUser().getLastName();
		try{
			userManagementBO.optOutSMS(smsNo, firstName, lastName);
			Results results = Results.getSuccess("The number is opted out successfully");
			response.setResults(results);
		}
		catch (Exception e) {
			Results results = Results.getError("Opting out was unsuccessful", ResultsCode.EDIT_FAILURE.getCode());
			response.setResults(results);
			return response;
		}
	return response;
	}
	public UserManagementBO getUserManagementBO() {
		return userManagementBO;
	}
	public void setUserManagementBO(UserManagementBO userManagementBO) {
		this.userManagementBO = userManagementBO;
	}
}
