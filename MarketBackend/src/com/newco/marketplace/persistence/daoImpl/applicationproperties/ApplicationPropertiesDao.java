package com.newco.marketplace.persistence.daoImpl.applicationproperties;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface ApplicationPropertiesDao 
{
	
	public int update(ApplicationProperties ap) throws DataAccessException;
    
    public ApplicationProperties query(ApplicationProperties ap)
	    throws DataAccessException;
    
    public ApplicationProperties insert(ApplicationProperties ap)
	    throws DataAccessException;
    
    public List queryList(ApplicationProperties ap) throws DataAccessException;

}
