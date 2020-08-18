package com.newco.marketplace.dto.vo.serviceorder;

import java.util.ArrayList;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.IValidator;
import com.newco.marketplace.webservices.base.ParamMap;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;
import com.sears.os.service.ServiceConstants;

/**
 * @author sahmad7
 *
 */
public class RouteValidator implements IValidator, OrderConstants{
	
	public RouteValidator() {
            super();
        }

    @SuppressWarnings("unchecked")
	public ValidatorResponse validate(ParamMap pMap) throws Exception {
		
		ValidatorResponse vr = new ValidatorResponse();
		ServiceOrder so = (ServiceOrder)pMap.get(OrderConstants.SERVICE_ORDER);
		ArrayList<Integer> providerContactList = (ArrayList<Integer>)pMap.get(OrderConstants.PROVIDERS);
		ArrayList<ServiceOrderTask> tasks = (ArrayList<ServiceOrderTask>)pMap.get(OrderConstants.TASKS);
		
		/*
		 * Do validation
		 */
		
		if(providerContactList.isEmpty()){
			vr.setMessage(OrderConstants.PROVIDER_LIST_EMPTY);
			return vr;
		}
		
		if(so == null){
			vr.setMessage(OrderConstants.INVALID_SERVICE_ORDER);
			vr.setCode(ServiceConstants.USER_ERROR_MSG);
			return vr;			
		}
		
		if( !(so.getWfStateId().equals(OrderConstants.DRAFT_STATUS)))
		{
			vr.setMessage(OrderConstants.SERVICE_ORDER_MUST_BE_IN_DRAFT_STATE);
			vr.setCode(ServiceConstants.USER_ERROR_MSG);
			return vr;
		}
		if( so.getSoId() == null)
			vr.setMessage(OrderConstants.SERVICE_ORDER_NUMBER_REQUIRED);
		if(so.getSowTitle() == null)
			vr.setMessage(OrderConstants.STATEMENT_OF_WORK_TITLE_REQUIRED);
		if(so.getSowDs() == null)
			vr.setMessage(OrderConstants.STATEMENT_OF_WORK_DESCRIPTION_REQUIRED);
		if(so.getPrimarySkillCatId() == null)
			vr.setMessage(OrderConstants.PRIMARY_SKILL_CATEGORY_REQUIRED);
		if(tasks == null)
			vr.setMessage(OrderConstants.TASKS_REQUIRED);
		if(so.getProviderInstructions() == null)
			vr.setMessage(OrderConstants.PROVIDER_SPECIAL_INSTRUCTIONS_REQUIRED);
		if(so.getBuyer().getBuyerId() == null)
			vr.setMessage(OrderConstants.BUYER_ID_REQUIRED);
		if(so.getServiceLocation().getStreet1() == null)
			vr.setMessage(OrderConstants.SERVICE_LOCATION_ADDRESS_REQUIRED);
		if(so.getServiceLocation().getCity() == null)
			vr.setMessage(OrderConstants.SERVICE_LOCATION_CITY_REQUIRED);
		if(so.getServiceLocation().getState() == null)
			vr.setMessage(OrderConstants.SERVICE_LOCATION_STATE_REQUIRED);
		if(so.getServiceLocation().getZip() == null)
			vr.setMessage(OrderConstants.SERVICE_LOCATION_ZIP_REQUIRED);
		if(so.getBuyer().getBuyerContact().getContactId() == null)
			vr.setMessage(OrderConstants.BUYER_CONTACT_ID_REQUIRED);
		if(so.getCreatedDate() == null)
			vr.setMessage(OrderConstants.DATE_CREATED_REQUIRED);
		if(so.getRoutedDate() == null)
			vr.setMessage(OrderConstants.DATE_ROUTED_REQUIRED);
		if(so.getPricingTypeId() == null)
			vr.setMessage(OrderConstants.PRICING_TYPE_REQUIRED);
		if(so.getInitialPrice() == null)
			vr.setMessage(OrderConstants.INITIAL_PRICE_REQUIRED);

		if(vr.getMessages().size() > 0)
			vr.setCode(ServiceConstants.USER_ERROR_MSG);
		else
			vr.setCode(ServiceConstants.VALID_MSG);
		
		return vr;
	}

}
