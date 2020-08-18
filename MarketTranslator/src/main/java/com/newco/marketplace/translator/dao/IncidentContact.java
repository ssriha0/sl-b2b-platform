package com.newco.marketplace.translator.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "incident_contact",  uniqueConstraints = {})
public class IncidentContact extends AbstractIncidentContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378477832088222259L;
	

}
