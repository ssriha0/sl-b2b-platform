package com.newco.marketplace.translator.dao;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * IncidentAck entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "incident_ack")
public class IncidentAck extends AbstractIncidentAck implements
		java.io.Serializable {

	private static final long serialVersionUID = -8208887356316056543L;
	
	// Constructors
	/** default constructor */
	public IncidentAck() {
		// intentionally blank
	}

	/** minimal constructor */
	public IncidentAck(Integer incidentAckId, Incident incident,
			Timestamp receivedDateTime, String ackFileName,
			String incidentStatus) {
		super(incidentAckId, incident, receivedDateTime, ackFileName,
				incidentStatus);
	}

	/** full constructor */
	public IncidentAck(Integer incidentAckId, Incident incident,
			Timestamp receivedDateTime, String ackFileName,
			String incidentStatus, Timestamp createdDate,
			Timestamp modifiedDate, String modifiedBy) {
		super(incidentAckId, incident, receivedDateTime, ackFileName,
				incidentStatus, createdDate, modifiedDate, modifiedBy);
	}

}
