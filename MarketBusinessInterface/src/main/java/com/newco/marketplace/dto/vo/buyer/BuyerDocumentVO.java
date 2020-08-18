package com.newco.marketplace.dto.vo.buyer;

import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * VO for the buyer_document table
 * @author Gordon Jackson, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

public class BuyerDocumentVO extends CommonVO {
	
	private static final long serialVersionUID = -6490256575389837702L;

	private int documentId;
	private Integer buyerId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Integer categoryId;
	
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
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
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	

}
/*
 * Maintenance History
 * $Log: BuyerDocumentVO.java,v $
 * Revision 1.5  2008/04/26 00:40:09  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.12.1  2008/04/23 11:41:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.4  2008/04/23 05:16:57  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.3  2008/02/11 15:17:20  gjacks8
 * added buyer ref call
 *
 * Revision 1.1.2.1  2008/02/06 18:19:55  gjacks8
 * init
 *
 * Revision 1.1  2007/11/02 01:11:23  akashya
 * Initial checkin
 *
 */