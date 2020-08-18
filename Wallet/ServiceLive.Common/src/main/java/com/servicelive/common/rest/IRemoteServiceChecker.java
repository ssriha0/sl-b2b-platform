package com.servicelive.common.rest;

public interface IRemoteServiceChecker {
	public boolean isServiceActive();
	public String getRemoteServiceName();
}
