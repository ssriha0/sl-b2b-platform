package com.newco.marketplace.dto.vo.audit;

import com.newco.marketplace.vo.MPBaseVO;

public class WorkflowStateVO  extends MPBaseVO { 
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 5382279135396260080L;
	public Integer wfStateId = null;  
    public String wfEntity = null;                                            
    public String wfState = null;                                           
    public String wfDescr = null;                                      
    public Integer auditLinkId = null;  
    public String auditKeyId = null;
    public String tableName = null;                       
    public String keyName = null;
  
    @Override
	public String toString () {
        return ("WorkflowStateVO : "+ wfEntity + " | " + wfState +" | "+ wfStateId);
    }

    public String getAuditKeyId() {
    
        return auditKeyId;
    }

    public void setAuditKeyId(String auditKeyId) {
    
        this.auditKeyId = auditKeyId;
    }

    public Integer getAuditLinkId() {
    
        return auditLinkId;
    }

    public void setAuditLinkId(Integer auditLinkId) {
    
        this.auditLinkId = auditLinkId;
    }

    public String getKeyName() {
    
        return keyName;
    }

    public void setKeyName(String keyName) {
    
        this.keyName = keyName;
    }

    public String getTableName() {
    
        return tableName;
    }

    public void setTableName(String tableName) {
    
        this.tableName = tableName;
    }

    public String getWfDescr() {
    
        return wfDescr;
    }

    public void setWfDescr(String wfDescr) {
    
        this.wfDescr = wfDescr;
    }

    public String getWfEntity() {
    
        return wfEntity;
    }

    public void setWfEntity(String wfEntity) {
    
        this.wfEntity = wfEntity;
    }

    public String getWfState() {
    
        return wfState;
    }

    public void setWfState(String wfState) {
    
        this.wfState = wfState;
    }

    public Integer getWfStateId() {
    
        return wfStateId;
    }

    public void setWfStateId(Integer wfStateId) {
    
        this.wfStateId = wfStateId;
    }
 
 
}
