package com.newco.marketplace.translator.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * An incident occurs related to a product under a warranty contract. It might be a broken harddrive on the product.
 * @author gjackson
 *
 */
@MappedSuperclass
public abstract class AbstractIncident {

	/**
	 * The client unique identifier for this contract - this is not unique to our system
	 */
	private String clientIncidentID;
	
	private Client client;
	
	/**
	 * Unique identifier for an incident
	 */
	private Integer incidentID;
	/**
	 * Many incidents can occur on a warranty contract
	 */
	private WarrantyContract warrantyContract;
	
	private Set<IncidentEvent> incidentEvents = new HashSet<IncidentEvent>(0);
	
	private Set<IncidentNote> incidentNotes = new HashSet<IncidentNote>(0);
	
	private Set<IncidentAck> incidentAcks = new HashSet<IncidentAck>(0);
	
	@Id
	@TableGenerator(name="tg", table="lu_last_identifier",
	pkColumnName="identifier", valueColumnName="last_value",
	allocationSize=1
	)@GeneratedValue(strategy=GenerationType.TABLE, generator="tg") 
	@Column(name = "incident_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(Integer incidentID) {
		this.incidentID = incidentID;
	}

	@Column (name = "client_incident_id", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getClientIncidentID() {
		return clientIncidentID;
	}

	public void setClientIncidentID(String clientIncidentID) {
		this.clientIncidentID = clientIncidentID;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "warranty_contract_id", unique = false, nullable = false, insertable = true, updatable = true)
	public WarrantyContract getWarrantyContract() {
		return warrantyContract;
	}

	public void setWarrantyContract(WarrantyContract warrantyContract) {
		this.warrantyContract = warrantyContract;
	}

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "incident")
	public Set<IncidentEvent> getIncidentEvents() {
		return incidentEvents;
	}

	public void setIncidentEvents(Set<IncidentEvent> incidentEvents) {
		this.incidentEvents = incidentEvents;
	}
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "incident")
	public Set<IncidentNote> getIncidentNotes() {
		return incidentNotes;
	}

	public void setIncidentNotes(Set<IncidentNote> incidentNotes) {
		this.incidentNotes = incidentNotes;
	}
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "incident")
	public Set<IncidentAck> getIncidentAcks() {
		return incidentAcks;
	}

	public void setIncidentAcks(Set<IncidentAck> incidentAcks) {
		this.incidentAcks = incidentAcks;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
