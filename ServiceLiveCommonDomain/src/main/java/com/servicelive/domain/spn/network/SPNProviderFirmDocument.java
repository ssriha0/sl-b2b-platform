package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.common.Document;
import com.servicelive.domain.provider.ProviderFirm;


/**
 * 
 * @author svanloo
 *
 */
@Entity
@Table(name="spnet_provider_firm_document")
public class SPNProviderFirmDocument extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090114L;

	@Id @GeneratedValue (strategy=IDENTITY)
	@Column (name = "id", unique = true, nullable = false, insertable = true, updatable = true)
    private Integer id;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "spn_id", nullable = false, insertable = true, updatable = true)
    private SPNHeader spnHeader;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "prov_firm_upld_doc_id", nullable = false, insertable = true, updatable = true)
    private Document providerFirmUploadedDocument;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "prov_firm_id", nullable = false, insertable = true, updatable = true)
    private ProviderFirm providerFirm;
    
    @Column(name="deleted_ind")
    private Boolean deletedInd;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the spnHeader
	 */
	public SPNHeader getSpnHeader() {
		return spnHeader;
	}

	/**
	 * @param spnHeader the spnHeader to set
	 */
	public void setSpnHeader(SPNHeader spnHeader) {
		this.spnHeader = spnHeader;
	}

	/**
	 * @return the providerFirmUploadedDocument
	 */
	public Document getProviderFirmUploadedDocument() {
		return providerFirmUploadedDocument;
	}

	/**
	 * @param providerFirmUploadedDocument the providerFirmUploadedDocument to set
	 */
	public void setProviderFirmUploadedDocument(
			Document providerFirmUploadedDocument) {
		this.providerFirmUploadedDocument = providerFirmUploadedDocument;
	}

	/**
	 * @return the providerFirm
	 */
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}

	/**
	 * @param providerFirm the providerFirm to set
	 */
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}

	/**
	 * @return the deletedInd
	 */
	public Boolean getDeletedInd() {
		return deletedInd;
	}

	/**
	 * @param deletedInd the deletedInd to set
	 */
	public void setDeletedInd(Boolean deletedInd) {
		this.deletedInd = deletedInd;
	}
}
