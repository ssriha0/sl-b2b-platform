package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.dao.ILeadDao;

public interface ILeadCommand {
  public	void setLeadDao(ILeadDao leadDao);
  public abstract void execute(Map<String, Object> processVariables);
}
