package com.servicelive.manage1099.main;

import com.servicelive.manage1099.encode.Cryptography;

public class DecryptTIN {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Cryptography cryptography = null;
		try {
			cryptography = new Cryptography();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String decryptedStr = cryptography.decodeString("4HjpXUwK5JguFX7K2bNeaw==");
		System.out.println(decryptedStr);
	}

}
