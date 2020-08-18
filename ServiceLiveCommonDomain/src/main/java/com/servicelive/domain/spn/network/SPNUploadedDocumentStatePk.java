package com.servicelive.domain.spn.network;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.common.Document;
import com.servicelive.domain.provider.ProviderFirm;

/**
 * 
 * @author svanloo
 *
 */
@Embeddable
public class SPNUploadedDocumentStatePk implements Serializable {

	private static final long serialVersionUID = 20090114L;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "spn_doc_id", nullable = false, insertable = true, updatable = true)
	private Document buyerDocument;
	
	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "prov_firm_upld_doc_id", nullable = false, insertable = true, updatable = true)
	private Document providerFirmDocument;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "buyer_id", nullable = false, insertable = true, updatable = true)
	private Buyer buyer;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "prov_firm_id", nullable = false, insertable = true, updatable = true)
	private ProviderFirm providerFirm;

	/**
	 * @return the buyerDocument
	 */
	public Document getBuyerDocument() {
		return buyerDocument;
	}

	/**
	 * @param buyerDocument the buyerDocument to set
	 */
	public void setBuyerDocument(Document buyerDocument) {
		this.buyerDocument = buyerDocument;
	}

	/**
	 * @return the providerFirmDocument
	 */
	public Document getProviderFirmDocument() {
		return providerFirmDocument;
	}

	/**
	 * @param providerFirmDocument the providerFirmDocument to set
	 */
	public void setProviderFirmDocument(Document providerFirmDocument) {
		this.providerFirmDocument = providerFirmDocument;
	}

	/**
	 * @return the buyer
	 */
	public Buyer getBuyer() {
		return buyer;
	}

	/**
	 * @param buyer the buyer to set
	 */
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		result = prime * result
				+ ((buyerDocument == null) ? 0 : buyerDocument.hashCode());
		result = prime * result
				+ ((providerFirm == null) ? 0 : providerFirm.hashCode());
		result = prime
				* result
				+ ((providerFirmDocument == null) ? 0 : providerFirmDocument
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SPNUploadedDocumentStatePk other = (SPNUploadedDocumentStatePk) obj;
		if (buyer == null) {
			if (other.buyer != null)
				return false;
		} else if (!buyer.equals(other.buyer))
			return false;
		if (buyerDocument == null) {
			if (other.buyerDocument != null)
				return false;
		} else if (!buyerDocument.equals(other.buyerDocument))
			return false;
		if (providerFirm == null) {
			if (other.providerFirm != null)
				return false;
		} else if (!providerFirm.equals(other.providerFirm))
			return false;
		if (providerFirmDocument == null) {
			if (other.providerFirmDocument != null)
				return false;
		} else if (!providerFirmDocument.equals(other.providerFirmDocument))
			return false;
		return true;
	}
}
