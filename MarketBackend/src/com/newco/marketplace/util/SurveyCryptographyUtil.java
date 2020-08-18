package com.newco.marketplace.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;

public class SurveyCryptographyUtil {

	private static final Logger logger = Logger.getLogger(SurveyCryptographyUtil.class.getName());
	IApplicationPropertiesDao applicationPropertiesDao;
	
	public CryptographyVO encryptKey(CryptographyVO cryptographyVO){

		logger.debug("SurveyCryptographyUtil-->encryptKey()");
		try{
			 ApplicationPropertiesVO prop = applicationPropertiesDao.query(cryptographyVO.getKAlias());
			
			byte[] raw = new BASE64Decoder().decodeBuffer(prop.getAppValue());
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal((cryptographyVO.getInput()).getBytes("8859_1"));
			String encryptedValue = new BASE64Encoder().encode(encrypted);

	        String urlEncodeddata=URLEncoder.encode(encryptedValue,"UTF-8");
			cryptographyVO.setResponse(urlEncodeddata);
			
			logger.debug("SurveyCryptographyUtil-->encryptKey()-->");
		}
		catch(Exception e){
			logger.error("SurveyCryptographyUtil-->encryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return cryptographyVO;
	}

	public CryptographyVO decryptKey(CryptographyVO cryptographyVO) {
		
		String originalString = null;
		logger.debug("SurveyCryptographyUtil-->decryptKey()-->");
		try {
			 ApplicationPropertiesVO prop = applicationPropertiesDao.query(cryptographyVO.getKAlias());
			
			byte[] raw = new BASE64Decoder().decodeBuffer(prop.getAppValue());
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			String urlDecodedData=URLDecoder.decode(cryptographyVO.getInput(), "UTF-8");
			byte[] decoded = new BASE64Decoder().decodeBuffer(urlDecodedData);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(decoded);
			originalString = new String(original, "8859_1");
			cryptographyVO.setResponse(originalString);
			logger.debug("SurveyCryptographyUtil-->encryptKey()-->");
			
		} 
		catch (Exception e) {
			logger.error("SurveyCryptographyUtil-->decryptKey()-->Exception-->" + e.getMessage(), e);
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
