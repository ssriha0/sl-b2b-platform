package com.newco.marketplace.dao.impl;

import org.apache.struts.util.MessageResources;

import com.sears.os.dao.ABaseDao;
import com.sears.os.dao.impl.ABaseImplDao;

public abstract class MPBaseDaoImpl extends ABaseImplDao implements ABaseDao {

    public static final MessageResources messages = MessageResources.getMessageResources("DataAccessLocalStrings");

    public static MessageResources getMessages() {
    
        return messages;
    }
    
    
    
}