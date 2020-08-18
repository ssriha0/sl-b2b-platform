package com.newco.marketplace.search.solr.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

import com.newco.marketplace.search.solr.dto.ProviderDto;
import com.newco.marketplace.search.solr.dto.ZipDto;
import com.newco.marketplace.search.types.Bucket;
import com.newco.marketplace.search.types.ServiceTypes;
import com.newco.marketplace.search.vo.BaseRequestVO;
import com.newco.marketplace.search.vo.BaseResponseVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.search.vo.SearchQueryResponse;
import com.newco.marketplace.searchInterface.ISearchProvider;
import com.newco.marketplace.util.StateCodes;

public class ProviderSearchSolrBO extends BaseSearchBO {
	private Logger logger = Logger.getLogger(ProviderSearchSolrBO.class);


	final String facetFieldLang = "langs";
	final String facetFieldDistance = "facet_geo_distances";
	//final float MAX_RATING = 5.0f;

	final String myRegex = "[!^a-zA-Z]";
	final String blank = "";
	
	private List<Bucket> facetList;
	private List<Bucket> distanceBucket, ratingBucket;
	private Map<String, Bucket> ratingFacetQueryMap;

	private String distanceFacetQuery;
	private boolean languageBucketenabled;


	public ProviderSearchSolrBO(String solrServerUrl) {
		super(solrServerUrl);
	}

	private void configureBuckets() {
		if (facetList == null)
			return;

		Set<Float> facetSet = new TreeSet<Float>();
		distanceBucket = new ArrayList<Bucket>();
		ratingBucket = new ArrayList<Bucket>();
		ratingFacetQueryMap = new HashMap<String, Bucket>();

		for (Bucket bucket: facetList) {
			switch (bucket.getType()) {
			case Bucket.DISTANCE_TYPE:
				distanceBucket.add(bucket);

				if (bucket.getStartRange() == null)
					bucket.setStartRange("0.0");

				if (bucket.getEndRange() == null)
					bucket.setEndRange("1000.0"); //max radius

				facetSet.add(new Float(bucket.getStartRange()));
				facetSet.add(new Float(bucket.getEndRange()));

				break;
			case Bucket.RATING_TYPE:
				if (bucket.getStartRange() == null)
					bucket.setStartRange("0");

				if (bucket.getEndRange() == null)
					bucket.setEndRange("5");
				ratingBucket.add(bucket);
				break;
			case Bucket.LANGUAGE_TYPE:
				languageBucketenabled = true;
				break;
			}
		}

		StringBuilder bld = new StringBuilder();
		for (Float f : facetSet) {
			bld.append(f + ",");
		}

		if (bld.length() > 0)
			distanceFacetQuery = chomp(bld.toString(), ',');

		if (ratingBucket.size() > 0) {
			for(Bucket bucket: ratingBucket) {
				String startRange = bucket.getStartRange();
				String endRange = bucket.getEndRange();
				ratingFacetQueryMap.put("overall:["+ startRange + " TO " + endRange + "]", bucket);
				if (Integer.parseInt(startRange)!=Integer.parseInt(endRange)) {
					ratingFacetQueryMap.put("overall:["+ endRange + " TO " + startRange +"]", bucket);
				}
			}
		}
	}

	private SolrQuery createProviderByResourceIds(ProviderSearchRequestVO req) {
		SolrQuery query = new  SolrQuery();
		StringBuilder bld = new StringBuilder();

		for (Integer ii:req.getProviderIds()) {
			bld.append("resource_id:" + ii + ' ');
		}

		if (bld.length() > 0) {
			String str = chomp(bld.toString(), ' ');
			query.setQuery(str);
			int size = req.getProviderIds().size();
			query.setRows(size);
		}
		return query;
	}
	
	private void addCategoryIdQuery(ProviderSearchRequestVO req, SolrQuery query) {
		if (req.getCategoryIds() == null)
			return;
		
		StringBuilder bld = new StringBuilder();
		for (Integer ii:req.getCategoryIds()) {
			String skill = "skills:" + ii;
			if (req.getServiceTypes() != null) {
				for (ServiceTypes type: req.getServiceTypes()) {
					query.addFilterQuery(skill + type.getValue());
				}
			} else {
				bld.append(skill + " ");
			}
		}

		if (bld.length() > 0) { // only when no ServiceTypes is provided.
			String str = chomp(bld.toString(), ' ');
			query.addFilterQuery(str);
		}
	}

	private SolrQuery createProviderByZipQuery(ProviderSearchRequestVO req) {
		SolrQuery query = new  SolrQuery();
		StringBuilder bld = new StringBuilder();

		
		addCategoryIdQuery(req, query);
		addFacets(query, true);
		addSorting(req, query);
		query.setQuery("*:*");
		query.setIncludeScore(true);

		if(req.getMinRating() < 0.0) {
			req.setMinRating(0.0f);
		}

		if (req.getMinRating() >= 0.0) {
			if(req.getMaxRating() < req.getMinRating()) {//user is trying to specify maxrating less than minrating, return nothing by being defensive
				query.addFilterQuery("overall:[" + -1 + " TO " + -1 +"]");
			} else if (req.getMaxRating() == req.getMinRating()) {
				query.addFilterQuery("overall:[" + req.getMinRating() + " TO " + req.getMaxRating() +"]");
			} else if (req.getMaxRating() < MAX_RATING) {
				query.addFilterQuery("overall:[" + req.getMinRating() + " TO " + req.getMaxRating() +"]");
			} else {//Cap the user to max_rating of 5
				query.addFilterQuery("overall:[" + req.getMinRating() + " TO " + MAX_RATING +"]");
			}
		}

		List<String> langList = req.getLanguages();
		bld = new StringBuilder();

		for (String lang:langList) {
			if (lang != null && !lang.equals("null"))
				lang = WordUtils.capitalize(lang);
				bld.append("langs:" + lang + " ");
		}

		if (bld.length() > 0) { // only when no ServiceTypes is provided.
			String str = chomp(bld.toString(), ' ');
			query.addFilterQuery(str);
		}

		int pageNumber = req.getPageNumber();
		if (pageNumber > 0)
			pageNumber --;

		//Provider Ids are optional for GetByZip API
		if(req.getProviderIds().size() > 0){
			for (Integer ii:req.getProviderIds()) {
				bld.append("resource_id:" + ii + ' ');
			}
			String str = chomp(bld.toString(), ' ');
			query.setQuery(str);
		}

		int comId = req.getCompanyId();
		if (comId > 0) {
			query.addFilterQuery("vendor_id:" + comId);
		}

		query.setRows(Integer.valueOf(req.getPageSize()));
		query.setStart(Integer.valueOf(pageNumber * req.getPageSize()));
		return query;
	}

	private void addFacets(SolrQuery query, boolean distanceFacet) {

		if (this.languageBucketenabled)
			query.addFacetField(facetFieldLang);

		if (distanceFacetQuery != null && distanceFacet == true) {
			query.setParam("facet.geo_distance.buckets", distanceFacetQuery);
			query.setParam("facet.geo_distance", "true");
		}

		for (String key: ratingFacetQueryMap.keySet()) {
			query.addFacetQuery(key);
		}
		query.setFacetMinCount(1);
	}


	private void addSorting(ProviderSearchRequestVO req, SolrQuery query) {
		String field = "geo_distance";
		ORDER order = ORDER.asc;

		if (req.getSortBy() == ProviderSearchRequestVO.SORT_BY_RATING)
			field = "overall"; //rating

		if (req.getSortOrder() == ProviderSearchRequestVO.SORT_ORDER_DESC)
			order = ORDER.desc;

		query.setSortField(field, order);
	}

	@Override
	public SearchQueryResponse query(BaseRequestVO baseReq) {
		ProviderSearchRequestVO req = (ProviderSearchRequestVO)baseReq;
		formatRequest(req);
		try {
			if (baseReq.getRequestAPIType() == BaseRequestVO.REQ_TYPE_BY_CITY_STATE) {
				return getProvidersCount(req);
			} else if (baseReq.getRequestAPIType() == BaseRequestVO.REQ_TYPE_CITY_LIST) {
				return getCityList(req);
			} else if (baseReq.getRequestAPIType() == BaseRequestVO.REQ_TYPE_BY_SKILLS) {
				return getProviderCountBySkills(req);
			} else if (baseReq.getRequestAPIType() == BaseRequestVO.REQ_TYPE_BY_CITY_STATE_SKILLS) {
				return getProvidersByCityAndState(req);
			} else {
				return query(req);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private void formatRequest(ProviderSearchRequestVO req) {
		String state = req.getState();
		String city = req.getCity();
		
		if (state != null) {
    		state = state.toLowerCase();
    		String code = '\"' + StateCodes.map.get(state) + '\"';
    		req.setState(code);
    		if (city != null) {
    			city = '\"' + city.toLowerCase() + '\"';
    		}
    		req.setCity(city);
    	}
	}

	private SearchQueryResponse getProvidersCount(ProviderSearchRequestVO req) throws SolrServerException {
		SolrQuery query = new  SolrQuery();
		query.setQueryType("provider");
		String city = req.getCity();
		String state = req.getState();
		QueryResponse qr = null;
		SearchQueryResponse res = new SearchQueryResponse(null);

		if (state != null)
			query.setQuery("state:" + state);
		else
			query.setQuery("*:*");
			
		if (city != null) {
			query.setFilterQueries("city:" + city);
		}
			
		addCategoryIdQuery(req, query);
		
		if (server != null) {
			logger.info("Solr Query:" + query.toString());
			query.setRows(0);
			qr = server.query(query);
			SolrDocumentList sdl = qr.getResults();
			int count = (int)sdl.getNumFound();
			res.setProperty("count", count);
		}
		return res;
	}
	
	private SearchQueryResponse getProvidersByCityAndState(ProviderSearchRequestVO req) throws SolrServerException{
		SolrQuery query = createProviderByZipQuery(req);
		List<BaseResponseVO> voList = new ArrayList<BaseResponseVO>();
		QueryResponse qr = null;
		int totalCount = 0;
		String state = req.getState();
		if (state!= null)
			query.addFilterQuery("state:" + state);

		if (req.getCity() != null) { //search by city and state
			query.addFilterQuery("city:" + req.getCity());					
		}
		
		query.setRows(1000);
		query.setQuery("*:*");
		query.addFilterQuery("doctype:provider");
	
		
		if (server != null) {
			logger.info("Solr Query:" + query.toString());
			qr = server.query(query);
			SolrDocumentList sdl = qr.getResults();
			totalCount = (int)sdl.getNumFound();
			List<ProviderDto> beans = qr.getBeans(ProviderDto.class);
			for (ProviderDto dto : beans) {
				voList.add(new ProviderSearchResponseVO(dto));
			}
		} else {
			return null;
		}

		SearchQueryResponse response = new SearchQueryResponse(voList);


		response.setProperty(ISearchProvider.PROPERTY_TOTAL_PROVIDER_FOUND, new Integer(totalCount));
		List<Bucket> bucketList = null;
		if (qr != null) {
			bucketList = getFacets (qr);
		} else {
			bucketList = new ArrayList<Bucket>();
		}
		response.setProperty(ISearchProvider.PROPERTY_FACETS, bucketList);
		return response;
	}

	private SearchQueryResponse getCityList(ProviderSearchRequestVO req) throws SolrServerException {
		SolrQuery query = new  SolrQuery();
		String facetField = "city";
		String state = req.getState();
		QueryResponse qr = null;
		SearchQueryResponse res = new SearchQueryResponse(null);
		HashMap<String, Long> cities = new HashMap<String, Long>();
		if (state != null)
			query.setQuery("state:" + state);

		addCategoryIdQuery(req, query);
		
		if (server != null) {
			query.setFacet(true);
			query.addFilterQuery("doctype:provider");
			query.addFacetField(facetField);
			query.setFacetMinCount(1);
			query.setRows(0);
			logger.info("Solr Query:" + query.toString());
			qr = server.query(query);
			//List<ProviderDto> beans = qr.getBeans(ProviderDto.class);
			List<FacetField> facets = qr.getFacetFields();
			for (FacetField facet : facets) {				
				if (facet.getName().equalsIgnoreCase(facetField)) {			
					List<FacetField.Count> facetEntries = facet.getValues();
					if (facetEntries != null) {
						for (FacetField.Count fcount : facetEntries) {							
							cities.put(fcount.getName(), fcount.getCount());							
						}
					}
				}
			}			
		}
		res.setProperty("cities", cities);
		return res;
	}
	
	private SearchQueryResponse getProviderCountBySkills(ProviderSearchRequestVO req) throws SolrServerException {
		SolrQuery query = new  SolrQuery();
		String facetField = "skills";
		String city = req.getCity();
		String state = req.getState();
		QueryResponse qr = null;
		SearchQueryResponse res = new SearchQueryResponse(null);
		HashMap<Integer, Integer> skillIds = new HashMap<Integer, Integer>();
		
		if (state != null)
			query.setQuery("state:" + state);
		else
			query.setQuery("*:*");
		
		if (city != null)
			query.setFilterQueries("city:" + city);
		
		if (server != null) {
			query.setFacet(true);
			query.setFilterQueries("doctype:provider");
			query.setFacetMinCount(1);
			query.addFacetField(facetField);
			query.setRows(0);
			logger.info("Solr Query:" + query.toString());
			qr = server.query(query);
			
			List<FacetField> facets = qr.getFacetFields();
			for (FacetField facet : facets) {				
				if (facet.getName().equalsIgnoreCase(facetField)) {			
					List<FacetField.Count> facetEntries = facet.getValues();
					if (facetEntries != null) {
						for (FacetField.Count fcount : facetEntries) {	
							try {
								int x = Integer.parseInt(fcount.getName());
								skillIds.put(x, (int)fcount.getCount());
							} catch(NumberFormatException nFE) {//skip the entry
							}														
						}
					}
				}
			}			
		}
		res.setProperty("skills", skillIds);
		return res;
	}



	private SearchQueryResponse query(ProviderSearchRequestVO req) throws SolrServerException {
		List<BaseResponseVO> voList = new ArrayList<BaseResponseVO>();
		QueryResponse qr = null;
		String zip = req.getZipCode();

		SolrQuery query = null;
		int totalCount = 0;


		if (StringUtils.isBlank(zip)) {			
			query = createProviderByResourceIds(req);
			query.setQueryType("provider");
			
		} else {
			ZipDto zipDdo = getLongLat(zip);
			if (zipDdo != null) {
				if (req.getRequestAPIType() == BaseRequestVO.REQ_TYPE_BY_ZIP) {
					query = createProviderByZipQuery(req);
					query.setParam("radius", "" + req.getMaxDistance());
					int solrReturnRows = req.getProviderIds().size();
					if(solrReturnRows > 0) {
						query.setRows(Integer.valueOf(solrReturnRows));
					}
				} else  {
					query = createProviderByResourceIds(req);
					query.setParam("radius", "" + 200);
				}

				query.setParam("long", "" + zipDdo.getLng());
				query.setParam("lat", "" + zipDdo.getLat());
				query.setQueryType("geo");
			} else {
				return null;
			}
		}

		if (server != null) {
			logger.info("Solr Query:" + query.toString());
			qr = server.query(query);
			SolrDocumentList sdl = qr.getResults();
			totalCount = (int)sdl.getNumFound();
			List<ProviderDto> beans = qr.getBeans(ProviderDto.class);
			for (ProviderDto dto : beans) {
				//System.out.println(dto);
				voList.add(new ProviderSearchResponseVO(dto));
			}
		} else {
			return null;
		}

		SearchQueryResponse response = new SearchQueryResponse(voList);

		if (req.getRequestAPIType() == BaseRequestVO.REQ_TYPE_BY_ZIP) {
			response.setProperty(ISearchProvider.PROPERTY_TOTAL_PROVIDER_FOUND, new Integer(totalCount));
			List<Bucket> bucketList = null;
			if (qr != null) {
				bucketList = getFacets (qr);
			} else {
				bucketList = new ArrayList<Bucket>();
			}
			response.setProperty(ISearchProvider.PROPERTY_FACETS, bucketList);

		}

		return response;
	}

	private List<Bucket> getFacets (QueryResponse qr){
		List<FacetField> facets = qr.getFacetFields();
		List<Bucket> buckets = new ArrayList<Bucket>();
		if (facets == null)
			return buckets;
		for (FacetField facet : facets) {
			int type = 0;
			if (facet.getName().equalsIgnoreCase(facetFieldLang)) {
				type = Bucket.LANGUAGE_TYPE;

				List<FacetField.Count> facetEntries = facet.getValues();
				if (facetEntries != null) {
					for (FacetField.Count fcount : facetEntries) {
						Bucket bucket = new Bucket(type);
						bucket.setStartRange(fcount.getName());
						// FIXME converting from long to int
						bucket.setCount(Integer.valueOf((int)fcount.getCount()));
						buckets.add(bucket);
					}
				}
			}
		}

		List<Bucket> disList = getDistanceFacets(qr);
		List<Bucket> ratingsList = getRatingsFacets(qr);

		buckets.addAll(disList);
		buckets.addAll(ratingsList);
		return buckets;
	}

	private List<Bucket> getRatingsFacets(QueryResponse qr) {
		List<Bucket> buckets = new ArrayList<Bucket>();
		Map<String,Integer> map = qr.getFacetQuery();
		HashMap<String, Bucket> newMap = new HashMap<String, Bucket>();

		for (String keyStr:ratingFacetQueryMap.keySet()) {
			int count = map.get(keyStr).intValue();
			Bucket bucket = ratingFacetQueryMap.get(keyStr);
			String newkey =  bucket.getStartRange() + "-" + bucket.getEndRange();

			Bucket bucketnew = newMap.get(newkey);
			if (bucketnew == null) {
				bucketnew = new Bucket(Bucket.RATING_TYPE);
				bucketnew.setStartRange(bucket.getStartRange());
				bucketnew.setEndRange(bucket.getEndRange());
				newMap.put(newkey, bucketnew);
			}

			int newcount = count;
			if (bucketnew.getCount() != null) {
				newcount  = bucketnew.getCount().intValue() + count;
			}


			bucketnew.setCount(Integer.valueOf(newcount));
		}

		for (String keybkt:newMap.keySet()) {
			buckets.add(newMap.get(keybkt));
		}


		return buckets;
	}

	private List<Bucket> getDistanceFacets(QueryResponse qr) {
		List<Bucket> buckets = new ArrayList<Bucket>();
		HashMap<Float, Long> disMap = new HashMap<Float, Long>();

		NamedList facetCounts = (NamedList)qr.getResponse().get("facet_counts");
		if (facetCounts != null) {
			NamedList facetDistance = (NamedList)facetCounts.get("facet_geo_distances");


			if (facetDistance != null) {
				for (int i=0; i<facetDistance.size(); i++) {
					String facetName = facetDistance.getName(i);
					long count = ((Number)facetDistance.getVal(i)).longValue();
					disMap.put(Float.valueOf(facetName), Long.valueOf(count));
					//System.out.println("FN:" + facetName + ", value:" + count);
				}

				for (Bucket bucket: distanceBucket) {
					Long startCount = disMap.get(new Float(bucket.getStartRange()));
					Long endCount = disMap.get(new Float(bucket.getEndRange()));
					if (startCount != null && endCount != null) {
						long count  = endCount.longValue() - startCount.longValue();
						Bucket newBucket = new Bucket(bucket.getType());
						newBucket.setStartRange(bucket.getStartRange());
						newBucket.setEndRange(bucket.getEndRange());
						newBucket.setCount(Integer.valueOf((int)count));
						buckets.add(newBucket);
					}
				}
			}
		}

		return buckets;
	}


	private String chomp(String str, char c) {
		int len  = str.length();
		if (str.charAt(len - 1) == c)
			str = str.substring(0, len-1);
		return str;
	}

	public List<Bucket> getFacetList() {
		return facetList;
	}

	public void setFacetList(List<Bucket> facetList) {
		this.facetList = facetList;
		configureBuckets();
	}

	/* R 16_2_0_1: SL-21376:
	 * Fetching providers based on search request
	 */
	public SearchQueryResponse queryforProviders(BaseRequestVO baseReq) {
		ProviderSearchRequestVO req = (ProviderSearchRequestVO)baseReq;
		try {
			return queryforProviders(req);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** R 16_2_0_1: SL-21376:
	 * Fetching providers based on search request
	 * @param req
	 * @return
	 * @throws SolrServerException
	 */
	private SearchQueryResponse queryforProviders(ProviderSearchRequestVO req) throws SolrServerException {
		List<BaseResponseVO> voList = new ArrayList<BaseResponseVO>();
		QueryResponse qr = null;
		String zip = req.getZipCode();

		SolrQuery query = null;
		int totalCount = 0;

		ZipDto zipDdo = getLongLat(zip);
		if (zipDdo != null) {
				query = fetchProvidersByZipQuery(req);
				query.setParam("radius", "" + req.getMaxDistance());
				query.setParam("long", "" + zipDdo.getLng());
				query.setParam("lat", "" + zipDdo.getLat());
				query.setQueryType("geo");
			} else {
				return null;
			}
		

		if (server != null) {
			logger.info("Solr Query:" + query.toString());
			qr = server.query(query);
			SolrDocumentList sdl = qr.getResults();
			totalCount = (int)sdl.getNumFound();
			List<ProviderDto> beans = qr.getBeans(ProviderDto.class);
			for (ProviderDto dto : beans) {
				voList.add(new ProviderSearchResponseVO(dto));
			}
		} else {
			return null;
		}

		SearchQueryResponse response = new SearchQueryResponse(voList);

		response.setProperty(ISearchProvider.PROPERTY_TOTAL_PROVIDER_FOUND, new Integer(totalCount));

		return response;
	}
	
	/**R 16_2_0_1: SL-21376:
	 * Forming query for fetching providers based on search request
	 * @param req
	 * @return
	 */
	private SolrQuery fetchProvidersByZipQuery(ProviderSearchRequestVO req) {
		SolrQuery query = new  SolrQuery();
		StringBuilder bld = new StringBuilder();
		
		addCategoryIdQuery(req, query);
		addFacets(query, true);
		addSorting(req, query);
		query.setQuery("*:*");
		query.setIncludeScore(true);

		if(req.getMinRating() < 0.0) {
			req.setMinRating(0.0f);
		}

		if (req.getMinRating() >= 0.0) {
			if(req.getMaxRating() < req.getMinRating()) {//user is trying to specify maxrating less than minrating, return nothing by being defensive
				query.addFilterQuery("overall:[" + -1 + " TO " + -1 +"]");
			} else if (req.getMaxRating() == req.getMinRating()) {
				query.addFilterQuery("overall:[" + req.getMinRating() + " TO " + req.getMaxRating() +"]");
			} else if (req.getMaxRating() < MAX_RATING) {
				query.addFilterQuery("overall:[" + req.getMinRating() + " TO " + req.getMaxRating() +"]");
			} else {//Cap the user to max_rating of 5
				query.addFilterQuery("overall:[" + req.getMinRating() + " TO " + MAX_RATING +"]");
			}
		}

		List<String> langList = req.getLanguages();

		for (String lang:langList) {
			if (lang != null && !lang.equals("null"))
				lang = WordUtils.capitalize(lang);
				bld.append("langs:" + lang + " ");
		}

		if (bld.length() > 0) { // only when no ServiceTypes is provided.
			String str = chomp(bld.toString(), ' ');
			query.addFilterQuery(str);
		}

		int pageNumber = req.getPageNumber();
		if (pageNumber > 0)
			pageNumber --;

		query.setRows(Integer.valueOf(req.getPageSize()));
		query.setStart(Integer.valueOf(pageNumber * req.getPageSize()));
		return query;
	}

	/**R 16_2_0_1: SL-21376: To filter providers based on skills
	 * @param voProviderList
	 * @param requestProvider
	 * @return
	 */
	public List<ProviderSearchResponseVO> filterBasedOnSkills(
			List<ProviderSearchResponseVO> voProviderList,
			ProviderSearchRequestVO requestProvider) {
		List<ProviderSearchResponseVO> filteredProviderList = new ArrayList<ProviderSearchResponseVO>();
		
		for (ProviderSearchResponseVO responseVO: voProviderList){
			List<String> skillList = responseVO.getSkills();
			int index = 0;
			List<Integer> skills = new ArrayList<Integer>();
			for (String s : skillList){
				skillList.set(index++, s.replaceAll(myRegex, blank));
			}
			for (String s : skillList){
				//converting to integer list
				skills.add(Integer.parseInt(s));
			}
			if (!(Collections.disjoint(skills, requestProvider.getCategoryIds()))){
				filteredProviderList.add(responseVO);
			}
		}
		return filteredProviderList;	
	}

	/*
    public void printResults(Object tree1) {
    	if (tree1 == null) {
    		System.out.println("No Results found");
    		return;
    	}

    	QueryResponseProvider tree = (QueryResponseProvider)tree1;
    	List<ProviderDto> list = tree.getList();
    	System.out.println("Solr Server:" + solrServerUrl);

    	if (tree.isWrongSpelling()) {
    		int i = (int)count(tree.getSuggestedSpelling());
    		System.out.println("Did you mean \""  + tree.getSuggestedSpelling() + "\" (" + i + ") ?");
    		return;
    	}

    	if (list == null) {
    		System.out.println("------------------------- List is null -----------------------------------");
    		return;
    	}

    	if (list.size() > 0) {
    		String format = "|%1$-10s|%2$-20s|%3$-20s|%4$-10s|%5$-10s|%6$-4s|%7$-5s";
    		String n = String.format(format, "Id", "name", "Skill", "Score", "Licence", "City", "Licence Iussuer");
    		System.out.println(n);

    		System.out.println("-------------------------------------------------------------------------");
    		for (ProviderDto dto : list) {
    			System.out.println(dto);
    		}
    	}
    } */

	//public String getSolrServerUrl() {
	//	return solrServerUrl;
	//}

	//public void setSolrServerUrl(String solrServerUrl) {
	//	this.solrServerUrl = solrServerUrl;
	//}


	/*public static void main(String[] args) {
    	CommonsHttpSolrServer server;
		try {
			server = new CommonsHttpSolrServer("http://localhost:9080/solr/");
			BaseSearchBO obj = new ProviderSearchBO();
			ProviderSearchRequestVO req = new ProviderSearchRequestVO();
			req.setMaxDistance(5);
			req.setMinRating(0);
			req.setZipCode("60169");

			req.appendLanguageToList("Hindi");

			req.appendIdToList(111);
			req.setServiceType(ServiceTypes.Training);
			List<ProviderSearchResponseVO> vo = obj.query(req);
			for (ProviderSearchResponseVO v1: vo) {
				System.out.println(ReflectionToStringBuilder.toString(v1));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } */
}
