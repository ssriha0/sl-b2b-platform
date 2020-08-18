/**
 * 
 */
package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class EntityTypeVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4283790638493286448L;
	private int typeId;
	private String type;
	private String desc;
	private int sortOrder;
	
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
	/**
	 * @return the typeId
	 */
	public int getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
