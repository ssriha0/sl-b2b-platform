package com.servicelive.common.crypto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;

import com.servicelive.common.crypto.exception.EncryptionNotImplementedException;

public class PGPEncryptor implements Encryptor {

	
	public Properties decrypt(Properties properties) {
		throw new EncryptionNotImplementedException("decrypt properties is not implemented.");
		
	}

	
	public BigDecimal decryptBigDecimalField(BigDecimal value) {
		throw new EncryptionNotImplementedException("decrypt BigDecimal is not implemented.");
	}

	
	public BigInteger decryptBigIntegerField(BigInteger value) {
		throw new EncryptionNotImplementedException("decrypt BigInteger Field is not implemented.");
	}

	
	public byte[] decryptBinaryField(byte[] value) {
		throw new EncryptionNotImplementedException("decrypt BinaryField Field is not implemented.");
	}

	
	public String decryptFile(String baseDir, String fileName) {
		throw new EncryptionNotImplementedException("decrypt File is not implemented.");
	}

	
	public String decryptStringField(String value) {
		throw new EncryptionNotImplementedException("decrypt StringField is not implemented.");
	}

	
	public Properties encrypt(Properties properties) {
		throw new EncryptionNotImplementedException("encrypt properties is not implemented.");
	}

	
	public BigDecimal encryptBigDecimalField(BigDecimal value) {
		throw new EncryptionNotImplementedException("encrypt BigDecimal Field is not implemented.");
	}

	
	public BigInteger encryptBigIntegerField(BigInteger value) {
		throw new EncryptionNotImplementedException("encrypt BigInteger Field is not implemented.");
	}

	
	public byte[] encryptBinaryField(byte[] value) {
		throw new EncryptionNotImplementedException("encrypt Binary Field is not implemented.");
	}

	
	public String encryptFile(String baseDir, String fileName) {
		throw new EncryptionNotImplementedException("encrypt File is not implemented.");
	}

	
	public String encryptStringField(String value) {
		throw new EncryptionNotImplementedException("encrypt String Field is not implemented.");
	}

	
	public boolean writeStringToEncryptedFile(String fileNameWithDir,
			String data) {
		boolean success = false;
		// init the security provider
		Security.addProvider(new BouncyCastleProvider());
		
		byte[] clearData = data.getBytes();
		
		String keyRingDir = "/appl/sl/.gnupg/";
		String keyRingFileName = "pubring.gpg";
		System.out.println("Public KeyRing File: " + keyRingDir + keyRingFileName);
		PGPPublicKey publicKey = null;
		FileOutputStream    out = null;
		OutputStream    cOut = null;
		try {
			FileInputStream fileInput = new FileInputStream(new File(keyRingDir + keyRingFileName));
			publicKey = PGPEncryptor.readPublicKey(fileInput);
			// find out a little about the keys in the public key ring
			System.out.println("Key Strength = " + publicKey.getBitStrength());
			System.out.println("Algorithm = " + publicKey.getAlgorithm());
//			Iterator it = publicKey.getUserIDs();
/**			
			for (Iterator iterator = publicKey.getUserIDs(); iterator.hasNext();) {
				String	id = (String) iterator.next();
				System.out.println("PublicKey UserId: " + id);
			}
*/			
			out = new FileOutputStream(fileNameWithDir + ".pgp");
			
			ByteArrayOutputStream       bOut = new ByteArrayOutputStream();
			PGPCompressedDataGenerator  comData = new PGPCompressedDataGenerator(
                    PGPCompressedData.ZIP);
			
			
			OutputStream cos = comData.open(bOut); // open it with the final destination
	        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
	        
	     // we want to generate compressed data. This might be a user option later,
	        // in which case we would pass in bOut.
	        OutputStream  pOut = lData.open(cos, // the compressed output stream
	                                        PGPLiteralData.BINARY,
	                                        fileNameWithDir,  // "filename" to store
	                                        clearData.length, // length of clear data
	                                        new Date()  // current time
	                                      );
			
	        pOut.write(clearData);

	        lData.close();
	        comData.close();
	        
	        PGPEncryptedDataGenerator   cPk = new PGPEncryptedDataGenerator(PGPEncryptedDataGenerator.CAST5, new SecureRandom(), "BC");

//	        cPk.addMethod("asandler@kmart.com".toCharArray());
	        cPk.addMethod(publicKey);

	        byte[]              bytes = bOut.toByteArray();

	        cOut = cPk.open(out, bytes.length);

	        cOut.write(bytes);  // obtain the actual bytes from the compressed stream
	        
	        success = true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			e.getUnderlyingException().printStackTrace();
			e.printStackTrace();
		}
		catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				cOut.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            
		}
		return success;
	}
	
	 /**
     * A simple routine that opens a key ring file and loads the first available key suitable for
     * encryption.
     * 
     * @param in
     * @return
     * @throws IOException
     * @throws PGPException
     */
    private static PGPPublicKey readPublicKey(
        InputStream    in)
        throws IOException, PGPException
    {
        in = PGPUtil.getDecoderStream(in);
        
        PGPPublicKeyRingCollection        pgpPub = new PGPPublicKeyRingCollection(in);

        //
        // we just loop through the collection till we find a key suitable for encryption
        //
        
        //
        // iterate through the key rings.
        //
        Iterator rIt = pgpPub.getKeyRings();
        PGPPublicKey    key = null;
        int keyRingCount = 0;
        int keyCount = 0;
        int EncKeyCount = 0;
        while (rIt.hasNext())
        {
            PGPPublicKeyRing    kRing = (PGPPublicKeyRing)rIt.next();    
            Iterator                        kIt = kRing.getPublicKeys();
           keyRingCount ++;
                      
            while (kIt.hasNext())
            {
                PGPPublicKey    k = (PGPPublicKey)kIt.next();
                keyCount ++;
               
                if (k.isEncryptionKey())
                {
                	EncKeyCount ++;
                    key = k;
                }
            }
        }
        System.out.println("KeyRingCount: " + keyRingCount);
        System.out.println("KeyCount: " + keyCount);
        System.out.println("EncKeyCount: " + EncKeyCount);
        if(key == null)
        	throw new IllegalArgumentException("Can't find encryption key in key ring.");
        else
        	return key;
    }

}
