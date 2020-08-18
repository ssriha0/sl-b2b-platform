package com.newco.marketplace.web.dto.ajax;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class AjaxCommonAddNoteReq extends SerializedBaseDTO{
	
	private String commonNoteSubject;
	private String commonMessageNote;
	
	
	public String getCommonNoteSubject() {
		return commonNoteSubject;
	}
	public void setCommonNoteSubject(String commonNoteSubject) {
		this.commonNoteSubject = commonNoteSubject;
	}
	public String getCommonMessageNote() {
		return commonMessageNote;
	}
	public void setCommonMessageNote(String commonMessageNote) {
		this.commonMessageNote = commonMessageNote;
	}

}
