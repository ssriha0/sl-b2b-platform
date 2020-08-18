/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-May-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.utility;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.UserVO;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.security.ISecurityBO;
import com.newco.marketplace.vo.login.LoginCredentialVO;

/**
 * This class acts as an utility class.
 * 
 * @author Infosys
 * @version 1.0
 */
public class CommonUtility {

	private static Logger logger = Logger.getLogger(CommonUtility.class);
	public static SimpleDateFormat sdfToDate = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");
	public static SimpleDateFormat sdf2ToDate = new SimpleDateFormat(
	"yyyy/MM/dd");
	public static SimpleDateFormat sdf3ToDate = new SimpleDateFormat(
	"MMddyyyy");
	public static SimpleDateFormat sdfToDateWithZone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	

	private static final Properties properties;
	private ISecurityBO securityBO;

	// The below static block will load the property file only once
	static{
		sdfToDateWithZone.setTimeZone(TimeZone.getTimeZone("GMT"));
		properties = new Properties();
		try{
			InputStream is = CommonUtility.class
			.getClassLoader()
			.getResourceAsStream(PublicAPIConstant.CODE_PROPERTY_FILE);
			properties.load(is);
		}catch(IOException e){
			logger.error("IO Exception while trying to load property file: " + e);
		}catch(Exception e){
			logger.error("Exception while trying to load property file: " + e);
		}
	}
	
	/**
	 * Static method for getting the internationalized message value for a 
	 * particular property code.
	 * 
	 * @param errorCode
	 * @return String errorMessage 
	 */
	public static String getMessage(String code) {
		return properties.getProperty(code);
	}
	
	
	/**
	 * This method  is similar to getMethod(String code) and is used to get 
	 * internationalized message value for a particular property code, except 
	 * that it also allows you to replace message with run time runtime arguments.
	 * 
	 * For e.g. property file may have an entry 
	 * 0012.create.buyer.account.username.already.exists=user {0} already exists.
	 * {0} will get replaced by arguments at run time.
	 * 
	 * @param code
	 * @param arguments
	 * @return String
	 */
	public static String getMessage(String code, ArrayList<Object> arguments) {
		Object argObjects[] = new Object[arguments.size()];

		int counter = 0;
		for(Iterator<Object> it=arguments.iterator(); it.hasNext(); ) {
			argObjects[counter] = it.next();
			counter++;
		}
		
		String propertyKey = properties.getProperty(code);
		MessageFormat mFormat = new MessageFormat(propertyKey);
		return mFormat.format(argObjects); 
	}
	
	/**
	 * This method creates a map of String key-value pairs from another map (from http request).
	 * Note that the map from http request is a map of Object key-value pairs.
	 * The purpose of this method is also to act as a common request processor for http <i>GET</i> methods.
	 *  @param attributeMap
	 * @return Map attributeMap
	 */	
	public static HashMap<String, String> getRequestMap(Map<String, String> attributeMap) {
		HashMap<String, String> reqMap = new HashMap<String, String>();
		Iterator<String> iterator = attributeMap.keySet().iterator();
		Object value = "";
		Object key = "";
		String[] valueArray = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			if (null != key) {
				value = attributeMap.get(key);
				valueArray = (String[]) value;				
				reqMap.put(((String)key).toLowerCase(), valueArray[0]);
			}
		}
		return reqMap;
	}
	
	/**
	 * This method returns the buyer details based on the username
	 *  @param username String
	 * @return userVO UserVO
	 */	
	public  UserVO getUserDetails(String username) {
		UserVO userVO= new UserVO();
		LoginCredentialVO lvo = new LoginCredentialVO();
		lvo.setUsername(username);
		LoginCredentialVO lcvo = getSecurityBO().getUserRoles(lvo);
		userVO.setRoleName(lcvo.getRoleName());
		userVO.setRoleId(lcvo.getRoleId());
		userVO.setUsername(username);
		userVO.setContactId(lcvo.getContactId());
		userVO.setFirstName(lcvo.getFirstName());
		userVO.setLastName(lcvo.getLastName());
		userVO.setEmail(lcvo.getEmail());
		userVO.setPhoneNo(lcvo.getPhoneNo());
		Map<String,?> buyer = getSecurityBO().getBuyerId(username);
		userVO.setBuyerId(((Long)buyer.get("buyerId")).intValue());
		userVO.setBuyerResourceId(((Long)buyer.get("buyerResourceId")).intValue());
		userVO.setClientId((Integer)buyer.get("clientId"));
		return userVO;
	}

	public ISecurityBO getSecurityBO() {
		return securityBO;
	}

	public void setSecurityBO(ISecurityBO securityBO) {
		this.securityBO = securityBO;
	}
}


