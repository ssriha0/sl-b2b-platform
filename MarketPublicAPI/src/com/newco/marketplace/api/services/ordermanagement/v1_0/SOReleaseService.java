/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseSO.SOReleaseRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseSO.SOReleaseResponse;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;

/**
 * This class would act as a Servicer class for SO Release
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOReleaseService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOReleaseService.class);

	private OrderManagementService managementService;



	private OrderManagementMapper omMapper ;
	ProcessResponse processResponse= new ProcessResponse();

	public SOReleaseService() {
		super(PublicAPIConstant.SO_RELEASE_REQUEST_XSD,
				PublicAPIConstant.SO_RELEASE_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOReleaseRequest.class, SOReleaseResponse.class);
	}

	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		Results results = null;
		SOReleaseResponse releaseResponse = new SOReleaseResponse();
		logger.info("Entering SOReleaseService.execute()");
		String firmId = (String) apiVO
				.getProviderId();
		Integer resourceId = apiVO.getProviderResourceId();
		String soId = (String) apiVO.getSOId();
		SOReleaseRequest releaseRequest =(SOReleaseRequest)apiVO.getRequestFromPostPut();
		if (null != firmId && null != soId) {
		String assignmentType = managementService.fetchAssignmentType(soId);
		List<Integer> providerList = new ArrayList<Integer>();
		SOElementCollection soec = new SOElementCollection();
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		securityContext = getSecurityContextForVendorAdmin(new Integer(firmId));
		Identification identity = createOFIdentityFromSecurityContext(securityContext);
		if(assignmentType!=null && assignmentType.equals("FIRM") || releaseRequest.getReleaseByFirmIndicator()){
			try{
			providerList = managementService.fetchProviderList(soId,firmId);
			}catch (Exception e) {
				logger.error("error fetching routed providers for firm", e);
			}
			List<Parameter> parameters = new ArrayList<Parameter>();
            parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_COMMENT,releaseRequest.getComments()));
			soec = createSOElementForProviderList(providerList,releaseRequest);
			response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_FIRM_RELEASE_ORDER, soec, identity, parameters);
		}else{
			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = createReleaseElement((long)resourceId, releaseRequest.getComments(),Integer.parseInt(releaseRequest.getReason()));
			response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_RELEASE_ORDER, routedProvider,identity);
		}
			releaseResponse = omMapper.mapReleaseResponse(response.getErrors());
		}
			logger.info("Leaving SOReleaseService.execute()");
			return releaseResponse;
		}
	public Identification createOFIdentityFromSecurityContext(SecurityContext securityContext) {
		int loginRoleId = securityContext.getRoles().getRoleId();
		switch (loginRoleId) {
		case OrderConstants.BUYER_ROLEID :
		case OrderConstants.SIMPLE_BUYER_ROLEID :
			return getIdentification(securityContext, EntityType.BUYER);

		case OrderConstants.PROVIDER_ROLEID :
			return getIdentification(securityContext, EntityType.PROVIDER);

		case OrderConstants.NEWCO_ADMIN_ROLEID :
			return getIdentification(securityContext, EntityType.SLADMIN);
		}
		return null;
	}
	
	private Identification getIdentification(SecurityContext securityContext, EntityType entityType){
		Identification id = new Identification();
		id.setEntityType(entityType);
		id.setCompanyId((long)securityContext.getCompanyId());
		id.setResourceId((long)securityContext.getVendBuyerResId());
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}
	

	public SOElementCollection createSOElementForProviderList(List<Integer> providerList,SOReleaseRequest releaseRequest){
		SOElementCollection soElementCollection = new SOElementCollection();
		for(Integer providerId :providerList){
			Long id = (long)providerId;
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setProviderResourceId(id);
			routedProvider.setProviderResponse(ProviderResponseType.RELEASED_BY_FIRM);
			if(releaseRequest.getReason().equals("-2")){
				routedProvider.setProviderRespReasonId(null);
			}else{
			routedProvider.setProviderRespReasonId(Integer.parseInt(releaseRequest.getReason()));
			}
			routedProvider.setProviderRespComment(releaseRequest.getComments());
			Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
			routedProvider.setProviderRespDate(providerRespDate);
			soElementCollection.addElement(routedProvider);
		}
		return soElementCollection;
	}
	
	public RoutedProvider createReleaseElement(Long resourceId, String providerComment,Integer reasonCode){
		RoutedProvider routedProvider = new RoutedProvider();
		routedProvider.setProviderResourceId(resourceId);
		routedProvider.setProviderResponse(ProviderResponseType.RELEASED);
		if(reasonCode.equals(-2)){
			routedProvider.setProviderRespReasonId(null);
		}else{
		routedProvider.setProviderRespReasonId(reasonCode);
		}
		routedProvider.setProviderRespComment(providerComment);
		Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		routedProvider.setProviderRespDate(providerRespDate);

		return routedProvider;
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