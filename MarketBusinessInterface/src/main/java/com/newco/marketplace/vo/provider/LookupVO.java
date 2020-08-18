/*
 * SecretQuestionVO.java    1.0     2007/05/30
 */
package com.newco.marketplace.vo.provider;


/**
 * Value object for Secret Question
 * 
 * @version
 * @author blars04
 *
 */
public class LookupVO extends BaseVO 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7997311794640977160L;
	String type = null;
    String descr = null;
    Integer sortOrder = null;
    String entity = null;
    int reasonCd = -1;
    
    


	/**
	 * @return the reasonCd
	 */
	public int getReasonCd() {
		return reasonCd;
	}
	/**
	 * @param reasonCd the reasonCd to set
	 */
	public void setReasonCd(int reasonCd) {
		this.reasonCd = reasonCd;
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

    
}//end class SecretQuectionVO