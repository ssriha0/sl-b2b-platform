package com.servicelive.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;


public class SmsAlert {

	private static final int MAX_SMS_TEXT_SZ = 160;
	private static final Logger logger = Logger.getLogger(SmsAlert.class
			.getName());
	private static final String EMAIL_DELIMITER = ";";
	public static final String SUCCESS = "success";
	public static Integer sendMessage(String to, String cc, String bcc, String message) {
		String from = 	 System.getProperty("smsalert.provider.campaignid");
		if (from  == null ) {
			logger.error(" System Properties  : *sms.provider.campaignid* for the SMS Provider is Unavailable");
			return null;
		}

		String smsUrl = System.getProperty("smsalert.provider.host");
		String smsProviderUsername = System.getProperty("sms.provider.username");
		String smsproviderCredential = System.getProperty("sms.provider.credential");
		String smsprovidermethod = CommonConstants.SMSALERT_PROVIDER_METHOD_ONE
				+ System.getProperty("smsalert.provider.campaignid")
				+ CommonConstants.SMSALERT_PROVIDER_METHOD_TWO;
		String smsProviderSSLPort = System.getProperty("https.port");

		if (smsUrl == null || smsProviderUsername == null || smsproviderCredential == null || smsProviderSSLPort == null) {
			logger.error(" System Properties for the SMS Provider is Unavailable");
			return null;
		}

		if (message.length() > MAX_SMS_TEXT_SZ) {
			message = message.substring(0, MAX_SMS_TEXT_SZ - 1);
		}

		HttpClient client = new HttpClient();
		HostConfiguration host = client.getHostConfiguration();
		Integer sslPort = Integer.valueOf(smsProviderSSLPort);
		int successCount = 0;
		try {

			host.setHost(smsUrl, sslPort.intValue(), new Protocol("https",
					(ProtocolSocketFactory) new EasySSLProtocolSocketFactory(),
					sslPort.intValue()));
			
			String proxyEnabled=System.getProperty("http.proxy.set");
            if (!(proxyEnabled.equalsIgnoreCase(null))
					&& proxyEnabled.equals("true")) {
            String portValue=System.getProperty("http.proxy.port");
            int port=0;
            if(portValue!=null){
                    try{
                            port=Integer.parseInt(portValue);
                    }catch(NumberFormatException e){
                    logger.info("Exception Occured :"+ e);
						return null;
                    }
            }
            host.setProxy(System.getProperty("http.proxy.host"), port);
            } 

			String toAddr[] = to.split(EMAIL_DELIMITER);
			String ccAddr[] = cc.split(EMAIL_DELIMITER);
			String bccAddr[] = bcc.split(EMAIL_DELIMITER);
			StringBuffer sb = new StringBuffer();
			sb.append("<message><text>");
			sb.append(message);
			sb.append("</text><users>");

			List<String> toAddrList = Arrays.asList(toAddr);
			List<String> ccAddrList = Arrays.asList(ccAddr);
			List<String> bccAddrList = Arrays.asList(bccAddr);
			List<String> dist = new ArrayList<String>();
			dist.addAll(toAddrList);
			dist.addAll(ccAddrList);
			dist.addAll(bccAddrList);
			
			//Removing the duplicate entries
			Set<String> uniquePhones = new HashSet<String>(dist);
			
			if (!uniquePhones.isEmpty()) {
				for (String ph:uniquePhones) {
					if(ph != null && !ph.trim().equals("")){
					sb.append("<mobile_phone>");
						sb.append(ph);
					sb.append("</mobile_phone>");
				}
			}
				}

			sb.append("</users></message>");

			PostMethod method = new PostMethod(smsUrl
					+ smsprovidermethod);
			String requestXml = sb.toString();

			try {
				method.setRequestEntity(new StringRequestEntity(requestXml,
						"application/xml", "UTF-8"));

				method.addRequestHeader(CommonConstants.SMSALERT_PROVIDER_HEADER_NAME, System
						.getProperty("smsalert.provider.header.value"));

				int returnCode = client.executeMethod(method);
				String responseString = method.getResponseBodyAsString();

				if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
					logger
							.error("SMSAlert.java : SMS Provider we are using HAVE NOT IMPLEMNETED POST Method yet. URI for SMS provider is  : "
									+ smsUrl);
					logger.error(responseString);
				} else if (returnCode == HttpStatus.SC_OK
						&& responseString.contains(SUCCESS)) {
					logger.info("Response from service was :"
							+ method.getResponseBodyAsString());
					successCount++;
				} else if (returnCode == HttpStatus.SC_OK
						&& !responseString.contains(SUCCESS)) {
					logger.error("Return code OK, but not success!!");
					logger.error(responseString);
				} else {
					logger.error("Trying to Send SMS " + " with message \n "
							+ message + " \n but got return code of : "
							+ method.getStatusText() + "and the response is "
							+ responseString);
				}
			} catch (IllegalArgumentException e) {
				logger.error(
						"SmsAlert.java : IllegalArgumentException :  while trying to SMS to "
								+ to, e);

			} finally {
				method.releaseConnection();
			}
		} catch (Exception e1) {
			logger.error("SmsAlert.java : MalFormed URI :  while trying to SMS to " + to, e1);
		}
		return Integer.valueOf(successCount);
		}
	/** Method to call API for subscribing a mobile number for SMS service
	 * @param String to
	 * @param String message
	 * @return boolean
	 */

	public static boolean createSubscription(String to, String message) {

		String smsUrl = System.getProperty("smsalert.provider.host");
		String smsprovidermethod = CommonConstants.SMSALERT_PROVIDER_METHOD_ONE
				+ System.getProperty("smsalert.provider.campaignid")
				+ CommonConstants.SMSALERT_PROVIDER_METHOD_SUBSCRIBE;
		String smsProviderSSLPort = System.getProperty("https.port");

		if (null == smsUrl || null == smsprovidermethod
				|| null == smsProviderSSLPort) {
			logger
					.error(" System Properties for the SMS Provider is Unavailable");
			return false;
		}

		HttpClient client = new HttpClient();
		HostConfiguration host = client.getHostConfiguration();
		Integer sslPort = Integer.valueOf(smsProviderSSLPort);
		int successCount = 0;
		try {

			host.setHost(smsUrl, sslPort.intValue(), new Protocol("https",
					(ProtocolSocketFactory) new EasySSLProtocolSocketFactory(),
					sslPort.intValue()));
			
			String proxyEnabled=System.getProperty("http.proxy.set");
            if(!(proxyEnabled.equalsIgnoreCase(null))&& proxyEnabled.equals("true"))
            {
			String portValue=System.getProperty("http.proxy.port");
			int port=0;
			if(portValue!=null){
				try{
					port=Integer.parseInt(portValue);
				}catch(NumberFormatException e){
					
				}
			}
			host.setProxy(System.getProperty("http.proxy.host"), port);
            }
			
			PostMethod method = new PostMethod(smsUrl
					+ smsprovidermethod);
			String requestXml = message;

			try {
				
				method.setRequestEntity(new StringRequestEntity(requestXml,
						"application/xml", "UTF-8"));

				method.addRequestHeader(CommonConstants.SMSALERT_PROVIDER_HEADER_NAME, System
						.getProperty("smsalert.provider.header.value"));

				int returnCode = client.executeMethod(method);
				String responseString = method.getResponseBodyAsString();

				if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
					logger
							.error("SMSAlert.java : SMS Provider we are using HAVE NOT IMPLEMNETED POST Method yet. URI for SMS provider is  : "
									+ smsUrl);
					logger.error(responseString);
				} else if (returnCode == HttpStatus.SC_OK
						&& !responseString.contains(CommonConstants.SUB_ERROR)) {
					logger.info("Response from service was :"
							+ method.getResponseBodyAsString());
					successCount++;
				} else if (returnCode == HttpStatus.SC_OK
						&& responseString.contains(CommonConstants.SUB_ERROR)) {
					logger.error("Return code OK, but not success!!");
					logger.error(responseString);
				} else if (returnCode == HttpStatus.SC_UNPROCESSABLE_ENTITY
						&& (responseString.contains(CommonConstants.SUB_REQUESTED)||
								responseString.contains(CommonConstants.SUB_ADDED))){
					logger.info("Mobile number is already subscribed");
					logger.info(responseString);
					successCount++;
				} else {
					logger.error("Trying to Subscribe " 
							+ " \n but got return code of : "
							+ method.getStatusText() + "and the response is "
							+ responseString);
				}
			} catch (IllegalArgumentException e) {
				logger.error(
						"SmsAlert.java : IllegalArgumentException :  while trying to SMS to "
								+ to, e);

			} finally {
				method.releaseConnection();
			}
		} catch (Exception e1) {
			logger.error(
					"SmsAlert.java : MalFormed URI :  while trying to subscribe number  "
							+ to, e1);
		}
		if (0 < successCount) {
			return true;
		} else {
			return false;
		}
	}
}
