package com.newco.marketplace.business.businessImpl.api;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Stand alone command line utility to generate consumer key & secret for request user.
 * It is used by Production Support.
 * Run it like this:
 * java -cp <Path>/oauth-core.jar net.oauth.secret.OAuthKeyGenerator <userName>
 *
 * @author Shekhar Nirkhe
 *
 *
 */
public class OAuthKeyGenerator {
	public static final String OAUTH_KEY="OAUTH_KEY";
	public static final String OAUTH_SECRET="OAUTH_SECRET";

	// SHA-256 algorithm conversion code.
	// Security Fix - M1
	public static String Sha256AlgorithmConversion(String dataToken){
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Cannot find digest algorithm");
			System.exit(1);			
		}
        md.update(dataToken.getBytes());
 
        byte byteData[] = md.digest();
 
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	System.out.println("Hex format : " + hexString.toString());
    	return hexString.toString();
	}
	
	// this method is commented as a part of checkmarx__java__low__Use_of_Broken_or_Risky_Cryptographic_Algorithm
	// MD5 algorithm - Deprecated Method
	// Using SHA256 algorithm instead
//	public static String md5(String arg) {
//		String md5val = "";
//		MessageDigest algorithm = null;
//
//		try {
//			algorithm = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException nsae) {
//			System.out.println("Cannot find digest algorithm");
//			System.exit(1);
//		}
//
//		byte[] defaultBytes = arg.getBytes();
//		algorithm.reset();
//		algorithm.update(defaultBytes);
//		byte messageDigest[] = algorithm.digest();
//		StringBuffer hexString = new StringBuffer();
//
//		for (int i = 0; i < messageDigest.length; i++)
//		{
//			String hex = Integer.toHexString(0xFF & messageDigest[i]);
//			if (hex.length() == 1)
//			{
//				hexString.append('0');
//			}
//			hexString.append(hex);
//		}
//		md5val = hexString.toString();
//		//System.out.println("MD5 (" + arg + ") = " + md5val);
//		return md5val;
//	}

	public static Map generate(String consumerName) {
		Map<String, String> map = new HashMap<String, String>();
		Date dt =  new Date();
		String key = Sha256AlgorithmConversion(consumerName);
		String secretseed = key +  dt.getTime();
		String secret = Sha256AlgorithmConversion(secretseed);
		map.put(OAUTH_KEY, key);
		map.put(OAUTH_SECRET, secret);
		return map;
	}

	public static String resetPassword(String consumerKey) {
		Date dt =  new Date();
		String secretseed = consumerKey +  dt.getTime();
		String secret = Sha256AlgorithmConversion(secretseed);
		return secret;
	}

	public static void main(String []args) {
		if (args.length == 0) {
			System.out.println("Usages : OAuthKeyGenerator <consumer_name>");
			return;
		}
		OAuthKeyGenerator.generate(args[0]);
	}
}
