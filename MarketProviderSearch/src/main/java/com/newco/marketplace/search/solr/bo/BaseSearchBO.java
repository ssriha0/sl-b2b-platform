package com.newco.marketplace.search.solr.bo;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.newco.marketplace.search.solr.dto.ZipDto;
import com.newco.marketplace.search.vo.BaseRequestVO;
import com.newco.marketplace.search.vo.SearchQueryResponse;

public abstract class BaseSearchBO {
    protected CommonsHttpSolrServer server;
    private static CommonsHttpSolrServer serverStatic;
    public static final float MAX_RATING = 5.0f;
    private String solrServerUrl;

    public BaseSearchBO(String solrServerUrl) {
    	try{
    		this.solrServerUrl = solrServerUrl;
    		if (serverStatic == null) {
    			serverStatic = new CommonsHttpSolrServer(solrServerUrl);
    			if(serverStatic == null){
    				serverStatic = getLocalServer();
    			}
    		}
    		server = serverStatic;
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }

	public abstract SearchQueryResponse query(BaseRequestVO req);
	
    public ZipDto getLongLat(String zipCode) throws SolrServerException {
    	SolrQuery query = new  SolrQuery();
    	List<ZipDto> beans = null;
		query.setQuery("zip:" + zipCode);    	
		query.setQueryType("zipcode");
		query.setRows(Integer.valueOf(1));  
		QueryResponse qr = server.query(query);               
		beans = qr.getBeans(ZipDto.class);    		
		if (beans.size() > 0) {
			return beans.get(0);
		}
		return null;
    }
    
    private CommonsHttpSolrServer getLocalServer(){
		try {
			server = new CommonsHttpSolrServer("http://localhost:9080/solr/");		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return server;
    }


}
