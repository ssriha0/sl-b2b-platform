package com.newco.marketplace.persistence.iDao.provider;

public interface IForgotPasswordDao {

	public Integer getSecretQuestionId(String userName) throws Exception;
}
