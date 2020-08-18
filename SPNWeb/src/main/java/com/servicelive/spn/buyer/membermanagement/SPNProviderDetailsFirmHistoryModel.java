package com.servicelive.spn.buyer.membermanagement;

import java.util.List;

import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.spn.core.SPNBaseModel;

public class SPNProviderDetailsFirmHistoryModel extends SPNBaseModel {

	private static final long serialVersionUID = 20100210L;

	private List<SPNProviderDetailsFirmHistoryRowDTO> firmChangeHistoryList;

	public List<SPNProviderDetailsFirmHistoryRowDTO> getFirmChangeHistoryList() {
		return firmChangeHistoryList;
	}

	public void setFirmChangeHistoryList(List<SPNProviderDetailsFirmHistoryRowDTO> firmChangeHistoryList) {
		this.firmChangeHistoryList = firmChangeHistoryList;
	}
}
