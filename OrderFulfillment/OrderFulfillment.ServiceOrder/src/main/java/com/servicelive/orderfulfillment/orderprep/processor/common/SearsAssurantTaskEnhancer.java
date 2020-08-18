package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.ServiceOrderNoteUtil;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;

public class SearsAssurantTaskEnhancer extends CommonTaskEnhancer {
	Logger logger = Logger.getLogger(getClass());
	private ServiceOrderNoteUtil noteUtil;
	
	public ServiceOrderNoteUtil getNoteUtil() {
		return noteUtil;
	}


	public void setNoteUtil(ServiceOrderNoteUtil noteUtil) {
		this.noteUtil = noteUtil;
	}


	@Override
	protected List<SOTask> validateSkus(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
		 List<SOTask> newTaskList = new ArrayList<SOTask>();
		 newTaskList = validateInputSkus(serviceOrder, orderBuyer);
	     return newTaskList;
	}
	
	
	private List<SOTask> validateInputSkus(ServiceOrder serviceOrder,IOrderBuyer orderBuyer){
		BuyerOrderSku buyerSku = null;
		ValidationHolder validationHolder = serviceOrder.getValidationHolder();
		List<SOTask> inputTaskList = serviceOrder.getTasks();
		boolean isInvalidPrimaryTask = false;
		List<SOTask> taskList = new ArrayList<SOTask>();
		 BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
	        if(buyerSkuMap == null){
	        	orderBuyer.initialize();
	        }
	        List<SOTask> invalidInputTasks = new ArrayList<SOTask>();
	        for (SOTask inputTask : inputTaskList) {
	        //check if the specialty code + sku combination is correct
		        buyerSku =  buyerSkuMap.getBuyerSku(inputTask.getSpecialtyCode(),inputTask.getExternalSku()) ;
		        if(null == buyerSku){
			        	logger.info("speciality code + sku combination is not correct");
			        	//check if the sku is available for any other specialty
			        	buyerSku = buyerSkuMap.getBuyerSku(inputTask.getExternalSku());
			        	if(null != buyerSku){
			        		logger.info("sku exists for some other speciality");
			        		throw new ServiceOrderException("The sku does not belong to the given specialty code. ");
			        		//validationUtil.addErrors(validationHolder, ProblemType.SpecialtySkuMismatch);
			        	}else{
			        		if(inputTask.isPrimaryTask()){
			        			logger.info("invalid primary task");
			        			isInvalidPrimaryTask = true;
			        		}
			        		serviceOrder.setCreateOrderInd(true);
			        		logger.info("createOrderInd::"+serviceOrder.isCreateOrderInd());
			        		
			        		//CR changes for 16667
			        		invalidInputTasks.add(inputTask);
			        	}
			        	
		        }else{
		        	if(null != buyerSku.getBuyerSkuTaskList() && buyerSku.getBuyerSkuTaskList().size()>0){
		        		taskList.addAll(convertToTasks(inputTask,buyerSku.getBuyerSkuTaskList(),orderBuyer,validationHolder));
		        		
		        	}else{
		        		throw new ServiceOrderException("Service Order has SKUs that could not be mapped to Skill Nodes. The order could not be priced. ");
		        		//validationUtil.addErrors(validationHolder, ProblemType.TaskTranslationProblem);
		        	}
		        }
	        }
	      //CR changes for 16667
	        if (invalidInputTasks.size()>0){
	        	logger.info("there are invalid Input tasks");
	        	StringBuilder sb = new StringBuilder();
	        	int count = 0;
	        	for(SOTask invalidInputTask : invalidInputTasks){
	        		sb.append(invalidInputTask.getTaskName());
	    			//sb.append(invalidInputTask.getExternalSku()).append(" - ").append(invalidInputTask.getSpecialtyCode());
	    			count++;
	    			if (count<invalidInputTasks.size()){
	    				sb.append(", ");
	    			}
	    		}
	        	Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("INVALID_SKUS", sb.toString());	
				logger.info("invalid skus are: " + sb.toString());
        		SONote note = noteUtil.getNewNote(serviceOrder, "NonExistantJobcodes", dataMap);
        		
        	}
	        
	        	if(taskList.size() >0 && isInvalidPrimaryTask)
	        	taskList.get(0).setPrimaryTask(true);
	        	return taskList;
	}
	


}
