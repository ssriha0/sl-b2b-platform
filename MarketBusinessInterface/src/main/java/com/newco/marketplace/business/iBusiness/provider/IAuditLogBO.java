package com.newco.marketplace.business.iBusiness.provider;

import java.util.HashMap;

import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.vo.audit.LoginAuditVO;

public interface IAuditLogBO {

	public HashMap<String,Integer> insertLoginAudit(LoginAuditVO loginAuditVO);

	public void updateLoginAudit(LoginAuditVO loginAuditVO, int loginAuditId,int activeSessionAuditId, int timeoutInd);

	public void auditUserProfile(AuditUserProfileVO auditUserProfileVO);

}
