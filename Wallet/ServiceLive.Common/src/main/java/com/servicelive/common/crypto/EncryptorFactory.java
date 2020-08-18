package com.servicelive.common.crypto;

public class EncryptorFactory {

  public static Encryptor getEncryptor(int type, String key) {
	  Encryptor encryptor = null;
  
	  switch (type) {
	case Algorithm.AES:
		encryptor = new AESEncryptor(key);
		break;

	case Algorithm.PGP:
		encryptor = new PGPEncryptor();
		break;
		
	default:
		encryptor = new AESEncryptor(key);
		break;
	}
	  
	  return encryptor;
  }
}