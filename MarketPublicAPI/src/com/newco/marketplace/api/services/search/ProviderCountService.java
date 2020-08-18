package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderResults;
import com.newco.marketplace.api.beans.search.providercount.Location;
import com.newco.marketplace.api.beans.search.providercount.Locations;
import com.newco.marketplace.api.beans.search.providercount.ProviderCounts;
import com.newco.marketplace.api.beans.search.skillTree.Categories;
import com.newco.marketplace.api.beans.search.skillTree.Category;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.search.SearchProviderMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.skillTree.SkillTreeRequestVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;
import com.newco.marketplace.util.StateCodes;
import com.newco.marketplace.utils.SimpleCache;


/**
 * This class would act as a service class for getting providers by zip Code Search.
 *
 * API     : /providers
 * APIType : Get
 * Request Processor  : {@link RequestProcessorTraining#getProvidersByProfileId}
 * Spring Bean Name   : ProviderByIdService
 * Spring Context XML : apiApplicationContext.xml
 *
 * @author: Shekhar Nirkhe(chandra@servicelive.com)
 * @since 10/10/2009
 * @version 1.0
 *
 */


@Namespace("http://www.servicelive.com/namespaces/providerCounts")
@APIResponseClass(ProviderCounts.class)
public class ProviderCountService extends AbstractSearchService{

	private Logger logger = Logger.getLogger(ProviderCountService.class);
	
	public ProviderCountService() {
	}


	/**
	 * This method is for retrieving providers counts for all or requested states. It will call solr service to
	 * retrieve provider details for requested ID(s).  The request can contain multiple IDs separated
	 * by hyphen(-) . It will convert them into an array before sending it to back-end.
	 *
	 * @param providers String
	 * @return String
	 */
	public IAPIResponse execute(APIRequestVO searchVO) {
		logger.info("Entering execute method");
		Map<String,String> requestMap = searchVO.getRequestFromGetDelete();
		String city = requestMap.get("city");
		String state = requestMap.get("state");
		String catName = requestMap.get("categoryName");
		List<Integer> catList = null;
		
		ProviderCounts providerCounts = new ProviderCounts();
		List<Location> list = new ArrayList<Location>();
		
		String location = state;
		
		if (catName != null)
			catList = getCatList(catName);
		if (StringUtils.isBlank(state)) {
			for (String stateName:StateCodes.map.keySet()) {
				Location pc =  getCountByLocation(stateName, null, catList);	
				list.add(pc);				
			}
			
			if (catList == null)
			  providerCounts.setCategories(getCountBySkills(null, null));
			
		} else if (StringUtils.isBlank(city)) { // Only State is present
			Map<String, Long> cities = providerSearch.getCities(state, catList);
			for (String name:cities.keySet()) {
				location = WordUtils.capitalize(name);
				int count = cities.get(name).intValue();
				Location pc=  new Location(location, count);
				list.add(pc);
			}
			//providerCounts.setCategories(getCountBySkills(state, null));
		} else {
		  providerCounts.setCategories(getCountBySkills(state, city));
		}

		if (list.size() > 0) {
			Locations locations = new Locations();
			locations.setLocationList(list);
			providerCounts.setLocations(locations);
		}
		
		providerCounts.setResults(Results.getSuccess());
		if (providerCounts.getLocations() != null && providerCounts.getLocations().getLocationList() != null)
		  Collections.sort(providerCounts.getLocations().getLocationList());
		
		if (providerCounts.getCategories() != null && providerCounts.getCategories().getCategoryList() != null)
		  Collections.sort(providerCounts.getCategories().getCategoryList());
		return providerCounts;
	}

	private Location getCountByLocation(String stateName, String city, List<Integer> catList) {
		int count = providerSearch.getProvidersCount(stateName, city, catList);
		String location = stateName;
		if (city != null)
			location = city;
		location = WordUtils.capitalize(location);
		Location pc =  new Location(location, count);
		 if (city != null)
			  pc.setType("city");
		  else
			  pc.setType("state");
		 return pc;
	}
	
	
	/*
	@SuppressWarnings("unchecked")
	private void addCount(final Integer skillId, final Integer count) {
		HashMap<Integer, SkillCat> skillIdParentMap = 
			(HashMap<Integer, SkillCat> )SimpleCache.getInstance().get(SKILL_ID_PARENT_MAP_KEY);
		if (skillIdParentMap == null) {
			prepareSkillTree();
			skillIdParentMap = 
				(HashMap<Integer, SkillCat> )SimpleCache.getInstance().get(SKILL_ID_PARENT_MAP_KEY);
		}
		
		SkillCat cat = skillIdParentMap.get(skillId);
		if (cat != null)
		  cat.addProviderCount(count);	
	}
     */
	
	
	private Categories getCountBySkills(String stateName, String city) {
		Categories categories  = new Categories();
//		Map<Integer, Integer> map = providerSearch.getProvidersCountBySkills(stateName, city);
//		for (Integer skillId: map.keySet()) {
//			addCount(skillId, map.get(skillId));
//		}
		
		HashMap<String, SkillCat> skillMap = 
			(HashMap<String, SkillCat> )SimpleCache.getInstance().get(SKILL_COUNT_MAP_KEY);
		
		if (skillMap == null)
			skillMap = prepareSkillTree();
		
		for (String name:skillMap.keySet()) {
			
			SkillCat sc = skillMap.get(name);
			if (sc != null) {
				int count = providerSearch.getProvidersCount(stateName, city, sc.skillList);
				if (count > 0) {
					Category cat = new Category();
					cat.setName(name);
					cat.setId(sc.id);
					cat.setProviderCount(count);
					cat.setParentId(0);
					categories.addCategory(cat);
				}
			}
		}
		
		if (categories.getCategoryList() != null && categories.getCategoryList().size() > 0)
			return categories;
		
		return null;
	}
	
	private List<Integer> getCatList(String catName) {
		HashMap<String, SkillCat> skillMap = 
			(HashMap<String, SkillCat> )SimpleCache.getInstance().get(SKILL_COUNT_MAP_KEY);

		if (skillMap == null)
			skillMap = prepareSkillTree();

		SkillCat sc = skillMap.get(catName);
		return sc.skillList;
	}
}


