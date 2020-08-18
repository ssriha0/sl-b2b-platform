package com.newco.marketplace.translator.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table (name = "incident", uniqueConstraints = {@UniqueConstraint(columnNames={"client_id", "client_incident_id"})})
public class Incident extends AbstractIncident implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245585051112277748L;
	
}
