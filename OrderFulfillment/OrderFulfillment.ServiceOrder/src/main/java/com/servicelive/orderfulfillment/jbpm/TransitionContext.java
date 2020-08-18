package com.servicelive.orderfulfillment.jbpm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransitionContext {
	private String soProcessId;
	private String transitionName;
	private Map<String, Serializable> processVariables;
	private Map<String, TransientVariable> transientVariables;
	
	////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////////////////////	
	public TransitionContext(String soProcessId, String transitionName) {
		setSoProcessId(soProcessId);
		setTransitionName(transitionName);
	}

	public TransitionContext(String soProcessId, String transitionName, Map<String, Serializable> processVariables) {
		setSoProcessId(soProcessId);
		setTransitionName(transitionName);
		setProcessVariables(processVariables);
	}

	public TransitionContext(String soProcessId, String transitionName, Map<String, Serializable> processVariables, Map<String, TransientVariable> transientVariables) {
		setSoProcessId(soProcessId);
		setTransitionName(transitionName);
		setProcessVariables(processVariables);
		setTransientVariables(transientVariables);
	}

	////////////////////////////////////////////////////////////////////////////
	// SETTERS AND GETTERS
	////////////////////////////////////////////////////////////////////////////
	public String getSoProcessId() {
		return soProcessId;
	}
	public void setSoProcessId(String soProcessId) {
		this.soProcessId = soProcessId;
	}
	
	public String getTransitionName() {
		return transitionName;
	}
	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
	
	public Map<String, Serializable> getProcessVariables() {
		return processVariables;
	}

	public void setProcessVariables(Map<String, Serializable> processVariables) {
		this.processVariables = processVariables;
	}

	public Map<String, TransientVariable> getTransientVariables() {
		return transientVariables;
	}

	public void setTransientVariables(Map<String, TransientVariable> transientVariables) {
		this.transientVariables = transientVariables;
	}

	////////////////////////////////////////////////////////////////////////////
	// HELPER METHODS
	////////////////////////////////////////////////////////////////////////////
	public Set<String> getProcessVariableNames() {
		if (processVariables == null)
			processVariables = new HashMap<String, Serializable>();
		return processVariables.keySet();
	}

	public Serializable getProcessVariable(String variableName) {
		if (processVariables == null) return null;
		else return processVariables.get(variableName);
	}

    public Set<String> getTransientVariableNames() {
        if (transientVariables == null)
            transientVariables = new HashMap<String, TransientVariable>();
        return transientVariables.keySet();
    }

    public TransientVariable getTransientVariable(String variableName) {
        if (transientVariables == null) return null;
        else return transientVariables.get(variableName);
    }
}
