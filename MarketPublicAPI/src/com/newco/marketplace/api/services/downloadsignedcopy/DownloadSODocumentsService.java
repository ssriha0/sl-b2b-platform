package com.newco.marketplace.api.services.downloadsignedcopy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadResult;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadResults;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadSODocumentsResponse;
import com.newco.marketplace.api.beans.downloadsignedcopy.SODocumentsResult;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.downloadsignedcopy.IDownloadSignedCopyBO;
import com.newco.marketplace.vo.downloadsignedcopy.SignedCopyVO;

/**
 * @author Infosys
 * 
 */
public class DownloadSODocumentsService {

	private static Logger logger = Logger.getLogger(DownloadSODocumentsService.class);
	
	private IDownloadSignedCopyBO downloadsignedCopyBO;

	/**
	 * @Description:Method will validate the URL parameters and retrieve
	 *                     document information
	 * @param apiVO
	 * @return DownloadSignedCopyResponse
	 * 
	 */
	public DownloadSODocumentsResponse execute(APIRequestVO apiVO) {
		// Response related Objects initialization*/
		DownloadSODocumentsResponse finalResponse = new DownloadSODocumentsResponse();
		List<DownloadResult> resultList = new ArrayList<DownloadResult>();
		DownloadResults results = new DownloadResults();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		
		Integer buyerId = apiVO.getBuyerIdInteger();
		String soId = (String) apiVO.getProperty(PublicAPIConstant.SO_ID);
		String docType = requestMap.get(PublicAPIConstant.DOC_TYPE);
		
		// validation of buyer Id
		if (null == buyerId || buyerId == PublicAPIConstant.BUYER_ID_IS_EMPTY) {
			return createErrorResponse(finalResponse, PublicAPIConstant.BUYER_ID_EMPTY);
		}
		// validation of soId 
		if (StringUtils.isBlank(soId)) {
			return createErrorResponse(finalResponse, PublicAPIConstant.SO_ID_EMPTY);
		}
		
		try {
			
			finalResponse.setSoId(soId);
			// Validating URL parameters
			results = validateURLParams(soId, buyerId, docType);
			finalResponse.setValidSoId(soId);
			if (results.isEligibleForDownLoad()) {
				List<SignedCopyVO> documentsVOList = new ArrayList<SignedCopyVO>();
				if(StringUtils.isBlank(docType)){
					//Retrieve all the SO documents for all service orders	
					documentsVOList = downloadsignedCopyBO.getDocumentsForSo(soId);
				}
				else if(StringUtils.isNotBlank(docType) && 
						PublicAPIConstant.retrieveSO.BUYER.equalsIgnoreCase(docType)){
					// Retrieve all the buyer SO documents for all service orders
					documentsVOList = downloadsignedCopyBO.getBuyerDocumentsForSo(soId);
				}
				else if(StringUtils.isNotBlank(docType) && 
						PublicAPIConstant.retrieveSO.PROVIDER.equalsIgnoreCase(docType)){
					 //Retrieve all the provider SO documents for all service orders
					documentsVOList = downloadsignedCopyBO.getProviderDocumentsForSo(soId);
				}
				
				if (documentsVOList.isEmpty()) {
					// Document is not available for downloading.
					results.setDocumentAvailable(false);
					if (null != results.getResults() && (!(results.getResults().isEmpty()))) {
						for (DownloadResult errorResult : results.getResults()) {
							if (null != errorResult	&& StringUtils.isBlank(errorResult.getCode())
									&& StringUtils.isBlank(errorResult.getMessage())) {
								// Document is not available for the order.
								errorResult.setCode(ResultsCode.SO_DOCUMENTS_NOT_AVAILABLE.getCode());
								errorResult.setMessage(ResultsCode.SO_DOCUMENTS_NOT_AVAILABLE.getMessage());
								errorResult.setSoId(soId);
								resultList.add(errorResult);
								results.setResults(resultList);
								
							}
						}
					}
					finalResponse.setResults(results);
					return finalResponse;
				} else {
					results.setDocumentAvailable(true);
					finalResponse.setResults(results);
				}
				// Iterate through resultList to create String Array of filePath of all Documents
				finalResponse = setFilePathOfAllDocuments(documentsVOList, finalResponse);
				// Mapping vo list to result list objects
				List<SODocumentsResult> SODocumentsResultList = convertVOListToResultList(documentsVOList);
				// Setting a map in response to hold meta data information of documents
				finalResponse = mapResultListToResultMap(SODocumentsResultList,	soId, finalResponse);
			}
			else{
				finalResponse.setResults(results);
				return finalResponse;
			}
			
		}
		catch (Exception e) {
			logger.error("Exception in downloading SO documents :" + e.getMessage());

		}
		finalResponse.setResults(results);
		return finalResponse;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getApplicationPropertyFromDB(String key) {
		String value = null;
		try {
			value = downloadsignedCopyBO.getApplicationPropertyFromDB(key);
		} catch (Exception e) {
			logger.error("Exception in fetching application property from DB:"
					+ e.getMessage());
		}
		return value;
	}
	
	/**
	 * @param finalResponse
	 * @param urlParam
	 * @return
	 */
	private DownloadSODocumentsResponse createErrorResponse(
			DownloadSODocumentsResponse finalResponse, String urlParam) {
		
		DownloadResults results = new DownloadResults();
		List<DownloadResult> resultList = new ArrayList<DownloadResult>();
		DownloadResult result = new DownloadResult();
		if (urlParam.equals(PublicAPIConstant.BUYER_ID_EMPTY)) {
			result.setCode(ResultsCode.INVALID_OPTIONAL_PARAMS.getCode());
			result.setMessage(ResultsCode.INVALID_OPTIONAL_PARAMS.getMessage()
					+ ":" + PublicAPIConstant.BUYER_ID_EMPTY);
		} 
		else {
			result.setCode(ResultsCode.INVALID_OPTIONAL_PARAMS.getCode());
			result.setMessage(ResultsCode.INVALID_OPTIONAL_PARAMS.getMessage()
					+ ":" + PublicAPIConstant.SO_ID_EMPTY);
		}
		resultList.add(result);
		results.setResults(resultList);
		finalResponse.setResults(results);
		return finalResponse;
	}
	
	/**
	 * @Description: This method will validate the url parameters and set error
	 *               message if any.
	 * @param soId
	 * @param buyerId
	 * @param docType
	 * @return DownloadResults
	 */
	private DownloadResults validateURLParams(String soId,
			Integer buyerId,String docType) {
		DownloadResults results = new DownloadResults();
		List<String> validSoIdList = new ArrayList<String>();
		List<DownloadResult> resultList = new ArrayList<DownloadResult>();
		boolean isValidBuyer = false;
		boolean isValidOrder = false;
		boolean isOrderAssociated = false;
		boolean isEligibleForDownLoad = false;
		try {
			// Validating buyer Id
			isValidBuyer = downloadsignedCopyBO.validateBuyer(buyerId);
			if (isValidBuyer) {
				DownloadResult errorResult = new DownloadResult();				
				errorResult.setSoId(soId);
				// Validating service order
				isValidOrder = downloadsignedCopyBO.validateServiceOrder(soId);
				if (isValidOrder) {
					// Validating whether SO is associated with buyer
					isOrderAssociated = downloadsignedCopyBO.validateSoIdAssociation(soId, buyerId);
					if (isOrderAssociated) {
						if( StringUtils.isBlank(docType) || 
							(StringUtils.isNotBlank(docType) && (PublicAPIConstant.retrieveSO.BUYER.equalsIgnoreCase(docType)|| PublicAPIConstant.retrieveSO.PROVIDER.equalsIgnoreCase(docType)))) {
							isEligibleForDownLoad = true;
							results.setEligibleForDownLoad(isEligibleForDownLoad);
							validSoIdList.add(soId);
							resultList.add(errorResult);
						}
						else
						{
							//Invalid document type
							errorResult.setCode(ResultsCode.INVALID_SO_DOCUMENT_TYPE.getCode());
							errorResult.setMessage(ResultsCode.INVALID_SO_DOCUMENT_TYPE.getMessage());
							errorResult.setSoId(soId);
							resultList.add(errorResult);
						}

					} else {
						// Buyer Id is not associated with order.
						errorResult.setCode(ResultsCode.SERVICE_ORDER_NOT_ASSOCIATED.getCode());
						errorResult.setMessage(ResultsCode.SERVICE_ORDER_NOT_ASSOCIATED.getMessage());
						errorResult.setSoId(soId);
						resultList.add(errorResult);
					}
						
				} else {
					//Invalid Service Order
					errorResult.setCode(ResultsCode.INVALID_SERVICE_ORDER.getCode());
					errorResult.setMessage(ResultsCode.INVALID_SERVICE_ORDER.getMessage());
					errorResult.setSoId(soId);
					resultList.add(errorResult);
				}
			} else {
				// Invalid Buyer Id
				DownloadResult errorResult = new DownloadResult();
				errorResult.setCode(ResultsCode.INVALID_BUYER_ID_NEW.getCode());
				errorResult.setMessage(ResultsCode.INVALID_BUYER_ID_NEW.getMessage());
				resultList.add(errorResult);
				results.setSoIdList(null);
				results.setResults(resultList);
				results.setOtherErrorMessage(true);
				return results;

			}
		} catch (Exception e) {
			logger.error("Exception in validating buyerId :" + e.getMessage());
		}
		// Setting valid soIds into Results
		results.setSoIdList(validSoIdList);
		results.setResults(resultList);
		return results;
	}

	public IDownloadSignedCopyBO getDownloadsignedCopyBO() {
		return downloadsignedCopyBO;
	}

	public void setDownloadsignedCopyBO(
			IDownloadSignedCopyBO downloadsignedCopyBO) {
		this.downloadsignedCopyBO = downloadsignedCopyBO;
	}
	
	
	/**
	 * @param signedCopyVOList
	 * @param finalResponse
	 * @return
	 */
	private DownloadSODocumentsResponse setFilePathOfAllDocuments(
			List<SignedCopyVO> signedCopyVOList,
			DownloadSODocumentsResponse finalResponse) {
		if (!signedCopyVOList.isEmpty()) {
			int size = signedCopyVOList.size();
			long fileSize = 0l;
			long resultBytes = 0l;
			String[] srcFiles = new String[size];
			for (int i = 0; i < size; i++) {
				SignedCopyVO copyVO = signedCopyVOList.get(i);
				srcFiles[i] = copyVO.getFilePath();
				fileSize = new Long(copyVO.getFileSize());
				resultBytes = resultBytes + fileSize;
				
			}
			finalResponse.setTotalFileSizeInKB(resultBytes);
			finalResponse.setSrcFiles(srcFiles);
		}

		return finalResponse;
	}
	
	/**
	 * @param signedCopyVOList
	 * @return
	 */
	private List<SODocumentsResult> convertVOListToResultList(
			List<SignedCopyVO> signedCopyVOList) {
		List<SODocumentsResult> resultList = new ArrayList<SODocumentsResult>();
		for (SignedCopyVO signedCopyVO : signedCopyVOList) {
			SODocumentsResult resultObj = new SODocumentsResult();
			resultObj.setSoId(signedCopyVO.getSoId());
			resultObj.setFileName(signedCopyVO.getFileName());
			resultObj.setTitle(signedCopyVO.getDocTitle());
			resultObj.setFilePath(signedCopyVO.getFilePath());
			resultObj.setFileSize(signedCopyVO.getFileSize());
			resultObj.setFileSizeInKB(new Long(signedCopyVO.getFileSize()));
			resultObj.setUploadedBy(signedCopyVO.getUploadedBy());
			resultObj.setUploadedDate(signedCopyVO.getUploadedDateString());
			resultList.add(resultObj);
		}
		return resultList;
	}

	/**
	 * @param resultList
	 * @param resultMap
	 * @param soIdList
	 * @return
	 */
	private DownloadSODocumentsResponse mapResultListToResultMap(
			List<SODocumentsResult> resultList, String soId,
			DownloadSODocumentsResponse response) {
		Map<String, List<SODocumentsResult>> resultMap = new HashMap<String, List<SODocumentsResult>>();
		// Iterate through the resultList against soId List and put it in a map.
		
			List<SODocumentsResult> documentVO = new ArrayList<SODocumentsResult>();
			if (null != resultList && (!(resultList.isEmpty()))) {
				for (SODocumentsResult documentObj : resultList) {
					if (StringUtils.isNotBlank(soId)
							&& StringUtils.isNotBlank(documentObj.getSoId())
							&& soId.equals(documentObj.getSoId())) {
						documentVO.add(documentObj);
					}
				}
			}
			resultMap.put(soId, documentVO);

		response.setResultMap(resultMap);
		return response;
	}
	
}
