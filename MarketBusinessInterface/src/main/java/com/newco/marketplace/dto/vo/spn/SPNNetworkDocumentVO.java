package com.newco.marketplace.dto.vo.spn;

import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

public class SPNNetworkDocumentVO extends CommonVO {
	private static final long serialVersionUID = -6490256575389837702L;

	private int documentId;
	private Integer spnNetworkId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Integer categoryId;
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public Integer getSpnNetworkId() {
		return spnNetworkId;
	}
	public void setSpnNetworkId(Integer spnNetworkId) {
		this.spnNetworkId = spnNetworkId;
	}
	
	

}
