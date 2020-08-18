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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

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
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderSkills {
	 
	@XStreamImplicit(itemFieldName="category")
	private List<CategoryItem> categoryList;
	
	public ProviderSkills(){
		
	}
	
	public ProviderSkills(ProviderSearchResponseVO providerSearchResponseVO) {
		this.categoryList = new ArrayList<CategoryItem>();
		List<Category> providerSearchCategoryList = providerSearchResponseVO
		.getCategories();
		
		if (null != providerSearchCategoryList) {
			for (Category providerSearchCategory : providerSearchCategoryList) {
				List<SkillItem> skillList = new ArrayList<SkillItem>();
				if ((null != providerSearchCategory.getSkillList())) {
					SkillItem prevProSkill = null; 
					for (Skill proSkill : providerSearchCategory.getSkillList()) {
						SkillItem providerSkill = new SkillItem(proSkill);
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
				CategoryItem providerCategory = new CategoryItem();
				providerCategory.setSkillList(skillList);
				providerCategory.setRoot(providerSearchCategory.getRoot());
				providerCategory.setRootId(providerSearchCategory.getRootId());
				this.categoryList.add(providerCategory);
			}
		}		
	}
	
	public List<CategoryItem> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryItem> categoryList) {
		this.categoryList = categoryList;
	}


}
