package com.newco.marketplace.translator.dao;

import java.util.List;

public interface IClientDAO {
	
	public void save(Client entity);

	public Client update(Client entity);

	public Client findById(Integer id);

	public List<Client> findByProperty(String propertyName, Object value);

	public List<Client> findByModifiedBy(Object modifiedBy);

	public List<Client> findAll();
	
	public Client findByClientName(String name);
}
