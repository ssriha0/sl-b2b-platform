package com.newco.marketplace.web.action.wizard;

public interface ISOWAction
{
	public String createEntryPoint() throws Exception;
	public String editEntryPoint() throws Exception;
	public String saveAsDraft() throws Exception;
	public String createAndRoute() throws Exception;
	public String next() throws Exception;
	public String previous() throws Exception;
	public String cancel() throws Exception;
}
