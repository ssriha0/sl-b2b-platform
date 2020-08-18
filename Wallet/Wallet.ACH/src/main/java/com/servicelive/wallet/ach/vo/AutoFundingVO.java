package com.servicelive.wallet.ach.vo;

import java.io.Serializable;

/**
 * User: Yunus Burhani
 * Date: Apr 2, 2010
 * Time: 5:48:48 PM
 */
public class AutoFundingVO implements Serializable {

    private Long entityId;
    private Integer entityTypeId;
    private Long accountId;
    private Integer enabled;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Integer entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }


    
}
