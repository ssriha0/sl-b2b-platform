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
import com.newco.marketplace.inhomeoutboundnotification.vo.InhomeResponseVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

@APIRequestClass(FindSupplierRequest.class)
@APIResponseClass(FindSupplierResponse.class)
public class FindSupplierService  extends BaseService{
    
	private Logger logger = Logger.getLogger(FindSupplierService.class);
	private IMobileSOActionsBO mobileSOActionsBO;
	private LPNClient lpnClient;
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method of FindSupplierService");
		FindSupplierResponse supplierResponse = new FindSupplierResponse();
		FindSupplierRequest findSupplierRequest =  (FindSupplierRequest) apiVO.getRequestFromPostPut();
		LpnRequest lpnRequest =new LpnRequest();
		LpnResponse lpnResponse = new LpnResponse();
		String requestXml = null;
		String responseXml = null;
		int statusCode = 0;
		//create the LPN  Post request
		lpnRequest = mapClientToLPN(findSupplierRequest,lpnRequest);
		requestXml = convertReqObjectToXMLString(lpnRequest,LpnRequest.class);
		InhomeResponseVO inhomeResponseVO = null;
		Map<String, String> apiDetails = null;
		try {
			apiDetails = mobileSOActionsBO.fetchLpnClientUrlHeader();
		} catch (Exception e) {
			logger.error("Exception in retrieving API details:"+ e.getMessage());
		}
		if(null != apiDetails){
			String url = apiDetails.get(MPConstants.LPN_URL);
			String header = apiDetails.get(MPConstants.LPN_HEADER);
			header = header + InHomeNPSConstants.SEQ_COM1 + InHomeNPSConstants.CURRENT_DATE_TIME + InHomeNPSConstants.SEQ_COL1 + getDateTime();
			logger.debug("FindSupplierService.execute:"+url);
			logger.debug("FindSupplierService.execute:header:"+header);
			try {
				//Calling rest Client to invoke LPN WebSErvice.
				inhomeResponseVO = lpnClient.createResponseFromLPN(requestXml, url, header);
			} catch (Exception e) {
				logger.error("Exception in invoking LPN Web Service:"+ e.getMessage());
			}
			if(null != inhomeResponseVO){
				responseXml = inhomeResponseVO.getResponseXml();
				statusCode = inhomeResponseVO.getStatusCode();
				logger.debug("FindSupplierService:responseXml:"+responseXml);
				logger.debug("FindSupplierService:statusCode:"+statusCode);
				//If Status is Ok, deserialize the response xml
				if (200 == statusCode && StringUtils.isNotBlank(responseXml)){
					try{
					   lpnResponse =(LpnResponse) deserializeLpnResponse(responseXml,LpnResponse.class);
					   mapLpnResponse(lpnResponse,supplierResponse);
					}catch (Exception e) {
						logger.error("Exception in deserization of lpn response"+ e.getMessage());
					  }
				}
			}
		}
		return supplierResponse;
	}
	
	private void mapLpnResponse(LpnResponse lpnResponse,FindSupplierResponse supplierResponse) {
		if(null != lpnResponse){
			supplierResponse.setResults(Results.getSuccess());
			supplierResponse.setCorrelationId(lpnResponse.getCorrelationId());
			List<String> errorMessages = lpnResponse.getMessages();
			List<String> errorMessageList = new ArrayList<String>();
			if(null != errorMessages && !(errorMessages.isEmpty())){
			     for(String message :errorMessages){
			    	 errorMessageList.add(message);
			     }
			}
			Messages messages = new Messages();
			messages.setMessage(errorMessageList);
			supplierResponse.setMessages(messages);
			supplierResponse.setMessages(messages);
			supplierResponse.setResponseCode(lpnResponse.getResponseCode());
			supplierResponse.setResponseMessage(lpnResponse.getResponseMessage());
			Suppliers suppliers = new Suppliers();
			if(null != lpnResponse.getSuppliers() &&!(lpnResponse.getSuppliers().isEmpty()) ){
			    List<Supplier> supplierList = new ArrayList<Supplier>();
			    for(LpnSupplier lpnSupplier:lpnResponse.getSuppliers()){
			    	Parts parts = new Parts();
					List<Part> partList = new ArrayList<Part>();
					if(null != lpnSupplier.getParts() && !(lpnSupplier.getParts().isEmpty())){
						for(LpnPart lpnPart : lpnSupplier.getParts()){
							Part part = new Part();
							part.setPls(lpnPart.getPls());
							part.setDiv(lpnPart.getDiv());
							part.setPartNumber(lpnPart.getPartNumber());
							part.setDescription(lpnPart.getDescription());
							part.setOnHandQuantity(lpnPart.getOnHandQuantity());
							part.setBrand(lpnPart.getBrand());
							partList.add(part);
						   }
						parts.setPart(partList);
					}
					Supplier supplier = new Supplier();
					supplier.setName(lpnSupplier.getName());
					supplier.setAddress1(lpnSupplier.getAddress1());
					supplier.setAddress2(lpnSupplier.getAddress2());
					supplier.setCity(lpnSupplier.getCity());
					supplier.setState(lpnSupplier.getState());
					supplier.setZip(lpnSupplier.getZip());
					supplier.setPhone(lpnSupplier.getPhone());
					supplier.setFax(lpnSupplier.getFax());
					supplier.setLatitude(lpnSupplier.getLatitude());
					supplier.setLongitude(lpnSupplier.getLongitude());
					supplier.setDistance(lpnSupplier.getDistance());
					supplier.setParts(parts);
					supplierList.add(supplier);
				 }
			    suppliers.setSupplierList(supplierList);
			}
			//Write code to sort the suppliers based on distance ascending
			if(null!= suppliers && null!= suppliers.getSupplierList() &&! suppliers.getSupplierList().isEmpty()){
				   Collections.sort(suppliers.getSupplierList(), new Comparator<Supplier>() {
					public int compare(Supplier o1, Supplier o2) {
						Double distance1 = o1.getDistance();
						Double distance2 = o2.getDistance();
						return distance1.compareTo(distance2);
					   }
				   });
				 }
			supplierResponse.setSuppliers(suppliers);
		}
		
	}

	private LpnRequest mapClientToLPN(FindSupplierRequest findSupplierRequest,LpnRequest lpnRequest) {
		if(null != findSupplierRequest){
		   lpnRequest.setLatitude(findSupplierRequest.getLatitude());
		   lpnRequest.setLongitude(findSupplierRequest.getLongitude());
		   lpnRequest.setMaxDistance(findSupplierRequest.getMaxDistance());
		   List<LpnPart> parts = new ArrayList<LpnPart>();
		   if(null!= findSupplierRequest.getParts()
				  && null != findSupplierRequest.getParts().getPart() 
				  && !(findSupplierRequest.getParts().getPart().isEmpty())){
			         for(Part part : findSupplierRequest.getParts().getPart()){
			        	 LpnPart lpnPart = new LpnPart();
			        	 if(null!= part && StringUtils.isNotBlank(part.getDiv())){
			        		 String divisionNo = part.getDiv();
			        		 divisionNo =modifyDivforLpn(divisionNo);
			        		 lpnPart.setDiv(divisionNo);
			        	 }
			        	 if(null!= part && StringUtils.isNotBlank(part.getPls())){
			        		 lpnPart.setPls(part.getPls());
			        	 }
			        	 if(null != part && StringUtils.isNotBlank(part.getPartNumber())){
			        		 lpnPart.setPartNumber(part.getPartNumber());
			        	 }
			        	parts.add(lpnPart);
			         }
		   }
		   lpnRequest.setParts(parts);
		}
		return lpnRequest;
	}
	/**
	 * @param divisionNo
	 * @return
	 */
	private String modifyDivforLpn(String divisionNo) {
		String divNo = divisionNo.trim();
		int length = divNo.length();
		if(length == 4){
			divNo = divNo.substring(1);
		}
		return divNo;
	}

	// Method to find current date time in 'yyyymmdd:HHMMSS' to pass with the header of the web service.
	public String getDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyymmdd:HHMMSS");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// Mapping the response xml to SendMessageResponse class.
	public Object deserializeLpnResponse(String responseXml,Class<?> classz) {
		XStream xstreamResponse = new XStream(new DomDriver());
		xstreamResponse.processAnnotations(classz);
		LpnResponse response = (LpnResponse) xstreamResponse.fromXML(responseXml);
		return response;
	}

	// Common methods to convert Object to String and String to Object
	/**
	 * call this method to convert request Object to XML string
	 * 
	 * @param request
	 * @param classz
	 * @return XML String
	 */
	protected String convertReqObjectToXMLString(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return xstream.toXML(request).toString();
	}

	/**
	 * 
	 * @param classz
	 * @return
	 */
	protected XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter(
				PublicMobileAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}
	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public LPNClient getLpnClient() {
		return lpnClient;
	}

	public void setLpnClient(LPNClient lpnClient) {
		this.lpnClient = lpnClient;
	}

}
