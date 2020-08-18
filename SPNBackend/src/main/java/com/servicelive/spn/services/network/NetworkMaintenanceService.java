/**
 * 
 */
package com.servicelive.spn.services.network;

import java.sql.SQLException;

import com.servicelive.spn.services.BaseServices;

/**
 * @author hoza
 *
 */
public class NetworkMaintenanceService extends BaseServices {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		// do nothing

	}
	/**
	 * This method is the entry point for Network maintenance.
	 * This service will be reponsible for updating the status of the network from INCOMPLETE  - DOC EDITED , EDITED to COMPLETE.
	 * Since we do not track the  alias network's status this service will ignore those networks 
	 */
	public void execute() throws SQLException{
			int spnCompletedCount = getSqlMapClient().update("network.networkmaintenance.updateNetworkSPNCOMPLETE", null);
			int spnINCompletedCount = getSqlMapClient().update("network.networkmaintenance.updateNetworkSPN_INCOMPLETE", null);
			logger.info("Network Maintenance job has updated " + spnCompletedCount + " to COMPLETE and "+ spnINCompletedCount + " to INCOMPLETE status");
	}

}
