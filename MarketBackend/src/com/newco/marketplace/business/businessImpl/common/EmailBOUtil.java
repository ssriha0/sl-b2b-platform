package com.newco.marketplace.business.businessImpl.common;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.util.PropertiesUtils;

public class EmailBOUtil{
	private static final String serviceLiveURLDefault = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SERVICELIVE_URL);
	
	public static String createDeepLinkForPassword(String serviceLiveURL, String password) {
		if (serviceLiveURL == null)
			serviceLiveURL = serviceLiveURLDefault;
		String deeplink = "http://" + serviceLiveURL+"/changePasswordStrutsAction.action";
		return deeplink;
	}
	
	public static String createDeepLinkForUserName() {
		String deeplink = "http://" + serviceLiveURLDefault +"/doLogin.action";
		return deeplink;
	}
}
