package com.newco.marketplace.util;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.dao.slkSecure.SlkSecureDao;

/**
 * @author infosys
 * For Encrypting and Decrypting creditcard and accountno using 128 bit
 */

public class Cryptography128 {
	
	private static final Logger logger = Logger.getLogger(Cryptography128.class.getName());
	SlkSecureDao slkSecureDao;
	

	/**
	 * @param cryptographyVO
	 * @return cryptographyVO
	 */
	public CryptographyVO encryptKey128Bit(CryptographyVO cryptographyVO){

		logger.debug("Cryptography128-->encryptKey()");
		try{
			String keyValue = slkSecureDao.valueForKey(cryptographyVO.getKAlias());
			SecretKey secret = null;
			SecretKeyFactory factory = SecretKeyFactory.getInstance(MPConstants.SECRET_KEY_ALGORITHM);
			KeySpec spec = new PBEKeySpec(keyValue.toCharArray(), keyValue.getBytes(),65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();
			secret = new SecretKeySpec(raw, MPConstants.SECRET_KEY);
			Cipher cipher = Cipher.getInstance(MPConstants.SECRET_KEY);
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			byte[] encrypted = cipher.doFinal((cryptographyVO.getInput()).getBytes(MPConstants.SECRET_KEY_BYTES));
			cryptographyVO.setResponse(new BASE64Encoder().encode(encrypted));
			
			logger.debug("Cryptography128-->encryptKey()-->");
		}
		catch(Exception e){
			logger.error("Cryptography128-->encryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return cryptographyVO;
	}
	/**
	 * @param cryptographyVO
	 * @return cryptographyVO
	 */
	public CryptographyVO decryptKey128Bit(CryptographyVO cryptographyVO) {
		
		String originalString = null;
		logger.debug("Cryptography128-->decryptKey()-->");
		try {
			SecretKey secret = null;
			String keyValue = slkSecureDao.valueForKey(cryptographyVO.getKAlias());
			SecretKeyFactory factory = SecretKeyFactory.getInstance(MPConstants.SECRET_KEY_ALGORITHM);
			KeySpec spec = new PBEKeySpec(keyValue.toCharArray(), keyValue.getBytes(),65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();
			secret = new SecretKeySpec(raw,  MPConstants.SECRET_KEY);
			Cipher cipher = Cipher.getInstance( MPConstants.SECRET_KEY);
			byte[] encrypted = new BASE64Decoder().decodeBuffer(cryptographyVO.getInput());
			cipher.init(Cipher.DECRYPT_MODE, secret);
			byte[] original = cipher.doFinal(encrypted);
			originalString = new String(original, MPConstants.SECRET_KEY_BYTES);
			cryptographyVO.setResponse(originalString);			
		} 
		catch (Exception e) {
			logger.error("Cryptography128-->decryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return cryptographyVO;
	}
	
	// Adding the methods for SL-20050
	/**
	 * @param cryptographyVO
	 * @return cryptographyVO
	 */
	public SecretKey generateSecretKey(CryptographyVO cryptographyVO) {
		SecretKey secret = null;
		try {
			String keyValue = slkSecureDao.valueForKey(cryptographyVO.getKAlias());
			SecretKeyFactory factory = SecretKeyFactory.getInstance(MPConstants.SECRET_KEY_ALGORITHM);
			KeySpec spec = new PBEKeySpec(keyValue.toCharArray(), keyValue.getBytes(),65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();
			secret = new SecretKeySpec(raw,  MPConstants.SECRET_KEY);			
		} 
		catch (Exception e) {
			logger.error("Cryptography128-->generateSecretKey()-->Exception-->" + e.getMessage(), e);
		}
		return secret;
	}
	
	public CryptographyVO encryptWithSecretKey(CryptographyVO cryptographyVO, SecretKey secret) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance(MPConstants.SECRET_KEY);
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			byte[] encrypted = cipher.doFinal((cryptographyVO.getInput()).getBytes(MPConstants.SECRET_KEY_BYTES));
			cryptographyVO.setResponse(new BASE64Encoder().encode(encrypted));
		} catch (Exception e) {
			logger.error("Cryptography128-->encryptWithSecretKey()-->Exception-->" + e.getMessage(), e);
		}
		return cryptographyVO;
	}

	public CryptographyVO decryptWithSecretKey(CryptographyVO cryptographyVO, SecretKey secret) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance( MPConstants.SECRET_KEY);
			byte[] encrypted = new BASE64Decoder().decodeBuffer(cryptographyVO.getInput());
			cipher.init(Cipher.DECRYPT_MODE, secret);
			byte[] original = cipher.doFinal(encrypted);
			String  originalString = new String(original, MPConstants.SECRET_KEY_BYTES);
			cryptographyVO.setResponse(originalString);	
		} catch (Exception e) {
			logger.error("Cryptography128-->decryptWithSecretKey()-->Exception-->" + e.getMessage(), e);
		}
		return cryptographyVO;
	}

	public SlkSecureDao getSlkSecureDao() {
		return slkSecureDao;
	}

	public void setSlkSecureDao(SlkSecureDao slkSecureDao) {
		this.slkSecureDao = slkSecureDao;
	}


	
}
