package com.mobile.test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;

public class JerseyRestClient {
//    private static Logger logger = Logger.getLogger(RestClient.class);
    private boolean loggingEnabled = false;
    private Client client = Client.create();
	private String apiBaseUrl;
	private WebResource resource;
    private MultivaluedMap<String,String> parameters = new MultivaluedMapImpl();
    
    public JerseyRestClient(){
       
    }
    
    public JerseyRestClient(String oAuthEnabled,String consumerKey,String consumerSecret,String baseUrl,String token){
    	if(oAuthEnabled.equalsIgnoreCase("on") || oAuthEnabled.equalsIgnoreCase("true")){
    		if(!StringUtils.isEmpty(token)){
    			OAuthParameters oauthParams = new OAuthParameters().
    				signatureMethod("HMAC-SHA1").
                    consumerKey(consumerKey).token(token).
                    version();
    			OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret);
                OAuthClientFilter oAuthFilter = new OAuthClientFilter(null, oauthParams, secrets);
                if(null==client){
                	client = Client.create();
                }
                client.addFilter(oAuthFilter);
    		}else{
    			
    			OAuthParameters oauthParams = new OAuthParameters().
					signatureMethod("HMAC-SHA1").
					consumerKey(consumerKey).version();
    			OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret);
                OAuthClientFilter oAuthFilter = new OAuthClientFilter(null, oauthParams, secrets);
                if(null==client){
                	client = Client.create();
                }
                client.addFilter(oAuthFilter);
               
    		}
        }
    	client.setConnectTimeout(10000);
        //client.setReadTimeout(15);
        apiBaseUrl = baseUrl;
        resource = client.resource(baseUrl);
    }
    
    public JerseyRestClient(String baseUrl){
    	client.setConnectTimeout(10000);
        //client.setReadTimeout(15);
        apiBaseUrl = baseUrl;
        resource = client.resource(baseUrl);
    }
    
    
    public void enableLogging() {
	if (!loggingEnabled) {
	    this.client.addFilter(new LoggingFilter());
	    this.loggingEnabled = true;
	}
    }
    
    /*public void useBaseURLOverRide(){
        String tmpUrl=config.getPublicBaseOverRideUrl();
        if(StringUtils.isNotBlank(tmpUrl)){
            baseUrl=tmpUrl;
            resource = client.resource(baseUrl);
        }
    }*/

    public void setPath(String path) {
        resource = resource.path(path);
    }
    
    public void addParameter(String paramName, String paramValue){
        parameters.add(paramName, paramValue);
    }

    public <T> T get(Class<T> aClass) {
        T returnVal = null;
        MultivaluedMap<String,String> _params = parameters;
        WebResource _resource = resource;
        clearState();
        if(!_params.isEmpty()){
        	returnVal = _resource.queryParams(_params)
        	.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                /*.accept(MediaType.APPLICATION_XML).type(MediaType.TEXT_XML)*/
                .get(aClass);
        }else{
            /*returnVal = _resource.accept(MediaType.APPLICATION_XML).get(aClass);*/
            returnVal = _resource.accept(MediaType.APPLICATION_JSON).get(aClass);
        }
        return returnVal;
    }

    public <T> T post(Class<T> clazz, Object payload){
        WebResource _resource = resource;
        MultivaluedMap<String,String> _params = parameters;
        clearState();
        T returnVal = _resource.queryParams(_params).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(clazz, payload);
        return returnVal;
    }

    public <T> T put(Class<T> clazz, Object payload) {
        WebResource _resource = resource;
        clearState();
        T returnVal = _resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(clazz, payload);
        return returnVal;
    }
    
    public <T> T delete(Class<T> clazz) {
    	T returnVal = null;
    	MultivaluedMap<String,String> _params = parameters;
        WebResource _resource = resource;
        clearState();        
        
        if(!_params.isEmpty()){
            returnVal = _resource.queryParams(_params)
                .accept(MediaType.APPLICATION_XML)
                .delete(clazz);
        }else{
            returnVal = _resource.accept(MediaType.APPLICATION_XML).delete(clazz);
        }
        return returnVal;
    }
    
    private void clearState(){
        parameters = new MultivaluedMapImpl();
        resource = client.resource(apiBaseUrl);
    }
    
	/*public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public boolean isOAuthEnabled() {
		return oAuthEnabled;
	}

	public void setOAuthEnabled(boolean authEnabled) {
		oAuthEnabled = authEnabled;
	}

	public boolean isHttpProxySet() {
		return httpProxySet;
	}

	public void setHttpProxySet(boolean httpProxySet) {
		this.httpProxySet = httpProxySet;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String getCsecret() {
		return csecret;
	}

	public void setCsecret(String csecret) {
		this.csecret = csecret;
	}*/
	
    public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

}
