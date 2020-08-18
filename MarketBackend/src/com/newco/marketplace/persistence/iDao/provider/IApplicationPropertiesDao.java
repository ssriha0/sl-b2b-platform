package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.vo.provider.ApplicationProperties;

public interface IApplicationPropertiesDao 
{
	
	public int update(ApplicationProperties ap) throws DataAccessException;
    public ApplicationProperties query(ApplicationProperties ap);    
    public ApplicationProperties insert(ApplicationProperties ap);
    public List queryList(ApplicationProperties ap);
}
