package com.newco.marketplace.validator.order.util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public class CreateDraftValidator extends ABaseValidator implements OrderConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2202724874513347897L;

	public ValidatorResponse validate(ServiceOrder so) throws Exception {

		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
	
		if(so == null) {
			setError(resp, SERVICE_OBJ_REQUIRED);
			return resp;
		}
		
		// check some required fields
		if (isEmpty(so.getBuyer().getBuyerId()))
			setError(resp, BUYER_ID_REQUIRED);

		if (isEmpty(so.getPrimarySkillCatId()))
			setError(resp, PRIMARY_SKILL_CATEGORY_REQUIRED);
		
		if (StringUtils.isEmpty(so.getCreatorUserName()))
			setError(resp, CREATOR_USERNAME_REQUIRED);

		if (isEmpty(so.getPricingTypeId()))
			setError(resp, PRICING_TYPE_REQUIRED);

		if (so.getServiceDate1() == null)
			setError(resp, APPOINTMENT_START_DATE_REQUIRED);
		
		if (so.getServiceDate2()== null)
			setError(resp, APPOINTMENT_END_DATE_REQUIRED);
	
		if (StringUtils.isEmpty(so.getServiceLocation().getStreet1())
				|| StringUtils.isEmpty(so.getServiceLocation().getCity())
				|| StringUtils.isEmpty(so.getServiceLocation().getState())
				|| StringUtils.isEmpty(so.getServiceLocation().getZip()))
			setError(resp, SERVICE_ADDRESS_REQUIRED);

		// validate that at least one Task is present
		List tasks = so.getTasks();
		if (isEmpty(tasks))
			setError(resp, TASKS_REQUIRED);

		// validate required Task
		Iterator iter = tasks.iterator();
		int cnt = 1;
		while (iter.hasNext()) {
			ServiceOrderTask task = (ServiceOrderTask) iter.next();
			if (!validateTask(task))
				setError(resp, "Task number:" + cnt + " is invalid");
			cnt++;
		}

		return resp;
	}

	private boolean validateTask(ServiceOrderTask task) {

		if (isEmpty(task.getFinalPrice()))
			return false;

		if (isEmpty(task.getServiceTypeId()))
			return false;

		if (isEmpty(task.getSkillNodeId()))
			return false;

		//return validateParts(task);
		return true;
	}

	/*private boolean validateParts(ServiceOrderTask task) {

		// parts are not required
		if (isEmpty(task.getParts()))
			return true;

		// if parts are passed validate
		List parts = task.getParts();

		// validate required values for Parts
		Iterator iter = parts.iterator();
		while (iter.hasNext()) {
			Part part = (Part) iter.next();
			if (isEmpty(part.getPrice()))
				return false;
			if (StringUtils.isEmpty(part.getPartNo()))
				return false;
		}

		return true;
	}
*/}
