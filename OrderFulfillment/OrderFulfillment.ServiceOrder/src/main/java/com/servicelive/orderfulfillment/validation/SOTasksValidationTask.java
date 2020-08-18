package com.servicelive.orderfulfillment.validation;

import java.util.List;

import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;

public class SOTasksValidationTask extends AbstractValidationTask {
    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
    	if(!(serviceOrder.isCreateWithOutTasks())){
	        util.addWarningsIfNull(serviceOrder.getTasks(), validationHolder, ProblemType.MissingTaskName, ProblemType.MissingSkillSelection);
	        if (serviceOrder.getTasks() != null) {
	            validateTasks(serviceOrder, validationHolder);
	        }
    	}
    }

    public void validateTasks(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        List<SOTask> tasks = serviceOrder.getTasks();
        util.addErrorsIfTrue(tasks.size()==0, validationHolder, ProblemType.EmptyTaskList);
        for (SOTask task : tasks) {
        	//SL-20728
            util.addErrorIfStringTooLong(task.getTaskComments(), 10000, validationHolder, ProblemType.TaskCommentsExceeds10000);
            util.addWarningsIfBlank(task.getTaskName(), validationHolder, ProblemType.MissingTaskName);
            util.addWarningIfStringTooLong(task.getTaskName(), 255, validationHolder, ProblemType.TaskNameExceeds255);
            if (isMissingSkillNode(task)) {
                util.addWarnings(validationHolder, ProblemType.MissingSkillSelection);
            }
            util.addWarningsIfTrue(isMissingServiceType(task), validationHolder, ProblemType.MissingSkillSelection);
        }
    }

    private boolean isMissingSkillNode(SOTask task) {
        return task.getSkillNodeId() == null || task.getSkillNodeId().equals(0);
    }

    private boolean isMissingServiceType(SOTask task) {
        return task.getServiceTypeId() == null || task.getServiceTypeId().equals(-1);
    }
}
