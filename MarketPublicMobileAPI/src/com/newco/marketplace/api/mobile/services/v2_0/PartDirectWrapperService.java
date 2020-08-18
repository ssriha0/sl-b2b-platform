package com.newco.marketplace.api.mobile.services.v2_0;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.FindSupplierRequest;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.FindSupplierResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.LpnPart;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.LpnRequest;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.LpnResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.LpnSupplier;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.Messages;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.Part;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.Parts;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.Supplier;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.Suppliers;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.services.clientv2_0.LPNClient;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOLoggingBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InhomeResponseVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.inhomeAutoCloseNotification.IInhomeAutoCloseDao;
import com.newco.marketplace.vo.mobile.PartDetailsDTO;
import com.servicelive.partsManagement.services.impl.LISClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

@APIResponseClass(PartDetailsDTO.class)
public class PartDirectWrapperService {
    
	private Logger logger = Logger.getLogger(PartDirectWrapperService.class);
	private IMobileSOLoggingBO mobileSOLoggingBO;
	private static final String MEDIA_TYPE_XML_STR = "application/xml";
	private static final String MEDIA_TYPE_JSON_STR = "application/json";

	private LISClient lisClient;
	private IInhomeAutoCloseDao inhomeAutoCloseDao;
	
	public String execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of PartDirectWrapperService");
		String consumerKey = null;
		String response = null;
		PartDetailsDTO partDetailsDTO  = new PartDetailsDTO();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
	
		for (Map.Entry<String, String> entry : requestMap.entrySet()){
			logger.info(entry.getKey() + "/" + entry.getValue());
		}
		
		String partNum =  requestMap.get(PublicMobileAPIConstant.PARTNO);		
		
		String modelNum =  requestMap.get(PublicMobileAPIConstant.MODELNO);
		
		String modelId =  requestMap.get(PublicMobileAPIConstant.MODELID);
		
		// Default
		String acceptType = MEDIA_TYPE_XML_STR;
				
		//override if the acceptType is set as JSON
		
		if(null!= apiVO.getAcceptHeader() && 
				apiVO.getAcceptHeader().equalsIgnoreCase(MEDIA_TYPE_JSON_STR)){
			acceptType = MEDIA_TYPE_JSON_STR;
		}
		
		//method to fetch the application constant value when search with key
		String applicationConst;
		try {
			
			if(null!=partNum && StringUtils.isNotBlank(partNum)){
				logger.info("part number: "+partNum+" and for SO: "+apiVO.getSOId());
				applicationConst = inhomeAutoCloseDao.getRecipientIdFromDB(MPConstants.LIS_APPKEY);
				String[] parts = applicationConst.split("\\?");
				String url = parts[0]; 
				consumerKey = parts[1]; 
				String [] key = consumerKey.split("=");
				consumerKey = key[1];
				response = lisClient.searchPartDetailsString(url,consumerKey,partNum,acceptType,null,null);
			}else if(null!=modelNum && StringUtils.isNotBlank(modelNum)){
				logger.info("model number: "+modelNum+" and for SO: "+apiVO.getSOId());
				applicationConst = inhomeAutoCloseDao.getRecipientIdFromDB(MPConstants.LIS_MODEL_APPKEY);
				String[] parts = applicationConst.split("\\?");
				String url = parts[0]; 
				consumerKey = parts[1]; 
				String [] key = consumerKey.split("=");
				consumerKey = key[1];
				response = lisClient.searchPartDetailsString(url,consumerKey,null,acceptType,modelNum,null);
			}else if(null!=modelId && StringUtils.isNotBlank(modelId)){
				logger.info("model id: "+modelId+" and for SO: "+apiVO.getSOId());
				applicationConst = inhomeAutoCloseDao.getRecipientIdFromDB(MPConstants.LIS_MODEL_DETAILS_APPKEY);
				String[] parts = applicationConst.split("\\?");
				String url = parts[0]; 
				consumerKey = parts[1]; 
				String [] key = consumerKey.split("=");
				consumerKey = key[1];
				response = lisClient.searchPartDetailsString(url,consumerKey,null,acceptType,null,modelId);
			}			
			
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return response;
	}
	
	
	public Integer validateResourceId(String providerId) {
		Integer resourceId = null;
		try {
			if (StringUtils.isNumeric(providerId)) {
				resourceId = Integer.parseInt(providerId);
			}
			return resourceId;

		} catch (Exception e) {
			logger.info(" error in parsing resource Id" + e);
			return null;
		}
	}

	public Integer logSOMobileHistory(String request, Integer actionId,
			Integer resourceId,String soId,String httpMethod) {
		try {
			long loggingStart = System.currentTimeMillis();		
			
			Integer loggingId = mobileSOLoggingBO.logSOMobileHistory(request,
					actionId, resourceId,soId,httpMethod);
			long loggingEnd = System.currentTimeMillis();
			logger.info("Total time taken for logging  :::"+(loggingEnd -loggingStart));
			return loggingId;
		} catch (Exception e) {
			logger.info("exception while logging SO Mobile history" + e);
			return null;
		}
	}
	
	public void updateSOMobileResponse(Integer loggingId,String response){
		try{
		long loggingStart = System.currentTimeMillis();		
		mobileSOLoggingBO.updateSOMobileResponse(loggingId, response);
		long loggingEnd = System.currentTimeMillis();
		logger.info("Total time taken for update logging  :::"+(loggingEnd -loggingStart));
		}catch(Exception e){
			logger.info("exception while updating SOMobileResponse" + e);
		}
	}
	
	public IMobileSOLoggingBO getMobileSOLoggingBO() {
		return mobileSOLoggingBO;
	}

	public void setMobileSOLoggingBO(IMobileSOLoggingBO mobileSOLoggingBO) {
		this.mobileSOLoggingBO = mobileSOLoggingBO;
	}


	public LISClient getLisClient() {
		return lisClient;
	}


	public void setLisClient(LISClient lisClient) {
		this.lisClient = lisClient;
	}


	public IInhomeAutoCloseDao getInhomeAutoCloseDao() {
		return inhomeAutoCloseDao;
	}


	public void setInhomeAutoCloseDao(IInhomeAutoCloseDao inhomeAutoCloseDao) {
		this.inhomeAutoCloseDao = inhomeAutoCloseDao;
	}	

}
