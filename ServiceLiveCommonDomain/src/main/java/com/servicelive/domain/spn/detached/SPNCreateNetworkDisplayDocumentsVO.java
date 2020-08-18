package com.servicelive.domain.spn.detached;
/**
 * 
 * @author svanloo
 *
 */
public class SPNCreateNetworkDisplayDocumentsVO
{
	private Integer spnDocId;
	private Integer documentId;//
	private String title;
	private String type;
	private Integer typeId;
	private String description;
	/**
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * @return String
	 */
	public String getType() {
		return type;
	}
	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * 
	 * @param typeId
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getDocumentId() {
		return documentId;
	}
	/**
	 * 
	 * @param documentId
	 */
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getSpnDocId() {
		return spnDocId;
	}
	/**
	 * 
	 * @param spnDocId
	 */
	public void setSpnDocId(Integer spnDocId) {
		this.spnDocId = spnDocId;
	}
}