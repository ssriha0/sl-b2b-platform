package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.persistence.iDao.provider.IAuditDao;
import com.newco.marketplace.vo.provider.AuditVO;

/**
 * @author KSudhanshu
 * 
 */
public class AuditDaoImpl extends SqlMapClientDaoSupport implements IAuditDao,
		AuditStatesInterface {
	private static final Logger logger = Logger.getLogger(AuditDaoImpl.class
			.getName());
	private static final MessageResources messages = MessageResources
			.getMessageResources("DataAccessLocalStrings");

	public AuditVO getResourceAudit(AuditVO auditVO) throws DBException {
		AuditVO rvAuditVO = null;
		try {
			rvAuditVO = (AuditVO) getSqlMapClient().queryForObject(
					"paudit_task.getAuditStatusForResource", auditVO);
		} catch (SQLException e) {

			throw new DBException("[AuditDaoImpl] - getResourceAudit", e);
		}
		return (rvAuditVO);
	}// getAudit

	public String getResourceBackgroundCheckStatus(AuditVO auditVO)
			throws DBException {
		try {
			String rvString = (String) getSqlMapClient().queryForObject(
					"paudit_task.getResourceBackgroundCheckStatus", auditVO);
			if (null == rvString) {
				throw new DBException(
						"[AuditDaoImpl]Select query returned null for method getVendorBackgroundCheckStatus");
			}
			return (rvString);
		} catch (SQLException e) {
			throw new DBException(
					"[AuditDaoImpl] - getResourceBackgroundCheckStatus", e);
		}
	}// getBackgroundCheckStatus


	public String getStatusByTaskId(AuditVO auditVO) throws DBException {
		try {
			return (String) getSqlMapClient().queryForObject(
					"paudit_task.getStatusByTaskId", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getStatusByTaskId", e);
		}
	}

	public String getStatusByVendorId(AuditVO auditVO) throws DBException {
		try {
			return (String) getSqlMapClient().queryForObject(
					"paudit_task.getStatusByVendorId", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getStatusByVendorId", e);
		}
	}

	public String getStatusResourceCredential(AuditVO auditVO)
			throws DBException {
		try {
			String rvString = (String) getSqlMapClient().queryForObject(
					"paudit_task.getStatusResourceCredential", auditVO);
			if (null == rvString) {
				throw new DBException(
						"[AuditDaoImpl]Select query returned null for method selectStatus Resource credential.");
			}
			return (rvString);
		} catch (SQLException e) {
			throw new DBException(
					"[AuditDaoImpl] - getStatusResourceCredential", e);
		}
	}

	public String getStatusVendorCredential(AuditVO auditVO) throws DBException {
		try {
			String rvString = (String) getSqlMapClient().queryForObject(
					"paudit_task.getStatusVendorCredential", auditVO);
			if (null == rvString) {
				throw new DBException(
						"[AuditDaoImpl]Select query returned null for method.");
			}
			return (rvString);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getStatusVendorCredential",
					e);
		}

	}

	public String getStatusVendorHeader(AuditVO auditVO) throws DBException {
		try {
			String rvString = (String) getSqlMapClient().queryForObject(
					"paudit_task.getStatusVendorHeader", auditVO);
			if (null == rvString) {
				throw new DBException(
						"[AuditDaoImpl]Select query returned null for method getStatusVendorHeader.");
			}
			return (rvString);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getStatusVendorHeader", e);
		}
	}

	public String getStatusVendorResource(AuditVO auditVO) throws DBException {
		try {
			String rvString = (String) getSqlMapClient().queryForObject(
					"paudit_task.getStatusVendorResource", auditVO);
			if (null == rvString) {
				throw new DBException(
						"[AuditDaoImpl]Select query returned null for method getStatusVendorResource.");
			}
			return (rvString);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getStatusVendorResource", e);
		}
	}

	public AuditVO getVendorAudit(AuditVO auditVO) throws DBException {
		try {
			AuditVO rvAuditVO = null;
			rvAuditVO = (AuditVO) getSqlMapClient().queryForObject(
					"paudit_task.getAuditStatusForVendor", auditVO);
			return (rvAuditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorAudit", e);
		}
	}

	public String getVendorBackgroundCheckStatus(AuditVO auditVO)
			throws DBException {
		try {
			String rvString = (String) getSqlMapClient().queryForObject(
					"paudit_task.getVendorBackgroundCheckStatus", auditVO);
			if (null == rvString) {
				throw new DBException(
						"[AuditDaoImpl]Select query returned null for method getVendorBackgroundCheckStatus");
			}
			return (rvString);
		} catch (SQLException e) {
			throw new DBException(
					"[AuditDaoImpl] - getVendorBackgroundCheckStatus", e);
		}
	}// getBackgroundCheckStatus

	public int getVendorIdByResourceId(int resourceId) throws DBException {
		try {
			Integer rvInteger = (Integer) getSqlMapClient().queryForObject(
					"paudit_task.getVendorIdByResourceId", resourceId);
			if (null == rvInteger) {
				throw new DBException(
						"[AuditDaoImpl] Select query returned null for method getVendorIdByResourceId");
			}
			return (rvInteger.intValue());
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// getVendorIdByResourceId

	public String getVendorStatusDocument(AuditVO auditVO) throws DBException {
		try {
			String rvString = (String) getSqlMapClient().queryForObject(
					"paudit_task.getVendorStatusDocument", auditVO);
			if (null == rvString) {
				throw new DBException(
						"[AuditDaoImpl]Select query returned null for method getVendorStatusDocument.");
			}
			return (rvString);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// getVendorStatusDocument

	public AuditVO insert(AuditVO auditVO) throws DBException {
		Integer auditTaskId = null;
		try {
			auditTaskId = (Integer) getSqlMapClient().insert("paudit_task.insert",
					auditVO);
			auditVO.setAuditTaskId(auditTaskId);
		} catch (Exception e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
		return auditVO;
	}// insert

	public int submitForVendorCompliance(int vendorId) throws DBException {
		try {
			return getSqlMapClient()
					.update(
							"paudit_task.submitForVendorComplianceStatus",
							new AuditVO(vendorId, 0,
									VENDOR_PENDING_COMPLIANCE_APPROVAL));
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int updateByResourceId(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update("paudit_task.updateByResourceId",
					auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// update

	public int updateByVendorId(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update("paudit_task.updateByVendorId",
					auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// update

	public int updateResourceBackgroundCheckStatus(AuditVO auditVO)
			throws DBException {
		try {
			return (getSqlMapClient().update(
					"paudit_task.updateResourceBackgroundCheckStatus", auditVO));
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int updateStatusByResourceId(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStatusByResourceId", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// updateStatusByResourceId(AuditVO)

	public int updateStatusByTaskId(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update("paudit_task.updateStatusByTaskId",
					auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// updateStatusByResourceId(AuditVO)

	public int updateStatusByVendorId(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStatusByVendorId", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// updateStatusByResourceId(AuditVO)

	public int updateStatusResourceCredential(AuditVO auditVO)
			throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStatusResourceCredential", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int updateStatusVendorCredential(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStatusVendorCredential", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int updateStatusVendorCredentialById(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStatusVendorCredentialById", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}
	
	
	public int updateStatusVendorHeader(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStatusVendorHeader", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int updateStatusVendorResource(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStatusVendorResource", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public String getVendorComplianceStatus(int vendorId) throws DBException {
		try {
			return (String) getSqlMapClient().queryForObject(
					"paudit_task.getVendorComplianceStatus", vendorId);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public Integer getVendorIdByUserName(String userName) throws DBException {
		try {
			Integer rvInteger = (Integer) getSqlMapClient().queryForObject(
					"paudit_task.getVendorIdByUserName", userName);
			if (null == rvInteger) {
				throw new DBException(
						"[AuditDaoImpl] Select query returned null for method getVendorIdByResourceId");
			}
			return (rvInteger.intValue());
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	} // getVendorIdByUserName

	public int setAuditPendingStatusForVendorHdr(int vendorId)
			throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.setAuditPendingStatusForVendorHdr", vendorId);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int unsetAuditPendingStatusForVendorHdr(int vendorId)
			throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.unsetAuditPendingStatusForVendorHdr", vendorId);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int getAuditPendingStatusForVendorHdr(int vendorId)
			throws DBException {
		try {
			Integer rvInteger = null;
			rvInteger = ((Integer) getSqlMapClient().queryForObject(
					"paudit_task.getAuditPendingStatusForVendorHdr", vendorId));

			return (rvInteger);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int updateStateReasonCdForResource(AuditVO auditVO)
			throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateStateReasonCdForResource", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int insertReasonCdForResource(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.insertReasonCdForResource", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public int deleteReasonCdForResource(AuditVO auditVO) throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.deleteReasonCdForResource", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}

	public AuditVO queryWfStateReasonCd(AuditVO auditVO) throws DBException {
		AuditVO auditVO2=null;
		try {
			auditVO2 = (AuditVO) getSqlMapClient().queryForObject(
					"paudit_task.queryWfStateReasonCd", auditVO);
			auditVO.setAuditTaskId(auditVO2.getAuditTaskId());
		} catch (Exception ex) {
			logger.info("[AuditDaoImpl.query- Exception] " + ex);
			final String error = messages.getMessage("dataaccess.select");
			throw new DBException(error, ex);
		}
		return auditVO;
	}

	public AuditVO queryWfStateReasonCdTM(AuditVO auditVO) throws DBException {
		try {
			logger.debug("In the queryWfStateReasonCdTM WORKFLOW ENTITY: "
					+ auditVO.getWfEntity());
			auditVO = (AuditVO) getSqlMapClient().queryForObject(
					"paudit_task.queryWfStateReasonCdTM", auditVO);
		} catch (Exception ex) {
			logger.info("[AuditDaoImpl.query- Exception] " + ex);
			final String error = messages.getMessage("dataaccess.select");
			throw new DBException(error, ex);
		}

		return auditVO;
	}

	public int updateBackGroundCheckByResourceId(AuditVO auditVO)
			throws DBException {
		try {
			return getSqlMapClient().update(
					"paudit_task.updateBGCheckByResourceId", auditVO);
		} catch (SQLException e) {
			throw new DBException("[AuditDaoImpl] - getVendorIdByResourceId", e);
		}
	}// update
	/*
	 * method to get the reason description for reason code for a particular document
	 */
	public String getReasonCodeDescription(AuditVO auditVO) throws DBException {
		String reasonDesc = null;
		try {
			reasonDesc = (String) getSqlMapClient().queryForObject(
					"paudit_task.getReasonCodeByVendorIDAndCredentialID", auditVO);
			if (null == reasonDesc) {
				reasonDesc = "";
			}
		} catch (Exception ex) {
			logger.info("[AuditDaoImpl.query- Exception] " + ex);
			final String error = messages.getMessage("dataaccess.select");
			throw new DBException(error, ex);
		}

		return reasonDesc;
	}
}// AuditDaoImpl
