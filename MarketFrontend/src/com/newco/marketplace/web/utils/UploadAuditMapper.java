package com.newco.marketplace.web.utils;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditErrorDTO;
import com.newco.marketplace.vo.buyerFileUpload.UploadAuditVO;
import com.newco.marketplace.web.dto.buyerFileUpload.UploadAuditDTO;

public class UploadAuditMapper extends ObjectMapper{
	
	public UploadAuditVO convertDTOtoVO(Object objectDto, Object objectVO) { 
		UploadAuditDTO uploadAuditDTO = (UploadAuditDTO) objectDto;
		UploadAuditVO uploadAuditVO = (UploadAuditVO) objectVO;
		
		uploadAuditVO.setBuyerId(uploadAuditDTO.getBuyerId());
		uploadAuditVO.setFirstName(uploadAuditDTO.getFirstName());
		uploadAuditVO.setLastName(uploadAuditDTO.getLastName());
		uploadAuditVO.setBuyerResContactId(uploadAuditDTO.getBuyerResContactId());
		uploadAuditVO.setBuyerResourceId(uploadAuditDTO.getBuyerResourceId());
		uploadAuditVO.setCreatedDate(uploadAuditDTO.getCreatedDate());
		uploadAuditVO.setFileContent(uploadAuditDTO.getFileContent());
		uploadAuditVO.setUploadFileName(uploadAuditDTO.getUploadFileName());
		uploadAuditVO.setSoId(uploadAuditDTO.getSoId());
		uploadAuditVO.setUploadFile(uploadAuditDTO.getUploadFile());
		return uploadAuditVO;
		
	}

	public UploadAuditDTO convertVOtoDTO(Object objectVO, Object objectDto) {
		UploadAuditDTO uploadAuditDTO = (UploadAuditDTO) objectDto;
		UploadAuditVO uploadAuditVO = (UploadAuditVO) objectVO;
		
		uploadAuditDTO.setBuyerId(uploadAuditVO.getBuyerId());
		uploadAuditDTO.setFirstName(uploadAuditVO.getFirstName());
		uploadAuditDTO.setLastName(uploadAuditVO.getLastName());
		uploadAuditDTO.setBuyerResContactId(uploadAuditVO.getBuyerResContactId());
		uploadAuditDTO.setBuyerResourceId(uploadAuditVO.getBuyerResourceId());
		uploadAuditDTO.setCreatedDate(uploadAuditVO.getCreatedDate());
		uploadAuditDTO.setFileContent(uploadAuditVO.getFileContent());
		uploadAuditDTO.setUploadFileName(uploadAuditVO.getUploadFileName());
		uploadAuditDTO.setSoId(uploadAuditVO.getSoId());
		uploadAuditDTO.setUploadFile(uploadAuditVO.getUploadFile());
	
		return uploadAuditDTO;
	}
	
	/**
	 * This method converts UploadAuditErrorVO objects to UploadAuditErrorDTO objects
	 * @param errorRecords
	 * @return errorRecordList
	 */
	public List<UploadAuditErrorDTO> convertUploadAuditErrorVOToErrorDTO(List<UploadAuditErrorVO> errorRecords){
		List<UploadAuditErrorDTO> errorRecordList = new ArrayList<UploadAuditErrorDTO>();	
		if(errorRecords.size()>0){
			for (UploadAuditErrorVO errorRecord : errorRecords) {
				UploadAuditErrorDTO fileErrorRecord = new UploadAuditErrorDTO();			
				fileErrorRecord.setFileId(errorRecord.getFileId());
				fileErrorRecord.setErrorNotes(errorRecord.getErrorNotes());
				fileErrorRecord.setRowId(errorRecord.getRowId());
				fileErrorRecord.setMainServiceCategory(errorRecord.getMainServiceCategory());			
				fileErrorRecord.setTaskOneName(errorRecord.getTaskOneName());
				fileErrorRecord.setTaskOneCategory(errorRecord.getTaskOneCategory());
				fileErrorRecord.setTaskOneSubCategory(errorRecord.getTaskOneSubCategory());
				fileErrorRecord.setTaskOneSkill(errorRecord.getTaskOneSkill());
				fileErrorRecord.setTaskOneComments(errorRecord.getTaskOneComments());			
				fileErrorRecord.setTaskTwoName(errorRecord.getTaskTwoName());
				fileErrorRecord.setTaskTwoCategory(errorRecord.getTaskTwoCategory());
				fileErrorRecord.setTaskTwoSubCategory(errorRecord.getTaskTwoSubCategory());
				fileErrorRecord.setTaskTwoSkill(errorRecord.getTaskTwoSkill());
				fileErrorRecord.setTaskTwoComments(errorRecord.getTaskTwoComments());			
				fileErrorRecord.setTaskThreeName(errorRecord.getTaskThreeName());
				fileErrorRecord.setTaskThreeCategory(errorRecord.getTaskThreeCategory());
				fileErrorRecord.setTaskThreeSubCategory(errorRecord.getTaskThreeSubCategory());
				fileErrorRecord.setTaskThreeSkill(errorRecord.getTaskThreeSkill());
				fileErrorRecord.setTaskThreeComments(errorRecord.getTaskThreeComments());			
				fileErrorRecord.setPartOneManufacturer(errorRecord.getPartOneManufacturer());
				fileErrorRecord.setPartOneName(errorRecord.getPartOneName());
				fileErrorRecord.setPartOneModel(errorRecord.getPartOneModel());
				fileErrorRecord.setPartOneDesc(errorRecord.getPartOneDesc());
				fileErrorRecord.setPartOneQuantity(errorRecord.getPartOneQuantity());
				fileErrorRecord.setPartOneInboundCarrier(errorRecord.getPartOneInboundCarrier());
				fileErrorRecord.setPartOneInboundTrackingId(errorRecord.getPartOneInboundTrackingId());
				fileErrorRecord.setPartOneOutboundCarrier(errorRecord.getPartOneOutboundCarrier());
				fileErrorRecord.setPartOneOutboundTrackingId(errorRecord.getPartOneOutboundTrackingId());			
				fileErrorRecord.setPartTwoManufacturer(errorRecord.getPartTwoManufacturer());
				fileErrorRecord.setPartTwoName(errorRecord.getPartTwoName());
				fileErrorRecord.setPartTwoModel(errorRecord.getPartTwoModel());
				fileErrorRecord.setPartTwoDesc(errorRecord.getPartTwoDesc());
				fileErrorRecord.setPartTwoQuantity(errorRecord.getPartTwoQuantity());
				fileErrorRecord.setPartTwoInboundCarrier(errorRecord.getPartTwoInboundCarrier());
				fileErrorRecord.setPartTwoInboundTrackingId(errorRecord.getPartTwoInboundTrackingId());
				fileErrorRecord.setPartTwoOutboundCarrier(errorRecord.getPartTwoOutboundCarrier());
				fileErrorRecord.setPartTwoOutboundTrackingId(errorRecord.getPartTwoOutboundTrackingId());			
				fileErrorRecord.setPartThreeManufacturer(errorRecord.getPartThreeManufacturer());
				fileErrorRecord.setPartThreeName(errorRecord.getPartThreeName());
				fileErrorRecord.setPartThreeModel(errorRecord.getPartThreeModel());
				fileErrorRecord.setPartThreeDesc(errorRecord.getPartThreeDesc());
				fileErrorRecord.setPartThreeQuantity(errorRecord.getPartThreeQuantity()); 
				fileErrorRecord.setPartThreeInboundCarrier(errorRecord.getPartThreeInboundCarrier());
				fileErrorRecord.setPartThreeInboundTrackingId(errorRecord.getPartThreeInboundTrackingId());
				fileErrorRecord.setPartThreeOutboundCarrier(errorRecord.getPartThreeOutboundCarrier());
				fileErrorRecord.setPartThreeOutboundTrackingId(errorRecord.getPartThreeOutboundTrackingId());			
				fileErrorRecord.setPartMaterial(errorRecord.getPartMaterial());
				fileErrorRecord.setLocationType(errorRecord.getLocationType());
				fileErrorRecord.setBusinessName(errorRecord.getBusinessName());
				fileErrorRecord.setFirstName(errorRecord.getFirstName());
				fileErrorRecord.setLastName(errorRecord.getLastName());
				fileErrorRecord.setStreet1(errorRecord.getStreet1());
				fileErrorRecord.setStreet2(errorRecord.getStreet2());
				fileErrorRecord.setAptNo(errorRecord.getAptNo()); 
				fileErrorRecord.setCity(errorRecord.getCity());
				fileErrorRecord.setState(errorRecord.getState());
				fileErrorRecord.setZip(errorRecord.getZip());
				fileErrorRecord.setEmail(errorRecord.getEmail());
				fileErrorRecord.setPhone(errorRecord.getPhone());
				fileErrorRecord.setPhoneType(errorRecord.getPhoneType());
				fileErrorRecord.setAltPhone(errorRecord.getAltPhone());
				fileErrorRecord.setAltPhoneType(errorRecord.getAltPhoneType());
				fileErrorRecord.setFax(errorRecord.getFax());
				fileErrorRecord.setSoLocNotes(errorRecord.getSoLocNotes());
				fileErrorRecord.setTitle(errorRecord.getTitle());			
				fileErrorRecord.setOverview(errorRecord.getOverview());
				fileErrorRecord.setBuyerTc(errorRecord.getBuyerTc());
				fileErrorRecord.setSpecialInst(errorRecord.getSpecialInst());		
				fileErrorRecord.setServiceDateType(errorRecord.getServiceDateType());
				fileErrorRecord.setServiceDate(errorRecord.getServiceDate());
				fileErrorRecord.setServiceTime(errorRecord.getServiceTime());
				fileErrorRecord.setServiceEndDate(errorRecord.getServiceEndDate());
				fileErrorRecord.setServiceEndTime(errorRecord.getServiceEndTime());			
				fileErrorRecord.setAttachment1(errorRecord.getAttachment1());
				fileErrorRecord.setDesc1(errorRecord.getDesc1());
				fileErrorRecord.setAttachment2(errorRecord.getAttachment2());
				fileErrorRecord.setDesc2(errorRecord.getDesc2());
				fileErrorRecord.setAttachment3(errorRecord.getAttachment3());
				fileErrorRecord.setDesc3(errorRecord.getDesc3());	
				fileErrorRecord.setProvConfTimeInd(errorRecord.getProvConfTimeInd());
				fileErrorRecord.setBrandingInfo(errorRecord.getBrandingInfo());
				fileErrorRecord.setCustRef1(errorRecord.getCustRef1());
				fileErrorRecord.setCustRef1Value(errorRecord.getCustRef1Value());
				fileErrorRecord.setCustRef2(errorRecord.getCustRef2());
				fileErrorRecord.setCustRef2Value(errorRecord.getCustRef2Value());
				fileErrorRecord.setCustRef3(errorRecord.getCustRef3());
				fileErrorRecord.setCustRef3Value(errorRecord.getCustRef3Value());
				fileErrorRecord.setCustRef4(errorRecord.getCustRef4());
				fileErrorRecord.setCustRef4Value(errorRecord.getCustRef4Value());
				fileErrorRecord.setCustRef5(errorRecord.getCustRef5());
				fileErrorRecord.setCustRef5Value(errorRecord.getCustRef5Value());
				fileErrorRecord.setCustRef6(errorRecord.getCustRef6());
				fileErrorRecord.setCustRef6Value(errorRecord.getCustRef6Value());
				fileErrorRecord.setCustRef7(errorRecord.getCustRef7());
				fileErrorRecord.setCustRef7Value(errorRecord.getCustRef7Value());
				fileErrorRecord.setCustRef8(errorRecord.getCustRef8());
				fileErrorRecord.setCustRef8Value(errorRecord.getCustRef8Value());
				fileErrorRecord.setCustRef9(errorRecord.getCustRef9());
				fileErrorRecord.setCustRef9Value(errorRecord.getCustRef9Value());
				fileErrorRecord.setCustRef10(errorRecord.getCustRef10());
				fileErrorRecord.setCustRef10Value(errorRecord.getCustRef10Value()) ;
				fileErrorRecord.setLaborSpendLimit(errorRecord.getLaborSpendLimit());
				fileErrorRecord.setPartSpendLimit(errorRecord.getPartSpendLimit());
				fileErrorRecord.setTemplateId(errorRecord.getTemplateId());
				errorRecordList.add(fileErrorRecord);
			}
		}
		return errorRecordList;
	}
}
		
