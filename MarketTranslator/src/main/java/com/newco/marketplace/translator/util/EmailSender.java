package com.newco.marketplace.translator.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailSender { 
	private static Logger logger = Logger.getLogger(EmailSender.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_"  + System.getProperty("sl_app_lifecycle"));
	private static final String MAIL_HOST = resourceBundle.getString("MAIL_HOST");
	private static final String TO_ADDRESS = resourceBundle.getString("MAIL_TO"); //hssomcs@searshc.com
	private static final String FROM_ADDRESS = resourceBundle.getString("MAIL_FROM");
	//private static final String SUBJECT = resourceBundle.getString("SUBJECT");
	//private static final String MSG_BODY = resourceBundle.getString("SUBJECT");
	private static StringBuilder getFirstPart( String fileName, String errorMsg)
	{
		StringBuilder sb = new StringBuilder( "filename: \"" )
			   .append(fileName).append( "\"" )
			   .append(Constants.NEWLINE_CHAR)
			   .append( "message: \"" )
			   .append(errorMsg).append( "\"" )
			   .append(Constants.NEWLINE_CHAR)
		   	   .append(Constants.NEWLINE_CHAR);
		return sb;
	}
	private static String getSubject( String client )
	{
		return getSubject(client,false);
	}
	
	private static String getSubject( String client, boolean isMQMessage )
	{
		
		String errorDate = null;
		try {
			errorDate = sdf.format(new Date());
		} catch (Exception e) {
			errorDate = new Date().toString();
		}
		if(isMQMessage) {
			return "Error processing the " + client + " Message feed on " + errorDate;
		}
		return "Error processing the " + client + " file feed on " + errorDate;
	}
	
	private static Boolean sendMessage(StringBuilder sb, String sub, File file, String fileName) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(MAIL_HOST);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(mimeMessage, true);
			mimeHelper.setText(sb.toString(), false);
			mimeHelper.setSubject(sub);
			mimeHelper.setTo(TO_ADDRESS);
			mimeHelper.setFrom(FROM_ADDRESS);

			mimeHelper.addAttachment( fileName + ".error.dump.txt", file);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		mailSender.send(mimeMessage);
		logger.error( "Error email sent to: " + TO_ADDRESS );
		
		return Boolean.TRUE;
	}
	
	public static Boolean sendMessage(StringBuilder sb, String sub, StringBuilder attachment, String fileName, String toAddresses, String ccAddresses) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(MAIL_HOST);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(mimeMessage, true);
			mimeHelper.setText(sb.toString(), false);
			mimeHelper.setSubject(sub);
			mimeHelper.setTo(toAddresses);
			mimeHelper.setFrom(FROM_ADDRESS);
			if(ccAddresses != null){
				mimeHelper.setCc(ccAddresses);
			}
			mimeHelper.addAttachment( fileName, new ByteArrayResource(attachment.toString().getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		mailSender.send(mimeMessage);
		logger.info( sub + " email sent to " + TO_ADDRESS );
		
		return Boolean.TRUE;
	}
	
	public static boolean sendMessage(String client, String fileName, String errorMsg, Object attachment) {
		String errorFilePath = resourceBundle.getString(client + "_ERROR_FILE_PATH");
		String errorFileSuffix = resourceBundle.getString("ERROR_FILE_SUFFIX");
		return sendMessage(client, fileName, errorMsg, attachment, errorFilePath, errorFileSuffix);
	}
	
	public static boolean sendMessage(String client, String fileName, String errorMsg, Object attachment, String errorFilePath, String errorFileSuffix)
	{
		StringBuilder mailMessage = getFirstPart( fileName, errorMsg );
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append("Error Message:")
			.append(Constants.NEWLINE_CHAR)
			.append( errorMsg )
			.append(Constants.NEWLINE_CHAR)
			.append(Constants.NEWLINE_CHAR);
		}
		catch( Exception e ){
			//FIXME shouldn't this be handled?
		}
		try
		{
			sb.append("Offending Object(s):")
				.append(Constants.NEWLINE_CHAR)
				.append( attachment.toString() );
		}
		catch( Exception e ){
			//FIXME shouldn't this be handled?
		}
		if(errorFileSuffix == null || "null".equalsIgnoreCase(errorFileSuffix)) {
			errorFileSuffix = ".error";
		}
		if(fileName != null) {
			int lastSlashindex = fileName.lastIndexOf("/");
			if(lastSlashindex > -1 && ( fileName.length() > (lastSlashindex+1) )) {
				fileName =  fileName.substring(lastSlashindex+1);
			}
		}
		File file = new File( errorFilePath + fileName + errorFileSuffix + ".dump.txt" );
		FileWriter out = null;
		try {
			out = new FileWriter(file);
			out.write(sb.toString());
		} catch( Exception e ) {
			// Do nothing
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ioEx) {
				// Nothing to be done
			}
		}
		String subject = getSubject( client );
		return sendMessage( mailMessage, subject, file, fileName ).booleanValue();
	}
	
	public static boolean sendMessage( String client, String fileName, String errorMsg, Object attachmentPart1, Object attachmentPart2, boolean isMQMessage ) {
		String errorFilePath = resourceBundle.getString(client + "_ERROR_FILE_PATH");
		String errorFileSuffix = resourceBundle.getString("ERROR_FILE_SUFFIX");
		return sendMessage(client, fileName, errorMsg, attachmentPart1, attachmentPart2, isMQMessage, errorFilePath, errorFileSuffix);
	}
	
	public static boolean sendMessage( String client, String fileName, String errorMsg, Object attachmentPart1, Object attachmentPart2, boolean isMQMessage, String errorFilePath, String errorFileSuffix) 
	{
		StringBuilder mailMessage = getFirstPart( fileName, errorMsg );
		StringBuilder sb = new StringBuilder();
		try
		{
				sb.append("Error Message:")
				.append(Constants.NEWLINE_CHAR)
				.append( errorMsg )
				.append(Constants.NEWLINE_CHAR)
				.append(Constants.NEWLINE_CHAR);
		}
		catch( Exception e ){
			//FIXME shouldn't this be handled?
		}
		try
		{
			sb.append("Offending Object(s):")
				.append(Constants.NEWLINE_CHAR)
				.append( attachmentPart1.toString() );
		}
		catch( Exception e ){
			//FIXME shouldn't this be handled?
		}
		try
		{
			sb.append(Constants.NEWLINE_CHAR)
				.append(Constants.NEWLINE_CHAR)
				.append( attachmentPart2.toString() );
		}
		catch( Exception e ){
			//FIXME shouldn't this be handled?
		}
		File file = new File( errorFilePath + fileName + errorFileSuffix + ".dump.txt" );
		FileWriter out = null;
		try {
			out = new FileWriter(file);
			out.write(sb.toString());
		} catch( Exception e ) {
			// Do nothing
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ioEx) {
				// Nothing to be done
			}
		}
		String subject = getSubject( client,isMQMessage );
		return sendMessage( mailMessage, subject, file, fileName ).booleanValue();
	}
	public static boolean sendMessage( String client, String fileName, String errorMsg, Object attachmentPart1, Object attachmentPart2 ) 
	{
		return sendMessage(client,fileName,errorMsg,attachmentPart1,attachmentPart2,false);
	}
	
	public static Boolean sendMessage(StringBuilder subject, StringBuilder body) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(MAIL_HOST);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(mimeMessage, true);
			mimeHelper.setText(body.toString(), false);
			mimeHelper.setSubject(subject.toString());
			mimeHelper.setTo(TO_ADDRESS);
			mimeHelper.setFrom(FROM_ADDRESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Boolean.FALSE;
		}
		mailSender.send(mimeMessage);
		logger.info("Error email sent to: " + TO_ADDRESS);
		
		return Boolean.TRUE;
	}
	
	public static Boolean sendMessage(StringBuilder sb, String sub, StringBuilder attachment, String fileName, String toAddresses) {
		return sendMessage(sb, sub, attachment, fileName, toAddresses, null);
	}
}
