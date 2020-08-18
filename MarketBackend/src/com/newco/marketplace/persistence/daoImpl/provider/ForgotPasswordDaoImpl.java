package com.newco.marketplace.persistence.daoImpl.provider;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.newco.marketplace.persistence.iDao.provider.IForgotPasswordDao;

public class ForgotPasswordDaoImpl extends SqlMapClientDaoSupport implements IForgotPasswordDao {

	public Integer getSecretQuestionId(String userName) throws Exception {
		Integer questionId = null;
		try {
			questionId = (Integer) getSqlMapClient().queryForObject(
				"getSecretQuestionId.query", userName);
	} catch (Exception ex) {
		logger.info("[ForgotPasswordDaoImpl.getSecretQuestionId - Exception] "
				+ ex.getStackTrace());
		ex.printStackTrace();
		throw ex;
	}
	return questionId;
	}

}
