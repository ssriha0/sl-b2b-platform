package com.newco.marketplace.web.delegates;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.incident.IncidentResponseVO;
import com.newco.marketplace.exception.BusinessServiceException;

public interface IAssurantIncidentEventTrackerDelegate {
	
	public void notifyBuyer(IncidentResponseVO incidentResponseVO, String clientIncidentID, 
			SecurityContext securityContext) throws BusinessServiceException;
}
