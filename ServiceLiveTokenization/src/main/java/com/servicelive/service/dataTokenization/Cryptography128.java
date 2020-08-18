package com.servicelive.service.dataTokenization;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * @author infosys
 * For Encrypting and Decrypting creditcard and accountno using 128 bit
 */

public class Cryptography128 {
	
	private static final Logger logger = Logger.getLogger(Cryptography128.class.getName());
	

	
	//sl-20853
	/**
	 * @param cryptographyVO
	 * @return cryptographyVO
	 */
	public CryptographyVO decryptKey128Bit(CryptographyVO cryptographyVO) {
		
		String originalString = null;
		logger.debug("Cryptography128-->decryptKey()-->");
		try {
			SecretKey secret = null;
			String keyValue =cryptographyVO.getEncryptionKey(); 
			//slkSecureDao.valueForKey(cryptographyVO.getKAlias());
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
			logger.info("Cryptography128-->decryptKey()-->Exception-->" + e.getMessage(), e);
			e.printStackTrace();
		}
		return cryptographyVO;
	}
	
	
	


	
}
