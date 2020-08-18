/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.create.SOCreateRequest;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOCreateMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * This class is a service class for creating a Service Order.
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOCreateService {
	private Logger logger = Logger.getLogger(SOCreateService.class);
	private XStreamUtility conversionUtility;
	private SOCreateMapper createMapper;
	private IServiceOrderBO serviceOrderBO;
	private SecurityProcess securityProcess;

	private IDocumentBO documentBO;


	/**
	 * This method is for creating a draft service order.
	 * 
	 * @param serviceOrderRequest   String
	 * @throws Exception  Exception
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public  HashMap<String, Object> dispatchCreateServiceRequest(String soCreateRequest)
			throws Exception {
		logger.info("Entering SOCreateService.dispatchCreateServiceRequest()");
		ServiceOrder serviceOrder = null;
		SOCreateResponse createResponse = null;
		List<String> invalidDocumentList = new ArrayList<String>();
		 HashMap<String, Object> returnMap=new HashMap<String, Object>();
		SOCreateRequest soRequest = conversionUtility
				.getCreateRequestObject(soCreateRequest);

		SecurityContext securityContext = securityProcess.getSecurityContext(
				soRequest.getIdentification().getUsername(), soRequest
						.getIdentification().getPassWord());
		if (securityContext == null) {
			logger.error("SecurityContext is null");
			throw new BusinessServiceException(CommonUtility.getMessage(
										PublicAPIConstant.SECURITY_ERROR_CODE));

		} else {
			logger.info("Calling SOCreateMapper for mapping SO");

			HashMap<String, Object> soMap = createMapper.mapServiceOrder(soRequest
					.getServiceOrder(), securityContext);
			if(null!=soMap.get(PublicAPIConstant.ERROR_KEY)){
				Collection errorValues=soMap.values();
				Iterator errorList=errorValues.iterator();
				if(errorList.hasNext()){
					String errorMessage=(String) errorList.next();
					returnMap.put(PublicAPIConstant.ERROR_KEY, errorMessage);
					return returnMap;
				}
				}
			
			serviceOrder = (ServiceOrder) soMap
					.get(PublicAPIConstant.SERVICE_ORDER);
			List titleList = (List) soMap.get(PublicAPIConstant.FILE_TITLE);
			logger.info("Create SO Mapping Completed. Now calling ServiceOrderBO"
							+ "for actual SO Creation");
			ProcessResponse processResponse = serviceOrderBO
					.processCreateDraftSO(serviceOrder, securityContext);
			logger.info("Association of Buyer Documents with SOId--->starts");
			if (!((String) processResponse.getObj())
					.equalsIgnoreCase(PublicAPIConstant.INVALID_SOID)
					&& (null != titleList) && titleList.size() > 0) {
				associateSOWithDocumentsForTitle((String) processResponse.getObj(), securityContext
								.getCompanyId(), titleList,invalidDocumentList);
			} else if (null != soRequest.getServiceOrder().getAttachments()
					&& null != soRequest.getServiceOrder().getAttachments()
							.getFilenameList()
					&& (!((String) processResponse.getObj())
							.equalsIgnoreCase(PublicAPIConstant.INVALID_SOID))) {
				List<String> filenameList = soRequest.getServiceOrder()
						.getAttachments().getFilenameList();
				if (!filenameList.isEmpty()) {
					invokeDocumentList(
							(String) processResponse.getObj(), securityContext
									.getCompanyId(), filenameList,invalidDocumentList);
				}
			}
			createResponse = createMapper.createSOResponseMapping(processResponse,
					invalidDocumentList);
		}
		if (createResponse == null) {
			logger.error("createSOResponse came as null");
			throw new BusinessServiceException(CommonUtility.getMessage(
										PublicAPIConstant.RESPONSE_ERROR_CODE));
		}
		String createResponseXML = conversionUtility
				.getCreateResponseXML(createResponse);
		returnMap.put(PublicAPIConstant.SERVICEORDER_KEY, createResponseXML);
		return returnMap;
	}

	private List<String> invokeDocumentList(String serviceOrderId, Integer ownerID,
			List<String> fileNameList,List<String>  invalidDocumentList) {
		invalidDocumentList = associateSOWithDocuments(serviceOrderId,
				ownerID, fileNameList,invalidDocumentList);
		return invalidDocumentList;
	}

	/**
	 * This method is for associating the documents with service order.
	 * 
	 * @param serviceOrderId  String
	 * @param ownerID Integer
	 * @param fileNameList  List<String>
	 * @return String
	 */

	public List<String> associateSOWithDocuments(String serviceOrderId,
			Integer ownerID, List<String> fileNameList,List<String>  invalidDocumentList) {
		DocumentVO documentVo = null;
		try {
			for (String fileName : fileNameList) {
				logger.info("Checks if the document with the same name::"
						+ fileName + " already exists");
				documentVo = documentBO.retrieveDocumentByFileNameAndEntityID(
						Constants.DocumentTypes.BUYER, fileName, ownerID);
				if (null != documentVo) {
					getDocumentDetails(documentVo.getTitle(), ownerID,
							serviceOrderId, fileName,invalidDocumentList);
				} else {
					if (null != fileName) {
						invalidDocumentList.add(fileName);
					}
					logger.info("Adding the unAssociated Document"
													+" to invalidDocumentList");
				}
			}
		} catch (Exception e) {
			logger.error("Association of Buyer Document with SoId was"
					+ " not Successful " + e);
		}
		return invalidDocumentList;
	}

	/**
	 * This method is for associating the documents with service order.
	 * 
	 * @param serviceOrderId  String
	 * @param ownerID  Integer
	 * @param fileNameList List<String>
	 * @return String
	 */

	public List<String> associateSOWithDocumentsForTitle(String serviceOrderId,
			Integer ownerID, List<String> fileNameList,List<String>  invalidDocumentList) {
		try {
			for (String title : fileNameList) {
				getDocumentDetails(title, ownerID, serviceOrderId, null,invalidDocumentList);
			}
		} catch (Exception e) {
			logger.error("Association of Buyer Document with SoId was"
					+ " not Successful " + e);
		}
		return invalidDocumentList;
	}
	/**
	 * This method is for getting  the documents and associating with service order.
	 * 
	 * @param title  String
	 * @param ownerID  Integer
	 * @param serviceOrderId String
	 * @return String
	 */
	private void getDocumentDetails(String title, Integer ownerID,
			String serviceOrderId, String fileName,List<String>  invalidDocumentList) {

		DocumentVO documentVo = null;
		try {

			logger.info("Getting the document details using SO Title");
			documentVo = documentBO.retrieveDocumentByTitleAndEntityID(
					Constants.DocumentTypes.BUYER, title, ownerID);
		}catch(Exception exception){
			logger.error("Exception occurred while retrieving the document"+exception);
		}
		try{
				if (null != documentVo) {
				documentVo.setDocumentId(null);
				documentVo.setSoId(serviceOrderId);
				documentVo.setCompanyId(ownerID);
				logger.info("Going to associate document with ServiceOrder");
				ProcessResponse insertDocResponse = documentBO
						.insertServiceOrderDocument(documentVo);
				if (null != insertDocResponse) {
					if (!insertDocResponse.isSuccess()) {
						logger.info("Adding the unAssociated Document to " 
														+"invalidDocumentList");
						if (null != fileName) {
							invalidDocumentList.add(fileName);
						}
					}
				} else {
					if (null != fileName) {
						invalidDocumentList.add(fileName);
					}
					logger.info("Adding the unAssociated Document to " 
														+"invalidDocumentList");
				}

			} else {
				if (null != fileName) {
					invalidDocumentList.add(fileName);
				}else{
					invalidDocumentList.add(title);
				}
				logger.info("Adding the unAssociated Document to " 
														+"invalidDocumentList");
			}
		} catch (Exception e) {
			logger.error("caught exception");
		}

	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	public void setCreateMapper(SOCreateMapper createMapper) {
		this.createMapper = createMapper;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

}
