package com.newco.marketplace.web.action.details.zerobid;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,
ServletRequestAware{

	@SuppressWarnings("unchecked")
	private Map sessionMap;
	private HttpServletRequest request;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8042440528777381812L;

	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		this.sessionMap = sessionMap;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**
	 * @return the sSessionMap
	 */
	@SuppressWarnings("unchecked")
	public Map getSessionMap() {
		return sessionMap;
	}


}
