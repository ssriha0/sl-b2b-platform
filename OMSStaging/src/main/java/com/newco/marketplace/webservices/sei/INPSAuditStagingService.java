package com.newco.marketplace.webservices.sei;

import com.newco.marketplace.webservices.dao.NpsAuditFiles;

public interface INPSAuditStagingService {
	
	public Object retrieveAuditMessageInfo(String message, String npsStatus) throws Exception;
	
	public void stageAuditOrdersInFile(NpsAuditFiles ordersInFiles)throws Exception;

}
