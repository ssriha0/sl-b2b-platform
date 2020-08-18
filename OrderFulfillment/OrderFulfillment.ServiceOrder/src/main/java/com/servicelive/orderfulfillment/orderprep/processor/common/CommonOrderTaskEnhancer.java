package com.servicelive.orderfulfillment.orderprep.processor.common;

import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuTask;
import com.servicelive.domain.so.BuyerOrderSpecialtyAddOn;
import com.servicelive.domain.so.TaskStatus;
import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.orderfulfillment.common.DumpUtil;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.type.SOTaskType;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import org.apache.commons.lang.StringUtils;


public class CommonOrderTaskEnhancer extends AbstractOrderEnhancer {
    protected QuickLookupCollection quickLookupCollection;
    protected IServiceOrderDao serviceOrderDao;
    private Integer maxSeqNum=1;
    public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
    	maxSeqNum=1;
    	// Check if tasks are present 
    	boolean taskPresent = false;
    	taskPresent = serviceOrder.isTasksPresent();
    	if(!taskPresent){
	        List<SOTask> newTaskList = createNewTaskListFromInputTaskList(serviceOrder, orderEnhancementContext.getOrderBuyer());
	        BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
	        if (newTaskList.size() > 0) {
	            assignSortOrderValuesToTasks(newTaskList);
	
	           if (serviceOrder.isPartPickupLocationAvailable() && !containsDeliveryTask(newTaskList) && isPickupCodeAvailable(serviceOrder)) {
	                addDeliveryTask(newTaskList, serviceOrder.getValidationHolder());
	            }
	            
	            replaceTaskCommentsWithSpecialtyAddOnInfo(serviceOrder, newTaskList);
	           // updateHashcodeForTask(serviceOrder, newTaskList);
	            // manipulation code for adding history into task
	            if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TASK_LEVEL, serviceOrder.getBuyerId())){
	            	 serviceOrder.setModifiedByName("SYSTEM");
	            }
	            serviceOrder.setTasks(newTaskList);
	        } else {
	            logger.error("CommonOrderTaskEnhancer.createNewTaskListFromInputTaskList() returned 0 size list.");
	        }
    	}
    }
  
    protected List<SOTask> createNewTaskListFromInputTaskList(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        List<SOTask> inputTaskList = serviceOrder.getTasks();
        DumpUtil.dumpTasks(inputTaskList, "CommonOrderTaskEnhancer.createNewTaskListFromInputTaskList() at entry.");
        List<SOTask> newTaskList = new ArrayList<SOTask>();
        for (SOTask inputTask : inputTaskList) {
            newTaskList.addAll(createTaskListFromInputTask(inputTask, orderBuyer, serviceOrder.getValidationHolder()));
        }
        DumpUtil.dumpTasks(newTaskList, "CommonOrderTaskEnhancer.createNewTaskListFromInputTaskList() at exit.");
        return newTaskList;
    }

    protected List<BuyerOrderSkuTask> getBuyerSkuTaskList(SOTask inputTask, IOrderBuyer orderBuyer, ValidationHolder validationHolder) {
    	logger.info("inside getBuyerSkuTaskList");
        BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
        logger.info("got buyerSkuMap");
        if(buyerSkuMap == null){
        	orderBuyer.initialize();
        }
        
        logger.info("before buyerSkuMap.getBuyerSkuTasks");
        return buyerSkuMap.getBuyerSkuTasks(inputTask.getSpecialtyCode(), inputTask.getExternalSku());
    }

    protected List<SOTask> createTaskListFromInputTask(SOTask inputTask, IOrderBuyer orderBuyer, ValidationHolder validationHolder) {
        List<SOTask> equivalentTasks = new ArrayList<SOTask>();

        List<BuyerOrderSkuTask> buyerSkuTaskList = getBuyerSkuTaskList(inputTask, orderBuyer, validationHolder);
        boolean isPriceSet = false;
        
        if(buyerSkuTaskList== null || buyerSkuTaskList.isEmpty()) {
        	validationUtil.addErrors(validationHolder, ProblemType.TaskTranslationProblem);
        } else {
        	
        for (BuyerOrderSkuTask buyerSkuTask : buyerSkuTaskList) {
            SOTask task = createTaskFrom(inputTask, buyerSkuTask);
            
            // Set the speciality code.
    		if(StringUtils.isEmpty(task.getSpecialtyCode())){
    			BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
    			BuyerOrderSku buyerOrderSku = buyerSkuMap.getBuyerSku(task.getExternalSku());
    			String specialityCode = buyerOrderSku.getBuyerSkuCategory().getCategoryName();
    			if(StringUtils.isNotEmpty(specialityCode)){
    				task.setSpecialtyCode(specialityCode);
    			}
    		}

            SkillNode skillNode = determineSkillNode(buyerSkuTask.getSkillNodeId());
            if (skillNode == null || skillNode.getId() == null || skillNode.getId().equals(0)) {
                validationUtil.addWarnings(validationHolder, ProblemType.TaskTranslationProblem);
                task.setSkillNodeId(0);
            } else {
                task.setSkillNodeId(skillNode.getId().intValue());
            }
          
            //Why is this needed? Wont the input file always have seq number?
            /*if(task.getSequenceNumber()== null || task.getSequenceNumber().equals(0))
            {
            	task.setSequenceNumber(maxSeqNum);
            	maxSeqNum=maxSeqNum+1;
            }*/
            task.setTaskSeqNum(maxSeqNum);
        	maxSeqNum=maxSeqNum+1;
            
            task.setServiceTypeId(buyerSkuTask.getServiceTypeTemplateId().intValue());
            
            LookupServiceType serviceType = getServiceTypeTemplate(task.getServiceTypeId());
            if(serviceType!=null)
            {
            if (OFConstants.SVC_TMPLT_TYP_DELIVERY.equalsIgnoreCase(serviceType.getDescription())) 
            	{
            		task.setSequenceNumber(0);
            		task.setTaskType(SOTaskType.Delivery.getId());
            		task.setAutoInjectedInd(0);
            	}
            }
           
            logger.info("task. isPriceSet() in createTaskListFromInputTask"+isPriceSet);
            
            if (!isPriceSet) {
            	
                task.setPrice(inputTask.getPrice());
                isPriceSet = true;
            	
            }
            else
            {
            	if(task.getSequenceNumber()!=null &&task.getSequenceNumber().intValue()!=0)
                {
                	task.setTaskType(SOTaskType.NonPrimary.getId());	
                }
            }
            equivalentTasks.add(task);
        }
        }

        return equivalentTasks;
    }

	protected SOTask createTaskFrom(SOTask inputTask, BuyerOrderSkuTask buyerOrderSkuTask) {
		SOTask task = new SOTask();
		if(StringUtils.isNotEmpty(inputTask.getTaskName())){
			task.setTaskName(inputTask.getTaskName());
			task.setTaskComments(inputTask.getTaskComments());
		}else{
			task.setTaskName(buyerOrderSkuTask.getTaskName());
			task.setTaskComments(buyerOrderSkuTask.getTaskComments());
		}
		task.setPrimaryTask(inputTask.isPrimaryTask());
		task.setAutoInjectedInd(1);
		task.setExternalSku(inputTask.getExternalSku());		
		task.setSpecialtyCode(inputTask.getSpecialtyCode());		
		task.setSequenceNumber(inputTask.getSequenceNumber());
		task.setSkuId(buyerOrderSkuTask.getBuyerSku().getSkuId().intValue());
		return task;
	}

    private void addDeliveryTask(List<SOTask> taskList, ValidationHolder validationHolder) {
        SOTask primaryTask = ServiceOrder.extractPrimaryTaskFromList(taskList);
        SOTask deliveryTask = new SOTask();
        deliveryTask.setTaskName(OFConstants.DELIVERY_TASK_NAME);
        deliveryTask.setTaskComments(OFConstants.DELIVERY_TASK_COMMENTS);
        Integer primarySkillNodeId = primaryTask.getSkillNodeId();
        deliveryTask.setSkillNodeId(primarySkillNodeId);
        SkillNode skillNode = quickLookupCollection.getSkillTreeLookup().getSkillNodeById(primarySkillNodeId.longValue());
        LookupServiceType primaryDeliverySvcTyp = getServiceTypeTemplate(skillNode.getRootNodeId().intValue(), OFConstants.SVC_TMPLT_TYP_DELIVERY);
        if (primaryDeliverySvcTyp != null) {
            deliveryTask.setServiceTypeId(primaryDeliverySvcTyp.getId());
            deliveryTask.setAutoInjectedInd(0);
            //added to synch up
            deliveryTask.setSequenceNumber(0);
            //deliveryTask.setPermitTask(false);
            deliveryTask.setTaskType(SOTaskType.Delivery.getId());
            deliveryTask.setSortOrder(taskList.size());
            deliveryTask.setTaskSeqNum(taskList.size() + 1);
            taskList.add(deliveryTask);
        } else {
            logger.error("Unable to determine \"Delivery\" service type for skill node - " + primaryTask.getSkillNodeId());
            validationUtil.addErrors(validationHolder, ProblemType.DeliveryTaskNotSet);
        }
    }

    private boolean containsDeliveryTask(List<SOTask> taskList) {
        for (SOTask task : taskList) {
            LookupServiceType serviceType = getServiceTypeTemplate(task.getServiceTypeId());
            if (serviceType.getDescription().equalsIgnoreCase(OFConstants.SVC_TMPLT_TYP_DELIVERY)) return true;
        }
        return false;
    }
    
   //SL-15646: checking whether pickupLocationCode is available
    private boolean isPickupCodeAvailable(ServiceOrder serviceOrder){
    	String buyerRefTypeId = "PICKUP LOCATION CODE";
    	String pickupLocationCode = serviceOrder.getCustomRefValue(buyerRefTypeId);
    	if (StringUtils.isNotBlank(pickupLocationCode)){
    		return true;
    	}
    	return false;
    }

    private SkillNode determineSkillNode(Long skillNodeId) {
        SkillNode skillNode = quickLookupCollection.getSkillTreeLookup().getSkillNodeById(skillNodeId);
//        if (skillNode.getLevel().equals(3)) {
//            skillNode = quickLookupCollection.getSkillTreeLookup().getSkillNodeById(skillNode.getParentNodeId());
//        }
        return skillNode;
    }

    private LookupServiceType getServiceTypeTemplate(Integer serviceTypeTemplateId) {
        return quickLookupCollection.getServiceTypeLookup().getLookupServiceTypeById(serviceTypeTemplateId);
    }

    private LookupServiceType getServiceTypeTemplate(Integer primaryNodeId, String description) {
        return quickLookupCollection.getServiceTypeLookup().getLookupServiceTypeByNodeAndDescription(primaryNodeId, description);
    }

    private void assignSortOrderValuesToTasks(List<SOTask> taskList) {
        for (int i=0; i<taskList.size(); i++) {
            taskList.get(i).setSortOrder(i);
        }
    }

    private void replaceTaskCommentsWithSpecialtyAddOnInfo(ServiceOrder serviceOrder, List<SOTask> taskList) {
        String specialtyCode = serviceOrder.getCustomRefValue(SOCustomReference.CREF_SPECIALTY_CODE);
        String storeNumber = serviceOrder.getCustomRefValue(SOCustomReference.CREF_STORE_NUMBER);

        for (SOTask task: taskList) {
            BuyerOrderSpecialtyAddOn specialtyAddOn = serviceOrderDao.getSpecialtyAddOn(specialtyCode, task.getExternalSku());
            if (specialtyAddOn != null) {
                task.setTaskComments("Store No:" + storeNumber + "-" + specialtyAddOn.getInclusionDescription());
            }
        }
    }        
    

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }
}

