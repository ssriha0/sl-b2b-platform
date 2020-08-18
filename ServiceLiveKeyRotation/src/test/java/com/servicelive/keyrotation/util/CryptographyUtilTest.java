package com.servicelive.keyrotation.util;

import javax.crypto.SecretKey;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.email.EmailUtil;
import com.servicelive.keyrotation.util.CryptographyUtil;

public class CryptographyUtilTest {
	private Logger logger =Logger.getLogger(CryptographyUtilTest.class);
	private static String key =  "P3Q6GUCryVD51h5JOo0WMQ==";
	private static String bit = "64";
    private CryptographyUtil util;
    public static void main(String[] args) {
			KeyRotationConstants.BIT_128 = "128";
			KeyRotationConstants.BIT_64 = "64";
		}
	@Test
	public void encryptDecryptTest(){
		util = new CryptographyUtil();
		String password = "Infy@190833";
		String encryptedPassword = null;
		String decryptedpassword = null;
		KeyRotationConstants.BIT_128 = "128";
		KeyRotationConstants.BIT_64 = "64";
		try{
			SecretKey secretNew = util.getSecretForBits(key,bit);
			encryptedPassword = util.encryptKey(password,secretNew);
		    decryptedpassword = util.decryptKey(encryptedPassword,secretNew);
		}catch(Exception e){
			logger.error("Exception in encryptingDecrypting logic:"+ e.getMessage());
		}
		Assert.assertEquals(password, decryptedpassword);
	}
}
