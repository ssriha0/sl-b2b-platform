package com.servicelive.domain.pendingcancel;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import com.servicelive.domain.LoggableBaseDomain;

/**
 * PendingCancelHistory entity.
 * 
 */
@Entity
@Table(name = "so_cancel_request_history")
public class PendingCancelHistory extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9077177299188337164L;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cancellation_hist_id", unique = true, nullable = false)
	private Integer pendingCancelHistoryId;
	
        
    @Column(name = "so_id")
    private String soId;
    
   
    @Column(name = "price")
    private Double price;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "comments")
    private String comments;
    
    @Column(name = "withdraw_ind")
    private Boolean withdrawFlag;
    
    @Column(name = "role_id")
    private Integer roleId;
    
    
    @Column(name = "admin_resource_id")
    private Integer adminResourceId;
    
    
    @Column(name = "admin_user_name")
    private String adminUserName;
    
    @Column(name = "so_substatus_id")
    private Integer wfStateId;
    

	// Constructors

	/** default constructor */
	public PendingCancelHistory() {
		super();
	}


	public Integer getPendingCancelHistoryId() {
		return pendingCancelHistoryId;
	}


	public void setPendingCancelHistoryId(Integer pendingCancelHistoryId) {
		this.pendingCancelHistoryId = pendingCancelHistoryId;
	}


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


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public Integer getAdminResourceId() {
		return adminResourceId;
	}


	public void setAdminResourceId(Integer adminResourceId) {
		this.adminResourceId = adminResourceId;
	}


	public String getAdminUserName() {
		return adminUserName;
	}


	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}


	public Integer getWfStateId() {
		return wfStateId;
	}


	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
    
	
	
	
	}