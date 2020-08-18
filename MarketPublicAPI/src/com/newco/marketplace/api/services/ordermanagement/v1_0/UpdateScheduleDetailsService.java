package com.newco.marketplace.api.services.ordermanagement.v1_0;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleDetailsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleDetailsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.ordermanagement.services.OrderManagementService;

public class UpdateScheduleDetailsService extends SOBaseService {
	private Logger logger = Logger.getLogger(UpdateScheduleDetailsService.class);

	private OrderManagementService managementService;
	private OrderManagementMapper omMapper ;
	
	public UpdateScheduleDetailsService() {
		super(PublicAPIConstant.UPDATE_SCHEDULE_DETAILS_REQUEST_XSD,
				PublicAPIConstant.UPDATE_SCHEDULE_DETAILS_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				UpdateScheduleDetailsRequest.class, UpdateScheduleDetailsResponse.class);
	}
	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside UpdateScheduleDetailsService.execute()");
		SecurityContext securityContext = null;
		Results results = null;
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse = new UpdateScheduleDetailsResponse();
		HashMap<String,List<LookupVO>> filters ;
		String providerId = (String) apiVO.getProviderId();
		String soId = (String) apiVO.getSOId();
		if (null != providerId && null != soId) {
			try {
				securityContext = getSecurityContextForVendorAdmin(new Integer(providerId));
			} catch (NumberFormatException nme) {
				/*logger.error("CompleteRequestService.execute(): "
						+ "Number Format Exception occurred for resourceId:"
						+ resourceId, nme);*/
			}
			UpdateScheduleDetailsRequest updateScheduleDetailsRequest = (UpdateScheduleDetailsRequest) apiVO.getRequestFromPostPut();

		
			if(updateScheduleDetailsRequest!=null){
					
				UpdateScheduleVO updateScheduleVO = new UpdateScheduleVO();
				updateScheduleVO.setSoId(soId);
				updateScheduleVO.setProviderId(Integer.parseInt(providerId));
				updateScheduleVO.setScheduleStatusId(updateScheduleDetailsRequest.getScheduleStatus());
				updateScheduleVO.setSource(updateScheduleDetailsRequest.getSource());
				updateScheduleVO.setReasonId(updateScheduleDetailsRequest.getReason());
				updateScheduleVO.setEta(updateScheduleDetailsRequest.getEta());
				updateScheduleVO.setCustomerConfirmedInd((updateScheduleDetailsRequest.getCustomerConfirmInd()) ? 1 : 0);
				updateScheduleVO.setSpecialInstructions(updateScheduleDetailsRequest.getSpecialInstructions());
				updateScheduleVO.setSoNotes(updateScheduleDetailsRequest.getSoNotes());
				updateScheduleVO.setServiceTimeStart(updateScheduleDetailsRequest.getServiceTimeStart());
				updateScheduleVO.setServiceTimeEnd(updateScheduleDetailsRequest.getServiceTimeEnd());
				//updateScheduleVO.setAppointStartDate(updateScheduleDetailsRequest.getRescheduleServiceDateTime1());
				//updateScheduleVO.setAppointEndDate(updateScheduleDetailsRequest.getRescheduleServiceDateTime2());
				updateScheduleVO.setModifiedByName(updateScheduleDetailsRequest.getModifiedByName());
				/**SL 18896 R8.2, pass the startDate & endDate parameter START**/
				updateScheduleVO.setServiceDateStart(updateScheduleDetailsRequest.getStartDate());
				updateScheduleVO.setServiceDateEnd(updateScheduleDetailsRequest.getEndDate());
				/**SL 18896 R8.2, pass the startDate & endDate parameter END**/
				
				managementService.updateScheduleDetails(updateScheduleVO);
				// for updating so substatus to schedule confirmd when schedule status is pre call completed confirmed
				if(updateScheduleVO.getScheduleStatusId() == OrderConstants.PRE_CALL_COMPLETED && (updateScheduleVO.getCustomerConfirmedInd()!=null && updateScheduleVO.getCustomerConfirmedInd().intValue()==1)){
					managementService.updateSOSubstatusToScheduleConfirmed(soId);
				}
			}

			updateScheduleDetailsResponse = omMapper.mapScheduleDetailsResponse();

		}
	logger.info("Leaving SOEditAppointmentTimeService.execute()");

	return updateScheduleDetailsResponse;
		

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
