package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * VO for the so_document table
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

public class SoDocumentVO extends CommonVO {
	static final long serialVersionUID = 1111111;
	
	private int documentId;
	private String soId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String docSource;
	public int getDocumentId() {
		return documentId;
	}
	public String getDocSource() {
		return docSource;
	}
	public void setDocSource(String docSource) {
		this.docSource = docSource;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	

}
/*
 * Maintenance History
 * $Log: SoDocumentVO.java,v $
 * Revision 1.3  2008/04/26 00:40:08  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.1.30.1  2008/04/23 11:41:14  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.2  2008/04/23 05:17:05  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.1  2007/11/02 01:11:23  akashya
 * Initial checkin
 *
 */