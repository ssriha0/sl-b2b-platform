package com.newco.marketplace.webservices.dto.serviceorder;

import com.sears.os.vo.response.ABaseServiceResponseVO;

public class AddNoteResponse extends ABaseServiceResponseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3011177952288702520L;

	private String noteId;

	private boolean hasError = false;
	
	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
}
