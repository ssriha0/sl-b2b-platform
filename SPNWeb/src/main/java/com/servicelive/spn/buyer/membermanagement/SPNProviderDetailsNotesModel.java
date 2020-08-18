package com.servicelive.spn.buyer.membermanagement;

import java.util.List;

import com.servicelive.domain.spn.detached.SPNProviderDetailsFirmHistoryRowDTO;
import com.servicelive.domain.spn.detached.SPNProviderDetailsNoteRowDTO;
import com.servicelive.spn.core.SPNBaseModel;

public class SPNProviderDetailsNotesModel extends SPNBaseModel {

	private static final long serialVersionUID = 20100202L;

	private List<SPNProviderDetailsFirmHistoryRowDTO> firmChangeHistoryList;
	
	private List<SPNProviderDetailsNoteRowDTO> noteList;

	public List<SPNProviderDetailsFirmHistoryRowDTO> getFirmChangeHistoryList() {
		return firmChangeHistoryList;
	}

	public void setFirmChangeHistoryList(
			List<SPNProviderDetailsFirmHistoryRowDTO> firmChangeHistoryList) {
		this.firmChangeHistoryList = firmChangeHistoryList;
	}

	public List<SPNProviderDetailsNoteRowDTO> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<SPNProviderDetailsNoteRowDTO> noteList) {
		this.noteList = noteList;
	}
	
	
}
