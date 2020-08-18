/**
 * 
 */
package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class SurveyQuestionCategoryVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2476708269920518869L;
	private int categoryId;
	private String type;
	private String desc;
	private int sortOrder;
	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	

}
