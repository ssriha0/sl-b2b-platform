package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

/**
 * This VO represents details of imported ratings from one single west survey file 
 */
public class WestImportDetailsVO extends SerializableBaseVO {
	
	private static final long serialVersionUID = 944889416013337245L;

	private int fileId;
	private String soId;
	
	public WestImportDetailsVO(int fileId, String soId) {
		this.fileId = fileId;
		this.soId = soId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}
}
