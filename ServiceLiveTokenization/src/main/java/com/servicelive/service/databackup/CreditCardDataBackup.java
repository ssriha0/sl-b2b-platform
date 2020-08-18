package com.servicelive.service.databackup;

import java.sql.SQLException;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.db.SLTokenizationDAO;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys, Jun, 2014 BackUp Data from so_additional_payment to
 *         so_additional_payment_tmp
 * 
 */
public class CreditCardDataBackup implements Runnable {

	private Connection connectionSchemaSupplierProd;

	public CreditCardDataBackup(Connection connectionSchemaSupplierProd) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
	}

	public void run() {
		// Call DB procedure

		accountHdrCreditCardData();
		soAdditionalPaymentCreditCardData();
		omsBuyerNotificationCreditCardData();
		shcUpsellPaymentCreditCardData();


	}

	public void accountHdrCreditCardData(){

		try {
			SLTokenizationDAO dao = new SLTokenizationDAO();
			Log.writeLog(Level.INFO,
					"Inside AccountHdrCreditCardData Backup ...");
			dao.backUpAccountHdrCreditCardData(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,
					"Exiting AccountHdrCreditCardData Backup ...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting AccountHdrCreditCardData Backup  with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	public void soAdditionalPaymentCreditCardData(){
		// Call DB procedure
		try {
			SLTokenizationDAO dao = new SLTokenizationDAO();

			Log.writeLog(Level.INFO,
					"Inside SOAdditionalPaymentData Backup ...");
			dao.backUpSoAdditionalPaymentCreditCardData(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,
					"Exiting SOAdditionalPaymentData Backup ...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SOAdditionalPaymentDataBackup  with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	public void omsBuyerNotificationCreditCardData(){
		// Call DB procedure
		try {
			SLTokenizationDAO dao = new SLTokenizationDAO();
			Log.writeLog(Level.INFO,
					"Inside OMSBuyerNotificationsDataBackup ...");
			dao.backUpOMSBuyerNotificationsCreditCardData(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,
					"Exiting OMSBuyerNotificationsDataBackup ...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting OMSBuyerNotificationsDataBackup with exception..."
							+ e);
			e.printStackTrace();
		}

	}
	public void shcUpsellPaymentCreditCardData(){
		// Call DB procedure
		try {
			SLTokenizationDAO dao = new SLTokenizationDAO();
			Log.writeLog(Level.INFO,
					"Inside SHCUpSellPaymentDataBackup ...");
			dao.backUpSHCUpSellPaymentCreditCardData(connectionSchemaSupplierProd);
			Log.writeLog(Level.INFO,
					"Exiting SHCUpSellPaymentDataBackup ...");
		} catch (SQLException e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SHCUpSellPaymentDataBackup with exception..."
							+ e);
			e.printStackTrace();
		}

	}


}
