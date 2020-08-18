package com.newco.marketplace.dto.vo.wallet;

import com.newco.marketplace.webservices.base.CommonVO;

public class LookupWalletControl extends CommonVO{
	private Integer id;
    private String name;
    private String description;
    private String banner;
    private Integer preference;
    private Integer emailTemplateId;
    private Integer transferReasonCode;
    private Integer releaseTemplateId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public Integer getPreference() {
		return preference;
	}
	public void setPreference(Integer preference) {
		this.preference = preference;
	}
	public Integer getEmailTemplateId() {
		return emailTemplateId;
	}
	public void setEmailTemplateId(Integer emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}
	public Integer getTransferReasonCode() {
		return transferReasonCode;
	}
	public void setTransferReasonCode(Integer transferReasonCode) {
		this.transferReasonCode = transferReasonCode;
	}
	public Integer getReleaseTemplateId() {
		return releaseTemplateId;
	}
	public void setReleaseTemplateId(Integer releaseTemplateId) {
		this.releaseTemplateId = releaseTemplateId;
	}
    
    
}
