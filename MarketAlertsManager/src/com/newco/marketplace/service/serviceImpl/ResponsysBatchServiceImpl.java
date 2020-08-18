package com.newco.marketplace.service.serviceImpl;

//This class is not being used. It has been retained for future reference.

@SuppressWarnings("serial")
public class ResponsysBatchServiceImpl {/*
	private EmailAlertBO emailAlertBO;
	private EmailAlertUtility emailAlertUtility;
	private ResponsysWS57Stub stub;
	SessionHeader sessionHeader;
	private static final Logger logger = Logger
			.getLogger(ResponsysBatchServiceImpl.class.getName());

	public void processAlert() throws DataServiceException {
		
		logger.info("Entering ResponsysBatchServiceImpl.processAlert()");
		ArrayList<AlertTask> alertTaskList = null;
		String parameter = null;
		String[] parameters = null;
		try {
			// The following implementation is for Bulk Messages.
			alertTaskList = (ArrayList<AlertTask>) emailAlertBO
					.retrieveAlertTasks(EmailAlertConstants.BATCH_PRIORITY, EmailAlertConstants.PROV_RESPONSYS);

		} catch (DataServiceException dse) {
			logger.error(
					"ResponsysBatchServiceImpl-->processAlert-->DataServiceException-->",
					dse);
		}
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		int templateid = 0;
		String campaignName = null;
		String subject=null;
		try {
			login("servicelive@sears", "53rv1c3", "");
		} catch (Exception e) {
			logger.error("ResponsysBatchServiceImpl-->processAlert-->Exception while logging-->", e);
		}
		List<Long> updateAlertTaskIds = new ArrayList<Long>();
		for (int i = 0; i < alertTaskList.size(); i++) {
			AlertTask alertTask = (AlertTask) alertTaskList.get(i);
			try {
				Map<String, String> basePMap = emailAlertUtility.toParameterMap(alertTask
						.getTemplateInputValue());
				
				List<String> emails = new ArrayList<String>();
				String listOfEmails = alertTask.getAlertTo();
				emailAlertUtility.addemailsToList(emails, listOfEmails);
				listOfEmails = alertTask.getAlertCc();
				emailAlertUtility.addemailsToList(emails, listOfEmails);
				listOfEmails = alertTask.getAlertBcc();
				emailAlertUtility.addemailsToList(emails, listOfEmails);

				// Remove the duplicate entries from the email list.

				Collection<String> uniqueEmails = emailAlertUtility.removeDuplicateEntries(emails);
				if (uniqueEmails.size() > 0) {
					if (templateid != alertTask.getTemplateId()) {
						if (!dataList.isEmpty()) {
							
							invokeResponsyBatchService(dataList, parameters,
									campaignName,subject);
							emailAlertBO.updateAlertStatus(updateAlertTaskIds);
							updateAlertTaskIds.clear();
							dataList.clear();
						}

					}
					for (String destinationEmail : uniqueEmails) {
						basePMap.put(EmailAlertConstants.EMAIL, destinationEmail);
						dataList.add(basePMap);
					}

				}

				templateid = alertTask.getTemplateId();
				updateAlertTaskIds.add(alertTask.getAlertTaskId());
				parameter = alertTask.getParameters();
				campaignName = alertTask.getEid();
				parameters = parameter.split(",");
				subject=alertTask.getTemplateSubject();
				} catch (MailSendException msEx) {
				logger
						.error("MAIL_SEND_EXCEPTION in EmailAlertProcessor.sendAlerts()");
			} catch (Exception e) {
				logger.error("ResponsysBatchServiceImpl-->processAlert-->EXCEPTION-->", e);
			}
		}
		if (!dataList.isEmpty()) {
			
			invokeResponsyBatchService(dataList, parameters, campaignName,subject);
			emailAlertBO.updateAlertStatus(updateAlertTaskIds);
			updateAlertTaskIds.clear();
			dataList.clear();
		}
		try {
			logout();
		} catch (Exception e) {
			logger.error("ResponsysBatchServiceImpl-->processAlert-->EXCEPTION-->", e);
		}
	}

	private void invokeResponsyBatchService(List<Map<String, String>> listMap,
			String[] parameters, String campaignName,String subject) {
		try {
			
			writeToFile(listMap, parameters);
			String status = getCampaignStatus(campaignName);
			if (status.equalsIgnoreCase(EmailAlertConstants.STATUS_TERMINATED)) {
				truncateTable(EmailAlertConstants.FOLDER_NAME,campaignName);
				appendDataSource(EmailAlertConstants.FOLDER_NAME, campaignName, parameters);
				launchCampaign(campaignName,subject);
			}
		} catch (Exception e) {

		}

	}

	private void writeToFile(List<Map<String, String>> listMap,
			String[] parameters) {
		try {

			FileWriter writer = new FileWriter(EmailAlertConstants.FILE_PATH);
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
					String value = baseMap.get(param);
					writer.write(value);
					if (j != parameters.length - 1) {
						writer.write(",");
					}
					
				}
				writer.append('\n');
			}
			writer.close();
		} catch (Exception e) {

		}

	}

	private boolean login(String custid, String password, String interact)
			throws RIFault, RemoteException {
		String URL = "https://ws4.responsys.net/webservices57/services/ResponsysWS57";
		String sessionId = null;

		// login
		stub = new ResponsysWS57Stub(URL);

		Login login = new Login();
		login.setUsername(custid);
		login.setPassword(password);
		LoginResponse response = stub.login(login);
		sessionId = response.getResult().getSessionId();

		if (sessionId != null) {
			sessionHeader = new SessionHeader();
			sessionHeader.setSessionId(sessionId);

			// maintain session between requests
			stub._getServiceClient().getOptions().setManageSession(true);
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(
					1000 * 60 * 60);

			logger.debug("Logged in");
		}

		return sessionId != null;
	}

	private boolean logout() throws RIFault, RemoteException {
		LogoutResponse logoutResponse = stub
				.logout(new Logout(), sessionHeader);

		if (logoutResponse.getResult()) {
			logger.debug("Logged out");
		}

		return logoutResponse.getResult();
	}

	private void launchCampaign(String campaignName,String subject) throws RIFault,
			RemoteException {
		LaunchCampaign launchCampaign = new LaunchCampaign();

		launchCampaign.setCampaignName(campaignName);
		launchCampaign.setScheduleFrequency(null);
		launchCampaign.setTestLaunch(false);
		launchCampaign.setDisplayName("Nick");
		launchCampaign.setReplyToAddress("nlee@responsys.com");
		launchCampaign.setSubject(subject);
		launchCampaign.setRecipientLimit(1);
		launchCampaign.setTestEmailAddress("nlee@responsys.com");

		LaunchCampaignResponse launchCampaignResponse = stub.launchCampaign(
				launchCampaign, sessionHeader);

		logger.debug("Launch ID: " + launchCampaignResponse.getResult());
	}

	public String getCampaignStatus(String campaignName) throws RIFault,
			RemoteException {
		GetCampaignStatus getCampaignStatus = new GetCampaignStatus();

		getCampaignStatus.setCampaignName(campaignName);

		GetCampaignStatusResponse getCampaignStatusResponse = stub
				.getCampaignStatus(getCampaignStatus, sessionHeader);

		logger.debug(campaignName + " status: "
				+ getCampaignStatusResponse.getResult());
		return getCampaignStatusResponse.getResult();
	}

	public boolean appendDataSource(String folderName, String tableName,
			String[] parameters) throws RIFault, RemoteException,
			InterruptedException {

		logger.debug("Appending...");
		try {
			Field[] fields = new Field[parameters.length];
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
				fields[i].setFieldType(FieldType.STR255);

			}
			
			DataSourceProperties dataSourceProperties = new DataSourceProperties();
			dataSourceProperties.setEmailAddressField(EmailAlertConstants.EMAIL);
			dataSourceProperties.setFields(fields);
			dataSourceProperties.setDelimitedBy("COMMA");
			dataSourceProperties.setEnclosedBy("DOUBLE_QUOTE");
			dataSourceProperties.setCharacterEncoding(CharacterEncoding.UTF_8);
			dataSourceProperties.setFolderName(folderName);
			dataSourceProperties.setTableName(tableName);
			dataSourceProperties.setCsvFileName(EmailAlertConstants.FILE_PATH);

			File fileObj = new File(EmailAlertConstants.FILE_PATH);
			FileDataSource fds = new FileDataSource(fileObj);
			DataHandler dataHandler = new DataHandler(fds);
			dataSourceProperties.setCsvFileData(dataHandler);
			AppendDataSource appendDataSource = new AppendDataSource();
			appendDataSource.setProperties(dataSourceProperties);
			appendDataSource.setMapping(fieldMap);

			AppendDataSourceResponse appendSourceResponse = stub
					.appendDataSource(appendDataSource, sessionHeader);
			DownloadDataSource downloadDataSource178=new DownloadDataSource();
		
			DownloadDataSourceResponse res= stub.downloadDataSource(downloadDataSource178, sessionHeader);
			
			RecordCount dataSrcResult = null;
			IntermediateResult irws = null;
			CheckResult checkResult = new CheckResult();
			while (true) {
				dataSrcResult = appendSourceResponse.getResult();
				checkResult.setIntermediateResultKey(dataSrcResult.getKey());
				irws = stub.checkResult(checkResult, sessionHeader).getResult();
				if (irws.getFinished()) {
					
					if (irws.getComplete()) {
						logger.debug("Record Count: "
								+ ((RecordCount) irws).getCount());
						break;
					}
				}
				// wait for 10 minutes before next check
				logger.debug("Sleep for bit ...");
				Thread.sleep(1 * 60000);
			}
			logger.debug("Completed table update");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return true;
	}

	private boolean truncateTable(String folderName, String tableName)
			throws RIFault, RemoteException {
		TruncateTable truncateTable = new TruncateTable();

		truncateTable.setFolderName(folderName);
		truncateTable.setTableName(tableName);

		TruncateTableResponse truncateTableResponse = stub.truncateTable(
				truncateTable, sessionHeader);

		if (truncateTableResponse.getResult()) {
			logger.debug(folderName + " / " + tableName + " truncated");
		} else {
			logger.debug(folderName + " / " + tableName
					+ " was not truncated");
		}

		return truncateTableResponse.getResult();
	}

	

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}

	public EmailAlertUtility getEmailAlertUtility() {
		return emailAlertUtility;
	}

	public void setEmailAlertUtility(EmailAlertUtility emailAlertUtility) {
		this.emailAlertUtility = emailAlertUtility;
	}
*/}
