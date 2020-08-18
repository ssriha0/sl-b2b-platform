package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.WarrantyVO;
import com.newco.marketplace.web.dto.provider.WarrantyDto;

public class WarrantyMapper extends ObjectMapper{

	public WarrantyVO convertDTOtoVO(Object objectDto, Object objectVO) {
		WarrantyDto wdto = (WarrantyDto)objectDto;
		WarrantyVO wvo = (WarrantyVO)objectVO;
		//map DTO to VO 
		if (objectDto != null && objectVO != null) {
			wvo.setMaxVendorID(wdto.getMaxVendorID());
			wvo.setWarrOfferedLabor(wdto.getWarrOfferedLabor());
			wvo.setWarrPeriodLabor(wdto.getWarrPeriodLabor());
			wvo.setWarrOfferedParts(wdto.getWarrOfferedParts());
			wvo.setWarrPeriodParts(wdto.getWarrPeriodParts());
			wvo.setWarrantyMeasure(wdto.getWarrantyMeasure());
			wvo.setFreeEstimate(wdto.getFreeEstimate());
			wvo.setConductDrugTest(wdto.getConductDrugTest());
			wvo.setConsiderDrugTest(wdto.getConsiderDrugTest());
			wvo.setHasEthicsPolicy(wdto.getHasEthicsPolicy());
			wvo.setRequireBadge(wdto.getRequireBadge());
			wvo.setConsiderBadge(wdto.getConsiderBadge());
			wvo.setRequireUsDoc(wdto.getRequireUsDoc());
			wvo.setConsiderImplPolicy(wdto.getConsiderImplPolicy());
			wvo.setConsiderEthicPolicy(wdto.getConsiderEthicPolicy());
			wvo.setWarrantyPeriods(wdto.getWarrantyPeriods());
			wvo.setVendorID(wdto.getVendorID());
		}
		return wvo;
	}

	public WarrantyDto convertVOtoDTO(Object objectVO, Object objectDto) {
		WarrantyDto wdto = (WarrantyDto)objectDto;
		WarrantyVO wvo = (WarrantyVO)objectVO;
		//map VO to DTO
		//Need NULL value logic?
		if (objectDto != null && objectVO != null) {
			wdto.setMaxVendorID(wvo.getMaxVendorID());
			wdto.setWarrOfferedLabor(wvo.getWarrOfferedLabor());
			wdto.setWarrPeriodLabor(wvo.getWarrPeriodLabor());
			wdto.setWarrOfferedParts(wvo.getWarrOfferedParts());
			wdto.setWarrPeriodParts(wvo.getWarrPeriodParts());
			wdto.setWarrantyMeasure(wvo.getWarrantyMeasure());
			wdto.setFreeEstimate(wvo.getFreeEstimate());
			wdto.setConductDrugTest(wvo.getConductDrugTest());
			wdto.setConsiderDrugTest(wvo.getConsiderDrugTest());
			wdto.setHasEthicsPolicy(wvo.getHasEthicsPolicy());
			wdto.setRequireBadge(wvo.getRequireBadge());
			wdto.setConsiderBadge(wvo.getConsiderBadge());
			wdto.setRequireUsDoc(wvo.getRequireUsDoc());
			wdto.setConsiderImplPolicy(wvo.getConsiderImplPolicy());
			wdto.setConsiderEthicPolicy(wvo.getConsiderEthicPolicy());
			wdto.setWarrantyPeriods(wvo.getWarrantyPeriods());
			wdto.setVendorID(wvo.getVendorID());
		}
		return wdto;
	}
}
