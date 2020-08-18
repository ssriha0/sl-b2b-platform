package com.servicelive.common.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.LoggingFilter;

public abstract class ServiceLiveBaseRestClient {
    protected Client client = Client.create();

    public ServiceLiveBaseRestClient(){
    	client.addFilter(new LoggingFilter(java.util.logging.Logger.getLogger(getClass().getName())));
    }
    protected String baseUrl;

    public void setBaseUrl(String baseUrl){
    	this.baseUrl = baseUrl;
    }
}
