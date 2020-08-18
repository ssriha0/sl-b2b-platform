package com.servicelive.routingrulesengine.services.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.lookup.LookupRoutingRuleType;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.routingrules.AutoAcceptHistory;
import com.servicelive.domain.routingrules.RoutingRuleBuyerAssoc;
import com.servicelive.domain.routingrules.RoutingRuleCriteria;
import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleFileError;
import com.servicelive.domain.routingrules.RoutingRuleFileHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;
import com.servicelive.domain.routingrules.RoutingRulePrice;
import com.servicelive.domain.routingrules.RoutingRuleUploadRule;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.domain.userprofile.User;
import com.servicelive.routingrulesengine.RoutingRuleType;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.ContactDao;
import com.servicelive.routingrulesengine.dao.ProviderFirmDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleBuyerAssocDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleErrorDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleUploadRuleDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleVendorDao;
import com.servicelive.routingrulesengine.services.RoutingRuleFileParser;
import com.servicelive.routingrulesengine.services.RoutingRuleImportService;
import com.servicelive.routingrulesengine.services.RoutingRulesConflictFinderService;
import com.servicelive.routingrulesengine.services.ValidationService;
import com.servicelive.routingrulesengine.vo.CustomReferenceVO;
import com.servicelive.routingrulesengine.vo.JobPriceVO;
import com.servicelive.routingrulesengine.vo.RoutingRuleUploadRuleVO;

public class RoutingRuleImportServiceImpl extends RoutingRulesConstants
		implements RoutingRuleImportService {

	private RoutingRuleFileHdrDao routingRuleFileHdrDao;
	private ContactDao routingRuleContactDao;
	private RoutingRuleFileParser routingRuleFileParser;
	private RoutingRuleHdrDao routingRuleHdrDao;
	private RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao;
	private ProviderFirmDao providerFirmDao;
	private RoutingRuleErrorDao routingRuleErrorDao;
	private RoutingRulesConflictFinderService routingRulesConflictFinderService;
	private RoutingRuleUploadRuleDao routingRuleUploadRuleDao;
	private RoutingRuleVendorDao routingRuleVendorDao;
	private ValidationService validationService;
	private IApplicationProperties applicationProperties;
	//private IBuyerFeatureSetBO buyerFeatureSetBO;
	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.services.RoutingRuleImportService#startFileProcessing()
	 *      this method processes the routing rule uploaded files
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void startFileProcessing() {
		List<RoutingRuleFileHdr> routingRuleFileHdrs = getFileToBeProcessed();
		if (routingRuleFileHdrs == null)
			return;
		File destDir;
		for (RoutingRuleFileHdr routingRuleFileHdr : routingRuleFileHdrs) {
			File fileToProcess = fetchFile(routingRuleFileHdr);
			try {
				List<RoutingRuleUploadRuleVO> uploadedRules = routingRuleFileParser
						.parseFile(fileToProcess);
				for (RoutingRuleUploadRuleVO uploadRule : uploadedRules) {
					if (uploadRule.getAction().equals(UPLOAD_ACTION_ERROR)) {
						addFileError(routingRuleFileHdr, uploadRule
								.getErrorType(), null, uploadRule.getFileaction());
					} else if (uploadRule.getAction().equalsIgnoreCase(
							UPLOAD_ACTION_NEW)) {
						createRule(uploadRule, routingRuleFileHdr);
					} else if (uploadRule.getAction().equalsIgnoreCase(
							UPLOAD_ACTION_ADD)
							|| uploadRule.getAction().equalsIgnoreCase(
									UPLOAD_ACTION_DELETE)) {
						editRule(uploadRule, routingRuleFileHdr);
					} else {
						updateRuleStatus(uploadRule, routingRuleFileHdr);
					}
				}
				routingRuleFileHdr.setProcessCompletedTime(new Date());
				routingRuleFileHdrDao.update(routingRuleFileHdr);
				destDir = new File(ROUTING_RULE_SUCCESS_FILE_PATH);
			} catch (Exception e) {
				addFileError(routingRuleFileHdr, EXCEPTION, e.getMessage(), null);
				routingRuleFileHdr.setProcessCompletedTime(new Date());
				routingRuleFileHdr.setFileStatus(FILE_STATUS_FAILURE);
				destDir = new File(ROUTING_RULE_ERROR_FILE_PATH);
				e.printStackTrace();
			}
			try {
				FileUtils.moveFileToDirectory(fileToProcess, destDir, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Retrieves the info of files to be processed and changes their status to
	 * processing
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private List<RoutingRuleFileHdr> getFileToBeProcessed() {
		try {
			List<RoutingRuleFileHdr> routingRuleFileHdr = routingRuleFileHdrDao
					.fetchFilesForProcessing();
			if (routingRuleFileHdr != null && !routingRuleFileHdr.isEmpty()) {
				List<Integer> fileIds = new ArrayList<Integer>();
				for (RoutingRuleFileHdr fileHdr : routingRuleFileHdr) {
					fileIds.add(fileHdr.getRoutingRuleFileHdrId());
				}
				routingRuleFileHdrDao.changeStatusToProccessing(fileIds);
			}
			return routingRuleFileHdr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fetches the file from the directory location
	 * 
	 * @param RoutingRuleFileHdr
	 *            file
	 * @return File
	 */

	private File fetchFile(RoutingRuleFileHdr file) {
		String fileName = file.getRoutingRuleFileName();
		String ruleUploadDir = null;
		try {
			ruleUploadDir = applicationProperties
					.getPropertyValue(RoutingRulesConstants.ROUTING_RULE_FILE_DIR);
		} catch (DataNotFoundException e) {
			ruleUploadDir = RoutingRulesConstants.ROUTING_RULE_FILE_PATH;
		}
		String fileNameWithPath = ruleUploadDir + fileName;
		File fileForProccessing = new File(fileNameWithPath);
		return fileForProccessing;
	}

	/**
	 * This function creates a new rule from the parsed data.
	 * 
	 * @param RoutingRuleUploadRuleVO
	 *            uploadRule
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void createRule(RoutingRuleUploadRuleVO uploadRule,
			RoutingRuleFileHdr routingRuleFileHdr) {
		RoutingRuleHdr ruleHdr = new RoutingRuleHdr();
		BuyerResource buyerRes = cloneBuyerRes(routingRuleFileHdr
				.getUploadedBy());
		User user = buyerRes.getUser();
		String userName = user.getUsername();
		int buyerId = buyerRes.getBuyer().getBuyerId();
		boolean generalInfoError = false;
		boolean criteriaError = false;
		String action = uploadRule.getAction();
		String ruleName = uploadRule.getRuleName();
		if (ruleName.length() > ROUTING_RULE_HDR_RULE_NAME_LEN) {
			addFileError(routingRuleFileHdr, INVALID_INFO, "Rule Name", action);
			return;
		}
		boolean ruleNameValid = validationService.validateRuleName(ruleName,
				buyerId);
		
		if (ruleNameValid) {
			addFileError(routingRuleFileHdr, FILE_ERROR_RULE_EXISTS, ruleName, action);
			return;
		}
		ruleHdr.setRuleName(uploadRule.getRuleName());
		ruleHdr.setRuleStatus(ROUTING_RULE_STATUS_INACTIVE);
		ruleHdr.setModifiedBy(userName);
		ruleHdr.setContact(new Contact());
		ruleHdr.setRoutingRuleCriteria(new ArrayList<RoutingRuleCriteria>());
		ruleHdr.setRoutingRulePrice(new ArrayList<RoutingRulePrice>());
		ruleHdr.setRoutingRuleVendor(new ArrayList<RoutingRuleVendor>());
		ruleHdr.setRoutingRuleHdrHist(new ArrayList<RoutingRuleHdrHist>());
		generalInfoError = addRuleContact(uploadRule, ruleHdr,
				routingRuleFileHdr);
		criteriaError = addRuleCriterias(uploadRule, ruleHdr,
				routingRuleFileHdr, buyerId);
		if (generalInfoError || criteriaError) {
			ruleHdr.setRuleSubstatus(ROUTING_RULE_SUBSTATUS_ERROR);
			return;
		} else {
			ruleHdr.setRuleSubstatus(ROUTING_RULE_SUBSTATUS_VALID);
			String comment = ruleHdr.getRuleComment();
			ruleHdr.setRuleComment((comment.length() > ROUTING_RULE_HDR_COMMENT_LEN) 
		        	? comment.substring(0,ROUTING_RULE_HDR_COMMENT_LEN - 5) + " >>"
					: comment.toString());
			try {
				if (ruleHdr.getContact() != null
						&& ruleHdr.getContact().getContactId() == null) {
					routingRuleContactDao.save(ruleHdr.getContact());
				}
				RoutingRuleBuyerAssoc buyerAssoc = routingRuleBuyerAssocDao
						.findByBuyerId(buyerId);
				ruleHdr.setRoutingRuleBuyerAssocId(buyerAssoc);
				updateRuleHistory(ruleHdr, routingRuleFileHdr
						.getRoutingRuleFileName(), uploadRule.getAction(),
						buyerRes);
				routingRuleHdrDao.save(ruleHdr);
				// create routing rule upload rule
				RoutingRuleUploadRule routingRuleUploadRule = new RoutingRuleUploadRule(
						routingRuleFileHdr, uploadRule.getAction(),
						ROUTING_RULE_UPLOAD_SUCCESS, ruleHdr);
				routingRuleUploadRule.setModifiedBy(MODIFIED_BY_CAR);
				routingRuleUploadRuleDao.saveUploadRule(routingRuleUploadRule)
						.getRoutingRuleUploadRuleId();
				routingRuleFileHdr.setNumNewRules(routingRuleFileHdr
						.getNumNewRules() + 1);
				routingRuleFileHdr.setFileStatus(FILE_STATUS_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	/**
	 * Edits a existing rule from the parsed data
	 * 
	 * @param RoutingRuleUploadRuleVO
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void editRule(RoutingRuleUploadRuleVO uploadRule,
			RoutingRuleFileHdr routingRuleFileHdr) throws Exception {
		BuyerResource buyerRes = cloneBuyerRes(routingRuleFileHdr
				.getUploadedBy());
		String userName = buyerRes.getUser().getUsername();
		int buyerId = buyerRes.getBuyer().getBuyerId();
		boolean generalInfoError = false;
		boolean criteriaError = false;
		RoutingRuleHdr ruleHdr = getRoutingRuleHdr(uploadRule,
				routingRuleFileHdr, buyerId);
		String action = uploadRule.getAction();
		if (ruleHdr == null) {
			// no need to add error as error already added.
			return;
		}

		if (ruleHdr.getRuleStatus().equals(ROUTING_RULE_STATUS_ARCHIVED)) {
			updateUploadRule(ruleHdr, routingRuleFileHdr, action,
					ROUTING_RULE_UPLOAD_ERROR);
			// String ruleErrorDesc = "Rule is currently archive and cannot be
			// updated.";
			addFileError(routingRuleFileHdr, INVALID_STATUS_ARCHIVE, String
					.valueOf(ruleHdr.getRoutingRuleHdrId()), action);
			return;
		}
		// ////////////////////////////////////////////////////////////////////////////////////////////////////
		Integer buyerAssocId = ruleHdr.getRoutingRuleBuyerAssoc().getId();
		RoutingRuleHdr ruleCopy = copyRule(ruleHdr);
		ruleCopy.setModifiedBy(userName);
		if (action.equalsIgnoreCase(UPLOAD_ACTION_ADD)) {
			generalInfoError = addRuleContact(uploadRule, ruleCopy,
					routingRuleFileHdr);
			criteriaError = addRuleCriterias(uploadRule, ruleCopy,
					routingRuleFileHdr, buyerId);
			if (generalInfoError || criteriaError) {
				ruleCopy.setRuleSubstatus(ROUTING_RULE_SUBSTATUS_ERROR);
				return;
			}
		}
		if (action.equalsIgnoreCase(UPLOAD_ACTION_DELETE)) {
			String comments = uploadRule.getComments();
			generalInfoError = addRuleContact(uploadRule, ruleCopy,
					routingRuleFileHdr);
			criteriaError = deleteRuleCriteria(uploadRule, ruleCopy,
					routingRuleFileHdr, buyerId);
			if (criteriaError || generalInfoError) {
				return;
			}
		}
		if(!ruleCopy.getRuleUpdated()){
			addFileError(routingRuleFileHdr, FILE_ERROR_INVALID_FILE, "No values to update", action);
			return;
		}
		boolean error = checkForPersistentErrors(ruleCopy, routingRuleFileHdr, action);
		if (!error) {
			if (ruleHdr.getRuleStatus().equals(ROUTING_RULE_STATUS_ACTIVE)) {
				List<RoutingRuleError> conflicts = routingRulesConflictFinderService
						.findRoutingRuleConflictsForRuleHdr(ruleCopy,
								buyerAssocId,
								RoutingRulesConstants.UPDATE_FROM_BATCH);
				if (conflicts != null && !conflicts.isEmpty()) {
					RoutingRuleUploadRule routingRuleUploadRule = new RoutingRuleUploadRule(
							routingRuleFileHdr, uploadRule.getAction(),
							ROUTING_RULE_UPLOAD_ERROR, ruleCopy);
					routingRuleUploadRule.setModifiedBy(MODIFIED_BY_CAR);
					Integer uploadRuleId = routingRuleUploadRuleDao
							.saveUploadRule(routingRuleUploadRule)
							.getRoutingRuleUploadRuleId();
					/*RoutingRuleError ruleError = new RoutingRuleError(
							NON_PERSISTENT_ERROR,
							RULE_CONFLICT,
							"Updating this rule with the file data will cause a conflict",
							userName);
					ruleError.setRoutingRuleHdr(ruleHdr);
					ruleError.setRoutingRuleUploadRuleId(uploadRuleId);
					routingRuleErrorDao.save(ruleError);*/
					Set<Integer> conflictedWithSet = new HashSet<Integer>();
					for (RoutingRuleError conflict : conflicts) {
						conflict.setErrorType(NON_PERSISTENT_ERROR);
						conflict.setRoutingRuleHdr(ruleHdr);
						conflict.setModifiedBy(userName);
						conflict.setRoutingRuleUploadRuleId(uploadRuleId);
						if(null != conflict.getConflictingRuleId())
							conflictedWithSet.add(conflict.getConflictingRuleId());
						routingRuleErrorDao.save(conflict);
					}
					int numberOfConflictedRules = conflictedWithSet.size();
					routingRuleFileHdr.setNumConflicts(numberOfConflictedRules);
					routingRuleFileHdr.setFileStatus(FILE_STATUS_CONFLICT);
					return;
				}
			}
			String comment = ruleCopy.getRuleComment();
			ruleCopy.setRuleComment((comment.length() > ROUTING_RULE_HDR_COMMENT_LEN) 
		        	? comment.substring(0,ROUTING_RULE_HDR_COMMENT_LEN - 5) + " >>"
					: comment.toString()); 
			routingRuleErrorDao.deletePersistentErrors(ruleHdr
					.getRoutingRuleHdrId());
			ruleCopy.setRuleSubstatus(ROUTING_RULE_SUBSTATUS_VALID);
			buyerRes.setUser(buyerRes.getUser());
			updateRuleHistory(ruleCopy, routingRuleFileHdr
					.getRoutingRuleFileName(), uploadRule.getAction(),
					buyerRes);
			ruleCopy.setModifiedDate(new Date());
			routingRuleHdrDao.update(ruleCopy);
			RoutingRuleUploadRule routingRuleUploadRule = new RoutingRuleUploadRule(
					routingRuleFileHdr, uploadRule.getAction(),
					ROUTING_RULE_UPLOAD_SUCCESS, ruleCopy);
			routingRuleUploadRule.setModifiedBy(MODIFIED_BY_CAR);
			routingRuleUploadRuleDao.saveUploadRule(routingRuleUploadRule);
			routingRuleFileHdr.setNumUpdateRules(routingRuleFileHdr
					.getNumUpdateRules() + 1);
			routingRuleFileHdr.setFileStatus(FILE_STATUS_SUCCESS);
			if (ruleHdr.getRuleStatus().equals(ROUTING_RULE_STATUS_ACTIVE)) {
				List<RoutingRuleHdr> ruleList = new ArrayList<RoutingRuleHdr>();
				ruleList.add(ruleCopy);
				routingRulesConflictFinderService.updateActiveRulesCache(
						ruleList, RULE_ACTION_UPDATE, buyerAssocId);
			}
		}

	}

	/**
	 * Deletes the routing rule criteria ( zip ,job code, firm, cust ref) from
	 * the rule
	 * 
	 * @param uploadRule
	 * @param ruleHdr
	 * @param ruleErrorList
	 * @param buyerId
	 */
	private boolean deleteRuleCriteria(RoutingRuleUploadRuleVO uploadRule,
			RoutingRuleHdr ruleHdr, RoutingRuleFileHdr routingRuleFileHdr,
			Integer buyerId) throws Exception{
		boolean errorStatus = false;
		String action = uploadRule.getAction();
		StringBuffer comment = new StringBuffer(ruleHdr.getRuleComment());
		RoutingRuleHdr ruleCopy = copyRule(ruleHdr);
		List<RoutingRuleCriteria> criteriaList = ruleCopy
				.getRoutingRuleCriteria();
		List<String> zipsToDelete = uploadRule.getZipCodes();
		List<CustomReferenceVO> cusRefsToDelete = uploadRule.getCustRefs();
		List<JobPriceVO> jobCodesTodelete = uploadRule.getJobPrice();
		List<String> firmsToDelete = uploadRule.getProviderFirmIds();
		List<String> invalidCustRefs = new ArrayList<String>();
		boolean inValid = false;
		if (zipsToDelete != null && !zipsToDelete.isEmpty()) {
			List<String> invalidZips = new ArrayList<String>();
			for (String zip : zipsToDelete) {
				inValid = false;
				if (criteriaList != null && !criteriaList.isEmpty()) {
					for (RoutingRuleCriteria criteria : criteriaList) {
						if (criteria.getCriteriaName()
								.equals(CRITERIA_NAME_ZIP)
								&& criteria.getCriteriaValue().equals(zip)) {
							ruleHdr.getRoutingRuleCriteria().remove(criteria);
							ruleHdr.setRuleUpdated(true);
							inValid = false;
							comment.append("- " + CRITERIA_NAME_ZIP + "=" + zip + "\n");
							break;
						} else {
							inValid = true;
						}
					}
					if (inValid) {
						invalidZips.add(zip);
					}
				} else {
					invalidZips.add(zip);
				}
			}
			if (!invalidZips.isEmpty()) {
				String errorString = StringUtils.join(invalidZips, ",");
				addFileError(routingRuleFileHdr, INVALID_ZIP, errorString, action);
				errorStatus = true;
			}
		}
		if (cusRefsToDelete != null && !cusRefsToDelete.isEmpty()) {
			List<String> missingCustRefs = new ArrayList<String>();
			for (CustomReferenceVO custRef : cusRefsToDelete) {
				if(!custRef.getCustomRefName().equals("")){
				inValid = true;
				if (criteriaList != null && !criteriaList.isEmpty()) {
					for (RoutingRuleCriteria criteria : criteriaList) {
						if (!criteria.getCriteriaName().equals(
								CRITERIA_NAME_ZIP)) {
							if (criteria.getCriteriaName().equalsIgnoreCase(
									custRef.getCustomRefName())
									&& criteria.getCriteriaValue().equals(
											custRef.getCustomRefValue())) {
								ruleHdr.getRoutingRuleCriteria().remove(
										criteria);
								ruleHdr.setRuleUpdated(true);
								comment.append("- " + custRef.getCustomRefName() + "=" + custRef.getCustomRefValue() + "\n");
								inValid = false;
								break;
							} 
						}
					}
					if (inValid) {
						invalidCustRefs.add(custRef.getCustomRefName() + " : "
								+ custRef.getCustomRefValue());
					}
				} else {
					invalidCustRefs.add(custRef.getCustomRefName() + " : "
							+ custRef.getCustomRefValue());
				}
				}else{
					missingCustRefs.add(custRef.getCustomRefValue());
				}
			}
			if (!invalidCustRefs.isEmpty()) {
				String errorString = StringUtils.join(invalidCustRefs, ", ");
				addFileError(routingRuleFileHdr, INVALID_CUST_REFS, errorString, action);
				errorStatus = true;
			}
			if (!missingCustRefs.isEmpty()) {
				String errorString = StringUtils.join(missingCustRefs, ", ");
				addFileError(routingRuleFileHdr, CUST_REF_MISSING, errorString, action);
				errorStatus = true;
			}
		}
		if (jobCodesTodelete != null && !jobCodesTodelete.isEmpty()) {
			List<RoutingRulePrice> rulePrices = ruleCopy.getRoutingRulePrice();
			List<String> invalidJobCodes = new ArrayList<String>();
			List<String> missingJobCodes = new ArrayList<String>();
			for (JobPriceVO jobCode : jobCodesTodelete) {
			if(!jobCode.getJobCode().equals("")){
				inValid = false;
				if (rulePrices != null && !rulePrices.isEmpty()) {
					try {
						String jobCodeVal = jobCode.getJobCode();
						// Check if jobCode is Integer value to prefix 0
						@SuppressWarnings("unused")
						int jobCodeInt = Integer.valueOf(jobCodeVal);
						int len = jobCodeVal.length();
						if (len < 5) {
							StringBuilder jobCodeString = new StringBuilder();
							jobCodeString.append(jobCodeVal);
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
							jobCode.setJobCode(jobCodeString.toString());
						}
					} catch (NumberFormatException nfe) {
						// do nothing.
					}
					for (RoutingRulePrice rulePrice : rulePrices) {
						if (rulePrice.getJobcode().equals(jobCode.getJobCode())) {
							ruleHdr.getRoutingRulePrice().remove(rulePrice);
							ruleHdr.setRuleUpdated(true);
							inValid = false;
							comment.append("- SKU " + jobCode.getJobCode() + "\n");
							break;
						} else {
							inValid = true;
						}
					}
					if (inValid) {
						invalidJobCodes.add(jobCode.getJobCode());
					}
				} else {
					invalidJobCodes.add(jobCode.getJobCode());
				}
			} else {
				missingJobCodes.add("$"+jobCode.getPrice());
			}
			}
			if (!invalidJobCodes.isEmpty()) {
				String errorString = StringUtils.join(invalidJobCodes, ",");
				addFileError(routingRuleFileHdr, INVALID_JOBCODE, errorString, action);
				errorStatus = true;
			}
			if (!missingJobCodes.isEmpty()) {
				String errorString = StringUtils.join(missingJobCodes, ",");
				addFileError(routingRuleFileHdr, JOB_CODE_MISSING, errorString, action);
				errorStatus = true;
			}

		}
		if (firmsToDelete != null && !firmsToDelete.isEmpty()) {
			List<RoutingRuleVendor> ruleVendors = ruleCopy
					.getRoutingRuleVendor();
			List<AutoAcceptHistory> autoAccpetHistory=ruleCopy.getAutoAcceptHistory();
			List<String> invalidFirms = new ArrayList<String>();
			for (String firm : firmsToDelete) {
				inValid = false;
				if (ruleVendors != null && !ruleVendors.isEmpty()) {
					try {
						Integer firmId = Integer.valueOf(firm);
						for (RoutingRuleVendor ruleVendor : ruleVendors) {
							if (ruleVendor.getVendor().getId().equals(firmId)) {
								ruleHdr.getRoutingRuleVendor().remove(ruleVendor);
								//To delete history from auto accept table when particular firm deleted from a rule
								//ruleHdr.getAutoAcceptHistory().remove(deleteHistory);
								routingRuleVendorDao.deleteHistoryFromAutoAccpetHistory(Integer.parseInt(uploadRule.getRuleId()),firmId);
								ruleHdr.setRuleUpdated(true);
								inValid = false;
								comment.append("- Vendor=" + firm + "\n");
								break;
							} else {
								inValid = true;
							}
						}
						if (inValid) {
							invalidFirms.add(firm);
						}
					} catch (NumberFormatException e) {
						invalidFirms.add(firm);
					}
				} else {
					invalidFirms.add(firm);
				}
			}
			if (!invalidFirms.isEmpty()) {
				String errorString = StringUtils.join(invalidFirms, ",");
				addFileError(routingRuleFileHdr, INVALID_FIRMS, errorString, action);
				errorStatus = true;
			}
		}
		ruleHdr.setRuleComment(comment.toString());
		return errorStatus;
	}

	/**
	 * @return the routingRuleVendorDao
	 */
	public RoutingRuleVendorDao getRoutingRuleVendorDao() {
		return routingRuleVendorDao;
	}

	/**
	 * @param routingRuleVendorDao the routingRuleVendorDao to set
	 */
	public void setRoutingRuleVendorDao(RoutingRuleVendorDao routingRuleVendorDao) {
		this.routingRuleVendorDao = routingRuleVendorDao;
	}

	/**
	 * Creates a replica of the rule
	 * 
	 * @param ruleHdr
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private RoutingRuleHdr copyRule(RoutingRuleHdr ruleHdr) throws IllegalAccessException, InvocationTargetException {
		RoutingRuleHdr ruleCopy = new RoutingRuleHdr();
		Contact contact = new Contact();
		BeanUtils.copyProperties(contact, ruleHdr.getContact());
		ruleCopy.setContact(contact);
		ruleCopy.setCreatedDate(ruleHdr.getCreatedDate());
		ruleCopy.setRoutingRuleAlerts(ruleHdr.getRoutingRuleAlerts());
		ruleCopy.setRoutingRuleBuyerAssocId(ruleHdr.getRoutingRuleBuyerAssoc());
		List<RoutingRuleCriteria> criteriaList = new ArrayList<RoutingRuleCriteria>();
		if (!ruleHdr.getRoutingRuleCriteria().isEmpty()) {
			for (RoutingRuleCriteria criteria : ruleHdr
					.getRoutingRuleCriteria()) {
				criteriaList.add(criteria);
			}
		}
		ruleCopy.setRoutingRuleCriteria(criteriaList);
		ruleCopy.setRoutingRuleError(ruleHdr.getRoutingRuleError());
		ruleCopy.setRoutingRuleHdrId(ruleHdr.getRoutingRuleHdrId());
		List<RoutingRulePrice> priceList = new ArrayList<RoutingRulePrice>();
		if (!ruleHdr.getRoutingRulePrice().isEmpty()) {
			for (RoutingRulePrice price : ruleHdr.getRoutingRulePrice()) {
				priceList.add(price);
			}
		}
		//SL 15642 Added to get the history and set it in list
		List<AutoAcceptHistory> autoAcceptHistoryList=new ArrayList<AutoAcceptHistory>();
		if(!ruleHdr.getAutoAcceptHistory().isEmpty())
		{
			for(AutoAcceptHistory autoAccpet:ruleHdr.getAutoAcceptHistory())
			{
				autoAcceptHistoryList.add(autoAccpet);
			}
			
		}
		ruleCopy.setAutoAcceptHistory(autoAcceptHistoryList);
		//End of SL 15642 
		
		ruleCopy.setRoutingRulePrice(priceList);
		List<RoutingRuleVendor> vendorList = new ArrayList<RoutingRuleVendor>();
		for (RoutingRuleVendor vendor : ruleHdr.getRoutingRuleVendor()) {
			vendorList.add(vendor);
		}
		ruleCopy.setRoutingRuleVendor(vendorList);
		ruleCopy.setRuleComment(ruleHdr.getRuleComment());
		ruleCopy.setRuleName(ruleHdr.getRuleName());
		ruleCopy.setRuleStatus(ruleHdr.getRuleStatus());
		ruleCopy.setRuleSubstatus(ruleHdr.getRuleSubstatus());
		ruleCopy.setModifiedBy(ruleHdr.getModifiedBy());
		ruleCopy.setModifiedDate(ruleHdr.getModifiedDate());
		List<RoutingRuleHdrHist> hdrHistList = new ArrayList<RoutingRuleHdrHist>();
		if (!ruleHdr.getRoutingRuleHdrHist().isEmpty()) {
			for (RoutingRuleHdrHist hdrHist : ruleHdr.getRoutingRuleHdrHist()) {
				hdrHistList.add(hdrHist);
			}
		}
		ruleCopy.setRoutingRuleHdrHist(hdrHistList);
		return ruleCopy;
	}

	/**
	 * Updates the rule status of the rules parsed from the file
	 * 
	 * @param uploadRule,
	 *            routingRuleFileHdr
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private void updateRuleStatus(RoutingRuleUploadRuleVO uploadRule,
			RoutingRuleFileHdr routingRuleFileHdr) throws Exception {
		String action = uploadRule.getAction();
		BuyerResource buyerRes = cloneBuyerRes(routingRuleFileHdr
				.getUploadedBy());
		String userName = buyerRes.getUser().getUsername();
		int buyerId = buyerRes.getBuyer().getBuyerId();
		RoutingRuleBuyerAssoc buyerAssoc = routingRuleBuyerAssocDao
				.findByBuyerId(buyerId);
		String comments = uploadRule.getComments();
		boolean error = false;
		boolean commentError = validateComment(comments, routingRuleFileHdr, action);
		if (commentError) {
			error = true;
		}
		List<Integer> allExistingRuleIds = routingRuleHdrDao
				.getAllRuleHdrIds(buyerAssoc.getId());
		List<String> invalidRuleIds = new ArrayList<String>();
		List<String> validRuleIds = new ArrayList<String>();
		if (!allExistingRuleIds.isEmpty()) {
			List<String> ruleIdsFromFile = uploadRule.getRuleIds();
			for (String ruleIdString : ruleIdsFromFile) {
				try {
					Integer ruleId = Integer.valueOf(ruleIdString);
					if (!allExistingRuleIds.contains(ruleId)) {
						invalidRuleIds.add(ruleIdString);
					} else {
						validRuleIds.add(ruleIdString);
					}
				} catch (NumberFormatException nfe) {
					invalidRuleIds.add(ruleIdString);
				}
			}
			if (!invalidRuleIds.isEmpty()) {
				addFileError(routingRuleFileHdr, FILE_ERROR_INVALID_RULE,
						StringUtils.join(invalidRuleIds, ","), action);
				error = true;
			}
			String status = null;
			invalidRuleIds = new ArrayList<String>();
			if (!validRuleIds.isEmpty()) {
				List<Integer> validRules = new ArrayList<Integer>();
				if (action.equals(UPLOAD_ACTION_ARCHIVE)) {
					validRules = routingRuleHdrDao
							.getAllRuleHdrIdsExceptActive(validRuleIds);
					status = ROUTING_RULE_STATUS_ARCHIVED;
				} else {
					validRules = routingRuleHdrDao
							.getAllRuleHdrIdsExceptArchive(validRuleIds);
					status = ROUTING_RULE_STATUS_INACTIVE;
				}
				for (String ruleIdString : validRuleIds) {
					Integer ruleId = Integer.valueOf(ruleIdString);
					if (!validRules.contains(ruleId)) {
						invalidRuleIds.add(ruleIdString);
					}
				}
			} else {error =true;}
			if (!invalidRuleIds.isEmpty()) {
				addFileError(routingRuleFileHdr, INVALID_STATUS, StringUtils
						.join(invalidRuleIds, ","), action);
				error = true;
			}
			if (error) {
				return;
			} else {
				boolean rulesUpdated = false;
				List<RoutingRuleHdr> ruleList = new ArrayList<RoutingRuleHdr>();
				for (String ruleId : ruleIdsFromFile) {
					if (action.equals(UPLOAD_ACTION_ARCHIVE)) {
						routingRuleErrorDao.deletePersistentErrors(Integer
								.valueOf(ruleId));
					}
					RoutingRuleHdr ruleHdr = routingRuleHdrDao
							.findByRoutingRuleHdrId(Integer.valueOf(ruleId));
					/* If the rule is already archived and agin trying to archive it,
					 * Ignore it. If the rule is already inactive and again trying
					 * to inactivate, ignore it */
					if(null!=ruleHdr){
						if((ROUTING_RULE_STATUS_ARCHIVED.equalsIgnoreCase(ruleHdr.getRuleStatus())
							&& UPLOAD_ACTION_ARCHIVE.equalsIgnoreCase(action))
							|| (ROUTING_RULE_STATUS_INACTIVE.equalsIgnoreCase(ruleHdr.getRuleStatus())
							&& UPLOAD_ACTION_INACTIVE.equalsIgnoreCase(action))){
							continue;
						}
					}
					
					ruleHdr.setRuleStatus(status);
					ruleHdr.setRuleSubstatus(ROUTING_RULE_SUBSTATUS_VALID);
					ruleHdr.setModifiedBy(userName);
					ruleHdr.setRuleComment(uploadRule.getComments());
					ruleHdr.setModifiedDate(new Date());
					updateRuleHistory(ruleHdr, routingRuleFileHdr
							.getRoutingRuleFileName(), action, buyerRes);
					routingRuleHdrDao.update(ruleHdr);
					routingRuleUploadRuleDao.addUploadRule(routingRuleFileHdr
							.getRoutingRuleFileHdrId(),
							Integer.valueOf(ruleId), action);
					routingRuleFileHdr.setNumUpdateRules(routingRuleFileHdr
							.getNumUpdateRules() + 1);
					ruleList.add(ruleHdr);
					rulesUpdated = true;
				}
				if(!rulesUpdated){
					addFileError(routingRuleFileHdr, FILE_ERROR_INVALID_FILE, "No values to update", action);
					return;
				}
				routingRuleFileHdr.setFileStatus(FILE_STATUS_SUCCESS);
				if (status.equals(ROUTING_RULE_STATUS_INACTIVE)) {
					routingRulesConflictFinderService
							.updateActiveRulesCache(ruleList,
									RULE_ACTION_INACTIVATE, buyerAssoc.getId());
				}
				if (status.equals(ROUTING_RULE_STATUS_ARCHIVED)) {
					routingRulesConflictFinderService
							.updateActiveRulesCache(ruleList,
									RULE_ACTION_ARCHIVED, buyerAssoc.getId());
				}
			}

		}
	}

	/**
	 * Fetches the ruleHdr for the rulId / ruleName
	 * 
	 * @param uploadRule
	 * @param routingRuleFileHdr
	 * @param buyerId
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private RoutingRuleHdr getRoutingRuleHdr(
			RoutingRuleUploadRuleVO uploadRule,
			RoutingRuleFileHdr routingRuleFileHdr, int buyerId) {
		RoutingRuleHdr routingRule = null;
		String ruleId = uploadRule.getRuleId();
		String action = uploadRule.getAction();
		if (ruleId != null && !ruleId.equals("")) {
			try {
				Integer ruleIdVal = Integer.valueOf(uploadRule.getRuleId());
				routingRule = routingRuleHdrDao
						.findByRoutingRuleHdrId(ruleIdVal);
			} catch (NumberFormatException e) {
				// do nothing as the following code will take care of the error
				e.printStackTrace();
			}
		}
		if (routingRule == null) {
			addFileError(routingRuleFileHdr, FILE_ERROR_INVALID_RULE,
						ruleId, action);
			return null;	
		}
		return routingRule;
	}

	/**
	 * 
	 * Inserts a new row into the routing_rule_upload_rule table
	 * 
	 * @param routingRule
	 * @param routingRuleFileHdr
	 * @param action
	 * @param uploadRuleStatus
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private RoutingRuleUploadRule updateUploadRule(RoutingRuleHdr routingRule,
			RoutingRuleFileHdr routingRuleFileHdr, String action,
			String uploadRuleStatus) {

		RoutingRuleUploadRule routingRuleUploadRule = new RoutingRuleUploadRule(
				routingRuleFileHdr, action, uploadRuleStatus, routingRule);
		routingRuleUploadRule.setModifiedBy(MODIFIED_BY_CAR);
		return routingRuleUploadRuleDao.saveUploadRule(routingRuleUploadRule);

	}

	/**
	 * Check for file error and add them to the list for persisting
	 * 
	 * @param routingRuleFileHdr
	 * @param errorType
	 * @param errorMsg
	 */
	private void addFileError(RoutingRuleFileHdr routingRuleFileHdr,
			Integer errorType, String errorMsg, String action) {
		List<RoutingRuleFileError> errorList = routingRuleFileHdr
				.getRoutingRuleFileError();
		boolean errorTypePresent = false;
		if (errorList != null && !errorList.isEmpty()) {
			for (RoutingRuleFileError error : routingRuleFileHdr
					.getRoutingRuleFileError()) {
				if (error.getErrorType().equals(errorType)) {
					if (errorType.equals(FILE_ERROR_INVALID_RULE)) {
						StringBuilder ruleIds = new StringBuilder();
						ruleIds.append(error.getErrorDesc());
						ruleIds.append(",");
						ruleIds.append(errorMsg);
						error.setErrorDesc(ruleIds.toString());
						errorTypePresent = true;
						routingRuleFileHdr.setNumErrors(routingRuleFileHdr
								.getNumErrors() + 1);
					}
				}
			}
		} else {
			List<RoutingRuleFileError> fileErrorList = new ArrayList<RoutingRuleFileError>();
			routingRuleFileHdr.setRoutingRuleFileError(fileErrorList);
		}
		if (!errorTypePresent) {
			RoutingRuleFileError fileError = new RoutingRuleFileError(
					routingRuleFileHdr, errorType, errorMsg, action);
			routingRuleFileHdr.getRoutingRuleFileError().add(fileError);
			routingRuleFileHdr
					.setNumErrors(routingRuleFileHdr.getNumErrors() + 1);
		}
		routingRuleFileHdr.setFileStatus(FILE_STATUS_ERROR);
	}

	/**
	 * Add rule contact for new rule/ updates contact in case of Add(update)
	 * 
	 * @param uploadRule
	 * @param ruleHdr
	 * @param ruleErrorList
	 * @return
	 */
	private boolean addRuleContact(RoutingRuleUploadRuleVO uploadRule,
			RoutingRuleHdr ruleHdr, RoutingRuleFileHdr fileHdr) {
		boolean errorSubStatus = false;
		String action = uploadRule.getAction(); 
		StringBuffer comment= new StringBuffer();
		errorSubStatus = validateComment(uploadRule.getComments(), fileHdr, action);
		if (!errorSubStatus) {
			ruleHdr.setRuleComment(uploadRule.getComments());
			comment.append(ruleHdr.getRuleComment() + "\n---\n");
		}
		if(uploadRule.getAction().equalsIgnoreCase(UPLOAD_ACTION_NEW)){
			comment.append("+ RuleName=" + ruleHdr.getRuleName() + "\n");
		}
		if (uploadRule.getContactFirstName() != null
				&& !uploadRule.getContactFirstName().equals("")) {
			if (uploadRule.getContactFirstName().length() > CONTACT_NAME_LEN) {
				addFileError(fileHdr, INVALID_INFO, "Contact First Name", action);
				errorSubStatus = true;
			} else {
				if(!uploadRule.getContactFirstName().equals(ruleHdr.getContact().getFirstName())){
					if(ruleHdr.getContact().getFirstName() != null){
						 comment.append("/ FirstName: " + ruleHdr.getContact().getFirstName() + " -> " + uploadRule.getContactFirstName() + "\n");
					}
					ruleHdr.getContact().setFirstName(
							uploadRule.getContactFirstName());
					ruleHdr.setRuleUpdated(true);
				}
			}
		} else {
			addFileError(fileHdr, MISSING_MANDATORY, "Contact First Name", action);
			errorSubStatus = true;
		}
		if (uploadRule.getContactLastName() != null
				&& !uploadRule.getContactLastName().equals("")) {
			if (uploadRule.getContactLastName().length() > CONTACT_NAME_LEN) {
				addFileError(fileHdr, INVALID_INFO, "Contact Last Name", action);
				errorSubStatus = true;
			} else {
				if(!uploadRule.getContactLastName().equals(ruleHdr.getContact().getLastName())){
					if(ruleHdr.getContact().getLastName() != null){
						 comment.append("/ LastName: " + ruleHdr.getContact().getLastName() + " -> " + uploadRule.getContactLastName() + "\n");
					}
					ruleHdr.getContact().setLastName(
							uploadRule.getContactLastName());
					ruleHdr.setRuleUpdated(true);
				}
			}
		} else {
			addFileError(fileHdr, MISSING_MANDATORY, "Contact Last Name", action);
			errorSubStatus = true;
		}
		if (uploadRule.getEmailId() != null
				&& !uploadRule.getEmailId().equals("")) {
			boolean valid = validationService.validateEmail(uploadRule
					.getEmailId());
			if (valid) {
				if(!uploadRule.getEmailId().equals(ruleHdr.getContact().getEmail())){
					if(ruleHdr.getContact().getEmail() != null){
						 comment.append("/ Email: " + ruleHdr.getContact().getEmail() + " -> " + uploadRule.getEmailId() + "\n");
					}
					ruleHdr.getContact().setEmail(uploadRule.getEmailId());
					ruleHdr.setRuleUpdated(true);
				}
			} else {
				addFileError(fileHdr, INVALID_INFO, "Contact Email Id", action);
				errorSubStatus = true;
			}
		} else {
			addFileError(fileHdr, MISSING_MANDATORY, "Contact Email Id", action);
			errorSubStatus = true;
		}
		ruleHdr.setRuleComment(comment.toString());
		return errorSubStatus;
	}

	/**
	 * Checks for persistent errors in the rule
	 * 
	 * @param ruleHdr
	 * @param ruleErrorList
	 * @return
	 */
	private Boolean checkForPersistentErrors(RoutingRuleHdr ruleHdr,
			RoutingRuleFileHdr routingRuleFileHdr, String action) {
		boolean errorSubStatus = false;
		if (ruleHdr.getRoutingRuleVendor() == null
				|| ruleHdr.getRoutingRuleVendor().isEmpty()) {
			addFileError(routingRuleFileHdr, MANDATORY_DELETED,
					"Provider Firm Id", action);
			errorSubStatus = true;
		}
		if ((ruleHdr.getRoutingRuleCriteria() == null || ruleHdr
				.getRoutingRuleCriteria().isEmpty())
				&& (ruleHdr.getRoutingRulePrice() == null || ruleHdr
						.getRoutingRulePrice().isEmpty())) {
			addFileError(routingRuleFileHdr, MANDATORY_DELETED,
					"Rule Preferences", action);
			errorSubStatus = true;
		}
		return errorSubStatus;
	}

	/**
	 * Add rule criteria (zip code, job code, custom ref, vendor ) to the rule.
	 * 
	 * @param uploadRule
	 * @param ruleHdr
	 * @param ruleErrorList
	 * @param buyerId
	 * @return
	 */
	private boolean addRuleCriterias(RoutingRuleUploadRuleVO uploadRule,
			RoutingRuleHdr ruleHdr, RoutingRuleFileHdr routingRuleFileHdr,
			Integer buyerId) {
		boolean errorSubStatus = false;
		String action = uploadRule.getAction();
		StringBuffer comment = new StringBuffer(ruleHdr.getRuleComment());
		if (uploadRule.getProviderFirmIds() != null
				&& !uploadRule.getProviderFirmIds().isEmpty()) {
			HashMap<String, List<String>> validatedList = validationService
					.validateFirmIds(uploadRule.getProviderFirmIds());
			List<String> invalidProviderFirms = validatedList
					.get(INVALID_FIRMIDS);
			;
			if (invalidProviderFirms != null && !invalidProviderFirms.isEmpty()) {
				String error = StringUtils.join(invalidProviderFirms, ",");
				addFileError(routingRuleFileHdr, INVALID_FIRMS, error, action);
				errorSubStatus = true;
			}
			//SL 15642 Update auto accept status of provider firm  to PENDING even the uploaded file don't have any provider added 
			// and insert entry inside auto accept history table
			//SL 15642 To update auto accept history if Auto Accept Status ON
			for(RoutingRuleVendor vendor:ruleHdr.getRoutingRuleVendor())
				{
					if(null!=vendor.getAutoAcceptStatus())
						{
							if(vendor.getAutoAcceptStatus().equalsIgnoreCase(RoutingRulesConstants.AUTO_ACCEPT_ON_STATUS))
							{
								try
								{
							routingRuleVendorDao.updateAutoStatusForRule(vendor.getVendor().getId(),ruleHdr.getRoutingRuleHdrId());
							//Changes to send mail to provider on auto accept status change
							AlertTask alertTask=new AlertTask();
							Date currentdate = new Date();
							alertTask.setAlertedTimestamp(null);
							alertTask.setAlertTypeId(AlertConstants.ALERT_TYPE_ID);
							alertTask.setCreatedDate(currentdate);
							alertTask.setModifiedDate(currentdate);
							//setting template_id
							alertTask.setTemplateId(AlertConstants.TEMPLATE_AUTO_ACCPET_CHANGE_PROVIDER_CHECK_EMAIL);
							//Method to fetch provider email id from DB
							String providerAdminEmailId= routingRuleVendorDao.getProviderAdminEmailId(vendor.getRoutingRuleVendorId());
							alertTask.setAlertFrom(Constants.EMPTY_STRING);
							alertTask.setAlertTo(providerAdminEmailId);
							alertTask.setAlertBcc(Constants.EMPTY_STRING);
							alertTask.setAlertCc(Constants.EMPTY_STRING);
							alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
							alertTask.setPriority(AlertConstants.PRIORITY);
							//generating template_input_value from Map
							Map<String, String> alertMap = new HashMap<String, String>();
							alertMap.put(Constants.FIRM_ID, Integer.toString(vendor.getRoutingRuleVendorId()));
							//Method to get vendor detail to send email 
							ServiceProvider rrv=routingRuleVendorDao.getProviderDetail(vendor.getRoutingRuleVendorId());
							alertMap.put(Constants.BUYER_NAME, ruleHdr.getRoutingRuleBuyerAssoc().getBuyer().getContact().getFullName());
							alertMap.put(Constants.PROVIDER_ADMIN_EMAIL_ID, providerAdminEmailId);
							alertMap.put(Constants.PROVIDER_FIRST_NAME, rrv.getContact().getFirstName());
							alertMap.put(Constants.PROVIDER_LAST_NAME, rrv.getContact().getLastName());
							alertMap.put(Constants.FIRM_NAME, rrv.getProviderFirm().getBusinessName());
							alertMap.put(Constants.RULE_NAME,ruleHdr.getRuleName());
							alertMap.put(Constants.BUYER_EMAIL, ruleHdr.getRoutingRuleBuyerAssoc().getBuyer().getContact().getEmail());
							alertTask.setTemplateInputValue(createMailMergeValueStringFromMap(alertMap));
							routingRuleVendorDao.saveAutoAcceptChangeProviderMailInfo(alertTask);
							String adoptedBy="";
							if(null!=ruleHdr.getModifiedBy() && ruleHdr.getModifiedBy().equalsIgnoreCase(routingRuleFileHdr.getModifiedBy()))
							{
								adoptedBy=null;
							}
							else
							{
								adoptedBy=routingRuleFileHdr.getModifiedBy();
							}
						   routingRuleVendorDao.updateAutoAcceptHistoryOnStatusChange(ruleHdr.getRoutingRuleHdrId(), RoutingRulesConstants.AUTO_ACCEPT_PENDING_STATUS,vendor.getRoutingRuleVendorId(),routingRuleFileHdr.getModifiedBy(),3, RoutingRulesConstants.AUTO_ACCEPT_HISTORY_UPDATED,adoptedBy);
							}
								 catch (Exception e) {
										// save error
									}
								}
					}
				}
			List<String> validProviderFirms = validatedList.get(VALID_FIRMIDS);
			if (validProviderFirms != null && !validProviderFirms.isEmpty()) {
				List<RoutingRuleVendor> vendorList = new ArrayList<RoutingRuleVendor>();
				List<String> existingVendors = new ArrayList<String>();
				if (uploadRule.getAction().equalsIgnoreCase(UPLOAD_ACTION_ADD)) {
					existingVendors = getExistingVendors(ruleHdr
							.getRoutingRuleVendor());
				}
				//SL 15642 Add auto accept status for a newly uploaded rule
				boolean autoAcceptFeatureBuyer=routingRuleBuyerAssocDao.validateFeature(buyerId, BuyerFeatureConstants.AUTO_ACCEPTANCE);
				String autoAcceptStatus="";
				if (uploadRule.getAction().equalsIgnoreCase(UPLOAD_ACTION_NEW)) 
				{
					if(autoAcceptFeatureBuyer==true)
					{
						autoAcceptStatus=RoutingRulesConstants.AUTO_ACCEPT_PENDING_STATUS;
					}
					else
					{
						autoAcceptStatus=RoutingRulesConstants.AUTO_ACCEPT_NA_STATUS;
					}
					if(null!=autoAcceptStatus && !autoAcceptStatus.equalsIgnoreCase("NA"))
					{
						updateAutoAcceptHistory(ruleHdr,autoAcceptStatus,null,routingRuleFileHdr,3,RoutingRulesConstants.AUTO_ACCEPT_HISTORY_CREATED);
					}
				}
				for (String firmId : validProviderFirms) {
					try {
						if (!existingVendors.isEmpty()
									&& existingVendors.contains(firmId)) {
							continue;
						}
						ProviderFirm providerFirm = providerFirmDao
								.findById(Integer.valueOf(firmId));
						Integer providerState = providerFirm
								.getServiceLiveWorkFlowState().getId();
						List<String> firmsWithInvalidState = new ArrayList<String>();
						if (providerState.equals(3) || providerState.equals(34)) {
							RoutingRuleVendor vendor = new RoutingRuleVendor();
							vendor.setVendor(providerFirm);
							vendor.setModifiedBy(ruleHdr.getModifiedBy());
							vendor.setRoutingRuleHdr(ruleHdr);
							
									if(autoAcceptFeatureBuyer==true)
									{
										autoAcceptStatus=RoutingRulesConstants.AUTO_ACCEPT_PENDING_STATUS;
										vendor.setAutoAcceptStatus(autoAcceptStatus);
									}
									else
									{
										autoAcceptStatus=RoutingRulesConstants.AUTO_ACCEPT_NA_STATUS;
										vendor.setAutoAcceptStatus(autoAcceptStatus);
									}
									
							vendorList.add(vendor);
							comment.append("+ Vendor=" + firmId + "\n");	
						} else {
							firmsWithInvalidState.add(firmId);
						}
						if (!firmsWithInvalidState.isEmpty()) {
							String error = StringUtils.join(
									firmsWithInvalidState, ",");
							addFileError(routingRuleFileHdr,
									INVALID_FIRM_STATE, error, action);
							errorSubStatus = true;
						}
						
					}
					 catch (Exception e) {
						// save error
					}
				}
				if(!vendorList.isEmpty()){
					ruleHdr.getRoutingRuleVendor().addAll(vendorList);
					ruleHdr.setRuleUpdated(true);
				}
			} 
		} else {
			if (uploadRule.getAction().equalsIgnoreCase(UPLOAD_ACTION_NEW)) {
				addFileError(routingRuleFileHdr, MISSING_MANDATORY,
						"Provider Firm Id", action);
				errorSubStatus = true;
			}
		}
		if ((uploadRule.getJobPrice() == null || uploadRule.getJobPrice()
				.isEmpty())
				&& (uploadRule.getCustRefs() == null || uploadRule
						.getCustRefs().isEmpty())
				&& (uploadRule.getZipCodes() == null || uploadRule
						.getZipCodes().isEmpty())) {
			if (uploadRule.getAction().equalsIgnoreCase(UPLOAD_ACTION_NEW)) {
				addFileError(routingRuleFileHdr, MISSING_MANDATORY,
						"Rule Preferences", action);
				errorSubStatus = true;
			}
		} else {
			boolean zipError = false;
			List<RoutingRuleCriteria> criteriaList = new ArrayList<RoutingRuleCriteria>();
			if (uploadRule.getZipCodes() != null
					&& !uploadRule.getZipCodes().isEmpty()) {
				HashMap<String, List<String>> validatedZipList = validationService
						.validateZipCodes(uploadRule.getZipCodes());
				List<String> invalidZipList = validatedZipList
						.get(INVALID_ZIPS);
				if (invalidZipList != null && !invalidZipList.isEmpty()) {
					String errorDesc = StringUtils.join(invalidZipList, ",");
					addFileError(routingRuleFileHdr, INVALID_ZIP, errorDesc, action);
					errorSubStatus = true;
				} else {
					List<String> validZipList = validatedZipList
							.get(VALID_ZIPS);
					if (validZipList != null && !validZipList.isEmpty()) {
						List<String> existingZipCodes = new ArrayList<String>();
						if (uploadRule.getAction().equalsIgnoreCase(
								UPLOAD_ACTION_ADD)) {
							existingZipCodes = getExistingZipCodes(ruleHdr
									.getRoutingRuleCriteria());
						}
						for (String zipCode : validZipList) {
							if (!existingZipCodes.isEmpty()
									&& existingZipCodes.contains(zipCode)) {
								continue;
							}
							RoutingRuleCriteria criteria = new RoutingRuleCriteria();
							criteria.setCriteriaName(CRITERIA_NAME_ZIP);
							criteria.setCriteriaValue(zipCode);
							criteria.setRuleTypeId(new LookupRoutingRuleType(
									RoutingRuleType.ZIP_MARKET.getId()));
							criteria.setModifiedBy(ruleHdr.getModifiedBy());
							criteria.setRoutingRuleHdr(ruleHdr);
							criteriaList.add(criteria);
							comment.append("+ " + CRITERIA_NAME_ZIP + "=" + zipCode + "\n");
						}
					} else {
						zipError = true;
					}
				}
			}
			if(!criteriaList.isEmpty()){
				ruleHdr.getRoutingRuleCriteria().addAll(criteriaList);
				ruleHdr.setRuleUpdated(true);
			}
				
			boolean custRefError = false;
			if (uploadRule.getCustRefs() != null
					&& !uploadRule.getCustRefs().isEmpty()) {
				HashMap<String, List<CustomReferenceVO>> validatedCustrefList = validationService
						.validateCustomReference(uploadRule.getCustRefs(),
								buyerId);
				List<CustomReferenceVO> invalidCustRef = validatedCustrefList
						.get(INVALID_CUSTREF);
				List<CustomReferenceVO> invalidCustRefVal = validatedCustrefList
						.get(INVALID_CUSTREF_VAL);
				List<CustomReferenceVO> missingCustRef = validatedCustrefList
						.get(MISSING_CUSTREF);
				if (!invalidCustRef.isEmpty() || !invalidCustRefVal.isEmpty() || !missingCustRef.isEmpty()) {
					if (!invalidCustRef.isEmpty()) {
						StringBuilder error = new StringBuilder("");
						Iterator<CustomReferenceVO> iterator = invalidCustRef
								.listIterator();
						while (iterator.hasNext()) {
							error.append(iterator.next().getCustomRefName());
							error.append(",");
						}
						error.deleteCharAt(error.length() - 1);
						addFileError(routingRuleFileHdr, INVALID_CUST_REFS,
								error.toString(), action);
					}
					if (!invalidCustRefVal.isEmpty()) {
						StringBuilder error = new StringBuilder("");
						Iterator<CustomReferenceVO> iterator = invalidCustRefVal
								.listIterator();
						while (iterator.hasNext()) {
							error.append(iterator.next().getCustomRefName());
							error.append(",");
						}
						error.deleteCharAt(error.length() - 1);
						addFileError(routingRuleFileHdr,
								INVALID_CUST_REF_VALUE, error.toString(), action);
					}
					if (!missingCustRef.isEmpty()) {
						StringBuilder error = new StringBuilder("");
						Iterator<CustomReferenceVO> iterator = missingCustRef
								.listIterator();
						while (iterator.hasNext()) {
							error.append(iterator.next().getCustomRefValue());
							error.append(",");
						}
						error.deleteCharAt(error.length() - 1);
						addFileError(routingRuleFileHdr,
								CUST_REF_MISSING, error.toString(), action);
					}
					errorSubStatus = true;
				} else {
					List<CustomReferenceVO> validCustRef = validatedCustrefList
							.get(VALID_CUSTREF);
					if (validCustRef != null && !validCustRef.isEmpty()) {
						boolean addRow = true;
						for (CustomReferenceVO custRef : validCustRef) {
							for (RoutingRuleCriteria ruleCriteria : ruleHdr
									.getRoutingRuleCriteria()) {
								if (ruleCriteria.getCriteriaName().equals(
										custRef.getCustomRefName())
										&& ruleCriteria
												.getCriteriaValue()
												.equalsIgnoreCase(
														custRef
																.getCustomRefValue())) {
									addRow = false;
									break;
								}
							}
							if (addRow) {
								RoutingRuleCriteria criteria = new RoutingRuleCriteria();
								criteria.setCriteriaName(custRef
										.getCustomRefName());
								criteria.setCriteriaValue(custRef
										.getCustomRefValue());
								criteria
										.setRuleTypeId(new LookupRoutingRuleType(
												RoutingRuleType.CUSTOM_REF
														.getId()));
								criteria.setModifiedBy(ruleHdr.getModifiedBy());
								criteria.setRoutingRuleHdr(ruleHdr);
								ruleHdr.getRoutingRuleCriteria().add(criteria);
								ruleHdr.setRuleUpdated(true);
								comment.append("+ " + custRef.getCustomRefName() + "=" + custRef.getCustomRefValue() + "\n");
							}
						}
					} else {
						custRefError = true;
					}
				}
			}

			boolean jobCodeError = false;
			if (uploadRule.getJobPrice() != null
					&& !uploadRule.getJobPrice().isEmpty()) {

				HashMap<String, List<JobPriceVO>> validatedJobCodes = validationService
						.validateJobCodes(uploadRule.getJobPrice(), buyerId);
				List<JobPriceVO> validJobCodes = validatedJobCodes
						.get(VALID_JOBCODES);
				List<JobPriceVO> invalidJobCodePrices = validatedJobCodes
						.get(INVALID_JOBCODE_PRICE);
				List<JobPriceVO> invalidJobCodes = validatedJobCodes
						.get(INVALID_JOBCODES);
				List<JobPriceVO> missingJobCodes = validatedJobCodes
					.get(MISSING_JOBCODES);
				//R12_2 : SL-20643
				List<JobPriceVO> permitJobCodes = validatedJobCodes
					.get(PERMIT_JOBCODES);
				if ((invalidJobCodes != null && !invalidJobCodes.isEmpty())
						|| (invalidJobCodePrices != null && !invalidJobCodePrices
								.isEmpty()) || !missingJobCodes.isEmpty() ||
								(null != permitJobCodes && !permitJobCodes.isEmpty())) {
					if (invalidJobCodes != null && !invalidJobCodes.isEmpty()) {
						StringBuilder error = new StringBuilder("");
						Iterator<JobPriceVO> iterator = invalidJobCodes
								.listIterator();
						while (iterator.hasNext()) {
							error.append(iterator.next().getJobCode());
							error.append(",");
						}
						error.deleteCharAt(error.length() - 1);
						addFileError(routingRuleFileHdr, INVALID_JOBCODE, error
								.toString(), action);
						
					}
					if (invalidJobCodePrices != null
							&& !invalidJobCodePrices.isEmpty()) {
						StringBuilder priceError = new StringBuilder("");
						Iterator<JobPriceVO> iterator1 = invalidJobCodePrices
								.listIterator();
						while (iterator1.hasNext()) {
							priceError.append(iterator1.next().getJobCode());
							priceError.append(",");
						}
						priceError.deleteCharAt(priceError.length() - 1);
						addFileError(routingRuleFileHdr, INVALID_PRICE,
								priceError.toString(), action);
					}
					if ( !missingJobCodes.isEmpty()) {
						StringBuilder error = new StringBuilder("");
						Iterator<JobPriceVO> iterator1 = missingJobCodes
								.listIterator();
						while (iterator1.hasNext()) {
							error.append(iterator1.next().getPrice());
							error.append(",");
						}
						error.deleteCharAt(error.length() - 1);
						addFileError(routingRuleFileHdr, JOB_CODE_MISSING,
								error.toString(), action);
					}
					//R12_2 : SL-20643
					if (null != permitJobCodes && !permitJobCodes.isEmpty()) {
						addFileError(routingRuleFileHdr, INVALID_INFO,
								PERMIT_ERROR, action);
					}
					errorSubStatus = true;
				} else {
					if (validJobCodes != null && !validJobCodes.isEmpty()) {
						DecimalFormat decimalFormat = new DecimalFormat("#.##");
						List<RoutingRulePrice> rulePriceList = new ArrayList<RoutingRulePrice>();
						boolean addRow = true;
						for (JobPriceVO jobCode : validJobCodes) {
							addRow = true;
							for (RoutingRulePrice jobPrice : ruleHdr
									.getRoutingRulePrice()) {
								if (jobPrice.getJobcode().equals(
										jobCode.getJobCode())) {
									if (new BigDecimal(jobCode.getPrice()).compareTo(jobPrice.getPrice()) != 0) {
										comment	.append("/SKU "
														+ jobCode.getJobCode()
														+ ": "
														+ (jobPrice.getPrice() == null ? "NULL": 
															decimalFormat.format(jobPrice.getPrice().doubleValue()))
														+ " -> $"
														+ (jobCode.getPrice() == null ? "NULL": 
															decimalFormat.format(new Double(jobCode.getPrice()).doubleValue()))
														+ "\n");
										jobPrice.setPrice(BigDecimal
												.valueOf(new Double(jobCode
														.getPrice())));
										ruleHdr.setRuleUpdated(true);
									}
									addRow = false;
									break;
								}
							}
							if (addRow) {
								RoutingRulePrice rulePrice = new RoutingRulePrice();
								rulePrice.setJobcode(jobCode.getJobCode());
								rulePrice
										.setPrice(BigDecimal
												.valueOf(new Double(jobCode
														.getPrice())));
								rulePrice
										.setModifiedBy(ruleHdr.getModifiedBy());
								rulePrice.setRoutingRuleHdr(ruleHdr);
								rulePriceList.add(rulePrice);
								comment.append("+ SKU " + jobCode.getJobCode() + " $"
										+ (jobCode.getPrice() == null ? "NULL" : 
										decimalFormat.format(new Double(jobCode.getPrice()).doubleValue())) + "\n");
							}
						}
						if(!rulePriceList.isEmpty()){
							ruleHdr.getRoutingRulePrice().addAll(rulePriceList);
							ruleHdr.setRuleUpdated(true);
						}
					} else {
						jobCodeError = true;
					}
				}
			}
			if (uploadRule.getAction().equalsIgnoreCase(UPLOAD_ACTION_NEW)
					&& jobCodeError && zipError && custRefError) {
				addFileError(routingRuleFileHdr, MISSING_MANDATORY,
						"Rule Preferences", action);
				errorSubStatus = true;
			}
		}
		ruleHdr.setRuleComment(comment.toString());
		return errorSubStatus;
	}

	/**
	 * Returns the list of zip codes in already present in the rule
	 * 
	 * @param ruleCriterias
	 * @return
	 */
	private List<String> getExistingZipCodes(
			List<RoutingRuleCriteria> ruleCriterias) {
		List<String> zipList = new ArrayList<String>();
		if (ruleCriterias != null && !ruleCriterias.isEmpty()) {
			for (RoutingRuleCriteria ruleCriteria : ruleCriterias) {
				if (ruleCriteria.getCriteriaName().equals(CRITERIA_NAME_ZIP)) {
					zipList.add(ruleCriteria.getCriteriaValue());
				}
			}
		}
		return zipList;
	}

	/**
	 * Returns the list of vendors already present in the rule
	 * 
	 * @param ruleVendors
	 * @return
	 */
	private List<String> getExistingVendors(List<RoutingRuleVendor> ruleVendors) {
		List<String> vendorList = new ArrayList<String>();
		if (ruleVendors != null && !ruleVendors.isEmpty()) {
			for (RoutingRuleVendor ruleVendor : ruleVendors) {
				vendorList.add(ruleVendor.getVendor().getId().toString());
			}
		}
		return vendorList;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	private void updateRuleHistory(RoutingRuleHdr ruleHdr, String fileName,
			String action, BuyerResource buyer) {
		RoutingRuleHdrHist ruleHist = new RoutingRuleHdrHist();
		ruleHist.setRoutingRuleHdr(ruleHdr);
		ruleHist.setRoutingRuleBuyerAssoc(ruleHdr.getRoutingRuleBuyerAssoc());
		ruleHist.setRuleName(ruleHdr.getRuleName());
		ruleHist.setRuleStatus(ruleHdr.getRuleStatus());
		ruleHist.setFileName(fileName);
		String histAction;
		if (action.equalsIgnoreCase(UPLOAD_ACTION_NEW)) {
			histAction = HISTORY_CREATED;
		} else if (action.equalsIgnoreCase(UPLOAD_ACTION_ADD)
				|| action.equalsIgnoreCase(UPLOAD_ACTION_DELETE)) {
			histAction = HISTORY_UPDATED;
		} else if (action.equalsIgnoreCase(UPLOAD_ACTION_ACTIVE)) {
			histAction = HISTORY_ACTIVATED;
		} else if (action.equalsIgnoreCase(UPLOAD_ACTION_INACTIVE)) {
			histAction = HISTORY_DEACTIVATED;
		} else {
			histAction = HISTORY_ARCHIVED;
		}
		ruleHist.setModifiedDate(new Date());
		ruleHist.setRuleComment(ruleHdr.getRuleComment());
		ruleHist.setRuleAction(histAction);
		ruleHist.setModifiedBy(buyer);
		ruleHdr.getRoutingRuleHdrHist().add(ruleHist);

	}

	/**
	 * @param rule
	 * @param autoAcceptStatus
	 * @param newVendorId
	 * @param modifiedBy
	 * @param buyer
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateAutoAcceptHistory(RoutingRuleHdr rule,String autoAcceptStatus,Integer newVendorId,RoutingRuleFileHdr routingRuleFileHdr,Integer roleId,String action)
	{
		AutoAcceptHistory autoAcceptHistory=new AutoAcceptHistory();
		autoAcceptHistory.setRoutingRuleHdr(rule);
		autoAcceptHistory.setAutoAcceptStatus(autoAcceptStatus);
		autoAcceptHistory.setVendorId(newVendorId);
		autoAcceptHistory.setAction(action);
		autoAcceptHistory.setModifiedBy(rule.getModifiedBy());
		autoAcceptHistory.setRoledId(roleId);
		if(null!=rule.getModifiedBy() && rule.getModifiedBy().equalsIgnoreCase(routingRuleFileHdr.getModifiedBy()))
		{
			autoAcceptHistory.setAdoptedBy(null);
		}
		else
		{
			autoAcceptHistory.setAdoptedBy(routingRuleFileHdr.getModifiedBy());
		}
		if(rule.getAutoAcceptHistory()==null)
		{
			 List<AutoAcceptHistory> autoAccpetHistoryList=new ArrayList<AutoAcceptHistory>();
			 autoAccpetHistoryList.add(autoAcceptHistory);
			 rule.setAutoAcceptHistory(autoAccpetHistoryList);
		}
		else
		{
			rule.getAutoAcceptHistory().add(autoAcceptHistory);
		}
		
	}
	public boolean validateComment(String comments, RoutingRuleFileHdr fileHdr, String action) {
		boolean error = false;
		if (comments != null && !comments.equals("")) {
			if (comments.length() > ROUTING_RULE_HDR_COMMENT_LEN) {
				addFileError(fileHdr, INVALID_INFO, "Rule Comment", action);
				error = true;
			}
		} else {
			addFileError(fileHdr, MISSING_MANDATORY, "Rule Comment", action);
			error = true;
		}
		return error;
	}

	private BuyerResource cloneBuyerRes(BuyerResource buyerRes) {
		BuyerResource resource = new BuyerResource();
		resource.setBuyer(buyerRes.getBuyer());
		resource.setUser(buyerRes.getUser());
		resource.setResourceId(buyerRes.getResourceId());
		resource.setContact(buyerRes.getContact());
		resource.setCompanyRoleId(buyerRes.getCompanyRoleId());
		return resource;
	}

	// return the template input value for alert_task
	public String createMailMergeValueStringFromMap(
			Map<String, String> templateMergeValueMap) {
		StringBuilder stringBuilder = new StringBuilder("");
		boolean isFirstKey = true;
		if (templateMergeValueMap != null) {
			Set<String> keySet = templateMergeValueMap.keySet();
			for (String key : keySet) {
				if (isFirstKey) {
					isFirstKey = !isFirstKey;
				} else {
					stringBuilder.append("|");
				}

				stringBuilder.append(key).append("=").append(
						templateMergeValueMap.get(key));
			}
		}
		return stringBuilder.toString();
	}
	
	public RoutingRuleFileHdrDao getRoutingRuleFileHdrDao() {
		return routingRuleFileHdrDao;
	}

	public void setRoutingRuleFileHdrDao(
			RoutingRuleFileHdrDao routingRuleFileHdrDao) {
		this.routingRuleFileHdrDao = routingRuleFileHdrDao;
	}

	public RoutingRuleFileParser getRoutingRuleFileParser() {
		return routingRuleFileParser;
	}

	public void setRoutingRuleFileParser(
			RoutingRuleFileParser routingRuleFileParser) {
		this.routingRuleFileParser = routingRuleFileParser;
	}

	public void setRoutingRuleHdrDao(RoutingRuleHdrDao routingRuleHdrDao) {
		this.routingRuleHdrDao = routingRuleHdrDao;
	}

	public void setRoutingRuleBuyerAssocDao(
			RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao) {
		this.routingRuleBuyerAssocDao = routingRuleBuyerAssocDao;
	}

	public void setRoutingRuleContactDao(ContactDao routingRuleContactDao) {
		this.routingRuleContactDao = routingRuleContactDao;
	}

	public void setProviderFirmDao(ProviderFirmDao providerFirmDao) {
		this.providerFirmDao = providerFirmDao;
	}

	public void setRoutingRuleErrorDao(RoutingRuleErrorDao routingRuleErrorDao) {
		this.routingRuleErrorDao = routingRuleErrorDao;
	}

	public void setRoutingRulesConflictFinderService(
			RoutingRulesConflictFinderService routingRulesConflictFinderService) {
		this.routingRulesConflictFinderService = routingRulesConflictFinderService;
	}

	public void setRoutingRuleUploadRuleDao(
			RoutingRuleUploadRuleDao routingRuleUploadRuleDao) {
		this.routingRuleUploadRuleDao = routingRuleUploadRuleDao;
	}

	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}

}
