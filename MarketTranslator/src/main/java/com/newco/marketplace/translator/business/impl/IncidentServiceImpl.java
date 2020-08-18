package com.newco.marketplace.translator.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.newco.marketplace.translator.business.IClientService;
import com.newco.marketplace.translator.business.IIncidentService;
import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.IIncidentAckDAO;
import com.newco.marketplace.translator.dao.IIncidentContactDAO;
import com.newco.marketplace.translator.dao.IIncidentDAO;
import com.newco.marketplace.translator.dao.IIncidentEventDAO;
import com.newco.marketplace.translator.dao.IIncidentNoteDAO;
import com.newco.marketplace.translator.dao.IIncidentPartDAO;
import com.newco.marketplace.translator.dao.IWarrantyContractDAO;
import com.newco.marketplace.translator.dao.Incident;
import com.newco.marketplace.translator.dao.IncidentAck;
import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.dao.IncidentNote;
import com.newco.marketplace.translator.dao.IncidentPart;
import com.newco.marketplace.translator.dao.WarrantyContract;

public class IncidentServiceImpl implements IIncidentService {
	
	private static final Logger logger = Logger.getLogger(IncidentServiceImpl.class);
	
	private IIncidentDAO incidentDAO;
	private IIncidentEventDAO incidentEventDAO;
	private IWarrantyContractDAO warrantyDAO;
	private IIncidentPartDAO partDAO;
	private IIncidentContactDAO contactDAO;
	private IClientService clientService;
	private IIncidentNoteDAO incidentNoteDAO;
	private IIncidentAckDAO incidentAckDAO;
	private JpaTransactionManager txManager;
	
	public Incident getIncidentByIncidentEvent(IncidentEvent incidentEvent) {
		WarrantyContract fileWarrantyContract = incidentEvent.getIncident().getWarrantyContract();
		Incident incident = getIncidentByClientIncident(incidentEvent.getIncident().getClientIncidentID(), incidentEvent.getIncident().getClient());
		if (incident == null) {
			incident = incidentEvent.getIncident();
			saveWarrantyContract(incident.getWarrantyContract());
			incident.setWarrantyContract(incident.getWarrantyContract());
			saveIncident(incident);
		}
		else {
			try {
				WarrantyContract warrantyContract = warrantyDAO.findByClientAndContract(incident.getClient(), incident.getWarrantyContract().getContractNumber());
				incident.setWarrantyContract(processWarrantyChanges(warrantyContract, fileWarrantyContract));
			}
			catch (Exception e) {
				logger.warn("Could not find warranty: " + incident.getWarrantyContract().getContractNumber() + " creating", e);
			}
		}
		return incident;
	}
	
	public WarrantyContract processWarrantyChanges(WarrantyContract savedWarranty, WarrantyContract fileWarranty) {
		boolean changedWarranty = false;
		if (savedWarranty.getContractDate().compareTo(fileWarranty.getContractDate()) != 0) {
			savedWarranty.setWarrantyNotes(savedWarranty.getWarrantyNotes() + " Contract Date Changed Original:" + savedWarranty.getContractTypeCode());
			savedWarranty.setContractDate(fileWarranty.getContractDate());
			changedWarranty = true;
		}
		if (!savedWarranty.getContractTypeCode().equals(fileWarranty.getContractTypeCode())) {
			savedWarranty.setWarrantyNotes(savedWarranty.getWarrantyNotes() + " Contract Type Code Changed Original:" + savedWarranty.getContractTypeCode());
			savedWarranty.setContractTypeCode(fileWarranty.getContractTypeCode());
			changedWarranty = true;
		}
		if (savedWarranty.getExpirationDate().compareTo(fileWarranty.getExpirationDate()) != 0) {
			savedWarranty.setWarrantyNotes(savedWarranty.getWarrantyNotes() + " Contract Ep Date Changed Original:" + savedWarranty.getExpirationDate());
			savedWarranty.setExpirationDate(fileWarranty.getExpirationDate());
			changedWarranty = true;
		}
		if (changedWarranty) {
			savedWarranty.setLastModidfiedDate(new Date());
			TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
			try {
				warrantyDAO.update(savedWarranty);
				txManager.commit(txn);
			}
			catch (Exception e) {
				if (!txn.isCompleted()) {
					txManager.rollback(txn);
				}
				logger.error("Error updating warranty contract", e);
			}
		}
		return savedWarranty;
	}
	
	public Incident getIncidentByClientIncident(String clientIncidentID, Client client) {
		try {
			return incidentDAO.findByClientIncidentID(clientIncidentID, client);
		}
		catch (Exception e) {
			logger.error("Incient#" + clientIncidentID + " for client " + client.getName() + " was not found.");
		}
		return null;
	}
	
	public boolean saveIncidentEvent(IncidentEvent incidentEvent) {
		boolean saved = false;
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			contactDAO.save(incidentEvent.getContact());
			incidentEventDAO.save(incidentEvent);
			for (IncidentPart part : incidentEvent.getIncidentParts()) {
				partDAO.save(part);
			}
			txManager.commit(txn);
			saved = true;
		}
		catch (Exception e) {
			if (!txn.isCompleted()) {
				txManager.rollback(txn);
			}
			logger.error("Error saving incident", e);
		}
		return saved;
	}

	public boolean saveCancelIncidentEvent(IncidentEvent incidentEvent) {
		boolean saved = false;
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			incidentEventDAO.save(incidentEvent);
			txManager.commit(txn);
			saved = true;
		}
		catch (Exception e) {
			if (!txn.isCompleted()) {
				txManager.rollback(txn);
			}
			logger.error("Error saving cancel incident event", e);
		}
		return saved;
	}
	public boolean saveIncident(Incident incident) {
		boolean saved = false;
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			incidentDAO.save(incident);
			txManager.commit(txn);
			saved = true;
		}
		catch (Exception e) {
			if (!txn.isCompleted()) {
				txManager.rollback(txn);
			}
			logger.error("Error saving incident", e);
		}
		return saved;
	}

	public boolean saveWarrantyContract(WarrantyContract contract) {
		boolean saved = false;
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			warrantyDAO.save(contract);
			txManager.commit(txn);
			saved = true;
		}
		catch (Exception e) {
			if (!txn.isCompleted()) {
				txManager.rollback(txn);
			}
			logger.error("Error saving incident", e);
		}
		return saved;
	}
	
	public boolean saveIncidentNote(IncidentNote incidentNote) {
		boolean saved = false;
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			incidentNoteDAO.save(incidentNote);
			txManager.commit(txn);
			saved = true;
		}
		catch (Exception e) {
			if (!txn.isCompleted()) {
				txManager.rollback(txn);
			}
			logger.error("Error saving incident note", e);
		}
		return saved;
	}
	
	public boolean saveIncidentAck(IncidentAck incidentAck) {
		boolean saved = false;
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			incidentAckDAO.save(incidentAck);
			txManager.commit(txn);
			saved = true;
		}
		catch (Exception e) {
			if (!txn.isCompleted()) {
				txManager.rollback(txn);
			}
			logger.error("Error saving incident acknowledgement", e);
		}
		return saved;
	}

	public IWarrantyContractDAO getWarrantyDAO() {
		return warrantyDAO;
	}

	public void setWarrantyDAO(IWarrantyContractDAO warrantyDAO) {
		this.warrantyDAO = warrantyDAO;
	}
	
	public IIncidentDAO getIncidentDAO() {
		return incidentDAO;
	}

	public void setIncidentDAO(IIncidentDAO incidentDAO) {
		this.incidentDAO = incidentDAO;
	}

	public IIncidentEventDAO getIncidentEventDAO() {
		return incidentEventDAO;
	}

	public void setIncidentEventDAO(IIncidentEventDAO incidentEventDAO) {
		this.incidentEventDAO = incidentEventDAO;
	}

	public JpaTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(JpaTransactionManager txManager) {
		this.txManager = txManager;
	}

	public IIncidentPartDAO getPartDAO() {
		return partDAO;
	}

	public void setPartDAO(IIncidentPartDAO partDAO) {
		this.partDAO = partDAO;
	}

	public IIncidentContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(IIncidentContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	public IIncidentNoteDAO getIncidentNoteDAO() {
		return incidentNoteDAO;
	}

	public void setIncidentNoteDAO(IIncidentNoteDAO incidentNoteDAO) {
		this.incidentNoteDAO = incidentNoteDAO;
	}

	public IIncidentAckDAO getIncidentAckDAO() {
		return incidentAckDAO;
	}

	public void setIncidentAckDAO(IIncidentAckDAO incidentAckDAO) {
		this.incidentAckDAO = incidentAckDAO;
	}

	public List<Incident> findLatestIncidentWithClientIncidentIdMatchingBeforeTestSuffix(
			Client client, List<String> clientIncidentIds, String testSuffix) {
		
		List<Incident> allIncidents = new ArrayList<Incident>(clientIncidentIds.size());
		for (String clientIncidentId : clientIncidentIds) {
			Incident incident = incidentDAO.findLatestByClientIncidentIdWithTestSuffix(client,
					clientIncidentId, testSuffix);
			if (incident != null) {
				allIncidents.add(incident);
			}
		}
		
		return allIncidents;
	}

	public List<Incident> findAllIncidents(Client client,
			List<String> clientIncidentIds) {
		return incidentDAO.findByClientIncidentIds(client, clientIncidentIds);
	}	
}
