package com.newco.marketplace.search;
//package com.newco.marketplace.search;
//
//import org.apache.solr.common.SolrDocumentList;
//import java.util.Collections;
//import java.util.Map;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.HashMap;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrQuery.ORDER;
//import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.client.solrj.response.FacetField;
//import org.apache.solr.client.solrj.response.SpellCheckResponse;
//import org.apache.solr.client.solrj.response.FacetField.Count;
//import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
//import static com.newco.marketplace.search.SkillTreeDto.MAX_SCORE_DIFF;
//import com.newco.marketplace.api.ProviderDto;
//import com.newco.marketplace.api.QueryRequest;
//import com.newco.marketplace.api.QueryResponseProvider;
//import com.newco.marketplace.api.SearchFilter;
//import com.newco.marketplace.search.SkillTreeDto;
//import com.newco.marketplace.search.provider.ProviderProfileBean;
//import com.newco.marketplace.search.types.ServiceTypes;
//import com.newco.marketplace.search.vo.SearchQueryVO;
//import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
//
//
//public class SolrjClient {
//    private String solrServerUrl;
//    CommonsHttpSolrServer server;
//    
//    public ArrayList<HashMap<String,Object>>search(SearchQueryVO slQuery){
//    	ArrayList<HashMap<String, Object>> searchResult = new ArrayList<HashMap<String, Object>>();
//    	
//    	return searchResult;
//    	
//    }
//    
//    
//    public long count(String str) {
//    	 SolrQuery query = new  SolrQuery();
//         query.setQuery(str);         
//         
//         QueryResponse qr = null;
//		try {
//			qr = server.query(query);
//		} catch (SolrServerException e) {			
//			e.printStackTrace();
//		}
//		SolrDocumentList sdl = qr.getResults();
//		return sdl.getNumFound();
//    }
//    
//    public List<String> getSuggestions(String val) {
//    	List<String> sug = new ArrayList<String>();
//    	String field = "skill";
//    	SolrQuery query = new  SolrQuery();
//        query.setQuery(val);
//    	query.setFacet(true);
//        query.addFacetField(field);       
//        
//        query.setFacetMinCount(1);
//        QueryResponse qr = null;
//		try {
//			qr = server.query(query);
//		} catch (SolrServerException e) {			
//			e.printStackTrace();
//		}
//		List<Count> l = qr.getFacetField(field).getValues();
//		
//		if (l == null)
//			return null;
//		
//		for(Count c : l) {
//			sug.add(c.getName());
//		}
//		System.out.println(sug);
//		return null; 
//    }
//    
//    public ProviderProfileBean loadParent(ProviderProfileBean dto) {   
//    	if (dto.isIAmRoot()) {
//    		return null;      	     
//    	}
//
//    	SolrQuery query = new  SolrQuery();
//    	QueryResponse qr;
//    	try {    		
//    	 	query.setQueryType("slDisMax");
//    		query.setQuery("" + dto.getParentId());
//    		query.setRows(1);
//    		qr = server.query(query);
//    		ProviderProfileBean dt = qr.getBeans(ProviderProfileBean.class).get(0);
//    		System.out.println("DDT:" + dt);
//    		return dt;
//    	} catch (SolrServerException e) {
//    		e.printStackTrace();
//    	}
//    	return null;
//    }
//    
//    /**
//     *  //TODO - why this funciton is required
//     */
//    public void resolveAbiguation(Object treeObj) {
//    	SkillTreeDto tree = (SkillTreeDto)treeObj;
//    	
//    	if (tree != null && tree.getList() != null && tree.getList().size() > 1) { 
//    		ProviderProfileBean p0 = null;
//    		
//    		for (ProviderProfileBean dto1 : tree.getList()) {    			
//    			if (dto1 != null) {
//    				p0 = loadParent(dto1);
//    				if (p0 != null)  {    				
//    					dto1.setSkill(dto1.getSkill() + " (" + p0.getSkill() + ")");
//    				}
//    			}
//    		}    		
//
//    		tree.setStatus(SkillTreeDto.MULTI_CHOISE);
//    	}
//    }
//    
//    private CommonsHttpSolrServer getServer() {
//    	try {
//    		if (server == null && solrServerUrl != null)
//    			server = new CommonsHttpSolrServer(solrServerUrl);
//    	} catch(Exception e) {
//    		e.printStackTrace();
//    	}
//    	return server;
//    }
//    
//    public QueryResponseProvider query(QueryRequest req) {
//    	return queryProvider(req);
//    }
//    
//    public List<ZipDto>  queryGeo(String q, int radius, int maxCount) {
//    	List<ZipDto> beans = null;
//    	if (q == null) {
//    		System.out.println("Provided search string is null");
//    		return new ArrayList<ZipDto>();
//    	}
//
//    	server = getServer();    	
//    	try {    		
//    		q = q.trim();    	
//    		SolrQuery query = new  SolrQuery();
//    		query.setQuery("zip:" + q);    	
//    		query.setQueryType("zipcode");
//    		query.setRows(1);    	
//
//    		QueryResponse qr = server.query(query);               
//    		beans = qr.getBeans(ZipDto.class);    		
//    		if (beans.size() > 0) {
//    			ZipDto dto = beans.get(0);
//    			query = new  SolrQuery();
//    			query.setQuery("*:*");    	
//        		query.setQueryType("geo");
//        		query.setRows(maxCount);
//        		query.setParam("long", "" + dto.getLng());
//        		query.setParam("lat", "" + dto.getLat());
//        		query.setParam("radius", "" + radius);
//        		qr = server.query(query); 
//        		beans = qr.getBeans(ZipDto.class);
//        		System.out.println("Sending geo query");
//    		}    		
//    		
////    		Collections.sort(beans);
//    		for(ZipDto dto: beans) {
//    			System.out.println(dto.getZip() + "," + dto.getDistance());
//    		}
//    	} catch (SolrServerException e) {
//    		e.printStackTrace();
//    	}
//    	return beans;
//    }
//
//    private SolrQuery createProviderQuery(QueryRequest req, String filterZipQuery, int count) {
//    	SolrQuery query = new  SolrQuery();
//    	query.setQuery(req.getQuery());
//    	query.setIncludeScore(true);
//    	query.setQueryType("provider");
//    	query.set("collapse", "true");
//       	query.set("collapse.field", "resource_id");       	
//    	query.set("spellcheck", "on");    
//    	query.set("spellcheck.build", "true");
//    	query.setFilterQueries(filterZipQuery);    	
//    	query.setSortField(req.getSortBy().toString(), ORDER.asc);    
//    	
//    	List<ServiceTypes> facetTypes = req.getRequestedFilters();
//    		
//    	for(ServiceTypes tp: facetTypes) {
//    	  query.addFacetField(tp.toString());
//    	}
//    	
//    	query.setRows(count);
//    	return query;
//    }
//    
//   
//    
//    public QueryResponseProvider queryProvider(QueryRequest req) {    	    	      
//    	List<ProviderDto> list = new ArrayList<ProviderDto>();
//    	QueryResponseProvider tree = null;
//    	HashMap<String, Float> map = new HashMap<String, Float>();
//    	String q = req.getQuery();
//    	List<SearchFilter> availFilters = null;
//    	
//    	if (q == null) {
//    		System.out.println("Provided search string is null");
//    		return new QueryResponseProvider();
//    	}
//    	
//    	server = getServer();
//    	if (server == null)
//    		return null;
//    	
//    	List<ZipDto> zipBeans = queryGeo(req.getZipCode(), req.getRadius(), 50);
//    	StringBuilder bld = new StringBuilder();
//    	
//    	for(ZipDto zipDto: zipBeans) {
//    		bld.append("zip:" + zipDto.getZip() + " ");
//    		map.put(zipDto.getZip(), zipDto.getGeoDistance());
//		}    	    	
//       
//    	SolrQuery query = createProviderQuery(req, bld.toString(),
//    			QueryResponseProvider.MAX_DISAMBIGUATION_RESULT);
//
//    	try {
//    		QueryResponse qr = server.query(query); 
//    		SolrDocumentList sdl = qr.getResults();
//    		List<ProviderDto> beans = qr.getBeans(ProviderDto.class);
//    		List<FacetField> lFacet = qr.getFacetFields();
//    		SearchFilter filter = null;
//    		
//    		if (lFacet.size() > 0) {
//    			availFilters = new ArrayList<SearchFilter>();
//    			for (FacetField ff : lFacet) {
//    				List<FacetField.Count> ffList= ff.getValues();
//    				for (FacetField.Count c : ffList) {
//    					System.out.println(c);
//    					filter =  new SearchFilter(ServiceTypes.findType(ff.getName()), c.getName(), c.getCount());
//    					availFilters.add(filter);
//    				}    				
//    				System.out.println(ff);
//    			}
//    		}
//
//    		float currentscoure = 0.0f;
//
//    		for (ProviderDto dto : beans) {
//    			if (currentscoure == 0.0) {
//    				currentscoure = dto.getScore();
//    			} else if (currentscoure - dto.getScore() > MAX_SCORE_DIFF) {
//    				break;
//    			} else {            		
//    				currentscoure = dto.getScore();
//    			} 
//    			
//    			dto.setDistance(map.get(dto.getZip()));
//    			list.add(dto);
//    		}
//    		
//    		if (req.getSortBy().equals(ServiceTypes.DISTANCE))
//    			Collections.sort(list);
//    		
//    		tree = new QueryResponseProvider(list);  
//    		if (availFilters != null)
//    		  tree.setAvailFilters(availFilters);
//    		SpellCheckResponse spellR = qr.getSpellCheckResponse();
//    		tree.setTotalCount(sdl.getNumFound());
//    		
//
//    		if (spellR != null && spellR.getSuggestions().size() > 0) {
//    			Suggestion sug = spellR.getSuggestions().get(0);
//    			tree.setWrongSpelling(true);
//    			tree.setSuggestedSpelling(sug.getSuggestions().get(0));
//    		}
//    		System.out.println("-------------------------------------------------------------------------");
//
//    	} catch (SolrServerException e) {
//    		e.printStackTrace();
//    	}
//
//    	if (tree == null) 
//    		tree = new QueryResponseProvider();
//
//    	return tree; 
//    }
//    
//    
//    public SkillTreeDto querySkill(String q) {
//    	SkillTreeDto tree = queryData(q, true);	
//		/**
//		 * By default search uses AND operator while search. The first search (with AND operator)
//		 * has returned 0 results. Now search again with OR operator.
//		 * If some results are found with OR operator then display them otherwise
//		 * show wrong spelling results of first search.
//		 */
//		if (tree.getList() != null && tree.getList().size() == 0) {
//			SkillTreeDto tree1 = queryData(q, false);
//			if (tree1.getList() != null && tree1.getList().size() > 0)
//				tree = tree1;
//		}
//		return tree;
//    }
//    
//    
//    
//    private SkillTreeDto queryData(String q, boolean flag) {       
//        List<ProviderProfileBean> list = new ArrayList<ProviderProfileBean>();
//        SkillTreeDto tree = null;
//        
//        if (q == null) {
//        	System.out.println("Provided search string is null");
//        	return new SkillTreeDto();
//        }
//        try {
//           if (server == null && solrServerUrl != null)
//        	   server = new CommonsHttpSolrServer(solrServerUrl);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        
//        if (server == null)
//        	return null;
//
//        q = q.trim();
//        SolrQuery query = new  SolrQuery();
//        query.setQuery(q);
//        
//        query.setIncludeScore(true);
//        
//        if (flag)
//       	  query.setQueryType("slDisMax");
//        
//       	query.set("spellcheck", "on");    
//       	query.set("spellcheck.build", "true");
//        query.setRows(SkillTreeDto.MAX_DISAMBIGUATION_RESULT);
//        query.set("score", "true");
//        
//        try {        	
//            QueryResponse qr = server.query(query);               
//            List<ProviderProfileBean> beans = qr.getBeans(ProviderProfileBean.class);    
//            
//            float currentscoure = 0.0f;
//            
//            for (ProviderProfileBean dto : beans) {
//            	if (currentscoure == 0.0) {
//            		currentscoure = dto.getScore();
//            	} else if (currentscoure - dto.getScore() > MAX_SCORE_DIFF) {
//            		break;
//            	} else {            		
//            		currentscoure = dto.getScore();
//            	} 
//            	
//            	list.add(dto);
//            }
//            
//            tree = new SkillTreeDto(list);            
//            SpellCheckResponse spellR = qr.getSpellCheckResponse();
//            
//            
//            System.out.println("Found: " + beans.size());
//            
//            if (spellR != null && spellR.getSuggestions().size() > 0) {
//            	Suggestion sug = spellR.getSuggestions().get(0);
//            	tree.setWrongSpelling(true);
//            	tree.setSuggestedSpelling(sug.getSuggestions().get(0));
//            }
//            System.out.println("-------------------------------------------------------------------------");
//
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        }
//        
//        if (tree == null) 
//        	tree = new SkillTreeDto();
//        
//        return tree; 
//    }
//    
//    public void printResults(Object tree1) {    	
//    	if (tree1 == null) {
//    		System.out.println("No Results found");
//    		return;
//    	}
//    		
//    	QueryResponseProvider tree = (QueryResponseProvider)tree1;
//    	List<ProviderDto> list = tree.getList();
//    	System.out.println("Solr Server:" + solrServerUrl);
//    		
//    	if (tree.isWrongSpelling()) {
//    		int i = (int)count(tree.getSuggestedSpelling());
//    		System.out.println("Did you mean \""  + tree.getSuggestedSpelling() + "\" (" + i + ") ?");
//    		return;
//    	}
//    	
//    	if (list == null) {
//    		System.out.println("------------------------- List is null -----------------------------------");
//    		return;
//    	}
//    	
//    	if (list.size() > 0) {
//    		String format = "|%1$-10s|%2$-20s|%3$-20s|%4$-10s|%5$-10s|%6$-4s|%7$-5s";
//    		String n = String.format(format, "Id", "name", "Skill", "Score", "Licence", "City", "Licence Iussuer");
//    		System.out.println(n);
//    		
//    		System.out.println("-------------------------------------------------------------------------");
//    		for (ProviderDto dto : list) {
//    			System.out.println(dto);
//    		}
//    	}
//    }
//    
//    public String getSolrServerUrl() {
//    	return solrServerUrl;
//    }
//    
//    public void setSolrServerUrl(String solrServerUrl) {
//    	this.solrServerUrl = solrServerUrl;
//    }
//    
//    /*
//    public static void main(String[] args) {
//    	String str = "electrnics";
//    	SolrjClient solrj = new SolrjClient();
//        SkillTreeDto tree = solrj.query(str, true);
//        solrj.printResults(tree);
//        solrj.getSuggestions("home");
//    } */
//}
