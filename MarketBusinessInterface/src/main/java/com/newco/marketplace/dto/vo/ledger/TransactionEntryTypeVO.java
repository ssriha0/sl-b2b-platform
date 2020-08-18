package com.newco.marketplace.dto.vo.ledger;

import com.sears.os.vo.SerializableBaseVO;


public class TransactionEntryTypeVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 297056649930630183L;
	private Integer entryTypeId;
    private String  type;
    private String  descr;
    
    
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Integer getEntryTypeId() {
		return entryTypeId;
	}
	public void setEntryTypeId(Integer entryTypeId) {
		this.entryTypeId = entryTypeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

    
    
    
    
    
}//end class SecretQuectionVO