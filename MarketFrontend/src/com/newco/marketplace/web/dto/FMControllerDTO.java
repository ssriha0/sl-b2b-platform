package com.newco.marketplace.web.dto;

public class FMControllerDTO extends SerializedBaseDTO {

	private static final long serialVersionUID = 0L;
	
	// For the Transfer Funds Widget
	private FMTransferFundsDTO transferFundsDTO = new FMTransferFundsDTO();

	
	
	public FMTransferFundsDTO getTransferFundsDTO() {
		return transferFundsDTO;
	}

	public void setTransferFundsDTO(FMTransferFundsDTO transferFundsDTO) {
		this.transferFundsDTO = transferFundsDTO;
	}
	
}
