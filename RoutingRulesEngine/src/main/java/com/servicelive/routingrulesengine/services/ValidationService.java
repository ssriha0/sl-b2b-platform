package com.servicelive.routingrulesengine.services;

import java.util.HashMap;
import java.util.List;

import com.servicelive.routingrulesengine.vo.CustomReferenceVO;
import com.servicelive.routingrulesengine.vo.JobPriceVO;

/**
 * @author Infosys The interface contain method declarations for validation
 *         methods for Routing rules.
 * 
 */
public interface ValidationService {
	/**
	 * The method is used to validate a list of zip codes
	 * 
	 * @param List
	 *            <String> zips :zips to get validated
	 * @return HashMap<String, List<String>> :VALID_ZIPS has valid zips,
	 *         INVALID_ZIPS has invalid zips
	 */
	public HashMap<String, List<String>> validateZipCodes(List<String> zips);

	/**
	 * The method is used to validate a list of job Codes
	 * 
	 * @param List
	 *            List<JobPriceVO> jobCodeVO :jobCodes to get validated
	 * @return HashMap<String, List<JobPriceVO>> :VALID_JOBCODES has valid
	 *         jobcodes, INVALID_JOBCODES has invalid jobcodes
	 */
	public HashMap<String, List<JobPriceVO>> validateJobCodes(
			List<JobPriceVO> jobCodeVO, Integer buyerId);
	
	/**
	 * The method is used to validate a list of job Codes
	 * 
	 * @param List
	 *            List<String> jobCodeVO :jobCodes to get validated
	 * @return HashMap<String, List<String>> :VALID_JOBCODES has valid
	 *         jobcodes, INVALID_JOBCODES has invalid jobcodes
	 */
	public HashMap<String, List<String>> validateJobCodeList(
			List<String> jobCodes, Integer buyerId);
	
	/**
	 * The method is used to validate email
	 * 
	 * @param email:
	 *            the email to be validated
	 * @return boolean: returns true if valid email id or else returns false
	 */
	public boolean validateEmail(String email);

	/**
	 * The method is used to validate provider firm ids
	 * 
	 * @param firmIds :
	 *            the firm ids to be validated
	 * @return HashMap<String, List<Integer>> :VALID_FIRMIDS has valid firm ids,
	 *         INVALID_FIRMIDS has invalid firm ids
	 */
	public HashMap<String, List<String>> validateFirmIds(List<String> firmIds);

	/**
	 * The method is used to validate a list of custom references
	 * 
	 * @param List
	 *            List<CustomReferenceVO> custRefsVO :custom references to get
	 *            validated
	 * @return HashMap<String,List<Object>> :VALID_CUSTREF has valid buyer
	 *         references, INVALID_CUSTREF has invalid buyer references
	 */
	public HashMap<String, List<CustomReferenceVO>> validateCustomReference(
			List<CustomReferenceVO> custRefsVO, Integer buyerId);

	/**
	 * This method is used to validate rule name
	 * 
	 * @param ruleName:
	 *            rule name to be validated
	 * @param buyerId
	 * @return boolean: returns true if valid else returns false
	 */
	public boolean validateRuleName(String ruleName, Integer buyerId);
}
