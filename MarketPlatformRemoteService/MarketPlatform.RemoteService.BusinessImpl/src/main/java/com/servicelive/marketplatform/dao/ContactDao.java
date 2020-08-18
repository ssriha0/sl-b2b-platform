package com.servicelive.marketplatform.dao;

import com.servicelive.domain.common.Contact;


public class ContactDao extends BaseEntityDao implements IContactDao {
    public Contact findById(int id) {
        return entityMgr.find(Contact.class, (int)id);
    }
}