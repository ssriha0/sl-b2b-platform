package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

public class FirmUploadLogo {
	private List<String> formData = new ArrayList<String>();
	
	private FileData file;
	
	public FileData getFile() {
		return file;
	}

	public void setFile(FileData file) {
		this.file = file;
	}

	public List<String> getFormData() {
		return formData;
	}

	public void setFormData(List<String> formData) {
		this.formData = formData;
	}



}
