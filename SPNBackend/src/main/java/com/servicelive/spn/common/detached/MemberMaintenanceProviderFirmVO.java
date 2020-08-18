package com.servicelive.spn.common.detached;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author svanloo
 *
 */
public class MemberMaintenanceProviderFirmVO extends MemberMaintenanceBaseVO {
	private static final long serialVersionUID = 20100202L;
	private int providerFirmId;
	private List<Integer> providerFirmIdList = new ArrayList<Integer>();

	public List<Integer> getProviderFirmIdList() {
		return providerFirmIdList;
	}
	public void setProviderFirmIdList(List<Integer> providerFirmIdList) {
		this.providerFirmIdList = providerFirmIdList;
	}
	/**
	 * @return the providerFirmId
	 */
	public int getProviderFirmId() {
		return providerFirmId;
	}
	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(int providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + providerFirmId;
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
		final MemberMaintenanceProviderFirmVO other = (MemberMaintenanceProviderFirmVO) obj;
		if (providerFirmId != other.providerFirmId)
			return false;
		return true;
	}
}
