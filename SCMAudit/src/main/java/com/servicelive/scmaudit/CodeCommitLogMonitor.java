package com.servicelive.scmaudit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;
import com.servicelive.common.util.BoundedBufferedReader;


public class CodeCommitLogMonitor {
	private static final Logger logger = Logger.getLogger(CodeCommitLogMonitor.class);
	private final String LAST_REVISION_FILE = "ServiceLive.LastScannedRevision.log";
	private final String REPOSITORY_URL = "https://ushofsvpsvn1.intra.searshc.com/viewvc/service_live/";
	private final String GET_REVISION_URL = "https://ushofsvpsvn1.intra.searshc.com/viewvc/service_live?view=rev&revision=";
	private final String EMAIL_HOST = "smtp.sears.com";
	private final String FROM_ADDRESS = "SVN_Admin@searshc.com";
	private final String FROM_ADDRESS_DISPLAY = "SVN_Admin";
	private final String CC_ADDRESS = "bneumei@searshc.com";
	private final String BCC_ADDRESS = "awadhwa@searshc.com";
	private final String SUBJECT_LINE = "SVN Commit(s) Not Reviewed!";
	private final String DEBUG = System.getProperty("SCMAUDIT_DEBUG");
	private final String DEBUG_TO_ADDRESS = "awadhwa@searshc.com";
	
	private Set<String> allowableCheckinPatterns;

	private CodeCommitLogMonitor() {
		this.allowableCheckinPatterns = new HashSet<String>();
		this.allowableCheckinPatterns.add("PAIRED-ON-BY");
		this.allowableCheckinPatterns.add("PAIRED ON BY");
		this.allowableCheckinPatterns.add("PAIRED-WITH");
		this.allowableCheckinPatterns.add("PAIRED WITH");
		this.allowableCheckinPatterns.add("REVIEWED-BY");
		this.allowableCheckinPatterns.add("REVIEWED BY");
		this.allowableCheckinPatterns.add("REVIEWED-WITH");
		this.allowableCheckinPatterns.add("REVIEWED WITH");
	}

	public void execute() throws Exception {
		int latestRevision = getLatestSVNRevision();
		System.out.println("Latest Revision: " + latestRevision);

		File lastScannedRevisionFile = new File(LAST_REVISION_FILE);
		int lastScannedRevision = getLastScannedRevision(lastScannedRevisionFile, latestRevision);
		System.out.println("Last Scanned Revision: " + lastScannedRevision);

		if ((lastScannedRevision == 0) || (latestRevision == lastScannedRevision)) {
			System.out.println("No New revision for review validation!\n*** END ***");
			return;
		}

		EmailBean emailBean = scanRevisions(latestRevision, lastScannedRevision);

		if (!(emailBean.getToAddress().isEmpty())) {
			System.out.println(emailBean.getToAddress());
			sendEmail(emailBean);
		}

		updateLastScannedRevision(lastScannedRevisionFile, latestRevision);
		System.out.println("*** END ***");
	}

	private int getLatestSVNRevision() throws Exception {
		String responseStr = retrieveURLContent(REPOSITORY_URL);
		String startPattern = "(of <a href=\"/viewvc/service_live?view=rev\">";
		String endPattern = "</a>";
		int latestRevision = Integer.valueOf(getHTMLFragmentBasedOnStartEndPattern(responseStr, startPattern, endPattern)).intValue();
		return latestRevision;
	}

	private int getLastScannedRevision(File lastScannedRevisionFile, int latestRevision) throws IOException {
		
		FileOutputStream lastScannedRevisionFileOutStream=null;
	     
		BoundedBufferedReader lastScannedRevisionInStream=null;
		int lastScannedRevision=0;
		FileInputStream fis=null;
		try
		{
		
		if (!(lastScannedRevisionFile.exists())) {
			lastScannedRevisionFileOutStream = new FileOutputStream(lastScannedRevisionFile);
			lastScannedRevisionFileOutStream.write(String.valueOf(latestRevision).getBytes());
			return 0;
		}

		fis = new FileInputStream(lastScannedRevisionFile);
		lastScannedRevisionInStream = new BoundedBufferedReader(new InputStreamReader(fis));
		
		lastScannedRevision = Integer.valueOf(lastScannedRevisionInStream.readLine()).intValue();
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		finally
		{	
			try
			{
			if(lastScannedRevisionFileOutStream!=null)
			lastScannedRevisionFileOutStream.close();
			if(lastScannedRevisionInStream!=null)
			lastScannedRevisionInStream.close();
			if(fis!=null)
			fis.close();
			}
			catch(Exception e)
			{
			//logging error as this can never occur	
			logger.error("Caught inside: <CodeCommitLogMonitor::getLastScannedRevision()>:Error: Got an exception that should not occur", e);
			
			}
		}
		return lastScannedRevision;
	}

	private EmailBean scanRevisions(int latestRevision, int lastScannedRevision)
			throws Exception {
		StringBuilder emailNotification = new StringBuilder("The following SVN commits were committed without review; Please review asap and update the log message!<br><br>");
		String logStartPattern = "<pre class=\"vc_log\">";
		String logEndPattern = "</pre>";
		String authorStartPattern = "<th>Author:</th>\n<td>";
		String authorEndPattern = "</td>";
		Set<String> toAddress = new HashSet<String>();
		for (int revision = lastScannedRevision + 1; revision < latestRevision; ++revision) {
			String revisionURL = GET_REVISION_URL + revision;
			String responseStr = retrieveURLContent(revisionURL);
			String log = getHTMLFragmentBasedOnStartEndPattern(responseStr, logStartPattern, logEndPattern);
			boolean allowableLog = false;
			for (String pattern : this.allowableCheckinPatterns) {
				if (log.toUpperCase().indexOf(pattern) >= 0) {
					allowableLog = true;
					break;
				}
			}
			if (!(allowableLog)) {
				String author = getHTMLFragmentBasedOnStartEndPattern(responseStr, authorStartPattern, authorEndPattern);
				toAddress.add(author + "@searshc.com");
				emailNotification.append("<tr><td><a href=\"").append(revisionURL).append("\">").append(revision);
				emailNotification.append("</a></td><td>").append(author);
				emailNotification.append("</td><td>").append(log).append("</td></tr>");
			}
		}
		if (!toAddress.isEmpty()) {
			emailNotification.insert(0, "<style>th {width='100px'; background='gray'; color='white';}</style><table border='1' cellspacing='0'><tr><th>Revision</th><th>Author</th><th>Log Message</th></tr>");
			emailNotification.append("</table>");
		}

		EmailBean emailBean = new EmailBean(FROM_ADDRESS, toAddress, CC_ADDRESS, SUBJECT_LINE, emailNotification.toString());
		return emailBean;
	}

	private void updateLastScannedRevision(File lastScannedRevisionFile, int latestRevision) throws IOException {
		FileOutputStream lastScannedRevisionFileOutStream = new FileOutputStream(lastScannedRevisionFile);
		lastScannedRevisionFileOutStream.write(String.valueOf(latestRevision).getBytes());
		lastScannedRevisionFileOutStream.close();
	}

	private String retrieveURLContent(String url) throws IOException {
		ProtocolSocketFactory socketFactory = new EasySSLProtocolSocketFactory();
		Protocol https = new Protocol("https", socketFactory, 443);
		Protocol.registerProtocol("https", https);
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		client.executeMethod(method);
		byte[] responseBody = method.getResponseBody();
		method.releaseConnection();
		return new String(responseBody);
	}

	private String getHTMLFragmentBasedOnStartEndPattern(String html, String startPattern, String endPattern) throws Exception {
		int startIndex = html.indexOf(startPattern) + startPattern.length();
		int endIndex = html.indexOf(endPattern, startIndex);
		if ((startIndex == -1) || (endIndex == -1)) {
			System.err.println("RETRIEVED HTML IS:\n" + html);
			System.err.println("Start Pattern = " + startPattern + " End Pattern = " + endPattern + " Start Index = " + startIndex + " End Index = " + endIndex);
			throw new Exception("Collabnet HTML Output may have changed; need to change program logic!!!");
		}
		return html.substring(startIndex, endIndex);
	}

	private void sendEmail(EmailBean emailBean) throws MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", EMAIL_HOST);
		Session session = Session.getInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setContent(emailBean.getBody(), "text/html");
		message.setSubject(emailBean.getSubject());
		Address[] addresses = new Address[emailBean.getToAddress().size()];
		int i = 0;
		for (String email : emailBean.getToAddress()) {
			addresses[i] = new InternetAddress(email);
			++i;
		}

		if (DEBUG == null || Boolean.valueOf(this.DEBUG).booleanValue()) {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(DEBUG_TO_ADDRESS));
		} else {
			message.setRecipients(Message.RecipientType.TO, addresses);
			message.setRecipient(Message.RecipientType.CC, new InternetAddress(emailBean.getCcAddress()));
		}

		message.setRecipient(Message.RecipientType.BCC, new InternetAddress(BCC_ADDRESS));
		
		message.setSender(new InternetAddress(FROM_ADDRESS));
		message.setFrom(new InternetAddress(FROM_ADDRESS_DISPLAY));

		message.saveChanges();
		Transport transport = session.getTransport("smtp");
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public static void main(String[] args) throws Exception {
		
		new CodeCommitLogMonitor().execute();
	}

}
