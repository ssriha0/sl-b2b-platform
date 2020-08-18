package com.newco.marketplace.utils.utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.AOPConstants;

public class EmailAlertUtility {

	private static final Logger logger = Logger.getLogger(EmailAlertUtility.class.getName());
	/**
	 * @param emails
	 * @param listOfEmails
	 * it adds the emailids to the list
	 */
	public void addemailsToList(List<String> emails, String listOfEmails) {
		if (null != listOfEmails) {
			StringTokenizer toTokens = new StringTokenizer(listOfEmails, ";");
			while (toTokens.hasMoreElements()) {
				String toEmail = toTokens.nextToken();
				if (!((toEmail == null)
						|| (org.apache.commons.lang.StringUtils
								.isEmpty(toEmail)) || (!toEmail.contains("@")))) {
					emails.add(toEmail);
				}
			}
		}
	}

	/**
	 * @param keyValue
	 * @return
	 * @throws Exception
	 * 
	 * it tokenizes the template value and stores it in the hash map
	 */
	public Map<String, String> toParameterMap(String keyValue) throws Exception {
		Map<String, String> parameterMap = new HashMap<String, String>();
		if (null != keyValue) {
			StringTokenizer st = new StringTokenizer(keyValue, "|");
			while (st.hasMoreTokens()) {
				String myKeyValue = st.nextToken();
				if (myKeyValue
						.startsWith(AOPConstants.AOP_REROUTE_SO_PROVIDER_PROMO_CONTENT)
						|| myKeyValue
								.startsWith(AOPConstants.AOP_ROUTE_SO_PROVIDER_PROMO_CONTENT)) {
					int firstIndex = myKeyValue.indexOf("=");
					String vContextKey = myKeyValue.substring(0, firstIndex);
					String vContextKeyValue = myKeyValue
							.substring(firstIndex + 1);
					parameterMap.put(vContextKey, vContextKeyValue);
				} else {

					int firstIndex = myKeyValue.indexOf("=");
					if(firstIndex != -1){
						String vContextKey = myKeyValue.substring(0, firstIndex);
						String vContextKeyValue = myKeyValue
								.substring(firstIndex + 1);
						parameterMap.put(vContextKey, vContextKeyValue);
					}else{
						parameterMap.put(myKeyValue, "");
					}
				}

			}
		}
		return parameterMap;
	}

	/**
	 * @param emails
	 * @return
	 * it removes the duplicate entries for the same alert task record
	 */
	public Collection<String> removeDuplicateEntries(List<String> emails) {

		Set<String> tokens = new HashSet<String>();
		tokens.addAll(emails);
		return tokens;
	}
	
	/**
	 * @param keyValue
	 * @return
	 * @throws Exception
	 * 
	 * it tokenizes the template value and stores it in the hash map
	 */
	public Map<String, String> toJsonParameterMap(String keyValue) throws Exception {
		Map<String, String> parameterMap = new HashMap<String, String>();
		if (null != keyValue) {
			String previousToken="";
			while(keyValue.length()>0)
			{
				int index = keyValue.indexOf("|");
				String param=keyValue;
				if(index > -1)
				{
					param = keyValue.substring(0, index);
					keyValue=keyValue.substring(index+1);
				}
				else
				{
					keyValue="";
				}
				if(param.indexOf("=")>-1)
				{	
					String tokenArr[] = param.split("=");
					
					if(tokenArr.length==2)
					{
						previousToken=tokenArr[0];
						parameterMap.put(previousToken, tokenArr[1]);
					}
					else if(tokenArr.length==1)
					{
						previousToken=tokenArr[0];
						parameterMap.put(previousToken, "");
					}
					else if (tokenArr.length>2)
					{
						int i=param.indexOf("=");
						previousToken=param.substring(0, i);
						parameterMap.put(previousToken, param.substring(i+1));
					}

				}
				else
				{
					parameterMap.put(previousToken, parameterMap.get(previousToken) + "|" + param);
				}

			}
		}
		return parameterMap;
	}

	
	/**
	 * @param emails
	 * @param listOfEmails
	 * it adds the emailids to the list
	 */
	public void addValidEmailsToList(List<String> emails, String listOfEmails) {
		if (null != listOfEmails) {
			StringTokenizer toTokens = new StringTokenizer(listOfEmails, ";");
			while (toTokens.hasMoreElements()) {
				String toEmail = toTokens.nextToken();
				if (!((toEmail == null)
						|| (org.apache.commons.lang.StringUtils
								.isEmpty(toEmail)) || (!isValid(toEmail)))) {
					emails.add(toEmail);
				}
				else{
					logger.info("EmailAlertUtility-->addValidEmailsToList() - email validation failed for email: " + toEmail);
				}
			}
		}
	}
	

	public  boolean isValid(String email) 
	{ 
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 

		Pattern pat = Pattern.compile(emailRegex); 
		
		if (email == null) 
			return false;
		
		return pat.matcher(email.trim()).matches(); 
	} 

	
}
