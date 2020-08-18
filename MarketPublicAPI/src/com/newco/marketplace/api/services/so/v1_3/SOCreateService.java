/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 07-Sep-2017	SLive   Rahul				2.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.Attachments;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.Skus;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.create.v1_3.SOCreateRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.exceptions.ValidationException;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_2.SOCreateMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_3.OFServiceOrderMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This is the service class for executing the Create Service Order request with multiple time slots.
 * 
 * @author rranja1
 * @version 3.0
 */

@APIRequestClass(SOCreateRequest.class)
@APIResponseClass(SOCreateResponse.class)
public class SOCreateService extends BaseService {
	// Private variables
	private Logger logger = Logger.getLogger(SOCreateService.class);
	private SOCreateMapper createMapper;
	private OFServiceOrderMapper ofServiceOrderMapper;
	private IServiceOrderBO serviceOrderBO;	
	private IDocumentBO documentBO;
	private OFHelper ofHelper = new OFHelper();
	private IApplicationProperties applicationProperties;
	private static final String CREF_TEMPLATE_NAME = "TEMPLATE NAME";
	
	//Public Constructor
	public SOCreateService() {
		super(PublicAPIConstant.REQUEST_XSD, PublicAPIConstant.SORESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_3,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOCreateRequest.class, SOCreateResponse.class);
	}

	/**
	 * This method dispatches the Create Service order request.
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
	@SuppressWarnings("unchecked")
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering SOCreateService.execute() ");
		SOCreateRequest createRequest = (SOCreateRequest) apiVO
														.getRequestFromPostPut();
		Integer buyerId = apiVO.getBuyerIdInteger();
		SecurityContext securityContext = null;
		SOCreateResponse createResponse = new SOCreateResponse();
		try {
			securityContext = getSecurityContextForBuyerAdmin(buyerId);
		}
		catch(NumberFormatException nme){
			logger.error("SOCreateService.execute(): Number Format Exception " +
						 "occurred for Buyer id: " + buyerId, nme);
		}
		catch (Exception exception) {
			logger.error("SOCreateService.execute(): Exception occurred while " +
						 "accessing security context with Buyer id: " + buyerId, exception);
		}
		
		// If security context is null.
		if (securityContext == null) {
			logger.error("SOCreateService.execute(): SecurityContext is null");
			return createErrorResponse(ResultsCode.INVALID_RESOURCE_ID.getMessage(), ResultsCode.INVALID_RESOURCE_ID.getCode());
		} else {
			    try {
			        if(isNewOFAllowed(securityContext)){
			        	
			        	//SL-20400 ->To find out the duplicate service orders with the given unit number and 
			       	 	//order number combination in ServiceLive database for InHome Buyer (3000).
			        	if(PublicAPIConstant.INHOME_BUYER_ID.intValue() == buyerId)
			        	{
			        		try{
			        			ServiceOrder servOrder=checkForDuplicateSOInHome(createRequest);
			        				if(null!=servOrder)
			        				{
			        				SOCreateResponse soCreateResponse =ofServiceOrderMapper.createDuplicateSOResponse(servOrder);
			        				
			        				String createSORequest = convertReqObjectToXMLString(createRequest, SOCreateRequest.class); 
			        				String createSOResponse = convertReqObjectToXMLString(soCreateResponse, SOCreateResponse.class); 
			        				
			        				//Logging duplicate service orders details in 'sl_duplicate_so_logging' table.
			        				logDuplicateSORequestResponse(createSORequest,createSOResponse,buyerId,PublicAPIConstant.CREATE_SO_API_LOGGING);
			        				
			        				return soCreateResponse;
			        				}
			        		   }catch(Exception e)
			        		   {
			        			   logger.error("SOCreateService.execute() while trying to find out the duplicate service orders for InHome Buyer (3000): ",e);
			        			   return createGenericErrorResponse();
			        		   }
			        	}		
			        	
			            return createUsingOF(createRequest, securityContext);
			        }
		        } catch (DataNotFoundException e1) {
		            logger.error("SOCreateService.execute(): DataNotFoundException occured: ", e1);
		            return createGenericErrorResponse();
		        }
		logger.info("Leaving SOCreateService.execute()");
		return createResponse;
		}
	}

	/**Description : SL-20400 ->To find out the duplicate service orders with the given unit number and 
	 * order number combination in ServiceLive database for InHome Buyer (3000).
	 * @param createRequest
	 * @throws BusinessServiceException
	 */
	private ServiceOrder checkForDuplicateSOInHome(SOCreateRequest createRequest)
			throws com.newco.marketplace.exception.core.BusinessServiceException {
		String customUnitNumber = null;
		String customOrderNumber =null;
		ServiceOrder servOrder=null;
		CustomReferences customReferences = createRequest.getServiceOrder().getCustomReferences();
		if (null != customReferences && null != customReferences.getCustomRefList()) {	
		for (CustomReference customRef : customReferences.getCustomRefList()) {
		
			String refType = customRef.getName();
			if(PublicAPIConstant.UNIT_NUBMER.equals(refType))
			{
				customUnitNumber = customRef.getValue();
			}else if(PublicAPIConstant.ORDER_NUBMER.equals(refType))
			{
				customOrderNumber = customRef.getValue();
			}
		}
			if(StringUtils.isNotBlank(customUnitNumber) && StringUtils.isNotBlank(customOrderNumber))
			{
				servOrder= serviceOrderBO.getDuplicateSOInHome(customUnitNumber,customOrderNumber);
			}
     	
     	}
		return servOrder;
	}
	
	/**
	 * Used to log request and response Duplicate SO's for InHome Buyer (3000).
	 * @param String request,String response, String buyerId
	 * @return none
	 */	
	public void logDuplicateSORequestResponse(String request,String response, Integer buyerId,String apiName) {
		Integer loggingId =null;
		try{
			 loggingId = serviceOrderBO.logDuplicateSORequestResponse(request,response,buyerId,apiName); 
		}catch (Exception e) {
			logger.error("Exception in logDuplicateSORequestResponse"+ e.getMessage());
		}
		
	} 
	
	
	private IAPIResponse createUsingOF(SOCreateRequest createRequest, SecurityContext securityContext ) {
    	long start = System.currentTimeMillis();

		CreateOrderRequest coRequest = null;
	    SOCreateResponse returnVal = null;
	    List<String> invalidDocumentList = new ArrayList<String>();
            try{
                //do the new SO mapper logic and call OF
            	coRequest = ofServiceOrderMapper.createServiceOrder(createRequest, securityContext);
            }catch(ValidationException slb){ 
            	 return createErrorResponse(slb.getErrorMessages(), ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
			}catch(BusinessServiceException de){
                logger.error("SOCreateService.execute(): DataException occured: ", de);
                return createGenericErrorResponse();
            }
			
			Integer buyerRoleId = securityContext.getRoleId();
			if (ofHelper.isOrderPrepRequiredForRole(buyerRoleId)) {
				coRequest.getServiceOrder().setOrderPrepRequired(true);
			}

            logger.info("Create SO Mapping Completed for new order fulfillment. Now calling ofHelper.createServiceOrder for actual SO Creation");
            long start1 = System.currentTimeMillis();
            OrderFulfillmentResponse response = ofHelper.createServiceOrder(coRequest);
            long end1 = System.currentTimeMillis();
            logger.info("Create SO using Of time taken:"+(end1-start1));

            if (!response.getErrors().isEmpty()){
                return createErrorResponse(response.getErrorMessage(), ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
            }
            
            /* SL-16601 Associate the documents to the service order.
             * This will associate the attachments present in the request
             * <attachments>
             * 	<filename></filename>
             * </attachments>
             * Please note that this will not attach the documents 
             * associated with the template in the request
             * */
            String soId = response.getSoId();
            Attachments attachments = createRequest.getServiceOrder().
            	getAttachments();
            
            // Get the title list from the template
            List<String> titleList = new ArrayList<String>();
            String templateName = null;
            
            if(StringUtils.isEmpty(templateName)){
            	// Check if the order is created using SKUs
            	// If yes: Get the template name associated with the SKU
            	Skus skus  = new Skus(); 
            	skus = createRequest.getServiceOrder().getScopeOfWork().getSkus();
            	List<String> skuList = new ArrayList<String>();
            	if(null!= skus){
            		skuList = skus.getSkuList(); 	
            	}
            	List<ServiceOrderCustomRefVO> customReferences = new ArrayList<ServiceOrderCustomRefVO>();	
            	
            	if(StringUtils.isEmpty(templateName) && 
            			(null!=skuList && skuList.size()>0)){
            		// Get the template from the service order.
            		try {
						customReferences = serviceOrderBO.getCustomReferenceFields(soId);
						for(ServiceOrderCustomRefVO custRefVO :customReferences ){
							if(CREF_TEMPLATE_NAME.equalsIgnoreCase(custRefVO.getRefType())){
								templateName = custRefVO.getRefValue();
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("Error while fetching the custom references:",e);
					}
            	}
            }
            logger.info("Template Name::"+templateName);
            if(!StringUtils.isEmpty(templateName)){
            	titleList = createMapper.getDocumentsFromTemplate
            		(securityContext.getCompanyId(),templateName);
            }
            
            logger.info("Associateing the documents from the request to " +
            	"the service order :"+response.getSoId());
            invalidDocumentList = associateAllDocuments(soId, 
            		titleList,securityContext.getCompanyId(), 
            		attachments);            
            returnVal = ofServiceOrderMapper.
            	createSOResponseMapping(response.getSoId(),invalidDocumentList);
            long end = System.currentTimeMillis();
            logger.info("Create Using Of API time:"+ (end-start));
            return returnVal;
            
    }
	
	/**
	 * Attach the documents with service order.
	 * @param soId
	 * @param titleList
	 * @param ownerId
	 * @param attachments
	 * @return
	 */
	private List<String> associateAllDocuments(String soId,List<String> titleList,
			Integer ownerId,Attachments attachments){
		List<String> invalidDocumentList = new ArrayList<String>();
		List<String> existingDocumentList = new ArrayList<String>();
		/* Step 1: The documents from the template would already be associated
		 * with the service order by the OF services */ 
		if (!soId.equalsIgnoreCase(PublicAPIConstant.INVALID_SOID)
				&& (null != titleList) && titleList.size() > 0){
			// Get all file names associated with the title in the template
			for(String title:titleList){
				List<DocumentVO> documentVos = new ArrayList<DocumentVO>();
				try {
					documentVos = documentBO.retrieveDocumentsByTitleAndEntityID(
							Constants.DocumentTypes.BUYER, title, ownerId);
					if(null!=documentVos && documentVos.size()>0){
						for(DocumentVO documentVo : documentVos){
							if(!existingDocumentList.contains(documentVo.getFileName())){
								existingDocumentList.add(documentVo.getFileName());
							}
						}
					}
				} catch (Exception e) {
					logger.error("Unable to find a document for the title",  e);
				}
			}
		}
		
		// Step 2: Associate the attachments in the requests
		if (null != attachments && null != attachments.getFilenameList()
				&& (!soId.equalsIgnoreCase(PublicAPIConstant.INVALID_SOID))) {
			List<String> filenameList = attachments.getFilenameList();			
			if (!filenameList.isEmpty()) {
				if(!existingDocumentList.isEmpty() && existingDocumentList.size()>0){
					// Remove the documents which are already in the template					
					filenameList.removeAll(existingDocumentList);
				}				
				invokeDocumentList(soId, ownerId, filenameList,invalidDocumentList);
		    }
		}
			return invalidDocumentList;
	}

	/**
	 * This method is for generating the list of invalid documents in the given set of documents.
	 * @param serviceOrderId
	 * @param ownerID
	 * @param fileNameList
	 * @param invalidDocumentList
	 * @return List<String>
	 */
	private List<String> invokeDocumentList(String serviceOrderId, 
											Integer ownerID,
											List<String> fileNameList,
											List<String> invalidDocumentList) {
		invalidDocumentList = associateSOWithDocuments(serviceOrderId,
														ownerID, 
														fileNameList,
														invalidDocumentList);
		return invalidDocumentList;
	}
	
	/**
	 * This method is for associating the documents with service order.
	 * 
	 * @param serviceOrderId  String
	 * @param ownerID Integer
	 * @param fileNameList  List<String>
	 * @return List<String>
	 */
	public List<String> associateSOWithDocuments(String serviceOrderId,
												 Integer ownerID, 
												 List<String> fileNameList,
												 List<String> invalidDocumentList) {
		DocumentVO documentVo = null;		try {
			 
			 
			for (String fileName : fileNameList) {
				logger.info("Checks if the document with the same name: "
						+ fileName + " already exists for service order "+serviceOrderId);				
				
				documentVo = documentBO.retrieveDocumentByFileNameAndEntityID(
						Constants.DocumentTypes.BUYER, fileName, ownerID);
				if (null != documentVo) {
					getDocumentDetails(documentVo.getTitle(), ownerID,
							serviceOrderId, fileName,invalidDocumentList);
				} else {
					if (null != fileName) {
						if(!invalidDocumentList.contains(fileName)){
							invalidDocumentList.add(fileName);
						}
					}
					logger.info("Adding the unAssociated Document"
													+" to invalidDocumentList");
				}
			}
		} catch (Exception e) {
			logger.error("Association of Buyer Document with SoId was"
					+ " not Successful ",  e);
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
														 Integer ownerID, 
														 List<String> fileNameList,
														 List<String> invalidDocumentList) {
		try {
			for (String title : fileNameList) {
				getDocumentDetails(title, ownerID, serviceOrderId, null, invalidDocumentList);
			}
		} catch (Exception e) {
			logger.error("Association of Buyer Document with SoId was not Successful ", e);
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
	private void getDocumentDetails(String title, 
									Integer ownerID,
									String serviceOrderId, 
									String fileName,
									List<String> invalidDocumentList) {
		DocumentVO documentVo = null;
		List<DocumentVO> documents = new ArrayList<DocumentVO>();
		try {
			logger.info("Entering SOCreateService.getDocumentDetails()");
			documents = documentBO.retrieveDocumentsByTitleAndEntityID(
					Constants.DocumentTypes.BUYER, title, ownerID);
		}catch(Exception exception){
			logger.error("SOCreateService.getDocumentDetails(): " +
					"Exception occurred while retrieving document by " +
					"title and entity id ", exception);
		}
		try{
			if (null != documents && documents.size()>0) {
				Map<String,DocumentVO> fileNameMap = new HashMap<String,DocumentVO>();
				for(DocumentVO docVO : documents){
					if(StringUtils.isNotEmpty(docVO.getFileName())){
						fileNameMap.put(docVO.getFileName(),docVO);
					}
				}
				//Check if the fileName is null
				if(StringUtils.isNotEmpty(fileName)){
					logger.info("Not empty fileName::"+fileName);
					documentVo = fileNameMap.get(fileName);
				}
				if(null == documentVo){
					// For title - file name is null OR
					// For title has a different file name
					documentVo = documents.get(0);
				}
				documentVo.setDocumentId(null);
				documentVo.setSoId(serviceOrderId);
				documentVo.setCompanyId(ownerID);
				logger.info("Going to associate document with ServiceOrder");
				ProcessResponse insertDocResponse = documentBO
						.insertServiceOrderDocument(documentVo);
				if (null != insertDocResponse) {
					boolean showError = true;
					
					// Check if the document was already attached to the SO
					String responseCode = insertDocResponse.getCode();
					logger.debug("response code :::"+responseCode);
					if(!StringUtils.isEmpty(responseCode) && 
							responseCode.equalsIgnoreCase(OrderConstants.SO_DOC_UPLOAD_EXSITS)){
						// No need to show the error
						showError = false;
					}
					if(showError){
						if (!insertDocResponse.isSuccess()) {
							logger.info("Adding the unAssociated Document to " 
															+"invalidDocumentList");
							if (null != fileName) {
								if(!invalidDocumentList.contains(fileName)){
									invalidDocumentList.add(fileName);
								}
								
							}
						}
					}
					
				} else {
					if (null != fileName) {
						if(!invalidDocumentList.contains(fileName)){
							invalidDocumentList.add(fileName);
						}
					}
					logger.info("Adding the unAssociated Document to " 
														+"invalidDocumentList");
				}

			} else {
				if (null != fileName) {
					if(!invalidDocumentList.contains(fileName)){
						invalidDocumentList.add(fileName);
					}
				}else{
					if(!invalidDocumentList.contains(title)){
						invalidDocumentList.add(title);
					}
				}
				logger.info("Adding the unAssociated Document to " 
														+"invalidDocumentList");
			}
		} catch (Exception e) {
			logger.error("Exception occured in SOCreateService.getDocumentDetails(): ", e);
		}
	}

	private SOCreateResponse createGenericErrorResponse(){
		return createErrorResponse(ResultsCode.GENERIC_ERROR.getMessage(), ResultsCode.GENERIC_ERROR.getCode());
	}
	
	private SOCreateResponse createErrorResponse(String message, String code){
		SOCreateResponse createResponse = new SOCreateResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}
	
	// Setter methods for Spring Injection {References in 
	// Spring Configuration file: apiApplicationContext.xml}

	public void setCreateMapper(SOCreateMapper createMapper) {
		this.createMapper = createMapper;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public void setOfServiceOrderMapper(OFServiceOrderMapper ofServiceOrderMapper) {
		this.ofServiceOrderMapper = ofServiceOrderMapper;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	public boolean isNewOFAllowed(SecurityContext securityContext) throws DataNotFoundException{
	    boolean returnVal = false;
        if(ofHelper.isOFEnabled()){
            if(ofHelper.isBuyerAllowed(securityContext.getRoleId())){
                returnVal = StringUtils.equals("1",applicationProperties.getPropertyFromDB(Constants.OrderFulfillment.USE_NEW_ORDER_FULFILLMENT_PROCESS));
            }
        }
	    return returnVal;
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
			 * call this method to convert XML string to object
			 * 
			 * @param request
			 * @param classz
			 * @return Object
			 */
			protected Object convertXMLStringtoRespObject(String request,
					Class<?> classz) {
				XStream xstream = this.getXstream(classz);
				return (Object) xstream.fromXML(request);
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
						PublicAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
				xstream.addDefaultImplementation(java.sql.Date.class,
						java.util.Date.class);
				xstream.processAnnotations(classz);
				return xstream;
			}
		
	
}
