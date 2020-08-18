package com.servicelive.service.databackup;

import java.sql.SQLException;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.db.SLTokenizationDAO;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys: Jun, 2014 BackUp Data from oms_buyer_notifications to
 *         oms_buyer_notifications_tmp
 * 
 */
public class OmsBuyerNotificationDataBackup implements Runnable {

	private Connection connectionSchemaIntegration;

	public OmsBuyerNotificationDataBackup(Connection connectionSchemaIntegration) {
		this.connectionSchemaIntegration = connectionSchemaIntegration;
	}

	public void run() {
		// Call DB procedure
		SLTokenizationDAO dao = new SLTokenizationDAO();
		try {
			Log.writeLog(Level.INFO,
					"Inside OmsBuyerNotificationDataBackup thread...");
			dao.backUpOmsBuyerNotification(connectionSchemaIntegration);
			Log.writeLog(Level.INFO,
					"Exiting OmsBuyerNotificationDataBackup thread...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting OmsBuyerNotificationDataBackup thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

}
