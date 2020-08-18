package com.servicelive;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.junit.Test;



public class SolrReportServletTest{
 private SolrReportServlet servlet;
 private CommonsHttpSolrServer server;
	
	@Test
  public void testInit(){
		servlet = new SolrReportServlet();
		
		ServletConfig config = new ServletConfig() {
			
			public String getServletName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Enumeration getInitParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getInitParameter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		try {
			servlet.init(config);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
