package com.servicelive.orderfulfillment.orderprep.processor.fileupload;

import com.servicelive.orderfulfillment.domain.type.ProblemType;
import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.processor.common.CommonOrderTaskEnhancer;

public class FileUploadOrderTaskEnhancer extends CommonOrderTaskEnhancer {

	@Override
	public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext){
		int taskSeqNum = 1;
		for(SOTask task : serviceOrder.getTasks()){
			//set skill category node id
			Long skillNodeId = 0L;
			if(StringUtils.isNotBlank(task.getCategory())){
				skillNodeId = quickLookupCollection.getSkillTreeLookup().getSubCategoryId(task.getCategory().trim());
			} else {
				validationUtil.addErrors(serviceOrder.getValidationHolder(), ProblemType.MissingTaskCategory);
			}
			if(StringUtils.isNotBlank(task.getSubCategory())){
				skillNodeId = quickLookupCollection.getSkillTreeLookup().getSubCategoryId(task.getSubCategory().trim());
			}
			if(skillNodeId.longValue() == 0){
				//either throw error or add note and put the order in draft
			}
			task.setSkillNodeId(skillNodeId.intValue());
			task.setTaskSeqNum(taskSeqNum);
			taskSeqNum++;
			
			//get service type id
			if(StringUtils.isNotBlank(task.getServiceType())){
				LookupServiceType serviceType = quickLookupCollection.getServiceTypeLookup().getLookupServiceTypeByNodeAndDescription(serviceOrder.getPrimarySkillCatId(), task.getServiceType());
				if(null != serviceType){
				task.setServiceTypeId(serviceType.getId());
                }else{
                    validationUtil.addErrors(serviceOrder.getValidationHolder(), ProblemType.CannotSpecifyTaskServiceType);
			}
		}
	}
	}
}
