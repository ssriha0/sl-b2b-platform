package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;


/**
 * 
 * @author akarker
 *
 */
public class VendorCredentialsLookupVO extends SerializableBaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8447380190748395855L;
	private int typeId;
    private String type;
    private String typeDesc;
    
    private int catId;
    private String category;
    private String categoryDesc;
        
    public String getCategory() {
    
        return category;
    }
    public void setCategory(String category) {
    
        this.category = category;
    }
    public String getCategoryDesc() {
    
        return categoryDesc;
    }
    public void setCategoryDesc(String categoryDesc) {
    
        this.categoryDesc = categoryDesc;
    }
    public int getCatId() {
    
        return catId;
    }
    public void setCatId(int catId) {
    
        this.catId = catId;
    }
    public String getType() {
    
        return type;
    }
    public void setType(String type) {
    
        this.type = type;
    }
    public String getTypeDesc() {
    
        return typeDesc;
    }
    public void setTypeDesc(String typeDesc) {
    
        this.typeDesc = typeDesc;
    }
    public int getTypeId() {
    
        return typeId;
    }
    public void setTypeId(int typeId) {
    
        this.typeId = typeId;
    }
	
}