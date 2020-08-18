package com.newco.marketplace.search.types;


import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorCredentialsVO;

public class ProviderCredential {
	public final static int CREDENTIAL_STATUS_REVIEWED = 0;
	public final static int CREDENTIAL_STATUS_PENDING = 1;
	public final static int CREDENTIAL_STATUS_VERIFIED = 2;
	
	public final static String CREDENTIAL_STATUS_REVIEWED_STR = "REVIEWED";
	public final static String CREDENTIAL_STATUS_PENDING_STR = "PENDING";
	public final static String CREDENTIAL_STATUS_VERIFIED_STR = "VERIFIED";
	
	public final static int CREDENTIAL_SCOPE_RESOURCE = 0;
	public final static int CREDENTIAL_SCOPE_COMPANY = 1;
	
	private int credentialScope;	
	private String type;
	private String category;
	private String source;
	private String name;
	private int status;

	public ProviderCredential() {
		// intentionally blank
	}
	
	public ProviderCredential(TeamCredentialsVO inVo) {
		this.type = inVo.getCredType();
		this.source = inVo.getIssuerName();
		this.name = inVo.getLicenseName();
		this.category = inVo.getCredCategory();
		this.credentialScope = CREDENTIAL_SCOPE_RESOURCE;
		if (inVo.getWfStateId() == ProviderConstants.TEAM_MEMBER_CREDENTIAL_APPROVED.intValue()) {
			status = CREDENTIAL_STATUS_VERIFIED;
		} else if (inVo.getWfStateId() == ProviderConstants.TEAM_MEMBER_CREDENTIAL_PENDING_APPROVAL.intValue()) {
			status = CREDENTIAL_STATUS_PENDING;
		} else {
			status = CREDENTIAL_STATUS_REVIEWED;
		}
		//inVo.getTypeId();	
	}
	
	public ProviderCredential(VendorCredentialsVO inVo) {
		this.type = inVo.getCredType();
		this.source = inVo.getSource();
		this.name = inVo.getName();
		this.category = inVo.getCredCategory();
		this.credentialScope = CREDENTIAL_SCOPE_COMPANY;
		
		if (inVo.getWfStateId() == ProviderConstants.TEAM_MEMBER_CREDENTIAL_APPROVED.intValue()) {
			status = CREDENTIAL_STATUS_VERIFIED;
		} else if (inVo.getWfStateId() == ProviderConstants.TEAM_MEMBER_CREDENTIAL_PENDING_APPROVAL.intValue()) {
			status = CREDENTIAL_STATUS_PENDING;
		} else {
			status = CREDENTIAL_STATUS_REVIEWED;
		}
		//inVo.getTypeId();	
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStatusStr() {
		switch (this.status) {
		case CREDENTIAL_STATUS_REVIEWED:
			return CREDENTIAL_STATUS_REVIEWED_STR;
		case CREDENTIAL_STATUS_PENDING:
			return CREDENTIAL_STATUS_PENDING_STR;
		case CREDENTIAL_STATUS_VERIFIED:
			return CREDENTIAL_STATUS_VERIFIED_STR;
		default:	
			return CREDENTIAL_STATUS_REVIEWED_STR;	
		}
	}	

	public int getCredentialScope() {
		return credentialScope;
	}

	public void setCredentialScope(int credentialScope) {
		this.credentialScope = credentialScope;
	}	
}
