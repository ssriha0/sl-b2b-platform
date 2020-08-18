package com.newco.marketplace.cheetahmail;

import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

public interface MailQueryStringFormatter {

    String LOGIN_PASSWORD_KEY = "cleartext";
    String LOGIN_NAME_KEY = "name";
    String APPLICATION_ID_KEY = "aid";
    String EMAIL_TYPE_ID_KEY = "eid";
    String EMAIL_TEST_KEY = "test";

    String getLoginQueryString();

    String getSendEmailHeaderString(String  eid,
            Map<String, String> parameters);
    
    NameValuePair[] getNameValuePairs(String  eid,
            Map<String, String> parameters);

}