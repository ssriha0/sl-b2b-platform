package com.servicelive.spn.common.detached;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author svanloo
 *
 */
public class MemberMaintenanceServiceProviderVO extends MemberMaintenanceBaseVO {

	private static final long serialVersionUID = 20100415L;
	private int serviceProviderId;
	private List<Integer> serviceProviderIds = new ArrayList<Integer>();

	public List<Integer> getServiceProviderIds() {
		return serviceProviderIds;
	}

	public void setServiceProviderIds(List<Integer> serviceProviderIds) {
		this.serviceProviderIds = serviceProviderIds;
	}

	/**
	 * @return the serviceProviderId
	 */
	public int getServiceProviderId() {
		return serviceProviderId;
	}

	/**
	 * @param serviceProviderId the serviceProviderId to set
	 */
	public void setServiceProviderId(int serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + serviceProviderId;
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
		final MemberMaintenanceServiceProviderVO other = (MemberMaintenanceServiceProviderVO) obj;
		if (serviceProviderId != other.serviceProviderId)
			return false;
		return true;
	}
}
