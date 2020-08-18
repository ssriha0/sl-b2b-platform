package com.newco.marketplace.translator.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Data concerning a warranty contract. This is usually associated with an incident and represents the product 
 * that is to be serviced. A Client will own the contract.
 * @author gjackson
 *
 */
@MappedSuperclass
public abstract class AbstractWarrantyContract {

	/**
	 * A client owns the contract, not service live.
	 */
	private Client client;
	private Date contractDate;
	/**
	 * The unique identifier from the client for the contract
	 */
	private String contractNumber;
	/**
	 * Clients may pass this to help identify the type of contract
	 */
	private String contractTypeCode;
	
	private Date expirationDate;
	/**
	 * Unique identifier for the warranty contract
	 */
	private Integer warrantyContractID;
	
	private String warrantyNotes;
	
	private Date lastModidfiedDate;
	
	/**
	 * Min Constructor
	 */
	public AbstractWarrantyContract() {
		// intentionally blank
	}
	
	/**
	 * Full Constructor
	 * @param client
	 * @param contractDate
	 * @param contractNumber
	 * @param contractTypeCode
	 * @param warrentyContractID
	 */
	public AbstractWarrantyContract(Client client, Date contractDate,
			String contractNumber, String contractTypeCode,
			Integer warrantyContractID, Date expirationDate) {
		super();
		this.client = client;
		this.contractDate = contractDate;
		this.contractNumber = contractNumber;
		this.contractTypeCode = contractTypeCode;
		this.warrantyContractID = warrantyContractID;
		this.expirationDate = expirationDate;
	}


	@Id
	@TableGenerator(name="tg", table="lu_last_identifier",
	pkColumnName="identifier", valueColumnName="last_value",
	allocationSize=1
	)@GeneratedValue(strategy=GenerationType.TABLE, generator="tg") 
	@Column(name = "warranty_contract_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getWarrantyContractID() {
		return warrantyContractID;
	}

	public void setWarrantyContractID(Integer warrantyContractID) {
		this.warrantyContractID = warrantyContractID;
	}

	@Column(name = "contract_number", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	@Temporal (TemporalType.DATE)
	@Column(name = "contract_date", unique = false, nullable = true, insertable = true, updatable = true)
	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	@Column(name = "contract_type_code", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getContractTypeCode() {
		return contractTypeCode;
	}

	public void setContractTypeCode(String contractTypeCode) {
		this.contractTypeCode = contractTypeCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	@Temporal (TemporalType.TIMESTAMP)
	@Column(name = "expiration_date", unique = false, nullable = true, insertable = true, updatable = true)
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name = "warranty_notes", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getWarrantyNotes() {
		return warrantyNotes;
	}

	public void setWarrantyNotes(String warrantyNotes) {
		this.warrantyNotes = warrantyNotes;
	}

	@Temporal (TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date", unique = false, nullable = true, insertable = true, updatable = true)
	public Date getLastModidfiedDate() {
		return lastModidfiedDate;
	}

	public void setLastModidfiedDate(Date lastModidfiedDate) {
		this.lastModidfiedDate = lastModidfiedDate;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
