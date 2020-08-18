/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.search.types.Category;
import com.newco.marketplace.search.types.Skill;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the Skills 
 * @author Infosys
 *
 */
@XStreamAlias("skills")
public class Skills {
	 
	@XStreamImplicit(itemFieldName="category")
	private List<ProviderCategory> categoryList;
	
	public Skills(ProviderSearchResponseVO providerSearchResponseVO) {
		this.categoryList = new ArrayList<ProviderCategory>();
		List<Category> providerSearchCategoryList = providerSearchResponseVO
		.getCategories();
		
		if (null != providerSearchCategoryList) {
			for (Category providerSearchCategory : providerSearchCategoryList) {
				List<ProviderSkill> skillList = new ArrayList<ProviderSkill>();
				if ((null != providerSearchCategory.getSkillList())) {
					ProviderSkill prevProSkill = null; 
					for (Skill proSkill : providerSearchCategory.getSkillList()) {
						ProviderSkill providerSkill = new ProviderSkill(proSkill);
						if(proSkill.getLevel() > 2){
							if(prevProSkill!=null){
								prevProSkill.setProviderSkill(providerSkill);
							}else{
								skillList.add(providerSkill);
								prevProSkill = providerSkill;
							}	
						}else{
							skillList.add(providerSkill);
							prevProSkill = providerSkill;
						}	
					}
				}
				ProviderCategory providerCategory = new ProviderCategory();
				providerCategory.setSkillList(skillList);
				providerCategory.setRoot(providerSearchCategory.getRoot());
				providerCategory.setRootId(providerSearchCategory.getRootId());
				this.categoryList.add(providerCategory);
			}
		}		
	}
	
	public List<ProviderCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ProviderCategory> categoryList) {
		this.categoryList = categoryList;
	}


}
