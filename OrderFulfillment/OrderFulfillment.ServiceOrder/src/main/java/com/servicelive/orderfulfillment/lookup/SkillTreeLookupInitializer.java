package com.servicelive.orderfulfillment.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;

public class SkillTreeLookupInitializer {
    IMarketPlatformProviderBO marketPlatformProviderBO;

    public void initialize(SkillTreeLookup skillTreeLookup) {
        List<SkillNode> skillNodes = marketPlatformProviderBO.retrieveAllAvailableProviderSkills();
        Map<Long, SkillNode> skillNodeMap = new TreeMap<Long, SkillNode>();
        Map<String, Long> mainCategoryMap = new HashMap<String, Long>();
        Map<String, Long> subCategoryMap = new HashMap<String, Long>();
        for (SkillNode skillNode : skillNodes) {
            skillNodeMap.put(skillNode.getId(), skillNode);
            if(skillNode.getLevel().intValue() == 1 && skillNode.getParentNodeId().longValue() == 0){//main category
            	mainCategoryMap.put(skillNode.getNodeName().trim(), skillNode.getId());
            }else{
            	subCategoryMap.put(skillNode.getNodeName().trim(), skillNode.getId());
            }
        }
        skillTreeLookup.setSkillNodeMap(skillNodeMap);
        skillTreeLookup.setMainCategoryMap(mainCategoryMap);
        skillTreeLookup.setSubCategoryMap(subCategoryMap);
        skillTreeLookup.setInitialized(true);
    }


    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    
    public void setMarketPlatformProviderBO(IMarketPlatformProviderBO marketPlatformProviderBO) {
        this.marketPlatformProviderBO = marketPlatformProviderBO;
    }
}
