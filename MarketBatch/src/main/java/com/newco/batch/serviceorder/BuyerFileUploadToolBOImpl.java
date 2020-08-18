package com.newco.batch.serviceorder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyerFileUploadTool.IBuyerFileUploadToolBO;
import com.newco.marketplace.business.iBusiness.provider.IEmailTemplateBO;
import com.newco.marketplace.buyerUploadScheduler.UploadToolConstants;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.EmailVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.BuyerRefTypeVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.LocationClassVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.LookUpPartsSuppliedVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.LookupShippngCarrierVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.PhoneClassVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.PhoneTypeVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.ServiceTypeTemplateVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.SkillTreeVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditSuccessVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.dto.vo.integration.IntegrationBatchVO;
import com.newco.marketplace.dto.vo.integration.IntegrationTransactionVO;
import com.newco.marketplace.persistence.iDao.buyerFileUpload.IBuyerFileUploadDao;
import com.newco.marketplace.persistence.iDao.buyerFileUploadTool.IBuyerFileUploadToolDAO;
import com.newco.marketplace.persistence.iDao.integration.IIntegrationDAO;
import com.newco.marketplace.util.buyerFileUploadTool.IBuyerFileUploadToolParser;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.validator.buyerFileUploadTool.IBuyerFileUploadToolValidator;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.orderfulfillment.client.OFHelper;

public class BuyerFileUploadToolBOImpl implements IBuyerFileUploadToolBO, UploadToolConstants {

	protected final Log logger = LogFactory.getLog(getClass());
	private IBuyerFileUploadToolDAO BFUTDaoImpl = null;
	private IBuyerFileUploadToolValidator BFUTValidatorImpl = null;
	private IBuyerFileUploadToolParser BFUTParserImpl = null;
	private IIntegrationDAO integrationDaoImpl = null;
	private IBuyerFileUploadDao buyerFileUploadDao = null;
	private IEmailTemplateBO emailTemplateBean;
	private HashMap<String, Object> lookUpMap = new HashMap<String, Object>();
	private OFHelper ofHelper;
	private IApplicationProperties applicationProperties;

	/* get all unprocessed files from the audit table and process it */
	public void StartFileToSOProcessing() {

		List<UploadFileVO> fileList = BFUTDaoImpl.getFileToCreateSo();
		if (fileList == null || fileList.isEmpty()) {
			logger.info("No file to process; exiting now.");
			return;
		}
		populateLookupMap();
		String fileDir = null;
		try {
			fileDir = applicationProperties.getPropertyValue("file_upload_dir");
		} catch (DataNotFoundException e) {
			logger.error("could not get the file dir property from database using default" , e);
			fileDir = "/appl/sl/fileUpload/indata"; //default value
		}
        //check if the application property use_new_orderfulfillment_process is turned on or not
        boolean isNewOFAllowed = isNewOFAllowed();

		/* Get each file and process it */
		for (UploadFileVO uploadFileVO : fileList) {
			if(ofHelper.isOFEnabled() && isNewOFAllowed){// && uploadFileVO.getBuyer_id() == BuyerConstants.FACILITIES_BUYER_ID){
				//create a file and move on
				//ESB will take care of the file processing
				boolean success = new File(fileDir).mkdir();
				if (success) {
					logger.info("Directory was created successfully");
				} else {
					logger.info("Directory already exists");
				}
				String filename = fileDir + "/" + generateFileName(uploadFileVO);
				File outputFile = new File(filename);
				try {
					FileUtils.writeByteArrayToFile(outputFile, uploadFileVO.getfile_content());
					BFUTDaoImpl.markAsSentToESB(uploadFileVO.getFile_id());
				} catch (IOException e) {
					logger.error("Could not write data to file " + filename);
					continue;
				}
			} else {
				processSOFile(uploadFileVO);
			}
	        markFileAsRead(uploadFileVO.getName_of_file());
		}
	}
	
	private String generateFileName(UploadFileVO uploadFileVO) {
		return uploadFileVO.getFile_id() + "_" + uploadFileVO.getBuyer_resource_id() + ".xls";
	}

    /* get a file and process it */
    public void processSOFile(UploadFileVO uploadFileVO) {
        logger.info("inside prcoessfile");
        long startTime = System.currentTimeMillis();
        // Setup BuyerInfoMap and put it in lookupMaps
        int buyerId=uploadFileVO.getBuyer_id();
        int contactId=BFUTDaoImpl.getBuyerContactId(buyerId);
        String fileName=uploadFileVO.getName_of_file();
        HashMap buyerInfoMap = new HashMap();
        buyerInfoMap.put("buyerResourceId", Integer.toString(uploadFileVO.getBuyer_resource_id()));
        buyerInfoMap.put("buyerId", Integer.toString(buyerId));
        buyerInfoMap.put("buyerResContactId", Integer.toString(uploadFileVO.getBuyer_resource_contact_id()));
        buyerInfoMap.put("fileName", fileName);
        buyerInfoMap.put("contactId", contactId);
		buyerInfoMap.put("creatorUserName", uploadFileVO.getCreatorUserName());
	
        logger.info("buyerResourceId is"+uploadFileVO.getBuyer_resource_id());
        logger.info("buyerResContactId is"+uploadFileVO.getBuyer_resource_contact_id());
        String email = BFUTDaoImpl.getUserEmail(buyerId);
        buyerInfoMap.put("email", email);
        lookUpMap.put("buyerInfoMap", buyerInfoMap);

		/* buyer reference look up */
        HashMap cusRefMap = (HashMap)lookUpMap.get("cusRefMap"+buyerId);
        if (cusRefMap == null) {
	        List<BuyerRefTypeVO> cusRefList = BFUTDaoImpl.getBuyerReferences(buyerId);
			cusRefMap = new HashMap();
			cusRefMap.put("cusRefMap"+buyerId, cusRefList);
			lookUpMap.put("cusRefMap"+buyerId, cusRefMap);
        }
		logger.info("cusref map" + cusRefMap);
		
        //
        List docIdList = BFUTDaoImpl.getDocIdValLookup(buyerId);
        Iterator docIter = docIdList.iterator();
		HashMap docIdMap = new HashMap();
        while (docIter.hasNext()) {
            DocumentVO docObj1 = (DocumentVO) docIter.next();
            docIdMap.put(docObj1.getTitle().trim().toUpperCase(), docObj1.getDocumentId());
        }
        lookUpMap.put("docInfoMap", docIdMap);
        
        //
        List logoIdList = BFUTDaoImpl.getLogoIdValLookup(buyerId);
        Iterator logoIter = logoIdList.iterator();
		HashMap logoIdMap = new HashMap();
        while (logoIter.hasNext()) {
            DocumentVO docObj2 = (DocumentVO) logoIter.next();
            logoIdMap.put(docObj2.getTitle().trim().toUpperCase(), docObj2.getDocumentId());
        }        
        lookUpMap.put("logoInfoMap", logoIdMap);
        // Read file to Map
        byte[] fileContent = uploadFileVO.getfile_content();
		List<Map<String, String>> soMapList = null;
		try {
			soMapList = BFUTParserImpl.parseBuyerXLSFile(fileContent);
	        long endTime = System.currentTimeMillis();
	        logger.error("Timer::Time taken to do extra lookups and parse XLS: "+fileName+" : " + (endTime-startTime)/1000 + " seconds");
	        System.err.println("Timer::Time taken to do extra lookups and parse XLS: "+fileName+" : " + (endTime-startTime)/1000 + " seconds");
		} catch (Exception ex) {
			logger.error("Unexpected error while parsing the uploaded xls file: "+fileName, ex);
		}
        
        int fileId = uploadFileVO.getFile_id();
        if(soMapList != null)
        {
            // Iterate map to get SOs, validate and inject SOs
        	SecurityContext securityContext =  ServiceOrderUtil.getSecurityContextForBuyer(buyerId);
            Iterator<Map<String, String>> soMapIterator = null;
            int soCount=0;
            soMapIterator = soMapList.iterator();
            while (soMapIterator.hasNext()) {
                soCount++;
                Map<String, String> soMap = soMapIterator.next();               
                // Validate and Inject SO
                BFUTValidatorImpl.validateAndSubmit(soMap, lookUpMap, fileId, soCount,securityContext);
            }
            logger.info("Total # of Records in "+fileName+" is "+soCount);
        }
        else
        {
        	try {
	            OutputStream os = null;
	            os = new FileOutputStream(uploadFileVO.getName_of_file() + ".xls");
	            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
	            int bytesRead = 0;
	            while ((bytesRead = is.read(fileContent)) != -1) {
	                os.write(fileContent, 0, bytesRead);
	            }
        	} catch (IOException ioEx) {
        		logger.error("Could not create the email attachment excel file because of IO Issues!", ioEx);
        	}
            File fileObj = new File(uploadFileVO.getName_of_file() + ".xls");
            List<String> errorList = new ArrayList<String>();
            errorList.add("File not processed due to incorrect number of columns. Please rectify and upload again.");
            UploadAuditErrorVO uAEVO = BFUTValidatorImpl.getUploadErrorObject(fileId, 1, errorList, null);
            BFUTDaoImpl.errorInsert(uAEVO);
            EmailVO emailVO = new EmailVO();
            emailVO.setFrom("support@servicelive.com");
            emailVO.setTo(buyerInfoMap.get("email").toString());
            emailVO.setSubject("Service Order Import Tool - Incorrect file uploaded");
            emailVO.setMessage("The attached excel file \"" + uploadFileVO.getName_of_file() + "\" was not processed due to incorrect format or number of columns.\r\nPlease correct the .xls file and retry.");
            try {
				emailTemplateBean.sendGenericEmailWithAttachment(emailVO,fileObj,uploadFileVO.getName_of_file() + ".xls");
			} catch (MessagingException msgEx) {
				logger.error("MessagingException occured while sending email to buyer!", msgEx);
			} catch (IOException ioEx) {
				logger.error("IOException occured while sending email to buyer!", ioEx);
			}
        }
    }

    private void populateLookupMap(){
		
		long startTime = System.currentTimeMillis();
		
		/* get all look up hash maps */
		List mainList = BFUTDaoImpl.getMainCategoryMap();
		logger.info("size is" + mainList.size());
		Iterator mainIt = mainList.iterator();
		HashMap<String, Integer> mainCatMap = new HashMap<String, Integer>();
		while (mainIt.hasNext()) {
			SkillTreeVO tempObj = (SkillTreeVO) mainIt.next();
			mainCatMap.put(tempObj.getNode_name().toUpperCase().trim(), tempObj.getNode_id());
		}

		/* location class look up */
		List locLookUpList = BFUTDaoImpl.getLocationLookUp();
		logger.info("size is" + mainList.size());
		mainIt = locLookUpList.iterator();
		HashMap<String, Integer> locationMap = new HashMap<String, Integer>();
		while (mainIt.hasNext()) {
			LocationClassVO tempObj = (LocationClassVO) mainIt.next();
			locationMap.put(tempObj.getDescr().toUpperCase().trim(), tempObj.getSo_locn_class_id());
		}

		/* phone type look up */
		List phoneTypeList = BFUTDaoImpl.getPhoneTypes();
		logger.info("size is" + mainList.size());
		mainIt = phoneTypeList.iterator();
		HashMap<String, Integer> phoneTypeMap = new HashMap<String, Integer>();
		while (mainIt.hasNext()) {
			PhoneTypeVO tempObj = (PhoneTypeVO) mainIt.next();
			phoneTypeMap.put(tempObj.getDescr().trim().toUpperCase(), tempObj.getPhone_type_id());
		}
		logger.info("phone typemap" + phoneTypeMap);
		/* phone class look up */
		List phoneClassList = BFUTDaoImpl.getPhoneClass();
		logger.info("size is" + mainList.size());
		mainIt = phoneClassList.iterator();
		HashMap<String, Integer> phoneClassMap = new HashMap<String, Integer>();
		while (mainIt.hasNext()) {
			PhoneClassVO tempObj = (PhoneClassVO) mainIt.next();
			phoneClassMap.put(tempObj.getDescr().trim().toUpperCase(), tempObj.getSo_phone_class_id());
		}
		logger.info("phone classmap" + phoneClassMap);
		
		/* parts supplied look up */
		List partsSupplied = BFUTDaoImpl.getPartsSuppliedBy();
		logger.info("size is" + mainList.size());
		mainIt = partsSupplied.iterator();
		HashMap<String, Integer> partSupplierMap = new HashMap<String, Integer>();
		while (mainIt.hasNext()) {
			LookUpPartsSuppliedVO tempObj = (LookUpPartsSuppliedVO) mainIt
					.next();
			partSupplierMap.put(tempObj.getDescr().trim().toUpperCase(), tempObj.getParts_supplied_by_Id());
		}

		/* subcategory look up */
		List subCatList = BFUTDaoImpl.getSubCatMap();
		mainIt = subCatList.iterator();
		HashMap<String, Integer> subCatMap = new HashMap<String, Integer>();
		while (mainIt.hasNext()) {
			SkillTreeVO tempObj = (SkillTreeVO) mainIt.next();
			subCatMap.put(tempObj.getNode_name().trim().toUpperCase(), tempObj.getNode_id());
		}
		logger.info("size is" + subCatList.size());

		/* skill look up */
		List skillList = BFUTDaoImpl.getSkillMap();
		mainIt = skillList.iterator();
		HashMap<String, Object> skillMap = new HashMap<String, Object>();
		while (mainIt.hasNext()) {
			ServiceTypeTemplateVO tempObj = (ServiceTypeTemplateVO) mainIt
					.next();
			skillMap.put(tempObj.getNode_id()+"|"+tempObj.getDescr().trim().toUpperCase(), tempObj.getService_type_template_id());
		}
		logger.info("size of sskill is" + skillList.size());

		/* ship info look up */
		List shipInfoList = BFUTDaoImpl.getShippingInfo();
		mainIt = shipInfoList.iterator();
		HashMap<String, Integer> shipInfoMap = new HashMap<String, Integer>();
		while (mainIt.hasNext()) {
			LookupShippngCarrierVO tempObj = (LookupShippngCarrierVO) mainIt.next();
			shipInfoMap.put(tempObj.getDescr().trim().toUpperCase(), tempObj.getShipping_carrier_id());
		}
		logger.info("size of sskill is" + skillList.size());
		lookUpMap.put("mainCatMap", mainCatMap);
		lookUpMap.put("subCatMap", subCatMap);
		lookUpMap.put("skillMap", skillMap);

		lookUpMap.put("shipInfoMap", shipInfoMap);
		lookUpMap.put("partSupplierMap", partSupplierMap);
		lookUpMap.put("phoneClassMap", phoneClassMap);
		lookUpMap.put("phoneTypeMap", phoneTypeMap);
		lookUpMap.put("locationMap", locationMap);
		long endTime = System.currentTimeMillis();

		logger.info("Timer::Time taken for lookup map - shipInfoMap partSupplierMap phoneClassMap phoneTypeMap locationMap : " + (endTime - startTime) / 1000 + " seconds");

    }
	
    public void moveFileUploadDataFromIntegrationDbToSupplierDb() {
    	int TRANSACTION_SUCCESS = 1;
    	int TRANSACTION_ERROR = 2;
    	
    	// Get new records from servicelive_integration.batches
    	try {
    		// Get all the files that have been sent to and processed by the ESB
    		List<UploadFileVO> uploadFiles = BFUTDaoImpl.getFilesSentToEsb();
    		
    		for (UploadFileVO uploadFile : uploadFiles) {
    			String fileName = generateFileName(uploadFile);
    		
    			// Find the batch in the integration database that matches this file name
    			IntegrationBatchVO batch = integrationDaoImpl.getProcessedFileUploadBatchByFilename(fileName);
    			if (batch == null) {
    				continue;
    			}
    			
	    		List<IntegrationTransactionVO> transactions = integrationDaoImpl.getTransactions(batch.getBatchId());
	    		
	    		// Split the transaction list into successful and unsuccessful
	    		List<IntegrationTransactionVO> successfulTransactions = new ArrayList<IntegrationTransactionVO>();
	    		List<IntegrationTransactionVO> errorTransactions = new ArrayList<IntegrationTransactionVO>();
	    		for (IntegrationTransactionVO transaction : transactions) {
	    			if (transaction.getStatusId().equals(TRANSACTION_SUCCESS)) {
	    				successfulTransactions.add(transaction);
	    			} else {
	    				errorTransactions.add(transaction);
	    			}
	    		}
	    		
				
				// insert a row for each successful transaction
				for (IntegrationTransactionVO transaction : successfulTransactions) {
					UploadAuditSuccessVO uploadSuccess = new UploadAuditSuccessVO(uploadFile.getFile_id(), transaction.getSoId());
					BFUTDaoImpl.successInsert(uploadSuccess);
				}
				
				// Get the SO data for writing to the error table
				List<Map<String, String>> soMapList = null;
				if (errorTransactions.size() > 0) {
					byte[] fileContent = uploadFile.getfile_content();
					
					try {
						soMapList = BFUTParserImpl.parseBuyerXLSFile(fileContent);
				        //long endTime = System.currentTimeMillis();
				        //logger.error("Timer::Time taken to do extra lookups and parse XLS: "+fileName+" : " + (endTime-startTime)/1000 + " seconds");
				        //System.err.println("Timer::Time taken to do extra lookups and parse XLS: "+fileName+" : " + (endTime-startTime)/1000 + " seconds");
					} catch (Exception ex) {
						logger.error("Unexpected error while parsing the uploaded xls file: "+uploadFile.getName_of_file(), ex);
					}
				}
				
				// insert a row for each failed transaction
				for (IntegrationTransactionVO transaction : errorTransactions) {
					
					addErrors(uploadFile, soMapList, transaction);
				}
				
				// Mark the fileUpload record as having been retrieved from ESB
				BFUTDaoImpl.markAsRetrievedFromESB(uploadFile.getFile_id());
	    	}	    	
    	} catch  (Exception e) {
    		logger.error("Error while attempting to load batches from the integration database into so_upload_audit.", e);
    	}
    	
    }
    
    private void addErrors(UploadFileVO uploadFile, List<Map<String, String>> soMapList, IntegrationTransactionVO transaction) {
    	// extract the row ID out of the external order number
		String externalOrderNumber = transaction.getExternalOrderNumber();
		int splitIndex = externalOrderNumber.indexOf("_");
		int rowId = 0;
		if (splitIndex > 0 && splitIndex < externalOrderNumber.length() - 1) {
			try {
				String rowIdString = externalOrderNumber.substring(splitIndex + 1);
				rowId = Integer.parseInt(rowIdString);
			} catch (Exception e) {
				logger.error("Error while trying to parse the upload file's row ID", e);
			}
		}
		
		UploadAuditErrorVO uploadError = null;
		if (soMapList != null) {
			Map<String, String> soMap = soMapList.get(rowId - 1);
			List<String> errorList = new ArrayList<String>();
			if (transaction.getException() != null) {
				if (transaction.getException().length() > 1000) {
					errorList.add(transaction.getException().substring(0, 1000));
				} else {
					errorList.add(transaction.getException());
				}
			}
			
			uploadError = BFUTValidatorImpl.getUploadErrorObject(uploadFile.getFile_id(), rowId,
					errorList, soMap);
		} else {
			uploadError = new UploadAuditErrorVO();
			uploadError.setFileId(uploadFile.getFile_id());
			uploadError.setRowId(rowId);
			BFUTDaoImpl.errorInsert(uploadError);
		}
		BFUTDaoImpl.errorInsert(uploadError);
    }
	
    public String markFileAsRead(String fileName) {
		BFUTDaoImpl.markFileAsRead(fileName);
		return "success";
	}

	public void insertIncorrectColumnRecord(String fileName) {
		markFileAsRead(fileName);
	}

	public IBuyerFileUploadToolDAO getBFUTDaoImpl() {
		return BFUTDaoImpl;
	}

	public void setBFUTDaoImpl(IBuyerFileUploadToolDAO daoImpl) {
		this.BFUTDaoImpl = daoImpl;
	}

	public IBuyerFileUploadToolValidator getBFUTValidatorImpl() {
		return BFUTValidatorImpl;
	}

	public void setBFUTValidatorImpl(IBuyerFileUploadToolValidator validatorImpl) {
		this.BFUTValidatorImpl = validatorImpl;
	}

	public IBuyerFileUploadToolParser getBFUTParserImpl() {
		return BFUTParserImpl;
	}

	public void setBFUTParserImpl(IBuyerFileUploadToolParser parserImpl) {
		this.BFUTParserImpl = parserImpl;
	}

    public IEmailTemplateBO getEmailTemplateBean() {
        return emailTemplateBean;
    }

    public void setEmailTemplateBean(IEmailTemplateBO emailTemplateBean) {
        this.emailTemplateBean = emailTemplateBean;
    }

	public void setApplicationProperties(IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

    public boolean isNewOFAllowed(){
	    boolean returnVal = false;
        if(ofHelper.isOFEnabled()){
            try{
                returnVal = StringUtils.equals("1",applicationProperties.getPropertyFromDB(Constants.OrderFulfillment.USE_NEW_ORDER_FULFILLMENT_PROCESS));
            }catch(DataNotFoundException dnfe){
                logger.error("We are ignoring the error and using the default value of false", dnfe);
            }
        }
	    return returnVal;
	}

	public void setIntegrationDaoImpl(IIntegrationDAO integrationDaoImpl) {
		this.integrationDaoImpl = integrationDaoImpl;
	}

	public IIntegrationDAO getIntegrationDaoImpl() {
		return integrationDaoImpl;
	}

	public void setBuyerFileUploadDao(IBuyerFileUploadDao buyerFileUploadDao) {
		this.buyerFileUploadDao = buyerFileUploadDao;
	}

	public IBuyerFileUploadDao getBuyerFileUploadDao() {
		return buyerFileUploadDao;
	}
}
