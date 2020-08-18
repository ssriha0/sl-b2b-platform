package com.servicelive.domain.spn.network;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.servicelive.domain.provider.ProviderFirm;

/**
 * 
 * @author svanloon
 *
 */
public class SPNMeetAndGreetStatePk implements Serializable {

	private static final long serialVersionUID = 20090128L;

	/**
	 * 
	 */
	public SPNMeetAndGreetStatePk() {
		super();
	}

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 */
	public SPNMeetAndGreetStatePk(Integer spnId, Integer providerFirmId) {
		super();

		ProviderFirm providerFirm1 = new ProviderFirm();
		providerFirm1.setId(providerFirmId);
		this.setProviderFirm(providerFirm1);

		SPNHeader spnHeader1 = new SPNHeader();
		spnHeader1.setSpnId(spnId);
		this.setSpnHeader(spnHeader1);
	}

	@ManyToOne
    @JoinColumn(name="spn_id", unique=false, nullable= false, insertable=false, updatable=false)
	private SPNHeader spnHeader;

	@ManyToOne
	@JoinColumn(name="prov_firm_id", unique=false, nullable= false, insertable=false, updatable=false)
	private ProviderFirm providerFirm;

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
		final SPNMeetAndGreetStatePk other = (SPNMeetAndGreetStatePk) obj;
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
