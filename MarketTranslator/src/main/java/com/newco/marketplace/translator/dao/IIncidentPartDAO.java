package com.newco.marketplace.translator.dao;

import java.util.List;

public interface IIncidentPartDAO {

	public void save(IncidentPart entity);

	public IncidentPart update(IncidentPart entity);

	public IncidentPart findById(Integer id);

	public List<IncidentPart> findByProperty(String propertyName, Object value);

	public List<IncidentPart> findByModifiedBy(Object modifiedBy);

	public List<IncidentPart> findAll();
}
