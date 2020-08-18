package com.newco.marketplace.persistence.iDao.applicationproperties;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.exception.core.DataNotFoundException;

public interface IApplicationPropertiesDao {
    
    /**
     * Queries application_properties table for key set in input parameter
     * @param key
     * @return
     * @throws DataAccessException
     */
    public ApplicationPropertiesVO query(String key) throws DataAccessException, DataNotFoundException;
    
    /**
     * Returns all rows from the application_properties table
     * @return
     * @throws DataAccessException
     */
    public List<ApplicationPropertiesVO> queryList() throws DataAccessException;
    
    public String queryByKey(String key) throws DataAccessException, DataNotFoundException;

}
