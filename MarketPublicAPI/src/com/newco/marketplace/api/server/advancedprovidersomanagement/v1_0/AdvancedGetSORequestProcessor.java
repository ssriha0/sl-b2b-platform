package com.newco.marketplace.api.server.advancedprovidersomanagement.v1_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.services.advancedprovidersomanagement.SoSubStatusService;

@Path("apso/v1.0")
public class AdvancedGetSORequestProcessor{
	private Logger logger = Logger.getLogger(AdvancedGetSORequestProcessor.class);
	@Resource
	protected HttpServletRequest httpRequest;
	private SoSubStatusService soSubstatusService;
	
	
	
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}
	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	public SoSubStatusService getSoSubstatusService() {
		return soSubstatusService;
	}
	public void setSoSubstatusService(SoSubStatusService soSubstatusService) {
		this.soSubstatusService = soSubstatusService;
	}
	
}
