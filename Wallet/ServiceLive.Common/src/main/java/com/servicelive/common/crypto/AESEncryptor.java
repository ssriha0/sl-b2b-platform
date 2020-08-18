package com.servicelive.common.crypto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.crypto.KeyGenerator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEBigDecimalEncryptor;
import org.jasypt.encryption.pbe.StandardPBEBigIntegerEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.servicelive.common.crypto.exception.EncryptionNotImplementedException;

public class AESEncryptor implements Encryptor {

  private String key;
  private String algorithm;
  private String provider;
  private String PBEWITHSHAAND128BITAES = "PBEWITHSHAAND128BITAES-CBC-BC";
  private String AES = "AES";
  private String AESWRAP = "AESWRAP";
  private String AESPadded = "AES SupportedPaddings";
  
  private String PBEWITHSHA256AND128BITAES = "PBEWITHSHA256AND128BITAES-CBC-BC";

  
public AESEncryptor(String key) {
	super();
	this.key = key;
	this.algorithm = this.PBEWITHSHAAND128BITAES;
	this.provider = "BC";

	Security.addProvider(new BouncyCastleProvider());
}


public Properties decrypt(Properties properties) {
	Properties props = new Properties();
	Set valueSet = properties.entrySet();
	for (Iterator iterator = valueSet.iterator(); iterator.hasNext();) {
		String valkey = (String) iterator.next();
		String val = properties.getProperty(valkey);
		String encVal = this.decryptStringField(val);
		props.setProperty(valkey, encVal);
	}
	return props;
}


public BigDecimal decryptBigDecimalField(BigDecimal value) {
	StandardPBEBigDecimalEncryptor decimalEncryptor = new StandardPBEBigDecimalEncryptor();
	decimalEncryptor.setProviderName(this.provider);
	decimalEncryptor.setAlgorithm(this.algorithm);
	decimalEncryptor.setPassword(key);
	
	BigDecimal plainDecimal = decimalEncryptor.decrypt(value);
	return plainDecimal;
}


public BigInteger decryptBigIntegerField(BigInteger value) {
	StandardPBEBigIntegerEncryptor integerEncryptor = new StandardPBEBigIntegerEncryptor();
	integerEncryptor.setProviderName(this.provider);
	integerEncryptor.setAlgorithm(this.algorithm);
	integerEncryptor.setPassword(key);
	
	BigInteger plainNumber = integerEncryptor.decrypt(value);
	
	return plainNumber;
}


public byte[] decryptBinaryField(byte[] value) {
	StandardPBEByteEncryptor byteEncryptor = new StandardPBEByteEncryptor();
	byteEncryptor.setProviderName(this.provider);
	byteEncryptor.setAlgorithm(this.algorithm);
	byteEncryptor.setPassword(key);
	
	byte[] plainBytes = byteEncryptor.decrypt(value);
	
	return plainBytes;
}


public String decryptStringField(String value) {
	StandardPBEStringEncryptor mySecondEncryptor = new StandardPBEStringEncryptor();
	mySecondEncryptor.setProviderName(this.provider);
	mySecondEncryptor.setAlgorithm(this.algorithm);
	mySecondEncryptor.setPassword(key);

	String plainText = mySecondEncryptor.decrypt(value);
	return plainText;
}


public Properties encrypt(Properties properties) {
	
	Properties props = new Properties();
	Set valueSet = properties.entrySet();
	for (Iterator iterator = valueSet.iterator(); iterator.hasNext();) {
		String valkey = (String) iterator.next();
		String val = properties.getProperty(valkey);
		String encVal = this.encryptStringField(val);
		props.setProperty(valkey, encVal);
	}
	return props;
}


public BigDecimal encryptBigDecimalField(BigDecimal value) {
	StandardPBEBigDecimalEncryptor decimalEncryptor = new StandardPBEBigDecimalEncryptor();
	decimalEncryptor.setProviderName(this.provider);
	decimalEncryptor.setAlgorithm(this.algorithm);
	decimalEncryptor.setPassword(key);
		
	BigDecimal encryptedDecimal = decimalEncryptor.encrypt(value);
	return encryptedDecimal;
}


public BigInteger encryptBigIntegerField(BigInteger value) {
	StandardPBEBigIntegerEncryptor integerEncryptor = new StandardPBEBigIntegerEncryptor();
	integerEncryptor.setProviderName(this.provider);
	integerEncryptor.setAlgorithm(this.algorithm);
	integerEncryptor.setPassword(key);
	
	BigInteger myEncryptedNumber = integerEncryptor.encrypt(value);
	
//	
	return myEncryptedNumber;
}


public byte[] encryptBinaryField(byte[] value) {
	StandardPBEByteEncryptor byteEncryptor = new StandardPBEByteEncryptor();
	byteEncryptor.setProviderName(this.provider);
	byteEncryptor.setAlgorithm(this.algorithm);
	byteEncryptor.setPassword(key);
	
	byte[] encryptedBytes = byteEncryptor.encrypt(value);
	return encryptedBytes;
}


public String encryptStringField(String value) {
	
	StandardPBEStringEncryptor mySecondEncryptor = new StandardPBEStringEncryptor();
//	mySecondEncryptor.setProviderName(this.provider);
	mySecondEncryptor.setProvider(new BouncyCastleProvider());
	mySecondEncryptor.setAlgorithm(this.algorithm);

	mySecondEncryptor.setPassword(key);
	

	String mySecondEncryptedText = mySecondEncryptor.encrypt(value);
	
	return mySecondEncryptedText;
}

	public String encryptFile(String baseDir, String fileName) {
	FileInputStream fio = null;
	FileOutputStream fos = null;
	String fName = baseDir + "/enc_" + fileName;
	
	try {
		fio = new FileInputStream(baseDir + "/" + fileName );
		
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		int c;
		while((c = fio.read()) != -1)
		{
			byteOut.write(c);
		}
		
		byte[] inBytes = byteOut.toByteArray();
		
		byte[] encBytes = this.encryptBinaryField(inBytes);
		System.out.println("Finished encrypting File");
		
		fos = new FileOutputStream(new File(fName));
		fos.write(encBytes);
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			fio.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return fName;
			
	}


	public String decryptFile(String baseDir, String fileName) {
	FileInputStream fio = null;
	FileOutputStream fos = null;
	String fName = baseDir + "/dec_" + fileName;
	
	try {
		fio = new FileInputStream(baseDir + "/" + fileName );
		
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		int c;
		while((c = fio.read()) != -1)
		{
			byteOut.write(c);
		}
		
		byte[] inBytes = byteOut.toByteArray();
		
		byte[] encBytes = this.decryptBinaryField(inBytes);
		System.out.println("Finished decrypting File");
		
		fos = new FileOutputStream(new File(fName));
		fos.write(encBytes);
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			fio.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return fName;
	}


public boolean writeStringToEncryptedFile(String fileNameWithDir, String data) {

	FileOutputStream fos = null;
	boolean success = false;
	try {
		
		byte[] inBytes = data.getBytes();
		
		byte[] encBytes = this.encryptBinaryField(inBytes);
		System.out.println("Finished encrypting File");
		
		fos = new FileOutputStream(new File(fileNameWithDir + ".aes"));
		fos.write(encBytes);
		success = true;
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return success;
}
}