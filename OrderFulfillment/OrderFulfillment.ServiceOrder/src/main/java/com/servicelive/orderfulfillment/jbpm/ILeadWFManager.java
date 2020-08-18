package com.servicelive.orderfulfillment.jbpm;

import java.util.Map;


public interface ILeadWFManager 
{
	public String createNewWFInstance(String leadId, String type, Map<String, Object> variables);
	public boolean processExists(String processId);
	public boolean isTransitionAvailable(String pid, String transitionName);
    boolean waitForTransition(String pid, String transitionName, int timeoutSeconds);
	public boolean takeTransition(String pid, String transitionName);
	public boolean takeTransition(TransitionContext transitionCtx);
	public String createNewWFInstance(String processName, Map<String, Object> variables);
}
