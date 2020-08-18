package com.newco.marketplace.translator.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * A client may own warranty contracts on which incidents occur
 * @author gjackson
 *
 */
@MappedSuperclass
public abstract class AbstractClient {
	
	private Integer clientID;
	private String name;
	private Set<WarrantyContract> contracts = new HashSet<WarrantyContract>(0);
	
	/**
	 * Min Constructor
	 */
	public AbstractClient() {
		// intentionally blank
	}

	/**
	 * Full Constructor
	 * @param clientID
	 * @param name
	 * @param contracts
	 */
	public AbstractClient(Integer clientID, String name,
			Set<WarrantyContract> contracts) {
		super();
		this.clientID = clientID;
		this.name = name;
		this.contracts = contracts;
	}

	@Id
	@Column(name = "client_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getClientID() {
		return clientID;
	}

	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}

	@Column(name = "name", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Set<WarrantyContract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<WarrantyContract> contracts) {
		this.contracts = contracts;
	}
	
}
