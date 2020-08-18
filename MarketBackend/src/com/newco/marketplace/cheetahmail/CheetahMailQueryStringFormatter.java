package com.newco.marketplace.cheetahmail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;

public class CheetahMailQueryStringFormatter implements
        MailQueryStringFormatter {

    private static final String ENCODING_UTF_8 = "UTF-8";
    private Properties emailProperties;

    public void setEmailProperties(Properties emailProperties) {
        this.emailProperties = emailProperties;
    }

    public String getLoginQueryString() {
        return LOGIN_NAME_KEY + "="
                + emailProperties.getProperty(LOGIN_NAME_KEY) + "&"
                + LOGIN_PASSWORD_KEY + "="
                + emailProperties.getProperty(LOGIN_PASSWORD_KEY);
    }

    public String getSendEmailHeaderString(String  eid,
            Map<String, String> parameters) {
        String queryString = APPLICATION_ID_KEY + "="
                + emailProperties.getProperty(APPLICATION_ID_KEY) + "&"
                + EMAIL_TYPE_ID_KEY + "=" + eid ;
        return queryString;
    }
    
    public NameValuePair[] getNameValuePairs(String  eid,
            Map<String, String> parameters) {
     

        Iterator<Entry<String, String>> pairs = parameters.entrySet().iterator();
        ArrayList<NameValuePair> nameValPairs = new ArrayList<NameValuePair>();
        Entry<String, String> curPair;

        while(pairs.hasNext()) {
        	curPair = pairs.next();
        	
            	if(curPair.getKey().equals("email")){
            		nameValPairs.add(new NameValuePair(curPair.getKey(),curPair.getValue()));
            	}else
            	{
            		nameValPairs.add(new NameValuePair(StringUtils.upperCase(curPair.getKey()),curPair.getValue()));

            	}

        }

        return nameValPairs.toArray(new NameValuePair[nameValPairs.size()]);
    }

}
