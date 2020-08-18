package com.servicelive.keyrotation.email;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;

import sun.net.smtp.SmtpClient;

import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class EmailUtil {

	public static void sendEmail(String loggedUser) throws Exception {

		try {
			// Send only if email notification flag is turned on
			if (KeyRotationConstants.EMAIL_NOTIFICATION
					.equalsIgnoreCase("true")
					|| KeyRotationConstants.EMAIL_NOTIFICATION
							.equalsIgnoreCase("yes")
					|| KeyRotationConstants.EMAIL_NOTIFICATION
							.equalsIgnoreCase("on")
					|| KeyRotationConstants.EMAIL_NOTIFICATION
							.equalsIgnoreCase("y")) {
				createAndSendEmail(loggedUser);
				Log.writeLog(Level.INFO, "Email send successfully!!!!!");
			} else {
				Log.writeLog(Level.INFO, "Email notification flag is OFF");
			}
		} catch (Exception e) {
			Log.writeLog(
					Level.SEVERE,
					"Error sending email notification to following receipients ="
							+ KeyRotationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED
							+ " [" + e.getMessage() + "]");
			throw new Exception(
					"Error sending email notification to following receipients ="
							+ KeyRotationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED
							+ " " + e.getMessage());
		}

	}

	/**
	 * @param text
	 * @throws Exception
	 */
	private static void createAndSendEmail(String loggedUser) throws Exception {
		if (KeyRotationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED != null
				&& KeyRotationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED
						.length() >= 1) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					KeyRotationConstants.DATE_FORMAT);
			String dateAsString = dateFormat.format(new Date());

			SmtpClient smtp = new SmtpClient(KeyRotationConstants.EMAIL_SMTP);
			smtp.from(KeyRotationConstants.EMAIL_SENDER);
			StringTokenizer st = new StringTokenizer(
					KeyRotationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED,
					",");

			while (st.hasMoreTokens()) {
				String TO = st.nextToken().trim();
				smtp.to(TO);
			}

			PrintStream msg = smtp.startMessage();

			while (st.hasMoreTokens()) {
				String TO = st.nextToken().trim();
				msg.println("To: " + TO); // so mailers will display the To:
			}

			// add subject
			msg.println(KeyRotationConstants.EMAIL_SUBJECT);
			msg.println();
			// add email content
			msg.println(KeyRotationConstants.EMAIL_CONTENT);
			if (null != loggedUser) {
				StringBuilder msgDetails = new StringBuilder(
						KeyRotationConstants.EMAIL_DETAIL_1);
				msgDetails.append(dateAsString);
				msgDetails.append(KeyRotationConstants.EMAIL_DETAIL_2);
				msgDetails.append(loggedUser);

				msg.println(msgDetails.toString());
			}
			msg.println("\n\n----------");
			// add email signature
			msg.println(KeyRotationConstants.EMAIL_SIGNATURE);

			smtp.closeServer();
		} else {
			Log.writeLog(Level.SEVERE,
					"Email could not be sent as no receipents available!");
		}
	}
}
