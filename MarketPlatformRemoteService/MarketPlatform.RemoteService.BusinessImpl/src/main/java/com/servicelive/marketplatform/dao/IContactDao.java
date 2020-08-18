package com.servicelive.marketplatform.dao;

import com.servicelive.domain.common.Contact;

public interface IContactDao {
    public Contact findById(int id);
}