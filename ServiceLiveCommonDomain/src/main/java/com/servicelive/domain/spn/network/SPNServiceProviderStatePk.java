package com.servicelive.domain.spn.network;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.servicelive.domain.provider.ServiceProvider;

/**
 * 
 * @author svanloon
 *
 */
@Embeddable
public class SPNServiceProviderStatePk implements Serializable {

	private static final long serialVersionUID = 20100206L;

	@ManyToOne
    @JoinColumn(name="spn_id", unique=false, nullable= false, insertable=false, updatable=false)
	private SPNHeader spnHeader;

	@ManyToOne
	@JoinColumn(name="service_provider_id", unique=false, nullable= false, insertable=false, updatable=false)
	private ServiceProvider serviceProvider;

	/**
	 * 
	 */
	public SPNServiceProviderStatePk() {
		super();
	}

	/**
	 * 
	 * @param spnId
	 * @param serviceProviderId
	 */
	public SPNServiceProviderStatePk(Integer spnId, Integer serviceProviderId) {
		super();
		this.spnHeader = new SPNHeader();
		this.spnHeader.setSpnId(spnId);
		this.serviceProvider = new ServiceProvider();
		this.serviceProvider.setId(serviceProviderId);
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
	 * @return the serviceProvider
	 */
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serviceProvider == null) ? 0 : serviceProvider.hashCode());
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
		final SPNServiceProviderStatePk other = (SPNServiceProviderStatePk) obj;
		if (serviceProvider == null) {
			if (other.serviceProvider != null)
				return false;
		} else if (!serviceProvider.equals(other.serviceProvider))
			return false;
		if (spnHeader == null) {
			if (other.spnHeader != null)
				return false;
		} else if (!spnHeader.equals(other.spnHeader))
			return false;
		return true;
	}

}
