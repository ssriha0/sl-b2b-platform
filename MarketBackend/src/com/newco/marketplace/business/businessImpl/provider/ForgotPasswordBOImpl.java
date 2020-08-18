package com.newco.marketplace.business.businessImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.IForgotPasswordBO;
import com.newco.marketplace.persistence.iDao.provider.IForgotPasswordDao;
import com.newco.marketplace.vo.provider.ForgotPasswordVO;

public class ForgotPasswordBOImpl implements IForgotPasswordBO {
	
	private IForgotPasswordDao iForgotPasswordDao;
	
	public ForgotPasswordBOImpl(IForgotPasswordDao iForgotPasswordDao) {
		this.iForgotPasswordDao = iForgotPasswordDao;
	}
	
	public Integer getSecretQuestionId(ForgotPasswordVO forgotPasswordVO) throws Exception {
		String userName = forgotPasswordVO.getUserName();
		return iForgotPasswordDao.getSecretQuestionId(userName);
	}
	
}
