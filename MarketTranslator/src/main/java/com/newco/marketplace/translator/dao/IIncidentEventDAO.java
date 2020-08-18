package com.newco.marketplace.translator.dao;

import java.util.List;

public interface IIncidentEventDAO {
	
	public void save(IncidentEvent entity);

	public IncidentEvent update(IncidentEvent entity);

	public IncidentEvent findById(Integer id);

	public List<IncidentEvent> findByProperty(String propertyName, Object value);

	public List<IncidentEvent> findByModifiedBy(Object modifiedBy);

	public List<IncidentEvent> findAll();
}
