package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.persistence.iDao.provider.IAuditLogDao;
import com.newco.marketplace.vo.audit.LoginAuditVO;

public class AuditLogDaoImpl extends SqlMapClientDaoSupport implements IAuditLogDao {

	private Logger logger = Logger.getLogger(AuditLogDaoImpl.class);

	public HashMap<String,Integer> insertLoginAudit(LoginAuditVO loginAuditVO) {
		HashMap<String,Integer> auditLogMap = new HashMap<String,Integer>();
		Integer loginAuditId = 0;
		int activeSessionCount = getActiveSessionsCount(loginAuditVO);
		if(activeSessionCount == 0) { // No active session, insert new login audit record
			loginAuditId = (Integer) getSqlMapClientTemplate().insert("login_audit.insert", loginAuditVO);
			logger.info("LoginLogging: LoginAudit record created with ID:" + loginAuditId);
		} else { // Retrieve latest login audit record associated with this user 
			loginAuditId = (Integer) getSqlMapClientTemplate().queryForObject("login_audit.select_latest_by_resource_company_role", loginAuditVO);
		}
		Integer sessionAuditId = (Integer) getSqlMapClientTemplate().insert("active_session_audit.insert", loginAuditVO);
		auditLogMap.put("loginAuditId", loginAuditId);
		auditLogMap.put("sessionAuditId", sessionAuditId);
		return auditLogMap;
	}

	public void updateLoginAudit(LoginAuditVO loginAuditVO, int loginAuditId, int activeSessionAuditId, int timeoutInd) {
		HashMap<String,Integer> loginAuditMap = new HashMap<String,Integer>();
		loginAuditMap.put("loginAuditId", loginAuditId);
		loginAuditMap.put("timeoutInd", timeoutInd);
		int activeSessionCount = getActiveSessionsCount(loginAuditVO);
		if(activeSessionCount <= 1) {
			getSqlMapClientTemplate().update("login_audit.update", loginAuditMap);
			logger.info("LoginLogging: LoginAudit record updated for ID:" + loginAuditId);
		} else {
			/* ****** This ELSE block is added to handle scenario when jboss is killed in production for app deployment and users active session / login audit record get remained unclosed ***** */
			// Check if there are any active sessions older than today
			int unclosedExpiredSessionCount = (Integer)getSqlMapClientTemplate().queryForObject("active_session_audit.query_unclosedExpiredSessions", loginAuditVO);
			if (unclosedExpiredSessionCount > 0) {
				// DELETE the older active sessions and UPDATE the previously unclosed audit record with default logout time
				int deletedRecordsCount = getSqlMapClientTemplate().delete("active_session_audit.delete_unclosedExpiredSessions", loginAuditVO);
				int updatedRecordsCount = getSqlMapClientTemplate().update("login_audit.update_unclosedExpiredAuditRecord", loginAuditId);
				logger.info("LoginLogging: DeletedRecordsCount:" + deletedRecordsCount + " UpdatedRecordsCount:" + updatedRecordsCount);
			}
		}
		
		getSqlMapClientTemplate().update("active_session_audit.delete", activeSessionAuditId);
	}

	private int getActiveSessionsCount(LoginAuditVO loginAuditVO) {
		Integer activeSessionCount = (Integer) getSqlMapClientTemplate().queryForObject("activeSessionCount.query", loginAuditVO);
		return (activeSessionCount == null ? 0 : activeSessionCount.intValue());
	}

	public void auditUserProfile(AuditUserProfileVO auditUserProfileVO) {
		getSqlMapClientTemplate().insert("audit_user_profile.insert",auditUserProfileVO);
	}

}
