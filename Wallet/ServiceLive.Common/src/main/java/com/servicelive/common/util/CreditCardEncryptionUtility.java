package com.servicelive.common.util;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.servicelive.common.properties.ISlkSecureDao;

import sun.misc.BASE64Decoder;

public class CreditCardEncryptionUtility {
	
	//Commenting the code for SL-18789
	//public static String creditCardEncryptionKey = "P3Q6GUCryVD51h5JOo0WMQ==";
	
	//Added code for SL-18789
	public static String CC_ENCRYPTION_KEY = "CCENKEY";
	//public ISlkSecureDao slkSecureDao;
	private static ISlkSecureDao slkSecureDaoWallet;
	
	 public CreditCardEncryptionUtility(ISlkSecureDao slkSecureDaoWallet) {
		 CreditCardEncryptionUtility.slkSecureDaoWallet = slkSecureDaoWallet;
	 }
		
	// This function is called from Mapforce.  Please do not change the function name or signature, unless
	// you also change mappingUtil.mff in the MarketServiceBus.Mapforce.Map project.  
	public static String decryptCreditCardNumber(String encryptedCardNumber) {
		
		String originalString = null;
		//logger.debug("Cryptography-->decryptKey()-->"+ cryptographyVO.getInput());
		try {
			//Commenting the code for SL-18789
			//byte[] raw = new BASE64Decoder().decodeBuffer(creditCardEncryptionKey);
			
			SecretKey secret = null;
			//String prop = (new CreditCardEncryptionUtility()).slkSecureDao.valueForKey(CC_ENCRYPTION_KEY);
			String prop = slkSecureDaoWallet.valueForKey(CC_ENCRYPTION_KEY);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(prop.toCharArray(), prop.getBytes(),65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();
			secret = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] encrypted = new BASE64Decoder().decodeBuffer(encryptedCardNumber);
			cipher.init(Cipher.DECRYPT_MODE, secret);
			byte[] original = cipher.doFinal(encrypted);
			originalString = new String(original, "8859_1");
			return originalString;
			//logger.debug("Cryptography-->encryptKey()-->Result="+ originalString);
			
		} 
		catch (Exception e) {
			//logger.error("Cryptography-->decryptKey()-->Exception-->" + e.getMessage(), e);
		}
		return encryptedCardNumber;		
	}
	
	public static ISlkSecureDao getSlkSecureDaoWallet() {
		return slkSecureDaoWallet;
	}
	public static void setSlkSecureDaoWallet(ISlkSecureDao slkSecureDaoWallet) {
		CreditCardEncryptionUtility.slkSecureDaoWallet = slkSecureDaoWallet;
	}
		
}
