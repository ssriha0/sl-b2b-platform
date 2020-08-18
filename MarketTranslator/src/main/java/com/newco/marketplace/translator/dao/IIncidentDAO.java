package com.newco.marketplace.translator.dao;

import java.util.List;

public interface IIncidentDAO {
	
	public void save(Incident entity);

	public Incident update(Incident entity);

	public Incident findById(Integer id);

	public List<Incident> findByProperty(String propertyName, Object value);

	public List<Incident> findByModifiedBy(Object modifiedBy);

	public List<Incident> findAll();
	
	public Incident findByClientIncidentID(final String clientIncidentID, final Client client);

	public Incident findLatestByClientIncidentIdWithTestSuffix(final Client client, final String clientIncidentId, final String testSuffix);

	public List<Incident> findByClientIncidentIds(final Client client,
			final List<String> clientIncidentIds);
}
