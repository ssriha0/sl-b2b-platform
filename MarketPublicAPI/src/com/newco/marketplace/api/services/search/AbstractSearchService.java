package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;
import com.newco.marketplace.utils.SimpleCache;

public abstract class AbstractSearchService extends BaseService {
	public static final String SKILL_ID_PARENT_MAP_KEY = "/skilltree/skillIdParentMap";
	public static final String SKILL_COUNT_MAP_KEY = "/skilltree/skillIdCountMap";
	private final long CACHE_TIMEOUT = SimpleCache.ONE_HOUR * 12;
	
	@Override
	abstract public IAPIResponse execute(APIRequestVO apiVO);
	
	protected ISearchProvider providerSearch;
	protected XStreamUtility conversionUtility;
	
	protected HashMap<String, SkillCat> prepareSkillTree() {
		SkillRequestVO reqVo = new SkillRequestVO();
		reqVo.setSpellCheck(false);
		
		List<SkillTreeResponseVO> providerResponseList = providerSearch.getSkillTree(reqVo);		
		HashMap<String, SkillCat> skillMap = new HashMap<String, SkillCat>();
		HashMap<Integer, SkillCat> skillIdParentMap = new HashMap<Integer, SkillCat>();

		for (SkillTreeResponseVO vo : providerResponseList) {
			if (vo.getLevel() == 1) {
				SkillCat cat = new SkillCat();
				cat.id = vo.getNodeId();
				cat.addSkillId(vo.getNodeId());
				cat.name = vo.getNodeName();
				cat.name = cat.name.replaceAll("&", "and");
				cat.name = cat.name.replaceAll("/", " and ");
				skillMap.put(cat.name, cat);
				skillIdParentMap.put(vo.getNodeId(), cat);
			} 
		}

		for(SkillTreeResponseVO vo : providerResponseList) {
			if (vo.getLevel() == 2) {				
				SkillCat parent = skillIdParentMap.get(vo.getParentNodeId());				
				parent.addSkillId(vo.getNodeId());
				skillIdParentMap.put(vo.getNodeId(), parent);
			}
		}
		
		for(SkillTreeResponseVO vo : providerResponseList) {			
			if (vo.getLevel() == 3) {				
				SkillCat parent = skillIdParentMap.get(vo.getParentNodeId());				
				parent.addSkillId(vo.getNodeId());
				skillIdParentMap.put(vo.getNodeId(), parent);
			}
		}
		
		SimpleCache.getInstance().put(SKILL_ID_PARENT_MAP_KEY, skillIdParentMap, CACHE_TIMEOUT);
		SimpleCache.getInstance().put(SKILL_COUNT_MAP_KEY, skillMap, CACHE_TIMEOUT);
		return skillMap;
	}

	public ISearchProvider getProviderSearch() {
		return providerSearch;
	}

	public void setProviderSearch(ISearchProvider providerSearch) {
		this.providerSearch = providerSearch;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}
	
	protected List<Integer> getNodeListByCategories(String catName) {
		HashMap<String, SkillCat> skillMap = (HashMap<String, SkillCat>)SimpleCache.getInstance().get(SKILL_COUNT_MAP_KEY);
		if (skillMap == null)
			skillMap = prepareSkillTree();
		SkillCat cat = skillMap.get(catName);
		if (cat != null)			
			return cat.skillList;
		else
			return new ArrayList<Integer>();
	}

}

class SkillCat {
	public int providerCount;
	public int id;
	public List<Integer> skillList;
	public String name;
	public void addSkillId(Integer id) {
		if (skillList == null)
			skillList = new ArrayList<Integer>();
		skillList.add(id);
	}
	
	public void addProviderCount(int count) {
		providerCount += count;
	}
}
