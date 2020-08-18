package com.servicelive.spn.buyer.network;

import java.util.List;
/**
 * 
 * 
 *
 */
public class SPNCreateNetworkDocumentsVO
{
	private boolean uploadSuccess = false;
	private Integer uploadDocId;
	private String uploadDocTitle;
	private String uploadDocDesc;
	private Integer uploadDocType;
	private String uploadDocES;
	private Integer deleteSPNDocId;
	private Integer deleteDocId;
	private Integer deleteDocFlag;
	private Integer uploadDocFlag;
	private List<Integer> uploadDocIdList;
	private List<String> uploadDocTitleList;
	private List<Integer> uploadDocTypeList;
	private List<String> uploadDocDescList;
	private String uploadDocErrorStr;
	
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getUploadDocIdList() {
		return uploadDocIdList;
	}
	/**
	 * 
	 * @param uploadDocIdList
	 */
	public void setUploadDocIdList(List<Integer> uploadDocIdList) {
		this.uploadDocIdList = uploadDocIdList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<String> getUploadDocTitleList() {
		return uploadDocTitleList;
	}
	/**
	 * 
	 * @param uploadDocTitleList
	 */
	public void setUploadDocTitleList(List<String> uploadDocTitleList) {
		this.uploadDocTitleList = uploadDocTitleList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getUploadDocTypeList() {
		return uploadDocTypeList;
	}
	/**
	 * 
	 * @param uploadDocTypeList
	 */
	public void setUploadDocTypeList(List<Integer> uploadDocTypeList) {
		this.uploadDocTypeList = uploadDocTypeList;
	}
	/**
	 * 
	 * @return List
	 */
	public List<String> getUploadDocDescList() {
		return uploadDocDescList;
	}
	/**
	 * 
	 * @param uploadDocDescList
	 */
	public void setUploadDocDescList(List<String> uploadDocDescList) {
		this.uploadDocDescList = uploadDocDescList;
	}
	/**
	 * 
	 * @return boolean
	 */
	public boolean isUploadSuccess() {
		return uploadSuccess;
	}
	/**
	 * 
	 * @param uploadSuccess
	 */
	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getUploadDocId() {
		return uploadDocId;
	}
	/**
	 * 
	 * @param uploadDocId
	 */
	public void setUploadDocId(Integer uploadDocId) {
		this.uploadDocId = uploadDocId;
	}
	/**
	 * 
	 * @return String
	 */
	public String getUploadDocTitle() {
		return uploadDocTitle;
	}
	/**
	 * 
	 * @param uploadDocTitle
	 */
	public void setUploadDocTitle(String uploadDocTitle) {
		this.uploadDocTitle = uploadDocTitle;
	}
	/**
	 * 
	 * @return String
	 */
	public String getUploadDocDesc() {
		return uploadDocDesc;
	}
	/**
	 * 
	 * @param uploadDocDesc
	 */
	public void setUploadDocDesc(String uploadDocDesc) {
		this.uploadDocDesc = uploadDocDesc;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getUploadDocType() {
		return uploadDocType;
	}
	/**
	 * 
	 * @param uploadDocType
	 */
	public void setUploadDocType(Integer uploadDocType) {
		this.uploadDocType = uploadDocType;
	}
	/**
	 * 
	 * @return String
	 */
	public String getUploadDocES() {
		return uploadDocES;
	}
	/**
	 * 
	 * @param uploadDocES
	 */
	public void setUploadDocES(String uploadDocES) {
		this.uploadDocES = uploadDocES;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getDeleteSPNDocId() {
		return deleteSPNDocId;
	}
	/**
	 * 
	 * @param deleteSPNDocId
	 */
	public void setDeleteSPNDocId(Integer deleteSPNDocId) {
		this.deleteSPNDocId = deleteSPNDocId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getDeleteDocId() {
		return deleteDocId;
	}
	/**
	 * 
	 * @param deleteDocId
	 */
	public void setDeleteDocId(Integer deleteDocId) {
		this.deleteDocId = deleteDocId;
	}
	/**
	 * 
	 * @return String
	 */
	public String getUploadDocErrorStr() {
		return uploadDocErrorStr;
	}
	/**
	 * 
	 * @param uploadDocErrorStr
	 */
	public void setUploadDocErrorStr(String uploadDocErrorStr) {
		this.uploadDocErrorStr = uploadDocErrorStr;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getDeleteDocFlag() {
		return deleteDocFlag;
	}
	/**
	 * 
	 * @param deleteDocFlag
	 */
	public void setDeleteDocFlag(Integer deleteDocFlag) {
		this.deleteDocFlag = deleteDocFlag;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getUploadDocFlag() {
		return uploadDocFlag;
	}
	/**
	 * 
	 * @param uploadDocFlag
	 */
	public void setUploadDocFlag(Integer uploadDocFlag) {
		this.uploadDocFlag = uploadDocFlag;
	}	
}