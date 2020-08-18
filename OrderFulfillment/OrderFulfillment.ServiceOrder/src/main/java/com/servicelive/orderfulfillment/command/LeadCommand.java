package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.dao.ILeadDao;

public abstract class LeadCommand implements ILeadCommand{
	protected final Logger logger = Logger.getLogger(getClass());
	protected ILeadDao leadDao;
	public abstract void execute(Map<String, Object> processVariables);
	public void setLeadDao(ILeadDao leadDao) {
		this.leadDao = leadDao;
	}
}
