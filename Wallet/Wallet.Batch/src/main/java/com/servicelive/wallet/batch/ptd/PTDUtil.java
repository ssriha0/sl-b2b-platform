package com.servicelive.wallet.batch.ptd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import com.servicelive.common.exception.SLBusinessServiceException;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDUtil.
 */
public class PTDUtil {

	/**
	 * Gets the mail body text.
	 * 
	 * @return the mail body text
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public static String getMailBodyText() throws SLBusinessServiceException {

		try {
			String str = "";
			String machineName = InetAddress.getLocalHost().getHostName() + "( " + (InetAddress.getLocalHost().getHostAddress()) + " )";
			str = "Process Name: PTD File Processor\n" + "Date : " + new Timestamp(System.currentTimeMillis()) + "\nServer Id: " + machineName + "\n" + "Details: ";

			return str;
		} catch (UnknownHostException e) {
			throw new SLBusinessServiceException(e.getMessage(),e);
		}
	}

	/**
	 * Gets the updated transactions email text.
	 * 
	 * @return the updated transactions email text
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public static String getUpdatedTransactionsEmailText() throws SLBusinessServiceException {

		try {
			String str = "";
			String machineName = InetAddress.getLocalHost().getHostName() + "( " + (InetAddress.getLocalHost().getHostAddress()) + " )";
			str =
				"Process Name: PTD File Processor\n" + "Date : " + new Timestamp(System.currentTimeMillis()) + "\nServer Id: " + machineName + "\n"
					+ "Details of Fullfillment Group Id's updated :\n\n " + "FullfillmentGroupId" + "\t" + "Action Code Id" + "\t" + "Action Code Desc" + "\n\n";

			return str;
		} catch (UnknownHostException e) {
			throw new SLBusinessServiceException(e.getMessage(),e);
		}
	}
}
