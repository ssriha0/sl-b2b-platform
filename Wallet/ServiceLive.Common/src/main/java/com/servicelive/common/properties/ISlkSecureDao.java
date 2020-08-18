package com.servicelive.common.properties;


import org.springframework.dao.DataAccessException;

import com.servicelive.common.exception.DataNotFoundException;

public interface ISlkSecureDao {
    
    /**
     * Queries encryption_key table for key set in input parameter
     * @param key
     * @return
     * @throws DataAccessException
     */
    
    public String valueForKey(String key) throws DataAccessException, DataNotFoundException;

}
