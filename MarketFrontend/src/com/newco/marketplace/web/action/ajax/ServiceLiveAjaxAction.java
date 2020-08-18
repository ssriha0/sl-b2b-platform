package com.newco.marketplace.web.action.ajax;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.opensymphony.xwork2.ActionContext;

public class ServiceLiveAjaxAction extends SLBaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8813260566162618511L;

	public String execute() throws Exception
	{
		return null;
	}
	
	public String doCommonAjaxTest() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		Map params = ActionContext.getContext().getParameters();
		System.out.println( params );
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		// UPDATE THE DOCUMENT SIGNING
		
			
				response.getWriter().write(
						"<message_output>" + 6677 + "</message_output>");
				
				
			
		return null;
	}
}
