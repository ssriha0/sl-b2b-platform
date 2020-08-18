/**
 * 
 */
package com.newco.marketplace.dto.vo.ach;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class AchResponseReasonVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4714285430845933276L;
	private Integer reasonId;
	private Integer fileTypeId;
	private String fileTypeCode;
	private Integer categoryId;
	private String categoryDesc;
	private String reasonCode;
	private String reasonDesc;
	private Integer reversalInd; 
	
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getFileTypeCode() {
		return fileTypeCode;
	}
	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}
	public Integer getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public Integer getReversalInd() {
		return reversalInd;
	}
	public void setReversalInd(Integer reversalInd) {
		this.reversalInd = reversalInd;
	}


	
	

}
