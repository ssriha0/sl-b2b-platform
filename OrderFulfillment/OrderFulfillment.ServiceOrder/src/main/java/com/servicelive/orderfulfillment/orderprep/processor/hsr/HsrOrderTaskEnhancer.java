package com.servicelive.orderfulfillment.orderprep.processor.hsr;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuTask;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.processor.common.CommonOrderTaskEnhancer;

import java.util.ArrayList;
import java.util.List;

public class HsrOrderTaskEnhancer extends CommonOrderTaskEnhancer {
    private static final int MAX_TASK_NAME_LENGTH = 35;

	@Override
    protected List<SOTask> createNewTaskListFromInputTaskList(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        List<SOTask> inputTaskList = serviceOrder.getTasks();
        List<SOTask> newTaskList = new ArrayList<SOTask>();
        for (int inputTaskIdx=0; inputTaskIdx<inputTaskList.size(); inputTaskIdx++) {
            SOTask inputTask = inputTaskList.get(inputTaskIdx);
            if (inputTaskIdx == 0) {
                // 1st task always primary for HSR
                inputTask.setPrimaryTask(true);
                // sku is merchandise code in custom refs if the order creation method is not API injection
                if(!serviceOrder.isCreatedViaAPI()){
                	inputTask.setExternalSku(serviceOrder.getCustomRefValue(SOCustomReference.CREF_MERCHANDISE_CODE));
                }                
            }
            newTaskList.addAll(createTaskListFromInputTask(inputTask, orderBuyer, serviceOrder.getValidationHolder()));
        }
        return newTaskList;
    }
    
    @Override
	protected SOTask createTaskFrom(SOTask inputTask, BuyerOrderSkuTask buyerOrderSkuTask) {
    	SOTask task = super.createTaskFrom(inputTask, buyerOrderSkuTask);
		String taskName = buyerOrderSkuTask.getTaskName();
		if (taskName != null && taskName.length() > MAX_TASK_NAME_LENGTH) {
			taskName = taskName.substring(0, MAX_TASK_NAME_LENGTH);
		}
		task.setTaskName(taskName);
		task.setTaskComments(buyerOrderSkuTask.getTaskComments());
		task.setExternalSku(buyerOrderSkuTask.getBuyerSku().getSku());
    	return task;
    }

    @Override
    protected List<BuyerOrderSkuTask> getBuyerSkuTaskList(SOTask inputTask, IOrderBuyer orderBuyer, ValidationHolder validationHolder) {
        BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
        BuyerOrderSku buyerOrderSku = buyerSkuMap.getBuyerSku(inputTask.getExternalSku());
        validationUtil.addWarningsIfNull(buyerOrderSku, validationHolder, ProblemType.PrimarySkuNotConfigured);
        return (buyerOrderSku!=null) ? buyerOrderSku.getBuyerSkuTaskList() : new ArrayList<BuyerOrderSkuTask>();
    }
}
