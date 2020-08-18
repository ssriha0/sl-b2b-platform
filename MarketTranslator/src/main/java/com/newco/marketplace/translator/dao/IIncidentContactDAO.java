package com.newco.marketplace.translator.dao;

import java.util.List;

public interface IIncidentContactDAO {
	
	public void save(IncidentContact entity);

	public IncidentContact update(IncidentContact entity);

	public IncidentContact findById(Integer id);

	public List<IncidentContact> findByProperty(String propertyName, Object value);

	public List<IncidentContact> findByModifiedBy(Object modifiedBy);

	public List<IncidentContact> findAll();
}
