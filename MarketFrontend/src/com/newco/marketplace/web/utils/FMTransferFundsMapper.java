package com.newco.marketplace.web.utils;

import com.newco.marketplace.dto.vo.ledger.FMTransferFundsVO;
import com.newco.marketplace.web.dto.FMTransferFundsDTO;

public class FMTransferFundsMapper extends ObjectMapper {
	
	public static FMTransferFundsVO convertDTOtoVO(Object objectDto, Object objectVO) {
		FMTransferFundsDTO transferFundsDTO = (FMTransferFundsDTO)objectDto;
		FMTransferFundsVO transferFundsVO = (FMTransferFundsVO)objectVO;
		transferFundsVO.setAmount(transferFundsDTO.getAmount());
		transferFundsVO.setFromAccount(transferFundsDTO.getFromAccount());
		transferFundsVO.setNote(transferFundsDTO.getNote());
		transferFundsVO.setReasonCode(transferFundsDTO.getReasonCode());
		transferFundsVO.setRoleType(transferFundsDTO.getRoleType());
		transferFundsVO.setToAccount(transferFundsDTO.getToAccount());
		transferFundsVO.setUser(transferFundsDTO.getAdminUser());
		transferFundsVO.setFirstName(transferFundsDTO.getFirstName());
		transferFundsVO.setLastName(transferFundsDTO.getLastName());
		transferFundsVO.setEntityId(transferFundsDTO.getEntityId());
		transferFundsVO.setWalletControlId(transferFundsDTO.getWalletControlId());
		transferFundsVO.setSlBucksMail(transferFundsDTO.getSlBucksMail());
		
		return transferFundsVO;
		
	}
	
	public static FMTransferFundsDTO convertVOtoDTO(Object objectVO, Object objectDto){
		
		FMTransferFundsVO transferFundsVO = (FMTransferFundsVO)objectVO;
		FMTransferFundsDTO transferFundsDTO = (FMTransferFundsDTO)objectDto;
		
		transferFundsDTO.setAmount(transferFundsVO.getAmount());
		transferFundsDTO.setFromAccount(transferFundsVO.getFromAccount());
		transferFundsDTO.setNote(transferFundsVO.getNote());
		transferFundsDTO.setReasonCode(transferFundsVO.getReasonCode());
		transferFundsDTO.setRoleType(transferFundsVO.getRoleType());
		transferFundsDTO.setToAccount(transferFundsVO.getToAccount());
		transferFundsDTO.setUser(transferFundsVO.getUser());
		
		
		
		return transferFundsDTO;
		
		
	}
	

}
