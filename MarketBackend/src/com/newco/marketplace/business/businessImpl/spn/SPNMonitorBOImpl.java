package com.newco.marketplace.business.businessImpl.spn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO;
import com.newco.marketplace.dto.vo.spn.ApprovalModel;
import com.newco.marketplace.dto.vo.spn.BackgroundCheckHistoryVO;
import com.newco.marketplace.dto.vo.spn.BackgroundInfoProviderVO;
import com.newco.marketplace.dto.vo.spn.ComplianceCriteriaVO;
import com.newco.marketplace.dto.vo.spn.ProviderMatchApprovalCriteriaVO;
import com.newco.marketplace.dto.vo.spn.ProviderMatchingCountsVO;
import com.newco.marketplace.dto.vo.spn.SPNApprovalCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNCompanyProviderRequirementsVO;
import com.newco.marketplace.dto.vo.spn.SPNComplianceVO;
import com.newco.marketplace.dto.vo.spn.SPNExclusionsVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNProvUploadedDocsVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderRequirementsVO;
import com.newco.marketplace.dto.vo.spn.SPNSkillsAndServicesVO;
import com.newco.marketplace.dto.vo.spn.SearchBackgroundInfoProviderVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.persistence.iDao.provider.IDocumentDAO;
import com.newco.marketplace.persistence.iDao.spn.SPNDao;
import com.newco.marketplace.util.spn.ApprovalCriteriaHelper;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.provider.DocumentVO;

public class SPNMonitorBOImpl implements ISPNMonitorBO, SPNConstants {
	
	private static final Logger logger = Logger.getLogger(SelectProviderNetworkBOImpl.class);
	
	private SPNDao spnDAO;
	private IDocumentDAO documentDAO;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO#getSPNMonitorList(java.lang.Integer)
	 */
	public List<SPNMonitorVO> getSPNMonitorList(Integer vendorId) throws BusinessServiceException{
		List<SPNMonitorVO> spnList = new ArrayList <SPNMonitorVO>();
		try {
			spnList = spnDAO.getSPNMonitorList(vendorId);
		} catch (DataServiceException dse) {
			logger.error("Error returned trying to retrieve spn list", dse);
			throw new BusinessServiceException("Error returned trying to retrieve spn list", dse);
		}
		
		return spnList;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO#isVendorSPNApplicant(java.lang.Integer)
	 */
	public Integer isVendorSPNApplicant(Integer vendorId) throws BusinessServiceException{
		Integer result = 0;
		Integer spnCount = 0;
		Integer spnListcount = 0;
		try {
			spnCount = spnDAO.isVendorSPNApplicant(vendorId);
			// spnListcount = spnDAO.isProviderSPNApplicant(vendorId);
			return spnCount;
		} catch (DataServiceException dse) {
			logger.error("Error returned trying to retrieve the count", dse);
			throw new BusinessServiceException("Error returned trying to retrieve the count", dse);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO#getSPMMainMonitorList(java.lang.Integer)
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList(Integer vendorId) throws BusinessServiceException{
		List<SPNMainMonitorVO> spnMainMonitorList = new ArrayList<SPNMainMonitorVO>();
		try {
			spnMainMonitorList = spnDAO.getSPMMainMonitorList(vendorId);
		} catch(DataServiceException dse) {
			logger.error("Error returned trying to retrieve spn main monitor list", dse);
			throw new BusinessServiceException("Error returned trying to retrieve spn main monitorlist", dse);
		}
		return spnMainMonitorList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO#getSPMMainMonitorList(java.lang.Integer, java.lang.Integer)
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList(Integer vendorId,
			Integer spnId) throws BusinessServiceException {
		List<SPNMainMonitorVO> spnMainMonitorList = new ArrayList<SPNMainMonitorVO>();
		try {
			spnMainMonitorList = spnDAO.getSPMMainMonitorList(vendorId,spnId);
		} catch(DataServiceException dse) {
			logger.error("Error returned trying to retrieve spn main monitor list", dse);
			throw new BusinessServiceException("Error returned trying to retrieve spn main monitorlist", dse);
		}
		return spnMainMonitorList;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO#acceptInvite(java.lang.Integer, java.lang.Integer)
	 */
	public void acceptInvite(Integer spnId,Integer vendorId,String modifiedBy) throws BusinessServiceException{
		try {
			spnDAO.acceptInvite(spnId,vendorId,modifiedBy);
		} catch(DataServiceException dse) {
			logger.error("Error returned trying to change the state of the provider", dse);
			throw new BusinessServiceException("Error returned trying to change the state of the provider", dse);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO#uploadDocument(com.newco.marketplace.dto.vo.spn.SPNProvUploadedDocsVO, java.lang.String)
	 */
	public int uploadDocument(SPNProvUploadedDocsVO spnProvUploadedDocsVO, String buttonType)throws BusinessServiceException{
		int id = 0;
		DocumentVO docVO = new DocumentVO();
		int oldProviderUpldDocId = 0;
		try {
			if (null != spnProvUploadedDocsVO ) {
				if (spnProvUploadedDocsVO.getProvFirmUplDocId()>0) {
					oldProviderUpldDocId = spnProvUploadedDocsVO.getProvFirmUplDocId();
				}
				docVO.setBlobBytes(spnProvUploadedDocsVO.getDocBytes());
				docVO.setDocCategoryId(SPN_DOCUMENT_CATEGORY_ID);
				docVO.setFileName(spnProvUploadedDocsVO.getDocFileName());
				docVO.setFormat(spnProvUploadedDocsVO.getDocFormat());
				docVO.setTitle(spnProvUploadedDocsVO.getDocTitle());
				docVO.setVendorId(spnProvUploadedDocsVO.getVendorId());
				docVO.setDescription(spnProvUploadedDocsVO.getDocDescription());
				docVO.setSource(spnProvUploadedDocsVO.getDocBytes().toString());
				docVO.setDocFileSize(spnProvUploadedDocsVO.getDocFileSize());
				docVO = documentDAO.insert(docVO);
				
				spnProvUploadedDocsVO.setDocStateDesc(SPN_DOCUMENT_PENDING_APPROVAL_DESC);
				spnProvUploadedDocsVO.setDocStateId(SPN_DOCUMENT_PENDING_APPROVAL_ID);
				if (SPN_BUTTON_TYPE_SELECT.equals(buttonType)) {
					spnProvUploadedDocsVO.setProvFirmUplDocId(docVO.getDocumentId());
					spnProvUploadedDocsVO.setDeletedInd(0);
					id = spnDAO.saveProviderUploadedDocument(spnProvUploadedDocsVO);
					spnProvUploadedDocsVO.setActiveInd(SPN_DOCUMENT_ACTIVE_IND);
					spnDAO.saveUploadedDocumentState(spnProvUploadedDocsVO);
				} else if(SPN_BUTTON_TYPE_UPDATE.equals(buttonType)) {
					//setting the new doc id to ProvFirmUplDocId and inserting 
					//to spnet_provider_firm_document
					spnProvUploadedDocsVO.setProvFirmUplDocId(docVO.getDocumentId());
					spnProvUploadedDocsVO.setDeletedInd(0);
					id = spnDAO.saveProviderUploadedDocument(spnProvUploadedDocsVO);
					
					//setting the new doc id to ProvFirmUplDocId and updating 
					//the ProvFirmUplDocId in spnet_uploaded_document_state table
					spnProvUploadedDocsVO.setProvFirmUpldOldDocId(oldProviderUpldDocId);
					spnProvUploadedDocsVO.setProvFirmUplDocId(docVO.getDocumentId());
					spnDAO.uploadDocumentUploadedIdAndStatus(spnProvUploadedDocsVO);
					
					//setting the old doc id to ProvFirmUplDocId and updating 
					//the deleted ind as 1 in spnet_provider_firm_document 
					spnProvUploadedDocsVO.setProvFirmUplDocId(oldProviderUpldDocId);
					spnProvUploadedDocsVO.setDeletedInd(1);
					spnDAO.updateProviderDocumetStatus(spnProvUploadedDocsVO);
					
					// Returning the VO with latest uploaded doc_id
					spnProvUploadedDocsVO.setProvFirmUplDocId(docVO.getDocumentId());
				} else {
					logger.error("UNEXPECTED BUTTON TYPE; Valid Values are:  Select, Update");
				}
				// this is commented out as the spnBuyerDocId doesnt change
				//spnProvUploadedDocsVO.setSpnBuyerDocId(docVO.getDocumentId());				
			}
		} catch (DataServiceException dse) {
			logger.error("Error returned trying to upload document", dse);
			throw new BusinessServiceException("Error returned trying to upload document", dse);
		} catch (DBException dbe) {
			logger.error("Error returned trying to upload document", dbe);
			throw new BusinessServiceException("Error returned trying to upload document", dbe);
		}		
		return id;
	}
	
	/**
	 * Returns the count of qualified providers for the given SPN
	 * @param spnId
	 * @return int qualifiedProviderCount
	 * @throws BusinessServiceException
	 */
	public int getProviderCountsForSPN(Integer spnId,Integer vendorId) throws BusinessServiceException {
		int qualifiedProsCount = 0;
		try {
			List<SPNApprovalCriteriaVO> approvalCriteria = spnDAO.getApprovalCriteriaList(spnId); 
			ApprovalModel approvalModel = ApprovalCriteriaHelper.getApprovalModelFromCriteria(approvalCriteria);
			ProviderMatchApprovalCriteriaVO providerMatchApprovalCriteriaVO = ApprovalCriteriaHelper.getCriteriaVOFromModel(approvalModel);
			providerMatchApprovalCriteriaVO.setVendorId(vendorId); 
			if(providerMatchApprovalCriteriaVO != null ){
				List<ProviderMatchingCountsVO> matchingProvidersList =  spnDAO.getMatchingProviderstList(providerMatchApprovalCriteriaVO);
				if (matchingProvidersList != null && !matchingProvidersList.isEmpty()) {
					qualifiedProsCount = (int)matchingProvidersList.get(0).getProviderCounts().longValue();
				}
			}
		} catch(DataServiceException dse) {
			logger.error("Error returned while fetching qualified providers count fo the SPN", dse);
			throw new BusinessServiceException("Error returned get Approval criteria for the given SPN", dse);
		}
		return qualifiedProsCount;   
	}
	 
	/**
	 * Returns the count of  providers for the given firm
	 * @param vendorId
	 * @return int totalProviderCount
	 * @throws BusinessServiceException
	 */
	public int getTotalProvidersForVendor(Integer vendorId) throws BusinessServiceException{
		int totalProviderCount = 0;
		try {
			totalProviderCount = spnDAO.getTotalProviderCount(vendorId);
		} catch(DataServiceException dse) {
			logger.error("Error returned while fetching total providers for vendor", dse);
			throw new BusinessServiceException("Error returned get Approval criteria for the given SPN", dse);
		}
		return totalProviderCount;
	}
	
	/**
	 * Returns the list of provider requirements
	 * @param SPNMainMonitorVO spnMainMonitorVO
	 * @return SPNCompanyProviderRequirementsVO
	 * @throws BusinessServiceException
	 */
	@SuppressWarnings("unchecked")
	public SPNCompanyProviderRequirementsVO getProviderRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws BusinessServiceException{
		List<SPNProviderRequirementsVO> matchingProviderRequirementsList = null;
		List<SPNProviderRequirementsVO> spnProviderRequirementsList = null;
		SPNCompanyProviderRequirementsVO spnCompanyProviderRequirementsVO = new SPNCompanyProviderRequirementsVO();
		Integer totalResourceCount =0;
		try {
			int spnId = spnMainMonitorVO.getSpnId();
			spnMainMonitorVO.setSpnId(spnId);
			matchingProviderRequirementsList = spnDAO.getMatchingProviderRequirementsList(spnMainMonitorVO);
			spnProviderRequirementsList = spnDAO.getSPNProviderRequirementsList(spnId);		
		//	exceptionsList = spnDAO.getResourceExceptionsApplied(spnMainMonitorVO.getSpnId(),spnMainMonitorVO.getVendorId());
			totalResourceCount = getTotalProvidersForVendor(spnMainMonitorVO.getVendorId());
			/*if(exceptionsList!=null && exceptionsList.size()>0){
				mapCredentialExceptions(exceptionsList);
			}*/
			for(SPNProviderRequirementsVO providerRequirementsVO: matchingProviderRequirementsList){
					int compliantCount = providerRequirementsVO.getMatchedProvidersCount();
					int overridedCount = providerRequirementsVO.getOverridedProvidersCount();
					int outOfComplianceCount = totalResourceCount - (compliantCount+overridedCount);
					providerRequirementsVO.setOutOfCompliantProvidersCount(outOfComplianceCount);
					//String criteriaDesc = providerRequirementsVO.getCriteriaDesc();
					/*if(providerRequirementsVO.getOverridedProvidersCount() > 0){
						if(providerRequirementsVO.getParentNode() != null && SPNConstants.CRITERIA_SP_CRED_CATEGORY.equals(criteriaDesc)  ) {
							if(exceptionsList!=null && exceptionsList.size()>0){
								for(SPNExclusionsVO exclusionsVO : exceptionsList){
								if(exclusionsVO!=null){
									String credentialName = exclusionsVO.getCredentialType() + " " + exclusionsVO.getCredentialCategory();
									if(credentialName.contains(providerRequirementsVO.getGroupValue())){
										exclusionsVOs.add(exclusionsVO);
									}
								}
								}
							}
						}
						else if(SPNConstants.CRITERIA_SP_CRED.equals(criteriaDesc) ){
							if(exceptionsList!=null && exceptionsList.size()>0){
								for(SPNExclusionsVO exclusionsVO : exceptionsList){
								if(exclusionsVO!=null){
									String credentialName = exclusionsVO.getCredentialType() + " " + exclusionsVO.getCredentialCategory();
									if(credentialName.equals(providerRequirementsVO.getParentNode())){
										exclusionsVOs.add(exclusionsVO);
									}
								}
								}
							}
						}
						providerRequirementsVO.setExclusionsVO(exclusionsVOs);
					}*/
				}
		} catch(DataServiceException dse) {
			logger.error("Error returned while fetching the provider requirements list", dse);
			throw new BusinessServiceException("Error returned while getting the provider requirements list", dse);
		}
		spnCompanyProviderRequirementsVO = checkProviderRequirementsStatus(matchingProviderRequirementsList,spnProviderRequirementsList,spnCompanyProviderRequirementsVO);		
		spnCompanyProviderRequirementsVO.setTotalResourceCount(totalResourceCount);
		return spnCompanyProviderRequirementsVO;
	}
	
	/**
	 * Returns the list of company requirements
	 * @param SPNMainMonitorVO spnMainMonitorVO
	 * @return SPNCompanyProviderRequirementsVO
	 * @throws BusinessServiceException
	 */
	public SPNCompanyProviderRequirementsVO getComapnyRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws BusinessServiceException{
		List<SPNProviderRequirementsVO> matchingCompanyRequirementsList = null;
		List<SPNProviderRequirementsVO> spnCompanyRequirementsList = null;
		List<SPNExclusionsVO> exceptionsList = null;
		SPNCompanyProviderRequirementsVO spnCompanyProviderRequirementsVO = new SPNCompanyProviderRequirementsVO();
		try {
			int spnId = spnMainMonitorVO.getSpnId();
			spnMainMonitorVO.setSpnId(spnId);
			matchingCompanyRequirementsList = spnDAO.getMatchingCompanyRequirementsList(spnMainMonitorVO);
			spnCompanyRequirementsList = spnDAO.getSPNCompanyRequirementsList(spnId); 
			exceptionsList = spnDAO.getCompanyExceptionsApplied(spnMainMonitorVO.getSpnId(),spnMainMonitorVO.getVendorId());
			if(exceptionsList!=null && exceptionsList.size()>0){
				mapCredentialExceptions(exceptionsList);
				for(SPNProviderRequirementsVO providerRequirementsVO: matchingCompanyRequirementsList){
					List<SPNExclusionsVO> exclusionsVOs = new ArrayList<SPNExclusionsVO>();
					if((SPNConstants.CRITERIA_COMPANY_CRED_OVERRIDED).equals(providerRequirementsVO.getCriteriaDesc())&& exceptionsList!=null && exceptionsList.size()>0){
						for(SPNExclusionsVO exclusionsVO : exceptionsList){
							if(exclusionsVO!=null){
								String credentialName = "";
								if(exclusionsVO.getCredentialType().contains(">")){
									credentialName = exclusionsVO.getCredentialType().replace(">", "");
								}
								else{
									credentialName = exclusionsVO.getCredentialType();
								}
								String exceptionTypeIds = providerRequirementsVO.getExceptionTypeId();
								String[] exceptionTypeIDs = exceptionTypeIds.split(",");
								if(credentialName.equals(providerRequirementsVO.getValue())&& exceptionTypeIds.contains(exclusionsVO.getExceptionTypeId().toString())){
									if(exclusionsVO.getExceptionTypeId().intValue() == 2){
										String state = providerRequirementsVO.getState();
										String exceptionValue = exclusionsVO.getExceptionValue();
										String[] selectedStates = exceptionValue.split(",");
										exclusionsVO.setExcepmtedStates(selectedStates);
										//exceptionValue.replace(state, "<strong>"+state+"</strong>");
										exclusionsVO.setExceptionValue(exceptionValue);
									}
									exclusionsVOs.add(exclusionsVO);
								}
							}
						}
					}
					providerRequirementsVO.setExclusionsVO(exclusionsVOs);
				}
			}
		} catch(DataServiceException dse) {
			logger.error("Error returned while fetching the company requirements list", dse);
			throw new BusinessServiceException("Error returned while getting the company requirements list", dse);
		}
		spnCompanyProviderRequirementsVO = checkCompanyRequirementsStatus(matchingCompanyRequirementsList,spnCompanyRequirementsList,spnCompanyProviderRequirementsVO,exceptionsList);		
		return spnCompanyProviderRequirementsVO;
	}
	
	
	/**
	 * @param exceptionsList
	 * 
	 * method to map exceptions
	 * 
	 */
	private void mapCredentialExceptions(List<SPNExclusionsVO> exceptionsList) {
		for(SPNExclusionsVO exclusionsVO : exceptionsList){
			if(exclusionsVO!=null){
				
				if(null != exclusionsVO.getCredentialCategoryId()){
					exclusionsVO.setCredentialType(exclusionsVO.getCredentialType()+" > "+ exclusionsVO.getCredentialCategory());
					if(exclusionsVO.getExceptionTypeId().intValue() == 1){
						
							exclusionsVO.setExceptionValue("Allowed Grace Period : "+exclusionsVO.getExceptionValue()+" days");
						}
					else if(exclusionsVO.getExceptionTypeId().intValue() == 2){
						String exceptionValue = exclusionsVO.getExceptionValue();
						String[] selectedStates = exceptionValue.split(",");
						exclusionsVO.setExcepmtedStates(selectedStates);
						exclusionsVO.setExceptionValue("State(s) Exempted: "+exclusionsVO.getExceptionValue());
					}
				}
				else{
					exclusionsVO.setCredentialType(exclusionsVO.getCredentialType());
					if(null == exclusionsVO.getCredentialCategoryId() && null != exclusionsVO.getCredentialTypeId()){
						if(exclusionsVO.getExceptionTypeId().intValue() == 1){
							exclusionsVO.setExceptionValue("Allowed Grace Period : "+exclusionsVO.getExceptionValue()+" days");
						}
						else if(exclusionsVO.getExceptionTypeId().intValue() == 2){
							String exceptionValue = exclusionsVO.getExceptionValue();
							String[] selectedStates = exceptionValue.split(",");
							exclusionsVO.setExcepmtedStates(selectedStates);
							exclusionsVO.setExceptionValue("State(s) Exempted: "+exclusionsVO.getExceptionValue());
						}
					}
				}
			
			}
		}
	}

	/**
	 * Check the status of company requirements
	 * @param matchingCompanyRequirementsList
	 * @param spnCompanyRequirementsList
	 * @param spnCompanyProviderRequirementsVO
	 * @param exceptionsList 
	 * @return SPNCompanyProviderRequirementsVO
	 */
	private SPNCompanyProviderRequirementsVO checkCompanyRequirementsStatus(List<SPNProviderRequirementsVO> matchingCompanyRequirementsList,List<SPNProviderRequirementsVO> spnCompanyRequirementsList,SPNCompanyProviderRequirementsVO spnCompanyProviderRequirementsVO, List<SPNExclusionsVO> exceptionsList){
		if(null!=spnCompanyRequirementsList && !spnCompanyRequirementsList.isEmpty()){
			String groupName = "";
			String matchedGroupValue ="";
			Map<String, SPNProviderRequirementsVO> credentialMap = new HashMap<String, SPNProviderRequirementsVO>();
			Map<String, String> insuranceMap = new HashMap<String, String>();
			for(SPNProviderRequirementsVO spnCompanyRequirementsVO : spnCompanyRequirementsList){
				groupName = spnCompanyRequirementsVO.getGroupName();
				String groupValue = spnCompanyRequirementsVO.getGroupValue();
				String value = spnCompanyRequirementsVO.getValue();
				if(null != groupName){
					if(groupName.equals(SPNConstants.CRITERIA_NAME_CREDENTIALS )){	
						SPNProviderRequirementsVO spnProviderRequirementsVO = new SPNProviderRequirementsVO();
						spnProviderRequirementsVO.setMatchCriteria( SPNConstants.NOT_MATCH_CRITERIA);
						credentialMap.put(groupValue,spnProviderRequirementsVO);
						String criteriaDesc = spnCompanyRequirementsVO.getCriteriaDesc();
						credentialMap = this.getCompanyCredentialMatchMap(credentialMap,groupValue,groupValue,value,criteriaDesc, spnCompanyRequirementsVO.getParentNode(),matchingCompanyRequirementsList);				
					}else if(groupName.equals(SPNConstants.CRITERIA_NAME_INSURANCE)){
						String criteriaDesc = spnCompanyRequirementsVO.getCriteriaDesc();
						if(criteriaDesc.equals(SPNConstants.CRITERIA_AUTO_LIABILITY_AMT)){
							String autoLiabilityAmount="";
							 if(StringUtils.isNotBlank(spnCompanyRequirementsVO.getGroupValue()))
								 {
								//Fixing SL-19872: converting amount to comma separated value.
								 Double autoLiabilityAmt=Double.parseDouble(spnCompanyRequirementsVO.getGroupValue());
								  autoLiabilityAmount=MoneyUtil.convertDoubleToCurrency(autoLiabilityAmt, Locale.US);
								 }
							 matchedGroupValue = SPNConstants.AUTO_LIABILITY+autoLiabilityAmount;
							 insuranceMap.put(matchedGroupValue, SPNConstants.NOT_MATCH_CRITERIA);
							// insuranceMap = this.getCompanyCriteriaMatchMap(insuranceMap, matchedGroupValue,groupValue, matchingCompanyRequirementsList, criteriaDesc);
						}else if(criteriaDesc.equals(SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_AMT)){
							String commercialLiabilityAmount="";
							 if(StringUtils.isNotBlank(spnCompanyRequirementsVO.getGroupValue()))
								 {
								//Fixing SL-19872: converting amount to comma separated value.
								 Double commercialLiabilityAmt=Double.parseDouble(spnCompanyRequirementsVO.getGroupValue());
								 commercialLiabilityAmount=MoneyUtil.convertDoubleToCurrency(commercialLiabilityAmt, Locale.US);
								 }
							matchedGroupValue = SPNConstants.COMMERCIAL_LIABILITY+commercialLiabilityAmount;
							insuranceMap.put(matchedGroupValue, SPNConstants.NOT_MATCH_CRITERIA);
							//insuranceMap = this.getCompanyCriteriaMatchMap(insuranceMap, matchedGroupValue,groupValue, matchingCompanyRequirementsList, criteriaDesc);
						}else if(criteriaDesc.equals(SPNConstants.CRITERIA_WC_LIABILITY_VERIFIED) || SPNConstants.CRITERIA_WC_LIABILITY_SELECTED.equals(criteriaDesc)){
							matchedGroupValue = SPNConstants.WORKMAN_COMPENSATION;
							insuranceMap.put(matchedGroupValue, SPNConstants.NOT_MATCH_CRITERIA);
						}						
						insuranceMap = this.getCompanyMatchMap(insuranceMap,matchedGroupValue,groupValue,value,criteriaDesc, spnCompanyRequirementsVO.getParentNode(),matchingCompanyRequirementsList);				
					}
				}
			}
			spnCompanyProviderRequirementsVO.setCredentialMap(credentialMap);
			spnCompanyProviderRequirementsVO.setInsuranceMap(insuranceMap);
			
		}		
		return spnCompanyProviderRequirementsVO;
	}
	
	/**
	 * @param credentialMap
	 * @param groupValue
	 * @param groupValue2
	 * @param value
	 * @param criteriaDesc
	 * @param parentNode
	 * @param matchingCompanyRequirementsList
	 * @return
	 */
	private Map<String, SPNProviderRequirementsVO> getCompanyCredentialMatchMap(
			Map<String, SPNProviderRequirementsVO> credentialMap, String matchedGroupValue, String groupValue,String value, String criteriaDesc, String parentNodeId, List<SPNProviderRequirementsVO> matchingCompanyRequirementsList) {
		String key = "";
		//String criteriaDesc = spnProviderRequirementVO.getCriteriaDesc();
		//String parentNodeId = spnProviderRequirementVO.getValue();			
		if(null != criteriaDesc){
			if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_VERIFIED)||criteriaDesc.equals(SPNConstants.CRITERIA_AUTO_LIABILITY_VERIFIED)||criteriaDesc.equals(SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED)){	
				if(null != parentNodeId){
					if(parentNodeId.equals(value)){
						if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_VERIFIED)){
							SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(groupValue);
							String status = providerRequirementsVO.getMatchCriteria();
							credentialMap.remove(groupValue);
							key = groupValue+SPNConstants.VERIFIED_BY_SERVICELIVE;								
							credentialMap.put(key,providerRequirementsVO);
						}else{
							SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(groupValue);
							String status = providerRequirementsVO.getMatchCriteria();
							credentialMap.remove(matchedGroupValue);
							key = matchedGroupValue+SPNConstants.VERIFIED_BY_SERVICELIVE;
							credentialMap.put(key,providerRequirementsVO);
						}							
						matchedGroupValue = key;							
					}
				}				
			} 				
			credentialMap = this.getCompanyCredentialCriteriaMatchMap(credentialMap,matchedGroupValue,groupValue,matchingCompanyRequirementsList,criteriaDesc);
		}	
	return credentialMap;
	}

	private Map<String, SPNProviderRequirementsVO> getCompanyCredentialCriteriaMatchMap(Map<String, SPNProviderRequirementsVO> credentialMap,String matchedGroupValue,String mainGroupValue,List<SPNProviderRequirementsVO> matchingCompanyRequirementsList,String criteriaDesc) {
		for(SPNProviderRequirementsVO matchProviderRequirementsVO : matchingCompanyRequirementsList){
			String matchGroupName = matchProviderRequirementsVO.getGroupName();
			String matchCriteriaDesc = matchProviderRequirementsVO.getCriteriaDesc();
			String groupValue = matchProviderRequirementsVO.getGroupValue();
			String value = matchProviderRequirementsVO.getValue();
			if(null != matchGroupName && null != matchCriteriaDesc){				
				if(matchCriteriaDesc.equals(criteriaDesc)){
					if(null != groupValue){
							if(groupValue.equals(mainGroupValue)&&(groupValue.equals(matchedGroupValue))){
								if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_VERIFIED)){									
									SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(matchedGroupValue);
									String status = providerRequirementsVO.getMatchCriteria();
									credentialMap.remove(matchedGroupValue);
									String key = groupValue+SPNConstants.VERIFIED_BY_SERVICELIVE;
									credentialMap.put(key,providerRequirementsVO);
									break;
								}
								else if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_OVERRIDED)){
									SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(matchedGroupValue);
									providerRequirementsVO.setMatchCriteria(SPNConstants.OVERRIDED_CRITERIA);
									credentialMap.remove(matchedGroupValue);
									credentialMap.put(matchedGroupValue,providerRequirementsVO);
									break;
								}
								else{
									SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(matchedGroupValue);
									providerRequirementsVO.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
									credentialMap.remove(matchedGroupValue);
									credentialMap.put(matchedGroupValue,providerRequirementsVO);
									break;
								}
							}
							else if(null!=value && value.equals(mainGroupValue)&&(value.equals(matchedGroupValue))){
								SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(matchedGroupValue);
								providerRequirementsVO.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
								credentialMap.remove(matchedGroupValue);
								credentialMap.put(matchedGroupValue,providerRequirementsVO);
								break;
							}
					}
				}
				else if(matchCriteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_OVERRIDED)){
					if(null != value){
						if(value.equals(mainGroupValue)&&(value.equals(matchedGroupValue))){
							SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(matchedGroupValue);
							providerRequirementsVO.setMatchCriteria(SPNConstants.OVERRIDED_CRITERIA);
							if(matchProviderRequirementsVO.getExclusionsVO()!=null){
								providerRequirementsVO.setExclusionsVO(matchProviderRequirementsVO.getExclusionsVO());
								providerRequirementsVO.setExpirationDate(matchProviderRequirementsVO.getExpirationDate());
								providerRequirementsVO.setState(matchProviderRequirementsVO.getState());
							}
							credentialMap.remove(matchedGroupValue);
							credentialMap.put(matchedGroupValue,providerRequirementsVO);
							break;
						}
					}
				}
			}	
		}
		return credentialMap; 
	}

	/**
	 * @param credentialMap
	 * @param matchedGroupValue
	 * @param groupValue
	 * @param value
	 * @param spnCompanyRequirementsList
	 * @param matchingCompanyRequirementsList
	 * @return
	 */
	private Map<String, String> getCompanyMatchMap(Map<String, String> credentialMap,String matchedGroupValue, String groupValue,String value, String criteriaDesc, String parentNodeId, List<SPNProviderRequirementsVO> matchingCompanyRequirementsList){
		String key = "";
			//String criteriaDesc = spnProviderRequirementVO.getCriteriaDesc();
			//String parentNodeId = spnProviderRequirementVO.getValue();			
			if(null != criteriaDesc){
				if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_VERIFIED)||criteriaDesc.equals(SPNConstants.CRITERIA_AUTO_LIABILITY_VERIFIED)||criteriaDesc.equals(SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED)){	
					if(null != parentNodeId){
						if(parentNodeId.equals(value)){
							if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_VERIFIED)){
								String status = credentialMap.get(groupValue);
								credentialMap.remove(groupValue);
								key = groupValue+SPNConstants.VERIFIED_BY_SERVICELIVE;								
								credentialMap.put(key,status);
							}else{
								String status = credentialMap.get(matchedGroupValue);
								credentialMap.remove(matchedGroupValue);
								key = matchedGroupValue+SPNConstants.VERIFIED_BY_SERVICELIVE;
								credentialMap.put(key,status);
							}							
							matchedGroupValue = key;							
						}
					}				
				} 				
				credentialMap = this.getCompanyCriteriaMatchMap(credentialMap,matchedGroupValue,groupValue,matchingCompanyRequirementsList,criteriaDesc);
			}	
		return credentialMap;
	}

	/**
	 * @param credentialMap
	 * @param matchedGroupValue
	 * @param mainGroupValue
	 * @param matchingCompanyRequirementsList
	 * @param criteriaDesc
	 * @return
	 */
	private Map<String, String> getCompanyCriteriaMatchMap(Map<String, String> credentialMap,String matchedGroupValue,String mainGroupValue,List<SPNProviderRequirementsVO> matchingCompanyRequirementsList,String criteriaDesc){
		for(SPNProviderRequirementsVO matchProviderRequirementsVO : matchingCompanyRequirementsList){
			String matchGroupName = matchProviderRequirementsVO.getGroupName();
			String matchCriteriaDesc = matchProviderRequirementsVO.getCriteriaDesc();
			String groupValue = matchProviderRequirementsVO.getGroupValue();
			String value = matchProviderRequirementsVO.getValue();
			if(null != matchGroupName && null != matchCriteriaDesc){				
				if((matchCriteriaDesc.equals(criteriaDesc))||((SPNConstants.CRITERIA_WC_LIABILITY_SELECTED.equals(criteriaDesc)) && (SPNConstants.CRITERIA_WC_LIABILITY_VERIFIED.equals(matchCriteriaDesc)))){
					if(null != groupValue){
						if(SPNConstants.CRITERIA_AUTO_LIABILITY_VERIFIED.equals(criteriaDesc)||SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED.equals(criteriaDesc)){
							if(groupValue.equals(mainGroupValue)){
								updateCredentialStatus(credentialMap,
										matchedGroupValue);
								break;
							}
							else {
								credentialMap.remove(matchedGroupValue);
								credentialMap.put(matchedGroupValue,SPNConstants.NOT_MATCH_CRITERIA);
								break;
							}
						}
						if (SPNConstants.CRITERIA_AUTO_LIABILITY_VERIFIED.equals(criteriaDesc)||SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED.equals(criteriaDesc)){
							BigDecimal spnAmount = new BigDecimal(mainGroupValue);
							BigDecimal provAmount = new BigDecimal(value);
							if(provAmount.compareTo(spnAmount) >= 0){
								updateCredentialStatus(credentialMap,
										matchedGroupValue);
								break;
							}
							else {
								credentialMap.remove(matchedGroupValue);
								credentialMap.put(matchedGroupValue,SPNConstants.NOT_MATCH_CRITERIA);
								break;
							}
					    }
						else if (SPNConstants.CRITERIA_AUTO_LIABILITY_AMT.equals(criteriaDesc)||SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_AMT.equals(criteriaDesc)){
							BigDecimal spnAmount = new BigDecimal(mainGroupValue);
							BigDecimal provAmount = new BigDecimal(value);
							if(provAmount.compareTo(spnAmount) >= 0){
								updateCredentialStatus(credentialMap,
										matchedGroupValue);
								break;
							}
							else {
								credentialMap.remove(matchedGroupValue);
								credentialMap.put(matchedGroupValue,SPNConstants.NOT_MATCH_CRITERIA);
								break;
							}
					    }
						else if(SPNConstants.CRITERIA_WC_LIABILITY_VERIFIED.equals(criteriaDesc) || SPNConstants.CRITERIA_WC_LIABILITY_SELECTED.equals(criteriaDesc)){
							if(groupValue.equals(mainGroupValue)){
								String status = credentialMap.get(matchedGroupValue);
								credentialMap.remove(matchedGroupValue);
								credentialMap.put(matchedGroupValue,SPNConstants.MATCH_CRITERIA);
								break;
							}
						}else{
							if(groupValue.equals(mainGroupValue)&&(groupValue.equals(matchedGroupValue))){
								if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_VERIFIED)){									
									String status = credentialMap.get(matchedGroupValue);
									credentialMap.remove(matchedGroupValue);
									String key = groupValue+SPNConstants.VERIFIED_BY_SERVICELIVE;
									credentialMap.put(key,status);
									break;
								}
								else if(criteriaDesc.equals(SPNConstants.CRITERIA_COMPANY_CRED_OVERRIDED)){
									credentialMap.remove(matchedGroupValue);
									credentialMap.put(matchedGroupValue,SPNConstants.OVERRIDED_CRITERIA);
									break;
								}
								else{
									credentialMap.remove(matchedGroupValue);
									credentialMap.put(matchedGroupValue,SPNConstants.MATCH_CRITERIA);
									break;
								}
							}
						}						
					}
				}
			}	
		}
		return credentialMap; 
	}

	/**
	 * @param credentialMap
	 * @param matchedGroupValue
	 */
	private void updateCredentialStatus(Map<String, String> credentialMap,
			String matchedGroupValue) {
		String status = credentialMap.get(matchedGroupValue);
		//FIXME This is patch ..
		if( null != status && SPNConstants.NOT_MATCH_CRITERIA.equalsIgnoreCase(status))
		{
			credentialMap.remove(matchedGroupValue);
			credentialMap.put(matchedGroupValue,SPNConstants.MATCH_CRITERIA);
		}
		//in case of null add match
		else if (null == status){
			credentialMap.put(matchedGroupValue,SPNConstants.MATCH_CRITERIA);
		}
	}
	
	/**
	 * Check if providers who match the approval criteria are present
	 * @param matchingProviderRequirementsList
	 * @param spnProviderRequirementsList
	 * @param spnCompanyProviderRequirementsVO
	 * @return
	 */
	private SPNCompanyProviderRequirementsVO checkProviderRequirementsStatus(List<SPNProviderRequirementsVO> matchingProviderRequirementsList,List<SPNProviderRequirementsVO> spnProviderRequirementsList,SPNCompanyProviderRequirementsVO spnCompanyProviderRequirementsVO){
		if(null!=spnProviderRequirementsList && !spnProviderRequirementsList.isEmpty()){
			String groupName = "";
			String key = "";
			boolean isParentNode = false;
			Map<String, SPNProviderRequirementsVO> credentialMap = new HashMap<String, SPNProviderRequirementsVO>();
			Map<String,SPNProviderRequirementsVO> spnRequirementCredentialMap = new HashMap<String,SPNProviderRequirementsVO>();
			Map<String, SPNProviderRequirementsVO> languageMap = new HashMap<String, SPNProviderRequirementsVO>();
			Map<String, SPNProviderRequirementsVO> locationMap = new HashMap<String, SPNProviderRequirementsVO>();
			Map<String, SPNProviderRequirementsVO> marketMap = new HashMap<String, SPNProviderRequirementsVO>();
			Map<String, SPNProviderRequirementsVO> ratingMap = new HashMap<String, SPNProviderRequirementsVO>();
			Map<String, SPNProviderRequirementsVO> completedSOMap = new HashMap<String, SPNProviderRequirementsVO>();				
			List<SPNSkillsAndServicesVO> servicesList = new ArrayList<SPNSkillsAndServicesVO>();
			//R11.0 SL-19387
			Map<String, SPNProviderRequirementsVO> backgroundCheckMap = new HashMap<String, SPNProviderRequirementsVO>();
			for(SPNProviderRequirementsVO spnProviderRequirementsVO : spnProviderRequirementsList){
				groupName = spnProviderRequirementsVO.getGroupName();
				String groupValue = spnProviderRequirementsVO.getGroupValue();
				String nodeStatus = spnProviderRequirementsVO.getParentNode();	
				String criteriaDescription = spnProviderRequirementsVO.getCriteriaDesc();
				String value = spnProviderRequirementsVO.getValue();
				isParentNode = checkNodeStatus(isParentNode,nodeStatus);
				SPNProviderRequirementsVO requirementsVO = new SPNProviderRequirementsVO();
				if(null != groupName){
					if(groupName.equals(SPNConstants.CRITERIA_NAME_CREDENTIALS )){							
						if(criteriaDescription.equals(SPNConstants.CRITERIA_SP_CRED)){						
							SPNProviderRequirementsVO providerRequirementsVO = credentialMap.get(groupValue);
							if(null == providerRequirementsVO ){
								credentialMap.put(groupValue, null);
							}
							spnRequirementCredentialMap.put(groupValue, spnProviderRequirementsVO);
						}
						else if (criteriaDescription.equals(SPNConstants.CRITERIA_SP_CRED_CATEGORY)) {
							credentialMap.put(nodeStatus, null);
							spnRequirementCredentialMap.put(nodeStatus, spnProviderRequirementsVO);
						}
						//credentialMap = this.getCriteriaMatchMap(credentialMap,groupName,groupValue,matchingProviderRequirementsList,criteriaDescription,nodeStatus);				
					}
					//R11.0 SL-19387
					else if(groupName.equals("Background check - 2 year recertification")){
						spnProviderRequirementsVO.setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
						backgroundCheckMap.put(groupValue,spnProviderRequirementsVO);
					}else if(groupName.equals(SPNConstants.CRITERIA_NAME_LANGUAGE)){
						spnProviderRequirementsVO .setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
						languageMap.put(groupValue,spnProviderRequirementsVO);
					//	languageMap = this.getCriteriaMatchMap(languageMap,groupName,groupValue,matchingProviderRequirementsList,criteriaDescription,nodeStatus);				
					}else if(groupName.equals(SPNConstants.CRITERIA_NAME_STATE)){
						spnProviderRequirementsVO .setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
						locationMap.put(groupValue, spnProviderRequirementsVO);
						locationMap = this.getCriteriaMatchMap(locationMap,groupName,groupValue,matchingProviderRequirementsList,criteriaDescription,nodeStatus);				
					}else if(groupName.equals(SPNConstants.CRITERIA_NAME_MARKET)){
						spnProviderRequirementsVO .setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
						marketMap.put(groupValue,spnProviderRequirementsVO);
						marketMap = this.getCriteriaMatchMap(marketMap,groupName,groupValue,matchingProviderRequirementsList,criteriaDescription,nodeStatus);				
					}else if(groupName.equals(SPNConstants.CRITERIA_NAME_RATING)){
						spnProviderRequirementsVO .setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
						ratingMap.put(groupValue,spnProviderRequirementsVO);
						ratingMap = this.getCriteriaMatchMap(ratingMap,groupName,groupValue,matchingProviderRequirementsList,criteriaDescription,nodeStatus);				
					}else if(groupName.equals(SPNConstants.CRITERIA_NAME_COMPLETED_SO)){
						spnProviderRequirementsVO .setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
						completedSOMap.put(groupValue,spnProviderRequirementsVO);
						completedSOMap = this.getCriteriaMatchMap(completedSOMap,groupName,groupValue,matchingProviderRequirementsList,criteriaDescription,nodeStatus);				
					}else if(groupName.equals(SPNConstants.CRITERIA_NAME_SERVICES)&& isParentNode){	
						if(criteriaDescription.equals(SPNConstants.CRITERIA_MAIN_SERVICES)){
							Map<String, SPNProviderRequirementsVO> servicesMap = (Map<String, SPNProviderRequirementsVO>) new HashMap<String,SPNProviderRequirementsVO>();
							SPNSkillsAndServicesVO spnSkillsAndServicesVO = new SPNSkillsAndServicesVO();
							spnSkillsAndServicesVO = this.getServicesMatchMap(servicesMap,spnSkillsAndServicesVO,spnProviderRequirementsList,spnProviderRequirementsVO,matchingProviderRequirementsList); 
							servicesList.add(spnSkillsAndServicesVO);
						}
					}
				}
			}
			credentialMap = this. updateCriteriaMatchMap ( spnRequirementCredentialMap , matchingProviderRequirementsList ) ;
			languageMap = matchlanguageMap(languageMap, matchingProviderRequirementsList,SPNConstants.CRITERIA_NAME_LANGUAGE);
			//R11.0 SL-19387
			backgroundCheckMap = matchBackgroundCheckMap(backgroundCheckMap, matchingProviderRequirementsList,"Background check - 2 year recertification");
			spnCompanyProviderRequirementsVO.setBackgroundCheckMap(backgroundCheckMap);
			
			spnCompanyProviderRequirementsVO.setCredentialMap(credentialMap);
			spnCompanyProviderRequirementsVO.setLanguageMap(languageMap);
			spnCompanyProviderRequirementsVO.setLocationMap(locationMap);
			spnCompanyProviderRequirementsVO.setCompletedSOMap(completedSOMap);
			spnCompanyProviderRequirementsVO.setRatingMap(ratingMap);
			spnCompanyProviderRequirementsVO.setServicesList(servicesList);	
			
		}	
		return spnCompanyProviderRequirementsVO;
	}
	//R11.0 SL-19387
	private Map<String, SPNProviderRequirementsVO> matchBackgroundCheckMap(Map<String, SPNProviderRequirementsVO> backgroundCheckMap,
			List<SPNProviderRequirementsVO> matchingProviderRequirementsList, String groupName) {
		if (null != matchingProviderRequirementsList
				&& !matchingProviderRequirementsList.isEmpty()) {
			for (SPNProviderRequirementsVO matchProviderRequirementsVO : matchingProviderRequirementsList) {
				String matchGroupName = matchProviderRequirementsVO.getGroupName();
				String valueDesc = matchProviderRequirementsVO.getCriteriaDesc();
				if (matchGroupName.equals(groupName) && 
						matchProviderRequirementsVO.getMatchedProvidersCount() > 0){
					backgroundCheckMap.remove(valueDesc);
					matchProviderRequirementsVO.setMatchCriteria( SPNConstants.MATCH_CRITERIA);
					backgroundCheckMap.put(valueDesc,matchProviderRequirementsVO);
				}
				
			}
		}	
		return backgroundCheckMap;
	}

	private Map<String, SPNProviderRequirementsVO> matchlanguageMap(Map<String, SPNProviderRequirementsVO> languageMap,
			List<SPNProviderRequirementsVO> matchingProviderRequirementsList, String groupName) {
		if (null != matchingProviderRequirementsList
				&& !matchingProviderRequirementsList.isEmpty()) {
			for (SPNProviderRequirementsVO matchProviderRequirementsVO : matchingProviderRequirementsList) {
				String matchGroupName = matchProviderRequirementsVO.getGroupName();
				String valueDesc = matchProviderRequirementsVO.getCriteriaDesc();
				if (matchGroupName.equals(groupName) && 
						matchProviderRequirementsVO.getMatchedProvidersCount() > 0){
					languageMap.remove(valueDesc);
					matchProviderRequirementsVO.setMatchCriteria( SPNConstants.MATCH_CRITERIA);
					languageMap.put(valueDesc,matchProviderRequirementsVO);
				}
				
			}
		}	
		return languageMap;
	}

	private  Map<String, SPNProviderRequirementsVO>  updateCriteriaMatchMap (Map<String,SPNProviderRequirementsVO> spnRequirementCredentialMap , List<SPNProviderRequirementsVO> matchingProviderRequirementsList ) {
		Map<String, SPNProviderRequirementsVO> credentialMap = new HashMap<String, SPNProviderRequirementsVO>();
		 Map<String,SPNProviderRequirementsVO > providerMatchMap = buildMapOfproviderMatched(matchingProviderRequirementsList);
		for(String credentialName : spnRequirementCredentialMap.keySet() ){
			
			SPNProviderRequirementsVO requirementVO = spnRequirementCredentialMap.get(credentialName);
				if(requirementVO != null ){
					requirementVO.setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
				credentialMap.put(credentialName,requirementVO);
				String groupName = requirementVO.getGroupName();
				if(SPNConstants.CRITERIA_NAME_CREDENTIALS.equals(groupName)) {
					if(  null != requirementVO.getParentNode()  ){
						//Find if matching found for this category 
						if(providerMatchMap.containsKey(requirementVO.getParentNode())){
							SPNProviderRequirementsVO  category = providerMatchMap.get(requirementVO.getParentNode());
							if(category != null) {
								if(requirementVO.getParentNode().equals(category.getParentNode())) {
										category.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
										credentialMap.put(credentialName,category);
								}
							}
							
						}
					}
					else {
						
						if(providerMatchMap.containsKey(credentialName)){
							SPNProviderRequirementsVO  credtype = providerMatchMap.get(credentialName);
							credtype.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
							credentialMap.put(credentialName, credtype);
						}
						
					}
				}
			}
		}
		return credentialMap;
	}
	
	private Map<String,SPNProviderRequirementsVO > buildMapOfproviderMatched(List<SPNProviderRequirementsVO> matchingProviderRequirementsList){
		 Map<String,SPNProviderRequirementsVO > providerMatchMap = new HashMap<String,SPNProviderRequirementsVO >();
		for (SPNProviderRequirementsVO matchProviderRequirementsVO : matchingProviderRequirementsList) {
			String criteriaDesc = matchProviderRequirementsVO.getCriteriaDesc();
			if(matchProviderRequirementsVO.getParentNode() != null && SPNConstants.CRITERIA_SP_CRED_CATEGORY.equals(criteriaDesc)  ) {
					if(providerMatchMap.containsKey((matchProviderRequirementsVO.getParentNode()))) providerMatchMap.remove(matchProviderRequirementsVO.getParentNode())  ;
					providerMatchMap.put(matchProviderRequirementsVO.getParentNode(), matchProviderRequirementsVO);
			}
			else if(SPNConstants.CRITERIA_SP_CRED.equals(criteriaDesc) ){
					if(!providerMatchMap.containsKey((matchProviderRequirementsVO.getGroupValue()))) {
						providerMatchMap.put(matchProviderRequirementsVO.getGroupValue(), matchProviderRequirementsVO);
					}
			}
		}
		return providerMatchMap;
	}
	
	/**
	 * * Check if providers who match the approval criteria are present
	 * @param criteriaMap
	 * @param groupName
	 * @param groupValue
	 * @param matchingProviderRequirementsList
	 * @return
	 */
	private Map<String, SPNProviderRequirementsVO> getCriteriaMatchMap(Map<String, SPNProviderRequirementsVO> criteriaMap ,String groupName,String groupValue,List<SPNProviderRequirementsVO> matchingProviderRequirementsList,String criteriaDescription,String parentNodeValue)
	{
		if (null != matchingProviderRequirementsList
				&& !matchingProviderRequirementsList.isEmpty()) {
			for (SPNProviderRequirementsVO matchProviderRequirementsVO : matchingProviderRequirementsList) {
				String matchGroupName = matchProviderRequirementsVO
						.getGroupName();
				String criteriaDesc = matchProviderRequirementsVO
						.getCriteriaDesc();

				// Group name does not match
				if (null != matchGroupName && matchGroupName.equals(groupName)) {

					if (matchGroupName
							.equals(SPNConstants.CRITERIA_NAME_RATING)
							|| matchGroupName
									.equals(SPNConstants.CRITERIA_NAME_COMPLETED_SO) ) {
						
							criteriaMap.remove(groupValue);
							matchProviderRequirementsVO.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
							criteriaMap.put(groupValue,
									matchProviderRequirementsVO);
							break;
					} else if (matchGroupName
							.equals(SPNConstants.CRITERIA_NAME_CREDENTIALS)) {
						if (criteriaDescription
								.equals(SPNConstants.CRITERIA_SP_CRED_CATEGORY)) {
							if (parentNodeValue != null) {
								if (parentNodeValue
										.equals(matchProviderRequirementsVO
												.getParentNode())) {
									criteriaMap.remove(parentNodeValue);
									matchProviderRequirementsVO.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
									criteriaMap.put(parentNodeValue,
											matchProviderRequirementsVO);
									break;
								} else {
									criteriaMap.remove(parentNodeValue);
									matchProviderRequirementsVO.setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
									criteriaMap.put(parentNodeValue,
											matchProviderRequirementsVO);
									break;
								}

							}

						} else if (criteriaDescription
								.equals(SPNConstants.CRITERIA_SP_CRED)) {
							String matchGroupValue = matchProviderRequirementsVO
									.getGroupValue();
							if (null != matchGroupValue) {
								if (matchGroupValue.equals(groupValue)) {
									
										String status = null;
										if(criteriaMap
												.get(groupValue)!=null){
											status= criteriaMap
													.get(groupValue).getMatchCriteria();
										}
										if (status == null) {
											criteriaMap.remove(groupValue);
											matchProviderRequirementsVO.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
											criteriaMap
													.put(
															groupValue,
															matchProviderRequirementsVO);
										}
										break;
								}
							}
						}

					}

				}

			}
		}
		return criteriaMap;
	}
	
	/**
	 * Maps Skills & Services
	 * @param servicesMap
	 * @param spnProviderRequirementsList
	 * @param spnProviderRequirementsVO
	 * @param matchingProviderRequirementsList
	 * @return
	 */
	private SPNSkillsAndServicesVO getServicesMatchMap(
			Map<String, SPNProviderRequirementsVO> servicesMap,
			SPNSkillsAndServicesVO spnSkillsAndServicesVO,
			List<SPNProviderRequirementsVO> spnProviderRequirementsList,
			SPNProviderRequirementsVO spnProviderRequirementsVO,
			List<SPNProviderRequirementsVO> matchingProviderRequirementsList) {
		Map<String, String> tempMap = new HashMap<String, String>();
		String serviceName = spnProviderRequirementsVO.getGroupValue();
		spnSkillsAndServicesVO.setMainService(serviceName);
		String serviceId = spnProviderRequirementsVO.getValue();
		for (SPNProviderRequirementsVO spnProviderRequirementVO : spnProviderRequirementsList) {
			String criteriaDesc = spnProviderRequirementVO.getCriteriaDesc();
			String parentNodeId = spnProviderRequirementVO.getParentNode();
			if (null != criteriaDesc) {
				if (criteriaDesc.equals(SPNConstants.CRITERIA_CATEGORY)
						|| criteriaDesc
								.equals(SPNConstants.CRITERIA_SUB_CATEGORY)
						|| criteriaDesc.equals(SPNConstants.CRITERIA_SKILLS)) {
					String matchedGroupValue = spnProviderRequirementVO
							.getGroupValue();
					if (parentNodeId.equals(serviceId)) {
						spnProviderRequirementVO.setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
						servicesMap.put(matchedGroupValue,
								spnProviderRequirementVO);
						servicesMap = getProviderCriteriaMatchMap(servicesMap,
								matchedGroupValue,
								matchingProviderRequirementsList, serviceId,
								criteriaDesc,tempMap);
						if (criteriaDesc.equals(SPNConstants.CRITERIA_CATEGORY)) {
							tempMap.put(spnProviderRequirementVO.getChildNode(), matchedGroupValue);
						}
					}
					if (criteriaDesc.equals(SPNConstants.CRITERIA_SUB_CATEGORY)) {
						if(tempMap.containsKey(parentNodeId)){
							matchedGroupValue = tempMap.get(parentNodeId)+"|"+spnProviderRequirementsVO.getGroupValue();
							spnProviderRequirementVO.setMatchCriteria(SPNConstants.NOT_MATCH_CRITERIA);
							servicesMap.put(matchedGroupValue,
									spnProviderRequirementVO);
							servicesMap = getProviderCriteriaMatchMap(servicesMap,
									matchedGroupValue,
									matchingProviderRequirementsList, serviceId,
									criteriaDesc,tempMap);
						}
					}
				}
			}
		}
		spnSkillsAndServicesVO.setSkills(servicesMap);
		return spnSkillsAndServicesVO;
	}
	
	/**
	 * Returns provider matching count for Skills & Services
	 * @param servicesMap
	 * @param matchedGroupValue
	 * @param matchingProviderRequirementsList
	 * @param serviceId
	 * @return
	 */
	private Map<String, SPNProviderRequirementsVO> getProviderCriteriaMatchMap(Map<String, SPNProviderRequirementsVO> servicesMap,String matchedGroupValue,List<SPNProviderRequirementsVO> matchingProviderRequirementsList,String serviceId,String criteriaDesc,Map tempMap){
		for(SPNProviderRequirementsVO matchProviderRequirementsVO : matchingProviderRequirementsList){
			String matchGroupName = matchProviderRequirementsVO.getGroupName();
			String matchCriteriaDesc = matchProviderRequirementsVO.getCriteriaDesc();
			String groupValue = matchProviderRequirementsVO.getGroupValue();
			String parentNode = matchProviderRequirementsVO.getParentNode();
			if(null != matchGroupName && null != matchCriteriaDesc){
				if(matchCriteriaDesc.equals(criteriaDesc)){
					if(null != parentNode && null != groupValue){						
						if(criteriaDesc.equals(SPNConstants.CRITERIA_SUB_CATEGORY)){ 
							if(tempMap.containsKey(parentNode)){
								String tempGroupValue = matchedGroupValue.substring(matchedGroupValue.indexOf("|"));
								if(groupValue.equals(tempGroupValue)){
									servicesMap.remove(matchedGroupValue);
									matchProviderRequirementsVO.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
									servicesMap.put(matchedGroupValue,matchProviderRequirementsVO);
									break;
								}
							}
						}
						if(matchGroupName.equalsIgnoreCase(SPNConstants.SERVICES_SKILLS) && matchCriteriaDesc.equalsIgnoreCase(SPNConstants.CATEGORY)){
							groupValue = groupValue.substring(groupValue.indexOf('>')+1);
							groupValue = groupValue.trim();
						}
						if(parentNode.equals(serviceId) && groupValue.equals(matchedGroupValue)){
							servicesMap.remove(matchedGroupValue);
							matchProviderRequirementsVO.setMatchCriteria(SPNConstants.MATCH_CRITERIA);
							servicesMap.put(matchedGroupValue,matchProviderRequirementsVO);
							break;
						}
					}
				}	
			}
		}
		return servicesMap;
	}
	
	
	
	
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */

	public List<SPNComplianceVO> getFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
	try
	{
		List<SPNComplianceVO> SPNComplianceList=spnDAO.getFirmCompliance(complianceCriteriaVO);
		return SPNComplianceList;
	}
	catch(DataServiceException e)
	{
		return null;//throw new BusinessServiceException(""+e);
	}
	}
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	
	public List<SPNComplianceVO> getProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
		try
		{
			List<SPNComplianceVO> SPNComplianceList=spnDAO.getProviderCompliance(complianceCriteriaVO);
			return SPNComplianceList;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}
	
	public Integer getProviderComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
		try
		{
			Integer count=spnDAO.getProviderComplianceCount(complianceCriteriaVO);
			return count;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}
	
	public Integer getFirmComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
		try
		{
			Integer count=spnDAO.getFirmComplianceCount(complianceCriteriaVO);
			return count;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}
	
	public List<SPNComplianceVO> getRequirementsforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException{ 
		try
		{
			List<SPNComplianceVO> requirementList=spnDAO.getRequirementsforFirmCompliance(complianceCriteriaVO);
			return requirementList;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}
	
	
	public List<String> getBuyersforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
		try
		{
			List<String> buyerList=spnDAO.getBuyersforFirmCompliance(complianceCriteriaVO);
			return buyerList;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}
	
	public List<String> getSPNforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
	try
	{
		List<String> SPNList=spnDAO.getSPNforFirmCompliance(complianceCriteriaVO);
		return SPNList;
	}
	catch(DataServiceException e)
	{
		return null;//throw new BusinessServiceException(""+e);
	}
	}
	
	public List<SPNComplianceVO> getRequirementsforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException{
		try
		{
			List<SPNComplianceVO> requirementList=spnDAO.getRequirementsforProviderCompliance(complianceCriteriaVO);
			return requirementList;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}

	public List<String> getBuyersforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
		try
		{
			List<String> buyerList=spnDAO.getBuyersforProviderCompliance(complianceCriteriaVO);
			return buyerList;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}

	public List<String> getSPNforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException {
		try
		{
			List<String> SPNList=spnDAO.getSPNforProviderCompliance(complianceCriteriaVO);
			return SPNList;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}	
	}

	public List<SPNComplianceVO> getProviderNamesforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException{ 
		try
		{
			List<SPNComplianceVO> providerNameList=spnDAO.getProviderNamesforProviderCompliance(complianceCriteriaVO);
			return providerNameList;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}
	
	
	/**
	 * Checks whether the selected node is parent node or not
	 * @param isParentNode
	 * @param nodeStatus
	 * @return
	 */
	private boolean checkNodeStatus(boolean isParentNode,String nodeStatus){
		if(StringUtils.isBlank(nodeStatus)){
			isParentNode = true;
		}else{
			if(nodeStatus.equals(SPNConstants.NOT_MATCH_CRITERIA)){
				isParentNode = true;
			}
		}
		return isParentNode;
	}
	public SPNDao getSpnDAO() {
		return spnDAO;
	}
	
	public void setSpnDAO(SPNDao spnDAO) {
		this.spnDAO = spnDAO;
	}
	
	public IDocumentDAO getDocumentDAO() {
		return documentDAO;
	}
	
	public void setDocumentDAO(IDocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO#getSPMMainMonitorListWithFilters(Integer, Boolean, List, String)
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorListWithFilters(Integer vendorId,
			Boolean filterAppliedInd, List<String> selectedBuyerValues,
			List<String> selectedMemStatus)throws BusinessServiceException{
		List<SPNMainMonitorVO> spnMainMonitorList = new ArrayList<SPNMainMonitorVO>();
		try {
			spnMainMonitorList = spnDAO.getSPMMainMonitorListWithFilters(vendorId,filterAppliedInd,selectedBuyerValues,selectedMemStatus);
		} catch(DataServiceException dse) {
			logger.error("Error returned trying to retrieve spn main monitor list applying filters", dse);
			throw new BusinessServiceException("Error returned trying to retrieve spn main monitorlist applying filters", dse);
		}
		return spnMainMonitorList;
	}
	
	
	public Date getProviderComplianceDate() throws BusinessServiceException {
		try
		{
			Date  date=spnDAO.getProviderComplianceDate();
			return date;
		}
		catch(DataServiceException e)
		{
			return null;//throw new BusinessServiceException(""+e);
		}
	}
	public Date getFirmComplianceDate() throws BusinessServiceException {
		try
		{
			Date  date=spnDAO.getFirmComplianceDate();
			return date;
		}
		
		catch(DataServiceException e)
		{
			return null;
		}
	}
	
	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return Integer
	 * @throws BusinessServiceException
	 */
	//SL-19387
	//Fetching Background Check details count of resources from db
	public Integer getBackgroundInformationCount(SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO) throws BusinessServiceException {
		Integer count=0;
		try {
			count = spnDAO.getBackgroundInformationCount(searchBackgroundInfoProviderVO);
		} catch(DataServiceException dse) {
			logger.error("Error returned trying to retrieve Background Information Count", dse);
			throw new BusinessServiceException("Error returned trying to retrieve Background Information Count", dse);
		}

		return count;
		}

	
	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return List<BackgroundInfoProviderVO>
	 * @throws BusinessServiceException
	 */
	//SL-19387
	//Fetching Background Check details of resources from db
	public List<BackgroundInfoProviderVO> getBackgroundInformation(SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO) throws BusinessServiceException {
		List<BackgroundInfoProviderVO> backgroundInfoList =new ArrayList<BackgroundInfoProviderVO>();
		
		try {
			backgroundInfoList = spnDAO.getBackgroundInformation(searchBackgroundInfoProviderVO);
		} catch(DataServiceException dse) {
			logger.error("Error returned trying to retrieve Background Information", dse);
			throw new BusinessServiceException("Error returned trying to retrieve Background Information", dse);
		}

		return backgroundInfoList;
		}
	
	//SL-19387
	public List<SPNMonitorVO> getSPNProviderList(Integer vendorId) throws BusinessServiceException {
		List<SPNMonitorVO> spnProviderList = new ArrayList<SPNMonitorVO>();
		try {
			spnProviderList = spnDAO.getSPNProviderList(vendorId);
		} catch(DataServiceException dse) {
			logger.error("Error returned trying to retrieve getSPNProviderList", dse);
			throw new BusinessServiceException("Error returned trying to retrieve getSPNProviderList", dse);
		}
		return spnProviderList;
	}

	
	
		//R11.0
		//Exporting data in excel format
		public ByteArrayOutputStream getExportToExcel(ByteArrayOutputStream outFinal, List<BackgroundInfoProviderVO> bckgdInfoVO) throws IOException, WriteException {
			//Creating excel
			WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			sheet.setColumnView(0,40);
			sheet.setColumnView(1,30);
			sheet.setColumnView(2,30);
			sheet.setColumnView(3,30);
			sheet.setColumnView(4,30);
			sheet.setColumnView(5,85);
			

			// Create a cell format for Arial 10 point font 
			WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD); 
			WritableCellFormat arial10format = new WritableCellFormat (arial10font);	
			arial10format.setAlignment(Alignment.CENTRE);
			arial10format.setBackground(Colour.ICE_BLUE);
			arial10format.setBorder(Border.ALL,BorderLineStyle.THIN);
			WritableCellFormat cellformat = new WritableCellFormat ();
			cellformat.setAlignment(Alignment.LEFT);

			Label provider = new Label(0, 0, "Provider",arial10format);
			Label bcStatus = new Label(1, 0, "Background Check Status",arial10format);
			Label certDate = new Label(2, 0, "Last Certification Date",arial10format);
			Label recDueDate = new Label(3, 0, "Recertification Due Date",arial10format);
			Label recStatus = new Label(4, 0, "Recertification Status",arial10format);
			Label systemAction = new Label(5, 0, "System Action/Notice Sent On",arial10format);
			
			
			sheet.addCell(provider);
			sheet.addCell(bcStatus);
			sheet.addCell(certDate);
			sheet.addCell(recDueDate);
			sheet.addCell(recStatus);
			sheet.addCell(systemAction);

			for (int i=0; i < bckgdInfoVO.size(); i++){

				
				Label providerName = new Label(0, (1+i), bckgdInfoVO.get(i).getProviderFirstName()+" "+bckgdInfoVO.get(i).getProviderLastName()+"\n"+"("+"ID #"+bckgdInfoVO.get(i).getResourceId()+")",cellformat);
				Label backgroundStatus = new Label(1, (1+i), bckgdInfoVO.get(i).getBackgroundState(),cellformat);
				Label certificationDate = null;
				if(null != bckgdInfoVO.get(i).getVerificationDate())
				{
					certificationDate= new Label(2, (1+i), DateUtils.getFormatedDate(bckgdInfoVO.get(i).getVerificationDate(),"MM/dd/yyyy"),cellformat);
				}else
				{					
					certificationDate= new Label(2, (1+i), null, cellformat);
				}
				String rectDate = null;
				if (null != bckgdInfoVO.get(i).getReverificationDate() && null!=bckgdInfoVO.get(i).getCriteriaBg() && bckgdInfoVO.get(i).getCriteriaBg() > 0) {
					rectDate = ""
							+ DateUtils.getFormatedDate(bckgdInfoVO.get(i).getReverificationDate(),
									"MM/dd/yyyy");
				}
				
				Label recertificationDueDate = new Label(3, (1+i),rectDate,cellformat);
				String recertStat = null;
				if (null != bckgdInfoVO.get(i).getRecertificationStatus() && null!=bckgdInfoVO.get(i).getCriteriaBg() && bckgdInfoVO.get(i).getCriteriaBg() > 0) {
					if(bckgdInfoVO.get(i).getRecertificationStatus().equals("In Process")) {
						recertStat =bckgdInfoVO.get(i).getRecertificationStatus();
					} else if (Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) == 0) {
						recertStat = "Due Today";
					} else if (Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) < 0) {
						recertStat = "Past Due";
					} else if(Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) > 0 && Integer.parseInt(bckgdInfoVO.get(i).getRecertificationStatus()) <= 30) {
						recertStat = "Due in "+ bckgdInfoVO.get(i).getRecertificationStatus() + " days";
					}
				}
				
				Label recertificationStatus = new Label(4, (1+i),recertStat,cellformat);
				
				StringBuilder notType = new StringBuilder();
				if (null !=  bckgdInfoVO.get(i).getNotificationType()&& (null != bckgdInfoVO.get(i).getNotificationDateThirty() ||
						null != bckgdInfoVO.get(i).getNotificationDateSeven() || null != bckgdInfoVO.get(i).getNotificationDateZero())
						&& null!=bckgdInfoVO.get(i).getCriteriaBg() && bckgdInfoVO.get(i).getCriteriaBg() > 0 && null != bckgdInfoVO.get(i).getRecertificationStatus()) {
					
					
					if(null != bckgdInfoVO.get(i).getNotificationDateThirty())
					{
						notType.append("30 days notice sent on").append(" ")
							.append(DateUtils.getFormatedDate(bckgdInfoVO.get(i).getNotificationDateThirty(),
									"MM/dd/yyyy")).append(" ");
					}
					
					if(null != bckgdInfoVO.get(i)
							.getNotificationDateSeven())
					{
						notType.append("7 days notice sent on").append(" ")
							.append(DateUtils.getFormatedDate(bckgdInfoVO.get(i).getNotificationDateSeven(),
									"MM/dd/yyyy")).append(" ");
					}
					
					if(null != bckgdInfoVO.get(i).getNotificationDateZero())
					{
						notType.append("0 days notice sent on").append(" ")
							.append(DateUtils.getFormatedDate(bckgdInfoVO.get(i).getNotificationDateZero(),
									"MM/dd/yyyy"));
					}

				}
				Label sytemAction = new Label(5, (1+i),notType.toString(),cellformat);

				
				sheet.addCell(providerName);
				sheet.addCell(backgroundStatus);
				sheet.addCell(certificationDate);
				sheet.addCell(recertificationDueDate);
				sheet.addCell(recertificationStatus);
				sheet.addCell(sytemAction);
			}


			workbook.write();
			workbook.close();
			outFinal.close();

			return outFinal;
		}

		/**
		 * @param List<BackgroundInformationVO>
		 * @return StringBuffer
		 */
		//R11.0
		//Exporting data in CSV Comma format
		public StringBuffer getExportToCSVComma(List<BackgroundInfoProviderVO> bckgdInfoVO) {
			StringBuffer buffer = new StringBuffer();

			Iterator<BackgroundInfoProviderVO> bkgdIterator = bckgdInfoVO.iterator();
			List<String> headerList = new ArrayList<String>();
			headerList.addAll(convertCommaSepStrToList(SPNConstants.SPN_MONITOR_HDR));
			for(String header : headerList){
				buffer.append(header ==null?"": header.toString());
				buffer.append(',');
			}
			buffer.append('\n');
			while(bkgdIterator.hasNext()){
				BackgroundInfoProviderVO bkgVO =  bkgdIterator.next();
				if(StringUtils.isNotEmpty(bkgVO.getProviderFirstName()) && StringUtils.isNotEmpty(bkgVO.getProviderLastName()) && null!=bkgVO.getResourceId() ){
					formatAndAppend(bkgVO.getProviderFirstName()+" "+bkgVO.getProviderLastName()+" "+"("+"ID #"+bkgVO.getResourceId()+")",buffer);
				}
				else{
					formatAndAppend(null, buffer);
				}
				if(StringUtils.isNotEmpty(bkgVO.getBackgroundState())){
					formatAndAppend(bkgVO.getBackgroundState(),buffer);
				}
				else{
					formatAndAppend(null, buffer);
				}
				if (null != bkgVO.getVerificationDate())
				{
				   formatAndAppend(DateUtils.getFormatedDate(bkgVO.getVerificationDate(),"MM/dd/yyyy"),buffer);
				}else
				{
					formatAndAppend(null, buffer);
				}
				if (null != bkgVO.getReverificationDate() && null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0) {
					formatAndAppend(DateUtils.getFormatedDate(bkgVO.getReverificationDate(),"MM/dd/yyyy"),buffer);
				}
				else{
					formatAndAppend(null, buffer);
				}
				setRecertificationStatus(bkgVO);
				formatAndAppend(bkgVO.getRecertificationStatus(),buffer);
				setNotificationType(bkgVO);
				formatAndAppend(bkgVO.getNotificationType(),buffer);
				buffer.append("\n");
				
			}
			return buffer;
		}
		/**
		 * @param List<BackgroundInformationVO>
		 * @return StringBuffer
		*/
		//R11.0
		//Exporting data in CSV Pipe format
		public StringBuffer getExportToCSVPipe(List<BackgroundInfoProviderVO> bckgdInfoVO) {
			StringBuffer buffer = new StringBuffer();

			Iterator<BackgroundInfoProviderVO> bkgdIterator = bckgdInfoVO.iterator();
			List<String> headerList = new ArrayList<String>();
			headerList.addAll(convertCommaSepStrToList(SPNConstants.SPN_MONITOR_HDR));
			for(String header : headerList){
				buffer.append(header ==null?"": header.toString());
				buffer.append('|');
			}
			buffer.append('\n');
			
			while(bkgdIterator.hasNext()){
				BackgroundInfoProviderVO bkgVO =  bkgdIterator.next();
				if(StringUtils.isNotEmpty(bkgVO.getProviderFirstName()) && StringUtils.isNotEmpty(bkgVO.getProviderLastName()) && null!=bkgVO.getResourceId() ){
					formatAndAppendPipe(bkgVO.getProviderFirstName()+" "+bkgVO.getProviderLastName()+" "+"("+"ID #"+bkgVO.getResourceId()+")",buffer);
				}
				else{
					formatAndAppendPipe(null, buffer);
				}
				if(StringUtils.isNotEmpty(bkgVO.getBackgroundState())){
					formatAndAppendPipe(bkgVO.getBackgroundState(),buffer);
				}
				else{
					formatAndAppendPipe(null, buffer);
				}
				if (null != bkgVO.getVerificationDate())
				{
				    formatAndAppendPipe(DateUtils.getFormatedDate(bkgVO.getVerificationDate(),"MM/dd/yyyy"),buffer);
				}
				else
				{
					formatAndAppendPipe(null, buffer);
				}
				if (null != bkgVO.getReverificationDate() && null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0) {
					formatAndAppendPipe(DateUtils.getFormatedDate(bkgVO.getReverificationDate(),"MM/dd/yyyy"),buffer);
				}
				else{
					String reVerificationDate = null;
					formatAndAppendPipe(reVerificationDate,buffer);
				}
				setRecertificationStatus(bkgVO);
				formatAndAppendPipe(bkgVO.getRecertificationStatus(),buffer);
				setNotificationType(bkgVO);
				formatAndAppendPipe(bkgVO.getNotificationType(),buffer);
				buffer.append("\n");
			}
			return buffer;
		}
		/**
		 * @param BackgroundInformationVO
		 * To set the notification type based on the notification date
		*/
		private void setNotificationType(BackgroundInfoProviderVO bkgVO) {
			StringBuilder notType = new StringBuilder();
			if (null !=  bkgVO.getNotificationType()&& (null != bkgVO.getNotificationDateThirty() ||
					null != bkgVO.getNotificationDateSeven() || null != bkgVO.getNotificationDateZero())
					&& null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0 && null != bkgVO.getRecertificationStatus()) {
				
				
				if(null != bkgVO.getNotificationDateThirty())
				{
					notType.append("30 days notice sent on").append(" ")
						.append(DateUtils.getFormatedDate(bkgVO.getNotificationDateThirty(),
								"MM/dd/yyyy")).append(" ");
				}
				
				if(null != bkgVO
						.getNotificationDateSeven())
				{
					notType.append("7 days notice sent on").append(" ")
						.append(DateUtils.getFormatedDate(bkgVO.getNotificationDateSeven(),
								"MM/dd/yyyy")).append(" ");
				}
				
				if(null != bkgVO.getNotificationDateZero())
				{
					notType.append("0 days notice sent on").append(" ")
						.append(DateUtils.getFormatedDate(bkgVO.getNotificationDateZero(),
								"MM/dd/yyyy"));
				}

			}
			bkgVO.setNotificationType(notType.toString());
		}
		
		/**
		 * @param BackgroundInformationVO
		 * To set the recertification status based on the recertification status value
		*/
		private void setRecertificationStatus(BackgroundInfoProviderVO bkgVO) {
			String recertStat = null;
			if (null != bkgVO.getRecertificationStatus() && null!=bkgVO.getCriteriaBg() && bkgVO.getCriteriaBg() > 0) {
				if(bkgVO.getRecertificationStatus().equals("In Process")) {
					recertStat =bkgVO.getRecertificationStatus();
				} else if (Integer.parseInt(bkgVO.getRecertificationStatus()) == 0) {
					recertStat = "Due Today";
				} else if (Integer.parseInt(bkgVO.getRecertificationStatus()) < 0) {
					recertStat = "Past Due";
				} else if(Integer.parseInt(bkgVO.getRecertificationStatus()) > 0 && Integer.parseInt(bkgVO.getRecertificationStatus()) <= 30) {
					recertStat = "Due in "+ bkgVO.getRecertificationStatus() + " days";
				}
				
			}
			bkgVO.setRecertificationStatus(recertStat);
		}
		/**
		 * @param inputString
		 * @return List<String>
		 * To convert the Comma Separated string to a list 
		*/
		private List<String> convertCommaSepStrToList(String inputStr) {
			String stringWithCommas = inputStr.trim();
			if (stringWithCommas != null) {
				List<String> listHdr = new ArrayList<String>(
						Arrays.asList(stringWithCommas.split(",")));
				return listHdr;
			} else {
				return null;
			}
		}
		/**
		 * @param value
		 * @param buffer
		 * To format the value and append comma delimiter 
		*/
		private void formatAndAppend(String value, StringBuffer buffer){  
			if (StringUtils.isEmpty(value)){
				value = "";
			}
			buffer.append(value);
			buffer.append(",");
		}
		/**
		 * @param value
		 * @param buffer
		 * To format the value and append pipe delimiter 
		*/
		private void formatAndAppendPipe(String value,StringBuffer buffer) {
			if (StringUtils.isEmpty(value)){
				value = "";
			}
			buffer.append(value);
			buffer.append("|");
		} 
		public List<BackgroundCheckHistoryVO> getBackgroundCheckHistoryDetails(BackgroundCheckHistoryVO bgHistVO) throws BusinessServiceException {
			List<BackgroundCheckHistoryVO> backgroundHistList = null;
			try{
			backgroundHistList = spnDAO.getBackgroundCheckHistoryDetails(bgHistVO);
					
			}
			catch(DataServiceException e){
				logger.error("Error returned trying to retrieve BackgroundCheckHistoryDetails", e);
				throw new BusinessServiceException("Error returned trying to retrieve BackgroundCheckHistoryDetails", e);
			}
			return backgroundHistList;
		}
		
		public String getProviderName(Integer resourceId) throws BusinessServiceException{
			String name = null;
			try{
				name = spnDAO.getProviderName(resourceId);
			}
			catch(DataServiceException e){
				logger.error("Error returned trying to retrieve ProviderName", e);
				throw new BusinessServiceException("Error returned trying to retrieve ProviderName", e);
			}
			return name;
		}
}
