package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.vo.provider.ForgotPasswordVO;

public interface IForgotPasswordBO {

	public Integer getSecretQuestionId(ForgotPasswordVO forgotPasswordVO) throws Exception;

}
