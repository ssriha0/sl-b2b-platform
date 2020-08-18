package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.util.SimpleCache;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.JobCode;
import com.servicelive.esb.dto.JobCodeInfo;
import com.servicelive.esb.dto.JobCodes;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.dto.ServiceOrders;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.esb.service.JobCodeMarginService;

public class MarginAndLeadTimeRetrieverAction extends AbstractEsbSpringAction {

	private Logger logger = Logger.getLogger(MarginAndLeadTimeRetrieverAction.class);
	private JobCodeMarginService jobCodeMarginService;
	
	public MarginAndLeadTimeRetrieverAction() {
		super();
	}

	public MarginAndLeadTimeRetrieverAction(ConfigTree configTree) {
		super.configTree = configTree;
	}
	
	public Message retrieveJobCodeInfo(Message message) throws ActionLifecycleException {
		logger.info(new StringBuffer("**** Invoking MarginAndLeadTimeRetrieverAction ---->")
			.append(message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH)));
		String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
		ServiceOrders serviceOrdersObj = (ServiceOrders) message.getBody().get(
				MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
		List<ServiceOrder> serviceOrders = serviceOrdersObj.getServiceOrders();
		for (ServiceOrder serviceOrder : serviceOrders) {
			JobCodes jobCodesObj = serviceOrder.getJobCodes();
			List<JobCode> jobCodes = jobCodesObj.getJobCodeList();
			JobCodeInfo[] jobCodeInfos = null;
			try
			{
				jobCodeInfos = populateJobCodeInfos(jobCodes,
					serviceOrder.getMerchandise().getSpecialty());
			}
			catch( Exception e )
			{
				String inputFilefeedName = 
					(String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
				String errorMsg = "Exception caught while populating JobCode List, skipping " 
					+ serviceOrder;
				ExceptionHandler.handle(client, serviceOrder.getServiceUnitNumber() + "  "
						+ serviceOrder.getServiceOrderNumber(), 
					inputFilefeedName,
					errorMsg,
					jobCodeInfos,
					e);
				continue;
			}
			// TODO JOBCODE TOSTRING
			// TODO JOBCODEINFOS TOSTRING
			// TODO EXCEPTIOINHANDLER
			try 
			{
				logger.info( new StringBuffer("Invoking jobCodeMarginService for: ") 
						.append(serviceOrder.getServiceUnitNumber())
						.append(serviceOrder.getServiceOrderNumber()));
				//jobCodeInfos = jobCodeMarginService.retrieveInfo(jobCodeInfos);
				jobCodeInfos = getJobCodeInfos(jobCodeInfos);
			} 
			catch (Exception e) 
			{
				String inputFilefeedName = 
					(String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
				String errorMsg = "JobCodeMarginService Exception: JIRA SL-7045 " 
					+ serviceOrder.getServiceUnitNumber()
					+ serviceOrder.getServiceOrderNumber();
				logger.error( errorMsg.toString(), e );
				ExceptionHandler.handle(client, serviceOrder.getServiceUnitNumber() + "  "
						+ serviceOrder.getServiceOrderNumber(), 
					inputFilefeedName,
					errorMsg,
					jobCodeInfos,
					e);
				continue;
			}
			for (int i = 0; i < jobCodeInfos.length; i++) {
				jobCodes.get(i).setJobCodeInfo(jobCodeInfos[i]);
			}
		}
		
		//Set the object graph on the message body again. (TODO - if this is really needed)
		message.getBody().add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, serviceOrdersObj);		
		return message;
	}
	
	
	private JobCodeInfo[] getJobCodeInfos(JobCodeInfo[] jobCodeInfos) throws Exception {
		final String keyPart = "JobCode-";
		final int arraySize = jobCodeInfos.length;
		
		if (jobCodeMarginService == null) {
			jobCodeMarginService = 
				(JobCodeMarginService) getBeanFactory().getBean("jobCodeMarginService");
		}
		
		HashMap<String, JobCodeInfo> objMap  = new HashMap<String, JobCodeInfo> ();		
		ArrayList<Object> listToGet = new ArrayList<Object>();		
		JobCodeInfo[] JobCodeInfoOutput = new JobCodeInfo[arraySize];
		
		for (int i = 0; i < arraySize; i++) {			
			JobCodeInfo obj = (JobCodeInfo)SimpleCache.getInstance().get(keyPart + jobCodeInfos[i].getPrimaryKey());
			if (obj != null) {
				if (logger.isDebugEnabled())
				  logger.debug("SST:Reading " + jobCodeInfos[i].getPrimaryKey() + " from cache");
				objMap.put(obj.getPrimaryKey(), obj);
			} else {
				listToGet.add(jobCodeInfos[i]);
			}
		}
		
		JobCodeInfo[] JobCodeInfo2 = new JobCodeInfo[listToGet.size()];	
		int jobCodeInfo2Len = 0;
		
		if (listToGet.size() > 0) {
			for (int i = 0; i < listToGet.size(); i++) {
				JobCodeInfo2[i] = (JobCodeInfo)listToGet.get(i);
			}				
			JobCodeInfo2 = jobCodeMarginService.retrieveInfo(JobCodeInfo2);				
		} else {
			if (logger.isDebugEnabled())
			  logger.debug("SST:found everything in cache. Skipping SST");
		}
		
		JobCodeInfo temp;
		jobCodeInfo2Len = JobCodeInfo2.length;
		
		for (int i = 0; i < jobCodeInfo2Len; i++) {
			temp = JobCodeInfo2[i];
			Object o = SimpleCache.getInstance().get(keyPart + temp.getPrimaryKey());
			if (o == null) {
				SimpleCache.getInstance().put(keyPart + temp.getPrimaryKey(), temp,  SimpleCache.TEN_MINUTES * 2);
				logger.info("SST:Putting " + jobCodeInfos[i].getPrimaryKey() + " in cache");
			}
			objMap.put(temp.getPrimaryKey(), temp);
		}
		
		for (int i = 0; i < arraySize; i++) {		
			JobCodeInfoOutput[i] = objMap.get(jobCodeInfos[i].getPrimaryKey());			
		}
		
		return JobCodeInfoOutput;
	}

	protected JobCodeInfo[] populateJobCodeInfos(List<JobCode> jobCodes,
			String specCode) 
		throws Exception
	{
		int num_job_codes = jobCodes.size();
		JobCodeInfo[] jobCodeInfos = new JobCodeInfo[num_job_codes];
		for (int i = 0; i < num_job_codes; i++) {
			jobCodeInfos[i] = new JobCodeInfo(jobCodes.get(i).getNumber(),
					specCode);
		}
		return jobCodeInfos;
	}
}
