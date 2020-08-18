/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.skillTree;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a  bean class for storing information for a list of categories 
 * @author Infosys
 *
 */
@XStreamAlias("categories")
public class Categories {

	@XStreamImplicit(itemFieldName="skillCategory")
	private List<Category> categoryList;

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	

	public void addCategory(Category category) {
		if (categoryList == null)
			categoryList = new ArrayList<Category>();
		this.categoryList.add(category);
	}


}
