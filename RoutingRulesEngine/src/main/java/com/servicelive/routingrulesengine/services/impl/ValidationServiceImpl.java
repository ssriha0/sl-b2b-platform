package com.servicelive.routingrulesengine.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.BuyerReferenceTypeDao;
import com.servicelive.routingrulesengine.dao.BuyerSkuDao;
import com.servicelive.routingrulesengine.dao.LookupZipGeocodeDao;
import com.servicelive.routingrulesengine.dao.ProviderFirmDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.services.ValidationService;
import com.servicelive.routingrulesengine.vo.CustomReferenceVO;
import com.servicelive.routingrulesengine.vo.JobPriceVO;

/**
 * This class contains various validation methods used while creating a rule
 * 
 * @author Infosys
 * 
 */
public class ValidationServiceImpl implements ValidationService {

	private LookupZipGeocodeDao lookupZipGeocodeDao;
	private ProviderFirmDao providerFirmDao;
	private BuyerSkuDao buyerSkuDao;
	private BuyerReferenceTypeDao buyerReferenceTypeDao;
	private RoutingRuleHdrDao routingRuleHdrDao;

	/**
	 * The method is used to validate a list of zip codes
	 * 
	 * @param List
	 *            <String> zips :zips to get validated
	 * @return HashMap<String, List<String>> :VALID_ZIPS has valid zips,
	 *         INVALID_ZIPS has invalid zips
	 */
	public HashMap<String, List<String>> validateZipCodes(List<String> zips) {
		HashMap<String, List<String>> validatedZipList = new HashMap<String, List<String>>();
		List<String> validZips = new ArrayList<String>();
		List<String> inValidZips = new ArrayList<String>();
		// convert list to set to avoid duplicates
		HashSet<String> zipSet = new HashSet<String>();
		zipSet.addAll(zips);
		try {
			List<String> items = lookupZipGeocodeDao.findZips(zips);
			for (String zip : zipSet) {
				if (items.contains(zip)) {
					validZips.add(zip);
				} else {
					inValidZips.add(zip);
				}
			}
			validatedZipList.put(RoutingRulesConstants.VALID_ZIPS, validZips);
			validatedZipList.put(RoutingRulesConstants.INVALID_ZIPS,
					inValidZips);

		} catch (NoResultException nre) {
			return null;
		}
		return validatedZipList;
	}

	/**
	 * The method is used to validate a list of job Codes
	 * 
	 * @param List
	 *            <String> jobCodes :jobCodes to get validated
	 * @return HashMap<String,List<Object>> :VALID_JOBCODES has valid
	 *         jobcodes, INVALID_JOBCODES has invalid jobcodes
	 */
	public HashMap<String, List<JobPriceVO>> validateJobCodes(
			List<JobPriceVO> jobCodesVO, Integer buyerId) {
		HashMap<String, List<JobPriceVO>> validatedJobCodeList = new HashMap<String, List<JobPriceVO>>();
		List<JobPriceVO> validJobCodes = new ArrayList<JobPriceVO>();
		List<JobPriceVO> inValidJobCodes = new ArrayList<JobPriceVO>();
		List<JobPriceVO> inValidPrices = new ArrayList<JobPriceVO>();
		List<JobPriceVO> missingJobCodes = new ArrayList<JobPriceVO>();
		//R12_2 : SL-20643
		List<JobPriceVO> permitJobCodes = new ArrayList<JobPriceVO>();
		List<String> jobCodes = new ArrayList<String>();
		HashMap<String, String> jobCodeMap = new HashMap<String, String>();
		for (JobPriceVO jobVO : jobCodesVO) {

			String jobCode = jobVO.getJobCode();
			if(jobCode != null && !jobCode.equals("")){
			try {
				@SuppressWarnings("unused")
				int jobCodeInt = Integer.valueOf(jobCode);
				int len = jobCode.length();
				if (len < 5) {
					StringBuilder jobCodeString = new StringBuilder();
					jobCodeString.append(jobCode);
					switch (len) {
					case 1:
						jobCodeString.insert(0, "0000");
						break;
					case 2:
						jobCodeString.insert(0, "000");
						break;
					case 3:
						jobCodeString.insert(0, "00");
						break;
					case 4:
						jobCodeString.insert(0, "0");
						break;
					default:
						break;
					}
					jobCode = jobCodeString.toString();
				}
			} catch (NumberFormatException nfe) {
				// do nothing.
			}
			jobCodes.add(jobCode);
			// convert list to map to avoid duplicates
			jobCodeMap.put(jobCode, jobVO.getPrice());
			} else {
				missingJobCodes.add(jobVO);
			}
		}
		try {
			if(!jobCodes.isEmpty()){
				List<String> items = buyerSkuDao
						.validateJobCodes(jobCodes, buyerId);
				for (String jobCode : jobCodeMap.keySet()) {
					
					JobPriceVO jobCodeVO = new JobPriceVO(jobCode, jobCodeMap
							.get(jobCode));
					
					if (items.contains(jobCode)) {
						//R12_2
						//SL-20643
						//If the buyer has a permit SKU, do not add it to the validJobCodes list
						if(RoutingRulesConstants.PERMIT_SKU.equalsIgnoreCase(jobCode)){
							permitJobCodes.add(jobCodeVO);
						}else{
							try {
								Double price = Double.valueOf(jobCodeVO.getPrice());
								if (price < 0) {
									inValidPrices.add(jobCodeVO);
								} else {
									validJobCodes.add(jobCodeVO);
								}
							} catch (NumberFormatException nfe) {
								inValidPrices.add(jobCodeVO);
							}
						}
						
					} else {
						inValidJobCodes.add(jobCodeVO);
					}
				}
			}
			validatedJobCodeList.put(RoutingRulesConstants.VALID_JOBCODES,
					validJobCodes);
			validatedJobCodeList.put(RoutingRulesConstants.INVALID_JOBCODES,
					inValidJobCodes);
			validatedJobCodeList.put(RoutingRulesConstants.MISSING_JOBCODES,
					missingJobCodes);
			validatedJobCodeList.put(
					RoutingRulesConstants.INVALID_JOBCODE_PRICE, inValidPrices);
			//R12_2 : SL-20643
			validatedJobCodeList.put(RoutingRulesConstants.PERMIT_JOBCODES, 
					permitJobCodes);

		} catch (NoResultException nre) {
			return null;
		}
		return validatedJobCodeList;
	}

	/**
	 * The method is used to validate a list of job Codes
	 * 
	 * @param List
	 *            <String> jobCodes :jobCodes to get validated
	 * @return HashMap<String,List<Object>> :VALID_JOBCODES has valid
	 *         jobcodes, INVALID_JOBCODES has invalid jobcodes
	 */
	public HashMap<String, List<String>> validateJobCodeList(
			List<String> jobCodes, Integer buyerId) {
		HashMap<String, List<String>> validatedJobCodeList = new HashMap<String, List<String>>();
		List<String> validJobCodes = new ArrayList<String>();
		List<String> inValidJobCodes = new ArrayList<String>();
		//R12_2 : SL-20643
		List<String> permitJobCodes = new ArrayList<String>();
		
		try {
			List<String> items = buyerSkuDao
					.validateJobCodes(jobCodes, buyerId);
			for (String jobCode : jobCodes) {
				if (items.contains(jobCode)) {
					//R12_2
					//SL-20643
					//If the buyer has a permit SKU, do not add it to the validJobCodes list
					if(RoutingRulesConstants.PERMIT_SKU.equalsIgnoreCase(jobCode)){
						permitJobCodes.add(jobCode);
					}else{
						validJobCodes.add(jobCode);
					}
				} else {
					inValidJobCodes.add(jobCode);
				}
			}

			validatedJobCodeList.put(RoutingRulesConstants.VALID_JOBCODES,
					validJobCodes);
			validatedJobCodeList.put(RoutingRulesConstants.INVALID_JOBCODES,
					inValidJobCodes);
			//R12_2 : SL-20643
			validatedJobCodeList.put(RoutingRulesConstants.PERMIT_JOBCODES,
					permitJobCodes);

		} catch (NoResultException nre) {
			return null;
		}
		return validatedJobCodeList;
	}

	/**
	 * The method is used to validate email
	 * 
	 * @param email:
	 *            the email to be validated
	 * @return boolean: returns true if valid email id or else returns false
	 */

	public boolean validateEmail(String email) {
		boolean valResult = false;
		if (email != null && email.length() > 1 && email.length() < 255) {
			// Set the email pattern string
			Pattern p = Pattern
					.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)");
			// Match the given string with the pattern
			Matcher m = p.matcher(email);
			valResult = m.matches();
		}
		return valResult;
	}

	/**
	 * The method is used to validate provider firm ids
	 * 
	 * @param firmIds :
	 *            the firm ids to be validated
	 * @return HashMap<String,List<Object>> :VALID_FIRMIDS has valid firm ids,
	 *         INVALID_FIRMIDS has invalid firm ids
	 */
	public HashMap<String, List<String>> validateFirmIds(List<String> firmIds) {
		HashMap<String, List<String>> validatedFirmIds = new HashMap<String, List<String>>();
		List<String> validFirmIds = new ArrayList<String>();
		List<String> inValidFirmIds = new ArrayList<String>();
		List<Integer> ids = new ArrayList<Integer>();
		for (String str : firmIds) {
			if (str.length() > 10) {
				inValidFirmIds.add(str);
				continue;
			}
			try {
				Integer integerId = Integer.valueOf(str);
				if (!ids.contains(integerId))
					ids.add(integerId);
			} catch (NumberFormatException e) {
				inValidFirmIds.add(str);
			}
		}
		try {
			if (!ids.isEmpty()) {
				List<Integer> items = providerFirmDao.validateFirmIds(ids);
				for (Integer firmId : ids) {
					if (items.contains(firmId)) {
						validFirmIds.add(firmId.toString());
					} else {
						inValidFirmIds.add(firmId.toString());
					}
				}
			}
			validatedFirmIds.put(RoutingRulesConstants.VALID_FIRMIDS,
					validFirmIds);
			validatedFirmIds.put(RoutingRulesConstants.INVALID_FIRMIDS,
					inValidFirmIds);

		} catch (NoResultException nre) {
			return null;
		}
		return validatedFirmIds;
	}

	/**
	 * The method is used to validate a list of custom references
	 * 
	 * @param List
	 *            <String> custRefs :custom references to get validated
	 * @return HashMap<String,List<Object>> :VALID_CUSTREF has valid buyer
	 *         references, INVALID_CUSTREF has invalid buyer references
	 */
	public HashMap<String, List<CustomReferenceVO>> validateCustomReference(
			List<CustomReferenceVO> custRefsVO, Integer buyerId) {
		HashMap<String, List<CustomReferenceVO>> validatedCustRefList = new HashMap<String, List<CustomReferenceVO>>();
		List<CustomReferenceVO> validCustRef = new ArrayList<CustomReferenceVO>();
		List<CustomReferenceVO> inValidCustRef = new ArrayList<CustomReferenceVO>();
		List<CustomReferenceVO> inValidCustRefVal = new ArrayList<CustomReferenceVO>();
		List<CustomReferenceVO> missingCustRef = new ArrayList<CustomReferenceVO>();
		// convert list to map to avoid duplicates
		List<CustomReferenceVO> uniqueList = new ArrayList<CustomReferenceVO>();
		List<String> custRefNameList = new ArrayList<String>();
		for (CustomReferenceVO refVO : custRefsVO) {
		if(refVO.getCustomRefName() != null && !refVO.getCustomRefName().equals("") ){
			if (refVO.getCustomRefName().length() > 30 ) {
				inValidCustRef.add(refVO);
				continue;
			}
			
			if(refVO.getCustomRefValue() != null && (refVO.getCustomRefValue().equals("")|| refVO.getCustomRefValue().length() > 30)){
				inValidCustRefVal.add(refVO);
				continue;
			}
			boolean addCust = true;
			if (uniqueList.isEmpty()) {
				uniqueList.add(refVO);
				custRefNameList.add(refVO.getCustomRefName());
			} else {
				for (CustomReferenceVO uniqRefVO : uniqueList) {
					if (uniqRefVO.getCustomRefName().equals(
							refVO.getCustomRefName())
							&& uniqRefVO.getCustomRefValue().equals(
									refVO.getCustomRefValue())) {
						addCust = false;
					}
				}
				if (addCust) {
					uniqueList.add(refVO);
					custRefNameList.add(refVO.getCustomRefName());
				}
			}
		} else {
			missingCustRef.add(refVO);
		}
		}
		try {
			if (!custRefNameList.isEmpty()) {
				boolean addInvalid = true;
				List<String> items = buyerReferenceTypeDao.getBuyerCustomReferences(buyerId);
				for (CustomReferenceVO uniqRefVO : uniqueList) {
					addInvalid = true;
					for(String item : items){
						if (item.equalsIgnoreCase(uniqRefVO.getCustomRefName())) {
							uniqRefVO.setCustomRefName(item);
							validCustRef.add(uniqRefVO);
							addInvalid = false;
							break;
						} 
					}
					if(addInvalid){
						inValidCustRef.add(uniqRefVO);
					}
				}
			}
			validatedCustRefList.put(RoutingRulesConstants.VALID_CUSTREF,
					validCustRef);
			validatedCustRefList.put(RoutingRulesConstants.INVALID_CUSTREF,
					inValidCustRef);
			validatedCustRefList.put(RoutingRulesConstants.INVALID_CUSTREF_VAL,
					inValidCustRefVal);
			validatedCustRefList.put(RoutingRulesConstants.MISSING_CUSTREF,
					missingCustRef);
	
		} catch (NoResultException nre) {
			return null;
		}
		return validatedCustRefList;
	}

	/**
	 * This method is used to validate rule name
	 * 
	 * @param ruleName
	 *            rule name to be validated
	 * @param buyerId
	 * @return boolean returns true if valid else returns false
	 */
	public boolean validateRuleName(String ruleName, Integer buyerId) {
		try {
			RoutingRuleHdr routingRule = routingRuleHdrDao
					.getRoutingRuleByName(ruleName, buyerId);
			if (routingRule == null)
				return false;
			else
				return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param lookupZipGeocodeDao
	 *            the lookupZipGeocodeDao to set
	 */
	public void setLookupZipGeocodeDao(LookupZipGeocodeDao lookupZipGeocodeDao) {
		this.lookupZipGeocodeDao = lookupZipGeocodeDao;
	}

	public ProviderFirmDao getProviderFirmDao() {
		return providerFirmDao;
	}

	public void setProviderFirmDao(ProviderFirmDao providerFirmDao) {
		this.providerFirmDao = providerFirmDao;
	}

	public BuyerSkuDao getBuyerSkuDao() {
		return buyerSkuDao;
	}

	public void setBuyerSkuDao(BuyerSkuDao buyerSkuDao) {
		this.buyerSkuDao = buyerSkuDao;
	}

	public BuyerReferenceTypeDao getBuyerReferenceTypeDao() {
		return buyerReferenceTypeDao;
	}

	public void setBuyerReferenceTypeDao(
			BuyerReferenceTypeDao buyerReferenceTypeDao) {
		this.buyerReferenceTypeDao = buyerReferenceTypeDao;
	}

	public RoutingRuleHdrDao getRoutingRuleHdrDao() {
		return routingRuleHdrDao;
	}

	public void setRoutingRuleHdrDao(RoutingRuleHdrDao routingRuleHdrDao) {
		this.routingRuleHdrDao = routingRuleHdrDao;
	}
}
