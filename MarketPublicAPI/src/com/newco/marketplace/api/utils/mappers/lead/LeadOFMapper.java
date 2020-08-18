package com.newco.marketplace.api.utils.mappers.lead;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.exceptions.ValidationException;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.servicelive.orderfulfillment.domain.LeadCancel;
import com.servicelive.orderfulfillment.domain.LeadContactInfo;
import com.servicelive.orderfulfillment.domain.LeadDocuments;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.LeadPostedFirm;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;

public class LeadOFMapper extends OFMapper {
	private Logger logger = Logger.getLogger(LeadOFMapper.class);
	public static  BigDecimal leadFinalPrice = new BigDecimal("0.00");
	public CreateLeadRequest createLead(FetchProviderFirmRequest firmRequest, SecurityContext securityContext) throws BusinessServiceException, ValidationException {

		logger.info("Entering OFLeadMapper.createLead()");
		CreateLeadRequest leadRequest = new CreateLeadRequest();
		LeadHdr leadHdr = new LeadHdr();
		
		//Mapping logic goes here
		mapLeadHdr(firmRequest,leadHdr);
		leadRequest.setLeadHdr(leadHdr);
		//setting the identification
		com.servicelive.orderfulfillment.serviceinterface.vo.Identification identification= createBuyerIdentFromSecCtx(securityContext);
		
		leadRequest.setIdentification(identification);
		
		logger.info("Exiting OFLeadMapper.createLead()");
		return leadRequest;
	}
	
	private void  mapLeadHdr(FetchProviderFirmRequest firmRequest, LeadHdr leadHdr){
		
		logger.info("firmRequest.getCustomerZipCode()::"+firmRequest.getCustomerZipCode());
		logger.info("firmRequest.getPrimaryProject()::"+firmRequest.getPrimaryProject());
		leadHdr.setZipCode(firmRequest.getCustomerZipCode());
		leadHdr.setUrgencyOfService(firmRequest.getUrgencyOfService());
		leadHdr.setSkill(firmRequest.getSkill());
		leadHdr.setLeadSource(firmRequest.getLeadSource());
		leadHdr.setClientId(firmRequest.getClientId());
		leadHdr.setLeadType(NewServiceConstants.COMPETITIVE_LEAD_TYPE);
		leadHdr.setCreatedBy(NewServiceConstants.LEAD_CREATED_BY);
		leadHdr.setModifiedBy(NewServiceConstants.LEAD_CREATED_BY);
		if(null!=firmRequest.getBuyerId()){
			leadHdr.setBuyerId(firmRequest.getBuyerId());
		}else{
			leadHdr.setBuyerId(7000L);
		}		
		leadHdr.setLeadWfStatus(NewServiceConstants.LEAD_UNMATCHED_INTEGER);
		try{
			Integer clientProjectTypeId = Integer.parseInt(firmRequest.getPrimaryProject());
			leadHdr.setClientProjectTypeId(clientProjectTypeId);			
		}catch(Exception ex){
			logger.info("Exception in mapLeadHdr: "+ex.getMessage());
		}		
		
	}
	
	public OrderFulfillmentLeadRequest createFetchProvidersLeadRequest(String leadId, SecurityContext securityContext) throws BusinessServiceException, ValidationException {

		 logger.info("Entering OFLeadMapper.createFetchProvidersLeadRequest()");
		 OrderFulfillmentLeadRequest request = new OrderFulfillmentLeadRequest();
		 LeadHdr hdr = new LeadHdr();
		 hdr.setLeadId(leadId);
		 request.setElement(hdr);
		 com.servicelive.orderfulfillment.serviceinterface.vo.Identification identification
			= createBuyerIdentFromSecCtx(securityContext);
		 request.setIdentification(identification);		 
		
		 return request;
	}

	public OrderFulfillmentLeadRequest createCompleteLeadRequest(CompleteLeadsRequest completeLeadsRequest,SecurityContext securityContext)throws BusinessServiceException, ValidationException  {
		logger.info("Entering OFLeadMapper.createCompleteLeadRequest()");
		OrderFulfillmentLeadRequest request = new OrderFulfillmentLeadRequest();
		LeadHdr hdr = new LeadHdr();
		mapLeadHdr(completeLeadsRequest,hdr,securityContext.getUsername());
		request.setElement(hdr);
		com.servicelive.orderfulfillment.serviceinterface.vo.Identification identification =createProviderIdentFromSecCtx(securityContext);
		request.setIdentification(identification);		 
		return request;
	}
	
	/**
	 * @author naveenkomanthakal_v
	 * @param cancelLeadRequest
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 * @throws ValidationException
	 */
	public OrderFulfillmentLeadRequest createCancelLeadRequest(CancelLeadRequest cancelLeadRequest,SecurityContext securityContext)throws BusinessServiceException, ValidationException  {
		logger.info("Entering OFLeadMapper.createCancelLeadRequest()");
		OrderFulfillmentLeadRequest request = new OrderFulfillmentLeadRequest();
		LeadHdr hdr = new LeadHdr();
		mapLeadHdr(cancelLeadRequest,hdr);
		request.setElement(hdr);
		com.servicelive.orderfulfillment.serviceinterface.vo.Identification identification =createBuyerIdentFromSecCtx(securityContext);
		request.setIdentification(identification);		 
		return request;
	}

	/**
	 * map request to lead_hdr for cancel lead
	 * @param completeLeadsRequest
	 * @param hdr
	 * @param userName 
	 */
	private void mapLeadHdr(CompleteLeadsRequest completeLeadsRequest,LeadHdr hdr, String userName) {
		//Mapping completion section
		BigDecimal leadLaborPrice = new BigDecimal("0.00");
		BigDecimal materialPrice = new BigDecimal("0.00");
		hdr.setLeadId(completeLeadsRequest.getLeadId());
		hdr.setCompletedDate(completeLeadsRequest.getCompleteDateAsDate());
		if(null!=completeLeadsRequest.getCompletedTime()){
		hdr.setCompletedTime(completeLeadsRequest.getCompletedTime());
		}
		else{
			hdr.setCompletedTime(null);
		}
		if(null!=completeLeadsRequest.getCompletionComment()){
		hdr.setCompletionComments(completeLeadsRequest.getCompletionComment());
		}
		else{
			hdr.setCompletionComments(null);
		}
		hdr.setLeadWfStatus(NewServiceConstants.BUYER_LEAD_MANAGEMENT_LEAD_STATUS_COMPLETED);
		if(null!=completeLeadsRequest.getPrice().getLaborPrice()){
		 leadLaborPrice = new BigDecimal(completeLeadsRequest.getPrice().getLaborPrice());
		}
		else{
		 leadLaborPrice = new BigDecimal("0.00");
		}
		if(null!=completeLeadsRequest.getPrice().getMaterialPrice()){
			materialPrice=new BigDecimal(completeLeadsRequest.getPrice().getMaterialPrice());
		}else{
			 materialPrice = new BigDecimal("0.00");
		}
	    leadFinalPrice=leadLaborPrice.add(materialPrice);
	    hdr.setLeadFinalPrice(leadFinalPrice);
		hdr.setLeadMaterialPrice(materialPrice);
		hdr.setLeadLaborPrice(leadLaborPrice);
		hdr.setModifiedDate(new Date());
		hdr.setModifiedBy(userName);
		//Mapping document sections
		//commenting the documents as no documents is required for lead completion
		/*LeadDocuments leadDocument=new LeadDocuments();
		leadDocument.setLead(hdr);
		leadDocument.setDocumentId(completeLeadsRequest.getDocument().getDocumentId());
		leadDocument.setDocumenetTypeId(completeLeadsRequest.getDocument().getDocumentType());
		leadDocument.setCreatedBy(userName);
		hdr.setLeadDocument(leadDocument);*/
		//Mapping posted Providers Section
		List<LeadPostedFirm>postedFirms = new ArrayList<LeadPostedFirm>();
		LeadPostedFirm postedFirm = new LeadPostedFirm();
		postedFirm.setLead(hdr);
		postedFirm.setLeadFirmStatus(NewServiceConstants.BUYER_LEAD_MANAGEMENT_LEAD_FIRM_STATUS_COMPLETED);
		postedFirm.setNoOfVisits(completeLeadsRequest.getNumberOfVisits());
		if(StringUtils.isNotBlank(completeLeadsRequest.getResourceId())){
		   postedFirm.setResourceId(Integer.parseInt(completeLeadsRequest.getResourceId()));
		}
        if(StringUtils.isNotBlank(completeLeadsRequest.getFirmId())){
		   postedFirm.setVendorId(Integer.parseInt(completeLeadsRequest.getFirmId()));
		}
		postedFirms.add(postedFirm);
		hdr.setPostedFirms(postedFirms);
		}
	
	/**
	 * @author naveenkomanthakal_v
	 * @param cancelLeadRequest
	 * @param hdr
	 */
	private void mapLeadHdr(CancelLeadRequest cancelLeadRequest,LeadHdr hdr) {
		//Mapping completion section
		hdr.setLeadId(cancelLeadRequest.getLeadId());
		if(cancelLeadRequest.isChkAllProviderInd())
		{
			hdr.setLeadWfStatus(NewServiceConstants.BUYER_LEAD_MANAGEMENT_LEAD_STATUS_CANCELLED);

		}
		hdr.setModifiedDate(new Date());
		
				
		//Mapping lead contact info
		
		if(cancelLeadRequest.isRevokePointsIndicator())
		{
			LeadContactInfo leadContactInfo=new LeadContactInfo();
			leadContactInfo.setLead(hdr);
			leadContactInfo.setSwyrReward(0);
			hdr.setLeadContactInfo(leadContactInfo);

		}
		
		
		
		//Mapping posted Providers Section
		List<Integer> providerInfoList=new ArrayList<Integer>();
		//converting string to Arraylist
		List<String>providerList1=new ArrayList<String>(Arrays.asList(cancelLeadRequest.getProviders().split(",")));
		for(String s:providerList1)
		{
			providerInfoList.add(Integer.parseInt(s));
		}
		
		List<LeadPostedFirm>postedFirms = new ArrayList<LeadPostedFirm>();
		for(Integer vendorId:providerInfoList)
		{
			LeadPostedFirm postedFirm = new LeadPostedFirm();
			postedFirm.setLead(hdr);
			postedFirm.setLeadFirmStatus(NewServiceConstants.BUYER_LEAD_MANAGEMENT_LEAD_STATUS_CANCELLED_PROVIDERS);
			postedFirm.setVendorId(vendorId);
			postedFirms.add(postedFirm);
			
		}
		hdr.setPostedFirms(postedFirms);
		
		//Mapping lead cancel sections
		List<LeadCancel> cancelList = new ArrayList<LeadCancel>();
		for (Integer vendorId : providerInfoList) {
			LeadCancel leadcancel = new LeadCancel();
			leadcancel.setLead(hdr);
			leadcancel.setCancellationComments(cancelLeadRequest.getComments());
			leadcancel.setModifiedBy(cancelLeadRequest.getVendorBuyerName());
			leadcancel.setCancellationReasonCode(cancelLeadRequest
					.getReasonCode());
			leadcancel.setVendorId(vendorId);
			leadcancel.setCancelInitiatedBy(cancelLeadRequest.getCancelInitiatedBy());
			cancelList.add(leadcancel);

		}
		hdr.setCancelLeadFirms(cancelList);
		}
	
	/************************************************B2C*****************************************/
	
	public CreateLeadRequest createLead(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest firmRequest, 
			SecurityContext securityContext) throws BusinessServiceException, ValidationException {

		logger.info("Entering OFLeadMapper.createLead()");
		CreateLeadRequest leadRequest = new CreateLeadRequest();
		LeadHdr leadHdr = new LeadHdr();
		
		//Mapping logic goes here
		mapLeadHdr(firmRequest,leadHdr);
		leadRequest.setLeadHdr(leadHdr);
		//setting the identification
		com.servicelive.orderfulfillment.serviceinterface.vo.Identification identification
		= createBuyerIdentFromSecCtx(securityContext);
		
		leadRequest.setIdentification(identification);
		
		logger.info("Exiting OFLeadMapper.createLead()");
		return leadRequest;
	}
	
	private void  mapLeadHdr(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest firmRequest, LeadHdr leadHdr){
		leadHdr.setZipCode(firmRequest.getCustomerZipCode());
		leadHdr.setUrgencyOfService(firmRequest.getUrgencyOfService());
		leadHdr.setSkill(firmRequest.getSkill());
		leadHdr.setLeadSource(firmRequest.getLeadSource());
		leadHdr.setClientId(firmRequest.getClientId());
		leadHdr.setLeadType(NewServiceConstants.COMPETITIVE_LEAD_TYPE);
		leadHdr.setCreatedBy(NewServiceConstants.LEAD_CREATED_BY);
		leadHdr.setModifiedBy(NewServiceConstants.LEAD_CREATED_BY);
		if(null!=firmRequest.getBuyerId()){
			leadHdr.setBuyerId(firmRequest.getBuyerId());
		}else{
			leadHdr.setBuyerId(7000L);
		}		
		leadHdr.setLeadWfStatus(NewServiceConstants.LEAD_UNMATCHED_INTEGER);
		try{
			Integer clientProjectTypeId = Integer.parseInt(firmRequest.getPrimaryProject());
			leadHdr.setClientProjectTypeId(clientProjectTypeId);			
		}catch(Exception ex){
			logger.info("Exception in mapLeadHdr: "+ex.getMessage());
		}		
		
	}
	
}
	