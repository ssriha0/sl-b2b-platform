package com.servicelive.domain.spn.network;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.servicelive.domain.provider.ProviderFirm;

/**
 * 
 * @author svanloon
 *
 */
@Embeddable
public class SPNProviderFirmStatePk implements Serializable {

	private static final long serialVersionUID = 20100206L;

	@ManyToOne (fetch=FetchType.EAGER, cascade= { CascadeType.REFRESH} )
    @JoinColumn(name="spn_id", unique=false, nullable= false, insertable=false, updatable=false)
	private SPNHeader spnHeader;

	@ManyToOne
	@JoinColumn(name="provider_firm_id", unique=false, nullable= false, insertable=false, updatable=false)
	private ProviderFirm providerFirm;

	/**
	 * 
	 */
	public SPNProviderFirmStatePk() {
		super();
	}

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 */
	public SPNProviderFirmStatePk(Integer spnId, Integer providerFirmId) {
		spnHeader = new SPNHeader();
		spnHeader.setSpnId(spnId);
		
		providerFirm = new ProviderFirm();
		providerFirm.setId(providerFirmId);
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
		result = prime * result
				+ ((providerFirm == null) ? 0 : providerFirm.hashCode());
		result = prime * result
				+ ((spnHeader == null) ? 0 : spnHeader.hashCode());
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
		final SPNProviderFirmStatePk other = (SPNProviderFirmStatePk) obj;
		if (providerFirm == null) {
			if (other.providerFirm != null)
				return false;
		} else if (!providerFirm.equals(other.providerFirm))
			return false;
		if (spnHeader == null) {
			if (other.spnHeader != null)
				return false;
		} else if (!spnHeader.equals(other.spnHeader))
			return false;
		return true;
	}

}
