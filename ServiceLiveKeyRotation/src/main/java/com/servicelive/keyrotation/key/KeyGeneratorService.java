package com.servicelive.keyrotation.key;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.db.SLKeyRotationDAO;
import com.servicelive.keyrotation.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class KeyGeneratorService {

	public String generateAndPersistKey(String passPhrase,
			Connection connectionSchemaSlk, String loggedUser) throws Exception {
		Log.writeLog(Level.INFO, "Starting to generate Key : ");
		String keyForDb = generate128BitKey(passPhrase);
		// persist the key in SLK database for CCNKEY_TEMP key
		Log.writeLog(Level.INFO, "Key generated...");

		SLKeyRotationDAO dao = new SLKeyRotationDAO();
		dao.persistKey(keyForDb, connectionSchemaSlk, loggedUser);
		
		return keyForDb;
	}

	private String generate128BitKey(String passPhrase) throws Exception {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		int seedByteCount = 10;
		byte[] seed = secureRandom.generateSeed(seedByteCount);
		secureRandom.setSeed(seed);

		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(),
				SecureRandom.getSeed(10), 65536, 128);

		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.ENCRYPT_MODE, secret);

		String strDataToEncrypt = passPhrase + secureRandom.nextLong();
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
		String strCipherText = new BASE64Encoder().encode(byteCipherText);

		return strCipherText;
	}
}
