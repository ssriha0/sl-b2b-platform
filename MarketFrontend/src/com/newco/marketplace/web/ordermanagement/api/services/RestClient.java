package com.newco.marketplace.web.ordermanagement.api.services;


import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
public class RestClient {
	//    private static Logger logger = Logger.getLogger(RestClient.class);
	private boolean loggingEnabled = false;
	private Client client = Client.create();
	private String baseUrl;
	private WebResource resource;
	private MultivaluedMap<String,String> parameters = new MultivaluedMapImpl();


	public RestClient(){


		System.setProperty("http.proxyHost", "192.168.1.99");
		System.setProperty("http.proxyPort", String.valueOf("3128"));
		//String baseUrl="https://151.149.116.100:1943/home";
		resource = client.resource("https://151.149.116.100:1943/home");
	}

	public void enableLogging() {
		if (!loggingEnabled) {
			this.client.addFilter(new LoggingFilter());
			this.loggingEnabled = true;
		}
	}

	public void useBaseURLOverRide(){
		String tmpUrl="https://151.149.116.100:1943/home";//config.getPublicBaseOverRideUrl();
		if(StringUtils.isNotBlank(tmpUrl)){
			baseUrl=tmpUrl;
			resource = client.resource(baseUrl);
		}
	}

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
					.accept(OrderConstants.REQUEST_CONTENT_TYPE)
					.get(aClass);
		}else{
			returnVal = _resource.accept(OrderConstants.REQUEST_CONTENT_TYPE)
					.get(aClass);
		}
		return returnVal;
	}

	public <T> T post(Class<T> clazz, Object payload){
		WebResource _resource = resource;
		clearState();
		T returnVal = _resource.accept(OrderConstants.REQUEST_CONTENT_TYPE).type("text/xml").post(clazz, payload);
		return returnVal;
	}

	public <T> T put(Class<T> clazz, Object payload) {
		WebResource _resource = resource;
		clearState();
		T returnVal = _resource.accept(OrderConstants.REQUEST_CONTENT_TYPE).type("text/xml").put(clazz, payload);
		return returnVal;
	}

	public <T> T delete(Class<T> clazz) {
		T returnVal = null;
		MultivaluedMap<String,String> _params = parameters;
		WebResource _resource = resource;
		clearState();        

		if(!_params.isEmpty()){
			returnVal = _resource.queryParams(_params)
					.accept(OrderConstants.REQUEST_CONTENT_TYPE)
					.delete(clazz);
		}else{
			returnVal = _resource.accept(OrderConstants.REQUEST_CONTENT_TYPE).delete(clazz);
		}
		return returnVal;
	}

	private void clearState(){
		parameters = new MultivaluedMapImpl();
		resource = client.resource(baseUrl);
	}
}
