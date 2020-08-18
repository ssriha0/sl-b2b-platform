/**
 * 
 */
package com.newco.marketplace.dto.vo.ach;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 * Entry Detail - Reject Reason Information
 *
 */
public class RejectReasonVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7906150017984443108L;
	private Integer rejectReasonId;
	private Integer categoryId;
	private String categoryDesc;
	private String rejectReasonCode;
	private String rejectReasonDesc;
	
	
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
	public String getRejectReasonCode() {
		return rejectReasonCode;
	}
	public void setRejectReasonCode(String rejectReasonCode) {
		this.rejectReasonCode = rejectReasonCode;
	}
	public String getRejectReasonDesc() {
		return rejectReasonDesc;
	}
	public void setRejectReasonDesc(String rejectReasonDesc) {
		this.rejectReasonDesc = rejectReasonDesc;
	}
	public Integer getRejectReasonId() {
		return rejectReasonId;
	}
	public void setRejectReasonId(Integer rejectReasonId) {
		this.rejectReasonId = rejectReasonId;
	}
	
	
}
