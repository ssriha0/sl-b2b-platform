/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 01-jAN-2013	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateFlag.SOPriorityResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Set SO Priority
 * 
 * @author Infosys
 * @version 1.0
 */
public class SetSOPriorityService extends SOBaseService {
	private Logger logger = Logger.getLogger(SetSOPriorityService.class);

	private OrderManagementService managementService;



	private OrderManagementMapper omMapper ;

	public SetSOPriorityService() {
		super(null,
				PublicAPIConstant.SET_SO_PRIORITY_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				null, SOPriorityResponse.class);
	}



	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside SetSOPriorityService.execute()");
		SOPriorityResponse priorityResponse = new SOPriorityResponse();		
		String firmId = (String) apiVO.getProviderId();	
		String soId = (String) apiVO.getSOId();
		//getting group indicator from parameter
		Map<String, String> reqMap = apiVO.getRequestFromGetDelete();
		String groupInd = "0";
		if(null != reqMap && null != reqMap.get(PublicAPIConstant.GROUP_IND_PARAM)){
			groupInd = reqMap.get(PublicAPIConstant.GROUP_IND_PARAM);
		}
		if (null != firmId && null != soId) {
			int flag = 0;
			//for grouped orders
			if(PublicAPIConstant.GROUPED_SO_IND.equals(groupInd)){
				//get the current priority
				flag = managementService.getGroupPriority(soId, firmId);
				if(0 == flag){
					flag = 1;
				}else{
					flag = 0;
				}
				//update priority
				managementService.updateGroupPriority(soId, firmId, flag);
			}
			else{
				//get the current priority
				flag = managementService.getSOPriority(soId, firmId);
				if(0 == flag){
					flag = 1;
				}else{
					flag = 0;
				}
				//update priority
				managementService.updateSOPriority(soId, firmId, flag);
			}
			
			priorityResponse = omMapper.mapPriorityResponse(flag);
		}
		logger.info("Leaving SetSOPriorityService.execute()");

		return priorityResponse;
	}
	
	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}



	public OrderManagementMapper getOmMapper() {
		return omMapper;
	}



	public void setOmMapper(OrderManagementMapper omMapper) {
		this.omMapper = omMapper;
	}





	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}




}