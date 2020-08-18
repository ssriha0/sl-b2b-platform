package com.newco.batch.lmscredentials.integration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.businessImpl.cache.CredentialCache;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.hi.IProviderResourceBO;
import com.newco.marketplace.business.iBusiness.lmscredential.IlmsCredentialFileUploadService;
import com.newco.marketplace.business.iBusiness.provider.ITeamCredentialBO;
import com.newco.marketplace.business.iBusiness.provider.IVendorCredentialBO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.LmsCredUploadConstants;
import com.newco.marketplace.utils.ValidationUtils;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.servicelive.domain.lms.LmsFileProcessingErrorDTO;
import com.servicelive.domain.lms.LmsFileUploadDTO;

/**
 * This class runs batch for LMS credential integration job. It checks all pending LMS credential files from 
 * file_upload_details table and inserts all credentials in vendor_credential and resource_credentials appropriately 
 * from all files with PENDING status. It also validates credential records and inserts error messages in file_
 * processing_error tables if any.
 */
public class LMSIntegrationProcessor extends ABatchProcess {

	private ITeamCredentialBO teamCredBO;
	private IVendorCredentialBO vendorCredBO;
	private IProviderResourceBO providerResourceBO;
	private IBuyerBO buyerBO;
	private IlmsCredentialFileUploadService lmsCredUploadScervice;
	private CredentialCache credentialCache;

/*	Map<String, Integer> luMapResCredCategory = new HashMap<String, Integer>();
	Map<String, Integer> luMapResCredType = new HashMap<String, Integer>();
	Map<String, Integer> luMapVenCredCategory = new HashMap<String, Integer>();
	Map<String, Integer> luMapVenCredType = new HashMap<String, Integer>();*/
	private final String cvsSplitBy = ",";
    private  String headerLine="";

	private static final Logger logger = Logger
			.getLogger(LMSIntegrationProcessor.class.getName());

	@Override
	public void setArgs(String[] args) {
	}

	@Override
	public int execute() throws com.newco.marketplace.exception.core.BusinessServiceException {
	
		LmsFileUploadDTO lmsFileUploadDTO = new LmsFileUploadDTO();
		List<LmsFileUploadDTO> uploadedFileDTOList = null;
		Buyer buyer;
		String buyerName = null;
		try {
			// skip batch if a file is already in process
			lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_PROCESSING);
			logger.info("Searching for LMS Credential file with status: "+LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_PROCESSING);
			uploadedFileDTOList = lmsCredUploadScervice.getFileContent(lmsFileUploadDTO);
			if (!CollectionUtils.isEmpty(uploadedFileDTOList)) {
				logger.warn(uploadedFileDTOList.size() + " file(s) is(are) already in process. "
						+ "So batch will schedule for next execution time.");
				return 1;
			}
			
			//get all look up data and save it in singleton map.
			//getLookUpData();
			//fetch all files from DB with status = PENDING
			lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_UPLOADED);
			logger.info("Searching for LMS Credential file with status: "+LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_UPLOADED);
			uploadedFileDTOList = lmsCredUploadScervice.getFileContent(lmsFileUploadDTO);
		}catch(DataServiceException dse){
			logger.error("LMS credential upload exception in getting file content from DB due to exception: "+dse);
			throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
		}catch(Exception exp){
			logger.error("LMS credential upload general exception for lookup: "+exp);
			throw new com.newco.marketplace.exception.core.BusinessServiceException(exp);
		}
		logger.info("No of file for LMS credential insert process := "+uploadedFileDTOList.size());
        BufferedReader br;
		int totalRecords;
		int errorRecords;
		for (LmsFileUploadDTO ilmsFileDto : uploadedFileDTOList) {
			try{
				ilmsFileDto.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_PROCESSING);
				lmsCredUploadScervice.saveOrUpdateFileDetails(ilmsFileDto);
				lmsFileUploadDTO = ilmsFileDto;
				totalRecords = 0;
				errorRecords = 0;
				File lmsFile = ilmsFileDto.getFileByteToText();
				br = new BufferedReader(new FileReader(lmsFile));
				if(lmsFile.length()==0L || br == null){
					if (br != null) {
						br.close();
					}
					throw new BusinessServiceException("LMS credential file does not have any record: "+lmsFileUploadDTO.getFileName());
				}
				// SL-21142
				/*if(lmsFileUploadDTO.getBuyerId() != null){
					buyerName = (buyer = buyerBO.getBuyerName(lmsFileUploadDTO.getBuyerId())) != null ? buyer.getBusinessName() : null;
				}else{
					buyerName = null;
				}*/
				//read each line and insert/upload each single credential 
				logger.info("Starting batch insert of LMS credential for file: "+lmsFile.getName());
	            headerLine = br.readLine();
				String line = "";
				while ((line = br.readLine()) != null) {
					line = line.replaceAll("\"", "");
					if (StringUtils.isEmpty(line)) {
						continue;
					}
					totalRecords++;
					LmsFileProcessingErrorDTO lmsFileProcessingErrorDTO = validateLMSCred(line);
					if (lmsFileProcessingErrorDTO == null) {
						String[] firmOrProviderDetails = line.split(cvsSplitBy);
						try {
							// SL-21142
							buyerName = ValidationUtils.isInteger(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_BUYER_ID]) 
									? ((buyer = buyerBO.getBuyerName(Integer.parseInt(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_BUYER_ID]))) != null 
										?  buyer.getBusinessName() : null) 
									: (LmsCredUploadConstants.SERVICE_LIVE.equalsIgnoreCase(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_BUYER_ID]) 
										? LmsCredUploadConstants.SERVICE_LIVE : null);
									
							logger.info("Fetched buyer from DB: "+ buyerName);
							// - end SL-21142
							
							if (!StringUtils.isEmpty(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_RES_ID])) {
								TeamCredentialsVO teamCredVo = new TeamCredentialsVO();
								String credType = LmsCredUploadConstants.SERVICE_LIVE.equalsIgnoreCase(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_BUYER_ID]) 
										? LmsCredUploadConstants.SL_BASED : LmsCredUploadConstants.CLIENT_BASED;
								teamCredVo.setCategoryId(credentialCache.getResourceCredentialCategoryId(credType, firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_CATEGORY]).getCatId());
								//teamCredVo.setCategoryId(luMapResCredCategory.get(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_CATEGORY]));
								// SL-21142
								teamCredVo.setTypeId(credentialCache.getResCredType(credType).getTypeId());
								// - end
								teamCredVo.setLicenseName(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_CATEGORY]);
								teamCredVo.setResourceId(Integer.valueOf(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_RES_ID]));
								teamCredVo.setIssueDate(DateUtils.getDateFromString(
										firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_IDATE],
										LmsCredUploadConstants.DATE_FORMT_MM_DD_YYYY_HH_MM));
								teamCredVo.setIsFileUploaded(true);
								teamCredVo.setIssuerName(buyerName);
								Integer teamCredID = teamCredBO.checkTeamCredExists(teamCredVo);
								if(teamCredID != null){
									teamCredVo.setModifiedDate(new Date());
									teamCredVo.setResourceCredId(teamCredID);
									teamCredBO.updateCredentials(teamCredVo);
									logger.info("Cred id found for record. Updating record: "+ teamCredID);
								}else{
									teamCredBO.insertCredentials(teamCredVo);
									logger.info("Cred id NOT found for record. Inserting new record: "+ teamCredVo.getResourceCredId());
								}
							} else if (!StringUtils.isEmpty(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_VEN_ID])) {
								VendorCredentialsVO vendorCredVo = new VendorCredentialsVO();
								String credType =LmsCredUploadConstants.SERVICE_LIVE.equalsIgnoreCase(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_BUYER_ID]) 
										? LmsCredUploadConstants.SL_BASED : LmsCredUploadConstants.CLIENT_BASED;
								vendorCredVo.setCategoryId(credentialCache.getVendorCredentialCategoryId(credType, firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_CATEGORY]).getCatId());
								//vendorCredVo.setCategoryId(luMapVenCredCategory.get(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_CATEGORY]));
								// SL-21142
								/*vendorCredVo.setTypeId(LmsCredUploadConstants.SL_BASED.equalsIgnoreCase(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_BUYER_ID]) 
										? luMapVenCredType.get(LmsCredUploadConstants.SL_BASED) : luMapVenCredType.get(LmsCredUploadConstants.CLIENT_BASED));
								*/// - end
								vendorCredVo.setTypeId(credentialCache.getVendorCredType(credType).getTypeId());
								vendorCredVo.setVendorId(Integer.valueOf(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_VEN_ID]));
								vendorCredVo.setName(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_CATEGORY]);
								vendorCredVo.setIssueDate(new java.sql.Date(
										DateUtils.getDateFromString(firmOrProviderDetails[LmsCredUploadConstants.LMS_CRED_IDATE],
										LmsCredUploadConstants.DATE_FORMT_MM_DD_YYYY_HH_MM).getTime()));
								vendorCredVo.setIsFileUploaded(true);
								vendorCredVo.setSource(buyerName);
								Integer vendorCredID = vendorCredBO.checkVendorCredExists(vendorCredVo);
								if(vendorCredID != null){
									vendorCredVo.setModifiedDate(new Date().toString());
									vendorCredVo.setVendorCredId(vendorCredID);
									vendorCredBO.updateCredentials(vendorCredVo);
									logger.info("Cred id found for record. Updating record: "+ vendorCredID);
								}else{
									vendorCredBO.insertCredentials(vendorCredVo);
									logger.info("Cred id NOT found for record. Inserting new record: "+ vendorCredVo.getVendorCredId());
								}
							}
						} catch (BusinessServiceException exp) {
							errorRecords++;
							logger.error("Exception occured in inserting//updating lms credential record: ", exp);
							LmsFileProcessingErrorDTO lmsFileProcessingExceptionDTO = new LmsFileProcessingErrorDTO();
							lmsFileProcessingExceptionDTO.setRecordText(line);
							lmsFileProcessingExceptionDTO.setFailureException(exp.getMessage());
							lmsFileProcessingExceptionDTO.setFailureReason(exp.getMessage());
							lmsFileProcessingExceptionDTO.setLmsFileUploadDTO(lmsFileUploadDTO);
							try{
								// sl-21142
								// updating error count
								lmsFileUploadDTO.setErrorRecordsCnt(errorRecords);
								lmsFileUploadDTO.setRowCount(totalRecords);
								lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
								// end
								
								// updating error in error table
								lmsCredUploadScervice.insertLmsErrorDetails(lmsFileProcessingExceptionDTO);
							}catch(DataServiceException dse){
								logger.error("Failed to insert error records for lmscredential due to : ", dse);
								throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
							}
							
							logger.info("Inserted error details in table. Total error count till now : "+ errorRecords + " for : " + lmsFile.getName());
						}
					} else {
						errorRecords++;
						
						// sl-21142
						// updating error count
						lmsFileUploadDTO.setErrorRecordsCnt(errorRecords);
						lmsFileUploadDTO.setRowCount(totalRecords);
						lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
						// -end
						
						// updating error in error table
						lmsFileProcessingErrorDTO.setLmsFileUploadDTO(lmsFileUploadDTO);
						lmsCredUploadScervice.insertLmsErrorDetails(lmsFileProcessingErrorDTO);
						
						logger.info("Inserted error details in table. Total error count till now : "+ errorRecords + " for : " + lmsFile.getName());
					}
				}
				if (br != null) {
						br.close();
				}
				
				if(errorRecords == 0){
					lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_PROCESSED);
				}else{
					lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_PROCESSED_ERROR);
				}
				logger.info("Setting file: "+ lmsFile.getName() +" status to: "+ lmsFileUploadDTO.getFileStatus());
				lmsFileUploadDTO.setErrorRecordsCnt(errorRecords);
				lmsFileUploadDTO.setRowCount(totalRecords);
				lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
				logger.info("Completed processing batch insert of LMS credential for file: "+lmsFile.getName()+" with total records: "+totalRecords+" and error records: "+errorRecords);
			}
			catch (FileNotFoundException e) {
				logger.error("LMS file is not valid or not accessible for read operation: ", e);
				lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_FAILED);
				try{
					lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
				}catch(DataServiceException dse){
					logger.error("failed to update lmscredential File status: ", dse);
					throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
				}
			} catch (IOException ex) {
				logger.error("LMS file reading IO operation throw excetpion: " , ex);
				lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_FAILED);
				try{
					lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
				}catch(DataServiceException dse){
					logger.error("failed to update lmscredential File status: ", dse);
					throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
				}
			} catch (BusinessServiceException e) {
				logger.error("LMS Credentiial upload job file is empty or problem with fetching buyer name : " , e);
				lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_FAILED);
				try{
					lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
				}catch(DataServiceException dse){
					logger.error("failed to update lmscredential File status: ", dse);
					throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
				}
			} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
				logger.error("LMS Credentiial upload job validation not performed due to exception : " , e);
				lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_FAILED);
				try{
					lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
				}catch(DataServiceException dse){
					logger.error("failed to update lmscredential File status: ",dse);
					throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
				}
			} catch (DataServiceException e){
				logger.error("LMS credential upload exception in updating error records : ",e);
				lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_FAILED);
				try{
					lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
				}catch(DataServiceException dse){
					logger.error("failed to update  File status: ",dse);
					throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
				}
			} catch (Exception e){
				logger.error("LMS credential upload general exception in insert: ",e);
				lmsFileUploadDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_FAILED);
				try{
					lmsCredUploadScervice.saveOrUpdateFileErrorCountDetails(lmsFileUploadDTO);
				}catch(DataServiceException dse){
					logger.error("failed to update  File status: ",dse);
					throw new com.newco.marketplace.exception.core.BusinessServiceException(dse);
				}
			}
		}
		return 1;
	}

	/*void getLookUpData() throws BusinessServiceException {
		logger.info("Getting lookup data for LMS file upload job.");
		if (luMapVenCredCategory.isEmpty()) {
			List<VendorCredentialsLookupVO> vendorCredLookUpVOList;
			if (luMapVenCredType.isEmpty()) {
				vendorCredLookUpVOList = vendorCredBO.getTypeList();
				for (VendorCredentialsLookupVO tvo : vendorCredLookUpVOList) {
					luMapVenCredType.put(tvo.getType(), tvo.getTypeId());
				}
				VendorCredentialsLookupVO teamCredLookUpVO = new VendorCredentialsLookupVO();
				teamCredLookUpVO.setTypeId(luMapVenCredType.get(LmsCredUploadConstants.CLIENT_BASED));
				vendorCredLookUpVOList = vendorCredBO.getCatListByTypeId(teamCredLookUpVO);
				for (VendorCredentialsLookupVO tvo : vendorCredLookUpVOList) {
					luMapVenCredCategory.put(tvo.getCategory(), tvo.getCatId());
				}
			}
		}

		if (luMapResCredCategory.isEmpty()) {
			List<TeamCredentialsLookupVO> teamCredLookUpVOList;
			if (luMapResCredType.isEmpty()) {
				teamCredLookUpVOList = teamCredBO.getTypeList();
				for (TeamCredentialsLookupVO tvo : teamCredLookUpVOList) {
					luMapResCredType.put(tvo.getType(), tvo.getTypeId());
				}
				TeamCredentialsLookupVO teamCredLookUpVO = new TeamCredentialsLookupVO();
				teamCredLookUpVO.setTypeId(luMapResCredType.get(LmsCredUploadConstants.CLIENT_BASED));
				teamCredLookUpVOList = teamCredBO.getCatListByTypeId(teamCredLookUpVO);
				for (TeamCredentialsLookupVO tvo : teamCredLookUpVOList) {
					luMapResCredCategory.put(tvo.getCategory(), tvo.getCatId());
				}
			}
		}
	}*/

	LmsFileProcessingErrorDTO validateLMSCred(String line)
			throws com.newco.marketplace.exception.core.BusinessServiceException {
		logger.info("Validating file for field errors");
		LmsFileProcessingErrorDTO lmsErrDTO = null;
        int recordCount=0;
		StringBuilder errorMsg = new StringBuilder();
		String[] credRawVO = StringUtils.splitPreserveAllTokens(line, cvsSplitBy);
		headerLine = headerLine.replaceAll("\"", "");
		String[] headerData=StringUtils.splitPreserveAllTokens(headerLine, cvsSplitBy);

	    //validation: Record is blank.
		for(int record=0;record<credRawVO.length;record++){
			if(StringUtils.isBlank(credRawVO[record])){
				recordCount++;
			}else{
				break;
			}
		}
		//validation:minimum 8 columns are not present.
		if (credRawVO.length<=LmsCredUploadConstants.LMS_CRED_VEN_ID) {
			errorMsg.append("Either record is blank or missing one or more columns, ");
		} else {
			
			//validation: category is missing.
			if (StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_CATEGORY])) {
				errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_CATEGORY]+"' column is missing in record, ");
			}
			//validation: credential status not TRUE or empty.
			if (StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_FLG])
					|| !credRawVO[LmsCredUploadConstants.LMS_CRED_FLG].equalsIgnoreCase("TRUE")) {
				errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_FLG]+"' column is not valid, ");
			}
			//validation: resource id and firm id both empty.
			if (StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_RES_ID])
					&& (credRawVO.length<=LmsCredUploadConstants.LMS_CRED_VEN_ID
					|| StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_VEN_ID]))) {
				errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_RES_ID]+"' and '"+headerData[LmsCredUploadConstants.LMS_CRED_VEN_ID]+"' both not present, ");
			}
			//validation: resource id and firm id both available.
			// SL-21142
			/*if(credRawVO.length>LmsCredUploadConstants.LMS_CRED_VEN_ID){
				if (!StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_RES_ID])
						&& !StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_VEN_ID])) {
					errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_RES_ID]+"' and '"+headerData[LmsCredUploadConstants.LMS_CRED_VEN_ID]+"' both available, ");
				}
			}*/
			//validation: when resource id is present but resource id is not integer and not matching in DB
			if (!StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_RES_ID])) {
				if (!ValidationUtils.isInteger(credRawVO[LmsCredUploadConstants.LMS_CRED_RES_ID])) {
					errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_RES_ID]+"' is not Integer, ");
				} else if (!providerResourceBO.validateResourceID(Integer.valueOf(credRawVO[LmsCredUploadConstants.LMS_CRED_RES_ID]))) {
					errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_RES_ID]+"' [" + credRawVO[LmsCredUploadConstants.LMS_CRED_RES_ID]
							+ "] is not available in DB, ");
				}
			}
			//validation: when firm id is present but firm id is not integer or not matching in DB
			if(credRawVO.length>LmsCredUploadConstants.LMS_CRED_VEN_ID){	
				if (!StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_VEN_ID])) {
					if (!ValidationUtils.isInteger(credRawVO[LmsCredUploadConstants.LMS_CRED_VEN_ID])) {
						errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_VEN_ID]+"'/FirmId is not Integer, ");
					} else if (!providerResourceBO.validateFirmID(Integer.valueOf(credRawVO[LmsCredUploadConstants.LMS_CRED_VEN_ID]))) {
						errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_VEN_ID]+"': [" + credRawVO[LmsCredUploadConstants.LMS_CRED_VEN_ID]
								+ "] is not available in DB, ");
					}
				}
			}
			//validation: date if date not present, not valid or not appropriate.
			if (StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_IDATE])) {
				errorMsg.append("Value for '"+headerData[LmsCredUploadConstants.LMS_CRED_IDATE]+"' is not present in record, ");
			} else if (!DateUtils.isValidDate(credRawVO[LmsCredUploadConstants.LMS_CRED_IDATE],LmsCredUploadConstants.DATE_FORMT_MM_DD_YYYY_HH_MM)) {
				errorMsg.append("Value for '"+headerData[LmsCredUploadConstants.LMS_CRED_IDATE]+"' is not valid for Date Format: MM/DD/YYYY HH:MM, ");
			} else if (!DateUtils.isFromDateLesserThanCurrentDate(credRawVO[LmsCredUploadConstants.LMS_CRED_IDATE],LmsCredUploadConstants.DATE_FORMT_MM_DD_YYYY_HH_MM)) {
				errorMsg.append("Value for '"+headerData[LmsCredUploadConstants.LMS_CRED_IDATE]+"' is greater than current date: " + new Date()+", ");
			}
			
			// SL-21142
			// validation if buyer id is valid.
			if(StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_BUYER_ID])) {
				errorMsg.append("Value for '"+headerData[LmsCredUploadConstants.LMS_CRED_BUYER_ID]+"' is blank, ");
			} else if (ValidationUtils.isInteger(credRawVO[LmsCredUploadConstants.LMS_CRED_BUYER_ID]) 
					&& null == buyerBO.getBuyer(Integer.parseInt(credRawVO[LmsCredUploadConstants.LMS_CRED_BUYER_ID]))) {
				errorMsg.append("Value for '"+headerData[LmsCredUploadConstants.LMS_CRED_BUYER_ID]+"' is not valid, ");
			} else{
				String credType = LmsCredUploadConstants.SERVICE_LIVE.equalsIgnoreCase(credRawVO[LmsCredUploadConstants.LMS_CRED_BUYER_ID]) 
						? LmsCredUploadConstants.SL_BASED : LmsCredUploadConstants.CLIENT_BASED;
				//If resource id is present - so validating category for resource 
				if(!StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_RES_ID])){
					if (!StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_CATEGORY])
							&& credentialCache.getResourceCredentialCategoryId(credType, credRawVO[LmsCredUploadConstants.LMS_CRED_CATEGORY]) == null) 
					{
						errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_CATEGORY]+"': [" + credRawVO[LmsCredUploadConstants.LMS_CRED_CATEGORY] 
								+ "] not present in DB for resource in type: "	+ credType+", ");
					}
				}//If resource id is not present and vendor id is present - so validating category for vendor 
				else if(!StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_VEN_ID])){
					if (!StringUtils.isEmpty(credRawVO[LmsCredUploadConstants.LMS_CRED_CATEGORY]) 
							&& credentialCache.getVendorCredentialCategoryId(credType, credRawVO[LmsCredUploadConstants.LMS_CRED_CATEGORY]) == null) 
					{
						errorMsg.append("'"+headerData[LmsCredUploadConstants.LMS_CRED_CATEGORY]+"': [" + credRawVO[LmsCredUploadConstants.LMS_CRED_CATEGORY] 
								+ "] not present in DB for vendor in type: "	+ credType+", ");
					}
				}
			}
			// - end
		}

		if (errorMsg.length() > 0) {
			lmsErrDTO = new LmsFileProcessingErrorDTO();
			if(recordCount>=7){
				errorMsg.setLength(0);
				errorMsg.append("Record is Blank");
				lmsErrDTO.setFailureReason(errorMsg.toString());
				lmsErrDTO.setRecordText(line);
			}else{
				lmsErrDTO.setFailureReason(errorMsg.toString());
				lmsErrDTO.setRecordText(line);
			}
			logger.error(lmsErrDTO.getFailureReason() + ": Error occured while validating data : "+ Arrays.toString(credRawVO));
		}
		return lmsErrDTO;
	}


	public ITeamCredentialBO getTeamCredBO() {
		return teamCredBO;
	}

	public void setTeamCredBO(ITeamCredentialBO teamCredBO) {
		this.teamCredBO = teamCredBO;
	}

	public IVendorCredentialBO getVendorCredBO() {
		return vendorCredBO;
	}

	public void setVendorCredBO(IVendorCredentialBO vendorCredBO) {
		this.vendorCredBO = vendorCredBO;
	}

	public IProviderResourceBO getProviderResourceBO() {
		return providerResourceBO;
	}

	public void setProviderResourceBO(IProviderResourceBO providerResourceBO) {
		this.providerResourceBO = providerResourceBO;
	}

	public IlmsCredentialFileUploadService getLmsCredUploadScervice() {
		return lmsCredUploadScervice;
	}

	public void setLmsCredUploadScervice(
			IlmsCredentialFileUploadService lmsCredUploadScervice) {
		this.lmsCredUploadScervice = lmsCredUploadScervice;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public CredentialCache getCredentialCache() {
		return credentialCache;
	}

	public void setCredentialCache(CredentialCache credentialCache) {
		this.credentialCache = credentialCache;
	}
	
}
