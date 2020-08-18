package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_manage_scope")
@XmlRootElement()
public class SOManageScope extends SOChild {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4024615461348556794L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manage_scope_id")
	private Integer manageScopeId;
	
	@Column(name= "reason_code_id")
	private Integer reasonCodeId;
	
	@Column(name= "reason_comments")
	private String reasonComments;
	
	@Column(name="manage_scope_source")
	private String manageScopeSource;

	public String getManageScopeSource() {
		return manageScopeSource;
	}

	public void setManageScopeSource(String manageScopeSource) {
		this.manageScopeSource = manageScopeSource;
	}

	public Integer getManageScopeId() {
		return manageScopeId;
	}

	public void setManageScopeId(Integer manageScopeId) {
		this.manageScopeId = manageScopeId;
	}

	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public String getReasonComments() {
		return reasonComments;
	}

	public void setReasonComments(String reasonComments) {
		this.reasonComments = reasonComments;
	}
	

}
