package com.servicelive.spn.web.servlet;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;


public class ServiceLiveSpnInitServlet extends HttpServlet{
 private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doPost(req, resp);
	}

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		loadResouceBundle(servletContext);
	}
	/**
	 * loadResouceBundle loads the servicelive_copy resource bundle into the application context
	 * @param servletContext
	 */
	protected void loadResouceBundle(ServletContext servletContext) {
		ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
		LocalizationContext serviceliveBundle = new LocalizationContext(rb);
		servletContext.setAttribute("serviceliveCopyBundle",serviceliveBundle);
	}		
}
