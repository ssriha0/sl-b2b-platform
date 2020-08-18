package com.servicelive.orderfulfillment.node;

import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;

import java.util.Map;

public class DefaultCustomActivity implements ExternalActivityBehaviour {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ActivityExecution activityExecution) throws Exception {
        activityExecution.takeDefaultTransition();
    }

    public void signal(ActivityExecution activityExecution, String signalName, Map<String, ?> stringMap) throws Exception {
        activityExecution.take(signalName);
    }
}
