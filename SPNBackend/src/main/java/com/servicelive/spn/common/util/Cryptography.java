package com.servicelive.spn.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;

public class Cryptography {
	
	private static final Logger logger = Logger.getLogger(Cryptography.class.getName());
	IApplicationPropertiesDao applicationPropertiesDao;
	
	public CryptographyVO encryptKey(CryptographyVO cryptographyVO){

		logger.debug("Cryptography-->encryptKey()");
		try{
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(cryptographyVO.getKAlias());
			
			byte[] raw = new BASE64Decoder().decodeBuffer(prop.getAppValue());
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal((cryptographyVO.getInput()).getBytes("8859_1"));
			cryptographyVO.setResponse(new BASE64Encoder().encode(encrypted));
			
			logger.debug("Cryptography-->encryptKey()-->");
		}
		catch(Exception e){
			logger.error("Cryptography-->encryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return cryptographyVO;
	}

	public CryptographyVO decryptKey(CryptographyVO cryptographyVO) {
		
		String originalString = null;
		logger.debug("Cryptography-->decryptKey()-->");
		try {
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(cryptographyVO.getKAlias());
			
			byte[] raw = new BASE64Decoder().decodeBuffer(prop.getAppValue());
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] encrypted = new BASE64Decoder().decodeBuffer(cryptographyVO.getInput());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(encrypted);
			originalString = new String(original, "8859_1");
			cryptographyVO.setResponse(originalString);
			logger.debug("Cryptography-->encryptKey()-->");
			
		} 
		catch (Exception e) {
			logger.error("Cryptography-->decryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return cryptographyVO;
	}

	/**
	 * @return the applicationPropertiesDao
	 */
	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}

	/**
	 * @param applicationPropertiesDao the applicationPropertiesDao to set
	 */
	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
	}

	
}
