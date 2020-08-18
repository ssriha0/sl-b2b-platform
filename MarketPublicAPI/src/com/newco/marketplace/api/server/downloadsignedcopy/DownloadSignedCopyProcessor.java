package com.newco.marketplace.api.server.downloadsignedcopy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadResult;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadResults;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadSODocumentsResponse;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadSignedCopyResponse;
import com.newco.marketplace.api.beans.downloadsignedcopy.SODocumentsResult;
import com.newco.marketplace.api.beans.downloadsignedcopy.SignedCopyResult;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.downloadsignedcopy.DownloadSODocumentsService;
import com.newco.marketplace.api.services.downloadsignedcopy.DownloadSignedCopyService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.servicelive.common.util.ZipFileUtil;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Path("v1.0")
public class DownloadSignedCopyProcessor {
	private Logger logger = Logger.getLogger(DownloadSignedCopyProcessor.class);

	/**service Class */
	private DownloadSignedCopyService signedCopyService;
	//SL-20420
	private DownloadSODocumentsService soDocumentsService;
	
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	@Produces({ "application/octet-stream" ,"application/xml", "application/json"})
	@GET
	@Path("/buyers/{buyerId}/serviceorders/{soId}/downloadSignedCopy")
	public Response getSignedCopyDocument(@PathParam("buyerId") String buyerId,@PathParam("soId") String soIds) {
		
		if (logger.isInfoEnabled()) {
			logger.info("Entering DownloadSignedCopyProcessor getSignedCopyDocument()");
		}
		APIRequestVO apiVO  = new APIRequestVO(getHttpRequest());
		//Setting Request Parameters in API VO 
		apiVO.setRequestType(RequestType.Get);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(PublicAPIConstant.SO_ID_LIST,soIds);
		//Initialize Response with status 200
		ResponseBuilder response = Response.ok();
		//TO DO: Needs to move to a property File
		//Default Location to create ZipFile.
		String zipFileLocation = signedCopyService.getApplicationPropertyFromDB(PublicAPIConstant.DOCUMENT_ZIP_LOCATION);
		//Need to move to constant file
		String title = signedCopyService.getApplicationPropertyFromDB(PublicAPIConstant.SIGNED_COPY_DOCUMENT_TITLE);
		//Calling service class to fetch signed copy documents for all service orders
		DownloadSignedCopyResponse responseObj = signedCopyService.execute(apiVO,title);
		//Checking for Results 
		if(null != responseObj.getResults() && null != responseObj.getResults().getResults()){
			// Checking for validation errors
		        if(responseObj.getResults().isDocumentAvailable()){
			      //Getting first valid service order Id to name the Zip file
					String soIdZip = null;
					if(null != responseObj.getValidSoIdList() && (!(responseObj.getValidSoIdList().isEmpty()))
							&&StringUtils.isNotBlank(responseObj.getValidSoIdList().get(0))){
						    soIdZip =responseObj.getValidSoIdList().get(0);
					}
					String zipFileName = buyerId +"_"+ soIdZip+"_";
					//Default inilization of String Array.
					String[] srcFiles = null;
					if(null!= responseObj.getSrcFiles() && responseObj.getSrcFiles().length > 0){
						srcFiles = responseObj.getSrcFiles();
					}
					//TO DO: Validate Total File Size before creating zip file and Set proper Error Message.
					boolean isFileSizeNotEligible = validateZipFileSize(responseObj,response);
					if(isFileSizeNotEligible){
						DownloadSignedCopyResponse errorMessage=createDownloadResponse(ResultsCode.DOCUMENT_SIZE_EXCEEDS_LIMIT.getCode(),ResultsCode.DOCUMENT_SIZE_EXCEEDS_LIMIT.getMessage());
						String resultString=convertReqObjectToXMLString(errorMessage,DownloadSignedCopyResponse.class);
						Response errorResponse = Response.ok(resultString).build();
						return errorResponse;
					}
					File zipFileGenerated = null;
					try {
						zipFileGenerated = ZipFileUtil.AddToZipFile(srcFiles, zipFileLocation,zipFileName);
					} catch (IOException e) {
						logger.error("Exception in generating Zip file:" + e.getMessage());
					}
					//Setting MimeType from the file
					MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
					String mimeType = mimeTypesMap.getContentType(zipFileGenerated);
					//Creating Zip file to set in Response
					response = Response.ok((Object) zipFileGenerated);
					logger.info("File name generated is:"+ zipFileGenerated.getName());
					String fileName=zipFileGenerated.getName();
					response.header("Content-disposition", "attachment; filename="+ fileName);
					response.header("Content-Type", mimeType);
					   for(int j=0;j< responseObj.getSoIdList().size();j++){
							String soId = responseObj.getSoIdList().get(j);
							Map<String,List<SignedCopyResult>> resultMap = responseObj.getResultMap();
									if(null != resultMap && (!resultMap.isEmpty())){
										    List<SignedCopyResult> documentList = resultMap.get(soId);
										      if((null == documentList || documentList.isEmpty()) && !(responseObj.getResults().getResults().isEmpty())){
										    	  /*No document is retrieved for the service order,ie: Some validation error exists.
										    	   Iterate through error List and set error messages in response header.*/
										    	   for(DownloadResult errorResult:responseObj.getResults().getResults()){
										    		   if(soId.equals(errorResult.getSoId())){
										    			   //Service order Specific Error Messages.
										    			   if(StringUtils.isNotBlank(errorResult.getMessage())){
										    			     response.header("File"+soId+"_"+ 1 +"-Message",errorResult.getMessage());
										    			   }else{
										    				 response.header("File"+soId+"_"+ 1 +"-Message",ResultsCode.SIGNED_COPY_NOT_AVAILABLE.getMessage()); 
										    			   }
										    		   }
										    	   }
										       }else{
													//Setting meta data informations in response header from result list
										    	    for(int i=0;i < documentList.size();i++){
										    	    	int sequence = i+1;
										    	    	SignedCopyResult resultObj = documentList.get(i);
										    	    	response.header("File"+soId+"_"+ sequence +"-Title",title);
										    	    	response.header("File"+soId+"_"+ sequence +"-Message",PublicAPIConstant.DOCUMET_RETRIEVE_SUCCESS);
														response.header("File"+soId+"_"+ sequence +"-Name",resultObj.getFileName());
														//TO DO: Convert size to long and convert it into KB from service class itself
														response.header("File"+soId+"_"+ sequence +"-FileSize",FileUtils.byteCountToDisplaySize(resultObj.getFileSizeInKB()));
														response.header("File"+soId+"_"+ sequence +"-UploadedBy",resultObj.getUploadedBy());
														response.header("File"+soId+"_"+ sequence +"-UploadedDate",resultObj.getUploadedDate());
														
										    	    }
										      }
							      }
						   
						    }
			           }else if(responseObj.getResults().isOtherErrorMessage()){
				        	//Buyer Id is invalid,Service order count exceeds limit.
							  DownloadSignedCopyResponse errorMessage=createDownloadResponse(responseObj.getResults());
							  String resultString=convertReqObjectToXMLString(errorMessage,DownloadSignedCopyResponse.class);
							  Response errorResponse = Response.ok(resultString).build();
							  return errorResponse;
				       }else if(!(responseObj.getResults().isDocumentAvailable())){
			        	    // Document is not available.But service orders are valid.
			        	  for(String soId:responseObj.getSoIdList()){
			        	      //Service order Specific Error Messages.
			        	      for(DownloadResult result:responseObj.getResults().getResults()){
			        	        if(null!=soId && null!= result.getSoId() && soId.equals(result.getSoId())){
				    			      response.header("File"+soId+"_"+ 1 +"-Message",result.getMessage());
			        	           }
			        	        }
			        	  }
		              }
				}
		 return response.build();
		 
	}
	
	//SL-20420
	@Produces({ "application/octet-stream" ,"application/xml", "application/json"})
	@GET
	@Path("/buyers/{buyerId}/serviceorders/{soId}/downloadSODocuments")
	public Response getSODocuments(@PathParam("buyerId") String buyerId,@PathParam("soId") String soId) {
		
		logger.info("Entering DownloadSignedCopyProcessor getSODocuments()");
		APIRequestVO apiVO  = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		//Setting Request Parameters in API VO 
		apiVO.setRequestType(RequestType.Get);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(PublicAPIConstant.SO_ID,soId);
		apiVO.setRequestFromGetDelete(reqMap);
		//Initialize Response with status 200
		ResponseBuilder response = Response.ok();
		//Default Location to create ZipFile.
		String zipFileLocation = soDocumentsService.getApplicationPropertyFromDB(PublicAPIConstant.DOCUMENT_ZIP_LOCATION);
		//Calling service class to fetch SO documents for all service orders
		DownloadSODocumentsResponse responseObj = soDocumentsService.execute(apiVO);
		//Checking for Results 
		if(null != responseObj.getResults() && null != responseObj.getResults().getResults()){
			// Checking for validation errors
	        if(responseObj.getResults().isDocumentAvailable()){
	        	//Getting first valid service order Id to name the zip file
				String soIdZip = null;
				if(StringUtils.isNotBlank(responseObj.getValidSoId())){
					soIdZip =responseObj.getValidSoId();
				}
				String zipFileName = buyerId +"_"+ soIdZip+"_";
				//default initialization of String Array.
				String[] srcFiles = null;
				if(null!= responseObj.getSrcFiles() && responseObj.getSrcFiles().length > 0){
					srcFiles = responseObj.getSrcFiles();
				}
				//validate total file size before creating zip file and set proper error message.
				boolean isFileSizeNotEligible = validateZipFileSize(responseObj,response);
				if(isFileSizeNotEligible){
					DownloadSODocumentsResponse errorMessage = createDocDownloadResponse(ResultsCode.SO_DOCUMENT_SIZE_EXCEEDS_LIMIT.getCode(),ResultsCode.SO_DOCUMENT_SIZE_EXCEEDS_LIMIT.getMessage());
					String resultString = convertReqObjectToXMLString(errorMessage,DownloadSODocumentsResponse.class);
					Response errorResponse = Response.ok(resultString).build();
					return errorResponse;
				}
				File zipFileGenerated = null;
				try {
					zipFileGenerated = ZipFileUtil.AddToZipFile(srcFiles, zipFileLocation,zipFileName);
				} catch (IOException e) {
					logger.error("Exception in generating Zip file:" + e.getMessage());
				}
				//Setting MimeType from the file
				MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
				String mimeType = mimeTypesMap.getContentType(zipFileGenerated);
				//Creating zip file to set in Response
				response = Response.ok((Object) zipFileGenerated);
				logger.info("File name generated is:"+ zipFileGenerated.getName());
				String fileName = zipFileGenerated.getName();
				response.header("Content-disposition", "attachment; filename="+ fileName);
				response.header("Content-Type", mimeType);
		
				Map<String,List<SODocumentsResult>> resultMap = responseObj.getResultMap();
				if(null != resultMap && (!resultMap.isEmpty())){
					List<SODocumentsResult> documentList = resultMap.get(soId);
					if((null !=documentList && !documentList.isEmpty()) && !(responseObj.getResults().getResults().isEmpty())){
						//Setting meta data informations in response header from result list
						int sequence = 0;
				    	for(int j=0; j < documentList.size(); j++){
				    		sequence = j+1;
				    	    SODocumentsResult resultObj = documentList.get(j);
				    	    response.header("File "+sequence+"-Title",resultObj.getTitle());
				    	    response.header("File "+sequence+"-Message",PublicAPIConstant.DOCUMET_RETRIEVE_SUCCESS);
							response.header("File "+sequence+"-Name",resultObj.getFileName());
							response.header("File "+sequence+"-FileSize",FileUtils.byteCountToDisplaySize(resultObj.getFileSizeInKB()));
							response.header("File "+sequence+"-UploadedBy",resultObj.getUploadedBy());
							response.header("File "+sequence+"-UploadedDate",resultObj.getUploadedDate());
				    	}
					}
				}
	        }else if(responseObj.getResults().isOtherErrorMessage() || !(responseObj.getResults().isDocumentAvailable())){
	        	
				DownloadSignedCopyResponse errorMessage=createDownloadResponse(responseObj.getResults());
				String resultString=convertReqObjectToXMLString(errorMessage,DownloadSignedCopyResponse.class);
				Response errorResponse = Response.ok(resultString).build();
				return errorResponse;
	       }
		}
		return response.build();
		
	}
	
	private boolean validateZipFileSize(DownloadSignedCopyResponse responseObj, ResponseBuilder response) {
		boolean isNotValid = false;
		long actualFileSizeInKB;
		//TO DO : Move to property/constants--->>>
		String sizeinBytes = signedCopyService.getApplicationPropertyFromDB(PublicAPIConstant.FILE_SIZE_LIMIT);
		long sizeinKBLimit = new Long(sizeinBytes);
		actualFileSizeInKB =responseObj.getTotalFileSizeInKB();
		if(actualFileSizeInKB > sizeinKBLimit){
			isNotValid = true;
		}
		return isNotValid;
	}
	
	//SL-20420
	private boolean validateZipFileSize(DownloadSODocumentsResponse responseObj, ResponseBuilder response) {
		boolean isNotValid = false;
		long actualFileSizeInKB;
		String sizeinBytes = soDocumentsService.getApplicationPropertyFromDB(PublicAPIConstant.FILE_SIZE_LIMIT);
		long sizeinKBLimit = new Long(sizeinBytes);
		actualFileSizeInKB =responseObj.getTotalFileSizeInKB();
		if(actualFileSizeInKB > sizeinKBLimit){
			isNotValid = true;
		}
		return isNotValid;
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
		xstream.registerConverter(new DateConverter(PublicAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	/**
	 * @param code
	 * @param message
	 * @return
	 */
	protected DownloadSignedCopyResponse createDownloadResponse(String code,String message){
		DownloadSignedCopyResponse errorResponse=new DownloadSignedCopyResponse();
		DownloadResults result=DownloadResults.getError(message, code);
		errorResponse.setResults(result);
		return errorResponse;
	}
	
	/**SL-20420
	 * @param code
	 * @param message
	 * @return
	 */
	protected DownloadSODocumentsResponse createDocDownloadResponse(String code,String message){
		DownloadSODocumentsResponse errorResponse=new DownloadSODocumentsResponse();
		DownloadResults result=DownloadResults.getError(message, code);
		errorResponse.setResults(result);
		return errorResponse;
	}
	/**
	 * @param results
	 * @return
	 */
	protected DownloadSignedCopyResponse createDownloadResponse(DownloadResults results){
		DownloadSignedCopyResponse errorResponse=new DownloadSignedCopyResponse();
		DownloadResults result=DownloadResults.getAllError(results.getResults());
		errorResponse.setResults(result);
		return errorResponse;
	}
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public DownloadSignedCopyService getSignedCopyService() {
		return signedCopyService;
	}

	public void setSignedCopyService(DownloadSignedCopyService signedCopyService) {
		this.signedCopyService = signedCopyService;
	}

	public DownloadSODocumentsService getSoDocumentsService() {
		return soDocumentsService;
	}

	public void setSoDocumentsService(DownloadSODocumentsService soDocumentsService) {
		this.soDocumentsService = soDocumentsService;
	}

}
