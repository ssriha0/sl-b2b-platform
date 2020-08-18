package com.servicelive.common.util;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.properties.ISlkSecureDao;

// TODO: Auto-generated Javadoc
/**
 * The Class Cryptography128.
 */
public class Cryptography128 {


	private static final Logger logger = Logger.getLogger(Cryptography128.class.getName());

	ISlkSecureDao slkSecureDao;
	/**
	 * Decrypt key.
	 * 
	 * @param encryptedInput 
	 * 
	 * @return the string
	 */
	public String decryptKey128Bit(String encryptedInput) {

		String originalString = null;
		logger.debug("Cryptography128-->decryptKey128Bit()--");
		try {
			SecretKey secret = null;
			String prop = slkSecureDao.valueForKey(CommonConstants.CC_ENCRYPTION_KEY);
			
			//SLT-2154 : Fetching the encryption key from the DB 
			String secretKeyAlgorithm=slkSecureDao.valueForKey(CommonConstants.SECRET_KEY_ALGORITHM_DUPLICATE);
			//Commenting the below code to fix the checkmarx issue.
			//SecretKeyFactory factory = SecretKeyFactory.getInstance(CommonConstants.SECRET_KEY_ALGORITHM);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyAlgorithm);

			KeySpec spec = new PBEKeySpec(prop.toCharArray(), prop.getBytes(),65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();
			secret = new SecretKeySpec(raw, CommonConstants.SECRET_KEY);
			Cipher cipher = Cipher.getInstance(CommonConstants.SECRET_KEY);
			byte[] encrypted = new BASE64Decoder().decodeBuffer(encryptedInput);
			cipher.init(Cipher.DECRYPT_MODE, secret);
			byte[] original = cipher.doFinal(encrypted);
			originalString = new String(original, CommonConstants.SECRET_KEY_BYTES);
			
			logger.debug("Cryptography128-->encryptKey()-->");

		} catch (Exception e) {
			logger.error("Cryptography128-->decryptKey()-->Exception-->" + e.getMessage(), e);
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
	public String encryptKey128Bit(String clearInput) {

		logger.debug("Cryptography128-->encryptKey()-->");
		String response = null;
		try {
			String prop = slkSecureDao.valueForKey(CommonConstants.CC_ENCRYPTION_KEY);
			//SLT-2154 : Fetching the encryption key from the DB 
			String secretKeyAlgorithm=slkSecureDao.valueForKey(CommonConstants.SECRET_KEY_ALGORITHM_DUPLICATE);
			SecretKey secret = null;
			//Commenting the below code to fix the checkmarx issue.
			//SecretKeyFactory factory = SecretKeyFactory.getInstance(CommonConstants.SECRET_KEY_ALGORITHM);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyAlgorithm);
			KeySpec spec = new PBEKeySpec(prop.toCharArray(), prop.getBytes(),65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();
			secret = new SecretKeySpec(raw, CommonConstants.SECRET_KEY);
			Cipher cipher = Cipher.getInstance(CommonConstants.SECRET_KEY);
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			byte[] encrypted = cipher.doFinal(clearInput.getBytes(CommonConstants.SECRET_KEY_BYTES));
			response = new BASE64Encoder().encode(encrypted);

			logger.debug("Cryptography128-->encryptKey()-->");
		} catch (Exception e) {
			logger.error("Cryptography128-->encryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return response;
	}

	public ISlkSecureDao getSlkSecureDao() {
		return slkSecureDao;
	}

	public void setSlkSecureDao(ISlkSecureDao slkSecureDao) {
		this.slkSecureDao = slkSecureDao;
	}

}
