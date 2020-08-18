package com.servicelive.esb.actions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.LuAuditMessages;
import com.newco.marketplace.webservices.dao.NpsAuditFiles;
import com.newco.marketplace.webservices.dao.NpsAuditOrderMessages;
import com.newco.marketplace.webservices.dao.NpsAuditOrders;
import com.newco.marketplace.webservices.sei.INPSAuditStagingService;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.NPSAuditOrder;
import com.servicelive.esb.dto.NPSAuditOrdersInfo;
import com.servicelive.esb.service.ExceptionHandler;

public class NPSAuditResponseTranslationAction extends AbstractEsbSpringAction {
	
	private Logger	logger = Logger.getLogger(NPSAuditResponseTranslationAction.class);
	private INPSAuditStagingService auditStagingService = null;
	
	public Message translateOrders (Message message){
		
		logger.info("in NPSAuditResponseTranslationAction ..");

		NPSAuditOrdersInfo auditorderInfo = null;
		NPSAuditOrdersInfo reportableOrdersInfo = null;
		Body body = message.getBody();
		
		//Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFilefeedName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		logger.info("*********** Translating the file feed : \""+inputFilefeedName+"\"");
		String client = (String) body.get(MarketESBConstant.CLIENT_KEY);
		
		try{
			if(message.getBody().get(MarketESBConstant.UNMARSHALLED_NPS_AUDIT_OBJ) != null){
				auditorderInfo = (NPSAuditOrdersInfo)message.getBody().get(MarketESBConstant.UNMARSHALLED_NPS_AUDIT_OBJ);
			    // get the related staged data
				reportableOrdersInfo = stageAuditOrders(auditorderInfo, inputFilefeedName);
		}
			message.getBody().add(MarketESBConstant.TRANSLATED_NPS_AUDIT_OBJ,reportableOrdersInfo);
		
		}catch(Exception e) {
			ExceptionHandler.handle(client, new String((byte[]) body.get()), 
					inputFilefeedName,	"Error occurred in mapAuditFields", 
					message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
		} 
		return message;
				
	}
	
	private NPSAuditOrdersInfo stageAuditOrders(NPSAuditOrdersInfo auditorderInfo, String inputFilefeedName) throws Exception{
		
		Integer successOrdersCount = 0;
		Integer errorOrdersCount = 0;
		
		auditStagingService = (INPSAuditStagingService) SpringUtil.factory.getBean("npsAuditStagingService");
		
		NPSAuditOrdersInfo reportableOrdersInfo = new NPSAuditOrdersInfo();
		List<NPSAuditOrder> errorAuditOrderList = new ArrayList<NPSAuditOrder>();
		
		NpsAuditFiles  auditFiles = new NpsAuditFiles();
		List<NpsAuditOrders> npsAuditOrdersList  = new ArrayList<NpsAuditOrders>();
		NpsAuditOrders npsAuditOrder = null;
				
		auditFiles.setFileName(inputFilefeedName);
		List<NPSAuditOrder>  auditOrders = auditorderInfo.getNpsAuditOrders();
		
		Calendar cal = Calendar.getInstance();
		Timestamp createdDateTS = new Timestamp(cal.getTimeInMillis());
		
 		
		for(NPSAuditOrder eachOrder : auditOrders){
			
			npsAuditOrder = new NpsAuditOrders();
			npsAuditOrder.setCreatedDate(createdDateTS);
			npsAuditOrder.setProcessId(eachOrder.getProcessId());
			npsAuditOrder.setReturnCode(eachOrder.getReturnCode());
			npsAuditOrder.setStagingOrderId(eachOrder.getStagingOrderId());
			npsAuditOrder.setNpsAuditOrderMessageses(eachOrder.getNpsAuditOrderMessages());
			npsAuditOrder.setNpsAuditFiles(auditFiles);
						
			for(NpsAuditOrderMessages orderMsg:	npsAuditOrder.getNpsAuditOrderMessageses()){
				orderMsg.setNpsAuditOrders(npsAuditOrder);
			}
						
			List<NpsAuditOrderMessages> auditOrderMsgsList = eachOrder.getNpsAuditOrderMessages();
			if(auditOrderMsgsList!= null){
				for(NpsAuditOrderMessages auditOrderMsgs: auditOrderMsgsList){
					LuAuditMessages auditMessage =  auditOrderMsgs.getLuAuditMessages();
					if(auditMessage.getSuccessInd()){
						successOrdersCount++;
						break;
			        }
					if(auditMessage.getReportable()){
						errorAuditOrderList.add(eachOrder);
						errorOrdersCount++;
						break;
		             }
				
				}
			}
			
			npsAuditOrdersList.add(npsAuditOrder);
			
		}
		
		auditFiles.setNpsAuditOrderses(npsAuditOrdersList);
		auditFiles.setNumFailure(errorOrdersCount);
		auditFiles.setNumSuccess(successOrdersCount);
		auditFiles.setCreatedDate(createdDateTS);
		
		auditStagingService.stageAuditOrdersInFile(auditFiles);
		
		reportableOrdersInfo.setNpsAuditOrders(errorAuditOrderList);
		reportableOrdersInfo.setErrorOrdersCnt(errorOrdersCount);
		reportableOrdersInfo.setSuccessOrdersCnt(successOrdersCount);
		
		return reportableOrdersInfo;
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public NPSAuditResponseTranslationAction (){
		
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param configTree
	 */
	public NPSAuditResponseTranslationAction (ConfigTree configTree) {
		super.configTree = configTree;
	}
	

}
