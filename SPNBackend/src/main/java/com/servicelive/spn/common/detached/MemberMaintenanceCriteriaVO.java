/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;

/**
 * @author hoza
 *
 */
public class MemberMaintenanceCriteriaVO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4317433894853677125L;
	
	private Integer spnId;
	private Integer providerFirmId;
	private Integer criteriaId;
	
	
	
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the providerFirmId
	 */
	public Integer getProviderFirmId() {
		return providerFirmId;
	}
	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	

}
