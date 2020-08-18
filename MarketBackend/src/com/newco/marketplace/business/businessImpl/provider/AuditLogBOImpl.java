package com.newco.marketplace.business.businessImpl.provider;

import java.util.HashMap;

import com.newco.marketplace.business.iBusiness.provider.IAuditLogBO;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.persistence.iDao.provider.IAuditLogDao;
import com.newco.marketplace.vo.audit.LoginAuditVO;

public class AuditLogBOImpl implements IAuditLogBO{

	private IAuditLogDao iAuditLogDao;

	public AuditLogBOImpl(IAuditLogDao iAuditLogDao) {
		this.iAuditLogDao = iAuditLogDao;
	}

	public HashMap<String,Integer> insertLoginAudit(LoginAuditVO loginAuditVO) {
		HashMap<String,Integer> auditLogMap = iAuditLogDao.insertLoginAudit(loginAuditVO);
		return auditLogMap;
	}

	public void updateLoginAudit(LoginAuditVO loginAuditVO, int loginAuditId, int activeSessionAuditId, int timeoutInd) {
		iAuditLogDao.updateLoginAudit(loginAuditVO, loginAuditId,activeSessionAuditId,timeoutInd);
	}

	public void auditUserProfile(AuditUserProfileVO auditUserProfileVO) {
		iAuditLogDao.auditUserProfile(auditUserProfileVO);
	}

}
