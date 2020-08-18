package com.newco.marketplace.business.iBusiness.audit.email;

import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.vo.audit.AuditVO;
import com.sears.os.vo.SerializableBaseVO;

public interface IAuditEmailBO {
	public void sendAuditEmail(String workflowEntity,  AuditVO auditVo)throws AuditException;
	public void sendAuditErrorEmail(AuditVO vo, String errorMessage);
}
