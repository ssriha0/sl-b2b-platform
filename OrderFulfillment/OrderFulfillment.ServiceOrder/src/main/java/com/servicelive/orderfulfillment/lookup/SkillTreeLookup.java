package com.servicelive.orderfulfillment.lookup;

import java.util.Map;

import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.orderfulfillment.common.ServiceOrderException;

public class SkillTreeLookup implements IQuickLookup, IRemoteServiceDependentLookup {
    Map<Long, SkillNode> skillNodeMap;
    Map<String, Long> mainCategoryMap;
    Map<String, Long> subCategoryMap;
    
    SkillTreeLookupInitializer initializer;
    
    boolean initialized;

    public void initialize() {
        initializer.initialize(this);
    }

    public void setSkillNodeMap(Map<Long, SkillNode> skillNodeMap) {
        this.skillNodeMap = skillNodeMap;
    }

    public void setMainCategoryMap(Map<String, Long> mainCategoryMap){
    	this.mainCategoryMap = mainCategoryMap;
    }
    
    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public SkillNode getSkillNodeById(Long id) {
        if (!isInitialized()) throw new ServiceOrderException("Skill Category Lookup not initialized");
        return skillNodeMap.get(id);
    }

    public Long getMainCategoryId(String catName){
    	if(!isInitialized())  throw new ServiceOrderException("Skill Category Lookup not initialized");
    	return mainCategoryMap.get(catName);
    }

    public Long getSubCategoryId(String catName){
    	if(!isInitialized())  throw new ServiceOrderException("Skill Category Lookup not initialized");
    	return subCategoryMap.get(catName);
    }
    
    public void setInitializer(SkillTreeLookupInitializer initializer) {
        this.initializer = initializer;
    }

	public void setSubCategoryMap(Map<String, Long> subCategoryMap) {
		this.subCategoryMap = subCategoryMap;
	}
}
