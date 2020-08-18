package com.servicelive.service.dataTokenization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;



import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class SimpleRestClient {
	
	private URL url; 
	private String userName = null;
	private String password = null;
	private HttpURLConnection httpURLConnection = null;
	private final String POST = "POST";
	private final String CONTENT_TYPE_XML = "application/xml";
	private final String CONTENT_TYPE = "Content-type";
	private final String CONTENT_LENGTH = "Content-Length";
	private final String AUTH = "Authorization";
	private final String AUTH_BASIC = "Basic ";
	
	private static final Logger LOGGER = Logger.getLogger(SimpleRestClient.class);
	
    public SimpleRestClient(String URL,String usrName,String passwd,boolean authRequired) throws MalformedURLException{
    	url = new URL(URL);
    	if(authRequired && null!= usrName && null!= passwd){
    		userName = usrName;
			password = passwd;
    	}	
    }
	
 
    /* Post method which returns home serice auth response
     * Written for PCI Vault
     * */
    @SuppressWarnings("deprecation")
	public HomeServiceResponseVO postMethod(String request, Map<String, String> headers) throws Exception{
    	 //This property is set to make handshake with Hs tokenization with TLSv1 protocol
   	     //System.setProperty("https.protocols", "TLSv1");
    	 StringBuffer responseBuffer = new StringBuffer();
    	 HomeServiceResponseVO homeServiceResponseVO = new HomeServiceResponseVO();
    	 LOGGER.info("SimpleRestClint.postMethod: Begin");
         try {
             httpURLConnection = (HttpURLConnection) url.openConnection();
             // I provide the input
             httpURLConnection.setDoInput(true);               
             
             // Requires output
             httpURLConnection.setDoOutput(true);
               
             // No caching
             httpURLConnection.setUseCaches(false);
               
             httpURLConnection.setConnectTimeout(5000);
             // Set authentication if required
             if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            	 String authStr = userName+":"+password;
            	 httpURLConnection.setRequestProperty(AUTH,AUTH_BASIC + javax.xml.bind.DatatypeConverter.printBase64Binary(authStr.getBytes()));
             }
             httpURLConnection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_XML);               
             httpURLConnection.setRequestProperty(CONTENT_LENGTH, Integer.toString(request.getBytes().length));                           
             httpURLConnection.setRequestMethod(POST);
             
             @SuppressWarnings("rawtypes")
             Iterator iterator = headers.entrySet().iterator();
             while (iterator.hasNext()) {
                 @SuppressWarnings("rawtypes")
                 Map.Entry pairs = (Map.Entry)iterator.next();
                 LOGGER.debug("SimpleRestClint.postMethod:"+pairs.getKey().toString()+"="+pairs.getValue().toString());
                 httpURLConnection.setRequestProperty(pairs.getKey().toString(), pairs.getValue().toString());
             }
             DataOutputStream dataOutputStream = null;
             DataInputStream dataInputStream = null;
             httpURLConnection.connect();
             dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
             dataOutputStream.writeBytes(request);
             dataOutputStream.flush();
             dataOutputStream.close();
             
             String responseValue;
             homeServiceResponseVO.setStatusCode(httpURLConnection.getResponseCode());
 	        
             homeServiceResponseVO.setStatusText(httpURLConnection.getResponseMessage());
           
             dataInputStream = new DataInputStream(httpURLConnection.getInputStream());
             while(null != ((responseValue = dataInputStream.readLine()))){
                 responseBuffer.append(responseValue);
             }
             homeServiceResponseVO.setResponseXml(responseBuffer.toString());
             LOGGER.info("post http Response is::::"+responseBuffer.toString());
	    	
		} 
        catch (IOException e) {
        	LOGGER.error("IO Exception occured in postMethod for HS():"+e);
			throw new Exception(e);

		} 
        catch (Exception e) {
        	LOGGER.error("Exception occured in postMethod for HS():"+e);
			throw new Exception(e);

			
		}
         LOGGER.info("SimpleRestClient.postMethod: End");
        return homeServiceResponseVO;
    }
    
    

}
