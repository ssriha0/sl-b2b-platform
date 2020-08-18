package com.servicelive.domain.spn.network;

import java.io.Serializable;
import java.util.List;



/**
 * Class to hold exceptions types,gracePeriod values (for SL_18018)
 *
 */
public class ExclusionsVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	private List<SPNExclusionsVO> companyExclusions;
	private List<SPNExclusionsVO> resourceExclusions;
	public List<SPNExclusionsVO> getCompanyExclusions() {
		return companyExclusions;
	}
	public void setCompanyExclusions(List<SPNExclusionsVO> companyExclusions) {
		this.companyExclusions = companyExclusions;
	}
	public List<SPNExclusionsVO> getResourceExclusions() {
		return resourceExclusions;
	}
	public void setResourceExclusions(List<SPNExclusionsVO> resourceExclusions) {
		this.resourceExclusions = resourceExclusions;
	}
	
	
	
	
	
}
