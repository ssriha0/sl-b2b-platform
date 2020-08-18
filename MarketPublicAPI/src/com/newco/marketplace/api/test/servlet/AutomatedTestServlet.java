/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newco.marketplace.api.test.main.ServiceAPITests;


@SuppressWarnings("serial")
public class AutomatedTestServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,
            HttpServletResponse response)
		throws ServletException, IOException {		
		ServiceAPITests test = new ServiceAPITests();
		StringBuffer contents = test.testMain(request);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        out.println("<html><head><title>Public API Testing</title></head>");
        out.println("<body bgcolor=\"#AE5700\"");
        out.println("<p align=\"center\"><b><font size=\"7\" color=\"#DBDBDB\" face=\"Arial\">Public API Testing Reports</font></b></p>");
        out.println(contents.toString());
        out.println("</body></html>");
	}

	public void init(ServletConfig config) throws ServletException {
		
	}
	
}
	

