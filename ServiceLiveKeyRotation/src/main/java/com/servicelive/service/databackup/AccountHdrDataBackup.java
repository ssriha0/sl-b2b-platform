package com.servicelive.service.databackup;

import java.sql.SQLException;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.keyrotation.db.SLKeyRotationDAO;
import com.servicelive.keyrotation.log.Log;

/**
 * @author Infosys: Jun, 2014 BackUp Data from account_hdr to account_hdr_tmp
 * 
 */
public class AccountHdrDataBackup implements Runnable {

	private Connection connectionSchemaAccountsProd;

	public AccountHdrDataBackup(Connection connectionSchemaAccountsProd) {
		this.connectionSchemaAccountsProd = connectionSchemaAccountsProd;
	}

	public void run() {
		// Call DB procedure
		Log.writeLog(Level.INFO, "Inside AccountHdrDataBackup thread...");
		SLKeyRotationDAO dao = new SLKeyRotationDAO();
		try {
			dao.backUpAccountHdr(connectionSchemaAccountsProd);
			Log.writeLog(Level.INFO, "Exiting AccountHdrDataBackup thread...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting AccountHdrDataBackup thread with exception..." + e);
			e.printStackTrace();
		}

	}

}
