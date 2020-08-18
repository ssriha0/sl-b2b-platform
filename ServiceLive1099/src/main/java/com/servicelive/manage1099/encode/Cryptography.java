package com.servicelive.manage1099.encode;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.servicelive.manage1099.constants.EncriptionConstants;
import com.servicelive.manage1099.db.DBAccess;



import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



public class Cryptography {
	
	private String dBEncodingStr ="";
	private byte[] raw=null;
	private SecretKeySpec skeySpec;
	private Cipher cipher;
	
	public Cryptography() throws Exception{
		dBEncodingStr= DBAccess.readEncondingString();
		//System.out.println("dbEncoding str="+dBEncodingStr);
		raw = new BASE64Decoder().decodeBuffer(dBEncodingStr);
		skeySpec = new SecretKeySpec(raw, "AES");
		cipher = Cipher.getInstance("AES");
	}
		/**
		 * returns the encoded String 
		 * @param decodedString
		 * @return
		 */
	public String encodeString(String decodedString){

		//System.out.println("encryptedString="+encryptedString);
		String encodedString="";
		try{
			
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal((decodedString).getBytes("8859_1"));
			encodedString = new BASE64Encoder().encode(encrypted);
			
		}
		catch(Exception e){
			// The exception is snubbed because of its inherent nature in API
		}
		return encodedString;
	}

	/**
	 * Returns the decoded string.
	 * @param encodedString
	 * @return
	 */
	public String decodeString(String encodedString) {
		
		String decodedString="";
		
		try {
			
			byte[] encrypted = new BASE64Decoder().decodeBuffer(encodedString);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(encrypted);
			decodedString = new String(original, "8859_1");
			
		
		} 
		catch (Exception e) {
			// The exception is snubbed because of its inherent nature in API
		}
		return decodedString;
	}

		
}
