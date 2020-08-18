package com.newco.marketplace.validator.buyerFileUploadTool;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;

public interface IBuyerFileUploadToolValidator {

	/**
	 * This function validates the input excel data; creates the SO object and saves the SO in database.
	 * 
	 * @param soMap
	 * @param lookUpMap
	 * @param fileId
	 * @param soCount
	 */
	public void validateAndSubmit(Map<String, String> soMap, Map lookUpMap, int fileId, int soCount,SecurityContext securityContext);
	
	public UploadAuditErrorVO getUploadErrorObject(int fileId, int soCount, List<String> errorList, Map<String, String> soMap);
}
