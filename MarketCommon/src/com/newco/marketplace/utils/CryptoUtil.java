package com.newco.marketplace.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptoUtil {
	
	 public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	 public static final int SALT_BYTE_SIZE = 24;
	 public static final int HASH_BYTE_SIZE = 18;
	 public static final int PBKDF2_ITERATIONS = 10000;
	 
	 // These constants define the encoding and may not be changed.
	    public static final int HASH_SECTIONS = 5;
	    public static final int HASH_ALGORITHM_INDEX = 0;
	    public static final int ITERATION_INDEX = 1;
	    public static final int HASH_SIZE_INDEX = 2;
	    public static final int SALT_INDEX = 3;
	    public static final int PBKDF2_INDEX = 4;

	public static synchronized String encryptKeyForSSNAndPlusOne(String plaintext)  {

		String hash = generateHash(plaintext);
		
	    return hash;
	  }

	private static byte[] write(Serializable obj) throws FileNotFoundException, IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(obj);
		os.flush();
		os.close();
		byte[] raw = bos.toByteArray();
		return raw;
	}

	private static Serializable read(byte[] serializedObject) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(serializedObject));
		return (Serializable) is.readObject();
	}

	private static final String sharedkey = "This is super secret don't you know".substring(0,16);

	public static String encryptObject(Serializable serializable) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
     	byte[] key = sharedkey.getBytes();
		byte[] dataToSend = write(serializable); 
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec k =  new SecretKeySpec(key, "AES");
		c.init(Cipher.ENCRYPT_MODE, k);
		byte[] encryptedData = c.doFinal(dataToSend);
		return new BASE64Encoder().encode(encryptedData);
	}

	public static Serializable decryptObject(String encryptedSharedSecret) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
		  byte[] key = sharedkey.getBytes();
			byte[] encryptedData = new BASE64Decoder().decodeBuffer(encryptedSharedSecret);
			Cipher c = Cipher.getInstance("AES");
			SecretKeySpec k =  new SecretKeySpec(key, "AES");
			c.init(Cipher.DECRYPT_MODE, k);
			byte[] data = c.doFinal(encryptedData);
			return read(data);

	}
	
	// Added for strong password encription
	public static synchronized String encrypt(String plaintext) {
		
		String hash = generateHash(plaintext);
		try {
			try {
				hash = generateStrongPasswordHash(hash.toCharArray());
			} catch (CannotPerformOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return hash;
	}

	public static synchronized String generateStrongPasswordHash(char[] password)
			throws CannotPerformOperationException,NoSuchAlgorithmException, InvalidKeySpecException {
		
		        // Generate a random salt
		        SecureRandom random = new SecureRandom();
		        byte[] salt = new byte[SALT_BYTE_SIZE];
		        random.nextBytes(salt);

		        // Hash the password
		        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		        int hashSize = hash.length;

		        // format: algorithm:iterations:hashSize:salt:hash
		        String parts = "sha1:" +
		            PBKDF2_ITERATIONS +
		            ":" + hashSize +
		            ":" +
		            toBase64(salt) +
		            ":" +
		            toBase64(hash);
		        return parts;
		    }
	 
	 private static synchronized byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
		        throws CannotPerformOperationException
		    {
		        try {
		            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		            return skf.generateSecret(spec).getEncoded();
		        } catch (NoSuchAlgorithmException ex) {
		            throw new CannotPerformOperationException(
		                "Hash algorithm not supported.",
		                ex
		            );
		        } catch (InvalidKeySpecException ex) {
		            throw new CannotPerformOperationException(
		                "Invalid key spec.",
		                ex
		            );
		        }
		    }



	public static synchronized String generateHash(String plaintext) {

		// TODO, comment out the encryption here for testing purposes.
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-384"); // step 2
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// throw new DataServiceException("Error creating MD5 Hash");
		}

		try {
			md.update(plaintext.getBytes("UTF-8")); // step 3
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// throw new DataServiceException("Error creating MD5 Hash");
		}

		byte raw[] = md.digest(); // step 4

		String hash = (new BASE64Encoder()).encode(raw); // step 5
		return hash;
	}
	
	 public static boolean verifyPassword(String password, String correctHash)
		        throws CannotPerformOperationException, InvalidHashException
		    {
		        return verifyPassword(password.toCharArray(), correctHash);
		    }

		    public static boolean verifyPassword(char[] password, String correctHash)
		        throws CannotPerformOperationException, InvalidHashException
		    {
		        // Decode the hash into its parameters
		        String[] params = correctHash.split(":");
		        if (params.length != HASH_SECTIONS) {
		            throw new InvalidHashException(
		                "Fields are missing from the password hash."
		            );
		        }

		        // Currently, Java only supports SHA1.
		        if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
		            throw new CannotPerformOperationException(
		                "Unsupported hash type."
		            );
		        }

		        int iterations = 0;
		        try {
		            iterations = Integer.parseInt(params[ITERATION_INDEX]);
		        } catch (NumberFormatException ex) {
		            throw new InvalidHashException(
		                "Could not parse the iteration count as an integer.",
		                ex
		            );
		        }

		        if (iterations < 1) {
		            throw new InvalidHashException(
		                "Invalid number of iterations. Must be >= 1."
		            );
		        }


		        byte[] salt = null;
		        try {
		            salt = fromBase64(params[SALT_INDEX]);
		        } catch (IllegalArgumentException ex) {
		            throw new InvalidHashException(
		                "Base64 decoding of salt failed.",
		                ex
		            );
		        }

		        byte[] hash = null;
		        try {
		            hash = fromBase64(params[PBKDF2_INDEX]);
		        } catch (IllegalArgumentException ex) {
		            throw new InvalidHashException(
		                "Base64 decoding of pbkdf2 output failed.",
		                ex
		            );
		        }


		        int storedHashSize = 0;
		        try {
		            storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
		        } catch (NumberFormatException ex) {
		            throw new InvalidHashException(
		                "Could not parse the hash size as an integer.",
		                ex
		            );
		        }

		        if (storedHashSize != hash.length) {
		            throw new InvalidHashException(
		                "Hash length doesn't match stored hash length."
		            );
		        }

		        // Compute the hash of the provided password, using the same salt, 
		        // iteration count, and hash length
		        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
		        // Compare the hashes in constant time. The password is correct if
		        // both hashes match.
		        return slowEquals(hash, testHash);
		    }

		    private static boolean slowEquals(byte[] a, byte[] b)
		    {
		        int diff = a.length ^ b.length;
		        for(int i = 0; i < a.length && i < b.length; i++)
		            diff |= a[i] ^ b[i];
		        return diff == 0;
		    }

	private static byte[] fromBase64(String hex)
	        throws IllegalArgumentException
	    {
	        return DatatypeConverter.parseBase64Binary(hex);
	    }

	    private static String toBase64(byte[] array)
	    {
	        return DatatypeConverter.printBase64Binary(array);
	    }

	  @SuppressWarnings("serial")
	    static public class InvalidHashException extends Exception {
	        public InvalidHashException(String message) {
	            super(message);
	        }
	        public InvalidHashException(String message, Throwable source) {
	            super(message, source);
	        }
	    }

	    @SuppressWarnings("serial")
	    static public class CannotPerformOperationException extends Exception {
	        public CannotPerformOperationException(String message) {
	            super(message);
	        }
	        public CannotPerformOperationException(String message, Throwable source) {
	            super(message, source);
	        }
	    }
	}