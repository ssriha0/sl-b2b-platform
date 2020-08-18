package com.newco.marketplace.translator.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "warranty_contract",  uniqueConstraints = {})
public class WarrantyContract extends AbstractWarrantyContract implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5306747699914395500L;

}
