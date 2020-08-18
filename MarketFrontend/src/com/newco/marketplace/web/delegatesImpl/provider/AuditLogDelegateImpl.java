package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.HashMap;

import com.newco.marketplace.business.iBusiness.provider.IAuditLogBO;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.vo.audit.LoginAuditVO;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;

public class AuditLogDelegateImpl implements IAuditLogDelegate {

	private IAuditLogBO auditLogBOImpl;

	public AuditLogDelegateImpl(IAuditLogBO auditLogBOImpl) {
		this.auditLogBOImpl = auditLogBOImpl;
	}

	public HashMap<String,Integer> insertLoginAudit(LoginAuditVO loginAuditVO) {
		HashMap<String,Integer> auditLogMap = auditLogBOImpl.insertLoginAudit(loginAuditVO);
		return auditLogMap;
	}

	public void updateLoginAudit(LoginAuditVO loginAuditVO, int loginAuditId,int activeSessionAuditId, int timeoutInd) {
		auditLogBOImpl.updateLoginAudit(loginAuditVO, loginAuditId,activeSessionAuditId,timeoutInd);
	}

	public void auditUserProfile(AuditUserProfileVO auditUserProfileVO) {
		auditLogBOImpl.auditUserProfile(auditUserProfileVO);
	}

}
