package com.servicelive.spn.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;



import com.newco.marketplace.utils.CryptoUtil.CannotPerformOperationException;

import sun.misc.BASE64Encoder;

/**
 * @author hoza
 *
 */
public class CryptoUtil {
	/**
	 * 
	 * @param plaintext
	 * @return String
	 */
	public static synchronized String encryptCredentials(String plaintext)  {

		String algorithm = "SHA-384";
		MessageDigest md;
		String hashSalt = null;
	    try {
	    	md = MessageDigest.getInstance(algorithm); // step 2
	    	md.update(plaintext.getBytes("UTF-8")); // step 3
	    } catch(NoSuchAlgorithmException e) {
	    	throw new RuntimeException("Couldn't find " + algorithm + " algorithm", e);
	    } catch(UnsupportedEncodingException e) {
	    	throw new RuntimeException("Couldn't update message", e);
	    }

	    byte raw[] = md.digest(); // step 4
	    String hash = (new BASE64Encoder()).encode(raw); // step 5
	    try {
	    	try {
				hash = com.newco.marketplace.utils.CryptoUtil.generateStrongPasswordHash(hash.toCharArray());
			} catch (CannotPerformOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	    return hashSalt; // step 6
	  }


}
