/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Apr-2010	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.servicelive.campaigns.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axis2.Constants;
import org.apache.log4j.Logger;

import responsys.ws57.AppendDataSource;
import responsys.ws57.AppendDataSourceResponse;
import responsys.ws57.CharacterEncoding;
import responsys.ws57.CheckResult;
import responsys.ws57.DataSourceProperties;
import responsys.ws57.DownloadDataSourceByTimestamp;
import responsys.ws57.DownloadDataSourceByTimestampResponse;
import responsys.ws57.Field;
import responsys.ws57.FieldMap;
import responsys.ws57.FieldType;
import responsys.ws57.IntermediateResult;
import responsys.ws57.RecordCount;
import responsys.ws57.UpdateDataSource;
import responsys.ws57.UpdateDataSourceResponse;
import responsys.ws57.client.RIFault;


/**
 * This class is for populating data in Responsys datasource.
 * 
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResponsysDataSourceServiceImpl extends BaseService{
		
	private static final Logger logger = Logger.getLogger(ResponsysDataSourceServiceImpl.class.getName());
		
	public void updateRemoteDataSource(List<HashMap<String, String>> dataList, String folder, String dataSource, String[] params, String tmpFileName, String matchingCol) {
		dataSource = getDecoratedCampaignName(dataSource);
		writeToFile(dataList, params, tmpFileName);
		try {
		updateDataSource(folder, dataSource, params, tmpFileName, matchingCol);
		}catch (Exception e) {
			logger.error("DataSourceServiceImpl-->sendToRemoteDataSource-->EXCEPTION-->",e);
		}
	}
	
	public void appendRemoteDataSource(List<HashMap<String, String>> dataList, String folder, String dataSource, String[] params, String tmpFileName) throws Exception {
		dataSource = getDecoratedCampaignName(dataSource);
		writeToFile(dataList, params, tmpFileName);
		try {
		appendDataSource(folder, dataSource, params, tmpFileName);
		}catch (Exception e) {
			logger.error("Exception in DataSourceServiceImpl-->sendToRemoteDataSource : ",e);
			throw e;
		}
	}
	
	/**
	 * This method writes data to a csv file to be populated into the Responsys datasource
	 * @param List<HashMap<String, String>> dataList
	 * @param String[] parameters
	 * @return void
	 */
	private void writeToFile(List<HashMap<String, String>> listMap,	String[] parameters, String tmpFileName) {
		try {

			FileWriter writer = new FileWriter(getTmpDSLocation()+ tmpFileName+".csv");
			int size = listMap.size();
			int count = 0;
			for (String header : parameters) {

				writer.write(header);
				count = count + 1;
				if (count != parameters.length) {
					writer.write(",");
				}
			}
			writer.append('\n');
			for (int i = 0; i < size; i++) {
				Map<String, String> baseMap = listMap.get(i);
				for (int j = 0; j < parameters.length; j++) {
					String param = parameters[j];
					Object val=baseMap.get(param);
					//String value = (String)baseMap.get(param);
					if(val!=null){
						String value=val.toString();
						value=value.replaceAll(","," ");
						writer.write(value);
					}else{
						writer.write("");
					}
					if (j != parameters.length - 1) {
						writer.write(",");
					}

				}
				writer.append('\n');
			}
			writer.close();
		} catch (Exception e) {
			logger.error("Exception in DataSourceServiceImpl-->writeToFile : ",e);
		}

	}

	/**
	 * This method appends data to the given Responsys datasource
	 * @param String folderName
	 * @param String tableName
	 * @param String[] parameters
	 * @return boolean
	 */
	private boolean appendDataSource(String folderName, String tableName, String[] parameters, String tmpFileName) throws RIFault, RemoteException,
			InterruptedException,Exception {

		logger.info("Appending data source...");
		try {
			
			if(!loginWS()){
				logger.error("Log-in to Responsys failed");
				return false;
			}
			Field[] fields = new Field[parameters.length];
			FieldMap[] fieldMap = getFieldMap(fields, parameters);

			DataSourceProperties dataSourceProperties = getDataSourceProperties(fields, folderName, tableName, tmpFileName);

			File fileObj = new File(getTmpDSLocation()+ tmpFileName+".csv");
			FileDataSource fds = new FileDataSource(fileObj);
			DataHandler dataHandler = new DataHandler(fds);
			dataSourceProperties.setCsvFileData(dataHandler);
			
			AppendDataSource appendDataSource = new AppendDataSource();
			appendDataSource.setProperties(dataSourceProperties);
			appendDataSource.setMapping(fieldMap);

			AppendDataSourceResponse appendSourceResponse = getStubWS().appendDataSource(appendDataSource, getSessionHeaderWS());
			
			RecordCount dataSrcResult = null;
			IntermediateResult irws = null;
			CheckResult checkResult = new CheckResult();
			int retry = 0;
			while (true) {
				dataSrcResult = appendSourceResponse.getResult();
				checkResult.setIntermediateResultKey(dataSrcResult.getKey());
				irws = getStubWS().checkResult(checkResult, getSessionHeaderWS()).getResult();
				if (irws.getFinished() || retry>=POLLING_RETRY_LIMIT) {
					logger.info("Finished");
					if (irws.getComplete()) {
						logger.info("Record Count: " + ((RecordCount) irws).getCount());
						if(!fileObj.delete()){
							logger.debug("File could not be deleted: " + fileObj.getPath());
						}
						break;
					}
				}
				logger.info("Sleep for bit ...");				
				Thread.sleep(1000);
				retry++;
			}
			logger.info("Completed table update");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

		logger.info("Logging out of Responsys");
		logoutWS();
		
		return true;
	}


	private boolean updateDataSource(String folderName, String tableName, String[] parameters, String tmpFileName, String matchingCol)
			throws RIFault, RemoteException, InterruptedException {

		logger.info("About to update data source...");
		try {
			
			if(!loginWS()){
				logger.error("Log-in to Responsys failed");
				return false;
			}
			Field[] fields = new Field[parameters.length];
			FieldMap[] fieldMap = getFieldMap(fields, parameters);

			DataSourceProperties dataSourceProperties = getDataSourceProperties(fields, folderName, tableName, tmpFileName);

			File fileObj = new File(getTmpDSLocation()+ tmpFileName+".csv");
			FileDataSource fds = new FileDataSource(fileObj);
			DataHandler dataHandler = new DataHandler(fds);
			dataSourceProperties.setCsvFileData(dataHandler);
			
			UpdateDataSource updateDataSource = new UpdateDataSource();
			updateDataSource.setProperties(dataSourceProperties);
			updateDataSource.setMapping(fieldMap);
			updateDataSource.setMatchColumnName(matchingCol);
			UpdateDataSourceResponse updateSourceResponse = getStubWS().updateDataSource(updateDataSource, getSessionHeaderWS());

			RecordCount dataSrcResult = null;
			IntermediateResult irws = null;
			CheckResult checkResult = new CheckResult();
			int retry = 0;
			while (true) {
				dataSrcResult = updateSourceResponse.getResult();
				checkResult.setIntermediateResultKey(dataSrcResult.getKey());
				irws = getStubWS().checkResult(checkResult, getSessionHeaderWS()).getResult();
				if (irws.getFinished() || retry>=POLLING_RETRY_LIMIT) {
					logger.info("Finished");
					if (irws.getComplete()) {
						logger.info("Record Count: " + ((RecordCount) irws).getCount());
						if(!fileObj.delete()){
							logger.debug("File could not be deleted: " + fileObj.getPath());
						}
						break;
					}
				}
				logger.info("Sleep for bit ...");
				Thread.sleep(1000);
				retry++;
			}
			logger.info("Completed table update");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("Logging out of Responsys");
		logoutWS();

		return true;
	}
	
	@SuppressWarnings("deprecation")
	public List<HashMap<String, String>> downloadDataSource(String folderName, String dataSourceName, String matchingColName, String matchingColValue, String tmpFileName){
		try
		{	if(!loginWS()){
				logger.error("Log-in to Responsys failed");
				return null;
			}
			
			dataSourceName = getDecoratedCampaignName(dataSourceName);
			boolean result = false;
			String delimitedBy = DELIMITER;
			CharacterEncoding characterSet = CharacterEncoding.UTF_8;
			String delimiterString = "";
			String enclosedBy =	ENCLOSING_SYMBOL;
			String timestampFieldName = TIMESTAMP_COLUMN_NAME;
			String afterTime = START_TIME;
			Timestamp now = new Timestamp(new Date(System.currentTimeMillis()).getTime());
			SimpleDateFormat beforesdf1 = new SimpleDateFormat(DATE_FORMAT);
			String beforeTime = beforesdf1.format(now);			
			String equalsFieldName = matchingColName;
			String equalsFieldValue = matchingColValue;
			boolean compressFile = false;
			Calendar timestampFieldAfterTime = null;
			if (!(afterTime == null || afterTime.trim().length() == 0))
			{
					SimpleDateFormat afterSdf = new SimpleDateFormat(DATE_FORMAT);
					afterSdf.setLenient(true);
					Date afterDate = afterSdf.parse(afterTime);
					timestampFieldAfterTime = Calendar.getInstance();
					timestampFieldAfterTime.setTime(afterDate);
		}
		Calendar timestampFieldBeforeTime = null;
		if (!(beforeTime == null || beforeTime.trim().length() == 0))
		{
				SimpleDateFormat beforesdf = new SimpleDateFormat(DATE_FORMAT);
				beforesdf.setLenient(true);
				Date beforeDate = beforesdf.parse(beforeTime);
				timestampFieldBeforeTime = Calendar.getInstance();
				timestampFieldBeforeTime.setTime(beforeDate);
		}
	
		DownloadDataSourceByTimestamp downloadDataSourceByTimestamp = new DownloadDataSourceByTimestamp();
		downloadDataSourceByTimestamp.setFolderName(folderName);
		downloadDataSourceByTimestamp.setDataSourceName(dataSourceName);
		downloadDataSourceByTimestamp.setTimestampFieldName(timestampFieldName);
		downloadDataSourceByTimestamp.setTimestampFieldAfterValue(timestampFieldAfterTime);
		downloadDataSourceByTimestamp.setTimestampFieldBeforeValue(timestampFieldBeforeTime);
		downloadDataSourceByTimestamp.setEqualsFieldName(equalsFieldName);
		downloadDataSourceByTimestamp.setEqualsFieldValue(equalsFieldValue);
		downloadDataSourceByTimestamp.setDelimitedBy(delimitedBy);
		downloadDataSourceByTimestamp.setDelimiterString(delimiterString);
		downloadDataSourceByTimestamp.setEnclosedBy(enclosedBy);
		downloadDataSourceByTimestamp.setCharacterEncoding(characterSet);
		downloadDataSourceByTimestamp.setCompressFile(compressFile);
		downloadDataSourceByTimestamp.setDownloadFileName(tmpFileName+".csv");
		getStubWS()._getServiceClient().getOptions().setProperty(org.apache.axis2.Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
	
		getStubWS()._getServiceClient().getOptions().setProperty(Constants.Configuration.CACHE_ATTACHMENTS, Constants.VALUE_TRUE);
		getStubWS()._getServiceClient().getOptions().setProperty(Constants.Configuration.ATTACHMENT_TEMP_DIR,"/usr/local/responsys/logs/webservices/attachments");	
		getStubWS()._getServiceClient().getOptions().setProperty(Constants.Configuration.FILE_SIZE_THRESHOLD, "4000");
		
		DownloadDataSourceByTimestampResponse response = getStubWS().downloadDataSourceByTimestamp(downloadDataSourceByTimestamp, getSessionHeaderWS() );
		DataHandler datahandler = response.getResult();
		
		if (datahandler != null){
			InputStream is = datahandler.getInputStream();
		
			if (is != null)	{	
				// write the files to the disk	
				File file = new File(getTmpDSLocation()+ tmpFileName+".csv");			
				writeToFile(is, file);
			}
	
			result = true;	
		}
		
		}
		catch (Exception ex){	
			logger.error("Error while downloading datasource: " + ex.getMessage());
			logger.error("Error stack trace: " + ex.getStackTrace());
			return null;
		}
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			
			File file = new File(getTmpDSLocation()+ tmpFileName+".csv");
			fis = new FileInputStream(file);

		// Here BufferedInputStream is added for fast reading.
		bis = new BufferedInputStream(fis);
		dis = new DataInputStream(bis);
		// dis.available() returns 0 if the file does not have more lines.
		
    	HashMap<String, String> downloadValues = new HashMap<String, String>();
    	LinkedList<String> keyVals = new LinkedList<String>();
    	if (dis.available() != 0) {
			String keyLine = dis.readLine();
			logger.info("**"+keyLine+"**");
			StringTokenizer strToken = new StringTokenizer(keyLine,",");
			int count = strToken.countTokens();
			for(int i=0; i<count; i++){
				String token = strToken.nextToken();
				keyVals.add(token.substring(1,token.length()-1));
			}
		}
    	while (dis.available() != 0) {
			String valueLine = dis.readLine();
			logger.info("**"+valueLine+"**");
			StringTokenizer valueToken = new StringTokenizer(valueLine,",");
			int count = valueToken.countTokens();
			
			for(int i=0; i<count; i++){
				String token = valueToken.nextToken();
				downloadValues.put( keyVals.get(i), token.substring(1,token.length()-1));
			}
			data.add(downloadValues);
    	}
    	
    	logger.info("Logging out of Responsys");
    	logoutWS();
		if(!file.delete()){
			logger.debug("File could not be deleted: " + file.getPath());
		}
		
		}catch (IOException ioEX){
			logger.error("Error while downloading datasource: " + ioEX.getMessage());			
			return null;
		}catch (Exception e){
			logger.error("Error while downloading datasource: " + e.getMessage());			
			return null;
		}
		
		finally
		{
			try
			{
			if(dis!=null)
			dis.close();
			if(bis!=null)
	    	bis.close();
			if(fis!=null)
	    	fis.close();
			}
			catch(Exception e)
			{
				//logging error as this can never occur
			logger.error("Exception in finally block", e);
			}
		}
		
		return data;
	}

	private FieldMap[] getFieldMap(Field[] fields, String[] parameters){		
		FieldMap[] fieldMap = new FieldMap[parameters.length];
		int i = 0;

		for (i = 0; i < parameters.length; i++) {
			String fieldName = parameters[i];
			fields[i] = new Field();
			fields[i].setFieldName(fieldName);
			fieldMap[i] = new FieldMap();
			fieldMap[i].setColumnNameInFile(fieldName);
			fieldMap[i].setColumnNameInTable(fieldName);
			fieldMap[i].setOverrideIfNull(false);
            if(fieldName.equalsIgnoreCase("CREATED_TIME")){
                fields[i].setFieldType(FieldType.TIMESTAMP);
            }else{
                fields[i].setFieldType(FieldType.STR255);
            }

		}
		
		return fieldMap;
	}
	
	private DataSourceProperties getDataSourceProperties(Field[] fields, String folderName, String tableName, String tmpFileName){
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setEmailAddressField(EMAIL);
		dataSourceProperties.setFields(fields);
		dataSourceProperties.setDelimitedBy("COMMA");
		dataSourceProperties.setEnclosedBy("DOUBLE_QUOTE");
		dataSourceProperties.setCharacterEncoding(CharacterEncoding.UTF_8);
		dataSourceProperties.setFolderName(folderName);
		dataSourceProperties.setTableName(tableName);
		dataSourceProperties.setCsvFileName(getTmpDSLocation()+ tmpFileName+".csv");
		return dataSourceProperties;

	}

}
