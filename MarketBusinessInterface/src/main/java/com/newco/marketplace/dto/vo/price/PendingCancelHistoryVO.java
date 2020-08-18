package com.newco.marketplace.dto.vo.price;



import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class PendingCancelHistoryVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2381160269187382921L;
	private Integer pendingCancelHistoryId;
	private String soId;
    private Double price;
    private Integer userId;
    private String comments;
    private Boolean withdrawFlag;
    private String userType;
    private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Boolean getWithdrawFlag() {
		return withdrawFlag;
	}
	public void setWithdrawFlag(Boolean withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getPendingCancelHistoryId() {
		return pendingCancelHistoryId;
	}
	public void setPendingCancelHistoryId(Integer pendingCancelHistoryId) {
		this.pendingCancelHistoryId = pendingCancelHistoryId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	} 
	
	

}
