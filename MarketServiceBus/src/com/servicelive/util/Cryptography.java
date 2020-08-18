package com.servicelive.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.servicelive.esb.constant.NPSConstants;
import com.servicelive.esb.vo.CryptographyVO;


public class Cryptography {
	
	private static final Logger logger = Logger.getLogger(Cryptography.class.getName());

	

	public CryptographyVO decryptKey(CryptographyVO cryptographyVO) {
		
		String originalString = null;
		logger.debug("Cryptography-->decryptKey()-->");
		try {
						byte[] raw = new BASE64Decoder().decodeBuffer(NPSConstants.CREDIT_CARD_ENCRYPTION_KEY);
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


	
}
