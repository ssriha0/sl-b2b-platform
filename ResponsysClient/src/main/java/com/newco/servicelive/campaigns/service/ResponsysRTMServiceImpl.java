/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 01-Sep-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.servicelive.campaigns.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.rsys.tmws.EmailFormat;
import com.rsys.tmws.PersonalizationData;
import com.rsys.tmws.RecipientData;
import com.rsys.tmws.SendTriggeredMessages;
import com.rsys.tmws.SendTriggeredMessagesResponse;
import com.rsys.tmws.TriggeredMessageResult;
import com.rsys.tmws.client.TriggeredMessageFault;
import com.rsys.tmws.client.UnexpectedErrorFault;
import com.rsys.tmws.fault.ExceptionCode;

/**
 * This class is for sending mails using Responsys Real Time Messaging service.
 * 
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResponsysRTMServiceImpl extends BaseService  {
	private static final Logger logger = Logger.getLogger(ResponsysRTMServiceImpl.class.getName());	
	
	/**
	 * This method invokes sendTriggeredMessages of Responsys to send RTM mails
	 * and update status in alert task table.
	 * 
	 * @param List
	 *            dataList
	 * @param String
	 *            campaignName
	 * @return void
	 */
	public boolean sendMail(List<Map<String, String>> dataList,
			String campaignName) {
		logger.info("Logging into Responsys");

		boolean isSucess = false;
		campaignName = getDecoratedCampaignName(campaignName);
logger.info("Logging into Responsys -- start calling loginRTM()111"+campaignName);
		if(!loginRTM()){
			logger.error("Log-in to Responsys failed");
			return false;
		}
logger.info("Logging into Responsys -- end calling loginRTM()222");	
		try {
			SendTriggeredMessages sendTriggeredMessages = new SendTriggeredMessages();
			RecipientData[] recipientData = null;
			int size = dataList.size();
			recipientData = new RecipientData[size];
			for (int j = 0; j < size; j++) {
				Map<String, String> baseMap = dataList.get(j);

				recipientData[j] = new RecipientData();
				EmailFormat emailFormat = EmailFormat.HTML;
				recipientData[j].setEmailFormat(emailFormat);

				Iterator<Entry<String, String>> pairs = baseMap.entrySet()
						.iterator();
				PersonalizationData[] data = new PersonalizationData[baseMap
						.size()];
				Entry<String, String> curPair;
				int count = 0;
				while (pairs.hasNext()) {
					curPair = pairs.next();
					if (curPair.getKey().equals(EMAIL)) {
						String emailAddress = curPair.getValue();
						recipientData[j].setEmailAddress(emailAddress);
					} else {
						data[count] = new PersonalizationData();
						data[count].setName(curPair.getKey());
						data[count].setValue(curPair.getValue());
						count = count + 1;

					}

				}

				recipientData[j].setPersonalizationData(data);
			}
			sendTriggeredMessages.setCampaignName(campaignName);
			sendTriggeredMessages.setRecipientData(recipientData);

			SendTriggeredMessagesResponse response = null;
			try {
				logger.info("Calling sendTriggeredMessages() of Responsys");
				response = getStubRTM().sendTriggeredMessages(sendTriggeredMessages,
						getSessionHeaderRTM());

				logger.info("Updating status in alert task table");

			} catch (TriggeredMessageFault trgFault) {
				logger.error("Exception Code = "
						+ trgFault.getFaultMessage().getErrorCode());
				logger.error("Exception Msg = "
						+ trgFault.getFaultMessage().getErrorMessage());
				ExceptionCode errorCode = trgFault.getFaultMessage()
						.getErrorCode();
				if (errorCode == ExceptionCode.SERVER_SHUTDOWN) {
					// logout & login again so that you can connect to
					// some other server
					Thread.sleep(30000);
					logoutRTM();
					loginRTM();
					response = getStubRTM().sendTriggeredMessages(
							sendTriggeredMessages, getSessionHeaderRTM());
				} else if (errorCode == ExceptionCode.TEMPORARILY_UNAVAILABLE
						|| errorCode == ExceptionCode.CAMPAIGN_STANDBY
						|| errorCode == ExceptionCode.REQUEST_LIMIT_EXCEEDED) {
					// Wait for some time and try sendTriggeredMessages again.
					// Try until sendTriggeredMessages is successful.
					boolean retry = true;

					while (retry) {
						try {
							Thread.sleep(3000);
							response = getStubRTM().sendTriggeredMessages(
									sendTriggeredMessages, getSessionHeaderRTM());
							retry = false;
						} catch (TriggeredMessageFault trgMsgFault) {
							errorCode = trgMsgFault.getFaultMessage()
									.getErrorCode();
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
			}

			TriggeredMessageResult[] tmResults = response.getResult();
			if (tmResults != null) {
				logger.info("SendTriggeredMessages Success");
				isSucess = true;
				int i = 1;
				for (TriggeredMessageResult result : tmResults) {
					if (result != null) {
						logger.info(result.getEmailAddress() + " = "
								+ result.getTmID());
					} else {
						logger.info("result is null for recipient " + i);
					}
					i++;
				}
			} else {
				logger.info("sendTriggeredMessages Failed");
			}
		} catch (TriggeredMessageFault trgMsgEx) {
			logger.error("Exception Code = "
					+ trgMsgEx.getFaultMessage().getErrorCode());
			logger.error("Exception Msg = "
					+ trgMsgEx.getFaultMessage().getErrorMessage());
		} catch (UnexpectedErrorFault unexpectedEx) {
			logger.error("Exception Code = "
					+ unexpectedEx.getFaultMessage().getErrorCode());
			logger.error("Exception Msg = "
					+ unexpectedEx.getFaultMessage().getErrorMessage());
		} catch (Exception ex) {
			logger.error("Exception Msg = " + ex.getMessage());
		}

		logger.info("Logging out of Responsys");
		logoutRTM();

		return isSucess;

	}

}
