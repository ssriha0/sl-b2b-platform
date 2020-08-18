package com.newco.marketplace.web.dto.homepage;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

/**
 * $Revision: 1.4 $ $Author: akashya $ $Date: 2008/05/21 23:33:09 $
 */
public class HomepagePopularServiceDTO extends SerializedBaseDTO{


	private static final long serialVersionUID = -1039141460895090414L;
	private String name;
	private Integer mainCategoryId;
	private Integer subCategoryId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public Integer getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	
	
}
