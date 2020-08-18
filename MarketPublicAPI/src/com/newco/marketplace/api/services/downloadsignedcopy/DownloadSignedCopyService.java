package com.newco.marketplace.api.services.downloadsignedcopy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadResult;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadResults;
import com.newco.marketplace.api.beans.downloadsignedcopy.DownloadSignedCopyResponse;
import com.newco.marketplace.api.beans.downloadsignedcopy.SignedCopyResult;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.downloadsignedcopy.IDownloadSignedCopyBO;
import com.newco.marketplace.vo.downloadsignedcopy.SignedCopyVO;

/**
 * @author Infosys
 * 
 */
public class DownloadSignedCopyService {

	private static Logger logger = Logger
			.getLogger(DownloadSignedCopyService.class);
	private IDownloadSignedCopyBO downloadsignedCopyBO;

	/**
	 * @Description:Method will validate the URL parameters and retrieve
	 *                     document information
	 * @param apiVO
	 * @return DownloadSignedCopyResponse
	 * 
	 */
	public DownloadSignedCopyResponse execute(APIRequestVO apiVO, String title) {
		// Response related Objects initialization*/
		DownloadSignedCopyResponse finalResponse = new DownloadSignedCopyResponse();
		DownloadResults results = new DownloadResults();
		Integer buyerId = apiVO.getBuyerIdInteger();
		// validation of buyer Id
		if (null == buyerId || buyerId == PublicAPIConstant.BUYER_ID_IS_EMPTY) {
			return createErrorResponse(finalResponse,
					PublicAPIConstant.BUYER_ID_EMPTY);
		}
		// Tokenizing the service orders*/
		String soIds = (String) apiVO.getProperty(PublicAPIConstant.SO_ID_LIST);
		// validation of soId String
		if (StringUtils.isBlank(soIds)) {
			return createErrorResponse(finalResponse,
					PublicAPIConstant.SO_ID_EMPTY);
		}
		List<String> soIdList = new ArrayList<String>();
		// TO DO: TO Check->>What will happen if soIdList is empty
		StringTokenizer soIdStr = new StringTokenizer(soIds,
				PublicAPIConstant.SO_SEPERATOR);
		try {
			while (soIdStr.hasMoreTokens()) {
				String soId = soIdStr.nextToken();
				if (!soIdList.contains(soId)) {
					soIdList.add(soId);
				}
			}
			// Validating URL parameters*/
			results = validateURLParams(soIdList, buyerId);
			// Setting all soId List in response
			finalResponse.setSoIdList(soIdList);
			List<String> validSoList = results.getSoIdList();
			// Setting Valid soId List from results after validation
			finalResponse.setValidSoIdList(validSoList);
			// Check any eligible document exists for downloading
			if (results.isEligibleForDownLoad()) {
				/*
				 * Retrieve all the signed copy document of all service orders
				 * as a List
				 */
				List<SignedCopyVO> signedCopyVOList = downloadsignedCopyBO
						.getSignedCopyForSo(validSoList, title);
				if (signedCopyVOList.isEmpty()) {
					// Document is not available for downloading.
					results.setDocumentAvailable(false);
					if (null != results.getResults()
							&& (!(results.getResults().isEmpty()))) {
						for (DownloadResult errorResult : results.getResults()) {
							if (null != errorResult
									&& StringUtils.isBlank(errorResult
											.getCode())
									&& StringUtils.isBlank(errorResult
											.getMessage())) {
								// Document is not available for the order.
								errorResult
										.setCode(ResultsCode.SIGNED_COPY_NOT_AVAILABLE
												.getCode());
								errorResult
										.setMessage(ResultsCode.SIGNED_COPY_NOT_AVAILABLE
												.getMessage());
							}
						}
					}
					finalResponse.setResults(results);
					return finalResponse;
				} else {
					results.setDocumentAvailable(true);
					finalResponse.setResults(results);
				}
				// Iterate through resultList to create String Array of filePath
				// of all Documents
				finalResponse = setFilePathOfAllDocuments(signedCopyVOList,
						finalResponse);
				// Mapping vo list to result list objects
				List<SignedCopyResult> SignedCopyResultList = convertVOListToResultList(signedCopyVOList);
				// Setting a map in response to hold meta data information of
				// documents
				finalResponse = mapResultListToResultMap(SignedCopyResultList,
						validSoList, finalResponse);
			} else {
				finalResponse.setResults(results);
				return finalResponse;
			}

		} catch (Exception e) {
			logger.error("Exception in downloading signed copy :"
					+ e.getMessage());

		}
		finalResponse.setResults(results);
		return finalResponse;
	}

	/**
	 * @Description: This method will validate the url parameters and set error
	 *               message if any.
	 * @param soIdList
	 * @param buyerId
	 * @return DownloadResults
	 */
	private DownloadResults validateURLParams(List<String> soIdList,
			Integer buyerId) {
		DownloadResults results = new DownloadResults();
		List<String> validSoIdList = new ArrayList<String>();
		/*
		 * TO DO :Validate following conditions 1.BuyerId 2.SoIds 3.Buyer so
		 * Combination 4.So status* 5.Service Order List size <= 5 6.Zip File
		 * Size :To Be Done after Zipping all files
		 */
		List<DownloadResult> resultList = new ArrayList<DownloadResult>();
		// Boolean variables specific to Buyer Id
		boolean isValidBuyer = false;
		// Variable to check document can be downloadable
		boolean isEligibleForDownLoad = false;
		try {
			// Validating buyer Id
			isValidBuyer = downloadsignedCopyBO.validateBuyer(buyerId);
			if (isValidBuyer) {
				// Validating service Order List Count
				int size = soIdList.size();
				String serviceOrderCount = downloadsignedCopyBO
						.getApplicationPropertyFromDB(PublicAPIConstant.SERVICE_ORDER_COUNT);
				int countSize = Integer.parseInt(serviceOrderCount);
				if (size <= countSize) {
					for (String soId : soIdList) {
						DownloadResult errorResult = new DownloadResult();
						// Boolean variables specific to soIds
						boolean isValidOrder = false;
						boolean isValidStatus = false;
						boolean isOrderAssociated = false;
						errorResult.setSoId(soId);
						isValidOrder = downloadsignedCopyBO
								.validateServiceOrder(soId);
						if (isValidOrder) {
							isValidStatus = downloadsignedCopyBO
									.validateSoStatus(soId);
							if (isValidStatus) {
								isOrderAssociated = downloadsignedCopyBO
										.validateSoIdAssociation(soId, buyerId);
								if (isOrderAssociated) {
									isEligibleForDownLoad = true;
									results.setEligibleForDownLoad(isEligibleForDownLoad);
									validSoIdList.add(soId);
									resultList.add(errorResult);
								} else {
									// TO DO:Error Message: Buyer Id is not
									// associated with order.
									errorResult
											.setCode(ResultsCode.SERVICE_ORDER_NOT_ASSOCIATED
													.getCode());
									errorResult
											.setMessage(ResultsCode.SERVICE_ORDER_NOT_ASSOCIATED
													.getMessage());
									errorResult.setSoId(soId);
									resultList.add(errorResult);
								}
							} else {
								// TO DO:Error Message :Invalid Service orders
								// Status
								errorResult
										.setCode(ResultsCode.INVALID_SO_STATUS
												.getCode());
								errorResult
										.setMessage(ResultsCode.INVALID_SO_STATUS
												.getMessage());
								errorResult.setSoId(soId);
								resultList.add(errorResult);
							}
						} else {
							// TO DO:Error Message: Invalid Service Order
							errorResult
									.setCode(ResultsCode.INVALID_SERVICE_ORDER
											.getCode());
							errorResult
									.setMessage(ResultsCode.INVALID_SERVICE_ORDER
											.getMessage());
							errorResult.setSoId(soId);
							resultList.add(errorResult);
						}

					}
				} else {
					// TO DO: Error Message service order count is greater than
					// allowable count
					DownloadResult errorResult = new DownloadResult();
					errorResult.setCode(ResultsCode.SO_COUNT_EXCEEDS_LIMIT
							.getCode());
					errorResult.setMessage(ResultsCode.SO_COUNT_EXCEEDS_LIMIT
							.getMessage());
					resultList.add(errorResult);
					results.setSoIdList(null);
					results.setResults(resultList);
					results.setOtherErrorMessage(true);
					return results;
				}
			} else {
				// TO DO: Error Message invalid Buyer Id,Populate Result List
				DownloadResult errorResult = new DownloadResult();
				errorResult.setCode(ResultsCode.INVALID_BUYER_ID_NEW.getCode());
				errorResult.setMessage(ResultsCode.INVALID_BUYER_ID_NEW
						.getMessage());
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

	/**
	 * @param signedCopyVOList
	 * @return
	 */
	private List<SignedCopyResult> convertVOListToResultList(
			List<SignedCopyVO> signedCopyVOList) {
		List<SignedCopyResult> resultList = new ArrayList<SignedCopyResult>();
		for (SignedCopyVO signedCopyVO : signedCopyVOList) {
			SignedCopyResult resultObj = new SignedCopyResult();
			resultObj.setSoId(signedCopyVO.getSoId());
			resultObj.setFileName(signedCopyVO.getFileName());
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
	private DownloadSignedCopyResponse mapResultListToResultMap(
			List<SignedCopyResult> resultList, List<String> soIdList,
			DownloadSignedCopyResponse response) {
		Map<String, List<SignedCopyResult>> resultMap = new HashMap<String, List<SignedCopyResult>>();
		// Iterate through the resultList against soId List and put it in a map.
		for (String soId : soIdList) {
			List<SignedCopyResult> documentVO = new ArrayList<SignedCopyResult>();
			if (null != resultList && (!(resultList.isEmpty()))) {
				for (SignedCopyResult documentObj : resultList) {
					if (StringUtils.isNotBlank(soId)
							&& StringUtils.isNotBlank(documentObj.getSoId())
							&& soId.equals(documentObj.getSoId())) {
						documentVO.add(documentObj);
					}
				}
			}
			resultMap.put(soId, documentVO);
		}
		response.setResultMap(resultMap);
		return response;
	}

	/**
	 * @param signedCopyVOList
	 * @param finalResponse
	 * @return
	 */
	private DownloadSignedCopyResponse setFilePathOfAllDocuments(
			List<SignedCopyVO> signedCopyVOList,
			DownloadSignedCopyResponse finalResponse) {
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
	 * @param finalResponse
	 * @param urlParam
	 * @return
	 */
	private DownloadSignedCopyResponse createErrorResponse(
			DownloadSignedCopyResponse finalResponse, String urlParam) {
		DownloadResults results = new DownloadResults();
		List<DownloadResult> resultList = new ArrayList<DownloadResult>();
		DownloadResult result = new DownloadResult();
		if (urlParam.equals(PublicAPIConstant.BUYER_ID_EMPTY)) {
			result.setCode(ResultsCode.INVALID_OPTIONAL_PARAMS.getCode());
			result.setMessage(ResultsCode.INVALID_OPTIONAL_PARAMS.getMessage()
					+ ":" + PublicAPIConstant.BUYER_ID_EMPTY);
		} else {
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

	public IDownloadSignedCopyBO getDownloadsignedCopyBO() {
		return downloadsignedCopyBO;
	}

	public void setDownloadsignedCopyBO(
			IDownloadSignedCopyBO downloadsignedCopyBO) {
		this.downloadsignedCopyBO = downloadsignedCopyBO;
	}

}
