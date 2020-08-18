package com.servicelive.tokenization.email;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;

import sun.net.smtp.SmtpClient;

import com.servicelive.tokenization.constants.TokenizationConstants;
import com.servicelive.tokenization.log.Log;

/**
 * @author Infosys : Jun, 2014
 */
public class EmailUtil {

	public static void sendEmail(String loggedUser) throws Exception {

		try {
			// Send only if email notification flag is turned on
			if (TokenizationConstants.EMAIL_NOTIFICATION
					.equalsIgnoreCase("true")
					|| TokenizationConstants.EMAIL_NOTIFICATION
							.equalsIgnoreCase("yes")
					|| TokenizationConstants.EMAIL_NOTIFICATION
							.equalsIgnoreCase("on")
					|| TokenizationConstants.EMAIL_NOTIFICATION
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
							+ TokenizationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED
							+ " [" + e.getMessage() + "]");
			throw new Exception(
					"Error sending email notification to following receipients ="
							+ TokenizationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED
							+ " " + e.getMessage());
		}

	}

	/**
	 * @param text
	 * @throws Exception
	 */
	private static void createAndSendEmail(String loggedUser) throws Exception {
		if (TokenizationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED != null
				&& TokenizationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED
						.length() >= 1) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					TokenizationConstants.DATE_FORMAT);
			String dateAsString = dateFormat.format(new Date());

			SmtpClient smtp = new SmtpClient(TokenizationConstants.EMAIL_SMTP);
			smtp.from(TokenizationConstants.EMAIL_SENDER);
			StringTokenizer st = new StringTokenizer(
					TokenizationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED,
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
			msg.println(TokenizationConstants.EMAIL_SUBJECT);
			msg.println();
			// add email content
			msg.println(TokenizationConstants.EMAIL_CONTENT);
			if (null != loggedUser) {
				StringBuilder msgDetails = new StringBuilder(
						TokenizationConstants.EMAIL_DETAIL_1);
				msgDetails.append(dateAsString);
				msgDetails.append(TokenizationConstants.EMAIL_DETAIL_2);
				msgDetails.append(loggedUser);

				msg.println(msgDetails.toString());
			}
			msg.println("\n\n----------");
			// add email signature
			msg.println(TokenizationConstants.EMAIL_SIGNATURE);

			smtp.closeServer();
		} else {
			Log.writeLog(Level.SEVERE,
					"Email could not be sent as no receipents available!");
		}
	}
}
