package com.servicelive.common.crypto;

import java.io.File;
import java.math.BigInteger;
import java.util.Properties;
import java.math.BigDecimal;

public interface Encryptor {

	/**
	 * Encrypt a set of name-value pairs presented as properties object.
	 * @param properties holding plain-text name-value Pair String objects
	 * @return properties holding encrypted name-value Pair String objects
	 */
  public java.util.Properties encrypt(java.util.Properties properties);

  /**
	 * decrypt a set of name-value pairs presented as properties object.
	 * @param properties holding encrypted name-value Pair String objects
	 * @return properties holding plain-text name-value Pair String objects
	 */
  public java.util.Properties decrypt(java.util.Properties properties);

  /**
   * Encrypt BigInteger Field
   * @param value plain BigInteger
   * @return Encrypted BigInteger
   */
  public BigInteger encryptBigIntegerField(BigInteger value);

  /**
   * Encrypt String Field
   * @param value plain String
   * @return Encrypted String
   */
  public String encryptStringField(String value);
  
  /**
   * Encrypt Binary data
   * @param value plain byte[] object
   * @return encrypted byte[] object
   */
  public byte[] encryptBinaryField(byte[] value);

  /**
   * Encrypt BigDecimal Field
   * @param value plain BigDecimal object
   * @return encrypted BigDecimal object
   */
  public BigDecimal encryptBigDecimalField(BigDecimal value);

  /**
   * Decrypt BigInteger Field
   * @param value Encrypted BigInteger object
   * @return plain BigInteger object
   */
  public BigInteger decryptBigIntegerField(BigInteger value);

  /**
   * Decrypt String Field
   * @param value Encrypted String object
   * @return plain String object
   */
  public String decryptStringField(String value);

  /**
   * Decrypt Binary data
   * @param value Encrypted byte[] object
   * @return plain byte[] object
   */
  public byte[] decryptBinaryField(byte[] value);

  /**
   * Decrypt BigDecimal Field
   * @param value Encrypted BigDecimal object
   * @return plain BigDecimal object
   */
  public BigDecimal decryptBigDecimalField(BigDecimal value);
  
  /**
   * Encrypts a file on the file system
   * @param baseDir url for the base directory for the file to be encrypted.
   * @param fileName name of the file to be encrypted.
   * @return url for the encrypted file.
   */
  public String encryptFile(String baseDir, String fileName);
  
  /**
   * Decrypts a file on the file system
   * @param baseDir url for the base directory for the file to be decrypted.
   * @param fileName name of the file to be decrypted.
   * @return url for the decrypted file.
   */
  public String decryptFile(String baseDir, String fileName);
  
  /**
   * Creates an encrypted file from the String data.
   * @param fileNameWithDir  
   * @param data
   * @return
   */
  public boolean writeStringToEncryptedFile(String fileNameWithDir, String data);
}