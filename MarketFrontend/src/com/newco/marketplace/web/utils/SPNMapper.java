package com.newco.marketplace.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderProfileBuyerVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderProfileVO;
import com.newco.marketplace.dto.vo.spn.SPNSkillVO;
import com.newco.marketplace.dto.vo.spn.SPNSummaryVO;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.dto.CommMonitorRowDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SPNBuilderDocRowDTO;
import com.newco.marketplace.web.dto.SPNBuilderFormDTO;
import com.newco.marketplace.web.dto.SPNCriteriaDTO;
import com.newco.marketplace.web.dto.SPNLandingTableRowDTO;
import com.newco.marketplace.web.dto.SPNProviderProfileBuyerTable;
import com.newco.marketplace.web.dto.SPNProviderProfileInfoRow;

/**
 * Maps DTO & VO objects for SPN functionality
 * @author RGURRA0
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:25 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNMapper extends ObjectMapper {
	
	public static String CHECKED_VALUE = "true";
	public static String UN_CHECKED_VALUE = "false";
	
	/**
	 * DTO to VO conversions for SPN Builder info
	 * @param spnBuilderFormDto
	 * @return
	 */
	public static SPNHeaderVO convertHeaderDTOToVO(SPNBuilderFormDTO spnBuilderFormDto){
		
		SPNHeaderVO spnHeaderVO = new SPNHeaderVO();
		
		spnHeaderVO.setContactEmail(spnBuilderFormDto.getContactEmail());
		spnHeaderVO.setContactName(spnBuilderFormDto.getContactName());

		spnHeaderVO.setDescription(spnBuilderFormDto.getNetworkDescription());
		
		if(!SLStringUtils.isNullOrEmpty(spnBuilderFormDto.getCheckboxNetworkRequiresDocuments()) ){
			spnHeaderVO.setDocRequired(Boolean.parseBoolean(spnBuilderFormDto.getCheckboxNetworkRequiresDocuments()) );
		} else {
			spnHeaderVO.setDocRequired(false);
		}

		spnHeaderVO.setInstruction(spnBuilderFormDto.getApprovalInstructions());
		spnHeaderVO.setName(spnBuilderFormDto.getNetworkName());
		spnHeaderVO.setSpnId(spnBuilderFormDto.getSpnId());
		spnHeaderVO.setBuyerId(spnBuilderFormDto.getBuyerId());
				
		spnHeaderVO.setSpnRelatedDocumentIds(setSelectedDocumentIdInVO(spnBuilderFormDto.getDocumentsDetailsList()));
		spnHeaderVO.setSpnCriteriaVO(convertDTOToVOSpnCriteria(spnBuilderFormDto.getTheCriteria()));
		
		return spnHeaderVO;
		
	}
	
	/**
	 * return List of Doc Ids which are checked 
	 * @param documentsDetailsList
	 * @return
	 */
	private static List<DocumentVO> setSelectedDocumentIdInVO(
			List<SPNBuilderDocRowDTO> documentsDetailsList) {
		
		 List<DocumentVO> spnInfoVOList = new ArrayList<DocumentVO>();
		 DocumentVO spnInfoVO = null;
		
		for(SPNBuilderDocRowDTO spnDto:documentsDetailsList){
			// if checked then only add to VO List
			if(!SLStringUtils.isNullOrEmpty(spnDto.getChecked())){
				if(spnDto.getChecked().equals(CHECKED_VALUE)){
					spnInfoVO = new DocumentVO();
					spnInfoVO.setFileName(spnDto.getName());
					if(SLStringUtils.IsParsableNumber(spnDto.getId())){
						Integer id = Integer.parseInt(spnDto.getId());
						spnInfoVO.setDocumentId(id);
					}
					
					spnInfoVO.setDescription(spnDto.getDescription());
					
					spnInfoVOList.add(spnInfoVO);
					
				}
			}
		}
		return spnInfoVOList;
	}

	/**
	 * conversions VO TO DTO for SPN Builder info
	 * @param spnHeaderVO
	 * @return
	 */
	public static SPNBuilderFormDTO convertHeaderVOToDTO(SPNHeaderVO spnHeaderVO, List<SPNBuilderDocRowDTO> documentsDTOList){
		
		SPNBuilderFormDTO spnBuilderFormDto = new SPNBuilderFormDTO();
		
		spnBuilderFormDto.setContactEmail(spnHeaderVO.getContactEmail());
		spnBuilderFormDto.setContactName(spnHeaderVO.getContactName());

		spnBuilderFormDto.setNetworkDescription(spnHeaderVO.getDescription());
		
		spnBuilderFormDto.setCheckboxNetworkRequiresDocuments(String.valueOf(spnHeaderVO.isDocRequired()));

		spnBuilderFormDto.setApprovalInstructions(spnHeaderVO.getInstruction());
		spnBuilderFormDto.setNetworkName(spnHeaderVO.getName());
		spnBuilderFormDto.setSpnId(spnHeaderVO.getSpnId());
		spnBuilderFormDto.setBuyerId(spnHeaderVO.getBuyerId());
				
		spnBuilderFormDto.setDocumentsDetailsList(getUpdatedCheckBoxList(documentsDTOList, spnHeaderVO.getSpnRelatedDocumentIds()));
		
		spnBuilderFormDto.setSpnLocked(spnHeaderVO.isSpnLocked());
		spnBuilderFormDto.setTheCriteria(convertVOToDTOSpnCriteria(spnHeaderVO.getSpnCriteriaVO()));
		
		return spnBuilderFormDto;
		
	}
	
	/**
	 * update document List with checked value based on list of doc Ids from DB 
	 * @param documentsDTOList
	 * @param spnRelatedDocumentIds
	 * @return
	 */
	private static List<SPNBuilderDocRowDTO> getUpdatedCheckBoxList(
			List<SPNBuilderDocRowDTO> documentsDTOList,
			List<DocumentVO> spnRelatedDocumentIds) {
		
		List<SPNBuilderDocRowDTO>  modofiedList = new ArrayList<SPNBuilderDocRowDTO>();
		
		for(SPNBuilderDocRowDTO docDto: documentsDTOList){
			for(DocumentVO checkedDoc: spnRelatedDocumentIds){
				int selectedDocId =  checkedDoc.getDocumentId().intValue();
				int documentId =  Integer.parseInt(docDto.getId());
				
					if(documentId == selectedDocId){
						docDto.setChecked(CHECKED_VALUE);
					}
					
				}
			modofiedList.add(docDto);
		}
		
		return modofiedList;
	}

	/**
	 * conversions VO TO DTO for document Info
	 * @param list
	 * @return
	 */
	public static List<SPNBuilderDocRowDTO> convertSPNDocInfoVOToDTO(
			List<DocumentVO> list) {
		List<SPNBuilderDocRowDTO> spnInfoDTOList = new ArrayList<SPNBuilderDocRowDTO>();

		SPNBuilderDocRowDTO spnInfoDTO = null;
		
		for (DocumentVO docVO : list) {
			spnInfoDTO = new SPNBuilderDocRowDTO();
			spnInfoDTO.setName(docVO.getFileName());
			if(docVO.getDocumentId()!= null){
				spnInfoDTO.setId(docVO.getDocumentId().toString());
			}
			
			spnInfoDTO.setDescription(docVO.getDescription());

			spnInfoDTOList.add(spnInfoDTO);

		}
		return spnInfoDTOList;
	}
	
    /**
     * conversions DTO TO VO for document Info
     * @param list
     * @return
     */
	public static List<DocumentVO> convertSPNDocInfoDTOToVO(List<SPNBuilderDocRowDTO> list) {
		
		List<DocumentVO> spnInfoVOList = new ArrayList<DocumentVO>();
		DocumentVO spnInfoVO = null;

		for(SPNBuilderDocRowDTO dto : list ){
			spnInfoVO = new DocumentVO();
			spnInfoVO.setFileName(dto.getName());
			if(SLStringUtils.isNullOrEmpty(dto.getId())){
				spnInfoVO.setDocumentId(Integer.getInteger(dto.getId()) );
			}
			
			spnInfoVO.setDescription(dto.getDescription());

			spnInfoVOList.add(spnInfoVO);

		}
		return spnInfoVOList;
	}
	
	/**
	 * conversions DTO TO VO for SPN related document Info
	 * @param list
	 * @return
	 */
	public static List<DocumentVO> convertBuyerDocInfoDTOToVO(List<LabelValueBean> list) {
		
		List<DocumentVO> spnInfoVOList = new ArrayList<DocumentVO>();

		DocumentVO spnInfoVO = null;
		for (LabelValueBean bean : list) {
			spnInfoVO = new DocumentVO();
			spnInfoVO.setFileName(bean.getLabel());
			if(SLStringUtils.isNullOrEmpty(bean.getValue())){
				spnInfoVO.setDocumentId(Integer.getInteger(bean.getValue()) );
			}

			spnInfoVOList.add(spnInfoVO);

		}
		return spnInfoVOList;
	}
	
	/**
	 * conversions VO TO DTO for SPN related document Info
	 * @param list
	 * @return
	 */
	public static List<SPNBuilderDocRowDTO> convertBuyerDocInfoVOToDTO(List<DocumentVO> list) {
		
		List<SPNBuilderDocRowDTO> spnInfoVOList = new ArrayList<SPNBuilderDocRowDTO>();

		SPNBuilderDocRowDTO spnInfoVO = null;
		for (DocumentVO docVO : list) {
			spnInfoVO = new SPNBuilderDocRowDTO();
			spnInfoVO.setDescription(docVO.getDescription());
			if(docVO.getDocumentId()!=null){
				spnInfoVO.setId(docVO.getDocumentId().toString()) ;
			}
			spnInfoVO.setName(docVO.getTitle());
			spnInfoVO.setChecked(UN_CHECKED_VALUE);
			spnInfoVOList.add(spnInfoVO);

		}
		return spnInfoVOList;
	}
	
	/**
	 * Converting VO to DTO for SPN landing Info
	 * @param spnSummaryVOList
	 * @return
	 */
	public static List<SPNLandingTableRowDTO> convertVOToDTOSpnLandingInfo
	                                                     (List<SPNSummaryVO> spnSummaryVOList){
		List<SPNLandingTableRowDTO> spnLandingDTOList = new ArrayList<SPNLandingTableRowDTO>();
		
		SPNLandingTableRowDTO landingTableDTO  = null;
		for (SPNSummaryVO summaryVO : spnSummaryVOList) {
			landingTableDTO = new SPNLandingTableRowDTO();
			
			landingTableDTO.setNumApplicants(summaryVO.getApplicantCnt());
			landingTableDTO.setNumInactive(summaryVO.getInactiveCnt());
			landingTableDTO.setNumMatches(summaryVO.getMatchesCnt());
			landingTableDTO.setNumMembers(summaryVO.getMemberCnt());
			landingTableDTO.setNumNonInterested(summaryVO.getNotInterestedCnt());
			landingTableDTO.setNumRemoved(summaryVO.getRemovedCnt());
			landingTableDTO.setId(summaryVO.getSpnId().toString());
			landingTableDTO.setName(summaryVO.getName());
			
			spnLandingDTOList.add(landingTableDTO);
		
		}
		
		return spnLandingDTOList;
	}
	
	/**
	 * convert SPN Criteria from DTO to VO
	 * @param criteriaDto
	 * @return
	 */
	private static SPNCriteriaVO convertDTOToVOSpnCriteria(SPNCriteriaDTO criteriaDto){
				
		SPNCriteriaVO criteriaVO = new SPNCriteriaVO();
		
		if(criteriaDto == null) {
			return criteriaVO;
		}
		
		if (criteriaDto.getVliInsurance()) {
			criteriaVO.setInsAutoLiability(criteriaDto.getVliInsurance());
			criteriaVO.setInsAutoLiabilityMinAmt(criteriaDto.getVliInsuranceAmount());
		}
		
		if (criteriaDto.getCgliInsurance()) {
			criteriaVO.setInsGeneralLiability(criteriaDto.getCgliInsurance());
			criteriaVO.setInsGeneralLiabilityMinAmt(criteriaDto.getCgliInsuranceAmount());
		}
		
		if (criteriaDto.getWciInsurance()) {
			criteriaVO.setInsWorkmanComp(criteriaDto.getWciInsurance());
			criteriaVO.setInsWorkmanCompMinAmt(criteriaDto.getWciInsuranceAmount());
		}
			
		if (null != criteriaDto.getLanguages() && criteriaDto.getLanguages().intValue() != -1){
			criteriaVO.setLanguageId(criteriaDto.getLanguages());
		}
		
		if (null != criteriaDto.getTotalNumOfServiceOrdersClosed() &&
				criteriaDto.getTotalNumOfServiceOrdersClosed().intValue() > -1) {
			criteriaVO.setMinSOClosed(Integer.valueOf(criteriaDto.getTotalNumOfServiceOrdersClosed()));
		}
		
		if (null != criteriaDto.getResourceCategory() && criteriaDto.getResourceCategory().intValue() != -1) {
			criteriaVO.setResourceCredCategoryId(criteriaDto.getResourceCategory());
			if (null != criteriaDto.getResourceCredential() && criteriaDto.getResourceCredential().intValue() != -1) {
				criteriaVO.setResourceCredTypeId(criteriaDto.getResourceCredential());
			}
		}
		criteriaVO.setSkills(convertDTOToVOSkills(criteriaDto.getTasks(), criteriaDto.getMainServiceCategoryId()));
		
		if (null != criteriaDto.getRatings() && criteriaDto.getRatings().doubleValue() != -1.0){
			criteriaVO.setStarRating(criteriaDto.getRatings());
			criteriaVO.setStarRatingIncludeNonRated(criteriaDto.getIncludeNonRated());
		}
		
		if (null != criteriaDto.getCredentialCategory() && criteriaDto.getCredentialCategory().intValue() != -1) {
			criteriaVO.setVendorCredCategoryId(criteriaDto.getCredentialCategory());
			if (null != criteriaDto.getCredentials() && criteriaDto.getCredentials().intValue() != -1) {
				criteriaVO.setVendorCredTypeId(criteriaDto.getCredentials());
			}
		}

		return criteriaVO;
	}
	
	/**
	 * Convert Skills from DTO to VO
	 * @param taskDtoList
	 * @param mainCategoryId
	 * @return
	 */
	private static List<SPNSkillVO> convertDTOToVOSkills(List<SOTaskDTO> taskDtoList, Integer mainCategoryId){
		List<SPNSkillVO>  spnListVO = new ArrayList<SPNSkillVO>();
		SPNSkillVO skillVo = null;
		
		if (null == taskDtoList || taskDtoList.size() == 0) {
			skillVo = new SPNSkillVO();
			skillVo.setMainCategory(mainCategoryId);
			spnListVO.add(skillVo);
		} else {
			for(SOTaskDTO taskDto:taskDtoList){
				skillVo = new SPNSkillVO();
				skillVo.setMainCategory(mainCategoryId);
				if (null != taskDto.getCategoryId() && taskDto.getCategoryId().intValue() != -1) {
					skillVo.setCategory(taskDto.getCategoryId());
				}
				
				if (null != taskDto.getSubCategoryId() && taskDto.getSubCategoryId().intValue() != -1) {
					skillVo.setSubCategory(taskDto.getSubCategoryId());
				}
				
				if (null != taskDto.getSkillId() && taskDto.getSkillId().intValue() != -1) {
					skillVo.setSkill(taskDto.getSkillId());
				}
				
				spnListVO.add(skillVo);
				
			}
		}
		return spnListVO;
		
	}
	
	/**
	 * Convert Skills from VO to DTO
	 * @param taskVOList
	 * @return
	 */
	private static List<SOTaskDTO> convertVOToDTOSkills(List<SPNSkillVO> taskVOList){
		List<SOTaskDTO>  spnListDTO = new ArrayList<SOTaskDTO>();
		SOTaskDTO skillDto = null;
		
		for(SPNSkillVO taskVO:taskVOList){
			if(taskVO.getMainCategory() != null &&
			   taskVO.getCategory() == null &&	
			   taskVO.getSkill() == null &&
			   taskVO.getSubCategory() == null )
			   {
				   // do nothing you are the main service category
			   }
			   else {
				skillDto = new SOTaskDTO();
				skillDto.setCategoryId(taskVO.getCategory());
				skillDto.setSkillId(taskVO.getSkill());
				skillDto.setSubCategoryId(taskVO.getSubCategory());
				spnListDTO.add(skillDto);
			   }
		}
		return spnListDTO;
		
	}
	
	/**
	 * Convert SpnCriteria from VO to DTO
	 * @param criteriaVo
	 * @return
	 */
	private static SPNCriteriaDTO convertVOToDTOSpnCriteria(SPNCriteriaVO criteriaVo){
		SPNCriteriaDTO criteriaDto = new SPNCriteriaDTO();
		
		if(criteriaVo == null)
			return criteriaDto;
		
		criteriaDto.setVliInsurance(criteriaVo.isInsAutoLiability());
		if (null != criteriaVo.getInsAutoLiabilityMinAmt()) {
			criteriaDto.setVliInsuranceAmount(criteriaVo.getInsAutoLiabilityMinAmt());		
		} else {
			criteriaDto.setVliInsuranceAmount(new Double(0.0));
		}
	
		criteriaDto.setCgliInsurance(criteriaVo.isInsGeneralLiability());
		
		if (null != criteriaVo.getInsGeneralLiabilityMinAmt()) {
			criteriaDto.setCgliInsuranceAmount(criteriaVo.getInsGeneralLiabilityMinAmt());
		} else {
			criteriaDto.setCgliInsuranceAmount(new Double(0.0));
		}
		
		criteriaDto.setWciInsurance(criteriaVo.isInsWorkmanComp());
		if (null != criteriaVo.getInsWorkmanCompMinAmt()) {
			criteriaDto.setWciInsuranceAmount(criteriaVo.getInsWorkmanCompMinAmt());
		} else {
			criteriaDto.setWciInsuranceAmount(new Double(0.0));
		}
		
		if (null != criteriaVo.getLanguageId()) {
			criteriaDto.setLanguages(criteriaVo.getLanguageId());
		} else {
			criteriaDto.setLanguages(new Integer(-1));
		}
		
		if (null != criteriaVo.getMinSOClosed()) {
			criteriaDto.setTotalNumOfServiceOrdersClosed(criteriaVo.getMinSOClosed());
		} else {
			criteriaDto.setTotalNumOfServiceOrdersClosed(new Integer(0));
		}
		
		if (null != criteriaVo.getResourceCredCategoryId()) {
			criteriaDto.setResourceCategory(criteriaVo.getResourceCredCategoryId());
		} else {
			criteriaDto.setResourceCategory(new Integer(-1));
		}
		
		if (null != criteriaVo.getResourceCredTypeId()) {
			criteriaDto.setResourceCredential(criteriaVo.getResourceCredTypeId());
		} else {
			criteriaDto.setResourceCredential(new Integer(-1));
		}
		
		criteriaDto.addTaskList((ArrayList<SOTaskDTO>)convertVOToDTOSkills(criteriaVo.getSkills()));
		
		if(criteriaVo.getSkills()!= null && criteriaVo.getSkills().size() > 0){
			criteriaDto.setMainServiceCategoryId( ((SPNSkillVO)criteriaVo.getSkills().get(0) ).getMainCategory());
		}
		
		if (null != criteriaVo.getStarRating()) {
			criteriaDto.setRatings(criteriaVo.getStarRating());
			if(criteriaVo.getStarRating() != null && criteriaVo.getStarRating() >= 0.0){
				criteriaDto.setRatingsNumber( UIUtils.calculateScoreNumber(criteriaVo.getStarRating()));
		    }else{
			    criteriaDto.setRatingsNumber(null);
		    }
		} else {
			criteriaDto.setRatings(null);
			criteriaDto.setRatingsNumber(null);
		}
		
		criteriaDto.setIncludeNonRated(criteriaVo.isStarRatingIncludeNonRated());
		
		if (null != criteriaVo.getVendorCredCategoryId()) {
			criteriaDto.setCredentialCategory(criteriaVo.getVendorCredCategoryId());
		} else {
			criteriaDto.setCredentialCategory(new Integer(-1));
		}
		
		if (null != criteriaVo.getVendorCredTypeId()) {
			criteriaDto.setCredentials(criteriaVo.getVendorCredTypeId());
		} else {
			criteriaDto.setCredentials(new Integer(-1));
		}
		
		return criteriaDto;
	}
	

	public static List<SPNBuilderDocRowDTO> convertSpnRelatedDocsVOToDTO(List<DocumentVO> spnRelatedDocumentIds) {
		 List<SPNBuilderDocRowDTO>  selectedDocList = new ArrayList<SPNBuilderDocRowDTO>();
		 SPNBuilderDocRowDTO docDTO = null;
		 for(DocumentVO docVO: spnRelatedDocumentIds){
			 docDTO = new SPNBuilderDocRowDTO();
			 docDTO.setId(docVO.getDocumentId().toString());
			 docDTO.setChecked(CHECKED_VALUE);
			 docDTO.setDescription(docVO.getDescription());
			 docDTO.setName(docVO.getTitle());
			 selectedDocList.add(docDTO);
		 }
		return selectedDocList;
	}	
	
	public static List<CommMonitorRowDTO> convertSPNHeaderListToCommRowList(List<SPNHeaderVO> voList)
	{
		List<CommMonitorRowDTO> dtoList = new ArrayList<CommMonitorRowDTO>();
		
		if(voList == null)
			return dtoList;
		
		CommMonitorRowDTO dto = null;
		
		
		for(SPNHeaderVO vo : voList)
		{
			dto = new CommMonitorRowDTO();
			
			dto.setDateTime(vo.getInviteDate());
			
			dto.setFrom(new LabelValueBean(vo.getBusinessName(), null));
			
			String link = "spnCampaignLandingAction_displayPage.action?spnID=" + vo.getSpnId()  +  "&spnNetworkID=" + vo.getNetworkId();
			String subject = "Select Provider Network Opportunity";
			if(vo.getInviteeFirstName() != null)
			{
				String name = vo.getInviteeFirstName() + " " + vo.getInviteeLastName();
				dto.setGeneralPurposeParameter(name);
				subject += ": "+ name;
				
				link +="&inviteeName=" + name;
			}
			
			
			dto.setSubject(new LabelValueBean(subject, link)); // TODO pull the subject from resource bundle
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	/**
	 * convert Provider Complete Profile Buyer SPN info from VO to DTO
	 * @param spnProProfileVOList
	 * @return List<SPNProviderProfileBuyerTable> DTO List
	 */
	public static List<SPNProviderProfileBuyerTable> convertProProfileBuyerListVOToDTO(
			List<SPNProviderProfileBuyerVO> spnProProfileVOList) {
	
		List<SPNProviderProfileBuyerTable> proProfileSPNbuyerTableList = 
			                         new ArrayList<SPNProviderProfileBuyerTable>();
		SPNProviderProfileBuyerTable profileBuyerTableRow = null;
		for(SPNProviderProfileBuyerVO profileSPNBuyerVO : spnProProfileVOList){
			profileBuyerTableRow = new SPNProviderProfileBuyerTable();
			profileBuyerTableRow.setBlobBytes(profileSPNBuyerVO.getLogoBlobBytes());
			profileBuyerTableRow.setCompanyName(profileSPNBuyerVO.getCompanyName());
			profileBuyerTableRow.setCompanyID(profileSPNBuyerVO.getBuyerId());
			profileBuyerTableRow.setLogoFileName(profileSPNBuyerVO.getLogoFileName());
			profileBuyerTableRow.setSpnList
						(convertSPNProviderProfileVOToDTOList(profileSPNBuyerVO.getProviderSPNList()));
			
			proProfileSPNbuyerTableList.add(profileBuyerTableRow);
		}
		
		return proProfileSPNbuyerTableList;
	}

	/**
	 * convert Provider Complete Profile SPN info from VO to DTO
	 * @param providerSPNList
	 * @return List<SPNProviderProfileInfoRow>  DTOList
	 */
	private static List<SPNProviderProfileInfoRow> convertSPNProviderProfileVOToDTOList(
			List<SPNProviderProfileVO> providerSPNList) {
		List<SPNProviderProfileInfoRow> spnProProfileInfoRowList = 
			                             new ArrayList<SPNProviderProfileInfoRow>();
		SPNProviderProfileInfoRow spnProProfileInfoRow = null;
		for(SPNProviderProfileVO spnProProfileVO : providerSPNList){
			spnProProfileInfoRow = new SPNProviderProfileInfoRow();
			spnProProfileInfoRow.setMembershipDate(spnProProfileVO.getMembershipDate());
			spnProProfileInfoRow.setSpnId(spnProProfileVO.getSpnId());
			spnProProfileInfoRow.setSpnName(spnProProfileVO.getSpnName());
			spnProProfileInfoRow.setSpnNetworkId(spnProProfileVO.getSpnNetworkId());
			spnProProfileInfoRow.setStatus(spnProProfileVO.getStatus());
			spnProProfileInfoRowList.add(spnProProfileInfoRow);
		}
		return spnProProfileInfoRowList;
	}
	
	public static List<SPNLandingTableRowDTO> convertspnMonitorVOToDTO(
			List<SPNMonitorVO> spnMonitorVOList) {
		List<SPNLandingTableRowDTO> spnLandingDTOList = new ArrayList<SPNLandingTableRowDTO>();
		
		SPNLandingTableRowDTO landingTableDTO  = null;
		for (SPNMonitorVO monitorVO : spnMonitorVOList) {
			landingTableDTO = new SPNLandingTableRowDTO();
			
			landingTableDTO.setId(String.valueOf(monitorVO.getSpnId()));
			landingTableDTO.setName(monitorVO.getSpnName());
			
			spnLandingDTOList.add(landingTableDTO);
		
		}
		
		return spnLandingDTOList;
	}

	public static List<SPNLandingTableRowDTO> convertAllspnMonitorVOToDTO(
			List<SPNMonitorVO> spnMonitorVOList, Map<Long, Long> spnApprovedProviderCountMap) {
		
		List<SPNLandingTableRowDTO> spnLandingDTOList = new ArrayList<SPNLandingTableRowDTO>();		
		SPNLandingTableRowDTO landingTableDTO  = null;
		
		for (SPNMonitorVO monitorVO : spnMonitorVOList){
			landingTableDTO = new SPNLandingTableRowDTO();			
			landingTableDTO.setId(String.valueOf(monitorVO.getSpnId()));
			
			if(null != spnApprovedProviderCountMap){
				Long spnId = new Long(monitorVO.getSpnId());
				
				if(null != spnApprovedProviderCountMap.get(spnId)){
					Long approvedSpnCount = spnApprovedProviderCountMap.get(spnId);
					landingTableDTO.setName(monitorVO.getSpnName()+"("+approvedSpnCount.toString()+")");
				}else{
					landingTableDTO.setName(monitorVO.getSpnName()+"(0)");
				}
			}else{
				landingTableDTO.setName(monitorVO.getSpnName()+"(0)");
			}
			spnLandingDTOList.add(landingTableDTO);	
		}		
		return spnLandingDTOList;
	}
}

/*
 * Maintenance History:
 * $Log: SPNMapper.java,v $
 * Revision 1.2  2008/05/02 21:23:25  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.35  2008/05/01 16:30:29  rgurra0
 * set Status Str for SPN ProviderProfileDTO --reverted as handled in SL copy_messages properties
 *
 * Revision 1.1.2.34  2008/05/01 15:41:10  rgurra0
 * set Status Str for SPN ProviderProfileDTO
 *
 * Revision 1.1.2.33  2008/04/30 16:36:12  rgurra0
 * set CompanyId on SpnProviderProfileBuyerTable
 *
 * Revision 1.1.2.32  2008/04/29 15:35:34  rgurra0
 * VO O conversion for Provider Profile SPN Info
 *
 * Revision 1.1.2.31  2008/04/25 20:02:53  cgarc03
 * Added 'inviteeName' to parameter list at end of link/url.
 *
 * Revision 1.1.2.30  2008/04/25 19:16:53  cgarc03
 * convertSPNHeaderListToCommRowList() - Added fname/lname to Subject.
 *
 * Revision 1.1.2.29  2008/04/25 16:01:16  rgurra0
 * set rating to null instead of -1 by default
 *
 * Revision 1.1.2.28  2008/04/24 21:49:48  dmill03
 * added null check in mapper
 *
 * Revision 1.1.2.27  2008/04/24 14:37:56  cgarc03
 * convertSPNHeaderListToCommRowList() - dto date/time types has changed.
 *
 * Revision 1.1.2.26  2008/04/22 20:33:35  cgarc03
 * Latest changes to display SPN Invite list on dashboard.
 *
 * Revision 1.1.2.25  2008/04/18 00:22:50  mhaye05
 * updated to all for saving of criteria
 *
 * Revision 1.1.2.24  2008/04/17 22:38:38  cgarc03
 * convertVOToDTOSpnCriteria() - Set new ratingsNumber data member.
 *
 * Revision 1.1.2.23  2008/04/16 17:49:41  rgurra0
 * setName for document
 *
 * Revision 1.1.2.22  2008/04/16 15:55:00  rgurra0
 * added convertSpnRelatedDocsVOToDTO
 *
 * Revision 1.1.2.21  2008/04/15 23:23:34  dmill03
 * *** empty log message ***
 *
 * Revision 1.1.2.20  2008/04/15 16:30:04  rgurra0
 * code clean up , java doc
 *
 */