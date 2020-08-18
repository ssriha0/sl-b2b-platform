package com.newco.marketplace.web.delegates;

import java.util.List;

import com.newco.marketplace.exception.DelegateException;

public interface IBlackoutStatesDelegate {
	
	public List<String> getBlackoutStates() throws DelegateException;

}
