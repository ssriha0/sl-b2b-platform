package com.servicelive.spn.services.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.servicelive.domain.spn.detached.Email;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.util.PropertyManagerUtil;

/**
 * 
 */
public class MailQueryStringFormatter {
	
    static String LOGIN_PASSWORD_KEY = "cleartext";
    static String LOGIN_NAME_KEY = "name";
    static String APPLICATION_ID_KEY = "aid";
    static String EMAIL_TYPE_ID_KEY = "eid";
    static String EMAIL_TEST_KEY = "test";

    private PropertyManagerUtil propertyManagerUtil;

	private Properties emailProperties;
	private EmailAlertBO emailAlertBO;
	/**
	 * 
	 * @param emailProperties
	 */
	public void setEmailProperties(Properties emailProperties) {
		this.emailProperties = emailProperties;
	}

	/**
	 * 
	 * @return String
	 */
	public String getLoginQueryString() {
		return LOGIN_NAME_KEY + "="
		+ emailProperties.getProperty(LOGIN_NAME_KEY) + "&"
		+ LOGIN_PASSWORD_KEY + "="
		+ emailProperties.getProperty(LOGIN_PASSWORD_KEY);
	}

	/**
	 * 
	 * @param eid
	 * @param parameters
	 * @return String
	 */
	public String getSendEmailHeaderString(int  eid, Map<String, String> parameters) {
		String queryString = APPLICATION_ID_KEY + "="
		+ emailProperties.getProperty(APPLICATION_ID_KEY) + "&"
		+ EMAIL_TYPE_ID_KEY + "=" + eid ;
		return queryString;
	}

	/**
	 * 
	 * @param eid
	 * @param parameters
	 * @return NameValuePair
	 */
	public NameValuePair[] getNameValuePairs(int  eid, Map<String, String> parameters) {
		List<NameValuePair> nameValPairs = new ArrayList<NameValuePair>();

		for(Entry<String, String> curPair: parameters.entrySet()) {
			if(curPair.getKey().equals("email")) {
				nameValPairs.add(new NameValuePair(curPair.getKey(),curPair.getValue()));
			} else {
				nameValPairs.add(new NameValuePair(StringUtils.upperCase(curPair.getKey()),curPair.getValue()));
			}
		}
		
		String serviceLiveUrl = propertyManagerUtil.getServiceLiveUrl();
		//serviceLiveUrl = "localhost:8080/MarketFrontend";
		nameValPairs.add(new NameValuePair("SERVICELIVE_URL", serviceLiveUrl));

		return nameValPairs.toArray(new NameValuePair[nameValPairs.size()]);
	}
	/**
	 * This method is for mapping Email object to AlertTask object.
	 * 
	 * @param email
	 *            Email
	 * @return AlertTask
	 */
	public AlertTask insertAlert(Email email){
			AlertTask alertTask=new AlertTask();
			Date date = new Date();
			//alert.setAlertBcc(alertBcc);
			alertTask.setAlertedTimestamp(null); 
			alertTask.setCreatedDate(date);
			alertTask.setModifiedDate(date);
			List<String> toList = email.getToList();
			String alertTo="";
			alertTo  = toList.size() > 0 ? toList.toString().substring(1, toList.toString().length()-1) : email.getTemplate().getTemplateTo();
			alertTask.setAlertTo(alertTo);
			alertTask.setAlertFrom(email.getTemplate().getTemplateFrom());
			alertTask.setTemplateId(getTemplateId(email.getTemplate().getTemplateId().intValue()));
			alertTask.setCompletionIndicator(SPNBackendConstants.INCOMPL_IND);
			alertTask.setAlertTypeId(SPNBackendConstants.EMAIL_TYPE);
			alertTask.setPriority(SPNBackendConstants.PRIORITY1);
			Map<String, String> params=email.getParams();
			Iterator<Entry<String, String>> pairs = params.entrySet()
			.iterator();
			Entry<String, String> curPair;
			String templateString="";
			while (pairs.hasNext()) {
				curPair = pairs.next();
				templateString=templateString+curPair.getKey();
				templateString=templateString+"=";
				templateString=templateString+curPair.getValue();
				templateString=templateString+"|";
			}
			String serviceLiveUrl = propertyManagerUtil.getServiceLiveUrl();
			templateString=templateString+"SERVICELIVE_URL="+ serviceLiveUrl+"|";
			alertTask.setTemplateInputValue(templateString);
			
		return alertTask;
		//	alert.setTemplate
		
		}
	/**
	 * This method is get template id
	 * 
	 * @param alertId
	 *            int
	 * @return int
	 */
	public int getTemplateId(int templateId){
		
		int id=0;
		if(templateId==SPNBackendConstants.SPN_TEMP_ID1){
			id=SPNBackendConstants.TEMP_ID1;
		}else if(templateId==SPNBackendConstants.SPN_TEMP_ID3){
			id=SPNBackendConstants.TEMP_ID3;
		}else if(templateId==SPNBackendConstants.SPN_TEMP_ID4 || templateId==SPNBackendConstants.SPN_TEMP_ID5){
			id=SPNBackendConstants.TEMP_ID4;
		}else if(templateId==SPNBackendConstants.SPN_TEMP_ID6 ||templateId==SPNBackendConstants.SPN_TEMP_ID7 ||
				templateId==SPNBackendConstants.SPN_TEMP_ID8 ||templateId==SPNBackendConstants.SPN_TEMP_ID9 ){
			id=SPNBackendConstants.TEMP_ID6;
		}else if(templateId==SPNBackendConstants.SPN_TEMP_ID10){
			id=SPNBackendConstants.TEMP_ID10;
		}else if(templateId==SPNBackendConstants.SPN_TEMP_ID11){
			id=SPNBackendConstants.TEMP_ID11;
		}else if(templateId==SPNBackendConstants.SPN_TEMP_ID12){
			id=SPNBackendConstants.TEMP_ID12;
		}
		return id;
	}
	
	/**
	 * @param propertyManagerUtil the propertyManagerUtil to set
	 */
	public void setPropertyManagerUtil(PropertyManagerUtil propertyManagerUtil) {
		this.propertyManagerUtil = propertyManagerUtil;
	}

	public EmailAlertBO getEmailAlertBO() {
		return emailAlertBO;
	}

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}
}
