package com.servicelive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import com.servicelive.ReportBean;


public class SolrReportServlet extends HttpServlet {
	 protected CommonsHttpSolrServer server;
	 String solrServerUrl = null;
	 public static final String FACET_FIELD = "keyword";
	 public static final int TYPE_TOP = 1;
	 public static final int TYPE_BOTTOM = 2;
	 public static final int TYPE_B2C = 3;
	 
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try{
    		if (server == null ) {
    			if (solrServerUrl != null)
    			  server = new CommonsHttpSolrServer(solrServerUrl);
    			
    			if (server == null){
    				server = new CommonsHttpSolrServer("http://172.22.16.25:9090/solr/");		
    			}
    		}
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
        
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	doPost(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	int type = TYPE_TOP;
    	int count = 10;
    	String search = null;
    	
        System.out.println("Inside SolrReportServlet");
        String welcome = "Top queries";
        
        String str = request.getParameter("qtype");
        if (str != null && str.equalsIgnoreCase("bottom")) {
        	type = TYPE_BOTTOM;
        	welcome = "Queries with no results";
        } else if (str != null && str.equalsIgnoreCase("b2c")) {
        	search =  request.getParameter("search");
        	type = TYPE_B2C;
        	welcome = "Queries with no good results at B2C";
        }
        
        str = request.getParameter("rcount");
        if (str != null) {
        	count = Integer.parseInt(str);
        }
        
        List<ReportBean> list = query(type, count, search);
        request.getSession().setAttribute("list", list); 
        request.getSession().setAttribute("welcome", welcome); 
        response.sendRedirect("result.jsp");
    }
    
    public List<ReportBean> query(int type, int count, String search) {  	
    	List<ReportBean> buckets = new ArrayList<ReportBean>();
    	String requestString = "";
    	SolrQuery query = new  SolrQuery();    	
    	
    	query.setIncludeScore(false);
		query.setRows(0);
		query.setQuery("*:*");	
		query.setFacet(true);
		query.setFacetLimit(count);
		query.setFacetSort(true);
		query.setFacetMinCount(1);
		query.addFacetField(FACET_FIELD);
		
		if (type == TYPE_TOP) {
			query.setQuery("*:*");
			query.addFilterQuery("doctype:report");
		} else if (type == TYPE_B2C) {
			query.setQuery("*:*");
			query.addFilterQuery("doctype:b2creport");
		} else {
			query.setQuery("hits:[0 TO 0]");
		} 
		
		System.out.println("Solr Query:" + query.toString());
		try {       	
			QueryResponse qr = server.query(query);			
			List<FacetField> facets = qr.getFacetFields();

	    	for (FacetField facet : facets) {
	  
	    		System.out.println("Fields:" + facet.getName());
	    		if (facet.getName().equalsIgnoreCase(FACET_FIELD)) {	    			
	    			List<FacetField.Count> facetEntries = facet.getValues();
	    			if (facetEntries != null) {
	    				for (FacetField.Count fcount : facetEntries) {
	    					ReportBean bucket = new ReportBean();
	    					bucket.name = fcount.getName();
	    					bucket.count = (int)fcount.getCount();	    					
	    					buckets.add(bucket); 
	    					System.out.println("Key:" + bucket.name + "(" + bucket.count + ")");
	    				}
	    			}
	    		}
	    	}
			
		} catch (SolrServerException e) {
          e.printStackTrace();
	    }   
		return buckets;
    }
    
}
