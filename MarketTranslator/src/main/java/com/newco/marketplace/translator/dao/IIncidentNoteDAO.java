package com.newco.marketplace.translator.dao;

import java.util.List;

public interface IIncidentNoteDAO {

	public void save(IncidentNote entity);

	public IncidentNote update(IncidentNote entity);

	public IncidentNote findById(Integer id);

	public List<IncidentNote> findByProperty(String propertyName, Object value);

	public List<IncidentNote> findByModifiedBy(Object modifiedBy);

	public List<IncidentNote> findAll();
	
}
