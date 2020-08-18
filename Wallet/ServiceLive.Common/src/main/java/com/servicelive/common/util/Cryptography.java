package com.servicelive.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.servicelive.common.properties.IApplicationProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class Cryptography.
 */
public class Cryptography {

	/** The Constant ENCRYPTION_KEY. */
	public static final String ENCRYPTION_KEY = "enKey";

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Cryptography.class.getName());

	/** The application properties. */
	IApplicationProperties applicationProperties;

	/**
	 * Decrypt key.
	 * 
	 * @param encryptedInput 
	 * 
	 * @return the string
	 */
	public String decryptKey(String encryptedInput) {

		String originalString = null;
		logger.debug("Cryptography-->decryptKey()-->");
		try {
			String prop = applicationProperties.getPropertyValue(ENCRYPTION_KEY);

			byte[] raw = new BASE64Decoder().decodeBuffer(prop);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] encrypted = new BASE64Decoder().decodeBuffer(encryptedInput);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(encrypted);
			originalString = new String(original, "8859_1");
			logger.debug("Cryptography-->encryptKey()-->");

		} catch (Exception e) {
			logger.error("Cryptography-->decryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return originalString;
	}

	/**
	 * Encrypt key.
	 * 
	 * @param clearInput 
	 * 
	 * @return the string
	 */
	public String encryptKey(String clearInput) {

		logger.debug("Cryptography-->encryptKey()-->");
		String response = null;
		try {
			String prop = applicationProperties.getPropertyValue(ENCRYPTION_KEY);

			byte[] raw = new BASE64Decoder().decodeBuffer(prop);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(clearInput.getBytes("8859_1"));
			response = new BASE64Encoder().encode(encrypted);

			logger.debug("Cryptography-->encryptKey()-->");
		} catch (Exception e) {
			logger.error("Cryptography-->encryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return response;
	}

	/**
	 * 
	 * 
	 * @return the applicationPropertiesDao
	 */
	public IApplicationProperties getApplicationProperties() {

		return applicationProperties;
	}

	/**
	 * 
	 * 
	 * @param applicationProperties 
	 */
	public void setApplicationProperties(IApplicationProperties applicationProperties) {

		this.applicationProperties = applicationProperties;
	}

}
