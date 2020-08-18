/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.skillTree;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing category information.
 * @author Infosys
 *
 */
@XStreamAlias("skillCategory")
public class Category implements Comparable<Category>{
	@XStreamAlias("id")
	private Integer id;
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("parentId")
	private Integer parentId;
	
	@XStreamAlias("subCategoryId")
	private Integer subCategoryId;
	
	@XStreamAlias("subCategoryName")
	private String subCategoryName;
	
	@XStreamAlias("categoryId")
	private Integer categoryId;
	
	@XStreamAlias("categoryName")
	private String categoryName;
	
	@XStreamAlias("mainCategoryId")
	private Integer mainCategoryId;
	
	@XStreamAlias("mainCategoryName")
	private String mainCategoryName;
	
	@XStreamAlias("type")
	private String type;
	
	@OptionalParam
	@XStreamAlias("parentName")
	private String parentName;
	
	@OptionalParam
	@XStreamAlias("level")
	private Integer level;
	
	@OptionalParam
	@XStreamAlias("score")
	private String score;
	
	@OptionalParam
	@XStreamAlias("skuList")
	private SkuList skuList;

	@OptionalParam
	@XStreamAlias("providerCount")
	private Integer providerCount;
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	

	

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	
	
	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
	
	

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	
	
	public Integer getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	
	
	

	public String getMainCategoryName() {
		return mainCategoryName;
	}

	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}

	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(Integer providerCount) {
		this.providerCount = providerCount;
	}
	
	public int compareTo(Category another) {
		if (!(another instanceof Category))
			throw new ClassCastException("A Category object expected.");
		return this.name.compareTo(another.name);
	}

	public SkuList getSkuList() {
		return skuList;
	}

	public void setSkuList(SkuList skuList) {
		this.skuList = skuList;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
