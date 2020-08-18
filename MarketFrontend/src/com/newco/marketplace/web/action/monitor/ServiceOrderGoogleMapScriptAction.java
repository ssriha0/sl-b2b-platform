package com.newco.marketplace.web.action.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.servicelive.common.util.BoundedBufferedReader;

public class ServiceOrderGoogleMapScriptAction extends SLBaseAction{
	
	private static final long serialVersionUID = -7925923354996501508L;
	
	private static final Logger logger = Logger.getLogger(ServiceOrderGoogleMapScriptAction.class);
	
	public void serviceConnection()throws IOException, HttpException{ 
		HttpServletRequest request=getRequest();
		HttpServletResponse response=getResponse();
		String requestUrl = request.getQueryString(); 
		String user_agent = request.getHeader("User-Agent"); 
		HttpMethod method = new GetMethod(requestUrl); 
		method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); 
		method.addRequestHeader("User-Agent", user_agent); 
		HttpURLConnection urlConnection =null;
		try { 
			//System.setProperty("http.proxyHost", "uskihsvpcflow.kih.kmart.com");
			//System.setProperty("http.proxyPort", "8080");
			//URLConnection urlConnection = null;
			logger.info("Request URL: " + requestUrl);
			URL url = new URL(requestUrl);    
			urlConnection = (HttpURLConnection) url.openConnection ();  
			InputStream inputStream = null;
			
			urlConnection.setReadTimeout (3000);  
			urlConnection.setRequestMethod ("GET");  
			    
			inputStream = urlConnection.getInputStream();    
			String response1 = IOUtils.toString(inputStream);
			
		    // replace http: with proxy 
		    if (response1.indexOf("/MarketFrontend/monitor/googleMap.action") == -1) { 
		    	response1 = 
		    		response1.replaceAll("http:","/MarketFrontend/monitor/googleMap.action?http:"); 
		    } 
		    PrintWriter out = response.getWriter(); 
		    out.println(response1); 
		    out.flush(); 
		    out.close();
		
		} catch(Exception ex){
			ex.printStackTrace();
		}finally { 
		// Release the connection.
			urlConnection.disconnect();
			method.releaseConnection(); 
		} 
	} 
	 
	public String convertInputStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BoundedBufferedReader reader = new BoundedBufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	if(is!=null)
                is.close();
                if(reader!=null)
                reader.close();
            } catch (IOException e) {
            	//logging error as this can never occur
            	logger.error("Caught inside: <ServiceOrderGoogleMapScriptAction::convertInputStreamToString()>:Error: Got an exception that should not occur", e);
            }
        }
 
        return sb.toString();
    }

}