package com.newco.marketplace.translator.business;

import java.util.List;

import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.Incident;
import com.newco.marketplace.translator.dao.IncidentAck;
import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.dao.IncidentNote;
import com.newco.marketplace.translator.dao.WarrantyContract;

public interface IIncidentService {
	
	public Incident getIncidentByClientIncident(String incidentID, Client client);
	
	public boolean saveIncidentEvent(IncidentEvent incidentEvent);
	
	public boolean saveCancelIncidentEvent(IncidentEvent incidentEvent);
	
	public boolean saveIncident(Incident incident);
	
	public boolean saveWarrantyContract(WarrantyContract contract);
	
	public Incident getIncidentByIncidentEvent(IncidentEvent incidentEvent);
	
	public boolean saveIncidentNote(IncidentNote incidentNote);
	
	public boolean saveIncidentAck(IncidentAck incidentAck);

	public List<Incident> findLatestIncidentWithClientIncidentIdMatchingBeforeTestSuffix(
			Client client, List<String> incidentIds, String testSuffix);

	public List<Incident> findAllIncidents(Client client,
			List<String> clientIncidentIds);

}
