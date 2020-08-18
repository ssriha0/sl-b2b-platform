package com.newco.marketplace.test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.newco.marketplace.dto.vo.CryptographyVO;

public class CryptographyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String number = "99999999999999999";
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(number);
		cryptographyVO.setKAlias("enKey");
				
		System.out.println("Original Number: " + number); 
		
		cryptographyVO = encryptKey(cryptographyVO);
		
		System.out.println("Encrypted Number: " + cryptographyVO.getResponse());
		
		cryptographyVO.setInput(cryptographyVO.getResponse());
		
		cryptographyVO = decryptKey(cryptographyVO);
		
		System.out.println("Decrypted Number: " + cryptographyVO.getResponse());
	}
	
	public static CryptographyVO encryptKey(CryptographyVO cryptographyVO){

		
		try{
						
			byte[] raw = new BASE64Decoder().decodeBuffer("P3Q6GUCryVD51h5JOo0WMQ==");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal((cryptographyVO.getInput()).getBytes("8859_1"));
			cryptographyVO.setResponse(new BASE64Encoder().encode(encrypted));
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cryptographyVO;
	}

	public static CryptographyVO decryptKey(CryptographyVO cryptographyVO) {
		
		String originalString = null;
		
		try {
			
			byte[] raw = new BASE64Decoder().decodeBuffer("P3Q6GUCryVD51h5JOo0WMQ==");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] encrypted = new BASE64Decoder().decodeBuffer(cryptographyVO.getInput());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(encrypted);
			originalString = new String(original, "8859_1");
			cryptographyVO.setResponse(originalString);
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return cryptographyVO;
	}

}
