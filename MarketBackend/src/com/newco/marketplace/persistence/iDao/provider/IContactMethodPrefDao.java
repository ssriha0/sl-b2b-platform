
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.ContactMethodPref;

public interface IContactMethodPrefDao
{
    public int update(ContactMethodPref contactMethodPref)throws DBException;
    public ContactMethodPref query(ContactMethodPref contactMethodPref)throws DBException;
    public ContactMethodPref insert(ContactMethodPref contactMethodPref)throws DBException;
    public List queryList(ContactMethodPref vo)throws DBException;
    
}
