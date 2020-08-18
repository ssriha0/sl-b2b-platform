package com.newco.marketplace.api.services.leadsmanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.lead.LeadOFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsRequest;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadMailVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.servicelive.buyerleadmanagement.services.BuyerLeadManagementService;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.LeadResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class CancelLeadService extends BaseService {
	private static Logger logger = Logger
			.getLogger(CancelLeadService.class);
	private LeadProcessingBO leadProcessingBO;
	private LeadOFMapper leadOFMapper;
	private LeadManagementValidator leadManagementValidator;
	private OFHelper ofHelper;
			
	public CancelLeadService() {
		super(
				PublicAPIConstant.CANCEL_LEAD_REQUEST_XSD,
				PublicAPIConstant.CANCEL_LEAD_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.CANCEL_LEAD_RESPONSE_SCHEMALOCATION,
				CancelLeadRequest.class,
				CancelLeadResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO){
		CancelLeadRequest cancelLeadRequest = (CancelLeadRequest) apiVO
				.getRequestFromPostPut();
		CancelLeadResponse response = new CancelLeadResponse();
		LeadResponse leadResponse=null;
		int retval=0;
		CancelLeadMailVO cancelLeadMailVO=new CancelLeadMailVO();
		List<CancelLeadVO> cancelLeadVO=new ArrayList<CancelLeadVO>();
		

		setTestResponse(cancelLeadRequest, response);
		
		// Invoke Validation Service to validate the request
		try {
			cancelLeadRequest = leadManagementValidator
					.validate(cancelLeadRequest);
			if (ResultsCode.SUCCESS != cancelLeadRequest
					.getValidationCode()) {
			
				return createErrorResponse(cancelLeadRequest
						.getValidationCode().getMessage(),
						cancelLeadRequest.getValidationCode().getCode());
			}
		} catch (Exception e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		try {
				//retval=leadProcessingBO.cancelLead(cancelLeadRequest);
				leadResponse = processLeadOFRequest(cancelLeadRequest);
				
				
				
				//if(retval>1)
			if (leadResponse != null) {
				if (leadResponse.getErrors().size() > 0) {
					return createErrorResponse(
							ResultsCode.UNABLE_TO_PROCESS.getMessage(),
							ResultsCode.UNABLE_TO_PROCESS.getCode());
				}
				List<Integer> providerInfoList = new ArrayList<Integer>();
				List<String> providerList1 = new ArrayList<String>(
						Arrays.asList(cancelLeadRequest.getProviders().split(
								",")));
				for (String s : providerList1) {
					providerInfoList.add(Integer.parseInt(s));
				}

				// Mailing Section Starts here
				cancelLeadMailVO.setLeadId(cancelLeadRequest.getLeadId());
				cancelLeadMailVO.setFirmIds(providerInfoList);
				
				cancelLeadMailVO.setCancelComments(cancelLeadRequest
						.getComments());
				Calendar currentDate = Calendar.getInstance(); // Get the
																// current date
				SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy");
				String canceDateNow = formatter.format(currentDate.getTime());
				DateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd");
//				SimpleDateFormat SDF2 = new SimpleDateFormat("MM/dd/yy");
				
				cancelLeadMailVO.setCancelDate(canceDateNow);
				if (cancelLeadRequest.isChkAllProviderInd()) {
					cancelLeadMailVO.setCancelMailReceiverType("CUSTOMER");
					
					cancelLeadVO = leadProcessingBO
							.getCancelLeadEmailDetails(cancelLeadMailVO);
					for (CancelLeadVO cancelVO : cancelLeadVO) {
						try {
							if(null!=cancelVO
									.getPreferredAppointment())
							{
							Date unFormattedDate = SDF1.parse(cancelVO
									.getPreferredAppointment());
							SDF1=new SimpleDateFormat("MMM dd,yyyy");
							String resultDate = SDF1.format(unFormattedDate);
							cancelVO.setPreferredAppointment(resultDate);
							}
							if(null!=cancelVO.getPreferredStartTime() && null!=cancelVO.getPreferredEndTime())
							{
								formatCancelPreferredTime(cancelVO);
							}

						} catch (Exception e) {
							System.out.println("Illegal Arguement Exception"
									+ e.getMessage());
						}
					}

					leadProcessingBO
					.sendConfirmationMailforCancelLeadtoProviders(
							cancelLeadVO, cancelLeadMailVO);
					for (CancelLeadVO cancelVO : cancelLeadVO) {
						try {
							String customerCancelDate=cancelVO
									.getPreferredAppointment();
//							customerCancelDate=customerCancelDate.replace("-","/");
							DateFormat SDF2 = new SimpleDateFormat("yyyy-MM-dd");
							if(null!=customerCancelDate)
							{
							Date unFormattedDate = SDF2.parse(customerCancelDate);
							SDF2=new SimpleDateFormat("MM/dd/yyyy");
							String resultDate = SDF2.format(unFormattedDate);
							cancelVO.setPreferredAppointment(resultDate);
							}
							if(null!=cancelVO.getPreferredStartTime() && null!=cancelVO.getPreferredEndTime())
							{
								formatCancelPreferredTime(cancelVO);
							}

						} catch (Exception e) {
							System.out.println("Illegal Arguement Exception"
									+ e.getMessage());
						}
					}
					leadProcessingBO
							.sendConfirmationMailforCancelLeadtoCustomer(
									cancelLeadVO, cancelLeadMailVO);
					
				} else {
					
					cancelLeadMailVO.setCancelMailReceiverType("PROVIDER");
					cancelLeadVO = leadProcessingBO
							.getCancelLeadEmailDetails(cancelLeadMailVO);
					for (CancelLeadVO cancelVO : cancelLeadVO) {
						try {
							if(null!=cancelVO
									.getPreferredAppointment())
							{
							Date unFormattedDate = SDF1.parse(cancelVO
									.getPreferredAppointment());
							SDF1=new SimpleDateFormat("MMM dd,yyyy");
							String resultDate = SDF1.format(unFormattedDate);
							cancelVO.setPreferredAppointment(resultDate);
							}
							if(null!=cancelVO.getPreferredStartTime() && null!=cancelVO.getPreferredEndTime())
							{
								formatCancelPreferredTime(cancelVO);
							}

						} catch (Exception e) {
							System.out.println("Illegal Arguement Exception"
									+ e.getMessage());
						}
					}
					leadProcessingBO
							.sendConfirmationMailforCancelLeadtoProviders(
									cancelLeadVO, cancelLeadMailVO);
				}

			}
			//Mailing Section ends
				
				
			// History Logging Starts here
			
			String userName = cancelLeadRequest.getCreatedBy();
			String modifiedBy=cancelLeadRequest.getModifiedBy();
			if( cancelLeadRequest.getRoleId() != 1){
			String comment = "";
			if (cancelLeadRequest.isChkAllProviderInd()) {
				List<String> items = Arrays.asList(cancelLeadRequest.getProviders().split("\\s*,\\s*"));
				String provider="";
				for(int i=0;i<items.size();i++){
					provider=items.get(i);
					Integer vendorId=Integer.parseInt(provider);
					String businessName=leadProcessingBO.getBusinessName(vendorId);
					comment = NewServiceConstants.LEAD_CANCELLED_BY_BUYER
							+ " for provider:"+ businessName;
					LeadLoggingVO leadLoggingVO = new LeadLoggingVO(
							cancelLeadRequest.getLeadId(),
							NewServiceConstants.LEAD_CANCELLED,
							NewServiceConstants.OLD_VALUE,
							NewServiceConstants.NEW_VALUE, comment, userName, modifiedBy,
							NewServiceConstants.ROLE_ID_BUYER,cancelLeadRequest.getEntityId());
					leadProcessingBO.insertLeadLogging(leadLoggingVO);
				}
			} else {
				List<String> items = Arrays.asList(cancelLeadRequest.getProviders().split("\\s*,\\s*"));
				String provider="";
				for(int i=0;i<items.size();i++){
					provider=items.get(i);
					Integer vendorId=Integer.parseInt(provider);
					String businessName=leadProcessingBO.getBusinessName(vendorId);
					comment = NewServiceConstants.LEAD_CANCELLED_BY_BUYER
							+ " for provider:"+ businessName;
					LeadLoggingVO leadLoggingVO = new LeadLoggingVO(
						cancelLeadRequest.getLeadId(),
						NewServiceConstants.LEAD_CANCELLED,
						NewServiceConstants.OLD_VALUE,
						NewServiceConstants.NEW_VALUE, comment, userName, modifiedBy,
						NewServiceConstants.ROLE_ID_BUYER,cancelLeadRequest.getEntityId());
				leadProcessingBO.insertLeadLogging(leadLoggingVO);
				}
			}
			
			if (cancelLeadRequest.isRevokePointsIndicator()) {
				comment = NewServiceConstants.REVOKED_SWF_POINTS;
				LeadLoggingVO leadLoggingVO = new LeadLoggingVO(
						cancelLeadRequest.getLeadId(),
						NewServiceConstants.LEAD_CANCELLED,
						NewServiceConstants.OLD_VALUE,
						NewServiceConstants.NEW_VALUE, comment, userName,
						modifiedBy, NewServiceConstants.ROLE_ID_BUYER,cancelLeadRequest.getEntityId());
				leadProcessingBO.insertLeadLogging(leadLoggingVO);
			}
			}
			//History Logging Ends
		}
		catch (Exception e) {
			   logger.info("Exception occurred in processLeadOFRequest of cancelLeadsService: "+e.getMessage());
			   return createErrorResponse(
						ResultsCode.UNABLE_TO_PROCESS.getMessage(),
						ResultsCode.UNABLE_TO_PROCESS.getCode());
			}
		  if(null!= leadResponse){
		    	if(cancelLeadRequest.isChkAllProviderInd())
				{
					response.setResults(Results.getSuccess("Lead Cancelled Successfully"));		

				}
				else
				{
					response.setResults(Results.getSuccess("Lead Cancelled Successfully for providers"));		

				}
		   }  
		
		
        
		
		
		return response;
	}
	public void formatCancelPreferredTime(CancelLeadVO cancelVO){
		
		String resultDate1 = "";
		String resultDate2 = "";
		String string1 = cancelVO.getPreferredStartTime();
		String string2 = cancelVO.getPreferredEndTime();
		String formatedPreferredTime="";
		
		SimpleDateFormat newString = new SimpleDateFormat("h:mm a");
		SimpleDateFormat oldstring = new SimpleDateFormat("HH:mm:ss");
		 try{
	  	   Date unFormatted=oldstring.parse(string1);
	         resultDate1 = newString.format(unFormatted);
	         Date unFormatted1=oldstring.parse(string2);
	         resultDate2 = newString.format(unFormatted1);
	         cancelVO.setPreferredStartTime(resultDate1);
	         cancelVO.setPreferredEndTime(resultDate2);
	   }
		 catch(Exception e)
	{
		System.out.println("Illegal Arguement Exception"+e.getMessage());	
	}
		
	}

	
	
	/** Description :Method which accepts ServiceLive lead id and executes JBPM transition
	 * @param cancelLeadsRequest 
	 * @param leadId
	 * @return LeadResponse
	 */
	private LeadResponse processLeadOFRequest(CancelLeadRequest cancelLeadRequest) {
		LeadResponse response = new LeadResponse();		
		//creating security context
		Integer buyerId =NewServiceConstants.HOME_SERVICES_BUYER_ID;
		SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
		try{
			OrderFulfillmentLeadRequest leadRequest = leadOFMapper.createCancelLeadRequest(cancelLeadRequest,securityContext);
			response = ofHelper.runLeadFulfillmentProcess(cancelLeadRequest.getLeadId(), SignalType.CANCEL_LEAD, leadRequest);
		}catch(Exception e){
			logger.info("Exception occurred in processLeadOFRequest of CancelLeadsService: "+e.getMessage());
		}
		
		return response;
	}

	public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	@SuppressWarnings("unchecked")
	private void setTestResponse(
			CancelLeadRequest cancelLeadRequest,
			CancelLeadResponse cancelLeadResponse) {
		
		cancelLeadResponse.setResults(Results.getSuccess());
	}
	
	private CancelLeadResponse createErrorResponse(String message, String code){
		CancelLeadResponse createResponse = new CancelLeadResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}

	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}

	public LeadOFMapper getLeadOFMapper() {
		return leadOFMapper;
	}

	public void setLeadOFMapper(LeadOFMapper leadOFMapper) {
		this.leadOFMapper = leadOFMapper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	
	
	

}
