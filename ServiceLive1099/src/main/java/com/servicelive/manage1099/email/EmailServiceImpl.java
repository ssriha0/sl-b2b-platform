package com.servicelive.manage1099.email;

import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.logging.Level;

import com.servicelive.manage1099.constants.EmailConstants;
import com.servicelive.manage1099.log.Log;

import sun.net.smtp.SmtpClient;

public class EmailServiceImpl implements IEmailService {

	public void sendSimpleEmail(String text) throws Exception {

		try {

			// Send email only if email notification flag is turned on in slproperties.properties file.
			if (EmailConstants.EMAIL_NOTIFICATION.equalsIgnoreCase("true")
					|| EmailConstants.EMAIL_NOTIFICATION
							.equalsIgnoreCase("yes")
					|| EmailConstants.EMAIL_NOTIFICATION.equalsIgnoreCase("on")
					|| EmailConstants.EMAIL_NOTIFICATION.equalsIgnoreCase("y")) {
				createAndSend(text);
			}
		} catch (Exception e) {
			System.out.println("Error sending email notification to following receipients ="+EmailConstants.RECIPIENT_LIST_COMMA_SEPARATED+
					"   "+ e.getMessage());
			Log.writeLog(Level.SEVERE, "Error sending email notification to following receipients ="+EmailConstants.RECIPIENT_LIST_COMMA_SEPARATED+
					"   "+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @param text
	 * @throws IOException
	 */
	private void createAndSend(String text) throws Exception {
		if (EmailConstants.RECIPIENT_LIST_COMMA_SEPARATED != null
				&& EmailConstants.RECIPIENT_LIST_COMMA_SEPARATED.length() > 1) {

			SmtpClient smtp = new SmtpClient(EmailConstants.EMAIL_SMTP);

			smtp.from(EmailConstants.SENDER_EMAIL);

			StringTokenizer st = new StringTokenizer(
					EmailConstants.RECIPIENT_LIST_COMMA_SEPARATED, ",");

			while (st.hasMoreTokens()) {
				String TO = st.nextToken().trim();
				smtp.to(TO);

			}

			PrintStream msg = smtp.startMessage();

			while (st.hasMoreTokens()) {
				String TO = st.nextToken().trim();
				msg.println("To: " + TO); // so mailers will display the To:

			}

			msg.println(EmailConstants.EMAIL_SUBJECT);
			msg.println();
			msg.println(text);
			msg.println("---");
			msg.println(EmailConstants.EMAIL_SIGNATURE);

			smtp.closeServer();

		}
	}
}
