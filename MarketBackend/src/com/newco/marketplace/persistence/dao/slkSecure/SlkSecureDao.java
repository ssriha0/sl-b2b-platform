package com.newco.marketplace.persistence.dao.slkSecure;


import org.springframework.dao.DataAccessException;
import com.newco.marketplace.exception.core.DataNotFoundException;

public interface SlkSecureDao {
    
    /**
     * Queries encryption_key table for key set in input parameter
     * @param key
     * @return
     * @throws DataAccessException
     */
    
    public String valueForKey(String key) throws DataAccessException, DataNotFoundException;

}
