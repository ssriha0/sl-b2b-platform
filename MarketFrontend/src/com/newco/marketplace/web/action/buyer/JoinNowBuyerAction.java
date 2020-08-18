package com.newco.marketplace.web.action.buyer;

import com.newco.marketplace.web.security.NonSecurePage;
import com.opensymphony.xwork2.ActionSupport;

@NonSecurePage
public class JoinNowBuyerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		return SUCCESS;
	}
}
