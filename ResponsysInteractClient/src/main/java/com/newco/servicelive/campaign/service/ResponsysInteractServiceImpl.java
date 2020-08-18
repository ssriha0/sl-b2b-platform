/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-Aug-2015	KMSLTVSZ     Infosys		   1.0
 */
package com.newco.servicelive.campaign.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.rsys.ws.EmailFormat;
import com.rsys.ws.InteractObject;
import com.rsys.ws.ListMergeRule;
import com.rsys.ws.MergeTriggerEmail;
import com.rsys.ws.MergeTriggerEmailResponse;
import com.rsys.ws.OptionalData;
import com.rsys.ws.Recipient;
import com.rsys.ws.RecipientData;
import com.rsys.ws.Record;
import com.rsys.ws.RecordData;
import com.rsys.ws.TriggerCampaignMessage;
import com.rsys.ws.TriggerCampaignMessageResponse;
import com.rsys.ws.TriggerData;
import com.rsys.ws.TriggerResult;
import com.rsys.ws.UpdateOnMatch;
import com.rsys.ws.client.TriggeredMessageFault;
import com.rsys.ws.client.UnexpectedErrorFault;
import com.rsys.ws.fault.ExceptionCode;

/**
 * This class is for sending mails using Responsys Interact Client Connect service.
 * SL-20653 Responsys Upgrade
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResponsysInteractServiceImpl extends BaseInteractService  {
	private static final Logger logger = Logger.getLogger(ResponsysInteractServiceImpl.class.getName());	
	

	/**
	 * This method invokes triggerCampaignMessage of Responsys to send ResponsysInteract mails
	 * and update status in alert task table.
	 * 
	 * @param List dataList
	 * @param String campaignName
	 * @return isSucess
	 */
	/*public boolean sendMail(List<Map<String, String>> dataList,
			String campaignName) {
		logger.info("Entering into sendMail method");
		//Logging method parameters, should be removed
		logger.info("printing campaignName:"+campaignName);
		//this code block shud be removed.
		
		
		logger.info("Logging into Responsys");
		boolean isSucess = false;
		campaignName = getDecoratedCampaignName(campaignName);
		logger.info("Logging into Responsys Interact -- start calling loginResponsysInteract() for campaign "+campaignName);
		if(!loginResponsysInteract()){
			logger.error("Log-in to Responsys failed");
			return false;
		}
		logger.info("Logged into Responsys Interact-- end calling loginResponsysInteract()");	
		try {
			TriggerCampaignMessage sendTriggeredMessages = new TriggerCampaignMessage();
			
			InteractObject localCampaign = new InteractObject();
			localCampaign.setFolderName("testFolder");//TODO this needs to be changed
			localCampaign.setObjectName(campaignName);
			
			RecipientData[] recipientData = null;
			
			
			int size = dataList.size();
			logger.info("dataList Size:"+size);
			recipientData = new RecipientData[size];
			
			Recipient localRecipient = null;
			
			for(int i = 0; i < size; i++){
				Map<String, String> baseMap = dataList.get(i);
				recipientData[i] = new RecipientData();

				OptionalData[] data = new OptionalData[baseMap.size()];
				
				Iterator<Entry<String, String>> pairs = baseMap.entrySet().iterator();
				Entry<String, String> curPair;
				int count = 0;
				
				while (pairs.hasNext()) {
					curPair = pairs.next();
					logger.info("curPair.getKey():"+curPair.getKey());
					if (curPair.getKey().equals(EMAIL)) {
						String emailAddress = curPair.getValue();
						localRecipient = new Recipient();
						localRecipient.setEmailAddress(emailAddress);
						localRecipient.setEmailFormat(EmailFormat.HTML_FORMAT);
						localRecipient.setListName(localCampaign);
						recipientData[i].setRecipient(localRecipient);
						logger.info("inside if condition");
						logger.info("emailAddress:"+emailAddress);
					} else {
						data[count] = new OptionalData();
						data[count].setName(curPair.getKey());
						data[count].setValue(curPair.getValue());
						count = count + 1;
						recipientData[i].setOptionalData(data);
						
						logger.info("inside else condition");
						logger.info("name:"+curPair.getKey());
						logger.info("value:"+curPair.getValue());
						
					}
				}
			}
			sendTriggeredMessages.setRecipientData(recipientData);
			sendTriggeredMessages.setCampaign(localCampaign);
			
			
			RecipientData[] datasForLogging = sendTriggeredMessages.getRecipientData();
			logger.info("new loggers start");
			if(null != datasForLogging){
				for(RecipientData data: datasForLogging){
					Recipient localRecipientForLogging = data.getRecipient();
					logger.info("email:"+localRecipientForLogging.getEmailAddress());
				}
			}
			logger.info("new loggers end");
			
			TriggerCampaignMessageResponse response = null;
			try{
				logger.info("Calling triggerCampaignMessage() of Responsys");
				if(null !=getStubResponsysInteract()){
					logger.info("Object of ResponsysWSServiceStub :"+getStubResponsysInteract());
				}
				else{
					logger.info("Object of ResponsysWSServiceStub getStubResponsysInteract() is null");
				}
				response = getStubResponsysInteract().triggerCampaignMessage(sendTriggeredMessages, getSessionHeader());
			}catch (TriggeredMessageFault trgFault){
				logger.error("Exception Code trgFault= " + trgFault.getFaultMessage().getExceptionCode());
				logger.error("Exception Msg trgFault= " + trgFault.getFaultMessage().getExceptionMessage());
				ExceptionCode errorCode = trgFault.getFaultMessage().getExceptionCode();
				//TODO logic based on exception code SERVER_SHUTDOWN,TEMPORARILY_UNAVAILABLE,CAMPAIGN_STANDBY,REQUEST_LIMIT_EXCEEDED refer RTM service
			}
			
			TriggerResult[] tmResults = response.getResult();
			if (tmResults != null ) {
				logger.info("SendTriggeredMessages Success");
				int i = 1;
				for (TriggerResult result : tmResults) {
					if (result != null && result.getSuccess() ) {
						isSucess = true;
						logger.info("result.getSuccess() is true for recipient " + i);
						logger.info("result.getRecipientId(): "+result.getRecipientId());
						logger.info("result.getSuccess(): "+result.getSuccess());
						logger.info("result.getErrorMessage(): "+result.getErrorMessage());
					} else {
						logger.info("result is null for recipient " + i);
						logger.info("result.getRecipientId(): "+result.getRecipientId());
						logger.info("result.getSuccess(): "+result.getSuccess());
						logger.info("result.getErrorMessage(): "+result.getErrorMessage());
					}
					i++;
				}
				//TODO print some elements in response if reqd, refer RTM service
			} else {
				logger.info("sendTriggeredMessages Failed");
			}
			
		} catch (UnexpectedErrorFault unexpectedEx) {
			logger.error("Exception Code unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionCode());
			logger.error("Exception Msg unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionMessage());
			unexpectedEx.printStackTrace();
		} catch (Exception ex) {
			logger.error("Severe Exception Msg ex= " + ex.getMessage());
			ex.printStackTrace();
		}

		logger.info("Logging out of Responsys");
		logoutResponsysInteract();

		return isSucess;

	}*/

	/**
	 * This method invokes MergeTriggerEmail of Responsys to send ResponsysInteract mails
	 * and update status in alert task table.
	 * 
	 * @param List dataList
	 * @param String campaignName
	 * @return isSucess
	 */
	
	public boolean sendMergeMail(List<Map<String, String>> dataList,
			String campaignName, String responsysFolderName) {
		logger.info("Entering into sendMergeMail method");
		//Logging method parameters, should be removed
		logger.info("printing campaignName:"+campaignName);
		//this code block shud be removed.
		logger.info("printing responsysFolderName:"+responsysFolderName);
		boolean isSucess = false;
		campaignName = getDecoratedCampaignName(campaignName);
		logger.info("Logging into Responsys Interact -- start calling loginResponsysInteract() for campaign "+campaignName);
		if(!loginResponsysInteract()){
			logger.error("Log-in to Responsys failed");
			return false;
		}
		logger.info("Logged into Responsys Interact-- end calling loginResponsysInteract()");	
		try {
			
			MergeTriggerEmail sendTriggeredEmail = new MergeTriggerEmail();
			
			InteractObject localCampaign = new InteractObject();
			
			/** Responsys folder name change done as part of v6 migration, change done on 06/08/2016 **/
			//set the original folder name fetched from DB
			//if value is null, set a dummy value as SL_ServiceOrder
			if(null != responsysFolderName && responsysFolderName != ""){
				localCampaign.setFolderName(responsysFolderName);
			}else{
				localCampaign.setFolderName("SL_ServiceOrder");//TODO this needs to be changed
			}
			localCampaign.setObjectName(campaignName);
			
			RecordData recordData = new RecordData();
			recordData.addFieldNames("EMAIL_ADDRESS_");
			
			Record[] records = null;
			TriggerData[] triggerData = null;
			
			int size = dataList.size();
			logger.info("dataList Size:"+size);
			records = new Record[size];
			triggerData = new TriggerData[size];
			
			for(int i = 0; i < size; i++){
				logger.debug("inside for loop");
				Map<String, String> baseMap = dataList.get(i);
				triggerData[i] = new TriggerData();
				OptionalData[] data = new OptionalData[baseMap.size()];
				
				Iterator<Entry<String, String>> pairs = baseMap.entrySet().iterator();
				Entry<String, String> curPair;
				int count = 0;
				
				while (pairs.hasNext()) {
					curPair = pairs.next();
					logger.debug("curPair.getKey():"+curPair.getKey());
					if (curPair.getKey().equals(EMAIL)) {
						String emailAddress = curPair.getValue();
						records[i] = new Record();
						records[i].addFieldValues(emailAddress);
						recordData.addRecords(records[i]);
						logger.debug("inside if condition");
						logger.debug("emailAddress:"+emailAddress);
					} else {
						data[count] = new OptionalData();
						data[count].setName(curPair.getKey());
						data[count].setValue(curPair.getValue());
						count = count + 1;
						triggerData[i].setOptionalData(data);
						
						logger.debug("inside else condition");
						logger.debug("name:"+curPair.getKey());
						logger.debug("value:"+curPair.getValue());
						
					}
				}
				logger.debug("exiting for loop");
			}
			
			ListMergeRule mergeRule = new ListMergeRule();
			mergeRule.setInsertOnNoMatch(true);
			mergeRule.setUpdateOnMatch(UpdateOnMatch.REPLACE_ALL);
			mergeRule.setMatchColumnName1("EMAIL_ADDRESS_");
			mergeRule.setHtmlValue("H");
			mergeRule.setTextValue("T");
			
			
			sendTriggeredEmail.setMergeRule(mergeRule);
			sendTriggeredEmail.setRecordData(recordData);
			sendTriggeredEmail.setTriggerData(triggerData);
			sendTriggeredEmail.setCampaign(localCampaign);
			
			MergeTriggerEmailResponse response = null;
			
			try{
				logger.info("Calling MergeTriggerEmail() of Responsys");
				if(null !=getStubResponsysInteract()){
					logger.info("Object of ResponsysWSServiceStub :"+getStubResponsysInteract());
				}
				else{
					logger.info("Object of ResponsysWSServiceStub getStubResponsysInteract() is null");
				}
				response = getStubResponsysInteract().mergeTriggerEmail(sendTriggeredEmail, getSessionHeader());
				
			}catch (TriggeredMessageFault trgFault){
				logger.error("Exception Code trgFault= " + trgFault.getFaultMessage().getExceptionCode());
				logger.error("Exception Msg trgFault= " + trgFault.getFaultMessage().getExceptionMessage());
				//ExceptionCode errorCode = trgFault.getFaultMessage().getExceptionCode();
				//TODO logic based on exception code SERVER_SHUTDOWN,TEMPORARILY_UNAVAILABLE,CAMPAIGN_STANDBY,REQUEST_LIMIT_EXCEEDED refer RTM service
				ExceptionCode errorCode = trgFault.getFaultMessage()
						.getExceptionCode();
				if (errorCode == ExceptionCode.SERVER_SHUTDOWN) {
					logger.error("SERVER SHUTDOWN");
					// logout & login again so that you can connect to
					// some other server
					Thread.sleep(30000);
					logoutResponsysInteract();
					loginResponsysInteract();
					response = getStubResponsysInteract().mergeTriggerEmail(sendTriggeredEmail, getSessionHeader());
				} else if (errorCode == ExceptionCode.TEMPORARILY_UNAVAILABLE
						|| errorCode == ExceptionCode.CAMPAIGN_STANDBY
						|| errorCode == ExceptionCode.REQUEST_LIMIT_EXCEEDED) {
					// Wait for some time and try mergeTriggerEmail again.
					// Try until mergeTriggerEmail is successful.
					boolean retry = true;

					while (retry) {
						try {
							Thread.sleep(3000);
							response = getStubResponsysInteract().mergeTriggerEmail(sendTriggeredEmail, getSessionHeader());
							retry = false;
						} catch (TriggeredMessageFault trgMsgFault) {
							errorCode = trgMsgFault.getFaultMessage()
									.getExceptionCode();
							if (errorCode == ExceptionCode.TEMPORARILY_UNAVAILABLE
									|| errorCode == ExceptionCode.CAMPAIGN_STANDBY
									|| errorCode == ExceptionCode.REQUEST_LIMIT_EXCEEDED) {
								retry = true;
							} else {
								retry = false;
							}
						}
					}
				} else {
					throw trgFault;
				}
			}catch(Exception ex){
				logger.error("Exception occured while calling mergeTriggerEmail()");
				ex.printStackTrace();
				throw new Exception("Exception occured while calling mergeTriggerEmail()");
			}
			if(null != response && null != response.getResult() && response.getResult().length>0){
				TriggerResult[] tmResults = response.getResult();
					logger.info("MergeTriggerEmail Success");
					int i = 1;
					for (TriggerResult result : tmResults) {
						if (result != null && result.getSuccess() ) {
							isSucess = true;
							logger.debug("result.getSuccess() is true for recipient " + i);
							logger.debug("result.getRecipientId(): "+result.getRecipientId());
							logger.debug("result.getSuccess(): "+result.getSuccess());
							logger.debug("result.getErrorMessage(): "+result.getErrorMessage());
						} else {
							logger.debug("result is null for recipient " + i);
							logger.debug("result.getRecipientId(): "+result.getRecipientId());
							logger.debug("result.getSuccess(): "+result.getSuccess());
							logger.debug("result.getErrorMessage(): "+result.getErrorMessage());
						}
						i++;
					}
					//TODO print some elements in response if reqd, refer RTM service
			}else{
				logger.info("MergeTriggerEmail Failed, response is null or response.getResult() is null");
				logger.info("response"+response);
				throw new Exception("MergeTriggerEmail Failed, response is null or response.getResult() is null");
			}
			
		} catch (UnexpectedErrorFault unexpectedEx) {
			logger.error("Exception Code unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionCode());
			logger.error("Exception Msg unexpectedEx= " + unexpectedEx.getFaultMessage().getExceptionMessage());
			unexpectedEx.printStackTrace();
		} catch (Exception ex) {
			logger.error("Severe Exception Msg in sendMergeMail...");
			ex.printStackTrace();
		}

		logger.info("Logging out of Responsys");
		logoutResponsysInteract();

		return isSucess;

	}
  
}
