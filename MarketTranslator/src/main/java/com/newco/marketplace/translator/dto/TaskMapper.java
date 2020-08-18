package com.newco.marketplace.translator.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.translator.dao.BuyerSkuTaskAssocMT;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;

public class TaskMapper {

	public static Task mapDomainToDTO(BuyerSkuTaskAssocMT taskAssoc, String comments, String title, boolean defaultTask) {
		if (taskAssoc == null) return null;
		
		Task task = new Task();
		if (taskAssoc.getSku() != null) {
			task.setSku(taskAssoc.getSku());
		}
		if (taskAssoc.getNodeId() != null) {
			task.setTaskNodeID(taskAssoc.getNodeId());
		}
		if (title != null) {
			task.setTitle(title);
		}
		if (comments != null) {
			task.setComments(comments);
		}
		if (taskAssoc.getServiceTypeTemplateId() != null) {
			task.setSkillID(taskAssoc.getServiceTypeTemplateId());
		}
		if (taskAssoc.getSpecialtyCode() != null) {
			task.setSpecialtyCode(taskAssoc.getSpecialtyCode());
		}
		
		task.setDefaultTask(defaultTask);
		return task;
	}
	
	public static Task mapRequestToDTO(CreateTaskRequest request, List<SkuPrice> skus) {
		
		if (request == null) return null;
		
		Task task = new Task();
		task.setComments(request.getTaskComments());
		
		if (request.getTaskName() != null && request.getTaskName().indexOf('|') >= 0) {
			task.setSku(request.getTaskName().substring(0, request.getTaskName().indexOf('|')).trim());
			task.setTitle(request.getTaskName().replace('|', '-'));
		}
		task.setTaskNodeID(request.getSkillNodeId());
		task.setServiceTypeId(request.getServiceTypeId());
		if (request.getServiceTypeId() != null && request.getServiceTypeId().intValue() == 1) {
			task.setDefaultTask(true);
			request.setServiceTypeId(new Integer(0));
		}
		for (SkuPrice skuPrice : skus) {
			if (task.getSku() != null && skuPrice.getSku() != null && task.getSku().equals(skuPrice.getSku())) {
				task.setSpecialtyCode(skuPrice.getSpecialtyCode());
			}
		}
		return task;
	}
	
	/**
	 * Maps main service category, category and sub category as custom reference fields
	 * @param request
	 * @param tasks
	 * @param serviceTypeDescr
	 * return request
	 */
	public static CreateDraftRequest mapTaskCustomRefs(CreateDraftRequest request, List<Task> tasks,String serviceTypeDescr) {
		List<CustomRef> refsToAdd = new ArrayList<CustomRef>();
		List<CustomRef> customRefs = request.getCustomRef();
		//Assigning this to a new list as it is immutable
		refsToAdd.addAll(customRefs);

		for (Task task : tasks) {
			if (task != null & task.getServiceTypeId().equals(Integer.valueOf(Constants.PRIMARY_JOB_CODE))) {
				if (null != task.getMainServiceCatName()) {
					CustomRef mainServiceCategory = new CustomRef();
					mainServiceCategory.setKey(Constants.CUSTOM_REF_MAIN_SERVICE_CAT);
					mainServiceCategory.setValue(task.getMainServiceCatName());
					refsToAdd.add(mainServiceCategory);
				}

				if (null != task.getTaskCatName()) {
					CustomRef category = new CustomRef();
					category.setKey(Constants.CUSTOM_REF_CATEGORY);
					category.setValue(task.getTaskCatName());
					refsToAdd.add(category);
				}

				if (null != task.getTaskSubCatName()) {
					CustomRef subCategory = new CustomRef();
					subCategory.setKey(Constants.CUSTOM_REF_SUB_CATEGORY);
					subCategory.setValue(task.getTaskSubCatName());
					refsToAdd.add(subCategory);
				}

				if(null != serviceTypeDescr){
					CustomRef skill = new CustomRef();
					skill.setKey(Constants.CUSTOM_REF_SKILL);
					skill.setValue(serviceTypeDescr);
					refsToAdd.add(skill);				 
				}
			}
		}
		request.setCustomRef(refsToAdd);
		return request;
	}
}
