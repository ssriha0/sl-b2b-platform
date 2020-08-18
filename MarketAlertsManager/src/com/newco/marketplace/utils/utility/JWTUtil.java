package com.newco.marketplace.utils.utility;

import io.jsonwebtoken.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.newco.marketplace.beans.EmailToken;
import com.servicelive.SimpleRestClient;
import com.servicelive.response.SimpleRestResponse;

public class JWTUtil {
	
    private Properties configProperties;
    private static LocalDateTime genratedOnTime;
    private static String accessToken;
    private JSONUtils jsonUtils;
    
    private static final Logger logger = Logger.getLogger(JWTUtil.class.getName());
    
    public String genrateToken(){
    	try {
			String jwtToken = getJWTToken();
			if(jwtToken != null && jwtToken != "") {
				this.accessToken = getAccessToken(jwtToken);
				this.genratedOnTime = LocalDateTime.now();
				logger.info("New Access Token: " + accessToken);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Error Occurred : " + e.getMessage(), e);
		}    	
    	return accessToken;    	
    }
    
    private String getJWTToken()	throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		// Load relevant properties from prop file
		String orgId = configProperties.getProperty("issuer");
		String technicalAccountId = configProperties.getProperty("technicalAccountId");
		String apiKey = configProperties.getProperty("apiKey");
		String keyPath = configProperties.getProperty("keyPath");
		String imsHost = configProperties.getProperty("imsHost");
		// Expiration time in seconds
		Long expirationTime = System.currentTimeMillis() / 1000 + 86400L;
		// Metascopes associated to key
		String metascopes[] = configProperties.getProperty("metascopes").split(",");
		// Secret key as byte array. Secret key file should be in DER encoded format.
		byte[] privateKeyFileContent = Files.readAllBytes(Paths.get(keyPath));

		// Read the private key
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		 // Private key is in PEM format, so base64 encoded. So decode the key content. Encode with PKCS8 format.
        byte[] decodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(privateKeyFileContent);
		KeySpec ks = new PKCS8EncodedKeySpec(decodedKey);
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(ks);

		// Create JWT payload
		Map<String, Object> jwtClaims = new HashMap<>();
		jwtClaims.put("iss", orgId);
		jwtClaims.put("sub", technicalAccountId);
		jwtClaims.put("exp", expirationTime);
		jwtClaims.put("aud", imsHost + "/c/" + apiKey);
		for(String metascope : metascopes) {
			jwtClaims.put(imsHost + "/s/" + metascope, true);
		}
		
		SignatureAlgorithm sa = SignatureAlgorithm.RS256;
		// Create the final JWT token
		String jwtToken = Jwts.builder().setClaims(jwtClaims).signWith(sa, privateKey).compact();
		return jwtToken;
	}

    
    private String getAccessToken(String jwtToken) throws IOException {
		String accessToken = "";
		String imsExchange = configProperties.getProperty("imsExchange");
		String apiKey = configProperties.getProperty("apiKey");
		String secret = configProperties.getProperty("secret");

		// Add parameters to request
		String urlParameters = "client_id=" + apiKey + "&client_secret=" + secret + "&jwt_token=" + jwtToken;

		SimpleRestClient simpleRestClient = new SimpleRestClient(imsExchange,null,null,false);
		SimpleRestResponse restResponse = simpleRestClient.post(urlParameters, null);

		if(restResponse!=null) {
			if (restResponse.getResponseCode() == 200){
				EmailToken token = jsonUtils.toObject(EmailToken.class, restResponse.getResponse());
				accessToken = token.getAccess_token();
				logger.info("token ---->"+ accessToken);
			}else{
				logger.error("Error genrating Token : "+ restResponse.getResponse());
				throw new RuntimeException(restResponse.getResponse());
			}			
		}
		return accessToken;
	}
    
    public String getAccessToken(){
		 return this.accessToken;
	}
	 
	public LocalDateTime getGenratedOnTime(){
		 return this.genratedOnTime;
	}
    
    public JSONUtils getJsonUtils() {
		return jsonUtils;
	}

	public void setJsonUtils(JSONUtils jsonUtils) {
		this.jsonUtils = jsonUtils;
	}
    
    public void setConfigProperties(Properties configProperties) {
	    this.configProperties = configProperties;
	 }
	
	 public Properties getConfigProperties() {
	    return configProperties;
	 }
}
