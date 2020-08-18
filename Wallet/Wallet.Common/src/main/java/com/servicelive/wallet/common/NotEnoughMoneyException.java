package com.servicelive.wallet.common;

import java.util.List;

import com.servicelive.common.exception.SLBusinessServiceException;

public class NotEnoughMoneyException extends SLBusinessServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1328043978974806605L;

	public NotEnoughMoneyException(List<String> errors) {
		super(errors);
	}

}
