/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime.SOEditAppointmentTimeRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.editAppTime.SOEditAppointmentTimeResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Service Edit Appointment Time 
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOEditAppointmentTimeService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOEditAppointmentTimeService.class);

	private OrderManagementService managementService;



	private OrderManagementMapper omMapper ;
	ProcessResponse processResponse= new ProcessResponse();

	public SOEditAppointmentTimeService() {
		super(PublicAPIConstant.EDIT_APPOINTMENT_REQUEST_XSD,
				PublicAPIConstant.EDIT_APPOINTMENT_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOEditAppointmentTimeRequest.class, SOEditAppointmentTimeResponse.class);
	}


	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO){
		logger.info("Inside SOEditAppointmentTimeService.execute()");
		SecurityContext securityContext = null;
		SOEditAppointmentTimeResponse editAppointmentTimeResponse = new SOEditAppointmentTimeResponse();
		SOEditAppointmentTimeRequest editAppointmentTimeRequest = (SOEditAppointmentTimeRequest) apiVO.getRequestFromPostPut();
		String providerId = (String) apiVO.getProviderId();		
		String soId = (String) apiVO.getSOId();
		
		//SL-21580: Code change starts
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Code change ends
		
		try {
			securityContext = getSecurityContextForVendorAdmin(new Integer(providerId));	
			
			if (null != providerId && null != soId) {
				if(editAppointmentTimeRequest!=null){
					String startTime = editAppointmentTimeRequest.getStartTime();
					String endTime = editAppointmentTimeRequest.getEndTime();
					/**SL 18896 R8.2, pass the startDate & endDate parameter START**/
					String startDate = editAppointmentTimeRequest.getStartDate();
					String endDate = editAppointmentTimeRequest.getEndDate();
					/**SL 18896 R8.2, pass the startDate & endDate parameter END**/
					String eta = editAppointmentTimeRequest.getEta(); 
					int confirmedInd = editAppointmentTimeRequest.getCustomerConfirmedInd() ? 1 : 0 ;
				
					UpdateScheduleVO scheduleVO = new UpdateScheduleVO();
					scheduleVO.setSource("UPDATE_TIME");
					scheduleVO.setServiceTimeStart(startTime);
					if(StringUtils.isNotBlank(endTime)){
						scheduleVO.setServiceTimeEnd(endTime);
					}
					else{
						scheduleVO.setServiceTimeEnd(null);
					}
					scheduleVO.setEta(eta);
					scheduleVO.setSoId(soId);
					scheduleVO.setProviderId(Integer.parseInt(providerId));
					scheduleVO.setCreatedDate(Timestamp.valueOf(format.format(new Date())));
					scheduleVO.setModifiedDate(Timestamp.valueOf(format.format(new Date())));
					scheduleVO.setModifiedByName(securityContext.getUsername());
					scheduleVO.setCustomerConfirmedInd(confirmedInd);
					/**SL 18896 R8.2, pass the startDate & endDate parameter START**/
					scheduleVO.setServiceDateStart(startDate);
					scheduleVO.setServiceDateEnd(endDate);
					/**SL 18896 R8.2, pass the startDate & endDate parameter END**/
					
					managementService.editSOAppointmentTime(scheduleVO);
					// relay notification
					managementService.updateTimeNotificationRelayServices(soId);
				}
			}
			editAppointmentTimeResponse = omMapper.mapEditAppointmentTimeResponse();
		}
		catch (Exception exception) {
			logger.error("SOEditAppointmentTimeService.execute(): exception due to "+exception.getMessage());
		}
		logger.info("Leaving SOEditAppointmentTimeService.execute()");

		return editAppointmentTimeResponse;
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

	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
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