package com.newco.marketplace.validator.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public class CreateDraftValidator extends ABaseValidator implements OrderConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3463214860749496985L;

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
		
		if (so.getServiceDate2() == null)
			setError(resp, APPOINTMENT_END_DATE_REQUIRED);
	
		if (StringUtils.isEmpty(so.getServiceLocation().getStreet1())
				|| StringUtils.isEmpty(so.getServiceLocation().getCity())
				|| StringUtils.isEmpty(so.getServiceLocation().getState())
				|| StringUtils.isEmpty(so.getServiceLocation().getZip()))
			setError(resp, SERVICE_ADDRESS_REQUIRED);
		
		//validate zip
		if (!StringUtils.isEmpty(so.getServiceLocation().getZip())) {
			int zipValidation = LocationUtils.checkIfZipAndStateValid(so.getServiceLocation().getZip(), so.getServiceLocation().getState());
			switch (zipValidation) {
				case Constants.LocationConstants.ZIP_NOT_VALID:
					setError(resp, SEARCH_BY_ZIP_CODE_VALUE_NOT_VALID);
					break;
				case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
					setError(resp, SEARCH_BY_ZIP_CODE_VALUE_NOT_VALID);
					break;
			}
		}
		// validate that at least one Task is present
		List<ServiceOrderTask> tasks = so.getTasks();
		if (isEmpty(tasks))
			setError(resp, TASKS_REQUIRED);

		// validate required Task
		for (ServiceOrderTask task : tasks) {
			if (!validateTask(task))
				setError(resp, "Task:" + task.getTaskName() + " is invalid");
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

		return true;
	}

}
