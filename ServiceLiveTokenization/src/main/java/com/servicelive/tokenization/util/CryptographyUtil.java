package com.servicelive.tokenization.util;

import java.security.spec.KeySpec;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.servicelive.tokenization.constants.TokenizationConstants;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class CryptographyUtil {

	public String encryptKey(String stringToEncrypt, String key,
			String bitLength) throws Exception {
		try {
			SecretKey secret = this.getSecretForBits(key, bitLength);

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			byte[] encrypted = cipher.doFinal((stringToEncrypt)
					.getBytes("8859_1"));
			String encodedString = new BASE64Encoder().encode(encrypted);

			return encodedString;
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Error while encrypting value!!!!" + e.getMessage());
			throw new Exception("Error while encrypting value!!!!" + "  "
					+ e.getMessage() + " " + e);
		}
	}

	public String decryptKey(String stringToDecrypt, String key,
			String bitLength) throws Exception {
		try {
			SecretKey secret = this.getSecretForBits(key, bitLength);

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secret);

			byte[] encrypted = new BASE64Decoder()
					.decodeBuffer(stringToDecrypt);

			byte[] original = cipher.doFinal(encrypted);
			String originalString = new String(original, "8859_1");

			return originalString;
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Error while decrypting value!!!!" + e.getMessage());
			throw new Exception("Error while decrypting value!!!!" + "  "
					+ e.getMessage() + " " + e);
		}
	}

	public SecretKey getSecretForBits(String key, String bits)
			throws Exception {
		SecretKey secret = null;
		if (TokenizationConstants.BIT_128.equals(bits)) {
			SecretKeyFactory factory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), key.getBytes(),
					65536, 128);
			byte[] raw = factory.generateSecret(spec).getEncoded();

			secret = new SecretKeySpec(raw, "AES");
		} else if (TokenizationConstants.BIT_64.equals(bits)) {
			byte[] raw = new BASE64Decoder().decodeBuffer(key);

			secret = new SecretKeySpec(raw, "AES");
		}
		return secret;
	}
	
	public String encryptKey(String stringToEncrypt, SecretKey secret) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			byte[] encrypted = cipher.doFinal((stringToEncrypt)
					.getBytes("8859_1"));
			String encodedString = new BASE64Encoder().encode(encrypted);

			return encodedString;
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Error while encrypting value!!!!" + e.getMessage());
			throw new Exception("Error while encrypting value!!!!" + "  "
					+ e.getMessage() + " " + e);
		}
	}

	public String decryptKey(String stringToDecrypt, SecretKey secret) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secret);

			byte[] encrypted = new BASE64Decoder()
					.decodeBuffer(stringToDecrypt);

			byte[] original = cipher.doFinal(encrypted);
			String originalString = new String(original, "8859_1");

			return originalString;
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Error while decrypting value!!!!" + e.getMessage());
			throw new Exception("Error while decrypting value!!!!" + "  "
					+ e.getMessage() + " " + e);
		}
	}
}
