package com.newco.marketplace.dto.vo.sitestatistics;

import org.apache.commons.lang.StringUtils;

import com.sears.os.vo.SerializableBaseVO;


/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */
public class PopularServicesVO extends SerializableBaseVO implements Comparable<PopularServicesVO> {

	private static final long serialVersionUID = -1213002601779961687L;
	private String name;
	private Integer mainCategoryId;
	private Integer categoryId;
	private Integer subCategoryId;
	private String categoryName;
	private String subCategoryName;
	private Integer serviceTypeTemplateId;
	private Integer sortOrder;
	private Integer buyerTypeId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public Integer getServiceTypeTemplateId() {
		return serviceTypeTemplateId;
	}
	public void setServiceTypeTemplateId(Integer serviceTypeTemplateId) {
		this.serviceTypeTemplateId = serviceTypeTemplateId;
	}
	public Integer getBuyerTypeId() {
		return buyerTypeId;
	}
	public void setBuyerTypeId(Integer buyerTypeId) {
		this.buyerTypeId = buyerTypeId;
	}
	
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int compareTo(PopularServicesVO o) {
		if (null == o) {
			return -1;
		}
		
		if (StringUtils.equals(this.name,o.name) &&
			this.buyerTypeId.intValue() == o.buyerTypeId.intValue() &&
			this.mainCategoryId.intValue() == o.mainCategoryId.intValue() &&
			( (null == this.categoryId && null == o.getCategoryId()) ||
			  (this.categoryId.intValue() == o.getCategoryId().intValue()) ) &&
			( (null == this.subCategoryId && null == o.getSubCategoryId()) ||
			  (this.subCategoryId.intValue() == o.getSubCategoryId().intValue()) ) &&
		    ( (null == this.serviceTypeTemplateId && null == o.getServiceTypeTemplateId()) ||
			  (this.serviceTypeTemplateId.intValue() == o.getServiceTypeTemplateId().intValue()) ) ) {
			return 0;
		} else {
			return -1;
		}
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}
	/**
	 * @param subCategoryName the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
	
}
