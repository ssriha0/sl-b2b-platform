/*
 * SecretQuestionVO.java    1.0     2007/05/30
 */
package com.newco.marketplace.dto.vo.leadsmanagement;


/**
 * Value object for Secret Question
 * 
 * @version
 * @author blars04
 *
 */
public class BuyerLeadLookupVO 
{

	String type = null;
    String descr = null;
    Integer sortOrder = null;
    String entity = null;
	String id;
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	/**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    
}