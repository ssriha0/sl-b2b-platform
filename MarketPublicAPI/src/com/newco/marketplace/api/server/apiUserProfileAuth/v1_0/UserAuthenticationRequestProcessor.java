package com.newco.marketplace.api.server.apiUserProfileAuth.v1_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.apiUserProfileAuth.LoginRequest;
import com.newco.marketplace.api.beans.apiUserProfileAuth.UserAuthenticationResponse;
import com.newco.marketplace.api.beans.apiUserProfileAuth.UserAuthenticationService;


@Path("v1.0")
public class UserAuthenticationRequestProcessor {
	private Logger logger = Logger.getLogger(UserAuthenticationRequestProcessor.class);
	@Resource
	protected HttpServletRequest httpRequest;
	protected UserAuthenticationService userAuthenticationService;	
	
	@POST
	@Path("/user/authenticate")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public UserAuthenticationResponse getResponseForUserAuthentication(LoginRequest loginRequest) {
		
		logger.info("Entering UserAuthenticationResponse.getResponseForUserAuthentication() method");
		UserAuthenticationResponse response = null;
		try {
			response = userAuthenticationService.execute(loginRequest);
		} catch (Exception e) {
			logger.error("Error in API execution", e);
		}
		logger.info(response.toString());
		return response;
	}
		   
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public UserAuthenticationService getUserAuthenticationService() {
		return userAuthenticationService;
	}

	public void setUserAuthenticationService(UserAuthenticationService userAuthenticationService) {
		this.userAuthenticationService = userAuthenticationService;
	}
	
	
}
